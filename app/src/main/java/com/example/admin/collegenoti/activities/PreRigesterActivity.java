package com.example.admin.collegenoti.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.admin.collegenoti.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreRigesterActivity extends AppCompatActivity {
    @BindView(R.id.txt_admin)
    TextView txt_admin;
    @BindView(R.id.txt_lecturers)
    TextView txt_lecturers;
    @BindView(R.id.txt_parents)
    TextView txt_parents;
    @BindView(R.id.txt_principal)
    TextView txt_principal;
    @BindView(R.id.txt_students)
    TextView txt_students;

    int lectures = 1;
    int principal = 2;
    int admins = 3;
    final int students = 4;
    int parents = 5;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_rigester);
        ButterKnife.bind(this);
        context = PreRigesterActivity.this;

        txt_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserLoginActivity.class);
                intent.putExtra("user_type", "STUDENT");
                startActivity(intent);
            }
        });

        txt_lecturers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserLoginActivity.class);
                intent.putExtra("user_type", "LECTURER");
                startActivity(intent);
            }
        });
        txt_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserLoginActivity.class);
                intent.putExtra("user_type", "PRINCIPAL");
                startActivity(intent);
            }
        });
        txt_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserLoginActivity.class);
                intent.putExtra("user_type", "ADMIN");
                startActivity(intent);
            }
        });
        txt_parents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserLoginActivity.class);
                intent.putExtra("user_type", "PARENT");
                startActivity(intent);
            }
        });
    }
}
