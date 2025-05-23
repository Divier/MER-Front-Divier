/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amx.service.exp.operation.updateclosingtask.v1;

import co.com.claro.visitastecnicas.ws.proxy.HeaderRequest;
import com.amx.service.automaticclosingonyx.v1.AutomaticClosingOnyxInterface;
import com.amx.service.automaticclosingonyx.v1.AutomaticClosingOnyxSoapBindingQSService;
import com.amx.service.automaticclosingonyx.v1.FaultMessage;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyRequestType;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyResponseType;
import java.net.URL;

/**
 *
 * @author bocanegravm
 */
public class ClientOnix {

    public MERNotifyResponseType merNotify(HeaderRequest headerRequest,MERNotifyRequestType body, URL url) throws FaultMessage {

        AutomaticClosingOnyxSoapBindingQSService service = new AutomaticClosingOnyxSoapBindingQSService(url);
        AutomaticClosingOnyxInterface port = service.getAutomaticClosingOnyxSoapBindingQSPort();
        return port.merNotify(headerRequest, body);
    }

    public UpdateClosingTaskResponse updateClosingTask(HeaderRequest headerRequest, 
            UpdateClosingTaskRequest body, URL url) throws FaultMessage {
        
        AutomaticClosingOnyxSoapBindingQSService service = new AutomaticClosingOnyxSoapBindingQSService(url);
        AutomaticClosingOnyxInterface port = service.getAutomaticClosingOnyxSoapBindingQSPort();
        return port.updateClosingTask(headerRequest,body);
    }

}
