package com.artisans.code.movimento1euro;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by duarte on 30-10-2016.
 */

public class PostBuilder {

    public static HttpURLConnection buildConnection(URL url, Map<String, String> parameters) throws IOException {
        String parametersStr = createQueryStringForParameters(parameters);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();

        request.addRequestProperty("Content-Length", Integer.toString(parametersStr.length()));
        request.addRequestProperty("Content-Type", "application/json");
        request.setRequestMethod("POST");
        request.setDoInput(true);
        request.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(request.getOutputStream());
        out.write(parametersStr);
        out.close();

        return request;
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
