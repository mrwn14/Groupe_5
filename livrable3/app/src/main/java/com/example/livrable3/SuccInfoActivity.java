package com.example.livrable3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SuccInfoActivity extends AppCompatActivity {
    DatabaseReference ref;
    Bundle reg;
    String username;
    EditText addInfoEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succ_info);
        ListView generalListView = (ListView) findViewById(R.id.GeneralListView);
        ArrayList<String> generals = new ArrayList<String>();
        addInfoEdit = (EditText) findViewById(R.id.addInfoEdit);
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, generals);
        generalListView.setAdapter(myArrayAdapter);
        reg = getIntent().getExtras();
        username  = reg.getString("username");
        ref = FirebaseDatabase.getInstance().getReference().child("Succursales").child(username);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String documentIterable = snapshot.getValue(String.class);
                String hamid = snapshot.getKey();
                generals.add(hamid);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myArrayAdapter.notifyDataSetChanged();
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

    public void buttonAddOnClick(View view) {


    }
}