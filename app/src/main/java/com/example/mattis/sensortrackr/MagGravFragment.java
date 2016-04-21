package com.example.mattis.sensortrackr;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import java.lang.Math;
import java.text.DecimalFormat;

/**
 * Created by mattis on 16.04.16.
 */
public class MagGravFragment extends Fragment implements SensorEventListener {
    SensorManager sensorManager;
    Sensor gravitySensor;
    Sensor magFieldSensor;
    TextView gravityText;
    TextView magFieldText;
    SeekBar gravityDecimalsSeekBar;
    SeekBar magFieldDecimalsSeekBar;
    DecimalFormat gravityDecimalFormat;
    DecimalFormat magFieldDecimalFormat;

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public  void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("#0.");
                    if(seekBar == gravityDecimalsSeekBar)
                    {
                        for(int i = 0; i < progress; i++)
                        {
                            stringBuilder.append("0");
                        }
                        gravityDecimalFormat = new DecimalFormat(stringBuilder.toString());
                        //Log.d("gravityDecFormat",stringBuilder.toString());
                    }
                    if(seekBar == magFieldDecimalsSeekBar)
                    {
                        for(int i = 0; i < progress; i++)
                        {
                            stringBuilder.append("0");
                        }
                        magFieldDecimalFormat = new DecimalFormat(stringBuilder.toString());
                        //Log.d("magFieldDecFormat",stringBuilder.toString());
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar){}
                @Override public void onStopTrackingTouch(SeekBar seekBar){}
            };

    private TextView.OnClickListener textOnClickListener =
            new TextView.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.d("Sensor Dialog", "triggered");

                }

            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) this.getContext().getSystemService(Context.SENSOR_SERVICE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        magFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gravityDecimalFormat = new DecimalFormat("#0.00000");
        magFieldDecimalFormat = new DecimalFormat("#0.00");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mag_grav_fragment, container, false);
        gravityText = (TextView) view.findViewById(R.id.gravity_text);

        TextView gravityLabel = (TextView) view.findViewById(R.id.gravity_label);
        gravityLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Sensor Dialog", "triggered");
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("name: " + gravitySensor.getName() +
                                    "\nvendor: " + gravitySensor.getVendor() +
                                    "\nmax range: " + gravitySensor.getMaximumRange() +
                                    "\nresolution : " + gravitySensor.getResolution())
                        .setTitle("acceleration sensor details");

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        TextView magFieldLabel = (TextView) view.findViewById(R.id.magField_label);
        magFieldLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Sensor Dialog", "triggered");
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("name: " + magFieldSensor.getName() +
                        "\nvendor: " + magFieldSensor.getVendor() +
                        "\nmax range: " + magFieldSensor.getMaximumRange() +
                        "\nresolution : " + magFieldSensor.getResolution())
                        .setTitle("magnitude sensor details");

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        //gravityText.setText("Hello World");
        magFieldText = (TextView) view.findViewById(R.id.magField_text);
        //magFieldText.setText("Hello Magnetfield");
        gravityDecimalsSeekBar = (SeekBar) view.findViewById(R.id.gravityDecimals_seekBar);
        gravityDecimalsSeekBar.setMax(15);
        gravityDecimalsSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        magFieldDecimalsSeekBar = (SeekBar) view.findViewById(R.id.magFieldDecimals_seekBar);
        magFieldDecimalsSeekBar.setMax(15);
        magFieldDecimalsSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor == gravitySensor)
        {
            double gravity = Math.sqrt(event.values[0]*event.values[0] +
                    event.values[1]*event.values[1] + event.values[2]*event.values[2]);
            gravityText.setText(gravityDecimalFormat.format(gravity));
        }
        if(event.sensor == magFieldSensor)
        {
            double magField = Math.sqrt(event.values[0]*event.values[0] +
                    event.values[1]*event.values[1] + event.values[2]*event.values[2]);
            magFieldText.setText(magFieldDecimalFormat.format(magField));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magFieldSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
