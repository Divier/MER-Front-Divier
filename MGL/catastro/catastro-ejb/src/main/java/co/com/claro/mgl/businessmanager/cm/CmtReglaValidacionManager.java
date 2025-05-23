package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.constantes.cm.MensajeTipoValidacion;
import co.com.claro.mgl.dao.impl.cm.CmtReglaValidacionDaoImpl;
import co.com.claro.mgl.dtos.CmtReglaValidacionDto;
import co.com.claro.mgl.dtos.CmtTipoValidacionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaValidacion;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Contiene la logica de negocio para el manejo de las Reglas de Validacion.
 * Manager Regla Validacion. Contiene la logica de negocio para el manejo de las
 * Reglas de Validacion en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.0 revision 15/05/2017.
 */
public class CmtReglaValidacionManager {
    
    /**
     * Atributo estatioco que contiene el log de la clase.
     */
    private static final Logger LOGGER = LogManager.getLogger(CmtReglaValidacionManager.class);

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
    public List<CmtReglaValidacionDto> findByFiltroPaginadoDto(
            int paginaSelected,
            int maxResults,
            CmtBasicaMgl proyecto)
            throws ApplicationException {
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        CmtReglaValidacionDaoImpl reglaValidacionDao 
                = new CmtReglaValidacionDaoImpl();
        List<CmtReglaValidacion> reglaProyectoList =
                reglaValidacionDao.findByFiltroPaginado(
                firstResult, maxResults, proyecto);
        List<CmtReglaValidacionDto> resulList = null;
        if (reglaProyectoList != null
                && !reglaProyectoList.isEmpty()) {
            resulList = new ArrayList<CmtReglaValidacionDto>();
            for (CmtReglaValidacion r : reglaProyectoList) {
                //convertimos la entidad a DTO
                CmtReglaValidacionDto reglaDto = convertReglaToDto(r);
                resulList.add(reglaDto);
            }
        }
        return resulList;
    }

