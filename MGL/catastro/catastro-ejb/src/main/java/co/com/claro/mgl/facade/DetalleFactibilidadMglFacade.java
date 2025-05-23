/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.DetalleFactibilidadMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DetalleFactibilidadMgl;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import com.amx.schema.fulfilloper.exp.customerorder.v1.CustomerOrderRequest;
import com.amx.schema.fulfilloper.exp.customerorder.v1.CustomerOrderResponse;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Fachada asociada a procesos de factibilidad.
 *
 * @author bocanegravm
 */
@Stateless
public class DetalleFactibilidadMglFacade implements DetalleFactibilidadMglFacadeLocal {

    /**
     * Crea un detalle de la factibilidad en el repositorio
     *
     * @param detalleFactibilidadMgl {@link DetalleFactibilidadMgl}
     * @return {@link DetalleFactibilidadMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    @Override
    public DetalleFactibilidadMgl create(DetalleFactibilidadMgl detalleFactibilidadMgl) throws ApplicationException {
        DetalleFactibilidadMglManager manager = new DetalleFactibilidadMglManager();
        return manager.create(detalleFactibilidadMgl);
    }

    /**
     * Encuentra la lista de detalles de la factibilidad en el repositorio
     *
     * @param idFactibilidad {@link BigDecimal}
     * @param tipoDir        {@link String} El tipo de dirección de la factibilidad que puede ser "UNIDAD" o "CCMM"
     * @return List<DetalleFactibilidadMgl>
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    @Override
    public List<DetalleFactibilidadMgl> findListDetalleFactibilidad(BigDecimal idFactibilidad, String tipoDir) throws ApplicationException {
        DetalleFactibilidadMglManager manager = new DetalleFactibilidadMglManager();
        return manager.findListDetalleFactibilidad(idFactibilidad, tipoDir);
    }

    /**
     * Encuentra un detalle de factibilidad en el repositorio por sla de ejecución
     *
     * @param slaEjecucionMgl {@link SlaEjecucionMgl}
     * @return {@link List<DetalleFactibilidadMgl>}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    @Override
    public List<DetalleFactibilidadMgl> findDetalleBySlaEjecucion(SlaEjecucionMgl slaEjecucionMgl) throws ApplicationException {
        DetalleFactibilidadMglManager manager = new DetalleFactibilidadMglManager();
        return manager.findDetalleBySlaEjecucion(slaEjecucionMgl);
    }

    /**
     * Modifica un detalle de la factibilidad en el repositorio
     *
     * @param detalleFactibilidadMgl {@link DetalleFactibilidadMgl}
     * @return {@link DetalleFactibilidadMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    @Override
    public DetalleFactibilidadMgl update(DetalleFactibilidadMgl detalleFactibilidadMgl) throws ApplicationException {
        DetalleFactibilidadMglManager manager = new DetalleFactibilidadMglManager();
        return manager.update(detalleFactibilidadMgl);
    }

    /**
     * Encuentra la lista de detalles de la factibilidad en el repositorio
     *
     * @param idFactibilidad {@link BigDecimal}
     * @return {@link List<DetalleFactibilidadMgl>}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author cardenaslb
     */
    @Override
    public List<DetalleFactibilidadMgl> findListDetalleFactibilidad(BigDecimal idFactibilidad) throws ApplicationException {
        DetalleFactibilidadMglManager manager = new DetalleFactibilidadMglManager();
        return manager.findListDetalleFactibilidad(idFactibilidad);
    }

    /**
     * Obtener lista de factibilidad por ID y tecnología
     *
     * @param solicitud {@link Solicitud}
     * @return {@link List<DetalleFactibilidadMgl>}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author cardenaslb
     */
    @Override
    public List<DetalleFactibilidadMgl> findListByIdFactTec(Solicitud solicitud) throws ApplicationException {
        DetalleFactibilidadMglManager manager = new DetalleFactibilidadMglManager();
        return manager.findListByIdFactTec(solicitud);
    }

    /**
     * Obtener lista de factibilidad por ID y fecha
     *
     * @param idFactibilidad {@link BigDecimal}
     * @param fecha          {@link String}
     * @return {@link List<DetalleFactibilidadMgl>}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author cardenaslb
     */
    @Override
    public List<DetalleFactibilidadMgl> findListByIdFactFecha(BigDecimal idFactibilidad, String fecha, String usuario)
            throws ApplicationException {
        DetalleFactibilidadMglManager manager = new DetalleFactibilidadMglManager();
        return manager.findListByIdFactFecha(idFactibilidad, fecha, usuario);
    }
    
    @Override
    public CustomerOrderResponse listCustomerOrder(String url, CustomerOrderRequest request) throws ApplicationException {
        DetalleFactibilidadMglManager manager = new DetalleFactibilidadMglManager();
        return manager.listCustomerOrders(url, request);
    }

}
