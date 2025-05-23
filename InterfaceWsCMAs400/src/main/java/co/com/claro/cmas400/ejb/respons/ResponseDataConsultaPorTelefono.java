/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement
public class ResponseDataConsultaPorTelefono {

    @XmlElement
    private String descripcionBlackList1 = "";
    @XmlElement
    private String descripcionBlackList4 = "";
    @XmlElement
    private String descripcionBlackList2 = "";
    @XmlElement
    private String descripcionBlackList3 = "";
    @XmlElement
    private String descripcionEstadoEdificio = "";
    @XmlElement
    private String nombreEdificio = "";
    @XmlElement
    private String calle = "";
    @XmlElement
    private String nombreBarrio = "";
    @XmlElement
    private String division = "";
    @XmlElement
    private String comunidad = "";
    @XmlElement
    private String telefonoPorteria = "";
    @XmlElement
    private String ultimaOT = "";
    @XmlElement
    private String fechaIngreso = "";
    @XmlElement
    private String telefonoPorteria2 = "";
    @XmlElement
    private String fechaUltimaOT = "";
    @XmlElement
    private String usuarioIngresaEdificio = "";
    @XmlElement
    private String fechaUltimoCambio = "";
    @XmlElement
    private String usuarioUltimoCambio = "";
    @XmlElement
    private String direccionAlterna1 = "";
    @XmlElement
    private String estrato = "";
    @XmlElement
    private String direccionAlterna2 = "";
    @XmlElement
    private String productos = "";
    @XmlElement
    private String direccionAlterna3 = "";
    @XmlElement
    private String costo = "";
    @XmlElement
    private String direccionAlterna4 = "";
    @XmlElement
    private String meta = "";
    @XmlElement
    private String contacto = "";
    @XmlElement
    private String cumplimiento = "";
    @XmlElement
    private String ciaAdministradora = "";
    @XmlElement
    private String cantidadHogares = "";
    @XmlElement
    private String ciaAscensores = "";
    @XmlElement
    private String renta = "";
    @XmlElement
    private String headEnd = "";
    @XmlElement
    private String tipoHeadEnd = "";
    @XmlElement
    private String nodo = "";
    @XmlElement
    private String totalUnidadesVT = "";
    @XmlElement
    private String numeroTrabajoCreado = "";
    @XmlElement
    private String totalUnidadesRR = "";
    @XmlElement
    private String numeroRegistros = "";

    public ResponseDataConsultaPorTelefono() {
    }

