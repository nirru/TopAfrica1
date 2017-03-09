package com.africa.annauiare.activity.createbusiness;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding.widget.RxRadioGroup;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.africa.annauiare.AppConstant;
import com.africa.annauiare.AppController;
import com.africa.annauiare.PermissionUtils;
import com.africa.annauiare.R;
import com.africa.annauiare.Utils;
import com.africa.annauiare.ValidationResult;
import com.africa.annauiare.ValidationUtils;
import com.africa.annauiare.adapter.CategoryAdapter;
import com.africa.annauiare.common.BaseDrawerActivity;
import com.africa.annauiare.modal.MyFirebase;
import com.africa.annauiare.modal.category.Annotation;
import com.africa.annauiare.modal.category.Businesse;
import com.africa.annauiare.modal.category.Category;
import com.africa.annauiare.modal.category.Day;
import com.africa.annauiare.modal.category.Mapdata;
import com.africa.annauiare.modal.category.Openhours;
import com.africa.annauiare.rx.AddressToStringListFunc;
import com.africa.annauiare.rx.DisplayAddressOnViewAction;
import com.africa.annauiare.rx.ErrorHandler;
import com.africa.annauiare.rx.FallbackReverseGeocodeObservable;
import com.africa.annauiare.rx.firebase.RxFirebaseDatabase;
import com.africa.annauiare.rx.firebase.RxFirebaseStorage;
import com.africa.annauiare.uiView.SpineerView;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import rx.functions.Func5;
import rx.schedulers.Schedulers;

public class EditBusiness extends BaseDrawerActivity implements GoogleMap.OnMarkerDragListener,GoogleMap.OnCameraIdleListener{

    @Nullable
    @Bind(R.id.id_search_view)
    SearchView searchView;

    @Nullable
    @Bind(R.id.id_radio_group)
    RadioGroup radioGroup;

    @Nullable
    @Bind(R.id.id_card_business_name)
    EditText text_business_name;

    @Nullable
    @Bind(R.id.id_card_business_short_name)
    EditText text_business_short_name;

    @Nullable
    @Bind(R.id.id_card_business_type)
    EditText text_business_type;

    @Nullable
    @Bind(R.id.id_card_business_description)
    EditText text_business_description;

    @Nullable
    @Bind(R.id.id_number_of_employee)
    Spinner spinner;

    @Nullable
    @Bind(R.id.id_country_picker)
    TextView text_country_picker;

    @Nullable
    @Bind(R.id.id_card_business_email)
    EditText text_business_email;

//    @Nullable
//    @Bind(R.id.id_text_input_layout_sec_email)
//    TextInputLayout text_input_layout;

    @Nullable
    @Bind(R.id.id_card_business_sec_email)
    EditText text_business_sec_email;

    @Nullable
    @Bind(R.id.id_card_business_telephone)
    EditText text_business_phone;

    @Nullable
    @Bind(R.id.id_card_business_fax)
    EditText text_business_fax;

    @Nullable
    @Bind(R.id.id_card_business_fax_2)
    EditText text_business_fax_2;

    @Nullable
    @Bind(R.id.id_country_code)
    TextView text_country_code;

    @Nullable
    @Bind(R.id.id_fax_code)
    TextView text_fax_code;

    @Nullable
    @Bind(R.id.id_fax_code_2)
    TextView text_fax_code_2;

    @Nullable
    @Bind(R.id.id_next_btn_step_1)
    AppCompatButton next_btn_step_1;

    @Nullable
    @Bind(R.id.id_include_1)
    View steps_one_view;

    @Nullable
    @Bind(R.id.id_include_2)
    View steps_two_view;

    @Nullable
    @Bind(R.id.id_include_3)
    View steps_three_view;

    @Nullable
    @Bind(R.id.id_include_4)
    View steps_four_view;

    @Nullable
    @Bind(R.id.id_recyleview)
    RecyclerView recyclerView;

    @Nullable
    @Bind(R.id.id_country)
    EditText country;

    @Nullable
    @Bind(R.id.id_state)
    EditText text_state;

    @Nullable
    @Bind(R.id.id_city)
    EditText city;

    @Nullable
    @Bind(R.id.id_district)
    EditText text_district;

    @Nullable
    @Bind(R.id.id_neighbour)
    EditText text_neighbour;

    @Nullable
    @Bind(R.id.id_address)
    EditText address;

    @Nullable
    @Bind(R.id.id_lat)
    EditText cordinate;

    @Nullable
    @Bind(R.id.id_facebook_share)
    EditText text_facebook_share;

    @Nullable
    @Bind(R.id.id_google_share)
    EditText text_google_share;

    @Nullable
    @Bind(R.id.id_twitter_share)
    EditText text_twitter_share;

    @Nullable
    @Bind(R.id.id_mor_mon_opn_time)
    TextView textView_mor_mon_opn_time;

    @Nullable
    @Bind(R.id.id_next_btn_steps_three)
    AppCompatButton next_steps_btn_three;

    @OnClick(R.id.id_country_picker)
    public void countryPicker(View v){
        mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
        mCountryPicker.setListener(new CountryPickerListener() {
            @Override public void onSelectCountry(String name, String code, String dialCode,
                                                  int flagDrawableResID) {
                country_code =Integer.parseInt( dialCode);
                text_country_picker.setText(name);
                text_country_code.setText(dialCode);
                text_fax_code.setText(dialCode);
                text_fax_code_2.setText(dialCode);
                mCountryPicker.dismiss();
            }
        });
    }

    @OnClick(R.id.id_mor_mon_opn_time)
    public void mondayMorningOpenTime(View v){
        Log.e("TIME1","" + "sadfd");
        openTimePicker(textView_mor_mon_opn_time);
    }

    @Nullable
    @Bind(R.id.id_mor_mon_close_time)
    TextView textView_mor_mon_close_time;
    @OnClick(R.id.id_mor_mon_close_time)
    public void mondayMorningCloseTime(View v){
        Log.e("TIME2","" + "sadfd");
        openTimePicker(textView_mor_mon_close_time);
    }

    @Nullable
    @Bind(R.id.id_mor_tue_opn_time)
    TextView textView_mor_tue_opn_time;
    @OnClick(R.id.id_mor_tue_opn_time)
    public void TuesMorningOpenTime(View v){
        Log.e("TIME3","" + "sadfd");
        openTimePicker(textView_mor_tue_opn_time);
    }

    @Nullable
    @Bind(R.id.id_mor_tue_close_time)
    TextView textView_mor_tue_close_time;
    @OnClick(R.id.id_mor_tue_close_time)
    public void TuesMorningCloseTime(View v){
        Log.e("TIME4","" + "sadfd");
        openTimePicker(textView_mor_tue_close_time);
    }

    @Nullable
    @Bind(R.id.id_mor_wed_opn_time)
    TextView textView_mor_wed_opn_time;
    @OnClick(R.id.id_mor_wed_opn_time)
    public void WedMorningOpenTime(View v){
        Log.e("TIME5","" + "sadfd");
        openTimePicker(textView_mor_wed_opn_time);
    }

    @Nullable
    @Bind(R.id.id_mor_wed_close_time)
    TextView textView_mor_wed_close_time;
    @OnClick(R.id.id_mor_wed_close_time)
    public void WedMorningCloseTime(View v){
        Log.e("TIME6","" + "sadfd");
        openTimePicker(textView_mor_wed_close_time);
    }

    @Nullable
    @Bind(R.id.id_mor_thrus_opn_time)
    TextView textView_mor_thrus_opn_time;
    @OnClick(R.id.id_mor_thrus_opn_time)
    public void thrusMorningOpenTime(View v){
        Log.e("TIME7","" + "sadfd");
        openTimePicker(textView_mor_thrus_opn_time);
    }

    @Nullable
    @Bind(R.id.id_mor_thrus_close_time)
    TextView textView_mor_thrus_close_time;
    @OnClick(R.id.id_mor_thrus_close_time)
    public void thrusMorningCloseTime(View v){
        Log.e("TIME8","" + "sadfd");
        openTimePicker(textView_mor_thrus_close_time);
    }

