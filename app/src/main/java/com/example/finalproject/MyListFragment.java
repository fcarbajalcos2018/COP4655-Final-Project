package com.example.finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyListFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button add;
    TextView dataSelect;
    RecyclerView recyclerView;

    private ArrayList<data> arrayList;
    //private ArrayAdapter<String> arrayAdapter;
    DatabaseReference databaseReference;
    dataAdapter dataAdapter;
    UserModel userModel;

    public MyListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyListFragment newInstance(String param1, String param2) {
        MyListFragment fragment = new MyListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_list, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("main");
        arrayList = new ArrayList<data>();
        dataSelect = (TextView)view.findViewById(R.id.selected);
        add = (Button)view.findViewById(R.id.add);
        recyclerView = (RecyclerView)view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //dataAdapter = new dataAdapter(list);
        //recyclerView.setAdapter(dataAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        userModel = myViewModel.getUserModel();
        boolean isSent = userModel.getCondition();

        if (isSent == true) {
            dataSelect.setText("Currently Selected: " + userModel.getLocationName());

            // When clicking the SAVE button, the UserModel data taken from selecting the business is added to Data and then sent to the database
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] cat = {"", "", ""};
                    cat = userModel.getCategory();
                    String[] tran = {"", "", ""};
                    tran = userModel.getTransaction();
                    data myData = new data();

                    myData.setLocationName(userModel.getLocationName());
                    myData.setLocationAddress(userModel.getLocationAddress());
                    myData.setCity(userModel.getCity());
                    myData.setState(userModel.getState());
                    myData.setCountry(userModel.getCountry());
                    myData.setZip(userModel.getZip());
                    myData.setId(userModel.getId());
                    myData.setCategory1(cat[0]);
                    myData.setCategory2(cat[1]);
                    myData.setCategory3(cat[2]);
                    myData.setRating(userModel.getRating());
                    myData.setTransaction1(tran[0]);
                    myData.setTransaction2(tran[1]);
                    myData.setTransaction3(tran[2]);
                    myData.setPhone(userModel.getPhone());
                    myData.setLatitude(userModel.getLatitude());
                    myData.setLongitude(userModel.getLongitude());

                    databaseReference.child(userModel.getLocationName()).setValue(myData);
                    recyclerListener();
                }
            });
        }

        recyclerListener();
    }

    private void recyclerListener() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    data data1 = dataSnapshot.getValue(data.class);
                    arrayList.add(data1);
                }
                dataAdapter = new dataAdapter(getContext(), arrayList, dataSendInterface);
                recyclerView.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Get the data from the database and send it to the UserModel for output
    dataAdapter.dataSend dataSendInterface = new dataAdapter.dataSend() {
        @Override
        public void OnDataClick(data myData) {
            userModel.setLocationName(myData.getLocationName());
            userModel.setLocationAddress(myData.getLocationAddress());
            userModel.setCity(myData.getCity());
            userModel.setState(myData.getState());
            userModel.setCountry(myData.getCountry());
            userModel.setZip(myData.getZip());
            userModel.setId(myData.getId());
            String[] categorySend = {myData.getCategory1(), myData.getCategory2(), myData.getCategory3()};
            userModel.setCategory(categorySend);
            userModel.setRating(myData.getRating());
            String[] transactionSend = {myData.getTransaction1(), myData.getTransaction2(), myData.getTransaction3()};
            userModel.setTransaction(transactionSend);
            userModel.setPhone(myData.getPhone());
            userModel.setLatitude(myData.getLatitude());
            userModel.setLongitude(myData.getLongitude());
            userModel.setCondition(true);
        }
    };

    @Override
    public void onStop()
    {
        super.onStop();
        //dataAdapter.stopListening();
    }
}

