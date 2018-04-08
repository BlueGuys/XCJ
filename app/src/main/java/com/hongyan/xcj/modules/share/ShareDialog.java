package com.hongyan.xcj.modules.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hongyan.xcj.R;

public class ShareDialog extends Dialog {

    private Button button;
    private Activity mActivity;
    private LinearLayout linearWeibo, linearQQ, linearQQZone;
    private OnShareClickListener listener;

    public ShareDialog(Activity activity) {
        this(activity, R.style.Custom_Progress);
        this.mActivity = activity;
    }

    private ShareDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_confirm_dialog);
        initView();
    }

    public void initView() {
        linearWeibo = this.findViewById(R.id.share_weibo);
        linearQQ = this.findViewById(R.id.share_qq);
        linearQQZone = this.findViewById(R.id.share_qqzone);
        button = this.findViewById(R.id.dialog_cancel);
        linearWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onShareWeibo();
                }
                dismiss();
            }
        });
        linearQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onShareQQ();
                }
                dismiss();
            }
        });
        linearQQZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onShareQQZone();
                }
                dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setOnShareClickListener(OnShareClickListener listener) {
        this.listener = listener;
    }

    public interface OnShareClickListener {
        void onShareWeibo();

        void onShareQQ();

        void onShareQQZone();
    }
}
