package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button login;
    EditText email_field,password_field;
    String email_login,password_login;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email_field=findViewById(R.id.email_field);
        password_field=findViewById(R.id.pass_field);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false )){
            Intent intent=new Intent(login.this,Home.class);
            startActivity(intent);
        }
        login=findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_login = email_field.getText().toString();
                password_login = password_field.getText().toString();

                if(email_login.isEmpty()){
                    email_field.setError("Email is required");
                    email_field.requestFocus();
                    return;
                }
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
                                    sp.edit().putBoolean("logged",true).apply();

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
    }
}