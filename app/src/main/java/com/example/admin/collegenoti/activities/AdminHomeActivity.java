package com.example.admin.collegenoti.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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

public class AdminHomeActivity extends AppCompatActivity {
    @BindView(R.id.img_time_table)
    ImageView img_time_table;
    @BindView(R.id.img_fee)
    ImageView img_fee;
    @BindView(R.id.img_settings)
    ImageView img_settings;
    @BindView(R.id.tool_bar_title)
    TextView tool_bar_title;
    @BindView(R.id.img_events)
    ImageView img_events;
    @BindView(R.id.img_pictures)
    ImageView img_pictures;
    @BindView(R.id.btn_events)
    Button btn_events;
    @BindView(R.id.btn_timetable)
    Button btn_timetable;
    @BindView(R.id.btn_shortage_of_attandance)
    Button btn_shortage_of_attandance;
    @BindView(R.id.img_shortage_of_attendance)
    ImageView img_shortage_of_attendance;
    @BindView(R.id.btn_pictures)
    Button btn_pictures;
    @BindView(R.id.btn_fee)
    Button btn_fee;
    private Bitmap bm_time_table, bm_fee, bm_events, bm_pic, bm_short;

    @BindView(R.id.sp_semister)
    Spinner sp_semister;
    private Context context;
    private List<String> semNamelist = new ArrayList<>();
    private List<String> semIdList = new ArrayList<>();
    int what = 1;
    ProgressDialog dialog;
    private WhoAdapter mDeptAdapter;
    private RecyclerView recyclerView;
    private List<Who> whos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        ButterKnife.bind(this);
        context = AdminHomeActivity.this;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Uploading...");
        tool_bar_title.setText("Admin Home");
//        img_settings.setImageResource(R.drawable.settings);





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

