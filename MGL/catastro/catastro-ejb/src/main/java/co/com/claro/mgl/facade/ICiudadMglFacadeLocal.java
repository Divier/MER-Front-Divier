package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.CiudadDTO;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

public interface ICiudadMglFacadeLocal {
    List<CiudadDTO> listarCiudadesPorDepartamento(String departamento) throws ApplicationException;
}
