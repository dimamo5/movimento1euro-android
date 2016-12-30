package com.artisans.code.movimento1euro.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.models.VotingCause;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by duarte on 16-11-2016.
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

    public void updateFirebaseToken(Context context){
        String token = FirebaseInstanceId.getInstance().getToken();
        new UpdateFirebaseTokenTask(context).execute(token);
    }

    public void vote(Context context, VotingCause cause){
        new VotingTask(context).execute(cause.getElection().getId()+"", cause.getId()+"");
    }

    public boolean isAuthenticated(Context context){
        if(getAppToken(context)==null){
            return false;
        }else{
            return true;
        }
    }

    public String getAppToken(Context context){
        try {
            return context.getApplicationContext().getSharedPreferences("userInfo",0).getString("token", "");
        }
        catch(Exception e){
            return null;
        }
    }

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

    public void saveLoginInfo(Context context, String token, long id, String name, Date expDate) {
        SharedPreferences loginInfo = context.getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = loginInfo.edit();
        editor.putString("token", token);
        editor.putLong("id", id);
        editor.putString("username", name);
        editor.putString("expDate",expDate.toString());
        editor.commit();
    }
}
