package com.artisans.code.movimento1euro.models;

import java.io.Serializable;
import java.net.URL;


/**
 * Created by Duart on 20/12/2016.
 */

public class UrlResource implements Serializable {
    String description;
    URL url;

    public UrlResource(URL url,String description) {
        this.description = description;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return description + ": "+ url.toString();
    }
}
