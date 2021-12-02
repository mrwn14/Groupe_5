package com.example.livrable3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;

public class EmployeeServiceChoice extends AppCompatActivity {
    ScrollView serviceChoice;
    DatabaseReference reff;
    ArrayList<String> correctDocs;
    long size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_service_choice);
        serviceChoice = (ScrollView) findViewById(R.id.serviceChoice);
        reff = FirebaseDatabase.getInstance().getReference().child("GeneralServices");
        correctDocs = new ArrayList<String>();
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    CheckBox cb = new CheckBox(EmployeeServiceChoice.this);
                    String val = ds.getKey();
                    cb.setText(val);
                    cb.setId(val.hashCode());
                    cb.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    serviceChoice.addView(cb);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        serviceChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    ((CheckBox)v).setChecked(false);
                }
                ((CheckBox) v).setChecked(true);
            }
        });

    }

    public void saveDocs(View view) {
        reff = FirebaseDatabase.getInstance().getReference().child("GeneralServices");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                size = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for (int i = 0; i < size; i++) {
            View v = serviceChoice.getChildAt(i);
            if (((CheckBox) v).isChecked()) {
                correctDocs.add(((CheckBox) v).getText().toString());
            }
        }
    }
}