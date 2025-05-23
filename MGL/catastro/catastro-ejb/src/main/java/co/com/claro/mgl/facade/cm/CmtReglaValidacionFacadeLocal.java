package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.CmtReglaValidacionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaValidacion;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 * Facade Local Regla Validacion. Expone la logica de negocio para el manejo de
 * Reglas de Validacion en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Local
public interface CmtReglaValidacionFacadeLocal extends BaseCmFacadeLocal<CmtReglaValidacion> {

    /**
     * Buscar las Reglas de validacion asociadas a un proyecto.
     * Busca las Reglas de validacion asociadas a un proyecto. Permite realizar
     * la busqueda de las Reglas de validacion paginado.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @param paginaSelected pagina para iniciar consulta de datos.
     * @param maxResults numero de registros requeridos en la consulta.
     * @param proyecto proyecto CmtBasica para el filtro.
     * @return Dto Reglas de validacion encontradas en el repositorio asociadas
     * al proyecto.
     * @throws ApplicationException Lanza una excepcion en caso de que ocurra un 
     * error en la ejecución de la consulta.
     */
    List<CmtReglaValidacionDto> findByFiltroPaginadoDto(
            int paginaSelected,
            int maxResults,
            CmtBasicaMgl proyecto)
            throws ApplicationException;

    /**
     * Cuenta las Reglas de validacion asociadas al filtro.
     * Cuenta las Reglas de validacion asociadas al filtro. Permite contar las
     * Reglas de validacion asociadas paginado en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @param proyecto proyecto CmtBasica para el filtro.
     * @return Numero de Reglas de validacion encontradas en el repositorio
     * asociadas al filtro.
     * @throws ApplicationException Lanza la excepción en caso de que ocurra un 
     * error actualizando la regla.
     */
    int countByFiltroPaginado(CmtBasicaMgl proyecto)
            throws ApplicationException;

    /**
     * Agregar una validacion a una Regla.Agrega una validacion a una Regla.Permite Adicionar un validacion a una
 regla.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param regla Regla a la que se le adicionara la validacion.
     * @param validacionToAdd validacion a adicionar a la regla.
     * @param valorValidacion valor de la validacion a adicionar a la regla.
     * @return Dto CmtReglaValidacion con la validacion adicionada.
     * @throws ApplicationException Lanza la excepción en caso de que se intente 
     * agregar una validación existente a la regla.
     */
    CmtReglaValidacionDto addValidacionToRegla(CmtReglaValidacion regla,
            CmtTipoValidacionMgl validacionToAdd,
            String valorValidacion)
            throws ApplicationException;
    
    /**
     * Agregar una validacion a una ReglaDto.
     * Agrega una validacion a una ReglaDto. Permite Adicionar un validacion a
     * una regla Dto sin persistir.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @param regla Dto Regla a la que se le adicionara la validacion.
     * @param validacionToAdd validacion a adicionar a la regla.
     * @param valorValidacion valor de la validacion a adicionar a la regla.
     * @return Dto CmtReglaValidacion con la validacion adicionada.
     * @throws ApplicationException Lanza la excepción en caso de que ya exista 
     * la validación a agregar a la regla.
     */
    CmtReglaValidacionDto addValidacionToReglaDto(CmtReglaValidacionDto regla,
            CmtTipoValidacionMgl validacionToAdd,
            String valorValidacion)
            throws ApplicationException;
    
    /**
     * Crear una regla a partir a una ReglaDto.Crea una regla a partir a una ReglaDto.Permite Crear una regla a partir
 a una ReglaDto en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @param reglaDto Dto Regla que se desea persistir.
     * @return Dto CmtReglaValidacion persistida.
     * @throws ApplicationException Lanza la excepción en caso de que ocurra un 
     * error creando la regla.
     */
    CmtReglaValidacionDto createReglaFromDto(
            CmtReglaValidacionDto reglaDto)
            throws ApplicationException;
    
    /**
     * Actualizar una regla a partir a una ReglaDto.
     * Actualiza una regla a partir a una ReglaDto. Permite Actualizar una regla
     * a partir a una ReglaDto en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @param reglaDtoToUpdate Dto Regla que se desea actualzar.
     * @return Dto CmtReglaValidacion actualizada.
     * @throws ApplicationException Lanza la excepción en caso de que ocurra un 
     * error actualizando la regla.
     */
    CmtReglaValidacionDto updateReglaFromDto(
            CmtReglaValidacionDto reglaDtoToUpdate)
            throws ApplicationException;
    
    /**
     * Eliminar una regla.
     * Elimina una regla. Permite eliminar logicamente una regla en el
     * repositorio.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param reglaDtoToDelete Dto Regla que se desea eliminar.
     * @return resultado del proceso.
     * @throws ApplicationException Lanza la excepción en caso de que ocurra un 
     * error eliminando la regla.
     */
    boolean deleteRegla(
            CmtReglaValidacionDto reglaDtoToDelete)
            throws ApplicationException;
    
    /**
     * Validarlos tipos de validación de cada regla.
     * Valida que los tipos de validación de cada regla se encuentren con estado 
     * en 1.
     * 
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @param reglas Reglas de validación para validar sus tipos de validación.
     * @return Reglas modificadas con los tipos de validación son estado igual a 
     * 1.
     */
    public List<CmtReglaValidacionDto> validarTiposValidacionReglas
        (List<CmtReglaValidacionDto> reglas);
        
    /**
     * Valida que la regla de validacion contenga más de un tipo de validación.
     * Valida que la regla de validacion contenga más de un tipo de validación 
     * con el fin de permitir eliminar el tipo de validación.
     * 
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @param id Identificador de la regla de validación.
     * @return True en caso de que se pueda eliminar el tipo de validación.
     * @throws ApplicationException Se lanza la excepción en caso de que la regla
     * no pueda eliminar el tipo de validación.
     */
    public Boolean validarEliminacionTipoValidacion(BigDecimal id) 
            throws ApplicationException;
}
