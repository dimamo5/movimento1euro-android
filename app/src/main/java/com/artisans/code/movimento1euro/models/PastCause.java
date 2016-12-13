package com.artisans.code.movimento1euro.models;

import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import static com.artisans.code.movimento1euro.models.Cause.Constants.DOCUMENTS_ARRAY_COLUMN;

/**
 * Created by Duart on 13/12/2016.
 */

public class PastCause extends Cause {


    public PastCause(JSONObject json) {
        super(json);

        //// TODO: 13/12/2016 ParseDocuments
    }

    protected static ArrayList<Pair<URL,String>> parseDocuments(JSONObject object){
        /*try{

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Association exception", "Error parsing JSON");
        }*/

        return null;
    }
}
