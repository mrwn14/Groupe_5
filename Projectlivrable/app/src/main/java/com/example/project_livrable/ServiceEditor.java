package com.example.project_livrable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;

public class ServiceEditor extends AppCompatActivity {

    EditText editForm;
    EditText editDocs;
    ArrayList<String> formNames;
    ArrayList<String> docNames;
    DatabaseReference ref;
    String serviceName;
    String service;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_editor);
        Bundle reg = getIntent().getExtras();
        username = reg.getString("username");
        serviceName = reg.getString("serviceName");
        service = reg.getString("service");
        ListView formlist = (ListView) findViewById(R.id.formList);
        ListView doclist = (ListView) findViewById(R.id.docList);
        formNames = new ArrayList<String>();
        docNames = new ArrayList<String>();
        editForm = (EditText) findViewById(R.id.editFormulaire);
        editDocs = (EditText) findViewById(R.id.editDocuments);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, formNames);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, docNames);

        formlist.setAdapter(myAdapter);
        doclist.setAdapter(myAdapter2);
        ref = FirebaseDatabase.getInstance().getReference().child("Services").child(username + "_services").child(service);
            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    try {
                        HashMap document = (HashMap) snapshot.child("document").getValue();
                        Object[] hamid = document.keySet().toArray();

                        for (Object k : hamid) {
                            docNames.add(String.valueOf(k));
                        }
                        HashMap formulaire = (HashMap) snapshot.child("formulaire").getValue();
                        Object[] hamid2 = formulaire.keySet().toArray();

                        for (Object k : hamid2) {
                            formNames.add(String.valueOf(k));
                        }
                        myAdapter.notifyDataSetChanged();
                        myAdapter2.notifyDataSetChanged();
                    }
                    catch (Exception e){

                    }

//                    if (!formNames.contains("prenom")) formNames.add("prenom");
//                    if (!formNames.contains("nom")) formNames.add("nom");
//                    if (!formNames.contains("adresse")) formNames.add("adresse");
//                    if (!formNames.contains("date de naissance")) formNames.add("date de naissance");
//                    if (!docNames.contains("Preuve de domicile")) docNames.add("Preuve de domicile");
//                    myAdapter.notifyDataSetChanged();
//                    myAdapter2.notifyDataSetChanged();

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
    public void FormulaireAdd(View view) {
        String champ = editForm.getText().toString();
        ref = FirebaseDatabase.getInstance().getReference().child("Services").child(username + "_services").child(service);
        if(!champ.equals("") && !formNames.contains(champ)){
            formNames.add(champ);
            ref.child("formulaire").child(champ).setValue("empty");
            getIntent().putExtra("service", service);
            getIntent().putExtra("serviceName", serviceName);
            getIntent().putExtra("username", username);

            finish();
            startActivity(getIntent());
        }
        else{
            Log.d("hamid", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
    }
}