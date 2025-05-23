package co.com.claro.mgl.jpa.entities;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TEC_EXPORT_FICHA", schema = "MGL_SCHEME")
@XmlRootElement
public class ExportFichasMgl implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "PreFicha.exportFichaSeq",sequenceName = "MGL_SCHEME.TEC_EXPORT_FICHA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "PreFicha.exportFichaSeq")
    @Column(name = "EXPORT_FICHA_ID", nullable = false)
    private BigDecimal exportId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFICHA_ID", referencedColumnName = "PREFICHA_ID", nullable = false)
    private PreFichaMgl prefichaId;
    @Column(name = "NOMBRE_ARCHIVO", nullable = false, length = 100)
    private String nombreArchivo;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false, length = 100)
    private String usuarioCrea;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_TECNOLOGIA_ID", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl tipoTecnologiaId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_ID", referencedColumnName = "NODO_ID", nullable = false)
    private NodoMgl nodo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTAMENTO_ID", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl departamento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CIUDAD_ID", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl ciudad;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CENTRO_POBLADO_ID", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl centroPoblado;
    
    @Column(name = "TEXTO_ARCHIVO", nullable = true)
    String textoArchivo;

    public String getTextoArchivo() {
        return textoArchivo;
    }

    public void setTextoArchivo(String textoArchivo) {
        this.textoArchivo = textoArchivo;
    }
    
    public ExportFichasMgl(){
          
    }

    public BigDecimal getExportId() {
        return exportId;
    }

    public void setExportId(BigDecimal exportId) {
        this.exportId = exportId;
    }

    public PreFichaMgl getPrefichaId() {
        if(prefichaId == null){
            prefichaId = new PreFichaMgl();
        }
        return prefichaId;
    }

    public void setPrefichaId(PreFichaMgl prefichaId) {
        this.prefichaId = prefichaId;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(String usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public CmtBasicaMgl getTipoTecnologiaId() {
        if(tipoTecnologiaId == null){
            tipoTecnologiaId = new CmtBasicaMgl();
        }
        return tipoTecnologiaId;
    }

    public void setTipoTecnologiaId(CmtBasicaMgl tipoTecnologiaId) {
        this.tipoTecnologiaId = tipoTecnologiaId;
    }

    public NodoMgl getNodo() {
        if(nodo == null){
            nodo = new NodoMgl();
        }
        return nodo;
    }

    public void setNodo(NodoMgl nodo) {
        this.nodo = nodo;
    }

    public GeograficoPoliticoMgl getDepartamento() {
        if(departamento == null){
            departamento = new GeograficoPoliticoMgl();
        }
        return departamento;
    }

    public void setDepartamento(GeograficoPoliticoMgl departamento) {
        this.departamento = departamento;
    }

    public GeograficoPoliticoMgl getCiudad() {
        if(ciudad == null){
            ciudad = new GeograficoPoliticoMgl();
        }
        return ciudad;
    }

    public void setCiudad(GeograficoPoliticoMgl ciudad) {
        this.ciudad = ciudad;
    }

    public GeograficoPoliticoMgl getCentroPoblado() {
        if(centroPoblado == null){
            centroPoblado = new GeograficoPoliticoMgl();
        }
        return centroPoblado;
    }

    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

}
