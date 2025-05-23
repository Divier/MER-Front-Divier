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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "TEC_PREFICHA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreFichaMgl.findByFase", query = "SELECT p FROM PreFichaMgl p where p.fase IN :faseList  AND p.estadoRegistro = 1 ORDER BY p.prfFechaGenera desc"),
    @NamedQuery(name = "PreFichaMgl.findByFaseAndDate", query = "SELECT p FROM PreFichaMgl p where p.fase IN :faseList  AND p.estadoRegistro = 1 and p.prfFechaGenera between :fechaInicial and :fechaFinal ORDER BY p.prfFechaGenera desc"),      
    @NamedQuery(name = "PreFichaMgl.findGeorreferenciada", query = "SELECT p FROM PreFichaMgl p where p.georreferenciada = '1'  AND p.estadoRegistro = 1 "),
    @NamedQuery(name = "PreFichaMgl.findFichaToCreate", query = "SELECT p FROM PreFichaMgl p where p.georreferenciada = '1' AND p.fichaCreada ='0'  AND p.estadoRegistro = 1  ORDER BY p.prfId DESC "),
    @NamedQuery(name = "PreFichaMgl.findFichaToCreateByDate", query = "SELECT p FROM PreFichaMgl p where p.fichaCreada ='0'  AND p.estadoRegistro = 1  and p.prfFechaGenera between :fechaInicial and :fechaFinal  ORDER BY p.prfId DESC "),
    @NamedQuery(name = "PreFichaMgl.findFichaToValidate", query = "SELECT p FROM PreFichaMgl p where p.georreferenciada = '1' AND p.fichaCreada ='1'  AND p.estadoRegistro = 1 ")})
public class PreFichaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "PreFicha.preFichaSeq",
            sequenceName = "MGL_SCHEME.TEC_PREFICHA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "PreFicha.preFichaSeq")
    @Column(name = "PREFICHA_ID", nullable = false)
    private BigDecimal prfId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_ID", referencedColumnName = "NODO_ID", nullable = false)
    private NodoMgl nodoMgl;
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
    @Column(name = "GEORREFERENCIADA", nullable = true, length = 1)
    private String georreferenciada;
    @Column(name = "FICHA_CREADA", nullable = true, length = 1)
    private String fichaCreada;
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
    @Column(name = "TIPO_DIRECCION")
    private String tipoDireccion;
    @Column(name = "MARCAS" , length = 500 , nullable = true)
    private String marcas;
    @Column(name = "NOTA" , length = 500 , nullable = true)
    private String nota;
    @Transient
    private boolean selected;



    public PreFichaMgl() {
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

    public NodoMgl getNodoMgl() {
        return nodoMgl;
    }

    public void setNodoMgl(NodoMgl nodoMgl) {
        this.nodoMgl = nodoMgl;
    }

    public String getGeorreferenciada() {
        return georreferenciada;
    }

    public void setGeorreferenciada(String georreferenciada) {
        this.georreferenciada = georreferenciada;
    }

    public String getFichaCreada() {
        return fichaCreada;
    }

    public void setFichaCreada(String fichaCreada) {
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

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
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

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
