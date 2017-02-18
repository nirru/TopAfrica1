package com.africa.annauiare.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.africa.annauiare.AppConstant;
import com.africa.annauiare.CountryToPhonePrefix;
import com.africa.annauiare.modal.category.Businesse;
import com.africa.annauiare.modal.places.Places;
import com.africa.annauiare.modal.user.RegisteredUser;
import com.africa.annauiare.rx.firebase.RxFirebaseDatabase;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.client.Firebase;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.rxbinding.widget.RxCheckedTextView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.africa.annauiare.common.BaseActivity;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import com.africa.annauiare.R;
import com.africa.annauiare.ValidationResult;
import com.africa.annauiare.ValidationUtils;
import com.africa.annauiare.rx.firebase.RxFirebaseAuth;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
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
import rx.functions.Func3;
import rx.functions.Func4;
import rx.functions.Func6;
import rx.schedulers.Schedulers;

public class SignUpActivity extends BaseActivity {

    @Nullable
    @Bind(R.id.reg_container)
    TextView reg_container;

    @Nullable
    @Bind(R.id.reg_container3)
    TextView reg_container3;

    @Nullable
    @Bind(R.id.sign_in)
    TextView sign_in;

    @Nullable
    @Bind(R.id.id_email)
    EditText email_tv;

    @Nullable
    @Bind(R.id.id_mobile_no)
    EditText mobile_tv;

    @Nullable
    @Bind(R.id.id_password)
    EditText password_tv;

    @Nullable
    @Bind(R.id.id_confirm_pwd)
    EditText confirm_pwd_tv;

    @Nullable
    @Bind(R.id.id_nikname)
    EditText nikname_tv;

    @Nullable
    @Bind(R.id.id_firstname)
    EditText firstname_tv;

    @Nullable
    @Bind(R.id.id_lastname)
    EditText lastname_tv;

    @Nullable
    @Bind(R.id.id_workplace)
    AutoCompleteTextView workplace_tv;

    @Nullable
    @Bind(R.id.id_homeplace)
    AutoCompleteTextView homeplace_tv;

    @Nullable
    @Bind(R.id.id_birthday)
    EditText birthday_tv;

    @Nullable
    @Bind(R.id.regview1)
    View registerview1;

    @Nullable
    @Bind(R.id.regview2)
    View registerview2;

    @Nullable
    @Bind(R.id.regview3)
    View registerview3;

    @Nullable
    @Bind(R.id.register1)
    AppCompatButton register1;


    @Nullable
    @Bind(R.id.register2)
    AppCompatButton register2;

    @Nullable
    @Bind(R.id.register3)
    AppCompatButton register3;

    @Nullable
    @Bind(R.id.appCompatCheckBox)
    AppCompatCheckBox appCompatCheckBox;

    @Nullable
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;

    @Nullable
    @Bind(R.id.action_male)
    RadioButton action_male;

    @Nullable
    @Bind(R.id.action_female)
    RadioButton action_female;

    @Nullable
    @Bind(R.id.country_flag)
    ImageView country_flag;

    @Nullable
    @Bind(R.id.id_country_code)
    TextView iso_code;

    @OnClick(R.id.id_birthday)
    public void choseBirthday(View v){
        openDatePicker();
    }

    @OnClick(R.id.country_view)
    public void chooseCountry(View v){
        mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
        mCountryPicker.setListener(new CountryPickerListener() {
            @Override public void onSelectCountry(String name, String code, String dialCode,
                                                  int flagDrawableResID) {
//                if (dialCode.contains("+")){
//                    dialCode.replace("+","");
//                }
                country_code =Integer.parseInt( dialCode);
                country_flag.setImageResource(flagDrawableResID);
                iso_code.setText(dialCode);
                mCountryPicker.dismiss();
            }
        });
    }

    @OnClick(R.id.sign_in)
    public void sign_in(View v){

    }

