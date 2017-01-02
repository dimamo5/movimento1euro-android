package com.artisans.code.movimento1euro.network;

import android.content.Context;

import com.artisans.code.movimento1euro.R;
import com.mashape.unirest.http.HttpResponse;

import org.json.JSONObject;

/**
 * Created by sergi on 02/01/2017.
 */

public class SeenNotificationTask extends ApiRequestTask {

    public SeenNotificationTask(Context context) {
        super(context);
        setMethod(Request.PUT);
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        // Preparation of variables for the request and response handling
        JSONObject result;

        urlString = context.getString(R.string.api_server_url)+context.getString(R.string.seen_notification_path);
        parametersMap.put("notificationID", params[0]);

        result = executeRequest();
        return result;
    }
}
