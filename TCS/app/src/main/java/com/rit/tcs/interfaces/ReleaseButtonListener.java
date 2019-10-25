package com.rit.tcs.interfaces;


import com.rit.tcs.bussinessobject.Assets;
import com.rit.tcs.bussinessobject.Space;

import java.io.Serializable;

/**
 * Created by A688629 on 08-08-2018.
 */

public interface ReleaseButtonListener extends Serializable {
    public void setOnReleased(Assets space);
    public void setOnClicked(Space space);
}
