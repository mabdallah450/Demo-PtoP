package com.example.axel.ptop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.PrivateKey;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerBtn;
    private EditText emailEt;
    private EditText passwordEt;
    private TextView signinTv;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, RoomListActivity.class));
        }

        registerBtn = (Button) findViewById(R.id.registerBtn);
        emailEt = (EditText) findViewById(R.id.emailEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);
        signinTv = (TextView) findViewById(R.id.signinTv);

        progressDialog = new ProgressDialog(this);

        passwordEt.setTypeface(Typeface.DEFAULT);

        registerBtn.setOnClickListener(this);
        signinTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == registerBtn){
            registerUser();
        }
        if (view == signinTv){
            //sign in activity
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void registerUser() {
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            emailEt.setError("Please enter email");
            return;
        }
        if (TextUtils.isEmpty(password)){
            passwordEt.setError("Please enter password");
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.cancel();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), RoomListActivity.class));
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Could not register... Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

