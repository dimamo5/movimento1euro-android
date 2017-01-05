package com.artisans.code.movimento1euro.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.menus.CausesDetailsActivity;
import com.artisans.code.movimento1euro.models.Cause;
import com.artisans.code.movimento1euro.models.VotingCause;
import com.artisans.code.movimento1euro.network.ApiManager;
import com.artisans.code.movimento1euro.network.VotingCausesTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VotingCausesFragment extends CauseListFragment  {

    private static final String TAG = VotingCausesFragment.class.getSimpleName();

    public static final List<String> MONTHS = Arrays.asList("No Month", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
    private OnFragmentInteractionListener mListener;

    /**
     * Stores the causes that are shown on the screen, each cause being on an hashmap format
     */
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    /**
     * Auxiliar container of the list of causes (to be transformed to hashmap)
     */
    ArrayList<Cause> causesList = new ArrayList<>();


    public VotingCausesFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Constants to be used as field names/keys for the list hashmap of the Voting Causes
     */
    public class Constants {
        public static final String NAME_COLUMN = "name";
        public static final String MONEY_COLUMN = "money";
        public static final String DESCRIPTION_COLUMN = "description";
        public static final String IMAGE_COLUMN = "thumbnail";
    }

    /**
     * Android function. On creating view, starts a web request for the voting causes
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_voting_causes, container, false);

        initializeListAdapter(view);
        new VotingCausesTask(this).execute();

        return view;
    }

    @Override
    protected void initializeListAdapter(View view) {
        listView = (ListView) view.findViewById(R.id.causes_list);

        listAdapter = new VotingCausesAdapter(
                this.getContext(),
                list,
                R.layout.item_voting_cause,
                new String[]{Constants.NAME_COLUMN, Constants.DESCRIPTION_COLUMN, Constants.MONEY_COLUMN},
                new int[]{R.id.voting_causes_item_name, R.id.voting_causes_item_description, R.id.voting_causes_item_money}
        );

        listView.setAdapter(listAdapter);
    }

    @Override
    protected HashMap<String, String> causeToHashMap(Cause cause) {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put(Constants.NAME_COLUMN, cause.getName());
        hashMap.put(Constants.DESCRIPTION_COLUMN, cause.getDescription());
        hashMap.put(Constants.MONEY_COLUMN, "Valor da causa: " + cause.getMoney() + "€");
        hashMap.put(Constants.IMAGE_COLUMN,cause.getYoutubeThumbnailLink());


        return hashMap;
    }

    /**
     * Event on clicking a cause item - starts a new activity detailing the cause
     * @param view
     */
    @Override
    public void cardClick(View view) {
        int index = listView.getPositionForView(view);
        Cause c = causesList.get(index);
        // Log.e("Cause",c.toString());
        Intent intent = new Intent(this.getActivity(), CausesDetailsActivity.class);
        intent.putExtra("Cause", causesList.get(index));
        startActivity(intent);
    }

    /**
     * Calls API web request to vote on the selected view (selected cause card)
     * @param view selected view (cause card)
     */
    public void vote(final View view) {
        int index = listView.getPositionForView(view);
        Cause cause = causesList.get(index);

        if(!ApiManager.getInstance().isAuthenticated(getContext())){
            Toast.makeText(getContext(), getContext().getString(R.string.unauthenticated_voting_error), Toast.LENGTH_LONG).show();
            return;
        }else{
            new VoteDialog(getContext(), (VotingCause) cause).create().show();
        }
    }

    @Override
    public ArrayList<HashMap<String, String>> getList() {
        return list;
    }

    @Override
    public void setList(ArrayList<HashMap<String, String>> list) {
        this.list = list;
    }

    public ArrayList<Cause> getCausesList() {
        return causesList;
    }

    public void setCausesList(ArrayList<Cause> causesList) {
        this.causesList = causesList;
    }
}
