package co.com.claro.mgl.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Clase de entidad para la interacción con la tabla TEC_OBS_SOL_HHPP
 * guarda las observaciones realizadas a una solicitud
 * @author José René Miranda de la O
 */
@Entity
@Table(name = "TEC_OBS_SOL_HHPP", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "TecObservacionSolicitudHHPP.findUrlsByIdSolicitud",
                query = "SELECT c FROM TecObservacionSolicitudHHPP c WHERE c.solicitudObj = :solicitudObj")})
public class TecObservacionSolicitudHHPP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "TecObservacionSolicitudHHPP.TecObservacionSolicitudHHPPSeq",
            sequenceName = "MGL_SCHEME.TEC_OBS_SOL_HHPP_SQ", allocationSize = 1)
    @GeneratedValue(generator = "TecObservacionSolicitudHHPP.TecObservacionSolicitudHHPPSeq")
    @Column(name = "ID_TEC_OBS_SOL_HHPP", nullable = false)
    private BigDecimal idTecObsSolHHPP;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID", nullable = false)
    private  Solicitud solicitudObj;
    @Size(min = 1, max = 2000)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Transient
    private List<TecArchivosEscalamientosHHPP> archivos;

    /**
     * get field @Id
     @Basic(optional = false)
     @SequenceGenerator( name = "TecObservacionSolicitudHHPP.TecObservacionSolicitudHHPPSeq",
     sequenceName = "MGL_SCHEME.TEC_OBS_SOL_HHPP_SQ", allocationSize = 1)
     @GeneratedValue(generator = "TecObservacionSolicitudHHPP.TecObservacionSolicitudHHPPSeq")
     @Column(name = "ID_TEC_OBS_SOL_HHPP", nullable = false)

      *
      * @return idTecObsSolHHPP @Id
     @Basic(optional = false)
     @SequenceGenerator( name = "TecObservacionSolicitudHHPP.TecObservacionSolicitudHHPPSeq",
     sequenceName = "MGL_SCHEME.TEC_OBS_SOL_HHPP_SQ", allocationSize = 1)
     @GeneratedValue(generator = "TecObservacionSolicitudHHPP.TecObservacionSolicitudHHPPSeq")
     @Column(name = "ID_TEC_OBS_SOL_HHPP", nullable = false)

     */
    public BigDecimal getIdTecObsSolHHPP() {
        return this.idTecObsSolHHPP;
    }

    /**
     * set field @Id
     @Basic(optional = false)
     @SequenceGenerator( name = "TecObservacionSolicitudHHPP.TecObservacionSolicitudHHPPSeq",
     sequenceName = "MGL_SCHEME.TEC_OBS_SOL_HHPP_SQ", allocationSize = 1)
     @GeneratedValue(generator = "TecObservacionSolicitudHHPP.TecObservacionSolicitudHHPPSeq")
     @Column(name = "ID_TEC_OBS_SOL_HHPP", nullable = false)

      *
      * @param idTecObsSolHHPP @Id
     @Basic(optional = false)
     @SequenceGenerator( name = "TecObservacionSolicitudHHPP.TecObservacionSolicitudHHPPSeq",
     sequenceName = "MGL_SCHEME.TEC_OBS_SOL_HHPP_SQ", allocationSize = 1)
     @GeneratedValue(generator = "TecObservacionSolicitudHHPP.TecObservacionSolicitudHHPPSeq")
     @Column(name = "ID_TEC_OBS_SOL_HHPP", nullable = false)

     */
    public void setIdTecObsSolHHPP(BigDecimal idTecObsSolHHPP) {
        this.idTecObsSolHHPP = idTecObsSolHHPP;
    }

    /**
     * get field @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID", nullable = false)

      *
      * @return solicitudObj @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID", nullable = false)

     */
    public Solicitud getSolicitudObj() {
        return this.solicitudObj;
    }

    /**
     * set field @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID", nullable = false)

      *
      * @param solicitudObj @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID", nullable = false)

     */
    public void setSolicitudObj(Solicitud solicitudObj) {
        this.solicitudObj = solicitudObj;
    }

    /**
     * get field @Size(min = 1, max = 2000)
     @Column(name = "OBSERVACION")

      *
      * @return observacion @Size(min = 1, max = 2000)
     @Column(name = "OBSERVACION")

     */
    public String getObservacion() {
        return this.observacion;
    }

    /**
     * set field @Size(min = 1, max = 2000)
     @Column(name = "OBSERVACION")

      *
      * @param observacion @Size(min = 1, max = 2000)
     @Column(name = "OBSERVACION")

     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * get field @Transient
     *
     * @return archivos @Transient

     */
    public List<TecArchivosEscalamientosHHPP> getArchivos() {
        return this.archivos;
    }

    /**
     * set field @Transient
     *
     * @param archivos @Transient

     */
    public void setArchivos(List<TecArchivosEscalamientosHHPP> archivos) {
        this.archivos = archivos;
    }
}
