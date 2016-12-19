package com.artisans.code.movimento1euro.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class ViewLastCausesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    */

    public static final List<String> MONTHS = Arrays.asList("No Month", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
    public static final int START_YEAR = 2011;
    // TODO: Rename and change types of parameters
    /*
    private String mParam1;
    private String mParam2;
    */

    private OnFragmentInteractionListener mListener;

    ArrayList<String> yearsList = new ArrayList<String>();

    // List of Cause objects. Cause objects are hashmaps
    ArrayList<Cause> shownCauseslist = new ArrayList<Cause>();
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

    HashMap<String, ArrayList<Cause>> allCausesByYear = new HashMap<String, ArrayList<Cause>>();

    ArrayAdapter<String> spinnerAdapter; // Spinner year list adapter
    Spinner spinner;
    SimpleAdapter listAdapter; // Main causes list adapter
    ListView listView;




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
        public static final String MONTH_COLUMN = "Month";
        public static final String YEAR_COLUMN = "Year";
        public static final String TITLE_COLUMN = "Title";
        public static final String ELECTION_TITLE_COLUMN = "Election_Title";
        public static final String MONEY_COLUMN = "Money";
        public static final String DESCRIPTION_COLUMN = "Description";
        public static final String VOTES_COLUMN = "Votes";
        public static final String ELECTION_MONEY_COLUMN = "Election_Money";
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

            SharedPreferences userDetails = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
            String token = userDetails.getString("token", "");

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

                if (response == null)
                    throw new Exception("Não foi possível carregar as causas passadas. Verifique a sua conexão.");

                //TODO ask if result != success
                JSONObject obj = new JSONObject(response.getBody());
                // Get Yearly elections array from response
                JSONArray yearlyElections = obj.getJSONArray("causes");
                int totalElectionNr = yearlyElections.length();


                ArrayList<Cause> requestedCauses = new ArrayList<Cause>();

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
                        Cause tempCause = new PastCause(cause);
                        tempCause.setElection(election);

                        /*
                        HashMap<String, String> tempCause = new HashMap<String, String>();

                        tempCause.put(JSONFields.MONTH_COLUMN, election.getString("titulo"));
                        tempCause.put(JSONFields.NAME_COLUMN, "Nome: " + cause.getString("nome"));
                        tempCause.put(JSONFields.YEAR_COLUMN, election.getString("data_de_fim"));
                        tempCause.put(JSONFields.MONEY_COLUMN, election.getString("montante_disponivel") + "€");
                        tempCause.put(JSONFields.VERBA_COLUMN, "Verba: " + cause.getString("verba") + "€");
                        tempCause.put(JSONFields.VOTES_COLUMN, "Votos: " + cause.getString("votos") + "€");
                        */
                        requestedCauses.add(tempCause);
                    }
                }

                allCausesByYear.put(year, requestedCauses);

            } catch (JSONException e) {
                // Log.d("causes", "JSONException2: " + e.getMessage());
            } catch (Exception e) {
                // Log.d("causes", "Exception: " + e.getMessage());

                // Handle error
                try {
                    Looper.prepare();
                    result.put("error", true);
                    result.put("errorMessage", e.getMessage());

                }catch(Exception b){
                    // Log.d("causes", "Exception: " +  b.getMessage());
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
                    if (result.getBoolean("error") == true && updateAfterRequest)
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            }catch(JSONException e){
                // Log.d("causes", e.getMessage());
            }catch(Exception b){
                // Log.d("causes", b.getMessage());
            }

            // Deprecated
            if(updateAfterRequest) {
                spinnerAdapter.notifyDataSetChanged();
               // Log.d("past", "I am on post execute updating: " + year);
                updateFromSpinner(year);
            }

        }
    }

    public void updateFromSpinner(String year) {

        if(allCausesByYear.get(year) != null) {

            shownCauseslist = allCausesByYear.get(year);

            Log.d("past", "All Causes loaded: " + allCausesByYear.toString());
            Log.d("past", "Causes shown: " + shownCauseslist.toString());

            causeList_to_hashmapList(shownCauseslist, list);


            /*
            Log.d("past", list.get(0).get(JSONFields.VOTES_COLUMN));
            Log.d("past", list.get(0).get(JSONFields.MONEY_COLUMN));
            Log.d("past", list.get(0).get(JSONFields.DESCRIPTION_COLUMN));
            Log.d("past", list.get(0).get(JSONFields.TITLE_COLUMN));
            Log.d("past", list.get(0).get(JSONFields.VERBA_COLUMN));
            */
        }else{
            // Mostrar no ecrã que não há causas para este período(ano)
        }
        Log.d("past", list.toString());
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_winners, container, false);

        // HARDCODED: GET THE YEARS WHERE THERE WERE CAUSES
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(currentYear >= START_YEAR) {
            for (int year = currentYear; year >= START_YEAR; year--) {
                yearsList.add(Integer.toString(year));
            }
        }else{
            Log.d("past", Integer.toString(currentYear) + "is  <  than " + Integer.toString(START_YEAR));
            yearsList.add(Integer.toString(START_YEAR));
        }


        // SPINNER - Create Spinner on ACTIVITY BAR + define adapter, functions and spinner items
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
                Log.d("past",  "I am on item selected updating: " + yearsList.get(i));
               //new CausesTask().execute(yearsList.get(i), "true");
                updateFromSpinner(yearsList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // LIST - Create List adapter
        listView = (ListView) view.findViewById(R.id.causes_list);

        listAdapter = new SimpleAdapter(
                this.getContext(),
                list,
                R.layout.item_previous_winner,
                new String[]{Constants.ELECTION_TITLE_COLUMN, Constants.TITLE_COLUMN, Constants.MONEY_COLUMN},
                new int[]{R.id.last_causes_item_main_title, R.id.last_causes_item_sub_title, R.id.last_causes_item_money}
        );
        listView.setAdapter(listAdapter);

        // API request -> with the first year in the list, which should currently be selected
        // Maybe execute all already, to store them
        new CausesTask(true).execute(yearsList.get(0));
        Log.d("past", "Year to be executed: " + yearsList.get(0));
        for(int i = 1; i< yearsList.size(); i++) {
            new CausesTask(false).execute(yearsList.get(i));
        }
        return view;
    }

    public void cardClick(View view) {
        //TODO card click
        //Toast.makeText(getActivity(), listView.getPositionForView(view) + " Clicked", Toast.LENGTH_SHORT).show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public ArrayList<HashMap<String, String>> causeList_to_hashmapList(ArrayList<Cause> causeList, ArrayList<HashMap<String,String>> hashmapList){
        hashmapList.clear();

        if(causeList != null)
        for(int i = 0; i < causeList.size(); i++){
            hashmapList.add(causeList.get(i).toHashMap());
        }
        return hashmapList;
    }

}
