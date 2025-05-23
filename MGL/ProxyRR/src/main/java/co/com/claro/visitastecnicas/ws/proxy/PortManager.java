/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.exception.UnavailableServiceException;
import co.com.claro.visitastecnicas.ws.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 *
 * @author carlos.villa.ext
 */
public class PortManager {

    /**
     * objetivo de la clase. Clase De negocio del cliente proxy de Servicios
     * unit service.
     *
     * @author Carlos Leonardo Villamil.
     * @versión 1.00.000
     */
    private SOAPConnectionFactory soapConnectionFactory;
    private SOAPConnection soapConnection;
    private String URL;
    private String SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(PortManager.class);

    private PortManager() {
    }

    public PortManager(String url, String service) {
        System.setProperty(Constants.PROPERTY_SOAPFACTORY, Constants.VALUE_SOAPFACTORY);
        System.setProperty(Constants.PROPERTY_MESSAGEFACTORY, Constants.VALUE_MESSAGEFACTORY);
        System.setProperty(Constants.PROPERTY_SOAPCONNECTIONFACTORY, Constants.VALUE_SOAPCONNECTIONFACTORY);
        URL = url;
        SERVICE = service;
    }

    public UnitManagerResponse addUnit(AddUnitRequest addUnitRequest) throws ApplicationException {

        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
            SOAPMessage soapResponse = soapConnection.call(addUnitRequest.createSOAPRequest(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlAddUnitResponse(soapResponseToString(soapResponse));
        } catch (SOAPException | UnsupportedOperationException | IOException | UnavailableServiceException ex) {
            LOGGER.error("Error en addUnit. EX000 " + ex.getMessage());
            throw new ApplicationException("Error en addUnit. EX000 " + ex.getMessage(), ex);
        }

    }

    public CRUDUnitManagerResponse addStreet(RequestCRUDUnit requestCRUDUnit) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
            SOAPMessage soapResponse = soapConnection.call(requestCRUDUnit.createSOAPRequest(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlCRUDUnitManagerResponse(soapResponseToString(soapResponse));
        } catch (ApplicationException | UnavailableServiceException | UnsupportedOperationException | SOAPException ex) {
            LOGGER.error("Error en addStreet. EX000 " + ex.getMessage());
            throw new ApplicationException("Error en addStreet. EX000 " + ex.getMessage(), ex);
        }
    }

    public ChangeUnitAddressResponse changeAddress(ChangeUnitAddressRequest requestChange) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
            SOAPMessage soapResponse = soapConnection.call(requestChange.createSOAPRequest(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlChangeUnitAddressResponse(soapResponseToString(soapResponse));
        } catch (Exception e) {          
            LOGGER.error("Error al momento de cambiar la dirección. EX000: " + e.getMessage());
            throw new ApplicationException("Error al momento de cambiar la dirección. EX000: " + e.getMessage(), e);
        }
    }

    public SpecialUpdateManagerResponse specialUpdate(RequestSpecialUpdate requestSpecialUpdate) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
            SOAPMessage soapResponse = soapConnection.call(requestSpecialUpdate.createSOAPRequest(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlSpecialUpdateManagerResponse(soapResponseToString(soapResponse));
        } catch (Exception e) {          
            LOGGER.error("Exception specialUpdate"  + e.getMessage(), e);
            throw new ApplicationException("Error en _______. EX000 " + e.getMessage(), e);
        }
    }

    public UpdateStreetGridResponse updateStreetGrid(RequestStreetGrid requestStreetGrid) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
            SOAPMessage soapResponse = soapConnection.call(requestStreetGrid.createSOAPRequest(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlUpdateStreetGridResponse(soapResponseToString(soapResponse));
        } catch (UnavailableServiceException | IOException | UnsupportedOperationException | SOAPException ex) {
            LOGGER.error("Error al momento de actualizar la street grid. EX000: " + ex.getMessage());
            throw new ApplicationException("Error al momento de actualizar la street grid. EX000: " + ex.getMessage(), ex);
        }
    }

    public QueryStreetGridResponse queryStreetGrid(RequestStreetGrid requestStreetGrid) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
            SOAPMessage soapResponse = soapConnection.call(requestStreetGrid.createSOAPRequestCreateGrid(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlQueryStreetGridResponse(soapResponseToString(soapResponse));
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el street grid: " + e.getMessage());
            throw new ApplicationException("Error al momento de consultar el street grid: " + e.getMessage(), e);
        }
    }

    public QueryRegularUnitResponse queryRegularUnit(RequestQueryRegularUnit requestQueryRegularUnit) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
            SOAPMessage soapResponse = soapConnection.call(requestQueryRegularUnit.createSOAPRequest(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlQueryRegularUnitResponse(soapResponseToString(soapResponse));
        } catch (Exception e) {     
            LOGGER.error("Error al momento de consultar la unidad. EX000: " + e.getMessage());
            throw new ApplicationException("Error al momento de consultar la unidad. EX000: " + e.getMessage(), e);
        }
    }

    public SOAPMessage queryRegularUnitHhPp(RequestQueryRegularUnit requestQueryRegularUnit) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            SOAPMessage soapResponse = soapConnection.call(requestQueryRegularUnit.createSOAPRequestHhPp(URL), URL + SERVICE);
            soapConnection.close();
            return soapResponse;
        } catch (Exception e) {        
            LOGGER.error("Error al momento de consultar la unidad de HHPP. EX000: " + e.getMessage());
            throw new ApplicationException("Error al momento de consultar la unidad de HHPP. EX000: " + e.getMessage(), e);
        }
    }

    public UnitAddInfManagerResponse unitAddInfManager(RequestUnitAddInf requestUnitAddInf) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
            SOAPMessage soapResponse = soapConnection.call(requestUnitAddInf.createSOAPRequest(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlUnitAddInfManagerResponse(soapResponseToString(soapResponse));
        } catch (Exception e) {     
            LOGGER.error("Error al momento de adicionar la unidad. EX000: " + e.getMessage());
            throw new ApplicationException("Error al momento de adicionar la unidad. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Descripción del objetivo del método.Metodo que consume el unit service 2
     * en el meto de creacion de llamadas de servicio proxy.
     *
     * @author Carlos Leonardo Villamil
     * @version 1.00.001
     * @param createServiceCallRequest
     * @throws co.com.claro.mgl.error.ApplicationException
     * @Created 2008-09-09
     * @ModifiedDate 2015-09-22
     * @return SOAPMessage Sopa Message.
     */
    public CreateServiceCallResponse createServiceCall(CreateServiceCallRequest createServiceCallRequest) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
            SOAPMessage soapResponse = soapConnection.call(createServiceCallRequest.createSOAPRequest(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlUnitResponseCreateServiceCall(soapResponseToString(soapResponse));
        } catch (Exception e) {
            LOGGER.error("Error al momento de crear la llamada al servicio. EX000: " + e.getMessage());
            throw new ApplicationException("Error al momento de crear la llamada al servicio. EX000: " + e.getMessage(), e);
        }
    }

    private String soapResponseToString(SOAPMessage soapResponse) throws ApplicationException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapResponse.writeTo(out);
            String strToString = out.toString();
            return strToString;
        } catch (IOException | SOAPException e) {
            LOGGER.error("Error al momento de crear la respuesta SOAP. EX000: " + e.getMessage());
            throw new ApplicationException("Error al momento de crear la respuesta SOAP. EX000: " + e.getMessage(), e);
        }
    }
  
    public ConsultaList_Response consultarOtHija(OtHijaOnixRequest otHijaOnixRequest) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
            SOAPMessage soapResponse = soapConnection.call(otHijaOnixRequest.createSOAPRequest(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlEntryResponseOnix(soapResponseToString(soapResponse));
        } catch (Exception e) {
            LOGGER.error("Error al momento de registrar la información del incidente. EX000: " + e.getMessage());
            throw new ApplicationException("Error al momento de registrar la información del incidente. EX000: " + e.getMessage(), e);
        }
    }

    //CONSUMIR SIEMPRE ESTE PARA CONSULTAR OT HIJA ONYX, SERVICIO VIGENTE 16/07/2021
    public ConsultaList_Response consultarOtHija(OtHijaOnixRequestNew otHijaOnixRequestNew) throws ApplicationException {
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            UtilFormatXml utilFormatXml = new UtilFormatXml();
             SOAPMessage soapResponse = soapConnection.call(otHijaOnixRequestNew.createSOAPRequest(URL), URL + SERVICE);
            soapConnection.close();
            return utilFormatXml.formatXmlEntryResponseOnix(soapResponseToString(soapResponse));
        } catch (Exception e) {
            LOGGER.error("Error al momento de registrar la información del incidente. EX000: " + e.getMessage());
            throw new ApplicationException("Error al momento de registrar la información del incidente. EX000: " + e.getMessage(), e);
        }
    }

        }
