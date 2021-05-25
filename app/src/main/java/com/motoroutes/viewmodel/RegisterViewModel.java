package com.motoroutes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.motoroutes.model.AppRepository;
import org.jetbrains.annotations.NotNull;

public class RegisterViewModel extends AndroidViewModel {
    private AppRepository appRepository;

    public RegisterViewModel(@NonNull @NotNull Application application, AppRepository appRepository) {
        super(application);
        this.appRepository = appRepository;
    }

    public void register(String email, String password,String fullName,String phone){
        appRepository.register(email,password,fullName,phone);
    }
}
