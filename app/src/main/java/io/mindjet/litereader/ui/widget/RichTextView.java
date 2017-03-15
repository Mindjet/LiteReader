package io.mindjet.litereader.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.lang.ref.WeakReference;

import io.mindjet.litereader.R;
import io.mindjet.jetutil.logger.JLogger;

/**
 * Created by Jet on 3/14/17.
 */

public class RichTextView extends android.support.v7.widget.AppCompatTextView {

    private static JLogger jLogger = JLogger.get(RichTextView.class.getSimpleName());

    public RichTextView(Context context) {
        super(context);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRichText(String html) {
        setText(Html.fromHtml(html, new ImageGetterImpl(this), null));
    }

    private static class ImageGetterImpl implements Html.ImageGetter {

        private WeakReference<RichTextView> weakRef;
        private double zoomFactor = 2;
        private int width;

        private ImageGetterImpl(RichTextView richTextView) {
            weakRef = new WeakReference<>(richTextView);
        }

        @Override
        public Drawable getDrawable(String source) {
            final RichTextView tv = weakRef.get();
            final LevelListDrawable mDrawable = new LevelListDrawable();

            Glide.with(tv.getContext()).load(source).asBitmap().placeholder(R.drawable.ic_placeholder).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (width == 0) {
                        tv.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        width = tv.getWidth();
                    }
                    BitmapDrawable d = new BitmapDrawable(resource);
                    mDrawable.addLevel(1, 1, d);
                    int imageWidth;
                    int imageHeight;
                    //小图只需略微扩大，大图则宽度铺满
                    if (resource.getHeight() < 50 || resource.getWidth() < 50) {
                        imageWidth = (int) (resource.getWidth() * zoomFactor);
                        imageHeight = (int) (resource.getHeight() * zoomFactor);
                    } else {
                        imageWidth = width;
                        double ratio = (double) resource.getHeight() / (double) resource.getWidth();
                        imageHeight = (int) (ratio * width);
                    }
                    mDrawable.setBounds(0, 0, imageWidth, imageHeight);
                    mDrawable.setLevel(1);
                    tv.invalidate();
                    tv.setText(tv.getText());
                }
            });
            return mDrawable;
        }
    }

}
