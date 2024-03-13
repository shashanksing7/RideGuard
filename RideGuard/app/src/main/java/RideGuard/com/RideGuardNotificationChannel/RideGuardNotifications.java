package RideGuard.com.RideGuardNotificationChannel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.opengl.Visibility;

import RideGuard.com.R;

/*
This is our class  that will be used to create Notification Channel.
 */
public class RideGuardNotifications extends ContextWrapper {
    public RideGuardNotifications(Context base) {
        super(base);
    }
    /*
    Method to Create the Notification Channel.
     */
    public void CreateChannel(String ChannelId, String ChannelName){
        /*
        Creating Notification Channel
         */
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel(ChannelId,ChannelName, NotificationManager.IMPORTANCE_HIGH);
            // Enable lights for notifications
            channel.enableLights(true);
            // Set visibility on the lockscreen (1 means VISIBILITY_PUBLIC)
            channel.setLockscreenVisibility(1);
            // Enable vibration for notifications
            channel.enableVibration(true);
            // Get the NotificationManager
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                // Create the notification channel
                manager.createNotificationChannel(channel);
            }
        }

    }
}
