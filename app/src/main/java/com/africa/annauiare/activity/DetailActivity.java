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
import android.support.v4.view.ViewPager;
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

import com.africa.annauiare.ObjectSetter;
import com.africa.annauiare.adapter.AfricaPagerAdapter;
import com.africa.annauiare.adapter.CategoryListAdapter;
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
    @Bind(R.id.image_logo)
    ImageView img_logo;

    @Nullable
    @Bind(R.id.id_image_bus_logo_camera)
    ImageView img_business_logo;

    @Nullable
    @Bind(R.id.id_card_business_description)
    TextView text_busiiness_desc;

    @Nullable
    @Bind(R.id.text_loc)
    TextView text_business_address;

    @Nullable
    @Bind(R.id.text_fax)
    TextView text_fax;

    @Nullable
    @Bind(R.id.ratingBar)
    AppCompatRatingBar rating_bar;

    @Nullable
    @Bind(R.id.reviews)
    TextView business_review;

    @Nullable
    @Bind(R.id.pager)
    ViewPager pager;

    @Nullable
    @Bind(R.id.viewPagerCountDots)
    LinearLayout pager_indicator;


    @Nullable
    @Bind(R.id.mapview)
    MapView mapView;



    private boolean mapsSupported = true;
    private GoogleMap mMap;
    Businesse businesse;
    ImageListAdapter imageListAdapter;
    public static List<MyImage> ITEMS ;
    public static List<Review> ITEM_REVIEW;
    double sourceLatitude,sourceLongitude,destinationLatitude,destinationLongitude;
    boolean isFavourite = false;
    Firebase remove_favourite;
    DatabaseReference remove_database_favourite;
    MarkerOptions markerOptions;
    AfricaPagerAdapter mAdapter;
    private int dotsCount;
    private ImageView[] dots;
    private DataSnapshot dataSnapshot;

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
//          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.5f));
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPlayServices())
            buildGoogleApiClient();
        setContentView(R.layout.activity_detail);
        dataSnapshot = ObjectSetter.getInstance().getDataSnapshot();
        try {
            MapsInitializer.initialize(DetailActivity.this);
            if (mapView != null) {
                mapView.onCreate(savedInstanceState);
            }
            initializeMap();
        } catch (Exception e) {
            mapsSupported = false;
        }
        if (getIvLogo()!=null){
            if (dataSnapshot.hasChild("name")){
                getIvLogo().setText(dataSnapshot.child("name").getValue().toString());
            }
        }

        setUpPager();
        setLogo();
        setDescription();
        settAddress(dataSnapshot);
        setFax();
//      getReviewByKey(key);
//      getFaviouriteByKey(key);

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


