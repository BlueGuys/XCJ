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
import com.hongyan.xcj.utils.DisplayUtils;

import java.text.DecimalFormat;


/**
 * Created by wangning on 2018/4/1.
 */

public class CoinProgressView extends View {

    private Paint mPaint;
    private float mCurrentProgress = 50.0f;
    private Rect mRect;
    private Context mContext;
    DecimalFormat decimalFormat;

    public CoinProgressView(Context context) {
        super(context);
    }

    public CoinProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mPaint = new Paint();
        mRect = new Rect();
        decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

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
        mPaint.setColor(getResources().getColor(R.color.xcj_chart_red));
        mRect.set(0, 0, right, getHeight());
        canvas.drawRect(mRect, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(DisplayUtils.dip2px(mContext, 12));
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        canvas.drawText(mCurrentProgress + "%", DisplayUtils.dip2px(mContext, 15), DisplayUtils.dip2px(mContext, 14), mPaint);
        canvas.drawText(decimalFormat.format((100.0f - mCurrentProgress)) + "%", getWidth() - DisplayUtils.dip2px(mContext, 50), DisplayUtils.dip2px(mContext, 14), mPaint);
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