    @Nullable
    @Bind(R.id.id_mor_friday_opn_time)
    TextView textView_mor_friday_opn_time;
    @OnClick(R.id.id_mor_friday_opn_time)
    public void fridayMorningOpenTime(View v){
        Log.e("TIME9","" + "sadfd");
        openTimePicker(textView_mor_friday_opn_time);
    }
    @Nullable
    @Bind(R.id.id_mor_friday_close_time)
    TextView textView_mor_friday_close_time;
    @OnClick(R.id.id_mor_friday_close_time)
    public void fridayMorningCloseTime(View v){
        Log.e("TIME10","" + "sadfd");
        openTimePicker(textView_mor_friday_close_time);
    }
    @Nullable
    @Bind(R.id.id_mor_sat_opn_time)
    TextView textView_mor_sat_opn_time;
    @OnClick(R.id.id_mor_sat_opn_time)
    public void satMorningOpenTime(View v){
        Log.e("TIME11","" + "sadfd");
        openTimePicker(textView_mor_sat_opn_time);
    }

    @Nullable
    @Bind(R.id.id_mor_sat_close_time)
    TextView textView_mor_sat_close_time;
    @OnClick(R.id.id_mor_sat_close_time)
    public void satMorningCloseTime(View v){
        Log.e("TIME12","" + "sadfd");
        openTimePicker(textView_mor_sat_close_time);
    }
    @Nullable
    @Bind(R.id.id_mor_sun_opn_time)
    TextView textView_mor_sun_opn_time;
    @OnClick(R.id.id_mor_sun_opn_time)
    public void sundayMorningOpenTime(View v){
        Log.e("TIME13","" + "sadfd");
        openTimePicker(textView_mor_sun_opn_time);
    }
    @Nullable
    @Bind(R.id.id_mor_sun_close_time)
    TextView textView_mor_sun_close_time;
    @OnClick(R.id.id_mor_sun_close_time)
    public void sundayMorningCloseTime(View v){
        Log.e("TIME14","" + "sadfd");
        openTimePicker(textView_mor_sun_close_time);
    }

    @Nullable
    @Bind(R.id.id_even_mon_opn_time)
    TextView textView_even_mon_opn_time;
    @OnClick(R.id.id_even_mon_opn_time)
    public void mondayEveningOpenTime(View v){
        Log.e("TIME15","" + "sadfd");
        openTimePicker(textView_even_mon_opn_time);
    }

    @Nullable
    @Bind(R.id.id_even_mon_close_time)
    TextView textView_even_mon_close_time;
    @OnClick(R.id.id_even_mon_close_time)
    public void mondayEveningCloseTime(View v){
        Log.e("TIME16","" + "sadfd");
        openTimePicker(textView_even_mon_close_time);
    }
    @Nullable
    @Bind(R.id.id_even_tue_opn_time)
    TextView textView_even_tue_opn_time;
    @OnClick(R.id.id_even_tue_opn_time)
    public void tuesEveningOpenTime(View v){
        Log.e("TIME17","" + "sadfd");
        openTimePicker(textView_even_tue_opn_time);
    }
    @Nullable
    @Bind(R.id.id_even_tue_close_time)
    TextView textView_even_tue_close_time;
    @OnClick(R.id.id_even_tue_close_time)
    public void tuesEveningCloseTime(View v){
        Log.e("TIME18","" + "sadfd");
        openTimePicker(textView_even_tue_close_time);
    }
    @Nullable
    @Bind(R.id.id_even_wed_opn_time)
    TextView textView_even_wed_opn_time;
    @OnClick(R.id.id_even_wed_opn_time)
    public void wedEveningOpenTime(View v){
        Log.e("TIME19","" + "sadfd");
        openTimePicker(textView_even_wed_opn_time);
    }
    @Nullable
    @Bind(R.id.id_even_wed_close_time)
    TextView textView_even_wed_close_time;
    @OnClick(R.id.id_even_wed_close_time)
    public void wedEveningCloseTime(View v){
        Log.e("TIME20","" + "sadfd");
        openTimePicker(textView_even_wed_close_time);
    }
    @Nullable
    @Bind(R.id.id_even_thrus_opn_time)
    TextView textView_even_thrus_opn_time;
    @OnClick(R.id.id_even_thrus_opn_time)
    public void thruEveningOpenTime(View v){
        Log.e("TIME21","" + "sadfd");
        openTimePicker(textView_even_thrus_opn_time);
    }
    @Nullable
    @Bind(R.id.id_even_thrus_close_time)
    TextView textView_even_thrus_close_time;
    @OnClick(R.id.id_even_thrus_close_time)
    public void thruEveningCloseTime(View v){
        Log.e("TIME22","" + "sadfd");
        openTimePicker(textView_even_thrus_close_time);
    }
    @Nullable
    @Bind(R.id.id_even_friday_opn_time)
    TextView textView_even_friday_opn_time;
    @OnClick(R.id.id_even_friday_opn_time)
    public void fridayEveningOpenTime(View v){
        Log.e("TIME23","" + "sadfd");
        openTimePicker(textView_even_friday_opn_time);
    }
    @Nullable
    @Bind(R.id.id_even_friday_close_time)
    TextView textView_even_friday_close_time;
    @OnClick(R.id.id_even_friday_close_time)
    public void fridayEveningCloseTime(View v){
        Log.e("TIME24","" + "sadfd");
        openTimePicker(textView_even_friday_close_time);
    }
    @Nullable
    @Bind(R.id.id_even_sat_opn_time)
    TextView textView_even_sat_opn_time;
    @OnClick(R.id.id_even_sat_opn_time)
    public void saturdayEveningOpenTime(View v){
        Log.e("TIME25","" + "sadfd");
        openTimePicker(textView_even_sat_opn_time);
    }
    @Nullable
    @Bind(R.id.id_even_sat_close_time)
    TextView textView_even_sat_close_time;
    @OnClick(R.id.id_even_sat_close_time)
    public void saturdayEveningCloseTime(View v){
        Log.e("TIME26","" + "sadfd");
        openTimePicker(textView_even_sat_close_time);
    }
    @Nullable
    @Bind(R.id.id_even_sun_opn_time)
    TextView textView_even_sun_opn_time;
    @OnClick(R.id.id_even_sun_opn_time)
    public void sundayEveningOpenTime(View v){
        Log.e("TIME27","" + "sadfd");
        openTimePicker(textView_even_sun_opn_time);
    }
    @Nullable
    @Bind(R.id.id_even_sun_close_time)
    TextView textView_even_sun_close_time;
    @OnClick(R.id.id_even_sun_close_time)
    public void sundayEveningCloseTime(View v){
        Log.e("TIME28","" + "sadfd");
        openTimePicker(textView_even_sun_close_time);
    }

    @Nullable
    @Bind(R.id.id_image_bus_logo_gallery)
    ImageView logo_camera_image;

    @OnClick(R.id.id_image_bus_logo_gallery)
    public void chooseLogoFromCamera(View v){
        i=1;
        Log.e("1===","" + "" + i);
        selectImage();
    }

    @Nullable
    @Bind(R.id.id_image_bus_pic_camere)
    ImageView pic_camera_image;

    @OnClick(R.id.id_image_bus_pic_camere)
    public void choosePicFromCamera(View v){
        i=2;
        Log.e("2===","" + "" + i);
        selectImage();
    }

    @Nullable
    @Bind(R.id.id_image_bus_pic_camere_second)
    ImageView pic_camera_image_2;

    @OnClick(R.id.id_image_bus_pic_camere_second)
    public void choosePicFromCamera2(View v){
        i=3;
        Log.e("3===","" + "" + i);
        selectImage();
    }

    @Nullable
    @Bind(R.id.id_image_bus_pic_camere_third)
    ImageView pic_camera_image_3;

    @OnClick(R.id.id_image_bus_pic_camere_third)
    public void choosePicFromCamera3(View v){
        i=4;
        Log.e("4===","" + "" + i);
        selectImage();
    }

    @Nullable
    @Bind(R.id.id_include_morning)
    View view_morning;

    @Nullable
    @Bind(R.id.id_include_evening)
    View view_evening;

    @Nullable
    @Bind(R.id.id_morning_schdule)
    TextView morning_sch;

    @Nullable
    @Bind(R.id.id_evening_schdule)
    TextView evening_sch;

    @Nullable
    @Bind(R.id.login_progress)
    View mProgressView;

    @Nullable
    @Bind(R.id.login_form)
    View login_form;

