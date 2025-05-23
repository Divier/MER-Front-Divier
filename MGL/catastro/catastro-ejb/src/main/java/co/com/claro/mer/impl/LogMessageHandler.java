/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.impl;

import co.com.claro.app.dtos.LogErrorDto;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author Fabian Duarte
 * duartey@globalhitss.com
 */
public class LogMessageHandler extends LogErrorDto implements SOAPHandler<SOAPMessageContext> {

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        try {
            SOAPMessage msg = context.getMessage();
            OutputStream os = new ByteArrayOutputStream();
            msg.writeTo(os);
            if (Boolean.TRUE.equals(context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))) {
                super.setRequest(os.toString());
            } else {
                super.setResponse(os.toString());
            }
        } catch (IOException | SOAPException ex) {
            Logger.getLogger(LogMessageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
        //Do nothing
    }

}
