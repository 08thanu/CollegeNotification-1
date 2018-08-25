package com.example.admin.collegenoti.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.api.Api;
import com.example.admin.collegenoti.api.IPActivity;
import com.example.admin.collegenoti.api.WebServices;
import com.example.admin.collegenoti.logic.P;
import com.example.admin.collegenoti.logic.U;
import com.example.admin.collegenoti.models.User;
import com.example.admin.collegenoti.models.UserLoginInput;
import com.example.admin.collegenoti.models.UserLoginResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import databases.RealmU;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserLoginActivity extends AppCompatActivity {
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.btn_login)
    ActionProcessButton btn_login;
    @BindView(R.id.btn_register)
    ActionProcessButton btn_register;
    @BindView(R.id.textView)
    TextView textView;
    private Context context;
    private String user_type = "STUDENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        context = UserLoginActivity.this;
        ButterKnife.bind(this);
        user_type = getIntent().getExtras().getString("user_type");

//        Realm.init(this);
//        if (new RealmU(context).isUserLoggedIn()) {
//            moveToNextActivity();
        // }
        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (user_type.equalsIgnoreCase("STUDENT")) {
                    Intent intent = new Intent(context, StudentRegisterActivity.class);
                    intent.putExtra("user_type", user_type);
                    startActivity(intent);
                } else {

                    Intent intent = new Intent(context, UserRegisterActivity.class);
                    intent.putExtra("user_type", user_type);
                    startActivity(intent);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();
            }
        });

        //MODIFIED  BY PRASAD
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, IPActivity.class);
                startActivity(intent);
                return false;
            }
        });


    }


    private void validation() {

        if (!P.isValidEditText(edt_username, "Username")) return;
        if (!P.isValidEditText(edt_password, "Password")) return;
        if (edt_username.getText().toString().trim().equalsIgnoreCase("admin") &&
                edt_password.getText().toString().trim().equalsIgnoreCase("admin")) {
            Intent intent = new Intent(context, AdminHomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        userLogin();
    }

    private void userLogin() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);


        //PREPARE INPUT/REQUEST PARAMETERS
        UserLoginInput userLoginInput = new UserLoginInput(
                edt_username.getText().toString().trim(),
                edt_password.getText().toString().trim()
        );

        btn_login.setProgress(1);
        btn_login.setEnabled(false);
        P.hideSoftKeyboard(UserLoginActivity.this);


        //CALL NOW
        webServices.userLogin(userLoginInput)
                .enqueue(new Callback<UserLoginResult>() {
                    @Override
                    public void onResponse(Call<UserLoginResult> call, Response<UserLoginResult> response) {
                        if (!P.analyseResponse(response)) {
                            btn_login.setProgress(0);
                            btn_login.setEnabled(true);
                            Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                            return;
                        }
                        UserLoginResult result = response.body();
                        if (result.is_success) {
                            btn_login.setProgress(100);
                            /*====SAVE USER DATA IN SHARED PREFERENCES====*/
                            P.saveUserLoginData(context, result.user.get(0));
                            moveToNextActivity();
                        } else {
                            btn_login.setProgress(0);
                            btn_login.setEnabled(true);
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserLoginResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        btn_login.setProgress(0);
                        btn_login.setEnabled(true);

                    }
                });
    }

    private void moveToNextActivity() {
//        if (new RealmU(context).isUserLoggedIn()) {
        Intent intent = null;
        switch (user_type) {
            case "STUDENT":
                intent = new Intent(context, StudentsActivity.class);
                startActivity(intent);
                finish();
                break;
            case "LECTURER":
                intent = new Intent(context, LecturersActivity.class);
                startActivity(intent);
                finish();
                break;
            case "PRINCIPAL":
                intent = new Intent(context, PrincipalActivity.class);
                startActivity(intent);
                finish();
                break;
            case "ADMIN":
                intent = new Intent(context, AdminHomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case "PARENT":
                intent = new Intent(context, ParentsActivity.class);
                startActivity(intent);
                finish();
                break;
        }


    }
}