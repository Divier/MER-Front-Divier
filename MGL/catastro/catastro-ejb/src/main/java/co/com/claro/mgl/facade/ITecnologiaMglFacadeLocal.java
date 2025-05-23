package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.TecnologiaDTO;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

public interface ITecnologiaMglFacadeLocal {
    List<TecnologiaDTO> listarTecnologias() throws ApplicationException;
}
