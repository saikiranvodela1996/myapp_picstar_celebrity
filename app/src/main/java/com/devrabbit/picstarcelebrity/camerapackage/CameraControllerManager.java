package com.devrabbit.picstarcelebrity.camerapackage;

import android.content.Context;

public abstract class CameraControllerManager {
    public abstract int getNumberOfCameras();
    /** Returns whether the supplied cameraId is front, back or external.
     */
    public abstract CameraController.Facing getFacing(int cameraId);

    /** Tries to return a textual description for the camera, such as front/back, along with extra
     *  details if possible such as "ultra-wide". Will be null if no description can be determined.
     */
    public abstract String getDescription(Context context, int cameraId);
}
