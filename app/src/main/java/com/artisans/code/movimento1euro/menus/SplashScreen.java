package com.artisans.code.movimento1euro.menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by duarte on 28-10-2016.
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;

        if(true){
            intent = new Intent(this, MainMenu.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();

    }
}
