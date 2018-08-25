package com.example.admin.collegenoti.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.api.Api;
import com.example.admin.collegenoti.api.WebServices;
import com.example.admin.collegenoti.logic.P;
import com.example.admin.collegenoti.logic.U;
import com.example.admin.collegenoti.models.GetManagementUtilsInput;
import com.example.admin.collegenoti.models.GetManagementUtilsResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import setsgets.ImageS;
import setsgets.StudentImageStgets;

public class StudentsResultDetailsActivity extends AppCompatActivity {
    @BindView(R.id.image_ia_marks)
    ImageView image_ia_marks;
    private Context context;
    @BindView(R.id.tool_bar_title)
    TextView tool_bar_title;
    private List<StudentImageStgets> studentImages = new ArrayList<>();
    private int position = 0;
    private String menu_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ia_marks);
        ButterKnife.bind(this);
        context = StudentsResultDetailsActivity.this;
        menu_name = getIntent().getStringExtra("menu_name");
        position = getIntent().getExtras().getInt("position");
        tool_bar_title.setText(menu_name);
        //showImage();

        management();

    }


    private void management() {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading....");
        dialog.show();

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);
        String sem = P.getUserDetails(context).user_sem;
        if (position == 7) sem = "0";
        //PREPARE INPUT/REQUEST PARAMETERS
        GetManagementUtilsInput management = new GetManagementUtilsInput(
                sem
        );


        P.hideSoftKeyboard(StudentsResultDetailsActivity.this);
        //CALL NOW
        webServices.getManagementUtils(management)
                .enqueue(new Callback<GetManagementUtilsResult>() {
                    @Override
                    public void onResponse(Call<GetManagementUtilsResult> call, Response<GetManagementUtilsResult> response) {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (!P.analyseResponse(response)) {

                            Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                            return;
                        }
                        GetManagementUtilsResult result = response.body();
                        if (result.is_success) {
                            showImage(result.images);
                        } else {
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetManagementUtilsResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                });


    }

    public void showImage(List<ImageS> images) {
        for (int i = 0; i < images.size(); i++) {
            P.LogD("StudentsresultDetails m_util_type : " + images.get(i).m_util_type);
            P.LogD("StudentsresultDetails menu_name : " + U.getImgeType(position));
            if (images.get(i).m_util_type.equalsIgnoreCase(U.getImgeType(position))) {
                image_ia_marks.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(P.IMAGE_URL + images.get(i).m_util_image) //server path of the image
                        .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher)) //this is optional the image to display while the url image is downloading
                        .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(image_ia_marks);
            }
        }
    }


}
