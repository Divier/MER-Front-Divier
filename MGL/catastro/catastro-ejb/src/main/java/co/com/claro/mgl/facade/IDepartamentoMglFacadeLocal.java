package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.DepartamentoDTO;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

public interface IDepartamentoMglFacadeLocal {
    List<DepartamentoDTO> listarDepartamentos() throws ApplicationException;
}
