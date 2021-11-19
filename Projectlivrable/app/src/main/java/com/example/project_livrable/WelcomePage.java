package com.example.project_livrable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Bundle reg = getIntent().getExtras();
        String firstName = reg.getString("firstName");
        String Role = reg.getString("role");

        TextView welcomeText = (TextView) findViewById(R.id.welcomeText);
        TextView roleText = (TextView) findViewById(R.id.roleText);

        welcomeText.setText("Bienvenue "+ firstName );
        roleText.setText("Vous êtes connecté en tant que " + Role);

    }
}