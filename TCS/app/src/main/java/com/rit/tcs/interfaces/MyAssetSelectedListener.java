package com.rit.tcs.interfaces;


import com.rit.tcs.bussinessobject.Allassets;
import com.rit.tcs.bussinessobject.Myassets;

import java.io.Serializable;

/**
 * Created by A688629 on 08-08-2018.
 */

public interface MyAssetSelectedListener extends Serializable {
    public void setOnClicked(Myassets allassets,String locker,Boolean occupancyy,Integer booking_id,String serial_no,Integer position);
}
