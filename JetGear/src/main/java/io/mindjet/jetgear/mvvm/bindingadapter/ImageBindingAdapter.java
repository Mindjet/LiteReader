package io.mindjet.jetgear.mvvm.bindingadapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import io.mindjet.jetimage.loader.ImageLoader;

/**
 * Created by Mindjet on 2017/2/15.
 */

public class ImageBindingAdapter {

    @BindingAdapter("url")
    public static void ImageUrl(ImageView imageView, String url) {
        ImageLoader.load(imageView, url);
    }

    @BindingAdapter(value = {"url", "placeHolder"})
    public static void ImagePlaceHolder(ImageView imageView, String url, @DrawableRes int placeHolder) {
        ImageLoader.loadWithPlaceHolder(imageView, url, placeHolder);
    }

    @BindingAdapter(value = {"url", "placeHolder"})
    public static void ImagePlaceHolder(ImageView imageView, String url, Drawable placeHolder) {
        ImageLoader.loadWithPlaceHolder(imageView, url, placeHolder);
    }

    @BindingAdapter("src")
    public static void ImageSrc(ImageView imageView, @DrawableRes int src) {
        ImageLoader.loadSrc(imageView, src);
    }

    @BindingAdapter("src")
    public static void ImageSrc(ImageView imageView, Drawable drawable) {
        ImageLoader.loadSrc(imageView, drawable);
    }

    @BindingAdapter("circle")
    public static void ImageCircle(ImageView imageView, String url) {
        ImageLoader.loadCircle(imageView, url);
    }

    @BindingAdapter(value = {"circle", "placeHolder"})
    public static void ImageCirclePlaceHolder(ImageView imageView, String url, @DrawableRes int placeHolder) {
        ImageLoader.loadCircleWithPlaceHolder(imageView, url, placeHolder);
    }

    @BindingAdapter(value = {"circle", "placeHolder"})
    public static void ImageCirclePlaceHolder(ImageView imageView, String url, Drawable placeHolder) {
        ImageLoader.loadCircleWithPlaceHolder(imageView, url, placeHolder);
    }

    @BindingAdapter(value = {"round", "radius"}, requireAll = false)
    public static void ImageRound(ImageView imageView, String url, int radius) {
        ImageLoader.loadWithRadius(imageView, url, radius);
    }

    @BindingAdapter(value = {"round", "radius", "placeHolder"})
    public static void ImageRoundPlaceHolder(ImageView imageView, String url, int radius, @DrawableRes int placeHolder) {
        ImageLoader.loadWithRadiusPlaceHolder(imageView, url, radius, placeHolder);
    }

    @BindingAdapter(value = {"round", "radius", "placeHolder"})
    public static void ImageRoundPlaceHolder(ImageView imageView, String url, int radius, Drawable placeHolder) {
        ImageLoader.loadWithRadiusPlaceHolder(imageView, url, radius, placeHolder);
    }

}
