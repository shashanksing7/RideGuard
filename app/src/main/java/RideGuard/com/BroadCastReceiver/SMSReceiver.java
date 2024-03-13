package RideGuard.com.BroadCastReceiver;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

/*
This is the broadcast receiver that will read for SMSs form our machine and then perform the needful.
 */
public class SMSReceiver  extends BroadcastReceiver {

    public SMSReceiver(SmsBroadcastReceiverListener smsBroadcastReceiverListener){
        this.smsBroadcastReceiverListener=smsBroadcastReceiverListener;
    }


    /*
    creating instance of the interface
     */
    public SmsBroadcastReceiverListener smsBroadcastReceiverListener;

    /*
    The Onreceive() method is called when the broadcast receiver receives a message.
    it has two parameters the context and intent.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("mytag", "onReceive: SMS intent 222");

        /*
        The intent contains the details about the message received by the broadcast receiver.
        here using the getAction() method to to get the action string associated with the string.
        the action string field is mandatory and specifies operations to be performed by intent.
        we check the if the action string is to retrieve message form sms.
         */
        if (intent.getAction().equals(SmsRetriever.SMS_RETRIEVED_ACTION)) {
            /*
            The getExtra() method is used to  get the extra message associated with
            the intent.
             */
            Log.d("mytag", "onReceive: SMS intent");
            Bundle extras = intent.getExtras();

            /*
            we create the object of the Status class that represents the  status of the
            sms retrieval process.SmsRetriever.EXTRA_STATUS,is the key that is used to store the
            object in the extras,getExtras() is the method that of intent class to
            get the object and store it ina bundle.
             */

            Status smsRetreiverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            /*
            Creating a conditional  block to check the status of the sms retrieval process.
             */
            switch (smsRetreiverStatus.getStatusCode()) {

                case CommonStatusCodes
                        .SUCCESS:

                    /*
                    Here on success we are going to retive the parcelable object from the extras bundle
                    using the getParcelable() method and we pass the  "msRetriever.EXTRA_CONSENT_INTENT"
                    keyword that represents the  consent returned by the retriever.
                     */
                    Intent messageIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                    smsBroadcastReceiverListener.onSuccess(messageIntent);
                    break;
                case CommonStatusCodes.TIMEOUT:
                    smsBroadcastReceiverListener.onFailure();
                    break;

            }
        }
    }


    public interface SmsBroadcastReceiverListener {

        void onSuccess(Intent intent);

        void onFailure();


    }

}