package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.UUID;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;


public class UserProfile extends AppCompatActivity {
    EditText fname_field, lname_field, age_field;
    public String fname, lname, gender, dp,age,in,ft;
    Button save_button;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ImageView iv;
    Uri selectedImage = null;
    SearchableSpinner spinner_ft,spinner_in;
    public static final int CAMERA_ACTION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        save_button = findViewById(R.id.save);
        fname_field=findViewById(R.id.fname);
        lname_field=findViewById(R.id.lname);
        age_field=findViewById(R.id.age);
        //spinner_ft
        spinner_ft = (SearchableSpinner) findViewById(R.id.spinner_ft);
        ArrayAdapter<String> Adapter_ft = new ArrayAdapter<String>(UserProfile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ft));
        Adapter_ft.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ft.setAdapter(Adapter_ft);
        //spinner_in
        spinner_in = (SearchableSpinner) findViewById(R.id.spinner_in);
        ArrayAdapter<String> Adapter_in = new ArrayAdapter<String>(UserProfile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.in));
        Adapter_in.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_in.setAdapter(Adapter_in);
        spinner_ft.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                ft = spinner_ft.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected() {
            }
        });
        spinner_in.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                in = spinner_in.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected() {
            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=fname_field.getText().toString();
                lname=lname_field.getText().toString();
                age=age_field.getText().toString();
                System.out.println("my output "+fname+"    "+lname+"   "+age+"   "+ft+"   "+in);
                shared.fname=fname;
                shared.lname=lname;
                shared.age=age;
                Intent intent = new Intent(UserProfile.this, signUp.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.dp).setOnClickListener(view -> {
            System.out.println("Selecting");
            Intent selecting = new Intent();
            selecting.setAction(Intent.ACTION_GET_CONTENT);
            selecting.setType("image/*");
            startActivityForResult(selecting, 200);
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            shared.selectedImage=selectedImage;
            iv = findViewById(R.id.dp);
            iv.setImageURI(selectedImage);


        }
    }

}