    @OnClick(R.id.id_morning_schdule)
    public void morningSchduleClick(View v){
        morning_sch.setBackgroundResource(R.color.colorAccent);
        evening_sch.setBackgroundResource(R.color.white);
        morning_sch.setTextColor(getResources().getColor(R.color.white));
        evening_sch.setTextColor(getResources().getColor(R.color.gray_500));
        view_morning.setVisibility(View.VISIBLE);
        view_evening.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.id_evening_schdule)
    public void eveningSchduleClick(View v){
        morning_sch.setBackgroundResource(R.color.white);
        evening_sch.setBackgroundResource(R.color.colorAccent);
        morning_sch.setTextColor(getResources().getColor(R.color.gray_500));
        evening_sch.setTextColor(getResources().getColor(R.color.white));
        view_morning.setVisibility(View.INVISIBLE);
        view_evening.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Bind(R.id.mapview)
    MapView mapView;

    boolean is_Second_Step_Crossed = false;
    boolean is_First_Step_Crossed = false;
    String business_node_key,business_node_key_second,business_node_key_third;
    String businessTypeenterprize[] = { "1-5", "6-10", "11-20", "21-50",
            "51-100", "101-250","251-500","501-1000","1001-5000","5000-10000",">10000" };
    String businessTypeprofressionals[] = { "1"};
    private CountryPicker mCountryPicker;
    private Subscription _subscription,_storage_subscription;
    static List<Category> LIST;
    private List<String> currentSelectedItems;

    CategoryAdapter categoryAdapter;

    private boolean mapsSupported = true;
    private GoogleMap mMap;
    private double lat_double, lng_double;

    public static final String TAG_CAMERA = "Camera";
    public static final String TAG_CHOOSE_FROM_LIBRARY = "Gallery";
    public static final String TAG_CANCEL = "Cancel";
    public static final String TAG_ADD_PHOTO = "Choose Photo From";
    public static final String TAG_SELECT_FILE = "Select File";
    static int i = 0;
    File file;
    Uri logo_uri = null;
    Uri pic_uri =  null;
    Uri pic_uri_2 =  null;
    Uri pic_uri_3 =  null;

    String logo_url = "";
    String picture_url = "";
    String picture_url_2 = "";
    String picture_url_3 = "";
    int country_code = 221;
    String listing = "Enterprise";
    String number_of_employee = "1-5";

    Day _dayMonday = new Day();
    Day _dayTuesday = new Day();
    Day _dayWednesday = new Day();
    Day _dayThrusday = new Day();
    Day _dayFriday = new Day();
    Day _daySaturday = new Day();
    Day _daySunday = new Day();

    Day _dayMondayEvening = new Day();
    Day _dayTuesdayEvening = new Day();
    Day _dayWednesdayEvening = new Day();
    Day _dayThrusdayEvening = new Day();
    Day _dayFridayEvening = new Day();
    Day _daySaturdayEvening = new Day();
    Day _daySundayEvening = new Day();

    Annotation _annotation = new Annotation();

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


    private void selectImage()  {
        final CharSequence[] items = {TAG_CAMERA, TAG_CHOOSE_FROM_LIBRARY,
                TAG_CANCEL};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditBusiness.this,R.style.MyDialogTheme);
        builder.setTitle(TAG_ADD_PHOTO);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                try {
                    if (items[item].equals(TAG_CAMERA)) {
                        captureImageFromCamera();
                    } else if (items[item].equals(TAG_CHOOSE_FROM_LIBRARY)) {
                        captureImageFromGallery();
                    } else if (items[item].equals(TAG_CANCEL)) {
                        dialog.dismiss();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        // display dialog
        dialog.show();
    }

    public void captureImageFromCamera() throws IOException {
        if(!PermissionUtils.hasPermissions(EditBusiness.this, AppConstant.PERMISSIONS)){
            ActivityCompat.requestPermissions(EditBusiness.this,AppConstant.PERMISSIONS, AppConstant.PERMISSION_ALL);
        }else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's aon camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, AppConstant.REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void captureImageFromGallery() throws IOException {
        if(!PermissionUtils.hasPermissions(EditBusiness.this, AppConstant.PERMISSIONS)){
            ActivityCompat.requestPermissions(EditBusiness.this,AppConstant.PERMISSIONS, AppConstant.PERMISSION_ALL);
        }else{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), AppConstant.PICK_IMAGE_REQUEST);
        }
    }

    private void openTimePicker(final TextView targetView){
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                String minuteString = minute < 10 ? "0" + minute : "" + minute;
                String secondString = second < 10 ? "0" + second : "" + second;
                String time = hourString + "h" + minuteString + "m" + secondString + "s";
                targetView.setText("" + time);
                gettimeList(targetView,hourOfDay,minute,second);
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

        timePickerDialog.show(getFragmentManager(),"Timepickerdialog");
    }

