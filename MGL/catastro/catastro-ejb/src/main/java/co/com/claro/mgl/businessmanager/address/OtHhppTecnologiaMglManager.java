/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.OtHhppTecnologiaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class OtHhppTecnologiaMglManager {

    /**
     * Lista tecnologias por el Id de ot que se encuentran relacionadas.
     *
     * @author Juan David Hernandez
     * @param otId
     * @return
     * @throws ApplicationException
     */
    public List<OtHhppTecnologiaMgl> findOtHhppTecnologiaByOtHhppId(BigDecimal otId)
            throws ApplicationException {
        OtHhppTecnologiaMglDaoImpl otHhppTecnologiaMglDaoImpl
                = new OtHhppTecnologiaMglDaoImpl();
        return otHhppTecnologiaMglDaoImpl.findOtHhppTecnologiaByOtHhppId(otId);
    }

    /**
     * Guardar tecnologia seleccionada para la Direccion
     *
     * @author Orlando Velasquez
     * @param otHhppTecnologiaMgl tecnologia a almacenar
     * @param mUser usuario
     * @param mPerfil perfil
     * @return
     * @throws ApplicationException
     */
    public OtHhppTecnologiaMgl createOtHhppTecnologiaMgl(OtHhppTecnologiaMgl otHhppTecnologiaMgl, String mUser, int mPerfil)
            throws ApplicationException {
        OtHhppTecnologiaMglDaoImpl otHhppTecnologiaMglDaoImpl = new OtHhppTecnologiaMglDaoImpl();
        return otHhppTecnologiaMglDaoImpl.createCm(otHhppTecnologiaMgl, mUser, mPerfil);
    }

    /**
     * Actualiza la tecnologia seleccionada para la Direccion
     *
     * @author Orlando Velasquez
     * @param otHhppTecnologiaMgl tecnologia a almacenar
     * @param mUser usuario
     * @param mPerfil perfil
     * @return
     * @throws ApplicationException
     */
    public OtHhppTecnologiaMgl updateOtHhppTecnologiaMgl(OtHhppTecnologiaMgl otHhppTecnologiaMgl, String mUser, int mPerfil)
            throws ApplicationException {
        OtHhppTecnologiaMglDaoImpl otHhppTecnologiaMglDaoImpl = new OtHhppTecnologiaMglDaoImpl();
        return otHhppTecnologiaMglDaoImpl.updateCm(otHhppTecnologiaMgl, mUser, mPerfil);
    }

    /**
     * Consulta las tecnologias viables
     *
     * @author Orlando Velasquez
     * @param otId
     * @param otHhppTecnologiaMgl tecnologia a almacenar
     * @param mUser usuario
     * @return
     * @throws ApplicationException
     */
    public List<OtHhppTecnologiaMgl> findTecnologiasViables(BigDecimal otId)
            throws ApplicationException {
        OtHhppTecnologiaMglDaoImpl otHhppTecnologiaMglDaoImpl = new OtHhppTecnologiaMglDaoImpl();
        return otHhppTecnologiaMglDaoImpl.findTecnologiasViables(otId);
    }
    
       /**
     * Consulta las tecnologias viables
     *
     * @author cardenaslb
     * @param otId
     * @param otHhppTecnologiaMgl tecnologia a almacenar
     * @param mUser usuario
     * @return
     * @throws ApplicationException
     */
    public List<OtHhppTecnologiaMgl> findTecnologias(BigDecimal otId)
            throws ApplicationException {
        OtHhppTecnologiaMglDaoImpl otHhppTecnologiaMglDaoImpl = new OtHhppTecnologiaMglDaoImpl();
        return otHhppTecnologiaMglDaoImpl.findTecnologias(otId);
    } 

}
