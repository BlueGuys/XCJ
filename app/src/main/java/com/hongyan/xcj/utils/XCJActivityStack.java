//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hongyan.xcj.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Build.VERSION;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class XCJActivityStack {
    private static final int FLAG_ADJUST_FORE = 1;
    private static final int FLAG_ADJUST_BACK = 2;
    protected List<XCJActivityStack.StackItem> mActivityStack = new CopyOnWriteArrayList();
    private Application mApplication;
    private boolean isForeground = true;
    private int mResumeCount = 0;
    private boolean mIsLauncher = true;
    private ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if(XCJActivityStack.this.checkActivity(activity)) {
                XCJActivityStack.this.pushStack(activity);
            }
        }

        public void onActivityStarted(Activity activity) {
            if(XCJActivityStack.this.mResumeCount == 0) {
                XCJActivityStack.this.isForeground = true;
                XCJActivityStack.this.sendAdjustForeOrBackMessage(1);
            }

            XCJActivityStack.this.mResumeCount++;
            if(XCJActivityStack.this.checkActivity(activity)) {
                XCJActivityStack.this.updateActivityStatus(activity, XCJActivityStack.StackItem.Status.START);
            }
        }

        public void onActivityResumed(Activity activity) {
            if(XCJActivityStack.this.checkActivity(activity)) {
                XCJActivityStack.this.updateActivityStatus(activity, XCJActivityStack.StackItem.Status.RESUME);
            }
        }

        public void onActivityPaused(Activity activity) {
            if(XCJActivityStack.this.checkActivity(activity)) {
                XCJActivityStack.this.updateActivityStatus(activity, XCJActivityStack.StackItem.Status.PAUSE);
            }
        }

        public void onActivityStopped(Activity activity) {
            XCJActivityStack.this.mResumeCount--;
            if(XCJActivityStack.this.mResumeCount == 0) {
                XCJActivityStack.this.isForeground = false;
                XCJActivityStack.this.sendAdjustForeOrBackMessage(2);
            }

            if(XCJActivityStack.this.checkActivity(activity)) {
                XCJActivityStack.this.updateActivityStatus(activity, XCJActivityStack.StackItem.Status.STOP);
            }
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityDestroyed(Activity activity) {
            if(XCJActivityStack.this.checkActivity(activity)) {
                XCJActivityStack.this.popStack(activity);
            }
        }
    };
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void dispatchMessage(Message msg) {
            int tWhat = msg.what;
            switch(tWhat) {
            case 1:
                if(XCJActivityStack.this.mIsLauncher) {
                    XCJActivityStack.this.mIsLauncher = false;
                } else {
                    XCJActivityStack.this.onAppStatusChanged(true);
                }
                break;
            case 2:
                XCJActivityStack.this.onAppStatusChanged(false);
            }

        }
    };

    public XCJActivityStack() {
    }

    private void sendAdjustForeOrBackMessage(int flag) {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.sendEmptyMessageDelayed(flag, 500L);
    }

    public final void initial(Application application) {
        this.mApplication = application;
        if(VERSION.SDK_INT >= 14) {
            this.mApplication.registerActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
        }
    }

    public synchronized XCJActivityStack.StackItem.Status getActivityStatus(Activity activity) {
        if(activity == null) {
            return XCJActivityStack.StackItem.Status.NONE;
        } else {
            XCJActivityStack.StackItem stackItem = this.getStackItem(activity);
            return stackItem == null? XCJActivityStack.StackItem.Status.NONE:stackItem.mStatus;
        }
    }

    public boolean isAppInBackground() {
        return !this.isForeground;
    }

    public synchronized boolean contains(Activity activity) {
        XCJActivityStack.StackItem stackItem = this.getStackItem(activity);
        return stackItem != null && stackItem.mActivity != null && stackItem.mActivity.get() != null && !((Activity)stackItem.mActivity.get()).isFinishing();
    }

    public synchronized Activity getLatestActivityByName(String activityName) {
        XCJActivityStack.StackItem stackItem = this.getLatestItemByName(activityName);
        return stackItem == null?null:(Activity)stackItem.mActivity.get();
    }

    public synchronized Activity getCurrentActivity(XCJActivityStack.ActivityFiltter filtter) {
        int index = this.mActivityStack.size() - 1;

        Activity activity;
        do {
            do {
                do {
                    XCJActivityStack.StackItem stackItem;
                    do {
                        do {
                            if(index <= -1) {
                                return null;
                            }

                            stackItem = (XCJActivityStack.StackItem)this.mActivityStack.get(index);
                            --index;
                        } while(stackItem == null);
                    } while(stackItem.mActivity == null);

                    activity = (Activity)stackItem.mActivity.get();
                } while(activity == null);
            } while(activity.isFinishing());
        } while(filtter != null && filtter.filtter(activity));

        return activity;
    }

    public synchronized Activity getCurrentActivity() {
        return this.getCurrentActivity((XCJActivityStack.ActivityFiltter)null);
    }

    public synchronized void finishAllActivity() {
        Iterator var1 = this.mActivityStack.iterator();

        while(var1.hasNext()) {
            XCJActivityStack.StackItem item = (XCJActivityStack.StackItem)var1.next();
            if(item != null && item.mActivity != null) {
                Activity activity = (Activity)item.mActivity.get();
                if(activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }

    }

    public synchronized void finishAllActivityWithoutSpecial(Activity keepActivity) {
        if(keepActivity != null) {
            Iterator var2 = this.mActivityStack.iterator();

            while(var2.hasNext()) {
                XCJActivityStack.StackItem item = (XCJActivityStack.StackItem)var2.next();
                if(item != null && item.mActivity != null) {
                    Activity activity = (Activity)item.mActivity.get();
                    if(activity != null && !activity.isFinishing() && !activity.equals(keepActivity)) {
                        activity.finish();
                    }
                }
            }

        }
    }


    public synchronized int size() {
        int size = 0;
        Iterator var2 = this.mActivityStack.iterator();

        while(var2.hasNext()) {
            XCJActivityStack.StackItem item = (XCJActivityStack.StackItem)var2.next();
            if(item != null && item.mActivity != null && item.mActivity.get() != null && !((Activity)item.mActivity.get()).isFinishing()) {
                ++size;
            }
        }

        return size;
    }

    protected abstract boolean isValidActivity(Activity var1);

    protected abstract void onAppStatusChanged(boolean var1);

    private boolean checkActivity(Activity activity) {
        return this.isValidActivity(activity);
    }

    private void pushStack(Activity activity) {
        XCJActivityStack.StackItem stackItem = this.getStackItem(activity);
        if(stackItem == null) {
            this.mActivityStack.add(new XCJActivityStack.StackItem(activity, XCJActivityStack.StackItem.Status.CREATE));
        }
    }

    private void popStack(Activity activity) {
        XCJActivityStack.StackItem stackItem = this.getStackItem(activity);
        if(stackItem != null) {
            stackItem.mStatus = XCJActivityStack.StackItem.Status.DESTROY;
            this.mActivityStack.remove(stackItem);
        }
    }

    private void updateActivityStatus(Activity activity, XCJActivityStack.StackItem.Status status) {
        XCJActivityStack.StackItem stackItem = this.getStackItem(activity);
        if(stackItem != null) {
            stackItem.mStatus = status;
        }
    }

    private XCJActivityStack.StackItem getStackItem(Activity activity) {
        if(activity == null) {
            return null;
        } else {
            Iterator var2 = this.mActivityStack.iterator();

            XCJActivityStack.StackItem item;
            do {
                if(!var2.hasNext()) {
                    return null;
                }

                item = (XCJActivityStack.StackItem)var2.next();
            } while(item == null || item.mActivity == null || !activity.equals(item.mActivity.get()));

            return item;
        }
    }

    private XCJActivityStack.StackItem getLatestItemByName(String activityName) {
        if(activityName == null) {
            return null;
        } else {
            int currentSize = this.mActivityStack.size();

            for(int i = currentSize - 1; i >= 0; --i) {
                XCJActivityStack.StackItem item = (XCJActivityStack.StackItem)this.mActivityStack.get(i);
                if(item != null && item.mActivity != null && item.mActivity.get() != null && !((Activity)item.mActivity.get()).isFinishing() && ((Activity)item.mActivity.get()).getClass().getName().equals(activityName)) {
                    return item;
                }
            }

            return null;
        }
    }

    public static class StackItem {
        public SoftReference<Activity> mActivity;
        public XCJActivityStack.StackItem.Status mStatus;

        StackItem(Activity activity, XCJActivityStack.StackItem.Status status) {
            this.mStatus = XCJActivityStack.StackItem.Status.NONE;
            this.mActivity = new SoftReference(activity);
            this.mStatus = status;
        }

        public static enum Status {
            CREATE,
            START,
            RESUME,
            PAUSE,
            STOP,
            DESTROY,
            NONE;

            private Status() {
            }
        }
    }

    public interface ActivityFiltter {
        boolean filtter(Activity var1);
    }
}
