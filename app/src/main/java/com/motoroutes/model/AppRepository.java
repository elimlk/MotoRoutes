package com.motoroutes.model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.motoroutes.R;

import org.jetbrains.annotations.NotNull;

public class AppRepository {

    private Application application;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;
    private MutableLiveData<Route> routeMutableLiveData;


    public AppRepository(Application application) {
        this.application = application;

        firebaseAuth = FirebaseAuth.getInstance();
        userMutableLiveData = new MutableLiveData<FirebaseUser>();
        loggedOutMutableLiveData = new MutableLiveData<>();
        routeMutableLiveData = new MutableLiveData<Route>();

        if(firebaseAuth.getCurrentUser() != null){
            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutMutableLiveData.postValue(false);
        }

    }

    public void register(String email, String password, String fullName, String phone){
        if (email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(application,"fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(email,password,fullName,phone);
                            FirebaseDatabase.getInstance().getReference("Users").
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                    setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(application,application.getString(R.string.Registartion_success), Toast.LENGTH_SHORT).show();
                                    } else {
                                        //display a failure message
                                    }

                                }
                                    });
                            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                        }
                        else {
                            Toast.makeText(application,"Registration Failed: " +task.getException()
                                            .getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void login(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                }
                else{
                    Toast.makeText(application,"Login Failed: " +task.getException()
                            .getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addRoute(Route route){
        FirebaseDatabase.getInstance().getReference("Routes").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                setValue(route).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(application,application.getString(R.string.routeAdded), Toast.LENGTH_SHORT).show();
                } else {
                    //display a failure message
                }

            }
        });

    }

    public void logout(){
        firebaseAuth.signOut();
        loggedOutMutableLiveData.postValue(true);
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Route> getRouteMutableLiveData() {
        return routeMutableLiveData;
    }
}
