package com.example.store.Ui;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.store.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplachScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splach_screen);
        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (!(user == null)) {
                    Intent intent=new Intent(SplachScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                else{
                    Intent intent=new Intent(SplachScreen.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, secondsDelayed * 1000);





    }
}
