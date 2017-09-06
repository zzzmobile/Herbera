package com.greengalaxy.herbera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.greengalaxy.herbera.network.HerberaAPI;
import com.greengalaxy.herbera.network.LoginResult;
import com.greengalaxy.herbera.network.RetrofitBuilder;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText m_txtEmail;
    EditText m_txtPassword;
    TextView m_btnForgotPassword;
    ImageButton m_btnLogin;
    TextView m_btnGotoRegister;
    SmoothProgressBar m_Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
    }

    private void initialize() {
        m_txtEmail = (EditText) findViewById(R.id.txt_email);
        m_txtPassword = (EditText) findViewById(R.id.txt_password);
        m_btnForgotPassword = (TextView) findViewById(R.id.btn_forgot);
        m_btnForgotPassword.setOnClickListener(this);
        m_btnLogin = (ImageButton) findViewById(R.id.btn_login);
        m_btnLogin.setOnClickListener(this);
        m_btnGotoRegister = (TextView) findViewById(R.id.btn_register);
        m_btnGotoRegister.setOnClickListener(this);
        m_Progress = (SmoothProgressBar) findViewById(R.id.loginProgress);
        m_Progress.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_forgot:
                forgotPassword();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                gotoRegister();
                break;
        }
    }

    private void forgotPassword()
    {
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    private void login()
    {
        String strEmail = m_txtEmail.getText().toString();
        String strPassword = m_txtPassword.getText().toString();
        if (strEmail.isEmpty() || strPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        m_Progress.setVisibility(View.VISIBLE);
        RetrofitBuilder.createRetrofitService(HerberaAPI.class).login(strEmail, strPassword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        m_Progress.setVisibility(View.GONE);
                        showLoginStatus("Login Error");
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        m_Progress.setVisibility(View.GONE);
                        showLoginStatus(loginResult.getMessage());
                    }
                });
    }

    private void gotoRegister()
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void showLoginStatus(String statusMessage) {
        Toast.makeText(this, statusMessage, Toast.LENGTH_SHORT).show();
    }
}
