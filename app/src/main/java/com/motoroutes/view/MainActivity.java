package com.motoroutes.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.motoroutes.R;
import com.motoroutes.model.User;
import com.motoroutes.viewmodel.LoggedInViewModel;
import com.motoroutes.viewmodel.MainActivityViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private static NavigationView navigationView;
    private static Toolbar toolbar;
    private static NavigationView drawerNavigationView;
    private MainActivityViewModel mainActivityViewModel;
    private TextView tv_userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.header_layout);
        tv_userName = findViewById(R.id.tv_userHeader);
        setContentView(R.layout.activity_main);
        mainActivityViewModel =  new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    String userID = firebaseUser.getUid();
                    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");
                    firebaseDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            User userProfile = snapshot.getValue(User.class);
                            //v_userName.setText("Hello "+userProfile.fullname);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });;

                }
            }
        });
        mainActivityViewModel.getLoggedOutMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if(loggedOut){
                    Navigation.findNavController(MainActivity.this,R.id.activity_main_navHostFragment).navigate(R.id.action_loggedInFragmemt_to_loginFragment);
                }
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);

        setNavigationViewListener();


    }

    public static void changeToolbarVisibility(boolean visible){
        if (visible)
            toolbar.setVisibility(View.VISIBLE);
        else
            toolbar.setVisibility(View.GONE);
    }

    public static void loadGuestMenu(boolean isGuest){
        if (isGuest){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.guest_drawer_menu);
        }else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_menu);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Toast.makeText(this,"Menu open",Toast.LENGTH_SHORT).show();
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
/*        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }*/
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.item_routes:
                item.setChecked(true);
                Toast.makeText(this,"routes",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_addRoute:
                item.setChecked(true);
                mainActivityViewModel.setToolBarItemState(String.valueOf(R.id.item_addRoute));
                Toast.makeText(this,"addRoutes",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_emergency:
                item.setChecked(true);
                Toast.makeText(this,"emergency",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_logout:
                item.setChecked(true);
                mainActivityViewModel.logOut();
                Toast.makeText(this,"logout",Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}