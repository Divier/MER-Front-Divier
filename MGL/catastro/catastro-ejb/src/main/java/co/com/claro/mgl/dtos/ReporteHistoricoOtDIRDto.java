/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;

/**
 *
 * @author cardenaslb
 */
public class ReporteHistoricoOtDIRDto {
   
    private BigDecimal id_OnyxCmDir;
    private String ot_Id_Cm;
    private String ot_Direccion_Id;
    private String Onyx_Ot_Hija_Cm;
    private String Onyx_Ot_Hija_Cm_Aud;
    private String Onyx_Ot_Hija_Dir;
    private String Onyx_Ot_Hija_Dir_Aud;
    private String Onyx_Ot_Padre_Cm;
    private String Onyx_Ot_Padre_Cm_Aud;
    private String Onyx_Ot_Padre_Dir;
    private String Onyx_Ot_Padre_Dir_Aud;
    private String nit_Cliente_Onyx;
    private String nit_Cliente_Onyx_Aud;
    private String nombre_Cliente_Onyx;
    private String nombre_Cliente_Onyx_Aud;
    private String nombre_Ot_Hija_Onyx;
    private String nombre_OT_Hija_Onyx_Aud;
    private String fecha_Creacion_Ot_Hija_Onyx;
    private String fecha_Creación_OT_Hija_Onyx_Aud;
    private String fecha_Creacion_Ot_Padre_Onyx;
    private String fecha_Creacion_OT_Padre_Onyx_Aud;
    private String contacto_Tecnico_Ot_Padre_Onyx;
    private String contacto_Tecnico_Ot_Padre_Onyx_Aud;
    private String telefono_Tecnico_Ot_Padre_Onyx;
    private String telefono_Tecnico_Ot_Padre_Onyx_Aud;
    private String descripcion_Onyx;
    private String descripcion_Onyx_Aud;
    private String direccion_Onyx;
    private String direccion_Onyx_Aud;
    private String segmento_Onyx;
    private String segmento_Onyx_Aud;
    private String tipo_Servicio_Onyx;
    private String tipo_Servicio_Onyx_Aud;
    private String servicios_Onyx;
    private String servicios_Onyx_Aud;
    private String recurrente_Mensual_Onyx;
    private String recurrente_Mensual_Onyx_Aud;
    private String codigo_Servicio_Onyx;
    private String codigo_Servicio_Onyx_Aud;
    private String vendedor_Onyx;
    private String vendedor_Onyx_Aud;
    private String telefono_Vendedor_Onyx;
    private String telefono_Vendedor_Onyx_Aud;
    private String estado_Ot_Hija_Onyx_Cm;
    private String estado_Ot_Hija_Onyx_Cm_Aud;
    private String estado_Ot_Padre_Onyx_Cm;
    private String estado_Ot_Padre_Onyx_Cm_Aud;
    private String estado_Ot_Hija_Onyx_Dir;
    private String estado_Ot_Hija_Onyx_Dir_Aud;
    private String estado_Ot_Padre_Onyx_Dir;
    private String estado_Ot_Padre_Onyx_Dir_Aud;
    private String fecha_Compromiso_Ot_Padre_Onyx;
    private String fecha_Compromiso_Ot_Padre_Onyx_Aud;
    private String ot_Padre_Resolucion_1_Onyx;
    private String ot_Padre_Resolucion_1_Onyx_Aud;
    private String ot_Padre_Resolucion_2_Onyx;
    private String ot_Padre_Resolucion_2_Onyx_Aud;
    private String ot_Padre_Resolucion_3_Onyx;
    private String ot_Padre_Resolucion_3_Onyx_Aud;
    private String ot_Padre_Resolucion_4_Onyx;
    private String ot_Padre_Resolucion_4_Onyx_Aud;
    private String ot_Hija_Resolucion_1_Onyx;
    private String ot_Hija_Resolucion_1_Onyx_Aud;
    private String ot_Hija_Resolucion_2_Onyx;
    private String ot_Hija_Resolucion_2_Onyx_Aud;
    private String ot_Hija_Resolucion_3_Onyx;
    private String ot_Hija_Resolucion_3_Onyx_Aud;
    private String ot_Hija_Resolucion_4_Onyx;
    private String ot_Hija_Resolucion_4_Onyx_Aud;
    private String tipo_OT_MER;
    private String sub_tipo_OT_MER;
    private String sub_tipo_OT_MER_Aud;
    private String estado_interno_OT_MER;
    private String estado_interno_OT_MER_Aud;
    private String fecha_creacion_OT_MER;
    private String fecha_Creacion_OT_MER_Aud;
    private String segmento_OT_MER;
    private String segmento_OT_MER_Aud;
    private String tecnologia_OT_MGL;
    private String codigoCMR;
    private String usuario_Creacion_OT_MER;
    private String appt_number_OFSC;
    private String orden_RR;
    private String subTipo_Orden_OFSC;
    private String subTipo_Orden_OFSC_Aud;
    private String fecha_agenda_OFSC;
    private String usuario_creacion_agenda_OFSC;
    private String time_slot_OFSC;
    private String estado_agenda_OFSC;
    private String estado_agenda_OFSC_Aud;
    private String fecha_inicia_agenda_OFSC;
    private String fecha_inicia_agenda_OFSC_Aud;
    private String fecha_fin_agenda_OFSC;
    private String fecha_fin_agenda_OFSC_Aud;
    private String id_aliado_OFSC;
    private String id_aliado_OFSC_Aud;
    private String nombre_aliado_OFSC;
    private String nombre_aliado_OFSC_Aud;
    private String id_tecnico_aliado_OFSC;
    private String id_tecnico_aliado_OFSC_Aud;
    private String nombre_tecnico_aliado_OFSC;
    private String nombre_tecnico_aliado_OFSC_Aud;
    private String ultima_agenda_multiagenda;
    private String observaciones_tecnico_OFSC;
    private String cmObj;
    private String complejidadServicio;
    private String multiAgenda;
    private String fechaAsigTecnico;
    private String fechaAsigTecnico_Aud;
    private String fechaReagenda;
    private String fechaReagenda_Aud;
    private String direccionMer;
    private String departamento;
    private String complejidadServicioAud;
    private String usuarioModOt;
    private String usuarioModOtAud;
    private String usuarioModAgenda;
    private String usuarioModAgendaAud;
    private String fechaModificacionOt;
    private String nombreCM;
    private String regional;
    private String ciudad;
    private String codigoProyecto;
    private String indicadorCumplimiento;
    private String emailCTec;
    private String personaAtiendeSitio;
    private String telefonoAtiendeSitio;
    private String tiempoProgramacion;
    private String tiempoAtencion;
    private String tiempoEjecucion;
    private String resultadoOrden;
    private String antiguedadOrden;
    private String cantReagenda;
    private String motivosReagenda;
    private String conveniencia;
    private String aImplement;
    private String estadoOt;
    private String fechaCreacionOnyx;
    private String ciudadFact;
    private String ciudadFactAud;
    private String estadoOt_Aud;
    private String fecha_Modificacion_Ot;

