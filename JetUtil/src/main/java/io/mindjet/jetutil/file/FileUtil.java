package io.mindjet.jetutil.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jet on 2/7/17.
 */

public class FileUtil {

    public static final int QUALITY_LOW = 0;
    public static final int QUALITY_MIDDLE = 50;
    public static final int QUALITY_HIGH = 100;

    public static final String EXT_PNG = ".png";
    public static final String EXT_JPG = ".jpg";

    /**
     * Copy uri to a specific file.
     *
     * @param context context
     * @param uri     the uri to copy
     * @param file    the location to saveBitmap
     * @throws IOException
     */
    public static void copyUri2File(Context context, Uri uri, File file) throws IOException {
        InputStream is = context.getContentResolver().openInputStream(uri);
        OutputStream os = new FileOutputStream(file);
        byte[] bytes = new byte[2 * 1024];
        int len;
        while ((len = is.read(bytes)) > 0) {
            os.write(bytes, 0, len);
        }
        os.flush();
        os.close();
        is.close();
    }

    /**
     * Save photo with specific name, default extension(png) and default quality(middle).
     * <p>
     * If want to explicitly assign the extension, please use {@link #savePhoto(Bitmap bm, File file, String name, String ext)}
     *
     * @param bitmap the bitmap to saveBitmap
     * @param folder the folder to saveBitmap the bitmap
     * @param name   the name of the photo(without extension)
     * @throws IOException
     */
    public static String savePhoto(Bitmap bitmap, File folder, String name) throws IOException {
        return savePhoto(bitmap, folder, name, EXT_JPG, QUALITY_MIDDLE);
    }

    /**
     * Save photo with specific name, specific extension and default quality(middle).
     * <p>
     * If want to explicitly assign the quality, please use {@link #savePhoto(Bitmap bm, File file, String name, String ext, int quality)}
     *
     * @param bitmap the bitmap to saveBitmap
     * @param folder the folder to saveBitmap the bitmap
     * @param name   the name of the photo(without extension)
     * @param ext    the extension of the photo
     * @throws IOException
     */
    public static String savePhoto(Bitmap bitmap, File folder, String name, @Extension String ext) throws IOException {
        return savePhoto(bitmap, folder, name, ext, QUALITY_MIDDLE);
    }

    /**
     * Save photo with specific name, extension and quality.
     *
     * @param bitmap  the bitmap to saveBitmap
     * @param folder  the folder to saveBitmap the bitmap
     * @param name    the name of the photo(without extension)
     * @param ext     the extension of the photo
     * @param quality the quality of the photo
     * @throws IOException
     */
    public static String savePhoto(Bitmap bitmap, File folder, String name, @Extension String ext, @Source int quality) throws IOException {
        if (!folder.exists()) {
            folder.mkdir();
        }
        File file = new File(folder, name + ext);
        OutputStream os = new FileOutputStream(file);
        if (ext.equals(EXT_JPG))
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
        if (ext.equals(EXT_PNG))
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, os);
        os.close();
        return file.getAbsolutePath();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({QUALITY_LOW, QUALITY_MIDDLE, QUALITY_HIGH})
    public @interface Source {
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({EXT_PNG, EXT_JPG})
    public @interface Extension {

    }

}
