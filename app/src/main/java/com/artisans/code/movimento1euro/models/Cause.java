package com.artisans.code.movimento1euro.models;

import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Antonio on 30-11-2016.
 */

public class Cause implements Serializable {
    private int id;
    private String description;
    private String money;
    private String votes;
    private String title;
    private String introduction;
    private ArrayList<Pair<URL, String>> documents = new ArrayList<Pair<URL, String>>();
    private ArrayList<Pair<URL, String>> videos = new ArrayList<Pair<URL, String>>();
    private boolean user_vote;
    private Association association;
    private Election election;

    public Cause () {
    }

    public class Constants {
        public static final String ELECTION_TITLE_COLUMN = "Election_Title";
        public static final String TITLE_COLUMN = "Title";
        public static final String NAME_COLUMN = "Name";
        public static final String DESCRIPTION_COLUMN = "Description";
        public static final String MONEY_COLUMN = "Money";
        public static final String VOTES_COLUMN = "Votes";
        public static final String ELECTION_MONEY_COLUMN = "Election_Money";
    }

    public static Cause parseVotingCause(JSONObject json) {
        Cause cause = new Cause();

        try {
            cause.id = json.getInt("id");
            cause.title = json.getString("titulo");
            cause.description = json.getString("descricao");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cause.description = Html.fromHtml(cause.description, Html.FROM_HTML_MODE_LEGACY).toString();
            } else {
                cause.description = Html.fromHtml(cause.description).toString();
            }
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
            cause.title = json.getString("nome");
            cause.description = json.getString("descricao");
            cause.money = json.getString("verba");
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
            ret.add(new Pair<>(new URL(obj.getString("url")), obj.getString("descricao")));
        }
        return ret;
    }

    // HashMap with pertinent info for the list appearance
    public HashMap<String, String> toHashMap(){
        HashMap<String, String> hashMap = new HashMap<String,String>();

        hashMap.put(Constants.ELECTION_TITLE_COLUMN, election.getTitle());
        hashMap.put(Constants.TITLE_COLUMN, title);
        hashMap.put(Constants.DESCRIPTION_COLUMN, description);
        hashMap.put(Constants.VOTES_COLUMN, votes+ " votos");
        hashMap.put(Constants.MONEY_COLUMN, "Verba: " + money + " € requirido");

        if(election != null)
            hashMap.put(Constants.NAME_COLUMN, election.getTitle());

        return hashMap;
    }

   /* public String toString(){
        return "ID: " + id + ", Title: " + title + ", Description: " + description + ",\n Votes: " + votes + ", Money: " ;
    }*/

    @Override
    public String toString() {
        return "Cause{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", money='" + money + '\'' +
                ", votes='" + votes + '\'' +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", ytbLink='" + association.getYoutube() + '\'' +
                ", documents=" + documents +
                ", videos=" + videos +
                ", user_vote=" + user_vote +
                ", association=" + association +
                ", election=" + election +
                '}';
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

    public Election getElection() { return election; }

    public void setElection(Election election) { this.election = election; }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public void setDocuments(ArrayList<Pair<URL, String>> documents) {
        this.documents = documents;
    }

    public void setVideos(ArrayList<Pair<URL, String>> videos) {
        this.videos = videos;
    }

    public void setUser_vote(boolean user_vote) {
        this.user_vote = user_vote;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIntroduction() {
        return introduction;
    }


}
