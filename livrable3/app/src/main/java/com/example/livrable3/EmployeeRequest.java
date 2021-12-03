package com.example.livrable3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeRequest extends AppCompatActivity {
    DatabaseReference reff;
    DatabaseReference reff2;
    ArrayList<String> correctDocs;
    ArrayList<String> correctDocs2;
    ArrayList<String> notCorrectDocs;
    Bundle reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_request);
        correctDocs = new ArrayList<String>();
        correctDocs2 = new ArrayList<String>();
        notCorrectDocs = new ArrayList<String>();

        reg = getIntent().getExtras();
//        username = reg.getString("username");
        ListView choicelistview = (ListView) findViewById(R.id.PendingListView);
        ListView choicelistview2 = (ListView) findViewById(R.id.ApprovedListView);
        ArrayAdapter myAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, correctDocs2);
        ArrayAdapter myAdapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, correctDocs);
        choicelistview.setAdapter(myAdapter);
        choicelistview2.setAdapter(myAdapter2);
        reff = FirebaseDatabase.getInstance().getReference().child("Requests").child("pending");
//        String[] hamid = {"Truck rental","Visa","Study permit","High wifi speed", "Roof permit","Gun permit","Pregnant woman permit","Milkman service","LondonScammer permit"};
//        for (String k :
//                hamid) {
//            reff.child("pending").child(k).setValue(k);
//        }
        reff2 = FirebaseDatabase.getInstance().getReference().child("Requests").child("approved");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String val = snapshot.getKey();
                correctDocs2.add(val);
                Log.d("test", val);
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
        reff2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myAdapter2.notifyDataSetChanged();

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
        choicelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeRequest.this);
                builder.setMessage("Approuver?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                correctDocs.add(correctDocs2.get(position));
                                correctDocs2.remove(position);
                                add();
                                myAdapter.notifyDataSetChanged();
                                myAdapter2.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reff.child(correctDocs2.get(position)).removeValue();
                                correctDocs2.remove(position);
                                myAdapter.notifyDataSetChanged();
                                myAdapter2.notifyDataSetChanged();
                            }
                        });


                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        choicelistview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                correctDocs2.add(correctDocs.get(position));
//                correctDocs.remove(position);
//                myAdapter.notifyDataSetChanged();
//                myAdapter2.notifyDataSetChanged();


            }
        });
    }

    public void add() {
        reff = FirebaseDatabase.getInstance().getReference().child("Requests").child("pending");
        reff2 = FirebaseDatabase.getInstance().getReference().child("Requests").child("approved");
        HashMap hh = new HashMap();
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (!correctDocs2.contains(ds.getKey())) {
                        hh.put(ds.getKey(), ds.getValue());
                    }
                }
                reff2.setValue(hh);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}