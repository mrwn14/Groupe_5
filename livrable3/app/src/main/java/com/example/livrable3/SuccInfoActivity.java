package com.example.livrable3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succ_info);
        ListView generalListView = (ListView) findViewById(R.id.GeneralListView);
        ArrayList<String> generals = new ArrayList<String>();
        addInfoEdit = (EditText) findViewById(R.id.addInfoEdit);
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, generals);
        generalListView.setAdapter(myArrayAdapter);
        reg = getIntent().getExtras();
        username  = reg.getString("username");
        ref = FirebaseDatabase.getInstance().getReference().child("Succursales").child(username);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String documentIterable = snapshot.getValue(String.class);
                String hamid = snapshot.getKey();
                generals.add(hamid);
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
                String delet = generals.get(position);
                showDialogDoc(delet);
                return true;
            }
        });

        generalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SuccInfoActivity.this);
                builder.setTitle("Changer la valeur du champ: ");
                final EditText input = new EditText(SuccInfoActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String service= input.getText().toString();
                        if(!service.equals("")) {
                            ref.child(generals.get(position)).setValue(service);
                            generals.remove(position);
                            generals.add(service);
                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
            }
        });
    }

    public void buttonAddOnClick(View view) {


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
}