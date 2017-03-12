package io.mindjet.jetimage.picker;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 2/7/17.
 */

public class HiddenActivity extends Activity {

    public static final String SOURCE = "source";
    private static final String TAG = "ImagePicker";
    private static final int CROP_PHOTO = 104;
    private Uri uri;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent();
    }

    private void handleIntent() {
        if (!checkPermission()) {
            return;
        }
        @ImagePicker.Source int sourceType = getIntent().getIntExtra(SOURCE, 0);
        switch (sourceType) {
            case ImagePicker.TAKE_PHOTO:
                handleTakePhoto();
                break;
            case ImagePicker.PICK_PHOTO:
                handlePickPhoto();
                break;
            case ImagePicker.TAKE_CROP_PHOTO:
                handleTakeCropPhoto();
                break;
            case ImagePicker.PICK_CROP_PHOTO:
                handlePickCropPhoto();
                break;
        }
    }

    private void handleTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri = createUri());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, ImagePicker.TAKE_PHOTO);
    }

    private void handlePickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, ImagePicker.PICK_PHOTO);
    }

    private void handleTakeCropPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri = createUri());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, ImagePicker.TAKE_CROP_PHOTO);
    }

    private void handlePickCropPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, ImagePicker.PICK_CROP_PHOTO);
    }

    private boolean checkPermission() {
        boolean isWritingStorageGranted
                = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean isCameraGranted
                = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if (isWritingStorageGranted && isCameraGranted) {
            return true;
        } else {
            List<String> permissions = new ArrayList<>();
            if (!isWritingStorageGranted) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (!isCameraGranted) {
                permissions.add(Manifest.permission.CAMERA);
            }
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[]{}), 0);
            return false;
        }
    }

    private Uri createUri() {
        ContentResolver contentResolver = getContentResolver();
        ContentValues cv = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        cv.put(MediaStore.Images.Media.TITLE, timeStamp);
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
    }

    private File createFile() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String fileName = dateFormat.format(new Date());
        return new File(ImagePicker.getImagePath(), fileName + ".png");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            handleIntent();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImagePicker.TAKE_PHOTO:
                    handleTakePhotoResult(uri);
                    break;
                case ImagePicker.PICK_PHOTO:
                    handlePickPhotoResult(data);
                    break;
                case ImagePicker.TAKE_CROP_PHOTO:
                    handleTakeCropPhotoResult(uri);
                    break;
                case ImagePicker.PICK_CROP_PHOTO:
                    handlePickCropPhotoResult(data.getData());
                    break;
                case CROP_PHOTO:
                    handCropPhotoResult();
                    break;
            }
        } else {
            finish();
        }
    }

    private void handleTakePhotoResult(final Uri uri) {
        Observable
                .fromCallable(new Callable<File>() {
                    @Override
                    public File call() throws Exception {
                        return copyUri2File(uri);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        ImagePicker.with(HiddenActivity.this).onPicked(file);
                        finish();
                    }
                });
    }

    private File copyUri2File(Uri uri) throws IOException {
        File file = createFile();
        InputStream is = getContentResolver().openInputStream(uri);
        OutputStream fos = new FileOutputStream(file);
        byte[] buff = new byte[2 * 1024];
        int len;
        while ((len = is.read(buff)) > 0) {
            fos.write(buff, 0, len);
        }
        fos.close();
        is.close();
        return file;
    }

    private void handlePickPhotoResult(Intent data) {
        File file = null;
        Uri uri = data.getData();
        String scheme = uri.getScheme();
        //As the uri maybe a 'true' uri or a file.
        switch (scheme) {
            case "content":     //uri path begins with 'content'.
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imagePath = cursor.getString(columnIndex);
                    cursor.close();
                    file = new File(imagePath);
                    Log.e(TAG, "The picked image is located at " + imagePath);
                }
                break;
            case "file":        //file path begins with 'file'.
                file = new File(uri.getPath());
                Log.e(TAG, "The picked image is located at " + file.getAbsolutePath());
                break;
        }
        ImagePicker.with(this).onPicked(file);
        finish();
    }

    private void handleTakeCropPhotoResult(Uri uri) {
        launchCropActivity(uri, file = createFile());
    }

    private void handlePickCropPhotoResult(Uri uri) {
        launchCropActivity(uri, file = createFile());
    }

    private void launchCropActivity(Uri uri, File file) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));           //if the output is assigned, the result contains no content.
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", false);
        startActivityForResult(intent, CROP_PHOTO);
    }

    private void handCropPhotoResult() {
        ImagePicker.with(this).onPicked(file);
        finish();
    }

}
