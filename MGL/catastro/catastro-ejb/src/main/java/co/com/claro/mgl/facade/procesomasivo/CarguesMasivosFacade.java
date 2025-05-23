package co.com.claro.mgl.facade.procesomasivo;

import co.com.claro.mer.dtos.request.procedure.TblLineasMasivoResquestDto;
import co.com.claro.mer.dtos.response.procedure.TblLineasMasivoResponseDto;
import co.com.claro.mer.dtos.sp.cursors.*;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.manager.procesomasivo.CarguesMasivosManager;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *     Clase que implementa los metodos de la interfaz CarguesMasivosFacadeLocal
 *     para el manejo de los cargues masivos.
 * </p>
 * @see CarguesMasivosFacadeLocal para mas informacion de los metodos
 * @see CarguesMasivosManager para mas informacion de los metodos
 * @autor Manuel Hernández Rivas
 */
@Stateless
public class CarguesMasivosFacade implements CarguesMasivosFacadeLocal{

    private CarguesMasivosManager getManager(){
        return new CarguesMasivosManager();
    }
    @Override
    public List<OpcionesCarguesDto> contblMasivosNombresSp(String listaRoles) throws ApplicationException {
        return getManager().contblMasivosNombresSp(listaRoles);
    }

    @Override
    public List<InfoGeneralCargueDto> contblMasivosSp(String nombreProceso) throws ApplicationException {
        return getManager().contblMasivosSp(nombreProceso);
    }

    @Override
    public List<CargueInformacionDto> contblLineasMasivoSp(String nombre, String tipo, BigDecimal id) throws ApplicationException {
        return getManager().contblLineasMasivoSp(nombre, tipo, id);
    }

    @Override
    public List<CamposFiltrosDto> conFiltrosSp(BigDecimal id) throws ApplicationException {
        return getManager().conFiltrosSp(id);
    }

    @Override
    public List<ListaItemsFiltrosDto> consultarListasFiltros(BigDecimal idColumna, String idConsulta) throws ApplicationException {
        return getManager().consultarListasFiltros(idColumna, idConsulta);
    }

    @Override
    public TblLineasMasivoResponseDto tblLineasMasivoSp(TblLineasMasivoResquestDto resquestDto) throws ApplicationException {
        return getManager().tblLineasMasivoSp(resquestDto);
    }
}
