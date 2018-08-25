package com.example.admin.collegenoti.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.api.Api;
import com.example.admin.collegenoti.api.WebServices;
import com.example.admin.collegenoti.logic.P;
import com.example.admin.collegenoti.logic.U;
import com.example.admin.collegenoti.models.ManagementUploadInput;
import com.example.admin.collegenoti.models.ManagementUploadResult;
import com.example.admin.collegenoti.models.WhoInput;
import com.example.admin.collegenoti.models.WhoResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import setsgets.Who;
import setsgets.WhoAdapter;

public class LecturersActivity extends AppCompatActivity {

    @BindView(R.id.sp_semister)
    Spinner sp_semister;

    @BindView(R.id.img_ia_marks)
    ImageView img_ia_marks;
    @BindView(R.id.img_attendence)
    ImageView img_attendence;
    @BindView(R.id.btn_ia)
    Button btn_ia;
    @BindView(R.id.upl)
    Button upl;
    @BindView(R.id.btn_attendance)
    Button btn_attendance;
    @BindView(R.id.tool_bar_title)
    TextView tool_bar_title;
    private Bitmap bm_ia, bm_attandance;
    int what = 1;
    private Context context;
    private List<String> semNamelist = new ArrayList<>();
    private List<String> semIdList = new ArrayList<>();
    private WhoAdapter mDeptAdapter;
    private RecyclerView recyclerView;
    private List<Who> whos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturers);
        ButterKnife.bind(this);
        context = LecturersActivity.this;
        tool_bar_title.setText("Lecturers Home");

        upl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LuploadsActivity.class);
                startActivity(intent);
            }
        });


        btn_ia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementIaMarks();
            }
        });
        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp_semister.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Select Semester", Toast.LENGTH_LONG).show();
                    return;
                }
                managementAttendance();
            }
        });

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

        img_ia_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = 1;
                captureImage();
            }
        });
        img_attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = 2;
                captureImage();
            }
        });



    }

    private void captureImage() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setCancelable(true);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        getImageFromCamera();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getImageFromGallery();
                    }
                });
        alertDialog.show();
    }

    private void getImageFromCamera() {
        Intent intent1 = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, U.CAMERA);
    }

    private void getImageFromGallery() {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), U.GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case U.CAMERA:
                switch (what) {
                    case 1:
                        bm_ia = (Bitmap) data.getExtras().get("data");//get the image from camera
                        img_ia_marks.setImageBitmap(bm_ia);//place the image
                        break;
                    case 2:
                        bm_attandance = (Bitmap) data.getExtras().get("data");//get the image from camera
                        img_attendence.setImageBitmap(bm_attandance);//place the image
                        break;
                }
                break;
            case U.GALLERY:
                try {
                    Uri uri = data.getData();
                    switch (what) {
                        case 1:

                            bm_ia = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            bm_ia = P.getResizedBitmap(bm_ia, U.IMAGE_SIZE);
                            img_ia_marks.setImageBitmap(bm_ia);//place the image
                            break;

                        case 2:
                            bm_attandance = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            bm_attandance = P.getResizedBitmap(bm_attandance, U.IMAGE_SIZE);
                            img_attendence.setImageBitmap(bm_attandance);//place the image
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }


    private void managementIaMarks() {
        if (bm_ia != null && sp_semister.getSelectedItemPosition() != 0) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Uploading...");
            dialog.show();
            Retrofit retrofit = Api.getRetrofitBuilder(this);
            WebServices webServices = retrofit.create(WebServices.class);

            //PREPARE INPUT/REQUEST PARAMETERS
            ManagementUploadInput management = new ManagementUploadInput(
//                edt_username.getText().toString().trim(),
//                edt_password.getText().toString().trim(),
                    semIdList.get(sp_semister.getSelectedItemPosition()),
                    "IA_MARKS",
                    P.BitmapToString(bm_ia),"LECTURE"
//                edt_number.getText().toString().trim()
            );


            P.hideSoftKeyboard(LecturersActivity.this);
            //CALL NOW
            webServices.management(management)
                    .enqueue(new Callback<ManagementUploadResult>() {
                        @Override
                        public void onResponse(Call<ManagementUploadResult> call, Response<ManagementUploadResult> response) {
                            if (dialog.isShowing()) dialog.dismiss();
                            if (!P.analyseResponse(response)) {

                                Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ManagementUploadResult result = response.body();
                            if (result.is_success) {

                                Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                                startActivity(getIntent());
                                finish();
                            } else {

                                Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ManagementUploadResult> call, Throwable t) {
                            P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                            t.printStackTrace();
                            if (dialog.isShowing()) dialog.dismiss();
                        }
                    });
        } else {
            Toast.makeText(context, "Please select Image", Toast.LENGTH_LONG).show();
        }
    }


    private void managementAttendance() {
        if (bm_attandance != null) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Uploading...");
            dialog.show();
            Retrofit retrofit = Api.getRetrofitBuilder(this);
            WebServices webServices = retrofit.create(WebServices.class);

            //PREPARE INPUT/REQUEST PARAMETERS
            ManagementUploadInput management = new ManagementUploadInput(
//                edt_username.getText().toString().trim(),
//                edt_password.getText().toString().trim(),
                    semIdList.get(sp_semister.getSelectedItemPosition()),
                    "ATTENDENCE",
                    P.BitmapToString(bm_attandance),"LECTURE"
//                edt_number.getText().toString().trim()
            );


            P.hideSoftKeyboard(LecturersActivity.this);
            //CALL NOW
            webServices.management(management)
                    .enqueue(new Callback<ManagementUploadResult>() {
                        @Override
                        public void onResponse(Call<ManagementUploadResult> call, Response<ManagementUploadResult> response) {
                            if (dialog.isShowing()) dialog.dismiss();
                            if (!P.analyseResponse(response)) {

                                Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ManagementUploadResult result = response.body();
                            if (result.is_success) {

                                Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                                startActivity(getIntent());
                                finish();
                            } else {

                                Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ManagementUploadResult> call, Throwable t) {
                            P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                            t.printStackTrace();
                            if (dialog.isShowing()) dialog.dismiss();
                        }
                    });
        } else {
            Toast.makeText(context, "Please select Image", Toast.LENGTH_LONG).show();
        }
    }

    public void onLogout(View v) {
        P.userLogout(context);
        Intent intent = new Intent(context, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }
    private void getSearch() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        WhoInput noti = new WhoInput(


                "A"
        );


        //CALL NOW
        webServices.who(noti)
                .enqueue(new Callback<WhoResult>() {
                    @Override
                    public void onResponse(Call<WhoResult> call, Response<WhoResult> response) {
                        if (!P.analyseResponse(response)) {

                            Toast.makeText(context, "Null", Toast.LENGTH_LONG).show();
                            return;
                        }
                        WhoResult result = response.body();
                        if (result.is_success) {
                            whos = result.clg_managements;
                            mDeptAdapter = new WhoAdapter(context, whos);
                            recyclerView.setAdapter(mDeptAdapter);

                        } else {

                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<WhoResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();

                    }
                });

    }
}
