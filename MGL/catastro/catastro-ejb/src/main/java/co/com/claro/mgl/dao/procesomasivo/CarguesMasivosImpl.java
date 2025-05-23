/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.procesomasivo;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.ConTblLineasMasivoRequestDto;
import co.com.claro.mer.dtos.request.procedure.ConTblMasivosRequestDto;
import co.com.claro.mer.dtos.request.procedure.TblLineasMasivoResquestDto;
import co.com.claro.mer.dtos.response.procedure.*;
import co.com.claro.mer.dtos.sp.cursors.*;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static co.com.claro.mer.utils.constants.ConstantsCargueMasivo.ORIGEN_MASIVOS;
import static co.com.claro.mer.utils.constants.ConstantsCargueMasivo.ARCHIVO_DUPLICADO;
import static co.com.claro.mer.utils.constants.ConstantsCargueMasivo.RESULTADO_EXITOSO;
/**
 * Clase tipo DAO que permite acceder a los datos de las tablas ejecutando
 * paquetes de base de datos.
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
@Log4j2
public class CarguesMasivosImpl {


    /**
     * <p>
     *  Metodo que permite ejecutar el procedimiento almacenado CON_TBL_MASIVOS_NOMBRES_SP
     * </p>
     * @return List<String> Lista con la respuesta del procedimiento almacenado
     * @throws ApplicationException Excepcion en caso de error en el procedimiento almacenado
     * @see co.com.claro.mer.dtos.response.procedure.ConTblMasivosNombresResponseDto más información de la respuesta
     * @see StoredProcedureUtil  más información de la ejecucion del procedimiento almacenado
     * @see StoredProcedureNamesConstants  más información de los nombres de los procedimientos almacenados
     * @see ParametrosMerUtil  más información de los parametros de MER
     * @autor Manuel Hernández Rivas
     */
    public List<OpcionesCarguesDto> contblMasivosNombresSp(String listaRoles) throws ApplicationException {

        final String PI_ORIGEN="PI_ORIGEN";
        final String PI_ROLES="PI_ROLES";
        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CON_TBL_MASIVOS_NOMBRES_SP);

        int origen = Integer.parseInt(
                ParametrosMerUtil.findValor(ORIGEN_MASIVOS)
        );

        storedProcedureUtil.addRequestData(PI_ORIGEN, Integer.class, origen);
        storedProcedureUtil.addRequestData(PI_ROLES, String.class, listaRoles);
        ConTblMasivosNombresResponseDto responseDto = storedProcedureUtil.executeStoredProcedure(ConTblMasivosNombresResponseDto.class);
        if (responseDto.getCodigo() == 0){
            return responseDto.getReportes();
        }

        String msgError = "Error en el procedimiento almacenado "
                +StoredProcedureNamesConstants.CON_TBL_MASIVOS_NOMBRES_SP
                +": " + responseDto.getResultado();

        LOGGER.error(msgError);
        throw new ApplicationException(msgError);
    }

    /**
     * <p>
     *     Metodo que permite ejecutar el procedimiento almacenado CON_TBL_MASIVOS_SP
     * </p>
     * @param nombreProceso nombre del proceso a consultar
     * @return List<InfoGeneralCargueDto> Lista con la información del proceso consultado
     * @throws ApplicationException Excepcion en caso de error en el procedimiento almacenado
     * @see co.com.claro.mer.dtos.request.procedure.ConTblMasivosRequestDto más información de la petición
     * @see co.com.claro.mer.dtos.response.procedure.ConTblMasivosResponseDto más información de la respuesta
     * @see StoredProcedureUtil  más información de la ejecucion del procedimiento almacenado
     * @see StoredProcedureNamesConstants  más información de los nombres de los procedimientos almacenados
     * @autor Manuel Hernández Rivas
     */
    public List<InfoGeneralCargueDto> contblMasivosSp(String nombreProceso) throws ApplicationException {

        ConTblMasivosRequestDto requestDto = new ConTblMasivosRequestDto(nombreProceso);

        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CON_TBL_MASIVOS_SP);
        storedProcedureUtil.addRequestData(requestDto);
        ConTblMasivosResponseDto response = storedProcedureUtil.executeStoredProcedure(ConTblMasivosResponseDto.class);

        if (response.getCodigo().equals(RESULTADO_EXITOSO)) {
            return response.getReportes();
        }
        String msgError = "Error en el procedimiento almacenado "
                +StoredProcedureNamesConstants.CON_TBL_MASIVOS_SP
                +": " + response.getResultado();

        LOGGER.error(msgError);
        throw new ApplicationException(msgError);

    }

    /**
     * <p>
     *     Metodo que permite ejecutar el procedimiento almacenado CON_TBL_LINEAS_MASIVO_SP
     * </p>
     * @param nombre nombre del proceso a consultar
     * @param tipo tipo de proceso a consultar
     * @param id id del proceso a consultar
     * @return List<CargueInformacionDto> Lista con la información del proceso consultado
     * @throws ApplicationException Excepcion en caso de error en el procedimiento almacenado
     * @see co.com.claro.mer.dtos.request.procedure.ConTblLineasMasivoRequestDto más información de la petición
     * @see co.com.claro.mer.dtos.response.procedure.ConTblLineasMasivoResponseDto más información de la respuesta
     * @see StoredProcedureUtil  más información de la ejecucion del procedimiento almacenado
     * @see StoredProcedureNamesConstants  más información de los nombres de los procedimientos almacenados
     * @autor Manuel Hernández Rivas
     */
    public List<CargueInformacionDto> contblLineasMasivoSp(String nombre, String tipo, BigDecimal id) throws ApplicationException {
        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CON_TBL_LINEAS_MASIVO_SP);

        ConTblLineasMasivoRequestDto requestDto = new ConTblLineasMasivoRequestDto(nombre, tipo, id, StringToolUtils.getUsuarioLogueado());
        storedProcedureUtil.addRequestData(requestDto);
        ConTblLineasMasivoResponseDto response = storedProcedureUtil.executeStoredProcedure(ConTblLineasMasivoResponseDto.class);

        if (response.getCodigo().equals(RESULTADO_EXITOSO)) {
            return response.getCargueInformacionDtoList();
        }
        throw new ApplicationException(response.getResultado());

    }

    /**
     * <p>
     *     Metodo que permite ejecutar el procedimiento almacenado CON_FILTROS_SP
     * </p>
     * @param id id del proceso a consultar
     * @return List<CamposFiltrosDto> Lista con la información del proceso consultado
     * @throws ApplicationException Excepcion en caso de error en el procedimiento almacenado
     * @see co.com.claro.mer.dtos.response.procedure.ConFiltrosResponseDto más información de la respuesta
     * @see StoredProcedureUtil  más información de la ejecucion del procedimiento almacenado
     * @see StoredProcedureNamesConstants  más información de los nombres de los procedimientos almacenados
     * @autor Manuel Hernández Rivas
     */
    public List<CamposFiltrosDto> conFiltrosSp(BigDecimal id) throws ApplicationException {
        final String PI_MASIVO_ID = "PI_MASIVO_ID";
        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CON_FILTROS_SP);

        try {
            storedProcedureUtil.addRequestData(PI_MASIVO_ID, BigDecimal.class, id);
            ConFiltrosResponseDto responseDto = storedProcedureUtil.executeStoredProcedure(ConFiltrosResponseDto.class);

            if (responseDto.getCodigo().equals(RESULTADO_EXITOSO)){
                List<CamposFiltrosDto> response = new ArrayList<>();
                for (ConFiltrosCursorDto conFiltrosCursorDto : responseDto.getCursorDto()) {
                    response.add(new CamposFiltrosDto(conFiltrosCursorDto));
                }
                return response;
            }

            String msgError = "Error en el procedimiento almacenado "
                    +StoredProcedureNamesConstants.CON_FILTROS_SP
                    +": " + responseDto.getResultado();

            LOGGER.error(msgError);
            throw new ApplicationException(msgError);
        } catch (ApplicationException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * <p>
     *     Metodo que permite ejecutar el procedimiento almacenado LISTA_FILTROS_SP para consultar las listas de filtros tipo select
     * </p>
     * @param idColumna id masivo de la lista a consultar
     * @param idConsulta id del elemento padre selecciono para la consulta
     * @return List<ListaItemsFiltrosDto> Lista con la información de los items de la lista consultada
     * @throws ApplicationException Excepcion en caso de error en el procedimiento almacenado
     * @see co.com.claro.mer.dtos.response.procedure.ListaFiltrosResponseDto DTO de respuesta del procedimiento almacenado
     * @see StoredProcedureUtil
     * @see StoredProcedureNamesConstants
     * @author Manuel Hernández Rivas
     */
    public List<ListaItemsFiltrosDto> consultarListasFiltros(BigDecimal idColumna, String idConsulta) throws ApplicationException{
        final String PI_ID_COLUMNA = "PI_ID_COLUMNA";
        final String PI_ID_PADRE = "PI_PADRE";

        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.LISTA_FILTROS_SP);
        storedProcedureUtil.addRequestData(PI_ID_COLUMNA, BigDecimal.class, idColumna);
        storedProcedureUtil.addRequestData(PI_ID_PADRE, String.class, idConsulta);

        ListaFiltrosResponseDto responseDto = storedProcedureUtil.executeStoredProcedure(ListaFiltrosResponseDto.class);

        if (responseDto.getCodigo().equals(RESULTADO_EXITOSO)){
            return responseDto.getCursor();
        }

        String msgError = "Error en el procedimiento almacenado "
                +StoredProcedureNamesConstants.LISTA_FILTROS_SP
                +": " + responseDto.getResultado();

        LOGGER.error(msgError);
        throw new ApplicationException(msgError);
    }

    public TblLineasMasivoResponseDto tblLineasMasivoSp(TblLineasMasivoResquestDto resquestDto) throws ApplicationException{

        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.TBL_LINEAS_MASIVO_SP);
        storedProcedureUtil.addRequestData(resquestDto);
        TblLineasMasivoResponseDto responseDto = storedProcedureUtil.executeStoredProcedure(TblLineasMasivoResponseDto.class);

        if (responseDto.getPoCodigo().equals(RESULTADO_EXITOSO) || responseDto.getPoCodigo().equals(ARCHIVO_DUPLICADO)){
             return responseDto;
        }

        String msgError = "Error en el procedimiento almacenado "
                +StoredProcedureNamesConstants.TBL_LINEAS_MASIVO_SP
                +": " + responseDto.getPoResultado();
        LOGGER.error(msgError);
        throw new ApplicationException(msgError);
    }

}
