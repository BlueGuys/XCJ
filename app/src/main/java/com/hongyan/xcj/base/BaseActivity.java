package com.hongyan.xcj.base;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hongyan.xcj.R;
import com.hongyan.xcj.test.TestActivity;
import com.hongyan.xcj.widget.NavigationView;
import com.hongyan.xcj.widget.loading.LoadingDialog;
import com.hongyan.xcj.widget.tost.IToast;


/**
 * com.jp.base.BaseActivity
 */
public abstract class BaseActivity extends FragmentActivity {

    private LoadingDialog dialog;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle();
    }

    private void setStyle() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xff007AFF);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorManager manager = (SensorManager) this.getSystemService(Service.SENSOR_SERVICE);
        if (manager != null) {
            manager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    //获取传感器类型
                    int sensorType = sensorEvent.sensor.getType();
                    //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
                    float[] values = sensorEvent.values;
                    //如果传感器类型为加速度传感器，则判断是否为摇一摇
                    if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                        if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                                .abs(values[2]) > 17)) {

                            startActivity(new Intent(BaseActivity.this, TestActivity.class));
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void setContentView(int ResLayout) {
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_base, null, false);
        LinearLayout rootLayout = (LinearLayout) rootView.findViewById(R.id.contentLayout);
        navigationView = (NavigationView) rootView.findViewById(R.id.navigation);
        View contentView = LayoutInflater.from(this).inflate(ResLayout, null, false);
        rootLayout.addView(contentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rootView);
        navigationView.setOnBackClickListener(new NavigationView.OnBackClickListener() {
            @Override
            public void callBack() {
                finish();
            }
        });
    }

    protected void setTitle(String title) {
        navigationView.setTitle(title);
    }

    protected void hideNavigationView() {
        navigationView.setVisibility(View.GONE);
    }

    public void showSuccessToast(String message) {
        IToast.showSuccessToast(message);
    }

    public void showErrorToast(String message) {
        IToast.showWarnToast(message);
    }

    public void startLoading() {
        if (dialog == null) {
            dialog = new LoadingDialog(this);
        }
        dialog.show();
    }

    public void cancelLoading() {
        if (dialog == null) {
            cancelLoading();
        }
        dialog.dismiss();
    }

}
