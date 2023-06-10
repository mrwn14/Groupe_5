package com.example.livrable3.Client_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.livrable3.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ClientServiceRequest extends AppCompatActivity {
    Bundle reg;
    String service;
    String username;
    String employee;

    String TAG = "boo";
    ArrayList<String> services;
    ArrayList<String> servicesRight;
    ArrayList<String> added;
    ListView myList;
    ArrayAdapter myAdapter;
    DatabaseReference reff;
    DatabaseReference reff3;
    HashMap Daddy;
    int sizerino = 0;

    HashMap formulaire;
    HashMap document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_service_request);
        reg = getIntent().getExtras();
        service = reg.getString("service");
        username = reg.getString("username");
        employee = reg.getString("employeeName");
        Daddy = new HashMap();
        services = new ArrayList<String>();
        servicesRight = new ArrayList<String>();
        added = new ArrayList<String>();
        myList = (ListView) findViewById(R.id.listViewSubmission);
        myAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, services);
        myList.setAdapter(myAdapter);
        Log.d(TAG, "username :" + username);
        Log.d(TAG, "service :" + service);
        reff = FirebaseDatabase.getInstance().getReference().child("Services").child(employee+"_services").child(service);
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                document = new HashMap();
                formulaire = new HashMap();
//                if(snapshot.getKey().equals("document")){
//                    for (DataSnapshot k : snapshot.getChildren()){
//                        document.put(k.getKey(),k.getValue());
//                    }
//                    Daddy.put("document",document);
//                }
                if(snapshot.getKey().equals("formulaire")){
                    sizerino = (int) snapshot.getChildrenCount();
                    for (DataSnapshot k : snapshot.getChildren()){
                        formulaire.put(k.getKey(),k.getValue());
                        services.add(k.getKey());
                        servicesRight.add(k.getKey());
                    }
                }
                if(snapshot.getKey().equals("employeeUsername")){
                    Daddy.put(snapshot.getKey(),snapshot.getValue());
                }
                Log.d(TAG, services.toString());
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
                AlertDialog.Builder build = new AlertDialog.Builder(ClientServiceRequest.this);
                build.setMessage("Valeur du champ");
                final EditText txt = new EditText(ClientServiceRequest.this);
                txt.setInputType(InputType.TYPE_CLASS_TEXT);
                build.setView(txt);
                build.setPositiveButton("Enregister", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String val = txt.getText().toString();
                        formulaire.put(servicesRight.get(position),val);
                        String temp = servicesRight.get(position);
                        services.remove(position);
                        servicesRight.remove(position);
                        servicesRight.add(position,temp);
                        services.add(position, temp + " : " + val);
                        if (!added.contains(temp)){
                            added.add(temp);
                        }
                        Log.d(TAG, added.toString());
                        myAdapter.notifyDataSetChanged();

                    }
                });
                build.setNegativeButton("Annuler", null);
                Daddy.put("formulaire", formulaire);
                AlertDialog alert = build.create();
                alert.show();

            }
        });

    }

    public void submitOnClick(View view) throws InterruptedException {
        HashMap form = (HashMap) Daddy.get("formulaire");
        if(form != null && form.size()>=sizerino) {
            if (prenomValidator((String) form.get("Prenom")) && nomValidator((String) form.get("Nom")) && dateDeNaissanceValidator((String) form.get("Date de naissance")) && adresseValidator((String) form.get("Adresse"))) {
                reff = FirebaseDatabase.getInstance().getReference().child("Client_requests").child(username).child("pending").child(service);
                reff3 = FirebaseDatabase.getInstance().getReference().child("Requests").child(employee).child("pending").child(service + "_" + username);
                if (services.size() == added.size()) {
                    reff.setValue(Daddy);
                    reff3.setValue(Daddy);

                    Intent hamid = new Intent(getApplicationContext(), ClientRatingActivity.class);
                    hamid.putExtra("username", username);
                    hamid.putExtra("employee", employee);
                    hamid.putExtra("service", service);
                    hamid.putExtra("dialog", "1");
                    startActivity(hamid);

                }
            } else {
                AlertDialog.Builder build = new AlertDialog.Builder(ClientServiceRequest.this);
                build.setMessage("Champ Invalide");
                AlertDialog alert = build.create();
                alert.show();
            }
        } else {
            AlertDialog.Builder build = new AlertDialog.Builder(ClientServiceRequest.this);
            build.setMessage("Vous devez remplir tout les champs");
            AlertDialog alert = build.create();
            alert.show();
        }
    }
    public static boolean nomValidator(String test){
        return  test.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
    }
    public static boolean prenomValidator(String test){
        return  test.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
    }
    public static boolean dateDeNaissanceValidator(String test){
        String[] testArray = test.split("/");
        return testArray.length == 3 && testArray[0].length() == 2 && testArray[1].length() == 2 && testArray[2].length() == 4;
    }
    public static boolean adresseValidator(String address) {
        return address.matches(
                "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" );
    }
}