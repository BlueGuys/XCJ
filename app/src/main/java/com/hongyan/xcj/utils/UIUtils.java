//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hongyan.xcj.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;

public class UIUtils {
    static boolean deviceDataInited = false;
    private static float density;
    private static int densityDpi;
    private static int screenWidth;
    private static int screenHeight;

    public UIUtils() {
    }

    public static void initDeviceData(Context context) {
        DisplayMetrics displayMetrics = null;
        if(context.getResources() != null && (displayMetrics = context.getResources().getDisplayMetrics()) != null) {
            density = displayMetrics.density;
            densityDpi = displayMetrics.densityDpi;
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
        }

        deviceDataInited = true;
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5F);
    }

    public static int dip2px(Context context, float dpValue) {
        if(deviceDataInited) {
            return (int)(dpValue * density + 0.5F);
        } else if(context != null && context.getResources() != null) {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int)(dpValue * scale + 0.5F);
        } else {
            return -1;
        }
    }

    public static int getScreenWidth(Context context) {
        if(deviceDataInited) {
            return screenWidth;
        } else if(context != null && context.getResources() != null) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return displayMetrics.widthPixels;
        } else {
            return -1;
        }
    }

    public static int getScreenHeight(Context context) {
        if(deviceDataInited) {
            return screenHeight;
        } else if(context != null && context.getResources() != null) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return displayMetrics.heightPixels;
        } else {
            return -1;
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public static float getScreenDensity(Context context) {
        if(deviceDataInited) {
            return density;
        } else if(context != null && context.getResources() != null) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return displayMetrics.density;
        } else {
            return 0.0F;
        }
    }

    public static int getScreenDPI(Context context) {
        if(deviceDataInited) {
            return densityDpi;
        } else if(context != null && context.getResources() != null) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return displayMetrics.densityDpi;
        } else {
            return 0;
        }
    }

    public static void setScreenBrightness(Activity activity, float value) {
        if(activity != null) {
            Window localWindow = activity.getWindow();
            LayoutParams localLayoutParams = localWindow.getAttributes();
            float percentage = value / 255.0F;
            localLayoutParams.screenBrightness = percentage;
            localWindow.setAttributes(localLayoutParams);
        }
    }

    public static int getScreenBrightness(Activity activity) {
        if(activity == null) {
            return -255;
        } else {
            int value = 0;
            ContentResolver cr = activity.getContentResolver();
            try {
                value = System.getInt(cr, "screen_brightness");
                return value;
            } catch (SettingNotFoundException var4) {
                return -255;
            }
        }
    }

    public static int[] getScreenDimensions(Context context) {
        int[] dimensions = new int[2];
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        dimensions[0] = display.getWidth();
        dimensions[1] = display.getHeight();
        return dimensions;
    }

    public static long getPxByScreenWidth(Context context, double scale) {
        return Math.round((double)getScreenWidth(context) * scale);
    }

    public static long getPxByEquipHeight(Context context, double scale) {
        return Math.round((double)getScreenHeight(context) * scale);
    }

    public static int getDimensionPixelSize(Context context, int dimenId) {
        return context == null?0:context.getResources().getDimensionPixelSize(dimenId);
    }

    public static int getFontHeight(Context context, float fontSize) {
        if(context == null) {
            return -1;
        } else {
            TextPaint paint = new TextPaint();
            setTextSize(context, paint, fontSize);
            FontMetrics fm = paint.getFontMetrics();
            return (int)Math.ceil((double)(fm.descent - fm.ascent));
        }
    }

    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if(str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);

            for(int j = 0; j < len; ++j) {
                iRet += (int)Math.ceil((double)widths[j]);
            }
        }

        return iRet;
    }

    public static String getTextOmit(TextPaint paint, String str, int width) {
        String des = null;
        CharSequence sequence = TextUtils.ellipsize(str, paint, (float)width, TruncateAt.END);
        if(sequence != null) {
            des = sequence.toString();
        }

        return des;
    }

    public static int getResId(Context context, String name, String defType) {
        Resources res = context.getResources();
        int id = res.getIdentifier(name, defType, context.getPackageName());
        return id;
    }

    public static void setWindowAlpha(Activity activity, float alpha) {
        if(activity != null) {
            LayoutParams params = activity.getWindow().getAttributes();
            params.screenBrightness = alpha;
            activity.getWindow().setAttributes(params);
        }
    }

    public static TextPaint setTextSize(Context context, TextPaint paint, float size) {
        Resources r;
        if(context == null) {
            r = Resources.getSystem();
        } else {
            r = context.getResources();
        }

        if(r != null) {
            paint.setTextSize(TypedValue.applyDimension(2, size, r.getDisplayMetrics()));
        }

        return paint;
    }

    public static float measureTextWidth(Paint paint, String str) {
        return paint != null && !TextUtils.isEmpty(str)?paint.measureText(str):0.0F;
    }

    public static Rect measureText(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect;
    }

    public static void setEditHintSize(EditText editText, String text, int size) {
        SpannableString ss = new SpannableString(text);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size);
        ss.setSpan(ass, 0, ss.length(), 33);
        StyleSpan span = new StyleSpan(0);
        ss.setSpan(span, 0, ss.length(), 33);
        editText.setHint(new SpannedString(ss));
    }

    public static void setBold(TextView textView, String text) {
        SpannableString ss = new SpannableString(text);
        StyleSpan span = new StyleSpan(1);
        ss.setSpan(span, 0, ss.length(), 33);
        textView.setText(new SpannedString(ss));
    }

    public static void setTextBold(TextView textView) {
        textView.getPaint().setFakeBoldText(true);
    }

    public static void setTextLeftDrawable(Context context, int drawableID, TextView textView) {
        if(textView != null) {
            Drawable drawable = context.getResources().getDrawable(drawableID);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable, (Drawable)null, (Drawable)null, (Drawable)null);
        }
    }

    public static void setTextDeleteLine(TextView textView, boolean isAdd) {
        if(textView != null) {
            if(isAdd) {
                textView.getPaint().setFlags(17);
            } else {
                textView.getPaint().setFlags(1);
            }

            textView.invalidate();
        }
    }
}
