package com.artisans.code.movimento1euro.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.models.VotingCause;
import com.artisans.code.movimento1euro.network.ApiManager;

/**
 * Created by Duart on 21/12/2016.
 * Confirmation dialog for voting on a cause
 */

public class VoteDialog extends AlertDialog.Builder {

    public VoteDialog(final Context context, final VotingCause cause) {
        super(context);

        setMessage("Tem a certeza que deseja votar nesta causa?");
        setTitle(context.getResources().getString(R.string.pop_up_voting_message));
        setIcon(android.R.drawable.ic_dialog_alert);

        setPositiveButton(R.string.pop_up_voting_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ApiManager.getInstance().vote(context,cause);
            }
        });
        setNegativeButton(R.string.pop_up_voting_negative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // dismiss
            }
        });

    }
}
