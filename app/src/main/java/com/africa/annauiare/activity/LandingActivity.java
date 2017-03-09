package com.africa.annauiare.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.africa.annauiare.ObjectSetter;
import com.africa.annauiare.R;
import com.africa.annauiare.adapter.CategorySearchAdapter;
import com.africa.annauiare.adapter.FavouriteListAdapter;
import com.africa.annauiare.adapter.PlaceSearchAutoAdapter;
import com.africa.annauiare.adapter.SearchAdapter;
import com.africa.annauiare.adapter.SearchPlaceAdapter;
import com.africa.annauiare.common.BaseDrawerActivity;
import com.africa.annauiare.fragment.DiscountFragment;
import com.africa.annauiare.fragment.PagesFragment;
import com.africa.annauiare.modal.RxWhatModal;
import com.africa.annauiare.modal.RxWhereModal;
import com.africa.annauiare.modal.category.Businesse;
import com.africa.annauiare.rx.firebase.RxFirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

public class LandingActivity extends BaseDrawerActivity implements PagesFragment.OnFragmentInteractionListener,
        DiscountFragment.OnFragmentInteractionListener{

    @Nullable
    @Bind(R.id.edit_what)
    AutoCompleteTextView searchWhat;

    @Nullable
    @Bind(R.id.edit_where)
    AutoCompleteTextView searchWhere;

    @Nullable
    @Bind(R.id.progress_bar_1)
    ProgressBar progressBar_1;

    @Nullable
    @Bind(R.id.progress_bar_2)
    ProgressBar progressBar_2;

    @Nullable
    @Bind(R.id.cancel_1)
    ImageView cancel_1;

    @Nullable
    @Bind(R.id.cancel_2)
    ImageView cancel_2;
//
//    @Nullable
//    @Bind(R.id.id_search_location)
//    AppCompatAutoCompleteTextView autoCompleteTextView;
//
//    @Nullable
//    @Bind(R.id.id_recyleview)
//    RecyclerView mRecyleView;
//
//    @Nullable
//    @Bind(R.id.id_weather_image)
//    ImageView weather_image;
//
//    @Nullable
//    @Bind(R.id.id_weather_temp)
//    TextView weather_temp;
//
//    @Nullable
//    @Bind(R.id.id_weather_location)
//    TextView weather_location;
//
//    @Nullable
//    @Bind(R.id.id_recyleview_category)
//    RecyclerView recyclerView;
//
//    @Nullable
//    @Bind(R.id.id_scroll_view)
//    ScrollView scrollViewContainer;
//
//    @Nullable
//    @Bind(R.id.id_recyle_relative)
//    RelativeLayout relativeRecyleContainer;

    SearchAdapter searchAdapter;
    SearchPlaceAdapter searchPlaceAdapter;
    ArrayList <String> list;
    ArrayList <String> listPlaces;

    ProgressDialog pDialog;

    public static final String KEY = "key";
    public static final String KEY_ACTIVITY = "key_activity";

//    private FirebaseUser firebaseUser;

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
//        getFaviourite();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPlayServices())
            buildGoogleApiClient();
        setContentView(R.layout.activity_home);
        createProgress();
        initInstance();
//        initReclyleList();
        setupSearchView();
//        placeSearch();
    }


    private void initInstance(){
//        list = new ArrayList<>();
//        listPlaces = new ArrayList<>();
//        favouriteListAdapter = new FavouriteListAdapter(ITEMS_FAVIOURITE,LandingActivity.this);
//        favouriteListAdapter.setOnItemClickListener(new FavouriteListAdapter.MyClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                Businesse dfdf = ITEMS_FAVIOURITE.get(position);
//                Log.e("SDSD", "" + dfdf.getKey());
//                Intent i = new Intent(LandingActivity.this, DetailActivity.class);
//                i.putExtra(FIREBASE_KEY,dfdf.getKey());
//                startActivity(i);
//                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
//            }
//        });
//
//
//        categorySearchAdapter = new CategorySearchAdapter(ITEMS,LandingActivity.this);
//        categorySearchAdapter.setOnItemClickListener(new CategorySearchAdapter.MyClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                Businesse dfdf = ITEMS.get(position);
//                Log.e("SDSD", "" + dfdf.getKey());
//                Intent i = new Intent(LandingActivity.this, DetailActivity.class);
//                i.putExtra(FIREBASE_KEY,dfdf.getKey());
//                startActivity(i);
//                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
//            }
//        });
    }