    /**
     * Crear una regla a partir a una ReglaDto.
     * Crea una regla a partir a una ReglaDto. Permite Crear una regla a partir
     * a una ReglaDto en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @param reglaDto Dto Regla que se desea persistir.
     * @param usuario usuario que realiza la operacion.
     * @param perfil perfil del usuario que realiza la operacion.
     * @return Dto CmtReglaValidacion persistida.
     * @throws ApplicationException Lanza la excepción en caso de que ocurra un 
     * error creando la regla.
     */
    public CmtReglaValidacionDto createReglaFromDto(
            CmtReglaValidacionDto reglaDto,
            String usuario, int perfil)
            throws ApplicationException {
        try {
            if (validaReglaDuplicada(reglaDto, false)) {
                throw new ApplicationException("Existe una regla con la misma configuracion");
            } else {
            CmtReglaValidacion reglaValidacionToPersist =
                    convertFromDtoToRegla(reglaDto);
            CmtReglaValidacionDaoImpl reglaValidacionDao =
                    new CmtReglaValidacionDaoImpl();

            reglaValidacionToPersist = reglaValidacionDao.createCm(
                    reglaValidacionToPersist, usuario, perfil);
            CmtReglaValidacionDto reglaValidacionDtoResult =
                    convertReglaToDto(reglaValidacionToPersist);
            return reglaValidacionDtoResult;
            }
        } catch (ApplicationException e) {
            String msg = "Ocurrio un error Creando la Regla '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
             LOGGER.error(msg);
             throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Actualizar una regla a partir a una ReglaDto.
     * Actualiza una regla a partir a una ReglaDto. Permite Actualizar una regla
     * a partir a una ReglaDto en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @param reglaDtoToUpdate Dto Regla que se desea actualzar
     * @param usuario usuario que realiza la operacion
     * @param perfil perfil del usuario que realiza la operacion
     * @return Dto CmtReglaValidacion actualizada
     * @throws ApplicationException Lanza la excepción en caso de que ocurra un 
     * error actualizando la regla.
     */
    public CmtReglaValidacionDto updateReglaFromDto(
            CmtReglaValidacionDto reglaDtoToUpdate,
            String usuario, int perfil)
            throws ApplicationException {
        try {
            CmtReglaValidacion reglaValidacionToUpdate =
                    convertFromDtoToRegla(reglaDtoToUpdate);
//            Validamos que no exista una regla con la misma configuracion
            if (validaReglaDuplicada(reglaDtoToUpdate, true)) {
                throw new ApplicationException("Existe una regla con la misma configuracion");
            } else {
            CmtReglaValidacionDaoImpl reglaValidacionDao =
                    new CmtReglaValidacionDaoImpl();
            reglaValidacionToUpdate = reglaValidacionDao.updateCm(
                    reglaValidacionToUpdate, usuario, perfil);
                CmtReglaValidacionDto reglaValidacionDtoResult =
                        convertReglaToDto(reglaValidacionToUpdate);
                return reglaValidacionDtoResult;
            }
        } catch (ApplicationException e) {
            String msg = "Ocurrio un error Actualizando la Regla '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Validar que no exista un regla con la misma configuracion.Valida que no exista un regla con la misma configuracion.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param reglaDtoTovalidate Dto Regla que se desea validar.
     * @param isUpdate
     * @return resultado exitoso si existen reglas duplicadas.
     * @throws ApplicationException Lanza la excepción en caso de que la regla 
     * ya se encuentre.
     */
    public boolean validaReglaDuplicada(
            CmtReglaValidacionDto reglaDtoTovalidate,
            boolean isUpdate)
            throws ApplicationException {
        try {
            boolean hayReglaDuplicada = false;
            CmtReglaValidacion reglaValidacionToUpdate =
                    convertFromDtoToRegla(reglaDtoTovalidate);
            //buscamos las reglas del mismo proyecto para validar que no exista
            //una regla con la misma configuracion
            CmtReglaValidacionDaoImpl reglaValidacionDao =
                    new CmtReglaValidacionDaoImpl();
            List<CmtReglaValidacion> reglasProyecto =
                    reglaValidacionDao.findByProyecto(
                    reglaValidacionToUpdate.getProyecto());
            //Validamos que existan reglas configuradas para el proyecto
            if (reglasProyecto != null && !reglasProyecto.isEmpty()) {
                //Revisamos las reglas del proyecto
                
                for (int j = 0;
                        j < reglasProyecto.size()
                        && !hayReglaDuplicada;
                        j++) {
                    CmtReglaValidacion r = reglasProyecto.get(j);
                    //validamos que exista una configuracion de regla
                    if (!hayReglaDuplicada
                            && r.getReglaValidacionId() != null
                            && r.getReglaValidacion() != null
                            && (!isUpdate || (isUpdate
                            && reglaValidacionToUpdate.getReglaValidacionId() != null
                            && r.getReglaValidacionId().compareTo(
                            reglaValidacionToUpdate.getReglaValidacionId()) != 0))
                            && !r.getReglaValidacion().isEmpty()
                            && reglaValidacionToUpdate.getReglaValidacion() != null
                            && !reglaValidacionToUpdate.getReglaValidacion().isEmpty()) {
                        String[] reglaSplit = r.getReglaValidacion().split("\\|");
                        String[] reglaToValidateSplit =
                                reglaValidacionToUpdate.getReglaValidacion().split("\\|");
                        //validamos que se tenga el mismo numero de validaciones configurada
                        if (reglaSplit.length == reglaToValidateSplit.length) {
                            boolean containsRegla = true;
                            for (int i = 0;
                                    i < reglaToValidateSplit.length
                                    && containsRegla;
                                    i++) {
                                containsRegla = r.getReglaValidacion().contains(
                                        reglaToValidateSplit[i]);

                            }
                            hayReglaDuplicada = containsRegla;
                        }
                    }
                }
            }
            return hayReglaDuplicada;
        } catch (ApplicationException e) {
            String msg = "Ocurrio un error Duplicidad en la Regla '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Eliminar una regla.
     * Elimina una regla. Permite eliminar logicamente una regla en el
     * repositorio.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param reglaDtoToDelete Dto Regla que se desea eliminar.
     * @param usuario usuario que realiza la operacion.
     * @param perfil perfil del usuario que realiza la operacion.
     * @return resultado del proceso.
     * @throws ApplicationException Lanza la excepción en caso de que ocurra un 
     * error eliminando la regla.
     */
    public boolean deleteRegla(
            CmtReglaValidacionDto reglaDtoToDelete,
            String usuario, int perfil)
            throws ApplicationException {
        try {
            boolean resultDelete;
            CmtReglaValidacion reglaValidacionToDelete =
                    reglaDtoToDelete.getReglaValidacion();
            CmtReglaValidacionDaoImpl reglaValidacionDao =
                    new CmtReglaValidacionDaoImpl();
            resultDelete = reglaValidacionDao.deleteCm(
                    reglaValidacionToDelete, usuario, perfil);
            if (!resultDelete) {
                throw new ApplicationException("No fue posible eliminar la regla");
            }
            return resultDelete;
        } catch (ApplicationException e) {
            String msg = "Ocurrio un error Eliminando en la Regla '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  e);
        }
    }

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
    public CmtReglaValidacionDto addValidacionToReglaDto(CmtReglaValidacionDto regla,
            CmtTipoValidacionMgl validacionToAdd,
            String valorValidacion)
            throws ApplicationException {
        try {
            CmtReglaValidacionDto reglaValidacionDtoResult = regla;
            for (CmtTipoValidacionDto v
                    : reglaValidacionDtoResult.getTipoValidacionList()) {
                if (v.getTipoValidacion().getIdTipoValidacion().
                        compareTo(
                        validacionToAdd.getIdTipoValidacion()) == 0) {
                    throw new ApplicationException("La regla ya posee la "
                            + "validacion que se intenta agregar");
                }
            }
            if (reglaValidacionDtoResult.getTipoValidacionList() == null
                    || reglaValidacionDtoResult.getTipoValidacionList().isEmpty()) {
                reglaValidacionDtoResult.setTipoValidacionList(
                        new ArrayList<CmtTipoValidacionDto>());
            }
            CmtTipoValidacionDto valDtoAdd = new CmtTipoValidacionDto();
            CmtTipoValidacionMglManager tipoValidacionMglManager =
                    new CmtTipoValidacionMglManager();
            validacionToAdd = tipoValidacionMglManager.findById(validacionToAdd);
            valDtoAdd.setTipoValidacion(validacionToAdd);
            valDtoAdd.setValueValidacion(valorValidacion);
            reglaValidacionDtoResult.getTipoValidacionList().add(valDtoAdd);

            return reglaValidacionDtoResult;
        } catch (ApplicationException e) {
                 String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Agregar una validacion a una Regla.
     * Agrega una validacion a una Regla. Permite Adicionar un validacion a una
     * regla.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param regla Regla a la que se le adicionara la validacion.
     * @param validacionToAdd validacion a adicionar a la regla.
     * @param valorValidacion valor de la validacion a adicionar a la regla.
     * @param usuario usuario que realiza la accion.
     * @param perfil perfil del usuario que realiza la accion.
     * @return Dto CmtReglaValidacion con la validacion adicionada.
     * @throws ApplicationException Lanza la excepción en caso de que se intente 
     * agregar una validación existente a la regla.
     */
    public CmtReglaValidacionDto addValidacionToRegla(CmtReglaValidacion regla,
            CmtTipoValidacionMgl validacionToAdd,
            String valorValidacion,
            String usuario, int perfil)
            throws ApplicationException {
        try {
            CmtReglaValidacionDto reglaValidacionDtoResult = null;
            CmtReglaValidacionDaoImpl reglaValidacionDao =
                    new CmtReglaValidacionDaoImpl();
            regla = reglaValidacionDao.find(regla.getReglaValidacionId());
            if (regla != null) {
                //validamos que la regla no posea la validacion que se quiere agregar
                reglaValidacionDtoResult = convertReglaToDto(regla);
                for (CmtTipoValidacionDto v
                        : reglaValidacionDtoResult.getTipoValidacionList()) {
                    if (v.getTipoValidacion().getIdTipoValidacion().
                            compareTo(
                            validacionToAdd.getIdTipoValidacion()) == 0) {
                        throw new ApplicationException("La regla ya posee la "
                                + "validacion que se intenta agregar");
                    }
                }
                String valToAddStr = regla.getReglaValidacion()
                        + "|"
                        + validacionToAdd.getIdTipoValidacion().toString()
                        + "="
                        + valorValidacion;
                regla.setReglaValidacion(valToAddStr);
                regla = reglaValidacionDao.updateCm(regla, usuario, perfil);
                reglaValidacionDtoResult = convertReglaToDto(regla);
            }
            return reglaValidacionDtoResult;
        } catch (ApplicationException e) {
                 String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Convierte la entidad a DTO.
     * Convierte la entidad a DTO. Permite convertir la entidad
     * CmtReglaValidacion a un DTO.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param reglaToConvert Regla a convertir a DTO.
     * @return Dto CmtReglaValidacion.
     * @throws ApplicationException Lanza la excepción en caso de que ocurra un 
     * error al convertir el dto.
     */
    public CmtReglaValidacionDto convertReglaToDto(
            CmtReglaValidacion reglaToConvert)
            throws ApplicationException {
        CmtReglaValidacionDto reglaDtoResult = null;
        try {
            //Validamos que se tenga la configuracion de la regla
            if (validaEstructuraRegla(reglaToConvert)) {
                reglaDtoResult = new CmtReglaValidacionDto();
                //Asignamos la regla al DTO
                reglaDtoResult.setReglaValidacion(reglaToConvert);

                List<CmtTipoValidacionDto> tipoValidacionDtoList =
                        new ArrayList<CmtTipoValidacionDto>();
                //Obtenemos las reglas configuradas para el proyecto
                String[] reglaSplit = reglaToConvert.getReglaValidacion().split("\\|");
                for (String s : reglaSplit) {
                    String[] valueSplit = s.split("=");
                    CmtTipoValidacionDto tipoValidacionDto =
                            new CmtTipoValidacionDto();
                    CmtTipoValidacionMglManager tipoValidacionManager =
                            new CmtTipoValidacionMglManager();
                    CmtTipoValidacionMgl tipoValidacion =
                            new CmtTipoValidacionMgl();
                    //Buscamos el tipo de validacion por el id
                    tipoValidacion.setIdTipoValidacion(
                            new BigDecimal(valueSplit[0]));
                    tipoValidacion = tipoValidacionManager.findByIdActive(
                            tipoValidacion.getIdTipoValidacion());
                    
                    if (tipoValidacion != null) {
                        tipoValidacionDto.setTipoValidacion(tipoValidacion);
                        tipoValidacionDto.setValueValidacion(valueSplit[1]);
                        //asignamos el valor del tipo de validacion
                        tipoValidacionDtoList.add(tipoValidacionDto);
                    }
                    
                }
                
                reglaDtoResult.setTipoValidacionList(tipoValidacionDtoList);
            }
            return reglaDtoResult;
        } catch (ApplicationException e) {
                 String msg = "Ocurrio un error convirtiendo la regla a DTO '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Convertir un DTO a la entidad.
     * Convierte un DTO a la entidad. Permite convertir DTO a la entidad
     * CmtReglaValidacion.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param reglaDtoToConvert DTO a convertir en CmtReglaValidacion.
     * @return CmtReglaValidacion a partir del DTO.
     * @throws ApplicationException Lanza la excepción en caso de que ocurra un 
     * error actualizando la regla.
     */
    public CmtReglaValidacion convertFromDtoToRegla(
            CmtReglaValidacionDto reglaDtoToConvert)
            throws ApplicationException {
        CmtReglaValidacion reglaResult = null;
        try {
            //validamos que el DTO posea un regla y un proyecto
            if (reglaDtoToConvert != null
                    && reglaDtoToConvert.getReglaValidacion() != null) {
                if (reglaDtoToConvert.getReglaValidacion().getProyecto() == null
                        || reglaDtoToConvert.getReglaValidacion().getProyecto().
                        getBasicaId() == null) {
                    throw new ApplicationException(
                            "La regla a convertir no tiene un proyecto asignado");
                }
                reglaResult = reglaDtoToConvert.getReglaValidacion();
            } else {
                throw new ApplicationException("No existe un regla a convertir");
            }
            //convertimos el listado de validaciones DTO a un String
            if (reglaDtoToConvert.getTipoValidacionList() != null) {
                String confReglaStr = "";
                for (CmtTipoValidacionDto td
                        : reglaDtoToConvert.getTipoValidacionList()) {
                    confReglaStr += "|"
                            + td.getTipoValidacion().getIdTipoValidacion()
                            + "="
                            + td.getValueValidacion();
                }
                if (!confReglaStr.isEmpty()) {
                    confReglaStr = confReglaStr.substring(1);
                }
                //asignamos el string resultante a la regla
                reglaResult.setReglaValidacion(confReglaStr);
            }
            return reglaResult;
        } catch (ApplicationException e) {
            String msg = "Ocurrio un error convirtiendo de DTO a la regla '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
				 LOGGER.error(msg);
				 throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Validar la estructura de una regla.
     * Valida la estructura de una regla. Permite validar la estructura de la
     * regla para determinar si esta bien configurada.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param reglaToValidate Regla a validar estructura.
     * @return resultado de la validacion de la estructura de la regla.
     * @throws ApplicationException Lanza la excepción en caso de que la regla 
     * no posea configuración.
     */
    public boolean validaEstructuraRegla(
            CmtReglaValidacion reglaToValidate)
            throws ApplicationException {
        boolean resultValidacion = false;
        if (reglaToValidate.getReglaValidacion() != null
                && !reglaToValidate.getReglaValidacion().trim().isEmpty()) {
            //validamos si existe multiples validaciones la configuracion de la regla
            if (reglaToValidate.getReglaValidacion().contains("|")
                    && reglaToValidate.getReglaValidacion().contains("=")) {
                resultValidacion = true;
                //validamos si existe solo una regla configurada
            } else if (reglaToValidate.getReglaValidacion().contains("=")) {
                resultValidacion = true;
            } else {
                String msn = "La regla con ID %1$.0f no posee configuracion Adecuada";
                throw new ApplicationException(
                        String.format(msn, reglaToValidate.getReglaValidacionId()));
            }
        } else {
            String msn = "La regla con ID %1$.0f no posee configuracion";
            throw new ApplicationException(
                    String.format(msn, reglaToValidate.getReglaValidacionId()));
        }
        return resultValidacion;
    }

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
    public int countByFiltroPaginado(
            CmtBasicaMgl proyecto)
            throws ApplicationException {
        CmtReglaValidacionDaoImpl reglaValidacionDaoImpl
                = new CmtReglaValidacionDaoImpl();
        return reglaValidacionDaoImpl.countByFiltroPaginado(proyecto);
    }


    /**
     * Busca en la base de datos una regla validacioin que cumpla con una id 
     * basica.
     * Busca en la base de datos una regla validacioin que cumpla con una id
     * basica ademas que esté activa, luego se compara con el parametro
     * validacionRegla y se determina si es igual o no.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @param validacionRegla valor String que representa la regla que se quiere
     * validar que cumpla con alguna de las presentes en la base de datos.
     * Ejemplo 1=S|2=N|3=P|4=NA. donde el valor número representa el id del tipo
     * devalidacion.
     *
     * @param basicaProyecto instancia con los valores de basica para ser
     * relacionados con la consulta.
     * @return true en caso que exista un registro en la base de datos que
     * cumpla las condiciones y false en caso que se presente cualquier otro
     * caso.
     * 
     * @throws ApplicationException Lanza la excepción en caso de que ocurra un 
     * error ejecutando la sentencia.
     */
    public boolean isViable(String validacionRegla, CmtBasicaMgl basicaProyecto) 
            throws ApplicationException {
        CmtReglaValidacionDaoImpl daoImpl=new CmtReglaValidacionDaoImpl();
        return daoImpl.isViable( validacionRegla,basicaProyecto);
    }
    
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
        (List<CmtReglaValidacionDto> reglas){
            
        for (int i=0;i<reglas.size();i++) {
            List<CmtTipoValidacionDto> tiposValidacionResultante = 
                    new ArrayList<CmtTipoValidacionDto>();
            for (CmtTipoValidacionDto tipoValidacion : reglas.get(i).getTipoValidacionList()) {
                if (tipoValidacion.getTipoValidacion().getEstadoRegistro() != 0) {
                    tiposValidacionResultante.add(tipoValidacion);
                }
            }
            
            if (tiposValidacionResultante.size() > 0) {
                reglas.get(i).setTipoValidacionList(tiposValidacionResultante);
            }else{
                reglas.remove(i);
            }
        }
        
        return reglas;
    }
    
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
            throws ApplicationException{
        CmtReglaValidacionDaoImpl reglaValidacionDao = new CmtReglaValidacionDaoImpl();
        CmtReglaValidacion reglaValidacion = reglaValidacionDao.find(id);
        CmtReglaValidacionDto reglaDto = convertReglaToDto(reglaValidacion);
        
        if (reglaDto.getTipoValidacionList().size()>1) {
            return true;
        }
        
        throw  new ApplicationException(MensajeTipoValidacion.ELIMINACION_RESTRINGIDA.getValor());
    }
        
}
