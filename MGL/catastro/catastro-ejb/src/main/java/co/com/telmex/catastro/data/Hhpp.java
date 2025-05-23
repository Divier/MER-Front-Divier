package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Clase Hhpp Implementa Serialización.
 *
 * @author Deiver Rovira
 * @version	1.0
 */
public class Hhpp implements Serializable {

    private BigDecimal hhppId;
    private Direccion direccion = new Direccion();
    private Nodo nodo;
    private TipoHhpp tipoHhpp;
    private SubDireccion subDireccion;
    private TipoHhppConexion tipoConexionHhpp;
    private TipoHhppRed tipoRedHhpp;
    private Date hhppFechaCreacion;
    private String hhppUsuarioCreacion;
    private Date hhppFechaModificacion;
    private String hhppUsuarioModificacion;
    private EstadoHhpp estadoHhpp;
    private List<Marcas> marcas;
    private String idRR;
    private String calleRR;
    private String unidadRR;
    private String aptoRR;
    private String comunidadRR;
    private String divisionRR;
    private String estado_unit;
    private String vendedor;
    private String codigo_postal;
    private String tipo_acomet;
    private String ult_ubicacion;
    private String head_end;
    private String tipo;
    private String edificio;
    private String tipo_unidad;
    private String tipo_cbl_acometida;
    private String amp;
    private String nap;
    private static String POINT = ".";
    private static String AMP = "amp=";
    
    /**
     * Constructor.
     */
    public Hhpp() {
    }

    /**
     * Obtiene la dirección.
     *
     * @return Objeto Direccion con la información solicitada.
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección
     *
     * @param direccion Objeto Direccion con la información solicitada.
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el estado del Hhpp
     *
     * @return Objeto EstadoHhpp con la información solicitada.
     */
    public EstadoHhpp getEstadoHhpp() {
        return estadoHhpp;
    }

    /**
     * Establece el estado del Hhpp.
     *
     * @param estadoHhpp Objeto EstadoHhpp con la información solicitada.
     */
    public void setEstadoHhpp(EstadoHhpp estadoHhpp) {
        this.estadoHhpp = estadoHhpp;
    }

    /**
     * Obtiene la fecha de creación .
     *
     * @return Fecha de creación.
     */
    public Date getHhppFechaCreacion() {
        return hhppFechaCreacion;
    }

