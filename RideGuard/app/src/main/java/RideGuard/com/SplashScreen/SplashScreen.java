package RideGuard.com.SplashScreen;

import static android.Manifest.permission.READ_SMS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import RideGuard.com.Accident.AccidentScreen;
import RideGuard.com.MainActivity;
import RideGuard.com.R;
import RideGuard.com.RideGuardGlobalVariables;
import RideGuard.com.RideGuardNotificationChannel.RideGuardNotifications;
import RideGuard.com.databinding.ActivitySplashScreenBinding;

/*
This class will be used as our Splash screen ,here we will ask for Permissions to Read and
Receive SMS and we will also Create Notification Channel.
 */
public class SplashScreen extends AppCompatActivity {

    /*
    Creating object of the Binding class.
     */
    private ActivitySplashScreenBinding binding;

    /*
    Notification Manager.
     */
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Inflating.
         */
        binding=ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_splash_screen);
        /*
        Checking if the Notification channel exists or not
         */
        if(!CheckChannel()){
            /*
            Channel doesn't exists.
             */
            createNotificationChannel();
        }

         /*
        Checking for Permissions ,calling the method for Requesting permission to the user to read SMS.
         */
        if (ActivityCompat.checkSelfPermission(this, READ_SMS) == PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)==PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted
            proceedToMainActivity();
        } else {
            // Permission is not granted, request it.
            requestSMSPermission();
        }
    }

    /*
    Method to check if notification Channel Exists or not.
     */
    private  boolean CheckChannel(){
        /*
    Getting Notification manager and checking for Channel.
    */
        NotificationManager manager = returnManager();

        if (manager != null) {
            NotificationChannel channel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channel = manager.getNotificationChannel(RideGuardGlobalVariables.ChannelId);
            }
            return channel != null; // Return true if the channel exists, false otherwise
        }

        return false; // Return false if NotificationManager is null
    }

    /*
    Method to get Notification Manager.
     */
    private  NotificationManager returnManager(){
        manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        return  manager;
    }
    /*
    Method to create Notification Channel.
     */
    private void createNotificationChannel() {
        RideGuardNotifications rideGuardNotifications = new RideGuardNotifications(this);
        rideGuardNotifications.CreateChannel(RideGuardGlobalVariables.ChannelId,RideGuardGlobalVariables.ChannelName);
    }
    /*
    This method will request permission for reading SMS and then it will perform further action.
*/
    private void requestSMSPermission() {
    /*
        Using Dexter library to handle runtime permissions.
    */
        Dexter.withContext(SplashScreen.this)
                .withPermissions(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    /*
                        Check if all requested permissions are granted.
                    */
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            /*
                            Permissions are granted,proceed to the main activity.
                             */
                            proceedToMainActivity();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    /*
                         user has denied a permission and
                        selected the "Don't ask again" option.
                        Continue with the permission request.
                    */
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    /*
       Method to proceed to the main activity after obtaining permissions.
    */
    private void proceedToMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }

}