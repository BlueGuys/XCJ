package com.hongyan.xcj.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.hongyan.xcj.R;

public class ConfirmDialog extends Dialog {

    private TextView tvMessage;
    private Button btnLeft;
    private Button btnRight;
    private Activity mActivity;

    public ConfirmDialog(Activity activity) {
        this(activity, R.style.Custom_Progress);
        this.mActivity = activity;
    }

    private ConfirmDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (mActivity.getWindowManager().getDefaultDisplay().getWidth() * 0.65);
            window.setAttributes(params);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_confirm_dialog);
    }

    public void setContent(String message, String leftStr, String rightStr) {
        tvMessage = (TextView) this.findViewById(R.id.dialog_message);
        btnLeft = (Button) this.findViewById(R.id.dialog_confirm);
        btnRight = (Button) this.findViewById(R.id.dialog_cancel);
        tvMessage.setText(message);
        btnLeft.setText(leftStr);
        btnRight.setText(rightStr);
    }

    public void setLeftListener(View.OnClickListener leftListener) {
        btnLeft.setOnClickListener(leftListener);
    }

    public void setRightListener(View.OnClickListener rightListener) {
        btnRight.setOnClickListener(rightListener);
    }
}
