package com.artisans.code.movimento1euro.models;

import android.os.Build;
import android.text.Html;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.artisans.code.movimento1euro.models.JSONFields.ASSOCIATION_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.DESCRIPTION_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.ID_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.INTRODUCTION_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.MONEY_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.NAME_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.VOTES_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.ID_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.NAME_COLUMN;

/**
 * Created by Antonio on 30-11-2016.
 */

public class Cause implements Serializable {
    protected int id;
    protected String description;
    protected String money;
    protected String votes;
    protected String name;
    protected String introduction;
    protected String imgLink;
    protected ArrayList<Pair<URL, String>> documents = new ArrayList<Pair<URL, String>>();
    protected ArrayList<Pair<URL, String>> videos = new ArrayList<Pair<URL, String>>();
    protected Association association;
    protected Election election;

    public Cause () {
    }

    // TODO: 13/12/2016 Remover isto para uma classe fora

    public Cause(JSONObject json) {


        try {
            this.id = json.getInt(ID_COLUMN);
            this.name = json.getString(NAME_COLUMN);
            this.introduction = json.getString(INTRODUCTION_COLUMN);
            this.description = json.getString(DESCRIPTION_COLUMN);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                this.description = Html.fromHtml(this.description, Html.FROM_HTML_MODE_LEGACY).toString();
            } else {
                this.description = Html.fromHtml(this.description).toString();
            }
            this.money = json.getString(MONEY_COLUMN);
            this.votes = json.getString(VOTES_COLUMN);

            this.association = new Association(json.getJSONObject(ASSOCIATION_COLUMN));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    protected static ArrayList<Pair<URL, String>> parseUrlArray(JSONArray jsonArray) throws JSONException, MalformedURLException {
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

        hashMap.put(JSONFields.ELECTION_TITLE_COLUMN, election.getTitle());
        hashMap.put(NAME_COLUMN, name);
        hashMap.put(JSONFields.DESCRIPTION_COLUMN, description);
        hashMap.put(JSONFields.VOTES_COLUMN, votes+ " votos");
        hashMap.put(JSONFields.MONEY_COLUMN, "Verba: " + money + " € requirido");

        if(election != null)
            hashMap.put(NAME_COLUMN, election.getTitle());

        return hashMap;
    }

   /* public String toString(){
        return "ID: " + id + ", Title: " + name + ", Description: " + description + ",\n Votes: " + votes + ", Money: " ;
    }*/

    @Override
    public String toString() {
        return "Cause{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", money='" + money + '\'' +
                ", votes='" + votes + '\'' +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", ytbLink='" + association.getYoutube() + '\'' +
                ", documents=" + documents +
                ", videos=" + videos +
                ", association=" + association +
                ", election=" + election +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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


    public Association getAssociation() {
        return association;
    }

    public Election getElection() { return election; }

    public void setElection(Election election) { this.election = election; }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
