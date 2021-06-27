package com.motoroutes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseUser;
import com.motoroutes.R;
import com.motoroutes.viewmodel.RegisterViewModel;

import org.jetbrains.annotations.NotNull;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    TextView tvSignIn;
    Button btnSignUp;
    EditText etEmail;
    EditText etPassword;
    EditText etFullName;
    EditText etPhone;
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
//                Toast.makeText(getContext(),"Backpressed", Toast.LENGTH_SHORT).show();
                getActivity().moveTaskToBack(true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        callback.setEnabled(true);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Navigation.findNavController(getView())
                            .navigate(R.id.action_registerFragment_to_loginFragment);
                }
            }
        });
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmant_register, container,false);
        tvSignIn = view.findViewById(R.id.tv_register_signIn);
        btnSignUp = view.findViewById(R.id.btn_register);
        etEmail = view.findViewById(R.id.et_register_email);
        etPassword = view.findViewById(R.id.et_register_password);
        etFullName = view.findViewById(R.id.et_register_fullName);
        etPhone = view.findViewById(R.id.et_register_phoneNumber);
        progressBar = view.findViewById(R.id.RagisterPrograssBar);
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString();
                String fullName = etFullName.getText().toString();
                String phone = etPhone.getText().toString();
                registerViewModel.register(email,password,fullName,phone);

            }
        });

        MainActivity.changeToolbarVisibility(false);
        return view;
    }
}
