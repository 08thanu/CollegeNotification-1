package com.example.admin.collegenoti.models;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.activities.StudentsResultDetailsActivity;

import java.util.List;

/**
 * Created by SHASHI on 14-03-2018.
 */

public class StudentDashboardAdapter extends RecyclerView.Adapter<StudentDashboardAdapter.ViewHolder> {

    private List<StudentDashboardSetGets> objects;
    private Context context;

    public StudentDashboardAdapter(Context context, List<StudentDashboardSetGets> objects) {
        this.objects = objects;
        this.context = context;
    }

    @Override
    public StudentDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleview_students_dashboard, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentDashboardAdapter.ViewHolder viewHolder, final int position) {
        final StudentDashboardSetGets a = objects.get(position);
        viewHolder.txt_menu_name.setText(a.menu_name);
        viewHolder.img_menu.setImageResource(a.menu_icon);
        viewHolder.lnr_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StudentsResultDetailsActivity.class);
                intent.putExtra("menu_name", a.menu_name);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_menu_name;
        private ImageView img_menu;
        private LinearLayout lnr_menu;

        public ViewHolder(View view) {
            super(view);
            txt_menu_name = (TextView) view.findViewById(R.id.txt_menu_name);
            img_menu = (ImageView) view.findViewById(R.id.img_menu);
            lnr_menu = (LinearLayout) view.findViewById(R.id.lnr_menu);
        }
    }
}