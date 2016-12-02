package com.artisans.code.movimento1euro.menus;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.elements.Cause;

import java.util.ArrayList;

/**
 * Created by Filipe on 02/12/2016.
 */

public class ViewActivity extends AppCompatActivity {

    Cause cause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("Entrei"," Entrei Aqui");
        cause = (Cause) getIntent().getSerializableExtra("Cause");
        setContentView(R.layout.activity_view_cause);

    }

}
