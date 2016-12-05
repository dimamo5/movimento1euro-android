package com.artisans.code.movimento1euro.menus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.elements.Cause;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Filipe on 02/12/2016.
 */

public class ViewActivity extends AppCompatActivity {

    Cause cause;
    private final int lineLimit = 5;
    ImageLoadTask imgTask = new ImageLoadTask();
    ImageLoadTask videoTask = new ImageLoadTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        cause = (Cause) getIntent().getSerializableExtra("Cause");
        setContentView(R.layout.activity_view_cause);

        fillFields(cause);


    }

    private void fillFields(Cause cause) {
        TextView textBox;

        if (cause.getImgLink() != null) {
            imgTask.setFields(cause.getImgLink(), (ImageView) findViewById(R.id.causeImageView));
            imgTask.execute();

        } else {

            imgTask.setFields("",(ImageView) findViewById(R.id.causeImageView));
            imgTask.getImageView().setImageResource(R.drawable.logo);
        }


        if (cause.getAssociation() != null) {
            if (cause.getAssociation().getName() != null) {
                getSupportActionBar().setTitle(cause.getAssociation().getName());
            }
        }
        if (cause.getTitle() != null) {
            textBox = (TextView) findViewById(R.id.causeSlogan);
            SpannableString spanString = new SpannableString(cause.getTitle());
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            textBox.setText(spanString);
        }

        if (cause.getIntroduction() != null) {
            textBox = (TextView) findViewById(R.id.causeInformation);
            textBox.setText(cause.getIntroduction());
        }else{
            textBox = (TextView) findViewById(R.id.causeInformation);
            textBox.setText("No Introduction available");
        }

        if (cause.getDescription() != null) {
            final TextView descriptionText = (TextView) findViewById(R.id.causeDetailedInformation);
            descriptionText.setText(cause.getDescription());
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
        }
        videoTask.setFields(getString(R.string.youtubeImg), (ImageView) findViewById(R.id.causeVideoView));
        videoTask.execute();
            if(cause.getVideos() != null && cause.getVideos().size() >0){

                final String videoLink=cause.getVideos().get(0).second;
                findViewById(R.id.causeVideoView).setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(videoLink));
                        startActivity(intent);
                    }
                });
            }else{
                final String videoLink=getString(R.string.testVideoLink);
                findViewById(R.id.causeVideoView).setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(videoLink));
                        startActivity(intent);
                    }
                });
            }
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
