package com.artisans.code.movimento1euro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.artisans.code.movimento1euro.menus.MainMenu;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public class Constants {
        public static final String MONTH_COLUMN="Month";
        public static final String NAME_COLUMN="Name";
        public static final String MONEY_COLUMN="Money";
    }
    TextView txtMonth;
    TextView txtName;
    TextView txtMoney;
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_last_causes, container, false);

        listView = (ListView) view.findViewById(R.id.last_causes_list);

        txtMonth = (TextView) view.findViewById(R.id.last_causes_item_month);
        txtName = (TextView) view.findViewById(R.id.last_causes_item_name);
        txtMoney = (TextView) view.findViewById(R.id.last_causes_item_money);

        for (int i = 0; i < 50; i++) {
            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(Constants.MONTH_COLUMN, "Mes " + i);
            temp.put(Constants.NAME_COLUMN, "Nome " + i);
            temp.put(Constants.MONEY_COLUMN, i+"€");
            list.add(temp);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this.getContext(),
                list,
                R.layout.last_cause_item,
                new String[] {Constants.MONTH_COLUMN,Constants.NAME_COLUMN,Constants.MONEY_COLUMN},
                new int[] {R.id.last_causes_item_month,R.id.last_causes_item_name, R.id.last_causes_item_money}
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO delete activity variable on MainMenu.java
                Toast.makeText(MainMenu.activity, Integer.toString(i)+" Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void cardClick(View view) {
        //TODO delete activity variable on MainMenu.java
        Toast.makeText(MainMenu.activity, listView.getPositionForView(view)+" Clicked", Toast.LENGTH_SHORT).show();
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
