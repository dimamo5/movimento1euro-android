package com.artisans.code.movimento1euro.elements;

import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Antonio on 30-11-2016.
 */

public class Cause implements Serializable {
    private int id;
    private String title;
    private String description;
    private String money;
    private String votes;
    private String slogan;
    private String introduction;
    private String imgLink;
    private String videoLink;
    private ArrayList<Pair<URL, String>> documents = new ArrayList<Pair<URL, String>>();
    private ArrayList<Pair<URL, String>> videos = new ArrayList<Pair<URL, String>>();
    private boolean user_vote;
    private Association association;

    public Cause () {
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
           // cause.slogan=json.getString("slogan");
            cause.introduction=json.getString("descricao_breve");
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

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getImgLink() {
        return imgLink;
    }

    public String getVideoLink() {
        return videoLink;
    }
}
