package com.hongyan.xcj.widget.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasTestView extends View {

    private int type;
    private Paint paint;

    private int centerX;
    private int centerY;

    public CanvasTestView(Context context) {
        this(context, null);
    }

    public CanvasTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDetail(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
    }

    private void drawDetail(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        paint.setAntiAlias(true);
        switch (type) {
            case 0://正方形
                Rect rect = new Rect(centerX - 150, centerY - 150, centerX + 150, centerY + 150);
                paint.setColor(Color.RED);
                canvas.drawRect(rect, paint);
                break;
            case 1://圆形
                paint.setColor(Color.parseColor("#00ff00"));
                canvas.drawCircle(centerX, centerY, 150, paint);
                break;
            case 2://三角形
                Path path = new Path();
                path.moveTo(centerX, centerY - 150);// 此点为多边形的起点
                path.lineTo(centerX - 150, centerY + 150);
                path.lineTo(centerX + 150, centerY + 150);
                path.close(); // 使这些点构成封闭的多边形
                canvas.drawPath(path, paint);
                break;
            case 3://椭圆曲线
                paint.setStyle(Paint.Style.STROKE);//设置空心
                RectF oval1 = new RectF(centerX - 150, centerY - 150, centerX + 150, centerY + 150);//定义了椭圆的上下左右
                canvas.drawArc(oval1, 0, 90, true, paint);//起始角度+扫过的角度，是否连接圆心
                break;
            case 4://画线
                paint.setColor(Color.YELLOW);
                paint.setStrokeWidth(4);
                canvas.drawLine(centerX - 150, centerY - 150, centerX + 150, centerY + 150, paint);
                break;
            case 5://画椭圆
                paint.setColor(Color.GREEN);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    canvas.drawOval(centerX - 150, centerY - 100, centerX + 150, centerY + 100, paint);
                }
                break;
            case 6://画posText
                Path path1 = new Path();
                path1.moveTo(centerX - 150, centerY - 150);// 此点为多边形的起点
                path1.lineTo(centerX + 200, centerY + 50);
                paint.setTextSize(48);
                canvas.drawTextOnPath("中华人民共和国万岁", path1, 0, 20, paint);
                break;
            case 7://画椭圆矩形
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.RED);
                RectF rect1 = new RectF(centerX - 150, centerY - 150, centerX + 150, centerY + 150);
                canvas.drawRoundRect(rect1,30,30,paint);
                break;
            case 8://画椭圆矩形
//                paint.setStyle(Paint.Style.FILL);
//                paint.setColor(Color.RED);
//                RectF rect1 = new RectF(centerX - 150, centerY - 150, centerX + 150, centerY + 150);
//                canvas.drawRoundRect(rect1,30,30,paint);
                break;
            default:
                paint.setColor(Color.WHITE);
                paint.setTextSize(48);
                canvas.drawText("谢谢欣赏！", centerX - 100, centerY - 8, paint);
                break;

        }
    }

    public void update(int type) {
        this.type = type;
        invalidate();
    }

}
