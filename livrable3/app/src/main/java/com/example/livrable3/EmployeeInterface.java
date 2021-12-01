package com.example.livrable3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmployeeInterface extends AppCompatActivity {
    Button succInfoButton;
    Button requestViewButton;
    Button servicesChoiceButton;
    Bundle reg;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_interface);
        succInfoButton = (Button)findViewById(R.id.SuccInfoButton);
        requestViewButton = (Button)findViewById(R.id.requestViewButton);
        servicesChoiceButton = (Button)findViewById(R.id.servicesChoiceButton);
        reg = getIntent().getExtras();
        username = reg.getString("username");
    }

    public void succInfoOnClick(View view) {
        Intent succ = new Intent(getApplicationContext(), SuccInfoActivity.class);
        succ.putExtra("username", username);
        startActivity(succ);
    }
}