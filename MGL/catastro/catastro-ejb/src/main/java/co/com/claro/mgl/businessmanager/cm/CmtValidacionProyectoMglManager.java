package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtValidacionProyectoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtValidacionProyectoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * clase encargada de la administracion de la tabla
 * {@link CmtValidacionProyectoMgl}. 
 * ofrece operaciones necesarias para crear,
 * modificar y eliminar registros de la tabla {@link CmtValidacionProyectoMgl}.
 * 
 * @author ortizjaf.
 * @version 1.0 revision 16/05/2017.
 */
public class CmtValidacionProyectoMglManager {

    /**
     * Constante para la escritura de log de la clase actual.
     */
    private static final Logger LOGGER = LogManager.getLogger(CmtValidacionProyectoMglManager.class);

    /**
     * Cargar la configuracions de validaciones.
     * Carga la configuracions de validaciones activas por proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param proyecto Proyecto a consultar las validaciones.
     * @return Lista de validaciones por proyecto.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public List<CmtValidacionProyectoMgl> cargarConfiguracionPrevia(
            CmtBasicaMgl proyecto)
            throws ApplicationException {
        List<CmtValidacionProyectoMgl> validacionProyectoList =
                new ArrayList<CmtValidacionProyectoMgl>();
        CmtTipoValidacionMglManager tipoValidacionMglManager =
                new CmtTipoValidacionMglManager();
        CmtBasicaMglManager basicaMglManager =
                new CmtBasicaMglManager();
        List<CmtTipoValidacionMgl> validacionList = tipoValidacionMglManager
                .findAllItemsActive();
        if (validacionList != null && !validacionList.isEmpty()) {
            for (CmtTipoValidacionMgl v : validacionList) {
                List<CmtBasicaMgl> valorValidacionList =
                        basicaMglManager.findByTipoBasica(v.getTipoBasicaObj());
                for (CmtBasicaMgl vv : valorValidacionList) {
                    CmtValidacionProyectoMgl val = null;
                    try {
                        val = findByProyectoAndValidacion(proyecto, vv);
                    } catch (ApplicationException e) {
						String msg = "Se produjo un error al momento de ejecutar el método '"+
						ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
						LOGGER.error(msg);
                        val = null;
                    }
                    //Si no encontro la validacion se carga
                    if (val == null) {
                        CmtValidacionProyectoMgl valida =
                                new CmtValidacionProyectoMgl();
                        valida.setTipoBasicaProyectoId(proyecto);
                        valida.setTipoBasicaValidacionId(vv);
                        valida.setViabilidad("N");
                        validacionProyectoList.add(valida);
                    }
                }
            }
        }
        return validacionProyectoList;
    }

    /**
     * Crear la configuración.
     * Crea la configuaración de las validaciones del proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param listToCreate Lista de validaciones a crear en el proyecto.
     * @param usuario Usuario de sesion.
     * @param perfil Perfil del usuario.
     * @return La lista de la configuración creada con sus Ids.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public List<CmtValidacionProyectoMgl> crearConfiguracion(
            List<CmtValidacionProyectoMgl> listToCreate,
            String usuario, int perfil)
            throws ApplicationException {
        List<CmtValidacionProyectoMgl> resultList =
                new ArrayList<CmtValidacionProyectoMgl>();
        CmtValidacionProyectoMglDaoImpl cmtValidacionProyectoMglDaoImpl =
                new CmtValidacionProyectoMglDaoImpl();
        for (CmtValidacionProyectoMgl v : listToCreate) {
            v = cmtValidacionProyectoMglDaoImpl.createCm(v, usuario, perfil);
            resultList.add(v);
        }
        return resultList;
    }

    /**
     * Buscar todas las validaciones.
     * Busca todas las validaciones de los proyectos.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return La lista de validaciones.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public List<CmtValidacionProyectoMgl> findAll() throws ApplicationException {

        List<CmtValidacionProyectoMgl> resulList;
        CmtValidacionProyectoMglDaoImpl cmtValidacionProyectoMglDaoImpl = 
                new CmtValidacionProyectoMglDaoImpl();

        resulList = cmtValidacionProyectoMglDaoImpl.findAll();

        return resulList;
    }

    /**
     * Buscar las validaciones por los filtros dados en los parametros.
     * Buscar las validaciones por los filtros proyecto y validacion.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param proyecto Proyecto en el cual se busca la validacion.
     * @param validacion Validacion que se desea encontrar en el proyecto.
     * @return Lista de validaciones que coincidad con los parametros de busqueda.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public CmtValidacionProyectoMgl findByProyectoAndValidacion(
            CmtBasicaMgl proyecto, CmtBasicaMgl validacion)
            throws ApplicationException {
        CmtValidacionProyectoMglDaoImpl cmtValidacionProyectoMglDaoImpl = 
                new CmtValidacionProyectoMglDaoImpl();
        return cmtValidacionProyectoMglDaoImpl.findByProyectoAndValidacion(
                proyecto, validacion);
    }

    /**
     * Crear una valiacion a un proyecto.
     * Crea una valiacion a un proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param cmtTipoSolicitudMgl Validacion de proyecto a crear.
     * @return La validacion creada con su nuevo id.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public CmtValidacionProyectoMgl create(CmtValidacionProyectoMgl cmtTipoSolicitudMgl) 
            throws ApplicationException {
        CmtValidacionProyectoMglDaoImpl cmtValidacionProyectoMglDaoImpl = 
                new CmtValidacionProyectoMglDaoImpl();
        return cmtValidacionProyectoMglDaoImpl.create(cmtTipoSolicitudMgl);
    }

    /**
     * Actualizar la validacion a un proyecto.
     * Actualiza la validacion a un proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param cmtTipoSolicitudMgl Validacion de proyecto a actualizar.
     * @return La validacion de un proyecto actualizada.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en a ejecución de la sentencia.
     */
    public CmtValidacionProyectoMgl update(CmtValidacionProyectoMgl cmtTipoSolicitudMgl) 
            throws ApplicationException {
        CmtValidacionProyectoMglDaoImpl cmtValidacionProyectoMglDaoImpl = 
                new CmtValidacionProyectoMglDaoImpl();
        return cmtValidacionProyectoMglDaoImpl.update(cmtTipoSolicitudMgl);
    }

