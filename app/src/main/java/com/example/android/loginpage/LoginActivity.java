package com.example.android.loginpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ImageView continueImage;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView resetPassword;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        continueImage = findViewById(R.id.login_continue);
        editTextEmail = findViewById(R.id.edit_text_login_email);
        editTextPassword = findViewById(R.id.edit_text_login_password);
        resetPassword = findViewById(R.id.login_forgot);

        if (firebaseAuth.getCurrentUser() != null) {
            // profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        continueImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
            }
        });

    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Check if fields are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(
                    this, "Please fill out email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(
                    this, "Please fill out password", Toast.LENGTH_SHORT).show();
            return;
        }

        // if validations are OK we first show a progress dialog
        progressDialog.setMessage("Logging in");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // user is registered successfully
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Login successful",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Could not login user, please try again",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
