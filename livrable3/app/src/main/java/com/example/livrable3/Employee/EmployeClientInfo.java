package com.example.livrable3.Employee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.livrable3.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeClientInfo extends AppCompatActivity {
    ListView infoList;
    ArrayList<String> infos;
    ArrayAdapter myAdapter;
    DatabaseReference reff;
    Bundle reg;
    String client;
    String username;
    String service;
    HashMap Daddy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe_client_info);

        reg = getIntent().getExtras();
        client = reg.getString("client");
        username = reg.getString("username");
        service = reg.getString("service");

        infoList = (ListView) findViewById(R.id.infoListView);
        infos = new ArrayList<String>();
        myAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, infos);
        infoList.setAdapter(myAdapter);
        reff = FirebaseDatabase.getInstance().getReference().child("Requests").child(username).child("pending").child(service+"_"+client).child("formulaire");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                infos.add(snapshot.getKey() + " : "+snapshot.getValue().toString());
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myAdapter.notifyDataSetChanged();;
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}