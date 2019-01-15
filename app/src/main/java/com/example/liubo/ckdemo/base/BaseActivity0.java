package com.example.liubo.ckdemo.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.example.liubo.ckdemo.R;
import com.example.liubo.ckdemo.event_bus_bean.FinishBus;
import com.example.liubo.ckdemo.utils.StatusUtils;
import com.example.liubo.ckdemo.utils.ToastUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by liubo on 2018/10/29.
 */

public abstract class BaseActivity0 extends AppCompatActivity {

    public static final String BACK_MAIN = "back_main";
    public String Tag = "";

    public static final String Action_moduleId = "Action_moduleId";

    private DBaseDialog dBaseDialog;
    private ProgressDialog mProgressDialog;
    private boolean isDestory;
    private boolean back_main;
    public String moduleID;


//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    public void hideLoading() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }

    }


    public void showLoading() {
//        hideLoading();
        if (isDestory) {
            return;
        }

        if (mProgressDialog == null) {
            mProgressDialog = new BaseLoadingDialog(this, R.style.BaseDialog);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        EventBus.getDefault().register(this);

        moduleID = getIntent().getStringExtra(Action_moduleId);

//        ToastUtils.toast(moduleID + "");
        onCreateNew(savedInstanceState);

        setstatusBar();

        Tag = getClass().getSimpleName();


        back_main = getIntent().getBooleanExtra(BACK_MAIN, false);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            XLog.e("底部背景颜色");
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
//        }

        Log.d(" Das ", Tag);
    }

    public void setstatusBar() {
        StatusUtils.setStatusBarLightMode(this, Color.WHITE);
    }

    public abstract void onCreateNew(Bundle savedInstanceState);


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FinishBus event) {
        /* Do something */
        if (TextUtils.equals(event.tag, Tag)) {
            finish();
        }
    }

    ;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            back();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    @Override
    protected void onDestroy() {
        hideLoading();

        isDestory = true;

        mProgressDialog = null;



        dismissDBaseDialog();

        EventBus.getDefault().unregister(this);
        super.onDestroy();


    }


    public void setCenterText(String name) {
        TextView tv_title = null;
        try {
            tv_title = (TextView) findViewById(R.id.tv_title);
        } catch (Exception e) {
//			e.printStackTrace();
            return;
        }

        if (tv_title == null) {
            return;
        }

        tv_title.setText(name);

    }

    public String getCenterText() {
        try {
            TextView tv_title = (TextView) findViewById(R.id.tv_title);
            return tv_title.getText().toString();
        } catch (Exception e) {
//			e.printStackTrace();
            return "";
        }
    }

    public void back() {
        this.finish();
    }

    public void finish(View view) {

        back();
    }


    public void showBaseDialog(String text, String left, String right, View.OnClickListener leftc, View.OnClickListener rightc) {

        if (dBaseDialog == null) {
            dBaseDialog = new DBaseDialog();
            dBaseDialog.setLeftListener(leftc);
            dBaseDialog.setRightListener(rightc);
        }

        if (dBaseDialog != null
                && dBaseDialog.getDialog() != null
                && dBaseDialog.getDialog().isShowing()
                && !dBaseDialog.isRemoving()) {

            dBaseDialog.setShowContent(text, left, right);
            return;
        }
        dBaseDialog.setShowContent(text, left, right);
        dBaseDialog.show(getSupportFragmentManager(), "dbase" + Tag);
    }

    public void showBaseDialog(String text, View.OnClickListener leftc, View.OnClickListener rightc) {
        showBaseDialog(text, "", "", leftc, rightc);
    }


    public void dismissDBaseDialog() {
        if (dBaseDialog == null || dBaseDialog.isResumed()) {
            return;
        }
        dBaseDialog.dismiss();
    }


    public void setRightTextShow(String text, int color) {
        try {
            TextView das_right_title_text = findViewById(R.id.das_right_title_text);
            das_right_title_text.setVisibility(View.VISIBLE);
            KLog.e("color " + color);
            if (color != 0) {
                das_right_title_text.setTextColor(color);
            }
            if (!TextUtils.isEmpty(text)) {
                das_right_title_text.setText(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRightAction(View.OnClickListener onClickListener) {
        try {
            TextView das_right_title_text = findViewById(R.id.das_right_title_text);
            das_right_title_text.setOnClickListener(onClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
