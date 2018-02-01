package com.hpm.sp.streaminfoportal.Interfaces;

import java.util.List;

/**
 * Created by kumardivyarajat on 1/21/18.
 */

public interface ResponseInterface {
    public void onResponseFromServer(List<?> objects, Exception e);
}
