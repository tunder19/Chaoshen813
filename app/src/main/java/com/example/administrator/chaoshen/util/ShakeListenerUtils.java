package com.example.administrator.chaoshen.util;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.youth.xframe.utils.log.XLog;

public class ShakeListenerUtils implements SensorEventListener {
    private WebActivity webActivity;
    private int count;
    private Vibrator vibrator;
    /**
     * 检测的时间间隔
     */
    static final int UPDATE_INTERVAL = 100;
    /**
     * 上一次检测的时间
     */
    long mLastUpdateTime;
    /**
     * 上一次检测时，加速度在x、y、z方向上的分量，用于和当前加速度比较求差。
     */
    float mLastX, mLastY, mLastZ;

    /**
     * 摇晃检测阈值，决定了对摇晃的敏感程度，越小越敏感。
     */
    public int shakeThreshold = 3000;


    public ShakeListenerUtils(WebActivity context,Vibrator vibrator) {
        super();
        this.vibrator=vibrator;
        this.webActivity = context;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;

        if (sensorType == Sensor.TYPE_ACCELEROMETER) {

//			正常情况下，任意轴数值最大就在9.8~10之间，只有在突然摇动手机
//			  的时候，瞬时加速度才会突然增大或减少。   监听任一轴的加速度大于17即可
            int shake=12;

            if ((Math.abs(values[0]) > shake || Math.abs(values[1]) > shake || Math
                    .abs(values[2]) > shake)) {
                toDo();
                //  Toast.makeText(webActivity,"要狂",Toast.LENGTH_LONG).show();

            }
        }
       /* long currentTime = System.currentTimeMillis();
        long diffTime = currentTime - mLastUpdateTime;
        if (diffTime < UPDATE_INTERVAL) {
            return;
        }
        mLastUpdateTime = currentTime;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float deltaX = x - mLastX;
        float deltaY = y - mLastY;
        float deltaZ = z - mLastZ;
        mLastX = x;
        mLastY = y;
        mLastZ = z;
        float delta = (float) (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / diffTime * 10000);
        XLog.e("----------shakeWeb--------delta="+delta);
        // 当加速度的差值大于指定的阈值，认为这是一个摇晃
        if (delta > shakeThreshold) {
            vibrator.vibrate(200);
            toDo();
        }*/
    }

    private void toDo() {
        //XLog.e("----------shakeWeb--------"+count);
       // count++;

        webActivity.shakeWeb();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //当传感器精度改变时回调该方法，Do nothing.
    }

}

