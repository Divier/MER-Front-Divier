package co.com.claro.mgl.facade.procesomasivo;

import co.com.claro.mer.dtos.request.procedure.TblLineasMasivoResquestDto;
import co.com.claro.mer.dtos.response.procedure.TblLineasMasivoResponseDto;
import co.com.claro.mer.dtos.sp.cursors.*;
import co.com.claro.mgl.error.ApplicationException;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *     interfaz que contiene los metodos para el manejo de los cargues masivos.
 *     <br>
 *     <b>Nota: </b> Esta interfaz es implementada por la clase CarguesMasivosFacade
 * </p>
 * @see CarguesMasivosFacade para más información de los metodos
 * @autor Manuel Hernández Rivas
 */
public interface CarguesMasivosFacadeLocal {

    List<OpcionesCarguesDto> contblMasivosNombresSp(String listaRoles) throws ApplicationException;

    List<InfoGeneralCargueDto> contblMasivosSp(String nombreProceso) throws ApplicationException;

    List<CargueInformacionDto> contblLineasMasivoSp(String nombre, String tipo, BigDecimal id) throws ApplicationException;

    List<CamposFiltrosDto> conFiltrosSp(BigDecimal id) throws ApplicationException;

    List<ListaItemsFiltrosDto> consultarListasFiltros(BigDecimal idColumna, String idConsulta) throws ApplicationException;

    TblLineasMasivoResponseDto tblLineasMasivoSp(TblLineasMasivoResquestDto resquestDto) throws ApplicationException;
}
