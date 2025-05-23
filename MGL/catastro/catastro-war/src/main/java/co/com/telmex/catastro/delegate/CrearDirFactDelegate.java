package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.services.direcciones.CrearDirFactEJB;
import co.com.telmex.catastro.services.direcciones.CrearDirFactEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase CrearDirFactDelegate Representa la implementación en Server de la
 * creación de direcciones de facturación implementa Serialización
 *
 * @author Camilo E. Gaviria
 * @version	1.0
 */
public class CrearDirFactDelegate implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(CrearDirFactDelegate.class);

    private static String CREARDIRFACTBATCHEJB = "CrearDirFactEJB#co.com.telmex.catastro.services.direcciones.CrearDirFactEJBRemote";

    /**
     *
     * @param archivoPlano
     * @param user
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     * @throws javax.naming.NamingException
     */
    public static byte[] getCrearDirFactEjb(StringBuffer archivoPlano, String user) throws ApplicationException, NamingException {

        byte[] loadAddress = null;
        InitialContext ctx = new InitialContext();

        CrearDirFactEJBRemote obj = (CrearDirFactEJBRemote) ctx.lookup(CREARDIRFACTBATCHEJB);
        if (obj != null) {

            try {
                loadAddress = obj.processFileAddress(archivoPlano, user);
                if (loadAddress == null) {
                }
            } catch (UnsupportedEncodingException ex) {
                LOGGER.error("Error al crear la direccióon. EX000 " + ex.getMessage(), ex);
                throw new ApplicationException("Error al crear la direccióon. EX000 " + ex.getMessage(), ex);
            }catch (Exception ex) {
                LOGGER.error("Error al crear la direccióon. EX000 " + ex.getMessage(), ex);
                throw new ApplicationException("Error al crear la direccióon. EX000 " + ex.getMessage(), ex);
            }
        }
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < loadAddress.length; i++) {
            char c = (char) loadAddress[i];
            text.append(c);
        }
        return loadAddress;

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
            CrearDirFactEJB obj = (CrearDirFactEJB) ctx.lookup(CREARDIRFACTBATCHEJB);
            if (obj != null) {
                returnMessageOk = obj.getMessageOk();
            }
            return returnMessageOk;
        } catch (NamingException ex) {
            LOGGER.error("Error al obtener mensake OK. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obtener mensake OK. " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al obtener mensake OK. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obtener mensake OK. " + ex.getMessage(), ex);
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
            CrearDirFactEJB obj = (CrearDirFactEJB) ctx.lookup(CREARDIRFACTBATCHEJB);
            if (obj == null) {
                returnMessageError = obj.getMessageError();
            }
            return returnMessageError;
        } catch (NamingException ex) {
            LOGGER.error("Error al obtener el mensaje de error. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obtener el mensaje de error. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el mensaje de error. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obtener el mensaje de error. EX000 " + ex.getMessage(), ex);
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
            CrearDirFactEJB obj = (CrearDirFactEJB) ctx.lookup(CREARDIRFACTBATCHEJB);
            if (obj != null) {
                returnFlagindicator = obj.getFlagTransaction();
            }
            return returnFlagindicator;
        } catch (NamingException ex) {
            LOGGER.error("Error al obtener el indicador. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obtener el indicador. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al obtener el indicador. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al obtener el indicador. EX000 " + ex.getMessage(), ex);
        }
    }
}
