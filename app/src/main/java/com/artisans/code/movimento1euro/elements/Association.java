package com.artisans.code.movimento1euro.elements;

import android.os.Build;
import android.text.Html;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Antonio on 30-11-2016.
 */

public class Association {
    private String name;
    private String presentation;
    private String address;
    private String landline;
    private String mobilePhone;
    private String website;
    private String email;
    private String facebook;
    private String youtube;

    Association(JSONObject json) {
        try {
            name = json.getString("nome");
            presentation = json.getString("apresentacao");
            address = removeTags(json.getString("morada"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                address = Html.fromHtml(address, Html.FROM_HTML_MODE_LEGACY).toString();
            } else {
                address = Html.fromHtml(address).toString();
            }
            landline = json.getString("telefone");
            mobilePhone = json.getString("telemovel");
            website = json.getString("website");
            email = json.getString("email");
            facebook = json.getString("facebook");
            youtube = json.getString("youtube");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Association exception", "Error parsing JSON");
        }
    }

    public String removeTags(String in)
    {
        int index=0;
        int index2=0;
        while(index!=-1)
        {
            index = in.indexOf("<");
            index2 = in.indexOf(">", index);
            if(index!=-1 && index2!=-1)
            {
                in = in.substring(0, index).concat(in.substring(index2+1, in.length()));
            }
        }
        return in;
    }

    public String getName() {
        return name;
    }

    public String getPresentation() {
        return presentation;
    }

    public String getAddress() {
        return address;
    }

    public String getLandline() {
        return landline;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getYoutube() {
        return youtube;
    }
}
