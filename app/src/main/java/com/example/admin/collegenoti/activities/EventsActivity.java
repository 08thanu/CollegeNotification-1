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

public class EventsActivity extends AppCompatActivity {
    @BindView(R.id.image_Events)
    ImageView image_Events;
    @BindView(R.id.tool_bar_title)
    TextView tool_bar_title;
    private Context context;
    private List<StudentImageStgets> studentImages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);
        context = EventsActivity.this;
        ButterKnife.bind(this);
        context = EventsActivity.this;
        tool_bar_title.setText("Events");
        //showImage();
    }


    private void showImage()
    {
        StudentImageStgets st = studentImages.get(1);
        if (st.getImg_images() != null && st.getImg_images().trim().length() > 0) {
            image_Events.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(P.IMAGE_URL + st.getImg_images()) //server path of the image
                    .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher)) //this is optional the image to display while the url image is downloading
                    .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(image_Events);
        } else {
            image_Events.setVisibility(View.GONE);
        }
    }
}
