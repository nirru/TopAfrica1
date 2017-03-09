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

import com.africa.annauiare.ObjectSetter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
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
import rx.functions.Func1;
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
            double destinationLatitude = 0,destinationLongitude = 0;
            if (ObjectSetter.getInstance().getDataSnapshots().size()>0){
                for (int i = 0; i < ObjectSetter.getInstance().getDataSnapshots().size();i++){
                    DataSnapshot dataSnapshot = ObjectSetter.getInstance().getDataSnapshots().get(i);
                    if (dataSnapshot.hasChild("officeLocation")){
                        String[] geoArray = dataSnapshot.child("officeLocation").getValue().toString().trim().split(",");
                        destinationLatitude = Double.valueOf(geoArray[0]);
                        destinationLongitude = Double.valueOf(geoArray[1]);
                        drawMarker(new LatLng(destinationLatitude,destinationLongitude));
                    }
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destinationLatitude, destinationLongitude), 15.5f));
            }else{
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.5f));
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (checkPlayServices())
        buildGoogleApiClient();
        setContentView(R.layout.activity_category);

        if (getIvLogo()!=null){
            getIvLogo().setText(ObjectSetter.getInstance().getSearch().toUpperCase());
        }
        try {
            MapsInitializer.initialize(CategoryActivity.this);
            if (mapView != null) {
                mapView.onCreate(savedInstanceState);
            }
            initializeMap();
        } catch (Exception e) {
            mapsSupported = false;
        }
        if (ObjectSetter.getInstance().getDataSnapshots().size()>0)
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
        return super.onCreateOptionsMenu(menu);
    }
    private void setUpRecyleView(){
        recyleView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        categoryListAdapter =  new CategoryListAdapter(ObjectSetter.getInstance().getDataSnapshots(),CategoryActivity.this);
        categoryListAdapter.setOnItemClickListener(new CategoryListAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                DataSnapshot dataSnapshot = ObjectSetter.getInstance().getDataSnapshots().get(position);
                ObjectSetter.getInstance().setDataSnapshot(dataSnapshot);
                Intent i = new Intent(CategoryActivity.this, DetailActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
        recyleView.setAdapter(categoryListAdapter);
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

    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        mMap.addMarker(markerOptions);
    }

}
