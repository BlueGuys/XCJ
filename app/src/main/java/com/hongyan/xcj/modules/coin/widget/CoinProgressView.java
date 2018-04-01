package com.hongyan.xcj.modules.coin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hongyan.xcj.R;


/**
 * Created by wangning on 2018/4/1.
 */

public class CoinProgressView extends View {

    private Paint mPaint;
    private float mCurrentProgress = 50.0f;
    private Rect mRect;

    public CoinProgressView(Context context) {
        super(context);
    }

    public CoinProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mRect = new Rect();
    }

    public CoinProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawColor(getResources().getColor(R.color.text_color_green));
        //画矩形
        int right = (int) (getWidth() * mCurrentProgress / 100);
        mPaint.setColor(Color.RED);
        mRect.set(0, 0, right, getHeight());
        canvas.drawRect(mRect, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(30);
        mPaint.setStrokeWidth(2);
        canvas.drawText(mCurrentProgress + "%", 30, 37, mPaint);
        canvas.drawText((100.0f - mCurrentProgress) + "%", getWidth() - 120, 37, mPaint);
        canvas.restore();
    }

    public void setProgress(float progress) {
        if (progress <= 0 || progress > 100) {
            return;
        }
        this.mCurrentProgress = progress;
        invalidate();
    }
}
