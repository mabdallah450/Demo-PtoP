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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;
    private EditText emailEt;
    private EditText passwordEt;
    private TextView signupTv;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, RoomListActivity.class));
        }

        loginBtn = (Button) findViewById(R.id.loginBtn);
        emailEt = (EditText) findViewById(R.id.emailEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);
        signupTv = (TextView) findViewById(R.id.signupTv);

        progressDialog = new ProgressDialog(this);

        passwordEt.setTypeface(Typeface.DEFAULT);

        loginBtn.setOnClickListener(this);
        signupTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view  ==loginBtn){
            loginUser();
        }
        if (view == signupTv){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void loginUser() {
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
        progressDialog.setMessage("Login User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.cancel();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), RoomListActivity.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Could not login... Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
