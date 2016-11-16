package com.artisans.code.movimento1euro.network;

import android.content.res.Resources;
import android.util.Log;

import com.artisans.code.movimento1euro.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by duarte on 16-11-2016.
 */
public class ApiManager {
    public final static String TAG = ApiManager.class.getCanonicalName();

    private static ApiManager ourInstance = new ApiManager();

    public static ApiManager getInstance() {
        return ourInstance;
    }

    private ApiManager() {
    }

    public void updateFirebaseToken(){
        String token = FirebaseInstanceId.getInstance().getToken();
        new UpdateFirebaseTokenTask().execute(token);
    }

    private class UpdateFirebaseTokenTask extends ApiRequest{

        @Override
        protected JSONObject doInBackground(String... parameters) {
            urlString = Resources.getSystem().getString(R.string.api_server_url) + Resources.getSystem().getString(R.string.upd_firebase_token_path);
            parametersMap.put("token", parameters[0]);

            JSONObject result = executeRequest();

            return result;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                if(result == null || !result.getString("result").equals("success")) {
                    Log.e(TAG, "Token update request result: " + result.getString("result"));
                }else {
                    Log.d(TAG, "Token Update Request Successful");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
