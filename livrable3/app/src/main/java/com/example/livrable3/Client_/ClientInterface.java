package com.example.livrable3.Client_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.livrable3.R;
import com.example.livrable3.Register_and_Login.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ClientInterface extends AppCompatActivity {
    String username;
    String dialog;
    ListView pending;
    ListView approved;
    ArrayAdapter myAdapter1;
    ArrayAdapter myAdapter2;
    ArrayList<String> myArray1;
    ArrayList<String> myArray2;
    DatabaseReference ref1;
    DatabaseReference ref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Bundle reg = getIntent().getExtras();

        username = reg.getString("username");
        dialog  = reg.getString("dialog");

        if(dialog.equals("1")){
            AlertDialog.Builder builder = new AlertDialog.Builder(ClientInterface.this);
            builder.setMessage("La requête a été envoyée et votre évaluation a été enregistrée!");
            AlertDialog alert = builder.create();
            alert.show();
        }

        myArray1 = new ArrayList<String>();
        myArray2 = new ArrayList<String>();

        myAdapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, myArray1);
        myAdapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, myArray2);

        pending = (ListView) findViewById(R.id.ListViewPending);
        approved = (ListView) findViewById(R.id.ListViewAccepted);

        pending.setAdapter(myAdapter1);
        approved.setAdapter(myAdapter2);

        ref1 = FirebaseDatabase.getInstance().getReference().child("Client_requests").child(username).child("pending");
        ref2 = FirebaseDatabase.getInstance().getReference().child("Client_requests").child(username).child("approved");

        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(!myArray1.contains(snapshot.getKey())){
                        myArray1.add(snapshot.getKey());
                    }
                myAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myAdapter1.notifyDataSetChanged();
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
        ref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    myArray2.add(ds.getKey());
                }
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

    }
    public void acheterButtonOnClick(View view){
        Intent purchaseIntent = new Intent(getApplicationContext(), PurchaseActivity.class);
        purchaseIntent.putExtra("username", username);
        startActivity(purchaseIntent);
    }
}