package com.artisans.code.movimento1euro.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

/**
 * Created by Duart on 13/12/2016.
 */

public class VotingCause extends Cause {
    protected boolean userVoted;

    public VotingCause(JSONObject json){
        super(json);

        try {
            this.userVoted = json.getBoolean("voto_utilizador");
            this.videos = parseUrlArray(json.getJSONArray("videos"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
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
