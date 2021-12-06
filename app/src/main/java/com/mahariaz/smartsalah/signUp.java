package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUp extends AppCompatActivity {
    EditText email_field_signup,password_field_signup,confirm_field,username_field;
    String email_signup,password_signup,conf_pass,username_signup;
    Button signup_button;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth. getInstance();
        signup_button=findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        if(email_signup.isEmpty()){
            email_field_signup.setError("Email is required");
            email_field_signup.requestFocus();
            return;
        }
        if(password_signup.isEmpty()){
            password_field_signup.setError("Password is required");
            password_field_signup.requestFocus();
            return;
        }
        if(conf_pass.isEmpty()){
            confirm_field.setError("Password is required");
            confirm_field.requestFocus();
            return;
        }
        if(username_signup.isEmpty()){
            username_field.setError("Username is required");
            username_field.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email_signup,password_signup)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            System.out.println("Reg done");


                            Intent intent=new Intent(signUp.this,login.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(signUp.this,"Can't Register",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}