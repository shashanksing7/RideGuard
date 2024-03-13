package RideGuard.com.Accident;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ncorti.slidetoact.SlideToActView;

import RideGuard.com.HospitalListScreen.HospitalListScreen;
import RideGuard.com.MainActivity;
import RideGuard.com.databinding.ActivityAccidentScreenBinding;

/*
The User will be redirected to this screen if he/she meets an accident.
 */
public class AccidentScreen extends AppCompatActivity {

    private ActivityAccidentScreenBinding binding;
    private Handler handler;
    private boolean isCancelled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccidentScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.AccidentProgressBar.setVisibility(View.VISIBLE);
        handler = new Handler(Looper.getMainLooper());

        startCountdownTimer();

        binding.TakeToHospital.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                NavigateToScreen(HospitalListScreen.class);
            }
        });

        binding.NoAccidentCancel.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                // Set the flag to signal the background thread to stop
                isCancelled = true;
                NavigateToScreen(MainActivity.class);
            }
        });
    }

    private void startCountdownTimer() {
        final long initialTime = 60000;
        final long interval = 1000;
        long currentTime = initialTime;
        final int totalSteps = (int) (initialTime / interval);
        final int progressPerStep = 100 / totalSteps;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int step = 0; step <= totalSteps; step++) {
                    if (isCancelled) {
                        break; // Break out of the loop if cancelled
                    }

                    final int progress = step * progressPerStep;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            binding.AccidentProgressBar.setProgress(progress);
                            binding.Countertext.setText(String.valueOf(progress));
                        }
                    });

                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!isCancelled) {
                            NavigateToScreen(HospitalListScreen.class);
                        }
                    }
                });
            }
        }).start();
    }
    private void NavigateToScreen(Class myclass) {
        Intent intent = new Intent(AccidentScreen.this, myclass);
        startActivity(intent);
        finish();
    }
}
