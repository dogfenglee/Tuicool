package com.lowwor.tuicool.utils;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by lowworker on 2015/9/20.
 */
public class GlideImageGetter implements Html.ImageGetter {

    private TextView mContainer;
    private int mMaxWidth;

    public GlideImageGetter(  TextView container) {
        mContainer = container;
    }

    @Override
    public Drawable getDrawable(String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        mMaxWidth = ScreenUtils.getDisplayWidth(mContainer.getContext());

        Glide.with(mContainer.getContext()).load(source).diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(final GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                int width;
                int height;
                int resourceWidth = resource.getIntrinsicWidth();
                int resourceHeight =  resource.getIntrinsicHeight();
                if(mMaxWidth> resourceWidth+150){
     width = mMaxWidth;
     height = mMaxWidth * resourceHeight / resourceWidth;
            }else{
                    width =resourceWidth;
                    height = resourceHeight;
                }


                resource.setBounds(0, 0, width, height);
                resource.setVisible(true, true);

                urlDrawable.setBounds(0, 0, width, height);
                urlDrawable.drawable = resource;

                if (resource instanceof GifDrawable) {

                    GifDrawable gifDrawable = (GifDrawable) resource;
                    gifDrawable.setLoopCount(GlideDrawable.LOOP_FOREVER);
                    gifDrawable.start();
                }

                mContainer.setText(mContainer.getText());
            }
        });

        return urlDrawable;
    }
}
