package com.example.admin.collegenoti.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class ParentsActivity extends AppCompatActivity {
    @BindView(R.id.sp_semister)
    Spinner sp_semister;
    @BindView(R.id.sp_settings)
    Spinner sp_settings;

    @BindView(R.id.tool_bar_title)
    TextView tool_bar_title;

    @BindView(R.id.img_ia_marks)ImageView img_ia_marks;
    @BindView(R.id.img_attendence)ImageView img_attendence;
    @BindView(R.id.img_time_table)ImageView img_time_table;
    @BindView(R.id.img_attendence_shortage)ImageView img_attendence_shortage;
    @BindView(R.id.img_fee)ImageView img_fee;


    private Context context;
    private List<String> semNamelist = new ArrayList<>();
    private List<String> semIdList = new ArrayList<>();
    private List<String> settingsNamelist = new ArrayList<>();
    private List<String> setttingsIdList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);

        ButterKnife.bind(this);
        context = ParentsActivity.this;
        tool_bar_title.setText("Child Information");


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


        settingsNamelist.add("Logout");

        setttingsIdList.add("1");
        P.setSpinnerAdapter(context, sp_settings, settingsNamelist);
        sp_settings.setSelection(0);

    }

    public void onSearch(View v){
        if(sp_semister.getSelectedItemPosition()==0){
            Toast.makeText(context, "Select Semester",Toast.LENGTH_LONG).show();
            return;
        }
        management();
    }

    public void onLogout(View v) {
        P.userLogout(context);
        Intent intent = new Intent(context, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }

    private void management() {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading....");
        dialog.show();

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);
        String sem = semIdList.get(sp_semister.getSelectedItemPosition());
        //PREPARE INPUT/REQUEST PARAMETERS
        GetManagementUtilsInput management = new GetManagementUtilsInput(
                sem
        );


        P.hideSoftKeyboard(ParentsActivity.this);
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
            P.LogD("StudentsresultDetails menu_name : " + U.getImgeType(0));
            if (images.get(i).m_util_type.equalsIgnoreCase(U.getImgeType(0))) {
                img_ia_marks.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(P.IMAGE_URL + images.get(i).m_util_image) //server path of the image
                        .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher)) //this is optional the image to display while the url image is downloading
                        .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(img_ia_marks);
            }

            if (images.get(i).m_util_type.equalsIgnoreCase(U.getImgeType(1))) {
                img_attendence.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(P.IMAGE_URL + images.get(i).m_util_image) //server path of the image
                        .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher)) //this is optional the image to display while the url image is downloading
                        .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(img_attendence);
            }

            if (images.get(i).m_util_type.equalsIgnoreCase(U.getImgeType(2))) {
                img_time_table.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(P.IMAGE_URL + images.get(i).m_util_image) //server path of the image
                        .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher)) //this is optional the image to display while the url image is downloading
                        .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(img_time_table);
            }

            if (images.get(i).m_util_type.equalsIgnoreCase(U.getImgeType(3))) {
                img_attendence_shortage.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(P.IMAGE_URL + images.get(i).m_util_image) //server path of the image
                        .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher)) //this is optional the image to display while the url image is downloading
                        .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(img_attendence_shortage);
            }
            if (images.get(i).m_util_type.equalsIgnoreCase(U.getImgeType(4))) {
                img_fee.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(P.IMAGE_URL + images.get(i).m_util_image) //server path of the image
                        .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher)) //this is optional the image to display while the url image is downloading
                        .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(img_fee);
            }
        }
    }

}
