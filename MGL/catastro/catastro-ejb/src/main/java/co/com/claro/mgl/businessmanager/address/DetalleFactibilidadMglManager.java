/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mer.handler.CustomerOrderHandler;
import co.com.claro.mgl.businessmanager.cm.DetalleSlaEjeMglManager;
import co.com.claro.mgl.businessmanager.cm.SlaEjecucionMglManager;
import co.com.claro.mgl.dao.impl.DetalleFactibilidadMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DetalleFactibilidadMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import com.amx.schema.fulfilloper.exp.customerorder.v1.CustomerOrderRequest;
import com.amx.schema.fulfilloper.exp.customerorder.v1.CustomerOrderResponse;
import com.amx.service.fulfilloper.exp.customerorder.v1.CustomerOrderSOAPBindingQSService;
import com.amx.service.fulfilloper.exp.customerorder.v1.FaultMessage;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.amx.service.fulfilloper.exp.customerorder.v1.ICustomerOrderInteface;

/**
 * Manager asociado a procesos de factibilidad de direcciones
 *
 * @author bocanegravm
 */
public class DetalleFactibilidadMglManager {
    
    private static final Logger LOGGER = LogManager.getLogger(DetalleFactibilidadMglManager.class);

    /**
     * Crea un detalle de la factibilidad en el repositorio
     *
     * @param detalleFactibilidadMgl {@link DetalleFactibilidadMgl}
     * @return {@link DetalleFactibilidadMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    public DetalleFactibilidadMgl create(DetalleFactibilidadMgl detalleFactibilidadMgl) throws ApplicationException {
        DetalleFactibilidadMglDaoImpl dao = new DetalleFactibilidadMglDaoImpl();
        return dao.create(detalleFactibilidadMgl);
    }

    /**
     * Encuentra la lista de detalles de la factibilidad en el repositorio
     *
     * @param idFactibilidad {@link BigDecimal} Identificador de la factibilidad
     * @param tipoDir        {@link String} El tipo de dirección de la factibilidad que puede ser "UNIDAD" o "CCMM"
     * @return {@link List<DetalleFactibilidadMgl>} Retorna la lista de detalle de la factibilidad
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    public List<DetalleFactibilidadMgl> findListDetalleFactibilidad(BigDecimal idFactibilidad, String tipoDir) throws ApplicationException {
        DetalleFactibilidadMglDaoImpl dao = new DetalleFactibilidadMglDaoImpl();
        List<DetalleFactibilidadMgl> listDetalleFactibilidad = dao.findListDetalleFactibilidad(idFactibilidad);

        if (Objects.isNull(listDetalleFactibilidad) || listDetalleFactibilidad.isEmpty()) {
            return new ArrayList<>();
        }

        List<DetalleFactibilidadMgl> resultListDetalleFactibilidad = new ArrayList<>();

        for (DetalleFactibilidadMgl detalleFactibilidadMgl : listDetalleFactibilidad) {
            resultListDetalleFactibilidad.add(agregarDetallesFactibilidad(tipoDir, detalleFactibilidadMgl));
        }

        return resultListDetalleFactibilidad;
    }

    /**
     * Agrega detalles a la factibilidad que se está procesando y si lo requiere la actualiza en loa BD.
     *
     * @param tipoDir                {@link String} El tipo de dirección de la factibilidad que puede ser "UNIDAD" o "CCMM"
     * @param detalleFactibilidadMgl {@link DetalleFactibilidadMgl} Detalle de la factibilidad para agregar detalles.
     * @return {@link DetalleFactibilidadMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Gildardo Mora
     */
    private DetalleFactibilidadMgl agregarDetallesFactibilidad(String tipoDir, DetalleFactibilidadMgl detalleFactibilidadMgl) throws ApplicationException {
        String codigoNodo = Optional.ofNullable(detalleFactibilidadMgl).map(DetalleFactibilidadMgl::getCodigoNodo).orElse(null);

        if (Objects.isNull(codigoNodo)) {
            return detalleFactibilidadMgl;
        }

        NodoMglManager nodoMglManager = new NodoMglManager();
        NodoMgl nodo = nodoMglManager.findByCodigo(detalleFactibilidadMgl.getCodigoNodo());

        if (Objects.isNull(nodo)) {
            return detalleFactibilidadMgl;
        }

        CmtBasicaMgl tecnologia = nodo.getNodTipo();
        SlaEjecucionMglManager slaEjecucionMglManager = new SlaEjecucionMglManager();
        SlaEjecucionMgl slaEjecucionMgl = slaEjecucionMglManager.findByTecnoAndEjecucion(tecnologia, tipoDir);

        if (Objects.isNull(slaEjecucionMgl)) {
            return detalleFactibilidadMgl;
        }

        DetalleSlaEjeMglManager detalleSlaEjeMglManager = new DetalleSlaEjeMglManager();
        int total = detalleSlaEjeMglManager.findSumSlaBySlaEje(slaEjecucionMgl);
        detalleFactibilidadMgl.setSlaEjecucionMglObj(slaEjecucionMgl);
        detalleFactibilidadMgl.setTiempoUltimaLilla(total);
        //Actualiza el detalle de la factibilidad en la BD
        detalleFactibilidadMgl = update(detalleFactibilidadMgl);
        return detalleFactibilidadMgl;
    }

