package com.example.liubo.ckdemo.base;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.liubo.ckdemo.R;


/**
 * Created by yuanyf on 2018/11/5.
 */

public class BaseLoadingDialog extends ProgressDialog {

    private TextView tv_msg;

    public BaseLoadingDialog(Context context) {
//        super(context);
        this(context, R.style.BaseDialog);
    }

    public BaseLoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
//        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.loading_base_layout);

        tv_msg=(TextView)findViewById(R.id.tv_msg);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

    }

    @Override
    public void show() {

        if(isShowing()){
            return;
        }

        super.show();
    }

    public void setMsg(String msg){
        if(tv_msg==null){
            return;
        }
        tv_msg.setText(msg);
    }
}
