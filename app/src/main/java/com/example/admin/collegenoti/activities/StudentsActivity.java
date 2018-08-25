package com.example.admin.collegenoti.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.logic.P;
import com.example.admin.collegenoti.models.StudentDashboardAdapter;
import com.example.admin.collegenoti.models.StudentDashboardSetGets;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentsActivity extends AppCompatActivity {
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;
    //    @BindView(R.id.tool_bar_title)
//    TextView tool_bar_title;
    private Context context;
    List<StudentDashboardSetGets> studentDashboardSetGets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        ButterKnife.bind(this);
        context = StudentsActivity.this;
        initViews();
    }

    private void initViews() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        studentDashboardSetGets.add(new StudentDashboardSetGets(R.drawable.ia, "IA Marks"));
        studentDashboardSetGets.add(new StudentDashboardSetGets(R.drawable.attandance, "Attendence"));
        studentDashboardSetGets.add(new StudentDashboardSetGets(R.drawable.timetable, " Time Table"));
        studentDashboardSetGets.add(new StudentDashboardSetGets(R.drawable.shortage, "      Shortage   of Attendence"));
        studentDashboardSetGets.add(new StudentDashboardSetGets(R.drawable.feee, "Fees"));
        studentDashboardSetGets.add(new StudentDashboardSetGets(R.drawable.events, "Events"));
        studentDashboardSetGets.add(new StudentDashboardSetGets(R.drawable.images, "Images"));
        studentDashboardSetGets.add(new StudentDashboardSetGets(R.drawable.holiday, "       Holiday Announcement"));

        StudentDashboardAdapter adapter = new StudentDashboardAdapter(context, studentDashboardSetGets);
        recyclerView.setAdapter(adapter);
    }

    public void onLogout(View v) {
        P.userLogout(context);
        Intent intent = new Intent(context, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }

}
