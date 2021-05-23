package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.parentLL)
    LinearLayout parentLL;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (psr_prefsManager.getBoolean(PSRConstants.ISLOGGEDIN) && psr_prefsManager.getBoolean(PSRConstants.ISSERVICESSELECTED)) {
                    Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                } else if (psr_prefsManager.getBoolean(PSRConstants.ISLOGGEDIN)) {
                    Intent intent = new Intent(SplashActivity.this, PersonaliseActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    parentLL.setVisibility(View.VISIBLE);
                }


            }
        }, 2000);
    }

    @OnClick(R.id.get_started)
    void onGetStartedClicked() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

}