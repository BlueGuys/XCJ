package com.hongyan.xcj.widget.loading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.widget.ImageView;

import com.hongyan.xcj.R;


public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.loading_a);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_loading);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        spinner.start();
    }

}
