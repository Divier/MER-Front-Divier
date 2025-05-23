/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import co.com.claro.mgl.exception.UnavailableServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author carlos.villa.ext
 */
public class UtilFormatXml {

    private static final Logger LOGGER = LogManager.getLogger(UtilFormatXml.class);

    private final String UNAVAILABLE_SERVICE_MESSAGE = "UnitService: No hubo respuesta del servicio.";

    protected UnitManagerResponse formatXmlAddUnitResponse(String xml) throws UnavailableServiceException {
        UnitManagerResponse response = null;
        try {
            String str = xml;
            int index1 = str.indexOf("UnitManagerResponse");
            if (index1 >= 0) {
                str = "<" + str.substring(index1);
                int index2 = str.indexOf("</responseAddUnit>", "</responseAddUnit>".length());
                str = str.substring(0, index2 + "</responseAddUnit>".length());
                str += "</UnitManagerResponse>";
                JAXBContext context = JAXBContext.newInstance(UnitManagerResponse.class);
                Unmarshaller unmarshall = context.createUnmarshaller();
                response = (UnitManagerResponse) unmarshall.unmarshal(new StringReader(str));
            } else {
                throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE);
            }
        } catch (UnavailableServiceException | JAXBException ex) {
            LOGGER.error("Error al momento de formatear el XML en la adición de unidad. EX000: " + ex.getMessage());
            throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE, ex);
        }
        return response;
    }

    protected CRUDUnitManagerResponse formatXmlCRUDUnitManagerResponse(String xml) throws UnavailableServiceException {
        CRUDUnitManagerResponse response = null;
        try {
            String str = xml;
            int index1 = str.indexOf("CRUDUnitManagerResponse");
            if (index1 >= 0) {
                str = "<" + str.substring(index1);
                int index2 = str.indexOf("</responseCRUDUnit>", "</responseCRUDUnit>".length());
                str = str.substring(0, index2 + "</responseCRUDUnit>".length());
                str += "</CRUDUnitManagerResponse>";
                JAXBContext context = JAXBContext.newInstance(CRUDUnitManagerResponse.class);
                Unmarshaller unmarshall = context.createUnmarshaller();
                response = (CRUDUnitManagerResponse) unmarshall.unmarshal(new StringReader(str));
            } else {
                throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE);
            }
        } catch (UnavailableServiceException | JAXBException ex) {
            LOGGER.error("Error al momento de formatear el XML para CRUD unit manager. EX000: " + ex.getMessage());
            throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE, ex);
        }
        return response;
    }

    protected ChangeUnitAddressResponse formatXmlChangeUnitAddressResponse(String xml) throws UnavailableServiceException {
        ChangeUnitAddressResponse response = null;
        try {
            String str = xml;
            int index1 = str.indexOf("changeUnitAddressResponse");
            if (index1 >= 0) {
                str = "<" + str.substring(index1);
                int index2 = str.indexOf("</response>", "</response>".length());
                str = str.substring(0, index2 + "</response>".length());
                str += "</changeUnitAddressResponse>";
                JAXBContext context = JAXBContext.newInstance(ChangeUnitAddressResponse.class);
                Unmarshaller unmarshall = context.createUnmarshaller();
                response = (ChangeUnitAddressResponse) unmarshall.unmarshal(new StringReader(str));
            } else {
                throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE);
            }
        } catch (UnavailableServiceException | JAXBException ex) {
            LOGGER.error("Error al momento de formatear el XML en la modificación de unit address. EX000: " + ex.getMessage());
            throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE, ex);
        }
        return response;
    }

    protected SpecialUpdateManagerResponse formatXmlSpecialUpdateManagerResponse(String xml) throws UnavailableServiceException {
        SpecialUpdateManagerResponse response = null;
        try {
            String str = xml;
            int index1 = str.indexOf("SpecialUpdateManagerResponse");
            if (index1 >= 0) {
                str = "<" + str.substring(index1);
                int index2 = str.indexOf("</responseSpecialUpdate>", "</responseSpecialUpdate>".length());
                str = str.substring(0, index2 + "</responseSpecialUpdate>".length());
                str += "</SpecialUpdateManagerResponse>";
                JAXBContext context = JAXBContext.newInstance(SpecialUpdateManagerResponse.class);
                Unmarshaller unmarshall = context.createUnmarshaller();
                response = (SpecialUpdateManagerResponse) unmarshall.unmarshal(new StringReader(str));
            } else {
                  LOGGER.error("Error al momento de formatear el XML de actualización especial. EX000: ");
                throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE);
            }
        } catch (UnavailableServiceException | JAXBException ex) {
            LOGGER.error("Error al momento de formatear el XML de actualización especial. EX000: " + ex.getMessage());
            throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE, ex);
        }
        return response;
    }

    protected UpdateStreetGridResponse formatXmlUpdateStreetGridResponse(String xml) throws UnavailableServiceException {
        UpdateStreetGridResponse response = null;
        try {
            String str = xml;
            int index1 = str.indexOf("updateStreetGridResponse");
            if (index1 >= 0) {
                str = "<" + str.substring(index1);
                int index2 = str.indexOf("</responseUpdateStreetGrid>", "</responseUpdateStreetGrid>".length());
                str = str.substring(0, index2 + "</responseUpdateStreetGrid>".length());
                str += "</updateStreetGridResponse>";
                JAXBContext context = JAXBContext.newInstance(UpdateStreetGridResponse.class);
                Unmarshaller unmarshall = context.createUnmarshaller();
                response = (UpdateStreetGridResponse) unmarshall.unmarshal(new StringReader(str));
            } else {
                 LOGGER.error("Error al momento de formatear el XML de actualización de street grid. EX000: ");
                throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE);
            }
        } catch (UnavailableServiceException | JAXBException ex) {
            LOGGER.error("Error al momento de formatear el XML de actualización de street grid. EX000: " + ex.getMessage());
            throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE, ex);
        }
        return response;
    }

    protected QueryStreetGridResponse formatXmlQueryStreetGridResponse(String xml) throws UnavailableServiceException {
        QueryStreetGridResponse response = null;
        try {
            String str = xml;
            int index1 = str.indexOf("queryStreetGridResponse");
            if (index1 >= 0) {
                str = "<" + str.substring(index1);
                int index2 = str.indexOf("</responseQueryStreetGrid>", "</responseQueryStreetGrid>".length());
                str = str.substring(0, index2 + "</responseQueryStreetGrid>".length());
                str += "</queryStreetGridResponse>";
                JAXBContext context = JAXBContext.newInstance(QueryStreetGridResponse.class);
                Unmarshaller unmarshall = context.createUnmarshaller();
                response = (QueryStreetGridResponse) unmarshall.unmarshal(new StringReader(str));
            } else {
                  LOGGER.error("Error al momento de formatear el XML de la consulta de street grid. EX000: ");
                throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE);
            }
        } catch (UnavailableServiceException | JAXBException ex) {
            LOGGER.error("Error al momento de formatear el XML de la consulta de street grid. EX000: " + ex.getMessage());
            throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE, ex);
        }
        return response;
    }

    protected QueryRegularUnitResponse formatXmlQueryRegularUnitResponse(String xml) throws UnavailableServiceException {
        QueryRegularUnitResponse response = null;
        try {
            String str = xml;
            int index1 = str.indexOf("QueryRegularUnitResponse");
            if (index1 >= 0) {
                str = "<" + str.substring(index1);
                int index2 = str.indexOf("</QueryRegularUnitManager>", "</QueryRegularUnitManager>".length());
                str = str.substring(0, index2 + "</QueryRegularUnitManager>".length());
                str += "</QueryRegularUnitResponse>";
                JAXBContext context = JAXBContext.newInstance(QueryRegularUnitResponse.class);
                Unmarshaller unmarshall = context.createUnmarshaller();
                response = (QueryRegularUnitResponse) unmarshall.unmarshal(new StringReader(str));
            } else {
                 LOGGER.error("Error al momento de formatear el XML de consulta regular de unidad. EX000: ");
                throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE);
            }
        } catch (UnavailableServiceException | JAXBException ex) {
            LOGGER.error("Error al momento de formatear el XML de consulta regular de unidad. EX000: " + ex.getMessage());
            throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE, ex);
        }
        return response;
    }

    protected UnitAddInfManagerResponse formatXmlUnitAddInfManagerResponse(String xml) throws UnavailableServiceException {
        UnitAddInfManagerResponse response = null;
        try {
            String str = xml;
            int index1 = str.indexOf("UnitAddInfManagerResponse");
            if (index1 >= 0) {
                str = "<" + str.substring(index1);
                int index2 = str.indexOf("</responseUnitAddInf>", "</responseUnitAddInf>".length());
                str = str.substring(0, index2 + "</responseUnitAddInf>".length());
                str += "</UnitAddInfManagerResponse>";
                JAXBContext context = JAXBContext.newInstance(UnitAddInfManagerResponse.class);
                Unmarshaller unmarshall = context.createUnmarshaller();
                response = (UnitAddInfManagerResponse) unmarshall.unmarshal(new StringReader(str));
            } else {
                LOGGER.error("Error al momento de formatear el XML de adición de unidad. EX000: ");
                throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE);
            }
        } catch (UnavailableServiceException | JAXBException ex) {
            LOGGER.error("Error al momento de formatear el XML de adición de unidad. EX000: " + ex.getMessage());
            throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE, ex);
        }
        return response;
    }

    /**
     * Descripción del objetivo del método.Metodo que formatea la respuesta a
     * opbjeto de respuesta de crteacion de una llamada de servicio.
     *
     * @author Carlos Leonardo Villamil
     * @version 1.00.001
     * @param xml
     * @throws co.com.claro.mgl.exception.UnavailableServiceException
     * @Created 2008-09-09
     * @ModifiedDate 2015-09-22
     * @return SOAPMessage Sopa Message.
     */
    protected CreateServiceCallResponse formatXmlUnitResponseCreateServiceCall(String xml) throws UnavailableServiceException {
        CreateServiceCallResponse response = null;
        try {
            String str = xml;
            int index1 = str.indexOf("createServiceCallResponse");
            if (index1 >= 0) {
                str = "<" + str.substring(index1);
                int index2 = str.indexOf("</ResponseCreateServiceCall>", "</ResponseCreateServiceCall>".length());
                str = str.substring(0, index2 + "</ResponseCreateServiceCall>".length());
                str += "</createServiceCallResponse>";
                JAXBContext context = JAXBContext.newInstance(CreateServiceCallResponse.class);
                Unmarshaller unmarshall = context.createUnmarshaller();
                response = (CreateServiceCallResponse) unmarshall.unmarshal(new StringReader(str));
            } else {
                 LOGGER.error("Error al momento de formatear el XML de unit response de creación. EX000: ");
                throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE);
            }
        } catch (UnavailableServiceException | JAXBException ex) {
            LOGGER.error("Error al momento de formatear el XML de unit response de creación. EX000: " + ex.getMessage());
            throw new UnavailableServiceException(UNAVAILABLE_SERVICE_MESSAGE, ex);
        }
        return response;
    }

    protected ConsultaList_Response formatXmlEntryResponseOnix(String xml) throws UnavailableServiceException {
        ConsultaList_Response response = null;
        try {
            String str = xml;
            int index1 = str.indexOf("consultaList_Response");
            if (index1 >= 0) {
                str = "<" + str.substring(index1);
                int index2 = str.indexOf("</consultaMglOnyxList>", "</consultaMglOnyxList>".length());
                str = str.substring(0, index2 + "</consultaMglOnyxList>".length());
                str += "</consultaList_Response>";
                JAXBContext context = JAXBContext.newInstance(ConsultaList_Response.class);
                Unmarshaller unmarshall = context.createUnmarshaller();
                response = (ConsultaList_Response) unmarshall.unmarshal(new StringReader(str));
            } else {
                LOGGER.error("Error al momento de formatear el XML de entrada. EX000: ");
            }
        } catch (JAXBException ex) {
            LOGGER.error("Error al momento de formatear el XML de entrada. EX000: " + ex.getMessage());
        }
        return response;
    }
}
