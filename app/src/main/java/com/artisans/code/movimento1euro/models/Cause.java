package com.artisans.code.movimento1euro.models;

import android.os.Build;
import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.artisans.code.movimento1euro.models.JSONFields.ASSOCIATION_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.DESCRIPTION_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.ID_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.MONEY_COLUMN;
import static com.artisans.code.movimento1euro.models.JSONFields.VOTES_COLUMN;

/**
 * Represents a cause with all its information
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

    /**
     * Constructor. Parses a JSON object to construct the cause.
     * @param json JSON Object containing information for the cause
     */
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
            this.videos = parseUrlArray(json.getJSONArray("videos"));
            this.documents = parseUrlArray(json.getJSONArray(JSONFields.DOCUMENTS_ARRAY_COLUMN));

            initializeYouTubeThumbnailLink();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /**
     * If there was an youtube resource, it initializes the thumbnail with the correct info from the resource.
     */
    private void initializeYouTubeThumbnailLink() {
        YoutubeUrlResource ytResource = getFirstYoutubeResource();

        if(ytResource == null){
            this.youtubeThumbnailLink = "";
//            this.youtubeThumbnailLink = "https://img.youtube.com/vi/GDFUdMvacI0/0.jpg";
        }else{
            this.youtubeThumbnailLink = ytResource.getThumbnailLink(0);
        }
    }

    /**
     * Parses a JSON object containing various URLs. These are URLs for the cause's resources, like images, videos and other documents
     * @param jsonArray JSON Object containing the URLs to parse
     * @return list of UrlResources parsed from the object
     * @throws JSONException JSON Malformed
     * @throws MalformedURLException URL Malformed
     */
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

    /**
     * Get first video available on the resources
     * @return Returns an YoutubeUrlResource containing the information for the video, if there is any video.
     */
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
