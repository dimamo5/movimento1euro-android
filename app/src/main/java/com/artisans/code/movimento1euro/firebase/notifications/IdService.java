package com.artisans.code.movimento1euro.firebase.notifications;

import android.util.Log;

import com.artisans.code.movimento1euro.network.ApiManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by duarte on 15-11-2016.
 */

public class IdService extends FirebaseInstanceIdService {
    public final static String TAG = IdService.class.getCanonicalName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Refresh Token: "+ refreshedToken);

        ApiManager.getInstance().updateFirebaseToken(this);

    }
}
