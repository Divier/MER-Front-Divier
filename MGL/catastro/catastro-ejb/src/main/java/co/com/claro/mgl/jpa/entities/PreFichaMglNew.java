/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity TEC_PREFICHA_NEW
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */

@Entity
@Table(name = "TEC_PREFICHA_NEW", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreFichaMglNew.findByFase", query = "SELECT p FROM PreFichaMglNew p where p.fase IN :faseList  AND p.estadoRegistro = 1 ORDER BY p.prfFechaGenera desc"),
    @NamedQuery(name = "PreFichaMglNew.findByFaseAndDate", query = "SELECT p FROM PreFichaMglNew p where p.fase IN :faseList  AND p.estadoRegistro = 1 and p.prfFechaGenera between :fechaInicial and :fechaFinal ORDER BY p.prfFechaGenera desc"),
    @NamedQuery(name = "PreFichaMglNew.findGeorreferenciada", query = "SELECT p FROM PreFichaMglNew p where p.georreferenciada = 1  AND p.estadoRegistro = 1 "),
    @NamedQuery(name = "PreFichaMglNew.findFichaToCreate", query = "SELECT p FROM PreFichaMglNew p where p.georreferenciada = 1 AND p.fichaCreada =0  AND p.estadoRegistro = 1  ORDER BY p.prfId DESC "),    
    @NamedQuery(name = "PreFichaMglNew.findFichaToCreateByDate", query = "SELECT p FROM PreFichaMglNew p where p.fichaCreada = 0  AND p.estadoRegistro = 1  and p.prfFechaGenera between :fechaInicial and :fechaFinal  ORDER BY p.prfId DESC "),    
    @NamedQuery(name = "PreFichaMglNew.findFichaToValidate", query = "SELECT p FROM PreFichaMglNew p where p.georreferenciada = 1 AND p.fichaCreada =1  AND p.estadoRegistro = 1 ")})

public class PreFichaMglNew implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "PreFichaMglNew.preFichaNewSeq",
            sequenceName = "MGL_SCHEME.TEC_PREFICHA_NEW_SQ", allocationSize = 1)
    @GeneratedValue(generator = "PreFichaMglNew.preFichaNewSeq")
    @Column(name = "ID_REGISTRO", nullable = false)
    private BigDecimal id;
    @Column(name = "PREFICHA_ID", nullable = false)
    private BigDecimal prfId;
    @Column(name = "USUARIO_GENERA", nullable = true, length = 200)
    private String prfUsuarioGenera;
    @Column(name = "FECHA_GENERA", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date prfFechaGenera;
    @Column(name = "USUARIO_VALIDACION", nullable = true, length = 200)
    private String prfUsuarioValidacion;
    @Column(name = "FECHA_VALIDACION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date prfFechaValidacion;
    @Column(name = "USUARIO_MODIFICA", nullable = true, length = 200)
    private String prfUsuarioModifica;
    @Column(name = "FECHA_MODIFICA", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date prfFechaModifica;
    @Column(name = "FASE", nullable = false, length = 50)
    private String fase;
    @Column(name = "NOMBRE_ARCHIVO", nullable = true, length = 500)
    private String nombreArchivo;
    @Column(name = "GEORREFERENCIADA")
    private int georreferenciada;
    @Column(name = "FICHA_CREADA")
    private int fichaCreada;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "MARCAS", length = 500, nullable = true)
    private String marcas;
    @Column(name = "NOTA", length = 500, nullable = true)
    private String nota;
    @Column(name = "CANTIDAD_REGISTROS")
    private int cantRegistros;
    @Column(name = "CANT_PROCESADOS")
    private int cantProcesados;
    @Column(name = "CANT_ERROR")
    private int cantError;
    @Column(name = "OBSERVACION", length = 1000, nullable = true)
    private String observacion;
    @Transient
    private boolean selected;

    public PreFichaMglNew() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getPrfId() {
        return prfId;
    }

    public void setPrfId(BigDecimal prfId) {
        this.prfId = prfId;
    }

    public String getPrfUsuarioGenera() {
        return prfUsuarioGenera;
    }

    public void setPrfUsuarioGenera(String prfUsuarioGenera) {
        this.prfUsuarioGenera = prfUsuarioGenera;
    }

    public Date getPrfFechaGenera() {
        return prfFechaGenera;
    }

    public void setPrfFechaGenera(Date prfFechaGenera) {
        this.prfFechaGenera = prfFechaGenera;
    }

    public String getPrfUsuarioValidacion() {
        return prfUsuarioValidacion;
    }

    public void setPrfUsuarioValidacion(String prfUsuarioValidacion) {
        this.prfUsuarioValidacion = prfUsuarioValidacion;
    }

    public Date getPrfFechaValidacion() {
        return prfFechaValidacion;
    }

    public void setPrfFechaValidacion(Date prfFechaValidacion) {
        this.prfFechaValidacion = prfFechaValidacion;
    }

    public String getPrfUsuarioModifica() {
        return prfUsuarioModifica;
    }

    public void setPrfUsuarioModifica(String prfUsuarioModifica) {
        this.prfUsuarioModifica = prfUsuarioModifica;
    }

    public Date getPrfFechaModifica() {
        return prfFechaModifica;
    }

    public void setPrfFechaModifica(Date prfFechaModifica) {
        this.prfFechaModifica = prfFechaModifica;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public int getGeorreferenciada() {
        return georreferenciada;
    }

    public void setGeorreferenciada(int georreferenciada) {
        this.georreferenciada = georreferenciada;
    }

    public int getFichaCreada() {
        return fichaCreada;
    }

    public void setFichaCreada(int fichaCreada) {
        this.fichaCreada = fichaCreada;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getMarcas() {
        return marcas;
    }

    public void setMarcas(String marcas) {
        this.marcas = marcas;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public int getCantRegistros() {
        return cantRegistros;
    }

    public void setCantRegistros(int cantRegistros) {
        this.cantRegistros = cantRegistros;
    }

    public int getCantProcesados() {
        return cantProcesados;
    }

    public void setCantProcesados(int cantProcesados) {
        this.cantProcesados = cantProcesados;
    }

    public int getCantError() {
        return cantError;
    }

    public void setCantError(int cantError) {
        this.cantError = cantError;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
