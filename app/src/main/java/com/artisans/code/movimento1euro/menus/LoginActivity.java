package com.artisans.code.movimento1euro.menus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.network.ApiManager;
import com.artisans.code.movimento1euro.network.LoginTask;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private final String REGISTRATION_URL = "http://movimento1euro.com/inscreva-se-aqui";

    EditText inputEmail;
    EditText inputPassword;
    AppCompatActivity activity = this;
    private LoginButton loginButton;
    private CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login_layout);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        findViewById(R.id.layout_login_screen).requestFocus();

        // TODO: 13-11-2016 Remover isto antes de entregar
/*        inputEmail.setText("diogo@cenas.pt");
        inputPassword.setText("123");*/

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
                standardLogin(view);
            }
        });

        facebookLoginInit();
    }

    private void facebookLoginInit() {
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("email");
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken token = loginResult.getAccessToken();
                Log.d(TAG, "userID: " + token.getUserId());
                Log.d(TAG, "token: " + token.getToken());
                /*Toast toast = Toast.makeText(activity,token.getUserId(), Toast.LENGTH_SHORT);
                toast.show();*/
                new LoginTask(activity, LoginTask.LoginType.FACEBOOK).execute(token.getUserId(), token.getToken());
            }

            @Override
            public void onCancel() {
                Toast toast = Toast.makeText(activity,"Login Canceled", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onError(FacebookException exception) {
                exception.printStackTrace();
                Toast toast = Toast.makeText(activity,exception.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void standardLogin(View view) {
        // Gets the URL from the UI's text field.
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new LoginTask(activity, LoginTask.LoginType.STANDARD).execute(email,password);
        } else {
            Toast toast = Toast.makeText(this,activity.getString(R.string.failed_connection), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void unauthenticatedLogin(View view){
        if(!ApiManager.getInstance().setAsUnauthenticated(this)){
            Toast toast = Toast.makeText(this,activity.getString(R.string.failed_login), Toast.LENGTH_SHORT);
            toast.show();
        }else{
            Intent intent = new Intent(activity, MainMenu.class);
            activity.startActivity(intent);
            activity.finish();
        }

    }




}
