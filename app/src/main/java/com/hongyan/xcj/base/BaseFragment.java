package com.hongyan.xcj.base;

import android.support.v4.app.Fragment;

import com.hongyan.xcj.widget.loading.LoadingDialog;
import com.hongyan.xcj.widget.tost.IToast;

/**
 * Created by wangning on 2018/3/20.
 */

public class BaseFragment extends Fragment {

    private LoadingDialog dialog;

    public void showSuccessToast(String message) {
        IToast.showSuccessToast(message);
    }

    public void showErrorToast(String message) {
        IToast.showWarnToast(message);
    }

    public void startLoading() {
        if (dialog == null) {
            dialog = new LoadingDialog(getActivity());
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
