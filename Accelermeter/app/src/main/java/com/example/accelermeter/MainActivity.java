package com.example.accelermeter;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager; //センサーマネージャー
    Sensor accelSensor; //センサー
    Sensor gyroSensor;
    MySensorEventListener mySensorEventListener;

    TextView accelX;
    TextView accelY;
    TextView accelZ;
    TextView gyroX;
    TextView gyroY;
    TextView gyroZ;
    TextView direction;

    int UPDATE_INTERVAL = 1000;
    long lastUpdate;
    long lastX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelX = findViewById(R.id.accelX);
        accelY = findViewById(R.id.accelY);
        accelZ = findViewById(R.id.accelZ);
        gyroX = findViewById(R.id.gyroX);
        gyroY = findViewById(R.id.gyroY);
        gyroZ = findViewById(R.id.gyroZ);
        direction = findViewById(R.id.direction);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(accelSensor == null){
            Toast.makeText(this,"加速度センサーが利用できません",Toast.LENGTH_SHORT).show();
        }
        if(gyroSensor == null){
            Toast.makeText(this,"ジャイロセンサーが利用できません",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(accelSensor != null){
            mySensorEventListener = new MySensorEventListener();
            sensorManager.registerListener(mySensorEventListener,accelSensor,SensorManager.SENSOR_DELAY_UI);
        }
        if(gyroSensor != null){
            mySensorEventListener = new MySensorEventListener();
            sensorManager.registerListener(mySensorEventListener,gyroSensor,SensorManager.SENSOR_DELAY_UI);
        }
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    protected void onPause(){
        sensorManager.unregisterListener(mySensorEventListener);
        super.onPause();
    }

    class MySensorEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                long actualTime = System.currentTimeMillis();

                if(actualTime - lastUpdate > UPDATE_INTERVAL){
                    lastUpdate = actualTime;

                    float x = sensorEvent.values[0];
                    System.out.println("x = " + String.valueOf(x));
                    float y = sensorEvent.values[1];
                    System.out.println("y = " + String.valueOf(y));
                    float z = sensorEvent.values[2];
                    System.out.println("z = " + String.valueOf(z));

                    accelX.setText(String.valueOf(x));
                    accelY.setText(String.valueOf(y));
                    accelZ.setText(String.valueOf(z));
                }
            }
            if(sensorEvent.sensor.getType()==Sensor.TYPE_GYROSCOPE){
                long actualTime = System.currentTimeMillis();

                if(actualTime - lastUpdate > UPDATE_INTERVAL){
                    lastUpdate = actualTime;

                    float x = sensorEvent.values[0];
                    System.out.println("x = " + String.valueOf(x));
                    float y = sensorEvent.values[1];
                    System.out.println("y = " + String.valueOf(y));
                    float z = sensorEvent.values[2];
                    System.out.println("z = " + String.valueOf(z));

                    gyroX.setText(String.valueOf(x));
                    gyroY.setText(String.valueOf(y));
                    gyroZ.setText(String.valueOf(z));

                    if(lastX < x){
                        direction.setText("→");
                    }if(lastX == x){
                        direction.setText("・");
                    }else{
                        direction.setText("←");
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}
