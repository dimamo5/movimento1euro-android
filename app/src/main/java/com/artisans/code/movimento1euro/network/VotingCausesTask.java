package com.artisans.code.movimento1euro.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.fragments.VotingCausesFragment;
import com.artisans.code.movimento1euro.models.Cause;
import com.artisans.code.movimento1euro.models.Election;
import com.artisans.code.movimento1euro.models.VotingCause;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Duart on 29/12/2016.
 */

public class VotingCausesTask extends ApiRequestTask {
    VotingCausesFragment fragment;
    ArrayList<Cause> causesList;
    ArrayList<HashMap<String, String>> list;
    public VotingCausesTask(VotingCausesFragment fragment) {
        super(fragment.getContext());
        this.fragment = fragment;
        this.causesList = fragment.getCausesList();
        this.list = fragment.getList();
        this.method = Request.GET;
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {

        HttpResponse<String> response = null;
        JSONObject result = new JSONObject();
        String token = "";

        /*token = ApiManager.getInstance().getAppToken(context);

        try {
            response = Unirest.get(context.getString(R.string.api_server_url) + context.getString(R.string.voting_causes_path))
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .header("Authorization", token)
                    .asString();
        } catch (UnirestException e) {
            Log.e("API", "Bad request");
        }*/

        try {
            /*if (response == null) {
                checkConnectivity();
            }*/

//            JSONObject obj = new JSONObject(response.getBody());
            urlString = context.getString(R.string.api_server_url) + context.getString(R.string.voting_causes_path);

            JSONObject obj = executeRequest();
            if(obj == null){
                checkConnectivity();
            }

            if (!obj.getString("result").equals(context.getString(R.string.api_success_response)))
                throw new Exception(context.getString(R.string.user_loading_authetication_error));

            JSONArray votingCauses = obj.getJSONArray("votacao");
            Election election = new Election(obj);
            VotingCause cause;
            causesList.clear();
            for (int j = 0; j < votingCauses.length(); j++) {
                //TODO discarded useful information?
                JSONArray arr = votingCauses.getJSONObject(j).getJSONArray("causas");
                for (int i = 0; i < arr.length(); i++) {
                    cause = new VotingCause(arr.getJSONObject(i));
                    cause.setElection(election);
                    causesList.add(cause);
                }
            }

            fragment.updateAdapterList(causesList, list);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("causes", "JSONException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                result.put("error", true);
                result.put("errorMessage", e.getMessage());
            } catch (Exception b) {
                b.printStackTrace();
                Log.e("causes", "Exception: " + b.getMessage());
            }
        }

        return result;
    }


    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(JSONObject result) {


        try {
            if (result != null) {  // RESULT != NULL MEANS THERE WAS AN ERROR
                String message = "";

                if(result.has("errorMessage")){
                    message = result.getString("errorMessage");
                }

                if (result.has("error"))
                    Toast.makeText(fragment.getContext(), message, Toast.LENGTH_SHORT).show();


            }
        } catch (Exception b) {
            b.printStackTrace();
        }
        fragment.notifyChanges();
    }
}