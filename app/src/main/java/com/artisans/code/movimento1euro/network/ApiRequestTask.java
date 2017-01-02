package com.artisans.code.movimento1euro.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artisans.code.movimento1euro.R;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.HttpRequest;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.artisans.code.movimento1euro.network.ApiRequestTask.Request.GET;
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

    protected JSONObject checkConnectivity() throws Exception {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        String connectionError = context.getString(R.string.user_connection_error);
        String requestError =context.getString(R.string.causes_request_error);

        String error = isConnected ? requestError : connectionError;

        throw new Exception(error);
    }

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

        if(this.method.equals(GET)){
            return executeGetRequest();
        }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject executeGetRequest() {
        JSONObject retObj;
        HttpRequest request;
        HttpResponse<String> response;
        String token = ApiManager.getInstance().getAppToken(context);
        String url = urlString;
        url = addParametersToGetUrl(url);

        try {
            request = Unirest.get(url)
                    .header("accept", "application/json")
                    .header("content-type", "application/json");

            if(ApiManager.getInstance().isAuthenticated(context)){
               request.header("Authorization", token);
            }else{
                request.header("Authorization", token); // TODO: 30/12/2016 Remover esta linha quando o servidor suportar não recever header Authorization
            }
            response = request.asString();
            retObj = new JSONObject(response.getBody());
            return retObj;
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String addParametersToGetUrl(String url){
        Set<String> keys = parametersMap.keySet();
        String value;
        int i = 0;
        for (String key:
             keys) {
            value = parametersMap.get(key);
            if(i== 0){
                url += "?";
            }else {
                url += "&";
            }
            url += key + "="+value;
            i++;
        }
        return url;
    }

    public Request getMethod() {
        return method;
    }

    public void setMethod(Request method) {
        this.method = method;
    }
}
