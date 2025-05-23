package co.com.claro.visitasTecnicas.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase DetalleDireccionEJBRemote
 *
 * @author Ivan Turriago.
 * @version	1.0
 */
@Remote
public interface DetalleDireccionEJBRemote {

    public List<DetalleDireccionEntity> consultarDireccionPorSolicitud
            (String idSolicitud) throws ApplicationException;

    /**
     * Descripción del objetivo del método.Llamada de entrada a la clase para
 convertir la direccion a la clase DetalleDireccionEntity, Llama a borrado
 de variables y Convertir ladireccion.
     *     
     * @author Carlos Leonardo Villamil
     * @param longAddressCodeGeo Codigo de Geo hasta la placa.
     * @param shortAddressCodeGeo Codigo de Geo hasta el complemento.
     * @return DetalleDireccionEntity Direccion tabulada en sus componentes.
     * @throws co.com.claro.mgl.error.ApplicationException
     */    
    public DetalleDireccionEntity conversionADetalleDireccion
            (String longAddressCodeGeo, String shortAddressCodeGeo, BigDecimal city,
            String barrio)
            throws ApplicationException;
    
    public String ValidarTipoDireccion(String codGeo, BigDecimal city, String origen) throws ApplicationException; 
}
