package com.example.expensemanager.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button button_registration;
    private TextView sign_in;

    private ProgressDialog dialog;

    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fbAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);

        email = findViewById(R.id.registration_email);
        password = findViewById(R.id.registration_password);
        button_registration = findViewById(R.id.registration_button);
        sign_in = findViewById(R.id.sign_in_here);

        button_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eMail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(eMail)){
                    email.setError("Email required");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    password.setError("Password required");
                }
                dialog.setMessage("Processing..");
                dialog.show();

                fbAuth.createUserWithEmailAndPassword(eMail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registration complete", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }


}
