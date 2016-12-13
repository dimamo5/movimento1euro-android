package com.artisans.code.movimento1euro.menus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.models.Cause;
import com.artisans.code.movimento1euro.youtube.DeveloperKey;
import com.artisans.code.movimento1euro.youtube.YouTubeFailureRecoveryActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Filipe on 02/12/2016.
 */

public class CausesDetailsActivity extends YouTubeFailureRecoveryActivity {

    Cause cause;
    private final int lineLimit = 5;

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
        }else{
            textBox = (TextView) findViewById(R.id.cause_destiny);
            textBox.setText("No Introduction available");
        }

        if (cause.getDescription() != null) {
            final TextView descriptionText = (TextView) findViewById(R.id.causeDetailedInformation);
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

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.cueVideo("nCgQDjiotG0");
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
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
        public ImageView getImageView(){
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
