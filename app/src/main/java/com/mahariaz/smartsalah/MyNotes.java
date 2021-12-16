package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyNotes extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> userList;
    Adapter_Home adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        userList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String content = snapshot.child("content").getValue().toString();
                    String notename = snapshot.child("notename").getValue().toString();
                    String notetime = snapshot.child("notetime").getValue().toString();
                    String nusername = snapshot.child("nusername").getValue().toString();




                    //System.out.println("fname"+snapshot.child("fname").getValue());

//                    for (ModelClass md :
//                            userList) {
//                        System.out.println("-" + md.getPerson_name());
//                        System.out.println("+" + fname + " " + lname);
//                        if (!md.getPerson_name().equals(fname + " " + lname)) {
//                            System.out.println("not there");
//                    userList.add(new ModelClass(R.drawable.nu, fname + " " + lname, "2:45pm", tagline));
                    userList.add(new ModelClass(content,notename,notetime,nusername));
//                        }
//
//                    }


                    recyclerView = findViewById(R.id.rec_view);
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new Adapter_Home(userList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        userList = new ArrayList<>();
//        userList.add(new ModelClass(R.drawable.nu,"maha","2:45pm","how are you and I"));
//        userList.add(new ModelClass(R.drawable.nu,"abubakar2000","2:45pm","how are you and I"));
//        userList.add(new ModelClass(R.drawable.nu,"ana","2:45pm","how are you and I"));
//        userList.add(new ModelClass(R.drawable.nu,"ayesha","2:45pm","how are you and I"));
//        userList.add(new ModelClass(R.drawable.nu,"aima","2:45pm","how are you and I"));
//        userList.add(new ModelClass(R.drawable.nu,"shiza","2:45pm","how are you and I"));

        EditText field = findViewById(R.id.REACTIVESEARCHFIELD);

        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(field.getText().toString());

                List<ModelClass> filtered = new LinkedList<ModelClass>();

                for (ModelClass it:
                        userList) {
                    if(it.getNusername().toLowerCase().contains(field.getText().toString())){
                        filtered.add(it);
                    }
                }

                adapter=new Adapter_Home(filtered);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        recyclerView=findViewById(R.id.rec_view);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter_Home(userList);
        recyclerView.setAdapter(adapter);

        List backupList = userList;
        adapter.notifyDataSetChanged();
    }
}