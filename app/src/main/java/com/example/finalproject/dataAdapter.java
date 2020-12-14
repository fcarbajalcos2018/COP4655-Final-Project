package com.example.finalproject;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class dataAdapter extends RecyclerView.Adapter<dataAdapter.mViewHolder> {

    Context context;
    ArrayList<data> list;
    dataSend dataSend = new dataSend() {
        @Override
        public void OnDataClick(data myData) {

        }
    };
    UserModel myUserModel;
    int pos = 0;

    public dataAdapter(Context c, ArrayList<data> l, dataSend ds) {
        context = c;
        list = l;
        dataSend = ds;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.datasave, parent, false);
        return new mViewHolder(view, dataSend);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        final data mydata = list.get(position);

        holder.locationName.setText(mydata.getLocationName());
        holder.locationAddress.setText(mydata.getLocationAddress());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView locationName;
        private TextView locationAddress;
        public LinearLayout linearLayout;
        dataSend mDataSend;
        Context ctxt;
        public mViewHolder(@NonNull View itemView, dataSend mDataSend1) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDataSend = mDataSend1;
            locationName = itemView.findViewById(R.id.locName);
            locationAddress = itemView.findViewById(R.id.locAddress);
            linearLayout = itemView.findViewById(R.id.linearLayout);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // Gets the elements based on the item that was pressed on
            data myData1 = list.get(position);
            Toast.makeText(context, myData1.getCategory3(), Toast.LENGTH_SHORT).show();
            if (myData1 != null) {
                // Send the data to the interface
                dataSend.OnDataClick(myData1);
            }
        }
    }

    public interface dataSend {
        void OnDataClick(data myData);
    }

}
