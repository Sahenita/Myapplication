package com.rit.tcs.interfaces;


import com.rit.tcs.bussinessobject.Allassets;
import com.rit.tcs.bussinessobject.Assets;
import com.rit.tcs.bussinessobject.Space;

import java.io.Serializable;

/**
 * Created by A688629 on 08-08-2018.
 */

public interface CategorySelectedListener extends Serializable {
    public void setOnClicked(Allassets allassets,Integer position);
}
