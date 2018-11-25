package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    private static final int CAMERA_CAPTURE_IMAGE_PREVIEW = 3;
    private static final String IMAGE_FILE = "IMAGE_FILE";
    private TextView textViewProfileName;
    private String DEFAULT = "NULL";
    private String mCurrentPhotoPath;
    private Button buttonTakeProfilePic;
    private ImageView imageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        /*
            PART A - Add profile name.
         */

        textViewProfileName = (TextView) findViewById(R.id.textViewProfileName);

        SharedPreferences sharedPreferences = getSharedPreferences("UserRegistrationData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", DEFAULT);

        textViewProfileName.setText("Hello, " + name + "!");

        /*
            PART B - Add profile picture.
         */

        buttonTakeProfilePic = (Button) findViewById(R.id.buttonTakeProfilePic);
        imageProfile = findViewById(R.id.imgProfile);

        buttonTakeProfilePic.setOnClickListener((v) -> {
            dispatchTakePictureIntent(CAMERA_CAPTURE_IMAGE_PREVIEW);
        });
    }

    private void dispatchTakePictureIntent(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("ex", "cannot create file");

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    /**
     * Receiving activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            previewCapturedImage();
        } else if (resultCode == RESULT_CANCELED) {
            // user cancelled Image capture
            Toast.makeText(this, "User cancelled the capture", Toast.LENGTH_SHORT).show();
        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            imageProfile.setVisibility(View.VISIBLE);

            Toast.makeText(this, mCurrentPhotoPath, Toast.LENGTH_LONG).show();
            Log.d("preview", mCurrentPhotoPath);

            final Bitmap bitmap = CameraUtils.scaleDownAndRotatePic(mCurrentPhotoPath);
            imageProfile.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // Save the image file location
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(IMAGE_FILE, mCurrentPhotoPath);
    }

    // Retrieve the image file location
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mCurrentPhotoPath = savedInstanceState.getString(IMAGE_FILE);
        previewCapturedImage();
    }
}
