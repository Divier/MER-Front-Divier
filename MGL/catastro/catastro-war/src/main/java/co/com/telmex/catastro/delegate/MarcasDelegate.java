package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.services.direcciones.MarcasEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase MarcasDelegate
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class MarcasDelegate {
    
    private static final Logger LOGGER = LogManager.getLogger(MarcasDelegate.class);

    private static String MarcasEJB = "marcasEJB#co.com.telmex.catastro.services.direcciones.MarcasEJBRemote";

    /**
     * 
     * @return
     * @throws javax.naming.NamingException
     */
    public static MarcasEJBRemote getMarcasEJBRemoteEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        MarcasEJBRemote obj = (MarcasEJBRemote) ctx.lookup(MarcasEJB);
        return obj;
    }

    /**
     * 
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal queryHhppByIdDir(String direccion) throws ApplicationException {
        BigDecimal hhpp = null;
        try {
            MarcasEJBRemote obj = getMarcasEJBRemoteEjb();
            if (obj != null) {
                hhpp = obj.queryHhppCodDir(direccion);

            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar HHPP por dirección. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar HHPP por dirección. EX000 " + ex.getMessage(), ex);
        }
        return hhpp;
    }

    /**
     * 
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Hhpp queryHhpp(String direccion) throws ApplicationException {
        BigDecimal hhppid = null;
        Hhpp hhpp = null;
        try {
            MarcasEJBRemote obj = getMarcasEJBRemoteEjb();
            if (obj != null) {
                hhpp = obj.queryHhpp(direccion);

            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar HHPP. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar HHPP. EX000 " + ex.getMessage(), ex);
        }
        return hhpp;
    }

    /**
     * 
     * @param estado
     * @param hhpp
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean updateEstadoHhp(String estado, BigDecimal hhpp) throws ApplicationException {
        boolean updatehhpp = false;

        try {
            MarcasEJBRemote obj = getMarcasEJBRemoteEjb();
            if (obj != null) {
                updatehhpp = obj.updateEstadoHhp(estado, hhpp);

            }
        } catch (NamingException ex) {
            LOGGER.error("Error al actualizar el estado HHPP. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar el estado HHPP. EX000 " + ex.getMessage(), ex);
        }
        return updatehhpp;
    }

    /**
     * 
     * @param codigo
     * @param idhhpp
     * @param marca
     * @param user
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean queryCreateMarcaHhpp(String codigo, BigDecimal idhhpp, BigDecimal marca, String user) throws ApplicationException {
        boolean hhpp = false;
        try {
            MarcasEJBRemote obj = getMarcasEJBRemoteEjb();
            if (obj != null) {
                hhpp = obj.createMarcaHhp(codigo, idhhpp, marca, user);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar marca HHPP. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar marca HHPP. EX000 " + ex.getMessage(), ex);
        }
        return hhpp;
    }

    /**
     * 
     * @param codigo
     * @param idhhpp
     * @param marca
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean queryDeleteMarcaHhpp(String codigo, BigDecimal idhhpp, BigDecimal marca) throws ApplicationException {
        boolean delete = false;
        try {
            MarcasEJBRemote obj = getMarcasEJBRemoteEjb();
            if (obj != null) {
                delete = obj.deleteMarcaHhp(codigo, idhhpp, marca);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar borrar marca HHPP. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar borrar marca HHPP. EX000 " + ex.getMessage(), ex);
        }
        return delete;
    }

    /**
     * 
     * @param codigo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal searchMarca(String codigo) throws ApplicationException {
        BigDecimal hhpp = null;
        try {
            MarcasEJBRemote obj = getMarcasEJBRemoteEjb();
            if (obj != null) {
                hhpp = obj.searchMarca(codigo);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al buscar marca. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al buscar marca. EX000 " + ex.getMessage(), ex);
        }
        return hhpp;
    }

    /**
     * 
     * @param hhpp
     * @param idMarca
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal searchMarcaHhpp(BigDecimal hhpp, BigDecimal idMarca) throws ApplicationException {
        BigDecimal marca_hhpp = null;
        try {
            MarcasEJBRemote obj = getMarcasEJBRemoteEjb();
            if (obj != null) {
                marca_hhpp = obj.searchMarcaHhpp(hhpp, idMarca);

            }
        } catch (NamingException ex) {
            LOGGER.error("Error al buscar marca HHPP. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al buscar marca HHPP. EX000 " + ex.getMessage(), ex);
        }
        return marca_hhpp;
    }

    /**
     * 
     * @param codigo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Geografico searchComunidad(String codigo) throws ApplicationException {
        BigDecimal hhpp = null;
        Geografico geografico = null;
        try {
            MarcasEJBRemote obj = getMarcasEJBRemoteEjb();
            if (obj != null) {
                geografico = obj.searchComunidad(codigo);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al buscar comunidad. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al buscar comunidad. EX000 " + ex.getMessage(), ex);
        }
        return geografico;
    }

    /**
     * 
     * @param codigoDepto
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String searchDepto(BigDecimal codigoDepto) throws ApplicationException {
        String depto = null;

        try {
            MarcasEJBRemote obj = getMarcasEJBRemoteEjb();
            if (obj != null) {
                depto = obj.searchDepto(codigoDepto);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al buscar departamentio. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al buscar departamentio. EX000 " + ex.getMessage(), ex);
        }
        return depto;
    }

    /**
     * 
     * @param estadoI
     * @param estadoF
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static BigDecimal searchEstadoHhp(String estadoI, String estadoF) throws ApplicationException {
        BigDecimal estadoPermitido = null;

        try {
            MarcasEJBRemote obj = getMarcasEJBRemoteEjb();
            if (obj != null) {
                estadoPermitido = obj.searchEstadoHhp(estadoI, estadoF);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al buscar estado HHPP. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al buscar estado HHPP. EX000 " + ex.getMessage(), ex);
        }
        return estadoPermitido;
    }
}