    public void gettimeList(TextView textView,int hour,int min,int sec){
        switch (textView.getId()){
            case R.id.id_mor_mon_opn_time:
                openTime(_dayMonday,"Monday",hour,min,sec);
                break;
            case R.id.id_mor_mon_close_time:
                closeTime(_dayMonday,"Monday",hour,min,sec);
                break;
            case R.id.id_mor_tue_opn_time:
                openTime(_dayTuesday,"Tuesday",hour,min,sec);
                break;
            case R.id.id_mor_tue_close_time:
                closeTime(_dayTuesday,"Tuesday",hour,min,sec);
                break;
            case R.id.id_mor_wed_opn_time:
                openTime(_dayWednesday,"Wednesday",hour,min,sec);
                break;
            case R.id.id_mor_wed_close_time:
                closeTime(_dayWednesday,"Wednesday",hour,min,sec);
                break;
            case R.id.id_mor_thrus_opn_time:
                openTime(_dayThrusday,"Thursday",hour,min,sec);
                break;
            case R.id.id_mor_thrus_close_time:
                closeTime(_dayThrusday,"Thursday",hour,min,sec);
                break;
            case R.id.id_mor_friday_opn_time:
                openTime(_dayFriday,"Friday",hour,min,sec);
                break;
            case R.id.id_mor_friday_close_time:
                closeTime(_dayFriday,"Friday",hour,min,sec);
                break;
            case R.id.id_mor_sat_opn_time:
                openTime(_daySaturday,"Saturday",hour,min,sec);
                break;
            case R.id.id_mor_sat_close_time:
                closeTime(_daySaturday,"Saturday",hour,min,sec);
                break;
            case R.id.id_mor_sun_opn_time:
                openTime(_daySunday,"Sunday",hour,min,sec);
                break;
            case R.id.id_mor_sun_close_time:
                closeTime(_daySunday,"Sunday",hour,min,sec);
                break;
            case R.id.id_even_mon_opn_time:
                openTime(_dayMondayEvening,"Monday",hour,min,sec);
                break;
            case R.id.id_even_mon_close_time:
                closeTime(_dayMondayEvening,"Monday",hour,min,sec);
                break;
            case R.id.id_even_tue_opn_time:
                openTime(_dayTuesdayEvening,"Tuesday",hour,min,sec);
                break;
            case R.id.id_even_tue_close_time:
                closeTime(_dayTuesdayEvening,"Tuesday",hour,min,sec);
                break;
            case R.id.id_even_wed_opn_time:
                openTime(_dayWednesdayEvening,"Wednesday",hour,min,sec);
                break;
            case R.id.id_even_wed_close_time:
                closeTime(_dayWednesdayEvening,"Wednesday",hour,min,sec);
                break;
            case R.id.id_even_thrus_opn_time:
                openTime(_dayThrusdayEvening,"Thursday",hour,min,sec);
                break;
            case R.id.id_even_thrus_close_time:
                closeTime(_dayThrusdayEvening,"Thursday",hour,min,sec);
                break;
            case R.id.id_even_friday_opn_time:
                openTime(_dayFridayEvening,"Friday",hour,min,sec);
                break;
            case R.id.id_even_friday_close_time:
                closeTime(_dayFridayEvening,"Friday",hour,min,sec);
                break;
            case R.id.id_even_sat_opn_time:
                openTime(_daySaturdayEvening,"Saturday",hour,min,sec);
                break;
            case R.id.id_even_sat_close_time:
                closeTime(_daySaturdayEvening,"Saturday",hour,min,sec);
                break;
            case R.id.id_even_sun_opn_time:
                openTime(_daySundayEvening,"Sunday",hour,min,sec);
                break;
            case R.id.id_even_sun_close_time:
                closeTime(_daySundayEvening,"Sunday",hour,min,sec);
                break;
            default:
                Toast.makeText(EditBusiness.this,"No Matching value find",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private Day openTime(Day _day,String day,int hr,int min, int second){
        _day.setDay(day);
//        _day.setOpenAt(Utils.getTimeInMilli(hr,min,second));
        return _day;
    }

    private Day closeTime(Day _day,String day,int hr,int min, int second){
        _day.setDay(day);
//        _day.setCloseAt(Utils.getTimeInMilli(hr,min,second));
        return _day;
    }

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
        if (_subscription !=null)
            _subscription.unsubscribe();
        if (_storage_subscription != null)
            _storage_subscription.unsubscribe();
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
            lat_double = location.getLatitude();
            lng_double = location.getLongitude();
            initializeMap(location);
        }
    }

    private void displayLocationOnDragerEnd(double latitude,double longitude,float mZoom) {
        try {
            lat_double = latitude;
            lng_double = longitude;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), mZoom));
            getStringAdress(latitude,longitude);
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
                .map(new AddressToStringListFunc(lat,lng))
                .subscribeOn(Schedulers.io())               // use I/O thread to query for addresses
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new DisplayAddressOnViewAction(country,text_state,city,text_district,text_neighbour,address,cordinate),new ErrorHandler(EditBusiness.this));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPlayServices())
            buildGoogleApiClient();
        setContentView(R.layout.activity_create_business);
        try {
            MapsInitializer.initialize(EditBusiness.this);
            if (mapView != null) {
                mapView.onCreate(savedInstanceState);
            }

        } catch (Exception e) {
            mapsSupported = false;
        }
        next_steps_btn_three.setEnabled(false);
        pic_camera_image.setTag(1);
        pic_camera_image_2.setTag(1);
        pic_camera_image_3.setTag(1);
        logo_camera_image.setTag(1);
        text_business_type.setText("http://");
        text_business_sec_email.setVisibility(View.GONE);
        setUpSpinner(businessTypeenterprize);
        initInstance();
        init();
        setTimeAsZeroIfNoTimeIsSelectedForDay();
        queryFirebase();
        setupObservables1();
        setupObservables3();
        setupObservables4();
        radioCheckd();
        setupSearchView();
    }

    private void initializeMap(final Location location) {
        if (mMap == null && mapsSupported) {
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    try {
                        mMap = googleMap;
                        mMap.setOnCameraIdleListener(EditBusiness.this);
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

    private void setUpSpinner(String businessType[]){
        SpineerView spineerView = new SpineerView(EditBusiness.this, businessType,spinner, new SpineerView.OnSpinnerSelected() {
            @Override
            public void OnItemSelected(String item) {
                number_of_employee = item;
            }
        });

        spineerView.openSpinner();
    }
    private void init(){
        //add ItemDecoration
        mCountryPicker = CountryPicker.newInstance("Select Country");
        searchView.onActionViewExpanded();
        searchView.setIconifiedByDefault(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditBusiness.this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initInstance(){
        currentSelectedItems = new ArrayList<>();
        LIST = new ArrayList<Category>();
        categoryAdapter = new CategoryAdapter(LIST,EditBusiness.this);
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                AppCompatCheckBox checkBox = (AppCompatCheckBox) v.findViewById(R.id.id_check_box_category);
                if(checkBox.isChecked() && CategoryAdapter.COUNT <3){
                    CategoryAdapter.SELECTED_POSITION.set(position,position);
                    CategoryAdapter.LAST_POSTION = position;
                    CategoryAdapter.COUNT++;
                    currentSelectedItems.add(LIST.get(CategoryAdapter.SELECTED_POSITION.get(position)).getCategoryname());
                }else{
                    if (checkBox.isChecked()){
                        currentSelectedItems.remove(LIST.get(CategoryAdapter.SELECTED_POSITION.get(CategoryAdapter.LAST_POSTION)).getCategoryname());
                        CategoryAdapter.SELECTED_POSITION.set(CategoryAdapter.LAST_POSTION,-1);
                        CategoryAdapter.SELECTED_POSITION.set(position,position);
                        currentSelectedItems.add(LIST.get(CategoryAdapter.SELECTED_POSITION.get(position)).getCategoryname());
                        CategoryAdapter.COUNT = 3;
                    }
                    else{
                        currentSelectedItems.remove(LIST.get(CategoryAdapter.SELECTED_POSITION.get(position)).getCategoryname());
                        CategoryAdapter.SELECTED_POSITION.set(position,-1);
                        CategoryAdapter.COUNT--;
                    }
                    CategoryAdapter.LAST_POSTION = position;
                }

//                category = LIST.get(CategoryAdapter.SELECTED_POSITION.get(position)).getCategoryname();
                categoryAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == AppConstant.REQUEST_TAKE_PHOTO) {
                try {
                    if(data!=null){
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        file =  Utils.saveImageToExternalStorage(photo);
                        if (i==1){
                            logo_camera_image.setTag(2);
                            setPic(file.getAbsolutePath(),logo_camera_image);
                            logo_uri = Uri.fromFile(file);
                        }
                        else if(i==2){
                            pic_camera_image.setTag(2);
                            setPic(file.getAbsolutePath(),pic_camera_image);
                            pic_uri = Uri.fromFile(file);
                        }else if(i==3){
                            pic_camera_image_2.setTag(2);
                            setPic(file.getAbsolutePath(),pic_camera_image_2);
                            pic_uri_2 = Uri.fromFile(file);
                        }else if(i==4){
                            pic_camera_image_3.setTag(2);
                            setPic(file.getAbsolutePath(),pic_camera_image_3);
                            pic_uri_3 = Uri.fromFile(file);
                        }

                        galleryAddPic(file.getAbsolutePath());

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (requestCode == AppConstant.PICK_IMAGE_REQUEST) {
                // SDK >= 11 && SDK < 19
                String realPath = "";
//                 if (Build.VERSION.SDK_INT < 19)
//                    realPath = Utils.getRealPathFromURI_API11to18(this, data.getData());
//                else
//                    realPath = Utils.getRealPathFromURI_API19(this, data.getData());

//                Log.e("REAL PATH", "" + realPath);
                if (i==1){
                    logo_camera_image.setTag(2);
                    logo_uri = data.getData();
//                    logo_camera_image.setImageURI(logo_uri);
                    setPic(logo_uri,logo_camera_image);
                }
                else if(i==2){
                    pic_camera_image.setTag(2);
                    pic_uri = data.getData();
//                    pic_camera_image.setImageURI(pic_uri);
                    setPic(pic_uri,pic_camera_image);
                }else if(i==3){
                    pic_camera_image_2.setTag(2);
                    pic_uri_2 = data.getData();
//                    pic_camera_image.setImageURI(pic_uri);
                    setPic(pic_uri_2,pic_camera_image_2);
                }
                else if(i==4){
                    pic_camera_image_3.setTag(2);
                    pic_uri_3 = data.getData();
//                    pic_camera_image.setImageURI(pic_uri);
                    setPic(pic_uri_3,pic_camera_image_3);
                }
            }
        }
    }

    private void setPic(String mCurrentPhotoPath ,ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
//
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private void setPic(Uri uri ,ImageView imageView) {
        // Get the dimensions of the View
        try {
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,bmOptions);
            imageView.setImageBitmap(bitmap);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void galleryAddPic(String mCurrentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }


    private void setupSearchView()
    {
        // search hint
        searchView.setQueryHint(getString(R.string.search_cat_hint));
        RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        if(charSequence!=null){
                            // Here you can get the text
                            categoryAdapter.filter(charSequence.toString());
                        }
                    }
                });
    }

    private void queryFirebase(){
        if(categoryAdapter!=null){
            categoryAdapter.clearItem();
            categoryAdapter.notifyDataSetChanged();
        }

        CategoryAdapter.SELECTED_POSITION.clear();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query wr = mDatabase.child("categories").orderByValue();
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

                        for (com.google.firebase.database.DataSnapshot child: dataSnapshot.getChildren()) {
//                            Log.e("User key", child.getKey());
//                            Log.e("User ref", child.getRef().toString());
//                            Log.e("User val", child.getValue().toString());
                            String cat = child.getValue().toString();
                            Category category = new Category();
                            category.setCategoryname(cat);
                            category.setChecked(false);
                            categoryAdapter.addItems(category);
                            categoryAdapter.addItem(category);

                        }
                    }
                });

    }


    private void radioCheckd(){
        RxRadioGroup.checkedChanges(radioGroup).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                if (integer == R.id.id_radio_button_1){
                    listing = "Enterprise";
                    setUpSpinner(businessTypeenterprize);
                    spinner.setEnabled(true);
                }else if(integer == R.id.id_radio_button_2){
                    listing = "Professional";
                    setUpSpinner(businessTypeprofressionals);
                    spinner.setEnabled(false);
                }else if(integer == R.id.id_radio_button_3){
                    listing = "Personnel";
                }
            }
        });
    }

    private void setTimeAsZeroIfNoTimeIsSelectedForDay(){
        openTime(_dayMonday,"Monday",0,0,0);
        closeTime(_dayMonday,"Monday",0,0,0);
        openTime(_dayTuesday,"Tuesday",0,0,0);
        closeTime(_dayTuesday,"Tuesday",0,0,0);
        openTime(_dayWednesday,"Wednesday",0,0,0);
        closeTime(_dayWednesday,"Wednesday",0,0,0);
        openTime(_dayThrusday,"Thrusday",0,0,0);
        closeTime(_dayThrusday,"Thrusday",0,0,0);
        openTime(_dayFriday,"Friday",0,0,0);
        closeTime(_dayFriday,"Friday",0,0,0);
        openTime(_daySaturday,"Saturday",0,0,0);
        closeTime(_daySaturday,"Saturday",0,0,0);
        openTime(_daySunday,"Saturday",0,0,0);
        closeTime(_daySunday,"Saturday",0,0,0);
        openTime(_dayMondayEvening,"Monday",0,0,0);
        closeTime(_dayMondayEvening,"Monday",0,0,0);
        openTime(_dayTuesdayEvening,"Tuesday",0,0,0);
        closeTime(_dayTuesdayEvening,"Tuesday",0,0,0);
        openTime(_dayWednesdayEvening,"Wednesday",0,0,0);
        closeTime(_dayWednesdayEvening,"Wednesday",0,0,0);
        openTime(_dayThrusdayEvening,"Thrusday",0,0,0);
        closeTime(_dayThrusdayEvening,"Thrusday",0,0,0);
        openTime(_dayFridayEvening,"Friday",0,0,0);
        closeTime(_dayFridayEvening,"Friday",0,0,0);
        openTime(_daySaturdayEvening,"Saturday",0,0,0);
        closeTime(_daySaturdayEvening,"Saturday",0,0,0);
        openTime(_daySundayEvening,"Sunday",0,0,0);
        closeTime(_daySundayEvening,"Sunday",0,0,0);
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
        try {
            text_business_type.setSelection(7);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        Observable<Boolean> nameObservable = RxTextView.textChanges(text_business_name)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validateBusiness(s.toString());
                        text_business_name.setError(result.getReason());
                        return result.isValid();
                    }
                });

        Observable<Boolean> websiteObservable = RxTextView.textChanges(text_business_type)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validateWebsite(s.toString());
                        text_business_type.setError(result.getReason());
                        return result.isValid();
                    }
                });

        Observable<Boolean> phoneObservable = RxTextView.textChanges(text_business_phone)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validatePhone(s.toString(),country_code);
                        Log.e("PHONE OBSERVABLE","" + result.isValid());
                        text_business_phone.setError(result.getReason());
                        return result.isValid();
                    }
                });

        Observable<Boolean> emailObservable = RxTextView.textChanges(text_business_email)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        Log.i(TAG, "validate email: " + s);
                        ValidationResult result = validateEmail(s.toString());
                        text_business_email.setError(result.getReason());
                        if (result.isValid())
                            text_business_sec_email.setVisibility(View.VISIBLE);
                        else
                            text_business_sec_email.setVisibility(View.GONE);
                        return result.isValid();
                    }
                });

        Observable<Boolean> sec_emailObservable = RxTextView.textChanges(text_business_sec_email)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        Log.i(TAG, "validate email: " + s);
                        ValidationResult result = validateEmail(s.toString());
                        text_business_sec_email.setError(result.getReason());
                        return result.isValid();
                    }
                });


