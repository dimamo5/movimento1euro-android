package com.artisans.code.movimento1euro.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.artisans.code.movimento1euro.models.JSONFields.ASSOCIATION_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.DESCRIPTION_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.ELECTION_TITLE_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.ID_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.INTRODUCTION_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.MONEY_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.VOTES_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.ID_COLUMN;

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
    protected ArrayList<UrlResource> documents = new ArrayList<>();
    protected ArrayList<UrlResource> videos = new ArrayList<>();
    protected Association association;
    protected Election election;
    protected String youtubeThumbnailLink;

    public Cause () {
    }

    // TODO: 13/12/2016 Remover isto para uma classe fora

    public Cause(JSONObject json) {


        try {
            this.id = json.getInt(ID_COLUMN);
            this.description = json.getString(DESCRIPTION_COLUMN);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                this.description = Html.fromHtml(this.description, Html.FROM_HTML_MODE_LEGACY).toString();
            } else {
                this.description = Html.fromHtml(this.description).toString();
            }
            this.money = json.getString(MONEY_COLUMN);
            this.votes = json.getString(VOTES_COLUMN);

            this.association = new Association(json.getJSONObject(ASSOCIATION_COLUMN));
            initializeYouTubeThumbnailLink();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initializeYouTubeThumbnailLink() {
        YoutubeUrlResource ytResource = getFirstYoutubeResource();

        if(ytResource == null){
            this.youtubeThumbnailLink = "";
//            this.youtubeThumbnailLink = "https://img.youtube.com/vi/GDFUdMvacI0/0.jpg";
        }else{
            this.youtubeThumbnailLink = ytResource.getThumbnailLink(0);
        }
    }


    protected static ArrayList<UrlResource> parseUrlArray(JSONArray jsonArray) throws JSONException, MalformedURLException {
        ArrayList<UrlResource> ret = new ArrayList<>();
        String url;
        String description;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            url=obj.getString("url");
            description = obj.getString("descricao");
            if(!YoutubeUrlResource.isValidYoutubeLink(url)){
                ret.add(new UrlResource(new URL(url), description));
            }else{
                try {
                    ret.add(new YoutubeUrlResource(new URL(url), description));
                } catch (Exception e) {
                    e.printStackTrace();
                    ret.add(new UrlResource(new URL(url), description));
                }
            }

        }
        return ret;
    }

    public YoutubeUrlResource getFirstYoutubeResource(){
        for(UrlResource resource : videos){
            if(YoutubeUrlResource.class.isInstance(resource)){
                return (YoutubeUrlResource) resource;
            }
        }

        return null;
    }

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

    public ArrayList<UrlResource> getDocuments() {
        return documents;
    }

    public ArrayList<UrlResource> getVideos() {
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

    public void setDocuments(ArrayList<UrlResource> documents) {
        this.documents = documents;
    }

    public void setVideos(ArrayList<UrlResource> videos) {
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

    public String getYoutubeThumbnailLink() {
        return youtubeThumbnailLink;
    }

    public void setYoutubeThumbnailLink(String youtubeThumbnailLink) {
        this.youtubeThumbnailLink = youtubeThumbnailLink;
    }
}
