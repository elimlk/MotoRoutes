package com.motoroutes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.motoroutes.model.AppRepository;
import com.motoroutes.model.Route;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoutesListViewModel extends AndroidViewModel {

    private AppRepository appRepository;


    public RoutesListViewModel(@NonNull @NotNull Application application) {
        super(application);

        appRepository = AppRepository.getInstance(application);
    }

    public LiveData<String> getToolBarItemStateMutableLiveData() {
        return appRepository.getToolBarItemStateMutableLiveData();
    }

    public ArrayList<Route> getRoutes() {
        return (appRepository.getRoutes());
    }


    public void logOut() {
        appRepository.logout();
    }
}
