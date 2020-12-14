package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    EditText searchIn;
    Button searchOnClick;
    TextView op1, op2, op3, op4;
    RequestQueue queue, queue2, queue3;
    UserModel myUserModel;
    boolean resultsPresent = false;

    String [] data1 = new String[17];
    String [] data2 = new String[17];

    String YELP_API = "Tg9MF-UGI9sChISM7-WDxCRPjStEnEY6Ijjqs-UWvzNfOB6T2FFeQNLOkLdUxPx00PKBV6rlZs97JyjU97XAsVYp2am4KGpONQJpVqIu8p36Iub7T1N3kdme2hPEX3Yx";
    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize ViewModel class
        MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);

        // Initialize UserModel class
        myUserModel = myViewModel.getUserModel();

        searchIn = view.findViewById(R.id.input);
        searchOnClick = view.findViewById(R.id.search);

        op1 = view.findViewById(R.id.sl1);
        op2 = view.findViewById(R.id.sl2);
        // Pressing the search button will start an API request
        searchOnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        return view;
    }

    private void getData() {

        String in = searchIn.getText().toString();
        String[] splitIn = in.split(" ");
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://api.yelp.com/v3/businesses/search?location=" + splitIn[0] + "&term=" + splitIn[1];
        JsonObjectRequest getLocation = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getActivity().getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
                    JSONArray businesses = response.getJSONArray("businesses");

                    // BUSINESS OPTION 1
                    //      Location
                    JSONObject businessElement1 = businesses.getJSONObject(0);
                    String locationName1 = businessElement1.getString("name");
                    String id1 = businessElement1.getString("id");
                    JSONObject location1 = businessElement1.getJSONObject("location");
                    String address1 = location1.getString("address1");
                    String city1 = location1.getString("city");
                    String zip1 = location1.getString("zip_code");
                    String country1 = location1.getString("country");
                    String state1 = location1.getString("state");
                    //      Characteristics
                    JSONArray category1 = businessElement1.getJSONArray("categories");
                    int category1Length = category1.length();
                    String[] title1 = new String[category1Length];
                    if (category1Length != 0) {
                        for (int i = 0; i < category1Length; i++) {
                            JSONObject categoryElement1 = category1.getJSONObject(i);
                            title1[i] = categoryElement1.getString("title");
                        }
                    }
                    String rating1 = businessElement1.getString("rating");
                    JSONArray transaction1 = businessElement1.getJSONArray("transactions");
                    int transaction1Length = transaction1.length();
                    String[] transactionType1 = new String[transaction1Length];
                    if (transaction1Length != 0) {
                        for (int i = 0; i < transaction1Length; i++) {
                            transactionType1[i] = transaction1.getString(i);
                        }
                    }
                    //      Contacts
                    String phone1 = businessElement1.getString("display_phone");
                    //      Coordinates
                    JSONObject coordinates1 = businessElement1.getJSONObject("coordinates");
                    String latitude1 = coordinates1.getString("latitude");
                    String longitude1 = coordinates1.getString("longitude");

                    // BUSINESS OPTION 2
                    //      Location
                    JSONObject businessElement2 = businesses.getJSONObject(1);
                    String locationName2 = businessElement2.getString("name");
                    String id2 = businessElement2.getString("id");
                    JSONObject location2 = businessElement2.getJSONObject("location");
                    String address2 = location2.getString("address1");
                    String city2 = location2.getString("city");
                    String zip2 = location2.getString("zip_code");
                    String country2 = location2.getString("country");
                    String state2 = location2.getString("state");
                    //      Characteristics
                    JSONArray category2 = businessElement2.getJSONArray("categories");
                    int category2Length = category2.length();
                    String[] title2 = new String[category2Length];
                    if (category2Length != 0) {
                        for (int i = 0; i < category2Length; i++) {
                            JSONObject categoryElement2 = category2.getJSONObject(i);
                            title2[i] = categoryElement2.getString("title");
                        }
                    }
                    String rating2 = businessElement2.getString("rating");
                    JSONArray transaction2 = businessElement2.getJSONArray("transactions");
                    int transaction2Length = transaction2.length();
                    String[] transactionType2 = new String[transaction2Length];
                    if (transaction2Length != 0)
                    {
                        for (int i = 0; i < transaction2Length; i++)
                        {
                            transactionType2[i] = transaction2.getString(i);
                        }
                    }
                    //      Contacts
                    String phone2 = businessElement2.getString("display_phone");
                    //      Coordinates
                    JSONObject coordinates2 = businessElement2.getJSONObject("coordinates");
                    String latitude2 = coordinates2.getString("latitude");
                    String longitude2 = coordinates2.getString("longitude");

                    op1.setText(locationName1);
                    op2.setText(locationName2);

                    // Assignment of data for FIRST RESULT
                    int i1 = 0;
                    data1[0] = locationName1;
                    data1[1] = address1;
                    data1[2] = city1;
                    data1[3] = state1;
                    data1[4] = country1;
                    data1[5] = zip1;
                    data1[6] = id1;
                    data1[7] = "";
                    data1[8] = "";
                    data1[9] = "";
                    for(int i = 0; i < category1Length; i++)
                    {
                        data1[7 + i] = title1[i];
                    }
                    data1[10] = rating1;
                    data1[11] = "";
                    data1[12] = "";
                    data1[13] = "";
                    for (int i = 0; i < transaction1Length; i++)
                    {
                        data1[11 + i] = transactionType1[i];
                    }
                    data1[14] = phone1;
                    data1[15] = latitude1;
                    data1[16] = longitude1;

                    // Assignment of data for SECOND RESULT
                    int i2 = 0;
                    data2[0] = locationName2;
                    data2[1] = address2;
                    data2[2] = city2;
                    data2[3] = state2;
                    data2[4] = country2;
                    data2[5] = zip2;
                    data2[6] = id2;
                    data2[7] = "";
                    data2[8] = "";
                    data2[9] = "";
                    for(int i = 0; i < category2Length; i++)
                    {
                        data2[7 + i] = title2[i];
                    }
                    data2[10] = rating2;
                    data2[11] = "";
                    data2[12] = "";
                    data2[13] = "";
                    for (int i = 0; i < transaction2Length; i++)
                    {
                        data2[11 + i] = transactionType2[i];
                    }
                    data2[14] = phone2;
                    data2[15] = latitude2;
                    data2[16] = longitude2;

                    resultsPresent = true;
                    getEvents();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to get the data.", Toast.LENGTH_SHORT).show();
            }
        }) {
            //@Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + YELP_API);
                return params;
            }
        };

        queue.add(getLocation);
    }

    private void getEvents() {
        if (resultsPresent == true)
        {
            Toast.makeText(getActivity().getApplicationContext(), data1[0], Toast.LENGTH_SHORT).show();
            op1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v1) {
                    sendData(data1);
                    Toast.makeText(getActivity().getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
                }
            });
            op2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    sendData(data2);

                }
            });
        }
    }

    private void sendData(String[] myData)
    {
        myUserModel.setLocationName(myData[0]);
        myUserModel.setLocationAddress(myData[1]);
        myUserModel.setCity(myData[2]);
        myUserModel.setState(myData[3]);
        myUserModel.setCountry(myData[4]);
        myUserModel.setZip(myData[5]);
        myUserModel.setId(myData[6]);
        String[] categorySend = {myData[7], myData[8], myData[9]};
        myUserModel.setCategory(categorySend);
        myUserModel.setRating(myData[10]);
        String[] transactionSend = {myData[11], myData[12], myData[13]};
        myUserModel.setTransaction(transactionSend);
        myUserModel.setPhone(myData[14]);
        myUserModel.setLatitude(myData[15]);
        myUserModel.setLongitude(myData[16]);
        myUserModel.setCondition(true);
    }
}
