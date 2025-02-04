package com.jivan.travelapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textUsernameLayout;
    private TextInputLayout textPasswordInput;
    private Button loginButton;
    private ProgressBar progressBar;
    private BlogReferences preferences;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new BlogReferences(this);
        if(preferences.isLoggedIn()){
            startMainActivity();
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        textUsernameLayout = findViewById(R.id.textUsernameLayout);
        textPasswordInput = findViewById(R.id.textPasswordInput);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        loginButton.setOnClickListener(v -> onLoginClicked());

        textUsernameLayout.getEditText().addTextChangedListener(createTextWatcher(textUsernameLayout));
        textPasswordInput.getEditText().addTextChangedListener((createTextWatcher(textPasswordInput)));
    }

    private void onLoginClicked() {
        String username = textUsernameLayout.getEditText().getText().toString();
        String password = textPasswordInput.getEditText().getText().toString();

        if (username.isEmpty()) {
            textUsernameLayout.setError("Username cannot be empty");
        } else if (password.isEmpty()) {
            textPasswordInput.setError("Password cannot be empty");
        }else if(!username.equals("admin") || !password.equals("admin")){
            showErrorDialog();
        }else{
            performLogin();
        }
    }
    private TextWatcher createTextWatcher(TextInputLayout textInput) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
    private void showErrorDialog() {
        new AlertDialog.Builder(this).setTitle("Login Failed").setMessage("Incorrect username or password")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
    private void performLogin(){
        preferences.setLoggedIn(true);

        textUsernameLayout.setEnabled(false);
        textPasswordInput.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(()->{
            startMainActivity();
            finish();
        }, 2000);
    }
    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


