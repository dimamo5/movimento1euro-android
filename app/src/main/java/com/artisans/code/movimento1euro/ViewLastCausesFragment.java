package com.artisans.code.movimento1euro;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


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

    // TODO: Rename and change types of parameters
    /*
    private String mParam1;
    private String mParam2;
    */

    private OnFragmentInteractionListener mListener;

    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ListView listView;
    SimpleAdapter adapter;

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

    public class Constants {
        public static final String MONTH_COLUMN = "Month";
        public static final String NAME_COLUMN = "Name";
        public static final String MONEY_COLUMN = "Money";
    }


    private class CausesTask extends AsyncTask<String, Void, JSONObject> {

        public CausesTask() {
            super();
        }

        @Override
        protected JSONObject doInBackground(String... parameters) {

            HttpResponse<String> response = null;

            try {
                response = Unirest.get("http://staging.diogomoura.me/api/winnerCauses")
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .header("Authorization", "d51dae9e0cdef919b5dae431b95fb199728b3003174be8e51834cbea7aa61bd73ed2a272a96c3c449fc0c74689f32681")
                        .asString();
            } catch (UnirestException e) {
                Log.d("causes", e.getMessage());
            }

            Log.d("causes", "completed");
            if (response != null)
                Log.d("causes", response.toString());

            try {

                JSONObject obj = new JSONObject(response.getBody());
                JSONArray arr = obj.getJSONArray("causes");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    HashMap<String, String> temp = new HashMap<String, String>();
                    temp.put(Constants.MONTH_COLUMN, "Mes " + o.getString("month"));
                    temp.put(Constants.NAME_COLUMN, "Nome " + o.getString("name"));
                    temp.put(Constants.MONEY_COLUMN, i + "€");
                    list.add(temp);
                }


            } catch (JSONException e) {
                Log.d("causes", "JSONException: " + e.getMessage());
            } catch (Exception e) {
                Log.d("causes", "Exception: " + e.getMessage());
                // Show message here: "Couldn't load causes from the network."
            }
            // int code = response.getCode();
            // Map<String, String> headers = response.getHeaders();
            //InputStream rawBody = response.getRawBody();

            JSONObject result = null;


            return result;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject result) {
            Log.d("causes", "before adapter notified");
            adapter.notifyDataSetChanged();
            Log.d("causes", "adapter notified");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_winners, container, false);

        listView = (ListView) view.findViewById(R.id.last_causes_list);

        new CausesTask().execute();
        Log.d("causes", "executed new CausesTask()");

        /*
        for (int i = 0; i < 50; i++) {
            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(Constants.MONTH_COLUMN, "Mes " + i);
            temp.put(Constants.NAME_COLUMN, "Nome " + i);
            temp.put(Constants.MONEY_COLUMN, i+"€");
            list.add(temp);
        }
        */

        adapter = new SimpleAdapter(
                this.getContext(),
                list,
                R.layout.item_previous_winner,
                new String[]{Constants.MONTH_COLUMN, Constants.NAME_COLUMN, Constants.MONEY_COLUMN},
                new int[]{R.id.last_causes_item_month, R.id.last_causes_item_name, R.id.last_causes_item_money}
        );
        listView.setAdapter(adapter);

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner_nav);
        spinner.setVisibility(View.VISIBLE);
        ArrayList<String> arrayList1 = new ArrayList<String>();
        arrayList1.add("2016");
        arrayList1.add("2015");
        arrayList1.add("2014");
        arrayList1.add("2013");
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this.getContext(),
                R.layout.spinner_previous_winnners_selected_year, arrayList1);
        adp.setDropDownViewResource(R.layout.spinner_previous_winnners_year);
        spinner.setAdapter(adp);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), 2016 - i + " Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public void cardClick(View view) {
        Toast.makeText(getActivity(), listView.getPositionForView(view) + " Clicked", Toast.LENGTH_SHORT).show();
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
}
