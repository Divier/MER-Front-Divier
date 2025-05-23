/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtGrupoProyectoValidacionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtGrupoProyectoValidacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class CmtGrupoProyectoValidacionMglManager {

    
    private static final Logger LOGGER = LogManager.getLogger(CmtGrupoProyectoValidacionMglManager.class);
    
    /**
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtGrupoProyectoValidacionMgl> findAll() throws ApplicationException {
        CmtGrupoProyectoValidacionMglDaoImpl cmtGrupoProyectoValidacionMglDaoImpl = new CmtGrupoProyectoValidacionMglDaoImpl();
        return cmtGrupoProyectoValidacionMglDaoImpl.findAll();
    }
    
        /**
     *
     * @author Juan David Hernandez
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtGrupoProyectoValidacionMgl> findAllGrupoProyecto() throws ApplicationException {
        CmtGrupoProyectoValidacionMglDaoImpl cmtGrupoProyectoValidacionMglDaoImpl = new CmtGrupoProyectoValidacionMglDaoImpl();
        CmtBasicaMglManager cmtBasicaMglMglManager = new CmtBasicaMglManager();
        
        List<CmtGrupoProyectoValidacionMgl> configuracionSolicitudTmpList = cmtGrupoProyectoValidacionMglDaoImpl.findAll();
        List<CmtGrupoProyectoValidacionMgl> configuracionSolicitudList = new ArrayList();
        configuracionSolicitudList = obtenerListaConstruida(configuracionSolicitudTmpList);
           
        return configuracionSolicitudList;         
    }
    
    
    public List<CmtGrupoProyectoValidacionMgl> obtenerListaConstruida(List<CmtGrupoProyectoValidacionMgl> configuracionSolicitudTmpList)
    {
        try {
            CmtTipoBasicaMglManager cmtTipoBasicaMglManager=new CmtTipoBasicaMglManager();
             List<CmtGrupoProyectoValidacionMgl> configuracionSolicitudList = new ArrayList();
             if (!configuracionSolicitudTmpList.isEmpty()) {
             CmtBasicaMglManager cmtBasicaMglMglManager = new CmtBasicaMglManager();    
                 CmtTipoBasicaMgl cmtTipoBasicaMgl=cmtTipoBasicaMglManager. 
                        findByCodigoInternoApp(
                         Constant.TIPO_BASICA_GRUPO_PROYECTO_ID);
             List<CmtBasicaMgl> grupoProyectoBasicaList = cmtBasicaMglMglManager.
                     findGrupoProyectoBasicaList(cmtTipoBasicaMgl.getTipoBasicaId());
             cmtTipoBasicaMgl=cmtTipoBasicaMglManager. 
                        findByCodigoInternoApp(Constant.TIPO_BASICA_PROYECTO_BASICA_ID);
             List<CmtBasicaMgl> proyectoBasicaList = cmtBasicaMglMglManager.findProyectoBasicaList(
                     cmtTipoBasicaMgl.getTipoBasicaId()); 
                            
             if (grupoProyectoBasicaList != null && !grupoProyectoBasicaList.isEmpty()
                        && proyectoBasicaList != null && !proyectoBasicaList.isEmpty()) {

                    List<CmtBasicaMgl> tipoGrupoProyectoTmpList = new ArrayList();
                    tipoGrupoProyectoTmpList.addAll(grupoProyectoBasicaList);
                    tipoGrupoProyectoTmpList.addAll(proyectoBasicaList);

                    //Realiza recorrido sobre lista global para extraer listados internos y armar una nueva lista
                    for (CmtGrupoProyectoValidacionMgl cmtGeneral : configuracionSolicitudTmpList) {
                        
                        CmtGrupoProyectoValidacionMgl obj = new CmtGrupoProyectoValidacionMgl();
                        obj.setGrupoProyectoId(cmtGeneral.getGrupoProyectoId());
                        obj.setFechaCreacion(cmtGeneral.getFechaCreacion());
                        obj.setFechaEdicion(cmtGeneral.getFechaEdicion());
                        obj.setUsuarioCreacion(cmtGeneral.getUsuarioCreacion());
                        obj.setUsuarioEdicion(cmtGeneral.getUsuarioEdicion());
                        obj.setPerfilCreacion(cmtGeneral.getPerfilCreacion());
                        obj.setPerfilEdicion(cmtGeneral.getPerfilEdicion());
                        obj.setEstadoRegistro(cmtGeneral.getEstadoRegistro());

                        for (CmtBasicaMgl cmtBasica : tipoGrupoProyectoTmpList) {
                            if (cmtGeneral.getGrupoProyectoBasicaId() != null
                                    && cmtGeneral.getGrupoProyectoBasicaId().equals(cmtBasica.getBasicaId())) {
                                obj.setGrupoProyectoBasicaId(cmtBasica.getBasicaId());
                                obj.setAbreviaturaGrupoProyecto(cmtBasica.getAbreviatura());
                                obj.setNombreGrupoProyecto(cmtBasica.getNombreBasica());
                            } else {
                                if (cmtGeneral.getProyectoBasicaId() != null && cmtGeneral.getProyectoBasicaId().equals(cmtBasica.getBasicaId())) {
                                    obj.setProyectoBasicaId(cmtBasica.getBasicaId());
                                    obj.setAbreviaturaProyectoBasica(cmtBasica.getAbreviatura());
                                    obj.setNombreProyectoBasica(cmtBasica.getNombreBasica());
                                }
                            }
                        }

                        //Inserta objeto en listado que se desplegará en pantalla.
                        if (obj.getGrupoProyectoId() != null && obj.getProyectoBasicaId() != null) {
                            configuracionSolicitudList.add(obj);
                        }
                    }
                }
            }
             return configuracionSolicitudList;
        } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                return null;
        }
    }

    public CmtGrupoProyectoValidacionMgl create(CmtGrupoProyectoValidacionMgl cmt, String usuario, int perfil) throws ApplicationException {
        CmtGrupoProyectoValidacionMglDaoImpl cmtGrupoProyectoValidacionMglDaoImpl = new CmtGrupoProyectoValidacionMglDaoImpl();
        cmt.setEstadoRegistro(1);
        return cmtGrupoProyectoValidacionMglDaoImpl.createCm(cmt, usuario.toUpperCase(), perfil);

    }

    public CmtGrupoProyectoValidacionMgl update(CmtGrupoProyectoValidacionMgl cmt, String usuario, int perfil) throws ApplicationException {
        CmtGrupoProyectoValidacionMglDaoImpl cmtGrupoProyectoValidacionMglDaoImpl = new CmtGrupoProyectoValidacionMglDaoImpl();

        return cmtGrupoProyectoValidacionMglDaoImpl.updateCm(cmt, usuario.toUpperCase(), perfil);

    }

    public boolean delete(CmtGrupoProyectoValidacionMgl cmt, String usuario, int perfil) throws ApplicationException, ApplicationException {
        CmtGrupoProyectoValidacionMglDaoImpl cmtGrupoProyectoValidacionMglDaoImpl = new CmtGrupoProyectoValidacionMglDaoImpl();
        return cmtGrupoProyectoValidacionMglDaoImpl.deleteCm(cmt, usuario.toUpperCase(), perfil);

    }

    public List<CmtGrupoProyectoValidacionMgl> findPendientesParaGestionPaginacion(int paginaSelected, int maxResults) throws ApplicationException {
        CmtGrupoProyectoValidacionMglDaoImpl cmtGrupoProyectoValidacionMglDaoImpl = new CmtGrupoProyectoValidacionMglDaoImpl();

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return cmtGrupoProyectoValidacionMglDaoImpl.findPendientesParaGestionPaginacion(firstResult, maxResults);
    }
    
    
    public List<CmtGrupoProyectoValidacionMgl> findByProyectoGeneral() throws ApplicationException {
        CmtGrupoProyectoValidacionMglDaoImpl cmtGrupoProyectoValidacionMglDaoImpl = new CmtGrupoProyectoValidacionMglDaoImpl();
 
       List<CmtGrupoProyectoValidacionMgl> configuracionSolicitudTmpList = cmtGrupoProyectoValidacionMglDaoImpl.findByProyectoGeneral();
             
        List<CmtGrupoProyectoValidacionMgl> configuracionSolicitudList = new ArrayList();
        configuracionSolicitudList = obtenerListaConstruida(configuracionSolicitudTmpList);         
         
        return configuracionSolicitudList;
    }
}
