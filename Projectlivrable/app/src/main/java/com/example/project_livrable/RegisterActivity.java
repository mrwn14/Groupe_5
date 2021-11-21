package com.example.project_livrable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.*;


import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    boolean existant = false;
    public static final String TAG = "hamid <3";

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void registerdInfo(View view) {
        existant = false;
        Intent registeredIntent = new Intent(getApplicationContext(), MainActivity.class);

        EditText firstNameView = (EditText) findViewById(R.id.FirstName);
        String firstName = firstNameView.getText().toString();
        EditText lastNameView = (EditText) findViewById(R.id.LastName);
        String lastName = lastNameView.getText().toString();
        EditText userNameView = (EditText) findViewById(R.id.Username2);
        String username = userNameView.getText().toString();
        EditText emailView = (EditText) findViewById(R.id.Email);
        String email = emailView.getText().toString();
        EditText passwordView = (EditText) findViewById(R.id.Password2);
        String password = passwordView.getText().toString();
        Spinner roleView = (Spinner) findViewById(R.id.spinner2);
        String role = roleView.getSelectedItem().toString();
        boolean fromRegistered;
            fromRegistered = true;
            rootNode = FirebaseDatabase.getInstance();

            reference = rootNode.getReference("Client");
            reference2 = rootNode.getReference("Employé(e)");
            HelperClass help = new HelperClass(firstName,lastName,username,email,password,role);
            MainActivity hamid = new MainActivity();
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!isValidEmailAddress(email)) {
                        showInvalidDialog("Email", "l'email doit être valide.");
                    }
                    if (!isPasswordValid(password)) {
                        showInvalidDialog("Mot de passe", "le mot de passe doit comporter plus de 5 caractères");
                    }
                    if (!isNameValid(firstName)) {
                        showInvalidDialog("Prénom", "le nom ne doit comporter que des lettres");
                    }
                    if (!isNameValid(lastName)) {
                        showInvalidDialog("Nom", "le nom ne doit comporter que des lettres");
                    }
                    if (!isUsernameValid(username)) {
                        showInvalidDialog("Username", "le nom d'utilisateur doit comprendre uniquement des lettres et des chiffres, et doit comporter plus de 3 caractères.");
                    }
                    if (hamid.checkIfUsername(username,snapshot)) {
                        showInvalidDialog("Username ou mot de passe", "le compte n'existe pas");
                    }
                    if(!hamid.checkIfUsername(username,snapshot) && isValidEmailAddress(email) && isUsernameValid(username) && isNameValid(firstName)&& isNameValid(lastName) && isPasswordValid(password)) {
                        registeredIntent.putExtra("firstName", firstName);
                        registeredIntent.putExtra("lastName", lastName);
                        registeredIntent.putExtra("username", username);
                        registeredIntent.putExtra("email", email);
                        registeredIntent.putExtra("password", password);
                        registeredIntent.putExtra("fromRegistered", fromRegistered);
                        if (role.equals("Employé(e)")) {
                            reference2.child(username).setValue(help);

                            HashMap hamid = new HashMap();
                            hamid.put("Prenom","empty");
                            hamid.put("Nom","empty");
                            hamid.put("Date de naissance","empty");
                            hamid.put("Adresse","empty");

                            HashMap hamid2 = new HashMap();
                            hamid2.put("Preuve de domicile","empty");

                            Hashtable<String,HelperService> hamid4 = new Hashtable<String,HelperService>();
                            HelperService help2 = new HelperService("Permis",username,hamid,hamid2);
                            HelperService help3 = new HelperService("CarteSante",username,hamid,hamid2);
                            HelperService help4 = new HelperService("Identite",username,hamid,hamid2);
                            hamid4.put("Permis", help2);
                            hamid4.put("CarteSante", help3);
                            hamid4.put("Identite", help4);

                            rootNode.getReference("Services").child(username+"_services").setValue(hamid4);
                        }
                        if (role.equals("Client")) {
                            reference.child(username).setValue(help);
                        }

                        startActivity(registeredIntent);

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinText = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public static boolean isValidEmailAddress(String email) {
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean isUsernameValid(String username){
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
        if (username == null) {
            return false;
        }
        Matcher m = pattern.matcher(username);
        return (m.matches() && username.length() >=3);
    }
    public static boolean isNameValid(String username){
        Pattern pattern = Pattern.compile("[A-Za-z]+");
        if (username == null || username.equals("")) {
            return false;
        }
        Matcher m = pattern.matcher(username);
        return m.matches();
    }
    public static boolean isPasswordValid(String password){
        return password.length()>5;
    }

    public void showInvalidDialog(String champ, String adv) {
        AlertDialog.Builder bb = new AlertDialog.Builder(RegisterActivity.this);
        bb.setMessage(champ + " Invalide, " + adv);
        AlertDialog alert = bb.create();
        alert.show();
    }
}