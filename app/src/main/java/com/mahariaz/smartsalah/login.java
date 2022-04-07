package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button login;
    EditText email_field,password_field,uname_field;
    String email_login,password_login,uname;
    SharedPreferences sp;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

//    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // [START config_signin]
        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("301981403946-k8lpo3qqvc2jsv0oeau3ltdjbuhoqcsk.apps.googleusercontent.com")
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END config_signin]

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        email_field=findViewById(R.id.email_field);
        password_field=findViewById(R.id.pass_field);
        uname_field=findViewById(R.id.uname_field);
        /*sp = getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false )){
            Intent intent=new Intent(login.this,Home.class);
            startActivity(intent);
        }*/
        login=findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_login = email_field.getText().toString();
                password_login = password_field.getText().toString();
                uname = uname_field.getText().toString();

                if(email_login.isEmpty()){
                    email_field.setError("Email is required");
                    email_field.requestFocus();
                    return;
                }
                shared.email=email_login;
                if(uname.isEmpty()){
                    uname_field.setError("Username is required");
                    uname_field.requestFocus();
                    return;
                }
                shared.username=uname;
                if(password_login.isEmpty()){
                    password_field.setError("Password is required");
                    password_field.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email_login, password_login)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent intent=new Intent(login.this,Home.class);
                                    startActivity(intent);
                                    //sp.edit().putBoolean("logged",true).apply();

                                }else{
                                    Toast.makeText(login.this,"Register First",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        TextView sign_up=findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (login.this,signUp.class);
                startActivity(intent);
            }
        });
//        Button google_login=findViewById(R.id.google_login);
//        google_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//            }
//        }
//    }
//    private void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                            Intent intent2=new Intent(login.this,Home.class);
//                            startActivity(intent2);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            updateUI(null);
//                        }
//                    }
//                });
//    }
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//    private void updateUI(FirebaseUser user) {
//
//    }
}