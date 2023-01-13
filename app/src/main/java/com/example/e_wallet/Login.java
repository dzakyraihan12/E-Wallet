package com.example.e_wallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private ImageButton Isi;
    private ImageButton Transfer;
    private ImageButton Akun;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-wallet-7789b-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView infoNama = findViewById(R.id.nama);
        final TextView infoSaldo = findViewById(R.id.saldo);

        String phoneTxt = getIntent().getStringExtra("phonenumber");

        Akun = (ImageButton)findViewById(R.id.akun);
        Akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Akun.class);
                intent.putExtra("phonenumber", phoneTxt);
                startActivity(intent);
            }
        });

        Transfer = (ImageButton)findViewById(R.id.transfer);
        Transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, Transfer.class);
                intent.putExtra("phonenumber", phoneTxt);
                startActivity(intent);
            }
        });



        Isi = (ImageButton) findViewById((R.id.confirm_button2));
        Isi.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, IsiSaldo.class);
                intent.putExtra("phonenumber", phoneTxt);
                startActivity(intent);
            }
        }));

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int infoSaldoTxt = snapshot.child(phoneTxt).child("saldo").getValue(Integer.class);
                infoSaldo.setText("Rp. " +infoSaldoTxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String NamaTxt = snapshot.child(phoneTxt).child("nama").getValue(String.class);
                infoNama.setText(NamaTxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        
    }


}