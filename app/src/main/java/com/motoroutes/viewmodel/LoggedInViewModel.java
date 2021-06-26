package com.motoroutes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.motoroutes.model.AppRepository;
import com.motoroutes.model.MyLocation;
import com.motoroutes.model.Route;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LoggedInViewModel extends AndroidViewModel {
    private AppRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;
    private MutableLiveData<Route> routeMutableLiveData;


    public LoggedInViewModel(@NonNull @NotNull Application application) {
        super(application);
        appRepository = appRepository = AppRepository.getInstance(application);
        //appRepository = new AppRepository(application);
        userMutableLiveData = appRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = appRepository.getLoggedOutMutableLiveData();
        routeMutableLiveData = appRepository.getRouteMutableLiveData();

    }

    public void logOut() {
        appRepository.logout();
    }

    public void addRoute(Route route) {
        appRepository.addRoute(route);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

    public MutableLiveData<Route> getRouteMutableLiveData() {
        return routeMutableLiveData;
    }

    public MutableLiveData<String> getToolBarItemStateMutableLiveData() {
        return appRepository.getToolBarItemStateMutableLiveData();
    }

    public ArrayList<Route> readRoutesFromDB() {
        return appRepository.getRoutes();
    }

    public void setToolBarItemState(String itemState) {
        appRepository.setToolBarItemState(itemState);
    }

    public ArrayList<MyLocation> getListPointsArray() {
        return appRepository.getListPointsArray();
    }
}
