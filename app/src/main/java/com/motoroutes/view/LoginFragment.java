package com.motoroutes.view;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialDialogs;
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
    private ProgressBar progressBar;
    private CardView cardViewLogin;

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                progressBar.setVisibility(View.GONE);
                cardViewLogin.setVisibility(View.VISIBLE);
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
        tvGuest = view.findViewById(R.id.guest_login);
        progressBar = view.findViewById(R.id.loginPrograssBar);
        cardViewLogin = view.findViewById(R.id.card_login);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView())
                        .navigate(R.id.action_loginFragment_to_registerFragment);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                cardViewLogin.setVisibility(View.INVISIBLE);
                MainActivity.loadGuestMenu(false);
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {
                    loginViewModel.login(email, password);
                }


                /*new AsyncTask<Object, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                    }

                    @Override
                    protected void onPostExecute(Void unused) {
                        super.onPostExecute(unused);
                        MainActivity.loadGuestMenu(false);
                        String email = etEmail.getText().toString().trim();
                        String password = etPassword.getText().toString();
                        if (!email.isEmpty() && !password.isEmpty()) {
                            loginViewModel.login(email, password);
                        }
                    }
                    @Override
                    protected Void doInBackground(Object... objects) {
                        return null;
                    }
                }.execute();*/

            }
        });

        tvGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.loadGuestMenu(true);
                loginViewModel.login("geust","geust");
                Navigation.findNavController(getView())
                        .navigate(R.id.action_loginFragment_to_loggedInFragmemt);

            }
        });

        MainActivity.changeToolbarVisibility(false);

        return view;
    }

}
