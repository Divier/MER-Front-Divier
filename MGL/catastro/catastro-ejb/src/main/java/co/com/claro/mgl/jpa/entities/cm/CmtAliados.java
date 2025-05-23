/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;



/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "ALIADOS", schema = "GESTIONNEW")
@NamedQueries({
    @NamedQuery(name = "CmtAliados.findByIdAliado",
            query = "SELECT c FROM CmtAliados c WHERE c.idAliado = :idAliado ")
})
public class CmtAliados implements Serializable {

    @Id
    @Column(name = "IDALIADO")
    private BigDecimal idAliado;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "TELEFONO")
    private String telefono;
    
    @Column(name = "REPRESENTANTE_LEGAL")
    private String representanteLegal;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ACTIVO")
    private String activo;

    @Column(name = "CONTRATO_MARCO")
    private Integer contratoMarco;

    @Column(name = "FECHA_OFERTA_DESDE")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaOfertaDesde;
    
    @Column(name = "FECHA_OFERTA_HASTA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaOfertaHasta;
        
    @Column(name = "FECHA_POLIZA_DESDE")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaPolizaDesde;
            
    @Column(name = "FECHA_POLIZA_HASTA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaPolizaHasta;

    @Column(name = "ID_OFERTA")
    private int idOferta;

    @Column(name = "ID_POLIZA")
    private int idPoliza;
    
    @Column(name = "SEGMENTO")
    private String segmento;
    
    @Column(name = "CODIGO_ALIADO")
    private String codigoAliado;
    
    @Column(name = "ALIADO_SAP")
    private BigDecimal aliadoSap;
        
    @Column(name = "CODIGO_OFICINA")
    private String codigoOficina;
    
   @Column(name = "ALIADO_SAP_OPS")
    private BigDecimal aliadoSapOps;
       
    
    /**
     * Constructor de la calse
     */
    public CmtAliados() {
    }

    public BigDecimal getIdAliado() {
        return idAliado;
    }

    public void setIdAliado(BigDecimal idAliado) {
        this.idAliado = idAliado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public Integer getContratoMarco() {
        return contratoMarco;
    }

    public void setContratoMarco(Integer contratoMarco) {
        this.contratoMarco = contratoMarco;
    }

    public Date getFechaOfertaDesde() {
        return fechaOfertaDesde;
    }

    public void setFechaOfertaDesde(Date fechaOfertaDesde) {
        this.fechaOfertaDesde = fechaOfertaDesde;
    }

    public Date getFechaOfertaHasta() {
        return fechaOfertaHasta;
    }

    public void setFechaOfertaHasta(Date fechaOfertaHasta) {
        this.fechaOfertaHasta = fechaOfertaHasta;
    }

    public Date getFechaPolizaDesde() {
        return fechaPolizaDesde;
    }

    public void setFechaPolizaDesde(Date fechaPolizaDesde) {
        this.fechaPolizaDesde = fechaPolizaDesde;
    }

    public Date getFechaPolizaHasta() {
        return fechaPolizaHasta;
    }

    public void setFechaPolizaHasta(Date fechaPolizaHasta) {
        this.fechaPolizaHasta = fechaPolizaHasta;
    }

    public int getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(int idOferta) {
        this.idOferta = idOferta;
    }

    public int getIdPoliza() {
        return idPoliza;
    }

    public void setIdPoliza(int idPoliza) {
        this.idPoliza = idPoliza;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getCodigoAliado() {
        return codigoAliado;
    }

    public void setCodigoAliado(String codigoAliado) {
        this.codigoAliado = codigoAliado;
    }

    public BigDecimal getAliadoSap() {
        return aliadoSap;
    }

    public void setAliadoSap(BigDecimal aliadoSap) {
        this.aliadoSap = aliadoSap;
    }

    public String getCodigoOficina() {
        return codigoOficina;
    }

    public void setCodigoOficina(String codigoOficina) {
        this.codigoOficina = codigoOficina;
    }

    public BigDecimal getAliadoSapOps() {
        return aliadoSapOps;
    }

    public void setAliadoSapOps(BigDecimal aliadoSapOps) {
        this.aliadoSapOps = aliadoSapOps;
    }
 
}
