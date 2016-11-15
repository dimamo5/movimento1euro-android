package com.artisans.code.movimento1euro.menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by duarte on 28-10-2016.
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;

        String firebaseToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase Token", firebaseToken);

        if(getSharedPreferences("userInfo",MODE_PRIVATE).getString("token", null) != null){
            intent = new Intent(this, MainMenu.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();

    }
}
