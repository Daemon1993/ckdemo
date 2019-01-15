package com.example.liubo.ckdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.liubo.ckdemo.base.BaseActivity0;

import com.example.liubo.ckdemo.serial_port_base.DataUtils;
import com.example.liubo.ckdemo.utils.PermissionsUtils;
import com.github.dfqin.grantor.PermissionsUtil;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import cn.shorr.serialport.SerialPortConfig;
import cn.shorr.serialport.SerialPortFinder;
import cn.shorr.serialport.SerialPortUtil;
import cn.shorr.serialport.SerialRead;
import cn.shorr.serialport.SerialWrite;

public class MainActivity extends BaseActivity0 {

    String OPEN_DOOR = "010100000000FFFF";

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        View tv_center = findViewById(R.id.tv_center);
        final View bt_send = findViewById(R.id.bt_send);


        //5.获取设备所有的串口信息
        SerialPortFinder serialPortFinder = new SerialPortFinder();
        String[] devices = serialPortFinder.getAllDevicesPath();

        KLog.e(Arrays.toString(devices));


        SerialPortUtil serialPortUtil = new SerialPortUtil(this,
                new SerialPortConfig("/dev/ttyS3", 115200));
        //设置为调试模式，打印收发数据
        serialPortUtil.setDebug(true);
        //绑定串口服务
        serialPortUtil.bindService();

        //串口0数据读取监听（可在不同Activity中同时设置监听）
        SerialRead serial0Read = new SerialRead(this);
        serial0Read.registerListener(0/*默认为0，此参数可省略*/, new Serial0ReadListener());

//3.串口发送数据
        SerialWrite.sendData(this, 0, "111".getBytes());
    }

    //2.设置串口读取监听
    private class Serial0ReadListener implements SerialRead.ReadDataListener {
        @Override
        public void onReadData(byte[] data) {
            KLog.e(DataUtils.ByteArrToHex(data));
        }
    }

    /**
     * 用EventBus进行线程间通信，也可以使用Handler
     *
     * @param string
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String string) {
        KLog.d("获取到了从传感器发送到Android主板的串口数据 " + string);

    }

}
