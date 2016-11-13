package com.artisans.code.movimento1euro.menus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.artisans.code.movimento1euro.R;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected String NEWS_URL;
    protected String ABOUT_US_URL;
    protected String CONTACTS_URL;

    TextView username;
    TextView expDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NEWS_URL = getResources().getString(R.string.website_url) + getResources().getString(R.string.news_path);
        ABOUT_US_URL = getResources().getString(R.string.website_url) + getResources().getString(R.string.about_us_path);
        CONTACTS_URL = getResources().getString(R.string.website_url) + getResources().getString(R.string.contacts_path);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        username = (TextView)hView.findViewById(R.id.nav_username);
        expDate = (TextView) hView.findViewById(R.id.nav_expiration_date);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news){
            Uri uri = Uri.parse(NEWS_URL);
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", uri);
            intent.putExtra("label", "Noticias");
            startActivity(intent);
        }else if (id == R.id.nav_about_us){
            Uri uri = Uri.parse(ABOUT_US_URL);
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", uri);
            intent.putExtra("label", "Sobre nós");
            startActivity(intent);
        }else if (id == R.id.nav_contacts){
            Uri uri = Uri.parse(CONTACTS_URL);
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", uri);
            intent.putExtra("label", "Contactos");
            startActivity(intent);
        }else if(id == R.id.nav_logout){
            logout();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();

        LoginManager.getInstance().logOut();

    }

    @Override
    protected void onStart() {
        super.onStart();

        refreshInfo();

    }

    private void refreshInfo() {

        //refreshInfoRequest();

        SharedPreferences preferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        String username = preferences.getString("username", "");

        Date expDate = new Date(preferences.getString("expDate",""));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        String expDateStr = sdf.format(expDate);
        Log.e("username", username);
        Log.e("expDate", expDateStr);

        this.username.setText(username);
        this.expDate.setText(expDateStr);
    }
}
