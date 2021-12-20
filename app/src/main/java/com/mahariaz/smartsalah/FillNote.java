package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

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



public class FillNote extends AppCompatActivity {
    EditText content_field,note_name_field;
    public String contentw, nusername, note_name, time;
    Button save_button;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    public static final int CAMERA_ACTION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_note);
        note_name_field = findViewById(R.id.note_name);
        content_field = findViewById(R.id.content);
        //register user
        save_button = findViewById(R.id.save_note);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                note_name = note_name_field.getText().toString();
                contentw = content_field.getText().toString();
                register_note();
                Intent intent = new Intent(FillNote.this, MyNotes.class);
                startActivity(intent);

            }
        });



    }

    public void register_note(){

        FirebaseDatabase fstorage = FirebaseDatabase.getInstance();
        DatabaseReference DBREF = fstorage.getReference("Notes");
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getInstance().getReference("Notes");
        SimpleDateFormat input = new SimpleDateFormat("mm:ss");
        String timestamp=input.format(new Date());
        UserNoteStorage userNoteStorage = new UserNoteStorage(contentw,note_name,timestamp,"untitled");
        reference.child(note_name).setValue(userNoteStorage);



    }
}