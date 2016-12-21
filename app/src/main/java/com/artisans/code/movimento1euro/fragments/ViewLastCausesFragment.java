package com.artisans.code.movimento1euro.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.menus.PastCauseDetailsActivity;
import com.artisans.code.movimento1euro.models.Cause;
import com.artisans.code.movimento1euro.models.Election;
import com.artisans.code.movimento1euro.models.PastCause;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewLastCausesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewLastCausesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewLastCausesFragment extends CauseListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    */
    public final static String requestError = "";
    public final static String connectionError = "";
    public final static String TAG = ViewLastCausesFragment.class.getCanonicalName();
    public static final List<String> MONTHS = Arrays.asList("No Month", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
    public static final int START_YEAR = 2011;
    // TODO: Rename and change types of parameters
    /*
    private String mParam1;
    private String mParam2;
    */


    HashMap<String, ArrayList<PastCause>> allCausesByYear = new HashMap<>();
    ArrayList<String> yearsList = new ArrayList<String>();
    ArrayList<Cause> shownCauseslist = new ArrayList<>();



    public ViewLastCausesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewLastCausesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewLastCausesFragment newInstance(String param1, String param2) {
        ViewLastCausesFragment fragment = new ViewLastCausesFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // mParam1 = getArguments().getString(ARG_PARAM1);
            // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner_nav);
        spinner.setVisibility(View.GONE);
    }

    public class Constants {
        public static final String TITLE_COLUMN = "Title";
        public static final String ELECTION_TITLE_COLUMN = "Election_Title";
        public static final String MONEY_COLUMN = "Money";

    }




    public void updateFromSpinner(String year) {

        if(allCausesByYear.get(year) != null) {

            shownCauseslist.clear();
            shownCauseslist.addAll(allCausesByYear.get(year));

            updateAdapterList(shownCauseslist,list);
            //Log.d(TAG, "size-list: "+list.size());

        }else{
            // Mostrar no ecrã que não há causas para este período(ano)
        }
        //Log.d("past", list.toString());
        notifyChanges();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_winners, container, false);

        fillYearsList();
        createSpinnerOnActivityBar();
        initializeListAdapter(view);

        // API request -> with the first year in the list, which should currently be selected
        // Maybe execute all already, to store them
        new CausesTask(true).execute(yearsList.get(0));
        //Log.d("past", "Year to be executed: " + yearsList.get(0));
        for(int i = 1; i< yearsList.size(); i++) {
            new CausesTask(false).execute(yearsList.get(i));
        }
        return view;
    }

    protected void fillYearsList() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(currentYear >= START_YEAR) {
            for (int year = currentYear; year >= START_YEAR; year--) {
                yearsList.add(Integer.toString(year));
            }
        }else{
            //Log.d("past", Integer.toString(currentYear) + "is  <  than " + Integer.toString(START_YEAR));
            yearsList.add(Integer.toString(START_YEAR));
        }
    }

    // SPINNER - Create Spinner on ACTIVITY BAR + define adapter, functions and spinner items
    protected void createSpinnerOnActivityBar() {
        spinner = (Spinner) getActivity().findViewById(R.id.spinner_nav);
        spinner.setVisibility(View.VISIBLE);

        spinnerAdapter = new ArrayAdapter<String>(this.getContext(),
                R.layout.spinner_previous_winners_selected_year, yearsList);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_previous_winners_year);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO spinner click

                // Old version: got values for the year when clicking. New version will have them already stored in cache
                // on item selected only updates spinner with values
                //Log.d("past",  "I am on item selected updating: " + yearsList.get(i));
               //new CausesTask().execute(yearsList.get(i), "true");
                updateFromSpinner(yearsList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected HashMap<String,String> causeToHashMap(Cause cause){
        HashMap<String, String> hashMap = new HashMap<String,String>();


        hashMap.put(Constants.ELECTION_TITLE_COLUMN, cause.getElection().getTitle());
        hashMap.put(Constants.TITLE_COLUMN, cause.getName());
        hashMap.put(Constants.MONEY_COLUMN, cause.getMoney() + "€");


        return hashMap;
    }

    @Override
    protected void initializeListAdapter(View view) {
        listView = (ListView) view.findViewById(R.id.causes_list);

        listAdapter = new SimpleAdapter(
                this.getContext(),
                list,
                R.layout.item_previous_winner,
                new String[]{Constants.ELECTION_TITLE_COLUMN, Constants.TITLE_COLUMN, Constants.MONEY_COLUMN},
                new int[]{R.id.last_causes_item_main_title, R.id.last_causes_item_sub_title, R.id.last_causes_item_money}
        );
        listView.setAdapter(listAdapter);
    }

    @Override
    public void cardClick(View view) {
        int index=listView.getPositionForView(view);
        Cause c= shownCauseslist.get(index);
        // Log.e("Cause",c.toString());
        Intent intent = new Intent(this.getActivity(), PastCauseDetailsActivity.class);
        intent.putExtra("Cause",c);
        startActivity(intent);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private class CausesTask extends AsyncTask<String, Void, JSONObject> {

        String year;
        boolean updateAfterRequest = false;

        public CausesTask() {
            super();
        }

        public CausesTask(boolean updateAfterRequest){
            this.updateAfterRequest = updateAfterRequest;
        }

        @Override
        protected JSONObject doInBackground(String... parameters) {

            //TODO: check if null
            year = parameters[0];
            // Preparation of variables for the request and response handling
            HttpResponse<String> response = null;
            JSONObject result = new JSONObject();
            String token = "";

            try {
                SharedPreferences userDetails = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                token = userDetails.getString("token", "");
            }catch(Exception e){

                //To prevent conflicts between async tasks, if user clicks various times on the menu item
                return result;

            }
            // API Request
            try {
                response = Unirest.get(getResources().getString(R.string.api_server_url) + getResources().getString(R.string.winner_causes_path)+ "?ano=" + year )
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .header("Authorization", token)
                        .asString();
            } catch (UnirestException e) {

            }

            try {
                if (response == null) {
                    ConnectivityManager cm =
                            (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();

                    String connectionError = getResources().getString(R.string.user_connection_error);
                    String requestError = getResources().getString(R.string.causes_request_error);

                    String error = isConnected ? requestError : connectionError;

                    throw new Exception(error);
                }

                JSONObject obj = new JSONObject(response.getBody());
                if (!obj.getString("result").equals(getResources().getString(R.string.api_success_response)))
                    throw new Exception(getResources().getString(R.string.user_loading_authetication_error));

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

                    for(int causeNr = 0; causeNr < totalCausesNr; causeNr++) {

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

            } catch (Exception e) {
                //Log.d("past", "Exception Where we put result: " + e.getMessage());

                // Handle error
                try {
                    if(updateAfterRequest) {

                        result.put("error", true);
                        result.put("errorMessage", e.getMessage());

                    }
                }catch(Exception b){

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
                if(result != null) {  // RESULT != NULL MEANS THERE WAS AN ERROR
                    String message = result.getString("errorMessage");

                    //only shows toast for the request which was to update screen, in case several requests are made
                    if (result.getBoolean("error") == true && updateAfterRequest) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    }
                }
            }catch(JSONException e){
                // Log.d("causes", e.getMessage());
            }catch(Exception b){
                // Log.d("causes", b.getMessage());
            }

            if(updateAfterRequest) {
                spinnerAdapter.notifyDataSetChanged();
                // Log.d("past", "I am on post execute updating: " + year);
                updateFromSpinner(year);
            }

        }
    }

}
