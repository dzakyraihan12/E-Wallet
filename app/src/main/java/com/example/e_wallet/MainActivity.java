package com.example.e_wallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {
    private Button Login;
    private Button SignUp;
    private Button ForgotPass;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-wallet-7789b-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText phoneNumber =  findViewById(R.id.nohp);
        final EditText Pass = findViewById(R.id.pass);

        Login = (Button) findViewById(R.id.confirm_button1);
        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String phoneTxt = phoneNumber.getText().toString();
                final String PassTxt = Pass.getText().toString();
                //FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                if(phoneTxt.isEmpty()) {
                    phoneNumber.setError("Enter Your PhoneNumber");
                }
                if(PassTxt.isEmpty()) {
                    Pass.setError("Enter Your Password");
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneTxt)){
                                final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);

                                if(getPassword.equals(PassTxt)){

                                    Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MainActivity.this, Login.class);

                                    intent.putExtra("phonenumber", phoneTxt);
                                    startActivity(intent);

                                    finish();
                                }else{
                                    Toast.makeText(MainActivity.this, "No hp atau password salah", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(MainActivity.this, "No hp atau password salah", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        SignUp = (Button) findViewById(R.id.confirm_button3);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSignUp();
            }
        });{

        }
    }

    public void openSignUp(){
        Intent intent2 = new Intent(this, SignUp.class);
        startActivity(intent2);
    }
}