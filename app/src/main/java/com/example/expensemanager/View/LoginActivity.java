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

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button button_login;
    private TextView forget_password;
    private TextView sign_up;

    private ProgressDialog dialog;

    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fbAuth = FirebaseAuth.getInstance();

        if (fbAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        dialog = new ProgressDialog(this);

            email = findViewById(R.id.login_email);
            password = findViewById(R.id.login_password);
            button_login = findViewById(R.id.login_button);
            forget_password = findViewById(R.id.forgot_password);
            sign_up = findViewById(R.id.sign_up_here);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            button_login.setOnClickListener(new View.OnClickListener() {
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
                        return;
                    }

                    dialog.setMessage("Processing..");
                    dialog.show();

                    fbAuth.signInWithEmailAndPassword(eMail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();
                            }else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

            sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                }
            });

            forget_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
                }
            });
        }
    }
