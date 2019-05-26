package com.example.danie.battleshipnewversion;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class LocalService extends Service implements SensorEventListener {
    private final IBinder mBinder = new MyBinder();
    SensorManager sensorManager;
    Sensor s1;
    private float theX , x;
    private int counter = 0 ;
    private ServiceCallbacks serviceCallbacks;


    private boolean firstMeasurement = true;
    private boolean firstChangeTime = true;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE );
        s1 = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this , s1 , SensorManager.SENSOR_DELAY_NORMAL);
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        float[] values = event.values;
        if(firstMeasurement == true){
            //Movement
            theX = values[0];
            firstMeasurement = false;


        }
        else{
            values = event.values;
            //Movement
            x = values[0];

        }
        if(MainActivity.startPlay == true) {
            if (Math.abs(theX - x) > 0.12 ) {
                counter++;
                if (counter == 12) {
                    counter = 0;
                    if (serviceCallbacks != null) {
                        serviceCallbacks.moveTheShips();
                    }

            }
       }
        else {
            if (serviceCallbacks != null) {
                serviceCallbacks.stopMoveShips();
            }
            counter = 0;
        }
        }
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        /// not in use

    }

    public void setCallbacks(ServiceCallbacks callbacks) {
        serviceCallbacks = callbacks;
    }


    public class MyBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }


}