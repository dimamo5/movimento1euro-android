package com.artisans.code.movimento1euro.menus;


import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.TestMethod;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.fragments.VoteDialog;
import com.artisans.code.movimento1euro.models.VotingCause;
import com.artisans.code.movimento1euro.network.VotingTask;
import com.artisans.code.movimento1euro.models.Cause;
import com.artisans.code.movimento1euro.models.UrlResource;
import com.artisans.code.movimento1euro.youtube.DeveloperKey;
import com.artisans.code.movimento1euro.youtube.YouTubeFailureRecoveryActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Filipe on 02/12/2016.
 */

public class CausesDetailsActivity extends YouTubeFailureRecoveryActivity {

    Cause cause;
    private final int lineLimit = 5;
    private String facebookUrl;
    private String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        cause = (Cause) getIntent().getSerializableExtra("Cause");
        setContentView(R.layout.activity_view_cause);

        android.support.v7.widget.Toolbar tb2 = (Toolbar) findViewById(R.id.toolbar);
        tb2.setVisibility(View.GONE);

        android.widget.Toolbar toolbar = (android.widget.Toolbar) findViewById(R.id.toolbar_normal);
        toolbar.setVisibility(View.VISIBLE);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);

        this.setTitle("Causa");

        fillFields(cause);
    }


    private void fillFields(Cause cause) {
        TextView textBox;

        if (cause.getName() != null) {
            textBox = (TextView) findViewById(R.id.cause_name);
            SpannableString spanString = new SpannableString(cause.getName());
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            textBox.setText(spanString);
        }

        if (cause.getIntroduction() != null) {
            textBox = (TextView) findViewById(R.id.cause_destiny);
            textBox.setText(cause.getIntroduction());
        } else {
            textBox = (TextView) findViewById(R.id.cause_destiny);
            textBox.setText("Não existe uma descrição disponível.");
        }

        if (cause.getDescription() != null) {
            final TextView descriptionText = (TextView) findViewById(R.id.cause_detail_info);
            descriptionText.setText(cause.getDescription());
            descriptionText.setMaxLines(lineLimit);

            final TextView readMore = (TextView) findViewById(R.id.moreInformation);
            readMore.setText(Html.fromHtml(getString(R.string.seeMoreInfo)));

            readMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (descriptionText.getMaxLines() != Integer.MAX_VALUE) {
                        descriptionText.setMaxLines(Integer.MAX_VALUE);
                        readMore.setText(Html.fromHtml(getString(R.string.seeLessInfo)));

                    } else {
                        descriptionText.setMaxLines(lineLimit);
                        readMore.setText(Html.fromHtml(getString(R.string.seeMoreInfo)));
                    }
                }
            });
        }


        setupSocialMediaButtons(cause);

        for(UrlResource doc : cause.getDocuments()){
            addDocument(doc);
        }

        try {
            addDocument(new UrlResource(new URL("http://google.com"), "Googlerino"));
            addDocument(new UrlResource(new URL("http://google.com"), "Googlerino123"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void addDocument(final UrlResource doc) {
        LayoutInflater inflater = LayoutInflater.from(this);


        /*LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5,5,5,5);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView docImage = new ImageView(this);
        docImage.setImageResource(R.drawable.ic_description_black_24dp);
        layout.addView(docImage);
*/

        LinearLayout layout = (LinearLayout)  inflater.inflate(R.layout.documents_fragment,null, false);

        TextView link = (TextView) layout.findViewById(R.id.doc_description);
        link.setText(doc.getDescription());
        link.setPaintFlags(link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        link.setClickable(true);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(doc.getUrl().toString()));
                startActivity(i);
            }
        });
        //layout.addView(link);

        LinearLayout documents = (LinearLayout) findViewById(R.id.documents_layout);
        documents.addView(layout);
    }


    private void setupSocialMediaButtons(Cause cause) {
        final ImageButton facebookButton = (ImageButton) findViewById(R.id.facebook_url_button);
        facebookUrl = cause.getAssociation().getFacebook();
        facebookUrl = "https://www.facebook.com/duarte.pinto.9";

        //if (facebookUrl == null || facebookUrl.equals("")){
        if(false){
            facebookButton.setVisibility(View.GONE);
        }else {

            facebookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(facebookUrl));
                    startActivity(i);
                }
            });

        }
        ImageButton webButton = (ImageButton) findViewById(R.id.web_url_button);
        webUrl = cause.getAssociation().getWebsite();
//        webUrl = "https://google.com";
        if(webUrl == null || webUrl.equals("")){
            webButton.setVisibility(View.GONE);
        }else{
            webButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(webUrl));
                    startActivity(i);
                }
            });
        }

        // TODO: 20/12/2016 Adicionar suport para o instagram
        //findViewById(R.id.instagram_url_button).setVisibility(View.GONE);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!(b || cause.getAssociation().getYoutube().equals(""))) {
            youTubePlayer.cueVideo(cause.getAssociation().getYoutube());

        } else { //no video or error
            //release all resources related to youtubePlayer
            youTubePlayer.release();
        }

        //youTubePlayer.cueVideo("KKelGIXYmXE");
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

    public void vote(final View view) {
        new VoteDialog(this, (VotingCause) cause).create().show();
    }


    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask() {
        }

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        public void setFields(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
    /*@Override
    public void onBackPressed() {
        imgTask.imageView.destroyDrawingCache();
    }*/


}
