/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@Table(name = "MGL_ONYX", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OnyxOtCmDir.findAll", query = "SELECT s FROM OnyxOtCmDir s"),
    @NamedQuery(name = "OnyxOtCmDir.findAllByNumeroOtHija", query = "SELECT s FROM OnyxOtCmDir s where s.Onyx_Ot_Hija_Cm=:Onyx_Ot_Hija_Cm")
    })
public class OnyxOtCmDir implements Serializable{

    @Id
    @Basic(optional = false)
      @SequenceGenerator(
            name = "OnyxOtCmDir.OnyxOtCmDirSq",
            sequenceName = "MGL_SCHEME.MGL_ONYX_SQ", allocationSize = 1)
    @GeneratedValue(generator = "OnyxOtCmDir.OnyxOtCmDirSq")
    @Column(name = "ID_ONYX",nullable = false)
    private BigDecimal id_Onyx;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID_CM", referencedColumnName = "OT_ID")
    private CmtOrdenTrabajoMgl ot_Id_Cm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_DIRECCION_ID", referencedColumnName = "OT_DIRECCION_ID")
    private OtHhppMgl ot_Direccion_Id;
    @Column(name = "ONYX_OT_HIJA_CM")
    private String Onyx_Ot_Hija_Cm;
    @Column(name = "ONYX_OT_HIJA_DIR")
    private String Onyx_Ot_Hija_Dir;
    @Column(name = "ONYX_OT_PADRE_CM")
    private String Onyx_Ot_Padre_Cm;
    @Column(name = "ONYX_OT_PADRE_DIR")
    private String Onyx_Ot_Padre_Dir; 
    @Column(name = "NIT_CLIENTE_ONYX")
    private String nit_Cliente_Onyx;
    @Column(name = "NOMBRE_CLIENTE_ONYX")
    private String nombre_Cliente_Onyx;
    @Column(name = "NOMBRE_OT_HIJA_ONYX")
    private String nombre_Ot_Hija_Onyx;
    @Column(name = "FECHA_CREACION_OT_HIJA_ONYX")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha_Creacion_Ot_Hija_Onyx;
    @Column(name = "FECHA_CREACION_OT_PADRE_ONYX")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha_Creacion_Ot_Padre_Onyx;
    @Column(name = "CONTACTO_TECNICO_OT_PADRE_ONYX")
    private String contacto_Tecnico_Ot_Padre_Onyx;
    @Column(name = "TELEFONO_TECNICO_OT_PADRE_ONYX")
    private String telefono_Tecnico_Ot_Padre_Onyx;
    @Column(name = "DESCRIPCION_ONYX")
    private String descripcion_Onyx;
    @Column(name = "DIRECCION_ONYX")
    private String direccion_Onyx;
    @Column(name = "SEGMENTO_ONYX")
    private String segmento_Onyx;
    @Column(name = "TIPO_SERVICIO_ONYX")
    private String tipo_Servicio_Onyx;
     @Column(name = "SERVICIOS_ONYX")
    private String servicios_Onyx;
    @Column(name = "RECURRENTE_MENSUAL_ONYX")
    private String recurrente_Mensual_Onyx;
    @Column(name = "CODIGO_SERVICIO_ONYX")
    private String codigo_Servicio_Onyx;
    @Column(name = "VENDEDOR_ONYX")
    private String vendedor_Onyx;
    @Column(name = "TELEFONO_VENDEDOR_ONYX")
    private String telefono_Vendedor_Onyx;
    @Column(name = "ESTADO_OT_HIJA_ONYX_CM")
    private String estado_Ot_Hija_Onyx_Cm;
    @Column(name = "ESTADO_OT_PADRE_ONYX_CM")
    private String estado_Ot_Padre_Onyx_Cm;
     @Column(name = "ESTADO_OT_HIJA_ONYX_DIR")
    private String estado_Ot_Hija_Onyx_Dir;
    @Column(name = "ESTADO_OT_PADRE_ONYX_DIR")
    private String estado_Ot_Padre_Onyx_Dir;
    @Column(name = "FECHA_COMPROMISO_OT_PADRE_ONYX")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha_Compromiso_Ot_Padre_Onyx;
    @Column(name = "OT_PADRE_RESOLUCION_1_ONYX")
    private String ot_Padre_Resolucion_1_Onyx;
    @Column(name = "OT_PADRE_RESOLUCION_2_ONYX")
    private String ot_Padre_Resolucion_2_Onyx;
    @Column(name = "OT_PADRE_RESOLUCION_3_ONYX")
    private String ot_Padre_Resolucion_3_Onyx;
    @Column(name = "OT_PADRE_RESOLUCION_4_ONYX")
    private String ot_Padre_Resolucion_4_Onyx;
    @Column(name = "OT_HIJA_RESOLUCION_1_ONYX")
    private String ot_Hija_Resolucion_1_Onyx;
    @Column(name = "OT_HIJA_RESOLUCION_2_ONYX")
    private String ot_Hija_Resolucion_2_Onyx;
    @Column(name = "OT_HIJA_RESOLUCION_3_ONYX")
    private String ot_Hija_Resolucion_3_Onyx;
    @Column(name = "OT_HIJA_RESOLUCION_4_ONYX")
    private String ot_Hija_Resolucion_4_Onyx;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEdicion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "CELLTEC")
    private String cellTec;
    @Column(name = "EMAILTEC")
    private String emailTec;
    @Column(name = "CODIGOPROYECTO")
    private String codigoProyecto;
    @Column(name = "REGIONALORIGEN")
    private String regionalOrigen;
    @Column(name = "AIMPLEMENT")
    private String aImplement;
    @Column(name = "DIRECCIONFACT")
    private String direccionFact;
    @Column(name = "CIUDADFACT")
    private String ciudadFact;
    @OneToMany(mappedBy = "id_OnyxCmDirObj", fetch = FetchType.LAZY)
    private List<OnyxOtCmDirAuditoria> listAuditoria;
    
    @Column(name = "COMPLEJIDAD_SERVICIO")
    private String complejidadServicio;
    
    
   
    
    public OnyxOtCmDir() {
    }

    public BigDecimal getId_Onyx() {
        return id_Onyx;
    }

    public void setId_Onyx(BigDecimal id_Onyx) {
        this.id_Onyx = id_Onyx;
    }

  

    public CmtOrdenTrabajoMgl getOt_Id_Cm() {
        return ot_Id_Cm;
    }

    public void setOt_Id_Cm(CmtOrdenTrabajoMgl ot_Id_Cm) {
        this.ot_Id_Cm = ot_Id_Cm;
    }

    public OtHhppMgl getOt_Direccion_Id() {
        return ot_Direccion_Id;
    }

    public void setOt_Direccion_Id(OtHhppMgl ot_Direccion_Id) {
        this.ot_Direccion_Id = ot_Direccion_Id;
    }

    public String getOnyx_Ot_Hija_Cm() {
        return Onyx_Ot_Hija_Cm;
    }

    public void setOnyx_Ot_Hija_Cm(String Onyx_Ot_Hija_Cm) {
        this.Onyx_Ot_Hija_Cm = Onyx_Ot_Hija_Cm;
    }

    public String getOnyx_Ot_Hija_Dir() {
        return Onyx_Ot_Hija_Dir;
    }

    public void setOnyx_Ot_Hija_Dir(String Onyx_Ot_Hija_Dir) {
        this.Onyx_Ot_Hija_Dir = Onyx_Ot_Hija_Dir;
    }

    public String getOnyx_Ot_Padre_Cm() {
        return Onyx_Ot_Padre_Cm;
    }

    public void setOnyx_Ot_Padre_Cm(String Onyx_Ot_Padre_Cm) {
        this.Onyx_Ot_Padre_Cm = Onyx_Ot_Padre_Cm;
    }

    public String getOnyx_Ot_Padre_Dir() {
        return Onyx_Ot_Padre_Dir;
    }

    public void setOnyx_Ot_Padre_Dir(String Onyx_Ot_Padre_Dir) {
        this.Onyx_Ot_Padre_Dir = Onyx_Ot_Padre_Dir;
    }

    public String getNit_Cliente_Onyx() {
        return nit_Cliente_Onyx;
    }

    public void setNit_Cliente_Onyx(String nit_Cliente_Onyx) {
        this.nit_Cliente_Onyx = nit_Cliente_Onyx;
    }

    public String getNombre_Cliente_Onyx() {
        return nombre_Cliente_Onyx;
    }

    public void setNombre_Cliente_Onyx(String nombre_Cliente_Onyx) {
        this.nombre_Cliente_Onyx = nombre_Cliente_Onyx;
    }

    public String getNombre_Ot_Hija_Onyx() {
        return nombre_Ot_Hija_Onyx;
    }

    public void setNombre_Ot_Hija_Onyx(String nombre_Ot_Hija_Onyx) {
        this.nombre_Ot_Hija_Onyx = nombre_Ot_Hija_Onyx;
    }

    public Date getFecha_Creacion_Ot_Hija_Onyx() {
        return fecha_Creacion_Ot_Hija_Onyx;
    }

    public void setFecha_Creacion_Ot_Hija_Onyx(Date fecha_Creacion_Ot_Hija_Onyx) {
        this.fecha_Creacion_Ot_Hija_Onyx = fecha_Creacion_Ot_Hija_Onyx;
    }

    public Date getFecha_Creacion_Ot_Padre_Onyx() {
        return fecha_Creacion_Ot_Padre_Onyx;
    }

    public void setFecha_Creacion_Ot_Padre_Onyx(Date fecha_Creacion_Ot_Padre_Onyx) {
        this.fecha_Creacion_Ot_Padre_Onyx = fecha_Creacion_Ot_Padre_Onyx;
    }

    public String getContacto_Tecnico_Ot_Padre_Onyx() {
        return contacto_Tecnico_Ot_Padre_Onyx;
    }

    public void setContacto_Tecnico_Ot_Padre_Onyx(String contacto_Tecnico_Ot_Padre_Onyx) {
        this.contacto_Tecnico_Ot_Padre_Onyx = contacto_Tecnico_Ot_Padre_Onyx;
    }

    public String getTelefono_Tecnico_Ot_Padre_Onyx() {
        return telefono_Tecnico_Ot_Padre_Onyx;
    }

    public void setTelefono_Tecnico_Ot_Padre_Onyx(String telefono_Tecnico_Ot_Padre_Onyx) {
        this.telefono_Tecnico_Ot_Padre_Onyx = telefono_Tecnico_Ot_Padre_Onyx;
    }

    public String getDescripcion_Onyx() {
        return descripcion_Onyx;
    }

    public void setDescripcion_Onyx(String descripcion_Onyx) {
        this.descripcion_Onyx = descripcion_Onyx;
    }

    public String getDireccion_Onyx() {
        return direccion_Onyx;
    }

    public void setDireccion_Onyx(String direccion_Onyx) {
        this.direccion_Onyx = direccion_Onyx;
    }

    public String getSegmento_Onyx() {
        return segmento_Onyx;
    }

    public void setSegmento_Onyx(String segmento_Onyx) {
        this.segmento_Onyx = segmento_Onyx;
    }

    public String getTipo_Servicio_Onyx() {
        return tipo_Servicio_Onyx;
    }

    public void setTipo_Servicio_Onyx(String tipo_Servicio_Onyx) {
        this.tipo_Servicio_Onyx = tipo_Servicio_Onyx;
    }

    public String getServicios_Onyx() {
        return servicios_Onyx;
    }

    public void setServicios_Onyx(String servicios_Onyx) {
        this.servicios_Onyx = servicios_Onyx;
    }

    public String getRecurrente_Mensual_Onyx() {
        return recurrente_Mensual_Onyx;
    }

    public void setRecurrente_Mensual_Onyx(String recurrente_Mensual_Onyx) {
        this.recurrente_Mensual_Onyx = recurrente_Mensual_Onyx;
    }

    public String getCodigo_Servicio_Onyx() {
        return codigo_Servicio_Onyx;
    }

    public void setCodigo_Servicio_Onyx(String codigo_Servicio_Onyx) {
        this.codigo_Servicio_Onyx = codigo_Servicio_Onyx;
    }

    public String getVendedor_Onyx() {
        return vendedor_Onyx;
    }

    public void setVendedor_Onyx(String vendedor_Onyx) {
        this.vendedor_Onyx = vendedor_Onyx;
    }

    public String getTelefono_Vendedor_Onyx() {
        return telefono_Vendedor_Onyx;
    }

    public void setTelefono_Vendedor_Onyx(String telefono_Vendedor_Onyx) {
        this.telefono_Vendedor_Onyx = telefono_Vendedor_Onyx;
    }

    public String getEstado_Ot_Hija_Onyx_Cm() {
        return estado_Ot_Hija_Onyx_Cm;
    }

    public void setEstado_Ot_Hija_Onyx_Cm(String estado_Ot_Hija_Onyx_Cm) {
        this.estado_Ot_Hija_Onyx_Cm = estado_Ot_Hija_Onyx_Cm;
    }

    public String getEstado_Ot_Padre_Onyx_Cm() {
        return estado_Ot_Padre_Onyx_Cm;
    }

    public void setEstado_Ot_Padre_Onyx_Cm(String estado_Ot_Padre_Onyx_Cm) {
        this.estado_Ot_Padre_Onyx_Cm = estado_Ot_Padre_Onyx_Cm;
    }

    public String getEstado_Ot_Hija_Onyx_Dir() {
        return estado_Ot_Hija_Onyx_Dir;
    }

    public void setEstado_Ot_Hija_Onyx_Dir(String estado_Ot_Hija_Onyx_Dir) {
        this.estado_Ot_Hija_Onyx_Dir = estado_Ot_Hija_Onyx_Dir;
    }

    public String getEstado_Ot_Padre_Onyx_Dir() {
        return estado_Ot_Padre_Onyx_Dir;
    }

    public void setEstado_Ot_Padre_Onyx_Dir(String estado_Ot_Padre_Onyx_Dir) {
        this.estado_Ot_Padre_Onyx_Dir = estado_Ot_Padre_Onyx_Dir;
    }

    public Date getFecha_Compromiso_Ot_Padre_Onyx() {
        return fecha_Compromiso_Ot_Padre_Onyx;
    }

    public void setFecha_Compromiso_Ot_Padre_Onyx(Date fecha_Compromiso_Ot_Padre_Onyx) {
        this.fecha_Compromiso_Ot_Padre_Onyx = fecha_Compromiso_Ot_Padre_Onyx;
    }

    public String getOt_Padre_Resolucion_1_Onyx() {
        return ot_Padre_Resolucion_1_Onyx;
    }

    public void setOt_Padre_Resolucion_1_Onyx(String ot_Padre_Resolucion_1_Onyx) {
        this.ot_Padre_Resolucion_1_Onyx = ot_Padre_Resolucion_1_Onyx;
    }

    public String getOt_Padre_Resolucion_2_Onyx() {
        return ot_Padre_Resolucion_2_Onyx;
    }

    public void setOt_Padre_Resolucion_2_Onyx(String ot_Padre_Resolucion_2_Onyx) {
        this.ot_Padre_Resolucion_2_Onyx = ot_Padre_Resolucion_2_Onyx;
    }

    public String getOt_Padre_Resolucion_3_Onyx() {
        return ot_Padre_Resolucion_3_Onyx;
    }

    public void setOt_Padre_Resolucion_3_Onyx(String ot_Padre_Resolucion_3_Onyx) {
        this.ot_Padre_Resolucion_3_Onyx = ot_Padre_Resolucion_3_Onyx;
    }

    public String getOt_Padre_Resolucion_4_Onyx() {
        return ot_Padre_Resolucion_4_Onyx;
    }

    public void setOt_Padre_Resolucion_4_Onyx(String ot_Padre_Resolucion_4_Onyx) {
        this.ot_Padre_Resolucion_4_Onyx = ot_Padre_Resolucion_4_Onyx;
    }

    public String getOt_Hija_Resolucion_1_Onyx() {
        return ot_Hija_Resolucion_1_Onyx;
    }

    public void setOt_Hija_Resolucion_1_Onyx(String ot_Hija_Resolucion_1_Onyx) {
        this.ot_Hija_Resolucion_1_Onyx = ot_Hija_Resolucion_1_Onyx;
    }

    public String getOt_Hija_Resolucion_2_Onyx() {
        return ot_Hija_Resolucion_2_Onyx;
    }

    public void setOt_Hija_Resolucion_2_Onyx(String ot_Hija_Resolucion_2_Onyx) {
        this.ot_Hija_Resolucion_2_Onyx = ot_Hija_Resolucion_2_Onyx;
    }

    public String getOt_Hija_Resolucion_3_Onyx() {
        return ot_Hija_Resolucion_3_Onyx;
    }

    public void setOt_Hija_Resolucion_3_Onyx(String ot_Hija_Resolucion_3_Onyx) {
        this.ot_Hija_Resolucion_3_Onyx = ot_Hija_Resolucion_3_Onyx;
    }

    public String getOt_Hija_Resolucion_4_Onyx() {
        return ot_Hija_Resolucion_4_Onyx;
    }

    public void setOt_Hija_Resolucion_4_Onyx(String ot_Hija_Resolucion_4_Onyx) {
        this.ot_Hija_Resolucion_4_Onyx = ot_Hija_Resolucion_4_Onyx;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public List<OnyxOtCmDirAuditoria> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<OnyxOtCmDirAuditoria> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public String getCellTec() {
        return cellTec;
    }

    public void setCellTec(String cellTec) {
        this.cellTec = cellTec;
    }

    public String getEmailTec() {
        return emailTec;
    }

    public void setEmailTec(String emailTec) {
        this.emailTec = emailTec;
    }

    public String getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(String codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

    public String getRegionalOrigen() {
        return regionalOrigen;
    }

    public void setRegionalOrigen(String regionalOrigen) {
        this.regionalOrigen = regionalOrigen;
    }

    public String getaImplement() {
        return aImplement;
    }

    public void setaImplement(String aImplement) {
        this.aImplement = aImplement;
    }

    public String getDireccionFact() {
        return direccionFact;
    }

    public void setDireccionFact(String direccionFact) {
        this.direccionFact = direccionFact;
    }

    public String getCiudadFact() {
        return ciudadFact;
    }

    public void setCiudadFact(String ciudadFact) {
        this.ciudadFact = ciudadFact;
    }

    public String getComplejidadServicio() {
        return complejidadServicio;
    }

    public void setComplejidadServicio(String complejidadServicio) {
        this.complejidadServicio = complejidadServicio;
    }

   }
