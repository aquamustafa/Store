package com.example.store.Ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.store.R;
import com.example.store.databinding.FragmentCartBinding;
import com.example.store.databinding.FragmentSettings2Binding;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
  * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    FragmentSettings2Binding binding;
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettings2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
      }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog(1);
            }
        });

        binding.HelpSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog(2);
            }
        });
        binding.SignOutSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOUT();
            }
        });
    }

    private void SignOUT() {
        binding.progress.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this.getActivity(), LoginActivity.class));
        getActivity().finish();


    }

    private void ShowDialog(int i) {
        Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.dialog_layout);
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

}