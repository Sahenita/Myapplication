package com.rit.tcs.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import com.rit.tcs.interfaces.DateTimeSelectedListener;
import com.rit.tcs.interfaces.showinterface;

import java.util.Calendar;

/**
 * Created by A688629 on 11-09-2018.
 */

public class DatePickerFragment extends DialogFragment {
    private static boolean shown = false;

    int type = 1;
    DateTimeSelectedListener selectedListener = null;
    showinterface showinterface = null;

    private String[] monthArr = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    @Override
    public void show(FragmentManager manager, String tag) {
        if (shown) return;

        super.show(manager, tag);
        shown = true;
       // showinterface.setOnClicked(shown);


    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        shown = false;
        super.onDismiss(dialog);
        if(!shown)
        showinterface.setOnClicked(shown);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            type = getArguments().getInt("type", 1);
            switch (type) {
                case 1:
                case 2:
                    selectedListener = (LockerTimeFragment) getArguments().getSerializable("friendsID");
                    showinterface = (LockerTimeFragment) getArguments().getSerializable("friendsID1");

                    break;
            }
        } catch (Exception e) {
        }
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    String retDate = ((view.getDayOfMonth() > 9) ?
                            view.getDayOfMonth() : "0" + view.getDayOfMonth()) + " " + monthArr[view.getMonth()] + ", " + view.getYear();
                    selectedListener.setOnDateSelected(type, retDate);
                }
            };
}
