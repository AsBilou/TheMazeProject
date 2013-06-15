package net.bilou.themaze;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Bilou on 01/06/13.
 */
public class MazeActivity extends Activity implements SensorEventListener {

    //private static final String LOG_TAG = "SensorsAccelerometer";
    float x, y, z;
    float maxX = 0, maxY = 0, maxZ = 0;
    float minX = 0, minY = 0, minZ = 0;
    float maxRange;


    LinearLayout.LayoutParams lParamsName;
    LinearLayout xyAccelerationLayout;
    mazeView xyAccelerationView;
    private Display mDisplay;
    SensorManager sensorManager;
    Sensor accelerometer;
    private int sensorType;
    private static final int ACCELE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        sensorType = ACCELE;

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mDisplay = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        xyAccelerationLayout = (LinearLayout) findViewById(R.id.layoutOfXYAcceleration);

        xyAccelerationView = new mazeView(this);
        // Definition des parametres du layout et ajoute la vue au layout
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        xyAccelerationLayout.addView(xyAccelerationView, layoutParam);

    }
    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, accelerometer);
        //Pause du thread de la vue
        xyAccelerationView.isPausing.set(true);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);

        // relance le thread de la vue
        xyAccelerationView.isPausing.set(false);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // kill le thread de la vue
        xyAccelerationView.isRunning.set(false);
        super.onDestroy();
    }

    /**
     * Au changement de precision du sensor.
     *
     * @param sensor
     *            type de sensor.
     * @param accuracy
     *            niveau de sensibilite
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Au changement du sensor.
     *
     * @param event
     *            Type d'evenemnt.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (((event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) && (sensorType == ACCELE))) {

            // Change les valeurs d'acceleration x et y en fonction de l'orientation du telephone
            switch (mDisplay.getRotation()) {
                case Surface.ROTATION_0:
                    x = event.values[0];
                    y = event.values[1];
                    break;
                case Surface.ROTATION_90:
                    x = -event.values[1];
                    y = event.values[0];
                    break;
                case Surface.ROTATION_180:
                    x = -event.values[0];
                    y = -event.values[1];
                    break;
                case Surface.ROTATION_270:
                    x = event.values[1];
                    y = -event.values[0];
                    break;
            }
            z = event.values[2];
        }
    }

    /**
     * Gestion de la vibration.
     *
     */
    public void phoneVibrate(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(50);
    }

    /**
     * Verifie si les deux obect Rect ce croise.
     *
     * @param rect1
     *            premier object a verifier.
     * @param rect2
     *            deuxieme objet a verifier
     * @return True si les object ce croise, false si rien.
     */
    public boolean onIntersects(Rect rect1, Rect rect2){
        if(Rect.intersects(rect1, rect2)){
            return true;
        }
        return false;
    }

    /**
     * Creation d'une alerte avec un message.
     *
     * @param message
     *            message a afficher.
     */
    public void alert(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Congratulation");
        alertDialog.setMessage(message);
        alertDialog.setButton("Main Menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //here you can add functions
                finish();

            } });
        alertDialog.setButton2("Replay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //here you can add functions
                onResume();

            } });
        alertDialog.show();
        onPause();
    }
}
