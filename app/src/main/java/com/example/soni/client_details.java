package com.example.soni;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class client_details extends Fragment {
    private FragmentActivity myContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clients_details, container, false);

            BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomnavigationid);
            bottomNavigationView.setOnNavigationItemSelectedListener(navListner);


            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_bottomnavigation, new Bottom_Profile_Fragment()).commit();


        return view;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedfragment = null;

            switch (item.getItemId()) {
                case R.id.profileid:
                    selectedfragment = new Bottom_Profile_Fragment();
                    break;

                case R.id.documentid:
                    selectedfragment = new Bottom_Document_Fragment();
                    break;

            }

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_bottomnavigation, selectedfragment).commit();

            return true;
        }
    };
}




