package com.example.axel.ptop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.registerBtn)
    Button registerBtn;
    @BindView(R.id.emailEt)
    EditText emailEt;
    @BindView(R.id.passwordEt)
    EditText passwordEt;
    @BindView(R.id.confirmPasswordEt)
    EditText confirmPasswordEt;
    @BindView(R.id.signinTv)
    TextView signinTv;


    private String email, password, confirmPassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, RoomListActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        passwordEt.setTypeface(Typeface.DEFAULT);
        confirmPasswordEt.setTypeface(Typeface.DEFAULT);

        registerBtn.setOnClickListener(this);
        signinTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == registerBtn) {
            registerUser();
        }
        if (view == signinTv) {
            //sign in activity
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void registerUser() {
        email = emailEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        confirmPassword = confirmPasswordEt.getText().toString().trim();

        if (!validateInputs()) return;

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.cancel();
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), RoomListActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Could not register... Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    boolean validateInputs() {
        boolean valid = true;
        if (email.isEmpty()) {
            emailEt.setError("Please enter email");
            valid = false;
        }
        if (password.isEmpty()) {
            passwordEt.setError("Please enter password");
            valid = false;
        }
        if (password.length() < 6 && !password.isEmpty()) {
            passwordEt.setError("Password is too short");
            valid = false;
        }
        if (confirmPassword.isEmpty()) {
            confirmPasswordEt.setError("Please enter password");
            valid = false;
        }
        if (valid && !confirmPassword.equals(password)) {
            confirmPasswordEt.setError("Passwords do not match");
            valid = false;
        }
        return valid;
    }

}

