package com.example.project_livrable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "hamid <3";
    helperClass loginMf = new helperClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle reg = getIntent().getExtras();
        if (reg == null) {
            return;
        }
        boolean fromRegistered = reg.getBoolean("fromRegistered");
        if (fromRegistered) {
            EditText username = (EditText) findViewById(R.id.Username);
            EditText password = (EditText) findViewById(R.id.Password);
            String usernameSent = reg.getString("username");
            String passwordSent = reg.getString("password");
            username.setText(usernameSent);
            password.setText(passwordSent);
        }
    }

    public void registerInfo(View view) {
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerIntent);
    }
    public void loginButtonOnClick(View view){

        EditText userNameView = (EditText) findViewById(R.id.Username);
        String username = userNameView.getText().toString();

        EditText passwordView = (EditText) findViewById(R.id.Password);
        String password = passwordView.getText().toString();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(checkIfUserExists(username,password,snapshot)){
                    Intent registeredIntent = new Intent(getApplicationContext(), welcomePage.class);
                    registeredIntent.putExtra("firstName",loginMf.getFirstName());
                    registeredIntent.putExtra("role",loginMf.getRole());
                    startActivity(registeredIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public boolean checkIfUserExists(String username, String password, DataSnapshot dataSnapshot){
        helperClass user = new helperClass();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            user.setUsername(ds.getValue(helperClass.class).getUsername());
            user.setPassword(ds.getValue(helperClass.class).getPassword());
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                Log.d(TAG,"reached here");
                loginMf.setFirstName(ds.getValue(helperClass.class).getFirstName());
                loginMf.setRole(ds.getValue(helperClass.class).getRole());
                return true;
            }
        }
        return false;
    }
    public boolean checkIfUsername(String username, DataSnapshot dataSnapshot){
        helperClass user = new helperClass();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            user.setUsername(ds.getValue(helperClass.class).getUsername());
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

}