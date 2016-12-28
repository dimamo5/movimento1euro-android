package com.artisans.code.movimento1euro.network;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artisans.code.movimento1euro.R;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;

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
import java.util.Objects;

import static com.artisans.code.movimento1euro.network.ApiRequestTask.Request.POST;
import static com.mashape.unirest.http.Unirest.post;

/**
 * Created by duarte on 16-11-2016.
 */

public abstract class ApiRequestTask extends AsyncTask<String, Void, JSONObject> {

    public final static String TAG = ApiRequestTask.class.getSimpleName();
    protected final Context context;
    protected String urlString;
    protected Map<String, String> parametersMap = new HashMap<>();
    protected Request method = POST;

    public enum Request{
        POST,
        GET,
        PUT
    }

    public ApiRequestTask(Context context){
        this.context = context;
    }

    @Nullable
    protected JSONObject executeRequest() {
        JSONObject result;
        String token;
        try {
            token = ApiManager.getInstance().getAppToken(context);
            URL url = new URL(urlString);
            HttpURLConnection request = ConnectionBuilder.buildConnection(url, method,token,parametersMap);


            InputStream in =new BufferedInputStream(request.getInputStream());

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            result = new JSONObject(responseStrBuilder.toString());
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Request getMethod() {
        return method;
    }

    public void setMethod(Request method) {
        this.method = method;
    }
}