    /**
     * Encuentra un detalle de factibilidad en el repositorio por sla de ejecución
     *
     * @param slaEjecucionMgl {@link SlaEjecucionMgl}
     * @return {@link DetalleFactibilidadMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Victor Bocanegra
     */
    public List<DetalleFactibilidadMgl> findDetalleBySlaEjecucion(SlaEjecucionMgl slaEjecucionMgl) throws ApplicationException {
        DetalleFactibilidadMglDaoImpl dao = new DetalleFactibilidadMglDaoImpl();
        return dao.findDetalleBySlaEjecucion(slaEjecucionMgl);
    }

    /**
     * Modifica un detalle de la factibilidad en el repositorio
     *
     * @param detalleFactibilidadMgl {@link DetalleFactibilidadMgl}
     * @return {@link DetalleFactibilidadMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Victor Bocanegra
     */
    public DetalleFactibilidadMgl update(DetalleFactibilidadMgl detalleFactibilidadMgl) throws ApplicationException {
        DetalleFactibilidadMglDaoImpl dao = new DetalleFactibilidadMglDaoImpl();
        return dao.update(detalleFactibilidadMgl);
    }

    /**
     * Encuentra la lista de detalles de la factibilidad en el repositorio
     *
     * @param idFactibilidad {@link BigDecimal}
     * @return {@link List<DetalleFactibilidadMgl>} Retorna la lista de detalles de la factibilidad encontrada
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author cardenaslb
     */
    public List<DetalleFactibilidadMgl> findListDetalleFactibilidad(BigDecimal idFactibilidad) throws ApplicationException {
        DetalleFactibilidadMglDaoImpl dao = new DetalleFactibilidadMglDaoImpl();
        return dao.findListDetalleFactibilidad(idFactibilidad);
    }

    /**
     * Obtener lista de factibilidad por ID y tecnología
     *
     * @param solicitud {@link Solicitud}
     * @return {@link List<DetalleFactibilidadMgl>} Retorna la lista de detalles de la factibilidad encontrada
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author cardenaslb
     */
    public List<DetalleFactibilidadMgl> findListByIdFactTec(Solicitud solicitud) throws ApplicationException {
        DetalleFactibilidadMglDaoImpl dao = new DetalleFactibilidadMglDaoImpl();
        return dao.findListByIdFactTec(solicitud);
    }

    /**
     * Obtener lista de factibilidad por ID y fecha
     *
     * @param idFactibilidad {@link BigDecimal}
     * @param fecha          {@link String}
     * @param usuario        {@link String}
     * @return {@link List<DetalleFactibilidadMgl>} Retorna la lista de detalles de la factibilidad encontrada
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author cardenaslb
     */
    public List<DetalleFactibilidadMgl> findListByIdFactFecha(BigDecimal idFactibilidad, String fecha, String usuario) throws ApplicationException {
        DetalleFactibilidadMglDaoImpl dao = new DetalleFactibilidadMglDaoImpl();
        return dao.findListByIdFactFecha(idFactibilidad, fecha, usuario);
    }
    
    public CustomerOrderResponse listCustomerOrders(String url, CustomerOrderRequest request) throws ApplicationException {
        try {
            CustomerOrderResponse response;
            CustomerOrderSOAPBindingQSService co = new CustomerOrderSOAPBindingQSService(new URL(url));
            ICustomerOrderInteface puerto = co.getCustomerOrderSOAPBindingQSPort();
            Binding binding = ((BindingProvider) puerto).getBinding();
            List<Handler> handlerChain;
            handlerChain = binding.getHandlerChain();
            CustomerOrderHandler ch = new CustomerOrderHandler();
            handlerChain.add(ch);
            binding.setHandlerChain(handlerChain);
            response = puerto.customerOrder(request);

            return response;
        } catch (MalformedURLException | FaultMessage ex) {
            LOGGER.error("NO fue posible consultar servicio customerOrder correctamente en listCustomerOrders: ", ex);
            throw new ApplicationException(ex.getMessage());
        }
    }

}
