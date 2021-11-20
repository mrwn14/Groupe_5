package com.example.project_livrable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    final String TAG = "hamid <3";
    String username;
    String serviceName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_display);
        Bundle reg = getIntent().getExtras();
        username = reg.getString("username");
        ArrayList<String> serviceNames = new ArrayList<String>();
        ArrayList<HelperService> services = new ArrayList<HelperService>();
        ListView myList = (ListView)findViewById(R.id.servicesList);
        TextView servicestext = (TextView) findViewById(R.id.servicesText);
        servicestext.setText("Services de la succursale " + username);
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, serviceNames);
        myList.setAdapter(myArrayAdapter);
        DatabaseReference referr = FirebaseDatabase.getInstance().getReference().child("Services").child(username+"_services");
        referr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String employeeUsername = snapshot.child("employeeUsername").getValue(String.class);
                serviceName = snapshot.child("serviceName").getValue(String.class);
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

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent hamid = new Intent(getApplicationContext(),ServiceEditor.class);
                hamid.putExtra("username", username);
                //hamid.putExtra("firstName", helisss.get(position).getFirstName());
                hamid.putExtra("serviceName", serviceNames.get(position));
                startActivity(hamid);
            }
        });
    }
    public void AddOnClick(View view){
        Intent hamid = new Intent(getApplicationContext(),ServiceEditor.class);
        hamid.putExtra("username", username);
        hamid.putExtra("serviceName", "");
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
                hamid.putExtra("service", service);
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
        DatabaseReference rafa = FirebaseDatabase.getInstance().getReference().child("Services").child(username+"_services");
        HashMap hamid = new HashMap();
        hamid.put("prenom", "empty");
        hamid.put("nom", "empty");
        hamid.put("ddn","empty");
        hamid.put("adresse","empty");

        HashMap hamid2 =  new HashMap();
        hamid2.put("Pdd","empty");

        HashMap hamid4 = new HashMap();


        HelperService ser = new HelperService(service, username, hamid, hamid2);
        hamid4.put(service, ser);
        rafa.child(service).setValue(ser);
    }
}