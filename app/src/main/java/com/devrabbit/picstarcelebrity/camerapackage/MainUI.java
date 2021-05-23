package com.devrabbit.picstarcelebrity.camerapackage;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.devrabbit.picstarcelebrity.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.devrabbit.picstarcelebrity.camerapackage.CameraController.Facing.FACING_BACK;
import static com.devrabbit.picstarcelebrity.camerapackage.CameraController.Facing.FACING_EXTERNAL;
import static com.devrabbit.picstarcelebrity.camerapackage.CameraController.Facing.FACING_FRONT;

/** This contains functionality related to the main UI.
 */
public class MainUI {
    private static final String TAG = "MainUI";

    private final VideoRecordingActivity main_activity;

    private volatile boolean popup_view_is_open; // must be volatile for test project reading the state
    private PopupView popup_view;
    private final static boolean cache_popup = true; // if false, we recreate the popup each time
    private boolean force_destroy_popup = false; // if true, then the popup isn't cached for only the next time the popup is closed

    private int current_orientation;
    enum UIPlacement {
        UIPLACEMENT_RIGHT,
        UIPLACEMENT_LEFT,
        UIPLACEMENT_TOP
    }
    private UIPlacement ui_placement = UIPlacement.UIPLACEMENT_RIGHT;
    private View top_icon = null;
    private boolean view_rotate_animation;
    private final static int view_rotate_animation_duration = 100; // duration in ms of the icon rotation animation

    private boolean immersive_mode;
    private boolean show_gui_photo = true; // result of call to showGUI() - false means a "reduced" GUI is displayed, whilst taking photo or video
    private boolean show_gui_video = true;

    private boolean keydown_volume_up;
    private boolean keydown_volume_down;

    // For remote control: keep track of the currently highlighted
    // line and icon within the line
    private boolean remote_control_mode; // whether remote control mode is enabled
    private int mPopupLine = 0;
    private int mPopupIcon = 0;
    private LinearLayout mHighlightedLine;
    private View mHighlightedIcon;
    private boolean mSelectingIcons = false;
    private boolean mSelectingLines = false;
    private int mExposureLine = 0;
    private boolean mSelectingExposureUIElement = false;
    private final int highlightColor = Color.rgb(183, 28, 28); // Red 900
    private final int highlightColorExposureUIElement = Color.rgb(244, 67, 54); // Red 500

    // for testing:
    private final Map<String, View> test_ui_buttons = new Hashtable<>();
    public int test_saved_popup_width;
    public int test_saved_popup_height;
    public volatile int test_navigation_gap;

    public MainUI(VideoRecordingActivity main_activity) {
        if( MyDebug.LOG )
            Log.d(TAG, "MainUI");
        this.main_activity = main_activity;

    }


    public void layoutUI() {
        layoutUI(false);
    }

