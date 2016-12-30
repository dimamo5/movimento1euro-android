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

    public LastCausesTask(ViewLastCausesFragment fragment) {
        super(fragment.getContext());
        this.fragment = fragment;
        this.setMethod(Request.GET);
        this.allCausesByYear = fragment.getAllCausesByYear();
    }

    public LastCausesTask(ViewLastCausesFragment fragment, boolean updateAfterRequest) {
        this(fragment);
        this.updateAfterRequest = updateAfterRequest;

    }

    @Override
    protected JSONObject doInBackground(String... parameters) {

        urlString = context.getString(R.string.api_server_url) + context.getString(R.string.winner_causes_path);
        parametersMap.put("ano",parameters[0]);
        year = parameters[0];
        JSONObject result = new JSONObject();

        try {


            JSONObject obj = executeRequest();
            if(obj == null){
                checkConnectivity();
            }
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