//
        Observable<Boolean> faxObservable = RxTextView.textChanges(text_business_fax)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        ValidationResult result = validatePhone(s.toString(),country_code);
                        text_business_fax.setError(result.getReason());
                        return result.isValid();
                    }
                });


        _subscription = Observable.combineLatest(nameObservable, websiteObservable, phoneObservable,emailObservable, sec_emailObservable, new Func5<Boolean, Boolean, Boolean,Boolean, Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean validName, Boolean validWebsite, Boolean validPhone,Boolean validEmail, Boolean validSecEmail) {
//               Log.i(TAG, "email: " + validEmail + ", username: " + validUsername + ", phone: " + validPhone);
                return validName && validWebsite && validPhone&& validEmail && validSecEmail;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                next_btn_step_1.setEnabled(aBoolean);
            }
        });
    }

    // Validate input data with debounce
    private void setupObservables3() {

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

        next_steps_btn_three.setEnabled(true);
//        Observable<Boolean> phoneObservable = RxTextView.textChanges(text_business_phone)
//                .debounce(800, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<CharSequence, Boolean>() {
//                    @Override
//                    public Boolean call(CharSequence s) {
//                        ValidationResult result = validatePhone(s.toString());
//                        text_business_phone.setError(result.getReason());
//                        return result.isValid();
//                    }
//                });
////
//        Observable<Boolean> faxObservable = RxTextView.textChanges(text_business_fax)
//                .debounce(800, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<CharSequence, Boolean>() {
//                    @Override
//                    public Boolean call(CharSequence s) {
//                        ValidationResult result = validatePhone(s.toString());
//                        text_business_fax.setError(result.getReason());
//                        return result.isValid();
//                    }
//                });
//
//
//        _subscription = Observable.combineLatest(phoneObservable, faxObservable, new Func2<Boolean, Boolean, Boolean>() {
//            @Override
//            public Boolean call(Boolean validPhone, Boolean validFax) {
//                return validPhone && validFax;
//            }
//        }).subscribe(new Action1<Boolean>() {
//            @Override
//            public void call(Boolean aBoolean) {
//                next_steps_btn_three.setEnabled(aBoolean);
//            }
//        });
    }

    // Validate input data with debounce
    private void setupObservables4() {

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


        Observable<Boolean> facebook_oservable = RxTextView.textChanges(text_facebook_share)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        text_facebook_share.setText("http://www.facebook.com"+s);
                        return true;
                    }
                });
