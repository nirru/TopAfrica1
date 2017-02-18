package com.africa.annauiare.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.africa.annauiare.R;
import com.africa.annauiare.common.BaseSpashActivity;

public class SplashActivity extends BaseSpashActivity {

    private static int SPLASH_TIME_OUT = 3000;

    public FirebaseUser firebaseUser;
    AppCompatButton btnLogin,btnSignup;

    @Override
    protected void onStart() {
        super.onStart();
//        observeState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
//        splashThread();
    }

    private void splashThread(){
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (firebaseUser!=null){
                    Intent i = new Intent(SplashActivity.this, LandingActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                finish();
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        }, SPLASH_TIME_OUT);
    }

    private void init(){
        btnLogin = (AppCompatButton)findViewById(R.id.simple_sign_in);
        btnSignup = (AppCompatButton)findViewById(R.id.simple_sign_up);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onConnected(Location location) {

    }

    @Override
    public void fireBaseAuthication(FirebaseUser firebaseUser) {
      this.firebaseUser = firebaseUser;
    }
}
