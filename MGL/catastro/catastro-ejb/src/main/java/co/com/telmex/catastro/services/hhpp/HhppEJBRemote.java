package co.com.telmex.catastro.services.hhpp;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.EstadoHhpp;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.HhppConsulta;
import com.jlcg.db.exept.ExceptionDB;
import java.util.List;
import javax.ejb.Remote;
import javax.faces.model.SelectItem;

/**
 * Interfase HhppEJBRemote
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
@Remote
public interface HhppEJBRemote {

    /**
     *
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<HhppConsulta> queryHhppByIdDir(String direccion) throws ApplicationException;

    /**
     *
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<HhppConsulta> queryHhppByIdSubDir(String direccion) throws ApplicationException;

    /**
     *
     * @param idHhpp
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Hhpp queryHhppById(String idHhpp) throws ApplicationException;

    /**
     *
     * @param estadoFinal
     * @param user
     * @param hhpp
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String updateEstadoHhpp(String estadoFinal, String user, Hhpp hhpp, String nombreFuncionalidad) throws ApplicationException;

    /**
     *
     * @param idEstadoInicial
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<SelectItem> queryEstadosFinales(String idEstadoInicial) throws ApplicationException;

    /**
     *
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String queryTipoCiudadByID(String idCiudad) throws ApplicationException;

    /**
     *
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean queryCiudadMultiorigen(String idCiudad) throws ApplicationException;

    /**
     *
     * @param codDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    public boolean queryCuentaMatrizOnDir(String codDireccion) throws ApplicationException;

    /**
     *
     * @param nuevoHhpp
     * @param nombreFuncionalidad
     * @param user
     * @return
     * @throws ExceptionDB
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String updateHhpp(Hhpp nuevoHhpp, String nombreFuncionalidad, String user) throws ExceptionDB, ApplicationException;

    /**
     *
     * @param nombreEstado
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    public EstadoHhpp queryEstadoHhpp(String nombreEstado) throws ApplicationException;

    /**
     *
     * @param direccion
     * @param addressGeodata
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Hhpp queryHhppByDireccion(String direccion, AddressGeodata addressGeodata) throws ApplicationException;

    /**
     *
     * @return
     */
    public boolean updateEstadosRR();

    /**
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void updateHhppMglFromRR() throws ApplicationException;
    
    /**
     * Ejecuta la actualización de los HHPP en Mer desde RR. Permite ejecutar la
     * actualizacion de los HHPP sobre MER con la Informacion actualizada desde RR.
     *
     * @author Johnnatan Ortiz
     * @throws ApplicationException
     */
    void updateHhppFromRR() throws ApplicationException;
    
          /**
     * Metodo usado para actualizar los Hhpp en MER apartir de la informacion
     * consultada en RR de la tabla de auditoria
     * @throws ApplicationException
     *
     * @Author Victor Bocanegra
     */
    public void updateHhppMERFromRR() throws ApplicationException;
}
