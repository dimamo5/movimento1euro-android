package com.artisans.code.movimento1euro.elements;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Antonio on 30-11-2016.
 */

public class Cause {
    private int id;
    private String title;
    private String description;
    private String money;
    private String votes;
    private ArrayList<String> documents = new ArrayList<String>();
    private ArrayList<String> videos = new ArrayList<String>();
    private boolean user_vote;
    private Association association;

    public Cause (JSONObject json) {
        try {
            id = json.getInt("id");
            title = json.getString("titulo");
            description = json.getString("descricao");
            money = json.getString("verba");
            votes = json.getString("votos");
            parseArray(getDocuments(), json.getJSONArray("documentos"));
            parseArray(getVideos(), json.getJSONArray("videos"));
            user_vote = json.getBoolean("voto_utilizador");
            association = new Association(json.getJSONObject("associacao"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Cause exception", "Error parsing JSON");
        }
    }

    private void parseArray(ArrayList<String> stringArray, JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            stringArray.add(jsonArray.getString(i));
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMoney() {
        return money;
    }

    public String getVotes() {
        return votes;
    }

    public ArrayList<String> getDocuments() {
        return documents;
    }

    public ArrayList<String> getVideos() {
        return videos;
    }

    public boolean isUser_vote() {
        return user_vote;
    }

    public Association getAssociation() {
        return association;
    }
}
