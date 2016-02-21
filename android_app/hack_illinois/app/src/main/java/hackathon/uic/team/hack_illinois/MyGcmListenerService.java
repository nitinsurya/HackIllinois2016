package hackathon.uic.team.hack_illinois;

import com.google.android.gms.gcm.GcmListenerService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class MyGcmListenerService extends GcmListenerService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.i("Hack",message);
        createNotification(from, message);
    }

    // Creates notification based on title and body received
    private void createNotification(String title, String body) {

        Context context = getBaseContext();
        Resources res = context.getResources();
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setTicker(body)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(body);


        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = builder.getNotification();

        n.defaults |= Notification.DEFAULT_ALL;
        nm.notify(0, n);



    }

}