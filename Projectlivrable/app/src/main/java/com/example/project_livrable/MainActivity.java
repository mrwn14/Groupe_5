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
    HelperClass loginMf = new HelperClass();
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


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Client");
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Employé(e)");
        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("admin");

        DatabaseReference[] references = {databaseReference,databaseReference2,databaseReference3};

        for (DatabaseReference ref : references) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(checkIfUserExists(username,password,snapshot)){
                        if (loginMf.getRole().equals("admin")) {
                            Intent registeredIntent2 = new Intent(getApplicationContext(), AdminInterface.class);
                            registeredIntent2.putExtra("firstName",loginMf.getFirstName());
                            registeredIntent2.putExtra("role",loginMf.getRole());
                            startActivity(registeredIntent2);
                        }
                        else{
                            Intent registeredIntent = new Intent(getApplicationContext(), WelcomePage.class);
                            registeredIntent.putExtra("firstName",loginMf.getFirstName());
                            registeredIntent.putExtra("role",loginMf.getRole());
                            startActivity(registeredIntent);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
    public boolean checkIfUserExists(String username, String password, DataSnapshot dataSnapshot){
        HelperClass user = new HelperClass();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            user.setUsername(ds.getValue(HelperClass.class).getUsername());
            user.setPassword(ds.getValue(HelperClass.class).getPassword());
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                Log.d(TAG,"reached here");
                loginMf.setFirstName(ds.getValue(HelperClass.class).getFirstName());
                loginMf.setRole(ds.getValue(HelperClass.class).getRole());
                return true;
            }
        }
        return false;
    }
    public boolean checkIfUsername(String username, DataSnapshot dataSnapshot){
        HelperClass user = new HelperClass();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            user.setUsername(ds.getValue(HelperClass.class).getUsername());
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

}