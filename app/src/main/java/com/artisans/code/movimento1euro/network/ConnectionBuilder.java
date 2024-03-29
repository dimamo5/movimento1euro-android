package com.artisans.code.movimento1euro.network;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Util class used for building the requests used by ApiRequestTask (except for the GET requests).
 */

public class ConnectionBuilder {
    public static final String TAG = ConnectionBuilder.class.getSimpleName();


    /**
     * Builds the connection and adds the parameters as json to the request.
     * @param url url to where the request will be sent
     * @param requestMethod type of method
     * @param token api token to authenticate
     * @param parameters map<parameterName, parameter>
     * @return
     * @throws IOException
     */
    public static HttpURLConnection buildConnection(URL url, ApiRequestTask.Request requestMethod, String token , Map<String, String> parameters) throws IOException {
        String parametersStr = createQueryStringForParameters(parameters);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setReadTimeout(10000);
        request.setConnectTimeout(15000);
        request.setDoInput(true);
        request.setDoOutput(true);
        request.setRequestMethod(requestMethod.toString());
        request.setChunkedStreamingMode(0);
        request.addRequestProperty("Content-Type", "application/json");
        request.addRequestProperty("Authorization", token);


        request.connect();
        Log.d(TAG,"parametersStr: " + parametersStr);/*
        Log.d(TAG, "Authorization token: " + token);*/
        OutputStreamWriter out = new OutputStreamWriter(request.getOutputStream());

        JSONObject sendObject = getJsonObject(parameters);
        out.write(sendObject.toString());
        /*Log.d("sendObject", sendObject.toString());*/

        out.flush();
        out.close();
        return request;


    }

    @NonNull
    private static JSONObject getJsonObject(Map<String, String> parameters) {
        JSONObject sendObject = new JSONObject();
        for (String key:parameters.keySet()) {
            try {
                sendObject.put(key, parameters.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sendObject;
    }

    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';
    public static String createQueryStringForParameters(Map<String, String> parameters) {
        StringBuilder parametersAsQueryString = new StringBuilder();
        if (parameters != null) {
            boolean firstParameter = true;

            for (String parameterName : parameters.keySet()) {
                if (!firstParameter) {
                    parametersAsQueryString.append(PARAMETER_DELIMITER);
                }

                parametersAsQueryString.append(parameterName)
                        .append(PARAMETER_EQUALS_CHAR)
                        .append(URLEncoder.encode(
                                parameters.get(parameterName)));

                firstParameter = false;
            }
        }
        return parametersAsQueryString.toString();
    }
}
