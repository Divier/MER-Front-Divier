package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.CentroPobladoDTO;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

public interface ICentroPobladoMglFacadeLocal {
    List<CentroPobladoDTO> listarCentroPobladoPorDepartamentoYCiudad(String departamento, String ciudad) throws ApplicationException;
}
