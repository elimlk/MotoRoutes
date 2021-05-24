package com.motoroutes.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.motoroutes.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId){
                    case R.id.item_routes:
                        Toast.makeText(MainActivity.this,"routes",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_addRoute:
                        Toast.makeText(MainActivity.this,"addRoutes",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_emergency:
                        Toast.makeText(MainActivity.this,"emergency",Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    public static void changeToolbarVisibility(boolean visible){
        if (visible)
            toolbar.setVisibility(View.VISIBLE);
        else
            toolbar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Toast.makeText(this,"Menu open",Toast.LENGTH_SHORT).show();
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

}