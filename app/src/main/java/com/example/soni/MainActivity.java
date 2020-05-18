package com.example.soni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private long backPressedTime;

    public Toolbar mtoolbar;
    public DrawerLayout drawer;
    public ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtoolbar = findViewById(R.id.toolb);
        setSupportActionBar(mtoolbar);

        drawer = findViewById(R.id.mainactivity_drawer_layout);
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, mtoolbar, R.string.navigation_darwer_open, R.string.navigation_darwer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null)
        {
            //Toast.makeText(getApplicationContext(),"Saved instance ",Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new clients_fragment()).commit();
            navigationView.setCheckedItem(R.id.clientsidedrawer);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.clientsidedrawer:
            //    Toast.makeText(getApplicationContext(),"Client selected",Toast.LENGTH_SHORT).show();

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new clients_fragment()).commit();
                break;

            case R.id.adddatasidedrawer:
              //  Toast.makeText(getApplicationContext(),"add data selected ",Toast.LENGTH_SHORT).show();

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new addclient_fragment()).commit();
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {{
            finishAffinity();
        }
        return;
    } else


           /* if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            return;*/
         {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

    //    backPressedTime = System.currentTimeMillis();


    }

}