    @OnClick(R.id.register1)
    public void register1(View v){
        if (!appCompatCheckBox.isChecked()){
            Toast.makeText(SignUpActivity.this,"Please agree to terms & Conditions",Toast.LENGTH_SHORT).show();
        }else{
            registerview2.setVisibility(View.VISIBLE);
            registerview3.setVisibility(View.GONE);
            registerview1.setVisibility(View.GONE);
            signUp(new Firebase(AppConstant.FIREBASE_DATABSE_REFRENCE_URL+"users"));
        }
    }


    @OnClick(R.id.register2)
    public void register2(View v){
        if (!action_male.isChecked() && !action_female.isChecked()){
            Toast.makeText(SignUpActivity.this,"Please select gender",Toast.LENGTH_SHORT).show();
        }else{
            gender = action_male.isChecked() ? "Male" : "Female";
            registerview3.setVisibility(View.VISIBLE);
            registerview2.setVisibility(View.GONE);
            registerview1.setVisibility(View.GONE);
            getIvLogo().setText("Registration Complete");
            reg_container.setText(nikname_tv.getText().toString());
            reg_container3.setText(email_tv.getText().toString());
            Firebase m_objFireBaseRef = new Firebase(AppConstant.FIREBASE_DATABSE_REFRENCE_URL);
            Firebase objRef = m_objFireBaseRef.child("users");
            Firebase taskRef = objRef.child(business_node_key);
            updateChild(taskRef,getMapObject());
        }

    }

    @OnClick(R.id.register3)
    public void register3(View v){

    }


