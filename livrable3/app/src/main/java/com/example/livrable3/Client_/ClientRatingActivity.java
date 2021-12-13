package com.example.livrable3.Client_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import com.example.livrable3.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientRatingActivity extends AppCompatActivity {
    DatabaseReference reff;
    DatabaseReference reff2;
    Bundle reg;
    String service;
    String username;
    String employee;
    float rating;
    int num;
    RatingBar hamid;
    int verificator = 0;
    boolean tester = false;
    String TAG = "clientRatingServiceLog";
    float ratata;
    int number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_rating);
        hamid = (RatingBar) findViewById(R.id.rating);
        reg = getIntent().getExtras();
        employee = reg.getString("employee");
        service = reg.getString("service");
        username = reg.getString("username");
        reff = FirebaseDatabase.getInstance().getReference().child("Ratings").child(employee).child(service);
        reff.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    tester = true;
                    String pp = snapshot.getValue().toString();
                    Log.d(TAG,pp);
                    Log.d(TAG,"IM HERE MANN " + tester);
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
    }
    public void submitRatingButton(View view){
        rating = hamid.getRating();
        if(tester == false) {
            reff = FirebaseDatabase.getInstance().getReference().child("Ratings").child(employee).child(service);
            reff.child("rating").setValue(0);
            reff.child("num").setValue(0);
        }
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if(snapshot.getKey().equals("rating")){
                    Log.d(TAG, snapshot.getValue().toString());
                    ratata = Float.parseFloat(snapshot.getValue().toString());
                    verificator += 1;
                }
                if(snapshot.getKey().equals("num")){
                    Log.d(TAG, snapshot.getValue().toString());
                    number = Integer.parseInt(snapshot.getValue().toString());
                    verificator += 1;
                }
                if(verificator == 2) {
                    rating = (rating + number * ratata) / (number + 1);
                    num = number + 1;
                    reff.child("rating").setValue(rating);
                    reff.child("num").setValue(num);
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
        Intent hamid = new Intent(getApplicationContext(),ClientInterface.class);
        hamid.putExtra("username",username);
        hamid.putExtra("employee",employee);
        hamid.putExtra("service",service);
        hamid.putExtra("dialog","1");
        startActivity(hamid);

    }
}