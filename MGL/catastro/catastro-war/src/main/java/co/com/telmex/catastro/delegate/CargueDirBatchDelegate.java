package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.services.direcciones.CargueDireccionesBatchEJB;
import co.com.telmex.catastro.services.direcciones.CargueDireccionesBatchEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase CargueDirBatchDelegate implementa Serialización
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
public class CargueDirBatchDelegate implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(CargueDirBatchDelegate.class);

    private static String CARGUEDIRECCIONESBATCHEJB = "cargueDireccionesBatchEJB#co.com.telmex.catastro.services.direcciones.CargueDireccionesBatchEJBRemote";

    /**
     *
     * @param archivoPlano
     * @param user
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static byte[] getCargueDirBatchEjb(StringBuffer archivoPlano, String user) throws ApplicationException {

        try {
            byte[] loadAddress = null;
            InitialContext ctx = new InitialContext();

            CargueDireccionesBatchEJBRemote obj = (CargueDireccionesBatchEJBRemote) ctx.lookup(CARGUEDIRECCIONESBATCHEJB);
            if (obj != null) {

                loadAddress = obj.processFileAddress(archivoPlano, user);
                if (loadAddress == null) {
                }
            }
            StringBuffer text = new StringBuffer();
            for (int i = 0; i < loadAddress.length; i++) {
                char c = (char) loadAddress[i];
                text.append(c);

            }
            return loadAddress;
        } catch (NamingException | UnsupportedEncodingException ex) {
            LOGGER.error("Error al cargar direcciones. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al cargar direcciones. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al cargar direcciones. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al cargar direcciones. EX000 " + ex.getMessage(), ex);
        }

    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static String getMessageOk() throws ApplicationException {
        try {
            String returnMessageOk = null;
            InitialContext ctx = new InitialContext();
            CargueDireccionesBatchEJB obj = (CargueDireccionesBatchEJB) ctx.lookup(CARGUEDIRECCIONESBATCHEJB);
            if (obj != null) {
                returnMessageOk = obj.getMessageOk();
            }
            return returnMessageOk;
        } catch (NamingException ex) {
            LOGGER.error("Error al obtener mensake OK. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obtener mensake OK. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al obtener mensake OK. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obtener mensake OK. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static String getMessageError() throws ApplicationException {
        try {
            String returnMessageError = null;
            InitialContext ctx = new InitialContext();
            CargueDireccionesBatchEJB obj = (CargueDireccionesBatchEJB) ctx.lookup(CARGUEDIRECCIONESBATCHEJB);
            if (obj == null) {
                returnMessageError = obj.getMessageError();
            }
            return returnMessageError;
        } catch (NamingException ex) {
            LOGGER.error("Error al obtener mensaje de error. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obtener mensaje de error. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al obtener mensaje de error. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obtener mensaje de error. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static String getFlagIndicator() throws ApplicationException {
        try {
            String returnFlagindicator = null;
            InitialContext ctx = new InitialContext();
            CargueDireccionesBatchEJB obj = (CargueDireccionesBatchEJB) ctx.lookup(CARGUEDIRECCIONESBATCHEJB);
            if (obj != null) {
                returnFlagindicator = obj.getFlagTransaction();
            }
            return returnFlagindicator;
        } catch (NamingException ex) {
            LOGGER.error("Error al obetener el indicador. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obetener el indicador. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al obetener el indicador. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obetener el indicador. EX000 " + ex.getMessage(), ex);
        }
    }
}
