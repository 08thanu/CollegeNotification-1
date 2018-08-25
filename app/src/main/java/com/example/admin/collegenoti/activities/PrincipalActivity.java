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

public class PrincipalActivity extends AppCompatActivity {
    @BindView(R.id.tool_bar_title)
    TextView tool_bar_title;
    @BindView(R.id.img_holiday)
    ImageView img_holiday;
    @BindView(R.id.btn_Submit)
    Button btn_Submit;
    private Bitmap bm_holiday;
    int what = 1;
    private Context context;
    private WhoAdapter mDeptAdapter;
    private RecyclerView recyclerView;
    private List<Who> whos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        context = PrincipalActivity.this;
        tool_bar_title.setText("Holiday Announcement");

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                management();
            }
        });


        img_holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = 1;
                captureImage();
            }
        });

        getSearch();

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view_uploadsp);
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
                        bm_holiday = (Bitmap) data.getExtras().get("data");//get the image from camera
                        img_holiday.setImageBitmap(bm_holiday);//place the image
                        break;
//                    case 2:
//                        bm_attandance = (Bitmap) data.getExtras().get("data");//get the image from camera
//                        img_attendence.setImageBitmap(bm_attandance);//place the image
//                        break;
                }
                break;
            case U.GALLERY:
                try {
                    Uri uri = data.getData();
                    switch (what) {
                        case 1:

                            bm_holiday = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            bm_holiday = P.getResizedBitmap(bm_holiday, U.IMAGE_SIZE);
                            img_holiday.setImageBitmap(bm_holiday);//place the image
                            break;

//                        case 2:
//                            bm_attandance = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                            bm_attandance = P.getResizedBitmap(bm_attandance,U.IMAGE_SIZE);
//                            img_attendence.setImageBitmap(bm_attandance);//place the image
//                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    private void management() {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Uploading...");
        dialog.show();
        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        ManagementUploadInput management = new ManagementUploadInput(
//                edt_username.getText().toString().trim(),
//                edt_password.getText().toString().trim(),
                "0",
                "HOLIDAY",
                P.BitmapToString(bm_holiday), "PRINCIPAL"
//                edt_number.getText().toString().trim()
        );

//        btn_Submit.setProgress(1);
//        btn_Submit.setEnabled(false);

        P.hideSoftKeyboard(PrincipalActivity.this);
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


                "P"
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