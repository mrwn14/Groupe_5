package com.example.livrable3.Client_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.livrable3.R;
import com.example.livrable3.Register_and_Login.MainActivity;

public class ClientInterface extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Bundle reg = getIntent().getExtras();
        String firstName = reg.getString("firstName");
        String Role = reg.getString("role");
        username = reg.getString("username");
    }
    public void acheterButtonOnClick(View view){
        Intent purchaseItent = new Intent(getApplicationContext(), PurchaseActivity.class);
        startActivity(purchaseItent);
    }
}