package com.artisans.code.movimento1euro.fragments;

import android.content.Intent;
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

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.menus.PastCauseDetailsActivity;
import com.artisans.code.movimento1euro.models.Cause;
import com.artisans.code.movimento1euro.models.PastCause;
import com.artisans.code.movimento1euro.network.LastCausesTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * Fragment for the list that shows the past causes
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewLastCausesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewLastCausesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewLastCausesFragment extends CauseListFragment {

    /**
     * First year to appear on the spinner. The requests will count the years upward from here.
     */
    public static final int START_YEAR = 2011;

    public final static String requestError = "";
    public final static String connectionError = "";
    public final static String TAG = ViewLastCausesFragment.class.getCanonicalName();

    ArrayAdapter<String> spinnerAdapter; // Spinner year list adapter
    /**
     * Hashmap mapping each year to the list of causes it contains
     */
    HashMap<String, ArrayList<PastCause>> allCausesByYear = new HashMap<>();
    /**
     * List of years available to search causes from
     */
    ArrayList<String> yearsList = new ArrayList<String>();
    /**
     * Auxiliar container of the list of causes to be shown on the screen (when transformed to hashmap)
     */
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

    /**
     * Constants to be used as field names/keys for the list hashmap of the Past Causes
     */
    public class Constants {
        public static final String TITLE_COLUMN = "Title";
        public static final String ELECTION_TITLE_COLUMN = "Election_Title";
        public static final String MONEY_COLUMN = "Money";

    }

    /**
     * Updates screen with the causes from a certain year
     * @param year Year whence the causes to load belong to
     */
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


    /**
     * Initializes all the adapters and makes the web requests for all the causes of each year.
     * Additionaly, the request for the current year is updated on the screen, when received.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

    /**
     * Makes a list with all the years from START_YEAR up until the present
     */
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

    /**
     * Create Spinner on ACTIVITY BAR and define adapter, functions and spinner items
     */
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

    /**
     * Event on clicking a cause item - starts a new activity detailing the cause
     * @param view
     */
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
