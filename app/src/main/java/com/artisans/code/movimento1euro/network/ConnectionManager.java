package com.artisans.code.movimento1euro.network;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duarte on 16-11-2016.
 */
public class ConnectionManager {
    private static ConnectionManager ourInstance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return ourInstance;
    }

    private ConnectionManager() {
    }

    private class UpdateFirebaseTokenTask extends ApiRequest{

        @Override
        protected JSONObject doInBackground(String... strings) {



            JSONObject result = executeRequest();

            return result;
        }

    }
}
