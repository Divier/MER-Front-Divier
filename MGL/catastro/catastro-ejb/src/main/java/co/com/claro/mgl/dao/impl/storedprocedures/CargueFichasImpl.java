/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.storedprocedures;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.ConsultaPrefichaXlsNewRequestDto;
import co.com.claro.mer.dtos.request.procedure.CrudTecPrefichaNewRequestDto;
import co.com.claro.mer.dtos.request.procedure.CrudTecPrefichaGeoNewRequestDto;
import co.com.claro.mer.dtos.response.procedure.ConsultaPrefichaXlsNewResponseDto;
import co.com.claro.mer.dtos.response.procedure.CrudTecPrefichaNewResponseDto;
import co.com.claro.mer.dtos.response.procedure.CrudTecPrefichaGeoNewResponseDto;
import co.com.claro.mgl.dtos.ConsultaPrefichaXlsNewDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressServiceBatchXml;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Implementación de lógica asociada al consumo de plsql 
 * para modulo de Cargue de Fichas
 *
 * @author Johan Gomez
 * @version 1.1, 2023/07/18 Rev gomezjoj
 */
public class CargueFichasImpl {
    
    private static final Logger LOGGER = LogManager.getLogger(CargueFichasImpl.class);
    private static final String OPCION_UPDATE_PREFICHA = "U";
    
    public int updatePrefichaGeo(BigDecimal idPreficha) throws ApplicationException{
        
        try{    
            StoredProcedureUtil sp = new StoredProcedureUtil(StoredProcedureNamesConstants.SP_TEC_UPDATE_PREFICHA_PRC);
            CrudTecPrefichaNewRequestDto requestDto = new CrudTecPrefichaNewRequestDto();
            assignParametersToUpdatePreficha(idPreficha, OPCION_UPDATE_PREFICHA, requestDto);
            sp.addRequestData(requestDto);
            CrudTecPrefichaNewResponseDto responseDto = sp.executeStoredProcedure(CrudTecPrefichaNewResponseDto.class);
            int codigoRespuesta = responseDto.getCodigo() != null ? responseDto.getCodigo() : 1;
            
            if(codigoRespuesta != 0){
                     throw new ApplicationException("Error ejecutando el Procedimiento almacenado.." +StoredProcedureNamesConstants.SP_TEC_UPDATE_PREFICHA_PRC + 
                             ClassUtils.getCurrentMethodName(this.getClass()));
                }
            return codigoRespuesta;
        
        } catch (ApplicationException ex) {
            String msgError = "Error ejecutando el Procedimiento almacenado.." +StoredProcedureNamesConstants.SP_TEC_CONSULTA_PREFICHA;
            LOGGER.error(msgError, ex.getMessage());
            throw ex;

        } catch (Exception e) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }
    
