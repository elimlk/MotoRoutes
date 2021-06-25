package com.motoroutes.model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.motoroutes.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AppRepository {

    private static AppRepository appRepository =null;
    private Application application;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;
    private MutableLiveData<Route> routeMutableLiveData;
    private MutableLiveData<String> toolBarItemStateMutableLiveData;
    public static ArrayList<MyLocation> listPointsArray= new ArrayList<>();

    private ArrayList<Route> routesList;


    private AppRepository(Application application) {
        this.application = application;
        routesList = new ArrayList<Route>();
        firebaseAuth = FirebaseAuth.getInstance();
        userMutableLiveData = new MutableLiveData<FirebaseUser>();
        loggedOutMutableLiveData = new MutableLiveData<>();
        routeMutableLiveData = new MutableLiveData<Route>();
        toolBarItemStateMutableLiveData = new MutableLiveData<>();
        if(firebaseAuth.getCurrentUser() != null){
            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutMutableLiveData.postValue(false);
        }

    }

    public static AppRepository getInstance(Application application) {
        if (appRepository == null){
            appRepository = new AppRepository(application);
            appRepository.updateRoutesFromDB();
            return appRepository;
        }
        else
            return appRepository;

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
        if (email == "geust" && password == "geust") {
            loggedOutMutableLiveData.postValue(false);
            toolBarItemStateMutableLiveData.postValue(String.valueOf(R.id.map));
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                loggedOutMutableLiveData.postValue(false);
                                toolBarItemStateMutableLiveData.postValue(String.valueOf(R.id.map));
                                userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                            }
                            else{
                                Toast.makeText(application,"Login Failed: " +task.getException()
                                        .getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    public void logout(){
        firebaseAuth.signOut();
        userMutableLiveData.setValue(null);
        loggedOutMutableLiveData.postValue(true);
    }

    public void addRoute(Route route){
        FirebaseDatabase.getInstance().getReference("Routes").
                child(route.getName()).
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

    public ArrayList<Route> getRoutes(){
        updateRoutesFromDB();
        return routesList;
    }

    public void updateRoutesFromDB(){
        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Routes");
        df.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    //Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    if (routesList != null)
                        routesList.clear();
                    for (DataSnapshot child : task.getResult().getChildren()) {
                        Route route = child.getValue(Route.class);
                        routesList.add(route);
                    }
                }
            }
        });
    }

    public void setToolBarItemState(String ItemStateID){
        toolBarItemStateMutableLiveData.setValue(ItemStateID);
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


    public MutableLiveData<String> getToolBarItemStateMutableLiveData() { return toolBarItemStateMutableLiveData; }

}