    /**
     * Establece la fecha de creación.
     *
     * @param hhppFechaCreacion Fecha de creación.
     */
    public void setHhppFechaCreacion(Date hhppFechaCreacion) {
        this.hhppFechaCreacion = hhppFechaCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     *
     * @return Fecha de modificación.
     */
    public Date getHhppFechaModificacion() {
        return hhppFechaModificacion;
    }

    /**
     * Establece la fecha de modificación.
     *
     * @param hhppFechaModificacion Fecha de modificación.
     */
    public void setHhppFechaModificacion(Date hhppFechaModificacion) {
        this.hhppFechaModificacion = hhppFechaModificacion;
    }

    /**
     * Obtiene el Id.
     *
     * @return Entero con el Id.
     */
    public BigDecimal getHhppId() {
        return hhppId;
    }

    /**
     * Establece el Id.
     *
     * @param hhppId Entero con el Id.
     */
    public void setHhppId(BigDecimal hhppId) {
        this.hhppId = hhppId;
    }

    /**
     * Obtiene el usuario que realiza la creación.
     *
     * @return Cadena con el usuario que realiza la creación.
     */
    public String getHhppUsuarioCreacion() {
        return hhppUsuarioCreacion;
    }

    /**
     * Establece el usuario que realiza la creación.
     *
     * @param hhppUsuarioCreacion Cadena con el usuario que realiza la creación.
     */
    public void setHhppUsuarioCreacion(String hhppUsuarioCreacion) {
        this.hhppUsuarioCreacion = hhppUsuarioCreacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     *
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getHhppUsuarioModificacion() {
        return hhppUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación.
     *
     * @param hhppUsuarioModificacion Cadena con el usuario que realiza la
     * modificación.
     */
    public void setHhppUsuarioModificacion(String hhppUsuarioModificacion) {
        this.hhppUsuarioModificacion = hhppUsuarioModificacion;
    }

    /**
     * Obtiene el nodo.
     *
     * @return Objeto Nodo con la información solicitada.
     */
    public Nodo getNodo() {
        return nodo;
    }

    /**
     * Establece el nodo.
     *
     * @param nodo Objeto Nodo con la información solicitada.
     */
    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }

    /**
     * Obtiene la subdirección.
     *
     * @return Objeto subdirección con la información solicitada.
     */
    public SubDireccion getSubDireccion() {
        return subDireccion;
    }

    /**
     * Establece la subdirección.
     *
     * @param subDireccion Objeto Subdirección con la información solicitada.
     */
    public void setSubDireccion(SubDireccion subDireccion) {
        this.subDireccion = subDireccion;
    }

    /**
     * Obtiene el tipo de conexión hhpp.
     *
     * @return Objeto TipoHhppConexion con la información solicitada.
     */
    public TipoHhppConexion getTipoConexionHhpp() {
        return tipoConexionHhpp;
    }

    /**
     * Establece el tipo de conexión hhpp.
     *
     * @param tipoConexionHhpp Objeto TipoHhppConexion con la información
     * solicitada.
     */
    public void setTipoConexionHhpp(TipoHhppConexion tipoConexionHhpp) {
        this.tipoConexionHhpp = tipoConexionHhpp;
    }

    /**
     * Obtiene el tipo de hhpp.
     *
     * @return Objeto TipoHhpp con la información solicitada.
     */
    public TipoHhpp getTipoHhpp() {
        return tipoHhpp;
    }

    /**
     * Establece le tipo de hhpp.
     *
     * @param tipoHhpp Objeto TipoHhpp con la información solicitada.
     */
    public void setTipoHhpp(TipoHhpp tipoHhpp) {
        this.tipoHhpp = tipoHhpp;
    }

    /**
     * Obtiene el tipo de red del hhpp.
     *
     * @return Objeto TipoHhppRed con la información solicitada.
     */
    public TipoHhppRed getTipoRedHhpp() {
        return tipoRedHhpp;
    }

    /**
     * Establece el tipo de red del hhpp.
     *
     * @param tipoRedHhpp Objeto TipoHhppRed con la información solicitada.
     */
    public void setTipoRedHhpp(TipoHhppRed tipoRedHhpp) {
        this.tipoRedHhpp = tipoRedHhpp;
    }

    /**
     * Obtiene la lista de Marcas.
     *
     * @return Lista de Objeto Marcas.
     */
    public List<Marcas> getMarcas() {
        return marcas;
    }

    /**
     * Establece la lista de marcas.
     *
     * @param marcas Lista de Objeto Marcas.
     */
    public void setMarcas(List<Marcas> marcas) {
        this.marcas = marcas;
    }

    public String getIdRR() {
        return idRR;
    }

    public void setIdRR(String idRR) {
        this.idRR = idRR;
    }

    public String getCalleRR() {
        return calleRR;
    }

    public void setCalleRR(String calleRR) {
        this.calleRR = calleRR;
    }

    public String getUnidadRR() {
        return unidadRR;
    }

    public void setUnidadRR(String unidadRR) {
        this.unidadRR = unidadRR;
    }

    public String getAptoRR() {
        return aptoRR;
    }

    public void setAptoRR(String aptoRR) {
        this.aptoRR = aptoRR;
    }

    public String getComunidadRR() {
        return comunidadRR;
    }

    public void setComunidadRR(String comunidadRR) {
        this.comunidadRR = comunidadRR;
    }

    public String getDivisionRR() {
        return divisionRR;
    }

    public void setDivisionRR(String divisionRR) {
        this.divisionRR = divisionRR;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getEstado_unit() {
        return estado_unit;
    }

    public void setEstado_unit(String estado_unit) {
        this.estado_unit = estado_unit;
    }

    public String getHead_end() {
        return head_end;
    }

    public void setHead_end(String head_end) {
        this.head_end = head_end;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo_acomet() {
        return tipo_acomet;
    }

    public void setTipo_acomet(String tipo_acomet) {
        this.tipo_acomet = tipo_acomet;
    }

    public String getTipo_cbl_acometida() {
        return tipo_cbl_acometida;
    }

    public void setTipo_cbl_acometida(String tipo_cbl_acometida) {
        this.tipo_cbl_acometida = tipo_cbl_acometida;
    }

    public String getTipo_unidad() {
        return tipo_unidad;
    }

    public void setTipo_unidad(String tipo_unidad) {
        this.tipo_unidad = tipo_unidad;
    }

    public String getUlt_ubicacion() {
        return ult_ubicacion;
    }

    public void setUlt_ubicacion(String ult_ubicacion) {
        this.ult_ubicacion = ult_ubicacion;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getAmp() {
        return amp;
    }

    public void setAmp(String amp) {
        this.amp = amp;
    }

    /**
     * Obtiene información para auditoría
     *
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        String auditoria = "";
        auditoria = "Hhpp:";
        if (hhppId != null) {
            auditoria = auditoria + "hhppId=" + hhppId + ",";
        }
        if (direccion != null) {
            auditoria = auditoria + " direccion=" + direccion.getDirId() + ",";
        }
        if (nodo != null) {
            auditoria = auditoria + "nodo=" + nodo.getNodId() + ",";
        }
        if (tipoHhpp != null) {
            auditoria = auditoria + "tipoHhpp=" + tipoHhpp.getThhId() + ",";
        }
        if (subDireccion != null) {
            auditoria = auditoria + ", subDireccion=" + subDireccion.getSdiId() + ",";
        }
        if (tipoConexionHhpp != null) {
            auditoria = auditoria + "tipoConexionHhpp=" + tipoConexionHhpp.getThcId() + ",";
        }
        if (tipoRedHhpp != null) {
            auditoria = auditoria + "tipoRedHhpp=" + tipoRedHhpp.getThrId() + ",";
        }
        if (estadoHhpp != null) {
            auditoria = auditoria + "estadoHhpp=" + estadoHhpp.getEhhId() + ".";
        }
        if (idRR != null) {
            auditoria = auditoria + "idHhppRR=" + idRR + ".";
        }
        if (calleRR != null) {
            auditoria = auditoria + "calleRR=" + calleRR + ".";
        }
        if (unidadRR != null) {
            auditoria = auditoria + "unidadRR=" + unidadRR + ".";
        }
        if (aptoRR != null) {
            auditoria = auditoria + "aptoRR=" + aptoRR + ".";
        }
        if (comunidadRR != null) {
            auditoria = auditoria + "comunidadRR=" + comunidadRR + ".";
        }
        if (divisionRR != null) {
            auditoria = auditoria + "divisionRR=" + divisionRR + ".";
        }

        if (estado_unit != null) {
            auditoria = auditoria + "estado_unit=" + estado_unit + ".";
        }
        if (vendedor != null) {
            auditoria = auditoria + "vendedor=" + vendedor + ".";
        }
        if (codigo_postal != null) {
            auditoria = auditoria + "codigo_postal=" + codigo_postal + ".";
        }
        if (tipo_acomet != null) {
            auditoria = auditoria + "tipo_acomet=" + tipo_acomet + ".";
        }
        if (ult_ubicacion != null) {
            auditoria = auditoria + "ult_ubicacion=" + ult_ubicacion + ".";
        }
        if (head_end != null) {
            auditoria = auditoria + "head_end=" + head_end + ".";
        }
        if (tipo != null) {
            auditoria = auditoria + "tipo=" + tipo + ".";
        }
        if (edificio != null) {
            auditoria = auditoria + "edificio=" + edificio + ".";
        }
        if (tipo_unidad != null) {
            auditoria = auditoria + "tipo_unidad=" + tipo_unidad + ".";
        }
        if (tipo_cbl_acometida != null) {
            auditoria = auditoria + "tipo_cbl_acometida=" + tipo_cbl_acometida + ".";
        }
        auditoria = setAuditAmp(amp, auditoria);
        return auditoria;
    }

    private String setAuditAmp(String amp, String auditoria) {
        if (amp != null) {
            auditoria = auditoria + AMP + amp + POINT;
        }
        return auditoria;
    }
    
    /**
     * Sobre Escritura del método toString()
     *
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        String resultado = "";
        resultado = "Hhpp:";
        if (hhppId != null) {
            resultado = resultado + "hhppId=" + hhppId + ",";
        }
        if (direccion != null) {
            resultado = resultado + " direccion=" + direccion.getDirId() + ",";
        }
        if (nodo != null) {
            resultado = resultado + "nodo=" + nodo.getNodId() + ",";
        }
        if (tipoHhpp != null) {
            resultado = resultado + "tipoHhpp=" + tipoHhpp.getThhId() + ",";
        }
        if (subDireccion != null) {
            resultado = resultado + ", subDireccion=" + subDireccion.getSdiId() + ",";
        }
        if (tipoConexionHhpp != null) {
            resultado = resultado + "tipoConexionHhpp=" + tipoConexionHhpp.getThcId() + ",";
        }
        if (tipoRedHhpp != null) {
            resultado = resultado + "tipoRedHhpp=" + tipoRedHhpp.getThrId() + ",";
        }
        if (estadoHhpp != null) {
            resultado = resultado + "estadoHhpp=" + estadoHhpp.getEhhId() + ".";
        }
        return resultado;
    }

    public String getNap() {
        return nap;
    }

    public void setNap(String nap) {
        this.nap = nap;
    }
}
