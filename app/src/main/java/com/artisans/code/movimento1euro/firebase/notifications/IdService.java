package com.artisans.code.movimento1euro.firebase.notifications;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by duarte on 15-11-2016.
 */

public class IdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(this.toString(),"Refresh Token: "+ refreshedToken);

        // TODO: 15-11-2016 Mandar token para o servidor

    }
}
