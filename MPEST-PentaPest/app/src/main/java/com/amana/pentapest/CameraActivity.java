package com.amana.pentapest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amana.pentapest.utils.PermissionUtils;
import com.amana.pentapest.utils.Utils;
import com.retropicker.FileUtils;
import com.retropicker.callback.CallbackPicker;
import com.retropicker.config.Retropicker;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class CameraActivity extends AppCompatActivity {

    private ImageView imageView;
    private Retropicker retropicker;
    Context mContext;
    Uri imageUri;
    String picturePath;
    private final int CAMERA_ACTIVITY_RESULT_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        imageView = (ImageView) findViewById(R.id.imageView);

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
    }

    public void btActionImage(View view) {

       /* Retropicker.Builder builder =  new Retropicker.Builder(this)
                .setTypeAction(Retropicker.CAMERA_PICKER)
                .setImageName("first_image.jpg")
                .checkPermission(true);


        builder.enquee(new CallbackPicker() {
            @Override
            public void onSuccess(Bitmap bitmap, String imagePath) {
               // imageView.setImageBitmap(bitmap);
                Log.e("TAG", "File Path: " + imagePath);

                File imgFile = new File(imagePath);
                if(imgFile.exists()){

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    imageView.setImageBitmap(myBitmap);

                }
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "error: " + error.getMessage());
                Log.e("TAG", "error toString: " + error.toString());
            }
        });

        retropicker = builder.create();
        retropicker.open();*/

        if (PermissionUtils.isPermissionGranted(getApplicationContext(), Manifest.permission.CAMERA) && PermissionUtils.isPermissionGranted(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            launchCamera();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionUtils.PERMISSION_REQUEST_CODE_GALLERY);
            }
        }


    }


    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

       /* Uri uri = getImageUri();
        picturePath = uri.getPath();
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);*/


        imageUri = getImageUri();
        picturePath = imageUri.getPath();
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_ACTIVITY_RESULT_CODE);
    };

    private Uri getImageUri() {
        // Store image in Kaakateeya folder
        Uri cameraImgUri = null;
        try {
            //String path = Environment.getExternalStorageDirectory() + "/Kaakateeya/pics";
            //String path = context.getFilesDir()
            String path = Environment.getExternalStorageDirectory()
                    + "/Android/data/" + mContext.getPackageName() + "/profile/IMG_" + Calendar.getInstance().getTimeInMillis();

           // String path = Environment.getExternalStorageDirectory() + "/Solutions/pics/";
           String fileName = ".jpg";

            path = path + fileName;
            File actualImageFile = new File(path);
            cameraImgUri = Uri.fromFile(actualImageFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cameraImgUri;
    }


    public void openGalery(View view) {


      //  Utils.deleteFolder(mContext);

        Retropicker.Builder builder = new Retropicker.Builder(this)
                .setTypeAction(Retropicker.GALLERY_PICKER)
                .setImageName("first_image.jpg")
                .checkPermission(true);


        builder.enquee(new CallbackPicker() {
            @Override
            public void onSuccess(Bitmap bitmap, String imagePath) {
                // imageView.setImageBitmap(bitmap);
                Log.e("TAG", "File Path: " + imagePath);


                final String path = FileUtils.getPath(mContext, Uri.parse(imagePath));

                File imgFile = new File(path);
                if (imgFile.exists()) {

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    imageView.setImageBitmap(myBitmap);

                } else {
                    Log.e("TAG", "File Path not Found");

                }
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "error: " + error.getMessage());
                Log.e("TAG", "error toString: " + error.toString());
            }
        });

        retropicker = builder.create();
        retropicker.open();
    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        retropicker.onRequesPermissionResult(requestCode, permissions, grantResults);

    }
*/


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case PermissionUtils.PERMISSION_REQUEST_CODE_CAMERA: {
                String permissionOne = permissions[0];
                int grantResultOne = grantResults[0];

                String permissionTwo = permissions[1];
                int grantResultTwo = grantResults[1];

                if (permissionOne.equals(Manifest.permission.CAMERA) && permissionTwo.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResultOne == PackageManager.PERMISSION_GRANTED && grantResultTwo == PackageManager.PERMISSION_GRANTED) {
                        launchCamera();
                    } else {
                        Toast.makeText(mContext, "Camera, File Permissions required to capture the Photo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case PermissionUtils.PERMISSION_REQUEST_CODE_GALLERY: {
                String permissionOne = permissions[0];
                int grantResultOne = grantResults[0];

                String permissionTwo = permissions[1];
                int grantResultTwo = grantResults[1];

                if (permissionOne.equals(Manifest.permission.CAMERA) && permissionTwo.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResultOne == PackageManager.PERMISSION_GRANTED && grantResultTwo == PackageManager.PERMISSION_GRANTED) {
                        //launchGallery();
                    } else {
                        Toast.makeText(mContext, "Camera, File Permissions required to capture the Photo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;


        }

        try {
            retropicker.onRequesPermissionResult(requestCode, permissions, grantResults);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_ACTIVITY_RESULT_CODE:
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                 /*   Bitmap photo = (Bitmap) data.getExtras().get("data");
                    Uri tempUri = getImageUri(mContext, photo);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    picturePath = getRealPathFromURI(tempUri);*/

                    onActivityResultOfCameraCapture(data);

                    Log.v("IMAG Path -- ", picturePath);

                    break;
               /* case GALLERY_ACTIVITY_RESULT_CODE:
                    onActivityResultOfGallery(data);
                    //uploadImageServiceCall();
                    uploadFileToServer();
                    break;*/
            }
        }
    }


    private void onActivityResultOfCameraCapture(Intent data) {
        Uri selectedImage = imageUri;


     //   Picasso.with(mContext).load(selectedImage).resize(180, 180).centerCrop().into(imageView);


        File imgFile = new File(selectedImage.getPath());
        if(imgFile.exists()){

            System.out.print("IMAGE LENGTH --:"+(int) imgFile.length());
                try{
                    Bitmap imgBitmap = Utils.compressImage(selectedImage.getPath());
                    imageView.setImageBitmap(imgBitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }

        }

    }


}
