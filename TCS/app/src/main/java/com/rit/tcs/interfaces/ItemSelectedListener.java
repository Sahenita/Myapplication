package com.rit.tcs.interfaces;


import com.rit.tcs.bussinessobject.Allassets;
import com.rit.tcs.bussinessobject.Assets;

import java.io.Serializable;

/**
 * Created by A688629 on 08-08-2018.
 */

public interface ItemSelectedListener extends Serializable {
    public void setOnItemClicked(Assets assets);
}
