package com.africa.annauiare.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.africa.annauiare.AppConstant;
import com.africa.annauiare.AppController;
import com.africa.annauiare.PermissionUtils;
import com.africa.annauiare.R;
import com.africa.annauiare.adapter.ImageListAdapter;
import com.africa.annauiare.common.BaseDrawerActivity;
import com.africa.annauiare.modal.MyImage;
import com.africa.annauiare.modal.category.Businesse;
import com.africa.annauiare.modal.review.Faviourite;
import com.africa.annauiare.modal.review.Review;
import com.africa.annauiare.rx.AddressListFunc;
import com.africa.annauiare.rx.DisplayAddressOnDetailPage;
import com.africa.annauiare.rx.ErrorHandler;
import com.africa.annauiare.rx.FallbackReverseGeocodeObservable;
import com.africa.annauiare.rx.firebase.RxFirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DetailActivity extends BaseDrawerActivity {

    @Nullable
    @Bind(R.id.id_favourite_image)
    ImageView img_favourite;

    @Nullable
    @Bind(R.id.id_card_business_route)
    ImageView img_route;

    @Nullable
    @Bind(R.id.id_image_bus_logo_camera)
    ImageView img_business_logo;

    @Nullable
    @Bind(R.id.id_card_business_name)
    TextView img_business_name;

    @Nullable
    @Bind(R.id.id_card_business_category)
    TextView text_business_category;

    @Nullable
    @Bind(R.id.id_address)
    TextView business_address;

    @Nullable
    @Bind(R.id.id_card_business_rating)
    AppCompatRatingBar rating_bar;

    @Nullable
    @Bind(R.id.id_card_business_review)
    TextView business_review;

    @Nullable
    @Bind(R.id.id_card_business_text_telephone)
    TextView business_telephone;

    @Nullable
    @Bind(R.id.id_card_business_text_email)
    TextView business_email;

    @Nullable
    @Bind(R.id.id_card_business_description)
    TextView business_description;

    @Nullable
    @Bind(R.id.id_card_business_text_website)
    TextView business_website;

    @Nullable
    @Bind(R.id.id_detail_rel_4)
    RelativeLayout facebook_relative;

    @Nullable
    @Bind(R.id.id_detail_rel_5)
    RelativeLayout twitter_relative;

    @Nullable
    @Bind(R.id.id_detail_rel_6)
    RelativeLayout google_relative;

    @Nullable
    @Bind(R.id.id_detail_rel_8)
    LinearLayout linear_description;


    @Nullable
    @Bind(R.id.mapview)
    MapView mapView;

    @Nullable
    @Bind(R.id.login_form)
    RelativeLayout login_form;

    @Nullable
    @Bind(R.id.login_progress)
    View mProgressView;

    @Nullable
    @Bind(R.id.id_recyleview_category)
    RecyclerView recyclerView;

    private boolean mapsSupported = true;
    private GoogleMap mMap;
    private String key;
    Businesse businesse;
    ImageListAdapter imageListAdapter;
    public static List<MyImage> ITEMS ;
    public static List<Review> ITEM_REVIEW;
    double sourceLatitude,sourceLongitude,destinationLatitude,destinationLongitude;
    boolean isFavourite = false;
    Firebase remove_favourite;
    DatabaseReference remove_database_favourite;
    MarkerOptions markerOptions;

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
        observeState();
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
        if (observeSignInSubscription !=null)
            observeSignInSubscription.unsubscribe();
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
            markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)).draggable(true);
            sourceLatitude = location.getLatitude();
            sourceLongitude = location.getLongitude();
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.5f));
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPlayServices())
         buildGoogleApiClient();
        setContentView(R.layout.activity_detail);
        try {
            MapsInitializer.initialize(DetailActivity.this);
            if (mapView != null) {
                mapView.onCreate(savedInstanceState);
            }
            initializeMap();
        } catch (Exception e) {
            mapsSupported = false;
        }

        initInstance();
        initReclyleList();
        if (getIntent()!=null){
            key = getIntent().getStringExtra(AppConstant.FIREBASE_KEY);
            showProgress(true);
            getObjectByKey(key,savedInstanceState);
            getReviewByKey(key);
            getFaviouriteByKey(key);
        }

    }

    private void initInstance(){
        ITEMS = new ArrayList<MyImage>();
        imageListAdapter = new ImageListAdapter(ITEMS,DetailActivity.this);
        imageListAdapter.setOnItemClickListener(new ImageListAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.e("URL==", "" + ITEMS.get(position).getImage_url());
            }
        });
    }

    private void initReclyleList(){
        //add ItemDecoration
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(imageListAdapter);
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

    private void initStretView(final Bundle savedInstanceState){
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        // Only set the panorama to SYDNEY on startup (when no panoramas have been
                        // loaded which is when the savedInstanceState is null).
                        if (savedInstanceState == null) {
                            if (businesse.getOfficeLocation()!=null){
                                String[] geoArray = businesse.getOfficeLocation().trim().split(",");
                                destinationLatitude = Double.valueOf(geoArray[0]);
                                destinationLongitude = Double.valueOf(geoArray[1]);
                                panorama.setPosition(new LatLng(-33.87365, 151.20689));
                            }

                        }
                    }
                });
    }

    private void getObjectByKey(final String key,final Bundle savedInstanceState){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("businesses").orderByKey().equalTo(key);
        RxFirebaseDatabase.observeSingleValueEvent(wr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataSnapshot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DataSnapshot dataSnapshot) {
                        showProgress(false);
                        businesse = dataSnapshot.child(key).getValue(Businesse.class);
                        initStretView(savedInstanceState);
                        checkSocialURL();
                        for (int i = 0 ; i < businesse.getPictures().size() ; i++){
                            MyImage image = new MyImage();
                            image.setImage_url(businesse.getPictures().get(i).toString());
                            imageListAdapter.addItem(image);
                        }

                        setBusinesDetail();
                    }
                });
    }

    private void getReviewByKey(final String key){
        ITEM_REVIEW = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("reviews").orderByChild("business").equalTo(key);
        RxFirebaseDatabase.observeValueEvent(wr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataSnapshot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DataSnapshot dataSnapshot) {
                        try {
                            for (DataSnapshot child: dataSnapshot.getChildren()) {
//                            Log.e("User key", child.getKey());
//                            Log.e("User ref", child.getRef().toString());
//                            Log.e("User val", child.getValue().toString());
                                Review review = child.getValue(Review.class);
                                ITEM_REVIEW.add(review);
                            }
                            if (ITEM_REVIEW.size()>0){
                                float avg_review = 0;
                                business_review.setText("(" + ITEM_REVIEW.size() + "review)");
                                for (Review mark : ITEM_REVIEW) {
                                    avg_review += mark.getRate();
                                }
                                avg_review = avg_review/ITEM_REVIEW.size();
                                rating_bar.setRating(avg_review);
                            }else {
                                business_review.setText("(" + 0 + "review)");
                                rating_bar.setRating(0);
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }


    private void getFaviouriteByKey(final String key){
        ITEM_REVIEW = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("favourite").orderByChild("business").equalTo(key);
        RxFirebaseDatabase.observeSingleValueEvent(wr)
               .map(new Func1<DataSnapshot, Faviourite>() {
                   @Override
                   public Faviourite call(DataSnapshot dataSnapshot) {
                       Faviourite faviourite=null;
                       DataSnapshot my_Children = null;
                       for (DataSnapshot child: dataSnapshot.getChildren()) {
//                            Log.e("User key", child.getKey());
//                            Log.e("User ref", child.getRef().toString());
//                            Log.e("User val", child.getValue().toString());
                           faviourite = child.getValue(Faviourite.class);
                           if (faviourite.getUid().equals(firebaseUser.getUid())){
                               my_Children = child;
                               break;
                           }
                       }
                       remove_database_favourite = my_Children.getRef();
                       return faviourite;
                   }
               })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Faviourite>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Faviourite faviourite) {
                        if (faviourite!=null){
                            img_favourite.setImageResource(R.drawable.heart_fill);
                            isFavourite = true;
                        }else{
                            img_favourite.setImageResource(R.drawable.heart_blank);
                            isFavourite = false;
                        }
                    }
                });
    }

    private void setBusinesDetail(){
        try {
            if (!businesse.getLogo().equals("") && businesse.getLogo()!=null){
                Picasso.with(DetailActivity.this)
                        .load(businesse.getLogo())
                        .resize(100,100)
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.no_image_available)
                        .into(img_business_logo);
                img_business_name.setText(businesse.getName());
                text_business_category.setText(businesse.getCategory());
                if (businesse.getOfficeLocation()!=null && !businesse.getOfficeLocation().equals("")){
                    String[] geoArray = businesse.getOfficeLocation().trim().split(",");
                    destinationLatitude = Double.valueOf(geoArray[0]);
                    destinationLongitude = Double.valueOf(geoArray[1]);
                    getStringAdress(destinationLatitude,destinationLongitude);
                    displayLocationOnDragerEnd(destinationLatitude,destinationLongitude);
                }else{
                    business_address.setText("No Address found");
                }

                business_telephone.setText(businesse.getPhoneNumber());
                business_email.setText(businesse.getEmail());
//                business_website.setClickable(true);
//                business_website.setMovementMethod(LinkMovementMethod.getInstance());
//                String text = String.format("<a href=\"%s\">\"%s\"</a> ", businesse.getWebsite(),businesse.getWebsite());
//                business_website.setText(Html.fromHtml(text));
                business_website.setText(businesse.getWebsite());
            }
            if (!businesse.getDescription().equals("") && businesse.getDescription()!=null){
                linear_description.setVisibility(View.VISIBLE);
                business_description.setText(businesse.getDescription());
            }else{
                linear_description.setVisibility(View.GONE);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void displayLocationOnDragerEnd(double latitude,double longitude) {
        try {
            if (mMap!=null){
                mMap.clear();
                mMap.addMarker(markerOptions.position(new LatLng(latitude, longitude)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.5f));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    private void getStringAdress(double lat, double lng){
        FallbackReverseGeocodeObservable.createObservable(Locale.getDefault(),lat,lng,1)
                .map(new Func1<List<Address>, Address>() {
                    @Override
                    public Address call(List<Address> addresses) {
                        return addresses != null && !addresses.isEmpty() ? addresses.get(0) : null;
                    }
                })
                .map(new AddressListFunc(lat,lng))
                .subscribeOn(Schedulers.io())               // use I/O thread to query for addresses
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new DisplayAddressOnDetailPage(business_address),new ErrorHandler(DetailActivity.this));
    }
    private void checkSocialURL(){
        if (!businesse.getFacebookPage().toString().equals("") && businesse.getFacebookPage()!=null){
            facebook_relative.setVisibility(View.VISIBLE);
        }else{
            facebook_relative.setVisibility(View.GONE);
        }
        if (!businesse.getTwitterPage().toString().equals("") && businesse.getTwitterPage()!=null){
            twitter_relative.setVisibility(View.VISIBLE);
        }else{
            twitter_relative.setVisibility(View.GONE);
        } if (!businesse.getTwitterPage().toString().equals("") && businesse.getTwitterPage()!=null){
            google_relative.setVisibility(View.VISIBLE);
        }else{
            google_relative.setVisibility(View.GONE);
        }
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

                login_form.setVisibility(show ? View.GONE : View.VISIBLE);
                login_form.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        login_form.setVisibility(show ? View.GONE : View.VISIBLE);
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
                login_form.setVisibility(show ? View.VISIBLE : View.GONE);
                login_form.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @OnClick(R.id.id_card_business_route)
    public void navigateToBusinessLocation(View v){
        startNavigation();
    }

    @OnClick(R.id.id_favourite_image)
    public void favouriteSelection(View v){
        if (!isFavourite){
            isFavourite = true;
            img_favourite.setImageResource(R.drawable.heart_fill);
            img_favourite.setEnabled(false);
            setBusinessAsFaviourite();
        }else{
            isFavourite = false;
            img_favourite.setImageResource(R.drawable.heart_blank);
            img_favourite.setEnabled(false);
            removeBusinessAsFaviourite();
        }
    }

    @OnClick(R.id.id_favourite_share)
    public void shareBusiness(View v){
        shareApp();
    }


    @OnClick(R.id.id_card_business_text_telephone)
    public void callBusinessNumber(View v){
        AppController.getInstance().dialNumber(business_telephone.getText().toString().trim(),DetailActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE)
            return;
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.CALL_PHONE)) {
            // Enable the my location layer if the permission has been granted.
           AppController.getInstance().dialNumber(business_telephone.getText().toString().trim(),DetailActivity.this);
        } else {
            // Display the missing permission error dialog when the fragments resume.
            if (ActivityCompat.shouldShowRequestPermissionRationale(DetailActivity.this,
                    Manifest.permission.CALL_PHONE)) {
                AppController.getInstance().confirmationDialog(DetailActivity.this
                        ,getString(R.string.confirmation_title)
                        ,img_business_name.getText().toString()
                        ,business_telephone.getText().toString());
            }
        }
    }

    private void setBusinessAsFaviourite(){
        Firebase ref =  new Firebase(AppConstant.FIREBASE_DATABSE_REFRENCE_URL+"favourite");
        Faviourite faviourite = new Faviourite();
        faviourite.setBusiness(key);
        faviourite.setUid(firebaseUser.getUid());
        faviourite.setFavourite(true);
        RxFirebaseDatabase.pushValue(ref,faviourite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Firebase>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Firebase firebase) {
                        remove_favourite = firebase;
                        img_favourite.setImageResource(R.drawable.heart_fill);
                        img_favourite.setEnabled(true);
                    }
                });
    }

    private void removeBusinessAsFaviourite(){
        if (remove_favourite!=null){
            img_favourite.setImageResource(R.drawable.heart_blank);
            remove_favourite.removeValue();
            img_favourite.setEnabled(true);
        }
        if (remove_database_favourite!=null){
            img_favourite.setImageResource(R.drawable.heart_blank);
            remove_database_favourite.removeValue();
            img_favourite.setEnabled(true);
        }


    }
    private void startNavigation(){
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", sourceLatitude, sourceLongitude, "current location", destinationLatitude, destinationLongitude, businesse.getCity());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try
        {
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }
}
