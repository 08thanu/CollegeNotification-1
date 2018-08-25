package com.example.admin.collegenoti.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.logic.P;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {
    @BindView(R.id.image_View)
    ImageView image_View;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        context = SplashScreenActivity.this;
        final Animation animation_2 = AnimationUtils.loadAnimation(context, R.anim.anim_splash);

        image_View.startAnimation(animation_2);

        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (P.isUserLoggedIn(context)) {
                    moveToNextActivity(P.getUserDetails(context).user_type);
                } else {
                    Intent intent = new Intent(context, PreRigesterActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    private void moveToNextActivity(String user_type) {
//        if (new RealmU(context).isUserLoggedIn()) {
        Intent intent = null;
        switch (user_type) {
            case "STUDENT":
                intent = new Intent(context, StudentsActivity.class);
                startActivity(intent);
                finish();
                break;
            case "LECTURER":
                intent = new Intent(context, LecturersActivity.class);
                startActivity(intent);
                finish();
                break;
            case "PRINCIPAL":
                intent = new Intent(context, PrincipalActivity.class);
                startActivity(intent);
                finish();
                break;
            case "ADMIN":
                intent = new Intent(context, AdminHomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case "PARENT":
                intent = new Intent(context, ParentsActivity.class);
                startActivity(intent);
                finish();
                break;
        }


    }


}
