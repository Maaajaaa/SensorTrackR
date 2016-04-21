package com.example.mattis.sensortrackr;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class SensorListFragment extends ListFragment implements OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        //toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT);
        /*toast.setText("Item: " + position);
        toast.show();*/
        AxisSelectionDialog dialog = new AxisSelectionDialog();
        //dialog.setTitle("Choose Axes of " + planets[position]);
        dialog.show(getFragmentManager(), "multi_demo");
    }
}