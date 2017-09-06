package com.greengalaxy.herbera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.greengalaxy.herbera.network.HerberaAPI;
import com.greengalaxy.herbera.network.RegisterResult;
import com.greengalaxy.herbera.network.RetrofitBuilder;

import java.util.Calendar;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText m_txtName;
    EditText m_txtEmail;
    EditText m_txtPassword;
    EditText m_txtNumber;
    CheckBox m_chkSeller;
    CheckBox m_chkBuyer;
    ImageButton m_btnRegister;
    TextView m_btnGotoLogin;
    SmoothProgressBar m_Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    private void initialize() {
        m_txtName = (EditText) findViewById(R.id.txt_name);
        m_txtEmail = (EditText) findViewById(R.id.txt_email);
        m_txtPassword = (EditText) findViewById(R.id.txt_password);
        m_txtNumber = (EditText) findViewById(R.id.txt_number);

        m_chkSeller = (CheckBox) findViewById(R.id.chk_seller);
        m_chkBuyer = (CheckBox) findViewById(R.id.chk_buyer);
        m_btnRegister = (ImageButton) findViewById(R.id.btn_register);
        m_btnRegister.setOnClickListener(this);
        m_btnGotoLogin = (TextView) findViewById(R.id.btn_gotoLogin);
        m_btnGotoLogin.setOnClickListener(this);

        m_Progress = (SmoothProgressBar) findViewById(R.id.registerProgress);
        m_Progress.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_gotoLogin:
                onBackPressed();
                break;
        }
    }

    private void register()
    {
        String strName = m_txtName.getText().toString();
        String strPassword = m_txtPassword.getText().toString();
        String strEmail = m_txtEmail.getText().toString();
        String strNumber = m_txtNumber.getText().toString();
        if (strName.isEmpty() || strPassword.isEmpty() || strEmail.isEmpty() || strNumber.isEmpty() ||
                (!m_chkSeller.isChecked() && !m_chkBuyer.isChecked()))
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String strType = "";
        String strCreatedDate = "";

        if (m_chkSeller.isChecked() && m_chkBuyer.isChecked())
            strType = "buyer_seller";
        else if (m_chkSeller.isChecked())
            strType = "seller";
        else
            strType = "buyer";

        Calendar calendar = Calendar.getInstance();
        strCreatedDate = calendar.getTime().toString();

        String strFirstName = "", strLastName = "";
        String[] names = strName.split(" ");
        if (names.length == 1) {
            strFirstName = names[0];
        } else if (names.length == 2) {
            strFirstName = names[0];
            strLastName = names[1];
        }

        m_Progress.setVisibility(View.VISIBLE);
        RetrofitBuilder.createRetrofitService(HerberaAPI.class).register(strFirstName, strLastName, strPassword, strEmail, strCreatedDate, strType, strNumber)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        m_Progress.setVisibility(View.GONE);
                        showRegisterStatus("Register Error");
                    }

                    @Override
                    public void onNext(RegisterResult registerResult) {
                        m_Progress.setVisibility(View.GONE);
                        showRegisterStatus(registerResult.getMessage());
                    }
                });
    }

    private void showRegisterStatus(String statusMessage) {
        Toast.makeText(this, statusMessage, Toast.LENGTH_SHORT).show();
    }
}
