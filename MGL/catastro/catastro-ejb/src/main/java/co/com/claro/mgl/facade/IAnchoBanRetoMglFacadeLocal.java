package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.AnchoBanRetoDTO;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

public interface IAnchoBanRetoMglFacadeLocal {
    List<AnchoBanRetoDTO> listarAnchoBanReto() throws ApplicationException;
}
