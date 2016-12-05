package com.artisans.code.movimento1euro.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.artisans.code.movimento1euro.elements.Cause;
import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.menus.ViewActivity;
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

public class VotingCausesFragment extends Fragment {

    public static final List<String> MONTHS = Arrays.asList("No Month", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");


    private OnFragmentInteractionListener mListener;

    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> fullList = new ArrayList<HashMap<String, String>>();
    ArrayList<Cause> causesList = new ArrayList<>();
    ListView listView;
    SimpleAdapter listAdapter;

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
        //public static final String IMAGE_COLUMN = "Image";
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
                response = Unirest.get(getResources().getString(R.string.api_server_url) + getResources().getString(R.string.voting_causes_path))
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .header("Authorization", token)
                        .asString();
            } catch (UnirestException e) {
                Log.e("API", "Bad request");
            }

            try {
                if (response == null)
                    throw new Exception(getResources().getString(R.string.user_connection_error));

                JSONObject obj = new JSONObject(response.getBody());
                if (!obj.getString("result").equals(getResources().getString(R.string.api_success_response)))
                    throw new Exception(getResources().getString(R.string.user_loading_authetication_error));
                JSONArray votingCauses = obj.getJSONArray("votacao");

                list.clear();
                causesList.clear();
                for (int j = 0; j < votingCauses.length(); j++) {
                    //TODO discarded useful information?
                    JSONArray arr = votingCauses.getJSONObject(j).getJSONArray("causas");
                    for (int i = 0; i < arr.length(); i++) {
                        causesList.add(Cause.parseVotingCause(arr.getJSONObject(i)));

                        HashMap<String, String> temp = new HashMap<>();

                        //TODO adicionar imagem da causa ?
                        temp.put(Constants.NAME_COLUMN, "Nome: " + causesList.get(i).getTitle());
                        temp.put(Constants.DESCRIPTION_COLUMN, causesList.get(i).getDescription());
                        temp.put(Constants.MONEY_COLUMN, "Valor da causa: " + causesList.get(i).getMoney() + "€");
                        list.add(temp);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("causes", "JSONException: " + e.getMessage());
            } catch (Exception e) {
                // Log.d("causes", "Exception: " + e.getMessage());
                try {
                    Looper.prepare();
                    result.put("error", true);
                    result.put("errorMessage", e.getMessage());
                } catch (Exception b) {
                    b.printStackTrace();
                    Log.e("causes", "Exception: " +  b.getMessage());
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
                Log.e("causes", e.getMessage());
            } catch (Exception b) {
                Log.e("causes", b.getMessage());
            }
            notifyChanges();
        }
    }

    public void notifyChanges(){
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voting_causes, container, false);

        listView = (ListView) view.findViewById(R.id.causes_list);

        listAdapter = new SimpleAdapter(
                this.getContext(),
                list,
                R.layout.item_voting_cause,
                new String[]{Constants.NAME_COLUMN, Constants.DESCRIPTION_COLUMN, Constants.MONEY_COLUMN},
                //TODO ADICIONAR COLUNA DA IMAGEM? -- R.id.voting_causes_item_image
                new int[]{R.id.voting_causes_item_name, R.id.voting_causes_item_description, R.id.voting_causes_item_money}
        );

        new CausesTask().execute();
        listView.setAdapter(listAdapter);

        return view;
    }

    public void cardClick(View view) {
        int index=listView.getPositionForView(view);
       Cause c= causesList.get(index);
       // Log.e("Cause",c.toString());
        Intent intent = new Intent(this.getActivity(), ViewActivity.class);
        intent.putExtra("Cause",causesList.get(index));
        startActivity(intent);

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
        void onFragmentInteraction(Uri uri);
    }
}
