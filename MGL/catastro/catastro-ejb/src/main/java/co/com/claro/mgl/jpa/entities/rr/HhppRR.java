/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.rr;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "UNITMSTR", schema = "CABLEDTA")
public class HhppRR implements Serializable { 
    

    @Id
    @Column(name = "UNAKYÑ")
    private BigDecimal idUnitMstr;
    @Column(name = "UN1SYC")
    private BigDecimal  fechaAudSiglo;
    @Column(name = "UN1SYY")
    private BigDecimal  fechaAudAño;
    @Column(name = "UN1SYM")
    private BigDecimal  fechaAudMes;
    @Column(name = "UN1SYD")
    private BigDecimal  fechaAudDia;
    @Column(name = "UNCSRC")
    private BigDecimal  fechaAudSiglo2;
    @Column(name = "UNCSRY")
    private BigDecimal  fechaAudAño2;
    @Column(name = "UNCSRM")
    private BigDecimal  fechaAudMes2;
    @Column(name = "UNCSRD")
    private BigDecimal  fechaAudDia2;
    @Column(name = "UNCCDE")
    private String  comunidad;
    @Column(name = "UNDCDE")
    private String  division;
    @Column(name = "UNDLRC")
    private String  vendedor;
    @Column(name = "UNDROP")
    private String  tipoAcometida;
    @Column(name = "UNDRPC")
    private String  tipoCable;
    @Column(name = "UNHEND")
    private String  headEnd;
    @Column(name = "UNLTYP")
    private String  tipoHhpp;
    @Column(name = "UNPOST")
    private String  codigoPostal;
    @Column(name = "UNNODE")
    private String  nodo;
    @Column(name = "UNLOC")
    private String  ultimaUbicacion;
    @Column(name = "UNSTRA")
    private String  estrato;

    
    public BigDecimal getIdUnitMstr() {
        return idUnitMstr;
    }

    public void setIdUnitMstr(BigDecimal idUnitMstr) {
        this.idUnitMstr = idUnitMstr;
    }

    public BigDecimal getFechaAudSiglo() {
        return fechaAudSiglo;
    }

    public void setFechaAudSiglo(BigDecimal fechaAudSiglo) {
        this.fechaAudSiglo = fechaAudSiglo;
    }

    public BigDecimal getFechaAudAño() {
        return fechaAudAño;
    }

    public void setFechaAudAño(BigDecimal fechaAudAño) {
        this.fechaAudAño = fechaAudAño;
    }

    public BigDecimal getFechaAudMes() {
        return fechaAudMes;
    }

    public void setFechaAudMes(BigDecimal fechaAudMes) {
        this.fechaAudMes = fechaAudMes;
    }

    public BigDecimal getFechaAudDia() {
        return fechaAudDia;
    }

    public void setFechaAudDia(BigDecimal fechaAudDia) {
        this.fechaAudDia = fechaAudDia;
    }

    public BigDecimal getFechaAudSiglo2() {
        return fechaAudSiglo2;
    }

    public void setFechaAudSiglo2(BigDecimal fechaAudSiglo2) {
        this.fechaAudSiglo2 = fechaAudSiglo2;
    }

    public BigDecimal getFechaAudAño2() {
        return fechaAudAño2;
    }

    public void setFechaAudAño2(BigDecimal fechaAudAño2) {
        this.fechaAudAño2 = fechaAudAño2;
    }

    public BigDecimal getFechaAudMes2() {
        return fechaAudMes2;
    }

    public void setFechaAudMes2(BigDecimal fechaAudMes2) {
        this.fechaAudMes2 = fechaAudMes2;
    }

    public BigDecimal getFechaAudDia2() {
        return fechaAudDia2;
    }

    public void setFechaAudDia2(BigDecimal fechaAudDia2) {
        this.fechaAudDia2 = fechaAudDia2;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getTipoAcometida() {
        return tipoAcometida;
    }

    public void setTipoAcometida(String tipoAcometida) {
        this.tipoAcometida = tipoAcometida;
    }

    public String getTipoCable() {
        return tipoCable;
    }

    public void setTipoCable(String tipoCable) {
        this.tipoCable = tipoCable;
    }

    public String getHeadEnd() {
        return headEnd;
    }

    public void setHeadEnd(String headEnd) {
        this.headEnd = headEnd;
    }

    public String getTipoHhpp() {
        return tipoHhpp;
    }

    public void setTipoHhpp(String tipoHhpp) {
        this.tipoHhpp = tipoHhpp;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getUltimaUbicacion() {
        return ultimaUbicacion;
    }

    public void setUltimaUbicacion(String ultimaUbicacion) {
        this.ultimaUbicacion = ultimaUbicacion;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }
    
}
