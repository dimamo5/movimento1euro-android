package com.artisans.code.movimento1euro.menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.artisans.code.movimento1euro.network.ApiRequest;
import com.artisans.code.movimento1euro.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.artisans.code.movimento1euro.network.ApiManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private final String REGISTRATION_URL = "http://movimento1euro.com/inscreva-se-aqui";
    EditText inputEmail;
    EditText inputPassword;
    AppCompatActivity activity = this;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    public enum LoginType {
        STANDARD,
        FACEBOOK
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.login_screen);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);

        // TODO: 13-11-2016 Remover isto antes de entregar
        inputEmail.setText("diogo@cenas.pt");
        inputPassword.setText("123");

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
                Log.e("userID", token.getUserId());
                Log.e("token", token.getToken());
                /*Toast toast = Toast.makeText(activity,token.getUserId(), Toast.LENGTH_SHORT);
                toast.show();*/
                new LoginTask(LoginType.FACEBOOK).execute(token.getUserId(), token.getToken());
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
            new LoginTask(LoginType.STANDARD).execute(email,password);
        } else {
            Toast toast = Toast.makeText(this,"Failed Connection", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class LoginTask extends ApiRequest{
        LoginType type;

        public LoginTask(LoginType type){
            super();
            this.type = type;
        }

        @Override
        protected JSONObject doInBackground(String... parameters) {

            switch (type){
                case STANDARD:
                    urlString = getResources().getString(R.string.api_server_url) + getResources().getString(R.string.std_login_path);
                    parametersMap.put("mail", parameters[0]);
                    parametersMap.put("password", parameters[1]);
                    break;
                case FACEBOOK:
                    urlString = getResources().getString(R.string.api_server_url) + getResources().getString(R.string.fb_login_path);
                    parametersMap.put("id", parameters[0]);
                    parametersMap.put("token",parameters[1]);
                    break;
                default:
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
            }


            JSONObject result = executeRequest();

            return result;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                if(result == null
                        || result.getString("result").equals("login failed")
                        || result.getString("result").equals("wrong params")
                        || result.getString("result").equals("error")){
                    Toast.makeText(activity.getApplicationContext(), "Failed Login", Toast.LENGTH_SHORT).show();
                    LoginManager.getInstance().logOut();
                    return;
                }

                String token = result.getString("token");
                long id = result.getLong("id");
                String name = result.getString("name");
                String expirationDateStr = result.getString("expDate");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                Date expDate = simpleDateFormat.parse(expirationDateStr);

                saveLoginInfo(token, id, name, expDate);
                Intent intent = new Intent(activity, MainMenu.class);
                startActivity(intent);
                activity.finish();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activity.getApplicationContext(), "Failed Login", Toast.LENGTH_SHORT).show();
                LoginManager.getInstance().logOut();
                return;
            } catch (ParseException e) {
                LoginManager.getInstance().logOut();
                e.printStackTrace();
            }
        }

        private void saveLoginInfo(String token, long id, String name, Date expDate) {
            SharedPreferences loginInfo = getSharedPreferences("userInfo",MODE_PRIVATE);
            SharedPreferences.Editor editor = loginInfo.edit();
            editor.putString("token", token);
            editor.putLong("id", id);
            editor.putString("username", name);
            editor.putString("expDate",expDate.toString());
            editor.commit();

            ApiManager.getInstance().updateFirebaseToken();
        }
    }


}
