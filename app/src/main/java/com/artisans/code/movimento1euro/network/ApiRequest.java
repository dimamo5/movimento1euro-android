package com.artisans.code.movimento1euro.network;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by duarte on 16-11-2016.
 */

public abstract class ApiRequest extends AsyncTask<String, Void, JSONObject> {
    protected String urlString;
    protected Map<String, String> parametersMap = new HashMap<>();
    protected Context context;
    protected ConnectionBuilder.Request method = ConnectionBuilder.Request.POST;

    public ApiRequest(Context context) {
        this.context = context;
    }

    @Nullable
    protected JSONObject executeRequest() {
        JSONObject result = null;


        try {
            URL url = new URL(urlString);
            HttpURLConnection request = ConnectionBuilder.buildConnection(url, method,context.getSharedPreferences("userInfo",0).getString("token", ""),parametersMap);


            InputStream in =new BufferedInputStream(request.getInputStream());

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            result = new JSONObject(responseStrBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public ConnectionBuilder.Request getMethod() {
        return method;
    }

    public void setMethod(ConnectionBuilder.Request method) {
        this.method = method;
    }
}
