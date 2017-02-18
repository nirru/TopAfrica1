package com.africa.annauiare.uiView;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * Created by ericbasendra on 09/10/16.
 */

public class SpineerView {
    //class members
    String businessType[];
    ArrayAdapter<String> spinnerAdapter;
    Context mContext;
    Spinner spinner;
    OnSpinnerSelected onSpinnerSelected;
    public SpineerView(Context mContext ,String businessType[] ,Spinner spinner, OnSpinnerSelected onSpinnerSelected){
        this.mContext = mContext;
        this.businessType = businessType;
        this.onSpinnerSelected = onSpinnerSelected;
        this.spinner = spinner;
        // Initialize and set Adapter
        spinnerAdapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item, businessType);
    }

    public void openSpinner(){
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String employee_number = spinner.getItemAtPosition(i).toString();
                onSpinnerSelected.OnItemSelected(employee_number);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public interface OnSpinnerSelected{
        public void OnItemSelected(String item);
    }


}
