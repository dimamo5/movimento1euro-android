package com.artisans.code.movimento1euro.network;

import android.content.Context;
import android.util.Log;

import com.artisans.code.movimento1euro.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Asynchronous task to send (and update) the new Firebase token to the server.
 * The firebase token must be passed like ex: new UpdateFirebaseTokenTask(context).execute(firebaseToken)
 */

public class UpdateFirebaseTokenTask extends ApiRequestTask {
    public final static String TAG = UpdateFirebaseTokenTask.class.getSimpleName();

    public UpdateFirebaseTokenTask(Context context) {
        super(context);
        this.setMethod(Request.PUT);
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {
        urlString = context.getString(R.string.api_server_url) + context.getString(R.string.upd_firebase_token_path);
        parametersMap.put("firebaseToken", parameters[0]);

        JSONObject result = executeRequest();

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            if(result == null || !result.getString("result").equals("success")) {
                if(result != null)
                    Log.e(TAG, "Token update request failed. Result: " + result);
                else
                    Log.e(TAG, "Token update request Failed");
            }else {
                Log.d(TAG, "Token Update Request Successful");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}