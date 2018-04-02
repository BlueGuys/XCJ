package com.hongyan.xcj.modules.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

public class SetNickNameActivity extends BaseActivity {


    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_nick_name);
        hideNavigationView();
        editText = findViewById(R.id.et_search);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void modifyNickName() {
        String nickName = editText.getText().toString();
        if (StringUtils.isEmpty(nickName)) {
            showErrorToast("请输入昵称");
        }
        JPRequest request = new JPRequest<>(SetNickNameResult.class, UrlConst.getModifyNickName(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if (null == response || null == response.getResult()) {
                    return;
                }
                SetNickNameResult result = (SetNickNameResult) response.getResult();
                if (result != null && result.isSuccessful()) {
                    showSuccessToast("昵称修改成功");
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorToast(error.getErrorMessage());
            }
        });
        request.addParam("nickname", nickName);
        JPBaseModel baseModel = new JPBaseModel();
        baseModel.sendRequest(request);
    }
}
