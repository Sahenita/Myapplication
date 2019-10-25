package com.rit.tcs.fragment;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;
import com.rit.tcs.interfaces.DateTimeSelectedListener;
import com.rit.tcs.interfaces.showinterface;

import java.util.Calendar;

/**
 * Created by A688629 on 11-09-2018.
 */

public class TimePickerFragment extends DialogFragment {
    private static boolean shown = false;

    int type = 1;
    DateTimeSelectedListener selectedListener = null;
    com.rit.tcs.interfaces.showinterface showinterface = null;
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
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), dateSetListener, hour, minute, true);
    }

    private TimePickerDialog.OnTimeSetListener dateSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String retRes = "";
                    int hr = view.getHour();
                    if (hr >= 12) {
                        retRes = " PM";
                        if(hr!=12)
                        hr = hr % 12;
                    } else {
                        retRes = " AM";
                    }
                    retRes = ((hr > 9) ?
                            hr : "0" + hr) + ":" + ((view.getMinute() > 9) ?
                            view.getMinute() : "0" + view.getMinute()) + retRes;
                    selectedListener.setOnTimeSelected(type, retRes);
                }
            };




}
