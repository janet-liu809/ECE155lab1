package lab1_202_08.uwaterloo.ca.lab1_202_08;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Lab1_202_08 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lab1_202_08);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        TextView lightText = (TextView) findViewById(R.id.lightFieldText);
        TextView lightMax = (TextView) findViewById(R.id.lightFieldMaxText);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        SensorEventListener light = new LightSensorEventListener(lightText, lightMax);
        sensorManager.registerListener(light, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        TextView accelerometerText = (TextView) findViewById(R.id.accelerometerText);
        TextView accelerometerMax = (TextView) findViewById(R.id.accelerometerMaxText);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener accelerometer = new AccelerometerSensorEventListener(accelerometerText, accelerometerMax);
        sensorManager.registerListener(accelerometer, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        TextView magneticFieldText = (TextView) findViewById(R.id.magneticFieldText);
        TextView magneticFieldMax = (TextView) findViewById (R.id.magneticFieldMaxText);
        Sensor magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        SensorEventListener magneticField = new MagneticSensorEventListener(magneticFieldText, magneticFieldMax);
        sensorManager.registerListener(magneticField, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);

        TextView rotationVectorText = (TextView) findViewById(R.id.rotationVectorText);
        TextView rotationVectorMax = (TextView) findViewById(R.id.rotationVectorMaxText);
        Sensor rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        SensorEventListener rotationVector = new RotataionVectorSensorEventListener(rotationVectorText, rotationVectorMax);
        sensorManager.registerListener(rotationVector, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

}

//Note: All se.values are given to us by Android OS as floats
class LightSensorEventListener implements SensorEventListener {
    private TextView output;
    private TextView outMax;
    private float maxLight = 0.0f;
    //do we have to initialize these TextViews to null?

    public LightSensorEventListener(TextView outputView, TextView outputMax){
        outMax = outputMax;
        output = outputView;
    }
    public void onAccuracyChanged(Sensor s, int i){

    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_LIGHT){
            output.setText("The Light Sensor Reading is:\n" +  String.valueOf(se.values[0]));
            if (se.values[0] > maxLight) {
                maxLight = se.values[0];
                outMax.setText("The Highest Light Sensor Reading is:\n" + String.valueOf(maxLight));
            }
        }
    }
}

class AccelerometerSensorEventListener implements SensorEventListener {
    private TextView output;
    private TextView outMax;
    private float maxX = -999.9f;
    private float maxY = -999.9f;
    private float maxZ = -999.9f;

    public AccelerometerSensorEventListener(TextView outputView, TextView outputMax) {
        output = outputView;
        outMax = outputMax;
    }

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            output.setText("The Accelerometer Sensor Reading is:\n(" +  (String.format("%.2f", se.values[0])) + ", " + (String.format("%.2f", se.values[1])) + ", " + (String.format("%.2f", se.values[2])) + ")");
            if (se.values[0] > maxX || se.values[1] > maxY || se.values[2] > maxZ) {
                //code to change outMax here
                //the condition inside if loop might be wrong
            }
        }
    }
}

class MagneticSensorEventListener implements SensorEventListener {
    private TextView output;
    private TextView outMax;
    private float maxX = -999.9f;
    private float maxY = -999.9f;
    private float maxZ = -999.9f;

    public MagneticSensorEventListener(TextView outputView, TextView outputMax){
        output = outputView;
        outMax = outputMax;
    }
    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            output.setText("The Magnetic Sensor Reading is: \n(" + String.format("%.2f", se.values[0]) + ", " + String.format("%.2f", se.values[1]) + ", " + String.format("%.2f", se.values[2]) + ")");
            if (se.values[0] > maxX || se.values[1] > maxY || se.values[2] > maxZ) {
                //same as above
            }
        }
    }
}

class RotataionVectorSensorEventListener implements SensorEventListener {
    private TextView output;
    private TextView outMax;
    private float maxX = -999.9f;
    private float maxY = -999.9f;
    private float maxZ = -999.9f;

    public RotataionVectorSensorEventListener(TextView outputView, TextView outputMax) {
        output = outputView;
        outMax = outputMax;
    }

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            output.setText("The Rotation Vector Sensor Reading is:\n(" +  (String.format("%.2f", se.values[0])) + ", " + (String.format("%.2f", se.values[1])) + ", " + (String.format("%.2f", se.values[2])) + ")");
            if (se.values[0] > maxX || se.values[1] > maxY || se.values[2] > maxZ) {
                //same as above
            }
        }
    }
}