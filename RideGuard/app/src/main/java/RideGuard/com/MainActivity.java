package RideGuard.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import RideGuard.com.Accident.AccidentScreen;
import RideGuard.com.BroadCastReceiver.SMSReceiver;
import RideGuard.com.databinding.ActivityMainBinding;

/*
This is thw main activity that will used as splash  screen and it will be used to request for
Permission for Location and SMS.
 */
public class MainActivity extends AppCompatActivity {

    /*
    Creating the object of the ViewBinding Class for this activity.
     */
    private ActivityMainBinding binding;
    /*
    Variables ofr extracting message form SMS
     */
    private static final int REQ_USER_CONSENT = 200;

    SMSReceiver.SmsBroadcastReceiverListener smsBroadcastReceiverListener;

    SMSReceiver smsReceiver;
    /*
    Variables for Updating.
     */
    private static final  String CONNECTED="connected";
    private static  final  String NOT_CONNECTED="not connected";
    private static final String HELMET_WORN="helmet worn";
    private static final String HELMET_NOT_WORN="helmet not worn";
    private static  final  String BLOOD_LEVEL="blood level";
    private  static final String ACCIDENT="accident";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Inflating the view of the class.
         */

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CheckAndUpdateUi();
        startSmartUserConsent();



    }

    /*
this method creates the consent flow,this flow prompts the user to grant the permission to the
app to read otp from message.once the permission is granted this automatically
listens for incoming sms message that match the app's registered hash string.
 */
    private void startSmartUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);
        // Start the SMS user consent flow
        Task<Void> task = client.startSmsUserConsent(null);

        // Attach a listener to handle the result of the task
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Consent flow started successfully
                Log.d("mytag", "startSmartUserConsent: Consent flow started successfully");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Consent flow could not be started
                Log.e("mytag", "startSmartUserConsent: Consent flow could not be started", e);
            }
        });

    }

    /*
    This methods return the result of the operation performed by the StartActivityForResult()
    method.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*
        Checking if the method is returning result fo the desired operation.
         */
        if (requestCode == REQ_USER_CONSENT) {

            /*
            checking the result of the operation if the operation is successful
            get otp from the message.
             */

            if ((resultCode == RESULT_OK) && (data != null)) {

                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                Log.d("mytag", "onActivityResult:result "+message);
                /*
                Updating UI on the Basis of Messages Received.
                 */
                if(message.contains("Ride Guard")){
                    /*
                    Getting the
                     */
                    String requiredMessage=message.substring(18);
                    Log.d("mytag", "onActivityResult: switch case "+requiredMessage);
                    /*
                    Calling the method to update
                     */
                    updateUI(requiredMessage);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /*
    this methods registers the broadcast receiver and start the onreceive() method.
    and listens for intent and extracts the status object through the on receive method defined in the
    VerifyOTPBroadcastReceiver class.and invoke s the StartActivityForResultMethod()
     */
    private void registerBroadcastReceiver() {
        Log.d("mytag", "registerBroadcastReceiver: ");

        smsReceiver=new SMSReceiver(new SMSReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                Log.d("mytag", "onSuccess: register intent");
                startActivityForResult(intent,REQ_USER_CONSENT);
                startSmartUserConsent();
            }

            @Override
            public void onFailure() {
                Log.d("mytag", "onFailure: register intent");
                startSmartUserConsent();

            }
        });

        /*
        Registering the intent filter to listen for intents to retrieved otp
         */
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
       registerReceiver(smsReceiver, intentFilter);
        Log.d("mytag", "registerBroadcastReceiver intent: ");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }

    /*
    method to update UI on basis of message received.
     */
    private void updateUI(String message) {
    /*
    Using switch to update UI.
    */
//        Log.d("mytag", "updateUI: ");
//        switch (message) {
//            case CONNECTED:
//                RideGuardGlobalVariables.Connected = true;
//                binding.Connected.setText("Connected");
//                binding.Connected.setBackgroundColor(getColor(R.color.NotConnectd));
//                break;
//            case NOT_CONNECTED:
//                RideGuardGlobalVariables.Connected = false;
//                binding.Connected.setText("Not Connected");
//                binding.Connected.setBackgroundColor(getColor(R.color.mainactivitytextviewbg));
//                break;
//            case HELMET_WORN:
//                RideGuardGlobalVariables.HelmetWorn = true;
//                binding.HelmetWorn.setText("Helmet Worn");
//                binding.HelmetWorn.setBackgroundColor(getColor(R.color.NotConnectd));
//                break;
//            case HELMET_NOT_WORN:
//                RideGuardGlobalVariables.HelmetWorn = false;
//                binding.HelmetWorn.setText("Helmet not worn");
//                binding.HelmetWorn.setBackgroundColor(getColor(R.color.mainactivitytextviewbg));
//                break;
//            case ACCIDENT:
//            /*
//            Accident Taking User to Accident Activity.
//            */
//                Intent intent = new Intent(MainActivity.this, AccidentScreen.class);
//                startActivity(intent);
//                finish();
//                break;
//            default:
//                Log.d("mytag", "updateUI: default " + message);
//                break;

//    }
        Log.d("mytag", "updateUI: message = " + message);

        if (message.equals(CONNECTED)) {
            RideGuardGlobalVariables.Connected = true;
            binding.Connected.setText("Connected");
            binding.Connected.setBackgroundColor(getColor(R.color.NotConnectd));
            Log.d("mytag", "updateUI: CONNECTED branch");
        } else if (message.equals(NOT_CONNECTED)) {
            RideGuardGlobalVariables.Connected = false;
            binding.Connected.setText("Not Connected");
            binding.Connected.setBackgroundColor(getColor(R.color.mainactivitytextviewbg));
            Log.d("mytag", "updateUI: NOT_CONNECTED branch");
        } else if (message.equals(HELMET_WORN)) {
            RideGuardGlobalVariables.HelmetWorn = true;
            binding.HelmetWorn.setText("Helmet Worn");
            binding.HelmetWorn.setBackgroundColor(getColor(R.color.NotConnectd));
            Log.d("mytag", "updateUI: HELMET_WORN branch");
        } else if (message.equals(HELMET_NOT_WORN)) {
            RideGuardGlobalVariables.HelmetWorn = false;
            binding.HelmetWorn.setText("Helmet not worn");
            binding.HelmetWorn.setBackgroundColor(getColor(R.color.mainactivitytextviewbg));
            Log.d("mytag", "updateUI: HELMET_NOT_WORN branch");
        } else if (message.equals(ACCIDENT)) {
            Intent intent = new Intent(MainActivity.this, AccidentScreen.class);
            startActivity(intent);
            finish();
            Log.d("mytag", "updateUI: ACCIDENT branch");
        } else {
            Log.d("mytag", "updateUI: default " + message);
        }

    }


    /*
        Method to check the Global Variables and Update the UI.
         */
    private  void CheckAndUpdateUi(){
        if(RideGuardGlobalVariables.HelmetWorn){
            binding.HelmetWorn.setText("Helmet Worn");
            binding.HelmetWorn.setBackgroundColor(getColor(R.color.NotConnectd));
        }else if(!RideGuardGlobalVariables.HelmetWorn){
            binding.HelmetWorn.setText("Helmet not Worn");
            binding.HelmetWorn.setBackgroundColor(getColor(R.color.mainactivitytextviewbg));
        }
        if(RideGuardGlobalVariables.Connected){
            binding.Connected.setText("Connected");
            binding.Connected.setBackgroundColor(getColor(R.color.NotConnectd));
        }
        else if(!RideGuardGlobalVariables.Connected){
            binding.Connected.setText("Not Connected");
            binding.Connected.setBackgroundColor(getColor(R.color.mainactivitytextviewbg));
        }
        binding.AlcoholLevel.setText("Blood Alcohol Level:"+RideGuardGlobalVariables.AlcoholLevel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckAndUpdateUi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }
}