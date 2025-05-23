package co.com.claro.visitasTecnicas.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.ParamMultivalor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase ParamMultivalor
 *
 * @author Diego Barrera
 * @version version 1.2
 * @date 2013/09/12
 *
 */
@Stateless(name = "ParametrosMultivalorEJB", mappedName = "ParametrosMultivalorEJB", description = "ParamMultivalor")
@Remote({ParametrosMultivalorEJBRemote.class})
public class ParametrosMultivalorEJB implements ParametrosMultivalorEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(ParametrosMultivalorEJB.class);

    /**
     * @PostConstruct
     */
    @Override
    public void postConstruct() {
    }

    /**
     * listaParamMultivalor retorna la lista de parametros agrupados por tipo
     *
     * @param idTipo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<ParamMultivalor> listaParamMultivalor(String idTipo) throws ApplicationException {
        NegocioParamMultivalor param = new NegocioParamMultivalor();
        return param.consultaParametrosMultivalor(idTipo);
    }

    /**
     * getCiudadByCodDane retorna la Ciudad, Departamento y Pais
     *
     * @param CodDane
     * @return cityEntity
     * @throws co.com.claro.mgl.error.ApplicationException
     * @DB
     */
    @Override
    public CityEntity getCiudadByCodDane(String codCity) throws ApplicationException {
        NegocioParamMultivalor param = new NegocioParamMultivalor();
        return param.consultaDptoCiudad(codCity);
    }

    @Override
    public boolean UpdateSolicitudInstdrDireccion(DetalleDireccionEntity detalleDireccionEntity) throws ApplicationException {
        NegocioParamMultivalor param = new NegocioParamMultivalor();
        return param.UpdateSolicitudInstdrDireccion(detalleDireccionEntity);
    }

    public BigDecimal isSolicitudInProcess(DetalleDireccionEntity detalleDireccionEntity, String comunidad) {

        BigDecimal result = BigDecimal.ZERO;
        try {
            NegocioParamMultivalor param = new NegocioParamMultivalor();
            BigDecimal id = BigDecimal.ZERO;
            id = param.getCountSolicitudByDrDireccion(detalleDireccionEntity, comunidad);
            if (id.compareTo(BigDecimal.ZERO) != 0) {
                result = id;
            }
        } catch (ApplicationException ex) {
            result = BigDecimal.ZERO;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }

        return result;
    }

    public BigDecimal isSolicitudInProcess(String streetName, String houseNumber, String apartmentNumber, String comunidad) {
        BigDecimal result = BigDecimal.ZERO;
        try {
            NegocioParamMultivalor param = new NegocioParamMultivalor();

            return param.getIsSolicitudInProcess(streetName, houseNumber, apartmentNumber, comunidad);

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }

        return result;
    }

    public String getBodymesajeMail(String rpt) throws ApplicationException {
        NegocioParamMultivalor param = new NegocioParamMultivalor();
        return param.getMensajeBodyMail(rpt);
    }
}
