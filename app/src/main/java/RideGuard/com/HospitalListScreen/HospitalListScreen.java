package RideGuard.com.HospitalListScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import RideGuard.com.R;
import RideGuard.com.databinding.ActivityHospitalListScreenBinding;

/*
This class will be used to load the list of hospital.
 */
public class HospitalListScreen extends AppCompatActivity {

        /*
    Creating the variable fo the binding class
     */

    private ActivityHospitalListScreenBinding binding;

    /*
    Map to hold the list of hospitals
     */
    private List<HospitalClass> hospitalLiat;
    /*
    Variable of the Adapter
     */
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Initialising the binding variable.
         */
        binding=ActivityHospitalListScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*
        Method to instantiate the hospital list.
         */
        hospitalLiat=getHospitalList();
        listAdapter=new ListAdapter(getApplicationContext(), hospitalLiat, new ListAdapter.eventListener() {
            @Override
            public void startCall(String number) {
                //start call
                makePhoneCall(number);
            }
        });
        binding.HospitalRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.HospitalRecycler.setAdapter(listAdapter);
    }

    private List<HospitalClass> getHospitalList() {
        List<HospitalClass>  hospitals=new ArrayList<>();
       hospitals.add(new HospitalClass("Sharda Hospital","7352590587"));
        hospitals.add( new HospitalClass("Kailash Hospital","0120 232 7799"));
        hospitals.add( new HospitalClass("JR Hospital","0120 411 9333"));
        hospitals.add(new HospitalClass("KIIMS Hospital","+91 471 294 1400"));
        hospitals.add( new HospitalClass("Green City Hospital","098118 57783"));
        return hospitals;
    }

    /*
    Method to call the Hospital.
     */
    private void makePhoneCall(String phoneNumber) {

            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

    }
}