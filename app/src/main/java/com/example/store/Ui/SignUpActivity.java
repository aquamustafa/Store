package com.example.store.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.store.Model.User;
import com.example.store.R;
import com.example.store.Utility.CheckInternetConnection;
import com.example.store.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
         Views();
    }


    private void Views() {
        auth=FirebaseAuth.getInstance();
        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.Emailedt.getText().toString().isEmpty()){
                    binding.Emaillayout.setError("Required");
                }
                if(binding.PasswordEdt.getText().toString().isEmpty()){
                    binding.PasswordLaout.setError("Required");
                }
                if(binding.nameEdt.getText().toString().isEmpty()){
                    binding.NameLayout.setError("Required");
                }
                if(!(binding.Emailedt.getText().toString().isEmpty()&&
                binding.PasswordEdt.getText().toString().isEmpty()&&
                binding.nameEdt.getText().toString().isEmpty())){

                    if(CheckInternetConnection.isOnline()){
                        binding.progressCircular.setVisibility(View.VISIBLE);

                        SignUp(binding.Emailedt.getText().toString(),
                                binding.PasswordEdt.getText().toString(),
                                binding.nameEdt.getText().toString());
                    }else {
                        Toast.makeText(SignUpActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();


                    }
                }
            }
        });
    }

    private void SignUp(String email, String pass, String name) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("SignUp","ok");
                        if (task.isSuccessful()) {
                            Log.d("SignUp","okok");

                            // Sign in success, update UI with the signed-in user's information
                            if (auth.getCurrentUser()!=null){
                                Log.d("SignUp","okokok");

                                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                            finish();
                        }
                            else{
                                binding.progressCircular.setVisibility(View.INVISIBLE);
                                Toast.makeText(SignUpActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
    }


}