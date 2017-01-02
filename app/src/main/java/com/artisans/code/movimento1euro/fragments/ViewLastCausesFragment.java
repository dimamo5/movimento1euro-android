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
import com.artisans.code.movimento1euro.network.LastCausesTask;
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


    public final static String requestError = "";
    public final static String connectionError = "";
    public final static String TAG = ViewLastCausesFragment.class.getCanonicalName();
    public static final List<String> MONTHS = Arrays.asList("No Month", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
    public static final int START_YEAR = 2011;


    ArrayAdapter<String> spinnerAdapter; // Spinner year list adapter
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

        if (allCausesByYear.get(year) != null) {

            shownCauseslist.clear();
            shownCauseslist.addAll(allCausesByYear.get(year));

            updateAdapterList(shownCauseslist,list);
            //Log.d(TAG, "size-list: "+list.size());

        } else {
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
        new LastCausesTask(this, true).execute(yearsList.get(0));
        //Log.d("past", "Year to be executed: " + yearsList.get(0));
        for(int i = 1; i< yearsList.size(); i++) {
            new LastCausesTask(this, false).execute(yearsList.get(i));
        }
        return view;
    }

    protected void fillYearsList() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (currentYear >= START_YEAR) {
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
    protected HashMap<String, String> causeToHashMap(Cause cause) {
        HashMap<String, String> hashMap = new HashMap<String, String>();


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

    public ArrayAdapter<String> getSpinnerAdapter() {
        return spinnerAdapter;
    }

    public void setSpinnerAdapter(ArrayAdapter<String> spinnerAdapter) {
        this.spinnerAdapter = spinnerAdapter;
    }

    public ArrayList<Cause> getShownCauseslist() {
        return shownCauseslist;
    }

    public void setShownCauseslist(ArrayList<Cause> shownCauseslist) {
        this.shownCauseslist = shownCauseslist;
    }

    public ArrayList<String> getYearsList() {
        return yearsList;
    }

    public void setYearsList(ArrayList<String> yearsList) {
        this.yearsList = yearsList;
    }

    public HashMap<String, ArrayList<PastCause>> getAllCausesByYear() {
        return allCausesByYear;
    }

    public void setAllCausesByYear(HashMap<String, ArrayList<PastCause>> allCausesByYear) {
        this.allCausesByYear = allCausesByYear;
    }
}