//
        Observable<Boolean> google_observable = RxTextView.textChanges(text_google_share)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        text_google_share.setText("http://www.google.com"+s);
                        return true;
                    }
                });

        Observable<Boolean> twitter_observable = RxTextView.textChanges(text_twitter_share)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence s) {
                        text_twitter_share.setText("http://www.twitter.com"+s);
                        return true;
                    }
                });
        _subscription = Observable.combineLatest(facebook_oservable, google_observable, twitter_observable,new Func3<Boolean, Boolean, Boolean,Boolean>() {
            @Override
            public Boolean call(Boolean validFacebook, Boolean validGoogle,Boolean validTwitter) {
                return validFacebook && validGoogle && validTwitter;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

            }
        });
    }

    private ValidationResult<String> validateEmail(@NonNull String email) {
        return ValidationUtils.isValidEmailAddress(email);
    }

    private ValidationResult<String> validateUsername(@NonNull String username) {
        return ValidationUtils.isValidUsername(username);
    }

    private ValidationResult<String> validateBusiness(@NonNull String username) {
        return ValidationUtils.isValidBusinessName(username);
    }
    private ValidationResult<String> validateWebsite(@NonNull String username) {
        return ValidationUtils.isValidWebsite(username);
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

    private void uploadFiles(){
        if (logo_uri==null){
            logo_uri = Utils.getUriToResource(EditBusiness.this,R.drawable.no_image_available);
        }
        if(pic_uri == null){
            pic_uri = Utils.getUriToResource(EditBusiness.this,R.drawable.ic_pic_one);;
        }
        if(pic_uri_2 == null){
            pic_uri_2 = Utils.getUriToResource(EditBusiness.this,R.drawable.ic_pic_two);;
        }
        if(pic_uri_3 == null){
            pic_uri_3 = Utils.getUriToResource(EditBusiness.this,R.drawable.ic_pic_three);
        }
        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(AppConstant.FIREBASE_STORAGE_REFRENCE_URL);
        _storage_subscription = RxFirebaseStorage.putFile(storageRef.child("images/"+logo_uri.getLastPathSegment()),logo_uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<UploadTask.TaskSnapshot, UploadTask.TaskSnapshot>() {
                    @Override
                    public UploadTask.TaskSnapshot call(UploadTask.TaskSnapshot taskSnapshot) {
                        logo_url = taskSnapshot.getDownloadUrl().toString();
                        System.out.println("LOGO URL is " + taskSnapshot.getDownloadUrl());
                        return taskSnapshot;
                    }
                })
                .flatMap(new Func1<UploadTask.TaskSnapshot, Observable<UploadTask.TaskSnapshot>>() {
                    @Override
                    public Observable<UploadTask.TaskSnapshot > call(UploadTask.TaskSnapshot taskSnapshot) {
                        return RxFirebaseStorage.putFile(storageRef.child("images/"+pic_uri.getLastPathSegment()),pic_uri);
                    }
                })
                .map(new Func1<UploadTask.TaskSnapshot, UploadTask.TaskSnapshot>() {
                    @Override
                    public UploadTask.TaskSnapshot call(UploadTask.TaskSnapshot taskSnapshot) {
                        picture_url = taskSnapshot.getDownloadUrl().toString();
                        System.out.println("PIC URL is " + picture_url);
                        return taskSnapshot;
                    }
                })
                .flatMap(new Func1<UploadTask.TaskSnapshot, Observable<UploadTask.TaskSnapshot>>() {
                    @Override
                    public Observable<UploadTask.TaskSnapshot > call(UploadTask.TaskSnapshot taskSnapshot) {
                        return RxFirebaseStorage.putFile(storageRef.child("images/"+pic_uri_2.getLastPathSegment()),pic_uri_2);
                    }
                })
                .map(new Func1<UploadTask.TaskSnapshot, UploadTask.TaskSnapshot>() {
                    @Override
                    public UploadTask.TaskSnapshot call(UploadTask.TaskSnapshot taskSnapshot) {
                        picture_url_2 = taskSnapshot.getDownloadUrl().toString();
                        System.out.println("PIC URL 2 is " + picture_url_2);
                        return taskSnapshot;
                    }
                })
                .flatMap(new Func1<UploadTask.TaskSnapshot, Observable<UploadTask.TaskSnapshot>>() {
                    @Override
                    public Observable<UploadTask.TaskSnapshot > call(UploadTask.TaskSnapshot taskSnapshot) {
                        return RxFirebaseStorage.putFile(storageRef.child("images/"+pic_uri_3.getLastPathSegment()),pic_uri_3);
                    }
                })
                .map(new Func1<UploadTask.TaskSnapshot, UploadTask.TaskSnapshot>() {
                    @Override
                    public UploadTask.TaskSnapshot call(UploadTask.TaskSnapshot taskSnapshot) {
                        picture_url_3 = taskSnapshot.getDownloadUrl().toString();
                        System.out.println("PIC URL 3 is " + picture_url_3);
                        return taskSnapshot;
                    }
                })
                .subscribe(new Subscriber<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        System.out.println("Upload is " + progress + "% done");
//                        saveDataToFirebaseDatabase();
                        saveToDatabaseStorageForSteps4(new Firebase(AppConstant.FIREBASE_DATABSE_REFRENCE_URL+"businesses"));
                    }
                });
    }

    private Businesse createBusinessListForStepOne(){
        Businesse businesse = new Businesse();
        try {
            Mapdata mapdata = new Mapdata();
            List<Day> TIMELIST = new ArrayList<>();
            ArrayList<Annotation> annonationList = new ArrayList<>();
            ArrayList<String> pictures = new ArrayList<>();
            pictures.add(picture_url);
            pictures.add(picture_url_2);
            pictures.add(picture_url_3);
//            _annotation.setLatitude(0.0);
//            _annotation.setLongitude(0.0);
            _annotation.setTitle("Title" + 1);
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            Openhours openhours = new Openhours();
            openhours.setDays(TIMELIST);
//            openhours.setZone(1);
            businesse.setNumberEmployes(number_of_employee);
//            businesse.setCategory("");
            businesse.setCity("");
            businesse.setCountry("");
            businesse.setDescription(text_business_description.getText().toString() != null ? text_business_description.getText().toString() : "");
            businesse.setEmail(text_business_email.getText().toString());
            businesse.setListingtype(listing);
            businesse.setLogo(logo_url);
            annonationList.add(_annotation);
            mapdata.setAnnotations(annonationList);
            businesse.setMapdata(mapdata);
            businesse.setName(text_business_name.getText().toString());
//            businesse.setNeighbour_hood("");
            businesse.setOfficeLocation(0.0 + "," + 0.0);
            businesse.setOpenhours(openhours);
//            businesse.setPhoneNumber(text_business_phone.getText().toString());
//            businesse.setFaxnumber(text_business_fax.getText().toString());
//            businesse.setAlternatefaxnumber(text_business_fax_2.getText().toString());
//            businesse.setPictures(pictures);
//            businesse.setRoad(2.0);
            businesse.setState("");
//            businesse.setDistrict("");
//            businesse.setSuburb("");
//            businesse.setuid(firebaseUser.getUid());
            businesse.setWebsite(text_business_type.getText().toString());
//            businesse.setFacebookPage("");
//            businesse.setTwitterPage("");
//            businesse.setGooglePage("");
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return businesse;
    }

    private Map<String, Object> updateBusinessListForStepTwo(String category){

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Mapdata mapdata = new Mapdata();
            List<Day> TIMELIST = new ArrayList<>();
            ArrayList<Annotation> annonationList = new ArrayList<>();
            ArrayList<String> pictures = new ArrayList<>();
            pictures.add(picture_url);
            pictures.add(picture_url_2);
            pictures.add(picture_url_3);
//            _annotation.setLatitude(0.0);
//            _annotation.setLongitude(0.0);
            _annotation.setTitle("Title" + 1);
            annonationList.add(_annotation);
            mapdata.setAnnotations(annonationList);
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            Openhours openhours = new Openhours();
            openhours.setDays(TIMELIST);
//            openhours.setZone(1);
            map.put("Number_employes",number_of_employee);
            map.put("category",category);
            map.put("email",text_business_email.getText().toString());
            map.put("description",text_business_description.getText().toString() != null ? text_business_description.getText().toString() : "");
            map.put("listingtype",listing);
            map.put("logo",logo_url);
            map.put("mapdata",mapdata);
            map.put("name",text_business_name.getText().toString());
            map.put("neighbour_hood","");
            map.put("officeLocation",0.0 + "," + 0.0);
            map.put("openhours",openhours);
            map.put("phoneNumber",text_business_phone.getText().toString());
            map.put("faxnumber",text_business_fax.getText().toString());
            map.put("alternatefaxnumber",text_business_fax_2.getText().toString());
            map.put("pictures",pictures);
            map.put("country","");
            map.put("state","");
            map.put("city","");
            map.put("district","");
            map.put("road","");
            map.put("suburb","");
            map.put("uid",firebaseUser.getUid());
            map.put("website",text_business_type.getText().toString());
            map.put("facebookPage","");
            map.put("googlePage","");
            map.put("twitterPage","");

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return map;
    }

    private Map<String, Object> updateBusinessListForStepThree(String category){

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Mapdata mapdata = new Mapdata();
            List<Day> TIMELIST = new ArrayList<>();
            ArrayList<Annotation> annonationList = new ArrayList<>();
            ArrayList<String> pictures = new ArrayList<>();
            pictures.add(picture_url);
            pictures.add(picture_url_2);
            pictures.add(picture_url_3);
//            _annotation.setLatitude(lat_double);
//            _annotation.setLongitude(lng_double);
            _annotation.setTitle("Title" + 1);
            annonationList.add(_annotation);
            mapdata.setAnnotations(annonationList);
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            TIMELIST.add(new Day());
            Openhours openhours = new Openhours();
            openhours.setDays(TIMELIST);
//            openhours.setZone(1);
            map.put("Number_employes",number_of_employee);
            map.put("category",category);
            map.put("email",text_business_email.getText().toString());
            map.put("description",text_business_description.getText().toString() != null ? text_business_description.getText().toString() : "");
            map.put("listingtype",listing);
            map.put("logo",logo_url);
            map.put("mapdata",mapdata);
            map.put("name",text_business_name.getText().toString());
            map.put("neighbour_hood","political");
            map.put("officeLocation",cordinate.getText().toString());
            map.put("openhours",openhours);
            map.put("phoneNumber",text_business_phone.getText().toString());
            map.put("faxnumber",text_business_fax.getText().toString());
            map.put("alternatefaxnumber",text_business_fax_2.getText().toString());
            map.put("pictures",pictures);
            map.put("country",country.getText().toString());
            map.put("state",text_state.getText().toString());
            map.put("city",city.getText().toString());
            map.put("district",text_district.getText().toString());
            map.put("road","");
            map.put("suburb","");
            map.put("uid",firebaseUser.getUid());
            map.put("website",text_business_type.getText().toString());
            map.put("facebookPage","");
            map.put("googlePage","");
            map.put("twitterPage","");
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return map;
    }

    private Map<String, Object> updateBusinessListForStepFour(String category){

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Mapdata mapdata = new Mapdata();
            List<Day> TIMELIST = new ArrayList<>();
            ArrayList<Annotation> annonationList = new ArrayList<>();
            ArrayList<String> pictures = new ArrayList<>();
            pictures.add(picture_url);
            pictures.add(picture_url_2);
            pictures.add(picture_url_3);
//            _annotation.setLatitude(lat_double);
//            _annotation.setLongitude(lng_double);
            _annotation.setTitle("Title" + 1);
            annonationList.add(_annotation);
            mapdata.setAnnotations(annonationList);
            TIMELIST.add(_dayMonday);
            TIMELIST.add(_dayTuesday);
            TIMELIST.add(_dayWednesday);
            TIMELIST.add(_dayThrusday);
            TIMELIST.add(_dayFriday);
            TIMELIST.add(_daySaturday);
            TIMELIST.add(_daySunday);
            TIMELIST.add(_dayMondayEvening);
            TIMELIST.add(_dayTuesdayEvening);
            TIMELIST.add(_dayWednesdayEvening);
            TIMELIST.add(_dayThrusdayEvening);
            TIMELIST.add(_dayFridayEvening);
            TIMELIST.add(_daySaturdayEvening);
            TIMELIST.add(_daySundayEvening);
            Openhours openhours = new Openhours();
            openhours.setDays(TIMELIST);
//            openhours.setZone(1);
            map.put("Number_employes",number_of_employee);
            map.put("category",category);
            map.put("email",text_business_email.getText().toString());
            map.put("description",text_business_description.getText().toString() != null ? text_business_description.getText().toString() : "");
            map.put("listingtype",listing);
            map.put("logo",logo_url);
            map.put("mapdata",mapdata);
            map.put("name",text_business_name.getText().toString());
            map.put("neighbour_hood","political");
            map.put("officeLocation",cordinate.getText().toString());
            map.put("openhours",openhours);
            map.put("phoneNumber",text_business_phone.getText().toString());
            map.put("faxnumber",text_business_fax.getText().toString());
            map.put("alternatefaxnumber",text_business_fax_2.getText().toString());
            map.put("pictures",pictures);
            map.put("country",country.getText().toString());
            map.put("state",text_state.getText().toString());
            map.put("city",city.getText().toString());
            map.put("district",text_district.getText().toString());
            map.put("road","");
            map.put("suburb","");
            map.put("uid",firebaseUser.getUid());
            map.put("website",text_business_type.getText().toString());
            map.put("facebookPage",text_facebook_share.getText().toString());
            map.put("googlePage",text_google_share.getText().toString());
            map.put("twitterPage",text_twitter_share.getText().toString());

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return map;
    }



    private Businesse createBusinessList(String category1){
        Businesse businesse = new Businesse();
        try {
            Mapdata mapdata = new Mapdata();
            List<Day> TIMELIST = new ArrayList<>();
            ArrayList<Annotation> annonationList = new ArrayList<>();
            ArrayList<String> pictures = new ArrayList<>();
            pictures.add(picture_url);
//            _annotation.setLatitude(lat_double);
//            _annotation.setLongitude(lng_double);
            _annotation.setTitle("Title" + 1);
            TIMELIST.add(_dayMonday);
            TIMELIST.add(_dayTuesday);
            TIMELIST.add(_dayWednesday);
            TIMELIST.add(_dayThrusday);
            TIMELIST.add(_dayFriday);
            TIMELIST.add(_daySaturday);
            TIMELIST.add(_daySunday);
            TIMELIST.add(_dayMondayEvening);
            TIMELIST.add(_dayTuesdayEvening);
            TIMELIST.add(_dayWednesdayEvening);
            TIMELIST.add(_dayThrusdayEvening);
            TIMELIST.add(_dayFridayEvening);
            TIMELIST.add(_daySaturdayEvening);
            TIMELIST.add(_daySundayEvening);
            Openhours openhours = new Openhours();
            openhours.setDays(TIMELIST);
//            openhours.setZone(1);
            businesse.setNumberEmployes("50");
//            businesse.setCategory(category1);
            businesse.setCity(city.getText().toString());
            businesse.setDescription(text_business_description.getText().toString() != null ? text_business_description.getText().toString() : "");
            businesse.setCountry(country.getText().toString());
            businesse.setEmail(text_business_email.getText().toString());
            businesse.setListingtype(listing);
            businesse.setLogo(logo_url);
            annonationList.add(_annotation);
            mapdata.setAnnotations(annonationList);
            businesse.setName(text_business_name.getText().toString());
            businesse.setOfficeLocation(cordinate.getText().toString());
            businesse.setOpenhours(openhours);
//            businesse.setPhoneNumber(text_business_phone.getText().toString());
//            businesse.setPictures(pictures);
//            businesse.setRoad(address.getText().toString());
            businesse.setState(city.getText().toString());
//            businesse.setDistrict(text_district.getText().toString());
//            businesse.setSuburb(city.getText().toString());
//            businesse.setuid(firebaseUser.getUid());
            businesse.setWebsite(text_business_type.getText().toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return businesse;
    }

    private void saveDataToDataStorageForStep1(Firebase ref){
        if (!is_First_Step_Crossed){
            RxFirebaseDatabase.pushValue(ref,createBusinessListForStepOne())
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
                            showProgress(false);
                            enableSecondstate();
                            business_node_key = firebase.getKey();
                            Log.e("KEY VALUE", "" + business_node_key);
                        }
                    });
        }else{
            RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepTwo(""))
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
                            showProgress(false);
                            enableSecondstate();
                            business_node_key = firebase.getKey();
                            Log.e("KEY VALUE", "" + business_node_key);
                        }
                    });
        }

    }

    private void saveToDatabaseStorageForSteps2(Firebase ref){
        Observable<Firebase> first_observable,second_observable,third_observable;
        Log.e("ddfdf","" + currentSelectedItems.size());
        if (currentSelectedItems.size() == 1){
            String as = currentSelectedItems.get(0);
            RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepTwo(as))
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
                            showProgress(false);
                            enableThirdState();
                        }
                    });
        }else if(currentSelectedItems.size() == 2){
            String as = currentSelectedItems.get(0);
            String as1 = currentSelectedItems.get(1);
            if (!is_Second_Step_Crossed){
                first_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepTwo(as))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                second_observable =  RxFirebaseDatabase.pushValue(ref,createBusinessList(as1))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }else{
                first_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepTwo(as))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                second_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key_second,updateBusinessListForStepTwo(as1))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

            Observable.zip(first_observable, second_observable, new Func2<Firebase, Firebase, MyFirebase>() {
                @Override
                public MyFirebase call(Firebase firebase, Firebase firebase2) {
                    return new MyFirebase(firebase,firebase2,null);
                }
            }).subscribe(new Subscriber<MyFirebase>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(MyFirebase firebase) {
                    showProgress(false);
                    business_node_key = firebase.getFirebase_one().getKey();
                    business_node_key_second = firebase.getFirebase_two().getKey();
                    enableThirdState();
                }
            });
        }else if (currentSelectedItems.size() == 3){
            String as = currentSelectedItems.get(0);
            String as1 = currentSelectedItems.get(1);
            String as2 = currentSelectedItems.get(2);
            if (!is_Second_Step_Crossed){
                first_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepTwo(as))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                second_observable =  RxFirebaseDatabase.pushValue(ref,createBusinessList(as1))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                third_observable = RxFirebaseDatabase.pushValue(ref,createBusinessList(as2))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }else{
                first_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepTwo(as))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                second_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key_second,updateBusinessListForStepTwo(as1))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                third_observable = RxFirebaseDatabase.overWriteValue(ref,business_node_key_third,updateBusinessListForStepTwo(as2))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

            Observable.zip(first_observable, second_observable, third_observable, new Func3<Firebase, Firebase, Firebase, MyFirebase>() {
                @Override
                public MyFirebase call(Firebase firebase, Firebase firebase2, Firebase firebase3) {
                    return new MyFirebase(firebase, firebase2,firebase3);
                }
            }).subscribe(new Subscriber<MyFirebase>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(MyFirebase firebase) {
                    showProgress(false);
                    enableThirdState();
                    business_node_key = firebase.getFirebase_one().getKey();
                    business_node_key_second = firebase.getFirebase_two().getKey();
                    business_node_key_third = firebase.getFirebase_three().getKey();
                }
            });
        }
    }

    private void saveToDatabaseStorageForSteps3(Firebase ref){
        Observable<Firebase> first_observable,second_observable,third_observable;
        if (currentSelectedItems.size() == 1){
            String as = currentSelectedItems.get(0);
            RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepThree(as))
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
                            showProgress(false);
                            enableFourthState();
                        }
                    });
        }else if(currentSelectedItems.size() == 2){
            String as = currentSelectedItems.get(0);
            String as1 = currentSelectedItems.get(1);
            first_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepThree(as))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            second_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key_second,updateBusinessListForStepThree(as1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable.zip(first_observable, second_observable, new Func2<Firebase, Firebase, MyFirebase>() {
                @Override
                public MyFirebase call(Firebase firebase, Firebase firebase2) {
                    return new MyFirebase(firebase,firebase2,null);
                }
            }).subscribe(new Subscriber<MyFirebase>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(MyFirebase firebase) {
                    showProgress(false);
                    enableFourthState();
                }
            });
        }else if (currentSelectedItems.size() == 3){
            String as = currentSelectedItems.get(0);
            String as1 = currentSelectedItems.get(1);
            String as2 = currentSelectedItems.get(2);
            first_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepThree(as))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            second_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key_second,updateBusinessListForStepThree(as1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            third_observable = RxFirebaseDatabase.overWriteValue(ref,business_node_key_third,updateBusinessListForStepThree(as2))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable.zip(first_observable, second_observable, third_observable, new Func3<Firebase, Firebase, Firebase, MyFirebase>() {
                @Override
                public MyFirebase call(Firebase firebase, Firebase firebase2, Firebase firebase3) {
                    return new MyFirebase(firebase,firebase2,firebase3);
                }
            }).subscribe(new Subscriber<MyFirebase>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(MyFirebase firebase) {
                    showProgress(false);
                    enableFourthState();
                }
            });
        }
    }

    private void saveToDatabaseStorageForSteps4(Firebase ref){
        Observable<Firebase> first_observable,second_observable,third_observable;
        if (currentSelectedItems.size() == 1){
            String as = currentSelectedItems.get(0);
            RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepFour(as))
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
                            showProgress(false);
                            AppController.getInstance().okAlert(EditBusiness.this,"","Informations sent");
                        }
                    });
        }else if(currentSelectedItems.size() == 2){
            String as = currentSelectedItems.get(0);
            String as1 = currentSelectedItems.get(1);
            first_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepFour(as))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            second_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key_second,updateBusinessListForStepFour(as1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable.zip(first_observable, second_observable, new Func2<Firebase, Firebase, MyFirebase>() {
                @Override
                public MyFirebase call(Firebase firebase, Firebase firebase2) {
                    return new MyFirebase(firebase,firebase2,null);
                }
            }).subscribe(new Subscriber<MyFirebase>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(MyFirebase firebase) {
                    showProgress(false);
                    AppController.getInstance().okAlert(EditBusiness.this,"","Informations sent");
                }
            });
        }else if (currentSelectedItems.size() == 3){
            String as = currentSelectedItems.get(0);
            String as1 = currentSelectedItems.get(1);
            String as2 = currentSelectedItems.get(2);
            first_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key,updateBusinessListForStepFour(as))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            second_observable =  RxFirebaseDatabase.overWriteValue(ref,business_node_key_second,updateBusinessListForStepFour(as1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            third_observable = RxFirebaseDatabase.overWriteValue(ref,business_node_key_third,updateBusinessListForStepFour(as2))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable.zip(first_observable, second_observable, third_observable, new Func3<Firebase, Firebase, Firebase, MyFirebase>() {
                @Override
                public MyFirebase call(Firebase firebase, Firebase firebase2, Firebase firebase3) {
                    return new MyFirebase(firebase,firebase2,firebase3);
                }
            }).subscribe(new Subscriber<MyFirebase>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(MyFirebase firebase) {
                    showProgress(false);
                    AppController.getInstance().okAlert(EditBusiness.this,"","Informations sent");
                }
            });
        }
    }
    private void saveToDatabaseStorage(Firebase ref){
        Observable<Firebase> first_observable,second_observable,third_observable;
        if (currentSelectedItems.size() == 1){
            String as = currentSelectedItems.get(0);
            RxFirebaseDatabase.pushValue(ref,createBusinessList(as))
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
                            showProgress(false);
                            AppController.getInstance().okAlert(EditBusiness.this,"","Informations sent");
                        }
                    });
        }else if(currentSelectedItems.size() == 2){
            String as = currentSelectedItems.get(0);
            String as1 = currentSelectedItems.get(1);
            first_observable =  RxFirebaseDatabase.pushValue(ref,createBusinessList(as))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            second_observable =  RxFirebaseDatabase.pushValue(ref,createBusinessList(as1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable.zip(first_observable, second_observable, new Func2<Firebase, Firebase, Firebase>() {
                @Override
                public Firebase call(Firebase firebase, Firebase firebase2) {
                    return firebase2;
                }
            }).subscribe(new Subscriber<Firebase>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(Firebase firebase) {
                    showProgress(false);
                    AppController.getInstance().okAlert(EditBusiness.this,"","Informations sent");
                }
            });
        }else if (currentSelectedItems.size() == 3){
            String as = currentSelectedItems.get(0);
            String as1 = currentSelectedItems.get(1);
            String as2 = currentSelectedItems.get(2);
            first_observable =  RxFirebaseDatabase.pushValue(ref,createBusinessList(as))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            second_observable =  RxFirebaseDatabase.pushValue(ref,createBusinessList(as1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            third_observable = RxFirebaseDatabase.pushValue(ref,createBusinessList(as2))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            Observable.zip(first_observable, second_observable, third_observable, new Func3<Firebase, Firebase, Firebase, Firebase>() {
                @Override
                public Firebase call(Firebase firebase, Firebase firebase2, Firebase firebase3) {
                    return firebase3;
                }
            }).subscribe(new Subscriber<Firebase>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(Firebase firebase) {
                    showProgress(false);
                    AppController.getInstance().okAlert(EditBusiness.this,"","Informations sent");
                }
            });
        }
    }
    private void enableFirstState(){
        steps_one_view.setVisibility(View.VISIBLE);
        steps_two_view.setVisibility(View.GONE);
        steps_three_view.setVisibility(View.GONE);
        steps_four_view.setVisibility(View.GONE);
    }
    private void enableSecondstate(){
        steps_one_view.setVisibility(View.GONE);
        steps_two_view.setVisibility(View.VISIBLE);
        steps_three_view.setVisibility(View.GONE);
        steps_four_view.setVisibility(View.GONE);
    }
    private void enableThirdState(){
        steps_one_view.setVisibility(View.GONE);
        steps_two_view.setVisibility(View.GONE);
        steps_three_view.setVisibility(View.VISIBLE);
        steps_four_view.setVisibility(View.GONE);
    }
    private void enableFourthState(){
        steps_one_view.setVisibility(View.GONE);
        steps_two_view.setVisibility(View.GONE);
        steps_three_view.setVisibility(View.GONE);
        steps_four_view.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.id_next_btn_step_1)
    public void stepFirstNextButtonClick(View v){
//       enableSecondstate();
        showProgress(true);
        saveDataToDataStorageForStep1(new Firebase(AppConstant.FIREBASE_DATABSE_REFRENCE_URL+"businesses"));
    }
    @OnClick(R.id.id_prev_btn_steps_two)
    public void stepSecondPrevButtonClick(View v){
        is_First_Step_Crossed = true;
        enableFirstState();
    }

    @OnClick(R.id.id_next_btn_steps_two)
    public void stepSecondNextButtonClick(View v){
//        enableThirdState();
        showProgress(true);
        saveToDatabaseStorageForSteps2(new Firebase(AppConstant.FIREBASE_DATABSE_REFRENCE_URL+"businesses"));
    }

    @OnClick(R.id.id_prev_btn_steps_three)
    public void stepThirdPrevButtonClick(View v){
        is_Second_Step_Crossed = true;
        enableSecondstate();
    }

    @OnClick(R.id.id_next_btn_steps_three)
    public void stepThirdNextButtonClick(View v){
        showProgress(true);
        saveToDatabaseStorageForSteps3(new Firebase(AppConstant.FIREBASE_DATABSE_REFRENCE_URL+"businesses"));
//        enableFourthState();
    }

    @OnClick(R.id.id_prev_btn_steps_four)
    public void stepFourthPrevButtonClick(View v){
        enableThirdState();
    }


    @OnClick(R.id.id_next_btn_steps_four)
    public void saveToStorage(View v){
        showProgress(true);
        uploadFiles();

    }




    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        assert marker != null;
        displayLocationOnDragerEnd(marker.getPosition().latitude,marker.getPosition().longitude,15.0f);
    }

    @Override
    public void onCameraIdle() {
        // Cleaning all the markers.
        if (mMap !=null){
            LatLng mPosition = mMap.getCameraPosition().target;
            float mZoom = mMap.getCameraPosition().zoom;
            displayLocationOnDragerEnd(mPosition.latitude,mPosition.longitude,mZoom);
        }
    }
}
