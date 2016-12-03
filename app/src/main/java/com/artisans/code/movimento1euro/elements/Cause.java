package com.artisans.code.movimento1euro.elements;

import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Antonio on 30-11-2016.
 */

public class Cause {
    private int id;
    private String title;
    private String description;
    private String money;
    private String votationMoney;
    private String votes;
    private ArrayList<Pair<URL, String>> documents = new ArrayList<Pair<URL, String>>();
    private ArrayList<Pair<URL, String>> videos = new ArrayList<Pair<URL, String>>();
    private boolean user_vote;
    private Association association;

    public Cause () {
    }

    public class Constants {
        public static final String TITLE_COLUMN = "Title";
        public static final String DESCRIPTION_COLUMN = "Description";
        public static final String MONEY_COLUMN = "Money";
        public static final String VOTES_COLUMN = "Votes";
        public static final String VERBA_COLUMN = "Verba";
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

    public static Cause parsePastCause(JSONObject json) {
        Cause cause = new Cause();

        try {
            cause.id = json.getInt("id");
            cause.title = json.getString("titulo");
            cause.description = json.getString("descricao");
            cause.money = json.getString("verba");
            cause.votationMoney = json.getString("montante_disponivel");
            cause.votes = json.getString("votos");
            cause.association = new Association(json.getJSONObject("associacao"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Cause exception", "Error parsing JSON");
            Log.d("past", "Error: "  + e.getMessage());
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

    public HashMap<String, String> toHashMap(){
        HashMap<String, String> hashMap = new HashMap<String,String>();

        hashMap.put(Constants.TITLE_COLUMN, title);
        hashMap.put(Constants.DESCRIPTION_COLUMN, description);
        hashMap.put(Constants.MONEY_COLUMN, votationMoney + " €");
        hashMap.put(Constants.VOTES_COLUMN, votes+ " votes");
        hashMap.put(Constants.VERBA_COLUMN, money + " €");

        return hashMap;
    }

    public String toString(){
        return "ID: " + id + ", Title: " + title + ", Description: " + description + ", Votes: " + votes + ", Money: " + money + ", Votation Money: " + votationMoney;
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

    public String getVotationMoney() { return votationMoney; }

    public void setVotationMoney(String votationMoney) { this.votationMoney = votationMoney; }

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
