/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amx.schema.operation.getmassivefailurerequest.v1;

import com.amx.co.schema.claroheaders.v1.HeaderRequest;
import com.amx.service.massivefailures.v1.MassiveFailure;
import com.amx.service.massivefailures.v1.MassiveFailureSoapBindingQSService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author bocanegravm
 */
public class ClientMax {
    
    private static final Logger LOGGER = LogManager.getLogger(ClientMax.class);

   public static void main(String[] args) throws DatatypeConfigurationException {
        // TODO code application logic here
        try {
            GetMassiveFailureRequest getMassiveFailureRequest = new GetMassiveFailureRequest();
            HeaderRequest headerRequest = new HeaderRequest();

            GregorianCalendar c = new GregorianCalendar();
            Date fecha = new Date();
            c.setTime(fecha);

            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            headerRequest.setRequestDate(date2);
            headerRequest.setSystem("");
            headerRequest.setTransactionId("");
            getMassiveFailureRequest.setHeaderRequest(headerRequest);

            MassiveFailureRequest massiveFailureRequest = new MassiveFailureRequest();
            massiveFailureRequest.setCcmm("7655");
            getMassiveFailureRequest.setMassiveFailureRequest(massiveFailureRequest);


            GetMassiveFailureResponse response = getMassiveFailureRequest(getMassiveFailureRequest);


            LOGGER.error(response.getHeaderResponse().getResponseDate());
            LOGGER.error(response.getMassiveFailureResponse().get(0).getFailureCause());
        }
        catch (Exception e) {
            LOGGER.error("Se produjo un error: " + e.getMessage(), e);
        }

    }

    private static GetMassiveFailureResponse getMassiveFailureRequest(com.amx.schema.operation.getmassivefailurerequest.v1.GetMassiveFailureRequest parameters) {
        MassiveFailureSoapBindingQSService service = new MassiveFailureSoapBindingQSService();
        MassiveFailure port = service.getMassiveFailureSoapBindingQSPort();
        return port.getMassiveFailureRequest(parameters);
    }
}
