package com.motoroutes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.motoroutes.model.AppRepository;

import org.jetbrains.annotations.NotNull;

public class MainActivityViewModel extends AndroidViewModel {
    private AppRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;


    public MainActivityViewModel(@NonNull @NotNull Application application) {
        super(application);
        appRepository = AppRepository.getInstance(application);
        userMutableLiveData = appRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = appRepository.getLoggedOutMutableLiveData();
    }

    public void logOut(){
        appRepository.logout();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return appRepository.getUserMutableLiveData();
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

    public void setToolBarItemState(String itemState) {
        appRepository.setToolBarItemState(itemState);
    }
    public MutableLiveData<String> getToolBarItemStateMutableLiveData() {
        return appRepository.getToolBarItemStateMutableLiveData();
    }
    public void getRoutesFromDB() {
        appRepository.updateRoutesFromDB();
    }
}
