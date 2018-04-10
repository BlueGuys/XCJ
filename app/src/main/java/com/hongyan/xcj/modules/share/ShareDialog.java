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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.hongyan.xcj.R;

public class ShareDialog extends Dialog {

    private Button button;
    private Activity mActivity;
    private GridView gridView;
    private ShareAdapter adapter;
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
        gridView = this.findViewById(R.id.share_gridView);
        button = this.findViewById(R.id.dialog_cancel);
        adapter = new ShareAdapter(mActivity);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                listener.onChannelSelect(position);
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
        void onChannelSelect(int channelId);
    }
}
