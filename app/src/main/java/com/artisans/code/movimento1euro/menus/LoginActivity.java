package com.artisans.code.movimento1euro.menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.artisans.code.movimento1euro.PostBuilder;
import com.artisans.code.movimento1euro.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private final String REGISTRATION_URL = "http://movimento1euro.com/inscreva-se-aqui";
    EditText inputEmail;
    EditText inputPassword;
    AppCompatActivity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);

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
                login(view);
            }
        });
    }


    public void login(View view) {
        // Gets the URL from the UI's text field.
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new LoginTask().execute(email,password);
        } else {
            Toast toast = Toast.makeText(this,"Failed Connection", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class LoginTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... parameters) {
            String urlString = getResources().getString(R.string.api_server_url) + getResources().getString(R.string.login_path);
            Map<String, String> parametersMap = new HashMap<>();
            parametersMap.put("mail", parameters[0]);
            parametersMap.put("password", parameters[1]);
            JSONObject result = null;

            try {
                URL url = new URL(urlString);
                Log.e("url", url.toString());
                HttpURLConnection request = PostBuilder.buildConnection(url, parametersMap);


                BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

                char[] buffer = new char[1024];

                String jsonString = new String();

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();

                jsonString = sb.toString();
                Log.e("jsonString", jsonString);
                result = new JSONObject(jsonString);
                Log.e("Result", result.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return result;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                if(result == null || result.getString("result").equals("failed")){
                    Toast.makeText(activity.getApplicationContext(), "Failed Login", Toast.LENGTH_SHORT).show();
                    return;
                }

                String token = result.getString("token");
                long id = result.getLong("id");
                String name = result.getString("name");

                SharedPreferences loginInfo = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = loginInfo.edit();
                editor.putString("token", token);
                editor.putLong("id", id);
                editor.putString("name", name);

                Intent intent = new Intent(activity, MainMenu.class);
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activity.getApplicationContext(), "Failed Login", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}
