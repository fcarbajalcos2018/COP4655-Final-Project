package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference, fvrt, fvrtList;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    Button logOut;
    TextView welcome;
    private BottomNavigationView bottomNavigationView;
    private MainFragment mainFragment;
    private InfoFragment infoFragment;
    private MapsFragment mapsFragment;
    private MyListFragment myListFragment;

    boolean gotData = false;
    String dataLocName, dataAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcome = findViewById(R.id.welcome);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        logOut = (Button) findViewById(R.id.logout);
        mainFragment = new MainFragment();
        infoFragment = new InfoFragment();
        mapsFragment = new MapsFragment();
        myListFragment = new MyListFragment();

        UserModel myUserModel = new UserModel();

        //Default
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mainFragment).commit();

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this );
        if (signInAccount != null)
        {
            welcome.setText("Welcome , " + signInAccount.getDisplayName() + " (" + signInAccount.getEmail() + ")!");
        }

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When pressing the logout button, the application will redirect the user back to the pre-login screen
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Fragment select = null;

                switch (item.getItemId())
                {
                    case R.id.app_bar_search:
                        select = mainFragment;
                        break;
                    case R.id.app_bar_info:
                        select = infoFragment;
                        break;
                    case R.id.app_bar_map:
                        select = mapsFragment;
                        break;
                    case R.id.app_bar_mylist:
                        select = myListFragment;
                        break;
                    default:

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, select).commit();
            }
        });
    }

}