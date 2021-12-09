package com.example.livrable3.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ServiceEditor extends AppCompatActivity {

    EditText editForm;
    EditText editDocs;
    ArrayList<String> formNames;
    ArrayList<String> docNames;
    DatabaseReference ref;
    DatabaseReference ref2;
    String serviceName;
    String service;
    String username;
    String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_editor);
        Bundle reg = getIntent().getExtras();
        username = reg.getString("username");
        serviceName = reg.getString("serviceName");
        service = reg.getString("service");
        formNames = new ArrayList<String>();
        docNames = new ArrayList<String>();
        for (String elem:formNames) {
            Log.d(TAG, "FOOOOOOORM ELEMENTS ARE: " + elem);
        }
        for (String elem:docNames) {
            Log.d(TAG, "DOOOOOOOOOCS ELEMENTS ARE: " + elem);
        }
//        Log.d("TAG", "THE RECEIVED service IS THE FOLLOWING : "+ caseType);
        ListView formlist = (ListView) findViewById(R.id.formList);
        ListView doclist = (ListView) findViewById(R.id.docList);

        editForm = (EditText) findViewById(R.id.editFormulaire);
        editDocs = (EditText) findViewById(R.id.editDocuments);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, formNames);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, docNames);

        formlist.setAdapter(myAdapter);
        doclist.setAdapter(myAdapter2);
            ref = FirebaseDatabase.getInstance().getReference().child("Services").child(username + "_services").child(service).child("formulaire");

        ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String documentIterable = snapshot.getValue(String.class);
                    String hamid = snapshot.getKey();
                    formNames.add(hamid);
//                    Log.d(TAG, "started");
//                    while(documentIterable.iterator().hasNext()) {
//                        Log.d(TAG, "onChildAdded: " + documentIterable.iterator().next());
//                    }
//                    Log.d(TAG, "finished");

//                    Object[] hamid = document.keySet().toArray();

//                        for (Object k : hamid) {
//                            docNames.add(String.valueOf(k));
//                            Log.d(TAG, "THE STRING DOCUMENT VALUES ARE THE FOLLOWING : "+ k);
//                        }
//                        HashMap formulaire = (HashMap) snapshot.child("formulaire").getValue();
//                        Object[] hamid2 = formulaire.keySet().toArray();
//
//                        for (Object k : hamid2) {
//                            formNames.add(String.valueOf(k));
//                            Log.d(TAG, "THE STRING FORM VALUES ARE THE FOLLOWING : "+ k);
//
//                        }

                        myAdapter.notifyDataSetChanged();



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

        ref2 = FirebaseDatabase.getInstance().getReference().child("Services").child(username + "_services").child(service).child("document");
        ref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String documentIterable2 = snapshot.getValue(String.class);
                String hamid = snapshot.getKey();
                docNames.add(hamid);
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

        formlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String delet = formNames.get(position);
                showDialogForm(delet);
                return true;
            }
        });
        formlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ServiceEditor.this);
                builder.setTitle("Changer le nom du champ: ");
                final EditText input = new EditText(ServiceEditor.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String service= input.getText().toString();
                        if(!service.equals("")) {
                            ref.child(service).setValue("empty");
                            DatabaseReference hamid = ref.child(formNames.get(position));
                            hamid.removeValue();
                            formNames.remove(position);
                            formNames.add(service);
                        }
                        else{
                            AlertDialog.Builder build = new AlertDialog.Builder(ServiceEditor.this);
                            build.setMessage("Can't change, empty input.");
                            AlertDialog alert = build.create();
                            alert.show();
                        }

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
        });
        doclist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ServiceEditor.this);
                builder.setTitle("Changer le nom du champ: ");
                final EditText input = new EditText(ServiceEditor.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String service= input.getText().toString();
                        if(!service.equals("")) {
                            ref2.child(service).setValue("empty");
                            DatabaseReference hamid = ref2.child(docNames.get(position));
                            hamid.removeValue();
                            docNames.remove(position);
                            docNames.add(service);
                        }
                        else{
                            AlertDialog.Builder build = new AlertDialog.Builder(ServiceEditor.this);
                            build.setMessage("Can't change, empty input.");
                            AlertDialog alert = build.create();
                            alert.show();
                        }

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
        });

        doclist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String delet = docNames.get(position);
                showDialogDoc(delet);
                return true;
            }
        });
    }

    public void FormulaireAdd(View view) {
        String champ = editForm.getText().toString();
        ref = FirebaseDatabase.getInstance().getReference().child("Services").child(username + "_services").child(service);
        //ref2 = FirebaseDatabase.getInstance().getReference().child("GeneralServices").child(username).child(service);
        if(isDocValid(champ) && !formNames.contains(champ)){
            formNames.add(champ);
            ref.child("formulaire").child(champ).setValue("empty");
            //ref2.child("formulaire").child(champ).setValue("empty");
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
    public void DocumentsAdd(View view) {
        String champ = editDocs.getText().toString();
        ref = FirebaseDatabase.getInstance().getReference().child("Services").child(username + "_services").child(service);
        ref2 = FirebaseDatabase.getInstance().getReference().child("GeneralServices").child(username).child(service);
        if(!champ.equals("") && !docNames.contains(champ)){
            docNames.add(champ);
            ref.child("document").child(champ).setValue("empty");
            ref2.child("document").child(champ).setValue("empty");

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

    public void showDialogForm(String champ) {
        AlertDialog.Builder dialogue = new AlertDialog.Builder(ServiceEditor.this);
        dialogue.setMessage("Do you want to delete?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteChampForm(champ);
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog alert = dialogue.create();
        alert.show();
    }
    public void showDialogDoc(String champ) {
        AlertDialog.Builder dialogue = new AlertDialog.Builder(ServiceEditor.this);
        dialogue.setMessage("Do you want to delete?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteChampDoc(champ);
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog alert = dialogue.create();
        alert.show();
    }

    public void deleteChampForm(String champ) {
        DatabaseReference toDelete = ref.child(champ);
        /*
        */
//        DatabaseReference referr2 = FirebaseDatabase.getInstance().getReference().child("Services");
//        referr2.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    for (DataSnapshot hamid : ds.getChildren()) {
//                        Log.d(TAG, "COUNT1 IS: " + hamid.getChildrenCount());
//                        if(hamid.getKey().equals(service)){
//                            hamid.child("formulaire").child(champ).getRef().removeValue();
//                        }
////                        HashMap hh = (HashMap) hamid.getValue();
////                        Set x = hh.keySet();
////                        for (Object elem : x) {
////                            elem = elem.toString();
////                            if (elem.equals(service)) {
////                                ds.getRef().child(service).removeValue();
////                            }
////                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        /*
         */
        toDelete.removeValue();
        finish();
        startActivity(getIntent());

    }
    public void deleteChampDoc(String champ) {
        DatabaseReference toDelete = ref2.child(champ);

        toDelete.removeValue();
        finish();
        startActivity(getIntent());
    }
    public void doneButton(View view){
        finish();
    }
    public static boolean isDocValid(String champ){
        return !champ.equals("");
    }
}