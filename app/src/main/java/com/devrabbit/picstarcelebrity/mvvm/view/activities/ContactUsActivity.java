package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class ContactUsActivity extends BaseActivity {

    @BindView(R.id.emailTV)
    TextView emailTV;
    @BindView(R.id.phoneNoTV)
    TextView phoneNoTV;
    @BindView(R.id.addressTV)
    TextView addressTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        emailTV.setText(psr_prefsManager.get(PSRConstants.CONTACT_US_EMAIL));
        phoneNoTV.setText(psr_prefsManager.get(PSRConstants.CONTACT_US_PHONE_NO));
        addressTV.setText(psr_prefsManager.get(PSRConstants.CONTACT_US_ADDRESS));
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
    @OnClick(R.id.backIcon)
    void onBackClicked(){
        finish();
    }


    @OnClick(R.id.emailTV)
    void onClickEmail(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{ psr_prefsManager.get(PSRConstants.CONTACT_US_EMAIL)});
        // intent.putExtra(Intent.EXTRA_EMAIL, Uri.parse("mailto:" +psr_prefsManager.get(PSRConstants.CONTACT_US_EMAIL)) );
    /*    intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
        intent.putExtra(Intent.EXTRA_TEXT, "your_text");*/
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }

    @OnClick(R.id.phoneNoTV)
    void onClickPhoneNumber(View v) {
        checkRuntTimePermission();
    }


    public void checkRuntTimePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + psr_prefsManager.get(PSRConstants.CONTACT_US_PHONE_NO)));
                        startActivity(callIntent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(ContactUsActivity.this, R.string.phone_call_permiision_alert, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

}