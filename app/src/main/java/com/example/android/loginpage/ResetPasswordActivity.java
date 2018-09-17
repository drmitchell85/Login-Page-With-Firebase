package com.example.android.loginpage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private ImageView sendEmail;
    private EditText editTextEmail;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        firebaseAuth = FirebaseAuth.getInstance();
        sendEmail = findViewById(R.id.imageView_sendemail);
        editTextEmail = findViewById(R.id.resetactivity_edittext_email);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter in email address", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),
                                                "Email sent", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    } else {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(getApplicationContext(),
                                                "Error " + message + " occurred, Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