    private UIPlacement computeUIPlacement() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        String ui_placement_string = sharedPreferences.getString(PreferenceKeys.UIPlacementPreferenceKey, "ui_top");
        switch( ui_placement_string ) {
            case "ui_left":
                return UIPlacement.UIPLACEMENT_LEFT;
            case "ui_top":
                return UIPlacement.UIPLACEMENT_TOP;
            default:
                return UIPlacement.UIPLACEMENT_RIGHT;
        }
    }

    private void layoutUI(boolean popup_container_only) {
        long debug_time = 0;
        if( MyDebug.LOG ) {
            Log.d(TAG, "layoutUI");
            debug_time = System.currentTimeMillis();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        // we cache the preference_ui_placement to save having to check it in the draw() method
        this.ui_placement = computeUIPlacement();
        if( MyDebug.LOG )
            Log.d(TAG, "ui_placement: " + ui_placement);
        // new code for orientation fixed to landscape
        // the display orientation should be locked to landscape, but how many degrees is that?
        int rotation = main_activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
            default:
                break;
        }
        // getRotation is anti-clockwise, but current_orientation is clockwise, so we add rather than subtract
        // relative_orientation is clockwise from landscape-left
        //int relative_orientation = (current_orientation + 360 - degrees) % 360;
        int relative_orientation = (current_orientation + degrees) % 360;
        if( MyDebug.LOG ) {
            Log.d(TAG, "    current_orientation = " + current_orientation);
            Log.d(TAG, "    degrees = " + degrees);
            Log.d(TAG, "    relative_orientation = " + relative_orientation);
        }
        final int ui_rotation = (360 - relative_orientation) % 360;
        main_activity.getPreview().setUIRotation(ui_rotation);
        int align_left = RelativeLayout.ALIGN_LEFT;
        int align_right = RelativeLayout.ALIGN_RIGHT;
        //int align_top = RelativeLayout.ALIGN_TOP;
        //int align_bottom = RelativeLayout.ALIGN_BOTTOM;
        int left_of = RelativeLayout.LEFT_OF;
        int right_of = RelativeLayout.RIGHT_OF;
        int iconpanel_left_of = left_of;
        int iconpanel_right_of = right_of;
        int above = RelativeLayout.ABOVE;
        int below = RelativeLayout.BELOW;
        int iconpanel_above = above;
        int iconpanel_below = below;
        int align_parent_left = RelativeLayout.ALIGN_PARENT_LEFT;
        int align_parent_right = RelativeLayout.ALIGN_PARENT_RIGHT;
        int iconpanel_align_parent_left = align_parent_left;
        int iconpanel_align_parent_right = align_parent_right;
        int align_parent_top = RelativeLayout.ALIGN_PARENT_TOP;
        int align_parent_bottom = RelativeLayout.ALIGN_PARENT_BOTTOM;
        int iconpanel_align_parent_top = align_parent_top;
        int iconpanel_align_parent_bottom = align_parent_bottom;
        if( ui_placement == UIPlacement.UIPLACEMENT_LEFT ) {
            above = RelativeLayout.BELOW;
            below = RelativeLayout.ABOVE;
            align_parent_top = RelativeLayout.ALIGN_PARENT_BOTTOM;
            align_parent_bottom = RelativeLayout.ALIGN_PARENT_TOP;
            iconpanel_align_parent_top = align_parent_top;
            iconpanel_align_parent_bottom = align_parent_bottom;
        }
        else if( ui_placement == UIPlacement.UIPLACEMENT_TOP ) {
            iconpanel_left_of = RelativeLayout.BELOW;
            iconpanel_right_of = RelativeLayout.ABOVE;
            iconpanel_above = RelativeLayout.LEFT_OF;
            iconpanel_below = RelativeLayout.RIGHT_OF;
            //noinspection SuspiciousNameCombination
            iconpanel_align_parent_left = RelativeLayout.ALIGN_PARENT_BOTTOM;
            //noinspection SuspiciousNameCombination
            iconpanel_align_parent_right = RelativeLayout.ALIGN_PARENT_TOP;
            //noinspection SuspiciousNameCombination
            iconpanel_align_parent_top = RelativeLayout.ALIGN_PARENT_LEFT;
            //noinspection SuspiciousNameCombination
            iconpanel_align_parent_bottom = RelativeLayout.ALIGN_PARENT_RIGHT;
        }

        Point display_size = new Point();
        Display display = main_activity.getWindowManager().getDefaultDisplay();
        display.getSize(display_size);
        final int display_height = Math.min(display_size.x, display_size.y);

        /*int navigation_gap = 0;
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ) {
            final int display_width = Math.max(display_size.x, display_size.y);
            Point real_display_size = new Point();
            display.getRealSize(real_display_size);
            final int real_display_width = Math.max(real_display_size.x, real_display_size.y);
            navigation_gap = real_display_width - display_width;
            if( MyDebug.LOG ) {
                Log.d(TAG, "display_width: " + display_width);
                Log.d(TAG, "real_display_width: " + real_display_width);
                Log.d(TAG, "navigation_gap: " + navigation_gap);
            }
        }*/
        int navigation_gap = main_activity.getNavigationGap();
        test_navigation_gap = navigation_gap;
        if( MyDebug.LOG ) {
            Log.d(TAG, "navigation_gap: " + navigation_gap);
        }

        if( !popup_container_only )
        {
            // reset:
            top_icon = null;

            // we use a dummy button, so that the GUI buttons keep their positioning even if the Settings button is hidden (visibility set to View.GONE)

            int button_size = main_activity.getResources().getDimensionPixelSize(R.dimen.onscreen_button_size);
            if( ui_placement == UIPlacement.UIPLACEMENT_TOP ) {
                // need to dynamically lay out the permanent icons

                int count = 0;
                View first_visible_view = null;
                View last_visible_view = null;
                //count = 10; // test
                if( MyDebug.LOG ) {
                    Log.d(TAG, "count: " + count);
                    Log.d(TAG, "display_height: " + display_height);
                }
                if( count > 0 ) {
					/*int button_size = display_height / count;
					if( MyDebug.LOG )
						Log.d(TAG, "button_size: " + button_size);
					for(View this_view : buttons) {
						if( this_view.getVisibility() == View.VISIBLE ) {
							layoutParams = (RelativeLayout.LayoutParams)this_view.getLayoutParams();
							layoutParams.width = button_size;
							layoutParams.height = button_size;
							this_view.setLayoutParams(layoutParams);
						}
					}*/
                    int total_button_size = count*button_size;
                    int margin = 0;
                    if( total_button_size > display_height ) {
                        if( MyDebug.LOG )
                            Log.d(TAG, "need to reduce button size");
                        button_size = display_height / count;
                    }
                    else {
                        if( MyDebug.LOG )
                            Log.d(TAG, "need to increase margin");
                        if( count > 1 )
                            margin = (display_height - total_button_size) / (count-1);
                    }
                    if( MyDebug.LOG ) {
                        Log.d(TAG, "button_size: " + button_size);
                        Log.d(TAG, "total_button_size: " + total_button_size);
                        Log.d(TAG, "margin: " + margin);
                    }
                    top_icon = first_visible_view;
                }
            }

            // end icon panel




        }

        if( !popup_container_only )
        {
            // set seekbar info
            int width_dp;
            if( ui_rotation == 0 || ui_rotation == 180 ) {
                // landscape
                width_dp = 350;
            }
            else {
                // portrait
                width_dp = 250;
                // prevent being too large on smaller devices (e.g., Galaxy Nexus or smaller)
                int max_width_dp = getMaxHeightDp(true);
                if( width_dp > max_width_dp )
                    width_dp = max_width_dp;
            }
            if( MyDebug.LOG )
                Log.d(TAG, "width_dp: " + width_dp);
            int height_dp = 50;
            final float scale = main_activity.getResources().getDisplayMetrics().density;
            int width_pixels = (int) (width_dp * scale + 0.5f); // convert dps to pixels
            int height_pixels = (int) (height_dp * scale + 0.5f); // convert dps to pixels



        }
        if( !popup_container_only ) {
            setTakePhotoIcon();
            // no need to call setSwitchCameraContentDescription()
        }

        if( MyDebug.LOG ) {
            Log.d(TAG, "layoutUI: total time: " + (System.currentTimeMillis() - debug_time));
        }
    }


    /** Set icons for taking photos vs videos.
     *  Also handles content descriptions for the take photo button and switch video button.
     */
    public void setTakePhotoIcon() {
        if( MyDebug.LOG )
            Log.d(TAG, "setTakePhotoIcon()");
        if( main_activity.getPreview() != null ) {
            ImageButton view = main_activity.findViewById(R.id.take_photo);
            int resource;
            int content_description;
            int switch_video_content_description;
            if( main_activity.getPreview().isVideo() ) {
                if( MyDebug.LOG )
                    Log.d(TAG, "set icon to video");
                resource = main_activity.getPreview().isVideoRecording() ? R.drawable.ic_capture : R.drawable.ic_capture;
                content_description = main_activity.getPreview().isVideoRecording() ? R.string.stop_video : R.string.start_video;
                switch_video_content_description = R.string.switch_to_photo;
            }
            else if( main_activity.getApplicationInterface().getPhotoMode() == MyApplicationInterface.PhotoMode.Panorama &&
                    main_activity.getApplicationInterface().getGyroSensor().isRecording() ) {
                if( MyDebug.LOG )
                    Log.d(TAG, "set icon to recording panorama");
                resource = R.drawable.ic_capture;
                content_description = R.string.finish_panorama;
                    switch_video_content_description = R.string.switch_to_video;
            }
            else {
                if( MyDebug.LOG )
                    Log.d(TAG, "set icon to photo");
                resource = R.drawable.ic_capture;
                content_description = R.string.take_photo;
                switch_video_content_description = R.string.switch_to_video;
            }
            view.setImageResource(resource);
            view.setContentDescription( main_activity.getResources().getString(content_description) );
            view.setTag(resource); // for testing

            view = main_activity.findViewById(R.id.switch_video);
            view.setContentDescription( main_activity.getResources().getString(switch_video_content_description) );
            resource = main_activity.getPreview().isVideo() ? R.drawable.ic_capture : R.drawable.ic_capture;
            view.setImageResource(resource);
            view.setTag(resource); // for testing
        }
    }

    /** Set content description for switch camera button.
     */
    public void setSwitchCameraContentDescription() {
        if( MyDebug.LOG )
            Log.d(TAG, "setSwitchCameraContentDescription()");
        if( main_activity.getPreview() != null && main_activity.getPreview().canSwitchCamera() ) {
            ImageButton view = main_activity.findViewById(R.id.switch_camera);
            int content_description;
            int cameraId = main_activity.getNextCameraId();
            switch( main_activity.getPreview().getCameraControllerManager().getFacing( cameraId  ) ) {
                case FACING_FRONT:
                    content_description = R.string.switch_to_front_camera;
                    break;
                case FACING_BACK:
                    content_description = R.string.switch_to_back_camera;
                    break;
                case FACING_EXTERNAL:
                    content_description = R.string.switch_to_external_camera;
                    break;
                default:
                    content_description = R.string.switch_to_unknown_camera;
                    break;
            }
            if( MyDebug.LOG )
                Log.d(TAG, "content_description: " + main_activity.getResources().getString(content_description));
            view.setContentDescription( main_activity.getResources().getString(content_description) );
        }
    }

    /** Set content description for pause video button.
     */
    public void setPauseVideoContentDescription() {
        if (MyDebug.LOG)
            Log.d(TAG, "setPauseVideoContentDescription()");
        ImageButton pauseVideoButton = main_activity.findViewById(R.id.pause_video);
        int content_description;
        if( main_activity.getPreview().isVideoRecordingPaused() ) {
            content_description = R.string.resume_video;
            pauseVideoButton.setImageResource(R.drawable.ic_launcher_background);
        }
        else {
            content_description = R.string.pause_video;
            pauseVideoButton.setImageResource(R.drawable.ic_launcher_background);
        }
        if( MyDebug.LOG )
            Log.d(TAG, "content_description: " + main_activity.getResources().getString(content_description));
        pauseVideoButton.setContentDescription(main_activity.getResources().getString(content_description));
    }

    public UIPlacement getUIPlacement() {
        return this.ui_placement;
    }


    public void onOrientationChanged(int orientation) {
		/*if( MyDebug.LOG ) {
			Log.d(TAG, "onOrientationChanged()");
			Log.d(TAG, "orientation: " + orientation);
			Log.d(TAG, "current_orientation: " + current_orientation);
		}*/
        if( orientation == OrientationEventListener.ORIENTATION_UNKNOWN )
            return;
        int diff = Math.abs(orientation - current_orientation);
        if( diff > 180 )
            diff = 360 - diff;
        // only change orientation when sufficiently changed
        if( diff > 60 ) {
            orientation = (orientation + 45) / 90 * 90;
            orientation = orientation % 360;
            if( orientation != current_orientation ) {
                this.current_orientation = orientation;
                if( MyDebug.LOG ) {
                    Log.d(TAG, "current_orientation is now: " + current_orientation);
                }
                view_rotate_animation = true;
                layoutUI();
                view_rotate_animation = false;

                // Call DrawPreview.updateSettings() so that we reset calculations that depend on
                // getLocationOnScreen() - since the result is affected by a View's rotation, we need
                // to recompute - this also means we need to delay slightly until after the rotation
                // animation is complete.
                // To reproduce issues, rotate from upside-down-landscape to portrait, and observe
                // the info-text placement (when using icons-along-top), or with on-screen angle
                // displayed when in 16:9 preview.
                // Potentially we could use Animation.setAnimationListener(), but we set a separate
                // animation for every icon.
                // Note, this seems to be unneeded due to the fix in DrawPreview for
                // "getRotation() == 180.0f", but good to clear the cached values (e.g., in case we
                // compute them during when the icons are being rotated).
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if( MyDebug.LOG )
                            Log.d(TAG, "onOrientationChanged->postDelayed()");

                        main_activity.getApplicationInterface().getDrawPreview().updateSettings();
                    }
                }, view_rotate_animation_duration+20);
            }
        }
    }

    public boolean showExposureLockIcon() {
        if( !main_activity.getPreview().supportsExposureLock() )
            return false;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        return sharedPreferences.getBoolean(PreferenceKeys.ShowExposureLockPreferenceKey, true);
    }

    public boolean showWhiteBalanceLockIcon() {
        if( !main_activity.getPreview().supportsWhiteBalanceLock() )
            return false;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        return sharedPreferences.getBoolean(PreferenceKeys.ShowWhiteBalanceLockPreferenceKey, false);
    }

    public boolean showCycleRawIcon() {
        if( !main_activity.getPreview().supportsRaw() )
            return false;
        if( !main_activity.getApplicationInterface().isRawAllowed(main_activity.getApplicationInterface().getPhotoMode()) )
            return false;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        return sharedPreferences.getBoolean(PreferenceKeys.ShowCycleRawPreferenceKey, false);
    }

    public boolean showStoreLocationIcon() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        return sharedPreferences.getBoolean(PreferenceKeys.ShowStoreLocationPreferenceKey, false);
    }

    public boolean showTextStampIcon() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        return sharedPreferences.getBoolean(PreferenceKeys.ShowTextStampPreferenceKey, false);
    }

    public boolean showStampIcon() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        return sharedPreferences.getBoolean(PreferenceKeys.ShowStampPreferenceKey, false);
    }

    public boolean showAutoLevelIcon() {
        if( !main_activity.supportsAutoStabilise() )
            return false;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        return sharedPreferences.getBoolean(PreferenceKeys.ShowAutoLevelPreferenceKey, false);
    }

    public boolean showCycleFlashIcon() {
        if( !main_activity.getPreview().supportsFlash() )
            return false;
        if( main_activity.getPreview().isVideo() )
            return false; // no point showing flash icon in video mode, as we only allow flash auto and flash torch, and we don't support torch on the on-screen cycle flash icon
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        return sharedPreferences.getBoolean(PreferenceKeys.ShowCycleFlashPreferenceKey, false);
    }

    public boolean showFaceDetectionIcon() {
        if( !main_activity.getPreview().supportsFaceDetection() )
            return false;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        return sharedPreferences.getBoolean(PreferenceKeys.ShowFaceDetectionPreferenceKey, false);
    }

    public void setImmersiveMode(final boolean immersive_mode) {
        if( MyDebug.LOG )
            Log.d(TAG, "setImmersiveMode: " + immersive_mode);
        this.immersive_mode = immersive_mode;
        main_activity.runOnUiThread(new Runnable() {
            public void run() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
                // if going into immersive mode, the we should set GONE the ones that are set GONE in showGUI(false)
                //final int visibility_gone = immersive_mode ? View.GONE : View.VISIBLE;
                final int visibility = immersive_mode ? View.GONE : View.VISIBLE;
                if( MyDebug.LOG )
                    Log.d(TAG, "setImmersiveMode: set visibility: " + visibility);
                // n.b., don't hide share and trash buttons, as they require immediate user input for us to continue
                View switchCameraButton = main_activity.findViewById(R.id.switch_camera);
                View switchVideoButton = main_activity.findViewById(R.id.switch_video);
                if( main_activity.getPreview().getCameraControllerManager().getNumberOfCameras() > 1 )
                    switchCameraButton.setVisibility(visibility);
                switchVideoButton.setVisibility(visibility);
                String pref_immersive_mode = sharedPreferences.getString(PreferenceKeys.ImmersiveModePreferenceKey, "immersive_mode_low_profile");
                if( pref_immersive_mode.equals("immersive_mode_everything") ) {
                    if( sharedPreferences.getBoolean(PreferenceKeys.ShowTakePhotoPreferenceKey, true) ) {
                        View takePhotoButton = main_activity.findViewById(R.id.take_photo);
                        takePhotoButton.setVisibility(visibility);
                    }
                    if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && main_activity.getPreview().isVideoRecording() ) {
                        View pauseVideoButton = main_activity.findViewById(R.id.pause_video);
                        pauseVideoButton.setVisibility(visibility);
                    }
                }
                if( !immersive_mode ) {
                    // make sure the GUI is set up as expected
                    showGUI();
                }
            }
        });
    }

    public boolean inImmersiveMode() {
        return immersive_mode;
    }

    public void showGUI(final boolean show, final boolean is_video) {
        if( MyDebug.LOG ) {
            Log.d(TAG, "showGUI: " + show);
            Log.d(TAG, "is_video: " + is_video);
        }
        if( is_video )
            this.show_gui_video = show;
        else
            this.show_gui_photo = show;
        showGUI();
    }

    public void showGUI() {
        if( MyDebug.LOG ) {
            Log.d(TAG, "showGUI");
            Log.d(TAG, "show_gui_photo: " + show_gui_photo);
            Log.d(TAG, "show_gui_video: " + show_gui_video);
        }
        if( inImmersiveMode() )
            return;
        if( (show_gui_photo || show_gui_video) && main_activity.usingKitKatImmersiveMode() ) {
            // call to reset the timer
            main_activity.initImmersiveMode();
        }
        main_activity.runOnUiThread(new Runnable() {
            public void run() {
                final boolean is_panorama_recording = main_activity.getApplicationInterface().getGyroSensor().isRecording();
                final int visibility = is_panorama_recording ? View.GONE : (show_gui_photo && show_gui_video) ? View.VISIBLE : View.GONE; // for UI that is hidden while taking photo or video
                final int visibility_video = is_panorama_recording ? View.GONE : show_gui_photo ? View.VISIBLE : View.GONE; // for UI that is only hidden while taking photo
                View switchCameraButton = main_activity.findViewById(R.id.switch_camera);
                View switchVideoButton = main_activity.findViewById(R.id.switch_video);
                if( main_activity.getPreview().getCameraControllerManager().getNumberOfCameras() > 1 )
                    switchCameraButton.setVisibility(visibility);
                switchVideoButton.setVisibility(visibility);
                if( !(show_gui_photo && show_gui_video) ) {
                    closePopup(); // we still allow the popup when recording video, but need to update the UI (so it only shows flash options), so easiest to just close
                }
                if( show_gui_photo && show_gui_video ) {
                    layoutUI(); // needed for "top" UIPlacement, to auto-arrange the buttons
                }
            }
        });
    }


    /**
     * Opens or close the exposure settings (ISO, white balance, etc)
     */
    public void toggleExposureUI() {
        closePopup();

        if( main_activity.getPreview().getCameraController() != null ) {
            setupExposureUI();
        }
    }



    private void highlightExposureUILine(boolean selectNext) {

        mExposureLine = ( mExposureLine  + 5 ) % 5;
    }

    private void nextExposureUILine() {
        mExposureLine++;
        highlightExposureUILine(true);
    }

    private void previousExposureUILine() {
        mExposureLine--;
        highlightExposureUILine(false);
    }

    /**
     * Our order for lines is:
     *  -0: ISO buttons
     *  -1: ISO slider
     *  -2: Shutter speed
     *  -3: exposure seek bar
     */
    private void nextExposureUIItem() {
        if( MyDebug.LOG )
            Log.d(TAG, "nextExposureUIItem");
        switch (mExposureLine) {
            case 0:
                nextIsoItem(false);
                break;
        }
    }

    private void nextIsoItem(boolean previous) {
        if( MyDebug.LOG )
            Log.d(TAG, "nextIsoItem: " + previous);
        // Find current ISO
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        String current_iso = sharedPreferences.getString(PreferenceKeys.ISOPreferenceKey, CameraController.ISO_DEFAULT);
        int count = iso_buttons.size();
        int step = previous ? -1 : 1;
        boolean found = false;
        for(int i = 0; i < count; i++) {
            Button button= (Button) iso_buttons.get(i);
            String button_text = "" + button.getText();
            if( button_text.contains(current_iso) ) {
                found = true;
                // Select next one, unless it's "Manual", which we skip since
                // it's not practical in remote mode.
                Button nextButton = (Button) iso_buttons.get((i + count + step)%count);
                String nextButton_text = "" + nextButton.getText();
                if (nextButton_text.contains("m")) {
                    nextButton = (Button) iso_buttons.get((i+count+ 2*step)%count);
                }
                nextButton.callOnClick();
                break;
            }
        }
        if (!found) {
            // For instance, we are in ISO manual mode and "M" is selected. default
            // back to "Auto" to avoid being stuck since we're with a remote control
            iso_buttons.get(0).callOnClick();
        }
    }

    /**
     * Select element on exposure UI. Based on the value of mExposureLine
     *         // Our order for lines is:
     *         // - ISO buttons
     *         // - ISO slider
     *         // - Shutter speed
     *         // - exposure seek bar
     */
    private void selectExposureUILine() {


        if (mExposureLine == 0) { // ISO presets
            //iso_buttons_container.setAlpha(1f);
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
            String current_iso = sharedPreferences.getString(PreferenceKeys.ISOPreferenceKey, CameraController.ISO_DEFAULT);
            // if the manual ISO value isn't one of the "preset" values, then instead highlight the manual ISO icon
            boolean found = false;
            Button manualButton = null;
            for(View view : iso_buttons) {
                Button button = (Button)view;
                String button_text = "" + button.getText();
                if( button_text.contains(current_iso) ) {
                  PopupView.setButtonSelected(button, true);
                    //button.setBackgroundColor(highlightColorExposureUIElement);
                    //button.setAlpha(0.3f);
                    found = true;
                }
                else {
                    if (button_text.contains("m")) {
                        manualButton = button;
                    }
                PopupView.setButtonSelected(button, false);
                    button.setBackgroundColor(Color.TRANSPARENT);
                }
            }
            if (!found && manualButton != null) {
                // We are in manual ISO, highlight the "M" button
             PopupView.setButtonSelected(manualButton, true);
                manualButton.setBackgroundColor(highlightColorExposureUIElement);
                //manualButton.setAlpha(0.3f);
            }
            mSelectingExposureUIElement = true;
        } else if (mExposureLine == 1) {
            mSelectingExposureUIElement = true;
        } else if (mExposureLine == 2) {
            // ISO seek bar - change color
            mSelectingExposureUIElement = true;
        } else if (mExposureLine == 3) {
            // Exposure compensation
            mSelectingExposureUIElement = true;
        } else if (mExposureLine == 4) {
            // Manual white balance
            mSelectingExposureUIElement = true;
        }
    }

    /** Returns the height of the device in dp (or width in portrait mode), allowing for space for the
     *  on-screen UI icons.
     * @param centred If true, then find the max height for a view that will be centred.
     */
    int getMaxHeightDp(boolean centred) {
        Display display = main_activity.getWindowManager().getDefaultDisplay();
        // ensure we have display for landscape orientation (even if we ever allow Open Camera
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        // normally we should always have heightPixels < widthPixels, but good not to assume we're running in landscape orientation
        int smaller_dim = Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
        // the smaller dimension should limit the width, due to when held in portrait
        final float scale = main_activity.getResources().getDisplayMetrics().density;
        int dpHeight = (int)(smaller_dim / scale);
        if( MyDebug.LOG ) {
            Log.d(TAG, "display size: " + outMetrics.widthPixels + " x " + outMetrics.heightPixels);
            Log.d(TAG, "dpHeight: " + dpHeight);
        }
        // allow space for the icons at top/right of screen
        int margin = centred ? 120 : 50;
        dpHeight -= margin;
        return dpHeight;
    }

    public boolean isSelectingExposureUIElement() {
        if( MyDebug.LOG )
            Log.d(TAG, "isSelectingExposureUIElement returns:" + mSelectingExposureUIElement);
        return mSelectingExposureUIElement;
    }


    /**
     * Process a press to the "Up" button on a remote. Called from MainActivity.
     * @return true if an action was taken
     */
    public boolean processRemoteUpButton() {
        if( MyDebug.LOG )
            Log.d(TAG, "processRemoteUpButton");
        boolean didProcess = false;
        if (popupIsOpen()) {
            didProcess = true;
            if (selectingIcons()) {
                previousPopupIcon();
            } else if (selectingLines()) {
                previousPopupLine();
            }
        }
        return didProcess;
    }

    public boolean processRemoteDownButton() {
        if( MyDebug.LOG )
            Log.d(TAG, "processRemoteDownButton");
        boolean didProcess = false;
        if (popupIsOpen()) {
            if (selectingIcons()) {
                nextPopupIcon();
            } else if (selectingLines()) {
                nextPopupLine();
            }
            didProcess = true;
        }
        return didProcess;
    }

    private List<View> iso_buttons;
    private final static String manual_iso_value = "m";

    /** Opens the exposure UI if not already open, and sets up or updates the UI.
     */
    public void setupExposureUI() {
        if( MyDebug.LOG )
            Log.d(TAG, "setupExposureUI");
        test_ui_buttons.clear();
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        final Preview preview = main_activity.getPreview();
        if( preview.supportsISORange() ) {
            if( MyDebug.LOG )
                Log.d(TAG, "supports ISO range");
            int min_iso = preview.getMinimumISO();
            int max_iso = preview.getMaximumISO();
            List<String> values = new ArrayList<>();
            values.add(CameraController.ISO_DEFAULT);
            values.add(manual_iso_value);
            int [] iso_values = {50, 100, 200, 400, 800, 1600, 3200, 6400};
            values.add("" + min_iso);
            for(int iso_value : iso_values) {
                if( iso_value > min_iso && iso_value < max_iso ) {
                    values.add("" + iso_value);
                }
            }
            values.add("" + max_iso);
        }
    }

    public void closePopup() {
        if( popupIsOpen() ) {
            clearSelectionState();

            popup_view_is_open = false;
            if( cache_popup && !force_destroy_popup ) {
                popup_view.setVisibility(View.GONE);
            }
            else {
                destroyPopup();
            }
            main_activity.initImmersiveMode(); // to reset the timer when closing the popup
        }
    }

    public boolean popupIsOpen() {
        return popup_view_is_open;
    }

    public boolean selectingIcons() {
        return mSelectingIcons;
    }

    public boolean selectingLines() {
        return mSelectingLines;
    }

    public void destroyPopup() {
        if( MyDebug.LOG )
            Log.d(TAG, "destroyPopup");
        force_destroy_popup = false;
        if( popupIsOpen() ) {
            closePopup();
        }
        popup_view = null;
    }


    private void highlightPopupLine(boolean highlight, boolean goUp) {

        if (!popupIsOpen()) { // Safety check
            clearSelectionState();
        }
    }

    private void highlightPopupIcon(boolean highlight, boolean goLeft) {
        if( MyDebug.LOG ) {
            Log.d(TAG, "highlightPopupIcon");
            Log.d(TAG, "highlight: " + highlight);
            Log.d(TAG, "goLeft: " + goLeft);
        }
        if (!popupIsOpen()) { // Safety check
            clearSelectionState();
            return;
        }
        highlightPopupLine(false, false);
        int count = mHighlightedLine.getChildCount();
        boolean foundIcon = false;
        while (!foundIcon) {
            // Ensure we stay within our bounds:
            // (careful, modulo in Java will allow negative numbers, hence the line below:
            mPopupIcon= (mPopupIcon + count ) % count;
            View v = mHighlightedLine.getChildAt(mPopupIcon);
            if( MyDebug.LOG )
                Log.d(TAG, "row: " + mPopupIcon + " view: " + v);
            if (v instanceof ImageButton || v instanceof Button ) {
                if (highlight) {
                    v.setBackgroundColor(highlightColor);
                    //v.setAlpha(0.5f);
                    mHighlightedIcon = v;
                    mSelectingIcons = true;
                } else {
                    v.setBackgroundColor(Color.TRANSPARENT);
                }
                if( MyDebug.LOG )
                    Log.d(TAG, "found icon at row: " + mPopupIcon);
                foundIcon = true;
            } else {
                mPopupIcon+= goLeft ? -1 : 1;
            }
        }
    }

    /**
     * Select the next line on the settings popup. Called by MainActivity
     * when receiving a remote control command.
     */
    private void nextPopupLine() {
        highlightPopupLine(false, false);
        mPopupLine++;
        highlightPopupLine(true, false);
    }

    private void previousPopupLine() {
        highlightPopupLine(false, true);
        mPopupLine--;
        highlightPopupLine(true, true);
    }

    private void nextPopupIcon() {
        highlightPopupIcon(false, false);
        mPopupIcon++;
        highlightPopupIcon(true, false);
    }

    private void previousPopupIcon() {
        highlightPopupIcon(false, true);
        mPopupIcon--;
        highlightPopupIcon(true, true);
    }

    /**
     * Simulates a press on the currently selected icon
     */
    private void clickSelectedIcon() {
        if( MyDebug.LOG )
            Log.d(TAG, "clickSelectedIcon: " + mHighlightedIcon);
        if (mHighlightedIcon != null) {
            mHighlightedIcon.callOnClick();
        }
    }

    /**
     * Ensure all our selection tracking variables are cleared when we
     * exit menu selection (used in remote control mode)
     */
    private void clearSelectionState() {
        if( MyDebug.LOG )
            Log.d(TAG, "clearSelectionState");
        mPopupLine = 0;
        mPopupIcon = 0;
        mSelectingIcons = false;
        mSelectingLines = false;
        mHighlightedIcon= null;
        mHighlightedLine = null;
    }

    public void togglePopupSettings() {
        if( popupIsOpen() ) {
            closePopup();
            return;
        }
        if( main_activity.getPreview().getCameraController() == null ) {
            if( MyDebug.LOG )
                Log.d(TAG, "camera not opened!");
            return;
        }

        if( MyDebug.LOG )
            Log.d(TAG, "open popup");

        main_activity.getPreview().cancelTimer(); // best to cancel any timer, in case we take a photo while settings window is open, or when changing settings
        main_activity.stopAudioListeners();

        final long time_s = System.currentTimeMillis();

        if( popup_view == null ) {
            if( MyDebug.LOG )
                Log.d(TAG, "create new popup_view");
            test_ui_buttons.clear();
            popup_view = new PopupView(main_activity);
        }
        else {
            if( MyDebug.LOG )
                Log.d(TAG, "use cached popup_view");
            popup_view.setVisibility(View.VISIBLE);
        }
        popup_view_is_open = true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( MyDebug.LOG )
            Log.d(TAG, "onKeyDown: " + keyCode);
        switch( keyCode ) {
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS: // media codes are for "selfie sticks" buttons
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_MEDIA_STOP:
            {
                if( keyCode == KeyEvent.KEYCODE_VOLUME_UP )
                    keydown_volume_up = true;
                else if( keyCode == KeyEvent.KEYCODE_VOLUME_DOWN )
                    keydown_volume_down = true;

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
                String volume_keys = sharedPreferences.getString(PreferenceKeys.VolumeKeysPreferenceKey, "volume_take_photo");

                if((keyCode==KeyEvent.KEYCODE_MEDIA_PREVIOUS
                        ||keyCode==KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                        ||keyCode==KeyEvent.KEYCODE_MEDIA_STOP)
                        &&!(volume_keys.equals("volume_take_photo"))) {
                    AudioManager audioManager = (AudioManager) main_activity.getSystemService(Context.AUDIO_SERVICE);
                    if(audioManager==null) break;
                    if(!audioManager.isWiredHeadsetOn()) break; // isWiredHeadsetOn() is deprecated, but comment says "Use only to check is a headset is connected or not."
                }

                switch(volume_keys) {
                    case "volume_take_photo":
                        main_activity.takePicture(false);
                        return true;
                    case "volume_focus":
                        if(keydown_volume_up && keydown_volume_down) {
                            if (MyDebug.LOG)
                                Log.d(TAG, "take photo rather than focus, as both volume keys are down");
                            main_activity.takePicture(false);
                        }
                        else {
                            // important not to repeatedly request focus, even though main_activity.getPreview().requestAutoFocus() will cancel, as causes problem if key is held down (e.g., flash gets stuck on)
                            // also check DownTime vs EventTime to prevent repeated focusing whilst the key is held down
                            if(event.getDownTime() == event.getEventTime() && !main_activity.getPreview().isFocusWaiting()) {
                                if(MyDebug.LOG)
                                    Log.d(TAG, "request focus due to volume key");
                                main_activity.getPreview().requestAutoFocus();
                            }
                        }
                        return true;
                    case "volume_zoom":
                        return true;
                    case "volume_auto_stabilise":
                        if( main_activity.supportsAutoStabilise() ) {
                            boolean auto_stabilise = sharedPreferences.getBoolean(PreferenceKeys.AutoStabilisePreferenceKey, false);
                            auto_stabilise = !auto_stabilise;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(PreferenceKeys.AutoStabilisePreferenceKey, auto_stabilise);
                            editor.apply();
                            String message = main_activity.getResources().getString(R.string.preference_auto_stabilise) + ": " + main_activity.getResources().getString(auto_stabilise ? R.string.on : R.string.off);
                            main_activity.getPreview().showToast(main_activity.getChangedAutoStabiliseToastBoxer(), message);
                            main_activity.getApplicationInterface().getDrawPreview().updateSettings(); // because we cache the auto-stabilise setting
                            this.destroyPopup(); // need to recreate popup in order to update the auto-level checkbox
                        }
                        else {
                            main_activity.getPreview().showToast(main_activity.getChangedAutoStabiliseToastBoxer(), R.string.auto_stabilise_not_supported);
                        }
                        return true;
                    case "volume_really_nothing":
                        // do nothing, but still return true so we don't change volume either
                        return true;
                }
                // else do nothing here, but still allow changing of volume (i.e., the default behaviour)
                break;
            }
            case KeyEvent.KEYCODE_MENU:
            {
                // needed to support hardware menu button
                // tested successfully on Samsung S3 (via RTL)
                // see http://stackoverflow.com/questions/8264611/how-to-detect-when-user-presses-menu-key-on-their-android-device
                main_activity.openSettings();
                return true;
            }
            case KeyEvent.KEYCODE_CAMERA:
            {
                if( event.getRepeatCount() == 0 ) {
                    main_activity.takePicture(false);
                    return true;
                }
            }
            case KeyEvent.KEYCODE_FOCUS:
            {
                // important not to repeatedly request focus, even though main_activity.getPreview().requestAutoFocus() will cancel - causes problem with hardware camera key where a half-press means to focus
                // also check DownTime vs EventTime to prevent repeated focusing whilst the key is held down - see https://sourceforge.net/p/opencamera/tickets/174/ ,
                // or same issue above for volume key focus
                if( event.getDownTime() == event.getEventTime() && !main_activity.getPreview().isFocusWaiting() ) {
                    if( MyDebug.LOG )
                        Log.d(TAG, "request focus due to focus key");
                    main_activity.getPreview().requestAutoFocus();
                }
                return true;
            }
            case KeyEvent.KEYCODE_ZOOM_IN:
            case KeyEvent.KEYCODE_PLUS:
            case KeyEvent.KEYCODE_NUMPAD_ADD:
            {
                return true;
            }
            case KeyEvent.KEYCODE_ZOOM_OUT:
            case KeyEvent.KEYCODE_MINUS:
            case KeyEvent.KEYCODE_NUMPAD_SUBTRACT:
            {
                return true;
            }
            case KeyEvent.KEYCODE_SPACE:
            case KeyEvent.KEYCODE_NUMPAD_5:
            {
                if( popupIsOpen() && remote_control_mode ) {
                    commandMenuPopup();
                    return true;
                }
                else if( event.getRepeatCount() == 0 ) {
                    main_activity.takePicture(false);
                    return true;
                }
                break;
            }
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_NUMPAD_8:
                //case KeyEvent.KEYCODE_VOLUME_UP: // test
                if( processRemoteUpButton() )
                    return true;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_NUMPAD_2:
                //case KeyEvent.KEYCODE_VOLUME_DOWN: // test
                if( processRemoteDownButton() )
                    return true;
                break;
            case KeyEvent.KEYCODE_FUNCTION:
            case KeyEvent.KEYCODE_NUMPAD_MULTIPLY:
                togglePopupSettings();
                break;
            case KeyEvent.KEYCODE_SLASH:
            case KeyEvent.KEYCODE_NUMPAD_DIVIDE:
                toggleExposureUI();
                break;
        }
        return false;
    }

    public void onKeyUp(int keyCode, KeyEvent event) {
        if( MyDebug.LOG )
            Log.d(TAG, "onKeyUp: " + keyCode);
        if( keyCode == KeyEvent.KEYCODE_VOLUME_UP )
            keydown_volume_up = false;
        else if( keyCode == KeyEvent.KEYCODE_VOLUME_DOWN )
            keydown_volume_down = false;
    }


    public void commandMenuPopup() {
        if( MyDebug.LOG )
            Log.d(TAG, "commandMenuPopup");
        if( popupIsOpen() ) {
            if( selectingIcons() ) {
                clickSelectedIcon();
            }
            else {
                highlightPopupIcon(true, false);
            }
        }
    }

    public AlertDialog showInfoDialog(int title_id, int info_id, final String info_preference_key) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(main_activity);
        alertDialog.setTitle(title_id);
        if( info_id != 0 )
            alertDialog.setMessage(info_id);
        alertDialog.setPositiveButton(android.R.string.ok, null);
        alertDialog.setNegativeButton(R.string.dont_show_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if( MyDebug.LOG )
                    Log.d(TAG, "user clicked dont_show_again for info dialog");
                final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(info_preference_key, true);
                editor.apply();
            }
        });

        main_activity.setWindowFlagsForSettings(false); // set set_lock_protect to false, otherwise if screen is locked, user will need to unlock to see the info dialog!

        AlertDialog alert = alertDialog.create();
        // AlertDialog.Builder.setOnDismissListener() requires API level 17, so do it this way instead
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface arg0) {
                if( MyDebug.LOG )
                    Log.d(TAG, "info dialog dismissed");
                main_activity.setWindowFlagsForCamera();
            }
        });
        main_activity.showAlert(alert);
        return alert;
    }

    /** Returns a (possibly translated) user readable string for a white balance preference value.
     *  If the value is not recognised (this can happen for the old Camera API, some devices can
     *  have device-specific options), then the received value is returned.
     */
    public String getEntryForWhiteBalance(String value) {
        int id = -1;
        switch( value ) {
            case CameraController.WHITE_BALANCE_DEFAULT:
                id = R.string.white_balance_auto;
                break;
            case "cloudy-daylight":
                id = R.string.white_balance_cloudy;
                break;
            case "daylight":
                id = R.string.white_balance_daylight;
                break;
            case "fluorescent":
                id = R.string.white_balance_fluorescent;
                break;
            case "incandescent":
                id = R.string.white_balance_incandescent;
                break;
            case "shade":
                id = R.string.white_balance_shade;
                break;
            case "twilight":
                id = R.string.white_balance_twilight;
                break;
            case "warm-fluorescent":
                id = R.string.white_balance_warm;
                break;
            case "manual":
                id = R.string.white_balance_manual;
                break;
            default:
                break;
        }
        String entry;
        if( id != -1 ) {
            entry = main_activity.getResources().getString(id);
        }
        else {
            entry = value;
        }
        return entry;
    }

    /** Returns a (possibly translated) user readable string for a scene mode preference value.
     *  If the value is not recognised (this can happen for the old Camera API, some devices can
     *  have device-specific options), then the received value is returned.
     */
    public String getEntryForSceneMode(String value) {
        int id = -1;
        switch( value ) {
            case "action":
                id = R.string.scene_mode_action;
                break;
            case "barcode":
                id = R.string.scene_mode_barcode;
                break;
            case "beach":
                id = R.string.scene_mode_beach;
                break;
            case "candlelight":
                id = R.string.scene_mode_candlelight;
                break;
            case CameraController.SCENE_MODE_DEFAULT:
                id = R.string.scene_mode_auto;
                break;
            case "fireworks":
                id = R.string.scene_mode_fireworks;
                break;
            case "landscape":
                id = R.string.scene_mode_landscape;
                break;
            case "night":
                id = R.string.scene_mode_night;
                break;
            case "night-portrait":
                id = R.string.scene_mode_night_portrait;
                break;
            case "party":
                id = R.string.scene_mode_party;
                break;
            case "portrait":
                id = R.string.scene_mode_portrait;
                break;
            case "snow":
                id = R.string.scene_mode_snow;
                break;
            case "sports":
                id = R.string.scene_mode_sports;
                break;
            case "steadyphoto":
                id = R.string.scene_mode_steady_photo;
                break;
            case "sunset":
                id = R.string.scene_mode_sunset;
                break;
            case "theatre":
                id = R.string.scene_mode_theatre;
                break;
            default:
                break;
        }
        String entry;
        if( id != -1 ) {
            entry = main_activity.getResources().getString(id);
        }
        else {
            entry = value;
        }
        return entry;
    }

    /** Returns a (possibly translated) user readable string for a color effect preference value.
     *  If the value is not recognised (this can happen for the old Camera API, some devices can
     *  have device-specific options), then the received value is returned.
     */
    public String getEntryForColorEffect(String value) {
        int id = -1;
        switch( value ) {
            case "aqua":
                id = R.string.color_effect_aqua;
                break;
            case "blackboard":
                id = R.string.color_effect_blackboard;
                break;
            case "mono":
                id = R.string.color_effect_mono;
                break;
            case "negative":
                id = R.string.color_effect_negative;
                break;
            case CameraController.COLOR_EFFECT_DEFAULT:
                id = R.string.color_effect_none;
                break;
            case "posterize":
                id = R.string.color_effect_posterize;
                break;
            case "sepia":
                id = R.string.color_effect_sepia;
                break;
            case "solarize":
                id = R.string.color_effect_solarize;
                break;
            case "whiteboard":
                id = R.string.color_effect_whiteboard;
                break;
            default:
                break;
        }
        String entry;
        if( id != -1 ) {
            entry = main_activity.getResources().getString(id);
        }
        else {
            entry = value;
        }
        return entry;
    }

    /** Returns a (possibly translated) user readable string for an antibanding preference value.
     *  If the value is not recognised, then the received value is returned.
     */
    public String getEntryForAntiBanding(String value) {
        int id = -1;
        switch( value ) {
            case CameraController.ANTIBANDING_DEFAULT:
                id = R.string.anti_banding_auto;
                break;
            case "50hz":
                id = R.string.anti_banding_50hz;
                break;
            case "60hz":
                id = R.string.anti_banding_60hz;
                break;
            case "off":
                id = R.string.anti_banding_off;
                break;
            default:
                break;
        }
        String entry;
        if( id != -1 ) {
            entry = main_activity.getResources().getString(id);
        }
        else {
            entry = value;
        }
        return entry;
    }

    /** Returns a (possibly translated) user readable string for an noise reduction mode preference value.
     *  If the value is not recognised, then the received value is returned.
     *  Also used for edge mode.
     */
    public String getEntryForNoiseReductionMode(String value) {
        int id = -1;
        switch( value ) {
            case CameraController.NOISE_REDUCTION_MODE_DEFAULT:
                id = R.string.noise_reduction_mode_default;
                break;
            case "off":
                id = R.string.noise_reduction_mode_off;
                break;
            case "minimal":
                id = R.string.noise_reduction_mode_minimal;
                break;
            case "fast":
                id = R.string.noise_reduction_mode_fast;
                break;
            case "high_quality":
                id = R.string.noise_reduction_mode_high_quality;
                break;
            default:
                break;
        }
        String entry;
        if( id != -1 ) {
            entry = main_activity.getResources().getString(id);
        }
        else {
            entry = value;
        }
        return entry;
    }

    View getTopIcon() {
        return this.top_icon;
    }

    // for testing
    public View getUIButton(String key) {
        if( MyDebug.LOG ) {
            Log.d(TAG, "getPopupButton(" + key + "): " + test_ui_buttons.get(key));
            Log.d(TAG, "this: " + this);
            Log.d(TAG, "popup_buttons: " + test_ui_buttons);
        }
        return test_ui_buttons.get(key);
    }

    Map<String, View> getTestUIButtonsMap() {
        return test_ui_buttons;
    }

    public PopupView getPopupView() {
        return popup_view;
    }

    public boolean testGetRemoteControlMode() {
        return remote_control_mode;
    }

    public int testGetPopupLine() {
        return mPopupLine;
    }

    public int testGetPopupIcon() {
        return mPopupIcon;
    }

    public int testGetExposureLine() {
        return mExposureLine;
    }
}
