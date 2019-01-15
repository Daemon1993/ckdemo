package com.example.liubo.ckdemo.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;


public class PermissionsUtils {


    public interface PermissonsCallback {
        void resultOK();
    }

    /**
     * 存储权限
     *
     * @param activity
     * @param permissonsCallback
     */
    public static void requestStorage(final Activity activity, final PermissonsCallback permissonsCallback) {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "需要存储权限", "取消", "打开权限");
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                permissonsCallback.resultOK();
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                ToastUtils.toast("用户拒绝了存储权限");
            }
        }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, true, tip);
    }

    /**
     * 摄像头权限
     *
     * @param activity
     * @param permissonsCallback
     */
    public static void requestCAMERA(Context activity, final PermissonsCallback permissonsCallback) {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "需要摄像头权限", "取消", "打开权限");
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                permissonsCallback.resultOK();
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                ToastUtils.toast("用户拒绝了摄像头权限");
            }
        }, new String[]{Manifest.permission.CAMERA}, true, tip);
    }


    /**
     * 联系人权限
     *
     * @param activity
     * @param permissonsCallback
     */
    public static void requestCONTACTS(Activity activity, final PermissonsCallback permissonsCallback) {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "需要通讯录权限", "取消", "打开权限");
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                permissonsCallback.resultOK();
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                ToastUtils.toast("用户拒绝了通讯录权限");
            }
        }, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.GET_ACCOUNTS}, true, tip);
    }


    /**
     * 位置权限
     *
     * @param activity
     * @param permissonsCallback
     */
    public static void requestLOCATION(Activity activity, final PermissonsCallback permissonsCallback) {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "需要地理位置权限", "取消", "打开权限");
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                permissonsCallback.resultOK();
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                ToastUtils.toast("用户拒绝了地理位置权限");
            }
        }, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, true, tip);
    }


    /**
     * 手机状态权限
     *
     * @param activity
     * @param permissonsCallback
     */
    public static void requestPHONE(Activity activity, final PermissonsCallback permissonsCallback) {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "需要读取手机状态权限", "取消", "打开权限");
        PermissionsUtil.requestPermission(activity, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                permissonsCallback.resultOK();
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                ToastUtils.toast("用户拒绝了手机状态权限");
            }
        }, new String[]{Manifest.permission_group.PHONE, Manifest.permission.READ_PHONE_STATE}, true, tip);
    }


    /*-------------------------------------------------------------------------------------------*/
    public static void go2CaptureActivity(final Context context, final Intent intent, final int scanninGrequestCode) {
        PermissionsUtils.requestCAMERA(context, new PermissionsUtils.PermissonsCallback() {
            @Override
            public void resultOK() {

                ((Activity) context).startActivityForResult(intent,
                        scanninGrequestCode);
            }
        });
    }

}
