package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.EstadoHhpp;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.HhppConsulta;
import co.com.telmex.catastro.services.hhpp.HhppEJBRemote;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.faces.model.SelectItem;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase HhppDelegate
 *
 * @author Jose Luis Caicedo
 * @version	1.0
 */
public class HhppDelegate {

    private static final Logger LOGGER = LogManager.getLogger(HhppDelegate.class);

    private static String HHPPEJB = "hhppEJB#co.com.telmex.catastro.services.hhpp.HhppEJBRemote";

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static HhppEJBRemote getHhppEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        HhppEJBRemote obj = (HhppEJBRemote) ctx.lookup(HHPPEJB);
        return obj;
    }

    /**
     *
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<HhppConsulta> queryHhppByIdDir(String direccion) throws ApplicationException {
        List<HhppConsulta> list = null;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                list = obj.queryHhppByIdDir(direccion);
            }
        } catch (ApplicationException | NamingException ex) {
            LOGGER.error("Error al consultar HHPP por dirección. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar HHPP por dirección. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param subDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<HhppConsulta> queryHhppByIdSubDir(String subDireccion) throws ApplicationException {
        List<HhppConsulta> list = null;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                list = obj.queryHhppByIdSubDir(subDireccion);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar HHPP por subdirección. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar HHPP por subdirección. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param idHhpp
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Hhpp queryHhppById(String idHhpp) throws ApplicationException {
        Hhpp hhpp = null;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                hhpp = obj.queryHhppById(idHhpp);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar HHPP por id. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar HHPP por id. EX000 " + ex.getMessage(), ex);
        }
        return hhpp;
    }

    /**
     *
     * @param estadoFinal
     * @param user
     * @param hhpp
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String updateEstadoHhpp(String estadoFinal, String user, Hhpp hhpp, String nombreFuncionalidad) throws ApplicationException {
        String mensaje = "";
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                mensaje = obj.updateEstadoHhpp(estadoFinal, user, hhpp, nombreFuncionalidad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar estado HHPP. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar estado HHPP. EX000 " + ex.getMessage(), ex);
        }
        return mensaje;
    }

    /**
     *
     * @param nuevoHhpp
     * @param nombreFuncionalidad
     * @param user
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String updateHhpp(Hhpp nuevoHhpp, String nombreFuncionalidad, String user) throws ApplicationException {
        String ok = "";
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                ok = obj.updateHhpp(nuevoHhpp, nombreFuncionalidad, user);
            }
        } catch (ApplicationException | ExceptionDB | NamingException ex) {
            LOGGER.error("Error al actualizar HHPP. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al actualizar HHPP. EX000 " + ex.getMessage(), ex);
        }
        return ok;
    }

    /**
     *
     * @param idEstadoInicial
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<SelectItem> queryEstadosFinales(String idEstadoInicial) throws ApplicationException {
        List<SelectItem> list = null;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                list = obj.queryEstadosFinales(idEstadoInicial);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar estados finales EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar estados finales EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static String queryTipoCiudadByID(String idCiudad) throws ApplicationException {
        String tipoCiudad = null;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                tipoCiudad = obj.queryTipoCiudadByID(idCiudad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar tipo de ciudad por id. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar tipo de ciudad por id. EX000 " + ex.getMessage(), ex);
        }
        return tipoCiudad;
    }

    /**
     *
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean queryCiudadMultiorigen(String idCiudad) throws ApplicationException {
        boolean multiorigen = false;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                multiorigen = obj.queryCiudadMultiorigen(idCiudad);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar ciudad multiorigen. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar ciudad multiorigen. EX000 " + ex.getMessage(), ex);
        }
        return multiorigen;
    }

    /**
     *
     * @param codDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean queryCuentaMatrizOnDir(String codDireccion) throws ApplicationException {
        boolean ctaMatriz = false;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                ctaMatriz = obj.queryCuentaMatrizOnDir(codDireccion);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar cuenta matriz por código dirección. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar cuenta matriz por código dirección. EX000 " + ex.getMessage(), ex);
        }
        return ctaMatriz;
    }

    /**
     *
     * @param nombreEstado
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static EstadoHhpp queryEstadoHhpp(String nombreEstado) throws ApplicationException {
        EstadoHhpp estadohhpp = null;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                estadohhpp = obj.queryEstadoHhpp(nombreEstado);
            }
        } catch (ApplicationException | NamingException ex) {
            LOGGER.error("Error al consultar estado HHPP. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar estado HHPP. EX000 " + ex.getMessage(), ex);
        }
        return estadohhpp;
    }

    /**
     *
     * @param direccion
     * @param addressGeodata
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Hhpp queryHhppByDireccion(String direccion, AddressGeodata addressGeodata) throws ApplicationException {
        Hhpp hhpp = null;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                hhpp = obj.queryHhppByDireccion(direccion, addressGeodata);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar HHPP por dirección. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar HHPP por dirección. EX000 " + ex.getMessage(), ex);
        }
        return hhpp;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static boolean updateEstadosRR() throws ApplicationException {
        boolean exitosa = true;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                exitosa = obj.updateEstadosRR();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al actualizar el estado RR. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al actualizar el estado RR. EX000 " + ex.getMessage(), ex);
        }
        return true;
    }

    /**
     * Actualiza los HHPP de MGL desde RR.
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean updateHhppMgl() throws ApplicationException {
        boolean hhppActualizado = false;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                obj.updateHhppMglFromRR();
                hhppActualizado = true;
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al actualizar HHPP desde RR. EX000 " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("Error al actualizar HHPP desde RR. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar HHPP desde RR. EX000 " + ex.getMessage(), ex);
        }
        return (hhppActualizado);
    }
    /**
     * Metodo para realizar Actualizacion de los HHPP sobre MGL desde RR.
     * @return
     * @throws ApplicationException
     */
    public static boolean syncUpHhppMgl() throws ApplicationException {
        boolean hhppActualizado = false;
        try {
            HhppEJBRemote obj = getHhppEjb();
            if (obj != null) {
                obj.updateHhppMERFromRR();
                hhppActualizado = true;
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al actualizar HHPP desde RR. EX000 " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("Error al actualizar HHPP desde RR. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar HHPP desde RR. EX000 " + ex.getMessage(), ex);
        }
        return (hhppActualizado);
    }

}
