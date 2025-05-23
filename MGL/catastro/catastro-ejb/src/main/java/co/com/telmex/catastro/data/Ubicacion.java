package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase Ubicación
 * Implementa Serialización
 *
 * @author 	Deiver
 * @version	1.0
 */
public class Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal ubiId;
    private String ubiNombre;
    private String ubiLatitud;
    private String ubiLongitud;
    private String ubiEstadoRed;
    private String ubiCuentaMatriz;
    private Date ubiFechaCreacion;
    private String ubiUsuarioCreacion;
    private Date ubiFechaModificacion;
    private String ubiUsuarioModificacion;
    private TipoUbicacion tubId;
    private GeograficoPolitico gpoId;
    private Geografico geoId;
    //INICIO Cambios Fase 1 Carlos leonardo Villamil
    private String ubiZonaDivipola;
    private String ubiDistritoCodigoPostal;
    //FIN Cambios Fase 1 Carlos leonardo Villamil

    /**
     * Constructor.
     */
    public Ubicacion() {
    }

    /**
     * Constructor con parámetros.
     * @param ubiId Entero código de la ubicación.
     */
    public Ubicacion(BigDecimal ubiId) {
        this.ubiId = ubiId;
    }

    /**
     * Obtiene el código de la ubicación.
     * @return Entero Código de la ubicación.
     */
    public BigDecimal getUbiId() {
        return ubiId;
    }

    /**
     * Establece el código de la ubicación.
     * @param ubiId Entero Código de la ubicación.
     */
    public void setUbiId(BigDecimal ubiId) {
        this.ubiId = ubiId;
    }

    /**
     * Obtiene el nombre de la ubicación.
     * @return Cadena con el nombre de la ubicación.
     */
    public String getUbiNombre() {
        return ubiNombre;
    }

    /**
     * Establece el nombre de la ubicación.
     * @param ubiNombre Cadena con el nombre de la ubicación.
     */
    public void setUbiNombre(String ubiNombre) {
        this.ubiNombre = ubiNombre;
    }

    /**
     * Obtiene el valor de la latitud de la ubicación.
     * @return Cadena con la latitud de la ubicación.
     */
    public String getUbiLatitud() {
        return ubiLatitud;
    }

    /**
     * Establece la latitud de la ubicación.
     * @param ubiLatitud Cadena con el valor de la latitud de la ubicación.
     */
    public void setUbiLatitud(String ubiLatitud) {
        this.ubiLatitud = ubiLatitud;
    }

    /**
     * Obtiene el valor de la longitud de la ubicación.
     * @return Cadena con la longitud de la ubicación.
     */
    public String getUbiLongitud() {
        return ubiLongitud;
    }

    /**
     * Establece la longitud de la ubicación.
     * @param ubiLongitud Cadena con el valor de la longitud de la ubicación.
     */
    public void setUbiLongitud(String ubiLongitud) {
        this.ubiLongitud = ubiLongitud;
    }

    /**
     * Obtiene el valor del estado de red de la ubicación.
     * @return Cadena con el valor del estado de red de la ubicación.
     */
    public String getUbiEstadoRed() {
        return ubiEstadoRed;
    }

    /**
     * Establece el valor del estado de red de la ubicación.
     * @param ubiEstadoRed Cadena con el valor del estado de red de la ubicación.
     */
    public void setUbiEstadoRed(String ubiEstadoRed) {
        this.ubiEstadoRed = ubiEstadoRed;
    }

    /**
     * Obtiene la cuenta matriz de la ubicación.
     * @return Cadena con la cuenta matriz de la ubicación.
     */
    public String getUbiCuentaMatriz() {
        return ubiCuentaMatriz;
    }

    /**
     * Establece la cuenta matriz de la ubicación.
     * @param ubiCuentaMatriz Cadena con la cuenta matriz de la ubicación.
     */
    public void setUbiCuentaMatriz(String ubiCuentaMatriz) {
        this.ubiCuentaMatriz = ubiCuentaMatriz;
    }

    /**
     * Obtiene la fecha de creación de la ubicación.
     * @return Fecha de creación de la ubicación.
     */
    public Date getUbiFechaCreacion() {
        return ubiFechaCreacion;
    }

    /**
     * Establece la fecha de creación de la ubicación.
     * @param ubiFechaCreacion Fecha con el valor de la fecha de creación de la ubicación.
     */
    public void setUbiFechaCreacion(Date ubiFechaCreacion) {
        this.ubiFechaCreacion = ubiFechaCreacion;
    }

    /**
     * Obtiene el usuario que realiza la creación de la ubicación.
     * @return Cadena con el usuario que realiza la creación de la ubicación.
     */
    public String getUbiUsuarioCreacion() {
        return ubiUsuarioCreacion;
    }

    /**
     * Establece el usuario que realiza la creación de la ubicación.
     * @param ubiUsuarioCreacion Cadena con el usuario que realiza la creación de la ubicación.
     */
    public void setUbiUsuarioCreacion(String ubiUsuarioCreacion) {
        this.ubiUsuarioCreacion = ubiUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación de la ubicación.
     * @return Fecha con el valor de la fecha de modificación de la ubicación.
     */
    public Date getUbiFechaModificacion() {
        return ubiFechaModificacion;
    }

    /**
     * Establece la fecha de modificación de la ubicación.
     * @param ubiFechaModificacion Fecha con el valor de la fecha de modificación de la ubicación.
     */
    public void setUbiFechaModificacion(Date ubiFechaModificacion) {
        this.ubiFechaModificacion = ubiFechaModificacion;
    }

    /**
     * Obtiene el usuario que modifica la ubicación.
     * @return Cadena con el usuario que modifica la ubicación.
     */
    public String getUbiUsuarioModificacion() {
        return ubiUsuarioModificacion;
    }

    /**
     * Establece el usuario que modifica la ubicación.
     * @param ubiUsuarioModificacion Cadena con el usuario que modifica la ubicación.
     */
    public void setUbiUsuarioModificacion(String ubiUsuarioModificacion) {
        this.ubiUsuarioModificacion = ubiUsuarioModificacion;
    }

    /**
     * Obtiene la Clase TipoUbicación correspondiente a la ubicación consultada.
     * @return TipoUbicación correspondiente a la ubicación consultada.
     */
    public TipoUbicacion getTubId() {
        return tubId;
    }

    /**
     * Establece la Clase TipoUbicación correspondiente a la ubicación consultada.
     * @param tubId TipoUbicación correspondiente a la ubicación consultada.
     */
    public void setTubId(TipoUbicacion tubId) {
        this.tubId = tubId;
    }

    /**
     * Obtiene la Clase GeograficoPolitico correspondiente a la ubicación consultada.
     * @return GeograficoPolitico correspondiente a la ubicación consultada.
     */
    public GeograficoPolitico getGpoId() {
        return gpoId;
    }

    /**
     * Establece la Clase GeograficoPolitico correspondiente a la ubicación consultada.
     * @param gpoId GeograficoPolitico correspondiente a la ubicación consultada.
     */
    public void setGpoId(GeograficoPolitico gpoId) {
        this.gpoId = gpoId;
    }

    /**
     * Obtiene la Clase Geografico correspondiente a la ubicación consultada.
     * @return Geografico correspondiente a la ubicación consultada.
     */
    public Geografico getGeoId() {
        return geoId;
    }

    /**
     * Establece la Clase Geografico correspondiente a la ubicación consultada.
     * @param geoId Geografico correspondiente a la ubicación consultada.
     */
    public void setGeoId(Geografico geoId) {
        this.geoId = geoId;
    }

    /**
     * Obtiene una cadena de control con los valores de la ubicación. 
     * @return Cadena con la información de la ubicación. (Id, Nombre, Latitud, Longitud, Estado de Red, Cuenta Matriz)
     */
    public String auditoria() {
        String auditoria = "Ubicacion:" + "ubiId=" + ubiId
                + ", ubiNombre=" + ubiNombre
                + ", ubiLatitud=" + ubiLatitud
                + ", ubiLongitud=" + ubiLongitud
                + ", ubiEstadoRed=" + ubiEstadoRed
                + ", ubiCuentaMatriz=" + ubiCuentaMatriz;
        if (tubId != null) {
            auditoria = auditoria + ", tubId=" + tubId.getTubId();
        }
        if (gpoId != null) {
            auditoria = auditoria + ", gpoId=" + gpoId.getGpoId() + '.';
        }
        if (geoId != null) {
            auditoria = auditoria + ", geoId=" + geoId.getGeoId() + '.';
        }
        return auditoria;

    }

    /**
     * Sobre escritura del método toString() para retornar información de auditoría.
     * @return Cadena con la información de la ubicación. (Id, Nombre, Latitud, Longitud, Estado de Red, Cuenta Matriz)
     */
    @Override
    public String toString() {
        String respuesta = "Ubicacion:" + "ubiId=" + ubiId
                + ", ubiNombre=" + ubiNombre
                + ", ubiLatitud=" + ubiLatitud
                + ", ubiLongitud=" + ubiLongitud
                + ", ubiEstadoRed=" + ubiEstadoRed
                + ", ubiCuentaMatriz=" + ubiCuentaMatriz;
        if (tubId != null) {
            respuesta = respuesta + ", tubId=" + tubId.getTubId();
        }
        if (gpoId != null) {
            respuesta = respuesta + ", gpoId=" + gpoId.getGpoId() + '.';
        }
        if (geoId != null) {
            respuesta = respuesta + ", geoId=" + geoId.getGeoId() + '.';
        }
        return respuesta;
    }

    //INICIO Cambios Fase 1 Carlos leonardo Villamil
    /**
     * Obtiene la Zona Divipola de la ubicación consultada.
     * @return Cadena con la zona divipola de la ubicación.
     */
    public String getUbiZonaDivipola() {
        return ubiZonaDivipola;
    }

    /**
     * Establece la zona divipola para la ubicación consultada.
     * @param ubiZonaDivipola Cadena con el valor de la zona divipola.
     */
    public void setUbiZonaDivipola(String ubiZonaDivipola) {
        this.ubiZonaDivipola = ubiZonaDivipola;
    }

    /**
     * Obtiene el valor del código postal de la ubicación consultada.
     * @return Cadena con el código postal de la ubicación.
     */
    public String getUbiDistritoCodigoPostal() {
        return ubiDistritoCodigoPostal;
    }

    /**
     * Establece el código postal de la ubicación.
     * @param ubiDistritoCodigoPostal Cadena con el valor del código postal de la ubicación.
     */
    public void setUbiDistritoCodigoPostal(String ubiDistritoCodigoPostal) {
        this.ubiDistritoCodigoPostal = ubiDistritoCodigoPostal;
    }

    //FIN Cambios Fase 1 Carlos leonardo Villamil
}