package com.example.livrable3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.livrable3.Admin.ServiceEditor;
import com.example.livrable3.Admin.ServiceEditor2;
import com.example.livrable3.Helpers.HelperService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ServicesDisplay extends AppCompatActivity {
    final String TAG = "hamid <3";
    String username;
    String serviceName;
    DatabaseReference referr;
    Bundle reg;
    DatabaseReference rafa2;

    int compteur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_display);
        reg = getIntent().getExtras();
        username = reg.getString("username");
        ArrayList<String> serviceNames = new ArrayList<String>();
        ArrayList<HelperService> services = new ArrayList<HelperService>();
        ListView myList = (ListView)findViewById(R.id.servicesList);
        TextView servicestext = (TextView) findViewById(R.id.servicesText);
        servicestext.setText("Services de la succursale " + username);
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, serviceNames);
        myList.setAdapter(myArrayAdapter);
        if(reg.getString("case").equals("Succursale")) {
            referr = FirebaseDatabase.getInstance().getReference().child("Services").child(username + "_services");
        }
        else{
            referr = FirebaseDatabase.getInstance().getReference().child("GeneralServices");
        }
        referr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String employeeUsername = snapshot.child("employeeUsername").getValue(String.class);
                serviceName = snapshot.child("serviceName").getValue(String.class);
                HashMap document = (HashMap) snapshot.child("document").getValue();
                HashMap formulaire = (HashMap) snapshot.child("formulaire").getValue();
                serviceNames.add(serviceName);
                compteur += 1;
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

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent hamid;
                if(reg.getString("case").equals("Succursale")) {
                    hamid = new Intent(getApplicationContext(), ServiceEditor.class);
                    hamid.putExtra("service", serviceNames.get(position));
                    hamid.putExtra("username", username);
                    hamid.putExtra("serviceName", serviceNames.get(position));
                }
                else{
                    hamid = new Intent(getApplicationContext(), ServiceEditor2.class);
                    hamid.putExtra("service", serviceNames.get(position));
                }
//                hamid.putExtra("username", username);
//                hamid.putExtra("serviceName", serviceNames.get(position));
//                hamid.putExtra("service", serviceNames.get(position));

                startActivity(hamid);
            }
        });

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isAddingServicePossible(compteur)) {
                    String delet = serviceNames.get(position);
                    showDialog(delet);
                    compteur -= 1;
                } else {
                    AlertDialog.Builder build = new AlertDialog.Builder(ServicesDisplay.this);
                    build.setMessage("Can't delete, you need at least 3 services.");
                    AlertDialog alert = build.create();
                    alert.show();
                }
                return true;
            }
        });

    }
    public void AddOnClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nom du service: ");


        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String service= input.getText().toString();
                reg = getIntent().getExtras();
                Intent hamid;
                for(int i = 0; i < 100000 ; i ++){
                    Log.d(TAG, "THE case is the following: " + reg.getString("case"));
                }
                if(reg.getString("case").equals("Succursale")) {
                    hamid = new Intent(getApplicationContext(),ServiceEditor.class);
                    hamid.putExtra("service", service);
                    hamid.putExtra("username", username);
                    hamid.putExtra("serviceName", "");
                }
                else if (reg.getString("case").equals("General")){
                    hamid = new Intent(getApplicationContext(),ServiceEditor2.class);

                    hamid.putExtra("service", service);
                }
                else{
                    hamid = new Intent(getApplicationContext(),ServiceEditor2.class);
                }
                addService(service);

                startActivity(hamid);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void addService(String service) {
        DatabaseReference rafa;
        reg = getIntent().getExtras();
        if (reg.getString("case").equals("Succursale")) {
            rafa2 = FirebaseDatabase.getInstance().getReference().child("GeneralServices");
            rafa = FirebaseDatabase.getInstance().getReference().child("Services").child(username + "_services");
        } else {
            rafa = FirebaseDatabase.getInstance().getReference().child("GeneralServices");
        }
        HashMap hamid = new HashMap();
        hamid.put("Prenom", "empty");
        hamid.put("Nom", "empty");
        hamid.put("Date de naissance", "empty");
        hamid.put("Adresse", "empty");

        HashMap hamid2 = new HashMap();
        hamid2.put("Preuve de domicile", "empty");

        HashMap hamid4 = new HashMap();


        HelperService ser = new HelperService(service, username, hamid, hamid2);
        hamid4.put(service, ser);
        rafa.child(service).setValue(ser);
        if (reg.getString("case").equals("Succursale")) {
            rafa2.child(service).setValue(ser);
        }
    }


    public void showDialog(String service) {
        AlertDialog.Builder dialogue = new AlertDialog.Builder(ServicesDisplay.this);
        dialogue.setMessage("Do you want to delete?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteService(service);
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog alert = dialogue.create();
        alert.show();
    }

    public void deleteService(String service) {
        DatabaseReference toDelete = referr.child(service);
        DatabaseReference toDelete2 = FirebaseDatabase.getInstance().getReference().child("Services");
        if (!reg.getString("case").equals("Succursale")){
            DatabaseReference referr2 = FirebaseDatabase.getInstance().getReference().child("Services");

            referr2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d("tag","username is :"+username);
                    Log.d("tag","snapshot is :"+snapshot.getKey());
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Log.d("tag","9lawi is :"+ds.getKey());

                        if (ds.getKey().equals(service)) {
                                ds.getRef().removeValue();
                        }
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
        }
        if (reg.getString("case").equals("Succursale")){
            toDelete2.child(username+"_services").child(service).removeValue();
            FirebaseDatabase.getInstance().getReference().child("Ratings").child(username).child(service).removeValue();
        }
        toDelete.removeValue();
        finish();
        startActivity(getIntent());

    }
    public static boolean isAddingServicePossible(int compteur){
        return (compteur > 3) ? true : false;
    }
}