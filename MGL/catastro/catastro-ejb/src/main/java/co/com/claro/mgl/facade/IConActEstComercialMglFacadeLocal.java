package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.ArchActEstComercialDto;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

public interface IConActEstComercialMglFacadeLocal {
    List<ArchActEstComercialDto> listarTodosConsulta() throws ApplicationException;

    List<ArchActEstComercialDto> listarTodosCargue() throws ApplicationException;

    ArchActEstComercialDto listarPorIdConsulta(int id_reporte) throws ApplicationException;

}
