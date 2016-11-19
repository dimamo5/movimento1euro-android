package com.artisans.code.movimento1euro.menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import com.facebook.FacebookSdk;

/**
 * Created by duarte on 28-10-2016.
 */

public class SplashScreen extends AppCompatActivity {
    public final static String TAG = SplashScreen.class.getCanonicalName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        Intent intent;

        String firebaseToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Firebase Token: " + firebaseToken);

        if(getSharedPreferences("userInfo",MODE_PRIVATE).getString("token", null) != null){
            intent = new Intent(this, MainMenu.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();

    }
}
