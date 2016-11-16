package com.artisans.code.movimento1euro.network;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by duarte on 30-10-2016.
 */

public class PostBuilder {

    public static HttpURLConnection buildConnection(URL url, Map<String, String> parameters) throws IOException {
        String parametersStr = createQueryStringForParameters(parameters);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setReadTimeout(10000);
        request.setConnectTimeout(15000);
        request.setRequestMethod("POST");
        request.setDoInput(true);
        request.setDoOutput(true);
        request.setChunkedStreamingMode(0);
        request.addRequestProperty("Content-Type", "application/json");

        request.connect();
        Log.e("parametersStr", parametersStr);
        OutputStreamWriter out = new OutputStreamWriter(request.getOutputStream());

        JSONObject sendObject = getJsonObject(parameters);
        out.write(sendObject.toString());
        Log.e("sendObject", sendObject.toString());

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
