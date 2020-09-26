package com.example.store.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.store.R;
import com.example.store.Utility.CheckInternetConnection;
import com.example.store.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        Views();
    }

    private void Views() {

        mAuth = FirebaseAuth.getInstance();

        binding.signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

            }
        });
        binding.SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show prograss bar
                if (binding.Emailedt.getText().toString().isEmpty()) {
                    binding.Emaillayout.setError("Enter Email");
                    //hide progress bar
                    i++;
                }
                if (binding.passwordEdt.getText().toString().isEmpty()) {
                    binding.passwordEdt.setError("Enter Password");
                    //hide progress bar
                    i++;
                } else if (i == 0) {
                    if (CheckInternetConnection.isOnline()) {
                        binding.progressCircular.setVisibility(View.VISIBLE);
                        signin(binding.Emailedt.getText().toString(),
                                binding.passwordEdt.getText().toString());
                    } else {
                        Toast.makeText(LoginActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void signin(String email, String pass) {

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    //the user now is signed in
                    //get the user information
                    //update the ui with the information
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            binding.progressCircular.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }
}