    public ResponseDataConsultaPorTelefono(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            descripcionBlackList1 = spliter[0];
            descripcionBlackList4 = spliter[1];
            descripcionBlackList2 = spliter[2];
            descripcionBlackList3 = spliter[3];
            descripcionEstadoEdificio = spliter[4];
            nombreEdificio = spliter[5];
            calle = spliter[6];
            nombreBarrio = spliter[7];
            division = spliter[8];
            comunidad = spliter[9];
            telefonoPorteria = spliter[10];
            ultimaOT = spliter[11];
            fechaIngreso = spliter[12];
            telefonoPorteria2 = spliter[13];
            fechaUltimaOT = spliter[14];
            usuarioIngresaEdificio = spliter[15];
            fechaUltimoCambio = spliter[16];
            usuarioUltimoCambio = spliter[17];
            direccionAlterna1 = spliter[18];
            estrato = spliter[19];
            direccionAlterna2 = spliter[20];
            productos = spliter[21];
            direccionAlterna3 = spliter[22];
            costo = spliter[23];
            direccionAlterna4 = spliter[24];
            meta = spliter[25];
            contacto = spliter[26];
            cumplimiento = spliter[27];
            ciaAdministradora = spliter[28];
            cantidadHogares = spliter[29];
            ciaAscensores = spliter[30];
            renta = spliter[31];
            headEnd = spliter[32];
            tipoHeadEnd = spliter[33];
            nodo = spliter[34];
            totalUnidadesVT = spliter[35];
            numeroTrabajoCreado = spliter[36];
            totalUnidadesRR = spliter[37];
            numeroRegistros = spliter[38];
        }
    }

    public String getDescripcionBlackList1() {
        return descripcionBlackList1;
    }

    public void setDescripcionBlackList1(String descripcionBlackList1) {
        this.descripcionBlackList1 = descripcionBlackList1;
    }

    public String getDescripcionBlackList4() {
        return descripcionBlackList4;
    }

    public void setDescripcionBlackList4(String descripcionBlackList4) {
        this.descripcionBlackList4 = descripcionBlackList4;
    }

    public String getDescripcionBlackList2() {
        return descripcionBlackList2;
    }

    public void setDescripcionBlackList2(String descripcionBlackList2) {
        this.descripcionBlackList2 = descripcionBlackList2;
    }

    public String getDescripcionBlackList3() {
        return descripcionBlackList3;
    }

    public void setDescripcionBlackList3(String descripcionBlackList3) {
        this.descripcionBlackList3 = descripcionBlackList3;
    }

    public String getDescripcionEstadoEdificio() {
        return descripcionEstadoEdificio;
    }

    public void setDescripcionEstadoEdificio(String descripcionEstadoEdificio) {
        this.descripcionEstadoEdificio = descripcionEstadoEdificio;
    }

    public String getNombreEdificio() {
        return nombreEdificio;
    }

    public void setNombreEdificio(String nombreEdificio) {
        this.nombreEdificio = nombreEdificio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNombreBarrio() {
        return nombreBarrio;
    }

    public void setNombreBarrio(String nombreBarrio) {
        this.nombreBarrio = nombreBarrio;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getTelefonoPorteria() {
        return telefonoPorteria;
    }

    public void setTelefonoPorteria(String telefonoPorteria) {
        this.telefonoPorteria = telefonoPorteria;
    }

    public String getUltimaOT() {
        return ultimaOT;
    }

    public void setUltimaOT(String ultimaOT) {
        this.ultimaOT = ultimaOT;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getTelefonoPorteria2() {
        return telefonoPorteria2;
    }

    public void setTelefonoPorteria2(String telefonoPorteria2) {
        this.telefonoPorteria2 = telefonoPorteria2;
    }

    public String getFechaUltimaOT() {
        return fechaUltimaOT;
    }

    public void setFechaUltimaOT(String fechaUltimaOT) {
        this.fechaUltimaOT = fechaUltimaOT;
    }

    public String getUsuarioIngresaEdificio() {
        return usuarioIngresaEdificio;
    }

    public void setUsuarioIngresaEdificio(String usuarioIngresaEdificio) {
        this.usuarioIngresaEdificio = usuarioIngresaEdificio;
    }

    public String getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(String fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }

    public String getUsuarioUltimoCambio() {
        return usuarioUltimoCambio;
    }

    public void setUsuarioUltimoCambio(String usuarioUltimoCambio) {
        this.usuarioUltimoCambio = usuarioUltimoCambio;
    }

    public String getDireccionAlterna1() {
        return direccionAlterna1;
    }

    public void setDireccionAlterna1(String direccionAlterna1) {
        this.direccionAlterna1 = direccionAlterna1;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getDireccionAlterna2() {
        return direccionAlterna2;
    }

    public void setDireccionAlterna2(String direccionAlterna2) {
        this.direccionAlterna2 = direccionAlterna2;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getDireccionAlterna3() {
        return direccionAlterna3;
    }

    public void setDireccionAlterna3(String direccionAlterna3) {
        this.direccionAlterna3 = direccionAlterna3;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getDireccionAlterna4() {
        return direccionAlterna4;
    }

    public void setDireccionAlterna4(String direccionAlterna4) {
        this.direccionAlterna4 = direccionAlterna4;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCumplimiento() {
        return cumplimiento;
    }

    public void setCumplimiento(String cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    public String getCiaAdministradora() {
        return ciaAdministradora;
    }

    public void setCiaAdministradora(String ciaAdministradora) {
        this.ciaAdministradora = ciaAdministradora;
    }

    public String getCantidadHogares() {
        return cantidadHogares;
    }

    public void setCantidadHogares(String cantidadHogares) {
        this.cantidadHogares = cantidadHogares;
    }

    public String getCiaAscensores() {
        return ciaAscensores;
    }

    public void setCiaAscensores(String ciaAscensores) {
        this.ciaAscensores = ciaAscensores;
    }

    public String getRenta() {
        return renta;
    }

    public void setRenta(String renta) {
        this.renta = renta;
    }

    public String getHeadEnd() {
        return headEnd;
    }

    public void setHeadEnd(String headEnd) {
        this.headEnd = headEnd;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getTotalUnidadesVT() {
        return totalUnidadesVT;
    }

    public void setTotalUnidadesVT(String totalUnidadesVT) {
        this.totalUnidadesVT = totalUnidadesVT;
    }

    public String getNumeroTrabajoCreado() {
        return numeroTrabajoCreado;
    }

    public void setNumeroTrabajoCreado(String numeroTrabajoCreado) {
        this.numeroTrabajoCreado = numeroTrabajoCreado;
    }

    public String getTotalUnidadesRR() {
        return totalUnidadesRR;
    }

    public void setTotalUnidadesRR(String totalUnidadesRR) {
        this.totalUnidadesRR = totalUnidadesRR;
    }

    public String getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(String numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public String getTipoHeadEnd() {
        return tipoHeadEnd;
    }

    public void setTipoHeadEnd(String tipoHeadEnd) {
        this.tipoHeadEnd = tipoHeadEnd;
    }

}
