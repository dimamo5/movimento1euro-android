package com.artisans.code.movimento1euro.menus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.fragments.CauseListFragment;
import com.artisans.code.movimento1euro.fragments.ViewLastCausesFragment;
import com.facebook.login.LoginManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.artisans.code.movimento1euro.fragments.VotingCausesFragment;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CauseListFragment.OnFragmentInteractionListener {

    public static final String TAG = MainMenu.class.getSimpleName();

    protected String NEWS_URL;
    protected String ABOUT_US_URL;
    protected String CONTACTS_URL;

    TextView username;
    TextView expDate;
    ViewLastCausesFragment viewLastCausesFragment;
    VotingCausesFragment viewVotingCausesFragment;
    CausesDetailsActivity viewCauses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Causas em votação");
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        viewVotingCausesFragment = new VotingCausesFragment();
        transaction.replace(R.id.menu_fragment, viewVotingCausesFragment);
        transaction.commit();

        //// TODO: 21/12/2016 REMOVE HARDCODED FIELDS - USE R.string...
        NEWS_URL = getResources().getString(R.string.website_url) + getResources().getString(R.string.news_path);
        ABOUT_US_URL = getResources().getString(R.string.website_url) + getResources().getString(R.string.about_us_path);
        CONTACTS_URL = getResources().getString(R.string.website_url) + getResources().getString(R.string.contacts_path);

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

    public void cardClickLastCauses(View view) {
        viewLastCausesFragment.cardClick(view);
    }

    public void cardClickVotingCauses(View view) {
        viewVotingCausesFragment.cardClick(view);
    }

    public void voteClick(View view){
        viewVotingCausesFragment.vote(view);
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
            new LogoutTask().execute();
        } else if (id == R.id.nav_prev_winners) {
            getSupportActionBar().setTitle("Vencedores Passados");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            viewLastCausesFragment = new ViewLastCausesFragment();
            transaction.replace(R.id.menu_fragment, viewLastCausesFragment);
            transaction.commit();
        } else if (id == R.id.nav_causes) {
            getSupportActionBar().setTitle("Causas em votação");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            viewVotingCausesFragment = new VotingCausesFragment();
            transaction.replace(R.id.menu_fragment, viewVotingCausesFragment);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class LogoutTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            HttpResponse<String> response = null;

            SharedPreferences userDetails = getSharedPreferences("userInfo", MODE_PRIVATE);
            String token = userDetails.getString("token", "");

            try {
                response = Unirest.get(getResources().getString(R.string.api_server_url) + getResources().getString(R.string.logout_path))
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .header("Authorization", token)
                        .asString();
                if (response == null)
                    throw new Exception("Não foi possível carregar as causas passadas. Verifique a sua conexão.");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            logout();
        }
    }

    protected void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();

        LoginManager.getInstance().logOut();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        fillUserDetails();
    }

    private void fillUserDetails() {

        // if necessary, it displays to the user a dialog warning about the proximity of the due date
        new AlertTask(this).execute();

        // fill user info (name and expiration date)
        SharedPreferences preferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        String username = preferences.getString("username", "");

        Date expDate = new Date(preferences.getString("expDate",""));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        String expDateStr = sdf.format(expDate);

        this.username.setText(username);
        this.expDate.setText(expDateStr);
    }

    private void refreshInfo(int delta, String title, String msg) {

        //refreshInfoRequest();

        SharedPreferences preferences = getSharedPreferences("userInfo",MODE_PRIVATE);

        Date expDate = new Date(preferences.getString("expDate",""));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        String expDateStr = sdf.format(expDate);

        Date currentDate = new Date(System.currentTimeMillis());

        long diffDays = TimeUnit.DAYS.convert(expDate.getTime() - currentDate.getTime(), TimeUnit.MILLISECONDS);

        msg = msg.replace("@proxPagamento", Long.toString(diffDays) + " " + (diffDays == 1 ? "dia" : "dias"));

        if (diffDays <= delta) {

            Drawable warningIcon = getResources().getDrawable( android.R.drawable.ic_dialog_alert );
            ColorFilter filter = new LightingColorFilter( Color.YELLOW, Color.BLACK);
            warningIcon.setColorFilter(filter);

            new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(warningIcon)
                    .show();
        }

        this.expDate.setText(expDateStr);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }

    private class AlertTask extends AsyncTask<String, Void, JSONObject> {

        AppCompatActivity activity;

        public AlertTask() {
            super();
        }

        public AlertTask(AppCompatActivity activity) {
            this.activity = activity;
        }

        @Override
        protected JSONObject doInBackground(String... parameters) {

            // Preparation of variables for the request and response handling
            HttpResponse<String> response = null;
            JSONObject result = new JSONObject();
            String token = "";

            try {
                SharedPreferences userDetails = getSharedPreferences("userInfo", MODE_PRIVATE);
                token = userDetails.getString("token", "");
            }catch(Exception e){
                return null;
            }
            // API Request
            try {
                response = Unirest.get(getResources().getString(R.string.api_server_url) + getResources().getString(R.string.days_to_warn_path))
                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        .header("Authorization", token)
                        .asString();
            } catch (UnirestException e) {
                return null;
            }

            try {
                if (response == null) {
                    ConnectivityManager cm =
                            (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();

                    String connectionError = getResources().getString(R.string.user_connection_error);
                    String requestError = getResources().getString(R.string.days_to_warn_request_error);

                    String error = isConnected ? requestError : connectionError;

                    throw new Exception(error);
                }

                JSONObject obj = new JSONObject(response.getBody());
                if (!obj.getString("result").equals(getResources().getString(R.string.api_success_response)))
                    throw new Exception(getResources().getString(R.string.user_loading_authetication_error));

                result.put("active", obj.getBoolean("active"));
                result.put("daysToWarn", obj.getInt("daysToWarn"));
                result.put("title", obj.getString("alertTitle"));
                result.put("msg", obj.getString("alertMsg"));

            } catch (JSONException e) {
                return null;
            } catch (Exception e) {
                // Handle error
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
            }

            return result;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject result) {

            try {
                if (result != null && result.getBoolean("active"))
                    refreshInfo(result.getInt("daysToWarn"), result.getString("title"), result.getString("msg"));
            } catch (JSONException e) {
                // Log.d("causes", e.getMessage());
            }
        }
    }
}
