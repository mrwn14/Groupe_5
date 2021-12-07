package com.example.livrable3.Employee;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.os.Bundle;

import com.example.livrable3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TimeActivity extends AppCompatActivity {
    private TimePicker timePicker1;
    private TimePicker timePicker2;
    DatabaseReference ref;
    Bundle reg;
    String username;
    String day;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        reg = getIntent().getExtras();
        username = reg.getString("username");
        day = reg.getString("day");
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timePicker2 = (TimePicker) findViewById(R.id.timePicker2);
        ref = FirebaseDatabase.getInstance().getReference().child("Succursales").child(username).child("Heures de travail").child(day);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap time = (HashMap) snapshot.getValue();
                String hourS = time.get("Start time").toString().split(":")[0];
                String minutesS = time.get("Start time").toString().split(":")[1];
                String hourE = time.get("End time").toString().split(":")[0];
                String minutesE = time.get("End time").toString().split(":")[1];
                Log.d("hour is", hourS);
                Log.d("minutes is", minutesS);
                Log.d("hour is", hourE);
                Log.d("minutes is", minutesE);
                timePicker1.setHour(Integer.parseInt(hourS));
                timePicker1.setMinute(Integer.parseInt(minutesS));
                timePicker2.setHour(Integer.parseInt(hourE));
                timePicker2.setMinute(Integer.parseInt(minutesE));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void saveOnClick(View view){
        reg = getIntent().getExtras();
        username = reg.getString("username");
        day = reg.getString("day");
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timePicker2 = (TimePicker) findViewById(R.id.timePicker2);


        HashMap startDate = new HashMap();
        HashMap endDate = new HashMap();
        ref = FirebaseDatabase.getInstance().getReference().child("Succursales").child(username).child("Heures de travail").child(day);
        startDate.put("Start time",timePicker1.getHour()+":"+timePicker1.getMinute());
        startDate.put("End time",timePicker2.getHour()+":"+timePicker2.getMinute());
        ref.setValue(startDate);
        finish();
    }

}