package com.africa.annauiare.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.africa.annauiare.R;
import com.africa.annauiare.Utils;
import com.africa.annauiare.adapter.CategoryListAdapter;
import com.africa.annauiare.common.BaseDrawerActivity;
import com.africa.annauiare.modal.category.Businesse;
import com.africa.annauiare.rx.firebase.RxFirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.africa.annauiare.AppConstant.FIREBASE_KEY;

public class CategoryActivity extends BaseDrawerActivity {

    @Nullable
    @Bind(R.id.mapview)
    MapView mapView;

    @Nullable
    @Bind(R.id.fab)
    FloatingActionButton fabCreate;

    @Nullable
    @Bind(R.id.id_recyleview)
    RecyclerView recyleView;

    @Nullable
    @Bind(R.id.login_progress)
    View mProgressView;

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    private boolean pendingIntroAnimation;
    private boolean mapsSupported = true;
    private GoogleMap mMap;

    private CategoryListAdapter categoryListAdapter;
    public  List<Businesse> ITEMS = new ArrayList<Businesse>();

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        resumeService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView!=null)
            mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView!=null)
            mapView.onLowMemory();
    }

    @Override
    public void onConnected(Location location) {
        super.onConnected(location);
        if(location != null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.5f));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (checkPlayServices())
            buildGoogleApiClient();
        setContentView(R.layout.activity_category);

        ITEMS = new ArrayList<Businesse>();
        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        } else {
//            feedAdapter.updateItems(false);
        }
        recyleView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        categoryListAdapter =  new CategoryListAdapter(ITEMS,CategoryActivity.this);
        categoryListAdapter.setOnItemClickListener(new CategoryListAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Businesse dfdf = ITEMS.get(position);
                Log.e("SDSD", "" + dfdf.getKey());
                Intent i = new Intent(CategoryActivity.this, DetailActivity.class);
                i.putExtra(FIREBASE_KEY,dfdf.getKey());
                startActivity(i);
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
        recyleView.setAdapter(categoryListAdapter);
        try {
            MapsInitializer.initialize(CategoryActivity.this);
            if (mapView != null) {
                mapView.onCreate(savedInstanceState);
            }
            initializeMap();
        } catch (Exception e) {
            mapsSupported = false;
        }
        setUpRecyleView();
    }

    private void initializeMap() {
        if (mMap == null && mapsSupported) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                try {
                    mMap = googleMap;
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
         });
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }
    private void startIntroAnimation() {
//        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
//
        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);

    }

    private void setUpRecyleView(){
        String type= "";
        if (getIntent() != null){
            type = getIntent().getStringExtra(LandingActivity.KEY);
        }
        showProgress(true);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("businesses").orderByChild("category").equalTo(type);
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
                        showProgress(false);
                            for (com.google.firebase.database.DataSnapshot child: dataSnapshot.getChildren()) {
//                            Log.e("User key", child.getKey());
//                            Log.e("User ref", child.getRef().toString());
//                            Log.e("User val", child.getValue().toString());
                                Businesse businesse = child.getValue(Businesse.class);
                                businesse.setKey(child.getKey());
                                categoryListAdapter.addItem(businesse);
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
                // and hide the relevant UI components.
                recyleView.setVisibility(show ? View.VISIBLE : View.GONE);
                recyleView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
