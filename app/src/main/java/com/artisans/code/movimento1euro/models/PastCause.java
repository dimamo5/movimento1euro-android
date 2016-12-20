package com.artisans.code.movimento1euro.models;

import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.artisans.code.movimento1euro.models.JSONFields.INTRODUCTION_COLUMN;

/**
 * Created by Duart on 13/12/2016.
 */

public class PastCause extends Cause {


    public PastCause(JSONObject json) {
        super(json);

        try {
            this.name = json.getString(JSONFields.PAST_NAME_COLUMN);
            this.introduction = json.getString(INTRODUCTION_COLUMN);
            this.documents = parseDocuments(json.getJSONArray(JSONFields.DOCUMENTS_ARRAY_COLUMN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected static ArrayList<Pair<URL,String>> parseDocuments(JSONArray array){
        ArrayList<Pair<URL,String>> documents = new ArrayList<>();

        JSONObject docObject;
        URL url;
        String description;
        Pair<URL, String> doc;

        try{
            for (int i = 0; i< array.length(); i++) {
                docObject = array.getJSONObject(i);
                url = new URL(docObject.getString(JSONFields.DOCUMENTS_URL_COLUMN));
                description = docObject.getString(JSONFields.DOCUMENTS_DESCRIPTION_COLUMN);
                doc = new Pair<>(url,description);
                documents.add(doc);
            }

            return documents;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Association exception", "Error parsing JSON");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
