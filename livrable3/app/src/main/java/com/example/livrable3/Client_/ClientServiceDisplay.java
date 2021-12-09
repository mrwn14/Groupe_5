package com.example.livrable3.Client_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;


public class ClientServiceDisplay extends AppCompatActivity {
    Bundle reg;
    String TAG = "boo";
    String employee;
    String username;
    ArrayList<String> services;
    ListView myList;
    ArrayAdapter myAdapter;
    DatabaseReference reff;
    DatabaseReference reff2;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_service_display);
        reg = getIntent().getExtras();
        employee = reg.getString("usernameEmployee");
        username = reg.getString("username");
        services = new ArrayList<String>();
        myList = (ListView) findViewById(R.id.clientListView);
        myAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, services);
        myList.setAdapter(myAdapter);
        reff = FirebaseDatabase.getInstance().getReference().child("Services").child(employee+"_services");
        reff2 = FirebaseDatabase.getInstance().getReference().child("Ratings").child(employee);
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (!services.contains(snapshot.getKey())) {
                        snapshot.getRef();
                        services.add(snapshot.getKey());
                    }
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myAdapter.notifyDataSetChanged();

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

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent requestIntent = new Intent(getApplicationContext(),ClientServiceRequest.class);
                requestIntent.putExtra("service",services.get(position));
                requestIntent.putExtra("username",username);
                requestIntent.putExtra("employeeName",employee);
                startActivity(requestIntent);

            }
        });
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder build = new AlertDialog.Builder(ClientServiceDisplay.this);
                build.setMessage("No ratings so far");

                reff2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d(TAG, snapshot.getKey());
                        if(services.contains(snapshot.getKey())){
                            messageChanger("Rating is "+ snapshot.child("rating").getValue());
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
                build.setMessage(message);
                messageChanger("No ratings so far");
                AlertDialog alert = build.create();
                alert.show();
                return true;
            }
        });

    }
    public void messageChanger(String message2){
        message = message2;
    }
}