package com.africa.annauiare.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.africa.annauiare.R;
import com.africa.annauiare.adapter.CategorySearchAdapter;
import com.africa.annauiare.common.BaseDrawerActivity;
import com.africa.annauiare.modal.category.Businesse;
import com.africa.annauiare.rx.firebase.RxFirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ManageBusinessActivity extends BaseDrawerActivity {

    @Nullable
    @Bind(R.id.id_recyleview)
    RecyclerView recyleView;

    @Nullable
    @Bind(R.id.login_progress)
    View mProgressView;

    CategorySearchAdapter categorySearchAdapter;
    public  static List<Businesse> ITEMS ;

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
        observeState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeService();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (observeSignInSubscription !=null)
            observeSignInSubscription.unsubscribe();
    }
    @Override
    public void fireBaseAuthication(FirebaseUser firebaseUser) {
        super.fireBaseAuthication(firebaseUser);
        getBusinessByUser();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_business);
        initInstance();
        initReclyleList();

    }

    private void initInstance(){
        ITEMS = new ArrayList<>();
        categorySearchAdapter = new CategorySearchAdapter(ITEMS,ManageBusinessActivity.this);
        categorySearchAdapter.setOnItemClickListener(new CategorySearchAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
//                Businesse dfdf = ITEMS.get(position);
//                Log.e("SDSD", "" + dfdf.getKey());
//                Intent i = new Intent(ManageBusinessActivity.this, DetailActivity.class);
//                i.putExtra(FIREBASE_KEY,dfdf.getKey());
//                startActivity(i);
//                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
    }

    private void initReclyleList(){
        //add ItemDecoration
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ManageBusinessActivity.this);
        recyleView.setLayoutManager(linearLayoutManager);
        recyleView.setAdapter(categorySearchAdapter);
    }

    private void getBusinessByUser(){
        showProgress(true);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("businesses").orderByChild("uid").equalTo(firebaseUser.getUid());
        RxFirebaseDatabase.observeValueEvent(wr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataSnapshot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(DataSnapshot dataSnapshot) {
                        showProgress(false);
                            for (DataSnapshot child: dataSnapshot.getChildren()) {
//                            Log.e("User key", child.getKey());
//                            Log.e("User ref", child.getRef().toString());
//                            Log.e("User val", child.getValue().toString());
                                Businesse businesse = child.getValue(Businesse.class);
//                                businesse.setKey(child.getKey());
                                categorySearchAdapter.addItem(businesse);
                            }
                    }
                });
    }



    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public  void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                recyleView.setVisibility(show ? View.GONE : View.VISIBLE);
                recyleView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        recyleView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                mProgressView.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {
                // The ViewPropertyAnimator APIs are not available, so simply show
                // and hide the relevant UI  components.
                recyleView.setVisibility(show ? View.VISIBLE : View.GONE);
                recyleView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
