package com.artisans.code.movimento1euro;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.artisans.code.movimento1euro.ViewLastCausesFragment.Constants.YEAR_COLUMN;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewLastCausesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewLastCausesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VotingCausesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    */

    public static final List<String> MONTHS = Arrays.asList("No Month", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");

    // TODO: Rename and change types of parameters
    /*
    private String mParam1;
    private String mParam2;
    */

    private OnFragmentInteractionListener mListener;

    ArrayList<String> yearsList = new ArrayList<String>();
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> fullList = new ArrayList<HashMap<String, String>>();
    ArrayAdapter<String> spinnerAdapter;
    ListView listView;
    SimpleAdapter listAdapter;

    public VotingCausesFragment() {
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
        public static final String NAME_COLUMN = "Name";
        public static final String MONEY_COLUMN = "Money";
        public static final String YEAR_COLUMN = "Year";
    }


    private class CausesTask extends AsyncTask<String, Void, JSONObject> {

        public CausesTask() {
            super();
        }

        @Override
        protected JSONObject doInBackground(String... parameters) {

            HttpResponse<String> response = null;
            JSONObject result = new JSONObject();

            SharedPreferences userDetails = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
            String token = userDetails.getString("token", "");

            try {
                response = Unirest.get(getResources().getString(R.string.api_server_url) + getResources().getString(R.string.view_causes_path))
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .header("Authorization", token)
                        .asString();
            } catch (UnirestException e) {

            }

            try {

                if (response == null)
                    throw new Exception("Não foi possível carregar as causas em votação. Verifique a sua conexão.");

                JSONObject obj = new JSONObject(response.getBody());
                JSONArray arr = obj.getJSONArray("causes");

                yearsList.clear();
                fullList.clear();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    String year = o.getString("date").substring(0, 4);

                    if (!yearsList.contains(year)) {
                        yearsList.add(year);
                    }

                    HashMap<String, String> temp = new HashMap<String, String>();

                    int month =  Integer.parseInt(o.getString("month"));
                    if(month > 12 || month < 0)
                        month = 0;
                    temp.put(Constants.MONTH_COLUMN, "Mês de " + MONTHS.get(month)); // -1 because index starts at 0

                    temp.put(Constants.NAME_COLUMN, "Nome: " + o.getString("name"));
                    temp.put(Constants.YEAR_COLUMN, year);
                    temp.put(Constants.MONEY_COLUMN, i + "€");
                    fullList.add(temp);
                }

                Collections.sort(yearsList);

                //yearsList.add();
            } catch (JSONException e) {
                // Log.d("causes", "JSONException: " + e.getMessage());
            } catch (Exception e) {
                // Log.d("causes", "Exception: " + e.getMessage());

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
                    if (result.getBoolean("error") == true)
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            }catch(JSONException e){
                // Log.d("causes", e.getMessage());
            }catch(Exception b){
                // Log.d("causes", b.getMessage());
            }


            spinnerAdapter.notifyDataSetChanged();
            if(yearsList.size()!= 0)
                updateFromSpinner(yearsList.get(0));
        }
    }

    public void updateFromSpinner(String year) {
        list.clear();
        for(HashMap<String, String> item : fullList) {
            String y  = item.get(YEAR_COLUMN);

            if(y.equals(year))
                list.add(item);
        }

        // QUICK FIX FOR THE MONTH ORDER -> DEPENDS ON API ORDER
        //TODO: Check if API will return ordered or manipulate lists to sort well
        Collections.reverse(list);

        listAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_winners, container, false);

        listView = (ListView) view.findViewById(R.id.last_causes_list);

        new CausesTask().execute();
        // Log.d("causes", "executed new CausesTask()");

        /*
        for (int i = 0; i < 50; i++) {
            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(Constants.MONTH_COLUMN, "Mes " + i);
            temp.put(Constants.NAME_COLUMN, "Nome " + i);
            temp.put(Constants.MONEY_COLUMN, i+"€");
            list.add(temp);
        }
        */

        listAdapter = new SimpleAdapter(
                this.getContext(),
                list,
                R.layout.item_previous_winner,
                new String[]{Constants.MONTH_COLUMN, Constants.NAME_COLUMN, Constants.MONEY_COLUMN},
                new int[]{R.id.last_causes_item_month, R.id.last_causes_item_name, R.id.last_causes_item_money}
        );
        listView.setAdapter(listAdapter);

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner_nav);
        spinner.setVisibility(View.VISIBLE);

        //TODO: Get existing years list
        spinnerAdapter = new ArrayAdapter<String>(this.getContext(),
                R.layout.spinner_previous_winners_selected_year, yearsList);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_previous_winners_year);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), yearsList.get(i) + " Clicked", Toast.LENGTH_SHORT).show();
                updateFromSpinner(yearsList.get(i));
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