//    private void getFaviouriteByKey(final String key){
//        ITEM_REVIEW = new ArrayList<>();
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        com.google.firebase.database.Query wr = mDatabase.child("favourite").orderByChild("business").equalTo(key);
//        RxFirebaseDatabase.observeSingleValueEvent(wr)
//               .map(new Func1<DataSnapshot, Faviourite>() {
//                   @Override
//                   public Faviourite call(DataSnapshot dataSnapshot) {
//                       Faviourite faviourite=null;
//                       DataSnapshot my_Children = null;
//                       for (DataSnapshot child: dataSnapshot.getChildren()) {
////                            Log.e("User key", child.getKey());
////                            Log.e("User ref", child.getRef().toString());
////                            Log.e("User val", child.getValue().toString());
//                           faviourite = child.getValue(Faviourite.class);
//                           if (faviourite.getUid().equals(firebaseUser.getUid())){
//                               my_Children = child;
//                               break;
//                           }
//                       }
//                       remove_database_favourite = my_Children.getRef();
//                       return faviourite;
//                   }
//               })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Faviourite>() {
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
//                    public void onNext(Faviourite faviourite) {
//                        if (faviourite!=null){
//                            img_favourite.setImageResource(R.drawable.heart_fill);
//                            isFavourite = true;
//                        }else{
//                            img_favourite.setImageResource(R.drawable.heart_blank);
//                            isFavourite = false;
//                        }
//                    }
//                });
//    }

   private void setLogo(){
       if (dataSnapshot.hasChild("logo")){
           if (!dataSnapshot.child("logo").getValue().toString().equals("")){
               Picasso.with(DetailActivity.this)
                       .load(dataSnapshot.child("logo").getValue().toString())
                       .fit()
                       .centerCrop()
                       .error(R.drawable.no_image_available)
                       .into(img_logo);
           }
       }
   }

    private void settAddress( DataSnapshot child){
        String finalAddress = "",road = "",sigle="",city = "",state="",district="",country="",municipality="";
        if (child.hasChild("road")){
            road = child.child("road").getValue().toString();
        }if(child.hasChild("Municipality")){
            municipality = child.child("Municipality").getValue().toString();
        }if (child.hasChild("sigle")){
            sigle = child.child("sigle").getValue().toString();
        }if (child.hasChild("city")){
            city = child.child("city").getValue().toString();
        }if (child.hasChild("state")){
            state = child.child("state").getValue().toString();
        }if (child.hasChild("district")){
            district = child.child("district").getValue().toString();
        }if (child.hasChild("country")){
            country = child.child("country").getValue().toString();
        }if(city.toString().equals(state.toString().trim()) || state.toString().equals(city.toString().trim())){
            finalAddress = road + " " + municipality + " " + sigle + " " + district + " " + state + " " + country + " " + finalAddress;
        }
        else{
            finalAddress = road + " " + municipality + " " + sigle + " " + district + " " + city + " " + state + " " + country + " " + finalAddress;
        }


        text_business_address.setText(finalAddress);
    }

    private void setFax(){
        String fax1 = "",finalFax = "";
        if (dataSnapshot.hasChild("fax")){
            fax1 = dataSnapshot.child("fax").getValue().toString();
        }if(dataSnapshot.hasChild("fax2")){
            fax1= fax1 + "\n" + dataSnapshot.child("fax2").getValue().toString();
        }

        if (!fax1.equals(""))
        text_fax.setText(fax1);
        else
            text_fax.setText("No Fax found");
    }

    private void setDescription(){
        if (dataSnapshot.hasChild("description")){
            text_busiiness_desc.setText(dataSnapshot.child("description").getValue().toString());
        }else{
            text_busiiness_desc.setText("No Description Found");
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



    private void checkSocialURL(){
//        if (!businesse.getFacebookPage().toString().equals("") && businesse.getFacebookPage()!=null){
//            facebook_relative.setVisibility(View.VISIBLE);
//        }else{
//            facebook_relative.setVisibility(View.GONE);
//        }
//        if (!businesse.getTwitterPage().toString().equals("") && businesse.getTwitterPage()!=null){
//            twitter_relative.setVisibility(View.VISIBLE);
//        }else{
//            twitter_relative.setVisibility(View.GONE);
//        } if (!businesse.getTwitterPage().toString().equals("") && businesse.getTwitterPage()!=null){
//            google_relative.setVisibility(View.VISIBLE);
//        }else{
//            google_relative.setVisibility(View.GONE);
//        }
    }



    @OnClick(R.id.id_card_business_route)
    public void navigateToBusinessLocation(View v){
        startNavigation();
    }

//    @OnClick(R.id.id_favourite_image)
//    public void favouriteSelection(View v){
//        if (!isFavourite){
//            isFavourite = true;
//            img_favourite.setImageResource(R.drawable.heart_fill);
//            img_favourite.setEnabled(false);
//            setBusinessAsFaviourite();
//        }else{
//            isFavourite = false;
//            img_favourite.setImageResource(R.drawable.heart_blank);
//            img_favourite.setEnabled(false);
//            removeBusinessAsFaviourite();
//        }
//    }

    @OnClick(R.id.id_favourite_share)
    public void shareBusiness(View v){
        shareApp();
    }


    @OnClick(R.id.id_card_business_text_telephone)
    public void callBusinessNumber(View v){
        String phone;
        if (!dataSnapshot.hasChild("phoneNumber") && !dataSnapshot.hasChild("phoneNumber2")){
            Toast.makeText(DetailActivity.this,"Sorry no number found",Toast.LENGTH_SHORT).show();
            return;
        }
        if (dataSnapshot.hasChild("phoneNumber")){
            phone = dataSnapshot.child("phoneNumber").getValue().toString();
            AppController.getInstance().dialNumber(phone,DetailActivity.this);
            return;
        }
        if (dataSnapshot.hasChild("phoneNumber2")){
            phone = dataSnapshot.child("phoneNumber2").getValue().toString();
            AppController.getInstance().dialNumber(phone,DetailActivity.this);
            return;
        }
    }

    @OnClick(R.id.btn_next)
    public void nextButtonClk(View v){
        pager.setCurrentItem((pager.getCurrentItem() < dotsCount)
                ? pager.getCurrentItem() + 1 : 0);
    }

    @OnClick(R.id.btn_finish)
    public void finishBtn(View v){
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE)
            return;
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.CALL_PHONE)) {
            // Enable the my location layer if the permission has been granted.
            String phone;
            if (dataSnapshot.hasChild("phoneNumber")){
                phone = dataSnapshot.child("phoneNumber").getValue().toString();
                AppController.getInstance().dialNumber(phone,DetailActivity.this);
                return;
            }
            if (dataSnapshot.hasChild("phoneNumber2")){
                phone = dataSnapshot.child("phoneNumber2").getValue().toString();
                AppController.getInstance().dialNumber(phone,DetailActivity.this);
                return;
            }
        } else {
            // Display the missing permission error dialog when the fragments resume.
            if (ActivityCompat.shouldShowRequestPermissionRationale(DetailActivity.this,
                    Manifest.permission.CALL_PHONE)) {
                if (dataSnapshot.hasChild("name") && dataSnapshot.hasChild("phoneNumber")){
                    AppController.getInstance().confirmationDialog(DetailActivity.this
                            ,getString(R.string.confirmation_title)
                            ,dataSnapshot.child("name").getValue().toString()
                            ,dataSnapshot.child("phoneNumber").getValue().toString());

                }else{
                    Toast.makeText(DetailActivity.this,"Sorry no name and number is found",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    private void setBusinessAsFaviourite(){
//        Firebase ref =  new Firebase(AppConstant.FIREBASE_DATABSE_REFRENCE_URL+"favourite");
//        Faviourite faviourite = new Faviourite();
//        faviourite.setBusiness(key);
//        faviourite.setUid(firebaseUser.getUid());
//        faviourite.setFavourite(true);
//        RxFirebaseDatabase.pushValue(ref,faviourite)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Firebase>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(Firebase firebase) {
//                        remove_favourite = firebase;
//                        img_favourite.setImageResource(R.drawable.heart_fill);
//                        img_favourite.setEnabled(true);
//                    }
//                });
//    }

    //    private void removeBusinessAsFaviourite(){
//        if (remove_favourite!=null){
//            img_favourite.setImageResource(R.drawable.heart_blank);
//            remove_favourite.removeValue();
//            img_favourite.setEnabled(true);
//        }
//        if (remove_database_favourite!=null){
//            img_favourite.setImageResource(R.drawable.heart_blank);
//            remove_database_favourite.removeValue();
//            img_favourite.setEnabled(true);
//        }
//
//
//    }
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

    private void setUpPager(){
        DataSnapshot dataSnapshot = ObjectSetter.getInstance().getDataSnapshot();
        if (!dataSnapshot.hasChild("pictures"))
            return;
        ArrayList<String> pictures = (ArrayList<String>) dataSnapshot.child("pictures").getValue();
        if (!(pictures.size() >0))
            return;
        mAdapter = new AfricaPagerAdapter(DetailActivity.this,  pictures.toArray(new String[0]));
        pager.setAdapter(mAdapter);
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                }

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUiPageViewController();
    }

    private void setUiPageViewController() {
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }
}
