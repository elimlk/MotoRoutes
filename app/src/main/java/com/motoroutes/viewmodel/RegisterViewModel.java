package com.motoroutes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.motoroutes.model.AppRepository;
import org.jetbrains.annotations.NotNull;

public class RegisterViewModel extends AndroidViewModel {

    private AppRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

    public RegisterViewModel(@NonNull @NotNull Application application) {
        super(application);
        appRepository = AppRepository.getInstance(application);
        //appRepository = new AppRepository(application);
        userMutableLiveData = appRepository.getUserMutableLiveData();
    }

    public void register(String email, String password, String fullName, String phone){
         appRepository.register(email,password,fullName,phone);

    }
    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
