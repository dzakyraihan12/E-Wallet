package com.example.e_wallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Akun extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-wallet-7789b-default-rtdb.firebaseio.com/");
    private ImageButton Home;
    private Button Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        final TextView infoNama = findViewById(R.id.nama);
        final TextView infoNo = findViewById(R.id.nohp);
        final TextView infosaldo = findViewById(R.id.infosaldo);
        final TextView infoEmail = findViewById(R.id.email);
        final TextView infoNamaBaru = findViewById(R.id.namabaru);


        String phoneTxt = getIntent().getStringExtra("phonenumber");

        Home = (ImageButton) findViewById(R.id.confirm_button8);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHome();
            }
        });
        Logout = (Button)findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Akun.this, MainActivity.class);
                startActivity(intent);
            }
        });


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String NamaTxt = snapshot.child(phoneTxt).child("nama").getValue(String.class);
                infoNama.setText(NamaTxt);
                String Nohptxt = snapshot.child(phoneTxt).child("phoneNumber").getValue(String.class);
                infoNo.setText(Nohptxt);
                int infoSaldoTxt = snapshot.child(phoneTxt).child("saldo").getValue(Integer.class);
                infosaldo.setText("Rp. " +infoSaldoTxt);
                String NamaBaruTxt = snapshot.child(phoneTxt).child("nama").getValue(String.class);
                infoNamaBaru.setText(NamaBaruTxt);
                String emailTxt = snapshot.child(phoneTxt).child("email").getValue(String.class);
                infoEmail.setText(emailTxt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void openHome(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}