        btn_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp_semister.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Select Semester", Toast.LENGTH_LONG).show();
                    return;
                }
                managementEvent();
            }
        });
        btn_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp_semister.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Select Semester", Toast.LENGTH_LONG).show();
                    return;
                }
                managementTimeTable();
            }
        });

        btn_shortage_of_attandance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp_semister.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Select Semester", Toast.LENGTH_LONG).show();
                    return;
                }
                managementShortageAt();
            }
        });

        btn_pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp_semister.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Select Semester", Toast.LENGTH_LONG).show();
                    return;
                }
                managementPictures();
            }
        });

        btn_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp_semister.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Select Semester", Toast.LENGTH_LONG).show();
                    return;
                }
                managementFee();
            }
        });


        img_pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = 4;
                captureImage();
            }
        });

        img_shortage_of_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = 5;
                captureImage();
            }
        });

        img_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = 3;
                captureImage();
            }
        });

        img_time_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = 1;
                captureImage();
            }
        });
        img_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = 2;
                captureImage();
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view_uploads);
        mDeptAdapter = new WhoAdapter(context, whos);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mDeptAdapter);
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//
//            @Override
//            public void onClick(View view, int position) {
//                Chat cha = chats.get(position);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//
//    }
        getSearch();

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
                        bm_time_table = (Bitmap) data.getExtras().get("data");//get the image from camera
                        img_time_table.setImageBitmap(bm_time_table);//place the image
                        break;
                    case 2:
                        bm_fee = (Bitmap) data.getExtras().get("data");//get the image from camera
                        img_fee.setImageBitmap(bm_fee);//place the image
                        break;

                    case 3:
                        bm_events = (Bitmap) data.getExtras().get("data");//get the image from camera
                        img_events.setImageBitmap(bm_events);//place the image
                        break;
                    case 4:
                        bm_pic = (Bitmap) data.getExtras().get("data");//get the image from camera
                        img_pictures.setImageBitmap(bm_pic);//place the image
                        break;
                    case 5:
                        bm_short = (Bitmap) data.getExtras().get("data");//get the image from camera
                        img_shortage_of_attendance.setImageBitmap(bm_short);//place the image
                        break;
                }
                break;
            case U.GALLERY:
                try {
                    Uri uri = data.getData();
                    switch (what) {
                        case 1:

                            bm_time_table = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            bm_time_table = P.getResizedBitmap(bm_time_table, U.IMAGE_SIZE);
                            img_time_table.setImageBitmap(bm_time_table);//place the image
                            break;

                        case 2:
                            bm_fee = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            bm_fee = P.getResizedBitmap(bm_fee, U.IMAGE_SIZE);
                            img_fee.setImageBitmap(bm_fee);//place the image
                            break;

                        case 3:
                            bm_events = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            bm_events = P.getResizedBitmap(bm_events, U.IMAGE_SIZE);
                            img_events.setImageBitmap(bm_events);//place the image
                            break;
                        case 4:
                            bm_pic = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            bm_events = P.getResizedBitmap(bm_pic, U.IMAGE_SIZE);
                            img_pictures.setImageBitmap(bm_pic);//place the image
                            break;
                        case 5:
                            bm_short = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            bm_events = P.getResizedBitmap(bm_short, U.IMAGE_SIZE);
                            img_shortage_of_attendance.setImageBitmap(bm_short);//place the image
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void managementEvent() {

        if (bm_events != null) {
            Retrofit retrofit = Api.getRetrofitBuilder(this);
            WebServices webServices = retrofit.create(WebServices.class);

            //PREPARE INPUT/REQUEST PARAMETERS
            ManagementUploadInput management = new ManagementUploadInput(
//                edt_username.getText().toString().trim(),
//                edt_password.getText().toString().trim(),
                    semIdList.get(sp_semister.getSelectedItemPosition()),
                    "EVENTS",
                    P.BitmapToString(bm_events),
                    "A"
//                edt_number.getText().toString().trim()
            );

//        btn_Submit.setProgress(1);
//        btn_Submit.setEnabled(false);
            P.hideSoftKeyboard(AdminHomeActivity.this);
            //CALL NOW
            dialog.show();
            webServices.management(management)
                    .enqueue(new Callback<ManagementUploadResult>() {
                        @Override
                        public void onResponse(Call<ManagementUploadResult> call, Response<ManagementUploadResult> response) {
                            if (dialog.isShowing()) dialog.dismiss();
                            if (!P.analyseResponse(response)) {
//                            btn_Submit.setProgress(0);
//                            btn_Submit.setEnabled(true);
                                Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ManagementUploadResult result = response.body();
                            if (result.is_success) {
//                            btn_Submit.setProgress(100);
                                Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                                startActivity(getIntent());
                                finish();
                            } else {
//                            btn_Submit.setProgress(0);
//                            btn_Submit.setEnabled(true);
                                Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ManagementUploadResult> call, Throwable t) {
                            P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                            t.printStackTrace();
                            if (dialog.isShowing()) dialog.dismiss();
//                        btn_Submit.setProgress(0);
//                        btn_Submit.setEnabled(true);

                        }
                    });
        } else {
            Toast.makeText(context, "Please select Image", Toast.LENGTH_LONG).show();
        }

    }

    private void managementFee() {

        if (bm_fee != null) {
            Retrofit retrofit = Api.getRetrofitBuilder(this);
            WebServices webServices = retrofit.create(WebServices.class);

            //PREPARE INPUT/REQUEST PARAMETERS
            ManagementUploadInput management = new ManagementUploadInput(
//                edt_username.getText().toString().trim(),
//                edt_password.getText().toString().trim(),
                    semIdList.get(sp_semister.getSelectedItemPosition()),
                    "FEE",
                    P.BitmapToString(bm_fee),"A"
//                edt_number.getText().toString().trim()
            );

//        btn_Submit.setProgress(1);
//        btn_Submit.setEnabled(false);
            dialog.show();
            P.hideSoftKeyboard(AdminHomeActivity.this);
            //CALL NOW
            webServices.management(management)
                    .enqueue(new Callback<ManagementUploadResult>() {
                        @Override
                        public void onResponse(Call<ManagementUploadResult> call, Response<ManagementUploadResult> response) {
                            if (dialog.isShowing()) dialog.dismiss();
                            if (!P.analyseResponse(response)) {
//                            btn_Submit.setProgress(0);
//                            btn_Submit.setEnabled(true);
                                Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ManagementUploadResult result = response.body();
                            if (result.is_success) {
//                            btn_Submit.setProgress(100);
                                Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                                startActivity(getIntent());
                                finish();
                            } else {
//                            btn_Submit.setProgress(0);
//                            btn_Submit.setEnabled(true);
                                Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ManagementUploadResult> call, Throwable t) {
                            P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                            t.printStackTrace();
                            if (dialog.isShowing()) dialog.dismiss();
//                        btn_Submit.setProgress(0);
//                        btn_Submit.setEnabled(true);

                        }
                    });
        } else {
            Toast.makeText(context, "Please select Image", Toast.LENGTH_LONG).show();
        }

    }

    private void managementTimeTable() {

        if (bm_time_table != null) {
            Retrofit retrofit = Api.getRetrofitBuilder(this);
            WebServices webServices = retrofit.create(WebServices.class);

            //PREPARE INPUT/REQUEST PARAMETERS
            ManagementUploadInput management = new ManagementUploadInput(
//                edt_username.getText().toString().trim(),
//                edt_password.getText().toString().trim(),
                    semIdList.get(sp_semister.getSelectedItemPosition()),
                    "TIME_TABLE",
                    P.BitmapToString(bm_time_table),"A"
//                edt_number.getText().toString().trim()
            );

            dialog.show();
            P.hideSoftKeyboard(AdminHomeActivity.this);
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
//                            btn_Submit.setProgress(100);
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


    private void managementShortageAt() {

        if (bm_short != null) {
            Retrofit retrofit = Api.getRetrofitBuilder(this);
            WebServices webServices = retrofit.create(WebServices.class);

            //PREPARE INPUT/REQUEST PARAMETERS
            ManagementUploadInput management = new ManagementUploadInput(
//                edt_username.getText().toString().trim(),
//                edt_password.getText().toString().trim(),
                    semIdList.get(sp_semister.getSelectedItemPosition()),
                    "ATTENDENCE_SHORTAGE",
                    P.BitmapToString(bm_short),"A"
//                edt_number.getText().toString().trim()
            );

            dialog.show();
            P.hideSoftKeyboard(AdminHomeActivity.this);
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
//                            btn_Submit.setProgress(100);
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

    private void managementPictures() {

        if (bm_pic != null) {
            Retrofit retrofit = Api.getRetrofitBuilder(this);
            WebServices webServices = retrofit.create(WebServices.class);

            //PREPARE INPUT/REQUEST PARAMETERS
            ManagementUploadInput management = new ManagementUploadInput(
//                edt_username.getText().toString().trim(),
//                edt_password.getText().toString().trim(),
                    semIdList.get(sp_semister.getSelectedItemPosition()),
                    "PICTURES",
                    P.BitmapToString(bm_pic),"A"
//                edt_number.getText().toString().trim()
            );

            dialog.show();
            P.hideSoftKeyboard(AdminHomeActivity.this);
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
//                            btn_Submit.setProgress(100);
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