//    private void initReclyleList(){
//        //add ItemDecoration
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LandingActivity.this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        mRecyleView.setLayoutManager(new LinearLayoutManager(LandingActivity.this,LinearLayoutManager.HORIZONTAL,false));
//        mRecyleView.setAdapter(favouriteListAdapter);
//    }


    private void setupSearchView()
    {

        list = new ArrayList<>();
        listPlaces = new ArrayList<>();
        searchAdapter = new SearchAdapter(LandingActivity.this,list);
        searchPlaceAdapter = new SearchPlaceAdapter(LandingActivity.this,listPlaces);
        searchWhat.setAdapter(searchAdapter);
        searchWhere.setAdapter(searchPlaceAdapter);
        // search hint
        searchWhat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>=3){
                    doFinalSearchOnBusiness(charSequence);
                }else{
                    progressBar_1.setVisibility(View.GONE);
                    cancel_1.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchWhere.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>=3){
                    doFinalSearchOnPlaces(charSequence);
                }else{
                    progressBar_2.setVisibility(View.GONE);
                    cancel_2.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private Observable<DataSnapshot> queryFirebaseBusiness(final String charSequence,String orderByChile){
        progressBar_1.setVisibility(View.VISIBLE);
        cancel_1.setVisibility(View.GONE);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("annuaire").child("businesses").orderByChild(orderByChile)
                .startAt(charSequence.toString())
                .endAt(charSequence.toString()+"\uf8ff")
                .limitToFirst(30);
        return RxFirebaseDatabase.observeValueEvent(wr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    private Observable<DataSnapshot> queryFirebaseBusiness(final String charSequence){
        progressBar_1.setVisibility(View.VISIBLE);
        cancel_1.setVisibility(View.GONE);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("annuaire").child("businesses")
                .limitToFirst(30);
        return RxFirebaseDatabase.observeValueEvent(wr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    private Observable<DataSnapshot> queryFirebasePlaces(String query,String name){
        progressBar_2.setVisibility(View.VISIBLE);
        cancel_2.setVisibility(View.GONE);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("annuaire").child("businesses").orderByChild(name)
                .startAt(query)
                .endAt(query.toString()+"\uf8ff")
                .limitToFirst(20);
        return RxFirebaseDatabase.observeValueEvent(wr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void doFinalSearchOnBusiness(final CharSequence charSequence){
        Log.e("YYYYY==", "" + charSequence.toString().toUpperCase());
        final String category = charSequence.toString().substring(0,1).toUpperCase() + charSequence.toString().substring(1).toLowerCase();
        Observable<DataSnapshot> businessNameObservable =  queryFirebaseBusiness(charSequence.toString().toUpperCase(),"name");
        Observable<DataSnapshot> categoryNameObservable =  queryFirebaseBusiness("category");
        Observable<RxWhatModal> finalObservable = Observable.zip(businessNameObservable, categoryNameObservable, new Func2<DataSnapshot, DataSnapshot, RxWhatModal>() {
            @Override
            public RxWhatModal call(DataSnapshot dataSnapshot, DataSnapshot dataSnapshot2) {
                RxWhatModal rxWhatModal = new RxWhatModal();
                rxWhatModal.setBusinessName(dataSnapshot);
                rxWhatModal.setBusinessCategory(dataSnapshot2);
                return rxWhatModal;
            }
        });

        finalObservable.subscribe(new Subscriber<RxWhatModal>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxWhatModal rxWhatModal) {
                searchAdapter.clearItem();
                progressBar_1.setVisibility(View.GONE);
                for (com.google.firebase.database.DataSnapshot child: rxWhatModal.getBusinessName().getChildren()) {
                    try {
                        String ddf =  child.child("name").getValue().toString();
                        searchAdapter.addItem(ddf);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                for (com.google.firebase.database.DataSnapshot child: rxWhatModal.getBusinessCategory().getChildren()) {

                    try {
                        if (child.hasChild("category")) {
                            ArrayList<String> ddf = (ArrayList<String>) child.child("category").getValue();
                            for (int i = 0; i < ddf.size(); i++) {
                                if (ddf.get(i).toString().contains(category))
                                    searchAdapter.addItem(ddf.get(i).toString());
                                if (ddf.get(i).toString().contains(charSequence.toString()))
                                    searchAdapter.addItem(ddf.get(i).toString());
                            }
                            searchAdapter.removeDuplicate();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                cancel_1.setVisibility(View.VISIBLE);
            }
        });
    }


    private void doFinalSearchOnPlaces(CharSequence charSequence){
        final String category = charSequence.toString().substring(0,1).toUpperCase() + charSequence.toString().substring(1).toLowerCase();
        Log.e("SSSS==",""+ category);
        Observable<DataSnapshot> cityObservable =  queryFirebasePlaces(category,"city");
        Observable<DataSnapshot> muncipalityObservable =  queryFirebasePlaces(category,"Municipality");
        Observable<DataSnapshot> neighbourHoodObservable =  queryFirebasePlaces(category,"neighborhood");

        Observable<RxWhereModal> finalObservable = Observable.zip(cityObservable, muncipalityObservable, neighbourHoodObservable, new Func3<DataSnapshot, DataSnapshot, DataSnapshot, RxWhereModal>() {
            @Override
            public RxWhereModal call(DataSnapshot dataSnapshot, DataSnapshot dataSnapshot2, DataSnapshot dataSnapshot3) {
                RxWhereModal rxWhereModal = new RxWhereModal();
                rxWhereModal.setCitySnapshot(dataSnapshot);
                rxWhereModal.setMunciplaitySnapshot(dataSnapshot2);
                rxWhereModal.setNeighbourHoodSnapshot(dataSnapshot3);
                return rxWhereModal;
            }
        });

        finalObservable.subscribe(new Subscriber<RxWhereModal>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxWhereModal rxWhereModal) {
                searchPlaceAdapter.clearItem();
                progressBar_2.setVisibility(View.GONE);
                ArrayList<String> strings = new ArrayList<String>();
                for (com.google.firebase.database.DataSnapshot child: rxWhereModal.getCitySnapshot().getChildren()) {
                    String places = child.child("city").getValue().toString();
                    searchPlaceAdapter.addItem(places);
                }
                for (com.google.firebase.database.DataSnapshot child: rxWhereModal.getMunciplaitySnapshot().getChildren()) {
                    String places = child.child("Municipality").getValue().toString();
                    searchPlaceAdapter.addItem(places);
                }
                for (com.google.firebase.database.DataSnapshot child: rxWhereModal.getNeighbourHoodSnapshot().getChildren()) {
                    String places = child.child("neighborhood").getValue().toString();
                    searchPlaceAdapter.addItem(places);
                }

                searchPlaceAdapter.removeDuplicate();
                cancel_2.setVisibility(View.VISIBLE);
            }
        });
    }



    private void searchDirectory(){
        showProgress();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("annuaire").child("businesses").orderByChild("name").equalTo(searchWhat.getText().toString().toUpperCase());

        final ArrayList<com.google.firebase.database.DataSnapshot> dataSnapshots = new ArrayList<>();
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
                        dismissProgress();
                        for (com.google.firebase.database.DataSnapshot child: dataSnapshot.getChildren()) {
//                            Log.e("User key", child.getKey());
//                            Log.e("User ref", child.getRef().toString());
//                            Log.e("User val", child.getValue().toString());
                            if (child.child("Municipality").getValue().toString().equals(searchWhere.getText().toString())){
                                dataSnapshots.add(child);
                            }if (child.child("city").getValue().toString().equals(searchWhere.getText().toString())){
                                dataSnapshots.add(child);
                            }if (child.child("neighborhood").getValue().toString().equals(searchWhere.getText().toString())){
                                dataSnapshots.add(child);
                            }
                        }

                        if (dataSnapshots.size()>0){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ObjectSetter.getInstance().setDataSnapshots(dataSnapshots);
                                    Log.e("User val==", "" + ObjectSetter.getInstance().getDataSnapshots().size());
                                    startActivity(new Intent(LandingActivity.this,CategoryActivity.class));
                                }
                            }, 300);
                        }
                        else {
                            Toast.makeText(LandingActivity.this,"Sorry No match found",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


    @OnClick(R.id.menuicon)
    public void onMenuCLick(View v){
        openDrawer();

    }
    public void yesNoAlert(final Context mContext,String title,String message ,final int key){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.MyDialogTheme);

        // Setting Dialog Title
        builder.setTitle(title);
        builder.setMessage(message);

        // On pressing Settings button
        builder.setPositiveButton(mContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent i = new Intent(LandingActivity.this, LoginActivity.class);
                i.putExtra(KEY_ACTIVITY,key);
                startActivity(i);
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
        builder.setNegativeButton(mContext.getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        // display dialog
        dialog.show();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public  void createProgress() {
        pDialog = new ProgressDialog(LandingActivity.this);
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void showProgress(){
        if (pDialog.isShowing())
            pDialog.dismiss();
        pDialog.show();
    }

    private void dismissProgress(){
        pDialog.dismiss();
    }

//    private void getFaviourite(){
//        if (favouriteListAdapter!=null)
//            favouriteListAdapter.clearItem();
//        favouriteListAdapter.notifyDataSetChanged();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        com.google.firebase.database.Query wr = mDatabase.child("favourite").orderByChild("uid").equalTo(firebaseUser.getUid());
//        RxFirebaseDatabase.observeSingleValueEvent(wr)
//                .flatMapIterable(new Func1<com.google.firebase.database.DataSnapshot, Iterable<com.google.firebase.database.DataSnapshot>>() {
//                    @Override
//                    public Iterable<com.google.firebase.database.DataSnapshot> call(com.google.firebase.database.DataSnapshot dataSnapshot) {
//
//                        return dataSnapshot.getChildren();
//                    }
//                })
//                .flatMap(new Func1<com.google.firebase.database.DataSnapshot, Observable<com.google.firebase.database.DataSnapshot>>() {
//                    @Override
//                    public Observable<com.google.firebase.database.DataSnapshot> call(com.google.firebase.database.DataSnapshot dataSnapshot) {
//                        Faviourite faviourite = dataSnapshot.getValue(Faviourite.class);
//                        com.google.firebase.database.Query wr = mDatabase.child("businesses").orderByKey().equalTo(faviourite.getBusiness());
//                        return RxFirebaseDatabase.observeSingleValueEvent(wr);
//                    }
//                })
//                .doOnEach(new Observer<com.google.firebase.database.DataSnapshot>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(com.google.firebase.database.DataSnapshot dataSnapshot) {
//                        for (com.google.firebase.database.DataSnapshot child: dataSnapshot.getChildren()) {
////                            Log.e("User key", child.getKey());
////                            Log.e("User ref", child.getRef().toString());
////                            Log.e("User val", child.getValue().toString());
//                            Businesse buss = child.getValue(Businesse.class);
////                            favouriteListAdapter.addItem(buss);
//                        }
//
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<com.google.firebase.database.DataSnapshot>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                      e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(com.google.firebase.database.DataSnapshot dataSnapshot) {
//                        for (com.google.firebase.database.DataSnapshot child: dataSnapshot.getChildren()) {
////                            Log.e("User key", child.getKey());
////                            Log.e("User ref", child.getRef().toString());
////                            Log.e("User val", child.getValue().toString());
//                            Businesse buss = child.getValue(Businesse.class);
//                            buss.setKey(child.getKey());
//                            favouriteListAdapter.addItem(buss);
//                        }
//
//                    }
//                });
//    }


    @Nullable
    @OnClick(R.id.search_image)
    public void searchMe(View v){
        if (!searchWhat.getText().toString().equals("") && !searchWhere.getText().toString().equals("")){
            ObjectSetter.getInstance().setSearch(searchWhat.getText().toString());
            searchDirectory();
        }

    }

    @Nullable
    @OnClick(R.id.cancel_1)
    public void clearWhat(View v){
        progressBar_1.setVisibility(View.GONE);
        searchWhat.setText("");
    }

    @Nullable
    @OnClick(R.id.cancel_2)
    public void clearWhere(View v){
        progressBar_2.setVisibility(View.GONE);
        searchWhere.setText("");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
