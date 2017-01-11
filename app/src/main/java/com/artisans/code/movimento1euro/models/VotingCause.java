package com.artisans.code.movimento1euro.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

/**
 * Represents a voting cause with all its information.
 * Also stores information as to whether the current logged user has voted or not on this cause.
 */

public class VotingCause extends Cause {
    protected boolean userVoted;

    public VotingCause(JSONObject json){
        super(json);

        try {
            this.name = json.getString(JSONFields.VOTING_NAME_COLUMN);
            this.userVoted = json.getBoolean("voto_utilizador");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isUserVoted() {
        return userVoted;
    }

    public void setUserVoted(boolean userVoted) {
        this.userVoted = userVoted;
    }
}