    /**
     * Borrar una validación de un proyecto.
     * Borra una validación de un proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param cmtTipoSolicitudMgl Validacion a eliminar del proyecto.
     * @return True si la eliminación es completada.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public boolean delete(CmtValidacionProyectoMgl cmtTipoSolicitudMgl) 
            throws ApplicationException {
        CmtValidacionProyectoMglDaoImpl cmtValidacionProyectoMglDaoImpl = 
                new CmtValidacionProyectoMglDaoImpl();
        return cmtValidacionProyectoMglDaoImpl.delete(cmtTipoSolicitudMgl);
    }

    /**
     * Consultar la validación del proyecto.
     * Consulta la validación del proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param cmtValidacionProyectoMgl Validación a buscar.
     * @return Validacion de proyecto si coincide con los filtros proporcionados.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public CmtValidacionProyectoMgl findById(CmtValidacionProyectoMgl cmtValidacionProyectoMgl) throws ApplicationException {
        CmtValidacionProyectoMglDaoImpl cmtValidacionProyectoMglDaoImpl = new CmtValidacionProyectoMglDaoImpl();
        return cmtValidacionProyectoMglDaoImpl.find(cmtValidacionProyectoMgl.getIdValidacionProyecto());
    }
    
    /**
     * Buscar la viabilidad.
     * Busca la viabilidad.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param basicaProyecto Proycto a buscar.
     * @param basica Validación a buscar.
     * @return Viabilidad coincidente con los parametros de busqueda.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en a ejecución de la sentencia.
     */
     public String buscarViabilidad(CmtBasicaMgl basicaProyecto
             , CmtBasicaMgl basica) throws ApplicationException{
        CmtValidacionProyectoMglDaoImpl cmtValidacionProyectoMglDaoImpl = 
                new CmtValidacionProyectoMglDaoImpl();
        return cmtValidacionProyectoMglDaoImpl.findViabilidad(
                basicaProyecto,basica);
    }

     /**
      * Buscar la validación del proyecto por paginación.
      * Busca la validación del proyecto por paginación.
      * 
      * @author Jhoimer Rios.
      * @version 1.0 revision 16/05/2017.
      * @param paginaSelected Pagina seleccionada.
      * @param maxResults Número máximo de registros a mostrar.
      * @param proyecto Proyecto en el cual se buscan las validaciones.
      * @return Lista de validaciones coincidentes con los criterios de búsqueda.
      * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
      * a ejecución de la sentencia.
      */
    public List<CmtValidacionProyectoMgl> findByFiltroPaginado(
            int paginaSelected,
            int maxResults, CmtBasicaMgl proyecto)
            throws ApplicationException {
        try {
            int firstResult = 0;
            if (paginaSelected > 1) {
                firstResult = (maxResults * (paginaSelected - 1));
            }

            CmtValidacionProyectoMglDaoImpl cmtValidacionProyectoMglDaoImpl =
                    new CmtValidacionProyectoMglDaoImpl();
            return cmtValidacionProyectoMglDaoImpl.findByFiltroPaginado(
                    firstResult, maxResults, proyecto);
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Obtener la cantidad de registros.
     * Obtiener la cantidad de registros de validacion por el 
     * proyecto dado.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param proyecto Poryecto al que se consulta las validaciones asociadas.
     * @return Cantidad de registos existentes de la consulta.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en a ejecución de la sentencia.
     */
    public int countByFiltroPaginado(CmtBasicaMgl proyecto)
            throws ApplicationException {
        try {
            CmtValidacionProyectoMglDaoImpl cmtValidacionProyectoMglDaoImpl =
                    new CmtValidacionProyectoMglDaoImpl();
            return cmtValidacionProyectoMglDaoImpl.countByFiltroPaginado(proyecto);
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
