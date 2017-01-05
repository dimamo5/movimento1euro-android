package com.artisans.code.movimento1euro.network;
import android.content.SharedPreferences;
import android.util.Log;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.SharedPreferencesNames;
import com.artisans.code.movimento1euro.menus.MainMenu;
import com.mashape.unirest.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Filipe on 04/01/2017.
 */

public class UserInfoUpdateTask extends ApiRequestTask{

    public static final String TAG = UserInfoUpdateTask.class.getSimpleName();

    MainMenu activity;


    public UserInfoUpdateTask(MainMenu activity) {
        super(activity);
        setMethod(Request.GET);
        this.activity = activity;

    }

    @Override
    protected JSONObject doInBackground(String... parameters) {

        // Preparation of variables for the request and response handling
        HttpResponse<String> response = null;
        JSONObject result = new JSONObject();

        urlString = context.getString(R.string.api_server_url)+context.getString(R.string.update_user_info);

        try {

            JSONObject obj = executeRequest();

            //APAGUEI O AVISO DE CONECTIVIDADE POIS ACHEI QUE NÃO FAZIA SENTIDO MOSTAR UM AVISO DE NÃO HAVER INTERNET QUANDO É UM PEDIDO FEITO NO BACKGROUND- Duarte
            if(obj == null){
                //checkConnectivity();
                Log.d(TAG, "Null return");
                return null;
            }

            /*if (!obj.getString("result").equals(context.getString(R.string.api_success_response)))
                throw new Exception(context.getString(R.string.user_loading_authetication_error));*/

            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(JSONObject result) {

        try {
            Log.d(TAG, result.toString());
            String token = result.getString("token");
            long id = result.getLong("id");
            String name = result.getString("name");
            String expirationDateStr = result.getString("expDate");
            SimpleDateFormat simpleDateFormat = ApiManager.getInstance().getExpirationSimpleDateFormat();
            Date expDate = simpleDateFormat.parse(expirationDateStr);
            ApiManager.getInstance().saveLoginInfo(context, token, id, name, expDate);
            ApiManager.getInstance().updateFirebaseToken(activity.getApplicationContext());

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        activity.showExpirationDateAlert();
    }
}