package com.example.e_wallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;

public class SignUp extends AppCompatActivity {
    private Button SignUp;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-wallet-7789b-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText Nama = findViewById(R.id.nama);
        final EditText Email = findViewById(R.id.email);
        final EditText Password = findViewById(R.id.password);
        final EditText NoHP = findViewById(R.id.noHP);




        SignUp = (Button) findViewById(R.id.confirm_button1);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String namaTxt = Nama.getText().toString();
                final String EmailTxt = Email.getText().toString();
                final String PasswordTxt = Password.getText().toString();
                final String NoHPTxt = NoHP.getText().toString();


                if(namaTxt.isEmpty()) {
                    Nama.setError("Enter Your Name");
                }if(EmailTxt.isEmpty()){
                    Email.setError("Enter Your Email");
                }if(PasswordTxt.isEmpty()) {
                    Password.setError("Enter Your Password");
                }if(!PasswordTxt.isEmpty()) {
                    if(PasswordTxt.length()<8){
                        Password.setError("Password too short");
                    }
                }if (NoHPTxt.isEmpty()){
                    NoHP.setError("Enter Your Phone Number");
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(NoHPTxt)){
                                Toast.makeText(SignUp.this, "No hp sudah terdaftar", Toast.LENGTH_SHORT).show();
                            }else{
                                databaseReference.child("users").child(NoHPTxt).child("nama").setValue(namaTxt);
                                databaseReference.child("users").child(NoHPTxt).child("email").setValue(EmailTxt);
                                databaseReference.child("users").child(NoHPTxt).child("password").setValue(PasswordTxt);
                                databaseReference.child("users").child(NoHPTxt).child("phoneNumber").setValue(NoHPTxt);
                                databaseReference.child("users").child(NoHPTxt).child("saldo").setValue(0);
//                                databaseReference.child("users").child(NoHPTxt).child("uangmasuk").setValue(0);

                                Toast.makeText(SignUp.this, "Berhasil melakukan registrasi", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    public void openSignUp(){
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }
}