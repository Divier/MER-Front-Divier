package co.com.claro.mgl.jpa.entities.rr;

import co.com.claro.mgl.dao.impl.cm.CmtBasicaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "TCNDIL00", schema = "TVCABLEDTA")
public class NodoRR implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(NodoRR.class);
    private static final long serialVersionUID = 1L;
    public static final String ESTADO_ACTIVO = "A";
    public static final String ESTADO_INACTIVO = "I";
    public static final String ESTADO_PREVENTA = "P";
    public static final String TIPO_RED_UNIDIRECCIONAL = "U";
    public static final String TIPO_RED_BIDIRECCIONAL = "B";
    @Id
    @Column(name = "NDCODG", nullable = false)
    private String id;
    @Column(name = "NDFEAP", nullable = false)
    private String fechaAperturaString;
    @Column(name = "NDLIMI", length = 50)
    private String limites;
    @Column(name = "NDCORE", length = 15)
    private Long costoRed;
    @Transient
    private Date fechaApertura;

    public Long getCostoRed() {
        return costoRed;
    }

    public void setCostoRed(Long costoRed) {
        this.costoRed = costoRed;
    }

    public Date getFechaApertura() {
        if (fechaApertura == null) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                fechaApertura = formatter.parse(fechaAperturaString);
            } catch (ParseException e) {
                LOGGER.error("Error al  crear la fechaApertura-" + e.getMessage());
            }
        }
        return fechaApertura;
    }

    public String getFechaAperturaString() {
        return fechaAperturaString;
    }

    public void setFechaAperturaString(String fechaAperturaString) {
        this.fechaAperturaString = fechaAperturaString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLimites() {
        return limites;
    }

    public void setLimites(String limites) {
        this.limites = limites;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return id;
    }

    /**
     * Informar el estado del nodo.
     * Informa el estado del nodo de acuerdo a la fecha de apertura.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return el estado del nodo
     * @see Nodo#ESTADO_INACTIVO
     * @see Nodo#ESTADO_PREVENTA
     * @see Nodo#ESTADO_ACTIVO
     */
    public CmtBasicaMgl getEstado() {
        Date dateFuture = null;
        CmtBasicaMglDaoImpl basicaMgl = new CmtBasicaMglDaoImpl();
        CmtBasicaMgl inactivo = null;
        CmtBasicaMgl activo = null;
        try {
            inactivo = basicaMgl.findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO);
            activo = basicaMgl.findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_CERTIFICADO);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            dateFuture = formatter.parse("3000/01/01");
        } catch (ApplicationException | ParseException e) {
            LOGGER.error("Error al  crear la fechaApertura-" + e.getMessage());
        }

        if (getId() == null || getId().trim().isEmpty()) {
            return null;
        } else if (getFechaApertura() == null
                || getFechaApertura().equals(dateFuture)
                || getFechaApertura().after(new Date())) {

            return inactivo;
        } else {
            return activo;
        }
    }
}
