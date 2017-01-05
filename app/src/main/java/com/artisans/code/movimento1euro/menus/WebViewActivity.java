package com.artisans.code.movimento1euro.menus;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;

/**
 * Activity to open the various possible webpages related
 * Receives the URL to open specifically from the button clicked on the main menu
 */
public class WebViewActivity extends AppCompatActivity {

    WebView webview;
    /**
     * Swipe Layout to refresh on swipe
     */
    SwipeRefreshLayout webSwipeRefreshLayout;
    boolean firstTimeLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_layout);

        //Log.d("load_url", "onCreate");

        webview = (WebView) findViewById(R.id.webview);
        webSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.web_swipeToRefresh);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        /************************ onStart code for testing ****************************/
        webview.clearCache(true);
        webview.clearHistory();

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        final Activity activity = this;
        // Let's display the progress in the activity name bar, like the
        // browser app does.
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
               // Log.d("progress", "Value of progress: "+progress);
                activity.setProgress(progress * 1000);

                if(progress == 100)
                    webSwipeRefreshLayout.setRefreshing(false);

            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        this.setTitle(getIntent().getExtras().get("label").toString());
        /**************************************************************************************/

        webSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //Log.d("load_url", "Calling Reload");

                        if (webview != null)
                            webview.loadUrl(webview.getUrl());
                        else
                            Log.d("load_url", "webview is null");
                        //Log.d("load_url", "Called Reload");
                    }
                }
        );

        //reset to true each time onCreate is ran
        firstTimeLaunch = true;
        webview.loadUrl(((Uri) getIntent().getExtras().get("url")).toString());

    }

    @Override
    protected void onStart() {
        super.onStart();

        webview = (WebView) findViewById(R.id.webview);

        if(firstTimeLaunch) {
            //Log.d("load_url", "onStart - firstTimeLaunch is true, didn't load url again");
            firstTimeLaunch = false;
        }else{
            webview.loadUrl(((Uri) getIntent().getExtras().get("url")).toString());
            //Log.d("load_url", "onStart - firstTimeLaunch is false, reloading url");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            NavUtils.navigateUpFromSameTask(this);
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}