    Subscription _subscription;
    static final int RC_SIGN_IN = 9001;
    CallbackManager callbackManager;
    Subscription sigInSubscription;
    ProgressDialog progressDialog;
    int country_code = 221;
    String business_node_key;
    String gender = "Male";
    private CountryPicker mCountryPicker;
    public static ArrayList<Places> placesArrayListst = new ArrayList<>();

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
        setContentView(R.layout.activity_sign_up);
        createDialog();
        getHomePlaceFromFirebase();
        init();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sigInSubscription!=null)
            sigInSubscription.unsubscribe();
        if (observeSignInSubscription !=null)
            observeSignInSubscription.unsubscribe();
    }

    private void init(){
        mCountryPicker = CountryPicker.newInstance("Select Country");
        register1.setEnabled(false);
        register2.setEnabled(false);
        setupObservables1();
        setupObservables2();
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
                    }

                    @Override
                    public void onNext(AuthResult authResult) {
                        if (authResult.getUser() != null) {
                            // User is signed in
                            Log.e(TAG, "onAuthStateChanged:signed_in:" + authResult.getUser().getUid());
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

        Observable<Boolean> phoneObservable = RxTextView.textChanges(mobile_tv)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        if (!s.toString().trim().equals("")){
//                        if (country_code!=null){
                            ValidationResult result = validatePhone(s.toString(),country_code);
                            mobile_tv.setError(result.getReason());
                            return result.isValid();
//                        }else{
//                            return false;
//                        }
                    }else{
                        return false;
                    }
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

        Observable<Boolean> confirmPasswordObservable = RxTextView.textChanges(confirm_pwd_tv)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validatePassword(password_tv.getText().toString(),s.toString());
                        confirm_pwd_tv.setError(result.getReason());
                        return result.isValid();
                    }
                });


        _subscription = Observable.combineLatest(emailObservable, phoneObservable,passwordObservable,confirmPasswordObservable, new Func4<Boolean, Boolean, Boolean,Boolean,Boolean>() {
            @Override
            public Boolean call(Boolean validEmail, Boolean validMobile, Boolean validPassword,Boolean confirmPassword) {
//               Log.i(TAG, "email: " + validEmail + ", username: " + validUsername + ", phone: " + validPhone);
                return validEmail && validMobile && validPassword && confirmPassword;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
              register1.setEnabled(aBoolean);

            }
        });
    }

    private void setupObservables2() {

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

        Observable<Boolean> userNameObservable = RxTextView.textChanges(nikname_tv)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validateUserName(s.toString());
                        nikname_tv.setError(result.getReason());
                        return result.isValid();
                    }
                });

        Observable<Boolean> firstNameObservable = RxTextView.textChanges(firstname_tv)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validateFirstName(s.toString());
                        firstname_tv.setError(result.getReason());
                        return result.isValid();
                    }
                });

        Observable<Boolean> lastNameObservable = RxTextView.textChanges(lastname_tv)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validateLastName(s.toString());
                        lastname_tv.setError(result.getReason());
                        return result.isValid();
                    }
                });

        Observable<Boolean> workObservable = RxTextView.textChanges(workplace_tv)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validateWorkPlace(s.toString());
                        workplace_tv.setError(result.getReason());
                        return result.isValid();
                    }
                });
        Observable<Boolean> homeObservable = RxTextView.textChanges(homeplace_tv)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validateWorkPlace(s.toString());
                        homeplace_tv.setError(result.getReason());
                        return result.isValid();
                    }
                });
        Observable<Boolean> birthdayObservable = RxTextView.textChanges(birthday_tv)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validateBirthday(s.toString());
                        birthday_tv.setError(result.getReason());
                        return result.isValid();
                    }
                });

        _subscription = Observable.combineLatest(userNameObservable, firstNameObservable,lastNameObservable,workObservable,homeObservable,birthdayObservable, new Func6<Boolean, Boolean, Boolean,Boolean,Boolean,Boolean,Boolean>() {
            @Override
            public Boolean call(Boolean validUserName, Boolean validFirstName, Boolean validLastName,Boolean validWorkPlace,Boolean validHomePlae,Boolean validBirthDay) {
//               Log.i(TAG, "email: " + validEmail + ", username: " + validUsername + ", phone: " + validPhone);
                return validUserName && validFirstName && validLastName && validWorkPlace && validHomePlae && validBirthDay;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                register2.setEnabled(aBoolean);
            }
        });
    }



    private void signUp(final Firebase ref){
        showProgress();
        RxFirebaseAuth.createUserWithEmailAndPassword(FirebaseAuth.getInstance(),email_tv.getText().toString(),password_tv.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<AuthResult, Observable<Firebase>>() {
                    @Override
                    public Observable<Firebase> call(AuthResult authResult) {
                        if (authResult.getUser() != null) {
//                            // User is signed in
//                            Log.e(TAG, "onAuthStateChanged:signed_in:" + authResult.getUser().getUid());
                            authResult.getUser().sendEmailVerification();
                        } else {
                            // User is signed out
                            Log.e(TAG, "onAuthStateChanged:signed_out");
                        }
                        return RxFirebaseDatabase.pushValue(ref,createRegisteredUser());
                    }
                })
                .subscribe(new Subscriber<Firebase>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showProgress();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Firebase firebase) {
                       showProgress();
//                        if (authResult.getUser() != null) {
//                            // User is signed in
////                            Log.e(TAG, "onAuthStateChanged:signed_in:" + authResult.getUser().getUid());
//                            Intent i = new Intent(SignUpActivity.this, LandingActivity.class);
//                            startActivity(i);
//                            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
//                        } else {
//                            // User is signed out
//                            Log.e(TAG, "onAuthStateChanged:signed_out");
//                        }
                       business_node_key = firebase.getKey();
                        Log.e("KEY VALUE", "" + business_node_key);

                    }
                });
    }

    private void updateChild(final Firebase ref, Map<String ,Object> object){
        showProgress();
        RxFirebaseDatabase.updateChildren(ref,object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Firebase>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showProgress();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Firebase firebase) {
                        showProgress();
//                        if (authResult.getUser() != null) {
//                            // User is signed in
////                            Log.e(TAG, "onAuthStateChanged:signed_in:" + authResult.getUser().getUid());
//                            Intent i = new Intent(SignUpActivity.this, LandingActivity.class);
//                            startActivity(i);
//                            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
//                        } else {
//                            // User is signed out
//                            Log.e(TAG, "onAuthStateChanged:signed_out");
//                        }
                        business_node_key = firebase.getKey();
                        Log.e("KEY VALUE", "" + business_node_key);

                    }
                });
    }

    private RegisteredUser createRegisteredUser(){
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setEmail(email_tv.getText().toString());
        registeredUser.setPhone(mobile_tv.getText().toString());

        return registeredUser;
    }

    private Map<String,Object> getMapObject(){
        Map<String,Object> taskMap = new HashMap<String,Object>();
        taskMap.put("nickname",nikname_tv.getText().toString());
        taskMap.put("first_name",firstname_tv.getText().toString());
        taskMap.put("last_name",lastname_tv.getText().toString());
        taskMap.put("work_place",workplace_tv.getText().toString());
        taskMap.put("home_place",homeplace_tv.getText().toString());
        taskMap.put("birthday",birthday_tv.getText().toString());
        taskMap.put("gender",gender);
        return taskMap;
    }

    private void getHomePlaceFromFirebase(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("localisation").orderByChild("name");
        RxFirebaseDatabase.observeValueEvent(wr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<com.google.firebase.database.DataSnapshot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        DataSnapshot placeSnapshot = dataSnapshot.child("senegal");
                        String [] name;
                        for (com.google.firebase.database.DataSnapshot child: placeSnapshot.getChildren()) {
//                            Log.e("User key", child.getKey());
//                            Log.e("User ref", child.getRef().toString());
//                            Log.e("User val", child.getValue().toString());
                            String df = child.getValue().toString();
                            Places places = child.getValue(Places.class);
                            placesArrayListst.add(places);
                        }
                        name = new String[placesArrayListst.size()];
                        for (int i = 0; i<placesArrayListst.size();i++){
                            name[i] = placesArrayListst.get(i).getName().trim();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignUpActivity.this,android.R.layout.simple_list_item_1,name);
                        workplace_tv.setAdapter(adapter);
                        homeplace_tv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                });
    }

    private void openDatePicker(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                birthday_tv.setText("" + date);
            }
        }, now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show(getFragmentManager(),"Datepickerdialog");
    }

    private ValidationResult<String> validateEmail(@NonNull String email) {
        return ValidationUtils.isValidEmailAddress(email);
    }

    private ValidationResult validatePhone(@NonNull String phone,@NonNull int countryCode) {
        if (phone.isEmpty()) {
            return ValidationResult.failure(null, phone);
        }
        boolean isValid = ValidationUtils.isValidMobileNumber(phone,countryCode);
        if (isValid) {
            return ValidationResult.success(phone);
        }

        return ValidationResult.failure("Phone Number is Invalid", phone);
    }


    private ValidationResult<String> validatePassword(@NonNull String password) {
        return ValidationUtils.isValidPassword(password);
    }

    private ValidationResult<String> validatePassword(@NonNull String old_password,@NonNull String new_password) {
        return ValidationUtils.isValidPassword(old_password,new_password);
    }

    private ValidationResult<String> validateUserName(@NonNull String name) {
        return ValidationUtils.isValidUsername(name);
    }

    private ValidationResult<String> validateFirstName(@NonNull String name) {
        return ValidationUtils.isValidFirstName(name);
    }
    private ValidationResult<String> validateLastName(@NonNull String name) {
        return ValidationUtils.isValidLastName(name);
    }
    private ValidationResult<String> validateWorkPlace(@NonNull String name) {
        return ValidationUtils.isValidWorkPlace(name);
    }
    private ValidationResult<String> validateBirthday(@NonNull String name) {
        return ValidationUtils.isValidBirthday(name);
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
            progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setMessage("your account is validate you are connected on Top Africa Annuiare in a few second");
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
