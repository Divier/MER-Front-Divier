package co.com.claro.mgl.ws.cm.request;

import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Clase usada para gestionar los datos generales en petición del proceso de creación y autogestión de solicitud
 * de traslado de HHPP Bloqueado (HHPP Virtual).
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/12/26
 */
public class RequestCreaSolicitudTrasladoHhppBloqueado extends RequestCreaSolicitud {
    /**
     * Número de cuenta del suscriptor a trasladar para crear HHPP Virtual.
     */
    @Getter
    @Setter
    private String numeroCuentaClienteTrasladar;

    /**
     * Identificador de la dirección asociada a la unidad virtual en RR
     */
    @Getter
    @Setter
    private String idDireccionUnidadVirtualRR;

    @Getter
    @Setter
    private HhppMgl hhppReal;

    @Getter
    @Setter
    private String tipoTecnologia;
    @Getter
    @Setter
    private List<UnidadStructPcml> unidadesList;
    @Getter
    @Setter
    private String tiempoDuracionSolicitud;
    @Getter
    @Setter
    private BigDecimal idCentroPoblado;
    @Getter
    @Setter
    private String usuarioVt;
    @Getter
    @Setter
    private Integer perfilVt;
    @Getter
    @Setter
    private String tipoAccionSolicitud;
    @Getter
    @Setter
    private Solicitud direccionActual;
    @Getter
    @Setter
    private boolean habilitarRrMer;
    @Getter
    @Setter
    private boolean solicitudDesdeMER;
    @Getter
    @Setter
    private GeograficoPoliticoMgl centroPobladoDireccion;
    @Getter
    @Setter
    private GeograficoPoliticoMgl ciudadDireccion;
    @Getter
    @Setter
    private GeograficoPoliticoMgl departamentoDireccion;
    /**
     * Representa el address que conforma la descripción enviada por correo.
     */
    @Getter
    @Setter
    private String addressDir;

}
