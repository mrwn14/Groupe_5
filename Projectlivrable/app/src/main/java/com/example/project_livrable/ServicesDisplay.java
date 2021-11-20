package com.example.project_livrable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ServicesDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_display);
        Bundle reg = getIntent().getExtras();
        String username = reg.getString("username");
        TextView txt = (TextView) findViewById(R.id.textService);
        txt.setText(username);
    }
}