package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.conexion.ps.ConsultaProcedimientos;
import co.com.claro.mgl.businessmanager.address.*;
import co.com.claro.mgl.constantes.cobertura.Constants;
import co.com.claro.mgl.dao.impl.DetalleFactibilidadMglDaoImpl;
import co.com.claro.mgl.dao.impl.FactibilidadMglDaoImpl;
import co.com.claro.mgl.dao.impl.VisorFactibilidadDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtDireccionDetalleMglDaoImpl;
import co.com.claro.mgl.dtos.CmtDireccionDetalladaMglDto;
import co.com.claro.mgl.dtos.DireccionSubDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.*;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.rest.dtos.*;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.CmtCoverEnum;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.response.ResponseValidacionViabilidad;
import co.com.claro.visitasTecnicas.business.ConsultaEspecificaManager;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.SubDireccion;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.util.ConsultaNodoSitiData;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author valbuenayf
 */
public class CmtDireccionDetalleMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtDireccionDetalleMglManager.class);
    // se asigna 15 ceros para complementar el codigocombinado para el campo DIRECCION_IX
    private static final String CEROS = "000000000000000";
    private static final String ROW_NUMBER = "ROW_NUM_RESULTS";
    private static final String TIPO_TECNOLOGIA = "DTH";
    private static final String TIPO_VIVIENDA = "CASA";
    private static final String CONTACTO = "Hitss";
    private static final String TELEFONO_CONTACTO = "0000000";
    private static final String OBSERVACIONES = "Creación automática de solicitud DTH";
    private static final String TIEMPO_DURACION_SOLICITUD = "00:01:00";
    private static final String RESPUESTA_GESTION = "HHPP CREADO";
    private static final String RESPUESTA = "AUTOGESTION CREACION HHPP DTH INSPIRA";
    private static final String CANAL_VENTAS = "INSPIRA";
    private static final String ID_CASOT_CRM = "0";
    private static final String VALIDAR_ESTRATO = "^[0-7]";
    private DrDireccion validarDireccion;
    private DireccionMgl direccion;
    private final HashMap<BigDecimal, CmtSubCcmmTechnologyDto> hashMapCmtSubCcmmTechnologyDto
            = new HashMap<>();
    private final CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDaoImpl = new CmtDireccionDetalleMglDaoImpl();
    /*Brief 98062*/
    private DrDireccion drDireccionConsultaSitiData;
    /*Cierre Brief 98062*/

    /**
     * valbuenayf Metodo para consultar la direccion general por texto o
     * tabulada
     *
     * @param cmtDireccionRequestDto
     * @return
     */
    public CmtAddressGeneralResponseDto ConsultaDireccionGeneral(CmtDireccionRequestDto cmtDireccionRequestDto) throws ExceptionDB, CloneNotSupportedException, ApplicationException {

        CmtAddressGeneralResponseDto respuesta = new CmtAddressGeneralResponseDto(); 
        List<CmtAddressGeneralDto> auxLista;
        Initialized.getInstance();
        validarDireccion = null;
        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
        
        try {
            if (cmtDireccionRequestDto == null) {
                respuesta.setMessage("Error: Debe crear un correcto request para consumir el servicio");
                respuesta.setMessageType("E");
                return respuesta;
            }
            
            if ((cmtDireccionRequestDto.getDireccion() == null || cmtDireccionRequestDto.getDireccion().isEmpty())
                    && cmtDireccionRequestDto.getDireccionTabulada() == null) {
                respuesta.setMessage("Error: Debe ingresar una direccion en texto o una direccion tabulada para ser consultada");
                respuesta.setMessageType("E");
                return respuesta;
            }


            //El codigo dane es obligatorio para realizar la busqueda de la ciudad
            if (cmtDireccionRequestDto.getCodigoDane() == null || cmtDireccionRequestDto.getCodigoDane().equals("")) {
                respuesta.setMessage("Error: ".concat(Constant.MSG_DIR_CODIGO_DANE));
                respuesta.setMessageType("E");
                return respuesta;
            } else {
                if (cmtDireccionRequestDto.getCodigoDane().length() < 7) {
                    respuesta.setMessage("El codigo dane debe ser de minimo 8 digitos");
                    respuesta.setMessageType("E");
                    return respuesta;
                }
            } 

           GeograficoPoliticoMgl ciudadGpo = null;
            
            try {
                //El codigo dane es debe ser valido
                ciudadGpo = buscarCiudad(cmtDireccionRequestDto.getCodigoDane());
                if (ciudadGpo == null) {
                    respuesta.setMessage("Error: ".concat(Constant.MSG_DIR_CODIGO_DANE_ERROR));
                    respuesta.setMessageType("E");
                    return respuesta;
                }
            } catch (ApplicationException ex) {
                LOGGER.error(ex.getMessage(), ex);
                respuesta.setMessage("Error: ".concat(Constant.MSG_DIR_CODIGO_DANE_ERROR)
                        .concat(" (".concat(ex.getMessage()).concat(").")));
                respuesta.setMessageType("E");
                return respuesta;
            }
            
            Integer rowNum = registroMaximo(ROW_NUMBER);

            //Inicio buscar por texto de formato IGAC
            if (cmtDireccionRequestDto.getDireccion() != null && !cmtDireccionRequestDto.getDireccion().isEmpty()) {

                auxLista = buscarDireccionGeneralTexto(cmtDireccionRequestDto.getDireccion(), ciudadGpo, rowNum,
                        cmtDireccionRequestDto.isIsDth());

                if (auxLista != null && !auxLista.isEmpty()) {// si encuentra direcciones retorna la lista
                    
                    //algoritmo que obtiene direcciones principales apartir de posibles direcciones alternas
                    List<CmtAddressGeneralDto> auxListaFinal = obtenerDireccionPrincipalByPosibleDireccionAlterna(auxLista, null);
                    
                    respuesta.setIdCentroPoblado(ciudadGpo.getGpoId().toString());
                    respuesta.setCentroPoblado(ciudadGpo.getGpoNombre());
                    respuesta.setListAddresses(auxListaFinal);
                    respuesta.setMessage("Operacion Exitosa");
                    respuesta.setMessageType("I");
                    // se valida la direccion para crear la solicitud
                    if (auxListaFinal.size() == 1 && cmtDireccionRequestDto.isIsDth()) {
                        if (validarDireccion != null) {
                            respuesta.setMessage(respuestaSolicitud(validarDireccion, respuesta.getMessage(), ciudadGpo, cmtDireccionRequestDto));
                        }
                    }

                    //JDHT Ajuste de mensaje de dirección antigua-nueva
                    if (auxListaFinal.size() == 1) {
                        if (auxListaFinal.get(0).getSplitAddres() != null
                                && auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada() != null
                                && !auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada().isEmpty()) {
                            CmtDireccionDetalladaMgl direccionDetallada = findById(new BigDecimal(auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada()));
                            if (direccionDetallada != null && direccionDetallada.getDireccion() != null) {
                                if (direccionDetallada.getDireccion().getDirOrigen() != null
                                        && !direccionDetallada.getDireccion().getDirOrigen().isEmpty()
                                        && direccionDetallada.getDireccion().getDirOrigen().equalsIgnoreCase("ANTIGUA")) {
                                    respuesta.setMessage(respuesta.getMessage() + "[Existe una direccon mas reciente para este predio]");
                                }
                            }
                        }
                    }
                    return respuesta;
                }

                // consultar la direccion por texto en servi informacion texto
                DrDireccion direccionTabulada = consultarServiInformacionTexto(cmtDireccionRequestDto.getDireccion(), ciudadGpo);
               
                
                if (direccionTabulada != null) {
                     if(cmtDireccionRequestDto.getDireccion() != null && cmtDireccionRequestDto.getDireccionTabulada() != null 
                        && cmtDireccionRequestDto.getDireccionTabulada().getBarrio() != null
                        && !cmtDireccionRequestDto.getDireccionTabulada().getBarrio().isEmpty()){
                   direccionTabulada.setBarrio(cmtDireccionRequestDto.getDireccionTabulada().getBarrio());
                }
                    auxLista = buscarDireccionGeneralTabulada(direccionTabulada, ciudadGpo, rowNum, cmtDireccionRequestDto.isIsDth());

                    if (auxLista != null && !auxLista.isEmpty()) {// si encuentra direcciones retorna la lista                        
                    //algoritmo que obtiene direcciones principales apartir de posibles direcciones alternas
                    List<CmtAddressGeneralDto> auxListaFinal = obtenerDireccionPrincipalByPosibleDireccionAlterna(auxLista, direccionTabulada);                        
                        
                        respuesta.setIdCentroPoblado(ciudadGpo.getGpoId().toString());
                        respuesta.setCentroPoblado(ciudadGpo.getGpoNombre());
                        respuesta.setListAddresses(auxListaFinal);
                        respuesta.setMessage("Operacion Exitosa");
                        respuesta.setMessageType("I");
                        // se valida la direccion para crear la solicitud
                        if (auxListaFinal.size() == 1 && cmtDireccionRequestDto.isIsDth()) {
                            respuesta.setMessage(respuestaSolicitud(direccionTabulada, respuesta.getMessage(), ciudadGpo, cmtDireccionRequestDto));
                           }
                         //JDHT Ajuste de mensaje de dirección antigua-nueva
                        if (auxListaFinal.size() == 1) {                           
                            if (auxListaFinal.get(0).getSplitAddres() != null
                                    && auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada() != null
                                    && !auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada().isEmpty()) {
                                CmtDireccionDetalladaMgl direccionDetallada = findById(new BigDecimal(auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada()));
                                if (direccionDetallada != null && direccionDetallada.getDireccion() != null) {
                                    if (direccionDetallada.getDireccion().getDirOrigen() != null
                                            && !direccionDetallada.getDireccion().getDirOrigen().isEmpty()
                                            && direccionDetallada.getDireccion().getDirOrigen().equalsIgnoreCase("ANTIGUA")) {
                                        respuesta.setMessage(respuesta.getMessage() + "[Existe una direccon mas reciente para este predio]");
                                    }
                                }
                            }
                        }
                        return respuesta;
                    } else {// Crea la direccion 
                        //Validar si la direcion cumple con los campos obligatorios
                        if (!validarCreacionDireccion(direccionTabulada)) {
                            respuesta.setMessage("Error: ".concat(Constant.MSG_CAMPOS_OBLIGATORIOS_DIR_ERROR));
                            respuesta.setMessageType("E");
                            return respuesta;
                        }
                        
                        ResponseMessage respuestaCrearDireccion = crearDireccionDetalle(direccionTabulada, cmtDireccionRequestDto.getCodigoDane(), ciudadGpo);
                        
                        if (respuestaCrearDireccion.isValidacionExitosa()) {
                            auxLista = buscarDireccionGeneralTabulada(direccionTabulada, ciudadGpo, rowNum, cmtDireccionRequestDto.isIsDth());
                            if (auxLista != null && !auxLista.isEmpty()) {// si encuentra direcciones retorna la lista
                                respuesta.setIdCentroPoblado(ciudadGpo.getGpoId().toString());
                                respuesta.setCentroPoblado(ciudadGpo.getGpoNombre());
                                respuesta.setListAddresses(auxLista);
                                respuesta.setMessage("Operacion Exitosa");
                                respuesta.setMessageType("I");
                                // se valida la direccion para crear la solicitud
                                if (auxLista.size() == 1 && cmtDireccionRequestDto.isIsDth()) {
                                    respuesta.setMessage(respuestaSolicitud(direccionTabulada, respuesta.getMessage(), ciudadGpo, cmtDireccionRequestDto));
                                }
                                return respuesta;
                            }
                        } else {// No se pudo crear el registro de la direccionDetallada
                            respuesta.setMessage("Error: ".concat(respuestaCrearDireccion.getMessageText()));
                            respuesta.setMessageType("E");
                        }
                    }
                } else {//La direccion no se pudo tabular
                    respuesta.setMessage("Error: ".concat(Constant.MSG_DIR_NO_TABULADA));
                    respuesta.setMessageType("E");
                }
            } else if (cmtDireccionRequestDto.getDireccionTabulada() != null) {
                
                 //Inicio buscar tabulada
                if (cmtDireccionRequestDto.getDireccionTabulada().getIdTipoDireccion() == null
                        || cmtDireccionRequestDto.getDireccionTabulada().getIdTipoDireccion().isEmpty()) {
                    respuesta.setMessage("Error: ".concat(Constant.MSG_TIPO_DIR_TABULADA));
                    respuesta.setMessageType("E");
                    return respuesta;
                }

                DrDireccion direccionTabulada = parseCmtDireccionTabuladaDtoToDrDireccion(cmtDireccionRequestDto.getDireccionTabulada());

                // Se realiza la busqueda de la direccion tabulada
                auxLista = buscarDireccionGeneralTabulada(direccionTabulada, ciudadGpo, rowNum, cmtDireccionRequestDto.isIsDth());

                if (auxLista != null && !auxLista.isEmpty()) {// si encuentra direcciones retorna la lista
                    
                    //algoritmo que obtiene direcciones principales apartir de posibles direcciones alternas
                    List<CmtAddressGeneralDto> auxListaFinal = obtenerDireccionPrincipalByPosibleDireccionAlterna(auxLista, direccionTabulada);
                    
                    respuesta.setIdCentroPoblado(ciudadGpo.getGpoId().toString());
                    respuesta.setCentroPoblado(ciudadGpo.getGpoNombre());
                    respuesta.setListAddresses(auxListaFinal);
                    respuesta.setMessage("Operacion Exitosa");
                    respuesta.setMessageType("I");
                    // se valida la direccion para crear la solicitud
                    if (auxListaFinal.size() == 1 && cmtDireccionRequestDto.isIsDth()) {
                        respuesta.setMessage(respuestaSolicitud(direccionTabulada, respuesta.getMessage(), ciudadGpo, cmtDireccionRequestDto));
                    }
                    
                    //JDHT Ajuste de mensaje de dirección antigua-nueva
                    if (auxListaFinal.size() == 1) {
                        if (auxListaFinal.get(0).getSplitAddres() != null
                                && auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada() != null
                                && !auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada().isEmpty()) {
                            CmtDireccionDetalladaMgl direccionDetallada = findById(new BigDecimal(auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada()));
                            if (direccionDetallada != null && direccionDetallada.getDireccion() != null) {
                                if (direccionDetallada.getDireccion().getDirOrigen() != null
                                        && !direccionDetallada.getDireccion().getDirOrigen().isEmpty()
                                        && direccionDetallada.getDireccion().getDirOrigen().equalsIgnoreCase("ANTIGUA")) {
                                    respuesta.setMessage(respuesta.getMessage() + "[Existe una direccon mas reciente para este predio]");
                                }
                            }
                        }
                    }
                    return respuesta;
                }

                if (direccionTabulada != null) {
                    // Crea la direccion 
                    //Validar si la direcion cumple con los campos obligatorios
                    if (!validarCreacionDireccion(direccionTabulada)) {
                        respuesta.setMessage("Error: ".concat(Constant.MSG_CAMPOS_OBLIGATORIOS_DIR_ERROR));
                        respuesta.setMessageType("E");
                        return respuesta;
                    }
                                        
                    ResponseMessage respuestCrearDir = crearDireccionDetalle(direccionTabulada, cmtDireccionRequestDto.getCodigoDane(), ciudadGpo);
                    
                    if (respuestCrearDir.isValidacionExitosa()) {
                    //ajuste de crear direccion con la principal si la ingresada es alterna
                    List<CmtDireccionMgl> direccionAlternaList = cmtDireccionMglManager.findDireccionAlternaByDireccionId(new BigDecimal(respuestCrearDir.getNuevaDireccionDetallada().getDireccion().getDirId().toString()));
                    
                    if(direccionAlternaList != null && !direccionAlternaList.isEmpty()){
                        //busqueda de direcciones con la direccion principal de la CCMM de la direccion alterna encontrada
                        List<CmtDireccionDetalladaMgl> direccionDetalladaCcmmPrincipalList = 
                                findDireccionDetallaByDirIdCCMM(direccionAlternaList.get(0).getCuentaMatrizObj().getCuentaMatrizId());

                        if (direccionDetalladaCcmmPrincipalList != null && !direccionDetalladaCcmmPrincipalList.isEmpty()) {
                            
                            for (CmtDireccionDetalladaMgl dirPrincipal : direccionDetalladaCcmmPrincipalList) {
                                if(dirPrincipal.getSubDireccion() == null){
                                  CmtDireccionDetalladaMgl dirPrincipalSinComplementos = obtenerDireccionPrincipalSinComplementos(dirPrincipal);
                                  dirPrincipalSinComplementos.setCpTipoNivel1(direccionTabulada.getCpTipoNivel1() != null ? direccionTabulada.getCpTipoNivel1().toUpperCase() : null);
                                  dirPrincipalSinComplementos.setCpTipoNivel2(direccionTabulada.getCpTipoNivel2() != null ? direccionTabulada.getCpTipoNivel2().toUpperCase() : null);
                                  dirPrincipalSinComplementos.setCpTipoNivel3(direccionTabulada.getCpTipoNivel3() != null ? direccionTabulada.getCpTipoNivel3().toUpperCase() : null);
                                  dirPrincipalSinComplementos.setCpTipoNivel4(direccionTabulada.getCpTipoNivel4() != null ? direccionTabulada.getCpTipoNivel4().toUpperCase() : null);
                                  dirPrincipalSinComplementos.setCpTipoNivel5(direccionTabulada.getCpTipoNivel5() != null ? direccionTabulada.getCpTipoNivel5().toUpperCase() : null);
                                  dirPrincipalSinComplementos.setCpTipoNivel6(direccionTabulada.getCpTipoNivel6() != null ? direccionTabulada.getCpTipoNivel6().toUpperCase() : null);
                                  
                                  dirPrincipalSinComplementos.setCpValorNivel1(direccionTabulada.getCpValorNivel1() != null ? direccionTabulada.getCpValorNivel1().toUpperCase() : null);
                                  dirPrincipalSinComplementos.setCpValorNivel2(direccionTabulada.getCpValorNivel2() != null ? direccionTabulada.getCpValorNivel2().toUpperCase() : null);
                                  dirPrincipalSinComplementos.setCpValorNivel3(direccionTabulada.getCpValorNivel3() != null ? direccionTabulada.getCpValorNivel3().toUpperCase() : null);
                                  dirPrincipalSinComplementos.setCpValorNivel4(direccionTabulada.getCpValorNivel4() != null ? direccionTabulada.getCpValorNivel4().toUpperCase() : null);
                                  dirPrincipalSinComplementos.setCpValorNivel5(direccionTabulada.getCpValorNivel5() != null ? direccionTabulada.getCpValorNivel5().toUpperCase() : null);
                                  dirPrincipalSinComplementos.setCpValorNivel6(direccionTabulada.getCpValorNivel6() != null ? direccionTabulada.getCpValorNivel6().toUpperCase() : null);
                                 
                                        DrDireccion dirTabulada = parseCmtDireccionDetalladaMglToDrDireccion(dirPrincipalSinComplementos);
                                        ResponseMessage respuestCrearDireccionPrincipal = crearDireccionDetalle(dirTabulada, cmtDireccionRequestDto.getCodigoDane(), ciudadGpo);
                                        if (!respuestCrearDireccionPrincipal.isValidacionExitosa()) {
                                            respuesta.setMessage("Error: ".concat(respuestCrearDireccionPrincipal.getMessageText()));
                                            respuesta.setMessageType("E");
                                            return respuesta;
                                        }
                                        break;
                                    }

                                }
                            }
                        }

                        auxLista = buscarDireccionGeneralTabulada(direccionTabulada, ciudadGpo, rowNum, cmtDireccionRequestDto.isIsDth());

                        if (auxLista != null && !auxLista.isEmpty()) {// si encuentra direcciones retorna la lista
                            //algoritmo que obtiene direcciones principales apartir de posibles direcciones alternas
                            List<CmtAddressGeneralDto> auxListaFinal = obtenerDireccionPrincipalByPosibleDireccionAlterna(auxLista, direccionTabulada);                            
                            
                            respuesta.setIdCentroPoblado(ciudadGpo.getGpoId().toString());
                            respuesta.setCentroPoblado(ciudadGpo.getGpoNombre());
                            respuesta.setListAddresses(auxListaFinal);
                            respuesta.setMessage("Operacion Exitosa");
                            respuesta.setMessageType("I");
                            // se valida la direccion para crear la solicitud
                            if (auxListaFinal.size() == 1 && cmtDireccionRequestDto.isIsDth()) {
                                respuesta.setMessage(respuestaSolicitud(direccionTabulada, respuesta.getMessage(), ciudadGpo, cmtDireccionRequestDto));
                            }
                            
                            //JDHT Ajuste de mensaje de dirección antigua-nueva
                            if (auxListaFinal.size() == 1) {
                                if (auxListaFinal.get(0).getSplitAddres() != null
                                        && auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada() != null
                                        && !auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada().isEmpty()) {
                                    CmtDireccionDetalladaMgl direccionDetallada = findById(new BigDecimal(auxListaFinal.get(0).getSplitAddres().getIdDireccionDetallada()));
                                    if (direccionDetallada != null && direccionDetallada.getDireccion() != null) {
                                        if (direccionDetallada.getDireccion().getDirOrigen() != null
                                                && !direccionDetallada.getDireccion().getDirOrigen().isEmpty()
                                                && direccionDetallada.getDireccion().getDirOrigen().equalsIgnoreCase("ANTIGUA")) {
                                            respuesta.setMessage(respuesta.getMessage() + "[Existe una direccon mas reciente para este predio]");
                                        }
                                    }
                                }
                            }
                            return respuesta;
                        }
                    } else {// No sepudo crear el registro de la direccionDetallada
                        respuesta.setMessage("Error: ".concat(respuestCrearDir.getMessageText()));
                        respuesta.setMessageType("E");
                    }
                }

            }else{
                respuesta.setMessage("Error: Es necesario ingrear una direccion texto o una direccion tabulada para consumir el servicio.");
                respuesta.setMessageType("E");
            }
            //Fin buscar tabulada

        } catch (ApplicationException e) {
            LOGGER.error("Error en consulta direccion general de CmtDireccionDetalleMglManager " + e.getMessage());
            respuesta.setMessage("Error: ".concat(e.getMessage()));
            respuesta.setMessageType("E");
            return respuesta;
        }
        return respuesta;
    }

    /**
     * valbuenayf Metodo para consultar la direccion general por texto y codgo
     * dane
     *
     * @param dir
     * @param ciudadGpo
     * @param rowNum
     * @param isDth
     * @return
     */
    public List<CmtAddressGeneralDto> buscarDireccionGeneralTexto(String dir, GeograficoPoliticoMgl ciudadGpo, Integer rowNum, boolean isDth) {
        CmtDireccionDetalleMglDaoImpl direccionDetalleDao = new CmtDireccionDetalleMglDaoImpl();
        List<CmtAddressGeneralDto> respuesta = null;
        List<CmtDireccionDetalladaMgl> resultList;
        try {
            if (ciudadGpo != null) {
                resultList = direccionDetalleDao.buscarDireccionTexto(ciudadGpo.getGpoId(), dir, rowNum);
                if (resultList != null && !resultList.isEmpty()) {
                    if (isDth) {//Validacion para crear una solicitud
                        if (resultList.size() == 1) {
                            validarDireccion = parseCmtDireccionDetalladaMglToDrDireccion(resultList.get(0));
                            direccion = resultList.get(0).getDireccion();
                        }
                    }
                    respuesta = parseCmtDireccionDetalladaMglToCmtAddressGeneralDto(resultList);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionGeneralTexto de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return respuesta;
    }

    /**
     * valbuenayf Metodo para consultar la direccion general tabulada y codgo
     * dane
     *
     * @param direccionTabulada
     * @param ciudadGpo
     * @param rowNum
     * @param isDth
     * @return
     */
    public List<CmtAddressGeneralDto> buscarDireccionGeneralTabulada(DrDireccion direccionTabulada, GeograficoPoliticoMgl ciudadGpo, Integer rowNum,
            boolean isDth) {
        CmtDireccionDetalleMglDaoImpl direccionDetalleDao = new CmtDireccionDetalleMglDaoImpl();
        List<CmtDireccionDetalladaMgl> resultList;
        List<CmtAddressGeneralDto> respuesta = null;
        try {
            if (ciudadGpo != null) {
                if (ciudadGpo.getGpoMultiorigen() != null && ciudadGpo.getGpoMultiorigen().equalsIgnoreCase("1")) {
                    direccionTabulada.setMultiOrigen("1");
                } else {
                    direccionTabulada.setMultiOrigen("0");
                }
            
                resultList = direccionDetalleDao.buscarDireccionTabulada(ciudadGpo.getGpoId(), direccionTabulada, true, 1, rowNum);
                if (resultList != null && !resultList.isEmpty()) { 
                    if (isDth) {//Validacion para crear una solicitud
                        if (resultList.size() == 1) {
                            direccion = resultList.get(0).getDireccion();
                        }
                    }
                    respuesta = parseCmtDireccionDetalladaMglToCmtAddressGeneralDto(resultList);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en buscarDireccionGeneralTabulada de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return respuesta;
    }

    /**
     * valbuenayf Metodo para setear lista de CmtDireccionDetalladaMgl a lista
     * de CmtAddressGeneralDto
     *
     * @param resultList
     * @return
     */
    public List<CmtAddressGeneralDto> parseCmtDireccionDetalladaMglToCmtAddressGeneralDto(List<CmtDireccionDetalladaMgl> resultList) {
        List<CmtAddressGeneralDto> respuesta = new ArrayList<>();
        try {
            if (resultList != null && !resultList.isEmpty()) {
                for (CmtDireccionDetalladaMgl direccionDet : resultList) {
                    CmtAddressGeneralDto address = new CmtAddressGeneralDto();
                    CmtDireccionGeneralTabuladaDto splitAddres = parseDireccionDetalladaToCmtDireccionGeneralTabuladaDto(direccionDet);
                    address.setSplitAddres(splitAddres);
                    respuesta.add(address);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error parseCmtDireccionDetalladaMglToCmtAddressGeneralDto de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return respuesta;
    }

    /**
     * valbuenayf Metodo para setear CmtDireccionDetalladaMgl a
     * CmtDireccionGeneralTabuladaDto
     *
     * @param direccionDet
     * @return
     */
    public CmtDireccionGeneralTabuladaDto parseDireccionDetalladaToCmtDireccionGeneralTabuladaDto(CmtDireccionDetalladaMgl direccionDet) {
        CmtDireccionGeneralTabuladaDto splitAddress = new CmtDireccionGeneralTabuladaDto();
        try {
            splitAddress.setIdDireccionDetallada(direccionDet.getDireccionDetalladaId().toString());
            splitAddress.setBarrio(direccionDet.getBarrio());
            splitAddress.setBisViaGeneradora(direccionDet.getBisViaGeneradora());
            splitAddress.setBisViaPrincipal(direccionDet.getBisViaPrincipal());
            splitAddress.setCpTipoNivel1(direccionDet.getCpTipoNivel1());
            splitAddress.setCpTipoNivel2(direccionDet.getCpTipoNivel2());
            splitAddress.setCpTipoNivel3(direccionDet.getCpTipoNivel3());
            splitAddress.setCpTipoNivel4(direccionDet.getCpTipoNivel4());
            splitAddress.setCpTipoNivel5(direccionDet.getCpTipoNivel5());
            splitAddress.setCpTipoNivel6(direccionDet.getCpTipoNivel6());
            splitAddress.setCpValorNivel1(direccionDet.getCpValorNivel1());
            splitAddress.setCpValorNivel2(direccionDet.getCpValorNivel2());
            splitAddress.setCpValorNivel3(direccionDet.getCpValorNivel3());
            splitAddress.setCpValorNivel4(direccionDet.getCpValorNivel4());
            splitAddress.setCpValorNivel5(direccionDet.getCpValorNivel5());
            splitAddress.setCpValorNivel6(direccionDet.getCpValorNivel6());
            splitAddress.setCuadViaGeneradora(direccionDet.getCuadViaGeneradora());
            splitAddress.setCuadViaPrincipal(direccionDet.getCuadViaPrincipal());
            splitAddress.setDirPrincAlt(direccionDet.getDirPrincAlt());
            splitAddress.setEstadoDirGeo(direccionDet.getEstadoDirGeo());
            splitAddress.setItTipoPlaca(direccionDet.getItTipoPlaca());
            splitAddress.setItValorPlaca(direccionDet.getItValorPlaca());
            splitAddress.setLetra3G(direccionDet.getLetra3G());
            splitAddress.setLtViaGeneradora(direccionDet.getLtViaGeneradora());
            splitAddress.setLtViaPrincipal(direccionDet.getLtViaPrincipal());
            splitAddress.setMzTipoNivel1(direccionDet.getMzTipoNivel1());
            splitAddress.setMzTipoNivel2(direccionDet.getMzTipoNivel2());
            splitAddress.setMzTipoNivel3(direccionDet.getMzTipoNivel3());
            splitAddress.setMzTipoNivel4(direccionDet.getMzTipoNivel4());
            splitAddress.setMzTipoNivel5(direccionDet.getMzTipoNivel5());
            splitAddress.setMzTipoNivel6(direccionDet.getMzTipoNivel6());
            splitAddress.setMzValorNivel1(direccionDet.getMzValorNivel1());
            splitAddress.setMzValorNivel2(direccionDet.getMzValorNivel2());
            splitAddress.setMzValorNivel3(direccionDet.getMzValorNivel3());
            splitAddress.setMzValorNivel4(direccionDet.getMzValorNivel4());
            splitAddress.setMzValorNivel5(direccionDet.getMzValorNivel5());
            splitAddress.setMzValorNivel6(direccionDet.getMzValorNivel6());
            splitAddress.setNlPostViaG(direccionDet.getNlPostViaG());
            splitAddress.setNlPostViaP(direccionDet.getNlPostViaP());
            splitAddress.setNumViaGeneradora(direccionDet.getNumViaGeneradora());
            splitAddress.setNumViaPrincipal(direccionDet.getNumViaPrincipal());
            splitAddress.setPlacaDireccion(direccionDet.getPlacaDireccion());
            splitAddress.setTipoViaGeneradora(direccionDet.getTipoViaGeneradora());
            splitAddress.setTipoViaPrincipal(direccionDet.getTipoViaPrincipal());
            splitAddress.setDireccionTexto(direccionDet.getDireccionTexto());
            if (direccionDet.getDireccion() != null) {
                splitAddress.setDireccionId(direccionDet.getDireccion().getDirId());
            }
            if (direccionDet.getSubDireccion() != null) {
                splitAddress.setSubdireccionId(direccionDet.getSubDireccion().getSdiId());
            }
        } catch (Exception e) {
            LOGGER.error("Error parseDireccionDetalladaToCmtDireccionGeneralTabuladaDto de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return splitAddress;
    }

    /**
     * valbuenayf Metodo para consultar la direccion por id de la direccion
     * detallada
     *
     * @param cmtDireccionDetalladaRequestDto
     * @return
     */
    public CmtAddressResponseDto ConsultaDireccion(CmtDireccionDetalladaRequestDto cmtDireccionDetalladaRequestDto) {
        CmtAddressResponseDto respuesta = new CmtAddressResponseDto();
        CmtAddressDto address;
        Initialized.getInstance();
        try {
            
            if(cmtDireccionDetalladaRequestDto != null){
                
                if (cmtDireccionDetalladaRequestDto.getIdDireccion() == null || cmtDireccionDetalladaRequestDto.getIdDireccion().equals(BigDecimal.ZERO)) {
                    respuesta.setMessage("Error: Debe ingresa el IdDireccion de una direccion detallada que desea consultar.");
                    respuesta.setMessageType("E");
                    return respuesta;
                }
            
            CmtDireccionDetalleMglDaoImpl direccionDetalleMglDaoImpl = new CmtDireccionDetalleMglDaoImpl();
            CmtDireccionDetalladaMgl direccionDetallada = direccionDetalleMglDaoImpl.buscarDireccionIdDireccion(cmtDireccionDetalladaRequestDto.getIdDireccion());
            if (direccionDetallada != null && direccionDetallada.getDireccionDetalladaId() != null) {
                address = parseCmtDireccionDetalladaMglToCmtAddressDto(direccionDetallada, cmtDireccionDetalladaRequestDto.getSegmento(), cmtDireccionDetalladaRequestDto.getProyecto());
                respuesta.setAddresses(address);
                respuesta.setMessage("Operacion Exitosa");
                //JDHT
                if(address != null && address.getMensajeDireccionAntigua() != null 
                        && !address.getMensajeDireccionAntigua().isEmpty() ){
                   respuesta.setMessage(respuesta.getMessage() + " " +address.getMensajeDireccionAntigua());
                }
                respuesta.setMessageType("I");
            } else {
                respuesta.setMessage("Error: ".concat("No existe la direccion detallada con el id ").concat(cmtDireccionDetalladaRequestDto.getIdDireccion().toString()));
                respuesta.setMessageType("E");
                return respuesta;
            }
            }else{
                respuesta.setMessage("Error: Debe construir una petición para consumir el servicio.");
                respuesta.setMessageType("E");
                return respuesta;
            }
        } catch (Exception e) {
            LOGGER.error("Error ConsultaDireccion de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return respuesta;

    }

    /**
     * valbuenayf Metodo para setear lista de CmtDireccionDetalladaMgl a lista
     * de CmtAddressDto
     *
     * @param direccionDet
     * @param segmento
     * @param proyecto
     * @return
     */
    public CmtAddressDto parseCmtDireccionDetalladaMglToCmtAddressDto(CmtDireccionDetalladaMgl direccionDet,
            String segmento, String proyecto) {
        CmtAddressDto address = new CmtAddressDto();
        try {
            GeograficoPoliticoMgl cityGpo = null;
            GeograficoPoliticoMgl departamentoGpo = null;
            if (direccionDet.getDireccion() != null && direccionDet.getDireccion().getUbicacion() != null
                    && direccionDet.getDireccion().getUbicacion().getGpoIdObj() != null
                    && direccionDet.getDireccion().getUbicacion().getGpoIdObj().getGpoId() != null) {
                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                cityGpo = geograficoPoliticoManager.findById(direccionDet.getDireccion().getUbicacion().getGpoIdObj().getGeoGpoId());
                departamentoGpo = buscarDepartamento(cityGpo.getGpoId());
            }

            address.setAddressId(direccionDet.getDireccionDetalladaId().toString());
            //city
            CmtCityDto city = new CmtCityDto();
            if (direccionDet.getDireccion() != null && direccionDet.getDireccion().getUbicacion() != null
                    && direccionDet.getDireccion().getUbicacion().getGpoIdObj() != null) {

                city.setCityId(direccionDet.getDireccion().getUbicacion().getGpoIdObj().getGpoId());
                city.setName(direccionDet.getDireccion().getUbicacion().getGpoIdObj().getGpoNombre());
                city.setDaneCode(direccionDet.getDireccion().getUbicacion().getGpoIdObj().getGeoCodigoDane());
                city.setClaroCode(direccionDet.getDireccion().getUbicacion().getGpoIdObj().getGpoCodigo());
                city.setGeographycLevelType(direccionDet.getDireccion().getUbicacion().getGpoIdObj().getGpoTipo());
                CmtUperGeographycLevelDto geographycState = new CmtUperGeographycLevelDto();
                if (departamentoGpo != null) {
                    geographycState.setGeographyStateId(departamentoGpo.getGpoId());
                    geographycState.setName(departamentoGpo.getGpoNombre());
                    geographycState.setDaneCode(departamentoGpo.getGeoCodigoDane());
                    geographycState.setGeographycLevelType(departamentoGpo.getGpoTipo());
                    geographycState.setUperGeographycLevel(null);
                }
                CmtUperGeographycLevelDto geographycCity = new CmtUperGeographycLevelDto();
                if (cityGpo != null) {
                    geographycCity.setGeographyStateId(cityGpo.getGpoId());
                    geographycCity.setName(cityGpo.getGpoNombre());
                    geographycCity.setDaneCode(cityGpo.getGeoCodigoDane().substring(0, 5));
                    geographycCity.setGeographycLevelType(cityGpo.getGpoTipo());
                    geographycCity.setUperGeographycLevel(geographycState);
                }

                city.setUperGeographycLevel(geographycCity);

                //Region TODO pediente por definir
                CmtRegionDto region = new CmtRegionDto();
                region.setRegionId(direccionDet.getDireccion().getUbicacion().getGpoIdObj().
                        getRegionalTecnicaObj().getBasicaId());
                region.setName(direccionDet.getDireccion().getUbicacion().getGpoIdObj().
                        getRegionalTecnicaObj().getDescripcion());
                region.setTechnicalCode(direccionDet.getDireccion().getUbicacion().getGpoIdObj().
                        getRegionalTecnicaObj().getCodigoBasica());
                city.setRegion(region);
            }
            address.setCity(city);
            List<CmtCoverDto> listCover = new ArrayList<>();

            NodoMglManager mglManager = new NodoMglManager();
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            if (direccionDet.getDireccion().getDirNodouno() != null && !direccionDet.getDireccion().getDirNodouno().equals("")
                    && !direccionDet.getDireccion().getDirNodouno().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getDirNodouno());
                NodoMgl nodeMgl = mglManager.findByCodigo(direccionDet.getDireccion().getDirNodouno());
                cover.setTechnology(CmtCoverEnum.NODO_UNO.getCodigo());
                if (nodeMgl != null) {
                    cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                    cover.setState(nodeMgl.getEstado().getCodigoBasica());
                }
                listCover.add(cover);
            }
            if (direccionDet.getDireccion().getDirNododos() != null && !direccionDet.getDireccion().getDirNododos().equals("")
                    && !direccionDet.getDireccion().getDirNododos().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getDirNododos());
                NodoMgl nodeMgl = mglManager.findByCodigo(direccionDet.getDireccion().getDirNododos());
                cover.setTechnology(CmtCoverEnum.NODO_DOS.getCodigo());
                if (nodeMgl != null) {
                    cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                    cover.setState(nodeMgl.getEstado().getCodigoBasica());
                }
                listCover.add(cover);
            }
            if (direccionDet.getDireccion().getDirNodotres() != null && !direccionDet.getDireccion().getDirNodotres().equals("")
                    && !direccionDet.getDireccion().getDirNodotres().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getDirNodotres());
                NodoMgl nodeMgl = mglManager.findByCodigo(direccionDet.getDireccion().getDirNodotres());
                cover.setTechnology(CmtCoverEnum.NODO_TRES.getCodigo());
                if (nodeMgl != null) {
                    cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                    cover.setState(nodeMgl.getEstado().getCodigoBasica());
                }
                listCover.add(cover);
            }
            if (direccionDet.getDireccion().getDirNodoDth() != null && !direccionDet.getDireccion().getDirNodoDth().equals("")
                    && !direccionDet.getDireccion().getDirNodoDth().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getDirNodoDth());
                NodoMgl nodeMgl = mglManager.findByCodigo(direccionDet.getDireccion().getDirNodoDth());
                cover.setTechnology(CmtCoverEnum.NODO_DTH.getCodigo());
                if (nodeMgl != null) {
                    cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                    cover.setState(nodeMgl.getEstado().getCodigoBasica());
                    listCover.add(cover);
                } 
            }else{
                //@cardenaslb 
                //devolver lista de nodos DTH de la Tabla Tec_Nodo  cuando el Geo no arroje info para esta tecnologia
                 CmtBasicaMgl cmtBasicaMglTecnologia;
                    CmtBasicaMgl cmtBasicaMglEstadoNodo;
                    cmtBasicaMglTecnologia = cmtBasicaMglManager.findByCodigoInternoApp(Constant.DTH);
                    cmtBasicaMglEstadoNodo = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_CERTIFICADO);
                    List<NodoMgl> listaNodosDth = mglManager.findNodosCiudadAndTecnoAndEstado(cityGpo.getGpoId(), cmtBasicaMglTecnologia, cmtBasicaMglEstadoNodo);

                    for (NodoMgl nododth : listaNodosDth) {
                        CmtCoverDto coverDth = new CmtCoverDto();
                        coverDth.setNode(nododth.getNodCodigo());
                        coverDth.setTechnology(CmtCoverEnum.NODO_DTH.getCodigo());
                        coverDth.setQualificationDate(dateToString(nododth.getNodFechaActivacion()));
                        coverDth.setState(nododth.getEstado().getCodigoBasica());
                        listCover.add(coverDth);
                    } 
            }
            if (direccionDet.getDireccion().getDirNodoMovil() != null && !direccionDet.getDireccion().getDirNodoMovil().equals("")
                    && !direccionDet.getDireccion().getDirNodoMovil().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getDirNodoMovil());                
                cover.setTechnology(CmtCoverEnum.NODO_MOVIL.getCodigo());                
                listCover.add(cover);
            }
            if (direccionDet.getDireccion().getDirNodoFtth() != null && !direccionDet.getDireccion().getDirNodoFtth().equals("")
                    && !direccionDet.getDireccion().getDirNodoFtth().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getDirNodoFtth());
                NodoMgl nodeMgl = mglManager.findByCodigo(direccionDet.getDireccion().getDirNodoFtth());
                cover.setTechnology(CmtCoverEnum.NODO_FTTH.getCodigo());
                if (nodeMgl != null) {
                    cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                    cover.setState(nodeMgl.getEstado().getCodigoBasica());
                }
                listCover.add(cover);
            }
            if (direccionDet.getDireccion().getDirNodoWifi() != null && !direccionDet.getDireccion().getDirNodoWifi().equals("")
                    && !direccionDet.getDireccion().getDirNodoWifi().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getDirNodoWifi());               
                cover.setTechnology(CmtCoverEnum.NODO_WIFI.getCodigo());                
                listCover.add(cover);
            }
            
            //nuevas coberturas JDHT
            if (direccionDet.getDireccion().getGeoZonaUnifilar()!= null && !direccionDet.getDireccion().getGeoZonaUnifilar().equals("")
                    && !direccionDet.getDireccion().getGeoZonaUnifilar().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getGeoZonaUnifilar());
                NodoMgl nodeMgl = mglManager.findByCodigo(direccionDet.getDireccion().getGeoZonaUnifilar());
                cover.setTechnology(CmtCoverEnum.NODO_FOU.getCodigo());
                if (nodeMgl != null) {
                    cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                    cover.setState(nodeMgl.getEstado().getCodigoBasica());
                }
                listCover.add(cover);
            }
            
            if (direccionDet.getDireccion().getGeoZonaGponDiseniado()!= null && !direccionDet.getDireccion().getGeoZonaGponDiseniado().equals("")
                    && !direccionDet.getDireccion().getGeoZonaGponDiseniado().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getGeoZonaGponDiseniado());               
                cover.setTechnology(CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo());               
                
                listCover.add(cover);
            }
            
            if (direccionDet.getDireccion().getGeoZonaMicroOndas()!= null && !direccionDet.getDireccion().getGeoZonaMicroOndas().equals("")
                    && !direccionDet.getDireccion().getGeoZonaMicroOndas().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getGeoZonaMicroOndas());                
                cover.setTechnology(CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo());                
                listCover.add(cover);
            }
            
              if (direccionDet.getDireccion().getGeoZonaCoberturaCavs()!= null && !direccionDet.getDireccion().getGeoZonaCoberturaCavs().equals("")
                    && !direccionDet.getDireccion().getGeoZonaCoberturaCavs().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getGeoZonaCoberturaCavs());               
                cover.setTechnology(CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo());               
                listCover.add(cover);
            }
              
               if (direccionDet.getDireccion().getGeoZonaCoberturaUltimaMilla()!= null && !direccionDet.getDireccion().getGeoZonaCoberturaUltimaMilla().equals("")
                    && !direccionDet.getDireccion().getGeoZonaCoberturaUltimaMilla().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getGeoZonaCoberturaUltimaMilla());               
                cover.setTechnology(CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo());               
                listCover.add(cover);
            }
               
            if (direccionDet.getDireccion().getGeoZonaCurrier() != null && !direccionDet.getDireccion().getGeoZonaCurrier().equals("")
                    && !direccionDet.getDireccion().getGeoZonaCurrier().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getGeoZonaCurrier());              
                cover.setTechnology(CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo());                
                listCover.add(cover);
            }
            
            if (direccionDet.getDireccion().getGeoZona3G()!= null && !direccionDet.getDireccion().getGeoZona3G().equals("")
                    && !direccionDet.getDireccion().getGeoZona3G().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getGeoZona3G());                
                cover.setTechnology(CmtCoverEnum.NODO_ZONA_3G.getCodigo());                
                listCover.add(cover);
            }
            
             if (direccionDet.getDireccion().getGeoZona4G()!= null && !direccionDet.getDireccion().getGeoZona4G().equals("")
                    && !direccionDet.getDireccion().getGeoZona4G().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getGeoZona4G());               
                cover.setTechnology(CmtCoverEnum.NODO_ZONA_4G.getCodigo());                
                listCover.add(cover);
            }
             
               if (direccionDet.getDireccion().getGeoZona5G()!= null && !direccionDet.getDireccion().getGeoZona5G().equals("")
                    && !direccionDet.getDireccion().getGeoZona5G().trim().equals("NA")) {
                CmtCoverDto cover = new CmtCoverDto();
                cover.setNode(direccionDet.getDireccion().getGeoZona5G());               
                cover.setTechnology(CmtCoverEnum.NODO_ZONA_5G.getCodigo());               
                listCover.add(cover);
            }

            if (listCover != null && !listCover.isEmpty()) {
                address.setListCover(listCover);

            } else {
                address.setListCover(null);
            }

            address.setIgacAddress(direccionDet.getDireccionTexto());
            if (direccionDet.getDireccion().getDirConfiabilidad() != null) {
                address.setAdressReliability(direccionDet.getDireccion().getDirConfiabilidad().intValue());
            } else {
                address.setAdressReliability(0);
            }
            address.setLatitudeCoordinate(direccionDet.getDireccion().getUbicacion().getUbiLatitud());
            address.setLengthCoordinate(direccionDet.getDireccion().getUbicacion().getUbiLongitud());

            CmtDireccionTabuladaDto splitAddres = pareDireccionDetToDireccionTab(direccionDet);
            address.setSplitAddres(splitAddres);

            //TODO: se debe cambiar el id de la ciudad cuando se ajuste el requerimiento de CENTRO POBLADO
            List<String> listCarrierCover = null;
            if (cityGpo != null) {
                listCarrierCover = buscarListaCoberturaEntregaIdCentroPoblado(city.getCityId());
            }

            address.setListCarrierCover(listCarrierCover);
            if (direccionDet.getSubDireccion() == null) {
                address.setStratum(direccionDet.getDireccion().getDirEstrato().toString());
            } else {
                if (direccionDet.getSubDireccion().getSdiEstrato() != null) {
                    address.setStratum(direccionDet.getSubDireccion().getSdiEstrato().toString());
                }
            }
            List<CmtHhppDto> listHhpps = null;
            List<HhppMgl> resultado = null;
            HhppMglManager hhppMglManager = new HhppMglManager();

            boolean isDir = false;
            boolean isSubDir = false;
            boolean continuar = false;

            // si tiene subdireccion se envian los hhpp de la subdireccion
            if (direccionDet.getSubDireccion() != null && direccionDet.getSubDireccion().getSdiId() != null) {
                resultado = hhppMglManager.findHhppSubDireccion(direccionDet.getSubDireccion().getSdiId());
                isSubDir = true;
            }// si tiene direccion se envian los hhpp de la direccion
            else if (direccionDet.getDireccion() != null && direccionDet.getDireccion().getDirId() != null) {
                resultado = hhppMglManager.findHhppDireccion(direccionDet.getDireccion().getDirId());
                isDir = true;
            }
            
            //JDHT Si la direccion es antigua agrega mensaje de direccion mas reciente.
            if(direccionDet.getDireccion() != null && direccionDet.getDireccion().getDirId() != null
                    && direccionDet.getDireccion().getDirOrigen() != null
                    && !direccionDet.getDireccion().getDirOrigen().isEmpty()
                    && direccionDet.getDireccion().getDirOrigen().equalsIgnoreCase("ANTIGUA")){
                address.setMensajeDireccionAntigua("[Existe una direccion mas reciente para este predio]");
            }else{
                address.setMensajeDireccionAntigua(null);
            }

            if (resultado != null && !resultado.isEmpty()) {
                listHhpps = new ArrayList<CmtHhppDto>();
                for (HhppMgl h : resultado) {

                    if (isSubDir && h.getSubDireccionObj() != null && h.getSubDireccionObj().getSdiId() != null) {
                        continuar = true;
                    } else if (isDir && h.getSubDireccionObj() == null) {
                        continuar = true;
                    } else {
                        continuar = false;
                    }

                    if (continuar) {
                        CmtTipoValidacionMglManager valMglManager = new CmtTipoValidacionMglManager();
                        ResponseValidacionViabilidad responseValVia;

                        CmtHhppDto hhPp = new CmtHhppDto();
                        hhPp.setHhppId(h.getHhpId());
                        
                        if(h.getSuscriptor() != null && !h.getSuscriptor().isEmpty() ){
                          hhPp.setAccountNumber(h.getSuscriptor());
                        }

                        List<String> notasHhpp = listaNotasAdicionalesHhpp(h.getHhpId());
                        hhPp.setNotasHhpp(notasHhpp);

                        hhPp.setTechnology(h.getNodId().getNodTipo().getCodigoBasica());
                        hhPp.setTechnology(h.getNodId() != null && h.getNodId().getNodTipo() != null ? h.getNodId().getNodTipo().getCodigoBasica() : "");

                        Map<String, String> estadoCM = new HashMap<String, String>();
                        if (h.getCmtTecnologiaSubId() == null) {
                            estadoCM.put("CodigoEstadoCM", "NA");
                            estadoCM.put("DescripcionEstadoCM", "NA");
                        } else {
                            estadoCM.put("CodigoEstadoCM", h.getCmtTecnologiaSubId().getBasicaIdEstadosTec().getCodigoBasica());
                            estadoCM.put("DescripcionEstadoCM", h.getCmtTecnologiaSubId().getBasicaIdEstadosTec().getNombreBasica());
                        }
                        CmtBasicaMgl estadoNodo = h.getNodId().getEstado();
                        String nombreRed = h.getNodId().getNodTipo().getCodigoBasica();

                        responseValVia = valMglManager.procesarViabilidadInspira(estadoNodo,
                                estadoCM, h.getEhhId().getEhhID(), address.getStratum(), nombreRed,
                                segmento, proyecto);

                        hhPp.setViability(responseValVia);
                        hhPp.setState(h.getEhhId().getEhhID());
                        if (h.getHhpTipo() != null && !h.getHhpTipo().isEmpty()) {
                            TipoHhppMglManager tipoHhppMglManager = new TipoHhppMglManager();
                            hhPp.setConstructionType(tipoHhppMglManager.findById(h.getHhpTipoUnidad()).getThhValor());
                        }
                        if (h.getThcId() != null) {
                            TipoHhppConexionMglManager tipoHhppConexionMglManager = new TipoHhppConexionMglManager();
                            hhPp.setRushtype(tipoHhppConexionMglManager.findById(h.getThcId()).getThcNombre());
                        }

                        CmtNodeDto node = new CmtNodeDto();
                        node.setCodeNode(h.getNodId().getNodCodigo());
                        node.setState(h.getNodId().getEstado().getCodigoBasica());
                        node.setNodeName(h.getNodId().getNodNombre());
                        node.setQualificationDate(dateToString(h.getNodId().getNodFechaActivacion()));
                        node.setTechnology(h.getNodId().getNodTipo().getCodigoBasica());
                        hhPp.setNode(node);

                        CmtSubCcmmTechnologyDto technology = null;

                        if (h.getCmtTecnologiaSubId() != null) {// Technology datos de cuenta matriz
                            if (hashMapCmtSubCcmmTechnologyDto.containsKey(h.getCmtTecnologiaSubId().getTecnoSubedificioId())) {
                                technology = hashMapCmtSubCcmmTechnologyDto.get(h.getCmtTecnologiaSubId().getTecnoSubedificioId());
                            } else {
                                technology = new CmtSubCcmmTechnologyDto();

                                technology.setCcmmId(h.getCmtTecnologiaSubId().getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId());
                                technology.setQualificationDate(dateToString(h.getCmtTecnologiaSubId().getFechaHabilitacion()));
                                technology.setState(h.getCmtTecnologiaSubId().getBasicaIdEstadosTec().getAbreviatura());
                                technology.setTechnology(h.getCmtTecnologiaSubId().getBasicaIdTecnologias().getCodigoBasica());
                                technology.setSubCcmmId(h.getCmtTecnologiaSubId().getSubedificioId().getSubEdificioId());
                                technology.setSubBuildingName(h.getCmtTecnologiaSubId().getSubedificioId().getNombreSubedificio());
                                technology.setBuildingName(h.getCmtTecnologiaSubId().getSubedificioId().
                                        getCmtCuentaMatrizMglObj().getNombreCuenta());
                                technology.setBuildingAddress(h.getCmtTecnologiaSubId().getSubedificioId()
                                        .getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getDireccion());

                                if (h.getCmtTecnologiaSubId().getSubedificioId().getCompaniaAdministracionObj() != null) {
                                    technology.setManagementCompany(h.getCmtTecnologiaSubId().getSubedificioId().
                                            getCompaniaAdministracionObj().getNombreCompania());
                                    technology.setBuildingContact(h.getCmtTecnologiaSubId().getSubedificioId().
                                            getCompaniaAdministracionObj().getNombreContacto());
                                    technology.setPhoneContactOne(h.getCmtTecnologiaSubId().getSubedificioId().
                                            getCompaniaAdministracionObj().getTelefonos());
                                    technology.setPhoneContactTwo(h.getCmtTecnologiaSubId().getSubedificioId().
                                            getCompaniaAdministracionObj().getTelefono2());
                                    technology.setEmailCompany(h.getCmtTecnologiaSubId().getSubedificioId().
                                            getCompaniaAdministracionObj().getEmail());
                                } else if (h.getCmtTecnologiaSubId().getSubedificioId().getCmtCuentaMatrizMglObj().
                                        getSubEdificioGeneral().getCompaniaAdministracionObj() != null) {
                                    technology.setManagementCompany(h.getCmtTecnologiaSubId().
                                            getSubedificioId().getCmtCuentaMatrizMglObj().
                                            getSubEdificioGeneral().getCompaniaAdministracionObj().getNombreCompania());
                                    technology.setBuildingContact(h.getCmtTecnologiaSubId().
                                            getSubedificioId().getCmtCuentaMatrizMglObj().
                                            getSubEdificioGeneral().getCompaniaAdministracionObj().getNombreContacto());
                                    technology.setPhoneContactOne(h.getCmtTecnologiaSubId().
                                            getSubedificioId().getCmtCuentaMatrizMglObj().
                                            getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefonos());
                                    technology.setPhoneContactTwo(h.getCmtTecnologiaSubId().
                                            getSubedificioId().getCmtCuentaMatrizMglObj().
                                            getSubEdificioGeneral().getCompaniaAdministracionObj().getTelefono2());
                                    technology.setEmailCompany(h.getCmtTecnologiaSubId().
                                            getSubedificioId().getCmtCuentaMatrizMglObj().
                                            getSubEdificioGeneral().getCompaniaAdministracionObj().getEmail());
                                } else {
                                    technology.setManagementCompany(null);
                                    technology.setBuildingContact(h.getCmtTecnologiaSubId().
                                            getSubedificioId().getAdministrador());
                                    technology.setPhoneContactOne(h.getCmtTecnologiaSubId().
                                            getSubedificioId().getTelefonoPorteria());
                                    technology.setPhoneContactTwo(h.getCmtTecnologiaSubId().
                                            getSubedificioId().getTelefonoPorteria2());
                                }
                                if (h.getCmtTecnologiaSubId().getSubedificioId().getCompaniaAscensorObj() != null) {
                                    technology.setManagementCompany(h.getCmtTecnologiaSubId().getSubedificioId().
                                            getCompaniaAscensorObj().getNombreCompania());
                                } else if (h.getCmtTecnologiaSubId().getSubedificioId().getCmtCuentaMatrizMglObj().
                                        getSubEdificioGeneral().getCompaniaAscensorObj() != null) {
                                    technology.setManagementCompany(h.getCmtTecnologiaSubId().
                                            getSubedificioId().getCmtCuentaMatrizMglObj().
                                            getSubEdificioGeneral().getCompaniaAscensorObj().getNombreCompania());
                                }
                                if (h.getCmtTecnologiaSubId().getTipoDistribucionObj() != null) {
                                    technology.setTypeDistribution(h.getCmtTecnologiaSubId().getTipoDistribucionObj().getNombreBasica());
                                }
                                technology.setSubCcmmTechnologyId(h.getCmtTecnologiaSubId().getTecnoSubedificioId());
                                CmtNodeDto nodeDtoCcmm = new CmtNodeDto();
                                nodeDtoCcmm.setCodeNode(h.getCmtTecnologiaSubId().getNodoId().getNodCodigo());
                                nodeDtoCcmm.setNodeName(h.getCmtTecnologiaSubId().getNodoId().getNodNombre());
                                nodeDtoCcmm.setTechnology(h.getCmtTecnologiaSubId().getNodoId().getNodTipo().getCodigoBasica());
                                nodeDtoCcmm.setQualificationDate(dateToString(h.getCmtTecnologiaSubId().getNodoId().
                                        getNodFechaActivacion()));
                                nodeDtoCcmm.setState(h.getCmtTecnologiaSubId().getNodoId().getEstado().getCodigoBasica());
                                technology.setTecnologyCcmmNode(nodeDtoCcmm);
                                if (h.getCmtTecnologiaSubId().getSubedificioId().
                                        getCmtCuentaMatrizMglObj().getDireccionAlternaList() != null) {
                                    List<CmtOtherDirectionsDto> otherAddressList = new ArrayList<CmtOtherDirectionsDto>();
                                    for (CmtDireccionMgl direccionMgl : h.getCmtTecnologiaSubId().getSubedificioId().
                                            getCmtCuentaMatrizMglObj().getDireccionAlternaList()) {
                                        CmtOtherDirectionsDto otherDirectionsDto = new CmtOtherDirectionsDto();
                                        otherDirectionsDto.setAddress(direccionMgl.getDireccionObj().getDirFormatoIgac());
                                        otherAddressList.add(otherDirectionsDto);
                                    }
                                    technology.setOtherAddressList(otherAddressList);
                                }

                                technology.setAddressesWithService(h.getCmtTecnologiaSubId().getAddressWithService());
                                List<CmtCcmmMarksDto> listCcmmMarks = null;

                                if (h.getHhppSubEdificioObj() != null && h.getHhppSubEdificioObj().getListCmtBlackListMgl() != null) {
                                    listCcmmMarks = new ArrayList<CmtCcmmMarksDto>();
                                    if (!h.getHhppSubEdificioObj().getListCmtBlackListMgl().isEmpty()) {
                                        for (CmtBlackListMgl c : h.getHhppSubEdificioObj().getListCmtBlackListMgl()) {
                                            CmtCcmmMarksDto ccmmMarks = new CmtCcmmMarksDto();
                                            ccmmMarks.setMarkId(c.getBlackListObj().getBasicaId());
                                            ccmmMarks.setDescriptionMark(c.getBlackListObj().getNombreBasica());
                                            ccmmMarks.setMarkCode(c.getBlackListObj().getAbreviatura());
                                            listCcmmMarks.add(ccmmMarks);
                                        }
                                    }
                                }

                                if (listCcmmMarks != null && !listCcmmMarks.isEmpty()) {
                                    technology.setListCcmmMarks(listCcmmMarks);
                                } else {
                                    technology.setListCcmmMarks(null);
                                }
                                hashMapCmtSubCcmmTechnologyDto.put(technology.getSubCcmmTechnologyId(), technology);
                            }
                        }

                        hhPp.setSubCcmmTechnology(technology);
                        List<CmtAddresMarksDto> listAddresMarks = null;
                        if (h.getListMarcasHhpp() != null && !h.getListMarcasHhpp().isEmpty()) {
                            listAddresMarks = parseMarcasHhppMglToCmtAddresMarksDto(h.getListMarcasHhpp());
                        }
                        hhPp.setListAddresMarks(listAddresMarks);
                        listHhpps.add(hhPp);
                    }
                }
            }
            if (listHhpps != null && !listHhpps.isEmpty()) {
                address.setListHhpps(listHhpps);
            } else {
                address.setListHhpps(null);
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error parseCmtDireccionDetalladaMglToCmtAddressDto de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return address;
    }

    /**
     * yfvalbuena metodo para asignar las notas de los HHPPP
     *
     * @param hhpId
     * @return
     */
    public List<String> listaNotasAdicionalesHhpp(BigDecimal hhpId) {
        List<NotasAdicionalesMgl> lista;
        List<String> respuesta = null;
        NotasAdicionalesMglManager notasAdicionalesMglManager;
        try {
            notasAdicionalesMglManager = new NotasAdicionalesMglManager();
            lista = notasAdicionalesMglManager.findNotasAdicionalesIdHhpp(hhpId);

            if (lista != null && !lista.isEmpty()) {
                respuesta = new ArrayList<>();
                int count = 1;
                for (NotasAdicionalesMgl n : lista) {
                    if (count <= 3) {
                        respuesta.add(n.getNota());
                        count++;
                    } else {
                        break;
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error listaNotasAdicionalesHhpp de CmtDireccionDetalleMglManager " + e.getMessage());
            return null;
        }
        return respuesta;
    }

    /**
     * valbuenayf Metodo para buscar en servi informacion texto y codgo dane
     *
     * @param direccion
     * @param ciudadGpo
     * @return
     */
    public DrDireccion consultarServiInformacionTexto(String direccion, GeograficoPoliticoMgl ciudadGpo) {
        CmtValidadorDireccionesManager validadorDireccionesManager;
        DrDireccion drDireccion;
        try {
            validadorDireccionesManager = new CmtValidadorDireccionesManager();
            drDireccion = new DrDireccion();
            if (ciudadGpo != null) {
                drDireccion = validadorDireccionesManager.convertirDireccionStringADrDireccion(direccion, ciudadGpo.getGpoId());
            }

        } catch (ApplicationException ex) {
            LOGGER.error("Error consultarServiInformacionTexto de CmtDireccionDetalleMglManager " + ex.getMessage());
            return null;
        }
        return drDireccion;
    }

    //Inicio Creacion de direccion
    /**
     * valbuenayf Metodo para crear la direccion detallada
     *
     * @param direccionTabulada
     * @param codigoDane
     * @param ciudadGpo
     * @return
     */
    public ResponseMessage crearDireccionDetalle(DrDireccion direccionTabulada, String codigoDane, GeograficoPoliticoMgl ciudadGpo) {
        DrDireccionManager drDireccionMg;
        DireccionSubDireccionDto dirSubDir;
        ResponseMessage respuestaCreaDir = new ResponseMessage();
        try {
            drDireccionMg = new DrDireccionManager();
            dirSubDir = drDireccionMg.getDireccionSubDireccion(direccionTabulada);
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setCity(ciudadGpo.getGpoNombre().toUpperCase());
            addressRequest.setCodDaneVt(codigoDane);
            addressRequest.setLevel("C");
            addressRequest.setNeighborhood(drDireccionMg.obtenerBarrio(direccionTabulada));
            GeograficoPoliticoMgl departamentoGpo;
            departamentoGpo = buscarDepartamento(ciudadGpo.getGeoGpoId());
            addressRequest.setState(departamentoGpo != null ? departamentoGpo.getGpoNombre().toUpperCase() : "");
            addressRequest.setAddress(dirSubDir.getDireccion() + " " + dirSubDir.getSubDireccion());
            respuestaCreaDir = getAddressEJB().createAddress(addressRequest, "MGL", "1", "TRUE", direccionTabulada);
            
            if (respuestaCreaDir != null 
                    && respuestaCreaDir.getMessageType().equalsIgnoreCase("ERROR")
                    && (respuestaCreaDir.getMessageText().contains(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_NUEVA)
                        || respuestaCreaDir.getMessageText().contains(ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA))){
                respuestaCreaDir.setValidacionExitosa(false);
                return respuestaCreaDir;
            }
            //espinosadiea se elimina por que la direccion detallada se guarda en el metodo de createAddress 26/09/2018
            if (respuestaCreaDir != null && respuestaCreaDir.getNuevaDireccionDetallada() != null) {
                respuestaCreaDir.setValidacionExitosa(true);
                return respuestaCreaDir;//espinosadiea la direcccion ya existe en MGL o fue creada 26/09/2018
            }
        } catch (ExceptionDB e) {
            LOGGER.error("Error:CmtDireccionDetalleMglManager:crearDireccionDetalle " + e.getMessage());
            respuestaCreaDir.setValidacionExitosa(false);
            respuestaCreaDir.setMessageText(e.getMessage());
            return respuestaCreaDir;
        }
        respuestaCreaDir.setValidacionExitosa(false);
        return respuestaCreaDir;
    }

    //Fin Creacion de direccion
    /**
     * valbuenayf Metodo para buscar el Centro Poblado por el codigo dane
     *
     * @param codigoDane
     * @return
     */
    public GeograficoPoliticoMgl buscarCentroPoblado(String codigoDane) {
        GeograficoPoliticoManager geograficoPoliticoManager;
        GeograficoPoliticoMgl centroPobladoGpo = null;
        try {
            geograficoPoliticoManager = new GeograficoPoliticoManager();
            centroPobladoGpo = geograficoPoliticoManager.findCentroPobladoCodDane(codigoDane);
        } catch (Exception ex) {
            LOGGER.error("Error buscarCentroPoblado de CmtDireccionDetalleMglManager " + ex.getMessage());
        }
        return centroPobladoGpo;
    }

    /**
     * valbuenayf Metodo para buscar la ciudad por el codigo dane
     *
     * @param codigoDane C&oaucte;digo DANE.
     * @return Geogr&aacute;fico Pol&iacute;tico.
     * @throws ApplicationException
     */
    public GeograficoPoliticoMgl buscarCiudad(String codigoDane) throws ApplicationException {
        GeograficoPoliticoManager geograficoPoliticoManager;
        GeograficoPoliticoMgl ciudadGpo = null;
        try {
            geograficoPoliticoManager = new GeograficoPoliticoManager();
            ciudadGpo = geograficoPoliticoManager.findCiudadCodDane(codigoDane);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  e);
        } catch (Exception e) {
             String msg = "Error al buscar la ciudad '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  e);
        }
        return ciudadGpo;
    }

    /**
     * valbuenayf Metodo para buscar el departamento por un id centro poblado *
     * (o) idciudad
     *
     * @param idCiuCenPob
     * @return
     */
    public GeograficoPoliticoMgl buscarDepartamento(BigDecimal idCiuCenPob) {
        GeograficoPoliticoManager geograficoPoliticoManager;
        GeograficoPoliticoMgl departamentoGpo = null;
        try {
            geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl ciudadGpo = geograficoPoliticoManager.findDepartamentoIdCiudadCenPob(idCiuCenPob);
            departamentoGpo = geograficoPoliticoManager.findDepartamentoIdCiudadCenPob(ciudadGpo.getGeoGpoId());
        } catch (ApplicationException e) {
            LOGGER.error("Error buscarDepartamento de CmtDireccionDetalleMglManager " + e.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Error buscarDepartamento de CmtDireccionDetalleMglManager " + ex.getMessage());
        }
        return departamentoGpo;
    }

    /**
     * valbuenayf Metodo para buscar el pais por el id
     *
     * @param idPais
     * @return
     */
    public GeograficoPoliticoMgl buscarPais(BigDecimal idPais) {
        GeograficoPoliticoManager geograficoPoliticoManager;
        GeograficoPoliticoMgl paisGpo = null;
        try {
            geograficoPoliticoManager = new GeograficoPoliticoManager();
            paisGpo = geograficoPoliticoManager.findPaisId(idPais);
        } catch (Exception ex) {
            LOGGER.error("Error buscarPais de CmtDireccionDetalleMglManager " + ex.getMessage());
        }
        return paisGpo;
    }

    /**
     * valbuenayf Metodo para buscar el numero maximo de registros para una
     * consulta
     *
     * @param nombreParametro
     * @return
     */
    public Integer registroMaximo(String nombreParametro) {
        Integer numero;
        Integer DEFAULT = 200;
        ParametrosMglManager parametrosMglManager;
        try {
            parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametrosMgl = parametrosMglManager.findByAcronimoName(nombreParametro);
            if (parametrosMgl != null) {
                numero = Integer.parseInt(parametrosMgl.getParValor());
            } else {
                numero = DEFAULT;
            }
        } catch (ApplicationException | NumberFormatException e) {
            LOGGER.error("Error registroMaximo de CmtDireccionDetalleMglManager " + e.getMessage());
            return DEFAULT;
        }
        return numero;
    }

    /**
     * valbuenayf Metodo para buscar la lista de cobertura_entrega de un centro
     * poblado
     *
     * @param idCentroPoblado
     * @return
     */
    public List<String> buscarListaCoberturaEntregaIdCentroPoblado(BigDecimal idCentroPoblado) {
        CmtCoberturaEntregaMglManager cmtCovEntMglManager;
        List<String> resulList;
        try {
            cmtCovEntMglManager = new CmtCoberturaEntregaMglManager();
            resulList = cmtCovEntMglManager.buscarListaCoberturaEntregaIdCentroPoblado(idCentroPoblado);
        } catch (Exception e) {
            LOGGER.error("Error buscarListaCoberturaEntregaIdCentroPoblado de CmtDireccionDetalleMglManager " + e.getMessage());
            return null;
        }
        return resulList;
    }

    // Metodos de parseo
    /**
     * valbuenayf Metodo para setear una lista de MarcasHhppMgl a
     * CmtAddresMarksDto
     *
     * @param listMarcasHhpp
     * @return
     */
    public List<CmtAddresMarksDto> parseMarcasHhppMglToCmtAddresMarksDto(List<MarcasHhppMgl> listMarcasHhpp) {
        List<CmtAddresMarksDto> listAddresMarks = new ArrayList<CmtAddresMarksDto>();
        try {
            for (MarcasHhppMgl m : listMarcasHhpp) {
                if(m.getEstadoRegistro() == 1){
                CmtAddresMarksDto addresMarks = new CmtAddresMarksDto();
                    addresMarks.setMarkId(m.getMarId().getMarCodigo());
                    addresMarks.setDescriptionMark(m.getMarId().getMarNombre());
                    listAddresMarks.add(addresMarks);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error parseMarcasHhppMglToCmtAddresMarksDto de CmtDireccionDetalleMglManager " + e.getMessage());
            return null;
        }
        return listAddresMarks;
    }

    /**
     * valbuenayf Metodo para setear CmtDireccionDetalladaMgl a
     * CmtDireccionTabuladaDto
     *
     * @param direccionDet
     * @return
     */
    public CmtDireccionTabuladaDto pareDireccionDetToDireccionTab(CmtDireccionDetalladaMgl direccionDet) {
        CmtDireccionTabuladaDto splitAddres = new CmtDireccionTabuladaDto();
        try {
            splitAddres.setBarrio(direccionDet.getBarrio());
            splitAddres.setBisViaGeneradora(direccionDet.getBisViaGeneradora());
            splitAddres.setBisViaPrincipal(direccionDet.getBisViaPrincipal());
            splitAddres.setCpTipoNivel1(direccionDet.getCpTipoNivel1());
            splitAddres.setCpTipoNivel2(direccionDet.getCpTipoNivel2());
            splitAddres.setCpTipoNivel3(direccionDet.getCpTipoNivel3());
            splitAddres.setCpTipoNivel4(direccionDet.getCpTipoNivel4());
            splitAddres.setCpTipoNivel5(direccionDet.getCpTipoNivel5());
            splitAddres.setCpTipoNivel6(direccionDet.getCpTipoNivel6());
            splitAddres.setCpValorNivel1(direccionDet.getCpValorNivel1());
            splitAddres.setCpValorNivel2(direccionDet.getCpValorNivel2());
            splitAddres.setCpValorNivel3(direccionDet.getCpValorNivel3());
            splitAddres.setCpValorNivel4(direccionDet.getCpValorNivel4());
            splitAddres.setCpValorNivel5(direccionDet.getCpValorNivel5());
            splitAddres.setCpValorNivel6(direccionDet.getCpValorNivel6());
            splitAddres.setCuadViaGeneradora(direccionDet.getCuadViaGeneradora());
            splitAddres.setCuadViaPrincipal(direccionDet.getCuadViaPrincipal());
            splitAddres.setDirPrincAlt(direccionDet.getDirPrincAlt());
            splitAddres.setEstadoDirGeo(direccionDet.getEstadoDirGeo());
            splitAddres.setIdDirCatastro(direccionDet.getIdDirCatastro());
            splitAddres.setIdTipoDireccion(direccionDet.getIdTipoDireccion());
            splitAddres.setItTipoPlaca(direccionDet.getItTipoPlaca());
            splitAddres.setItValorPlaca(direccionDet.getItValorPlaca());
            splitAddres.setLetra3G(direccionDet.getLetra3G());
            splitAddres.setLtViaGeneradora(direccionDet.getLtViaGeneradora());
            splitAddres.setLtViaPrincipal(direccionDet.getLtViaPrincipal());
            splitAddres.setMzTipoNivel1(direccionDet.getMzTipoNivel1());
            splitAddres.setMzTipoNivel2(direccionDet.getMzTipoNivel2());
            splitAddres.setMzTipoNivel3(direccionDet.getMzTipoNivel3());
            splitAddres.setMzTipoNivel4(direccionDet.getMzTipoNivel4());
            splitAddres.setMzTipoNivel5(direccionDet.getMzTipoNivel5());
            splitAddres.setMzTipoNivel6(direccionDet.getMzTipoNivel6());
            splitAddres.setMzValorNivel1(direccionDet.getMzValorNivel1());
            splitAddres.setMzValorNivel2(direccionDet.getMzValorNivel2());
            splitAddres.setMzValorNivel3(direccionDet.getMzValorNivel3());
            splitAddres.setMzValorNivel4(direccionDet.getMzValorNivel4());
            splitAddres.setMzValorNivel5(direccionDet.getMzValorNivel5());
            splitAddres.setMzValorNivel6(direccionDet.getMzValorNivel6());
            splitAddres.setNlPostViaG(direccionDet.getNlPostViaG());
            splitAddres.setNlPostViaP(direccionDet.getNlPostViaP());
            splitAddres.setNumViaGeneradora(direccionDet.getNumViaGeneradora());
            splitAddres.setNumViaPrincipal(direccionDet.getNumViaPrincipal());
            splitAddres.setPlacaDireccion(direccionDet.getPlacaDireccion());
            splitAddres.setTipoViaGeneradora(direccionDet.getTipoViaGeneradora());
            splitAddres.setTipoViaPrincipal(direccionDet.getTipoViaPrincipal());
        } catch (Exception e) {
            LOGGER.error("Error pareDireccionDetToDireccionTab de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return splitAddres;
    }

    /**
     * valbuenayf Metodo para setear DrDireccion a CmtDireccionDetalladaMgl
     *
     * @param direccionTabulada
     * @return
     */
    public CmtDireccionDetalladaMgl parseDrDireccionToCmtDireccionDetalladaMgl(DrDireccion direccionTabulada) {
        CmtDireccionDetalladaMgl direccionDet = new CmtDireccionDetalladaMgl();
        try {
            if (direccionTabulada.getIdTipoDireccion() != null && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                direccionDet.setIdTipoDireccion(direccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (direccionTabulada.getTipoViaPrincipal() != null && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                direccionDet.setTipoViaPrincipal(direccionTabulada.getTipoViaPrincipal().toUpperCase());
            }

            if (direccionTabulada.getNumViaPrincipal() != null && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                direccionDet.setNumViaPrincipal(direccionTabulada.getNumViaPrincipal().toUpperCase());
            }

            if (direccionTabulada.getLtViaPrincipal() != null && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                direccionDet.setLtViaPrincipal(direccionTabulada.getLtViaPrincipal().toUpperCase());
            }

            if (direccionTabulada.getNlPostViaP() != null && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                direccionDet.setNlPostViaP(direccionTabulada.getNlPostViaP().toUpperCase());
            }

            if (direccionTabulada.getBisViaPrincipal() != null && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                direccionDet.setBisViaPrincipal(direccionTabulada.getBisViaPrincipal().toUpperCase());
            }

            if (direccionTabulada.getCuadViaPrincipal() != null && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                direccionDet.setCuadViaPrincipal(direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }

            if (direccionTabulada.getTipoViaGeneradora() != null && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                direccionDet.setTipoViaGeneradora(direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }

            if (direccionTabulada.getNumViaGeneradora() != null && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                direccionDet.setNumViaGeneradora(direccionTabulada.getNumViaGeneradora().toUpperCase());
            }

            if (direccionTabulada.getLtViaGeneradora() != null && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                direccionDet.setLtViaGeneradora(direccionTabulada.getLtViaGeneradora().toUpperCase());
            }

            if (direccionTabulada.getNlPostViaG() != null && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                direccionDet.setNlPostViaG(direccionTabulada.getNlPostViaG().toUpperCase());
            }

            if (direccionTabulada.getBisViaGeneradora() != null && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                direccionDet.setBisViaGeneradora(direccionTabulada.getBisViaGeneradora().toUpperCase());
            }

            if (direccionTabulada.getCuadViaGeneradora() != null && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                direccionDet.setCuadViaGeneradora(direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }

            if (direccionTabulada.getPlacaDireccion() != null && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                direccionDet.setPlacaDireccion(direccionTabulada.getPlacaDireccion().toUpperCase());
            }

            if (direccionTabulada.getMzTipoNivel1() != null && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                direccionDet.setMzTipoNivel1(direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                direccionDet.setMzTipoNivel2(direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                direccionDet.setMzTipoNivel3(direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                direccionDet.setMzTipoNivel4(direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                direccionDet.setMzTipoNivel5(direccionTabulada.getMzTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel6() != null && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                direccionDet.setMzTipoNivel6(direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                direccionDet.setMzValorNivel1(direccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                direccionDet.setMzValorNivel2(direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                direccionDet.setMzValorNivel3(direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                direccionDet.setMzValorNivel4(direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                direccionDet.setMzValorNivel5(direccionTabulada.getMzValorNivel5().toUpperCase());
            }
             if (direccionTabulada.getMzValorNivel6() != null && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                direccionDet.setMzValorNivel6(direccionTabulada.getMzValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getBarrio() != null && !direccionTabulada.getBarrio().trim().isEmpty()) {
                direccionDet.setBarrio(direccionTabulada.getBarrio().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                direccionDet.setCpTipoNivel1(direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                direccionDet.setCpTipoNivel2(direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                direccionDet.setCpTipoNivel3(direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                direccionDet.setCpTipoNivel4(direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                direccionDet.setCpTipoNivel5(direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                direccionDet.setCpTipoNivel6(direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                direccionDet.setCpValorNivel1(direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                direccionDet.setCpValorNivel2(direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                direccionDet.setCpValorNivel3(direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                direccionDet.setCpValorNivel4(direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                direccionDet.setCpValorNivel5(direccionTabulada.getCpValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel6() != null && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                direccionDet.setCpValorNivel6(direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getItTipoPlaca() != null && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                direccionDet.setItTipoPlaca(direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                direccionDet.setItValorPlaca(direccionTabulada.getItValorPlaca().toUpperCase());
            }

        } catch (Exception e) {
            LOGGER.error("Error parseDrDireccionToCmtDireccionDetalladaMgl de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return direccionDet;

    }

    /**
     * valbuenayf Metodo para setear DrDireccion a CmtDireccionDetalladaMgl
     *
     * @param direccionTabulada
     * @return
     */
    public CmtDireccionDetalladaMgl parseDrDireccionToCmtDireccionDetalladaMglSubDir(DrDireccion direccionTabulada) {
        CmtDireccionDetalladaMgl direccionDet = new CmtDireccionDetalladaMgl();
        try {

            if (direccionTabulada.getBarrio() != null && !direccionTabulada.getBarrio().trim().isEmpty()) {
                direccionDet.setBarrio(direccionTabulada.getBarrio().toUpperCase());
            }

            if (direccionTabulada.getIdTipoDireccion() != null && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                direccionDet.setIdTipoDireccion(direccionTabulada.getIdTipoDireccion().toUpperCase());
            }
            if (direccionTabulada.getTipoViaPrincipal() != null && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                direccionDet.setTipoViaPrincipal(direccionTabulada.getTipoViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNumViaPrincipal() != null && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                direccionDet.setNumViaPrincipal(direccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getLtViaPrincipal() != null && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                direccionDet.setLtViaPrincipal(direccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaP() != null && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
                direccionDet.setNlPostViaP(direccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (direccionTabulada.getBisViaPrincipal() != null && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                direccionDet.setBisViaPrincipal(direccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getCuadViaPrincipal() != null && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                direccionDet.setCuadViaPrincipal(direccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (direccionTabulada.getTipoViaGeneradora() != null && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                direccionDet.setTipoViaGeneradora(direccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNumViaGeneradora() != null && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                direccionDet.setNumViaGeneradora(direccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getLtViaGeneradora() != null && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                direccionDet.setLtViaGeneradora(direccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getNlPostViaG() != null && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
                direccionDet.setNlPostViaG(direccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (direccionTabulada.getBisViaGeneradora() != null && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                direccionDet.setBisViaGeneradora(direccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getCuadViaGeneradora() != null && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                direccionDet.setCuadViaGeneradora(direccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (direccionTabulada.getPlacaDireccion() != null && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                direccionDet.setPlacaDireccion(direccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel1() != null && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                direccionDet.setMzTipoNivel1(direccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel2() != null && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                direccionDet.setMzTipoNivel2(direccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel3() != null && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                direccionDet.setMzTipoNivel3(direccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel4() != null && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                direccionDet.setMzTipoNivel4(direccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzTipoNivel5() != null && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                direccionDet.setMzTipoNivel5(direccionTabulada.getMzTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel1() != null && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                direccionDet.setMzValorNivel1(direccionTabulada.getMzValorNivel1().toUpperCase());
            }
             if (direccionTabulada.getMzTipoNivel6() != null && !direccionTabulada.getMzTipoNivel6().trim().isEmpty()) {
                direccionDet.setMzTipoNivel6(direccionTabulada.getMzTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel6() != null && !direccionTabulada.getMzValorNivel6().trim().isEmpty()) {
                direccionDet.setMzValorNivel6(direccionTabulada.getMzValorNivel6().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel2() != null && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                direccionDet.setMzValorNivel2(direccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel3() != null && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                direccionDet.setMzValorNivel3(direccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel4() != null && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                direccionDet.setMzValorNivel4(direccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getMzValorNivel5() != null && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                direccionDet.setMzValorNivel5(direccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel1() != null && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                direccionDet.setCpTipoNivel1(direccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel2() != null && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                direccionDet.setCpTipoNivel2(direccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel3() != null && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                direccionDet.setCpTipoNivel3(direccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel4() != null && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                direccionDet.setCpTipoNivel4(direccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel5() != null && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                direccionDet.setCpTipoNivel5(direccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpTipoNivel6() != null && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                direccionDet.setCpTipoNivel6(direccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel1() != null && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                direccionDet.setCpValorNivel1(direccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel2() != null && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                direccionDet.setCpValorNivel2(direccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel3() != null && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                direccionDet.setCpValorNivel3(direccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel4() != null && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                direccionDet.setCpValorNivel4(direccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel5() != null && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                direccionDet.setCpValorNivel5(direccionTabulada.getCpValorNivel5().toUpperCase());
            }
            if (direccionTabulada.getCpValorNivel6() != null && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                direccionDet.setCpValorNivel6(direccionTabulada.getCpValorNivel6().toUpperCase());
            }

            if (direccionTabulada.getItTipoPlaca() != null && !direccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                direccionDet.setItTipoPlaca(direccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (direccionTabulada.getItValorPlaca() != null && !direccionTabulada.getItValorPlaca().trim().isEmpty()) {
                direccionDet.setItValorPlaca(direccionTabulada.getItValorPlaca().toUpperCase());
            }
        } catch (Exception e) {
            LOGGER.error("Error parseDrDireccionToCmtDireccionDetalladaMglSubDir de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return direccionDet;

    }

    /**
     * valbuenayf Metodo para setear CmtDireccionTabuladaDto a DrDireccion
     *
     * @param cmtDireccionTabulada
     * @return
     */
    public DrDireccion parseCmtDireccionTabuladaDtoToDrDireccion(CmtDireccionTabuladaDto cmtDireccionTabulada) {
        DrDireccion direccion1 = new DrDireccion();
        try {
            if (cmtDireccionTabulada.getIdTipoDireccion() != null && !cmtDireccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
                direccion1.setIdTipoDireccion(cmtDireccionTabulada.getIdTipoDireccion().toUpperCase());
            }

            if (cmtDireccionTabulada.getTipoViaPrincipal() != null && !cmtDireccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
                direccion1.setTipoViaPrincipal(cmtDireccionTabulada.getTipoViaPrincipal().toUpperCase());
            }
            if (cmtDireccionTabulada.getNumViaPrincipal() != null && !cmtDireccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
                direccion1.setNumViaPrincipal(cmtDireccionTabulada.getNumViaPrincipal().toUpperCase());
            }
            if (cmtDireccionTabulada.getLtViaPrincipal() != null && !cmtDireccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
                direccion1.setLtViaPrincipal(cmtDireccionTabulada.getLtViaPrincipal().toUpperCase());
            }
            if (cmtDireccionTabulada.getNlPostViaP() != null && !cmtDireccionTabulada.getNlPostViaP().trim().isEmpty()) {
                direccion1.setNlPostViaP(cmtDireccionTabulada.getNlPostViaP().toUpperCase());
            }
            if (cmtDireccionTabulada.getBisViaPrincipal() != null && !cmtDireccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
                direccion1.setBisViaPrincipal(cmtDireccionTabulada.getBisViaPrincipal().toUpperCase());
            }
            if (cmtDireccionTabulada.getCuadViaPrincipal() != null && !cmtDireccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
                direccion1.setCuadViaPrincipal(cmtDireccionTabulada.getCuadViaPrincipal().toUpperCase());
            }
            if (cmtDireccionTabulada.getTipoViaGeneradora() != null && !cmtDireccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
                direccion1.setTipoViaGeneradora(cmtDireccionTabulada.getTipoViaGeneradora().toUpperCase());
            }
            if (cmtDireccionTabulada.getNumViaGeneradora() != null && !cmtDireccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
                direccion1.setNumViaGeneradora(cmtDireccionTabulada.getNumViaGeneradora().toUpperCase());
            }
            if (cmtDireccionTabulada.getLtViaGeneradora() != null && !cmtDireccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
                direccion1.setLtViaGeneradora(cmtDireccionTabulada.getLtViaGeneradora().toUpperCase());
            }
            if (cmtDireccionTabulada.getNlPostViaG() != null && !cmtDireccionTabulada.getNlPostViaG().trim().isEmpty()) {
                direccion1.setNlPostViaG(cmtDireccionTabulada.getNlPostViaG().toUpperCase());
            }
            if (cmtDireccionTabulada.getBisViaGeneradora() != null && !cmtDireccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
                direccion1.setBisViaGeneradora(cmtDireccionTabulada.getBisViaGeneradora().toUpperCase());
            }
            if (cmtDireccionTabulada.getCuadViaGeneradora() != null && !cmtDireccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
                direccion1.setCuadViaGeneradora(cmtDireccionTabulada.getCuadViaGeneradora().toUpperCase());
            }
            if (cmtDireccionTabulada.getPlacaDireccion() != null && !cmtDireccionTabulada.getPlacaDireccion().trim().isEmpty()) {
                direccion1.setPlacaDireccion(cmtDireccionTabulada.getPlacaDireccion().toUpperCase());
            }
            if (cmtDireccionTabulada.getMzTipoNivel1() != null && !cmtDireccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
                direccion1.setMzTipoNivel1(cmtDireccionTabulada.getMzTipoNivel1().toUpperCase());
            }
            if (cmtDireccionTabulada.getMzTipoNivel2() != null && !cmtDireccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
                direccion1.setMzTipoNivel2(cmtDireccionTabulada.getMzTipoNivel2().toUpperCase());
            }
            if (cmtDireccionTabulada.getMzTipoNivel3() != null && !cmtDireccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
                direccion1.setMzTipoNivel3(cmtDireccionTabulada.getMzTipoNivel3().toUpperCase());
            }
            if (cmtDireccionTabulada.getMzTipoNivel4() != null && !cmtDireccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
                direccion1.setMzTipoNivel4(cmtDireccionTabulada.getMzTipoNivel4().toUpperCase());
            }
            if (cmtDireccionTabulada.getMzTipoNivel5() != null && !cmtDireccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
                direccion1.setMzTipoNivel5(cmtDireccionTabulada.getMzTipoNivel5().toUpperCase());
            }
            if (cmtDireccionTabulada.getMzValorNivel1() != null && !cmtDireccionTabulada.getMzValorNivel1().trim().isEmpty()) {
                direccion1.setMzValorNivel1(cmtDireccionTabulada.getMzValorNivel1().toUpperCase());
            }
            if (cmtDireccionTabulada.getMzValorNivel2() != null && !cmtDireccionTabulada.getMzValorNivel2().trim().isEmpty()) {
                direccion1.setMzValorNivel2(cmtDireccionTabulada.getMzValorNivel2().toUpperCase());
            }
            if (cmtDireccionTabulada.getMzValorNivel3() != null && !cmtDireccionTabulada.getMzValorNivel3().trim().isEmpty()) {
                direccion1.setMzValorNivel3(cmtDireccionTabulada.getMzValorNivel3().toUpperCase());
            }
            if (cmtDireccionTabulada.getMzValorNivel4() != null && !cmtDireccionTabulada.getMzValorNivel4().trim().isEmpty()) {
                direccion1.setMzValorNivel4(cmtDireccionTabulada.getMzValorNivel4().toUpperCase());
            }
            if (cmtDireccionTabulada.getMzValorNivel5() != null && !cmtDireccionTabulada.getMzValorNivel5().trim().isEmpty()) {
                direccion1.setMzValorNivel5(cmtDireccionTabulada.getMzValorNivel5().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpTipoNivel1() != null && !cmtDireccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
                direccion1.setCpTipoNivel1(cmtDireccionTabulada.getCpTipoNivel1().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpTipoNivel2() != null && !cmtDireccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
                direccion1.setCpTipoNivel2(cmtDireccionTabulada.getCpTipoNivel2().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpTipoNivel3() != null && !cmtDireccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
                direccion1.setCpTipoNivel3(cmtDireccionTabulada.getCpTipoNivel3().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpTipoNivel4() != null && !cmtDireccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
                direccion1.setCpTipoNivel4(cmtDireccionTabulada.getCpTipoNivel4().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpTipoNivel5() != null && !cmtDireccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
                direccion1.setCpTipoNivel5(cmtDireccionTabulada.getCpTipoNivel5().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpTipoNivel6() != null && !cmtDireccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
                direccion1.setCpTipoNivel6(cmtDireccionTabulada.getCpTipoNivel6().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpValorNivel1() != null && !cmtDireccionTabulada.getCpValorNivel1().trim().isEmpty()) {
                direccion1.setCpValorNivel1(cmtDireccionTabulada.getCpValorNivel1().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpValorNivel2() != null && !cmtDireccionTabulada.getCpValorNivel2().trim().isEmpty()) {
                direccion1.setCpValorNivel2(cmtDireccionTabulada.getCpValorNivel2().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpValorNivel3() != null && !cmtDireccionTabulada.getCpValorNivel3().trim().isEmpty()) {
                direccion1.setCpValorNivel3(cmtDireccionTabulada.getCpValorNivel3().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpValorNivel4() != null && !cmtDireccionTabulada.getCpValorNivel4().trim().isEmpty()) {
                direccion1.setCpValorNivel4(cmtDireccionTabulada.getCpValorNivel4().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpValorNivel5() != null && !cmtDireccionTabulada.getCpValorNivel5().trim().isEmpty()) {
                direccion1.setCpValorNivel5(cmtDireccionTabulada.getCpValorNivel5().toUpperCase());
            }
            if (cmtDireccionTabulada.getCpValorNivel6() != null && !cmtDireccionTabulada.getCpValorNivel6().trim().isEmpty()) {
                direccion1.setCpValorNivel6(cmtDireccionTabulada.getCpValorNivel6().toUpperCase());
            }
            if (cmtDireccionTabulada.getBarrio() != null && !cmtDireccionTabulada.getBarrio().trim().isEmpty()) {
                direccion1.setBarrio(cmtDireccionTabulada.getBarrio().toUpperCase());
            }
            if (cmtDireccionTabulada.getItTipoPlaca() != null && !cmtDireccionTabulada.getItTipoPlaca().trim().isEmpty()) {
                direccion1.setItTipoPlaca(cmtDireccionTabulada.getItTipoPlaca().toUpperCase());
            }

            if (cmtDireccionTabulada.getItValorPlaca() != null && !cmtDireccionTabulada.getItValorPlaca().trim().isEmpty()) {
                direccion1.setItValorPlaca(cmtDireccionTabulada.getItValorPlaca().toUpperCase());
            }

        } catch (Exception e) {
            LOGGER.error("Error parseCmtDireccionTabuladaDtoToDrDireccion de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return direccion1;
    }

    /**
     * valbuenayf Metodo para AddressEJBRemote
     *
     * @return
     */
    public AddressEJBRemote getAddressEJB() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * valbuenayf Metodo para pasar de date a string
     *
     * @param date
     * @return
     */
    public String dateToString(Date date) {
        String fecha = "";
        try {
            if (date != null) {
                DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                fecha = format.format(date);
            }
        } catch (Exception e) {
            LOGGER.error("Error dateToString de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return fecha;
    }

    /**
     * valbuenayf Metodo para setear DrDireccion a CmtDireccionDetalladaMgl
     *
     * @param direccionDetMgl
     * @return
     */
    public DrDireccion parseCmtDireccionDetalladaMglToDrDireccion(CmtDireccionDetalladaMgl direccionDetMgl) {
        DrDireccion drDireccion = new DrDireccion();
        try {

            if (direccionDetMgl.getBarrio() != null && !direccionDetMgl.getBarrio().trim().isEmpty()) {
                drDireccion.setBarrio(direccionDetMgl.getBarrio().toUpperCase());
            }

            if (direccionDetMgl.getIdDirCatastro() != null && !direccionDetMgl.getIdDirCatastro().isEmpty()) {
                drDireccion.setIdDirCatastro(direccionDetMgl.getIdDirCatastro());
            }

            if (direccionDetMgl.getIdTipoDireccion() != null && !direccionDetMgl.getIdTipoDireccion().trim().isEmpty()) {
                drDireccion.setIdTipoDireccion(direccionDetMgl.getIdTipoDireccion().toUpperCase());
            }

            if (direccionDetMgl.getTipoViaPrincipal() != null && !direccionDetMgl.getTipoViaPrincipal().trim().isEmpty()) {
                drDireccion.setTipoViaPrincipal(direccionDetMgl.getTipoViaPrincipal().toUpperCase());
            }

            if (direccionDetMgl.getNumViaPrincipal() != null && !direccionDetMgl.getNumViaPrincipal().trim().isEmpty()) {
                drDireccion.setNumViaPrincipal(direccionDetMgl.getNumViaPrincipal().toUpperCase());
            }

            if (direccionDetMgl.getLtViaPrincipal() != null && !direccionDetMgl.getLtViaPrincipal().trim().isEmpty()) {
                drDireccion.setLtViaPrincipal(direccionDetMgl.getLtViaPrincipal().toUpperCase());
            }

            if (direccionDetMgl.getNlPostViaP() != null && !direccionDetMgl.getNlPostViaP().trim().isEmpty()) {
                drDireccion.setNlPostViaP(direccionDetMgl.getNlPostViaP().toUpperCase());
            }

            if (direccionDetMgl.getBisViaPrincipal() != null && !direccionDetMgl.getBisViaPrincipal().trim().isEmpty()) {
                drDireccion.setBisViaPrincipal(direccionDetMgl.getBisViaPrincipal().toUpperCase());
            }

            if (direccionDetMgl.getCuadViaPrincipal() != null && !direccionDetMgl.getCuadViaPrincipal().trim().isEmpty()) {
                drDireccion.setCuadViaPrincipal(direccionDetMgl.getCuadViaPrincipal().toUpperCase());
            }

            if (direccionDetMgl.getTipoViaGeneradora() != null && !direccionDetMgl.getTipoViaGeneradora().trim().isEmpty()) {
                drDireccion.setTipoViaGeneradora(direccionDetMgl.getTipoViaGeneradora().toUpperCase());
            }

            if (direccionDetMgl.getNumViaGeneradora() != null && !direccionDetMgl.getNumViaGeneradora().trim().isEmpty()) {
                drDireccion.setNumViaGeneradora(direccionDetMgl.getNumViaGeneradora().toUpperCase());
            }

            if (direccionDetMgl.getLtViaGeneradora() != null && !direccionDetMgl.getLtViaGeneradora().trim().isEmpty()) {
                drDireccion.setLtViaGeneradora(direccionDetMgl.getLtViaGeneradora().toUpperCase());
            }

            if (direccionDetMgl.getNlPostViaG() != null && !direccionDetMgl.getNlPostViaG().trim().isEmpty()) {
                drDireccion.setNlPostViaG(direccionDetMgl.getNlPostViaG().toUpperCase());
            }

            if (direccionDetMgl.getBisViaGeneradora() != null && !direccionDetMgl.getBisViaGeneradora().trim().isEmpty()) {
                drDireccion.setBisViaGeneradora(direccionDetMgl.getBisViaGeneradora().toUpperCase());
            }

            if (direccionDetMgl.getCuadViaGeneradora() != null && !direccionDetMgl.getCuadViaGeneradora().trim().isEmpty()) {
                drDireccion.setCuadViaGeneradora(direccionDetMgl.getCuadViaGeneradora().toUpperCase());
            }

            if (direccionDetMgl.getPlacaDireccion() != null && !direccionDetMgl.getPlacaDireccion().trim().isEmpty()) {
                drDireccion.setPlacaDireccion(direccionDetMgl.getPlacaDireccion().toUpperCase());
            }

            if (direccionDetMgl.getMzTipoNivel1() != null && !direccionDetMgl.getMzTipoNivel1().trim().isEmpty()) {
                drDireccion.setMzTipoNivel1(direccionDetMgl.getMzTipoNivel1().toUpperCase());
            }
            if (direccionDetMgl.getMzTipoNivel2() != null && !direccionDetMgl.getMzTipoNivel2().trim().isEmpty()) {
                drDireccion.setMzTipoNivel2(direccionDetMgl.getMzTipoNivel2().toUpperCase());
            }
            if (direccionDetMgl.getMzTipoNivel3() != null && !direccionDetMgl.getMzTipoNivel3().trim().isEmpty()) {
                drDireccion.setMzTipoNivel3(direccionDetMgl.getMzTipoNivel3().toUpperCase());
            }
            if (direccionDetMgl.getMzTipoNivel4() != null && !direccionDetMgl.getMzTipoNivel4().trim().isEmpty()) {
                drDireccion.setMzTipoNivel4(direccionDetMgl.getMzTipoNivel4().toUpperCase());
            }
            if (direccionDetMgl.getMzTipoNivel5() != null && !direccionDetMgl.getMzTipoNivel5().trim().isEmpty()) {
                drDireccion.setMzTipoNivel5(direccionDetMgl.getMzTipoNivel5().toUpperCase());
            }
            
            if (direccionDetMgl.getMzTipoNivel6() != null && !direccionDetMgl.getMzTipoNivel6().trim().isEmpty()) {
                drDireccion.setMzTipoNivel6(direccionDetMgl.getMzTipoNivel6().toUpperCase());
            }
            
            if (direccionDetMgl.getMzValorNivel1() != null && !direccionDetMgl.getMzValorNivel1().trim().isEmpty()) {
                drDireccion.setMzValorNivel1(direccionDetMgl.getMzValorNivel1().toUpperCase());
            }
            if (direccionDetMgl.getMzValorNivel2() != null && !direccionDetMgl.getMzValorNivel2().trim().isEmpty()) {
                drDireccion.setMzValorNivel2(direccionDetMgl.getMzValorNivel2().toUpperCase());
            }
            if (direccionDetMgl.getMzValorNivel3() != null && !direccionDetMgl.getMzValorNivel3().trim().isEmpty()) {
                drDireccion.setMzValorNivel3(direccionDetMgl.getMzValorNivel3().toUpperCase());
            }
            if (direccionDetMgl.getMzValorNivel4() != null && !direccionDetMgl.getMzValorNivel4().trim().isEmpty()) {
                drDireccion.setMzValorNivel4(direccionDetMgl.getMzValorNivel4().toUpperCase());
            }
            if (direccionDetMgl.getMzValorNivel5() != null && !direccionDetMgl.getMzValorNivel5().trim().isEmpty()) {
                drDireccion.setMzValorNivel5(direccionDetMgl.getMzValorNivel5().toUpperCase());
            }
            if (direccionDetMgl.getMzValorNivel6() != null && !direccionDetMgl.getMzValorNivel6().trim().isEmpty()) {
                drDireccion.setMzValorNivel6(direccionDetMgl.getMzValorNivel6().toUpperCase());
            }

            if (direccionDetMgl.getCpTipoNivel1() != null && !direccionDetMgl.getCpTipoNivel1().trim().isEmpty()) {
                drDireccion.setCpTipoNivel1(direccionDetMgl.getCpTipoNivel1().toUpperCase());
            }
            if (direccionDetMgl.getCpTipoNivel2() != null && !direccionDetMgl.getCpTipoNivel2().trim().isEmpty()) {
                drDireccion.setCpTipoNivel2(direccionDetMgl.getCpTipoNivel2().toUpperCase());
            }
            if (direccionDetMgl.getCpTipoNivel3() != null && !direccionDetMgl.getCpTipoNivel3().trim().isEmpty()) {
                drDireccion.setCpTipoNivel3(direccionDetMgl.getCpTipoNivel3().toUpperCase());
            }
            if (direccionDetMgl.getCpTipoNivel4() != null && !direccionDetMgl.getCpTipoNivel4().trim().isEmpty()) {
                drDireccion.setCpTipoNivel4(direccionDetMgl.getCpTipoNivel4().toUpperCase());
            }
            if (direccionDetMgl.getCpTipoNivel5() != null && !direccionDetMgl.getCpTipoNivel5().trim().isEmpty()) {
                drDireccion.setCpTipoNivel5(direccionDetMgl.getCpTipoNivel5().toUpperCase());
            }
            if (direccionDetMgl.getCpTipoNivel6() != null && !direccionDetMgl.getCpTipoNivel6().trim().isEmpty()) {
                drDireccion.setCpTipoNivel6(direccionDetMgl.getCpTipoNivel6().toUpperCase());
            }

            if (direccionDetMgl.getCpValorNivel1() != null && !direccionDetMgl.getCpValorNivel1().trim().isEmpty()) {
                drDireccion.setCpValorNivel1(direccionDetMgl.getCpValorNivel1().toUpperCase());
            }
            if (direccionDetMgl.getCpValorNivel2() != null && !direccionDetMgl.getCpValorNivel2().trim().isEmpty()) {
                drDireccion.setCpValorNivel2(direccionDetMgl.getCpValorNivel2().toUpperCase());
            }
            if (direccionDetMgl.getCpValorNivel3() != null && !direccionDetMgl.getCpValorNivel3().trim().isEmpty()) {
                drDireccion.setCpValorNivel3(direccionDetMgl.getCpValorNivel3().toUpperCase());
            }
            if (direccionDetMgl.getCpValorNivel4() != null && !direccionDetMgl.getCpValorNivel4().trim().isEmpty()) {
                drDireccion.setCpValorNivel4(direccionDetMgl.getCpValorNivel4().toUpperCase());
            }
            if (direccionDetMgl.getCpValorNivel5() != null && !direccionDetMgl.getCpValorNivel5().trim().isEmpty()) {
                drDireccion.setCpValorNivel5(direccionDetMgl.getCpValorNivel5().toUpperCase());
            }
            if (direccionDetMgl.getCpValorNivel6() != null && !direccionDetMgl.getCpValorNivel6().trim().isEmpty()) {
                drDireccion.setCpValorNivel6(direccionDetMgl.getCpValorNivel6().toUpperCase());
            }

            if (direccionDetMgl.getItTipoPlaca() != null && !direccionDetMgl.getItTipoPlaca().trim().isEmpty()) {
                drDireccion.setItTipoPlaca(direccionDetMgl.getItTipoPlaca().toUpperCase());
            }

            if (direccionDetMgl.getItValorPlaca() != null && !direccionDetMgl.getItValorPlaca().trim().isEmpty()) {
                drDireccion.setItValorPlaca(direccionDetMgl.getItValorPlaca().toUpperCase());
            }

        } catch (Exception e) {
            LOGGER.error("Error parseCmtDireccionDetalladaMglToDrDireccion de CmtdrDireccionalleMglManager " + e.getMessage());
        }
        return drDireccion;
    }

    /**
     * valbuenayf Metodo para validar la direccion para crear una solicitud
     *
     * @param direccionTabulada
     * @return
     */
    public boolean validarDireccionTipoDir(DrDireccion direccionTabulada) {
        DireccionesValidacionManager direccionesValidacion;
        try {
            direccionesValidacion = new DireccionesValidacionManager();
            if (direccionTabulada.getCpTipoNivel5() == null || direccionTabulada.getCpTipoNivel5().equals("")
                    || direccionTabulada.getCpTipoNivel5().isEmpty()) {
                direccionTabulada.setCpTipoNivel5(TIPO_VIVIENDA);
            }
            return direccionesValidacion.validarEstructuraDireccion(direccionTabulada, Constant.TIPO_VALIDACION_DIR_HHPP);
        } catch (ApplicationException e) {
            LOGGER.error("Error validarDireccionTipoDir de CmtdrDireccionalleMglManager " + e.getMessage());
        }
        return false;
    }

    /**
     * valbuenayf Metodo para validar la direccion
     *
     * @param direccionTabulada
     * @return
     */
    public boolean validarCreacionDireccion(DrDireccion direccionTabulada) {
        try {
            if (direccionTabulada.getIdTipoDireccion().equals("CK")) {
                return validarDireccionCalleCarrera(direccionTabulada);
            } else {
                return validarDireccionTipoDir(direccionTabulada);
            }
        } catch (Exception e) {
            LOGGER.error("Error validarCreacionDireccion de CmtdrDireccionalleMglManager " + e.getMessage());
            return false;
        }
    }

    /**
     * valbuenayf Metodo para validar si la direccion de tipo (CK) calle -
     * carrera cumple con los campos minimos
     *
     * @param direccionTabulada
     * @return
     */
    public boolean validarDireccionCalleCarrera(DrDireccion direccionTabulada) {
        try {
            if (!validarTexto(direccionTabulada.getTipoViaPrincipal())) {
                return false;
            }
            if (!validarTexto(direccionTabulada.getNumViaPrincipal())) {
                return false;
            }
            if (!validarTexto(direccionTabulada.getNumViaGeneradora())) {
                return false;
            }
            if (!validarTexto(direccionTabulada.getPlacaDireccion())) {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error validarDireccionCalleCarrera de CmtDireccionDetalleMglManager" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * valbuenayf Metodo para validar campos de texto
     *
     * @param texto
     * @return
     */
    public boolean validarTexto(String texto) {
        boolean respuesta = false;
        try {
            if (texto != null && !texto.trim().isEmpty()) {
                respuesta = true;
            }
        } catch (Exception e) {
            LOGGER.error("Error validarTexto de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return respuesta;
    }

    /**
     * valbuenayf Metodo para el mensaje de respuesta de la cracion de la
     * solicitud
     *
     * @param direccionTabulada
     * @param mensaje
     * @param ciudadGpo
     * @param cmtDireccionRequestDto
     * @throws ExceptionDB
     * @throws CloneNotSupportedException
     */
    public String respuestaSolicitud(DrDireccion direccionTabulada, String mensaje,
            GeograficoPoliticoMgl ciudadGpo, CmtDireccionRequestDto cmtDireccionRequestDto) throws ExceptionDB, CloneNotSupportedException {
        String result;
        CmtRequestCrearSolicitudInspira requestCrearSolicitud;
        String estrato = null;

        try {
            if (direccion.getDirEstrato() == null) {
                if (direccion.getDirNivelSocioecono() != null) {
                    estrato = direccion.getDirNivelSocioecono().toString();
                } else {
                    if (cmtDireccionRequestDto.getEstrato() != null && !cmtDireccionRequestDto.getEstrato().isEmpty()) {
                        if (!cmtDireccionRequestDto.getEstrato().contains("-")
                                && cmtDireccionRequestDto.getEstrato().matches(VALIDAR_ESTRATO)) {
                            estrato = cmtDireccionRequestDto.getEstrato();
                        } else {
                            result = mensaje.concat(", ").concat(Constant.MSG_DIR_ESTRATO_ERROR);
                            return result;
                        }
                    }
                }
            } else {
                estrato = direccion.getDirEstrato().toString();
            }

            if (validarDireccionTipoDir(direccionTabulada)) {
                String tiempoDuracionSolicitud = TIEMPO_DURACION_SOLICITUD;
                String nodoCercano = null;
                String respuestaGestion = RESPUESTA_GESTION;
                String respuesta = RESPUESTA;
                String idCasoTcrm = ID_CASOT_CRM;

                requestCrearSolicitud = getCmtRequestCrearSolicitudInspira(ciudadGpo, direccionTabulada, cmtDireccionRequestDto, estrato);

                if (crearGestionarSolicitud(requestCrearSolicitud, TIPO_TECNOLOGIA, tiempoDuracionSolicitud, cmtDireccionRequestDto.getCodigoDane(),
                        cmtDireccionRequestDto.getUser(), cmtDireccionRequestDto.getNodoGestion(), nodoCercano, respuestaGestion,
                        respuesta, idCasoTcrm)) {
                    result = mensaje.concat(", ").concat(Constant.MSG_DIR_SOLICITUD_OK);
                } else {
                    result = mensaje.concat(", ").concat(Constant.MSG_DIR_SOLICITUD_ERROR);
                }
            } else {
                result = mensaje.concat(", ").concat(Constant.MSG_DIR_SOLICITUD_ERROR);
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error respuestaSolicitud de CmtDireccionDetalleMglManager " + e.getMessage());
            result = mensaje.concat(", ").concat(e.getMessage());
            return result;
        }
        return result;
    }

    /**
     * valbuenayf Metodo para crear la solicitud de la direccion
     *
     * @param request
     * @param tipoTecnologia
     * @param tiempoDuracionSolicitud
     * @param codigoDane
     * @param usuarioVt
     * @param nodoGestion
     * @param nodoCercano
     * @param respuestaGestion
     * @param respuesta
     * @param idCasoTcrm
     * @return
     * @throws ApplicationException
     */
    public boolean crearGestionarSolicitud(CmtRequestCrearSolicitudInspira request, String tipoTecnologia, String tiempoDuracionSolicitud,
            String codigoDane, String usuarioVt, String nodoGestion, String nodoCercano, String respuestaGestion,
            String respuesta, String idCasoTcrm) throws ApplicationException, ExceptionDB, CloneNotSupportedException {
        SolicitudManager solicitudManager;
        try {
            solicitudManager = new SolicitudManager();
            solicitudManager.crearGestionarSolicitudinspira(request, tipoTecnologia, tiempoDuracionSolicitud, codigoDane,
                    usuarioVt, nodoGestion, nodoCercano, respuestaGestion, respuesta, idCasoTcrm);
        } catch (ApplicationException e) {
            LOGGER.error("Error crearGestionarSolicitud de CmtDireccionDetalleMglManager " + e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    /**
     * valbuenayf Metodo para setear los valores de
     * CmtRequestCrearSolicitudInspira
     *
     * @param ciudadGpo
     * @param direccionTabulada
     * @param cmtDireccionRequestDto
     * @param estrato
     * @return
     * @throws ApplicationException
     */
    public CmtRequestCrearSolicitudInspira getCmtRequestCrearSolicitudInspira(GeograficoPoliticoMgl ciudadGpo, DrDireccion direccionTabulada,
            CmtDireccionRequestDto cmtDireccionRequestDto, String estrato) throws ApplicationException {
        CmtRequestCrearSolicitudInspira requestCrearSolicitud = new CmtRequestCrearSolicitudInspira();
        try {
            requestCrearSolicitud.setIdUsuario(cmtDireccionRequestDto.getUser());
            requestCrearSolicitud.setObservaciones(OBSERVACIONES);
            requestCrearSolicitud.setContacto(CONTACTO);
            requestCrearSolicitud.setTelefonoContacto(TELEFONO_CONTACTO);
            requestCrearSolicitud.setCanalVentas(CANAL_VENTAS);
            //Direccion
            requestCrearSolicitud.setDrDireccion(direccionTabulada);
            //City
            requestCrearSolicitud.setCmtCityEntityDto(getCmtCityEntityDto(ciudadGpo, direccionTabulada, cmtDireccionRequestDto, estrato));

        } catch (Exception e) {
            LOGGER.error("Error getCmtRequestCrearSolicitudInspira de CmtDireccionDetalleMglManager " + e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
        return requestCrearSolicitud;
    }

    /**
     * valbuenayf Metodo para setear los valores de CmtCityEntityDto
     *
     * @param ciudadGpo
     * @param direccionTabulada
     * @param cmtDireccionRequestDto
     * @param estrato
     * @return
     */
    public CmtCityEntityDto getCmtCityEntityDto(GeograficoPoliticoMgl ciudadGpo, DrDireccion direccionTabulada,
            CmtDireccionRequestDto cmtDireccionRequestDto, String estrato) {
        CmtCityEntityDto city = new CmtCityEntityDto();
        try {
            GeograficoPoliticoMgl departamentoGpo = buscarDepartamento(ciudadGpo.getGeoGpoId());
            GeograficoPoliticoMgl paisGpo = buscarPais(departamentoGpo.getGeoGpoId());
            city.setAddress(direccion.getDirFormatoIgac());
            city.setBarrio(direccionTabulada.getBarrio());
            city.setCityName(ciudadGpo.getGpoNombre());
            city.setCodCity(ciudadGpo.getGpoCodigo());
            city.setCodDane(ciudadGpo.getGeoCodigoDane());
            if (direccion.getDirConfiabilidad() != null) {
                city.setConfiabilidadDir(direccion.getDirConfiabilidad().toString());
            } else {
                city.setConfiabilidadDir("0");
            }
            city.setDireccion(direccion.getDirFormatoIgac());
            city.setDpto(departamentoGpo.getGpoNombre());
            city.setEstratoDir(estrato);
            city.setIdUsuario(cmtDireccionRequestDto.getUser());
            city.setPais(paisGpo.getGpoNombre());
        } catch (Exception e) {
            LOGGER.error("Error getCmtCityEntityDto de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return city;
    }

        /**
         * Obtiene Hhpp por dirección
         *
         * @param direccion la dirección construida que deseamos buscarle hhpp
         * asociados
         * @param centroPobladoId ciudad por la que se desea filtrar
         * @param evaluarEstadoRegistro indica si se debe validar el estado del
         * registro en la busqueda
         * @param paginaSelected
         * @param maxResults
         * @param busquedaPaginada indica si la busqueda es total o paginada
         * @return listado de hhpp asociados a la dirección buscada
         * @author Juan David Hernandez
         * @throws ApplicationException
         */
    public List<HhppMgl> findHhppByDireccion(DrDireccion direccion,
            BigDecimal centroPobladoId, boolean evaluarEstadoRegistro, int paginaSelected,
            int maxResults, boolean busquedaPaginada) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();

        List<CmtDireccionDetalladaMgl> direccionDetalladaList;

        //calculo de paginacion en caso de que la busqueda sea paginada
        int firstResult = 0;
        if (paginaSelected > 1 && busquedaPaginada) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
                
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(centroPobladoId);
        
        if (centroPobladoCompleto != null && centroPobladoCompleto.getGpoMultiorigen() != null
                && !centroPobladoCompleto.getGpoMultiorigen().isEmpty() && centroPobladoCompleto.getGpoMultiorigen().equalsIgnoreCase("1")) {
            direccion.setMultiOrigen("1");
        } else {
            direccion.setMultiOrigen("0");
        }

        //Buscamos la direccion tabulada en direccion_detallada
        direccionDetalladaList = cmtDireccionDetalleMglDao.buscarDireccionTabuladaMer(centroPobladoId,
                direccion, busquedaPaginada, firstResult, maxResults);        
        
        
        List<CmtDireccionDetalladaMgl> direccionDetalladaTextoList = new ArrayList();
        ///Busqueda por texto para direcciones con nombres en la via principal      
        if (direccion != null && direccion.getDireccionRespuestaGeo() != null
                && !direccion.getDireccionRespuestaGeo().isEmpty()) {

            direccionDetalladaTextoList = busquedaDireccionTextoRespuestaGeo(direccion.getDireccionRespuestaGeo(),
                    direccion, centroPobladoId);
        }

        if ((direccionDetalladaList != null && !direccionDetalladaList.isEmpty())
                || (direccionDetalladaTextoList != null && !direccionDetalladaTextoList.isEmpty())) {
            direccionDetalladaList = combinarDireccionDetalladaList(direccionDetalladaList, direccionDetalladaTextoList);
        }


        List<HhppMgl> hhppList = new ArrayList();

        //las direcciones detalladas encontradas extraemos la direccion y subdireccion
        if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
            HhppMglManager hhppMglManager = new HhppMglManager();

            for (CmtDireccionDetalladaMgl dirDetallada : direccionDetalladaList) {
                //Obtenemos los Hhpp de la Subdireccion  
                if (dirDetallada.getSubDireccion() != null
                        && dirDetallada.getSubDireccion().getSdiId() != null) {

                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                            .findHhppSubDireccion(dirDetallada.getSubDireccion().getSdiId());

                    if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                        for (HhppMgl hhppSubMgl : hhhpSubDirList) {
                            if (evaluarEstadoRegistro) {
                                if (!hhppList.contains(hhppSubMgl) && hhppSubMgl.getEstadoRegistro() == 1) {
                                    hhppSubMgl.setDireccionDetalladaHhpp(dirDetallada.getDireccionTexto());
                                    hhppSubMgl.setBarrioHhpp(dirDetallada.getBarrio() != null
                                            ? dirDetallada.getBarrio() : null);
                                    hhppList.add(hhppSubMgl);
                                }
                            } else {
                                if (!hhppList.contains(hhppSubMgl)) {
                                    hhppSubMgl.setBarrioHhpp(dirDetallada.getBarrio() != null
                                            ? dirDetallada.getBarrio() : null);
                                    hhppSubMgl.setDireccionDetalladaHhpp(dirDetallada.getDireccionTexto());
                                    hhppList.add(hhppSubMgl);
                                }
                            }
                        }
                    }

                } else {
                    //Obtenemos los Hhpp de la Direccion principal    
                    if (dirDetallada.getDireccion() != null
                            && dirDetallada.getDireccion().getDirId() != null) {

                        List<HhppMgl> hhhpDirList
                                = hhppMglManager
                                        .findHhppDireccion(dirDetallada.getDireccion().getDirId());

                        if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                            for (HhppMgl hhppMgl : hhhpDirList) {
                                if (evaluarEstadoRegistro) {
                                    if (!hhppList.contains(hhppMgl) && hhppMgl.getEstadoRegistro() == 1) {
                                        hhppMgl.setDireccionDetalladaHhpp(dirDetallada.getDireccionTexto());
                                        hhppMgl.setBarrioHhpp(dirDetallada.getBarrio() != null
                                                ? dirDetallada.getBarrio() : null);
                                        hhppList.add(hhppMgl);
                                    }
                                } else {
                                    if (!hhppList.contains(hhppMgl)) {
                                        hhppMgl.setBarrioHhpp(dirDetallada.getBarrio() != null
                                                ? dirDetallada.getBarrio() : null);
                                        hhppMgl.setDireccionDetalladaHhpp(dirDetallada.getDireccionTexto());
                                        hhppList.add(hhppMgl);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        return hhppList;
    }

    /**
     * Obtiene Hhpp por dirección
     *
     * @param direccion la dirección construida que deseamos buscarle hhpp
     * asociados
     * @param gpo ciudad por la que se desea filtrar
     * @param evaluarEstadoRegistro indica si se debe validar el estado del
     * registro en la busqueda
     * @param paginaSelected
     * @param maxResults
     * @param busquedaPaginada true (total) indica si la busqueda es total o paginada
     * @return listado de hhpp asociados a la dirección buscada
     * @author Juan David Hernandez
     * @throws ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> findDireccionByDireccionDetallada(DrDireccion direccion,
            BigDecimal gpo, boolean evaluarEstadoRegistro, int paginaSelected,
            int maxResults, boolean busquedaPaginada) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();

        //calculo de paginacion en caso de que la busqueda sea paginada
        int firstResult = 0;
        if (paginaSelected > 1 && busquedaPaginada) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(gpo);
        
        if (centroPobladoCompleto != null && centroPobladoCompleto.getGpoMultiorigen() != null
                && !centroPobladoCompleto.getGpoMultiorigen().isEmpty() && centroPobladoCompleto.getGpoMultiorigen().equalsIgnoreCase("1")) {
            direccion.setMultiOrigen("1");
        } else {
            direccion.setMultiOrigen("0");
        }

        //Buscamos la direccion tabulada en direccion_detallada
        return cmtDireccionDetalleMglDao.buscarDireccionTabuladaMer(gpo, direccion, busquedaPaginada, firstResult, maxResults);
    }
    
    
         /**
     * Obtiene listado el conteo de direccion detalladas por dirección
     * actualemente se utuliza en la pantalla de modelo de hhpp
     * @param direccion la dirección construida
     * @param gpo ciudad por la que se desea filtrar
     * 
     *
     * @author Juan David Hernandez
     * @return 
     * @throws ApplicationException
     */  
    public int countDireccionByDireccionDetallada(DrDireccion direccion,
            BigDecimal gpo) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();

        return cmtDireccionDetalleMglDao.countDireccionTabulada(gpo,
                direccion);
    }
    
    /**
     * Obtiene Hhpp por dirección
     *
     * @param direccion la dirección construida que deseamos buscarle hhpp
     * asociados
     * @param gpo ciudad por la que se desea filtrar
     * @return listado de hhpp asociados a la dirección buscada
     *
     * @author Juan David Hernandez
     * @throws ApplicationException
     */
    public int countHhppByDireccion(DrDireccion direccion,
            BigDecimal gpo) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();

        return cmtDireccionDetalleMglDao.countBuscarDireccionTabulada(gpo, direccion);
    }

    /**
     * Obtiene Hhpp por dirección
     *
     * @param direccion la dirección construida que deseamos buscarle hhpp
     * asociados
     * @param evaluarEstadoRegistro
     *
     * @return listado de hhpp asociados a la dirección buscada
     *
     * @author Juan David Hernandez
     * @throws ApplicationException
     */
    public List<HhppMgl> findHhppByDireccionNodoCercano(DrDireccion direccion,
            BigDecimal centroPoblado, boolean evaluarEstadoRegistro) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();

        Integer rowNum = registroMaximo(ROW_NUMBER);
        
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(centroPoblado);
        
        if (centroPobladoCompleto != null && centroPobladoCompleto.getGpoMultiorigen() != null
                && centroPobladoCompleto.getGpoMultiorigen().equalsIgnoreCase("1")) {
            direccion.setMultiOrigen("1");
        } else {
            direccion.setMultiOrigen("0");
        }
        
        //Buscamos la direccion tabulada en direccion_detallada
        List<CmtDireccionDetalladaMgl> direccionDetalladaList
                = cmtDireccionDetalleMglDao.buscarDireccionTabuladaNodoCercano(centroPoblado, direccion);
        List<HhppMgl> hhppList = new ArrayList();

        //las direcciones detalladas encontradas extraemos la direccion y subdireccion
        if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
            HhppMglManager hhppMglManager = new HhppMglManager();

            for (CmtDireccionDetalladaMgl dirDetallada : direccionDetalladaList) {
                //Obtenemos los Hhpp de la Subdireccion  
                if (dirDetallada.getSubDireccion() != null
                        && dirDetallada.getSubDireccion().getSdiId() != null) {

                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                            .findHhppSubDireccion(dirDetallada.getSubDireccion().getSdiId());

                    if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                        for (HhppMgl hhppSubMgl : hhhpSubDirList) {
                            if (evaluarEstadoRegistro) {
                                if (!hhppList.contains(hhppSubMgl) && hhppSubMgl.getEstadoRegistro() == 1) {
                                    hhppList.add(hhppSubMgl);
                                }
                            } else {
                                if (!hhppList.contains(hhppSubMgl)) {
                                    hhppList.add(hhppSubMgl);
                                }
                            }
                        }
                    }

                } else {
                    //Obtenemos los Hhpp de la Direccion principal    
                    if (dirDetallada.getDireccion() != null
                            && dirDetallada.getDireccion().getDirId() != null) {

                        List<HhppMgl> hhhpDirList
                                = hhppMglManager
                                        .findHhppDireccion(dirDetallada.getDireccion().getDirId());

                        if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                            for (HhppMgl hhppMgl : hhhpDirList) {
                                if (evaluarEstadoRegistro) {
                                    if (!hhppList.contains(hhppMgl) && hhppMgl.getEstadoRegistro() == 1) {
                                        hhppList.add(hhppMgl);
                                    }
                                } else {
                                    if (!hhppList.contains(hhppMgl)) {
                                        hhppList.add(hhppMgl);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return hhppList;
    }

    public boolean numeroPar(int numero) {
        try {
            if (numero % 2 == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error en numeroPar. " +e.getMessage(), e);  
        }
        return false;
    }


    /**
     * Villamilc Metodo para buscar la ciudad por un id centro poblado * (o)
     * idciudad
     *
     * @param idCiuCenPob
     * @return GeograficoPoliticoMgl
     */
    public GeograficoPoliticoMgl buscarCity(BigDecimal idCiuCenPob) {
        GeograficoPoliticoManager geograficoPoliticoManager;
        GeograficoPoliticoMgl ciudadGpo = null;
        try {
            geograficoPoliticoManager = new GeograficoPoliticoManager();
            ciudadGpo = geograficoPoliticoManager.findDepartamentoIdCiudadCenPob(idCiuCenPob);
        } catch (ApplicationException e) {
            LOGGER.error("Error buscarDepartamento de CmtDireccionDetalleMglManager " + e.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Error buscarDepartamento de CmtDireccionDetalleMglManager " + ex.getMessage());
        }

        return ciudadGpo;
    }

    /**
     * Autor: Victor Bocanegra
     *
     * @param cmtDireccionSolicitudMgl
     * @return CmtDireccionMgl Para sacar unidades previas.
     * @throws ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> retornaDireccionDetUnidadesPrevias(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl)
            throws ApplicationException {

        List<CmtDireccionDetalladaMgl> lstCmtDireccionDetalladaMgl;

        if (cmtDireccionSolicitudMgl.getCodTipoDir().equalsIgnoreCase(Constant.ADDRESS_TIPO_CK)) {
            lstCmtDireccionDetalladaMgl = cmtDireccionDetalleMglDaoImpl.findDirDetalladaCK(cmtDireccionSolicitudMgl);
        } else if (cmtDireccionSolicitudMgl.getCodTipoDir().equalsIgnoreCase(Constant.ADDRESS_TIPO_BM)) {
            lstCmtDireccionDetalladaMgl = cmtDireccionDetalleMglDaoImpl.findDirDetalladaBM(cmtDireccionSolicitudMgl);
        } else {
            lstCmtDireccionDetalladaMgl = cmtDireccionDetalleMglDaoImpl.findDirDetalladaIT(cmtDireccionSolicitudMgl);
        }

        return lstCmtDireccionDetalladaMgl;

    }

    /**
     * valbuenayf Metodo para buscar una direcciondetallada por el id
     *
     * @param direccionDetalladaId
     * @return
     */
    public CmtDireccionDetalladaMgl buscarDireccionIdDireccion(BigDecimal direccionDetalladaId) {
        CmtDireccionDetalladaMgl result = null;
        try {
            CmtDireccionDetalleMglDaoImpl direccionDetalleMglDaoImpl = new CmtDireccionDetalleMglDaoImpl();
            result = direccionDetalleMglDaoImpl.buscarDireccionIdDireccion(direccionDetalladaId);

        } catch (Exception e) {
            LOGGER.error("Error CmtDireccionDetalladaMgl de CmtDireccionDetalleMglManager " + e.getMessage());
        }
        return result;
    }

    /**
     * Buscar DrDireccion según el parámetro. Se busca la DrDireccion del Hhpp.
     *
     * @author becerraarmr
     * @param hhppMgl hhpp para realizar la busqueda.
     * @return null si no enonctró o una DrDireccion válida
     * @throws ApplicationException is hay algún
     * inconveniente al buscar en la base de datos o si la respuesta es nula.
     */
    public DrDireccion find(HhppMgl hhppMgl) throws ApplicationException {
        if (hhppMgl == null || hhppMgl.getDireccionObj() == null) {
            return null;
        }

        CmtDireccionDetalleMglDaoImpl dao = new CmtDireccionDetalleMglDaoImpl();

        CmtDireccionDetalladaMgl dirDetallada = dao.find(hhppMgl);

        if (dirDetallada == null) {
            throw new ApplicationException("No se encontró dirección del Hhpp "
                    + hhppMgl.getHhpId());
        }

        return parseCmtDireccionDetalladaMglToDrDireccion(dirDetallada);
    }

    /**
     * Buscar CmtDireccionDetalladaMgl Busca una dirección detallada según la
     * DrDireccion y el id de la ciudad
     *
     * @author becerraarmr
     * @param drDireccion DrDirección
     * @return un objeto CmtDireccionDetalladaMgl o null
     */
    public CmtDireccionDetalladaMgl find(DrDireccion drDireccion, BigDecimal centroPoblado) throws ApplicationException {
        if (drDireccion == null || centroPoblado == null) {
            return null;
        }
        CmtDireccionDetalleMglDaoImpl dao = new CmtDireccionDetalleMglDaoImpl();
                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(centroPoblado);
        
        if (centroPobladoCompleto != null && centroPobladoCompleto.getGpoMultiorigen() != null
                && !centroPobladoCompleto.getGpoMultiorigen().isEmpty() && centroPobladoCompleto.getGpoMultiorigen().equalsIgnoreCase("1")) {
            drDireccion.setMultiOrigen("1");
        } else {
            drDireccion.setMultiOrigen("0");
        }
        List<CmtDireccionDetalladaMgl> list = dao.buscarDireccionTabuladaMer(centroPoblado, drDireccion, false, 1, 1);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Crear CmtDireccionDetalladaMgl.
     *
     * Para crear una direccion detallada (CmtDireccionDetalladaMgl) debes tener
     * en cuenta la DrDirección nueva (dirNueva), la ciudad donde se va a crear.
     *
     * @author becerraarmr
     * @param dirNueva DrDireccion para crear dirección detallada
     * @param ciudadGpo ciudad donde se va a crear la dirección.
     * @throws ApplicationException Error al momento de
     * crear un nuevo CmtDireccionDetalladaMgl
     */
    public void create(
            DrDireccion dirNueva,
            GeograficoPoliticoMgl ciudadGpo) throws ApplicationException {

        //Se prepara a crear la dirección detallada.
        if (!crearDireccionDetalle(dirNueva, ciudadGpo.getGeoCodigoDane(), ciudadGpo).isValidacionExitosa()) {
            throw new ApplicationException("Se presentó un inconveniente al momento "
                    + "de crear la dirección detallada");
        }
    }

    /**
     * Obtiene una dirección detallada por id de dirección y id de la
     * subdireccion
     *
     * @param dirId id de la dirección
     * @param sdirId id de la subdireccion
     *
     * @return dirección detalla encontrada
     *
     * @author Juan David Hernandez
     * @throws ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> findDireccionDetallaByDirIdSdirId(BigDecimal dirId,
            BigDecimal sdirId) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();

        List<CmtDireccionDetalladaMgl> detalladaMgls = cmtDireccionDetalleMglDao
                .findDireccionDetallaByDirIdSdirId(dirId, sdirId);

        List<CmtDireccionDetalladaMgl> retorno = null;
        if (detalladaMgls != null && !detalladaMgls.isEmpty()) {
            HhppMglManager hhppMglManager = new HhppMglManager();
            retorno = new ArrayList<>();

            for (CmtDireccionDetalladaMgl direccionDetalladaMgl : detalladaMgls) {
                if (direccionDetalladaMgl.getSubDireccion() != null) {
                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                            .findHhppSubDireccion(direccionDetalladaMgl.getSubDireccion().getSdiId());
                    if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                        if (hhhpSubDirList.get(0).getEstadoRegistro() == 1) {
                            direccionDetalladaMgl.setHhppExistente(true);
                            direccionDetalladaMgl.setHhppMgl(hhhpSubDirList.get(0));
                        }
                    }
                }
                retorno.add(direccionDetalladaMgl);
            }
        }
        return retorno;
    }

    /**
     * Obtiene una dirección detallada apartir de un DrDireccion
     *
     * @param direccion DrDireccion del hhpp
     * @param gpo id del centro Poblado donde esta ubicada la direccion
     *
     * @return dirección detalla encontrada
     *
     * @author Juan David Hernandez
     * @throws ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaTabulada(DrDireccion direccion,
            BigDecimal gpo) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();
        return cmtDireccionDetalleMglDao.buscarDireccionTabuladaMer(gpo, direccion, false, 0, 0);
    }

    /**
     * Realiza el cambio de direccion de Antigua a Nueva
     *
     * @param hhppAntiguo DrListado de hhpp antiguos
     * @param direccionNueva objecto con la dirección nueva a cambiar.
     *
     * @author Juan David Hernandez
     * @param perfil
     * @throws ApplicationException
     */
    public void cambiarDireccionDetalladaHhppAntiguaANueva(HhppMgl hhppAntiguo,
            CmtDireccionDetalladaMgl direccionNueva, BigDecimal centroPobladoId,
            String usuario, int perfil) throws ApplicationException {
        try {
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            BigDecimal idDireccion = null;
            BigDecimal idSubDireccion = null;

            if (hhppAntiguo != null
                    && direccionNueva != null && centroPobladoId != null) {
                                                         
                //Se convierte el DrDireccion en formato detallaDireccionEntity                        
                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                GeograficoPoliticoMgl geograficoPoliticoMgl = geograficoPoliticoManager.findById(centroPobladoId);
      
                //Valida que el hhpp tenga idDireccion
                if (hhppAntiguo.getDireccionObj() != null
                        && hhppAntiguo.getDireccionObj().getDirId() != null) {
                    idDireccion = hhppAntiguo.getDireccionObj().getDirId();
                }

                //Valida si el hhpp tiene id SubDireccion
                if (hhppAntiguo.getSubDireccionObj() != null
                        && hhppAntiguo.getSubDireccionObj().getSdiId() != null) {
                    idSubDireccion = hhppAntiguo.getSubDireccionObj().getSdiId();
                }  
                
                List<CmtDireccionDetalladaMgl> direcionDetalladaAntigua 
                        = findDireccionDetallaByDirIdSdirId(idDireccion, idSubDireccion);
                
                if(direcionDetalladaAntigua != null && !direcionDetalladaAntigua.isEmpty()){
                    
              //se realiza el setteo de los tipos complementos a la direccion de la antigua a la nueva      
              direccionNueva.setCpTipoNivel1(direcionDetalladaAntigua.get(0).getCpTipoNivel1() != null
                      ? direcionDetalladaAntigua.get(0).getCpTipoNivel1() : null);
              
              direccionNueva.setCpTipoNivel2(direcionDetalladaAntigua.get(0).getCpTipoNivel2() != null
                      ? direcionDetalladaAntigua.get(0).getCpTipoNivel2() : null);
              
              direccionNueva.setCpTipoNivel3(direcionDetalladaAntigua.get(0).getCpTipoNivel3() != null
                      ? direcionDetalladaAntigua.get(0).getCpTipoNivel3() : null);
              
              direccionNueva.setCpTipoNivel4(direcionDetalladaAntigua.get(0).getCpTipoNivel4() != null
                      ? direcionDetalladaAntigua.get(0).getCpTipoNivel4() : null);
              
              direccionNueva.setCpTipoNivel5(direcionDetalladaAntigua.get(0).getCpTipoNivel5() != null
                      ? direcionDetalladaAntigua.get(0).getCpTipoNivel5() : null);
              
              direccionNueva.setCpTipoNivel6(direcionDetalladaAntigua.get(0).getCpTipoNivel6() != null
                      ? direcionDetalladaAntigua.get(0).getCpTipoNivel6() : null);
              
              //se realiza el setteo de los valores de complementos a la direccion de la antigua a la nueva      
              direccionNueva.setCpValorNivel1(direcionDetalladaAntigua.get(0).getCpValorNivel1() != null
                      ? direcionDetalladaAntigua.get(0).getCpValorNivel1() : null);
              
              direccionNueva.setCpValorNivel2(direcionDetalladaAntigua.get(0).getCpValorNivel2() != null
                      ? direcionDetalladaAntigua.get(0).getCpValorNivel2() : null);
              
              direccionNueva.setCpValorNivel3(direcionDetalladaAntigua.get(0).getCpValorNivel3() != null
                      ? direcionDetalladaAntigua.get(0).getCpValorNivel3() : null);
              
              direccionNueva.setCpValorNivel4(direcionDetalladaAntigua.get(0).getCpValorNivel4() != null
                      ? direcionDetalladaAntigua.get(0).getCpValorNivel4() : null);
              
              direccionNueva.setCpValorNivel5(direcionDetalladaAntigua.get(0).getCpValorNivel5() != null
                      ? direcionDetalladaAntigua.get(0).getCpValorNivel5() : null);
              
              direccionNueva.setCpValorNivel6(direcionDetalladaAntigua.get(0).getCpValorNivel6() != null
                      ? direcionDetalladaAntigua.get(0).getCpValorNivel6() : null);
              
                                          
               //Se transforma el objeto de direccionDetallada a DrDireccion de la nueva direccion 
               DrDireccion drDireccionNueva = parseCmtDireccionDetalladaMglToDrDireccion(direccionNueva);
         
                //Obtenemos la direccion actualizada en texto para mandarla a georeferenciar y crear
                String direccionNuevaTextoActualizada = drDireccionManager.getDireccion(drDireccionNueva);

                NegocioParamMultivalor param = new NegocioParamMultivalor();
                CityEntity cityEntityCreaDir = param.consultaDptoCiudad(geograficoPoliticoMgl.getGeoCodigoDane());
                if (cityEntityCreaDir == null || cityEntityCreaDir.getCityName() == null
                        || cityEntityCreaDir.getDpto() == null
                        || cityEntityCreaDir.getCityName().isEmpty()
                        || cityEntityCreaDir.getDpto().isEmpty()) {
                    throw new ApplicationException("La Ciudad no esta configurada en Direcciones");
                }

                String barrioStr = drDireccionManager.obtenerBarrio(drDireccionNueva);
                AddressRequest addressRequest = new AddressRequest();
                addressRequest.setCodDaneVt(cityEntityCreaDir.getCodDane());
                addressRequest.setAddress(direccionNuevaTextoActualizada);
                addressRequest.setCity(cityEntityCreaDir.getCityName());
                addressRequest.setState(cityEntityCreaDir.getDpto());
                addressRequest.setNeighborhood(barrioStr);

                AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
                ResponseMessage responseMessageCreateDir =
                        addressEJBRemote.createAddress(addressRequest,
                        usuario, "MGL", "", drDireccionNueva);
                if (responseMessageCreateDir != null
                        && responseMessageCreateDir.getNuevaDireccionDetallada() != null) {

                    //se busca la direccion detallada recien creada
                    CmtDireccionDetalladaMgl direccionDetalladaMgl = 
                            findById(responseMessageCreateDir.getNuevaDireccionDetallada().getDireccionDetalladaId());

                    //Si la direccion nueva ya existe en detallada hace el cambios de id en el hhpp
                    if (direccionDetalladaMgl != null) {
                        
                       //START AJUSTE ACTUALIZACION DE ESTRATO ANTERIOR A NUEVA DIRECCION
                        
                        //actualizacion de estrato en el cual mantiene el estrato anterior.
                        //id subdireccion anterior
                        if(idSubDireccion != null){
                            SubDireccionMglManager subDireccionManager = new SubDireccionMglManager();
                         SubDireccionMgl subDireccion = subDireccionManager.findById(idSubDireccion);
                        
                         //si se encontro la subdireccion anterior y la nueva tiene subdireccion se actualiza
                        if (subDireccion != null && direccionDetalladaMgl.getSubDireccion() != null
                                && direccionDetalladaMgl.getSubDireccion().getSdiId() != null) {
                           //se asigna el estrato anterior a la subdireccion
                            direccionDetalladaMgl.getSubDireccion().setSdiEstrato(subDireccion.getSdiEstrato());
                            //se actualiza la nueva subdireccion con el estrato anterior
                            subDireccionManager.update(direccionDetalladaMgl.getSubDireccion());
                        }else{
                            //si existe subdireccion anterior y la nueva direccion no tiene subdireccion
                            if(subDireccion != null && direccionDetalladaMgl.getDireccion() != null 
                                    && direccionDetalladaMgl.getDireccion().getDirId() != null ){                                
                                DireccionMglManager direccionMglManager = new DireccionMglManager();
                                DireccionMgl direccionMglAnterior = new DireccionMgl();
                                direccionMglAnterior.setDirId(idDireccion);
                                //direccion anterior
                                DireccionMgl direccionAnt = direccionMglManager.findById(direccionMglAnterior);
                                
                                if(direccionAnt != null && direccionDetalladaMgl.getDireccion() != null){
                                    //se asigna el estrato anterior a la nueva direccion y se actualiza
                                    direccionDetalladaMgl.getDireccion().setDirEstrato(direccionAnt.getDirEstrato());
                                    direccionMglManager.update(direccionDetalladaMgl.getDireccion());
                                }                                
                            }
                        }                           
                            
                        }else{
                            //si la direccion no es una subdireccion
                            if(idDireccion != null){                                
                                if (direccionDetalladaMgl.getSubDireccion() != null) {

                                    DireccionMglManager direccionMglManager = new DireccionMglManager();
                                    DireccionMgl direccionMglAnterior = new DireccionMgl();
                                    direccionMglAnterior.setDirId(idDireccion);
                                    //direccion anterior
                                    DireccionMgl direccionAnt = direccionMglManager.findById(direccionMglAnterior);

                                      //si la direccion anterior es direccion y la nueva una subdireccion
                                    if (direccionAnt != null && direccionDetalladaMgl.getSubDireccion() != null) {
                                        SubDireccionMglManager subDireccionManager = new SubDireccionMglManager();
                                        //se asigna el estrato anterior a la subdireccion
                                        direccionDetalladaMgl.getSubDireccion().setSdiEstrato(direccionAnt.getDirEstrato());
                                        //se actualiza la nueva subdireccion con el estrato anterior
                                        subDireccionManager.update(direccionDetalladaMgl.getSubDireccion());
                                    }

                                }else{
                                    //si existe direccion anterior y la nueva direccion no tiene subdireccion
                                    if (direccionDetalladaMgl.getDireccion() != null
                                            && direccionDetalladaMgl.getDireccion().getDirId() != null) {

                                        DireccionMglManager direccionMglManager = new DireccionMglManager();
                                        DireccionMgl direccionMglAnterior = new DireccionMgl();
                                        direccionMglAnterior.setDirId(idDireccion);
                                        //direccion anterior
                                        DireccionMgl direccionAnt = direccionMglManager.findById(direccionMglAnterior);

                                        if (direccionAnt != null && direccionDetalladaMgl.getDireccion() != null) {
                                            //se asigna el estrato anterior a la nueva direccion y se actualiza
                                            direccionDetalladaMgl.getDireccion().setDirEstrato(direccionAnt.getDirEstrato());
                                            direccionMglManager.update(direccionDetalladaMgl.getDireccion());
                                        }
                                    }
                                }
                            }
                        }
                        //FIN AJUSTE ACTUALIZACION DE ESTRATO ANTERIOR A NUEVA DIRECCION
                        
                        //actualización en formato RR al Hhpp  
                        DetalleDireccionEntity detalleDireccionEntity = drDireccionNueva.convertToDetalleDireccionEntity();
                        DireccionRRManager direccionRRManager = new DireccionRRManager(detalleDireccionEntity,
                                geograficoPoliticoMgl.getGpoMultiorigen(), geograficoPoliticoMgl);
                        //Se obtiene el detalle de la direccion en formato RR
                        DireccionRREntity detalleDireccionEntityRR = direccionRRManager.getDireccion();

                        HhppMglManager hhppMglManager = new HhppMglManager();

                        //Obtenemos los Hhpp de la Subdireccion  
                        if (idSubDireccion != null) {
                            //Se realiza busqueda de todas las subdireccion del Hhpp para hacer actualización de datos del hhpp en todas sus tecnologias
                            List<HhppMgl> hhhpSubDirList = hhppMglManager.findHhppSubDireccion(idSubDireccion);

                            if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                                for (HhppMgl hhppSubMgl : hhhpSubDirList) {
                                    //Se establecen los valores al hhpp que se desea actualizar
                                    hhppSubMgl.setHhpCalle(detalleDireccionEntityRR.getCalle() != null
                                            ? detalleDireccionEntityRR.getCalle().toUpperCase() : null);
                                    hhppSubMgl.setHhpPlaca(detalleDireccionEntityRR.getNumeroUnidad() != null
                                            ? detalleDireccionEntityRR.getNumeroUnidad().toUpperCase() : null);
                                    hhppSubMgl.setHhpApart(detalleDireccionEntityRR.getNumeroApartamento() != null
                                            ? detalleDireccionEntityRR.getNumeroApartamento().toUpperCase() : null); 
                                    
                                    if(direccionDetalladaMgl.getSubDireccion() != null 
                                            && direccionDetalladaMgl.getSubDireccion().getSdiId() != null){                                        
                                        hhppSubMgl.setSubDireccionObj(direccionDetalladaMgl.getSubDireccion());
                                        hhppSubMgl.setDireccionObj(direccionDetalladaMgl.getDireccion());
                                    }else{
                                         hhppSubMgl.setSubDireccionObj(null);
                                        hhppSubMgl.setDireccionObj(direccionDetalladaMgl.getDireccion());                                        
                                    }
                                    hhppMglManager.updateHhppMgl(hhppSubMgl, usuario, perfil);
                                    LOGGER.error("Hhpp actualizado " + hhppSubMgl.getHhpId());
                                }
                            }
                        } else {
                            //Obtenemos los Hhpp de la Direccion principal    
                            if (idDireccion != null) {
                                List<HhppMgl> hhhpDirList = hhppMglManager.findHhppDireccion(idDireccion);

                                if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                                    for (HhppMgl hhppDirMgl : hhhpDirList) {
                                        //Se establecen los valores al hhpp que se desea actualizar
                                        hhppDirMgl.setHhpCalle(detalleDireccionEntityRR.getCalle() != null
                                                ? detalleDireccionEntityRR.getCalle().toUpperCase() : null);
                                        hhppDirMgl.setHhpPlaca(detalleDireccionEntityRR.getNumeroUnidad() != null
                                                ? detalleDireccionEntityRR.getNumeroUnidad().toUpperCase() : null);
                                        hhppDirMgl.setHhpApart(detalleDireccionEntityRR.getNumeroApartamento() != null
                                                ? detalleDireccionEntityRR.getNumeroApartamento().toUpperCase() : null);
                                        
                                        if (direccionDetalladaMgl.getSubDireccion() != null
                                                && direccionDetalladaMgl.getSubDireccion().getSdiId() != null) {
                                            hhppDirMgl.setSubDireccionObj(direccionDetalladaMgl.getSubDireccion());
                                            hhppDirMgl.setDireccionObj(direccionDetalladaMgl.getDireccion());
                                        } else {
                                            hhppDirMgl.setSubDireccionObj(null);
                                            hhppDirMgl.setDireccionObj(direccionDetalladaMgl.getDireccion());
                                        }
                                        hhppMglManager.updateHhppMgl(hhppDirMgl, usuario, perfil);
                                        LOGGER.error("Hhpp actualizado " + hhppDirMgl.getHhpId());
                                    }
                                }
                            }
                        }

                    } else {
                        throw new ApplicationException("Se fue posible crear la direccion en el sistema MGL, el cambio de dirección no es posible realizarse.");
                    }
                } else {
                    throw new ApplicationException("Se fue posible crear la direccion en el sistema MGL, el cambio de dirección no es posible realizarse.");
                }
                } else {
                    throw new ApplicationException("Se fue posible crear la direccion en el sistema MGL, el cambio de dirección no es posible realizarse.");
                }
                
            }
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Error la intentar hacer cambios de dirección de malla antigua a nueva '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  e);
        }
    }

    public static CmtDireccionDetalladaMgl update(CmtDireccionDetalladaMgl direccionDetallada, String usuario, int perfil)
            throws ApplicationException {
        try {
            CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDaoImpl1 = new CmtDireccionDetalleMglDaoImpl();
            return cmtDireccionDetalleMglDaoImpl1.updateCm(direccionDetallada, usuario, perfil);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(CmtDireccionDetalleMglManager.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Obtiene una direccion detallada actualizado con un cambio en los valores
     * correspondientes a la CK.Cambia los valores viejos y resetea los no
 modificados.
     *
     * @param direccionDetalladaNueva direccion detallada con los nuevos valores a
     * actualizar
     * @param direccionDetalladaAntigua direccion detallada con direccion
     * antigua.
     *
     * @return direccion detallada con valores actualizados.
     *
     * @author Juan David Hernandez
     * @throws ApplicationException
     */
    public CmtDireccionDetalladaMgl obtenerDireccionDetalladaActualizada(CmtDireccionDetalladaMgl direccionDetalladaNueva,
            CmtDireccionDetalladaMgl direccionDetalladaAntigua) throws ApplicationException {
        try {

            if (direccionDetalladaNueva != null && direccionDetalladaAntigua != null) {

                if (direccionDetalladaNueva.getTipoViaPrincipal() != null
                        && !direccionDetalladaNueva.getTipoViaPrincipal().trim().isEmpty()) {
                    direccionDetalladaAntigua.setTipoViaPrincipal(direccionDetalladaNueva.getTipoViaPrincipal().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setTipoViaPrincipal(null);
                }
                if (direccionDetalladaNueva.getNumViaPrincipal() != null
                        && !direccionDetalladaNueva.getNumViaPrincipal().trim().isEmpty()) {
                    direccionDetalladaAntigua.setNumViaPrincipal(direccionDetalladaNueva.getNumViaPrincipal().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setTipoViaPrincipal(null);
                }

                if (direccionDetalladaNueva.getNumViaGeneradora() != null
                        && !direccionDetalladaNueva.getNumViaGeneradora().trim().isEmpty()) {
                    direccionDetalladaAntigua.setNumViaGeneradora(direccionDetalladaNueva.getNumViaGeneradora().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setNumViaGeneradora(null);
                }

                if (direccionDetalladaNueva.getLtViaPrincipal() != null
                        && !direccionDetalladaNueva.getLtViaPrincipal().trim().isEmpty()) {
                    direccionDetalladaAntigua.setLtViaPrincipal(direccionDetalladaNueva.getLtViaPrincipal().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setLtViaPrincipal(null);
                }

                if (direccionDetalladaNueva.getLtViaGeneradora() != null
                        && !direccionDetalladaNueva.getLtViaGeneradora().trim().isEmpty()) {
                    direccionDetalladaAntigua.setLtViaGeneradora(direccionDetalladaNueva.getLtViaGeneradora().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setLtViaGeneradora(null);
                }

                if (direccionDetalladaNueva.getNlPostViaP() != null
                        && !direccionDetalladaNueva.getNlPostViaP().trim().isEmpty()) {
                    direccionDetalladaAntigua.setNlPostViaP(direccionDetalladaNueva.getNlPostViaP().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setNlPostViaP(null);
                }

                if (direccionDetalladaNueva.getNlPostViaG() != null
                        && !direccionDetalladaNueva.getNlPostViaG().trim().isEmpty()) {
                    direccionDetalladaAntigua.setNlPostViaG(direccionDetalladaNueva.getNlPostViaG().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setNlPostViaG(null);
                }

                if (direccionDetalladaNueva.getBisViaPrincipal() != null
                        && !direccionDetalladaNueva.getBisViaPrincipal().trim().isEmpty()) {
                    direccionDetalladaAntigua.setBisViaPrincipal(direccionDetalladaNueva.getBisViaPrincipal().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setBisViaPrincipal(null);
                }

                if (direccionDetalladaNueva.getBisViaGeneradora() != null
                        && !direccionDetalladaNueva.getBisViaGeneradora().trim().isEmpty()) {
                    direccionDetalladaAntigua.setBisViaGeneradora(direccionDetalladaNueva.getBisViaGeneradora().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setBisViaGeneradora(null);
                }

                if (direccionDetalladaNueva.getCuadViaPrincipal() != null
                        && !direccionDetalladaNueva.getCuadViaPrincipal().trim().isEmpty()) {
                    direccionDetalladaAntigua.setCuadViaPrincipal(direccionDetalladaNueva.getCuadViaPrincipal().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setCuadViaPrincipal(null);
                }

                if (direccionDetalladaNueva.getCuadViaGeneradora() != null
                        && !direccionDetalladaNueva.getCuadViaGeneradora().trim().isEmpty()) {
                    direccionDetalladaAntigua.setCuadViaGeneradora(direccionDetalladaNueva.getCuadViaGeneradora().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setCuadViaGeneradora(null);
                }

                if (direccionDetalladaNueva.getTipoViaGeneradora() != null
                        && !direccionDetalladaNueva.getTipoViaGeneradora().trim().isEmpty()) {
                    direccionDetalladaAntigua.setTipoViaGeneradora(direccionDetalladaNueva.getTipoViaGeneradora().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setTipoViaGeneradora(null);
                }

                if (direccionDetalladaNueva.getPlacaDireccion() != null
                        && !direccionDetalladaNueva.getPlacaDireccion().trim().isEmpty()) {
                    direccionDetalladaAntigua.setPlacaDireccion(direccionDetalladaNueva.getPlacaDireccion().toUpperCase());
                } else {
                    direccionDetalladaAntigua.setPlacaDireccion(null);
                }
            }
            return direccionDetalladaAntigua;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Obtiene una DrDireccion CK a partir de una direccion detallada CK
     *
     * @param direccionDetMgl direccion detallada a convertir
     * @param subDireccion true para indicar que se desea convertir con los
     * campos correspondiente a la subDireccion CK
     *
     * @return drDireccion de la direccion.
     *
     * @author Juan David Hernandez
     * @throws ApplicationException
     */
    public DrDireccion parseDireccionDetalladaMglToDrDireccionSubDireccion(CmtDireccionDetalladaMgl direccionDetMgl, boolean subDireccion) throws ApplicationException {
        DrDireccion drDireccion = new DrDireccion();
        try {
            if (direccionDetMgl.getIdTipoDireccion() != null && !direccionDetMgl.getIdTipoDireccion().trim().isEmpty()) {
                drDireccion.setIdTipoDireccion(direccionDetMgl.getIdTipoDireccion().toUpperCase());
            }

            if (direccionDetMgl.getTipoViaPrincipal() != null && !direccionDetMgl.getTipoViaPrincipal().trim().isEmpty()) {
                drDireccion.setTipoViaPrincipal(direccionDetMgl.getTipoViaPrincipal().toUpperCase());
            }

            if (direccionDetMgl.getNumViaPrincipal() != null && !direccionDetMgl.getNumViaPrincipal().trim().isEmpty()) {
                drDireccion.setNumViaPrincipal(direccionDetMgl.getNumViaPrincipal().toUpperCase());
            }

            if (direccionDetMgl.getLtViaPrincipal() != null && !direccionDetMgl.getLtViaPrincipal().trim().isEmpty()) {
                drDireccion.setLtViaPrincipal(direccionDetMgl.getLtViaPrincipal().toUpperCase());
            }

            if (direccionDetMgl.getNlPostViaP() != null && !direccionDetMgl.getNlPostViaP().trim().isEmpty()) {
                drDireccion.setNlPostViaP(direccionDetMgl.getNlPostViaP().toUpperCase());
            }

            if (direccionDetMgl.getBisViaPrincipal() != null && !direccionDetMgl.getBisViaPrincipal().trim().isEmpty()) {
                drDireccion.setBisViaPrincipal(direccionDetMgl.getBisViaPrincipal().toUpperCase());
            }

            if (direccionDetMgl.getCuadViaPrincipal() != null && !direccionDetMgl.getCuadViaPrincipal().trim().isEmpty()) {
                drDireccion.setCuadViaPrincipal(direccionDetMgl.getCuadViaPrincipal().toUpperCase());
            }

            if (direccionDetMgl.getTipoViaGeneradora() != null && !direccionDetMgl.getTipoViaGeneradora().trim().isEmpty()) {
                drDireccion.setTipoViaGeneradora(direccionDetMgl.getTipoViaGeneradora().toUpperCase());
            }

            if (direccionDetMgl.getNumViaGeneradora() != null && !direccionDetMgl.getNumViaGeneradora().trim().isEmpty()) {
                drDireccion.setNumViaGeneradora(direccionDetMgl.getNumViaGeneradora().toUpperCase());
            }

            if (direccionDetMgl.getLtViaGeneradora() != null && !direccionDetMgl.getLtViaGeneradora().trim().isEmpty()) {
                drDireccion.setLtViaGeneradora(direccionDetMgl.getLtViaGeneradora().toUpperCase());
            }

            if (direccionDetMgl.getNlPostViaG() != null && !direccionDetMgl.getNlPostViaG().trim().isEmpty()) {
                drDireccion.setNlPostViaG(direccionDetMgl.getNlPostViaG().toUpperCase());
            }

            if (direccionDetMgl.getBisViaGeneradora() != null && !direccionDetMgl.getBisViaGeneradora().trim().isEmpty()) {
                drDireccion.setBisViaGeneradora(direccionDetMgl.getBisViaGeneradora().toUpperCase());
            }

            if (direccionDetMgl.getCuadViaGeneradora() != null && !direccionDetMgl.getCuadViaGeneradora().trim().isEmpty()) {
                drDireccion.setCuadViaGeneradora(direccionDetMgl.getCuadViaGeneradora().toUpperCase());
            }

            if (direccionDetMgl.getPlacaDireccion() != null && !direccionDetMgl.getPlacaDireccion().trim().isEmpty()) {
                drDireccion.setPlacaDireccion(direccionDetMgl.getPlacaDireccion().toUpperCase());
            }

            if (direccionDetMgl.getMzTipoNivel1() != null && !direccionDetMgl.getMzTipoNivel1().trim().isEmpty()) {
                drDireccion.setMzTipoNivel1(direccionDetMgl.getMzTipoNivel1().toUpperCase());
            }
            if (direccionDetMgl.getMzTipoNivel2() != null && !direccionDetMgl.getMzTipoNivel2().trim().isEmpty()) {
                drDireccion.setMzTipoNivel2(direccionDetMgl.getMzTipoNivel2().toUpperCase());
            }
            if (direccionDetMgl.getMzTipoNivel3() != null && !direccionDetMgl.getMzTipoNivel3().trim().isEmpty()) {
                drDireccion.setMzTipoNivel3(direccionDetMgl.getMzTipoNivel3().toUpperCase());
            }
            if (direccionDetMgl.getMzTipoNivel4() != null && !direccionDetMgl.getMzTipoNivel4().trim().isEmpty()) {
                drDireccion.setMzTipoNivel4(direccionDetMgl.getMzTipoNivel4().toUpperCase());
            }
            if (direccionDetMgl.getMzTipoNivel5() != null && !direccionDetMgl.getMzTipoNivel5().trim().isEmpty()) {
                drDireccion.setMzTipoNivel5(direccionDetMgl.getMzTipoNivel5().toUpperCase());
            }
            
             if (direccionDetMgl.getMzTipoNivel6() != null && !direccionDetMgl.getMzTipoNivel6().trim().isEmpty()) {
                drDireccion.setMzTipoNivel6(direccionDetMgl.getMzTipoNivel6().toUpperCase());
            }
            
            if (direccionDetMgl.getMzValorNivel1() != null && !direccionDetMgl.getMzValorNivel1().trim().isEmpty()) {
                drDireccion.setMzValorNivel1(direccionDetMgl.getMzValorNivel1().toUpperCase());
            }
            if (direccionDetMgl.getMzValorNivel2() != null && !direccionDetMgl.getMzValorNivel2().trim().isEmpty()) {
                drDireccion.setMzValorNivel2(direccionDetMgl.getMzValorNivel2().toUpperCase());
            }
            if (direccionDetMgl.getMzValorNivel3() != null && !direccionDetMgl.getMzValorNivel3().trim().isEmpty()) {
                drDireccion.setMzValorNivel3(direccionDetMgl.getMzValorNivel3().toUpperCase());
            }
            if (direccionDetMgl.getMzValorNivel4() != null && !direccionDetMgl.getMzValorNivel4().trim().isEmpty()) {
                drDireccion.setMzValorNivel4(direccionDetMgl.getMzValorNivel4().toUpperCase());
            }
            if (direccionDetMgl.getMzValorNivel5() != null && !direccionDetMgl.getMzValorNivel5().trim().isEmpty()) {
                drDireccion.setMzValorNivel5(direccionDetMgl.getMzValorNivel5().toUpperCase());
            }
            if (direccionDetMgl.getMzValorNivel6() != null && !direccionDetMgl.getMzValorNivel6().trim().isEmpty()) {
                drDireccion.setMzValorNivel6(direccionDetMgl.getMzValorNivel6().toUpperCase());
            }

            if (direccionDetMgl.getItTipoPlaca() != null && !direccionDetMgl.getItTipoPlaca().trim().isEmpty()) {
                drDireccion.setItTipoPlaca(direccionDetMgl.getItTipoPlaca().toUpperCase());
            }

            if (direccionDetMgl.getItValorPlaca() != null && !direccionDetMgl.getItValorPlaca().trim().isEmpty()) {
                drDireccion.setItValorPlaca(direccionDetMgl.getItValorPlaca().toUpperCase());
            }

            if (direccionDetMgl.getBarrio() != null && !direccionDetMgl.getBarrio().trim().isEmpty()) {
                drDireccion.setBarrio(direccionDetMgl.getBarrio().toUpperCase().toUpperCase());
            }

            if (subDireccion) {

                if (direccionDetMgl.getCpTipoNivel1() != null && !direccionDetMgl.getCpTipoNivel1().trim().isEmpty()) {
                    drDireccion.setCpTipoNivel1(direccionDetMgl.getCpTipoNivel1().toUpperCase());
                }
                if (direccionDetMgl.getCpTipoNivel2() != null && !direccionDetMgl.getCpTipoNivel2().trim().isEmpty()) {
                    drDireccion.setCpTipoNivel2(direccionDetMgl.getCpTipoNivel2().toUpperCase());
                }
                if (direccionDetMgl.getCpTipoNivel3() != null && !direccionDetMgl.getCpTipoNivel3().trim().isEmpty()) {
                    drDireccion.setCpTipoNivel3(direccionDetMgl.getCpTipoNivel3().toUpperCase());
                }
                if (direccionDetMgl.getCpTipoNivel4() != null && !direccionDetMgl.getCpTipoNivel4().trim().isEmpty()) {
                    drDireccion.setCpTipoNivel4(direccionDetMgl.getCpTipoNivel4().toUpperCase());
                }
                if (direccionDetMgl.getCpTipoNivel5() != null && !direccionDetMgl.getCpTipoNivel5().trim().isEmpty()) {
                    drDireccion.setCpTipoNivel5(direccionDetMgl.getCpTipoNivel5().toUpperCase());
                }
                if (direccionDetMgl.getCpTipoNivel6() != null && !direccionDetMgl.getCpTipoNivel6().trim().isEmpty()) {
                    drDireccion.setCpTipoNivel6(direccionDetMgl.getCpTipoNivel6().toUpperCase());
                }

                if (direccionDetMgl.getCpValorNivel1() != null && !direccionDetMgl.getCpValorNivel1().trim().isEmpty()) {
                    drDireccion.setCpValorNivel1(direccionDetMgl.getCpValorNivel1().toUpperCase());
                }
                if (direccionDetMgl.getCpValorNivel2() != null && !direccionDetMgl.getCpValorNivel2().trim().isEmpty()) {
                    drDireccion.setCpValorNivel2(direccionDetMgl.getCpValorNivel2().toUpperCase());
                }
                if (direccionDetMgl.getCpValorNivel3() != null && !direccionDetMgl.getCpValorNivel3().trim().isEmpty()) {
                    drDireccion.setCpValorNivel3(direccionDetMgl.getCpValorNivel3().toUpperCase());
                }
                if (direccionDetMgl.getCpValorNivel4() != null && !direccionDetMgl.getCpValorNivel4().trim().isEmpty()) {
                    drDireccion.setCpValorNivel4(direccionDetMgl.getCpValorNivel4().toUpperCase());
                }
                if (direccionDetMgl.getCpValorNivel5() != null && !direccionDetMgl.getCpValorNivel5().trim().isEmpty()) {
                    drDireccion.setCpValorNivel5(direccionDetMgl.getCpValorNivel5().toUpperCase());
                }
                if (direccionDetMgl.getCpValorNivel6() != null && !direccionDetMgl.getCpValorNivel6().trim().isEmpty()) {
                    drDireccion.setCpValorNivel6(direccionDetMgl.getCpValorNivel6().toUpperCase());
                }
            } else {
                drDireccion.setCpTipoNivel1(null);
                drDireccion.setCpTipoNivel2(null);
                drDireccion.setCpTipoNivel3(null);
                drDireccion.setCpTipoNivel4(null);
                drDireccion.setCpTipoNivel5(null);
                drDireccion.setCpTipoNivel6(null);
                drDireccion.setCpValorNivel1(null);
                drDireccion.setCpValorNivel2(null);
                drDireccion.setCpValorNivel3(null);
                drDireccion.setCpValorNivel4(null);
                drDireccion.setCpValorNivel5(null);
                drDireccion.setCpValorNivel6(null);
            }
        } catch (Exception e) {
            LOGGER.error("Error parseCmtDireccionDetalladaMglToDrDireccion "
                    + "de CmtdrDireccionalleMglManager " + e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
        return drDireccion;
    }

    /**
     * Realiza el cambio de apto a un hhpp.
     *
     * @param hhppMgl hhppAntiguo que se desea cambiar el apto
     * @param centroPobladoId donde se encuentra ubicado el hhpp
     * @param unidadModificadaNuevoApto unidad que cuenta con el apto nuevo que
     * se desea cambiar
     * @param usuario logueado
     * @param perfil logueado
     *
     * @author Juan David Hernandez
     * @throws ApplicationException
     */
    public void cambiarAptoDireccionDetalladaHhpp(HhppMgl hhppMgl, UnidadStructPcml unidadModificadaNuevoApto,
            BigDecimal centroPobladoId, String usuario, int perfil) throws ApplicationException {
        try {
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            DireccionRRManager direccionRRManager = new DireccionRRManager(false);
            BigDecimal idDireccion = null;
            BigDecimal idSubDireccion = null;

            if (hhppMgl != null && centroPobladoId != null) {
                //Valida que el hhpp tenga idDireccion
                if (hhppMgl.getDireccionObj() != null
                        && hhppMgl.getDireccionObj().getDirId() != null) {
                    idDireccion = hhppMgl.getDireccionObj().getDirId();
                }

                //Valida si el hhpp tiene id SubDireccion
                if (hhppMgl.getSubDireccionObj() != null
                        && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                    idSubDireccion = hhppMgl.getSubDireccionObj().getSdiId();
                }

                //Se convierte el DrDireccion en formato detallaDireccionEntity                        
                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                GeograficoPoliticoMgl geograficoPoliticoMgl = geograficoPoliticoManager.findById(centroPobladoId);

                //Se realiza busqueda de direccion detallada segun los ids que contenga el Hhpp
                List<CmtDireccionDetalladaMgl> direccionDetalladaList = findDireccionDetallaByDirIdSdirId(idDireccion, idSubDireccion);


                //Se toma cada uno de los registros que se hayan encontrado para realizar la actualización de dirección

                //establecemos los valores del nuevo apto
                direccionDetalladaList.get(0).setCpTipoNivel5(unidadModificadaNuevoApto.getTipoNivel5() != null
                        ? unidadModificadaNuevoApto.getTipoNivel5() : null);
                direccionDetalladaList.get(0).setCpValorNivel5(unidadModificadaNuevoApto.getValorNivel5() != null
                        ? unidadModificadaNuevoApto.getValorNivel5() : null);
                direccionDetalladaList.get(0).setCpTipoNivel6(unidadModificadaNuevoApto.getTipoNivel6() != null
                        ? unidadModificadaNuevoApto.getTipoNivel6() : null);
                direccionDetalladaList.get(0).setCpValorNivel6(unidadModificadaNuevoApto.getValorNivel6() != null
                        ? unidadModificadaNuevoApto.getValorNivel6() : null);


                //obtener el drDireccion de la direccionDetallada actualizada para obtener string de la direccion
                DrDireccion drDireccionActualizada = parseDireccionDetalladaMglToDrDireccionSubDireccion(direccionDetalladaList.get(0), true);

                //Obtenemos la direccion actualizada en texto para mandarla a georeferenciar
                String direccionNuevaTextoActualizada = drDireccionManager.getDireccion(drDireccionActualizada);

                NegocioParamMultivalor param = new NegocioParamMultivalor();
                CityEntity cityEntityCreaDir = param.consultaDptoCiudad(geograficoPoliticoMgl.getGeoCodigoDane());
                if (cityEntityCreaDir == null || cityEntityCreaDir.getCityName() == null
                        || cityEntityCreaDir.getDpto() == null
                        || cityEntityCreaDir.getCityName().isEmpty()
                        || cityEntityCreaDir.getDpto().isEmpty()) {
                    throw new ApplicationException("La Ciudad no esta configurada en Direcciones");
                }

                String barrioStr = drDireccionManager.obtenerBarrio(drDireccionActualizada);
                AddressRequest addressRequest = new AddressRequest();
                addressRequest.setCodDaneVt(cityEntityCreaDir.getCodDane());
                addressRequest.setAddress(direccionNuevaTextoActualizada);
                addressRequest.setCity(cityEntityCreaDir.getCityName());
                addressRequest.setState(cityEntityCreaDir.getDpto());
                addressRequest.setNeighborhood(barrioStr);

                AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
                ResponseMessage responseMessageCreateDir =
                        addressEJBRemote.createAddress(addressRequest,
                        usuario, "MGL", "", drDireccionActualizada);
                if (responseMessageCreateDir != null && responseMessageCreateDir.getNuevaDireccionDetallada() != null) {

                    CmtDireccionDetalladaMgl direccionDetalladaMgl = findById(responseMessageCreateDir.getNuevaDireccionDetallada().getDireccionDetalladaId());

                    if (direccionDetalladaMgl != null) {
                        
                        //START AJUSTE ACTUALIZACION DE ESTRATO ANTERIOR A NUEVA DIRECCION
                        
                        //actualizacion de estrato en el cual mantiene el estrato anterior.
                        //id subdireccion anterior
                        if(idSubDireccion != null){
                            SubDireccionMglManager subDireccionManager = new SubDireccionMglManager();
                         SubDireccionMgl subDireccion = subDireccionManager.findById(idSubDireccion);
                        
                         //si se encontro la subdireccion anterior y la nueva tiene subdireccion se actualiza
                        if (subDireccion != null && direccionDetalladaMgl.getSubDireccion() != null
                                && direccionDetalladaMgl.getSubDireccion().getSdiId() != null) {
                           //se asigna el estrato anterior a la subdireccion
                            direccionDetalladaMgl.getSubDireccion().setSdiEstrato(subDireccion.getSdiEstrato());
                            //se actualiza la nueva subdireccion con el estrato anterior
                            subDireccionManager.update(direccionDetalladaMgl.getSubDireccion());
                        }else{
                            //si existe subdireccion anterior y la nueva direccion no tiene subdireccion
                            if(subDireccion != null && direccionDetalladaMgl.getDireccion() != null 
                                    && direccionDetalladaMgl.getDireccion().getDirId() != null ){                                
                                DireccionMglManager direccionMglManager = new DireccionMglManager();
                                DireccionMgl direccionMglAnterior = new DireccionMgl();
                                direccionMglAnterior.setDirId(idDireccion);
                                //direccion anterior
                                DireccionMgl direccionAnt = direccionMglManager.findById(direccionMglAnterior);
                                
                                if(direccionAnt != null && direccionDetalladaMgl.getDireccion() != null){
                                    //se asigna el estrato anterior a la nueva direccion y se actualiza
                                    direccionDetalladaMgl.getDireccion().setDirEstrato(direccionAnt.getDirEstrato());
                                    direccionMglManager.update(direccionDetalladaMgl.getDireccion());
                                }                                
                            }
                        }                           
                            
                        }else{
                            //si la direccion no es una subdireccion
                            if(idDireccion != null){                                
                                if (direccionDetalladaMgl.getSubDireccion() != null) {

                                    DireccionMglManager direccionMglManager = new DireccionMglManager();
                                    DireccionMgl direccionMglAnterior = new DireccionMgl();
                                    direccionMglAnterior.setDirId(idDireccion);
                                    //direccion anterior
                                    DireccionMgl direccionAnt = direccionMglManager.findById(direccionMglAnterior);

                                      //si la direccion anterior es direccion y la nueva una subdireccion
                                    if (direccionAnt != null && direccionDetalladaMgl.getSubDireccion() != null) {
                                        SubDireccionMglManager subDireccionManager = new SubDireccionMglManager();
                                        //se asigna el estrato anterior a la subdireccion
                                        direccionDetalladaMgl.getSubDireccion().setSdiEstrato(direccionAnt.getDirEstrato());
                                        //se actualiza la nueva subdireccion con el estrato anterior
                                        subDireccionManager.update(direccionDetalladaMgl.getSubDireccion());
                                    }

                                }else{
                                    //si existe direccion anterior y la nueva direccion no tiene subdireccion
                                    if (direccionDetalladaMgl.getDireccion() != null
                                            && direccionDetalladaMgl.getDireccion().getDirId() != null) {

                                        DireccionMglManager direccionMglManager = new DireccionMglManager();
                                        DireccionMgl direccionMglAnterior = new DireccionMgl();
                                        direccionMglAnterior.setDirId(idDireccion);
                                        //direccion anterior
                                        DireccionMgl direccionAnt = direccionMglManager.findById(direccionMglAnterior);

                                        if (direccionAnt != null && direccionDetalladaMgl.getDireccion() != null) {
                                            //se asigna el estrato anterior a la nueva direccion y se actualiza
                                            direccionDetalladaMgl.getDireccion().setDirEstrato(direccionAnt.getDirEstrato());
                                            direccionMglManager.update(direccionDetalladaMgl.getDireccion());
                                        }
                                    }
                                }
                            }
                        }
                        //FIN AJUSTE ACTUALIZACION DE ESTRATO ANTERIOR A NUEVA DIRECCION
                        

                        //actualización en formato RR al Hhpp                           
                        //Se convierte el DrDireccion en formato detallaDireccionEntity 
                        DetalleDireccionEntity detalleDireccionEntity = drDireccionActualizada.convertToDetalleDireccionEntity();

                        direccionRRManager = new DireccionRRManager(detalleDireccionEntity,
                                geograficoPoliticoMgl.getGpoMultiorigen(), geograficoPoliticoMgl);

                        //Se obtiene el detalle de la direccion en formato RR
                        DireccionRREntity detalleDireccionEntityRR = direccionRRManager.getDireccion();

                        //actualización en formato RR al Hhpp  
                        HhppMglManager hhppMglManager = new HhppMglManager();

                        //Obtenemos los Hhpp de la Subdireccion  
                        if (idSubDireccion != null) {
                            //Se realiza busqueda de todas las subdireccion del Hhpp para hacer actualización de datos del hhpp en todas sus tecnologias
                            List<HhppMgl> hhhpSubDirList = hhppMglManager.findHhppSubDireccion(idSubDireccion);

                            if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                                for (HhppMgl hhppSubMgl : hhhpSubDirList) {
                                    //Se establecen los valores al hhpp que se desea actualizar
                                    hhppSubMgl.setHhpCalle(detalleDireccionEntityRR.getCalle() != null
                                            ? detalleDireccionEntityRR.getCalle().toUpperCase() : null);
                                    hhppSubMgl.setHhpPlaca(detalleDireccionEntityRR.getNumeroUnidad() != null
                                            ? detalleDireccionEntityRR.getNumeroUnidad().toUpperCase() : null);
                                    hhppSubMgl.setHhpApart(detalleDireccionEntityRR.getNumeroApartamento() != null
                                            ? detalleDireccionEntityRR.getNumeroApartamento().toUpperCase() : null);
                                    //cardenaslb
                                    if (hhppMgl != null && hhppMgl.getThhId() != null && !hhppMgl.getThhId().isEmpty()) {
                                        hhppSubMgl.setThhId(hhppMgl.getThhId());
                                        hhppSubMgl.setHhpTipoUnidad(hhppMgl.getThhId());
                                    }
                                    //actualiza hhpp los campos calle, unidad, placa

                                    if (direccionDetalladaMgl.getSubDireccion() != null) {
                                        hhppSubMgl.setSubDireccionObj(direccionDetalladaMgl.getSubDireccion());
                                        hhppSubMgl.setDireccionObj(direccionDetalladaMgl.getDireccion());
                                    } else {
                                        if (direccionDetalladaMgl.getDireccion() != null) {
                                            hhppSubMgl.setSubDireccionObj(null);
                                            hhppSubMgl.setDireccionObj(direccionDetalladaMgl.getDireccion());
                                        }
                                    }

                                    hhppMglManager.updateHhppMgl(hhppSubMgl, usuario, perfil);
                                    LOGGER.error("Hhpp actualizado de subDireccion. idHhpp: " + hhppSubMgl.getHhpId());
                                }
                            } else {
                                throw new ApplicationException("Error al intentar cargar el listado de Hhpp de "
                                        + "subdireccion al actualizar el cambio de apto");
                            }
                        } else {
                            //Obtenemos los Hhpp de la Direccion principal    
                            if (idDireccion != null) {
                                List<HhppMgl> hhhpDirList = hhppMglManager.findHhppDireccion(idDireccion);

                                if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                                    for (HhppMgl hhppDirMgl : hhhpDirList) {
                                        //Se establecen los valores al hhpp que se desea actualizar
                                        hhppDirMgl.setHhpCalle(detalleDireccionEntityRR.getCalle() != null
                                                ? detalleDireccionEntityRR.getCalle().toUpperCase() : null);
                                        hhppDirMgl.setHhpPlaca(detalleDireccionEntityRR.getNumeroUnidad() != null
                                                ? detalleDireccionEntityRR.getNumeroUnidad().toUpperCase() : null);
                                        hhppDirMgl.setHhpApart(detalleDireccionEntityRR.getNumeroApartamento() != null
                                                ? detalleDireccionEntityRR.getNumeroApartamento().toUpperCase() : null);
                                        //actualiza hhpp los campos calle, unidad, placa
                                        if (direccionDetalladaMgl.getSubDireccion() != null) {
                                            hhppDirMgl.setSubDireccionObj(direccionDetalladaMgl.getSubDireccion());
                                            hhppDirMgl.setDireccionObj(direccionDetalladaMgl.getDireccion());
                                        } else {
                                            if (direccionDetalladaMgl.getDireccion() != null) {
                                                hhppDirMgl.setSubDireccionObj(null);
                                                hhppDirMgl.setDireccionObj(direccionDetalladaMgl.getDireccion());
                                            }
                                        }
                                     //cardenaslb
                                    if (hhppMgl != null && hhppMgl.getThhId() != null && !hhppMgl.getThhId().isEmpty()) {
                                        hhppDirMgl.setThhId(hhppMgl.getThhId());
                                        hhppDirMgl.setHhpTipoUnidad(hhppMgl.getThhId());
                                    }
                                        hhppMglManager.updateHhppMgl(hhppDirMgl, usuario, perfil);
                                        LOGGER.error("Hhpp actualizado de Direccion. idHhpp: " + hhppDirMgl.getHhpId());
                                    }
                                } else {
                                    throw new ApplicationException("Error al intentar cargar el listado de Hhpp de "
                                            + "direccion al actualizar el cambio de apto");
                                }
                            }
                        }
                    } else {
                        throw new ApplicationException("NO fue posible encontrar la direccion detallada, el cambio de direccion no fue posible realizarse");
                    }
                }
            } else {
                throw new ApplicationException("Ocurrio un error consultando la dirección detallada de una de las unidades asociadas al predio. "
                        + "No se realizó el cambio de apto a dicha unidad.");
            }
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Error la intentar hacer cambios de dirección de apto de las unidades asociadas al predio '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  e);
        }
    }



    /**
     * Buscar DrDireccion seg&uacute;n el par&aacute;metro. Se busca la
     * DrDireccion del Hhpp.
     *
     * @author becerraarmr
     * @param hhppMgl hhpp para realizar la busqueda.
     * @return null si no enonctr&oacute; una DrDireccion v&aacute;lida
     * @throws ApplicationException is hay alg&uacute;n
     * inconveniente al buscar en la base de datos o si la respuesta es nula.
     */
    public CmtDireccionDetalladaMgl findByHhPP(HhppMgl hhppMgl) throws ApplicationException {
        if (hhppMgl == null || hhppMgl.getDireccionObj() == null) {
            return null;
        }
        CmtDireccionDetalleMglDaoImpl dao = new CmtDireccionDetalleMglDaoImpl();
        CmtDireccionDetalladaMgl dirDetallada = dao.find(hhppMgl);
        if (dirDetallada == null) {
            throw new ApplicationException("No se encontró direcció del Hhpp "
                    + hhppMgl.getHhpId());
        }
        return dirDetallada;
    }

    /**
     * Victor Bocanegra Metodo para crear una direccion y/o subdireccion
     * detallada
     *
     * @param idDireccion
     * @param drDireccion
     * @return true o false si la operacion es exitosa
     */
    public CmtDireccionDetalladaMgl guardarDireccionDetalleXSolicitud(String idDireccion, DrDireccion drDireccion) throws ApplicationException {
        String tipoDireccion = idDireccion.substring(0, 1);
        CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl;
        CmtDireccionDetalladaMgl cmtDireccionDetalladaMglSubDir;
        DrDireccionManager drDireccionMg = new DrDireccionManager();
        DireccionSubDireccionDto dirSubDir;
        SubDireccionMgl subDireccionMgl;
        CmtDireccionDetalladaMgl respuesta = null;
        if (tipoDireccion.equals("d")) {
            BigDecimal direccionId = new BigDecimal(idDireccion.replace("d", "").trim());
            cmtDireccionDetalladaMgl = parseDrDireccionToCmtDireccionDetalladaMgl(drDireccion);
            DireccionMgl direccionMgl = new DireccionMgl();
            direccionMgl.setDirId(direccionId);
            cmtDireccionDetalladaMgl.setDireccion(direccionMgl);
            String codigo = String.format("%015d%s", cmtDireccionDetalladaMgl.getDireccion().getDirId().intValue(), CEROS);
            dirSubDir = drDireccionMg.getDireccionSubDireccion(drDireccion);
            CmtDireccionDetalleMglDaoImpl direccionDetalle = new CmtDireccionDetalleMglDaoImpl();
            CmtDireccionDetalladaMgl buscarCod = direccionDetalle.buscarDireccion(codigo);
            if (buscarCod == null) {
                if (dirSubDir != null) {
                    cmtDireccionDetalladaMgl.setDireccionTexto(dirSubDir.getDireccion());
                }
                cmtDireccionDetalladaMgl.setDireccionIx(codigo);
                cmtDireccionDetalladaMgl.setIdDirCatastro(idDireccion != null ? idDireccion : null);
                cmtDireccionDetalladaMgl.setEstadoDirGeo(drDireccion.getEstadoDirGeo() != null ? drDireccion.getEstadoDirGeo() : null);
                //crea direccionDetalle como direccion
                cmtDireccionDetalladaMgl.setFechaCreacion(new Date());
                cmtDireccionDetalladaMgl.setUsuarioCreacion(drDireccion.getUsuarioCreacion());
                cmtDireccionDetalladaMgl.setPerfilCreacion(Long.valueOf((long)drDireccion.getPerfilCreacion()));
                respuesta = direccionDetalle.crearDireccion(cmtDireccionDetalladaMgl);
            } else {
                //JDHT si encuentra una dirección y no tiene complementos y la que se desea crear si tiene la actualiza
                if((buscarCod.getCpTipoNivel5() == null || buscarCod.getCpTipoNivel5().isEmpty())
                        && drDireccion.getCpTipoNivel5() != null && !drDireccion.getCpTipoNivel5().isEmpty()){
                    buscarCod.setCpTipoNivel5(drDireccion.getCpTipoNivel5());
                    update(buscarCod, "MER", 1 );
                }               
                
                respuesta = buscarCod;
                LOGGER.info("No se crea la direccion detallada;  porque ya existe la  direccionIx:" + " " + buscarCod.getDireccionIx());
            }
        } else {
            try {
                BigDecimal idSubDireccion = new BigDecimal(idDireccion.replace("s", "").trim());
                ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
                SubDireccion subDirAuditoria = manager.querySubAddressOnRepositoryById(idSubDireccion);
                cmtDireccionDetalladaMgl = parseDrDireccionToCmtDireccionDetalladaMglSubDir(drDireccion);
                DireccionMgl direccionMgl = new DireccionMgl();
                direccionMgl.setDirId(subDirAuditoria.getDireccion().getDirId());
                cmtDireccionDetalladaMgl.setDireccion(direccionMgl);
                
                /*author Juan David Hernandez*/
                String codigo = String.format("%015d%s", cmtDireccionDetalladaMgl.getDireccion().getDirId().intValue(), CEROS);
                String auxCodigo = codigo.replace(CEROS, "").trim();
                String codigoSubDir = auxCodigo + String.format("%015d", subDirAuditoria.getSdiId().intValue());
                dirSubDir = drDireccionMg.getDireccionSubDireccion(drDireccion);
                CmtDireccionDetalleMglDaoImpl direccionDetalle = new CmtDireccionDetalleMglDaoImpl();
                CmtDireccionDetalladaMgl buscarCod = direccionDetalle.buscarDireccion(codigoSubDir);
                if (buscarCod == null) {
                    cmtDireccionDetalladaMgl.setDireccionTexto(dirSubDir.getDireccion());
                    cmtDireccionDetalladaMgl.setDireccionIx(codigo);
                    subDireccionMgl = new SubDireccionMgl();
                    subDireccionMgl.setSdiId(idSubDireccion);
                    cmtDireccionDetalladaMglSubDir = parseDrDireccionToCmtDireccionDetalladaMglSubDir(drDireccion);
                    cmtDireccionDetalladaMglSubDir.setDireccionIx(codigo);
                    cmtDireccionDetalladaMglSubDir.setDireccion(cmtDireccionDetalladaMgl.getDireccion());
                    cmtDireccionDetalladaMglSubDir.setSubDireccion(subDireccionMgl);
                    cmtDireccionDetalladaMglSubDir.setDireccionTexto(dirSubDir.getDireccion() + " " + dirSubDir.getSubDireccion());
                    String auxCod = cmtDireccionDetalladaMglSubDir.getDireccionIx().replace(CEROS, "").trim();
                    String codigoSub = auxCod + String.format("%015d", cmtDireccionDetalladaMglSubDir.getSubDireccion().getSdiId().intValue());
                    cmtDireccionDetalladaMglSubDir.setDireccionIx(codigoSub);
                    CmtDireccionDetalleMglDaoImpl subDireccionDetalle = new CmtDireccionDetalleMglDaoImpl();
                    CmtDireccionDetalladaMgl buscarSubCod = subDireccionDetalle.buscarDireccion(codigoSub);
                    if (buscarSubCod == null) {
                        //crea direccionDetalle como subdireccion
                        cmtDireccionDetalladaMglSubDir.setDireccionDetalladaId(null);
                        cmtDireccionDetalladaMglSubDir.setIdDirCatastro(idDireccion != null ? idDireccion : null);
                        cmtDireccionDetalladaMglSubDir.setEstadoDirGeo(drDireccion.getEstadoDirGeo() != null ? drDireccion.getEstadoDirGeo() : null);
                        cmtDireccionDetalladaMglSubDir.setFechaCreacion(new Date());
                        cmtDireccionDetalladaMglSubDir.setUsuarioCreacion(drDireccion.getUsuarioCreacion());
                        cmtDireccionDetalladaMglSubDir.setPerfilCreacion(Long.valueOf((long)drDireccion.getPerfilCreacion()));
                        cmtDireccionDetalladaMglSubDir = subDireccionDetalle.crearDireccion(cmtDireccionDetalladaMglSubDir);
                    }
                    respuesta = cmtDireccionDetalladaMglSubDir;
                } else {
                    respuesta = buscarCod;
                    LOGGER.error("No se crea la  sub direccion detallada ya existe un registro con direccionIx:" + " " + buscarCod.getDireccionIx());
                }
            } catch (ApplicationException ex) {
                LOGGER.error("Error al momento de consultar la subdireccion " + ex.getMessage(), ex);
                return null;
            } catch (Exception ex) {
                LOGGER.error("Error al momento de consultar la subdireccion " + ex.getMessage(), ex);
                return null;
            }
        }
        return respuesta;
    }

    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByCoordenadas(double latitudRestada,
            double longitudRestada, double latitudAumentada, double longitudAumentada, String codigoDane)
            throws ApplicationException {
        try {
            return cmtDireccionDetalleMglDaoImpl.findDireccionDetalladaByCoordenadas(latitudRestada, longitudRestada,
                    latitudAumentada, longitudAumentada, codigoDane);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    
    /**
     * Realiza la consulta del listado de <b>HHPP</b> por <i>Coordenadas</i>, de
     * acuerdo a un rango de <i>Desviaci&oacute;n</i>.
     *
     * @param coordenadasDto DTO que contiene la informaci&oacute;n de las
     * Coordenadas y su Desviaci&oacute;n.
     * @return Listado de entidad Direcci&oacute;n Detallada.
     * @throws ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByCoordenadas(CmtRequestHhppByCoordinatesDto coordenadasDto)
            throws ApplicationException {
        List<CmtDireccionDetalladaMgl> direccionDetalladaCercanaList = null;

        try {

            //validacion codigo dane obligatorio
            if (coordenadasDto == null || coordenadasDto.getCiudad() == null
                    || coordenadasDto.getCiudad().isEmpty()) {
                throw new ApplicationException("Debe ingresar el codigo dane de la ciudad por favor.");
            } else {
                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                GeograficoPoliticoMgl ciudadGpo = geograficoPoliticoManager.findCiudadCodDane(coordenadasDto.getCiudad());

                if (ciudadGpo == null) {
                    throw new ApplicationException(Constant.MSG_DIR_CODIGO_DANE_ERROR);
                }
            }
            //validación metros desviacion
            if (coordenadasDto.getDeviationMtr() == 0) {
                throw new ApplicationException("Debe ingresar el numero de metros de desviacion.");
            }

            //validación latitud
            if (coordenadasDto.getLatitude() == null || coordenadasDto.getLatitude().isEmpty()) {
                throw new ApplicationException("Debe ingresar la latitud.");
            } else {
                if (coordenadasDto.getLatitude().contains(",")) {
                    throw new ApplicationException("La latitud debe ser ingresada con Punto '.' y no con Coma ','");
                }
                if (!isNumeric(coordenadasDto.getLatitude().trim())) {
                    throw new ApplicationException("La latitud debe ser numerica, no debe ingresar letras por favor.");
                }
            }
            //validación latitud
            if (coordenadasDto.getLongitude() == null || coordenadasDto.getLongitude().isEmpty()) {
                throw new ApplicationException("Debe ingresar la longitud.");
            } else {
                if (coordenadasDto.getLongitude().contains(",")) {
                    throw new ApplicationException("La longitud debe ser ingresada con Punto '.' y no con Coma ','");
                }
                if (!isNumeric(coordenadasDto.getLongitude().trim())) {
                    throw new ApplicationException("La longitud debe ser numerica, no debe ingresar letras por favor.");
                }
            }

            if (coordenadasDto.getUnitsNumber() == 0) {
                throw new ApplicationException("Debe ingresar el numero de unidades que desea obtener de la busqueda.");
            }

            CmtDireccionDetalleMglManager direccionDetalleMglManager = new CmtDireccionDetalleMglManager();

            double latitudIngresada = Double.parseDouble(redondearCoordenada(coordenadasDto.getLatitude()));
            double longitudIngresada = Double.parseDouble(redondearCoordenada(coordenadasDto.getLongitude()));

            double latitudAumentadamts = latitudIngresada + (Constant.DISTANCIA_1_METRO_COORDENADA * coordenadasDto.getDeviationMtr());
            double longitudAumentadamts = longitudIngresada + (Constant.DISTANCIA_1_METRO_COORDENADA * coordenadasDto.getDeviationMtr());

            double latitudRestadaMts = latitudIngresada - (Constant.DISTANCIA_1_METRO_COORDENADA * coordenadasDto.getDeviationMtr());
            double longitudRestadaMts = longitudIngresada - (Constant.DISTANCIA_1_METRO_COORDENADA * coordenadasDto.getDeviationMtr());

            List<CmtDireccionDetalladaMgl> direccionDetalladaList = direccionDetalleMglManager
                    .findDireccionDetalladaByCoordenadas(latitudRestadaMts, longitudRestadaMts,
                            latitudAumentadamts, longitudAumentadamts,
                            coordenadasDto.getCiudad());

            if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                direccionDetalladaCercanaList = new ArrayList();

                for (CmtDireccionDetalladaMgl direccionDetallada : direccionDetalladaList) {

                    if (direccionDetallada.getDireccion() != null
                            && direccionDetallada.getDireccion().getUbicacion() != null
                            && direccionDetallada.getDireccion().getUbicacion().getUbiLatitud() != null
                            && direccionDetallada.getDireccion().getUbicacion().getUbiLongitud() != null) {

                        //valida el numero de unidades que desea recibir el usuario
                        if (direccionDetalladaCercanaList.size() < coordenadasDto.getUnitsNumber()) {
                            direccionDetalladaCercanaList.add(direccionDetallada);
                        } else {
                            break;
                        }
                    }
                }

                if (direccionDetalladaCercanaList.isEmpty()) {
                    throw new ApplicationException("No se encontraron direcciones de hhpp con desviacion de "
                            + coordenadasDto.getDeviationMtr() + " metros a su alrededor");
                }

            } else {
                throw new ApplicationException("No se encontraron direcciones con la informacion ingresada");
            }

        } catch (ApplicationException | NumberFormatException e) {
            String msg = "Error al realizar la busqueda de"
                    + " direcciones por coordenada '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }

        return (direccionDetalladaCercanaList);
    }
    
    
     /**
     * Obtiene listado de HHPP por coordenada a un rango de desviaci&oacute;n.
     *
     * @author Juan David Hernandez
     * @param coordenadasDto DTO que contiene la informaci&oacute;n de las 
     * Coordenadas y su Desviaci&oacute;n.
     * @return DTO de Respuesta de Direcci&oacute;n General.
     * @throws ApplicationException
     */
    public CmtAddressGeneralResponseDto findDireccionByCoordenadas(CmtRequestHhppByCoordinatesDto coordenadasDto) throws ApplicationException {
        CmtAddressGeneralResponseDto respuesta = new CmtAddressGeneralResponseDto();
        try {
            List<CmtDireccionDetalladaMgl> direccionDetalladaCercanaList
                    = this.findDireccionDetalladaByCoordenadas(coordenadasDto);

            if (direccionDetalladaCercanaList != null && !direccionDetalladaCercanaList.isEmpty()) {
                CmtDireccionDetalleMglManager direccionDetalleMglManager = new CmtDireccionDetalleMglManager();
                respuesta.setListAddresses(direccionDetalleMglManager.parseCmtDireccionDetalladaMglToCmtAddressGeneralDto(direccionDetalladaCercanaList));
                GeograficoPoliticoManager geo = new GeograficoPoliticoManager();
                GeograficoPoliticoMgl centroPoblado = geo.findCityByCodDaneCp(coordenadasDto.getCiudad());
                respuesta.setCentroPoblado(centroPoblado.getGpoNombre());
                respuesta.setIdCentroPoblado(centroPoblado.getGpoId().toString());
                respuesta.setMessage("Direccion(es) encontrada(s) a una desviacion de "
                        + coordenadasDto.getDeviationMtr() + " metros a su alrededor");
                respuesta.setMessageType("I");
            } else {
                respuesta.setMessage("No se encontraron direcciones de hhpp con desviacion de "
                        + coordenadasDto.getDeviationMtr() + " metros a su alrededor");
                respuesta.setMessageType("E");
            }

        } catch (NumberFormatException e) {
            respuesta.setMessage("Error al realizar la busqueda de"
                    + " direcciones por coordenada debido a :" + e.getMessage());
            respuesta.setMessageType("E");

            String msg = "Error al realizar la busqueda de"
                    + " direcciones por coordenada '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);

        } catch (ApplicationException e) {
            String msg = e.getMessage();
            respuesta.setMessage(msg);
            respuesta.setMessageType("E");
            LOGGER.error(msg);
        }

        return (respuesta);
    }
    

    /**
     * obtiene la distancia en metros entre 2 coordenadas geograficas
     *
     * @author Juan David Hernandez
     * @param longitud_2
     * @param latitud_2
     * @return distancia en metros
     *
     */
    public double calcularDistanciaCoordenadasMtrs(double latitud_1, double longitud_1,
            double latitud_2, double longitud_2) {

        double EARTH_RADIUS = 6371; // radio de la tierra en KM.
        double dLat = toRadians(latitud_2 - latitud_1);
        double dLon = toRadians(longitud_2 - longitud_1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(toRadians(latitud_1)) * Math.cos(toRadians(latitud_2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    public double toRadians(double degrees) {
        return degrees * (Math.PI / 180);
    }

    /**
     * Función que corta una coordenada y le quita los últimos 3 digitos.En
 caso de que falten digitos a la coordenada es rellenada con ceros.
     *
     * @author Juan David Hernandez
     * @param coordenadaOriginal
     * @return 
     */
    public String cortarCoordenada(String coordenadaOriginal) {
        try {

            if (coordenadaOriginal != null && !coordenadaOriginal.equalsIgnoreCase("0")) {
                // tamaño de la coordenada antes del punto
                int tamañoCordenadaAntesPunto = coordenadaOriginal.lastIndexOf(".");
                // valor de la coordenada despues del punto, agregar 1 para omitir el punto
                String coordenadaPostPunto = coordenadaOriginal.substring(tamañoCordenadaAntesPunto + 1);
                // tamaño de la coordenada despues del punto que debe ser 8
                int tamañoCoordenada = coordenadaPostPunto.length();
                //se igualan las coordenada para agregarle ceros a la derecha en caso de que falten
                String coordenadaCortada = coordenadaOriginal;
                //Se realiza resta para conocer cuantos punto hay que agregar
                int cantidadCerosAgregar = 8 - tamañoCoordenada;
                //se realiza for para agregar los ceros faltantes para completa 8
                if (cantidadCerosAgregar != 0) {
                    for (int i = 0; i < cantidadCerosAgregar; i++) {
                        coordenadaCortada += "0";
                    }
                }
                /* se obtiene el index de la coordenada despues de rellenarla de ceros (si fue necesario)
                 de quitar 3 digitos que es como se va a cortar la coordenada*/
                int indexCoordenadaCortada = coordenadaCortada.length() - 3;
                return coordenadaCortada.substring(0, indexCoordenadaCortada);
            }
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
    /**
     * Función que corta una coordenada y le quita los últimos 3 digitos.En
 caso de que falten digitos a la coordenada es rellenada con ceros.
     *
     * @author Juan David Hernandez
     * @param coordenadaOriginal
     * @return 
     */
    public String redondearCoordenada(String coordenadaOriginal) {
        try {

            if (coordenadaOriginal != null && !coordenadaOriginal.equalsIgnoreCase("0")) {
                // tamaño de la coordenada antes del punto
                int tamañoCordenadaAntesPunto = coordenadaOriginal.lastIndexOf(".");
                // valor de la coordenada despues del punto, agregar 1 para omitir el punto
                String coordenadaPostPunto = coordenadaOriginal.substring(tamañoCordenadaAntesPunto + 1);
                // tamaño de la coordenada despues del punto que debe ser 8
                int tamañoCoordenada = coordenadaPostPunto.length();
                //se igualan las coordenada para agregarle ceros a la derecha en caso de que falten
                String coordenadaRedondeada = coordenadaOriginal;
                //Se realiza resta para conocer cuantos punto hay que agregar
                int cantidadCerosAgregar = 8 - tamañoCoordenada;
                //se realiza for para agregar los ceros faltantes para completa 8
                if (cantidadCerosAgregar != 0) {
                    for (int i = 0; i < cantidadCerosAgregar; i++) {
                        coordenadaRedondeada += "0";
                    }
                }

                return coordenadaRedondeada;
            }
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
             /**
     * Función para validar si el dato recibido es númerico
     * 
     * @author Juan David Hernandez}
     * return true si el dato es úumerico
     * @param cadena
     * @return 
     */
    public boolean isNumeric(String cadena) {
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            return false;
        }
    }
    
    public CmtDireccionDetalladaMgl findById(BigDecimal idDireccionDetallada) throws ApplicationException {
        return cmtDireccionDetalleMglDaoImpl.findById(idDireccionDetallada);
    }  

    private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
    
        
      /**
     * Función que encuentra una dirección por la columna direccion texto, la direccion 
     * debe ser la que entrega el geo  si es CK o si es Bm o IT la que es estandarizada
     * 
     * @param direccionTexto
     * @param ciudadGpo
     *@return direcciones detallas que cumplan el criterio de busqueda
     * 
     *@author Juan David Hernandez
     * @throws ApplicationException
     */
     public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByDireccionTexto(String direccionTexto,
            BigDecimal ciudadGpo, boolean resultadoUnico, String barrio, String tipoDireccion) throws ApplicationException {
        return cmtDireccionDetalleMglDaoImpl.findDireccionDetalladaByDireccionTexto(direccionTexto,ciudadGpo,
                resultadoUnico, barrio,tipoDireccion);
    }

    /**
     * Obtiene listado de direcciones detalladas apartir de una direccion texto con
     * complementos en los niveles del DrDireccion con ids de direcciones hasta la placa
     *
     * @param idDireccionDetalladaList
     * @param centroPobladoId
     * @param direccionTabulada
     * @return direcciones detallas que cumplan el criterio de busqueda
     * @throws ApplicationException
     * @author Juan David Hernandez
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaNivelesComplementosById(List<BigDecimal> idDireccionDetalladaList,
            BigDecimal centroPobladoId, DrDireccion direccionTabulada) throws ApplicationException {
        return cmtDireccionDetalleMglDaoImpl.buscarDireccionDetalladaNivelesComplementosById(idDireccionDetalladaList, centroPobladoId, direccionTabulada);
    }
    
          /**
     * Obtiene listado de direcciones detalladas apartir de una direccion texto con
     * complementos en los niveles del DrDireccion
     * 
     * @param direccionTexto
     * @param direccionTabulada
     * @param centroPobladoId
     *@return direcciones detallas que cumplan el criterio de busqueda
     * 
     *@author Juan David Hernandez
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaNivelesComplementosByDireccionTexto(BigDecimal centroPobladoId, DrDireccion direccionTabulada, 
            String direccionTexto) throws ApplicationException {
        return cmtDireccionDetalleMglDaoImpl.buscarDireccionDetalladaNivelesComplementosByDireccionTexto
        (centroPobladoId, direccionTabulada, direccionTexto);
    }
    
    
      /**
     * Obtiene listado de direcciones detalladas apartir de una direccion texto.
     * 
     * @param direccionTexto
     * @param direccionTabulada
     * @param centroPobladoId
     *@return direcciones detallas que cumplan el criterio de busqueda
     * 
     *@author Juan David Hernandez
     */
    public List<CmtDireccionDetalladaMgl> busquedaDireccionTextoRespuestaGeo(String direccionTexto,
            DrDireccion direccionTabulada, BigDecimal centroPobladoId) throws ApplicationException{
        try {
            List<CmtDireccionDetalladaMgl> direccionDetalladaTextoList = new ArrayList();
            //busqueda por direccion texto como ultima instancia
            if (direccionTabulada != null && direccionTexto != null && !direccionTexto.isEmpty()
                    && centroPobladoId != null && !centroPobladoId.equals(BigDecimal.ZERO)) {

                //Si en la construccion de la direccion ingresan niveles de complementos 
                if ((direccionTabulada.getCpTipoNivel5() != null
                        && !direccionTabulada.getCpTipoNivel5().isEmpty())
                        || ((direccionTabulada.getCpTipoNivel1() != null
                        && !direccionTabulada.getCpTipoNivel1().isEmpty())
                        || (direccionTabulada.getCpTipoNivel2() != null
                        && !direccionTabulada.getCpTipoNivel2().isEmpty())
                        || (direccionTabulada.getCpTipoNivel3() != null
                        && !direccionTabulada.getCpTipoNivel3().isEmpty())
                        || (direccionTabulada.getCpTipoNivel4() != null
                        && !direccionTabulada.getCpTipoNivel4().isEmpty()))) {

                    direccionDetalladaTextoList
                            = buscarDireccionDetalladaNivelesComplementosByDireccionTexto(centroPobladoId,
                                    direccionTabulada, direccionTexto);
                } else {
                    String barrio = "";
                    if(direccionTabulada.getBarrio() != null && !direccionTabulada.getBarrio().isEmpty()){
                        barrio = direccionTabulada.getBarrio();
                    }
                    direccionDetalladaTextoList
                            = findDireccionDetalladaByDireccionTexto(direccionTexto,
                                    centroPobladoId, false, barrio, direccionTabulada.getIdTipoDireccion());

                }
            }
            return direccionDetalladaTextoList;
        } catch (ApplicationException e) {
            LOGGER.error("Error al consulta direccion detallada por texto", e);                 
            return null;
        }
    }

    /**
     * Realiza copia de un listado de direcciondetallada
     *
     * @param direccionDetalladaList
     *
     * @author Juan David Hernandez
     * @return listado resultado de la copia realizada,
     * quien hace el llamado debe validar que no sea null o vacia la lista
     */
    public List<CmtDireccionDetalladaMgl> copiarDireccionDetalladaList(List<CmtDireccionDetalladaMgl> direccionDetalladaList) {
        try {
            List<CmtDireccionDetalladaMgl> direccionDetalladaListResult = new ArrayList();
            if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : direccionDetalladaList) {
                    direccionDetalladaListResult.add(cmtDireccionDetalladaMgl.clone());
                }
            }
            return direccionDetalladaListResult;

        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    
    /**
     * Metodo que fusiona 2 listados de direccion detallada retornando un solo
     * listado sin elementos repetidos
     *
     * @param direccionDetalladaList direcciones tabuladas encontradas
     * @param direccionDetalladaTextoList direcciones texto encontradas
     *
     * @author Juan David Hernandez
     * @return
     */
    public List<CmtDireccionDetalladaMgl> combinarDireccionDetalladaList(List<CmtDireccionDetalladaMgl> direccionDetalladaList,
            List<CmtDireccionDetalladaMgl> direccionDetalladaTextoList) {
        try {
            
            if ((direccionDetalladaList == null || direccionDetalladaList.isEmpty())
                    && (direccionDetalladaTextoList == null || direccionDetalladaTextoList.isEmpty())) {
                return null;
            }

            /*se verifica si direcciones encontradas por texto estan repetidas y las que no se unen al listado 
            final de direcciones detalladas encontradas*/
            if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()
                    && direccionDetalladaTextoList != null && !direccionDetalladaTextoList.isEmpty()) {
                // copia de las direcciones tabuladas encontradas para realizar comparacion y agregar nuevos registros
                List<CmtDireccionDetalladaMgl> direccionDetalladaTmp = copiarDireccionDetalladaList(direccionDetalladaList);

                /*se realiza comparacion de listado de direccion detalladad tabulada encontradas con direcciones detalladas texto encontradas
                y las direcciones que no esten se agregan al listado final de direcciones detalladas*/
                if (direccionDetalladaTmp != null && !direccionDetalladaTmp.isEmpty()) {
                    for (CmtDireccionDetalladaMgl direccionDetalladaTexto : direccionDetalladaTextoList) {
                        boolean direccionExistente = false;
                        for (CmtDireccionDetalladaMgl direccionDetalladaTabuladaTmp : direccionDetalladaTmp) {
                            if (direccionDetalladaTexto.getDireccionDetalladaId().equals(direccionDetalladaTabuladaTmp.getDireccionDetalladaId())) {
                                direccionExistente = true;
                                break;
                            }
                        }
                        if (!direccionExistente) {
                            direccionDetalladaList.add(direccionDetalladaTexto);
                        }
                    }
                    return direccionDetalladaList;
                }

            } else {
                if ((direccionDetalladaList == null || direccionDetalladaList.isEmpty())
                        && (direccionDetalladaTextoList != null && !direccionDetalladaTextoList.isEmpty())) {
                    direccionDetalladaList = new ArrayList();
                    for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : direccionDetalladaTextoList) {
                        direccionDetalladaList.add(cmtDireccionDetalladaMgl.clone());
                    }
                    return direccionDetalladaList;

                } else {
                  if ((direccionDetalladaList == null || direccionDetalladaList.isEmpty())
                        && (direccionDetalladaTextoList == null || direccionDetalladaTextoList.isEmpty())) {
                      return null;
                  }else{
                       return direccionDetalladaList;
                  }
                   
                }
            }

        } catch (CloneNotSupportedException e) {
            return null;
        }
        return null;
    }
    
    
        /**
     * Obtiene listado de direccion detallada por busqueda parcial o completa
     *
     * @param direccionTabulada
     * @param centroPobladoId centro poblado por la que se desea filtrar
     *
     * @return listado de hhpp asociados a la dirección buscada
     *
     * @author Juan David Hernandez
     * @throws ApplicationException
     */
    public List <CmtDireccionDetalladaMgl> buscarDireccionTabuladaCompletaParcial(DrDireccion direccionTabulada,
            BigDecimal centroPobladoId, int numeroMaximoResults) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();        
        return cmtDireccionDetalleMglDao.buscarDireccionTabulada(centroPobladoId, direccionTabulada, true, 1, numeroMaximoResults);
    }
    
    
     /* Obtiene listado direcciones consultada por barrio
     *
     * @param centroPobladoId la dirección construida
     * @param barrio ciudad por la que se desea filtrar     
     *
     * @author Juan David Hernandez
     * @return 
     */    
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaByBarrioNodo(BigDecimal centroPobladoId, 
            String barrio, String tipoDireccion, String codigoNodo) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();        
        return cmtDireccionDetalleMglDao.buscarDireccionDetalladaByBarrioNodo(centroPobladoId, barrio,tipoDireccion, codigoNodo);
    }


    /**
     * Obtiene listado CmtDireccionDetalladaMgl por dirección
     *
     * @param centroPobladoId
     * @param direccionTabulada
     * @author bocanegravm
     * @return
     */
    public List<CmtDireccionDetalladaMgl> buscarDireccionTabuladaUnica(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada) {

        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();
        return cmtDireccionDetalleMglDao.buscarDireccionTabuladaUnica(centroPobladoId, direccionTabulada);

    }
    
    /**
     *  Obtiene datos del Geo por dirección para
     *  validar creacion automatica de Hhpp por visor
     *
     * @param direccion
     * @param centroPoblado
     * @author Johan Gomez
     * @return AddressService
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public AddressService consultarGeoDataVisor(DireccionMgl direccion,
            GeograficoPoliticoMgl centroPoblado) throws ApplicationException {
                
        ConsultaNodoSitiData consultaNodoSitiData = new ConsultaNodoSitiData();
        AddressService responseSitiData = consultaNodoSitiData.findNodosSitiData(drDireccionConsultaSitiData, centroPoblado);
        return responseSitiData;
    }

    
    /**
     * Obtiene listado de coberturas por dirección
     *
     * @param direccion
     * @param subDireccionMgl
     * @author bocanegravm
     * @return List<CmtCoverDto>
     */
    public List<CmtCoverDto> coberturasDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccionMgl) throws ApplicationException {

        List<CmtCoverDto> listCover = new ArrayList<>();

        NodoMglManager mglManager = new NodoMglManager();
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        CmtTipoBasicaMglManager tipoBasicaMglManager = new CmtTipoBasicaMglManager();
        List<NodoMgl> listaNodosDth = new ArrayList<>();

        CmtTipoBasicaMgl cmtTipoBasicaTec = tipoBasicaMglManager.findByCodigoInternoApp(
                Constant.TIPO_BASICA_TECNOLOGIA);
        CmtTipoBasicaExtMgl tipoBasicaExtMglVC = null;

        if (cmtTipoBasicaTec != null
                && !cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl().isEmpty()) {
            for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl
                    : cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl()) {
                if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().equalsIgnoreCase(Constant.TECNOLOGIA_MANEJA_NODOS)) {
                    tipoBasicaExtMglVC = cmtTipoBasicaExtMgl;
                }
            }

        }

        GeograficoPoliticoMgl cityGpo = direccion.getUbicacion() != null ? direccion.getUbicacion().getGpoIdObj() : null;
        /* Brief 98062 */
        /* Ejecuta la consulta a sitidata*/
        GeograficoPoliticoMgl ubicacion = null;
        try {
            if (cityGpo != null) {
                ubicacion = cityGpo.clone();
            }
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Error al clonar la ubicacion ");
        }
        ConsultaNodoSitiData consultaNodoSitiData = new ConsultaNodoSitiData();
        AddressService responseSitiData = consultaNodoSitiData.findNodosSitiData(drDireccionConsultaSitiData, ubicacion);
        /* Cierre Brief 98062  */
        if (subDireccionMgl != null) {
            /* ------ Cobertura Nodo BI ------- */
            //si trae información de nodo uno (BI)
            if (subDireccionMgl.getSdiNodouno() != null && !subDireccionMgl.getSdiNodouno().equals("")
                    && !subDireccionMgl.getSdiNodouno().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_UNO.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getSdiNodouno());
                    NodoMgl nodeMgl = mglManager.findByCodigo(subDireccionMgl.getSdiNodouno());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_UNO.getCodigo());
                    cover.setColorTecno("yellow");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoUno());// Nodo BI SITIDATA

                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else {//Cuando no trae información de nodo Uno (BI) desde BD
                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_UNO.getCodigo());
                }
                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_UNO.getCodigo());
                    cover.setColorTecno("yellow");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoUno());//Nodo BI SITIDATA

                    listCover.add(cover);
                }
            }

            /* -------- Cobertura Nodo UNI -------- */
            //Si trae la información de nodo dos (UNI)

            if (subDireccionMgl.getSdiNododos() != null && !subDireccionMgl.getSdiNododos().equals("")
                    && !subDireccionMgl.getSdiNododos().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_DOS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getSdiNododos());
                    NodoMgl nodeMgl = mglManager.findByCodigo(subDireccionMgl.getSdiNododos());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_DOS.getCodigo());
                    cover.setColorTecno("blue");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoDos());//Nodo UNI SITIDATA
                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else {  //Cuando no trae info de nodo dos (UNI)
                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_DOS.getCodigo());
                }
                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_DOS.getCodigo());
                    cover.setColorTecno("blue");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoDos());//Nodo UNI SITIDATA
                    listCover.add(cover);
                }
            }
            /* -------- Cobertura Nodo FOG --------- */
            if (subDireccionMgl.getSdiNodotres() != null && !subDireccionMgl.getSdiNodotres().equals("")
                    && !subDireccionMgl.getSdiNodotres().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_TRES.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getSdiNodotres());
                    NodoMgl nodeMgl = mglManager.findByCodigo(subDireccionMgl.getSdiNodotres());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_TRES.getCodigo());
                    cover.setColorTecno("red");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoTres());//Nodo FOG SITIDATA
                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else {//Cuando trae info de nodo tres (FOG)
                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_TRES.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_TRES.getCodigo());
                    cover.setColorTecno("red");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoTres());//Nodo FOG SITIDATA
                    listCover.add(cover);
                }
            }
            /* ---------- Cobertura Nodo DTH ------------ */
            if (subDireccionMgl.getSdiNodoDth() != null && !subDireccionMgl.getSdiNodoDth().equals("")
                    && !subDireccionMgl.getSdiNodoDth().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_DTH.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getSdiNodoDth());
                    NodoMgl nodeMgl = mglManager.findByCodigo(subDireccionMgl.getSdiNodoDth());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_DTH.getCodigo());
                    cover.setColorTecno("green");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoDth());//Nodo DTH SITIDATA

                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo tres (DTH)
                //@cardenaslb 
                //devolver lista de nodos DTH de la Tabla Tec_Nodo  cuando el Geo no arroje info para esta tecnologia
                CmtBasicaMgl cmtBasicaMglTecnologia;
                CmtBasicaMgl cmtBasicaMglEstadoNodo;
                cmtBasicaMglTecnologia = cmtBasicaMglManager.findByCodigoInternoApp(Constant.DTH);
                cmtBasicaMglEstadoNodo = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_CERTIFICADO);

                if (cityGpo != null) {
                    listaNodosDth = mglManager.findNodosCiudadAndTecnoAndEstado(cityGpo.getGpoId(), cmtBasicaMglTecnologia, cmtBasicaMglEstadoNodo);
                }

                for (NodoMgl nododth : listaNodosDth) {
                    CmtBasicaMgl tecnologia = null;
                    if (cmtTipoBasicaTec != null) {
                        tecnologia = cmtBasicaMglManager.
                                findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_DTH.getCodigo());
                    }

                    if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                        CmtCoverDto coverDth = new CmtCoverDto();
                        coverDth.setNode(nododth.getNodCodigo());
                        coverDth.setTechnology(CmtCoverEnum.NODO_DTH.getCodigo());
                        coverDth.setColorTecno("green");
                        coverDth.setValidaCobertura(true);
                        coverDth.setNodoSitiData(responseSitiData.getNodoDth());//Nodo DTH SITIDATA
                        String estadoNodo = null;
                        coverDth.setQualificationDate(dateToString(nododth.getNodFechaActivacion()));
                        if (nododth.getEstado() != null
                                && nododth.getEstado().getAbreviatura() != null
                                && nododth.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nododth.getEstado() != null
                                && nododth.getEstado().getAbreviatura() != null
                                && nododth.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        coverDth.setState(estadoNodo);
                        listCover.add(coverDth);
                    }
                }

                /* Brief 98062 */
                //cuando no hay nodo DTH de la lista de nodos, asigna el nodo de SITIDATA
                if (listaNodosDth.isEmpty()) {
                    CmtCoverDto coverDth = new CmtCoverDto();
                    coverDth.setTechnology(CmtCoverEnum.NODO_DTH.getCodigo());
                    coverDth.setValidaCobertura(true);
                    coverDth.setNodoSitiData(responseSitiData.getNodoDth());//Nodo DTH SITIDATA
                    listCover.add(coverDth);
                }
                /* Cierre Brief 98062*/
            }
            /* ------------ Cobertura Nodo MOV ------------- */
            if (subDireccionMgl.getSdiNodoMovil() != null && !subDireccionMgl.getSdiNodoMovil().equals("")
                    && !subDireccionMgl.getSdiNodoMovil().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_MOVIL.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getSdiNodoMovil());
                    cover.setTechnology(CmtCoverEnum.NODO_MOVIL.getCodigo());
                    cover.setColorTecno("orange");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoMovil());//Nodo MOV SITIDATA
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo Movil (MOV)
                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_MOVIL.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_MOVIL.getCodigo());
                    cover.setColorTecno("orange");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoMovil());//Nodo MOV SITIDATA
                    listCover.add(cover);
                }
            }
            /* ----------- Cobertura Nodo FTTH ----------- */
            if (subDireccionMgl.getSdiNodoFtth() != null && !subDireccionMgl.getSdiNodoFtth().equals("")
                    && !subDireccionMgl.getSdiNodoFtth().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_FTTH.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getSdiNodoFtth());
                    NodoMgl nodeMgl = mglManager.findByCodigo(subDireccionMgl.getSdiNodoFtth());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_FTTH.getCodigo());
                    cover.setColorTecno("brown");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoFtth());//Nodo FTTH SITIDATA
                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo FTTH
                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_FTTH.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_FTTH.getCodigo());
                    cover.setColorTecno("brown");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoFtth());//Nodo FTTH SITIDATA
                    listCover.add(cover);
                }
            }
            /*Brief 118814*/
            /* ----------- Cobertura FTTX -------------- */
            if (subDireccionMgl.getSdiNodoFttx() != null && !subDireccionMgl.getSdiNodoFttx().equals("")
                    && !subDireccionMgl.getSdiNodoFttx().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_FTTX.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getSdiNodoFttx());
                    NodoMgl nodeMgl = mglManager.findByCodigo(subDireccionMgl.getSdiNodoFttx());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_FTTX.getCodigo());
                    cover.setColorTecno("brown");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoCuatro());//Nodo FTTX SITIDATA
                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo FTTX
                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_FTTX.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_FTTX.getCodigo());
                    cover.setColorTecno("brown");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoCuatro());//Nodo FTTX SITIDATA
                    listCover.add(cover);
                }
            }
            
            
            /* ----------- Cobertura Nodo LTE ---------- */
            if (subDireccionMgl.getSdiNodoWifi() != null && !subDireccionMgl.getSdiNodoWifi().equals("")
                    && !subDireccionMgl.getSdiNodoWifi().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_WIFI.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getSdiNodoWifi());
                    cover.setTechnology(CmtCoverEnum.NODO_WIFI.getCodigo());
                    cover.setColorTecno("violet");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoWifi());//Nodo LTE SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo LTE
                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_WIFI.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_WIFI.getCodigo());
                    cover.setColorTecno("violet");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoWifi());//Nodo LTE SITIDATA
                    listCover.add(cover);
                }
            }

            //nuevas coberturas JDHT
            /* ----------- Cobertura Nodo FOU ----------- */
            if (subDireccionMgl.getGeoZonaUnifilar() != null && !subDireccionMgl.getGeoZonaUnifilar().equals("")
                    && !subDireccionMgl.getGeoZonaUnifilar().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_FOU.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getGeoZonaUnifilar());
                    NodoMgl nodeMgl = mglManager.findByCodigo(subDireccionMgl.getGeoZonaUnifilar());
                    cover.setTechnology(CmtCoverEnum.NODO_FOU.getCodigo());
                    cover.setColorTecno("pink");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaUnifilar());//Nodo FOU SITIDATA
                    String estadoNodo = null;
                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo FOU

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_FOU.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_FOU.getCodigo());
                    cover.setColorTecno("pink");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaUnifilar());//Nodo FOU SITIDATA
                    listCover.add(cover);
                }
            }
            /* ----------- Cobertura Nodo GPON ------------ */
            if (subDireccionMgl.getGeoZonaGponDiseniado() != null && !subDireccionMgl.getGeoZonaGponDiseniado().equals("")
                    && !subDireccionMgl.getGeoZonaGponDiseniado().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getGeoZonaGponDiseniado());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo());
                    cover.setColorTecno("turquoise");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaGponDiseniado());//Nodo GPON SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo GPON

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo());
                    cover.setColorTecno("turquoise");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaGponDiseniado());//Nodo GPON SITIDATA
                    listCover.add(cover);
                }
            }
            /* ---------- Cobertura nodo MICRO ONDAS ---------- */
            if (subDireccionMgl.getGeoZonaMicroOndas() != null && !subDireccionMgl.getGeoZonaMicroOndas().equals("")
                    && !subDireccionMgl.getGeoZonaMicroOndas().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getGeoZonaMicroOndas());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo());
                    cover.setColorTecno("fuchsia");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaMicroOndas());//Nodo MICRO ONDAS SITIDATA
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo MICRO ONDAS

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo());
                    cover.setColorTecno("fuchsia");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaMicroOndas());//Nodo MICRO ONDAS SITIDATA
                    listCover.add(cover);
                }
            }
            /* --------- Cobertura Nodo CAVS ---------- */
            if (subDireccionMgl.getGeoZonaCoberturaCavs() != null && !subDireccionMgl.getGeoZonaCoberturaCavs().equals("")
                    && !subDireccionMgl.getGeoZonaCoberturaCavs().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getGeoZonaCoberturaCavs());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo());
                    cover.setColorTecno("gray");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCoberturaCavs());//Nodo CAVS SITIDATA
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo CAVS

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo());
                    cover.setColorTecno("gray");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCoberturaCavs());//Nodo CAVS SITIDATA
                    listCover.add(cover);
                }
            }

            /* ------------ Cobertura Nodo ULTIMA MILLA --------- */
            if (subDireccionMgl.getGeoZonaCoberturaUltimaMilla() != null && !subDireccionMgl.getGeoZonaCoberturaUltimaMilla().equals("")
                    && !subDireccionMgl.getGeoZonaCoberturaUltimaMilla().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getGeoZonaCoberturaUltimaMilla());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo());
                    cover.setColorTecno("black");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCoberturaUltimaMilla());
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo ULTIMA  MILLA

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo());
                    cover.setColorTecno("black");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCoberturaUltimaMilla());// Nodo ULTIMA MILLA SITIDATA
                    listCover.add(cover);
                }
            }

            /* ---------- Cobertura Nodo CURRIER ----------- */
            if (subDireccionMgl.getGeoZonaCurrier() != null && !subDireccionMgl.getGeoZonaCurrier().equals("")
                    && !subDireccionMgl.getGeoZonaCurrier().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getGeoZonaCurrier());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo());
                    cover.setColorTecno("purple");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCurrier());//Nodo CURRIER SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo CURRIER

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo());
                    cover.setColorTecno("purple");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCurrier()); //Nodo CURRIER SITIDATA
                    listCover.add(cover);
                }
            }

            /* --------- Cobertura Nodo 3G ----------*/
            if (subDireccionMgl.getGeoZona3G() != null && !subDireccionMgl.getGeoZona3G().equals("")
                    && !subDireccionMgl.getGeoZona3G().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(), CmtCoverEnum.NODO_ZONA_3G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getGeoZona3G());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_3G.getCodigo());
                    cover.setColorTecno("yellowgreen");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona3G());//Nodo 3G SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo 3G

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_3G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_3G.getCodigo());
                    cover.setColorTecno("yellowgreen");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona3G());//Nodo 3G SITIDATA
                    listCover.add(cover);
                }
            }

            /* ---------- Cobertura Nodo 4G --------- */
            if (subDireccionMgl.getGeoZona4G() != null && !subDireccionMgl.getGeoZona4G().equals("")
                    && !subDireccionMgl.getGeoZona4G().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_4G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getGeoZona4G());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_4G.getCodigo());
                    cover.setColorTecno("salmon");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona4G());//Nodo 4G SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo 4G

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_4G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_4G.getCodigo());
                    cover.setColorTecno("salmon");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona4G());//Nodo 4G SITIDATA
                    listCover.add(cover);
                }
            }

            /* ---------- Cobertura Nodo 5G ----------- */
            if (subDireccionMgl.getGeoZona5G() != null && !subDireccionMgl.getGeoZona5G().equals("")
                    && !subDireccionMgl.getGeoZona5G().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_5G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(subDireccionMgl.getGeoZona5G());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_5G.getCodigo());
                    cover.setColorTecno("indigo");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona5G());//Nodo 5G SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo 5G

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_5G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_5G.getCodigo());
                    cover.setColorTecno("indigo");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona5G());//Nodo 5G SITIDATA
                    listCover.add(cover);
                }
            }

        } else {//cuando no tiene sub dirección
            /* -------- Cobertura Nodo BI ------ */
            if (direccion.getDirNodouno() != null && !direccion.getDirNodouno().equals("")
                    && !direccion.getDirNodouno().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_UNO.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getDirNodouno());
                    NodoMgl nodeMgl = mglManager.findByCodigo(direccion.getDirNodouno());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_UNO.getCodigo());
                    cover.setColorTecno("yellow");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoUno());//Nodo BI SITIDATA

                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else {//Cuando no trae información del nodo BI desde la dirección en BD

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_UNO.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_UNO.getCodigo());
                    cover.setColorTecno("yellow");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoUno());//Nodo BI SITIDATA
                    listCover.add(cover);
                }
            }

            /* ---------- Cobertura Nodo UNI ----------- */
            if (direccion.getDirNododos() != null && !direccion.getDirNododos().equals("")
                    && !direccion.getDirNododos().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_DOS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getDirNododos());
                    NodoMgl nodeMgl = mglManager.findByCodigo(direccion.getDirNododos());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_DOS.getCodigo());
                    cover.setColorTecno("blue");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoDos());//Nodo UNI SITIDATA

                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo dos (UNI)

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_DOS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_DOS.getCodigo());
                    cover.setColorTecno("blue");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoDos());//Nodo UNI SITIDATA
                    listCover.add(cover);
                }
            }

            /* ---------- Cobertura Nodo FOG ---------- */
            if (direccion.getDirNodotres() != null && !direccion.getDirNodotres().equals("")
                    && !direccion.getDirNodotres().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_TRES.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getDirNodotres());
                    NodoMgl nodeMgl = mglManager.findByCodigo(direccion.getDirNodotres());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_TRES.getCodigo());
                    cover.setColorTecno("red");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoTres());//Nodo FOG SITIDATA

                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo FOG

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_TRES.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_TRES.getCodigo());
                    cover.setColorTecno("red");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoTres());//Nodo FOG SITIDATA
                    listCover.add(cover);
                }
            }

            /* ------------ Cobertura Nodo DTH ------------ */
            if (direccion.getDirNodoDth() != null && !direccion.getDirNodoDth().equals("")
                    && !direccion.getDirNodoDth().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_DTH.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getDirNodoDth());
                    NodoMgl nodeMgl = mglManager.findByCodigo(direccion.getDirNodoDth());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_DTH.getCodigo());
                    cover.setColorTecno("green");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoDth());//Nodo DTH SITIDATA

                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else {//Cuando trae info de nodo DTH
                //@cardenaslb 
                //devolver lista de nodos DTH de la Tabla Tec_Nodo  cuando el Geo no arroje info para esta tecnologia
                CmtBasicaMgl cmtBasicaMglTecnologia;
                CmtBasicaMgl cmtBasicaMglEstadoNodo;
                cmtBasicaMglTecnologia = cmtBasicaMglManager.findByCodigoInternoApp(Constant.DTH);
                cmtBasicaMglEstadoNodo = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_CERTIFICADO);

                if (cityGpo != null) {
                    listaNodosDth = mglManager.findNodosCiudadAndTecnoAndEstado(cityGpo.getGpoId(), cmtBasicaMglTecnologia, cmtBasicaMglEstadoNodo);
                }

                for (NodoMgl nododth : listaNodosDth) {

                    CmtBasicaMgl tecnologia = null;
                    if (cmtTipoBasicaTec != null) {
                        tecnologia = cmtBasicaMglManager.
                                findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                        CmtCoverEnum.NODO_DTH.getCodigo());
                    }

                    if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                        CmtCoverDto coverDth = new CmtCoverDto();
                        coverDth.setNode(nododth.getNodCodigo());
                        coverDth.setTechnology(CmtCoverEnum.NODO_DTH.getCodigo());
                        String estadoNodo = null;
                        coverDth.setQualificationDate(dateToString(nododth.getNodFechaActivacion()));
                        coverDth.setColorTecno("green");
                        coverDth.setValidaCobertura(true);
                        coverDth.setNodoSitiData(responseSitiData.getNodoDth());//Nodo DTH SITIDATA

                        if (nododth.getEstado() != null
                                && nododth.getEstado().getAbreviatura() != null
                                && nododth.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nododth.getEstado() != null
                                && nododth.getEstado().getAbreviatura() != null
                                && nododth.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        coverDth.setState(estadoNodo);
                        listCover.add(coverDth);
                    }
                }
            /* Brief 98062 */
            //cuando no hay nodo DTH de la lista de nodos, asigna el nodo de sitidata
            if (listaNodosDth.isEmpty() || listaNodosDth.size() == 0){
                CmtCoverDto coverDth = new CmtCoverDto();
                coverDth.setTechnology(CmtCoverEnum.NODO_DTH.getCodigo());
                coverDth.setNodoSitiData(responseSitiData.getNodoDth());//Nodo DTH SITIDATA
                listCover.add(coverDth);
            }
            /* Cierre Brief 98062*/
        }
        /* ----------- Cobertura Nodo MOV ------------- */

        if (direccion.getDirNodoMovil() != null && !direccion.getDirNodoMovil().equals("")
                    && !direccion.getDirNodoMovil().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_MOVIL.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getDirNodoMovil());
                    cover.setTechnology(CmtCoverEnum.NODO_MOVIL.getCodigo());
                    cover.setColorTecno("orange");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoMovil());//Nodo MOV SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo MOV

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_MOVIL.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_MOVIL.getCodigo());
                    cover.setColorTecno("orange");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoMovil());//Nodo MOV SITIDATA
                    listCover.add(cover);
                }
            }

            /* ---------- Cobertura Nodo FTTH ---------- */
            if (direccion.getDirNodoFtth() != null && !direccion.getDirNodoFtth().equals("")
                    && !direccion.getDirNodoFtth().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_FTTH.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getDirNodoFtth());
                    NodoMgl nodeMgl = mglManager.findByCodigo(direccion.getDirNodoFtth());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_FTTH.getCodigo());
                    cover.setColorTecno("brown");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoFtth());//Nodo FTTH SITIDATA

                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo FTTH

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_FTTH.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_FTTH.getCodigo());
                    cover.setColorTecno("brown");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoFtth());//Nodo FTTH SITIDATA
                    listCover.add(cover);
                }
            }

            /*Brief 118814*/
            /* ---------- Cobertura Nodo FTTX ---------- */
            if (direccion.getDirNodoFttx() != null && !direccion.getDirNodoFttx().equals("")
                    && !direccion.getDirNodoFttx().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_FTTX.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getDirNodoFttx());
                    NodoMgl nodeMgl = mglManager.findByCodigo(direccion.getDirNodoFttx());
                    String estadoNodo = null;
                    cover.setTechnology(CmtCoverEnum.NODO_FTTX.getCodigo());
                    cover.setColorTecno("brown");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoCuatro());//Nodo FTTX SITIDATA

                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo FTTX

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_FTTX.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_FTTX.getCodigo());
                    cover.setColorTecno("brown");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoCuatro());//Nodo FTTX SITIDATA
                    listCover.add(cover);
                }
            }

            /* ----------- Cobertura Nodo LTE ---------- */
            if (direccion.getDirNodoWifi() != null && !direccion.getDirNodoWifi().equals("")
                    && !direccion.getDirNodoWifi().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_WIFI.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getDirNodoWifi());
                    cover.setTechnology(CmtCoverEnum.NODO_WIFI.getCodigo());
                    cover.setColorTecno("violet");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoWifi());//Nodo LTE SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo LTE

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_WIFI.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_WIFI.getCodigo());
                    cover.setColorTecno("violet");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoWifi());//Nodo LTE SITIDATA
                    listCover.add(cover);
                }
            }

            //nuevas coberturas JDHT
            /* ----------- Cobertura Nodo FOU -------- */
            if (direccion.getGeoZonaUnifilar() != null && !direccion.getGeoZonaUnifilar().equals("")
                    && !direccion.getGeoZonaUnifilar().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_FOU.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getGeoZonaUnifilar());
                    NodoMgl nodeMgl = mglManager.findByCodigo(direccion.getGeoZonaUnifilar());
                    cover.setTechnology(CmtCoverEnum.NODO_FOU.getCodigo());
                    cover.setColorTecno("pink");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaUnifilar());//Nodo FOU SITIDATA
                    String estadoNodo = null;
                    if (nodeMgl != null) {
                        cover.setQualificationDate(dateToString(nodeMgl.getNodFechaActivacion()));
                        if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("A")) {
                            estadoNodo = "ACTIVO";
                        } else if (nodeMgl.getEstado() != null
                                && nodeMgl.getEstado().getAbreviatura() != null
                                && nodeMgl.getEstado().getAbreviatura().equalsIgnoreCase("I")) {
                            estadoNodo = "INACTIVO";
                        }
                        cover.setState(estadoNodo);
                    }
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo FOU

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_FOU.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_FOU.getCodigo());
                    cover.setColorTecno("pink");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaUnifilar());//Nodo FOU SITIDATA
                    listCover.add(cover);
                }
            }

            /* ----------- Cobertura Nodo GPON ------------- */
            if (direccion.getGeoZonaGponDiseniado() != null && !direccion.getGeoZonaGponDiseniado().equals("")
                    && !direccion.getGeoZonaGponDiseniado().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getGeoZonaGponDiseniado());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo());
                    cover.setColorTecno("turquoise");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaGponDiseniado());//Nodo GPON SITIDATA
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo GPON

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo());
                    cover.setColorTecno("turquoise");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaGponDiseniado());//Nodo GPON SITIDATA
                    listCover.add(cover);
                }
            }

            /* ---------- Cobertura Nodo MICRO ONDAS ----------- */
            if (direccion.getGeoZonaMicroOndas() != null && !direccion.getGeoZonaMicroOndas().equals("")
                    && !direccion.getGeoZonaMicroOndas().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getGeoZonaMicroOndas());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo());
                    cover.setColorTecno("fuchsia");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaMicroOndas());//Nodo MICRO ONDAS SITIDATA
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo MICRO ONDAS

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo());
                    cover.setColorTecno("fuchsia");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaMicroOndas());//Nodo MICRO ONDAS SITIDATA
                    listCover.add(cover);
                }
            }

            /* -------------- Cobertura Nodo CAVS ------------ */
            if (direccion.getGeoZonaCoberturaCavs() != null && !direccion.getGeoZonaCoberturaCavs().equals("")
                    && !direccion.getGeoZonaCoberturaCavs().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getGeoZonaCoberturaCavs());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo());
                    cover.setColorTecno("gray");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCoberturaCavs());//Nodo CAVS SITIDATA
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo CAVS

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo());
                    cover.setColorTecno("gray");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCoberturaCavs());//Nodo CAVS SITIDATA
                    listCover.add(cover);
                }
            }

            /* ----------- Cobertura Nodo ULTIMA MILLA ----------- */
            if (direccion.getGeoZonaCoberturaUltimaMilla() != null && !direccion.getGeoZonaCoberturaUltimaMilla().equals("")
                    && !direccion.getGeoZonaCoberturaUltimaMilla().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getGeoZonaCoberturaUltimaMilla());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo());
                    cover.setColorTecno("black");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCoberturaUltimaMilla());//Nodo ULTIMA MILLA SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo ULTIMA MILLA

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo());
                    cover.setColorTecno("black");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCoberturaUltimaMilla());//Nodo ULTIMA MILLA
                    listCover.add(cover);
                }
            }

            /* ----------- Cobertura Nodo CURRIER ----------- */
            if (direccion.getGeoZonaCurrier() != null && !direccion.getGeoZonaCurrier().equals("")
                    && !direccion.getGeoZonaCurrier().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getGeoZonaCurrier());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo());
                    cover.setColorTecno("purple");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCurrier());//Nodo CURRIER SITIDATA
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo CURRIER

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo());
                    cover.setColorTecno("purple");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZonaCurrier());//Nodo CURRIER SITIDATA
                    listCover.add(cover);
                }
            }

            /* ---------- Cobertura Nodo 3G --------------- */
            if (direccion.getGeoZona3G() != null && !direccion.getGeoZona3G().equals("")
                    && !direccion.getGeoZona3G().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_3G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getGeoZona3G());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_3G.getCodigo());
                    cover.setColorTecno("yellowgreen");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona3G());//Nodo 3G SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo 3G

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_3G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_3G.getCodigo());
                    cover.setColorTecno("yellowgreen");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona3G());//Nodo 3G SITIDATA
                    listCover.add(cover);
                }
            }

            /* ---------- Cobertura 4G ------------ */
            if (direccion.getGeoZona4G() != null && !direccion.getGeoZona4G().equals("")
                    && !direccion.getGeoZona4G().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_4G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getGeoZona4G());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_4G.getCodigo());
                    cover.setColorTecno("salmon");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona4G());//Nodo 4G SITIDATA
                    listCover.add(cover);
                }
            } else {//Cuando no trae info de nodo 4G

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_4G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_4G.getCodigo());
                    cover.setColorTecno("salmon");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona4G());//Nodo 4G SITIDATA
                    listCover.add(cover);
                }
            }

            /* ----------- Cobertura Nodo 5G --------- */
            if (direccion.getGeoZona5G() != null && !direccion.getGeoZona5G().equals("")
                    && !direccion.getGeoZona5G().trim().equals("NA")) {

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_5G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(direccion.getGeoZona5G());
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_5G.getCodigo());
                    cover.setColorTecno("indigo");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona5G());//Nodo 5G SITIDATA
                    listCover.add(cover);
                }
            } else { //Cuando no trae info de nodo 5G

                CmtBasicaMgl tecnologia = null;
                if (cmtTipoBasicaTec != null) {
                    tecnologia = cmtBasicaMglManager.
                            findByTipoBasicaAndCodigo(cmtTipoBasicaTec.getTipoBasicaId(),
                                    CmtCoverEnum.NODO_ZONA_5G.getCodigo());
                }

                if (tecnologiaValidaCobertura(tecnologia, tipoBasicaExtMglVC)) {
                    CmtCoverDto cover = new CmtCoverDto();
                    cover.setNode(null);
                    cover.setTechnology(CmtCoverEnum.NODO_ZONA_5G.getCodigo());
                    cover.setColorTecno("indigo");
                    cover.setValidaCobertura(true);
                    cover.setNodoSitiData(responseSitiData.getNodoZona5G());//Nodo 5G SITIDATA
                    listCover.add(cover);
                }
            }

        }

        return listCover;
    }

    /**
     * Obtiene Lista de dirección detallada por id de dirección
     *
     * @param dirId id de la dirección
     * @param idDetallada id de la dirección
     * @return List<CmtDireccionDetalladaMgl>
     *
     * @author Victor Bocanegra
     */
    public List<CmtDireccionDetalladaMgl> findDireccionDetallaByDirId(BigDecimal dirId,
            BigDecimal idDetallada) {

        List<CmtDireccionDetalladaMgl> detalladaMgls = cmtDireccionDetalleMglDaoImpl.findDireccionDetallaByDirId(dirId, idDetallada);
        List<CmtDireccionDetalladaMgl> retorno = new ArrayList<>();

        if (detalladaMgls != null && !detalladaMgls.isEmpty()) {
            HhppMglManager hhppMglManager = new HhppMglManager();

            for (CmtDireccionDetalladaMgl direccionDetalladaMgl : detalladaMgls) {
                if (direccionDetalladaMgl.getSubDireccion() != null) {
                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                            .findHhppSubDireccion(direccionDetalladaMgl.getSubDireccion().getSdiId());
                    if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                        if (hhhpSubDirList.get(0).getEstadoRegistro() == 1) {
                            direccionDetalladaMgl.setHhppExistente(true);
                            direccionDetalladaMgl.setHhppMgl(hhhpSubDirList.get(0));
                        }
                    }
                }
                retorno.add(direccionDetalladaMgl);
            }
        }

        return retorno;
    }
    
         /**
     * Obtiene listado de barrios  por dirección
     *
     * @param centroPobladoId
     * @param direccionTabulada
     *
     * @author Victor Bocanegra
     * @return 
     */    
    public List<String> findBarriosDireccionTabuladaMer(BigDecimal centroPobladoId, 
            DrDireccion direccionTabulada) {
        
        return  cmtDireccionDetalleMglDaoImpl.findBarriosDireccionTabuladaMer(centroPobladoId, direccionTabulada);
    }
    
    /**
     * Metodo para actualizar una direccion detallada con la indicacion
     *
     * @author bocanegravm
     * @param indicacionesRequestDto
     * @return CmtIndicacionesResponseDto respuesta con el proceso de
     * actualizacion de la solicitud
     * @throws ApplicationException
     *
     */
    public CmtIndicacionesResponseDto actualizarIndicaciones(CmtIndicacionesRequestDto indicacionesRequestDto){

        CmtIndicacionesResponseDto respuesta = new CmtIndicacionesResponseDto();
        try {

            if (indicacionesRequestDto == null) {
                respuesta.setMessage("Error: Debe crear un correcto request para consumir el servicio");
                respuesta.setMessageType("E");
                return respuesta;
            }

            if (indicacionesRequestDto.getIdDetallada() == null) {
                respuesta.setMessage("Error: El campo idDetallada es obligatorio y debe ser numerico");
                respuesta.setMessageType("E");
                return respuesta;
            }

            if (indicacionesRequestDto.getIndicacion() == null) {
                respuesta.setMessage("Error: El campo indicacion es obligatorio");
                respuesta.setMessageType("E");
                return respuesta;
            }

            if (indicacionesRequestDto.getIndicacion().length() > 1000) {
                respuesta.setMessage("Error: El campo indicacion excede la cantidad permitida de 1000 caracteres");
                respuesta.setMessageType("E");
                return respuesta;
            }

            //Buscamos si existe la direccion
            CmtDireccionDetalladaMgl direccionDetalladaMgl
                    = cmtDireccionDetalleMglDaoImpl.find(indicacionesRequestDto.getIdDetallada());
            if (direccionDetalladaMgl != null
                    && direccionDetalladaMgl.getDireccionDetalladaId() != null) {
                direccionDetalladaMgl.setIndicaciones(indicacionesRequestDto.getIndicacion());
                cmtDireccionDetalleMglDaoImpl.update(direccionDetalladaMgl);
                respuesta.setMessage("Actualizacion de direccion exitosa");
                respuesta.setMessageType("I");
                return respuesta;
            } else {
                respuesta.setMessage("Error: No existe "
                        + "direccion en MER con el id: " + indicacionesRequestDto.getIdDetallada() + "");
                respuesta.setMessageType("E");
                return respuesta;
            }
            //Fin buscamos si existe la direccion

        } catch (ApplicationException ex) {
            respuesta.setMessage("Error: " + ex.getMessage());
            respuesta.setMessageType("E");
        }
        return respuesta;
    }

    /**
     * Metodo para consultar la indicacion de una direccion
     *
     * @author bocanegravm
     * @param indicacionesRequestDto
     * @return CmtIndicacionesResponseDto respuesta con el proceso de consulta
     * de la solicitud
     * @throws ApplicationException
     *
     */
    public CmtIndicacionesResponseDto consultarIndicaciones(CmtIndicacionesRequestDto indicacionesRequestDto) {

        CmtIndicacionesResponseDto respuesta = new CmtIndicacionesResponseDto();
        try {

            if (indicacionesRequestDto == null) {
                respuesta.setMessage("Error: Debe crear un correcto request para consumir el servicio");
                respuesta.setMessageType("E");
                return respuesta;
            }

            if (indicacionesRequestDto.getIdDetallada() == null) {
                respuesta.setMessage("Error: El campo idDetallada es obligatorio y debe ser numerico");
                respuesta.setMessageType("E");
                return respuesta;
            }

            //Buscamos si existe la direccion
            CmtDireccionDetalladaMgl direccionDetalladaMgl
                    = cmtDireccionDetalleMglDaoImpl.find(indicacionesRequestDto.getIdDetallada());
            if (direccionDetalladaMgl != null
                    && direccionDetalladaMgl.getDireccionDetalladaId() != null) {
                respuesta.setMessage("Consulta exitosa");
                respuesta.setMessageType("I");
                respuesta.setIndicacion(direccionDetalladaMgl.getIndicaciones());
                return respuesta;
            } else {
                respuesta.setMessage("Error: No existe "
                        + "direccion en MER con el id: " + indicacionesRequestDto.getIdDetallada() + "");
                respuesta.setMessageType("E");
                return respuesta;
            }
            //Fin buscamos si existe la direccion

        } catch (ApplicationException ex) {
            respuesta.setMessage("Error: " + ex.getMessage());
            respuesta.setMessageType("E");
            return respuesta;
        }
    }

    public boolean tecnologiaValidaCobertura(CmtBasicaMgl tecnologia,
            CmtTipoBasicaExtMgl tipoBasicaExtMglVC) {

        boolean validaCob = false;

        if (tecnologia != null) {

            List<CmtBasicaExtMgl> extenList = tecnologia.getListCmtBasicaExtMgl();
            if (extenList != null && !extenList.isEmpty() && tipoBasicaExtMglVC != null) {
                for (CmtBasicaExtMgl basicaExtMgl : extenList) {

                    if (Objects.equals(basicaExtMgl.getIdTipoBasicaExt().
                            getIdTipoBasicaExt(), tipoBasicaExtMglVC.getIdTipoBasicaExt())
                            && basicaExtMgl.getIdBasicaObj().getBasicaId().compareTo(tecnologia.getBasicaId()) == 0
                            && basicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")
                            && basicaExtMgl.getEstadoRegistro() == 1) {
                        validaCob = true;
                    }
                }

            }
        }

        return validaCob;
    }
    
    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaCcmmByDirId(BigDecimal dirId) {
        return cmtDireccionDetalleMglDaoImpl.findDireccionDetalladaCcmmByDirId(dirId);
    }
    
      public List<CmtDireccionDetalladaMgl> findDireccionByDireccionDetalladaExacta(DrDireccion direccion,
            BigDecimal centroPobladoId, boolean evaluarEstadoRegistro, GeograficoPoliticoMgl centroPobladoCompleto) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();

        List<CmtDireccionDetalladaMgl> direccionDetalladaList = new ArrayList();

          if (centroPobladoCompleto != null && centroPobladoCompleto.getGpoId() != null) {
              if (centroPobladoCompleto != null && centroPobladoCompleto.getGpoMultiorigen() != null
                      && !centroPobladoCompleto.getGpoMultiorigen().isEmpty() && centroPobladoCompleto.getGpoMultiorigen().equalsIgnoreCase("1")) {
                  direccion.setMultiOrigen("1");
              } else {
                  direccion.setMultiOrigen("0");
              }

          } else {
              GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
              GeograficoPoliticoMgl centroPoblado = geograficoPoliticoManager.findById(centroPobladoId);

              if (centroPoblado != null && centroPoblado.getGpoMultiorigen() != null
                      && !centroPoblado.getGpoMultiorigen().isEmpty() && centroPoblado.getGpoMultiorigen().equalsIgnoreCase("1")) {
                  direccion.setMultiOrigen("1");
              } else {
                  direccion.setMultiOrigen("0");
              }
          }

        //Buscamos la direccion tabulada en direccion_detallada
        direccionDetalladaList = cmtDireccionDetalleMglDao.buscarDireccionTabuladaMerExacta(centroPobladoId, direccion);
     
        return direccionDetalladaList;
    }
      
    public List<CmtDireccionDetalladaMgl> findDireccionByDireccionDetalladaPrincipalExacta(DrDireccion direccion,
            BigDecimal gpo, boolean evaluarEstadoRegistro) throws ApplicationException {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();

        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(gpo);
        
        if (centroPobladoCompleto != null && centroPobladoCompleto.getGpoMultiorigen() != null
                && !centroPobladoCompleto.getGpoMultiorigen().isEmpty() && centroPobladoCompleto.getGpoMultiorigen().equalsIgnoreCase("1")) {
            direccion.setMultiOrigen("1");
        } else {
            direccion.setMultiOrigen("0");
        }

        //Buscamos la direccion tabulada en direccion_detallada
        return cmtDireccionDetalleMglDao.buscarDireccionTabuladaMerPrincipalExacta(gpo, direccion);
    }
    
    public List<CmtDireccionDetalladaMgl> findDireccionDetallaByDirIdCCMM(BigDecimal dirIdCuentaMatriz) {
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDao = new CmtDireccionDetalleMglDaoImpl();
        return cmtDireccionDetalleMglDao.findDireccionDetallaByDirIdCCMM(dirIdCuentaMatriz);
    }
    
       /**
     * Metodo recibir un listado de direccion y recorrer una a una para determinar 
     * si es alterna, al ser alterna buscar las direccion con la direccion principal 
     * y sustituir los resultados por los nuevos.
     *
     * @author hernandezJuat
     * @param resultadoOriginalDireccionesList
     * @return List CmtAddressGeneralDto listado de direccion principales reemplazando las que fueron alternas
     * @throws ApplicationException
     *
     */
    public List<CmtAddressGeneralDto> obtenerDireccionPrincipalByPosibleDireccionAlterna(List<CmtAddressGeneralDto> resultadoOriginalDireccionesList, DrDireccion direccionIngresadaTabulada) throws ApplicationException, CloneNotSupportedException {
        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();

        //se crea estructura que nos ayuda a eliminar repetidos
        HashSet idDireccionList = new HashSet();
        
        //se recorre el listado de resultados originales y al agregarlos al hashSet se eliminan los idDireccion repetidos
        for (CmtAddressGeneralDto cmtAddressGeneralDto : resultadoOriginalDireccionesList) {
            idDireccionList.add(cmtAddressGeneralDto.getSplitAddres().getDireccionId());
        }

        if (idDireccionList != null && !idDireccionList.isEmpty()) {
            for (Object idDireccion : idDireccionList) {
                //Busqueda de direcciones alternas en ccmm 
                List<CmtDireccionMgl> direccionAlternaList = cmtDireccionMglManager.findDireccionAlternaByDireccionId(new BigDecimal(idDireccion.toString()));

                if (direccionAlternaList != null && !direccionAlternaList.isEmpty()) {

                    for (CmtDireccionMgl cmtDireccionAlternaMgl : direccionAlternaList) {

                        //busqueda de direcciones con la direccion principal de la CCMM de la direccion alterna encontrada
                        List<CmtDireccionDetalladaMgl> direccionDetalladaCcmmPrincipalList = 
                                findDireccionDetallaByDirIdCCMM(cmtDireccionAlternaMgl.getCuentaMatrizObj().getCuentaMatrizId());

                        if (direccionDetalladaCcmmPrincipalList != null && !direccionDetalladaCcmmPrincipalList.isEmpty()) {

                            CmtDireccionDetalladaMgl direccionAlternaExacta = null;                             
                            
                            //si la direccion tiene complementos se realiza comparación para obtener la exacta
                            if (direccionIngresadaTabulada != null) {
                                if (direccionIngresadaTabulada.getCpTipoNivel1() != null || direccionIngresadaTabulada.getCpTipoNivel2() != null
                                        || direccionIngresadaTabulada.getCpTipoNivel3() != null || direccionIngresadaTabulada.getCpTipoNivel4() != null
                                        || direccionIngresadaTabulada.getCpTipoNivel5() != null || direccionIngresadaTabulada.getCpTipoNivel6() != null) {

                                    String cpTipoNivel1 = direccionIngresadaTabulada.getCpTipoNivel1() != null ? direccionIngresadaTabulada.getCpTipoNivel1().toUpperCase() : "";
                                    String cpTipoNivel2 = direccionIngresadaTabulada.getCpTipoNivel2() != null ? direccionIngresadaTabulada.getCpTipoNivel2().toUpperCase() : "";
                                    String cpTipoNivel3 = direccionIngresadaTabulada.getCpTipoNivel3() != null ? direccionIngresadaTabulada.getCpTipoNivel3().toUpperCase() : "";
                                    String cpTipoNivel4 = direccionIngresadaTabulada.getCpTipoNivel4() != null ? direccionIngresadaTabulada.getCpTipoNivel4().toUpperCase() : "";
                                    String cpTipoNivel5 = direccionIngresadaTabulada.getCpTipoNivel5() != null ? direccionIngresadaTabulada.getCpTipoNivel5().toUpperCase() : "";
                                    String cpTipoNivel6 = direccionIngresadaTabulada.getCpTipoNivel6() != null ? direccionIngresadaTabulada.getCpTipoNivel6().toUpperCase() : "";

                                    String cpValorNivel1 = direccionIngresadaTabulada.getCpValorNivel1() != null ? direccionIngresadaTabulada.getCpValorNivel1().toUpperCase() : "";
                                    String cpValorNivel2 = direccionIngresadaTabulada.getCpValorNivel2() != null ? direccionIngresadaTabulada.getCpValorNivel2().toUpperCase() : "";
                                    String cpValorNivel3 = direccionIngresadaTabulada.getCpValorNivel3() != null ? direccionIngresadaTabulada.getCpValorNivel3().toUpperCase() : "";
                                    String cpValorNivel4 = direccionIngresadaTabulada.getCpValorNivel4() != null ? direccionIngresadaTabulada.getCpValorNivel4().toUpperCase() : "";
                                    String cpValorNivel5 = direccionIngresadaTabulada.getCpValorNivel5() != null ? direccionIngresadaTabulada.getCpValorNivel5().toUpperCase() : "";
                                    String cpValorNivel6 = direccionIngresadaTabulada.getCpValorNivel6() != null ? direccionIngresadaTabulada.getCpValorNivel6().toUpperCase() : "";

                                   
                                    for (CmtDireccionDetalladaMgl direccionDetallada : direccionDetalladaCcmmPrincipalList) {

                                        String dirCpTipoNivel1 = direccionDetallada.getCpTipoNivel1() != null ? direccionDetallada.getCpTipoNivel1().toUpperCase() : "";
                                        String dirCpTipoNivel2 = direccionDetallada.getCpTipoNivel2() != null ? direccionDetallada.getCpTipoNivel2().toUpperCase() : "";
                                        String dirCpTipoNivel3 = direccionDetallada.getCpTipoNivel3() != null ? direccionDetallada.getCpTipoNivel3().toUpperCase() : "";
                                        String dirCpTipoNivel4 = direccionDetallada.getCpTipoNivel4() != null ? direccionDetallada.getCpTipoNivel4().toUpperCase() : "";
                                        String dirCpTipoNivel5 = direccionDetallada.getCpTipoNivel5() != null ? direccionDetallada.getCpTipoNivel5().toUpperCase() : "";
                                        String dirCpTipoNivel6 = direccionDetallada.getCpTipoNivel6() != null ? direccionDetallada.getCpTipoNivel6().toUpperCase() : "";

                                        String dirCpValorNivel1 = direccionDetallada.getCpValorNivel1() != null ? direccionDetallada.getCpValorNivel1().toUpperCase() : "";
                                        String dirCpValorNivel2 = direccionDetallada.getCpValorNivel2() != null ? direccionDetallada.getCpValorNivel2().toUpperCase() : "";
                                        String dirCpValorNivel3 = direccionDetallada.getCpValorNivel3() != null ? direccionDetallada.getCpValorNivel3().toUpperCase() : "";
                                        String dirCpValorNivel4 = direccionDetallada.getCpValorNivel4() != null ? direccionDetallada.getCpValorNivel4().toUpperCase() : "";
                                        String dirCpValorNivel5 = direccionDetallada.getCpValorNivel5() != null ? direccionDetallada.getCpValorNivel5().toUpperCase() : "";
                                        String dirCpValorNivel6 = direccionDetallada.getCpValorNivel6() != null ? direccionDetallada.getCpValorNivel6().toUpperCase() : "";

                                        if (cpTipoNivel1.equalsIgnoreCase(dirCpTipoNivel1) && cpTipoNivel2.equalsIgnoreCase(dirCpTipoNivel2)
                                                && cpTipoNivel3.equalsIgnoreCase(dirCpTipoNivel3) && cpTipoNivel4.equalsIgnoreCase(dirCpTipoNivel4)
                                                && cpTipoNivel5.equalsIgnoreCase(dirCpTipoNivel5) && cpTipoNivel6.equalsIgnoreCase(dirCpTipoNivel6)
                                                && cpValorNivel1.equalsIgnoreCase(dirCpValorNivel1) && cpValorNivel2.equalsIgnoreCase(dirCpValorNivel2)
                                                && cpValorNivel3.equalsIgnoreCase(dirCpValorNivel3) && cpValorNivel4.equalsIgnoreCase(dirCpValorNivel4)
                                                && cpValorNivel5.equalsIgnoreCase(dirCpValorNivel5) && cpValorNivel6.equalsIgnoreCase(dirCpValorNivel6)) {
                                            direccionAlternaExacta = direccionDetallada.clone();
                                            break;

                                        }
                                    }
                                }
                            }
                      
                            List<CmtAddressGeneralDto> direccionPrincipalCcmmList = null;
                            if (direccionAlternaExacta != null) {
                                List<CmtDireccionDetalladaMgl> direccionAlternaExactaList = Arrays.asList(direccionAlternaExacta);
                                direccionPrincipalCcmmList = parseCmtDireccionDetalladaMglToCmtAddressGeneralDto(direccionAlternaExactaList);
                            } else {
                                direccionDetalladaCcmmPrincipalList = findDireccionDetalladaCcmmByDirId(direccionDetalladaCcmmPrincipalList.get(0).getDireccionId());
                                direccionPrincipalCcmmList = parseCmtDireccionDetalladaMglToCmtAddressGeneralDto(direccionDetalladaCcmmPrincipalList);
                            }
                            return direccionPrincipalCcmmList;
                        }
                    }
                }
            }
        }

        List<CmtAddressGeneralDto> auxListaFinal = new ArrayList<>();
        for (CmtAddressGeneralDto cmtAddressGeneralDto : resultadoOriginalDireccionesList) {
            if (!cmtAddressGeneralDto.isDirAlterna()) {
                auxListaFinal.add(cmtAddressGeneralDto);
            }
        }
        return auxListaFinal;
    }
    
    
     public CmtDireccionDetalladaMgl obtenerDireccionPrincipalSinComplementos(CmtDireccionDetalladaMgl direccionPrincipal){
         try {
             direccionPrincipal.setCpTipoNivel1(null);
             direccionPrincipal.setCpTipoNivel2(null);
             direccionPrincipal.setCpTipoNivel3(null);
             direccionPrincipal.setCpTipoNivel4(null);
             direccionPrincipal.setCpTipoNivel5(null);
             direccionPrincipal.setCpTipoNivel6(null);
             
             direccionPrincipal.setCpValorNivel1(null);
             direccionPrincipal.setCpValorNivel2(null);
             direccionPrincipal.setCpValorNivel3(null);
             direccionPrincipal.setCpValorNivel4(null);
             direccionPrincipal.setCpValorNivel5(null);
             direccionPrincipal.setCpValorNivel6(null);
             
             return direccionPrincipal;
             
         } catch (Exception e) {
             return null;
         }
     }
     
      /**
     * Juan David Hernandez Metodo para consultar la direccion exacta por texto o
     * tabulada
     *
     * @param cmtDireccionRequestDto
     * @return
     */
    public CmtAddressGeneralResponseDto consultaDireccionExactaTabulada(CmtDireccionRequestDto cmtDireccionRequestDto) throws ExceptionDB, CloneNotSupportedException, ApplicationException {

        CmtAddressGeneralResponseDto respuesta = new CmtAddressGeneralResponseDto(); 
        List<CmtAddressGeneralDto> auxLista;
        Initialized.getInstance();
        validarDireccion = null;
        try {
            if (cmtDireccionRequestDto == null) {
                respuesta.setMessage("Error: Debe crear un correcto request para consumir el servicio");
                respuesta.setMessageType("E");
                return respuesta;
            }
            
            if (cmtDireccionRequestDto.getDireccionTabulada() == null) {
                respuesta.setMessage("Error: Debe ingresar una direccion tabulada para ser consultada");
                respuesta.setMessageType("E");
                return respuesta;
            }

            //El codigo dane es obligatorio para realizar la busqueda de la ciudad
            if (cmtDireccionRequestDto.getCodigoDane() == null || cmtDireccionRequestDto.getCodigoDane().equals("")) {
                respuesta.setMessage("Error: ".concat(Constant.MSG_DIR_CODIGO_DANE));
                respuesta.setMessageType("E");
                return respuesta;
            } else {
                if (cmtDireccionRequestDto.getCodigoDane().length() < 7) {
                    respuesta.setMessage("El codigo dane debe ser de minimo 8 digitos");
                    respuesta.setMessageType("E");
                    return respuesta;
                }
            } 

           GeograficoPoliticoMgl ciudadGpo = null;
            
            try {
                //El codigo dane es debe ser valido
                ciudadGpo = buscarCiudad(cmtDireccionRequestDto.getCodigoDane());
                if (ciudadGpo != null) {                    
                    if(ciudadGpo.getGpoMultiorigen() != null && ciudadGpo.getGpoMultiorigen().equalsIgnoreCase("1")){                       
                        if(!(cmtDireccionRequestDto.getDireccionTabulada().getBarrio() != null && !cmtDireccionRequestDto.getDireccionTabulada().getBarrio().isEmpty())){
                            respuesta.setMessage("La ciudad es MultiOrigen, es necesario ingresar el campo barrio de la direccion tabulada. ");
                            respuesta.setMessageType("E");
                            return respuesta;
                        }                        
                    }
                }else{
                    respuesta.setMessage("Error: ".concat(Constant.MSG_DIR_CODIGO_DANE_ERROR));
                    respuesta.setMessageType("E");
                    return respuesta; 
                }
                    
                
            } catch (ApplicationException ex) {
                LOGGER.error(ex.getMessage(), ex);
                respuesta.setMessage("Error: ".concat(Constant.MSG_DIR_CODIGO_DANE_ERROR)
                        .concat(" (".concat(ex.getMessage()).concat(").")));
                respuesta.setMessageType("E");
                return respuesta;
            }            
   
                 //Inicio buscar tabulada
                if (cmtDireccionRequestDto.getDireccionTabulada().getIdTipoDireccion() == null
                        || cmtDireccionRequestDto.getDireccionTabulada().getIdTipoDireccion().isEmpty()) {
                    respuesta.setMessage("Error: ".concat(Constant.MSG_TIPO_DIR_TABULADA));
                    respuesta.setMessageType("E");
                    return respuesta;
                }

                DrDireccion direccionTabulada = parseCmtDireccionTabuladaDtoToDrDireccion(cmtDireccionRequestDto.getDireccionTabulada());

                // Se realiza la busqueda de la direccion tabulada exacta
                List<CmtDireccionDetalladaMgl> resultList = findDireccionByDireccionDetalladaExacta(direccionTabulada, ciudadGpo.getGpoId(), true, ciudadGpo); 
                
                auxLista = parseCmtDireccionDetalladaMglToCmtAddressGeneralDto(resultList);

                // si encuentra direcciones retorna la lista
                if (auxLista != null && !auxLista.isEmpty()) {                     
                    //algoritmo que obtiene direcciones principales apartir de posibles direcciones alternas
                   List<CmtAddressGeneralDto> auxListaFinal = obtenerDireccionPrincipalByPosibleDireccionAlterna(auxLista, direccionTabulada);  
                   
                if (auxLista != null && !auxLista.isEmpty()) {

                    respuesta.setIdCentroPoblado(ciudadGpo.getGpoId().toString());
                    respuesta.setCentroPoblado(ciudadGpo.getGpoNombre());
                    respuesta.setListAddresses(auxListaFinal);
                    respuesta.setMessage("Operacion Exitosa");
                    respuesta.setMessageType("I");
                    return respuesta;
                } else {
                    respuesta.setIdCentroPoblado(ciudadGpo.getGpoId().toString());
                    respuesta.setCentroPoblado(ciudadGpo.getGpoNombre());
                    respuesta.setListAddresses(auxLista);
                    respuesta.setMessage("No se encontro ninguna dirección con los criterios de busqueda ingresados");
                    respuesta.setMessageType("I");
                }
            } else {
                respuesta.setIdCentroPoblado(ciudadGpo.getGpoId().toString());
                respuesta.setCentroPoblado(ciudadGpo.getGpoNombre());
                respuesta.setListAddresses(auxLista);
                respuesta.setMessage("No se encontro ninguna dirección con los criterios de busqueda ingresados");
                respuesta.setMessageType("I");
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error en consulta direccion exacta de CmtDireccionDetalleMglManager " + e.getMessage());
            respuesta.setMessage("Error: ".concat(e.getMessage()));
            respuesta.setMessageType("E");
            return respuesta;
        }
        return respuesta;
    }
    
    public ConsultaIdMerResponseDto consultaIdDireccionMER(ConsultaIdMerRequestDto consultaIdMerRequestDto) {
        ConsultaIdMerResponseDto response = new ConsultaIdMerResponseDto();
        try {
            if (consultaIdMerRequestDto != null) {
                if (StringUtils.isEmpty(consultaIdMerRequestDto.getIdRR())
                        || !consultaIdMerRequestDto.getIdRR().matches("[0-9]*")) {
                    response.setMessage("Error: id de factibilidad esta vacio o contiene letras.");
                    response.setMessageType("ERROR");
                    return response;
                }
                ConsultaIdMerResponseDto responsePL = cmtDireccionDetalleMglDaoImpl
                        .consultaIdDireccionMER(Integer.parseInt(consultaIdMerRequestDto.getIdRR()));
                if (responsePL.getIdMER() != 0 && !responsePL.getTipoUbicacion().equals("NO_HAY_DATOS")) {
                    response.setIdMER(responsePL.getIdMER());
                    response.setTipoUbicacion(responsePL.getTipoUbicacion());
                    response.setMessage("Operacion consultaIdDireccionMER Exitosa");
                    response.setMessageType("SUCCESS");
                } else {
                    response.setMessage("Error: ID RR no encontrado.");
                    response.setMessageType("ERROR");
                    return response;
                }

            }
        } catch (Exception e) {
            LOGGER.error("Error consultaIdDireccionMER ejecutando el Procedimiento almacenado BUSCAR_ID_MER " + e.getMessage());
            response.setMessage("Error: ejecutando el Procedimiento almacenado BUSCAR_ID_MER.");
            response.setMessageType("ERROR");
        }
        return response;
    }

    /**
     * @author Manuel Hernández Rivas
     *
     * Metodo para consultar la direccion por nuemero de cuenta
     * detallada
     * @param nodeIDsByAccountRequestDto
     * @return nodeIDsByAccountRequestDto
     */
    public CmtAddressResponseDto getNodeIDsByAccount(getNodeIDsByAccountRequestDto nodeIDsByAccountRequestDto) {
            CmtAddressResponseDto respuesta = new CmtAddressResponseDto();

            if (nodeIDsByAccountRequestDto.getNumCuenta() == null || nodeIDsByAccountRequestDto.getNumCuenta().isEmpty()) {
                respuesta.setMessage("Error: Debe ingresa el numero de cuenta de la direccion detallada que desea consultar.");
                respuesta.setMessageType("E");
                return respuesta;
            }

            CmtDireccionDetalleMglDaoImpl direccionDetalleMglDaoImpl = new CmtDireccionDetalleMglDaoImpl();
            String idDireccion = direccionDetalleMglDaoImpl.storedProcedureGetNodeIDsByAccount(nodeIDsByAccountRequestDto);

            if (idDireccion == null || idDireccion.isEmpty() || idDireccion.equals("NO_HAY_DATOS")) {
                respuesta.setMessage("Error: No existe la direccion detallada con el numero de cuenta");
                respuesta.setMessageType("E");
                return respuesta;
            }else if(idDireccion.equals("ENTRADA_INVALIDA")){
                respuesta.setMessage("Error: cuenta inexistente");
                respuesta.setMessageType("E");
                return respuesta;
            }else{
                CmtDireccionDetalladaRequestDto request = new CmtDireccionDetalladaRequestDto();
                request.setIdDireccion(new BigDecimal(idDireccion));
                request.setSegmento(null);
                return ConsultaDireccion(request);
            }
    }


    /**
     * Realiza la consulta del valor de un par&aacute;metro, a trav&eacute;s de
     * su acr&oacute;nimo.
     *
     * @param acronimo Acr&oacute;nimo del Par&aacute;metro a buscar.
     * @return Valor del Par&aacute;metro.
     * @throws ApplicationException
     */
    private String consultarParametro(String acronimo) {
        String valor = null;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        try {
            valor = parametrosMglManager.findByAcronimoName(acronimo).getParValor();
        } catch (ApplicationException e) {
            LOGGER.error("No se encuentra configurado el parámetro '" + acronimo + "'.");
        }
        return (valor);
    }

    public Date fechaVencimiento(Date fecha, int diasAsumar) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, diasAsumar);
        return calendar.getTime();
    }

    public String retornaColorTecno(String codigoBasica) {

        String color = "";
        if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_UNO.getCodigo())) {
            color = "orange";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_DOS.getCodigo())) {
            color = "blue";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_TRES.getCodigo())) {
            color = "red";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_DTH.getCodigo())) {
            color = "green";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_MOVIL.getCodigo())) {
            color = "yellow";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_FTTH.getCodigo())) {
            color = "brown";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_WIFI.getCodigo())) {
            color = "pink";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_FOU.getCodigo())) {
            color = "violet";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo())) {
            color = "turquoise";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo())) {
            color = "fuchsia";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo())) {
            color = "gray";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo())) {
            color = "black";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo())) {
            color = "purple";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_3G.getCodigo())) {
            color = "yellowgreen";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_4G.getCodigo())) {
            color = "salmon";
        } else if (codigoBasica.equalsIgnoreCase(CmtCoverEnum.NODO_ZONA_5G.getCodigo())) {
            color = "indigo";
        }
        return color;
    }

    public CmtDireccionDetalladaMglDto consultarCoberturas(CmtDireccionDetalladaMglDto direccionDetalladaMglDto) throws ApplicationException {
        CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal = new CmtDireccionDetalleMglFacade();
        NodoMglFacadeLocal nodoMglFacadeLocal = new NodoMglFacade();
        HhppMglFacadeLocal hhppMglFacadeLocal = new HhppMglFacade();
        CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal = new CmtTipoBasicaMglFacade();
        CmtTecnologiaSubMglFacadeLocal tecnologiaSubMglFacadeLocal = new CmtTecnologiaSubMglFacade();
        FactibilidadMglFacadeLocal factibilidadMglFacadeLocal = new FactibilidadMglFacade();
        List<CmtCoverDto> coberturas = null;
        List<CmtCoverDto> listCover = new ArrayList<>();
        Map<String, NodoMgl> mapNodos = new HashMap<>();
        if (direccionDetalladaMglDto.getDireccionMgl() != null) {
            coberturas = cmtDireccionDetalleMglFacadeLocal.
                    coberturasDireccion(direccionDetalladaMglDto.getDireccionMgl(),
                            direccionDetalladaMglDto.getSubDireccionMgl());
        }
        if (coberturas != null && coberturas.size() > 0) {
            for (CmtCoverDto coverDto : coberturas) {
                if (coverDto.getNode() != null && !coverDto.getNode().isEmpty()) {
                    // Realiza la busqueda del Nodo por Codigo.
                    NodoMgl nodoMgl = mapNodos.get(coverDto.getNode());
                    if (nodoMgl == null) {
                        nodoMgl = nodoMglFacadeLocal.findByCodigo(coverDto.getNode());
                        if (nodoMgl != null) {
                            mapNodos.put(coverDto.getNode(), nodoMgl);
                        }
                    }
                }
                listCover.add(coverDto);
            }
            direccionDetalladaMglDto.setListCover(listCover);
        }

        if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("UNIDAD")) {
            //Busco hhpp de la direccion
            DireccionMgl direccionMgl = new DireccionMgl();
            direccionMgl.setDirId(direccionDetalladaMglDto.getDireccionId());
            SubDireccionMgl subDireccionMgl = new SubDireccionMgl();
            if (direccionDetalladaMglDto.getSubdireccionId() != null) {
                subDireccionMgl.setSdiId(direccionDetalladaMglDto.getSubdireccionId());
            } else {
                subDireccionMgl = null;
            }
            List<HhppMgl> listadoTecHab
                    = hhppMglFacadeLocal.findByDirAndSubDir(direccionMgl, subDireccionMgl);

            if (listadoTecHab != null && !listadoTecHab.isEmpty()) {

                CmtTipoBasicaMgl cmtTipoBasicaTec = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TECNOLOGIA);

                CmtTipoBasicaExtMgl tipoBasicaExtMglVC = null;

                if (cmtTipoBasicaTec != null
                        && !cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl().isEmpty()) {
                    for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl
                            : cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl()) {
                        if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().equalsIgnoreCase(Constant.TECNOLOGIA_MANEJA_NODOS)) {
                            tipoBasicaExtMglVC = cmtTipoBasicaExtMgl;
                        }
                    }

                }

                for (HhppMgl hhppMgl : listadoTecHab) {
                    if (listCover != null && !listCover.isEmpty()) {
                        int control = 0;
                        for (CmtCoverDto coverDtoCon : new ArrayList<>(listCover)) {

                            NodoMgl nodo = hhppMgl.getNodId();
                            CmtBasicaMgl tecnologia2 = nodo != null ? nodo.getNodTipo() : null;
                            CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                            if (tecnologia2 != null) {
                                if ((coverDtoCon.getTechnology().equalsIgnoreCase(tecnologia2.getCodigoBasica()))) {

                                    LOGGER.info("tecnologias iguales  agrego a la lista el HHPP");
                                    listCover.remove(coverDtoCon);
                                    CmtCoverDto coverDto = new CmtCoverDto();
                                    coverDto.setHhppExistente(true);
                                    coverDto.setTechnology(tecnologia2.getCodigoBasica());
                                    coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                                    coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                    coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                    coverDto.setHhppMgl(hhppMgl);
                                    coverDto.setValidaCobertura(true);
                                    listCover.add(coverDto);
                                    control++;
                                }

                            }

                        }
                        if (control == 0) {
                            NodoMgl nodo = hhppMgl.getNodId();
                            CmtBasicaMgl tecnologia2 = nodo != null ? nodo.getNodTipo() : null;
                            CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;
                            if (tecnologia2 != null) {
                                if (tecnologiaValidaCobertura(tecnologia2, tipoBasicaExtMglVC)) {
                                    CmtCoverDto coverDto = new CmtCoverDto();

                                    coverDto.setHhppExistente(true);

                                    coverDto.setTechnology(tecnologia2.getCodigoBasica());
                                    coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                                    coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                    coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                    coverDto.setHhppMgl(hhppMgl);
                                    coverDto.setValidaCobertura(true);
                                    listCover.add(coverDto);
                                }
                            }
                        }

                    } else {
                        NodoMgl nodo = hhppMgl.getNodId();
                        CmtBasicaMgl tecnologia2 = nodo != null ? nodo.getNodTipo() : null;
                        CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                        if (tecnologiaValidaCobertura(tecnologia2, tipoBasicaExtMglVC)) {

                            CmtCoverDto coverDto = new CmtCoverDto();
                            coverDto.setHhppExistente(true);

                            coverDto.setTechnology(tecnologia2 != null ? tecnologia2.getCodigoBasica() : "NA");
                            coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                            coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                            coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                            coverDto.setHhppMgl(hhppMgl);
                            coverDto.setValidaCobertura(true);
                            listCover.add(coverDto);
                        }
                    }
                }
                direccionDetalladaMglDto.setListCover(listCover);
                direccionDetalladaMglDto.setLstHhppMgl(listadoTecHab);
                //Fin Busqueda hhpp de la direccion
            }
        } else if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("CCMM")) {
            //Busco tecnologias sub de la ccmm
            if (direccionDetalladaMglDto.getCuentaMatrizMgl() != null) {
                List<CmtTecnologiaSubMgl> tecnologiasCcmm = tecnologiaSubMglFacadeLocal.
                        findTecnoSubBySubEdi(direccionDetalladaMglDto.getCuentaMatrizMgl().getSubEdificioGeneral());

                if (tecnologiasCcmm != null && !tecnologiasCcmm.isEmpty()) {

                    CmtTipoBasicaMgl cmtTipoBasicaTec = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.TIPO_BASICA_TECNOLOGIA);
                    CmtTipoBasicaExtMgl tipoBasicaExtMglVC = null;

                    if (cmtTipoBasicaTec != null
                            && !cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl().isEmpty()) {
                        for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl
                                : cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl()) {
                            if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().equalsIgnoreCase(Constant.TECNOLOGIA_MANEJA_NODOS)) {
                                tipoBasicaExtMglVC = cmtTipoBasicaExtMgl;
                            }
                        }

                    }

                    for (CmtTecnologiaSubMgl tecno : tecnologiasCcmm) {
                        if (listCover != null && !listCover.isEmpty()) {
                            int control = 0;
                            for (CmtCoverDto coverDtoCon : new ArrayList<>(listCover)) {
                                if (tecno != null) {
                                    if ((coverDtoCon.getTechnology().equalsIgnoreCase(tecno.getBasicaIdTecnologias().getCodigoBasica()))) {

                                        LOGGER.info("tecnologias iguales  agrego a la lista tecnoSub");
                                        listCover.remove(coverDtoCon);
                                        CmtCoverDto coverDto = new CmtCoverDto();
                                        NodoMgl nodo = tecno.getNodoId();
                                        CmtBasicaMgl tec = nodo != null ? nodo.getNodTipo() : null;
                                        CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                                        coverDto.setTechnology(tec != null ? tec.getCodigoBasica() : "NA");
                                        coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                                        coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                        coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                        coverDto.setTecnologiaSubMgl(tecno);
                                        coverDto.setValidaCobertura(true);
                                        listCover.add(coverDto);
                                        control++;
                                    }
                                }
                            }
                            if (control == 0) {
                                if (tecno != null) {
                                    if (tecnologiaValidaCobertura(tecno.getBasicaIdTecnologias(), tipoBasicaExtMglVC)) {

                                        CmtCoverDto coverDto = new CmtCoverDto();
                                        NodoMgl nodo = tecno.getNodoId();
                                        CmtBasicaMgl tec = nodo != null ? nodo.getNodTipo() : null;
                                        CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                                        coverDto.setTechnology(tec != null ? tec.getCodigoBasica() : "NA");
                                        coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                                        coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                        coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                        coverDto.setTecnologiaSubMgl(tecno);
                                        coverDto.setValidaCobertura(true);
                                        listCover.add(coverDto);
                                    }
                                }
                            }
                        } else {
                            NodoMgl nodo = tecno != null ? tecno.getNodoId() : null;
                            CmtBasicaMgl tec = nodo != null ? nodo.getNodTipo() : null;
                            CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                            if (tecnologiaValidaCobertura(tec, tipoBasicaExtMglVC)) {

                                CmtCoverDto coverDto = new CmtCoverDto();
                                coverDto.setTechnology(tec != null ? tec.getCodigoBasica() : "NA");
                                coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                                coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                                coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                                coverDto.setTecnologiaSubMgl(tecno);
                                coverDto.setValidaCobertura(true);
                                listCover.add(coverDto);

                            }
                        }
                    }
                    direccionDetalladaMglDto.setListCover(listCover);
                    direccionDetalladaMglDto.setLstTecnologiaSubMgls(tecnologiasCcmm);
                    //Fin Busqueda hhpp de la direccion
                }
            }
        }

        //Busco si la direccion tiene una factibilidad  vigente
        List<FactibilidadMgl> factibilidadVig
                = factibilidadMglFacadeLocal.findFactibilidadVigByDetallada(direccionDetalladaMglDto.getDireccionDetalladaId(), new Date());

        if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
            FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
            direccionDetalladaMglDto.setFactibilidadId(factibilidadMgl.getFactibilidadId());
        }
        //Fin Busqueda si la direccion tiene una factibilidad  vigente
        return direccionDetalladaMglDto;
    }

    public FactibilidadMgl refactibilizar(CmtDireccionDetalladaMgl direccionDetalladaMgl, String usuario) throws ApplicationException {
        DireccionMgl dir = direccionDetalladaMgl.getDireccion();
        CmtDireccionDetalladaMglDto direccionDetalladaMglDto = direccionDetalladaMgl.convertirADto();
        direccionDetalladaMglDto.setDireccionMgl(dir);
        CmtDireccionMglFacadeLocal cmtDireccionMglFacadeLocal = new CmtDireccionMglFacade();
        CmtDireccionMgl direccionCm = cmtDireccionMglFacadeLocal.
                findCmtIdDireccion(dir.getDirId());
        if (direccionCm != null && direccionCm.getCuentaMatrizObj() != null) {
            CmtCuentaMatrizMgl cuenta = direccionCm.getCuentaMatrizObj();
            direccionDetalladaMglDto.setTipoDireccion("CCMM");
            direccionDetalladaMglDto.setCuentaMatrizMgl(cuenta);
        } else {
            direccionDetalladaMglDto.setTipoDireccion("UNIDAD");
        }
        NodoMglFacadeLocal nodoMglFacadeLocal = new NodoMglFacade();
        CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal = new CmtBasicaMglFacade();
        CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal = new CmtTipoBasicaMglFacade();
        SlaEjecucionMglFacadeLocal slaEjecucionMglFacadeLocal = new SlaEjecucionMglFacade();
        DetalleSlaEjeMglFacadeLocal detalleSlaEjeMglFacadeLocal = new DetalleSlaEjeMglFacade();
        OtHhppMglFacadeLocal otHhppMglFacadeLocal = new OtHhppMglFacade();
        DetalleFactibilidadMglFacadeLocal detalleFactibilidadMglFacadeLocal = new DetalleFactibilidadMglFacade();
        FactibilidadMglDaoImpl factibilidadMglDao = new FactibilidadMglDaoImpl();
        FactibilidadMgl factibilidadMgl = new FactibilidadMgl();
        factibilidadMgl.setUsuario(usuario);
        factibilidadMgl.setDireccionDetalladaId(direccionDetalladaMglDto.getDireccionDetalladaId());
        String diasVen = this.consultarParametro(Constants.DIAS_VENCIMIENTO_FACTIBILIDAD);
        int diasForVencer = Integer.parseInt(diasVen);
        factibilidadMgl.setFechaCreacion(new Date());
        Date fechaVence = fechaVencimiento(new Date(), diasForVencer);
        factibilidadMgl.setFechaVencimiento(fechaVence);
        factibilidadMgl = factibilidadMglDao.create(factibilidadMgl);
        //Consulto las coberturas
        direccionDetalladaMglDto = consultarCoberturas(direccionDetalladaMglDto);
        List<CmtCoverDto> listCover = direccionDetalladaMglDto.getListCover();
        //Creo el detalle de la factibilidad
        if (listCover != null && !listCover.isEmpty()) {
            for (CmtCoverDto coverDto : listCover) {
                DetalleFactibilidadMgl detalleFactibilidadMgl = new DetalleFactibilidadMgl();
                detalleFactibilidadMgl.setCodigoNodo(coverDto.getNode());
                detalleFactibilidadMgl.setEstadoNodo(coverDto.getState());
                detalleFactibilidadMgl.setNombreTecnologia(coverDto.getTechnology());
                detalleFactibilidadMgl.setColorTecno(coverDto.getColorTecno());
                //Busco el nodo
                CmtBasicaMgl tecnologia2;
                NodoMgl nodo;
                CmtTipoBasicaMgl cmtTipoBasicaTec = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TECNOLOGIA);
                if (coverDto.getNode() != null) {
                    nodo = nodoMglFacadeLocal.findByCodigo(coverDto.getNode());
                    if (nodo != null) {
                        if (nodo.getFactibilidad() == null) {
                            detalleFactibilidadMgl.setClaseFactibilidad("POSITIVA");
                        } else if (nodo.getFactibilidad().equalsIgnoreCase("P")) {
                            detalleFactibilidadMgl.setClaseFactibilidad("POSITIVA");
                        } else if (nodo.getFactibilidad().equalsIgnoreCase("N")) {
                            detalleFactibilidadMgl.setClaseFactibilidad("NEGATIVA");
                        } else if (!coverDto.isIsCobertura() && nodo.getFactibilidad() == null) {
                            detalleFactibilidadMgl.setClaseFactibilidad("NEGATIVA");
                        }
                        detalleFactibilidadMgl.setSds(nodo.getLimites() != null ? nodo.getLimites() : "");
                        detalleFactibilidadMgl.setProyecto(nodo.getProyecto() != null ? nodo.getProyecto() : "");
                        tecnologia2 = nodo.getNodTipo();
                    } else {
                        detalleFactibilidadMgl.setClaseFactibilidad("NEGATIVA");
                        tecnologia2 = cmtBasicaMglFacadeLocal.findByBasicaCode(coverDto.getTechnology(),
                                cmtTipoBasicaTec.getTipoBasicaId());
                    }
                } else {
                    detalleFactibilidadMgl.setClaseFactibilidad("NEGATIVA");
                    tecnologia2 = cmtBasicaMglFacadeLocal.findByBasicaCode(coverDto.getTechnology(),
                            cmtTipoBasicaTec.getTipoBasicaId());
                }

                CmtBasicaMgl estadoTecno = null;

                if (coverDto.getHhppMgl() != null) {
                    HhppMgl hhppMgl = coverDto.getHhppMgl();
                    EstadoHhppMgl estadoHhpp = hhppMgl.getEhhId();
                    detalleFactibilidadMgl.setEstadoTecnologia(estadoHhpp != null ? estadoHhpp.getEhhNombre() : "NA");

                } else if (coverDto.getTecnologiaSubMgl() != null) {
                    estadoTecno = coverDto.getTecnologiaSubMgl().getBasicaIdEstadosTec();
                    detalleFactibilidadMgl.setEstadoTecnologia(estadoTecno != null ? estadoTecno.getNombreBasica() : "NA");
                }

                //Detalle SLA de ejecucion
                if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("CCMM")
                        && tecnologia2 != null && tecnologia2.getBasicaId() != null) {
                    //consulto sla de ejecucion
                    SlaEjecucionMgl slaEjecucionMgl = slaEjecucionMglFacadeLocal.findByTecnoAndEjecucion(tecnologia2, "CCMM");
                    if (slaEjecucionMgl != null && estadoTecno != null) {
                        DetalleSlaEjecucionMgl detalleSlaEjecucionMgls = detalleSlaEjeMglFacadeLocal.
                                findBySlaEjecucionAndEstCcmmAndTipoOt(slaEjecucionMgl, estadoTecno, null);
                        if (detalleSlaEjecucionMgls != null
                                && detalleSlaEjecucionMgls.getDetSlaEjecucionId() != null) {

                            List<DetalleSlaEjecucionMgl> resulList = detalleSlaEjeMglFacadeLocal.
                                    findDetalleSlaEjecucionMaySecProcesoList(detalleSlaEjecucionMgls, 0);

                            int totalSla = 0;
                            String detalleSla = "";
                            if (resulList != null && !resulList.isEmpty()) {
                                for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl1 : resulList) {
                                    totalSla = totalSla + detalleSlaEjecucionMgl1.getSla();
                                    detalleSla += detalleSlaEjecucionMgl1.getDetSlaEjecucionId().toString() + ",";
                                }
                                detalleFactibilidadMgl.setDetallesSlas(detalleSla);
                            }
                            detalleFactibilidadMgl.setTiempoUltimaLilla(totalSla);
                            detalleFactibilidadMgl.setSlaEjecucionMglObj(slaEjecucionMgl);
                        }
                        detalleFactibilidadMgl.setSlaEjecucionMglObj(slaEjecucionMgl);
                    }
                } else if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("UNIDAD")
                        && tecnologia2 != null && tecnologia2.getBasicaId() != null) {
                    //consulto sla de ejecucion
                    SlaEjecucionMgl slaEjecucionMgl = slaEjecucionMglFacadeLocal.findByTecnoAndEjecucion(tecnologia2, "UNIDAD");
                    List<OtHhppMgl> ordenDir = otHhppMglFacadeLocal.findOtHhppByDireccionAndTecnologias(direccionDetalladaMglDto.getDireccionMgl(),
                            direccionDetalladaMglDto.getSubDireccionMgl(), tecnologia2);

                    if (slaEjecucionMgl != null) {

                        if (ordenDir != null && !ordenDir.isEmpty()) {
                            OtHhppMgl ot = ordenDir.get(0);
                            DetalleSlaEjecucionMgl detalleSlaEjecucionMgls = detalleSlaEjeMglFacadeLocal.
                                    findBySlaEjecucionAndEstCcmmAndTipoOt(slaEjecucionMgl, null, ot.getTipoOtHhppId());

                            if (detalleSlaEjecucionMgls != null
                                    && detalleSlaEjecucionMgls.getDetSlaEjecucionId() != null) {

                                CmtBasicaMgl estadoAbierto
                                        = cmtBasicaMglFacadeLocal.
                                        findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO);
                                CmtBasicaMgl estadoCerrado
                                        = cmtBasicaMglFacadeLocal.
                                        findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO);

                                int control;
                                if (ot.getEstadoGeneral().getBasicaId().compareTo(estadoAbierto.getBasicaId()) == 0) {
                                    control = 1;
                                } else if (ot.getEstadoGeneral().getBasicaId().compareTo(estadoCerrado.getBasicaId()) == 0) {
                                    control = 2;
                                } else {
                                    control = 3;
                                }
                                List<DetalleSlaEjecucionMgl> resulList = detalleSlaEjeMglFacadeLocal.
                                        findDetalleSlaEjecucionMaySecProcesoList(detalleSlaEjecucionMgls, control);

                                int totalSla = 0;
                                String detalleSla = "";
                                if (resulList != null && !resulList.isEmpty()) {
                                    for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl1 : resulList) {
                                        totalSla = totalSla + detalleSlaEjecucionMgl1.getSla();
                                        detalleSla += detalleSlaEjecucionMgl1.getDetSlaEjecucionId().toString() + ",";
                                    }
                                    detalleFactibilidadMgl.setDetallesSlas(detalleSla);
                                }
                                detalleFactibilidadMgl.setTiempoUltimaLilla(totalSla);
                            }
                        } else {
                            //No hay orden de trabajo
                            int totalSla = 0;
                            String detalleSla = "";
                            List<DetalleSlaEjecucionMgl> lstDetalleSlaEjecucionMgls = slaEjecucionMgl.getLstDetalleSlaEjecucionMgls();
                            for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl : lstDetalleSlaEjecucionMgls) {
                                if (detalleSlaEjecucionMgl.getEstadoRegistro() == 1) {
                                    totalSla = totalSla + detalleSlaEjecucionMgl.getSla();
                                    detalleSla += detalleSlaEjecucionMgl.getDetSlaEjecucionId().toString() + ",";
                                }
                            }
                            detalleFactibilidadMgl.setDetallesSlas(detalleSla);
                            detalleFactibilidadMgl.setTiempoUltimaLilla(totalSla);
                        }

                        detalleFactibilidadMgl.setSlaEjecucionMglObj(slaEjecucionMgl);
                    }
                }

                detalleFactibilidadMgl.setFactibilidadMglObj(factibilidadMgl);
                if (coverDto.isHhppExistente()) {
                    detalleFactibilidadMgl.setEsHhpp("1");
                } else {
                    detalleFactibilidadMgl.setEsHhpp("0");
                }
                detalleFactibilidadMglFacadeLocal.create(detalleFactibilidadMgl);
            }
        }
        return factibilidadMgl;
    }

    public ConsultaFactibilidadResponseDto consultaFactibilidad(ConsultaFactibilidadRequestDto consultaFactibilidadRequestDto) {
        ConsultaFactibilidadResponseDto respuesta = new ConsultaFactibilidadResponseDto();
        Initialized.getInstance();
        try {
            if (consultaFactibilidadRequestDto != null) {
                if (StringUtils.isEmpty(consultaFactibilidadRequestDto.getCodigoConsulta()) && StringUtils.isEmpty(consultaFactibilidadRequestDto.getIdFactibilidad()) && StringUtils.isEmpty(consultaFactibilidadRequestDto.getIdDireccion())) {
                    respuesta.setMessage("Error: Debe ingresa el codigo de consulta, id de dirección o el id de factibilidad.");
                    respuesta.setMessageType("E");
                    return respuesta;
                }
                BigDecimal idFactibilidad = null;
                CmtDireccionDetalleMglDaoImpl direccionDetalleMglDaoImpl = new CmtDireccionDetalleMglDaoImpl();
                CmtDireccionDetalladaMgl direccionDetallada;
                FactibilidadMglDaoImpl factibilidadMglDao = new FactibilidadMglDaoImpl();
                FactibilidadMgl factibilidad;
                if (!StringUtils.isEmpty(consultaFactibilidadRequestDto.getIdDireccion())) {
                    try {
                        direccionDetallada = direccionDetalleMglDaoImpl.buscarDireccionIdDireccion(BigDecimal.valueOf(Long.parseLong(consultaFactibilidadRequestDto.getIdDireccion())));
                        if (direccionDetallada != null && direccionDetallada.getDireccionDetalladaId() != null) {
                            factibilidad = refactibilizar(direccionDetallada, direccionDetallada.getUsuarioCreacion());
                            if (factibilidad == null) {
                                respuesta.setMessage("Error: ".concat("Error al refactibilizar la direccion con el id ").concat(consultaFactibilidadRequestDto.getIdDireccion()));
                                respuesta.setMessageType("E");
                                return respuesta;
                            }
                        } else {
                            respuesta.setMessage("Error: ".concat("No existe la direccion detallada con el id ").concat(consultaFactibilidadRequestDto.getIdDireccion()));
                            respuesta.setMessageType("E");
                            return respuesta;
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error refactibilizar de CmtDireccionDetalleMglManager " + e.getMessage());
                        respuesta.setMessage("Error: ".concat("Error refactibilizando la direccion con el id ").concat(consultaFactibilidadRequestDto.getIdDireccion()));
                        respuesta.setMessageType("E");
                        return respuesta;
                    }
                } else {
                    if (StringUtils.isEmpty(consultaFactibilidadRequestDto.getCodigoConsulta())) {
                        idFactibilidad = BigDecimal.valueOf(Long.parseLong(consultaFactibilidadRequestDto.getIdFactibilidad()));
                    } else {
                        VisorFactibilidadDaoImpl visorFactibilidadMglDao = new VisorFactibilidadDaoImpl();
                        idFactibilidad = visorFactibilidadMglDao.findIdFactibilidadByCodigo(consultaFactibilidadRequestDto.getCodigoConsulta());
                    }
                    factibilidad = factibilidadMglDao.findFactibilidadById(idFactibilidad);
                    direccionDetallada = direccionDetalleMglDaoImpl.buscarDireccionIdDireccion(factibilidad.getDireccionDetalladaId());
                }
                if (factibilidad != null && factibilidad.getFactibilidadId() != null) {
                    if (direccionDetallada != null && direccionDetallada.getDireccionDetalladaId() != null) {
                        respuesta.setFactibilidadId(String.valueOf(factibilidad.getFactibilidadId()));
                        if (direccionDetallada.getDireccion() != null && direccionDetallada.getDireccion().getUbicacion() != null) {
                            respuesta.setCodigoDane(direccionDetallada.getDireccion().getUbicacion().getGpoIdObj() == null ? null : direccionDetallada.getDireccion().getUbicacion().getGpoIdObj().getGeoCodigoDane());
                            respuesta.setLatitud(direccionDetallada.getDireccion().getUbicacion().getUbiLatitud());
                            respuesta.setLongitud(direccionDetallada.getDireccion().getUbicacion().getUbiLongitud());
                        }
                        respuesta.setDireccionEstandarizada(direccionDetallada.getDireccionTexto());

                        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
                        CmtDireccionMgl cmtDireccionMgl = cmtDireccionMglManager.findById(direccionDetallada.getDireccionId());
                        respuesta.setCcmId((cmtDireccionMgl == null || cmtDireccionMgl.getCuentaMatrizObj() == null) ? null : String.valueOf(cmtDireccionMgl.getCuentaMatrizObj().getCuentaMatrizId()));
                        respuesta.setDireccionId(String.valueOf(direccionDetallada.getDireccionDetalladaId()));

                        DetalleFactibilidadMglDaoImpl detalleFactibilidadMglDao = new DetalleFactibilidadMglDaoImpl();
                        List<DetalleFactibilidadMgl> listDetalleFactibilidad = detalleFactibilidadMglDao.findListDetalleFactibilidad(factibilidad.getFactibilidadId());

                        FactibilidadesDto factibilidadesDto = new FactibilidadesDto();

                        for (DetalleFactibilidadMgl detalleFactibilidadMgl : listDetalleFactibilidad) {
                            ListaTecnologiaDto tecnologiaDto = new ListaTecnologiaDto();
                            tecnologiaDto.setCodigoTecnologia(detalleFactibilidadMgl.getNombreTecnologia());
                            tecnologiaDto.setEstado(detalleFactibilidadMgl.getEstadoTecnologia());
                            tecnologiaDto.setNodo(detalleFactibilidadMgl.getCodigoNodo());
                            tecnologiaDto.setEstadoNodo(detalleFactibilidadMgl.getEstadoNodo());
                            tecnologiaDto.setFactibilidad(detalleFactibilidadMgl.getClaseFactibilidad());
                            tecnologiaDto.setDistanciaOffNet(detalleFactibilidadMgl.getDistanciaNodoApro());
                            tecnologiaDto.setNodoBakup(detalleFactibilidadMgl.getNodoMglBackup() == null ? null : detalleFactibilidadMgl.getNodoMglBackup().getNodCodigo());
                            tecnologiaDto.setSds(detalleFactibilidadMgl.getSds());
                            tecnologiaDto.setSdsBackup(detalleFactibilidadMgl.getNodoMglBackup() == null ? null : detalleFactibilidadMgl.getNodoMglBackup().getLimites());

                            ArrendatariosDto arrendatariosDto = new ArrendatariosDto();
                            if (detalleFactibilidadMgl.getArrendatarioMgl() != null) {
                                ArrendatarioDto arrendatarioDto = new ArrendatarioDto();
                                arrendatarioDto.setNombre(detalleFactibilidadMgl.getArrendatarioMgl().getNombreArrendatario());
                                arrendatarioDto.setCuadrante(detalleFactibilidadMgl.getArrendatarioMgl().getCuadrante());
                                arrendatariosDto.getArrendatario().add(arrendatarioDto);
                            }
                            tecnologiaDto.setArrendatarios(arrendatariosDto);
                            tecnologiaDto.setTiempoAproximadoInstalacion(String.valueOf(detalleFactibilidadMgl.getTiempoUltimaLilla()));
                            factibilidadesDto.getListaTecnologia().add(tecnologiaDto);
                        }
                        respuesta.setFactibilidades(factibilidadesDto);
                        respuesta.setMessage("Operacion Exitosa");
                        respuesta.setMessageType("I");
                    } else {
                        respuesta.setMessage("Error: ".concat("No existe la direccion detallada con el id ").concat(factibilidad.getDireccionDetalladaId().toString()));
                        respuesta.setMessageType("E");
                        return respuesta;
                    }
                } else {
                    respuesta.setMessage("Error: ".concat("No existe la factibilidad con el id ").concat(idFactibilidad.toString()));
                    respuesta.setMessageType("E");
                    return respuesta;
                }
            } else {
                respuesta.setMessage("Error: Debe construir una petición para consumir el servicio.");
                respuesta.setMessageType("E");
                return respuesta;
            }
        } catch (Exception e) {
            LOGGER.error("Error consultaFactibilidad de CmtDireccionDetalleMglManager " + e.getMessage());
            respuesta.setMessage("Error: Ha fallado la consulta de factibilidad.");
            respuesta.setMessageType("E");
            return respuesta;
        }
        return respuesta;
    }

    /* Brief 98062 */
    public DrDireccion getDrDireccionConsultaSitiData() {
        return drDireccionConsultaSitiData;
    }

    public void setDrDireccionConsultaSitiData(DrDireccion drDireccionConsultaSitiData) {
        this.drDireccionConsultaSitiData = drDireccionConsultaSitiData;
    }
    /* Cierre Brief 98062 */
	
	 /**
     * Identifica el flujo del metodo consultarNodosPorRecurso. Contiene el flujo
     * detallado de la implementacion para el metodo consultarNodosPorRecurso y registro
     * de log.
     *
     * @author Hitts - Leidy Montero
     * @param consultarNodosPorRecursoRequest parametros iniciales
     * @return retorna el CmtAddressResponseDto del servicio
     */
    public CmtAddressResponseDto consultarNodosPorRecurso(ConsultarNodosPorRecursoRequest consultarNodosPorRecursoRequest) {
            Initialized.getInstance();     
            CmtAddressResponseDto response = new CmtAddressResponseDto();
            if (consultarNodosPorRecursoRequest.getType()== null || consultarNodosPorRecursoRequest.getType().trim().equals("") 
                || consultarNodosPorRecursoRequest.getValue()== null || consultarNodosPorRecursoRequest.getValue().trim().equals("") 
                || !isNumeric(consultarNodosPorRecursoRequest.getValue())) {
                response.setMessage("Error: Los parametros estan mal ingresados.");
                response.setMessageType("E");
                return response;
            }
        try {
            ConsultaProcedimientos consultaProcedimientos = new ConsultaProcedimientos();  
            CmtDireccionDetalladaRequestDto request1 = new CmtDireccionDetalladaRequestDto();
            request1.setIdDireccion(consultaProcedimientos.PrcConsultaTecNodo(consultarNodosPorRecursoRequest));
            response = ConsultaDireccion(request1);           
        } catch (ApplicationException ex) {
            response.setMessage("Error: ver logs para mas informacion. "+ex);
            response.setMessageType("E");
        }
        return response;
    }
}
