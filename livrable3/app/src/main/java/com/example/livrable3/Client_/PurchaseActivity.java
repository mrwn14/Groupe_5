package com.example.livrable3.Client_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.livrable3.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class PurchaseActivity extends AppCompatActivity {
    String TAG = "logerinooooo!";
    ListView myList;
    ArrayList<String> searchResults;
    DatabaseReference reff;
    Spinner spinner;
    EditText txtxtxt;
    ArrayAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        searchResults = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinner3);
        myList = (ListView) findViewById(R.id.myList);
        txtxtxt = (EditText) findViewById(R.id.lookupValue);
        myAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, searchResults);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.sort, android.R.layout.simple_spinner_item);
        myList.setAdapter(myAdapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        reff = FirebaseDatabase.getInstance().getReference().child("Succursales");
    }

    public void searchbuttonOnClick(View view) {
        searchResults = new ArrayList<String>();
        reff = FirebaseDatabase.getInstance().getReference().child("Succursales");
        String val = txtxtxt.getText().toString();
        String look = spinner.getSelectedItem().toString();
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (look.equals("Adresse") && ds.getKey().equals("Adresse") && ds.getValue().toString().contains(val)) {
                        searchResults.add(snapshot.getKey());
                        for (int i = 0; i < searchResults.size(); i++) {
                            Log.d(TAG, searchResults.get(i));
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                }
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
    }
}