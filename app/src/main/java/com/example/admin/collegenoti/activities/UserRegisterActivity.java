package com.example.admin.collegenoti.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.api.Api;
import com.example.admin.collegenoti.api.WebServices;
import com.example.admin.collegenoti.logic.P;
import com.example.admin.collegenoti.models.UserRegisterInput;
import com.example.admin.collegenoti.models.UserRegisterResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRegisterActivity extends AppCompatActivity {

    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.edt_number)
    EditText edt_number;
    @BindView(R.id.btn_Submit)
    ActionProcessButton btn_Submit;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        context = UserRegisterActivity.this;
        ButterKnife.bind(this);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

    private void validation() {
        if (!P.isValidEditText(edt_username, "Username")) return;
        if (!P.isValidEditText(edt_password, "Password")) return;
        if (!P.isValidEditText(edt_number, "Mobile Number")) return;
        userRegister();
    }
    private void userRegister() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        UserRegisterInput userRegisterInput = new UserRegisterInput(
                edt_username.getText().toString().trim(),
                edt_password.getText().toString().trim(),
                getIntent().getExtras().getString("user_type"),
                edt_number.getText().toString().trim(),
                "0",
                ""
        );

        btn_Submit.setProgress(1);
        btn_Submit.setEnabled(false);
        P.hideSoftKeyboard(UserRegisterActivity.this);
        //CALL NOW
        webServices.userRegister(userRegisterInput)
                .enqueue(new Callback<UserRegisterResult>() {
                    @Override
                    public void onResponse(Call<UserRegisterResult> call, Response<UserRegisterResult> response) {
                        if (!P.analyseResponse(response)) {
                            btn_Submit.setProgress(0);
                            btn_Submit.setEnabled(true);
                            Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                            return;
                        }
                        UserRegisterResult result = response.body();
                        if (result.is_success) {
                            btn_Submit.setProgress(100);
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            btn_Submit.setProgress(0);
                            btn_Submit.setEnabled(true);
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<UserRegisterResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        btn_Submit.setProgress(0);
                        btn_Submit.setEnabled(true);

                    }
                });
    }
}