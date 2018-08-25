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

public class ShortageAtActivity extends AppCompatActivity {
    @BindView(R.id.image_shortage_attandance)
    ImageView image_shortage_attandance;
    @BindView(R.id.tool_bar_title)
    TextView tool_bar_title;
    private Context context;
    private List<StudentImageStgets> studentImages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortage_at);
        ButterKnife.bind(this);
        context = ShortageAtActivity.this;
        tool_bar_title.setText("Shortage of Attendance");
       // showImage();
    }


    private void showImage()
    {
        StudentImageStgets st = studentImages.get(1);
        if (st.getImg_images() != null && st.getImg_images().trim().length() > 0) {
            image_shortage_attandance.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(P.IMAGE_URL + st.getImg_images()) //server path of the image
                    .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher)) //this is optional the image to display while the url image is downloading
                    .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(image_shortage_attandance);
        } else {
            image_shortage_attandance.setVisibility(View.GONE);
        }
    }
}
