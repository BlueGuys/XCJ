
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hongyan.xcj.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InflaterService {
    public static final int RESOURCE_LOAD_MAX_TRY_COUNT = 3;
    private static InflaterService instance = new InflaterService();

    private InflaterService() {
    }

    public static InflaterService getInstance() {
        return instance;
    }

    private LayoutInflater getLayoutInflater(Context context) {
        return null == context?null:(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View inflate(Context context, int resId, ViewGroup root) {
        if(null == context) {
            return null;
        } else {
            View view = null;

            for(int i = 0; i < 3; ++i) {
                try {
                    LayoutInflater layoutInflater = this.getLayoutInflater(context);
                    if(null != layoutInflater) {
                        view = this.getLayoutInflater(context).inflate(resId, root);
                    }
                    break;
                } catch (RuntimeException var7) {
                    if(i == 2) {
                        throw var7;
                    }

                } catch (OutOfMemoryError var8) {
                    if(i == 2) {
                        throw var8;
                    }
                }
            }

            return view;
        }
    }

    public View inflate(Context context, int resId, ViewGroup root, boolean attachToRoot) {
        if(null == context) {
            return null;
        } else {
            View view = null;

            for(int i = 0; i < 3; ++i) {
                try {
                    view = this.getLayoutInflater(context).inflate(resId, root, attachToRoot);
                    break;
                } catch (RuntimeException var8) {
                    if(i == 2) {
                        throw var8;
                    }

                } catch (OutOfMemoryError var9) {
                    if(i == 2) {
                        throw var9;
                    }
                }
            }

            return view;
        }
    }
}
