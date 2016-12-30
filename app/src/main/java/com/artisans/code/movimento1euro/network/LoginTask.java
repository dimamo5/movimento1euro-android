package com.artisans.code.movimento1euro.network;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.menus.MainMenu;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Duart on 28/12/2016.
 */
public class LoginTask extends ApiRequestTask {
    public enum LoginType {
        STANDARD,
        FACEBOOK,
        UNAUTHENTICATED
    }

    LoginType type;
    Activity activity;

    public LoginTask(Activity activity, LoginType type){
        super(activity.getApplicationContext());
        this.activity = activity;
        this.type = type;
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {

        switch (type){
            case STANDARD:
                urlString = activity.getResources().getString(R.string.api_server_url) + activity.getResources().getString(R.string.std_login_path);
                parametersMap.put("mail", parameters[0]);
                parametersMap.put("password", parameters[1]);
                break;
            case FACEBOOK:
                urlString = activity.getResources().getString(R.string.api_server_url) + activity.getResources().getString(R.string.fb_login_path);
                parametersMap.put("id", parameters[0]);
                parametersMap.put("token",parameters[1]);
                //this.method = Request.GET;
                break;
            case UNAUTHENTICATED:
                urlString = activity.getResources().getString(R.string.api_server_url) + activity.getResources().getString(R.string.unauth_login_path);
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

                if(result.getString("result").equals("wrong params")){
                    Toast.makeText(context.getApplicationContext(), activity.getString(R.string.wrong_parameters), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, activity.getString(R.string.failed_login), Toast.LENGTH_SHORT).show();
                }
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
            ApiManager.getInstance().updateFirebaseToken(activity.getApplicationContext());
            Intent intent = new Intent(activity, MainMenu.class);
            activity.startActivity(intent);
            activity.finish();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.failed_login), Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logOut();
            return;
        } catch (ParseException e) {
            LoginManager.getInstance().logOut();
            e.printStackTrace();
        }
    }

    private void saveLoginInfo(String token, long id, String name, Date expDate) {
        SharedPreferences loginInfo = activity.getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = loginInfo.edit();
        editor.putString("token", token);
        editor.putLong("id", id);
        editor.putString("username", name);
        editor.putString("expDate",expDate.toString());
        editor.commit();
    }
}