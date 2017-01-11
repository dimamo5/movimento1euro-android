package com.artisans.code.movimento1euro.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.SharedPreferencesNames;
import com.artisans.code.movimento1euro.models.VotingCause;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

/**
 * Util class with methods for interacting with the API
 */
public class ApiManager {
    public final static String TAG = ApiManager.class.getSimpleName();

    public final static String UNAUTHENTICATED_FLAG = "UNAUTHENTICATED_USER";
    public final static long UNAUTHENTICATED_ID = Integer.MAX_VALUE;

    private static ApiManager ourInstance = new ApiManager();

    public static ApiManager getInstance() {
        return ourInstance;
    }

    private ApiManager() {
    }

    /**
     * Send request with the new firebase token
     * @param context
     */
    public void updateFirebaseToken(Context context){
        String token = FirebaseInstanceId.getInstance().getToken();
        new UpdateFirebaseTokenTask(context).execute(token);
    }

    /**
     * Send vote request to the api
     * @param context
     * @param cause
     */
    public void vote(Context context, VotingCause cause){
        new VotingTask(context).execute(cause.getElection().getId()+"", cause.getId()+"");
    }

    /**
     * Verify if the user is Authenticated
     * @param context
     * @return
     */
    public boolean isAuthenticated(Context context){
        String token = getAppToken(context);
        if(token==null || token.equals(UNAUTHENTICATED_FLAG)){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Get user's authorization token used to authenticate in the API
     * @param context
     * @return
     */
    public String getAppToken(Context context){
        try {
            return context.getSharedPreferences(SharedPreferencesNames.USER_INFO,MODE_PRIVATE).getString("token", "");
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Update SharedPreferences with information that correspond to the unauthenticated status
     * @param context
     * @return true if successful, false if there was an error
     */
    public boolean setAsUnauthenticated(Context context) {
        String token = UNAUTHENTICATED_FLAG;
        long id = UNAUTHENTICATED_ID;
        String name = UNAUTHENTICATED_FLAG;
        try {
            Date expDate = new Date(Long.MAX_VALUE);
            saveLoginInfo(context,token,id, name,expDate);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Save the information associated with the login in the SharedPreferences
     * @param context
     * @param token
     * @param id
     * @param name
     * @param expDate
     */
    public void saveLoginInfo(Context context, String token, long id, String name, Date expDate) {
        SharedPreferences loginInfo = context.getSharedPreferences(SharedPreferencesNames.USER_INFO,MODE_PRIVATE);
        SharedPreferences.Editor editor = loginInfo.edit();
        editor.putString("token", token);
        editor.putLong("id", id);
        editor.putString("username", name);
        editor.putString("expDate",expDate.toString());
        editor.commit();
    }

    /**
     * Update the expiration alert settings
     * @param context
     * @param expirationAlertActive
     * @param daysToWarn
     * @param title
     * @param errorMessage
     */
    public void updateExpirationAlertInfo(Context context, boolean expirationAlertActive, int daysToWarn, String title, String errorMessage){
        SharedPreferences settings = context.getSharedPreferences(SharedPreferencesNames.SETTINGS,MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("expirationAlertActive", expirationAlertActive);
        editor.putInt("daysToWarn", daysToWarn);
        editor.putString("expirationAlertTitle",title);
        editor.putString("expirationAlertMessage", errorMessage);
        editor.commit();
    }

    /**
     * Get number of days after which the expiration alert is hurled
     * @param context
     * @return
     */
    public int getDaysToWarn(Context context){
        return context.getSharedPreferences(SharedPreferencesNames.SETTINGS,MODE_PRIVATE).getInt("daysToWarn", 0);
    }

    /**
     * Get the Title to be used in the expiration alert
     * @param context
     * @return
     */
    public String getExpirationAlertTitle(Context context){
        return context.getSharedPreferences(SharedPreferencesNames.SETTINGS,MODE_PRIVATE).getString("expirationAlertTitle", "");
    }

    /**
     * Get the message to be used in the expiration alert
     * @param context
     * @return
     */
    public String getExpirationAlertMessage(Context context){
        return context.getSharedPreferences(SharedPreferencesNames.SETTINGS,MODE_PRIVATE).getString("expirationAlertMessage", "");
    }

    /**
     * Assert if the expiration alert is active
     * @param context
     * @return
     */
    public boolean isExpirationAlertActive(Context context){
        return context.getSharedPreferences(SharedPreferencesNames.SETTINGS,MODE_PRIVATE).getBoolean("expirationAlertActive", false);
    }

    /**
     * Get the user's subscription expiration date
     * @param context
     * @return
     */
    public Date getExpirationDate(Context context){
        return new Date(context.getSharedPreferences(SharedPreferencesNames.USER_INFO,0).getString("expDate", ""));
    }

    @NonNull
    public SimpleDateFormat getExpirationSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat;
    }
}
