package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad de la tabla CMT_DIRECCION.
 * 
* @author alejandro.martinez.ext@claro.com.co
 * 
* @versión 1.0
 */
@Entity
@Table(name = "CMT_DIRECCION$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtDireccionAuditoriaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)

    @Column(name = "DIRECCIONAUD_ID", nullable = false)
    private BigDecimal direccionAuditoriaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_CCMM_ID", nullable = false)
    private CmtDireccionMgl direccionCmtObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTAMATRIZ_ID")
    private CmtCuentaMatrizMgl cuentaMatrizObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl subEdificioObj;

    @Column(name = "COD_TIPO_DIR", nullable = false, length = 5)
    private String codTipoDir;

    @Column(name = "BARRIO", nullable = true, length = 100)
    private String barrio;

    @Column(name = "TIPO_VIA_PRINCIPAL", nullable = false, length = 50)
    private String tipoViaPrincipal;

    @Column(name = "NUM_VIA_PRINCIPAL", nullable = false, length = 5)
    private String numViaPrincipal;

    @Column(name = "LT_VIA_PRINCIPAL", nullable = false, length = 5)
    private String ltViaPrincipal;

    @Column(name = "NL_POST_VIA_P", nullable = false, length = 5)
    private String nlPostViaP;

    @Column(name = "BIS_VIA_PRINCIPAL", nullable = false, length = 5)
    private String bisViaPrincipal;

    @Column(name = "CUAD_VIA_PRINCIPAL", nullable = false, length = 20)
    private String cuadViaPrincipal;

    @Column(name = "TIPO_VIA_GENERADORA", nullable = false, length = 50)
    private String tipoViaGeneradora;

    @Column(name = "NUM_VIA_GENERADORA", nullable = false, length = 5)
    private String numViaGeneradora;

    @Column(name = "LT_VIA_GENERADORA", nullable = false, length = 5)
    private String ltViaGeneradora;

    @Column(name = "NL_POST_VIA_G", nullable = false, length = 5)
    private String nlPostViaG;

    @Column(name = "BIS_VIA_GENERADORA", nullable = false, length = 5)
    private String bisViaGeneradora;

    @Column(name = "CUAD_VIA_GENERADORA", nullable = false, length = 20)
    private String cuadViaGeneradora;

    @Column(name = "PLACA_DIRECCION", nullable = false, length = 5)
    private String placaDireccion;

    @Column(name = "CP_TIPO_NIVEL1", nullable = false, length = 50)
    private String cpTipoNivel1;

    @Column(name = "CP_TIPO_NIVEL2", nullable = false, length = 50)
    private String cpTipoNivel2;

    @Column(name = "CP_TIPO_NIVEL3", nullable = false, length = 50)
    private String cpTipoNivel3;

    @Column(name = "CP_TIPO_NIVEL4", nullable = false, length = 50)
    private String cpTipoNivel4;

    @Column(name = "CP_TIPO_NIVEL5", nullable = false, length = 50)
    private String cpTipoNivel5;

    @Column(name = "CP_TIPO_NIVEL6", nullable = false, length = 50)
    private String cpTipoNivel6;

    @Column(name = "CP_VALOR_NIVEL1", nullable = false, length = 200)
    private String cpValorNivel1;

    @Column(name = "CP_VALOR_NIVEL2", nullable = false, length = 200)
    private String cpValorNivel2;

    @Column(name = "CP_VALOR_NIVEL3", nullable = false, length = 200)
    private String cpValorNivel3;

    @Column(name = "CP_VALOR_NIVEL4", nullable = false, length = 200)
    private String cpValorNivel4;

    @Column(name = "CP_VALOR_NIVEL5", nullable = false, length = 200)
    private String cpValorNivel5;

    @Column(name = "CP_VALOR_NIVEL6", nullable = false, length = 200)
    private String cpValorNivel6;

    @Column(name = "MZ_TIPO_NIVEL1", nullable = false, length = 50)
    private String mzTipoNivel1;

    @Column(name = "MZ_TIPO_NIVEL2", nullable = false, length = 50)
    private String mzTipoNivel2;

    @Column(name = "MZ_TIPO_NIVEL3", nullable = false, length = 50)
    private String mzTipoNivel3;

    @Column(name = "MZ_TIPO_NIVEL4", nullable = false, length = 50)
    private String mzTipoNivel4;

    @Column(name = "MZ_TIPO_NIVEL5", nullable = false, length = 50)
    private String mzTipoNivel5;

    @Column(name = "MZ_TIPO_NIVEL6", nullable = false, length = 50)
    private String mzTipoNivel6;

    @Column(name = "MZ_VALOR_NIVEL1", nullable = false, length = 200)
    private String mzValorNivel1;

    @Column(name = "MZ_VALOR_NIVEL2", nullable = false, length = 200)
    private String mzValorNivel2;

    @Column(name = "MZ_VALOR_NIVEL3", nullable = false, length = 200)
    private String mzValorNivel3;

    @Column(name = "MZ_VALOR_NIVEL4", nullable = false, length = 200)
    private String mzValorNivel4;

    @Column(name = "MZ_VALOR_NIVEL5", nullable = false, length = 200)
    private String mzValorNivel5;

    @Column(name = "MZ_VALOR_NIVEL6", nullable = false, length = 200)
    private String mzValorNivel6;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_ID")
    private DireccionMgl direccionObj;

    @Column(name = "IT_TIPO_PLACA", nullable = false, length = 50)
    private String itTipoPlaca;

    @Column(name = "IT_VALOR_PLACA", nullable = false, length = 200)
    private String itValorPlaca;

    @Column(name = "ESTRATO")
    private int estrato;

    @Column(name = "ESTADO_DIR_GEO", nullable = false, length = 1)
    private String estadoDirGeo;

    @Column(name = "TDI_ID")
    private int tdiId;

    @Column(name = "LETRA3G", nullable = false, length = 5)
    private String letra3G;

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

    
    @Column(name = "CALLE_RR", nullable = false, length = 50)
    private String calleRr;

    
    @Column(name = "UNIDAD_RR", nullable = false, length = 10)
    private String unidadRr;

    
    @Column(name = "COMENTARIO", nullable = false, length = 10)
    private String comentario;

    @Column(name = "AUD_ACTION", length = 3)
    private String accionAuditoria;

    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAuditoria;

    
    @Column(name = "AUD_USER", length = 30)
    private String usuarioAuditoria;

    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria;


    public DetalleDireccionEntity getDetalleDireccionEntity(){
        DetalleDireccionEntity detalleDireccionEntity = new DetalleDireccionEntity();
        detalleDireccionEntity.setIdtipodireccion(codTipoDir);
        detalleDireccionEntity.setBarrio(barrio);
        detalleDireccionEntity.setTipoviaprincipal(tipoViaPrincipal);
        detalleDireccionEntity.setNumviaprincipal(numViaPrincipal);
        detalleDireccionEntity.setLtviaprincipal(ltViaPrincipal);
        detalleDireccionEntity.setNlpostviap(nlPostViaP);
        detalleDireccionEntity.setBisviaprincipal(bisViaPrincipal);
        detalleDireccionEntity.setCuadviaprincipal(cuadViaPrincipal);
        detalleDireccionEntity.setTipoviageneradora(tipoViaGeneradora);
        detalleDireccionEntity.setNumviageneradora(numViaGeneradora);
        detalleDireccionEntity.setLtviageneradora(ltViaGeneradora);
        detalleDireccionEntity.setNlpostviag(nlPostViaG);
        detalleDireccionEntity.setBisviageneradora(bisViaGeneradora);
        detalleDireccionEntity.setCuadviageneradora(cuadViaGeneradora);
        detalleDireccionEntity.setPlacadireccion(placaDireccion);
        detalleDireccionEntity.setCptiponivel1(cpTipoNivel1);
        detalleDireccionEntity.setCptiponivel2(cpTipoNivel2);
        detalleDireccionEntity.setCptiponivel3(cpTipoNivel3);
        detalleDireccionEntity.setCptiponivel4(cpTipoNivel4);
        detalleDireccionEntity.setCptiponivel5(cpTipoNivel5);
        detalleDireccionEntity.setCptiponivel6(cpTipoNivel6);
        detalleDireccionEntity.setCpvalornivel1(cpValorNivel1);
        detalleDireccionEntity.setCpvalornivel2(cpValorNivel2);
        detalleDireccionEntity.setCpvalornivel3(cpValorNivel3);
        detalleDireccionEntity.setCpvalornivel4(cpValorNivel4);
        detalleDireccionEntity.setCpvalornivel5(cpValorNivel5);
        detalleDireccionEntity.setCpvalornivel6(cpValorNivel6);
        detalleDireccionEntity.setMztiponivel1(mzTipoNivel1);
        detalleDireccionEntity.setMztiponivel2(mzTipoNivel2);
        detalleDireccionEntity.setMztiponivel3(mzTipoNivel3);
        detalleDireccionEntity.setMztiponivel4(mzTipoNivel4);
        detalleDireccionEntity.setMztiponivel5(mzTipoNivel5);
        detalleDireccionEntity.setMztiponivel6(mzTipoNivel6);
        detalleDireccionEntity.setMzvalornivel1(mzValorNivel1);
        detalleDireccionEntity.setMzvalornivel2(mzValorNivel2);
        detalleDireccionEntity.setMzvalornivel3(mzValorNivel3);
        detalleDireccionEntity.setMzvalornivel4(mzValorNivel4);
        detalleDireccionEntity.setMzvalornivel5(mzValorNivel5);
        detalleDireccionEntity.setMzvalornivel6(mzValorNivel6);
        detalleDireccionEntity.setEstadoDir(estadoDirGeo);
        detalleDireccionEntity.setItTipoPlaca(itTipoPlaca);
        return detalleDireccionEntity;
    }
    
    /**
     * @return the direccionId
     */
    public BigDecimal getDireccionId() {
        return direccionCmtObj.getDireccionId();
    }


    /**
     * @return the cuentaMatrizObj
     */
    public CmtCuentaMatrizMgl getCuentaMatrizObj() {
        return cuentaMatrizObj;
    }

    /**
     * @param cuentaMatrizObj the cuentaMatrizObj to set
     */
    public void setCuentaMatrizObj(CmtCuentaMatrizMgl cuentaMatrizObj) {
        this.cuentaMatrizObj = cuentaMatrizObj;
    }

    /**
     * @return the codTipoDir
     */
    public String getCodTipoDir() {
        return codTipoDir;
    }

    /**
     * @param codTipoDir the codTipoDir to set
     */
    public void setCodTipoDir(String codTipoDir) {
        this.codTipoDir = codTipoDir;
    }

    /**
     * @return the barrio
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     * @param barrio the barrio to set
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    /**
     * @return the tipoViaPrincipal
     */
    public String getTipoViaPrincipal() {
        return tipoViaPrincipal;
    }

    /**
     * @param tipoViaPrincipal the tipoViaPrincipal to set
     */
    public void setTipoViaPrincipal(String tipoViaPrincipal) {
        this.tipoViaPrincipal = tipoViaPrincipal;
    }

    /**
     * @return the numViaPrincipal
     */
    public String getNumViaPrincipal() {
        return numViaPrincipal;
    }

    /**
     * @param numViaPrincipal the numViaPrincipal to set
     */
    public void setNumViaPrincipal(String numViaPrincipal) {
        this.numViaPrincipal = numViaPrincipal;
    }

    /**
     * @return the ltViaPrincipal
     */
    public String getLtViaPrincipal() {
        return ltViaPrincipal;
    }

    /**
     * @param ltViaPrincipal the ltViaPrincipal to set
     */
    public void setLtViaPrincipal(String ltViaPrincipal) {
        this.ltViaPrincipal = ltViaPrincipal;
    }

    /**
     * @return the nlPostViaP
     */
    public String getNlPostViaP() {
        return nlPostViaP;
    }

    /**
     * @param nlPostViaP the nlPostViaP to set
     */
    public void setNlPostViaP(String nlPostViaP) {
        this.nlPostViaP = nlPostViaP;
    }

    /**
     * @return the bisViaPrincipal
     */
    public String getBisViaPrincipal() {
        return bisViaPrincipal;
    }

    /**
     * @param bisViaPrincipal the bisViaPrincipal to set
     */
    public void setBisViaPrincipal(String bisViaPrincipal) {
        this.bisViaPrincipal = bisViaPrincipal;
    }

    /**
     * @return the cuadViaPrincipal
     */
    public String getCuadViaPrincipal() {
        return cuadViaPrincipal;
    }

    /**
     * @param cuadViaPrincipal the cuadViaPrincipal to set
     */
    public void setCuadViaPrincipal(String cuadViaPrincipal) {
        this.cuadViaPrincipal = cuadViaPrincipal;
    }

    /**
     * @return the tipoViaGeneradora
     */
    public String getTipoViaGeneradora() {
        return tipoViaGeneradora;
    }

    /**
     * @param tipoViaGeneradora the tipoViaGeneradora to set
     */
    public void setTipoViaGeneradora(String tipoViaGeneradora) {
        this.tipoViaGeneradora = tipoViaGeneradora;
    }

    /**
     * @return the numViaGeneradora
     */
    public String getNumViaGeneradora() {
        return numViaGeneradora;
    }

    /**
     * @param numViaGeneradora the numViaGeneradora to set
     */
    public void setNumViaGeneradora(String numViaGeneradora) {
        this.numViaGeneradora = numViaGeneradora;
    }

    /**
     * @return the ltViaGeneradora
     */
    public String getLtViaGeneradora() {
        return ltViaGeneradora;
    }

    /**
     * @param ltViaGeneradora the ltViaGeneradora to set
     */
    public void setLtViaGeneradora(String ltViaGeneradora) {
        this.ltViaGeneradora = ltViaGeneradora;
    }

    /**
     * @return the nlPostViaG
     */
    public String getNlPostViaG() {
        return nlPostViaG;
    }

    /**
     * @param nlPostViaG the nlPostViaG to set
     */
    public void setNlPostViaG(String nlPostViaG) {
        this.nlPostViaG = nlPostViaG;
    }

    /**
     * @return the bisViaGeneradora
     */
    public String getBisViaGeneradora() {
        return bisViaGeneradora;
    }

    /**
     * @param bisViaGeneradora the bisViaGeneradora to set
     */
    public void setBisViaGeneradora(String bisViaGeneradora) {
        this.bisViaGeneradora = bisViaGeneradora;
    }

    /**
     * @return the cuadViaGeneradora
     */
    public String getCuadViaGeneradora() {
        return cuadViaGeneradora;
    }

    /**
     * @param cuadViaGeneradora the cuadViaGeneradora to set
     */
    public void setCuadViaGeneradora(String cuadViaGeneradora) {
        this.cuadViaGeneradora = cuadViaGeneradora;
    }

    /**
     * @return the placaDireccion
     */
    public String getPlacaDireccion() {
        return placaDireccion;
    }

    /**
     * @param placaDireccion the placaDireccion to set
     */
    public void setPlacaDireccion(String placaDireccion) {
        this.placaDireccion = placaDireccion;
    }

    /**
     * @return the cpTipoNivel1
     */
    public String getCpTipoNivel1() {
        return cpTipoNivel1;
    }

    /**
     * @param cpTipoNivel1 the cpTipoNivel1 to set
     */
    public void setCpTipoNivel1(String cpTipoNivel1) {
        this.cpTipoNivel1 = cpTipoNivel1;
    }

    /**
     * @return the cpTipoNivel2
     */
    public String getCpTipoNivel2() {
        return cpTipoNivel2;
    }

    /**
     * @param cpTipoNivel2 the cpTipoNivel2 to set
     */
    public void setCpTipoNivel2(String cpTipoNivel2) {
        this.cpTipoNivel2 = cpTipoNivel2;
    }

    /**
     * @return the cpTipoNivel3
     */
    public String getCpTipoNivel3() {
        return cpTipoNivel3;
    }

    /**
     * @param cpTipoNivel3 the cpTipoNivel3 to set
     */
    public void setCpTipoNivel3(String cpTipoNivel3) {
        this.cpTipoNivel3 = cpTipoNivel3;
    }

    /**
     * @return the cpTipoNivel4
     */
    public String getCpTipoNivel4() {
        return cpTipoNivel4;
    }

    /**
     * @param cpTipoNivel4 the cpTipoNivel4 to set
     */
    public void setCpTipoNivel4(String cpTipoNivel4) {
        this.cpTipoNivel4 = cpTipoNivel4;
    }

    /**
     * @return the cpTipoNivel5
     */
    public String getCpTipoNivel5() {
        return cpTipoNivel5;
    }

    /**
     * @param cpTipoNivel5 the cpTipoNivel5 to set
     */
    public void setCpTipoNivel5(String cpTipoNivel5) {
        this.cpTipoNivel5 = cpTipoNivel5;
    }

    /**
     * @return the cpTipoNivel6
     */
    public String getCpTipoNivel6() {
        return cpTipoNivel6;
    }

    /**
     * @param cpTipoNivel6 the cpTipoNivel6 to set
     */
    public void setCpTipoNivel6(String cpTipoNivel6) {
        this.cpTipoNivel6 = cpTipoNivel6;
    }

    /**
     * @return the cpValorNivel1
     */
    public String getCpValorNivel1() {
        return cpValorNivel1;
    }

    /**
     * @param cpValorNivel1 the cpValorNivel1 to set
     */
    public void setCpValorNivel1(String cpValorNivel1) {
        this.cpValorNivel1 = cpValorNivel1;
    }

    /**
     * @return the cpValorNivel2
     */
    public String getCpValorNivel2() {
        return cpValorNivel2;
    }

    /**
     * @param cpValorNivel2 the cpValorNivel2 to set
     */
    public void setCpValorNivel2(String cpValorNivel2) {
        this.cpValorNivel2 = cpValorNivel2;
    }

    /**
     * @return the cpValorNivel3
     */
    public String getCpValorNivel3() {
        return cpValorNivel3;
    }

    /**
     * @param cpValorNivel3 the cpValorNivel3 to set
     */
    public void setCpValorNivel3(String cpValorNivel3) {
        this.cpValorNivel3 = cpValorNivel3;
    }

    /**
     * @return the cpValorNivel4
     */
    public String getCpValorNivel4() {
        return cpValorNivel4;
    }

    /**
     * @param cpValorNivel4 the cpValorNivel4 to set
     */
    public void setCpValorNivel4(String cpValorNivel4) {
        this.cpValorNivel4 = cpValorNivel4;
    }

    /**
     * @return the cpValorNivel5
     */
    public String getCpValorNivel5() {
        return cpValorNivel5;
    }

    /**
     * @param cpValorNivel5 the cpValorNivel5 to set
     */
    public void setCpValorNivel5(String cpValorNivel5) {
        this.cpValorNivel5 = cpValorNivel5;
    }

    /**
     * @return the cpValorNivel6
     */
    public String getCpValorNivel6() {
        return cpValorNivel6;
    }

    /**
     * @param cpValorNivel6 the cpValorNivel6 to set
     */
    public void setCpValorNivel6(String cpValorNivel6) {
        this.cpValorNivel6 = cpValorNivel6;
    }

    /**
     * @return the mzTipoNivel1
     */
    public String getMzTipoNivel1() {
        return mzTipoNivel1;
    }

    /**
     * @param mzTipoNivel1 the mzTipoNivel1 to set
     */
    public void setMzTipoNivel1(String mzTipoNivel1) {
        this.mzTipoNivel1 = mzTipoNivel1;
    }

    /**
     * @return the mzTipoNivel2
     */
    public String getMzTipoNivel2() {
        return mzTipoNivel2;
    }

    /**
     * @param mzTipoNivel2 the mzTipoNivel2 to set
     */
    public void setMzTipoNivel2(String mzTipoNivel2) {
        this.mzTipoNivel2 = mzTipoNivel2;
    }

    /**
     * @return the mzTipoNivel3
     */
    public String getMzTipoNivel3() {
        return mzTipoNivel3;
    }

    /**
     * @param mzTipoNivel3 the mzTipoNivel3 to set
     */
    public void setMzTipoNivel3(String mzTipoNivel3) {
        this.mzTipoNivel3 = mzTipoNivel3;
    }

    /**
     * @return the mzTipoNivel4
     */
    public String getMzTipoNivel4() {
        return mzTipoNivel4;
    }

    /**
     * @param mzTipoNivel4 the mzTipoNivel4 to set
     */
    public void setMzTipoNivel4(String mzTipoNivel4) {
        this.mzTipoNivel4 = mzTipoNivel4;
    }

    /**
     * @return the mzTipoNivel5
     */
    public String getMzTipoNivel5() {
        return mzTipoNivel5;
    }

    /**
     * @param mzTipoNivel5 the mzTipoNivel5 to set
     */
    public void setMzTipoNivel5(String mzTipoNivel5) {
        this.mzTipoNivel5 = mzTipoNivel5;
    }

    /**
     * @return the mzTipoNivel6
     */
    public String getMzTipoNivel6() {
        return mzTipoNivel6;
    }

    /**
     * @param mzTipoNivel6 the mzTipoNivel6 to set
     */
    public void setMzTipoNivel6(String mzTipoNivel6) {
        this.mzTipoNivel6 = mzTipoNivel6;
    }

    /**
     * @return the mzValorNivel1
     */
    public String getMzValorNivel1() {
        return mzValorNivel1;
    }

    /**
     * @param mzValorNivel1 the mzValorNivel1 to set
     */
    public void setMzValorNivel1(String mzValorNivel1) {
        this.mzValorNivel1 = mzValorNivel1;
    }

    /**
     * @return the mzValorNivel2
     */
    public String getMzValorNivel2() {
        return mzValorNivel2;
    }

    /**
     * @param mzValorNivel2 the mzValorNivel2 to set
     */
    public void setMzValorNivel2(String mzValorNivel2) {
        this.mzValorNivel2 = mzValorNivel2;
    }

    /**
     * @return the mzValorNivel3
     */
    public String getMzValorNivel3() {
        return mzValorNivel3;
    }

    /**
     * @param mzValorNivel3 the mzValorNivel3 to set
     */
    public void setMzValorNivel3(String mzValorNivel3) {
        this.mzValorNivel3 = mzValorNivel3;
    }

    /**
     * @return the mzValorNivel4
     */
    public String getMzValorNivel4() {
        return mzValorNivel4;
    }

    /**
     * @param mzValorNivel4 the mzValorNivel4 to set
     */
    public void setMzValorNivel4(String mzValorNivel4) {
        this.mzValorNivel4 = mzValorNivel4;
    }

    /**
     * @return the mzValorNivel5
     */
    public String getMzValorNivel5() {
        return mzValorNivel5;
    }

    /**
     * @param mzValorNivel5 the mzValorNivel5 to set
     */
    public void setMzValorNivel5(String mzValorNivel5) {
        this.mzValorNivel5 = mzValorNivel5;
    }

    /**
     * @return the mzValorNivel6
     */
    public String getMzValorNivel6() {
        return mzValorNivel6;
    }

    /**
     * @param mzValorNivel6 the mzValorNivel6 to set
     */
    public void setMzValorNivel6(String mzValorNivel6) {
        this.mzValorNivel6 = mzValorNivel6;
    }

    /**
     * @return the direccionObj
     */
    public DireccionMgl getDireccionObj() {
        return direccionObj;
    }

    /**
     * @param direccionObj the direccionObj to set
     */
    public void setDireccionObj(DireccionMgl direccionObj) {
        this.direccionObj = direccionObj;
    }

    /**
     * @return the itTipoPlaca
     */
    public String getItTipoPlaca() {
        return itTipoPlaca;
    }

    /**
     * @param itTipoPlaca the itTipoPlaca to set
     */
    public void setItTipoPlaca(String itTipoPlaca) {
        this.itTipoPlaca = itTipoPlaca;
    }

    /**
     * @return the itValorPlaca
     */
    public String getItValorPlaca() {
        return itValorPlaca;
    }

    /**
     * @param itValorPlaca the itValorPlaca to set
     */
    public void setItValorPlaca(String itValorPlaca) {
        this.itValorPlaca = itValorPlaca;
    }

    /**
     * @return the estrato
     */
    public int getEstrato() {
        return estrato;
    }

    /**
     * @param estrato the estrato to set
     */
    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    /**
     * @return the estadoDirGeo
     */
    public String getEstadoDirGeo() {
        return estadoDirGeo;
    }

    /**
     * @param estadoDirGeo the estadoDirGeo to set
     */
    public void setEstadoDirGeo(String estadoDirGeo) {
        this.estadoDirGeo = estadoDirGeo;
    }

    /**
     * @return the tdiId
     */
    public int getTdiId() {
        return tdiId;
    }

    /**
     * @param tdiId the tdiId to set
     */
    public void setTdiId(int tdiId) {
        this.tdiId = tdiId;
    }

    /**
     * @return the letra3G
     */
    public String getLetra3G() {
        return letra3G;
    }

    /**
     * @param letra3g the letra3G to set
     */
    public void setLetra3G(String letra3g) {
        letra3G = letra3g;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the perfilCreacion
     */
    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * @param perfilCreacion the perfilCreacion to set
     */
    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    /**
     * @return the fechaEdicion
     */
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * @param fechaEdicion the fechaEdicion to set
     */
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    /**
     * @return the usuarioEdicion
     */
    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    /**
     * @param usuarioEdicion the usuarioEdicion to set
     */
    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    /**
     * @return the perfilEdicion
     */
    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * @param perfilEdicion the perfilEdicion to set
     */
    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    /**
     * @return the estadoRegistro
     */
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro the estadoRegistro to set
     */
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public CmtSubEdificioMgl getSubEdificioObj() {
        return subEdificioObj;
    }

    public void setSubEdificioObj(CmtSubEdificioMgl subEdificioObj) {
        this.subEdificioObj = subEdificioObj;
    }
    


    /**
     * @return the calleRr
     */
    public String getCalleRr() {
        return calleRr;
    }

    /**
     * @param calleRr the calleRr to set
     */
    public void setCalleRr(String calleRr) {
        this.calleRr = calleRr;
    }

    /**
     * @return the unidadRr
     */
    public String getUnidadRr() {
        return unidadRr;
    }

    /**
     * @param unidadRr the unidadRr to set
     */
    public void setUnidadRr(String unidadRr) {
        this.unidadRr = unidadRr;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BigDecimal getDireccionAuditoriaId() {
        return direccionAuditoriaId;
    }

    public void setDireccionAuditoriaId(BigDecimal direccionAuditoriaId) {
        this.direccionAuditoriaId = direccionAuditoriaId;
    }

    public CmtDireccionMgl getDireccionCmtObj() {
        return direccionCmtObj;
    }

    public void setDireccionCmtObj(CmtDireccionMgl direccionCmtObj) {
        this.direccionCmtObj = direccionCmtObj;
    }

    public String getAccionAuditoria() {
        return accionAuditoria;
    }

    public void setAccionAuditoria(String accionAuditoria) {
        this.accionAuditoria = accionAuditoria;
    }

    public Date getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(Date fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public String getUsuarioAuditoria() {
        return usuarioAuditoria;
    }

    public void setUsuarioAuditoria(String usuarioAuditoria) {
        this.usuarioAuditoria = usuarioAuditoria;
    }

    public BigDecimal getSesionAuditoria() {
        return sesionAuditoria;
    }

    public void setSesionAuditoria(BigDecimal sesionAuditoria) {
        this.sesionAuditoria = sesionAuditoria;
    }
}
