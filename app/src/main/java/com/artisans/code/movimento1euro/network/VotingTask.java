package com.artisans.code.movimento1euro.network;

import android.content.Context;
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
 * Asynchronous task to send a user's vote to the server. Must receive the ID of the voting and the ID of the cause in which the user will vote.
 * Both ID's must be passed in the following order ex: new VotingTask(context).execute(idVote,idCause)
 */

public class VotingTask extends ApiRequestTask{

    public VotingTask(Context context) {
        super(context);
        urlString = context.getString(R.string.api_server_url)+context.getString(R.string.vote_in_cause_path);
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {
        String idCause,idVote;

        if (parameters != null && parameters.length == 2) {
            parametersMap.put("idVote", parameters[0]);
            parametersMap.put("idCause", parameters[1]);
        } else {
            Log.e("Voting", "Exception: " + "Wrong params.");
            return null;
        }

        return executeRequest();
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            if (result == null) {
                Toast.makeText(context, context.getResources().getString(R.string.unkown_vote_error), Toast.LENGTH_LONG).show();
                return;
            }
            if (result.get("result").equals(R.string.api_success_response)) {
                Toast.makeText(context, context.getResources().getString(R.string.vote_cause_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.user_vote_error), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
