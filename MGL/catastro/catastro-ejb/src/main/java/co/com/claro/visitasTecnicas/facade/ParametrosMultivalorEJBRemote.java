package co.com.claro.visitasTecnicas.facade;


import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.ParamMultivalor;
import java.util.List;
import javax.ejb.Remote;

/**
 * Clase ParamMultivalor
 * @author 	Diego Barrera
 * @version     version 1.2
 * @date        2013/09/12
 **/
@Remote
public interface ParametrosMultivalorEJBRemote {
    
    public void postConstruct();
    
    /**
     * Metodo que retorna lista de parametros para inicializar los combos
     * @param idTipo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<ParamMultivalor> listaParamMultivalor(String idTipo) throws ApplicationException;
    
    
    public boolean UpdateSolicitudInstdrDireccion(DetalleDireccionEntity detalleDireccionEntity) throws ApplicationException;
    
    CityEntity getCiudadByCodDane(String codCity)throws ApplicationException;
}
