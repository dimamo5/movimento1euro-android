package com.artisans.code.movimento1euro.menus;

import android.os.Bundle;
import android.view.View;

import com.artisans.code.movimento1euro.R;

/**
 * Created by Duart on 20/12/2016.
 */

public class PastCauseDetailsActivity extends CausesDetailsActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.voteButton).setVisibility(View.GONE);
    }
}
