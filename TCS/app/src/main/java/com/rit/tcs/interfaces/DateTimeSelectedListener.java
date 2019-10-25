package com.rit.tcs.interfaces;

import java.io.Serializable;

/**
 * Created by A688629 on 08-08-2018.
 */

public interface DateTimeSelectedListener extends Serializable {
    public void setOnDateSelected(int type, String date);
    public void setOnTimeSelected(int type, String time);
}
