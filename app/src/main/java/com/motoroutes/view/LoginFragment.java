package com.motoroutes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseUser;
import com.motoroutes.R;
import com.motoroutes.viewmodel.LoginViewModel;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private TextView tvForgotPassword;
    private TextView tvGuest;

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Navigation.findNavController(getView())
                            .navigate(R.id.action_loginFragment_to_loggedInFragmemt);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container,false);

        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvRegister = view.findViewById(R.id.tv_register);
        tvForgotPassword = view.findViewById(R.id.tv_forgot_password);
        tvGuest = view.findViewById(R.id.guest_login);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView())
                        .navigate(R.id.action_loginFragment_to_registerFragment);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()) {
                    loginViewModel.login(email, password);
                }
            }
        });

        MainActivity.changeToolbarVisibility(false);

        return view;
    }
}
