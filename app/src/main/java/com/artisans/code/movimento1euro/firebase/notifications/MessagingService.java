package com.artisans.code.movimento1euro.firebase.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.artisans.code.movimento1euro.R;
import com.artisans.code.movimento1euro.menus.MainMenu;
import com.artisans.code.movimento1euro.network.SeenNotificationTask;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by duarte on 15-11-2016.
 */

public class MessagingService extends FirebaseMessagingService {
    private final String TAG = MessagingService.class.getCanonicalName();
    private static String notificationId;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "FROM" + remoteMessage.getFrom());

        if(remoteMessage.getData().size() > 0){
            Log.d(TAG, "Message data: " +remoteMessage.getData());
            notificationId = remoteMessage.getMessageId();
            sendNotification(remoteMessage.getMessageId(), remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
        }
    }

    private void sendNotification(String id, String title, String body) {

        Intent intent = new Intent(this, MainMenu.class); // AINDA NÃO TENHO A CERTEZA SE COLOCO MainMenu.class ou SplashScreen.class
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fromNotification", true);  //this values are checked out on MainMenu.class
        intent.putExtra("notificationID", id);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Intent deleteIntent = new Intent(this, MyBroadcastReceiver.class);
        deleteIntent.putExtra("com.my.app.notificationId", notificationId);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 0, deleteIntent, 0);//mudar one shot para 0 caso esteja a dar peido

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(notificationSound);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(body));

        notificationBuilder.setDeleteIntent(pendingIntent2);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }

    //dismiss notification related
    public static class MyBroadcastReceiver extends BroadcastReceiver {

        public MyBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            new SeenNotificationTask(context).execute(notificationId);
        }

    }

}
