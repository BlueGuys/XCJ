package com.hongyan.xcj.modules.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.core.AccountManager;
import com.hongyan.xcj.modules.search.SearchActivity;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.utils.SoftInputUtils;
import com.hongyan.xcj.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

public class SetNickNameActivity extends BaseActivity {


    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_nick_name);
        hideNavigationView();
        ImageView imageClose = findViewById(R.id.image_close);
        ImageView imageCommit = findViewById(R.id.image_commit);
        editText = findViewById(R.id.et_set_nick_name);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyNickName();
            }
        });
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
                    AccountManager.getInstance().refresh();
                    SoftInputUtils.hideSoftKeyPad(SetNickNameActivity.this, editText);
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
        request.setCheckLogin(true);
        JPBaseModel baseModel = new JPBaseModel();
        baseModel.sendRequest(request);
    }
}