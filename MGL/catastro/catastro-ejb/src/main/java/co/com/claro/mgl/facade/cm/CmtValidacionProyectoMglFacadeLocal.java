/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtValidacionProyectoMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtValidacionProyectoMgl;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface con operaciones a implementar. 
 * Encargada de exponer la logica delmanager {@link CmtValidacionProyectoMglManager}.
 * 
 * @author ortizjaf
 * @versión 1.0 revision 16/05/2017.
 * @see BaseCmFacadeLocal.
 */
@Local
public interface CmtValidacionProyectoMglFacadeLocal
        extends BaseCmFacadeLocal<CmtValidacionProyectoMgl> {

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
    List<CmtValidacionProyectoMgl> cargarConfiguracionPrevia(CmtBasicaMgl proyecto) 
            throws ApplicationException;
    
    /**
     * Crear la configuración.
     * Crea la configuaración de las validaciones del proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param listToCreate Lista de validaciones a crear en el proyecto.
     * @return La lista de la configuración creada con sus Ids.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    List<CmtValidacionProyectoMgl> crearConfiguracion(
            List<CmtValidacionProyectoMgl> listToCreate) 
            throws ApplicationException;

    /**
     * Crear una valiacion a un proyecto.
     * Crea una valiacion a un proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param t Validacion de proyecto a crear.
     * @return La validacion creada con su nuevo id.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public CmtValidacionProyectoMgl create(CmtValidacionProyectoMgl t) 
            throws ApplicationException;

    /**
     * Actualizar la validacion a un proyecto.
     * Actualiza la validacion a un proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param t Validacion de proyecto a actualizar.
     * @return La validacion de un proyecto actualizada.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en a ejecución de la sentencia.
     */
    public CmtValidacionProyectoMgl update(CmtValidacionProyectoMgl t)
            throws ApplicationException;

    /**
     * Borrar una validación de un proyecto.
     * Borra una validación de un proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param t Validacion a eliminar del proyecto.
     * @return True si la eliminación es completada.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public boolean delete(CmtValidacionProyectoMgl t)
            throws ApplicationException;

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
    List<CmtValidacionProyectoMgl> findByFiltroPaginado(
            int paginaSelected,
            int maxResults, CmtBasicaMgl proyecto) 
            throws ApplicationException;

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
    int countByFiltroPaginado(CmtBasicaMgl proyecto)
            throws ApplicationException;
}
