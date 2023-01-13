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

public class Transfer extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-wallet-7789b-default-rtdb.firebaseio.com/");
    private Button kirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        final EditText noTeman = findViewById(R.id.nohp);
        final EditText nominal = findViewById(R.id.nominal);
        final TextView infoSaldo = findViewById(R.id.saldo);

        String NoHPTxt = getIntent().getStringExtra("phonenumber");

        kirim = (Button) findViewById(R.id.kirim);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final String notemantxt = noTeman.getText().toString();
                        final String nominaltxt = nominal.getText().toString();

                        final int saldoInputint = Integer.parseInt(nominaltxt);

                        int saldouser = snapshot.child(NoHPTxt).child("saldo").getValue(Integer.class);


                        if(snapshot.hasChild(notemantxt)){
                            if(saldoInputint > saldouser){
                                Toast.makeText(Transfer.this, "Saldo tidak mencukupi", Toast.LENGTH_SHORT).show();
                            }else {
                                int saldoteman = snapshot.child(notemantxt).child("saldo").getValue(Integer.class);

                                databaseReference.child("users").child(notemantxt).child("saldo").setValue((saldoInputint + saldoteman));

                                databaseReference.child("users").child(NoHPTxt).child("saldo").setValue((saldouser - saldoInputint));
                                Toast.makeText(Transfer.this, "Transfer berhasil", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Transfer.this, Login.class);
                                intent.putExtra("phonenumber", NoHPTxt);
                                startActivity(intent);
                            }
                        }else{
                            Toast.makeText(Transfer.this, "No tidak tersedia", Toast.LENGTH_SHORT).show();
                        }
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