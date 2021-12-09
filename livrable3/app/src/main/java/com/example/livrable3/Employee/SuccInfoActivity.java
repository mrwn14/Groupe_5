package com.example.livrable3.Employee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.livrable3.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SuccInfoActivity extends AppCompatActivity {
    DatabaseReference ref;
    Bundle reg;
    String username;
    EditText addInfoEdit;
    ArrayList<String> generals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succ_info);
        ListView generalListView = (ListView) findViewById(R.id.GeneralListView);
        generals = new ArrayList<String>();
        addInfoEdit = (EditText) findViewById(R.id.addInfoEdit);
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, generals);
        generalListView.setAdapter(myArrayAdapter);
        reg = getIntent().getExtras();
        username  = reg.getString("username");
        ref = FirebaseDatabase.getInstance().getReference().child("Succursales").child(username);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //String documentIterable = snapshot.getValue(String.class);
                String hamid = snapshot.getKey();
                if(!hamid.equals("Heures de travail")){
                    String hamid2 = (String) snapshot.getValue();
                    generals.add(hamid + ": "+hamid2);
                    Log.d("jonathon", "I'M here ( but like up yk )");
                }
                else{
                    Log.d("jonathon", "I'M here");
                    generals.add(hamid + ": Click to see more");
                }
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

        generalListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String delet = generals.get(position).split(":")[0];
                showDialogDoc(delet);
                return true;
            }
        });

        generalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(generals.get(position).split(":")[0].equals("Heures de travail")){
                    Intent daysIntent = new Intent(getApplicationContext(), DaysActivity.class);
                    daysIntent.putExtra("username",username);
                    startActivity(daysIntent);

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SuccInfoActivity.this);
                    builder.setTitle("Changer la valeur du champ: ");
                    final EditText input = new EditText(SuccInfoActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String service= input.getText().toString();
                            if((!service.equals("") && validateAdress(service)) || (!service.equals("") && validateNumber(service))) {
                                ref.child(generals.get(position).split(":")[0]).setValue(service);
                                generals.remove(position);
                                generals.add(service);
                                finish();
                                startActivity(getIntent());
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SuccInfoActivity.this);
                                builder.setMessage("Champ Invalide, svp reessayer");
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
            }
        }});

    }

    public void buttonAddOnClick(View view) {
        String val = addInfoEdit.getText().toString();
        if(!val.equals("") && !val.contains(":")){
            AlertDialog.Builder builder = new AlertDialog.Builder(SuccInfoActivity.this);
            builder.setTitle("Valeur du champs");
            EditText valeur = new EditText(SuccInfoActivity.this);
            valeur.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(valeur);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ref = FirebaseDatabase.getInstance().getReference().child("Succursales").child(username);
                    ref.child(val).setValue(valeur.getText().toString());
                    finish();
                    startActivity(getIntent());
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
        else{

        }


    }

    public void showDialogDoc(String champ) {
        AlertDialog.Builder dialogue = new AlertDialog.Builder(SuccInfoActivity.this);
        dialogue.setMessage("Do you want to delete?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteChamp(champ);
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog alert = dialogue.create();
        alert.show();
    }

    public void deleteChamp(String champ) {
        DatabaseReference toDelete = ref.child(champ);

        toDelete.removeValue();
        finish();
        startActivity(getIntent());
    }

    public static boolean validateNumber(String number) {
        return number.matches("^\\+([0-9\\-]?){9,11}[0-9]$");
    }

    public static boolean validateAdress(String address) {
        return address.matches(
                "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" );
    }
    public static boolean textInputValidator(String test){
        return !test.equals("") && !test.contains(":");
    }
}