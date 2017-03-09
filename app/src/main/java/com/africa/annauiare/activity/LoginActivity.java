package com.africa.annauiare.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.africa.annauiare.AppController;
import com.africa.annauiare.dialog.ResetPasswordDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.africa.annauiare.common.BaseActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import com.africa.annauiare.R;
import com.africa.annauiare.ValidationResult;
import com.africa.annauiare.ValidationUtils;
import com.africa.annauiare.rx.firebase.RxFirebaseAuth;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {


    @Nullable
    @Bind(R.id.id_email)
    EditText email_tv;

    @Nullable
    @Bind(R.id.id_password)
    EditText password_tv;

    @Nullable
    @Bind(R.id.simple_sign_up)
    AppCompatButton sign_up_btn;

    @Nullable
    @Bind(R.id.simple_sign_in)
    AppCompatButton sign_in_btn;

    @Nullable
    @Bind(R.id.login_button)
    TextView loginButton;

    @OnClick(R.id.sign_in_facebook)
    public void facebook(View v){
       startFacebookLoginUi();
    }

    @Nullable
    @Bind(R.id.sign_in_google)
    ImageView signInButton;

    @Nullable
    @Bind(R.id.sign_in_twitter)
    ImageView twitterLoginButton;

    @OnClick(R.id.sign_in_twitter)
    public void signInTwiiter(View v){
        startTwitterLoginUi();
    }

    @OnClick(R.id.sign_in_google)
    public void signIn(View v){
        if (mGoogleApiClient!=null){
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @OnClick(R.id.simple_sign_in)
    public void simpleSignIn(View v){
       signIn();
    }

    @OnClick(R.id.simple_sign_up)
    public void simpleSignUp(View v){
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    @OnClick(R.id.forgot_password)
    public void forgotPassword(View v){
        FragmentManager fm = getSupportFragmentManager();
        ResetPasswordDialog dialogFragment = new ResetPasswordDialog();
        dialogFragment.show(fm, "OTP Fragment");
    }

    @OnClick(R.id.register_here)
    public void register(View v){
        Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    static final int RC_SIGN_IN = 9001;
    CallbackManager callbackManager;
    Subscription sigInSubscription;
    Subscription _subscription;
    int key=2;
    ProgressDialog progressDialog;
    TwitterAuthClient mTwitterAuthClient;

    @Override
    protected void onStart() {
        super.onStart();
        observeState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPlayServices())
            buildGoogleApiClient();
        setContentView(R.layout.activity_login);
        if (getIvLogo()!=null)
        getIvLogo().setText("Login");
        if (getIntent()!=null){
            key = getIntent().getIntExtra(LandingActivity.KEY_ACTIVITY,2);
        }
        createDialog();
        mTwitterAuthClient = new TwitterAuthClient();
        setupObservables1();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sigInSubscription!=null)
            sigInSubscription.unsubscribe();
        if (observeSignInSubscription !=null)
            observeSignInSubscription.unsubscribe();
    }

//    public void observeState(){
//        observeSignInSubscription = RxFirebaseAuth.observeAuthState(FirebaseAuth.getInstance())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<FirebaseUser>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(FirebaseUser firebaseUser) {
//                        if (firebaseUser != null) {
//                            // User is signed in
//                            Log.e(TAG, "onAuthStateChanged:signed_in:" + firebaseUser.getUid());
//                        } else {
//                            // User is signed out
//                            Log.e(TAG, "onAuthStateChanged:signed_out");
//                        }
//                    }
//                });
//    }

    private void initGoogleLoginUi(){
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
    }

    private void startTwitterLoginUi(){
//        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
//            @Override
//            public void success(Result<TwitterSession> result) {
//                // The TwitterSession is also available through:
//                // Twitter.getInstance().core.getSessionManager().getActiveSession()
//                TwitterSession session = result.data;
//                // TODO: Remove toast and use the TwitterSession's userID
//                // with your app's user model
//                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//                AuthCredential credential = TwitterAuthProvider.getCredential(
//                        session.getAuthToken().token,
//                        session.getAuthToken().secret);
//                handleFirebaseSignIn(credential);
//            }
//            @Override
//            public void failure(TwitterException exception) {
//                Log.d("TwitterKit", "Login with Twitter failure", exception);
//            }
//        });

        mTwitterAuthClient.authorize(LoginActivity.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                AuthCredential credential = TwitterAuthProvider.getCredential(
                        session.getAuthToken().token,
                        session.getAuthToken().secret);
                handleFirebaseSignIn(credential);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

    }
    private void startFacebookLoginUi(){
//        loginButton.setReadPermissions("email");
        // Callback registration
         callbackManager = CallbackManager.Factory.create();
        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email","user_photos","public_profile"));
         LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                handleFirebaseSignIn(credential);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager !=null)
          callbackManager.onActivityResult(requestCode, resultCode, data);

          mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
//         Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                Log.e(TAG, "firebaseAuthWithGoogle:" + account.getIdToken());
                firebaseAuthWithGoogle(account);
            }
            else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }

    }

    @Override
    public void onConnected(Location location) {

    }

    @Override
    public void fireBaseAuthication(FirebaseUser firebaseUser) {

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        handleFirebaseSignIn(credential);
    }


    private void handleFirebaseSignIn(AuthCredential credential){
        showProgress();
        sigInSubscription =  RxFirebaseAuth.signInWithCredential(FirebaseAuth.getInstance(),credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showProgress();
                    }

                    @Override
                    public void onNext(AuthResult authResult) {
                        showProgress();
                        if (authResult.getUser() != null) {
                            // User is signed in
                           goToNextActicity();
                        } else {
                            // User is signed out
                            Log.e(TAG, "onAuthStateChanged:signed_out");
                        }
                    }
                });
    }

    // Validate input data with debounce
    private void setupObservables1() {

        // Debounce is coming in very handy here.
        // What I had understood before is that if I use debounce, it will emit event after the give
        // time period regardless of other events.
        // But now I am realizing that this is not the case.
        // Let's say debounce interval is 200 milliseconds. Once an event is emitted, RxJava clock starts
        // ticking. Once 200 ms is up, debounce operator will emit that event.
        // One more event comes to debounce and it will start the clock for 200 ms. If another event comes
        // in 100 ms, debounce operator will reset the clock and start to count 200 ms again.
        // So let's say if you continue emitting events at 199 ms intervals, this debounce operator
        // will never emit any event.

        // Also, debounce by default goes on Scheduler thread, so it is important to add observeOn
        // and observe it on main thread.

        Observable<Boolean> emailObservable = RxTextView.textChanges(email_tv)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validateEmail(s.toString());
                        email_tv.setError(result.getReason());
                        return result.isValid();
                    }
                });


        Observable<Boolean> passwordObservable = RxTextView.textChanges(password_tv)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validatePassword(s.toString());
                        password_tv.setError(result.getReason());
                        return result.isValid();
                    }
                });


        _subscription = Observable.combineLatest(emailObservable, passwordObservable, new Func2<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean validEmail, Boolean validPassword) {
//               Log.i(TAG, "email: " + validEmail + ", username: " + validUsername + ", phone: " + validPhone);
                return validEmail && validPassword;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                sign_in_btn.setEnabled(aBoolean);
            }
        });
    }


    private void signIn(){
       showProgress();
        RxFirebaseAuth.signInWithEmailAndPassword(FirebaseAuth.getInstance(),email_tv.getText().toString(),password_tv.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AuthResult authResult) {
                        showProgress();
                        if (authResult.getUser() != null) {
                            // User is signed in
                            Log.e(TAG, "onAuthStateChanged:signed_in:" + authResult.getUser().getUid());
                            if(authResult.getUser().isEmailVerified()){
                                goToNextActicity();
//                                Toast.makeText(LoginActivity.this,"Wow you did it",Toast.LENGTH_SHORT).show();
                            }else{
                                authResult.getUser().sendEmailVerification();
                                Toast.makeText(LoginActivity.this,"Please verify your email",Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            // User is signed out
                            Log.e(TAG, "onAuthStateChanged:signed_out");
                        }
                    }
                });
    }


    private void goToNextActicity(){
        if (key==2){
            Intent i = new Intent(LoginActivity.this, LandingActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            finish();
        }else if(key == 1){
            Intent i = new Intent(LoginActivity.this, LandingActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            finish();
        }else{
            Intent i = new Intent(LoginActivity.this, LandingActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            finish();
        }
    }


    private ValidationResult<String> validateEmail(@NonNull String email) {
        return ValidationUtils.isValidEmailAddress(email);
    }

    private ValidationResult<String> validatePassword(@NonNull String password) {
        return ValidationUtils.isValidPassword(password);
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public  void createDialog() {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        try{
             progressDialog = new ProgressDialog(LoginActivity.this);
             progressDialog.setMessage("your account is validate you are connected on Top Africa Annuaire in a few second");
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public  void showProgress() {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        try{
            if (!progressDialog.isShowing())
                progressDialog.show();
            else
                progressDialog.dismiss();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


}
