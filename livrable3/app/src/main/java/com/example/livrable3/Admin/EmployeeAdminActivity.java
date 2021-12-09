package com.example.livrable3.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.livrable3.Helpers.HelperClass;
import com.example.livrable3.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EmployeeAdminActivity extends AppCompatActivity {

    DatabaseReference myref;
    DatabaseReference myref2;
    DatabaseReference myref3;
    DatabaseReference myref4;
    Bundle reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_admin);
        ListView myList = (ListView)findViewById(R.id.SuccListView);
        ArrayList<HelperClass> helisss = new ArrayList<HelperClass>();
        ArrayList<String> lissss = new ArrayList<String>();
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lissss);
        myList.setAdapter(myArrayAdapter);
        reg = getIntent().getExtras();
        TextView txt = (TextView) findViewById(R.id.RoleAdmin);
        txt.setText("Users");

        myref = FirebaseDatabase.getInstance().getReference().child(reg.getString("role"));
        myref2 = FirebaseDatabase.getInstance().getReference().child("Services");
        myref3 = FirebaseDatabase.getInstance().getReference().child("Succursales");
        myref4 = FirebaseDatabase.getInstance().getReference().child("Client_requests");

        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HelperClass value = snapshot.getValue(HelperClass.class);
                lissss.add(value.getUsername());
                helisss.add(value);
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

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String us = lissss.get(position);
                showDialog(us);
                return true;
            }
        });
    }

    //I slept at 7am so dont wake me up, I sent the vid i used on discord
    //Hope your understand shdrt



    public void showDialog(String username) {
        AlertDialog.Builder dialogue = new AlertDialog.Builder(EmployeeAdminActivity.this);
        //LayoutInflater layoutInflate= getLayoutInflater();
        //View dialogueView = layoutInflate.inflate(R.layout.activity_succ_list, null);
        //dialogue.setView(dialogueView);
        dialogue.setMessage("Do you want to delete?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSucc(username);
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog alert = dialogue.create();
        alert.show();
    }

    public void deleteSucc(String username) {
        if(reg.getString("role").equals("Employé(e)")){
            DatabaseReference toDelete = myref.child(username);
            DatabaseReference toDelete2 = myref2.child(username+"_services");
            DatabaseReference toDelete3 = myref3.child(username);

            toDelete.removeValue();
            toDelete2.removeValue();
            toDelete3.removeValue();
            finish();
            startActivity(getIntent());
        }
        else {
            DatabaseReference toDelete = myref.child(username);
            DatabaseReference toDelete2 = myref4.child(username);

            toDelete.removeValue();
            toDelete2.removeValue();
            finish();
            startActivity(getIntent());
        }
    }
}

