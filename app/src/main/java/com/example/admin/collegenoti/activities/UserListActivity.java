package com.example.admin.collegenoti.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.admin.collegenoti.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListActivity extends AppCompatActivity {
    @BindView(R.id.btn_lecturer)
    ActionProcessButton btn_lecturer;
    @BindView(R.id.btn_principal)
    ActionProcessButton btn_principal;
    @BindView(R.id.btn_admin)
    ActionProcessButton btn_admin;
    @BindView(R.id.btn_students)
    ActionProcessButton btn_students;
    @BindView(R.id.btn_parents)
    ActionProcessButton btn_parents;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);
        context = UserListActivity.this;
        btn_lecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminHomeActivity.class);
                startActivity(intent);
            }
        });
        btn_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_parents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
