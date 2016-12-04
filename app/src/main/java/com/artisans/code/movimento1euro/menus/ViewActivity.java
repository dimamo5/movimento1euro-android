package com.artisans.code.movimento1euro.menus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.elements.Cause;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Filipe on 02/12/2016.
 */

public class ViewActivity extends AppCompatActivity {

    Cause cause;
    private final int lineLimit = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        cause = (Cause) getIntent().getSerializableExtra("Cause");
        setContentView(R.layout.activity_view_cause);

        TextView textBox = (TextView) findViewById(R.id.causeName);
        textBox.setText(cause.getTitle());

        textBox = (TextView) findViewById(R.id.causeInformation);
        textBox.setText(cause.getDescription());

        final TextView descriptionText = (TextView) findViewById(R.id.causeDetailedInformation);
        descriptionText.setText(cause.getIntroduction());
        descriptionText.setMaxLines(lineLimit);

        final TextView readMore = (TextView) findViewById(R.id.moreInformation);
        readMore.setText(Html.fromHtml(getString(R.string.seeMoreInfo)));
        readMore.setTextColor(Color.BLUE);

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

       /* VideoView video = (VideoView) findViewById(R.id.causeVideoView);
        Uri uri=Uri.parse(cause.getVideoLink());
        video.setVideoURI(uri);
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);
        video.setVideoURI(uri);
        video.start();*/

        new ImageLoadTask(cause.getImgLink(), (ImageView) findViewById(R.id.causeImageView)).execute();


    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
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
}
