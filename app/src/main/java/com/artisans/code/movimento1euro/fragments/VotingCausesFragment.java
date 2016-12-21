package com.artisans.code.movimento1euro.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.menus.CausesDetailsActivity;
import com.artisans.code.movimento1euro.models.Cause;
import com.artisans.code.movimento1euro.models.VotingCause;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class VotingCausesFragment extends CauseListFragment {

    public static final List<String> MONTHS = Arrays.asList("No Month", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");


    private OnFragmentInteractionListener mListener;

    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ArrayList<Cause> causesList = new ArrayList<>();
    CauseListFragment fragment = this;


    public VotingCausesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class Constants {
        public static final String NAME_COLUMN = "Name";
        public static final String MONEY_COLUMN = "Money";
        public static final String DESCRIPTION_COLUMN = "Description";
        public static final String IMAGE_COLUMN = "Image";
    }


    private class CausesTask extends AsyncTask<String, Void, JSONObject> {

        public CausesTask() {
            super();
        }

        @Override
        protected JSONObject doInBackground(String... parameters) {

            HttpResponse<String> response = null;
            JSONObject result = new JSONObject();
            String token = "";

            try {
                SharedPreferences userDetails = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                token = userDetails.getString("token", "");
            }catch(Exception e){
                Log.d("past", e.getMessage());
            //To prevent conflicts between async tasks, if user clicks various times on the menu item
            return result;

            }

            try {
                response = Unirest.get(getResources().getString(R.string.api_server_url) + getResources().getString(R.string.voting_causes_path))
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .header("Authorization", token)
                        .asString();
            } catch (UnirestException e) {
                Log.e("API", "Bad request");
            }

            try {
                if (response == null) {
                    ConnectivityManager cm =
                            (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();

                    String connectionError = getResources().getString(R.string.user_connection_error);
                    String requestError = getResources().getString(R.string.causes_request_error);

                    String error = isConnected ? connectionError : connectionError;

                    throw new Exception(error);
                }

                JSONObject obj = new JSONObject(response.getBody());
                if (!obj.getString("result").equals(getResources().getString(R.string.api_success_response)))
                    throw new Exception(getResources().getString(R.string.user_loading_authetication_error));

                JSONArray votingCauses = obj.getJSONArray("votacao");

                causesList.clear();
                for (int j = 0; j < votingCauses.length(); j++) {
                    //TODO discarded useful information?
                    JSONArray arr = votingCauses.getJSONObject(j).getJSONArray("causas");
                    for (int i = 0; i < arr.length(); i++) {
                        causesList.add(new VotingCause(arr.getJSONObject(i)));
                    }
                }

                updateAdapterList(causesList, list);

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
                    String message = result.getString("errorMessage");
                    if (result.getBoolean("error") == true)
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception b) {
                b.printStackTrace();
            }
            notifyChanges();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_voting_causes, container, false);

        initializeListAdapter(view);
        new CausesTask().execute();

        return view;
    }

    @Override
    protected void initializeListAdapter(View view) {
        listView = (ListView) view.findViewById(R.id.causes_list);

        listAdapter = new SimpleAdapter(
                this.getContext(),
                list,
                R.layout.item_voting_cause,
                new String[]{Constants.NAME_COLUMN, Constants.DESCRIPTION_COLUMN, Constants.MONEY_COLUMN},
                //TODO ADICIONAR COLUNA DA IMAGEM? -- R.id.voting_causes_item_image
                new int[]{R.id.voting_causes_item_name, R.id.voting_causes_item_description, R.id.voting_causes_item_money}
        );

        listView.setAdapter(listAdapter);
    }

    @Override
    protected HashMap<String,String> causeToHashMap(Cause cause){
        HashMap<String, String> hashMap = new HashMap<String,String>();


        hashMap.put(Constants.NAME_COLUMN, cause.getName());
        hashMap.put(Constants.DESCRIPTION_COLUMN, cause.getDescription());
        hashMap.put(Constants.MONEY_COLUMN, "Valor da causa: " + cause.getMoney() + "€");


        return hashMap;
    }

    @Override
    public void cardClick(View view) {
        int index=listView.getPositionForView(view);
        Cause c= causesList.get(index);
       // Log.e("Cause",c.toString());
        Intent intent = new Intent(this.getActivity(), CausesDetailsActivity.class);
        intent.putExtra("Cause",causesList.get(index));
        startActivity(intent);

    }




}
