package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.ActivityLoginBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.LoginViewModel;
import com.devrabbit.picstarcelebrity.navigator.LoginNavigator;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity implements LoginNavigator {

    LoginViewModel loginViewModel;
    ActivityLoginBinding activityLoginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new LoginViewModel(this,this);
         activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setViewModel(loginViewModel);
        activityLoginBinding.executePendingBindings();

    }

    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public PSR_PrefsManager getPreferences() {
        return psr_prefsManager;
    }


    @Override
    public void onClickEyeIcon(boolean isPwdShown) {
        if(isPwdShown){
            activityLoginBinding.passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            activityLoginBinding.paswordToggle.setImageDrawable(getResources().getDrawable(R.drawable.ic_password_show));
            activityLoginBinding.passwordET.setSelection(activityLoginBinding.passwordET.getText().length());
        }else {
            activityLoginBinding.paswordToggle.setImageDrawable(getResources().getDrawable(R.drawable.ic_password_hide));
            activityLoginBinding.passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            activityLoginBinding.passwordET.setSelection(activityLoginBinding.passwordET.getText().length());
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
}