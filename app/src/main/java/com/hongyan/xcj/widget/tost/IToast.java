package com.hongyan.xcj.widget.tost;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hongyan.xcj.R;
import com.hongyan.xcj.core.BaseApplication;


/**
 * com.jp.views.IToast
 */
public class IToast {

    public static void showWarnToast(String msg) {
        show(msg, R.drawable.icon_fail_toast);
    }

    public static void showSuccessToast(String msg) {
        show(msg, R.drawable.icon_success_toast);
    }

    private static void show(String message, int resId) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        if (context == null) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(message);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageResource(resId);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 70);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}

