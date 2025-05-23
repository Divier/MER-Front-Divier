/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amx.schema.operation.getmassivefailurerequest.v1;

import com.amx.service.massivefailures.v1.MassiveFailure;
import com.amx.service.massivefailures.v1.MassiveFailureSoapBindingQSService;
import java.net.URL;

/**
 *
 * @author bocanegravm
 */
public class ClientMaximo {

    
     public GetMassiveFailureResponse getMassiveFailureRequest(com.amx.schema.operation.getmassivefailurerequest.v1.GetMassiveFailureRequest parameters,URL url) {
        MassiveFailureSoapBindingQSService service = new MassiveFailureSoapBindingQSService(url);
        MassiveFailure port = service.getMassiveFailureSoapBindingQSPort();
        return port.getMassiveFailureRequest(parameters);
    }
    
    
}
