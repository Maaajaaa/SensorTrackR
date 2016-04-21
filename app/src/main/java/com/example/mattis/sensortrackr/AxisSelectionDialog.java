package com.example.mattis.sensortrackr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mattis on 02.01.16.
 */
public class AxisSelectionDialog extends  DialogFragment {

    ArrayList<String> list = new ArrayList<String>();
    String title = "Choose Axes";
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle saveInstanceState){
        final String[] items = getResources().getStringArray(R.array.Axes);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMultiChoiceItems(R.array.Axes, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                //An item was selected
                if(isChecked) {
                    list.add(items[which]);
                }
                else if(list.contains(items[which])) {
                    list.remove(items[which]);
                }
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selections = "";
                for (String ms:list)
                {
                    selections += "\n" + ms;
                }
                Toast.makeText(getActivity(), "Axes: " + selections, Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();


    }

    /*public void setTitle(String newTitle)
    {
        title = newTitle;
    }*/
}
