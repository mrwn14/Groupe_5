package com.example.livrable3.Client_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.livrable3.Admin.AdminInterface;
import com.example.livrable3.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import java.util.List;

public class PurchaseActivity extends AppCompatActivity {
    String TAG = "logerinooooo!";
    ListView myList;
    ArrayList<String> searchResults;
    ArrayList<String> searchResultsNames;
    DatabaseReference reff;
    DatabaseReference reff2;
    Spinner spinner;
    EditText txtxtxt;
    ArrayAdapter myAdapter;
    Bundle reg;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        reg = getIntent().getExtras();
        username = reg.getString("username");

        txtxtxt = (EditText) findViewById(R.id.lookupValue);



        spinner = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.sort, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        myList = (ListView) findViewById(R.id.myList);
        searchResults = new ArrayList<String>();
        searchResultsNames = new ArrayList<String>();
        myAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, searchResults);
        myList.setAdapter(myAdapter);

        reff = FirebaseDatabase.getInstance().getReference().child("Succursales");
        reff2 = FirebaseDatabase.getInstance().getReference().child("Services");

    }

    public void searchbuttonOnClick(View view) {
        searchResults.clear();
        searchResultsNames.clear();
        reff = FirebaseDatabase.getInstance().getReference().child("Succursales");
        String val = txtxtxt.getText().toString();
        String look = spinner.getSelectedItem().toString();
        Calendar.getInstance().setTimeZone(TimeZone.getTimeZone("EST"));
        String[] hamid = Calendar.getInstance().getTime().toString().split(" ");
        String day = hamid[0];
        String hour =  String.valueOf((Integer.parseInt(hamid[3].split(":")[0])+2) % 24);
        Log.d(TAG, day + " " + hour);

        switch (day) {
            case "Mon":  day = "Lundi";
                break;
            case "Tue":  day = "Mardi";
                break;
            case "Wed":  day = "Mercredi";
                break;
            case "Thu":  day = "Jeudi";
                break;
            case "Fri":  day = "Vendredi";
                break;
            case "Sat":  day = "Samedi";
                break;
            case "Sun":  day = "Dimanche";
                break;
        }
        final String dayFinal = day;
        final int hourFinal = Integer.parseInt(hour);
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (look.equals("Adresse") && ds.getKey().equals("Adresse") && ds.getValue().toString().contains(val)) {
                        searchResults.add(ds.getValue().toString());
                        searchResultsNames.add(snapshot.getKey());
                        for (int i = 0; i < searchResults.size(); i++) {
                            Log.d(TAG, searchResults.get(i));
                        }
                    }
                    if(look.equals("Heures de travail") && ds.getKey().equals("Heures de travail")){
                        DataSnapshot dayRef = ds.child(dayFinal);
                        int startTime = Integer.parseInt(dayRef.child("Start time").getValue().toString().split(":")[0]);
                        int endtTime = Integer.parseInt(dayRef.child("End time").getValue(String.class).split(":")[0]);
                        if (hourFinal >= startTime && hourFinal < endtTime) {
                            searchResults.add(snapshot.getKey());
                            searchResultsNames.add(snapshot.getKey());
                        }
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

        reff2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (look.equals("Services") && ds.getValue().toString().contains(val)) {
                        searchResults.add(ds.getKey().toString() +" from "+ snapshot.getKey().split("_")[0]);
                        searchResultsNames.add(snapshot.getKey().split("_")[0]);
                        for (int i = 0; i < searchResults.size(); i++) {
                            Log.d(TAG, searchResults.get(i));
                        }
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
                Intent hamid = new Intent(getApplicationContext(), ClientServiceDisplay.class);
                hamid.putExtra("usernameEmployee", searchResultsNames.get(position));
                hamid.putExtra("username", username);
                startActivity(hamid);
            }
        });
    }
}