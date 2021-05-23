package com.devrabbit.picstarcelebrity.app;

import android.app.Application;
import android.os.StrictMode;

import com.devrabbit.picstarcelebrity.utils.PSRUtils;


import java.io.File;

public class PicstarCelebrityApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        createAppFolderIfRequired();
    }

    private void createAppFolderIfRequired() {
        File appFolder = new File(PSRUtils.createFilePath(getApplicationContext()));
        if (!appFolder.exists()) {
            appFolder.mkdir();
        }
    }
}
