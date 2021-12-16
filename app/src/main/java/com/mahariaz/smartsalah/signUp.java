package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class signUp extends AppCompatActivity {
    EditText email_field_signup,password_field_signup,confirm_field,username_field;
    String email_signup,password_signup,conf_pass,username_signup;
    Button signup_button;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ImageView iv;
    public String dp;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    boolean is_same_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth. getInstance();
        signup_button=findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_same_username=false;
                registerUser();


            }
        });
        TextView login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signUp.this,login.class);
                startActivity(intent);
            }
        });

    }

    private void registerUser() {
        email_field_signup = findViewById(R.id.email_s);
        password_field_signup = findViewById(R.id.pass);
        confirm_field = findViewById(R.id.con_pass);
        username_field = findViewById(R.id.user_name);
        email_signup = email_field_signup.getText().toString();
        password_signup = password_field_signup.getText().toString();
        username_signup = username_field.getText().toString();
        conf_pass = confirm_field.getText().toString();
        if (email_signup.isEmpty()) {
            email_field_signup.setError("Email is required");
            email_field_signup.requestFocus();
            return;
        }
        if (password_signup.isEmpty()) {
            password_field_signup.setError("Password is required");
            password_field_signup.requestFocus();
            return;
        }
        if (conf_pass.isEmpty()) {
            confirm_field.setError("Password is required");
            confirm_field.requestFocus();
            return;
        }
        if (username_signup.isEmpty()) {
            username_field.setError("Username is required");
            username_field.requestFocus();
            return;
        }
        shared.email = email_signup;
        shared.username = username_signup;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserBio").child(shared.username).child("username");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                System.out.println("same : " + value + " " + shared.username);
                if(value.equalsIgnoreCase(shared.username)){
                    is_same_username=true;
                    System.out.println("same");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("FAILED");

            }
        });
        if (is_same_username){
        mAuth.createUserWithEmailAndPassword(email_signup, password_signup)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Reg done");

                            register_bio();
                            Intent intent = new Intent(signUp.this, login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(signUp.this, "Can't Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }else{
            Toast.makeText(signUp.this, "Username is taken", Toast.LENGTH_SHORT).show();
        }
    }
    public void register_bio(){
        StorageReference storage = FirebaseStorage.getInstance().getReference();

        String uniqueID = UUID.randomUUID().toString();
        storage = storage.child("UserDP/"+uniqueID+".jpg");

        storage.putFile(shared.selectedImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                        task
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        dp = uri.toString();
                                        FirebaseDatabase fstorage = FirebaseDatabase.getInstance();
                                        DatabaseReference DBREF = fstorage.getReference("UserBio");
                                        /*DBREF.push().setValue(
                                                dp
                                        );*/
                                        rootNode = FirebaseDatabase.getInstance();
                                        reference = rootNode.getInstance().getReference("UserBio");
                                        UserBioStorage userBioStorage = new UserBioStorage(dp,shared.email,shared.username, shared.height, shared.age, shared.gender);
                                        reference.child(shared.username).setValue(userBioStorage);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println("failllledddd1 " +shared.gender+shared.age+shared.height);

                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("failllledddd2 " +shared.gender+shared.age+shared.height);

                    }
                });

    }

}