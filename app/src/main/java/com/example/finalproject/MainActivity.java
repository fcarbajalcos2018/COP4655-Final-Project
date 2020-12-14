package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    Button google;

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        // When the activity starts, the Firebase will automatically authenticate the user if already logged in
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null)
        {
            // If there is a user already logged in, then an intent will cause the application to display the main application
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Establishes Firebase instance
        google = (Button)findViewById(R.id.googlelogin);
        mAuth = FirebaseAuth.getInstance();

        // Calls a function to request sign in data
        createRequest();

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Once the user presses the log in button, the procedure to authenticate the user takes place
                GoogleLogin();
            }
        });
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void GoogleLogin() {
        Intent login = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(login, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int getCode, int result, Intent data) {
        super.onActivityResult(getCode, result, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (getCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //Toast.makeText(this, e.getMessage(),)
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount myAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(myAccount.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            CoordinatorLayout mBinding = findViewById(R.id.layout);
                            Snackbar.make(mBinding, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}