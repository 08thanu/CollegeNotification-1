package com.example.admin.collegenoti.activities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.logic.P;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import setsgets.StudentImageStgets;

public class TimetableActivity extends AppCompatActivity {
    @BindView(R.id.image_timetable)
    ImageView image_timetable;
    private Context context;
    @BindView(R.id.tool_bar_title)
    TextView tool_bar_title;
    private List<StudentImageStgets> studentImages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        ButterKnife.bind(this);
        context = TimetableActivity.this;
        tool_bar_title.setText("Time Table");
        showImage();
    }


    private void showImage()
    {
        StudentImageStgets st = studentImages.get(1);
        if (st.getImg_images() != null && st.getImg_images().trim().length() > 0) {
            image_timetable.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(P.IMAGE_URL + st.getImg_images()) //server path of the image
                    .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher)) //this is optional the image to display while the url image is downloading
                    .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(image_timetable);
        } else {
            image_timetable.setVisibility(View.GONE);
        }
    }
}