    public BigDecimal getId_OnyxCmDir() {
        return id_OnyxCmDir;
    }

    public void setId_OnyxCmDir(BigDecimal id_OnyxCmDir) {
        this.id_OnyxCmDir = id_OnyxCmDir;
    }

    public String getOt_Id_Cm() {
        return ot_Id_Cm;
    }

    public void setOt_Id_Cm(String ot_Id_Cm) {
        this.ot_Id_Cm = ot_Id_Cm;
    }

    public String getOt_Direccion_Id() {
        return ot_Direccion_Id;
    }

    public void setOt_Direccion_Id(String ot_Direccion_Id) {
        this.ot_Direccion_Id = ot_Direccion_Id;
    }

    public String getOnyx_Ot_Hija_Cm() {
        return Onyx_Ot_Hija_Cm;
    }

    public void setOnyx_Ot_Hija_Cm(String Onyx_Ot_Hija_Cm) {
        this.Onyx_Ot_Hija_Cm = Onyx_Ot_Hija_Cm;
    }

    public String getOnyx_Ot_Hija_Cm_Aud() {
        return Onyx_Ot_Hija_Cm_Aud;
    }

    public void setOnyx_Ot_Hija_Cm_Aud(String Onyx_Ot_Hija_Cm_Aud) {
        this.Onyx_Ot_Hija_Cm_Aud = Onyx_Ot_Hija_Cm_Aud;
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

    public String getOnyx_Ot_Padre_Cm_Aud() {
        return Onyx_Ot_Padre_Cm_Aud;
    }

    public void setOnyx_Ot_Padre_Cm_Aud(String Onyx_Ot_Padre_Cm_Aud) {
        this.Onyx_Ot_Padre_Cm_Aud = Onyx_Ot_Padre_Cm_Aud;
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

    public String getNit_Cliente_Onyx_Aud() {
        return nit_Cliente_Onyx_Aud;
    }

    public void setNit_Cliente_Onyx_Aud(String nit_Cliente_Onyx_Aud) {
        this.nit_Cliente_Onyx_Aud = nit_Cliente_Onyx_Aud;
    }

    public String getNombre_Cliente_Onyx() {
        return nombre_Cliente_Onyx;
    }

    public void setNombre_Cliente_Onyx(String nombre_Cliente_Onyx) {
        this.nombre_Cliente_Onyx = nombre_Cliente_Onyx;
    }

    public String getNombre_Cliente_Onyx_Aud() {
        return nombre_Cliente_Onyx_Aud;
    }

    public void setNombre_Cliente_Onyx_Aud(String nombre_Cliente_Onyx_Aud) {
        this.nombre_Cliente_Onyx_Aud = nombre_Cliente_Onyx_Aud;
    }

    public String getNombre_Ot_Hija_Onyx() {
        return nombre_Ot_Hija_Onyx;
    }

    public void setNombre_Ot_Hija_Onyx(String nombre_Ot_Hija_Onyx) {
        this.nombre_Ot_Hija_Onyx = nombre_Ot_Hija_Onyx;
    }

    public String getNombre_OT_Hija_Onyx_Aud() {
        return nombre_OT_Hija_Onyx_Aud;
    }

    public void setNombre_OT_Hija_Onyx_Aud(String nombre_OT_Hija_Onyx_Aud) {
        this.nombre_OT_Hija_Onyx_Aud = nombre_OT_Hija_Onyx_Aud;
    }

    public String getFecha_Creacion_Ot_Hija_Onyx() {
        return fecha_Creacion_Ot_Hija_Onyx;
    }

    public void setFecha_Creacion_Ot_Hija_Onyx(String fecha_Creacion_Ot_Hija_Onyx) {
        this.fecha_Creacion_Ot_Hija_Onyx = fecha_Creacion_Ot_Hija_Onyx;
    }

    public String getFecha_Creación_OT_Hija_Onyx_Aud() {
        return fecha_Creación_OT_Hija_Onyx_Aud;
    }

    public void setFecha_Creación_OT_Hija_Onyx_Aud(String fecha_Creación_OT_Hija_Onyx_Aud) {
        this.fecha_Creación_OT_Hija_Onyx_Aud = fecha_Creación_OT_Hija_Onyx_Aud;
    }

    public String getFecha_Creacion_Ot_Padre_Onyx() {
        return fecha_Creacion_Ot_Padre_Onyx;
    }

    public void setFecha_Creacion_Ot_Padre_Onyx(String fecha_Creacion_Ot_Padre_Onyx) {
        this.fecha_Creacion_Ot_Padre_Onyx = fecha_Creacion_Ot_Padre_Onyx;
    }

    public String getFecha_Creacion_OT_Padre_Onyx_Aud() {
        return fecha_Creacion_OT_Padre_Onyx_Aud;
    }

    public void setFecha_Creacion_OT_Padre_Onyx_Aud(String fecha_Creacion_OT_Padre_Onyx_Aud) {
        this.fecha_Creacion_OT_Padre_Onyx_Aud = fecha_Creacion_OT_Padre_Onyx_Aud;
    }

    public String getContacto_Tecnico_Ot_Padre_Onyx() {
        return contacto_Tecnico_Ot_Padre_Onyx;
    }

    public void setContacto_Tecnico_Ot_Padre_Onyx(String contacto_Tecnico_Ot_Padre_Onyx) {
        this.contacto_Tecnico_Ot_Padre_Onyx = contacto_Tecnico_Ot_Padre_Onyx;
    }

    public String getContacto_Tecnico_Ot_Padre_Onyx_Aud() {
        return contacto_Tecnico_Ot_Padre_Onyx_Aud;
    }

    public void setContacto_Tecnico_Ot_Padre_Onyx_Aud(String contacto_Tecnico_Ot_Padre_Onyx_Aud) {
        this.contacto_Tecnico_Ot_Padre_Onyx_Aud = contacto_Tecnico_Ot_Padre_Onyx_Aud;
    }

    public String getTelefono_Tecnico_Ot_Padre_Onyx() {
        return telefono_Tecnico_Ot_Padre_Onyx;
    }

    public void setTelefono_Tecnico_Ot_Padre_Onyx(String telefono_Tecnico_Ot_Padre_Onyx) {
        this.telefono_Tecnico_Ot_Padre_Onyx = telefono_Tecnico_Ot_Padre_Onyx;
    }

    public String getTelefono_Tecnico_Ot_Padre_Onyx_Aud() {
        return telefono_Tecnico_Ot_Padre_Onyx_Aud;
    }

    public void setTelefono_Tecnico_Ot_Padre_Onyx_Aud(String telefono_Tecnico_Ot_Padre_Onyx_Aud) {
        this.telefono_Tecnico_Ot_Padre_Onyx_Aud = telefono_Tecnico_Ot_Padre_Onyx_Aud;
    }

    public String getDescripcion_Onyx() {
        return descripcion_Onyx;
    }

    public void setDescripcion_Onyx(String descripcion_Onyx) {
        this.descripcion_Onyx = descripcion_Onyx;
    }

    public String getDescripcion_Onyx_Aud() {
        return descripcion_Onyx_Aud;
    }

    public void setDescripcion_Onyx_Aud(String descripcion_Onyx_Aud) {
        this.descripcion_Onyx_Aud = descripcion_Onyx_Aud;
    }

    public String getDireccion_Onyx() {
        return direccion_Onyx;
    }

    public void setDireccion_Onyx(String direccion_Onyx) {
        this.direccion_Onyx = direccion_Onyx;
    }

    public String getDireccion_Onyx_Aud() {
        return direccion_Onyx_Aud;
    }

    public void setDireccion_Onyx_Aud(String direccion_Onyx_Aud) {
        this.direccion_Onyx_Aud = direccion_Onyx_Aud;
    }

    public String getSegmento_Onyx() {
        return segmento_Onyx;
    }

    public void setSegmento_Onyx(String segmento_Onyx) {
        this.segmento_Onyx = segmento_Onyx;
    }

    public String getSegmento_Onyx_Aud() {
        return segmento_Onyx_Aud;
    }

    public void setSegmento_Onyx_Aud(String segmento_Onyx_Aud) {
        this.segmento_Onyx_Aud = segmento_Onyx_Aud;
    }

    public String getTipo_Servicio_Onyx() {
        return tipo_Servicio_Onyx;
    }

    public void setTipo_Servicio_Onyx(String tipo_Servicio_Onyx) {
        this.tipo_Servicio_Onyx = tipo_Servicio_Onyx;
    }

    public String getTipo_Servicio_Onyx_Aud() {
        return tipo_Servicio_Onyx_Aud;
    }

    public void setTipo_Servicio_Onyx_Aud(String tipo_Servicio_Onyx_Aud) {
        this.tipo_Servicio_Onyx_Aud = tipo_Servicio_Onyx_Aud;
    }

    public String getServicios_Onyx() {
        return servicios_Onyx;
    }

    public void setServicios_Onyx(String servicios_Onyx) {
        this.servicios_Onyx = servicios_Onyx;
    }

    public String getServicios_Onyx_Aud() {
        return servicios_Onyx_Aud;
    }

    public void setServicios_Onyx_Aud(String servicios_Onyx_Aud) {
        this.servicios_Onyx_Aud = servicios_Onyx_Aud;
    }

    public String getRecurrente_Mensual_Onyx() {
        return recurrente_Mensual_Onyx;
    }

    public void setRecurrente_Mensual_Onyx(String recurrente_Mensual_Onyx) {
        this.recurrente_Mensual_Onyx = recurrente_Mensual_Onyx;
    }

    public String getRecurrente_Mensual_Onyx_Aud() {
        return recurrente_Mensual_Onyx_Aud;
    }

    public void setRecurrente_Mensual_Onyx_Aud(String recurrente_Mensual_Onyx_Aud) {
        this.recurrente_Mensual_Onyx_Aud = recurrente_Mensual_Onyx_Aud;
    }

    public String getCodigo_Servicio_Onyx() {
        return codigo_Servicio_Onyx;
    }

    public void setCodigo_Servicio_Onyx(String codigo_Servicio_Onyx) {
        this.codigo_Servicio_Onyx = codigo_Servicio_Onyx;
    }

    public String getCodigo_Servicio_Onyx_Aud() {
        return codigo_Servicio_Onyx_Aud;
    }

    public void setCodigo_Servicio_Onyx_Aud(String codigo_Servicio_Onyx_Aud) {
        this.codigo_Servicio_Onyx_Aud = codigo_Servicio_Onyx_Aud;
    }

    public String getVendedor_Onyx() {
        return vendedor_Onyx;
    }

    public void setVendedor_Onyx(String vendedor_Onyx) {
        this.vendedor_Onyx = vendedor_Onyx;
    }

    public String getVendedor_Onyx_Aud() {
        return vendedor_Onyx_Aud;
    }

    public void setVendedor_Onyx_Aud(String vendedor_Onyx_Aud) {
        this.vendedor_Onyx_Aud = vendedor_Onyx_Aud;
    }

    public String getTelefono_Vendedor_Onyx() {
        return telefono_Vendedor_Onyx;
    }

    public void setTelefono_Vendedor_Onyx(String telefono_Vendedor_Onyx) {
        this.telefono_Vendedor_Onyx = telefono_Vendedor_Onyx;
    }

    public String getTelefono_Vendedor_Onyx_Aud() {
        return telefono_Vendedor_Onyx_Aud;
    }

    public void setTelefono_Vendedor_Onyx_Aud(String telefono_Vendedor_Onyx_Aud) {
        this.telefono_Vendedor_Onyx_Aud = telefono_Vendedor_Onyx_Aud;
    }

    public String getEstado_Ot_Hija_Onyx_Cm() {
        return estado_Ot_Hija_Onyx_Cm;
    }

    public void setEstado_Ot_Hija_Onyx_Cm(String estado_Ot_Hija_Onyx_Cm) {
        this.estado_Ot_Hija_Onyx_Cm = estado_Ot_Hija_Onyx_Cm;
    }

    public String getEstado_Ot_Hija_Onyx_Cm_Aud() {
        return estado_Ot_Hija_Onyx_Cm_Aud;
    }

    public void setEstado_Ot_Hija_Onyx_Cm_Aud(String estado_Ot_Hija_Onyx_Cm_Aud) {
        this.estado_Ot_Hija_Onyx_Cm_Aud = estado_Ot_Hija_Onyx_Cm_Aud;
    }

    public String getEstado_Ot_Padre_Onyx_Cm() {
        return estado_Ot_Padre_Onyx_Cm;
    }

    public void setEstado_Ot_Padre_Onyx_Cm(String estado_Ot_Padre_Onyx_Cm) {
        this.estado_Ot_Padre_Onyx_Cm = estado_Ot_Padre_Onyx_Cm;
    }

    public String getEstado_Ot_Padre_Onyx_Cm_Aud() {
        return estado_Ot_Padre_Onyx_Cm_Aud;
    }

    public void setEstado_Ot_Padre_Onyx_Cm_Aud(String estado_Ot_Padre_Onyx_Cm_Aud) {
        this.estado_Ot_Padre_Onyx_Cm_Aud = estado_Ot_Padre_Onyx_Cm_Aud;
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

    public String getFecha_Compromiso_Ot_Padre_Onyx() {
        return fecha_Compromiso_Ot_Padre_Onyx;
    }

    public void setFecha_Compromiso_Ot_Padre_Onyx(String fecha_Compromiso_Ot_Padre_Onyx) {
        this.fecha_Compromiso_Ot_Padre_Onyx = fecha_Compromiso_Ot_Padre_Onyx;
    }

    public String getFecha_Compromiso_Ot_Padre_Onyx_Aud() {
        return fecha_Compromiso_Ot_Padre_Onyx_Aud;
    }

    public void setFecha_Compromiso_Ot_Padre_Onyx_Aud(String fecha_Compromiso_Ot_Padre_Onyx_Aud) {
        this.fecha_Compromiso_Ot_Padre_Onyx_Aud = fecha_Compromiso_Ot_Padre_Onyx_Aud;
    }

    public String getOt_Padre_Resolucion_1_Onyx() {
        return ot_Padre_Resolucion_1_Onyx;
    }

    public void setOt_Padre_Resolucion_1_Onyx(String ot_Padre_Resolucion_1_Onyx) {
        this.ot_Padre_Resolucion_1_Onyx = ot_Padre_Resolucion_1_Onyx;
    }

    public String getOt_Padre_Resolucion_1_Onyx_Aud() {
        return ot_Padre_Resolucion_1_Onyx_Aud;
    }

    public void setOt_Padre_Resolucion_1_Onyx_Aud(String ot_Padre_Resolucion_1_Onyx_Aud) {
        this.ot_Padre_Resolucion_1_Onyx_Aud = ot_Padre_Resolucion_1_Onyx_Aud;
    }

    public String getOt_Padre_Resolucion_2_Onyx() {
        return ot_Padre_Resolucion_2_Onyx;
    }

    public void setOt_Padre_Resolucion_2_Onyx(String ot_Padre_Resolucion_2_Onyx) {
        this.ot_Padre_Resolucion_2_Onyx = ot_Padre_Resolucion_2_Onyx;
    }

    public String getOt_Padre_Resolucion_2_Onyx_Aud() {
        return ot_Padre_Resolucion_2_Onyx_Aud;
    }

    public void setOt_Padre_Resolucion_2_Onyx_Aud(String ot_Padre_Resolucion_2_Onyx_Aud) {
        this.ot_Padre_Resolucion_2_Onyx_Aud = ot_Padre_Resolucion_2_Onyx_Aud;
    }

    public String getOt_Padre_Resolucion_3_Onyx() {
        return ot_Padre_Resolucion_3_Onyx;
    }

    public void setOt_Padre_Resolucion_3_Onyx(String ot_Padre_Resolucion_3_Onyx) {
        this.ot_Padre_Resolucion_3_Onyx = ot_Padre_Resolucion_3_Onyx;
    }

    public String getOt_Padre_Resolucion_3_Onyx_Aud() {
        return ot_Padre_Resolucion_3_Onyx_Aud;
    }

    public void setOt_Padre_Resolucion_3_Onyx_Aud(String ot_Padre_Resolucion_3_Onyx_Aud) {
        this.ot_Padre_Resolucion_3_Onyx_Aud = ot_Padre_Resolucion_3_Onyx_Aud;
    }

    public String getOt_Padre_Resolucion_4_Onyx() {
        return ot_Padre_Resolucion_4_Onyx;
    }

    public void setOt_Padre_Resolucion_4_Onyx(String ot_Padre_Resolucion_4_Onyx) {
        this.ot_Padre_Resolucion_4_Onyx = ot_Padre_Resolucion_4_Onyx;
    }

    public String getOt_Padre_Resolucion_4_Onyx_Aud() {
        return ot_Padre_Resolucion_4_Onyx_Aud;
    }

    public void setOt_Padre_Resolucion_4_Onyx_Aud(String ot_Padre_Resolucion_4_Onyx_Aud) {
        this.ot_Padre_Resolucion_4_Onyx_Aud = ot_Padre_Resolucion_4_Onyx_Aud;
    }

    public String getOt_Hija_Resolucion_1_Onyx() {
        return ot_Hija_Resolucion_1_Onyx;
    }

    public void setOt_Hija_Resolucion_1_Onyx(String ot_Hija_Resolucion_1_Onyx) {
        this.ot_Hija_Resolucion_1_Onyx = ot_Hija_Resolucion_1_Onyx;
    }

    public String getOt_Hija_Resolucion_1_Onyx_Aud() {
        return ot_Hija_Resolucion_1_Onyx_Aud;
    }

    public void setOt_Hija_Resolucion_1_Onyx_Aud(String ot_Hija_Resolucion_1_Onyx_Aud) {
        this.ot_Hija_Resolucion_1_Onyx_Aud = ot_Hija_Resolucion_1_Onyx_Aud;
    }

    public String getOt_Hija_Resolucion_2_Onyx() {
        return ot_Hija_Resolucion_2_Onyx;
    }

    public void setOt_Hija_Resolucion_2_Onyx(String ot_Hija_Resolucion_2_Onyx) {
        this.ot_Hija_Resolucion_2_Onyx = ot_Hija_Resolucion_2_Onyx;
    }

    public String getOt_Hija_Resolucion_2_Onyx_Aud() {
        return ot_Hija_Resolucion_2_Onyx_Aud;
    }

    public void setOt_Hija_Resolucion_2_Onyx_Aud(String ot_Hija_Resolucion_2_Onyx_Aud) {
        this.ot_Hija_Resolucion_2_Onyx_Aud = ot_Hija_Resolucion_2_Onyx_Aud;
    }

    public String getOt_Hija_Resolucion_3_Onyx() {
        return ot_Hija_Resolucion_3_Onyx;
    }

    public void setOt_Hija_Resolucion_3_Onyx(String ot_Hija_Resolucion_3_Onyx) {
        this.ot_Hija_Resolucion_3_Onyx = ot_Hija_Resolucion_3_Onyx;
    }

    public String getOt_Hija_Resolucion_3_Onyx_Aud() {
        return ot_Hija_Resolucion_3_Onyx_Aud;
    }

    public void setOt_Hija_Resolucion_3_Onyx_Aud(String ot_Hija_Resolucion_3_Onyx_Aud) {
        this.ot_Hija_Resolucion_3_Onyx_Aud = ot_Hija_Resolucion_3_Onyx_Aud;
    }

    public String getOt_Hija_Resolucion_4_Onyx() {
        return ot_Hija_Resolucion_4_Onyx;
    }

    public void setOt_Hija_Resolucion_4_Onyx(String ot_Hija_Resolucion_4_Onyx) {
        this.ot_Hija_Resolucion_4_Onyx = ot_Hija_Resolucion_4_Onyx;
    }

    public String getOt_Hija_Resolucion_4_Onyx_Aud() {
        return ot_Hija_Resolucion_4_Onyx_Aud;
    }

    public void setOt_Hija_Resolucion_4_Onyx_Aud(String ot_Hija_Resolucion_4_Onyx_Aud) {
        this.ot_Hija_Resolucion_4_Onyx_Aud = ot_Hija_Resolucion_4_Onyx_Aud;
    }

    public String getTipo_OT_MER() {
        return tipo_OT_MER;
    }

    public void setTipo_OT_MER(String tipo_OT_MER) {
        this.tipo_OT_MER = tipo_OT_MER;
    }

    public String getSub_tipo_OT_MER() {
        return sub_tipo_OT_MER;
    }

    public void setSub_tipo_OT_MER(String sub_tipo_OT_MER) {
        this.sub_tipo_OT_MER = sub_tipo_OT_MER;
    }

    public String getSub_tipo_OT_MER_Aud() {
        return sub_tipo_OT_MER_Aud;
    }

    public void setSub_tipo_OT_MER_Aud(String sub_tipo_OT_MER_Aud) {
        this.sub_tipo_OT_MER_Aud = sub_tipo_OT_MER_Aud;
    }

    public String getEstado_interno_OT_MER() {
        return estado_interno_OT_MER;
    }

    public void setEstado_interno_OT_MER(String estado_interno_OT_MER) {
        this.estado_interno_OT_MER = estado_interno_OT_MER;
    }

    public String getEstado_interno_OT_MER_Aud() {
        return estado_interno_OT_MER_Aud;
    }

    public void setEstado_interno_OT_MER_Aud(String estado_interno_OT_MER_Aud) {
        this.estado_interno_OT_MER_Aud = estado_interno_OT_MER_Aud;
    }

    public String getFecha_creacion_OT_MER() {
        return fecha_creacion_OT_MER;
    }

    public void setFecha_creacion_OT_MER(String fecha_creacion_OT_MER) {
        this.fecha_creacion_OT_MER = fecha_creacion_OT_MER;
    }

    public String getFecha_Creacion_OT_MER_Aud() {
        return fecha_Creacion_OT_MER_Aud;
    }

    public void setFecha_Creacion_OT_MER_Aud(String fecha_Creacion_OT_MER_Aud) {
        this.fecha_Creacion_OT_MER_Aud = fecha_Creacion_OT_MER_Aud;
    }

    public String getSegmento_OT_MER() {
        return segmento_OT_MER;
    }

    public void setSegmento_OT_MER(String segmento_OT_MER) {
        this.segmento_OT_MER = segmento_OT_MER;
    }

    public String getSegmento_OT_MER_Aud() {
        return segmento_OT_MER_Aud;
    }

    public void setSegmento_OT_MER_Aud(String segmento_OT_MER_Aud) {
        this.segmento_OT_MER_Aud = segmento_OT_MER_Aud;
    }

    public String getTecnologia_OT_MGL() {
        return tecnologia_OT_MGL;
    }

    public void setTecnologia_OT_MGL(String tecnologia_OT_MGL) {
        this.tecnologia_OT_MGL = tecnologia_OT_MGL;
    }

    public String getCodigoCMR() {
        return codigoCMR;
    }

    public void setCodigoCMR(String codigoCMR) {
        this.codigoCMR = codigoCMR;
    }

    public String getUsuario_Creacion_OT_MER() {
        return usuario_Creacion_OT_MER;
    }

    public void setUsuario_Creacion_OT_MER(String usuario_Creacion_OT_MER) {
        this.usuario_Creacion_OT_MER = usuario_Creacion_OT_MER;
    }

    public String getAppt_number_OFSC() {
        return appt_number_OFSC;
    }

    public void setAppt_number_OFSC(String appt_number_OFSC) {
        this.appt_number_OFSC = appt_number_OFSC;
    }

    public String getOrden_RR() {
        return orden_RR;
    }

    public void setOrden_RR(String orden_RR) {
        this.orden_RR = orden_RR;
    }

    public String getSubTipo_Orden_OFSC() {
        return subTipo_Orden_OFSC;
    }

    public void setSubTipo_Orden_OFSC(String subTipo_Orden_OFSC) {
        this.subTipo_Orden_OFSC = subTipo_Orden_OFSC;
    }

    public String getSubTipo_Orden_OFSC_Aud() {
        return subTipo_Orden_OFSC_Aud;
    }

    public void setSubTipo_Orden_OFSC_Aud(String subTipo_Orden_OFSC_Aud) {
        this.subTipo_Orden_OFSC_Aud = subTipo_Orden_OFSC_Aud;
    }

    public String getFecha_agenda_OFSC() {
        return fecha_agenda_OFSC;
    }

    public void setFecha_agenda_OFSC(String fecha_agenda_OFSC) {
        this.fecha_agenda_OFSC = fecha_agenda_OFSC;
    }

    public String getUsuario_creacion_agenda_OFSC() {
        return usuario_creacion_agenda_OFSC;
    }

    public void setUsuario_creacion_agenda_OFSC(String usuario_creacion_agenda_OFSC) {
        this.usuario_creacion_agenda_OFSC = usuario_creacion_agenda_OFSC;
    }

    public String getTime_slot_OFSC() {
        return time_slot_OFSC;
    }

    public void setTime_slot_OFSC(String time_slot_OFSC) {
        this.time_slot_OFSC = time_slot_OFSC;
    }

    public String getEstado_agenda_OFSC() {
        return estado_agenda_OFSC;
    }

    public void setEstado_agenda_OFSC(String estado_agenda_OFSC) {
        this.estado_agenda_OFSC = estado_agenda_OFSC;
    }

    public String getEstado_agenda_OFSC_Aud() {
        return estado_agenda_OFSC_Aud;
    }

    public void setEstado_agenda_OFSC_Aud(String estado_agenda_OFSC_Aud) {
        this.estado_agenda_OFSC_Aud = estado_agenda_OFSC_Aud;
    }

    public String getFecha_inicia_agenda_OFSC() {
        return fecha_inicia_agenda_OFSC;
    }

    public void setFecha_inicia_agenda_OFSC(String fecha_inicia_agenda_OFSC) {
        this.fecha_inicia_agenda_OFSC = fecha_inicia_agenda_OFSC;
    }

    public String getFecha_inicia_agenda_OFSC_Aud() {
        return fecha_inicia_agenda_OFSC_Aud;
    }

    public void setFecha_inicia_agenda_OFSC_Aud(String fecha_inicia_agenda_OFSC_Aud) {
        this.fecha_inicia_agenda_OFSC_Aud = fecha_inicia_agenda_OFSC_Aud;
    }

    public String getFecha_fin_agenda_OFSC() {
        return fecha_fin_agenda_OFSC;
    }

    public void setFecha_fin_agenda_OFSC(String fecha_fin_agenda_OFSC) {
        this.fecha_fin_agenda_OFSC = fecha_fin_agenda_OFSC;
    }

    public String getFecha_fin_agenda_OFSC_Aud() {
        return fecha_fin_agenda_OFSC_Aud;
    }

    public void setFecha_fin_agenda_OFSC_Aud(String fecha_fin_agenda_OFSC_Aud) {
        this.fecha_fin_agenda_OFSC_Aud = fecha_fin_agenda_OFSC_Aud;
    }

    public String getId_aliado_OFSC() {
        return id_aliado_OFSC;
    }

    public void setId_aliado_OFSC(String id_aliado_OFSC) {
        this.id_aliado_OFSC = id_aliado_OFSC;
    }

    public String getId_aliado_OFSC_Aud() {
        return id_aliado_OFSC_Aud;
    }

    public void setId_aliado_OFSC_Aud(String id_aliado_OFSC_Aud) {
        this.id_aliado_OFSC_Aud = id_aliado_OFSC_Aud;
    }

    public String getNombre_aliado_OFSC() {
        return nombre_aliado_OFSC;
    }

    public void setNombre_aliado_OFSC(String nombre_aliado_OFSC) {
        this.nombre_aliado_OFSC = nombre_aliado_OFSC;
    }

    public String getNombre_aliado_OFSC_Aud() {
        return nombre_aliado_OFSC_Aud;
    }

    public void setNombre_aliado_OFSC_Aud(String nombre_aliado_OFSC_Aud) {
        this.nombre_aliado_OFSC_Aud = nombre_aliado_OFSC_Aud;
    }

    public String getId_tecnico_aliado_OFSC() {
        return id_tecnico_aliado_OFSC;
    }

    public void setId_tecnico_aliado_OFSC(String id_tecnico_aliado_OFSC) {
        this.id_tecnico_aliado_OFSC = id_tecnico_aliado_OFSC;
    }

    public String getId_tecnico_aliado_OFSC_Aud() {
        return id_tecnico_aliado_OFSC_Aud;
    }

    public void setId_tecnico_aliado_OFSC_Aud(String id_tecnico_aliado_OFSC_Aud) {
        this.id_tecnico_aliado_OFSC_Aud = id_tecnico_aliado_OFSC_Aud;
    }

    public String getNombre_tecnico_aliado_OFSC() {
        return nombre_tecnico_aliado_OFSC;
    }

    public void setNombre_tecnico_aliado_OFSC(String nombre_tecnico_aliado_OFSC) {
        this.nombre_tecnico_aliado_OFSC = nombre_tecnico_aliado_OFSC;
    }

    public String getNombre_tecnico_aliado_OFSC_Aud() {
        return nombre_tecnico_aliado_OFSC_Aud;
    }

    public void setNombre_tecnico_aliado_OFSC_Aud(String nombre_tecnico_aliado_OFSC_Aud) {
        this.nombre_tecnico_aliado_OFSC_Aud = nombre_tecnico_aliado_OFSC_Aud;
    }

    public String getUltima_agenda_multiagenda() {
        return ultima_agenda_multiagenda;
    }

    public void setUltima_agenda_multiagenda(String ultima_agenda_multiagenda) {
        this.ultima_agenda_multiagenda = ultima_agenda_multiagenda;
    }

    public String getObservaciones_tecnico_OFSC() {
        return observaciones_tecnico_OFSC;
    }

    public void setObservaciones_tecnico_OFSC(String observaciones_tecnico_OFSC) {
        this.observaciones_tecnico_OFSC = observaciones_tecnico_OFSC;
    }

    public String getCmObj() {
        return cmObj;
    }

    public void setCmObj(String cmObj) {
        this.cmObj = cmObj;
    }

    public String getComplejidadServicio() {
        return complejidadServicio;
    }

    public void setComplejidadServicio(String complejidadServicio) {
        this.complejidadServicio = complejidadServicio;
    }

    public String getMultiAgenda() {
        return multiAgenda;
    }

    public void setMultiAgenda(String multiAgenda) {
        this.multiAgenda = multiAgenda;
    }

    public String getFechaAsigTecnico() {
        return fechaAsigTecnico;
    }

    public void setFechaAsigTecnico(String fechaAsigTecnico) {
        this.fechaAsigTecnico = fechaAsigTecnico;
    }

    public String getFechaReagenda() {
        return fechaReagenda;
    }

    public void setFechaReagenda(String fechaReagenda) {
        this.fechaReagenda = fechaReagenda;
    }

    public String getFechaAsigTecnico_Aud() {
        return fechaAsigTecnico_Aud;
    }

    public void setFechaAsigTecnico_Aud(String fechaAsigTecnico_Aud) {
        this.fechaAsigTecnico_Aud = fechaAsigTecnico_Aud;
    }

    public String getFechaReagenda_Aud() {
        return fechaReagenda_Aud;
    }

    public void setFechaReagenda_Aud(String fechaReagenda_Aud) {
        this.fechaReagenda_Aud = fechaReagenda_Aud;
    }

    public String getDireccionMer() {
        return direccionMer;
    }

    public void setDireccionMer(String direccionMer) {
        this.direccionMer = direccionMer;
    }

    public String getOnyx_Ot_Padre_Dir_Aud() {
        return Onyx_Ot_Padre_Dir_Aud;
    }

    public void setOnyx_Ot_Padre_Dir_Aud(String Onyx_Ot_Padre_Dir_Aud) {
        this.Onyx_Ot_Padre_Dir_Aud = Onyx_Ot_Padre_Dir_Aud;
    }

    public String getEstado_Ot_Hija_Onyx_Dir_Aud() {
        return estado_Ot_Hija_Onyx_Dir_Aud;
    }

    public void setEstado_Ot_Hija_Onyx_Dir_Aud(String estado_Ot_Hija_Onyx_Dir_Aud) {
        this.estado_Ot_Hija_Onyx_Dir_Aud = estado_Ot_Hija_Onyx_Dir_Aud;
    }

    public String getEstado_Ot_Padre_Onyx_Dir_Aud() {
        return estado_Ot_Padre_Onyx_Dir_Aud;
    }

    public void setEstado_Ot_Padre_Onyx_Dir_Aud(String estado_Ot_Padre_Onyx_Dir_Aud) {
        this.estado_Ot_Padre_Onyx_Dir_Aud = estado_Ot_Padre_Onyx_Dir_Aud;
    }

    public String getOnyx_Ot_Hija_Dir_Aud() {
        return Onyx_Ot_Hija_Dir_Aud;
    }

    public void setOnyx_Ot_Hija_Dir_Aud(String Onyx_Ot_Hija_Dir_Aud) {
        this.Onyx_Ot_Hija_Dir_Aud = Onyx_Ot_Hija_Dir_Aud;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getComplejidadServicioAud() {
        return complejidadServicioAud;
    }

    public void setComplejidadServicioAud(String complejidadServicioAud) {
        this.complejidadServicioAud = complejidadServicioAud;
    }

    public String getUsuarioModOt() {
        return usuarioModOt;
    }

    public void setUsuarioModOt(String usuarioModOt) {
        this.usuarioModOt = usuarioModOt;
    }

    public String getUsuarioModOtAud() {
        return usuarioModOtAud;
    }

    public void setUsuarioModOtAud(String usuarioModOtAud) {
        this.usuarioModOtAud = usuarioModOtAud;
    }

    public String getUsuarioModAgenda() {
        return usuarioModAgenda;
    }

    public void setUsuarioModAgenda(String usuarioModAgenda) {
        this.usuarioModAgenda = usuarioModAgenda;
    }

    public String getUsuarioModAgendaAud() {
        return usuarioModAgendaAud;
    }

    public void setUsuarioModAgendaAud(String usuarioModAgendaAud) {
        this.usuarioModAgendaAud = usuarioModAgendaAud;
    }

    public String getFechaModificacionOt() {
        return fechaModificacionOt;
    }

    public void setFechaModificacionOt(String fechaModificacionOt) {
        this.fechaModificacionOt = fechaModificacionOt;
    }

    public String getNombreCM() {
        return nombreCM;
    }

    public void setNombreCM(String nombreCM) {
        this.nombreCM = nombreCM;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(String codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

    public String getIndicadorCumplimiento() {
        return indicadorCumplimiento;
    }

    public void setIndicadorCumplimiento(String indicadorCumplimiento) {
        this.indicadorCumplimiento = indicadorCumplimiento;
    }

    public String getEmailCTec() {
        return emailCTec;
    }

    public void setEmailCTec(String emailCTec) {
        this.emailCTec = emailCTec;
    }

    public String getPersonaAtiendeSitio() {
        return personaAtiendeSitio;
    }

    public void setPersonaAtiendeSitio(String personaAtiendeSitio) {
        this.personaAtiendeSitio = personaAtiendeSitio;
    }

    public String getTelefonoAtiendeSitio() {
        return telefonoAtiendeSitio;
    }

    public void setTelefonoAtiendeSitio(String telefonoAtiendeSitio) {
        this.telefonoAtiendeSitio = telefonoAtiendeSitio;
    }

    public String getTiempoProgramacion() {
        return tiempoProgramacion;
    }

    public void setTiempoProgramacion(String tiempoProgramacion) {
        this.tiempoProgramacion = tiempoProgramacion;
    }

    public String getTiempoAtencion() {
        return tiempoAtencion;
    }

    public void setTiempoAtencion(String tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }

    public String getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setTiempoEjecucion(String tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public String getResultadoOrden() {
        return resultadoOrden;
    }

    public void setResultadoOrden(String resultadoOrden) {
        this.resultadoOrden = resultadoOrden;
    }

    public String getAntiguedadOrden() {
        return antiguedadOrden;
    }

    public void setAntiguedadOrden(String antiguedadOrden) {
        this.antiguedadOrden = antiguedadOrden;
    }

    public String getCantReagenda() {
        return cantReagenda;
    }

    public void setCantReagenda(String cantReagenda) {
        this.cantReagenda = cantReagenda;
    }

    public String getMotivosReagenda() {
        return motivosReagenda;
    }

    public void setMotivosReagenda(String motivosReagenda) {
        this.motivosReagenda = motivosReagenda;
    }

    public String getConveniencia() {
        return conveniencia;
    }

    public void setConveniencia(String conveniencia) {
        this.conveniencia = conveniencia;
    }

    public String getaImplement() {
        return aImplement;
    }

    public void setaImplement(String aImplement) {
        this.aImplement = aImplement;
    }

    public String getEstadoOt() {
        return estadoOt;
    }

    public void setEstadoOt(String estadoOt) {
        this.estadoOt = estadoOt;
    }

    public String getFechaCreacionOnyx() {
        return fechaCreacionOnyx;
    }

    public void setFechaCreacionOnyx(String fechaCreacionOnyx) {
        this.fechaCreacionOnyx = fechaCreacionOnyx;
    }

    public String getCiudadFact() {
        return ciudadFact;
    }

    public void setCiudadFact(String ciudadFact) {
        this.ciudadFact = ciudadFact;
    }

    public String getCiudadFactAud() {
        return ciudadFactAud;
    }

    public void setCiudadFactAud(String ciudadFactAud) {
        this.ciudadFactAud = ciudadFactAud;
    }

    public String getEstadoOt_Aud() {
        return estadoOt_Aud;
    }

    public void setEstadoOt_Aud(String estadoOt_Aud) {
        this.estadoOt_Aud = estadoOt_Aud;
    }

    public String getFecha_Modificacion_Ot() {
        return fecha_Modificacion_Ot;
    }

    public void setFecha_Modificacion_Ot(String fecha_Modificacion_Ot) {
        this.fecha_Modificacion_Ot = fecha_Modificacion_Ot;
    }
    
    
}
