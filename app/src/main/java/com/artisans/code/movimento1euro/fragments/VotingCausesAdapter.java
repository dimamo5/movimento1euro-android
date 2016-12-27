package com.artisans.code.movimento1euro.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.artisans.code.movimento1euro.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Duart on 27/12/2016.
 */

public class VotingCausesAdapter extends SimpleAdapter {

    private Context mContext;
    public LayoutInflater inflater=null;
    public VotingCausesAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View vi = super.getView(position, convertView, parent);

        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);

        ImageView image=(ImageView)vi.findViewById(R.id.voting_causes_item_image);
        String imageUrl = (String) data.get(VotingCausesFragment.Constants.IMAGE_COLUMN);
        if(!( imageUrl == null || imageUrl.equals(""))){
            Picasso.with(mContext).load(imageUrl).into(image);
        }
        return vi;
    }
}