package com.example.store.Ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.store.Model.User;
import com.example.store.R;
import com.example.store.Ui.CartFragment;
import com.example.store.Ui.HomePageFragment;
import com.example.store.Utility.UserPref;
import com.example.store.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
     //  UserPref userPref =new UserPref(getApplicationContext());
      // userPref.DeletePrefs();
        Intent intent=getIntent();
         if(intent!=null)
           if(intent.getStringExtra("Activity")!=null ){

               if(intent.getStringExtra("Activity").equals("ConfirmOrder"))
               {
                   loadFragment(new OrdersFragment());
               }
               else{
                   loadFragment(new CartFragment());
               }
               }
       else {
           loadFragment(new HomePageFragment());
       }

        binding.navView.setOnNavigationItemSelectedListener(this);
    }


    private void loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_main, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_Home:
                fragment = new HomePageFragment();
                break;
                
            case R.id.navigation_Orders:
                fragment = new OrdersFragment();
                break;

            case R.id.navigation_Cart:
                fragment = new CartFragment();
                break;
            case R.id.navigation_Settings:
                fragment = new SettingsFragment();
                break;
        }
        loadFragment(fragment);

        return true;
    }

}