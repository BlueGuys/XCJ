package com.hongyan.xcj.widget.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hongyan.xcj.R;
import com.hongyan.xcj.utils.DisplayUtils;


public class CanvasBitmapMeshView extends View {

    private Bitmap bitmap;
    private int mWidth;
    private int mHeight;

    //定义两个常量，这两个常量指定该图片横向、纵向上被划分为40格
    private final int WIDTH = 40;
    private final int HEIGHT = 40;

    //记录该图片上包含41*41个顶点
    private final int COUNT = (WIDTH + 1) * (HEIGHT + 1);

    //定义一个数组，保存Bitmap上的41*41个点得坐标
    private final float[] orig = new float[COUNT * 2];

    //定义一个数组，记录Bitmap上的41*41个点经过扭曲后的坐标
    //对 图片进行扭曲的关键就是修改该数组里的元素的值
    private final float[] verts = new float[COUNT * 2];

    public CanvasBitmapMeshView(Context context) {
        super(context);
    }

    public CanvasBitmapMeshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        mWidth = DisplayUtils.getScreenW(context) - 300;
        mHeight = mWidth * bitmap.getHeight() / bitmap.getWidth();

        int n = 0;
        float dw = mWidth / WIDTH;
        float dh = mHeight / HEIGHT;
        for (int y = 0; y <= HEIGHT; y++) {
            for (int x = 0; x <= WIDTH; x++) {
                float pointX = x * dw;
                float pointY = y * dh;
                orig[n * 2] = pointX;
                orig[n * 2 + 1] = pointY;

                verts[n * 2] = pointX;
                verts[n * 2 + 1] = pointY;
                n += 1;
            }
        }
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //这个方法可以自己设定View的大小
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
    }

    private void warp(float pointX, float pointY) {
        for (int i = 0; i < COUNT * 2; i += 2) {
            //计算每个坐标点与当前点（pointX、pointY）之间的距离(勾股定理)
            float dx = pointX - orig[i + 0];
            float dy = pointY - orig[i + 1];
            float dd = dx * dx + dy * dy;
            float d = (float) Math.sqrt(dd);
            //计算扭曲度，距离当前点（pointX，pointY）越远，扭曲度越小
            float pull = 80000 / ((dd * d));
            //（保存bitmap上41*41个点经过扭曲后的坐标）重新辅助
            if (pull >= 1) {
                verts[i] = pointX;
                verts[i + 1] = pointY;
            } else {
                //控制各定点向触摸事件发生点偏移
                verts[i] = orig[i] + dx * pull;
                verts[i + 1] = orig[i + 1] + dy * pull;
            }
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        warp(event.getX(), event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                warp(0, 0);
                break;
            case MotionEvent.ACTION_CANCEL:
                warp(0, 0);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //告诉父组件不要拦截onTouch事件
        getParent().requestDisallowInterceptTouchEvent(true);
        return onTouchEvent(event);
    }
}
