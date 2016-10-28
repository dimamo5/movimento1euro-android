package com.artisans.code.movimento1euro.menus;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;

public class LoginActivity extends AppCompatActivity {

    private final String REGISTRATION_URL = "http://movimento1euro.com/inscreva-se-aqui";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        Button signUpBtn = (Button) findViewById(R.id.btn_sign_up);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(REGISTRATION_URL);
                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                intent.putExtra("url", uri);
                intent.putExtra("label", "Registo");
                startActivity(intent);
            }
        });

        Button signInBtn = (Button) findViewById(R.id.btn_sign_in);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement user authentication
                Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });
    }
}
