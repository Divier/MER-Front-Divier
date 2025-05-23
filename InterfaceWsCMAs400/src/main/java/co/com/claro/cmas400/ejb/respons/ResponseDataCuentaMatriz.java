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
 * @author camargomf
 */
@XmlRootElement
public class ResponseDataCuentaMatriz {

    @XmlElement
    private String codigo = "";
    @XmlElement
    private String observacion1 = "";
    @XmlElement
    private String observacion2 = "";
    @XmlElement
    private String observacion3 = "";
    @XmlElement
    private String obeservacion4 = "";
    @XmlElement
    private String estado = "";
    @XmlElement
    private String nombreEdificio = "";
    @XmlElement
    private String direccion = "";
    @XmlElement
    private String barrio = "";
    @XmlElement
    private String division = "";
    @XmlElement
    private String comunidad = "";
    @XmlElement
    private String telefonoPorteria = "";
    @XmlElement
    private String ultimoTrabajo = "";
    @XmlElement
    private String fechaIngreso = "";
    @XmlElement
    private String telefonoOtro = "";
    @XmlElement
    private String fechaUltimoTrabajo = "";
    @XmlElement
    private String usuarioIngreso = "";
    @XmlElement
    private String fechaUltimaModificacion = "";
    @XmlElement
    private String usuarioUltimaModificacion = "";
    @XmlElement
    private String otrasDirecciones = "";
    @XmlElement
    private String estrato = "";
    @XmlElement
    private String otrasDirecciones2 = "";
    @XmlElement
    private String producto = "";
    @XmlElement
    private String otrasDirecciones3 = "";
    @XmlElement
    private String costo = "";
    @XmlElement
    private String otrasDirecciones4 = "";
    @XmlElement
    private String meta = "";
    @XmlElement
    private String contacto = "";
    @XmlElement
    private String cumplimiento = "";
    @XmlElement
    private String administracion = "";
    @XmlElement
    private String hogares = "";
    @XmlElement
    private String ciaAscensores = "";
    @XmlElement
    private String renta = "";
    @XmlElement
    private String headEnd = "";
    @XmlElement
    private String tipo = "";
    @XmlElement
    private String nodo = "";
    @XmlElement
    private String totalUnidadesVt = "";
    @XmlElement
    private String var = "";
    @XmlElement
    private String totalUnidadesRr = "";
    @XmlElement
    private String var2 = "";

    public ResponseDataCuentaMatriz() {
    }

    public ResponseDataCuentaMatriz(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            observacion1 = spliter[0];
            observacion2 = spliter[1];
            observacion3 = spliter[2];
            obeservacion4 = spliter[3];
            estado = spliter[4];
            nombreEdificio = spliter[5];
            direccion = spliter[6];
            barrio = spliter[7];
            division = spliter[8];
            comunidad = spliter[9];
            telefonoPorteria = spliter[10];
            ultimoTrabajo = spliter[11];
            fechaIngreso = spliter[12];
            telefonoOtro = spliter[13];
            fechaUltimoTrabajo = spliter[14];
            usuarioIngreso = spliter[15];
            fechaUltimaModificacion = spliter[16];
            usuarioUltimaModificacion = spliter[17];
            otrasDirecciones = spliter[18];
            estrato = spliter[19];
            otrasDirecciones2 = spliter[20];
            producto = spliter[21];
            otrasDirecciones3 = spliter[22];
            costo = spliter[23];
            otrasDirecciones4 = spliter[24];
            meta = spliter[25];
            contacto = spliter[26];
            cumplimiento = spliter[27];
            administracion = spliter[28];
            hogares = spliter[29];
            ciaAscensores = spliter[30];
            renta = spliter[31];
            headEnd = spliter[32];
            tipo = spliter[33];
            nodo = spliter[34];
            totalUnidadesVt = spliter[35];
            var = spliter[36];
            totalUnidadesRr = spliter[37];
            var2 = spliter[38];
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getObservacion1() {
        return observacion1;
    }

    public void setObservacion1(String observacion1) {
        this.observacion1 = observacion1;
    }

    public String getObservacion2() {
        return observacion2;
    }

    public void setObservacion2(String observacion2) {
        this.observacion2 = observacion2;
    }

    public String getObservacion3() {
        return observacion3;
    }

    public void setObservacion3(String observacion3) {
        this.observacion3 = observacion3;
    }

    public String getObeservacion4() {
        return obeservacion4;
    }

    public void setObeservacion4(String obeservacion4) {
        this.obeservacion4 = obeservacion4;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreEdificio() {
        return nombreEdificio;
    }

    public void setNombreEdificio(String nombreEdificio) {
        this.nombreEdificio = nombreEdificio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
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

    public String getUltimoTrabajo() {
        return ultimoTrabajo;
    }

    public void setUltimoTrabajo(String ultimoTrabajo) {
        this.ultimoTrabajo = ultimoTrabajo;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getTelefonoOtro() {
        return telefonoOtro;
    }

    public void setTelefonoOtro(String telefonoOtro) {
        this.telefonoOtro = telefonoOtro;
    }

    public String getFechaUltimoTrabajo() {
        return fechaUltimoTrabajo;
    }

    public void setFechaUltimoTrabajo(String fechaUltimoTrabajo) {
        this.fechaUltimoTrabajo = fechaUltimoTrabajo;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public String getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public String getUsuarioUltimaModificacion() {
        return usuarioUltimaModificacion;
    }

    public void setUsuarioUltimaModificacion(String usuarioUltimaModificacion) {
        this.usuarioUltimaModificacion = usuarioUltimaModificacion;
    }

    public String getOtrasDirecciones() {
        return otrasDirecciones;
    }

    public void setOtrasDirecciones(String otrasDirecciones) {
        this.otrasDirecciones = otrasDirecciones;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getOtrasDirecciones2() {
        return otrasDirecciones2;
    }

    public void setOtrasDirecciones2(String otrasDirecciones2) {
        this.otrasDirecciones2 = otrasDirecciones2;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getOtrasDirecciones3() {
        return otrasDirecciones3;
    }

    public void setOtrasDirecciones3(String otrasDirecciones3) {
        this.otrasDirecciones3 = otrasDirecciones3;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getOtrasDirecciones4() {
        return otrasDirecciones4;
    }

    public void setOtrasDirecciones4(String otrasDirecciones4) {
        this.otrasDirecciones4 = otrasDirecciones4;
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

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public String getHogares() {
        return hogares;
    }

    public void setHogares(String hogares) {
        this.hogares = hogares;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getTotalUnidadesVt() {
        return totalUnidadesVt;
    }

    public void setTotalUnidadesVt(String totalUnidadesVt) {
        this.totalUnidadesVt = totalUnidadesVt;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getTotalUnidadesRr() {
        return totalUnidadesRr;
    }

    public void setTotalUnidadesRr(String totalUnidadesRr) {
        this.totalUnidadesRr = totalUnidadesRr;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }
}
