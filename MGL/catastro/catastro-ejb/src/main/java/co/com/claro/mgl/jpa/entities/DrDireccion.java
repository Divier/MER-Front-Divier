/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Parzifal de León
 */
@Entity
@Table(name = "TEC_SOLICITUD_DIRECCION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DrDireccion.findAll", query = "SELECT s FROM DrDireccion s")})
public class DrDireccion implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "TEC_SOLICITUD_DIRECCION_SQ", sequenceName = "TEC_SOLICITUD_DIRECCION_SQ", allocationSize = 1, schema = "MGL_SCHEME")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEC_SOLICITUD_DIRECCION_SQ")
    @Column(name = "SOLICITUD_DIRECCION_ID", nullable = false)
    private BigDecimal id;
    @Column(name = "SOL_TEC_HABILITADA_ID")
    private BigDecimal idSolicitud;
    @Column(name = "COD_TIPO_DIRECCION")
    private String idTipoDireccion;
    @Column(name = "DIR_PRINC_ALT")
    private String dirPrincAlt;
    @Column(name = "BARRIO")
    private String barrio;
    @Column(name = "TIPO_VIA_PRINCIPAL")
    private String tipoViaPrincipal;
    @Column(name = "NUM_VIA_PRINCIPAL")
    private String numViaPrincipal;
    @Column(name = "LT_VIA_PRINCIPAL")
    private String ltViaPrincipal;
    @Column(name = "NL_POST_VIA_P")
    private String nlPostViaP;
    @Column(name = "BIS_VIA_PRINCIPAL")
    private String bisViaPrincipal;
    @Column(name = "CUAD_VIA_PRINCIPAL")
    private String cuadViaPrincipal;
    @Column(name = "TIPO_VIA_GENERADORA")
    private String tipoViaGeneradora;
    @Column(name = "NUM_VIA_GENERADORA")
    private String numViaGeneradora;
    @Column(name = "LT_VIA_GENERADORA")
    private String ltViaGeneradora;
    @Column(name = "NL_POST_VIA_G")
    private String nlPostViaG;
    @Column(name = "BIS_VIA_GENERADORA")
    private String bisViaGeneradora;
    @Column(name = "CUAD_VIA_GENERADORA")
    private String cuadViaGeneradora;
    @Column(name = "PLACA_DIRECCION")
    private String placaDireccion;
    @Column(name = "CP_TIPO_NIVEL1")
    private String cpTipoNivel1;
    @Column(name = "CP_TIPO_NIVEL2")
    private String cpTipoNivel2;
    @Column(name = "CP_TIPO_NIVEL3")
    private String cpTipoNivel3;
    @Column(name = "CP_TIPO_NIVEL4")
    private String cpTipoNivel4;
    @Column(name = "CP_TIPO_NIVEL5")
    private String cpTipoNivel5;
    @Column(name = "CP_TIPO_NIVEL6")
    private String cpTipoNivel6;
    @Column(name = "CP_VALOR_NIVEL1")
    private String cpValorNivel1;
    @Column(name = "CP_VALOR_NIVEL2")
    private String cpValorNivel2;
    @Column(name = "CP_VALOR_NIVEL3")
    private String cpValorNivel3;
    @Column(name = "CP_VALOR_NIVEL4")
    private String cpValorNivel4;
    @Column(name = "CP_VALOR_NIVEL5")
    private String cpValorNivel5;
    @Column(name = "CP_VALOR_NIVEL6")
    private String cpValorNivel6;
    @Column(name = "MZ_TIPO_NIVEL1")
    private String mzTipoNivel1;
    @Column(name = "MZ_TIPO_NIVEL2")
    private String mzTipoNivel2;
    @Column(name = "MZ_TIPO_NIVEL3")
    private String mzTipoNivel3;
    @Column(name = "MZ_TIPO_NIVEL4")
    private String mzTipoNivel4;
    @Column(name = "MZ_TIPO_NIVEL5")
    private String mzTipoNivel5;
    @Column(name = "MZ_VALOR_NIVEL1")
    private String mzValorNivel1;
    @Column(name = "MZ_VALOR_NIVEL2")
    private String mzValorNivel2;
    @Column(name = "MZ_VALOR_NIVEL3")
    private String mzValorNivel3;
    @Column(name = "MZ_VALOR_NIVEL4")
    private String mzValorNivel4;
    @Column(name = "MZ_VALOR_NIVEL5")
    private String mzValorNivel5;
    @Column(name = "ID_DIR_CATASTRO")
    private String idDirCatastro;
    @Column(name = "MZ_TIPO_NIVEL6")
    private String mzTipoNivel6;
    @Column(name = "MZ_VALOR_NIVEL6")
    private String mzValorNivel6;
    @Column(name = "IT_TIPO_PLACA")
    private String itTipoPlaca;
    @Column(name = "IT_VALOR_PLACA")
    private String itValorPlaca;
    @Column(name = "ESTRATO")
    private String estrato;
    @Column(name = "ESTADO_DIR_GEO")
    private String estadoDirGeo;
    @Column(name = "LETRA3G")
    private String letra3G;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = false)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION", nullable = false)
    private int perfilEdicion;
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;
    @Transient
    private String dirEstado;
    @Transient
    private String barrioTxtBM;
    @Transient
    private String geoReferenciadaString;  
    @Transient
    private String observacionFicha;
    @Transient
    private String direccionHHPP;
    @Transient
    private String tipoViviendaHHPP;
    @Transient
    private String direccionRespuestaGeo;
    @Transient
    private String multiOrigen;
    @Transient
    private boolean direccionFactibilidad;    
    @Transient
    private boolean actualizaCasa = false;       

    public void obtenerFromCmtDireccionMgl(CmtDireccionMgl cmtDireccionMgl) {
        idTipoDireccion = cmtDireccionMgl.getCodTipoDir();
        barrio = cmtDireccionMgl.getBarrio();
        tipoViaPrincipal = cmtDireccionMgl.getTipoViaPrincipal();
        numViaPrincipal = cmtDireccionMgl.getNumViaPrincipal();
        ltViaPrincipal = cmtDireccionMgl.getLtViaPrincipal();
        nlPostViaP = cmtDireccionMgl.getNlPostViaP();
        bisViaPrincipal = cmtDireccionMgl.getBisViaPrincipal();
        cuadViaPrincipal = cmtDireccionMgl.getCuadViaPrincipal();
        tipoViaGeneradora = cmtDireccionMgl.getTipoViaGeneradora();
        numViaGeneradora = cmtDireccionMgl.getNumViaGeneradora();
        ltViaGeneradora = cmtDireccionMgl.getLtViaGeneradora();
        nlPostViaG = cmtDireccionMgl.getNlPostViaG();
        bisViaGeneradora = cmtDireccionMgl.getBisViaGeneradora();
        cuadViaGeneradora = cmtDireccionMgl.getCuadViaGeneradora();
        placaDireccion = cmtDireccionMgl.getPlacaDireccion();
        cpTipoNivel1 = cmtDireccionMgl.getCpTipoNivel1();
        cpTipoNivel2 = cmtDireccionMgl.getCpTipoNivel2();
        cpTipoNivel3 = cmtDireccionMgl.getCpTipoNivel3();
        cpTipoNivel4 = cmtDireccionMgl.getCpTipoNivel4();
        cpTipoNivel5 = cmtDireccionMgl.getCpTipoNivel5();
        cpTipoNivel6 = cmtDireccionMgl.getCpTipoNivel6();
        cpValorNivel1 = cmtDireccionMgl.getCpValorNivel1();
        cpValorNivel2 = cmtDireccionMgl.getCpValorNivel2();
        cpValorNivel3 = cmtDireccionMgl.getCpValorNivel3();
        cpValorNivel4 = cmtDireccionMgl.getCpValorNivel4();
        cpValorNivel5 = cmtDireccionMgl.getCpValorNivel5();
        cpValorNivel6 = cmtDireccionMgl.getCpValorNivel6();
        mzTipoNivel1 = cmtDireccionMgl.getMzTipoNivel1();
        mzTipoNivel2 = cmtDireccionMgl.getMzTipoNivel2();
        mzTipoNivel3 = cmtDireccionMgl.getMzTipoNivel3();
        mzTipoNivel4 = cmtDireccionMgl.getMzTipoNivel4();
        mzTipoNivel5 = cmtDireccionMgl.getMzTipoNivel5();
        mzTipoNivel6 = cmtDireccionMgl.getMzTipoNivel6();
        mzValorNivel1 = cmtDireccionMgl.getMzValorNivel1();
        mzValorNivel2 = cmtDireccionMgl.getMzValorNivel2();
        mzValorNivel3 = cmtDireccionMgl.getMzValorNivel3();
        mzValorNivel4 = cmtDireccionMgl.getMzValorNivel4();
        mzValorNivel5 = cmtDireccionMgl.getMzValorNivel5();
        mzValorNivel6 = cmtDireccionMgl.getMzValorNivel6();
        estadoDirGeo = cmtDireccionMgl.getEstadoDirGeo();
        itTipoPlaca = cmtDireccionMgl.getItTipoPlaca();
        itValorPlaca = cmtDireccionMgl.getItValorPlaca();
        estrato = String.valueOf(cmtDireccionMgl.getEstrato());
    }

    public CmtDireccionMgl convertToCmtDireccionMgl() {
        CmtDireccionMgl cmtDireccionMgl = new CmtDireccionMgl();
        cmtDireccionMgl.setCodTipoDir(idTipoDireccion);
        cmtDireccionMgl.setBarrio(barrio);
        cmtDireccionMgl.setTipoViaPrincipal(tipoViaPrincipal);
        cmtDireccionMgl.setNumViaPrincipal(numViaPrincipal);
        cmtDireccionMgl.setLtViaPrincipal(ltViaPrincipal);
        cmtDireccionMgl.setNlPostViaP(nlPostViaP);
        cmtDireccionMgl.setBisViaPrincipal(bisViaPrincipal);
        cmtDireccionMgl.setCuadViaPrincipal(cuadViaPrincipal);
        cmtDireccionMgl.setTipoViaGeneradora(tipoViaGeneradora);
        cmtDireccionMgl.setNumViaGeneradora(numViaGeneradora);
        cmtDireccionMgl.setLtViaGeneradora(ltViaGeneradora);
        cmtDireccionMgl.setNlPostViaG(nlPostViaG);
        cmtDireccionMgl.setBisViaGeneradora(bisViaGeneradora);
        cmtDireccionMgl.setCuadViaGeneradora(cuadViaGeneradora);
        cmtDireccionMgl.setPlacaDireccion(placaDireccion);
        cmtDireccionMgl.setCpTipoNivel1(cpTipoNivel1);
        cmtDireccionMgl.setCpTipoNivel2(cpTipoNivel2);
        cmtDireccionMgl.setCpTipoNivel3(cpTipoNivel3);
        cmtDireccionMgl.setCpTipoNivel4(cpTipoNivel4);
        cmtDireccionMgl.setCpTipoNivel5(cpTipoNivel5);
        cmtDireccionMgl.setCpTipoNivel6(cpTipoNivel6);
        cmtDireccionMgl.setCpValorNivel1(cpValorNivel1);
        cmtDireccionMgl.setCpValorNivel2(cpValorNivel2);
        cmtDireccionMgl.setCpValorNivel3(cpValorNivel3);
        cmtDireccionMgl.setCpValorNivel4(cpValorNivel4);
        cmtDireccionMgl.setCpValorNivel5(cpValorNivel5);
        cmtDireccionMgl.setCpValorNivel6(cpValorNivel6);
        cmtDireccionMgl.setMzTipoNivel1(mzTipoNivel1);
        cmtDireccionMgl.setMzTipoNivel2(mzTipoNivel2);
        cmtDireccionMgl.setMzTipoNivel3(mzTipoNivel3);
        cmtDireccionMgl.setMzTipoNivel4(mzTipoNivel4);
        cmtDireccionMgl.setMzTipoNivel5(mzTipoNivel5);
        cmtDireccionMgl.setMzTipoNivel6(mzTipoNivel6);
        cmtDireccionMgl.setMzValorNivel1(mzValorNivel1);
        cmtDireccionMgl.setMzValorNivel2(mzValorNivel2);
        cmtDireccionMgl.setMzValorNivel3(mzValorNivel3);
        cmtDireccionMgl.setMzValorNivel4(mzValorNivel4);
        cmtDireccionMgl.setMzValorNivel5(mzValorNivel5);
        cmtDireccionMgl.setMzValorNivel6(mzValorNivel6);
        cmtDireccionMgl.setEstadoDirGeo(estadoDirGeo);
        cmtDireccionMgl.setItTipoPlaca(itTipoPlaca);
        cmtDireccionMgl.setItValorPlaca(itValorPlaca);
        cmtDireccionMgl.setEstrato(estrato == null ? 0:Integer.valueOf(estrato).intValue());
        return cmtDireccionMgl;
    }

    public DetalleDireccionEntity convertToDetalleDireccionEntity() {
        DetalleDireccionEntity detalleDireccionEntity = new DetalleDireccionEntity();
        detalleDireccionEntity.setIdtipodireccion(idTipoDireccion);
        detalleDireccionEntity.setBarrio((barrioTxtBM == null || barrioTxtBM.isEmpty() ? barrio : barrioTxtBM));
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
        detalleDireccionEntity.setItValorPlaca(itValorPlaca);
        detalleDireccionEntity.setEstrato(estrato);
        detalleDireccionEntity.setIdDirCatastro(idDirCatastro);
        return detalleDireccionEntity;
    }

    public void obtenerFromDetalleDireccionEntity(DetalleDireccionEntity detalleDireccionEntity) {
        idTipoDireccion = detalleDireccionEntity.getIdtipodireccion();
        barrio = detalleDireccionEntity.getBarrio();
        tipoViaPrincipal = detalleDireccionEntity.getTipoviaprincipal();
        numViaPrincipal = detalleDireccionEntity.getNumviaprincipal();
        ltViaPrincipal = detalleDireccionEntity.getLtviaprincipal();
        nlPostViaP = detalleDireccionEntity.getNlpostviap();
        bisViaPrincipal = detalleDireccionEntity.getBisviaprincipal();
        cuadViaPrincipal = detalleDireccionEntity.getCuadviaprincipal();
        tipoViaGeneradora = detalleDireccionEntity.getTipoviageneradora();
        numViaGeneradora = detalleDireccionEntity.getNumviageneradora();
        ltViaGeneradora = detalleDireccionEntity.getLtviageneradora();
        nlPostViaG = detalleDireccionEntity.getNlpostviag();
        bisViaGeneradora = detalleDireccionEntity.getBisviageneradora();
        cuadViaGeneradora = detalleDireccionEntity.getCuadviageneradora();
        placaDireccion = detalleDireccionEntity.getPlacadireccion();
        cpTipoNivel1 = detalleDireccionEntity.getCptiponivel1();
        cpTipoNivel2 = detalleDireccionEntity.getCptiponivel2();
        cpTipoNivel3 = detalleDireccionEntity.getCptiponivel3();
        cpTipoNivel4 = detalleDireccionEntity.getCptiponivel4();
        cpTipoNivel5 = detalleDireccionEntity.getCptiponivel5();
        cpTipoNivel6 = detalleDireccionEntity.getCptiponivel6();
        cpValorNivel1 = detalleDireccionEntity.getCpvalornivel1();
        cpValorNivel2 = detalleDireccionEntity.getCpvalornivel2();
        cpValorNivel3 = detalleDireccionEntity.getCpvalornivel3();
        cpValorNivel4 = detalleDireccionEntity.getCpvalornivel4();
        cpValorNivel5 = detalleDireccionEntity.getCpvalornivel5();
        cpValorNivel6 = detalleDireccionEntity.getCpvalornivel6();
        mzTipoNivel1 = detalleDireccionEntity.getMztiponivel1();
        mzTipoNivel2 = detalleDireccionEntity.getMztiponivel2();
        mzTipoNivel3 = detalleDireccionEntity.getMztiponivel3();
        mzTipoNivel4 = detalleDireccionEntity.getMztiponivel4();
        mzTipoNivel5 = detalleDireccionEntity.getMztiponivel5();
        mzTipoNivel6 = detalleDireccionEntity.getMztiponivel6();
        mzValorNivel1 = detalleDireccionEntity.getMzvalornivel1();
        mzValorNivel2 = detalleDireccionEntity.getMzvalornivel2();
        mzValorNivel3 = detalleDireccionEntity.getMzvalornivel3();
        mzValorNivel4 = detalleDireccionEntity.getMzvalornivel4();
        mzValorNivel5 = detalleDireccionEntity.getMzvalornivel5();
        mzValorNivel6 = detalleDireccionEntity.getMzvalornivel6();
        estadoDirGeo = detalleDireccionEntity.getEstadoDir();
        estrato = detalleDireccionEntity.getEstrato();
        itTipoPlaca = detalleDireccionEntity.getItTipoPlaca();
        itValorPlaca = detalleDireccionEntity.getItValorPlaca();
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(BigDecimal idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIdTipoDireccion() {
        return idTipoDireccion;
    }

    public void setIdTipoDireccion(String idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }

    public String getDirPrincAlt() {
        return dirPrincAlt;
    }

    public void setDirPrincAlt(String dirPrincAlt) {
        this.dirPrincAlt = dirPrincAlt;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipoViaPrincipal() {
        return tipoViaPrincipal;
    }

    public void setTipoViaPrincipal(String tipoViaPrincipal) {
        this.tipoViaPrincipal = tipoViaPrincipal;
    }

    public String getNumViaPrincipal() {
        return numViaPrincipal;
    }

    public void setNumViaPrincipal(String numViaPrincipal) {
        this.numViaPrincipal = numViaPrincipal;
    }

    public String getLtViaPrincipal() {
        return ltViaPrincipal;
    }

    public void setLtViaPrincipal(String ltViaPrincipal) {
        this.ltViaPrincipal = ltViaPrincipal;
    }

    public String getNlPostViaP() {
        return nlPostViaP;
    }

    public void setNlPostViaP(String nlPostViaP) {
        this.nlPostViaP = nlPostViaP;
    }

    public String getBisViaPrincipal() {
        return bisViaPrincipal;
    }

    public void setBisViaPrincipal(String bisViaPrincipal) {
        this.bisViaPrincipal = bisViaPrincipal;
    }

    public String getCuadViaPrincipal() {
        return cuadViaPrincipal;
    }

    public void setCuadViaPrincipal(String cuadViaPrincipal) {
        this.cuadViaPrincipal = cuadViaPrincipal;
    }

    public String getTipoViaGeneradora() {
        return tipoViaGeneradora;
    }

    public void setTipoViaGeneradora(String tipoViaGeneradora) {
        this.tipoViaGeneradora = tipoViaGeneradora;
    }

    public String getNumViaGeneradora() {
        return numViaGeneradora;
    }

    public void setNumViaGeneradora(String numViaGeneradora) {
        this.numViaGeneradora = numViaGeneradora;
    }

    public String getLtViaGeneradora() {
        return ltViaGeneradora;
    }

    public void setLtViaGeneradora(String ltViaGeneradora) {
        this.ltViaGeneradora = ltViaGeneradora;
    }

    public String getNlPostViaG() {
        return nlPostViaG;
    }

    public void setNlPostViaG(String nlPostViaG) {
        this.nlPostViaG = nlPostViaG;
    }

    public String getBisViaGeneradora() {
        return bisViaGeneradora;
    }

    public void setBisViaGeneradora(String bisViaGeneradora) {
        this.bisViaGeneradora = bisViaGeneradora;
    }

    public String getCuadViaGeneradora() {
        return cuadViaGeneradora;
    }

    public void setCuadViaGeneradora(String cuadViaGeneradora) {
        this.cuadViaGeneradora = cuadViaGeneradora;
    }

    public String getPlacaDireccion() {
        return placaDireccion;
    }

    public void setPlacaDireccion(String placaDireccion) {
        this.placaDireccion = placaDireccion;
    }

    public String getCpTipoNivel1() {
        return cpTipoNivel1;
    }

    public void setCpTipoNivel1(String cpTipoNivel1) {
        this.cpTipoNivel1 = cpTipoNivel1;
    }

    public String getCpTipoNivel2() {
        return cpTipoNivel2;
    }

    public void setCpTipoNivel2(String cpTipoNivel2) {
        this.cpTipoNivel2 = cpTipoNivel2;
    }

    public String getCpTipoNivel3() {
        return cpTipoNivel3;
    }

    public void setCpTipoNivel3(String cpTipoNivel3) {
        this.cpTipoNivel3 = cpTipoNivel3;
    }

    public String getCpTipoNivel4() {
        return cpTipoNivel4;
    }

    public void setCpTipoNivel4(String cpTipoNivel4) {
        this.cpTipoNivel4 = cpTipoNivel4;
    }

    public String getCpTipoNivel5() {
        return cpTipoNivel5;
    }

    public void setCpTipoNivel5(String cpTipoNivel5) {
        this.cpTipoNivel5 = cpTipoNivel5;
    }

    public String getCpTipoNivel6() {
        return cpTipoNivel6;
    }

    public void setCpTipoNivel6(String cpTipoNivel6) {
        this.cpTipoNivel6 = cpTipoNivel6;
    }

    public String getCpValorNivel1() {
        return cpValorNivel1;
    }

    public void setCpValorNivel1(String cpValorNivel1) {
        this.cpValorNivel1 = cpValorNivel1;
    }

    public String getCpValorNivel2() {
        return cpValorNivel2;
    }

    public void setCpValorNivel2(String cpValorNivel2) {
        this.cpValorNivel2 = cpValorNivel2;
    }

    public String getCpValorNivel3() {
        return cpValorNivel3;
    }

    public void setCpValorNivel3(String cpValorNivel3) {
        this.cpValorNivel3 = cpValorNivel3;
    }

    public String getCpValorNivel4() {
        return cpValorNivel4;
    }

    public void setCpValorNivel4(String cpValorNivel4) {
        this.cpValorNivel4 = cpValorNivel4;
    }

    public String getCpValorNivel5() {
        return cpValorNivel5;
    }

    public void setCpValorNivel5(String cpValorNivel5) {
        this.cpValorNivel5 = cpValorNivel5;
    }

    public String getCpValorNivel6() {
        return cpValorNivel6;
    }

    public void setCpValorNivel6(String cpValorNivel6) {
        this.cpValorNivel6 = cpValorNivel6;
    }

    public String getMzTipoNivel1() {
        return mzTipoNivel1;
    }

    public void setMzTipoNivel1(String mzTipoNivel1) {
        this.mzTipoNivel1 = mzTipoNivel1;
    }

    public String getMzTipoNivel2() {
        return mzTipoNivel2;
    }

    public void setMzTipoNivel2(String mzTipoNivel2) {
        this.mzTipoNivel2 = mzTipoNivel2;
    }

    public String getMzTipoNivel3() {
        return mzTipoNivel3;
    }

    public void setMzTipoNivel3(String mzTipoNivel3) {
        this.mzTipoNivel3 = mzTipoNivel3;
    }

    public String getMzTipoNivel4() {
        return mzTipoNivel4;
    }

    public void setMzTipoNivel4(String mzTipoNivel4) {
        this.mzTipoNivel4 = mzTipoNivel4;
    }

    public String getMzTipoNivel5() {
        return mzTipoNivel5;
    }

    public void setMzTipoNivel5(String mzTipoNivel5) {
        this.mzTipoNivel5 = mzTipoNivel5;
    }

    public String getMzValorNivel1() {
        return mzValorNivel1;
    }

    public void setMzValorNivel1(String mzValorNivel1) {
        this.mzValorNivel1 = mzValorNivel1;
    }

    public String getMzValorNivel2() {
        return mzValorNivel2;
    }

    public void setMzValorNivel2(String mzValorNivel2) {
        this.mzValorNivel2 = mzValorNivel2;
    }

    public String getMzValorNivel3() {
        return mzValorNivel3;
    }

    public void setMzValorNivel3(String mzValorNivel3) {
        this.mzValorNivel3 = mzValorNivel3;
    }

    public String getMzValorNivel4() {
        return mzValorNivel4;
    }

    public void setMzValorNivel4(String mzValorNivel4) {
        this.mzValorNivel4 = mzValorNivel4;
    }

    public String getMzValorNivel5() {
        return mzValorNivel5;
    }

    public void setMzValorNivel5(String mzValorNivel5) {
        this.mzValorNivel5 = mzValorNivel5;
    }

    public String getIdDirCatastro() {
        return idDirCatastro;
    }

    public void setIdDirCatastro(String idDirCatastro) {
        this.idDirCatastro = idDirCatastro;
    }

    public String getMzTipoNivel6() {
        return mzTipoNivel6;
    }

    public void setMzTipoNivel6(String mzTipoNivel6) {
        this.mzTipoNivel6 = mzTipoNivel6;
    }

    public String getMzValorNivel6() {
        return mzValorNivel6;
    }

    public void setMzValorNivel6(String mzValorNivel6) {
        this.mzValorNivel6 = mzValorNivel6;
    }

    public String getItTipoPlaca() {
        return itTipoPlaca;
    }

    public void setItTipoPlaca(String itTipoPlaca) {
        this.itTipoPlaca = itTipoPlaca;
    }

    public String getItValorPlaca() {
        return itValorPlaca;
    }

    public void setItValorPlaca(String itValorPlaca) {
        this.itValorPlaca = itValorPlaca;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getEstadoDirGeo() {
        return estadoDirGeo;
    }

    public void setEstadoDirGeo(String estadoDirGeo) {
        this.estadoDirGeo = estadoDirGeo;
    }


    public String getLetra3G() {
        return letra3G;
    }

    public void setLetra3G(String letra3G) {
        this.letra3G = letra3G;
    }
    
    public String getDirEstado() {
        return dirEstado;
    }

    public void setDirEstado(String dirEstado) {
        this.dirEstado = dirEstado;
    }

    public String getBarrioTxtBM() {
        return barrioTxtBM;
    }

    public void setBarrioTxtBM(String barrioTxtBM) {
        this.barrioTxtBM = barrioTxtBM;
    }

    @Override
    public DrDireccion clone() throws CloneNotSupportedException {
        return (DrDireccion) super.clone();
    }

  /**
   * @return the geoReferenciadaString
   */
  public String getGeoReferenciadaString() {
    return geoReferenciadaString;
  }

  /**
   * @param geoReferenciadaString the geoReferenciadaString to set
   */
  public void setGeoReferenciadaString(String geoReferenciadaString) {
    this.geoReferenciadaString = geoReferenciadaString;
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

    public String getObservacionFicha() {
        return observacionFicha;
    }

    public void setObservacionFicha(String observacionFicha) {
        this.observacionFicha = observacionFicha;
    }

    /**
     * @return the direccionHHPP
     */
    public String getDireccionHHPP() {
        return direccionHHPP;
    }

    /**
     * @param direccionHHPP the direccionHHPP to set
     */
    public void setDireccionHHPP(String direccionHHPP) {
        this.direccionHHPP = direccionHHPP;
    }    
    
    
    /**
     * @return the tipoViviendaHHPP
     */
    public String getTipoViviendaHHPP() {
        return tipoViviendaHHPP;
    }

    /**
     * @param tipoViviendaHHPP the tipoViviendaHHPP to set
     */
    public void setTipoViviendaHHPP(String tipoViviendaHHPP) {
        this.tipoViviendaHHPP = tipoViviendaHHPP;
    }

    public String getDireccionRespuestaGeo() {
        return direccionRespuestaGeo;
    }

    public void setDireccionRespuestaGeo(String direccionRespuestaGeo) {
        this.direccionRespuestaGeo = direccionRespuestaGeo;
    }

    public String getMultiOrigen() {
        return multiOrigen;
    }

    public void setMultiOrigen(String multiOrigen) {
        this.multiOrigen = multiOrigen;
    }

    public boolean isDireccionFactibilidad() {
        return direccionFactibilidad;
    }

    public void setDireccionFactibilidad(boolean direccionFactibilidad) {
        this.direccionFactibilidad = direccionFactibilidad;
    }
    
    public boolean getActualizaCasa() {
        return actualizaCasa;
    }
    
    public void setActualizaCasa(boolean actualizaCasa) {
        this.actualizaCasa = actualizaCasa;
    }
}
