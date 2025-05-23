/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity TEC_CARGUE_PREFICHAS
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */

@Entity
@Table(name = "TEC_CARGUE_PREFICHAS", schema = "MGL_SCHEME")
@XmlRootElement
public class PreFichaTxtMglNew implements Serializable, Comparator<PreFichaTxtMglNew> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "PreFichaTxt.preFichaTxtSeq",
            sequenceName = "MGL_SCHEME.TEC_PREFICHA_TXT_SQ", allocationSize = 1
    )
    @GeneratedValue(generator = "PreFichaTxt.preFichaTxtSeq")
    @Column(name = "ID_REGISTRO", nullable = false)
    private BigDecimal id;
    @Column(name = "PREFICHA_ID", nullable = false)
    private BigDecimal prfId;
    @Column(name = "HANDLE", nullable = true, length = 20)
    private String handle;
    @Column(name = "BLOCKNAME", nullable = true, length = 200)
    private String blockName;
    @Column(name = "NOMBRECALL", nullable = true, length = 200)
    private String nombreCall;
    @Column(name = "PLACAUNIDA", nullable = true, length = 20)
    private String placaUnida;
    @Column(name = "BARRIO", nullable = true, length = 200)
    private String barrio;
    @Column(name = "NOMBRE_CONJ", nullable = true, length = 200)
    private String nombreConj;
    @Column(name = "NOMBRE_ED", nullable = true, length = 200)
    private String nombreEd;
    @Column(name = "NUM_CASAS", nullable = true)
    private BigDecimal numCasas;
    @Column(name = "NUMINT", nullable = true)
    private BigDecimal numInt;
    @Column(name = "APTOS", nullable = true)
    private BigDecimal aptos;
    @Column(name = "OFICINAS", nullable = true)
    private BigDecimal oficinas;
    @Column(name = "LOCALES", nullable = true)
    private BigDecimal locales;
    @Column(name = "INT_DIRECCION", nullable = true)
    private BigDecimal interiores;
    @Column(name = "PISOS", nullable = true)
    private BigDecimal pisos;
    @Column(name = "PISOS_EDIFICIOS", nullable = true)
    private BigDecimal pi;
    @Column(name = "NOMBRECALLANT2", nullable = true, length = 200)
    private String nombreCallAnt2;
    @Column(name = "PLACAUNIDAANT2", nullable = true, length = 20)
    private String placaUnidaAnt2;
    @Column(name = "NOMBRECALLANT3", nullable = true, length = 200)
    private String nombreCallAnt3;
    @Column(name = "PLACAUNIDAANT3", nullable = true, length = 20)
    private String placaUnidaAnt3;
    @Column(name = "NO_DEFINICION", nullable = true, length = 20)
    private String no;
    @Column(name = "AMP", nullable = true, length = 20)
    private String amp;
    @Column(name = "DISTRIBUCION", nullable = true, length = 200)
    private String distribucion;
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
    @Column(name = "ID_TIPO_DIRECCION", nullable = true)
    private String tipoDireccion;
    @Column(name = "REGISTRO_VALIDO", nullable = true)
    private int registroValido;
    //Registros nuevos
    @Column(name = "CORDX", nullable = true, length = 255)
    private String cordX;
    @Column(name = "CORDY", nullable = true, length = 255)
    private String cordY;
    @Column(name = "TOTAL_HHPP", nullable = true)
    private BigDecimal totalHHPP;
    //Cambios nombre y tipo dato en columna
    @Column(name = "NODO", nullable = true)
    private String nodo;
    @Column(name = "TIPO_TECNOLOGIA", nullable = true, length = 255)
    private String tipoTecnologia;
    @Column(name = "CIUDAD", nullable = true, length = 255)
    private String ciudad;
    @Column(name = "CLASIFICACION", nullable = true, length = 255)
    private String clasificacion;
    @Column(name = "PESTANA", nullable = true, length = 255)
    private String pestana;
    @Column(name = "OBSERVACIONES", nullable = true, length = 3000)
    private String observaciones;

    public PreFichaTxtMglNew() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal pftId) {
        this.id = pftId;
    }

    public BigDecimal getPrfId() {
        return prfId;
    }

    public void setPrfId(BigDecimal prfId) {
        this.prfId = prfId;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String pftHandle) {
        this.handle = pftHandle;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String pftBlockName) {
        this.blockName = pftBlockName;
    }

    public String getNombreCall() {
        return nombreCall;
    }

    public void setNombreCall(String pftNombreCall) {
        this.nombreCall = pftNombreCall;
    }

    public String getPlacaUnida() {
        return placaUnida;
    }

    public void setPlacaUnida(String pftPlacaUnida) {
        this.placaUnida = pftPlacaUnida;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String pftBarrio) {
        this.barrio = pftBarrio;
    }

    public String getNombreConj() {
        return nombreConj;
    }

    public void setNombreConj(String pftNombreConj) {
        this.nombreConj = pftNombreConj;
    }

    public String getNombreEd() {
        return nombreEd;
    }

    public void setNombreEd(String pftNombreEd) {
        this.nombreEd = pftNombreEd;
    }

    public BigDecimal getNumCasas() {
        return numCasas;
    }

    public void setNumCasas(BigDecimal pftNumCasas) {
        this.numCasas = pftNumCasas;
    }

    public BigDecimal getNumInt() {
        return numInt;
    }

    public void setNumInt(BigDecimal pftNumInt) {
        this.numInt = pftNumInt;
    }

    public BigDecimal getAptos() {
        return aptos;
    }

    public void setAptos(BigDecimal pftAptos) {
        this.aptos = pftAptos;
    }

    public BigDecimal getOficinas() {
        return oficinas;
    }

    public void setOficinas(BigDecimal pftOficinas) {
        this.oficinas = pftOficinas;
    }

    public BigDecimal getLocales() {
        return locales;
    }

    public void setLocales(BigDecimal pftLocales) {
        this.locales = pftLocales;
    }

    public BigDecimal getInteriores() {
        return interiores;
    }

    public void setInteriores(BigDecimal pftInt) {
        this.interiores = pftInt;
    }

    public BigDecimal getPisos() {
        return pisos;
    }

    public void setPisos(BigDecimal pftPisos) {
        this.pisos = pftPisos;
    }

    public BigDecimal getPI() {
        return pi;
    }

    public void setPI(BigDecimal pftPI) {
        this.pi = pftPI;
    }

    public String getNombreCallAnt2() {
        return nombreCallAnt2;
    }

    public void setNombreCallAnt2(String pftNombreCallAnt2) {
        this.nombreCallAnt2 = pftNombreCallAnt2;
    }

    public String getPlacaUnidaAnt2() {
        return placaUnidaAnt2;
    }

    public void setPlacaUnidaAnt2(String pftPlacaUnidaAnt2) {
        this.placaUnidaAnt2 = pftPlacaUnidaAnt2;
    }

    public String getNombreCallAnt3() {
        return nombreCallAnt3;
    }

    public void setNombreCallAnt3(String pftNombreCallAnt3) {
        this.nombreCallAnt3 = pftNombreCallAnt3;
    }

    public String getPlacaUnidaAnt3() {
        return placaUnidaAnt3;
    }

    public void setPlacaUnidaAnt3(String pftPlacaUnidaAnt3) {
        this.placaUnidaAnt3 = pftPlacaUnidaAnt3;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String pftNo) {
        this.no = pftNo;
    }

    public String getAmp() {
        return amp;
    }

    public void setAmp(String pftAmp) {
        this.amp = pftAmp;
    }

    public String getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(String pftDistribucion) {
        this.distribucion = pftDistribucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getPi() {
        return pi;
    }

    public void setPi(BigDecimal pi) {
        this.pi = pi;
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

    @Override
    public int compare(PreFichaTxtMglNew o1, PreFichaTxtMglNew o2) {
        return o2.getObservaciones().compareTo(o1.getObservaciones());
    }

    public int getRegistroValido() {
        return registroValido;
    }

    public void setRegistroValido(int registroValido) {
        this.registroValido = registroValido;
    }

    public String getCordX() {
        return cordX;
    }

    public void setCordX(String cordX) {
        this.cordX = cordX;
    }

    public String getCordY() {
        return cordY;
    }

    public void setCordY(String cordY) {
        this.cordY = cordY;
    }

    public BigDecimal getTotalHHPP() {
        return totalHHPP;
    }

    public void setTotalHHPP(BigDecimal totalHHPP) {
        this.totalHHPP = totalHHPP;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getTipoTecnologia() {
        return tipoTecnologia;
    }

    public void setTipoTecnologia(String tipoTecnologia) {
        this.tipoTecnologia = tipoTecnologia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String centroPoblado) {
        this.clasificacion = centroPoblado;
    }

    public String getPestana() {
        return pestana;
    }

    public void setPestana(String codigoDaneCp) {
        this.pestana = codigoDaneCp;
    }        

    public String toFileTest() {
        return handle + "\t" + blockName + "\t" + nombreConj + "\t" + nombreCall + "\t" + placaUnida + "\t" + numCasas + "\t" + aptos + "\t" + locales + "\t" + pisos + "\t" + barrio + "\t" + interiores + "\t" + nombreCallAnt2 + "\t" + placaUnidaAnt2 + "\t" + nombreCallAnt3 + "\t" + placaUnidaAnt3 + "\t" + distribucion + "\t" + amp + "\t" + nombreEd + "\t" + numInt + "\t" + pi + "\t" + oficinas + "\t" + no + "\t" + cordX + "\t" + cordY + "\t" + tipoDireccion + "\t" + observaciones;
    }

    public String toFileString() {
        return handle + "\t" + blockName + "\t" + nombreConj + "\t" + nombreCall + "\t" + placaUnida + "\t" + numCasas + "\t" + aptos + "\t" + locales + "\t" + pisos + "\t" + barrio + "\t" + interiores + "\t" + nombreCallAnt2 + "\t" + placaUnidaAnt2 + "\t" + nombreCallAnt3 + "\t" + placaUnidaAnt3 + "\t" + distribucion + "\t" + amp + "\t" + cordX + "\t" + cordY + "\t" + observaciones;
    }
}
