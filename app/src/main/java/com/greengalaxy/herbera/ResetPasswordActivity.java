package com.greengalaxy.herbera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.greengalaxy.herbera.network.HerberaAPI;
import com.greengalaxy.herbera.network.ResetPasswordResult;
import com.greengalaxy.herbera.network.RetrofitBuilder;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText m_txtEmail;
    EditText m_txtOTP;
    EditText m_txtPassword;
    ImageButton m_btnReset;
    SmoothProgressBar m_Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initialize();
    }

    private void initialize() {
        m_txtEmail = (EditText) findViewById(R.id.txt_email);
        m_txtOTP = (EditText) findViewById(R.id.txt_otp);
        m_txtPassword = (EditText) findViewById(R.id.txt_password);
        m_btnReset = (ImageButton) findViewById(R.id.btn_reset);
        m_btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        m_Progress = (SmoothProgressBar) findViewById(R.id.resetProgress);
        m_Progress.setVisibility(View.GONE);
    }

    private void reset() {
        String strEmail = m_txtEmail.getText().toString();
        String strOTP = m_txtOTP.getText().toString();
        String strPassword = m_txtPassword.getText().toString();
        if (strEmail.isEmpty() || strPassword.isEmpty() || strOTP.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        m_Progress.setVisibility(View.VISIBLE);
        RetrofitBuilder.createRetrofitService(HerberaAPI.class).forgotPassword(strEmail, strOTP, strPassword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResetPasswordResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        m_Progress.setVisibility(View.GONE);
                        showResetStatus("Reset Password Error");
                    }

                    @Override
                    public void onNext(ResetPasswordResult resetResult) {
                        m_Progress.setVisibility(View.GONE);
                        showResetStatus(resetResult.getMessage());
                    }
                });
    }

    private void showResetStatus(String statusMessage) {
        Toast.makeText(this, statusMessage, Toast.LENGTH_SHORT).show();
    }
}
