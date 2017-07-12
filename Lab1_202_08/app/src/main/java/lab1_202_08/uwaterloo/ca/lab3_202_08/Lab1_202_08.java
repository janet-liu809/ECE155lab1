package lab1_202_08.uwaterloo.ca.lab3_202_08;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class Lab1_202_08 extends AppCompatActivity {
    //I put all the TextView and Graph at the top to make them global variables in the bundle
    public TextView gesture;
    private FSM_Y FSM_MY_FSM;
    private GameLoopTask GLT;
    public String buttonDirection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_202_08);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        RelativeLayout RL = (RelativeLayout) findViewById(R.id.relativeLayout);

        //RL.setLayoutParams(new RelativeLayout.LayoutParams(1440, 120));
        //RL.LayoutParams(1440,120);
        RL.getLayoutParams().height = 1650;
        RL.getLayoutParams().width = 1650;
        ImageView gameboard = new ImageView(getApplicationContext());
        gameboard.setY(-100f);
        gameboard.setX(-100f);
        gameboard.setScaleY(0.88f);
        gameboard.setScaleX(0.88f);
        gameboard.setImageResource(R.drawable.gameboard);
        RL.addView(gameboard);
        //RL.setBackgroundResource(R.drawable.gameboard);

        //Create all the needed TextView
        gesture = new TextView(getApplicationContext());
        gesture.setTextColor(Color.BLACK);
        gesture.setTextSize(35);
        RL.addView(gesture);

        GameLoopTask myGameLoopTask = new GameLoopTask(this,RL,getApplicationContext());

        buttonDirection = "NO_MOVEMENT";

        Button buttonUp;
        buttonUp = new Button(getApplicationContext());
        buttonUp.setText("UP");
        buttonUp.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonDirection = "UP";
                        System.out.println("UP");
                    }
                }
        );
        buttonUp.setX(1002);
        buttonUp.setY(1450);
        RL.addView(buttonUp);

        Button buttonDown;
        buttonDown = new Button(getApplicationContext());
        buttonDown.setText("DOWN");
        buttonDown.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonDirection = "DOWN";
                        System.out.println("DOWN");
                    }
                }
        );
        buttonDown.setX(640);
        buttonDown.setY(1450);
        RL.addView(buttonDown);

        Button buttonRight;
        buttonRight = new Button(getApplicationContext());
        buttonRight.setText("RIGHT");
        buttonRight.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonDirection = "RIGHT";
                        System.out.println("RIGHT");
                    }
                }
        );
        buttonRight.setX(278);
        buttonRight.setY(1450);
        RL.addView(buttonRight);

        Button buttonLeft;
        buttonLeft = new Button(getApplicationContext());
        buttonLeft.setText("LEFT");
        buttonLeft.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonDirection = "LEFT";
                        System.out.println("LEFT");
                    }
                }
        );
        buttonLeft.setX(-78f);
        buttonLeft.setY(1450);
        RL.addView(buttonLeft);

        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener accelerometer = new AccelerometerSensorEventListener(gesture, myGameLoopTask, buttonDirection);
        sensorManager.registerListener(accelerometer, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);



        //Sets up the game task, so it is called every 50 milliseconds
        myGameLoopTask.setStringDir(buttonDirection);
        Timer myGameLoop = new Timer();
        myGameLoop.schedule(myGameLoopTask,50,50);

        //Save all the max readings, and stores them with in a key-value pair
        if (savedInstanceState != null) {

        }

        FSM_MY_FSM = new FSM_Y(gesture);

    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

    }

    private class onButtonPressed{
        private String buttonDirection;
        private GameLoopTask GLT;

        private void onButtonPressed(String buttonInput, GameLoopTask myGameLoopTask){
            this.buttonDirection = buttonInput;
            this.GLT = myGameLoopTask;
        }
        private void setDirection(){
            GLT.setStringDir(buttonDirection);
        }
    }

    //Note: All se.values are given to us by Android OS as floats

    private class AccelerometerSensorEventListener implements SensorEventListener {

        private TextView gesture;
        private final float constant = 25;
        private float[] previousNum;
        private float[] tempElem = new float[3];
        private GameLoopTask GLT;
        private String buttonDirection;

        private AccelerometerSensorEventListener(TextView gestureTV, GameLoopTask myGameLoopTask, String buttonInput) {
            gesture = gestureTV;
            GLT = myGameLoopTask;
            buttonDirection = buttonInput;
            //If the maxValue for the sensor exists, then use regex to extract the relevant information from the TextView
        }

        public void onAccuracyChanged(Sensor s, int i) {

        }

        public void onSensorChanged(SensorEvent se) {
            if (se.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //If this is the first number, set that as the previous number
                if (previousNum == null) {
                    previousNum = se.values;
                } else {
                    //This goes through the X,Y,and Z values
                    for (int i = 0; i < 3; i++) {
                        //This just finds the difference between the two and divides it by a constant number
                        if (se.values[i] > previousNum[i]) {
                            tempElem[i] = (se.values[i] - previousNum[i]) / constant;
                        } else if (se.values[i] < previousNum[i]) {
                            tempElem[i] = (se.values[i] + previousNum[i]) / constant;
                        } else {
                            tempElem[i] = se.values[i];
                        }
                    }
                }
                //FSM_MY_FSM.activateFSM(tempElem[2], tempElem[0]);
                //String currentState = FSM_MY_FSM.getState();
                //GLT.setStringDir(currentState);
                //GLT.setStringDir(buttonDirection);

            }
        }

    }
}
