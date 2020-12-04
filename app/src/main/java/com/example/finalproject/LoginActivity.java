package com.example.finalproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class LoginActivity extends AppCompatActivity {

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
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Fragment select = null;

                switch (item.getItemId())
                {
                    case R.id.app_bar_search:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        select = mainFragment;
                        break;
                    case R.id.app_bar_info:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorAccent);
                        select = infoFragment;
                        break;
                    case R.id.app_bar_map:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimaryDark);
                        select = mapsFragment;
                        break;
                    case R.id.app_bar_mylist:
                        bottomNavigationView.setItemBackgroundResource(R.color.design_default_color_primary_variant);
                        select = myListFragment;
                        break;
                    default:

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, select).commit();
            }
        });
    }

}