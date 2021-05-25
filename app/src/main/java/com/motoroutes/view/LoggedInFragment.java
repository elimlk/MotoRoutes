package com.motoroutes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.motoroutes.R;
import com.motoroutes.viewmodel.LoggedInViewModel;

import org.jetbrains.annotations.NotNull;

public class LoggedInFragment extends Fragment {

    private TextView loggedInUserTextView;
    private Button logOutButton;

    private LoggedInViewModel loggedInViewModel;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loggedInViewModel =  new ViewModelProvider(this).get(LoggedInViewModel.class);
        loggedInViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    loggedInUserTextView.setText("Logged In User: "+ firebaseUser.getEmail());
                }
            }
        });

        loggedInViewModel.getLoggedOutMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if(loggedOut){
                    Navigation.findNavController(getView()).navigate(R.id.action_loggedInFragmemt_to_loginFragment);
                }
            }
        });

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_logged_in, container,false);
        loggedInUserTextView = view.findViewById(R.id.fragment_loggedin_loggedInUser);
        logOutButton = view.findViewById(R.id.fragment_loggedin_logOut);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedInViewModel.logOut();
            }
        });

        MainActivity.changeToolbarVisibility(true);
        return view;
    }
}
