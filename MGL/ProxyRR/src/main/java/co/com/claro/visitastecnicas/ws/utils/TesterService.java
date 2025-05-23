/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.utils;

import co.com.claro.visitastecnicas.ws.proxy.PortManager;
import co.com.claro.visitastecnicas.ws.proxy.QueryRegularUnitResponse;
import co.com.claro.visitastecnicas.ws.proxy.RequestQueryRegularUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class TesterService {
    
    private static final Logger LOGGER = LogManager.getLogger(TesterService.class);

    public static void main(String[] args) {
        try {

            //Pruebas crear ServiceCall
            //Testing Servicio Adicion de notas F8
            //Testing Servicio Crear Calle RR
            //Testing servicio Query Regular Unit para obtener orden de trabajo en curso
            RequestQueryRegularUnit requestQueryRegularUnit = new RequestQueryRegularUnit();

            requestQueryRegularUnit.setApartmentNumber("PISO2");
            requestQueryRegularUnit.setCommunity("BOG");
            requestQueryRegularUnit.setDivision("TVC");
            requestQueryRegularUnit.setHouseNumber("17-45");
            requestQueryRegularUnit.setStreetName("CR 102");

            QueryRegularUnitResponse regularUnitResponse = queryRegularUnit(requestQueryRegularUnit);
            String strMessage = regularUnitResponse.getQueryRegularUnitManager().getMessageText();

            if (regularUnitResponse != null
                    && regularUnitResponse.getQueryRegularUnitManager() != null) {

			if (regularUnitResponse.getQueryRegularUnitManager().getWONO() != null && !regularUnitResponse.getQueryRegularUnitManager().getWONO().trim().isEmpty()) {
                    System.out.println("Existe OT");
                } else {
                    System.out.println("No Existe OT");
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error en main. " +e.getMessage(), e);  
        }

    }

    private static QueryRegularUnitResponse queryRegularUnit(RequestQueryRegularUnit requestQueryRegularUnit) throws Exception {
        String wsURL = "http://192.168.253.21:8080/UnitService/";
        String wsService = "management";
        PortManager port = new PortManager(wsURL, wsService);

        return port.queryRegularUnit(requestQueryRegularUnit);
    }

}
