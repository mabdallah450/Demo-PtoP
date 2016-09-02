package com.example.axel.ptop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity{

    @BindView(R.id.loginBtn) Button loginBtn;
    @BindView(R.id.emailEt) EditText emailEt;
    @BindView(R.id.passwordEt) EditText passwordEt;
    @BindView(R.id.signupTv) TextView signupTv;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, RoomListActivity.class));
        }
        progressDialog = new ProgressDialog(this);

        passwordEt.setTypeface(Typeface.DEFAULT);
    }

    @OnClick(R.id.loginBtn) void loginUser() {
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();

        short error = 0;
        if (TextUtils.isEmpty(email)){
            emailEt.setError("Please enter email");
            error++;
        }
        if (TextUtils.isEmpty(password)){
            passwordEt.setError("Please enter password");
            error++;
        }
        if (error > 0) return;
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
    @OnClick(R.id.signupTv) void goToRegisterActivity(){
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
