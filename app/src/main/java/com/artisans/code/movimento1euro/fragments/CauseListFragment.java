package com.artisans.code.movimento1euro.fragments;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.artisans.code.movimento1euro.models.Cause;
import com.artisans.code.movimento1euro.models.PastCause;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Duart on 19/12/2016.
 */
public abstract class CauseListFragment extends Fragment {
    OnFragmentInteractionListener mListener;
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

    ArrayAdapter<String> spinnerAdapter; // Spinner year list adapter
    Spinner spinner;
    ListView listView;
    SimpleAdapter listAdapter;


    protected int updateAdapterList(ArrayList<Cause> causeList, ArrayList<HashMap<String,String>> hashmapList){
        hashmapList.clear();

        if(causeList != null)
            for(int i = 0; i < causeList.size(); i++){
                hashmapList.add(causeToHashMap(causeList.get(i)));
            }
        return hashmapList.size();
    }

    protected abstract HashMap<String,String> causeToHashMap(Cause cause);

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
}
