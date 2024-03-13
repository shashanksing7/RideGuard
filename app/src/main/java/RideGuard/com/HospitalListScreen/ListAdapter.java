package RideGuard.com.HospitalListScreen;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import RideGuard.com.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {

    /*
    Map to hold the list of hospitals
     */
    private List<HospitalClass> hospitalLiat;;

    private Context context;
    private  eventListener eventListener;

    ListAdapter(Context context, List<HospitalClass> hospitalLiat,eventListener eventListener){
        this.hospitalLiat=hospitalLiat;
        this.context=context;
        this.eventListener=eventListener;
    }
    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.listlayout,parent,false);
        ListHolder listHolder=new ListHolder(view);
        return listHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        HospitalClass hospitalClass=hospitalLiat.get(position);
        holder.hospitalName.setText(hospitalClass.getHospitalName().toString());
        holder.hospitalNumber.setText("Ph no: "+hospitalClass.getHospitalNumber().toString());

    }

    @Override
    public int getItemCount() {
        return hospitalLiat.size();
    }

    public  class ListHolder extends RecyclerView.ViewHolder{

        private TextView hospitalName,hospitalNumber;
        AppCompatButton button;
        public ListHolder(@NonNull View itemView) {
            super(itemView);
            hospitalName=itemView.findViewById(R.id.HospitalName);
            hospitalNumber=itemView.findViewById(R.id.HospitalNumber);
            button=itemView.findViewById(R.id.HospitalCall);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HospitalClass hospitalClass=hospitalLiat.get(getAdapterPosition());
                    eventListener.startCall(hospitalClass.getHospitalNumber());

                }
            });
        }
    }

    public interface eventListener{
        public  void  startCall(String number);
    }
}
