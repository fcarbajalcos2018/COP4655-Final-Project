package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String YELP_API = "Tg9MF-UGI9sChISM7-WDxCRPjStEnEY6Ijjqs-UWvzNfOB6T2FFeQNLOkLdUxPx00PKBV6rlZs97JyjU97XAsVYp2am4KGpONQJpVqIu8p36Iub7T1N3kdme2hPEX3Yx";

    TextView locationName, locationAddress, locationArea;
    TextView categories, rating, transactions, phone;
    TextView openClose;
    TextView monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    RequestQueue queue;


    boolean isSent = false;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        locationName = (TextView) view.findViewById(R.id.locationName);
        locationAddress = (TextView) view.findViewById(R.id.locationAddress);
        locationArea = (TextView) view.findViewById(R.id.city);
        categories = (TextView) view.findViewById(R.id.category);
        rating = (TextView) view.findViewById(R.id.rating);
        transactions = (TextView) view.findViewById(R.id.transactions);
        phone = (TextView) view.findViewById(R.id.phone);

        openClose = (TextView) view.findViewById(R.id.openClose);

        monday = (TextView) view.findViewById(R.id.monday);
        tuesday = (TextView) view.findViewById(R.id.tuesday);
        wednesday = (TextView) view.findViewById(R.id.wednesday);
        thursday = (TextView) view.findViewById(R.id.thursday);
        friday = (TextView) view.findViewById(R.id.friday);
        saturday = (TextView) view.findViewById(R.id.saturday);
        sunday = (TextView) view.findViewById(R.id.sunday);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        final UserModel userModel = myViewModel.getUserModel();
        // Determines whether the data in the user model is present
        isSent = userModel.getCondition();
        if (isSent == true) {
            String[] cat = {"", "",""};
            if (userModel.getCategory() != null)
            {
                cat = userModel.getCategory();
            }
            String[] tran = {"", "",""};
            if (userModel.getTransaction() != null)
            {
                tran = userModel.getTransaction();
            }
            locationName.setText(userModel.getLocationName());
            locationAddress.setText(userModel.getLocationAddress() + " - " + userModel.getZip());
            locationArea.setText(userModel.getCity() + ", " + userModel.getState() + " - " + userModel.getCountry());
            categories.setText(cat[0] + ", " + cat[1] + ", " + cat[2]);
            transactions.setText(tran[0] + ", " + tran[1] + ", " + tran[2]);
            rating.setText(userModel.getRating() + "/5");
            phone.setText(userModel.getPhone());
            Toast.makeText(getActivity().getApplicationContext(), userModel.getId(), Toast.LENGTH_SHORT).show();
            queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "https://api.yelp.com/v3/businesses/" + userModel.getId();

            // Makes an API request to access data based on the ID of the location
            JsonObjectRequest getLocationData = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONArray hours = response.getJSONArray("hours");

                        JSONObject hoursElement = hours.getJSONObject(0);
                        JSONArray open = hoursElement.getJSONArray("open");

                        String[] openArray1 = {"", "", "", "", "", "", ""};
                        String[] openArray2 = {"", "", "", "", "", "", ""};

                        boolean isClosed = false;
                        isClosed = response.getBoolean("is_closed");
                        String operation = "Closed";
                        if (isClosed == false)
                        {
                            operation = "Open";
                        }

                        for (int i = 0; i < open.length(); i++)
                        {
                            JSONObject openElement = open.getJSONObject(i);
                            openArray1[i] = openElement.getString("start");
                            openArray2[i] = openElement.getString("end");
                        }

                        String MondayStart = openArray1[0];
                        String MondayEnd = openArray2[0];

                        String TuesdayStart = openArray1[1];
                        String TuesdayEnd = openArray2[1];

                        String WednesdayStart = openArray1[2];
                        String WednesdayEnd = openArray2[2];

                        String ThursdayStart = openArray1[3];
                        String ThursdayEnd = openArray2[3];

                        String FridayStart = openArray1[4];
                        String FridayEnd = openArray2[4];

                        String SaturdayStart = openArray1[5];
                        String SaturdayEnd = openArray2[5];

                        String SundayStart = openArray1[6];
                        String SundayEnd = openArray2[6];

                        Toast.makeText(getActivity().getApplicationContext(), getMins(MondayEnd), Toast.LENGTH_SHORT).show();

                        openClose.setText(operation);

                        monday.setText(getHours(MondayStart) + ":" + getMins(MondayStart) + " - " + getHours(MondayEnd) + ":" + getMins(MondayEnd));
                        tuesday.setText(getHours(TuesdayStart) + ":" + getMins(TuesdayStart) + " - " + getHours(TuesdayEnd) + ":" + getMins(TuesdayEnd));
                        wednesday.setText(getHours(WednesdayStart) + ":" + getMins(WednesdayStart) + " - " + getHours(WednesdayEnd) + ":" + getMins(WednesdayEnd));
                        thursday.setText(getHours(ThursdayStart) + ":" + getMins(ThursdayStart) + " - " + getHours(ThursdayEnd) + ":" + getMins(ThursdayEnd));
                        friday.setText(getHours(FridayStart) + ":" + getMins(FridayStart) + " - " + getHours(FridayEnd) + ":" + getMins(FridayEnd));
                        saturday.setText(getHours(SaturdayStart) + ":" + getMins(SaturdayStart) + " - " + getHours(SaturdayEnd) + ":" + getMins(SaturdayEnd));
                        sunday.setText(getHours(SundayStart) + ":" + getMins(SundayStart) + " - " + getHours(SundayEnd) + ":" + getMins(SundayEnd));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                private String getHours(String day) {
                    if (day != "") {
                        int hours = Integer.parseInt(day);
                        // Divides by 100 to get the first two digits of the non-formatted time
                        int hoursRes = hours / 100;
                        return String.valueOf(hoursRes);
                    }
                    return "";
                }

                private String getMins(String day) {
                    if (day != "") {
                        int mins = Integer.parseInt(day);
                        // Modulates by 100 to get the last two digits of the non-formatted time
                        int minsRes = mins % 100;
                        char zero = '0';
                        String result = String.valueOf(minsRes);
                        if (minsRes < 10)
                        {
                            return zero + result;
                        }
                        return result;
                    }
                    return "";
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

            queue.add(getLocationData);
        }
    }
}