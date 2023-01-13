package com.example.e_wallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IsiSaldo extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-wallet-7789b-default-rtdb.firebaseio.com/");

    private Button tambah;
    private EditText isisaldo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_saldo);

        final EditText saldoval = findViewById(R.id.isisaldo);
        final TextView infoSaldo = findViewById(R.id.saldo);

        String NoHPTxt = getIntent().getStringExtra("phonenumber");



        tambah = (Button) findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final String saldotxt = saldoval.getText().toString();
                        final int saldoint = Integer.parseInt(saldotxt);

                        int saldouser = snapshot.child(NoHPTxt).child("saldo").getValue(Integer.class);

                        databaseReference.child("users").child(NoHPTxt).child("saldo").setValue((saldouser+saldoint));

                        Toast.makeText(IsiSaldo.this, "Saldo berhasil ditambahkan", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(IsiSaldo.this, Login.class);
                        intent.putExtra("phonenumber", NoHPTxt);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int infoSaldoTxt = snapshot.child(NoHPTxt).child("saldo").getValue(Integer.class);
                infoSaldo.setText("Rp. " +infoSaldoTxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}