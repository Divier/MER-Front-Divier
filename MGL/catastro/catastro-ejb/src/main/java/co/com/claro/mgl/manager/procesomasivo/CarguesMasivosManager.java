package co.com.claro.mgl.manager.procesomasivo;

import co.com.claro.mer.dtos.request.procedure.TblLineasMasivoResquestDto;
import co.com.claro.mer.dtos.response.procedure.TblLineasMasivoResponseDto;
import co.com.claro.mer.dtos.sp.cursors.*;
import co.com.claro.mgl.dao.procesomasivo.CarguesMasivosImpl;
import co.com.claro.mgl.error.ApplicationException;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *     Clase que implementa los metodos de la interfaz CarguesMasivosFacadeLocal
 *     para el manejo de los cargues masivos.
 * </p>
 * @see co.com.claro.mgl.facade.procesomasivo.CarguesMasivosFacadeLocal para más información de los metodos
 * @see co.com.claro.mgl.dao.procesomasivo.CarguesMasivosImpl para más información de los metodos de la capa de datos
 * @autor Manuel Hernández Rivas
 */
public class CarguesMasivosManager {

    private CarguesMasivosImpl carguesMasivosImp(){
        return new CarguesMasivosImpl();
    }

    public List<OpcionesCarguesDto> contblMasivosNombresSp(String listaRoles) throws ApplicationException {
        return carguesMasivosImp().contblMasivosNombresSp(listaRoles);
    }

    public List<InfoGeneralCargueDto> contblMasivosSp(String nombreProceso) throws ApplicationException {
        return carguesMasivosImp().contblMasivosSp(nombreProceso);
    }

    public List<CargueInformacionDto> contblLineasMasivoSp(String nombre, String tipo, BigDecimal id) throws ApplicationException {
        return carguesMasivosImp().contblLineasMasivoSp(nombre, tipo, id);
    }

    public List<CamposFiltrosDto> conFiltrosSp(BigDecimal id) throws ApplicationException {
        return carguesMasivosImp().conFiltrosSp(id);
    }

    public List<ListaItemsFiltrosDto> consultarListasFiltros(BigDecimal idColumna, String idConsulta) throws ApplicationException {
        return carguesMasivosImp().consultarListasFiltros(idColumna, idConsulta);
    }

    public TblLineasMasivoResponseDto tblLineasMasivoSp(TblLineasMasivoResquestDto resquestDto) throws ApplicationException {
        return carguesMasivosImp().tblLineasMasivoSp(resquestDto);
    }
}
