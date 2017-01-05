package com.artisans.code.movimento1euro.models;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * URL Resource specifically for youtube links
 */

public class YoutubeUrlResource extends UrlResource {

    public final static String THUMBNAIL_LINK = "https://img.youtube.com/vi/VIDEO_ID/default.jpg";

    String videoId;

    /**
     * Creates the youtube resource, validating URL, extracting ID and setting a description
     * @param url Resource URL
     * @param description Resource description
     * @throws Exception
     */
    public YoutubeUrlResource(URL url, String description) throws Exception {
        super(url, description);
        if(!isValidYoutubeLink(url.toString())){
            throw new Exception("Url doesn't match a valid YouTube link");
        }

        this.videoId = extractYoutubeVideoId(url.toString());

    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * Validates if URL is a valid youtube link youtube URL
     * @param link URL in string form
     * @return True if it is a valid youtube link, false otherwise
     */
    protected static boolean isValidYoutubeLink(String link){
        String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(link);
        return matcher.matches();
    }

    /**
     * Get video ID from the link
     * @param link Youtube URL
     * @return Youtube video ID
     */
    protected static String extractYoutubeVideoId(String link){
        if(!isValidYoutubeLink(link)){
            return null;
        }{
            String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(link);

            if(matcher.find()){
                return matcher.group();
            }else{
                return null;
            }
        }
    }

    protected String getThumbnailLink(){
        return THUMBNAIL_LINK.replaceAll("VIDEO_ID",getVideoId());
    }

    protected String getThumbnailLink(int index){
        return THUMBNAIL_LINK.replaceAll("VIDEO_ID",getVideoId()).replaceAll("default",""+index);
    }
}
