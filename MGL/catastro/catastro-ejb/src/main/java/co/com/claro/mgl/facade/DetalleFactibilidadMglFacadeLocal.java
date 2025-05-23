/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DetalleFactibilidadMgl;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import com.amx.schema.fulfilloper.exp.customerorder.v1.CustomerOrderRequest;
import com.amx.schema.fulfilloper.exp.customerorder.v1.CustomerOrderResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface asociada a procesos de factibilidad
 *
 * @author bocanegravm
 */
public interface DetalleFactibilidadMglFacadeLocal {

    /**
     * Crea un detalle de la factibilidad en el repositorio
     *
     * @param detalleFactibilidadMgl {@link DetalleFactibilidadMgl}
     * @return DetalleFactibilidadMgl
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    DetalleFactibilidadMgl create(DetalleFactibilidadMgl detalleFactibilidadMgl) throws ApplicationException;

    /**
     * Encuentra la lista de detalles de la factibilidad en el repositorio
     *
     * @param idFactibilidad {@link BigDecimal}
     * @param tipoDir        {@link String} El tipo de dirección de la factibilidad que puede ser "UNIDAD" o "CCMM"
     * @return List<DetalleFactibilidadMgl>
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    List<DetalleFactibilidadMgl> findListDetalleFactibilidad(BigDecimal idFactibilidad, String tipoDir) throws ApplicationException;

    /**
     * Encuentra un detalle de factibilidad en el repositorio por sla de ejecución
     *
     * @param slaEjecucionMgl {@link SlaEjecucionMgl}
     * @return {@link List<DetalleFactibilidadMgl>}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    List<DetalleFactibilidadMgl> findDetalleBySlaEjecucion(SlaEjecucionMgl slaEjecucionMgl) throws ApplicationException;

    /**
     * Modifica un detalle de la factibilidad en el repositorio
     *
     * @param detalleFactibilidadMgl {@link DetalleFactibilidadMgl}
     * @return DetalleFactibilidadMgl
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    DetalleFactibilidadMgl update(DetalleFactibilidadMgl detalleFactibilidadMgl) throws ApplicationException;

    /**
     * Consulta la lista de detalles de la factibilidad en el repositorio
     *
     * @param idFactibilidad {@link BigDecimal} Id de la factibilidad a buscar
     * @return {@link List<DetalleFactibilidadMgl>} Lista de detalles de la factibilidad
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    List<DetalleFactibilidadMgl> findListDetalleFactibilidad(BigDecimal idFactibilidad) throws ApplicationException;

    /**
     * Obtener lista de factibilidad por ID y tecnología
     *
     * @param solicitud {@link Solicitud}
     * @return {@link DetalleFactibilidadMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author cardenaslb
     */
    List<DetalleFactibilidadMgl> findListByIdFactTec(Solicitud solicitud) throws ApplicationException;

    /**
     * Obtener lista de factibilidad por ID y tecnología
     *
     * @param idFactibilidad {@link BigDecimal}
     * @param fecha          {@link String}
     * @param usuario        {@link String}
     * @return {@link List<DetalleFactibilidadMgl>}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author cardenaslb
     */
    List<DetalleFactibilidadMgl> findListByIdFactFecha(BigDecimal idFactibilidad, String fecha, String usuario) throws ApplicationException;

    public CustomerOrderResponse listCustomerOrder(String url, CustomerOrderRequest request) throws ApplicationException;
    
}