    public int tecInsertGeoreferencia(List<AddressServiceBatchXml> addressService, String usuarioVT) throws ApplicationException{
        
        int codigoRespuesta = -1;
        try{    
            StoredProcedureUtil sp = new StoredProcedureUtil(StoredProcedureNamesConstants.SP_TEC_INSERT_GEOREFERENCIA_PRC);
            
            for( AddressServiceBatchXml addressGeo : addressService){
                
                CrudTecPrefichaGeoNewRequestDto requestDto = assignParametersToInsertGeoreferencia(addressGeo, usuarioVT);
                sp.addRequestData(requestDto);
                CrudTecPrefichaGeoNewResponseDto responseDto = sp.executeStoredProcedure(CrudTecPrefichaGeoNewResponseDto.class);
                
                String msgRespuesta = responseDto.getDescripcion();
                codigoRespuesta = responseDto.getCodigo() != null ? responseDto.getCodigo() : 1;
                
                if(codigoRespuesta != 0){
                     throw new ApplicationException("Error ejecutando el Procedimiento almacenado.." +StoredProcedureNamesConstants.SP_TEC_CONSULTA_PREFICHA + 
                             ClassUtils.getCurrentMethodName(this.getClass()));
                }
                sp.clearInputData();
            }
            
            return codigoRespuesta;
        
        } catch (ApplicationException ex) {
            String msgError = "Error ejecutando el Procedimiento almacenado.." +StoredProcedureNamesConstants.SP_TEC_CONSULTA_PREFICHA;
            LOGGER.error(msgError, ex.getMessage());
            throw ex;

        } catch (Exception e) {
            String msgError = "Error ejecutando el Procedimiento almacenado.." +StoredProcedureNamesConstants.SP_TEC_CONSULTA_PREFICHA;
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }
    
    private void assignParametersToUpdatePreficha(BigDecimal idPreficha, String opcion, CrudTecPrefichaNewRequestDto request){
        
        request.setOpcion(opcion);
        request.setPrefichaId(idPreficha);
        request.setGeorreferenciada(1);
        
        request.setUsuarioGenera(null);
        request.setFechaGenera(null);
        request.setUsuarioValidacion(null);
        request.setFechaValidacion(null);
        request.setUsuarioModifica(null);
        request.setFechaModifica(null);
        request.setFase(null);
        request.setNombreArchivo(null);
        request.setFichaCreada(null);
        request.setFechaCreacion(null);
        request.setUsuarioCreacion(null);
        request.setPerfilCreacion(null);
        request.setFechaEdicion(null);
        request.setUsuarioEdicion(null);
        request.setPerfilEdicion(null);
        request.setEstadoRegistro(null);
        request.setMarcas(null);
        request.setNota(null);
        request.setCantidadRegistros(null);
        request.setCantidadProcesados(null);
        request.setCantidadError(null);
        request.setObservacion(null);
    
    }
    
    private CrudTecPrefichaGeoNewRequestDto assignParametersToInsertGeoreferencia(AddressServiceBatchXml addressServiceGeo, String usuarioVT){
        
        Date currentDate = new Date();
        CrudTecPrefichaGeoNewRequestDto request = new CrudTecPrefichaGeoNewRequestDto();
        /* Asignación de valores */
        request.setPrefichaXlsId(Integer.parseInt(addressServiceGeo.getRespuestaGeo().getIdentificador()));
        request.setPrefichaTecHabDetaId(0);
        request.setActivityEconomic(addressServiceGeo.getActivityeconomic());
        request.setAddress(addressServiceGeo.getAddress());
        request.setAddressCode(addressServiceGeo.getAddressCode());
        request.setAddressCodeFound(addressServiceGeo.getAddressCodeFound());
        request.setAlternateAddress(addressServiceGeo.getAlternateaddress());
        request.setAppletStandar(addressServiceGeo.getAppletstandar());
        request.setCategory(addressServiceGeo.getCategory());
        request.setChageNumber(addressServiceGeo.getChagenumber());
        request.setCodDaneMcpio(addressServiceGeo.getCoddanemcpio());
        request.setCx(addressServiceGeo.getCx());
        request.setCy(addressServiceGeo.getCy());
        request.setDaneCity(addressServiceGeo.getDaneCity());
        request.setDanePopArea(addressServiceGeo.getDanePopulatedArea());
        request.setExist(addressServiceGeo.getExist());
        request.setIdAddress(addressServiceGeo.getIdaddress());
        
        if (addressServiceGeo.getLeveleconomic() == null || addressServiceGeo.getLeveleconomic().trim().isEmpty()) {
            addressServiceGeo.setLeveleconomic("-1");
        }
        request.setLevelEconomic(addressServiceGeo.getLeveleconomic());
        request.setLevelLive(addressServiceGeo.getLevellive());
        request.setLocality(addressServiceGeo.getLocality());
        request.setNeighborhood(addressServiceGeo.getNeighborhood());
        request.setNodoDos(addressServiceGeo.getNodoDos());
        request.setNodoTres(addressServiceGeo.getNodoTres());
        request.setNodoUno(addressServiceGeo.getNodoUno());
        request.setQualifiers(addressServiceGeo.getQualifiers());
        request.setSource(addressServiceGeo.getSource());
        request.setStateDef(addressServiceGeo.getState());
        request.setTraslate(addressServiceGeo.getTraslate());
        request.setZipCode(addressServiceGeo.getZipCode());
        request.setZipCodeDistrict(addressServiceGeo.getZipCodeDistrict());
        request.setZipCodeState(addressServiceGeo.getZipCodeState());
        request.setFechaCreacion(currentDate);
        request.setUsuarioCreacion(usuarioVT);
        request.setFechaEdicion(currentDate);
        request.setUsuarioEdicion(usuarioVT);
        request.setEstadoRegistro(1);
        request.setCambioEstrato(null);
        request.setConfiabilidad(null);
        request.setConfiabilidadComplemento(null);
        
        return request;
    }
    
    public List<ConsultaPrefichaXlsNewDto> tecConsultaPrefichaXlsNew(BigDecimal linMasivoId) throws ApplicationException{
        try{    
            StoredProcedureUtil sp = new StoredProcedureUtil(StoredProcedureNamesConstants.SP_TEC_CONSULTA_PREFICHA);
            ConsultaPrefichaXlsNewRequestDto requestDto = new ConsultaPrefichaXlsNewRequestDto();
            requestDto.setPrefichaXlsId(linMasivoId);
            sp.addRequestData(requestDto);
            
            ConsultaPrefichaXlsNewResponseDto responseDto = sp.executeStoredProcedure(ConsultaPrefichaXlsNewResponseDto.class);
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            List<ConsultaPrefichaXlsNewDto> listaPrefichaXlsNewDto = new ArrayList<>();
            
            responseDto.getPrefichaXls().forEach(resultado -> {
                ConsultaPrefichaXlsNewDto xlsNew = new ConsultaPrefichaXlsNewDto();
                if(resultado.getPrefichaXlsId() != null){
                    xlsNew.setPrefichaXlsId(resultado.getPrefichaXlsId().toString());
                    xlsNew.setTrazaId(resultado.getTrazaId().toString());
                    xlsNew.setDireccion(resultado.getDireccion());
                    xlsNew.setCodigoDane(resultado.getCodigoDane());
                    xlsNew.setBarrio(resultado.getBarrio());
                    xlsNew.setFechaCreacion(df.format(resultado.getFechaCreacion()));
                    xlsNew.setUsuaCreacion(resultado.getUsuaCreacion());
                    xlsNew.setEstadoRegistro(resultado.getEstadoRegistro().toString());
                    xlsNew.setFechaEdicion(df.format(resultado.getFechaEdicion()));
                    xlsNew.setUsuaEdicion(resultado.getUsuaEdicion());
                    listaPrefichaXlsNewDto.add(xlsNew);
                }
            });
            
            return listaPrefichaXlsNewDto;
        
        } catch (ApplicationException ex) {
            String msgError = "Error ejecutando el Procedimiento almacenado.." +StoredProcedureNamesConstants.SP_TEC_CONSULTA_PREFICHA;
            LOGGER.error(msgError, ex.getMessage());
            throw ex;

        } catch (Exception e) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }
}
