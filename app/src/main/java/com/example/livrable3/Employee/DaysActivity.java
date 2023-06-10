package com.example.livrable3.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.livrable3.R;

import java.util.ArrayList;

public class DaysActivity extends AppCompatActivity {
    ListView ListViewDays;
    Bundle reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
        ListViewDays = (ListView) findViewById(R.id.ListViewDays);
        ArrayList<String> days = new ArrayList<String>();
        reg = getIntent().getExtras();
        String username = reg.getString("username");
        days.add("Lundi");
        days.add("Mardi");
        days.add("Mercredi");
        days.add("Jeudi");
        days.add("Vendredi");
        days.add("Samedi");
        days.add("Dimanche");
        ArrayAdapter<String> hamid = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,days);
        ListViewDays.setAdapter(hamid);
        ListViewDays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent hamid = new Intent(getApplicationContext(), TimeActivity.class);
                hamid.putExtra("username",username);
                hamid.putExtra("day", days.get(position));
                startActivity(hamid);
            }
        });
    }
}