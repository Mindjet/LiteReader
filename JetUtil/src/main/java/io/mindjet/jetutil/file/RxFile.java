package io.mindjet.jetutil.file;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;

import rx.subjects.ReplaySubject;

/**
 * Created by Jet on 2/9/17.
 */

public class RxFile {

    private volatile static RxFile rxFile;

    public static RxFile get() {
        if (rxFile == null) {
            synchronized (RxFile.class) {
                if (rxFile == null) {
                    rxFile = new RxFile();
                }
            }
        }
        return rxFile;
    }

    private ReplaySubject<String> bitmapSubject;

    public ReplaySubject<String> saveBitmap(final Bitmap bitmap, final File folder, final String name) {
        bitmapSubject = ReplaySubject.create();
        try {
            String path = FileUtil.savePhoto(bitmap, folder, name);
            onSuccess(path);
        } catch (IOException e) {
            e.printStackTrace();
            onFail(e);
        }
        return bitmapSubject;
    }

    private void onSuccess(String path) {
        if (bitmapSubject != null) {
            bitmapSubject.onNext(path);
            bitmapSubject.onCompleted();
        }
    }

    private void onFail(Throwable t) {
        bitmapSubject.onError(t);
    }

}
