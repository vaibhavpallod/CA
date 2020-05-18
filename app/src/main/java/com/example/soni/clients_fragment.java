package com.example.soni;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class clients_fragment extends Fragment {

    public RecyclerView recyclerView;
    //  JudgecustomListAdapter judgecustomListAdapter;
    List<Client_return> clientlist;
    clients_list_adapter myadapter;
    Firebase mref1,mref2;
    DatabaseReference database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clients,container,false);
        recyclerView = view.findViewById(R.id.recyclerviewclients);
        //recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        clientlist = new ArrayList<>();



        myadapter = new clients_list_adapter(view.getContext(),clientlist);
        database = FirebaseDatabase.getInstance().getReference("clients");
        mref1 = new Firebase("https://soni-database.firebaseio.com/");


        mref1.child("clients/").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            //Toast.makeText(getContext(),dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
                String name = dataSnapshot.getKey();
               // toast(name);
                clientlist.add(new Client_return(name));
                myadapter.notifyDataSetChanged();
                recyclerView.setAdapter(myadapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        return view;
    }


    public void toast(String x) {
        Toast.makeText(getActivity(), x, Toast.LENGTH_SHORT).show();
    }

}


/*

public class clients_fragment extends Fragment {
    private FragmentActivity myContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.clients_details,container,false);

        BottomNavigationView bottomNavigationView =  view.findViewById(R.id.bottomnavigationid);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);


getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_bottomnavigation,new Bottom_Profile_Fragment()).commit();
        return view;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedfragment = null;

            switch (item.getItemId()){
                case R.id.profileid:
                    selectedfragment = new Bottom_Profile_Fragment();
                    break;

                case R.id.documentid:
                    selectedfragment = new Bottom_Document_Fragment();
                    break;

            }

getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_bottomnavigation,selectedfragment).commit();

         return true;
        }
    };
}
*/
