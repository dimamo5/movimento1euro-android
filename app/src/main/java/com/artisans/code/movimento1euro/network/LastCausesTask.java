package com.artisans.code.movimento1euro.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.fragments.ViewLastCausesFragment;
import com.artisans.code.movimento1euro.models.Election;
import com.artisans.code.movimento1euro.models.PastCause;
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
 * Created by Duart on 28/12/2016.
 */

public class LastCausesTask extends ApiRequestTask{

    String year;
    boolean updateAfterRequest = false;
    ViewLastCausesFragment fragment;
    HashMap<String, ArrayList<PastCause>> allCausesByYear;

    public LastCausesTask(ViewLastCausesFragment fragment, HashMap<String, ArrayList<PastCause>> allCausesByYear) {
        super(fragment.getContext());
        this.fragment = fragment;
        this.setMethod(Request.GET);
        this.allCausesByYear = allCausesByYear;
    }

    public LastCausesTask(ViewLastCausesFragment fragment, HashMap<String, ArrayList<PastCause>> allCausesByYear, boolean updateAfterRequest) {
        super(fragment.getContext());
        this.setMethod(Request.GET);
        this.fragment = fragment;
        this.allCausesByYear = allCausesByYear;
        this.updateAfterRequest = updateAfterRequest;

    }

    @Override
    protected JSONObject doInBackground(String... parameters) {

        urlString = context.getString(R.string.api_server_url) + context.getString(R.string.winner_causes_path);
        parametersMap.put("ano",parameters[0]);
        year = parameters[0];
        JSONObject result = new JSONObject();

        // Preparation of variables for the request and response handling
        HttpResponse<String> response = null;
        String token = "";
        try {
            SharedPreferences userDetails = context.getSharedPreferences("userInfo", MODE_PRIVATE);
            token = userDetails.getString("token", "");
        }catch(Exception e){
            e.printStackTrace();
            //To prevent conflicts between async tasks, if user clicks various times on the menu item
            return result;

        }
        // API Request
        try {
            //// TODO: 21/12/2016 Remove hardcoded fields - use R.string(..)
            response = Unirest.get(context.getString(R.string.api_server_url) + context.getString(R.string.winner_causes_path) + "?ano=" + year)
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .header("Authorization", token)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        try {

            if (response == null) {
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                String connectionError = context.getString(R.string.user_connection_error);
                String requestError = context.getString(R.string.causes_request_error);

                String error = isConnected ? requestError : connectionError;

                throw new Exception(error);
            }

            JSONObject obj = new JSONObject(response.getBody());;

            if (!obj.getString("result").equals(context.getString(R.string.api_success_response)))
                throw new Exception(context.getString(R.string.user_loading_authetication_error));

            // Get Yearly elections array from response
            JSONArray yearlyElections = obj.getJSONArray("causes");
            int totalElectionNr = yearlyElections.length();


            ArrayList<PastCause> requestedCauses = new ArrayList<PastCause>();

            for (int electionNr = 0; electionNr < totalElectionNr; electionNr++) {

                // Get each election's winning causes and add them
                JSONObject electionObject = yearlyElections.getJSONObject(electionNr);
                JSONArray winningCauses = electionObject.getJSONArray("causas");
                int totalCausesNr = winningCauses.length();

                Election election = new Election(electionObject);

                for (int causeNr = 0; causeNr < totalCausesNr; causeNr++) {

                    JSONObject cause = winningCauses.getJSONObject(causeNr);

                    //HashMap<String, String> tempCause = new HashMap<String, String>();
                    //add titulo and montante to the cause object
                    cause.put("titulo", electionObject.getString("titulo"));
                    cause.put("montante_disponivel", electionObject.getString("montante_disponivel"));
                    PastCause tempCause = new PastCause(cause);
                    tempCause.setElection(election);


                    requestedCauses.add(tempCause);
                }
            }

            allCausesByYear.put(year, requestedCauses);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

            // Handle error
            try {
                if(updateAfterRequest) {

                    result.put("error", true);
                    result.put("errorMessage", e.getMessage());

                }
            }catch(Exception b){
                b.printStackTrace();
            }
        }
        // int code = response.getCode();
        // Map<String, String> headers = response.getHeaders();
        //InputStream rawBody = response.getRawBody();

        return result;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(JSONObject result) {

        try {
            if (result != null) {
                String message = "";
                if(result.has("errorMessage")){
                     message = result.getString("errorMessage");
                }

                //only shows toast for the request which was to update screen, in case several requests are made
                if (result.has("error") == true && updateAfterRequest) {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        } catch (Exception b) {
            b.printStackTrace();
        }

        if(updateAfterRequest) {
            Log.d(TAG, "Doing update");
            fragment.getSpinnerAdapter().notifyDataSetChanged();
            fragment.updateFromSpinner(year);
        }

    }
}