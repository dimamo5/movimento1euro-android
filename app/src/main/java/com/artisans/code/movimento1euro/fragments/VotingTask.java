package com.artisans.code.movimento1euro.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sergi on 21/12/2016.
 */

public class VotingTask extends AsyncTask<String, Void, JSONObject> {

    private Activity activity;

    public VotingTask(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {
        String idCause = null, idVote = null;

        if (parameters != null && parameters.length == 2) {
            idCause = parameters[0];
            idVote = parameters[1];
        } else {
            Log.e("Voting", "Exception: " + "No idCause provided.");
        }

        // Preparation of variables for the request and response handling
        HttpResponse<String> response = null;
        JSONObject result = null;

        SharedPreferences userDetails = activity.getSharedPreferences("userInfo", MODE_PRIVATE);
        String token = userDetails.getString("token", "");

        // API Request
        try {
            String url = activity.getResources().getString(R.string.api_server_url) + activity.getResources().getString(R.string.vote_in_cause_path) + "/" + idVote + "/" + idCause;
            response = Unirest.post(url)
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .header("Authorization", token)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        try {
            result = new JSONObject(response != null ? response.getBody() : null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            if (result == null)
                throw new Exception("Ocorreu um erro ao realizar o pedido de votação");

            if (result.get("result").equals(R.string.api_success_response)) {
                Toast.makeText(activity, activity.getResources().getString(R.string.vote_cause_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, activity.getResources().getString(R.string.user_vote_error), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
