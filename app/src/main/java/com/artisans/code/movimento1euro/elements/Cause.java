package com.artisans.code.movimento1euro.elements;

import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
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
    private ArrayList<Pair<URL, String>> documents = new ArrayList<Pair<URL, String>>();
    private ArrayList<Pair<URL, String>> videos = new ArrayList<Pair<URL, String>>();
    private boolean user_vote;
    private Association association;

    private Cause () {
    }

    public static Cause parseVotingCause(JSONObject json) {
        Cause cause = new Cause();

        try {
            cause.id = json.getInt("id");
            cause.title = json.getString("titulo");
            cause.description = json.getString("descricao");
            cause.money = json.getString("verba");
            cause.votes = json.getString("votos");
            cause.documents = parseArray(json.getJSONArray("documentos"));
            cause.videos = parseArray(json.getJSONArray("videos"));
            cause.user_vote = json.getBoolean("voto_utilizador");
            cause.association = new Association(json.getJSONObject("associacao"));
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
            Log.e("Cause exception", "Error parsing JSON");
        }

        return cause;
    }

    private static ArrayList<Pair<URL, String>> parseArray(JSONArray jsonArray) throws JSONException, MalformedURLException {
        ArrayList<Pair<URL, String>> ret = new ArrayList<Pair<URL, String>>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            ret.add(new Pair(new URL(obj.getString("url")), obj.getString("descricao")));
        }
        return ret;
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

    public ArrayList<Pair<URL, String>> getDocuments() {
        return documents;
    }

    public ArrayList<Pair<URL, String>> getVideos() {
        return videos;
    }

    public boolean isUser_vote() {
        return user_vote;
    }

    public Association getAssociation() {
        return association;
    }
}
