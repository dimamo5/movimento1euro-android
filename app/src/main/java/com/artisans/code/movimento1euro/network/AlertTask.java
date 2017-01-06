package com.artisans.code.movimento1euro.network;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.menus.MainMenu;
import com.mashape.unirest.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Asynchronous task to refresh the alert settings from the API. After a successful request it launches the expiration alert.
 */
public class AlertTask extends ApiRequestTask{

    MainMenu activity;


    public AlertTask(MainMenu activity) {
        super(activity);
        setMethod(Request.GET);
        this.activity = activity;
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {

        // Preparation of variables for the request and response handling
        HttpResponse<String> response = null;
        JSONObject result = new JSONObject();

        urlString = context.getString(R.string.api_server_url)+context.getString(R.string.days_to_warn_path);

        try {

            JSONObject obj = executeRequest();

            //APAGUEI O AVISO DE CONECTIVIDADE POIS ACHEI QUE NÃO FAZIA SENTIDO MOSTAR UM AVISO DE NÃO HAVER INTERNET QUANDO É UM PEDIDO FEITO NO BACKGROUND- Duarte
            /*if(obj == null){
                checkConnectivity();
            }*/

            if (!obj.getString("result").equals(context.getString(R.string.api_success_response)))
                throw new Exception(context.getString(R.string.user_loading_authetication_error));

            result.put("active", obj.getBoolean("active"));
            result.put("daysToWarn", obj.getInt("daysToWarn"));
            result.put("title", obj.getString("alertTitle"));
            result.put("msg", obj.getString("alertMsg"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(JSONObject result) {

        try {

            ApiManager.getInstance().updateExpirationAlertInfo(context, result.getBoolean("active"), result.getInt("daysToWarn"),result.getString("title"),result.getString("msg"));

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        activity.showExpirationDateAlert();
    }
}