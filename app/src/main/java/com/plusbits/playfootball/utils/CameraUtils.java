package com.plusbits.playfootball.utils;

import android.app.Activity;

import com.nguyenhoanglam.imagepicker.activity.ImagePicker;

/**
 * Created by mberengueras on 05/10/2016.
 */

public class CameraUtils {

    public static void openCameraGalleryPicker(Activity activity, int activityResult){
        ImagePicker.create(activity)
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .start(activityResult); // start image picker activity with request code
    }
}
