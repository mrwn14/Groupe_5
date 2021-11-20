package com.example.project_livrable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class ServicesDisplay extends AppCompatActivity {
    final String TAG = "hamod <3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_display);
        Bundle reg = getIntent().getExtras();
        String username = reg.getString("username");
        ArrayList<String> serviceNames = new ArrayList<String>();
        ArrayList<HelperService> services = new ArrayList<HelperService>();
        ListView myList = (ListView)findViewById(R.id.servicesList);
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, serviceNames);
        myList.setAdapter(myArrayAdapter);
        DatabaseReference referr = FirebaseDatabase.getInstance().getReference().child("Services").child(username+"_services");
        referr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String employeeUsername = snapshot.child("employeeUsername").getValue(String.class);
                String serviceName = snapshot.child("serviceName").getValue(String.class);
                HashMap document = (HashMap) snapshot.child("document").getValue();
                HashMap formulaire = (HashMap) snapshot.child("formulaire").getValue();
//                int n = 0;
//                while (n < 100000) {
//                    Log.d(TAG, "AAAAAAAAAAAAAAHGSJSJDAASASAA " + employeeUsername + " " + serviceName + " " + document.get("Pdd")+ " "+formulaire.get("adresse") );
//                    n += 1;
//                }


                serviceNames.add(serviceName);
                services.add(new HelperService(serviceName, employeeUsername, formulaire, document));
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
}