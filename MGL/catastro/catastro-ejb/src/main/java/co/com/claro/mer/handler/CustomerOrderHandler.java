/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.handler;

import co.com.claro.app.dtos.LogErrorDto;
import co.com.claro.mer.impl.LogMessageHandler;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Fabian Duarte
 * duartey@globalhitss.com
 */
public class CustomerOrderHandler extends LogErrorDto implements SOAPHandler<SOAPMessageContext> {
    
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(CustomerOrderHandler.class);

    @Override
    public Set<QName> getHeaders() {
        return new HashSet<>();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        SOAPMessage msg = context.getMessage();
        if (Boolean.TRUE.equals(context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))) {
            try {
                SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();
                SOAPElement headerRequest = header.addChildElement(envelope.createName("headerRequest", "v1", "http://www.amx.com/CO/Schema/ClaroHeaders/v1"));
                SOAPElement transactionId = headerRequest.addChildElement("transactionId", "v1");
                transactionId.setValue("?");

                SOAPElement system = headerRequest.addChildElement("system", "v1");
                system.setValue("?");

                SOAPElement target = headerRequest.addChildElement("target", "v1");
                target.setValue("?");

                SOAPElement user = headerRequest.addChildElement("user", "v1");
                user.setValue("?");

                SOAPElement password = headerRequest.addChildElement("password", "v1");
                password.setValue("?");
               
                SOAPElement requestDate = headerRequest.addChildElement("requestDate", "v1");
                requestDate.setValue("?");
               
                SOAPElement ipApplication = headerRequest.addChildElement("ipApplication", "v1");
                ipApplication.setValue("?");
               
                SOAPElement traceabilityId = headerRequest.addChildElement("traceabilityId", "v1");
                traceabilityId.setValue("?");
               
                msg.saveChanges();
            } catch (SOAPException e) {
                LOGGER.error("Error en handleMessage: ", e);
                return false;
            }
        }
        logToSystemOut(context);
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logToSystemOut(context);
        return true;
    }

    @Override
    public void close(MessageContext context) {
        //Do nothing
    }

    private void logToSystemOut(SOAPMessageContext smc) {
        try {
            SOAPMessage message = smc.getMessage();
            OutputStream os = new ByteArrayOutputStream();
            message.writeTo(os);
            if (Boolean.TRUE.equals(smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))) {
                super.setRequest(os.toString());
            } else {
                super.setResponse(os.toString());
            }
        } catch (IOException | SOAPException ex) {
            Logger.getLogger(LogMessageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
