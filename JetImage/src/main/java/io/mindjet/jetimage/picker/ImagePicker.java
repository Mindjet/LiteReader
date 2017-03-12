package io.mindjet.jetimage.picker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import rx.subjects.PublishSubject;

/**
 * Created by Jet on 2/7/17.
 */

public class ImagePicker {

    private static ImagePicker instance;
    private static String imagePath;
    public static final int TAKE_PHOTO = 100;
    public static final int PICK_PHOTO = 101;
    public static final int TAKE_CROP_PHOTO = 102;
    public static final int PICK_CROP_PHOTO = 103;

    //Double check to ensure that there is only one instance anytime, anywhere.
    public static ImagePicker with(Context context) {
        if (instance == null) {
            synchronized (ImagePicker.class) {
                if (instance == null) {
                    instance = new ImagePicker(context);
                }
            }
        }
        return instance;
    }

    private Context context;
    private PublishSubject<File> subject;

    private ImagePicker(Context context) {
        this.context = context;
        if (getImagePath() == null || getImagePath().equals("")) {
            throw new IllegalArgumentException("Image path not found, did you forget to assign the ImagePath ?");
        } else {
            File folder = new File(ImagePicker.getImagePath());
            if (!folder.exists())
                folder.mkdir();
        }
    }

    public PublishSubject<File> requestImage(@Source int source) {
        subject = PublishSubject.create();
        Intent intent = new Intent(context, HiddenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(HiddenActivity.SOURCE, source);
        context.startActivity(intent);
        return subject;
    }

    void onPicked(File file) {
        if (subject != null) {
            subject.onNext(file);
            subject.onCompleted();
        }
    }

    static String getImagePath() {
        return imagePath;
    }

    public static void setImagePath(String path) {
        imagePath = path;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TAKE_PHOTO, PICK_PHOTO, TAKE_CROP_PHOTO, PICK_CROP_PHOTO})
    public @interface Source {

    }

}
