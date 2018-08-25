package com.example.admin.collegenoti.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.api.Api;
import com.example.admin.collegenoti.api.WebServices;
import com.example.admin.collegenoti.logic.P;
import com.example.admin.collegenoti.models.WhoInput;
import com.example.admin.collegenoti.models.WhoResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import setsgets.Who;
import setsgets.WhoAdapter;

public class LuploadsActivity extends AppCompatActivity {
    private WhoAdapter mDeptAdapter;
    private RecyclerView recyclerView;
    private List<Who> whos = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luploads);
        ButterKnife.bind(this);
        context = LuploadsActivity.this;
        getSearch();

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view_uploadsle);
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
    private void getSearch() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        WhoInput noti = new WhoInput(


                "L"
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
