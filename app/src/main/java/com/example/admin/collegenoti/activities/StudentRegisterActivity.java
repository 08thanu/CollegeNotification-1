package com.example.admin.collegenoti.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.api.Api;
import com.example.admin.collegenoti.api.WebServices;
import com.example.admin.collegenoti.logic.P;
import com.example.admin.collegenoti.models.UserRegisterInput;
import com.example.admin.collegenoti.models.UserRegisterResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StudentRegisterActivity extends AppCompatActivity {
    @BindView(R.id.sp_semister)
    Spinner sp_semister;
    @BindView(R.id.edt_urn)
    EditText edt_urn;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.edt_mobile_number)
    EditText edt_mobile_number;
    @BindView(R.id.btn_Submit)
    ActionProcessButton btn_Submit;
    private Context context;

    private List<String> semNamelist = new ArrayList<>();
    private List<String> semIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        ButterKnife.bind(this);
        context = StudentRegisterActivity.this;

        semNamelist.add("Select Semister");
        semNamelist.add("1");
        semNamelist.add("2");
        semNamelist.add("3");
        semNamelist.add("4");
        semNamelist.add("5");
        semNamelist.add("6");

        semIdList.add("0");
        semIdList.add("1");
        semIdList.add("2");
        semIdList.add("3");
        semIdList.add("4");
        semIdList.add("5");
        semIdList.add("6");
        P.setSpinnerAdapter(context, sp_semister, semNamelist);
        sp_semister.setSelection(0);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

    private void validation() {
        if (!P.isValidEditText(edt_urn, "URN")) return;
        if (!P.isValidEditText(edt_password, "Password")) return;
        if (!P.isValidEditText(edt_mobile_number, "Mobile Number")) return;
     //   if (!P.isValidsemIdList(sp_semister, "Sem")) return;
        userRegister();
    }
    private void userRegister() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        UserRegisterInput userRegisterInput = new UserRegisterInput(
                edt_urn.getText().toString().trim(),
                edt_password.getText().toString().trim(),
                getIntent().getExtras().getString("user_type"),
                edt_mobile_number.getText().toString().trim(),
                semIdList.get(sp_semister.getSelectedItemPosition()),
                edt_urn.getText().toString().trim()
        );

        btn_Submit.setProgress(1);
        btn_Submit.setEnabled(false);
        P.hideSoftKeyboard(StudentRegisterActivity.this);
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
