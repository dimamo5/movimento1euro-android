package com.artisans.code.movimento1euro.fragments;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.artisans.code.movimento1euro.models.Cause;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Duart on 19/12/2016.
 */
public abstract class CauseListFragment extends Fragment {
    OnFragmentInteractionListener mListener;
    /**
     * Stores the past causes that are shown on the screen, each cause being on an hashmap format
     */
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

    Spinner spinner;
    ListView listView;
    SimpleAdapter listAdapter;

    /**
     * Clears the hashmap list and fills it with new values transformed from the causes list
     * @param causeList list of causes to transform into hashmap
     * @param hashmapList list of hashmaps that is updated - each hashmap object on the list corresponds to a cause, the keys of the hashmap correspond to the data fields of the cause
     * @return
     */
    public int updateAdapterList(ArrayList<Cause> causeList, ArrayList<HashMap<String,String>> hashmapList){
        hashmapList.clear();

        if(causeList != null)
            for(int i = 0; i < causeList.size(); i++){
                hashmapList.add(causeToHashMap(causeList.get(i)));
            }
        return hashmapList.size();
    }

    /**
     * Transforms a cause object into HashMap format (for adapter list to receive later)
     * @param cause Cause object to be transformed
     * @return Hashmap, the names of the data fields are the keys, mapping each cause data field to its value
     */
    protected abstract HashMap<String,String> causeToHashMap(Cause cause);

    /**
     * Internally updates the adapter with the current state of the list of causes
     * Updating the list itself does nothing until this method is called.
     */
    public void notifyChanges() {
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    protected abstract void initializeListAdapter(View view);
    public abstract void cardClick(View view);

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

    public OnFragmentInteractionListener getmListener() {
        return mListener;
    }

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    /**
     *
     * @return  HashMap list that contains the information for the list of causes being shown
     */
    public ArrayList<HashMap<String, String>> getList() {
        return list;
    }

    /**
     * Sets the HashMap list with information to be shown as the one on the parameters
     * @param  list of hashmaps that contain causes' information
     */
    public void setList(ArrayList<HashMap<String, String>> list) {
        this.list = list;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public SimpleAdapter getListAdapter() {
        return listAdapter;
    }

    public void setListAdapter(SimpleAdapter listAdapter) {
        this.listAdapter = listAdapter;
    }
}
