/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.businessmanager.address.OtHhppTecnologiaMglManager;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dao.impl.GeograficoPoliticoDaoImpl;
import co.com.claro.mgl.dtos.ReporteHistoricoOtCmDto;
import co.com.claro.mgl.dtos.ReporteHistoricoOtDIRDto;
import co.com.claro.mgl.dtos.ReporteOtDIRDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccionAuditoria;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDirAuditoria;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMglAuditoria;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendaAuditoria;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.DateUtils;
import co.com.claro.mgl.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author cardenaslb
 */
public class CmtOrdenTrabajoMglReportDaoImpl extends GenericDaoImpl<CmtOrdenTrabajoMgl>{
     
    
    private static final Logger LOGGER = LogManager.getLogger(CmtOrdenTrabajoMglReportDaoImpl.class);
     

    public List<ReporteOtDIRDto> getReporteEstadoActualOtDIR(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            List<ReporteOtDIRDto> listaReporteOtDIRDto = new ArrayList<>();
            CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
            CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
            GeograficoPoliticoDaoImpl geograficoPoliticoDaoImpl = new GeograficoPoliticoDaoImpl();
            List<GeograficoPoliticoMgl> listaGeograficomgl = geograficoPoliticoDaoImpl.findAll();
            CmtTipoBasicaMgl subtipoOden;
            subtipoOden = cmtTipoBasicaMglDaoImpl.findByCodigoInternoApp(
                     Constant.TIPO_BASICA_SUB_TIPO_TRABAJO_DIRECCIONES);
            List<CmtBasicaMgl> listaBasica = cmtBasicaMglDaoImpl.findByTipoBasica(subtipoOden);
            List<Object[]> listaOrdenesAgendas;

                  sql.append("SELECT o, a,oy "
                       + "FROM OtHhppMgl o "
                       + "left join o.listAgendaDireccion a "
                       + "left join o.listOnyx oy "
                       + "WHERE (o.estadoRegistro is null or o.estadoRegistro = 1 )"
                       + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");
                 
            generarSelectReporteHistoricoOtDIR(sql, params, true);
            Query q = entityManager.createQuery(sql.toString());
            agregarParametrosDIR(q, params, true);
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            listaOrdenesAgendas = (List<Object[]>) q.getResultList();
            for (Object[] orden : listaOrdenesAgendas) {
                ReporteOtDIRDto reporteHistoricoOtDIRDto = new ReporteOtDIRDto();
                OtHhppTecnologiaMglManager otHhppTecnologiaMglManager = new OtHhppTecnologiaMglManager();
                
                reporteHistoricoOtDIRDto.setOt_Direccion_Id(((OtHhppMgl)orden[0]).getOtHhppId().toString());
                reporteHistoricoOtDIRDto.setTipo_OT_MER(((OtHhppMgl)orden[0]).getTipoOtHhppId().getNombreTipoOt() == null ? "" : ((OtHhppMgl)orden[0]).getTipoOtHhppId().getNombreTipoOt());
                reporteHistoricoOtDIRDto.setSub_tipo_OT_MER(((OtHhppMgl)orden[0]).getTipoOtHhppId().getTipoTrabajoOFSC()== null ? "": ((OtHhppMgl)orden[0]).getTipoOtHhppId().getTipoTrabajoOFSC().getNombreBasica());
                reporteHistoricoOtDIRDto.setSubTipo_Orden_OFSC(((OtHhppMgl)orden[0]).getTipoOtHhppId().getSubTipoOrdenOFSC()== null ? "": ((OtHhppMgl)orden[0]).getTipoOtHhppId().getSubTipoOrdenOFSC().getNombreBasica());
                
                reporteHistoricoOtDIRDto.setEstado_interno_OT_MER(((OtHhppMgl)orden[0]).getEstadoGeneral()== null ? "": ((OtHhppMgl)orden[0]).getEstadoGeneral().getNombreBasica());
                SimpleDateFormat fecha_creacion_OT_MER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                String getFechaCreacionMer;
                if (((OtHhppMgl) orden[0]) != null && ((OtHhppMgl) orden[0]).getFechaCreacion() != null) {
                    getFechaCreacionMer = fecha_creacion_OT_MER.format(((OtHhppMgl) orden[0]).getFechaCreacion());
                } else {
                    getFechaCreacionMer = null;
                }
                reporteHistoricoOtDIRDto.setFecha_creacion_OT_MER(getFechaCreacionMer);
                String listaTecno = "";
                List<OtHhppTecnologiaMgl> listaTecnologias = otHhppTecnologiaMglManager.findTecnologias(((OtHhppMgl) orden[0]).getOtHhppId());
                if(listaTecnologias != null){
                    for(OtHhppTecnologiaMgl otHhppTecnologiaMgl : listaTecnologias) {
                        listaTecno += otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getNombreBasica() + ",";
                    }
                    if (!listaTecno.isEmpty()) {
                        listaTecno = listaTecno.substring(0, listaTecno.length() - 1);
                    }
                }else{
                    listaTecno = null;
                }
                reporteHistoricoOtDIRDto.setTecnologia_OT_MGL(listaTecno);
                
                reporteHistoricoOtDIRDto.setDireccionMer(((OtHhppMgl)orden[0]).getSubDireccionId() == null ? ((OtHhppMgl)orden[0]).getDireccionId() == null ? "" : ((OtHhppMgl)orden[0]).getDireccionId().getDirFormatoIgac() : ((OtHhppMgl)orden[0]).getSubDireccionId().getSdiFormatoIgac());
                reporteHistoricoOtDIRDto.setUsuario_Creacion_OT_MER((((OtHhppMgl)orden[0]).getUsuarioCreacion() == null ? "":((OtHhppMgl)orden[0]).getUsuarioCreacion() ));
                reporteHistoricoOtDIRDto.setOnyx_Ot_Hija_Dir(((OtHhppMgl)orden[0]).getOnyxOtHija() == null ? "":((OtHhppMgl)orden[0]).getOnyxOtHija().toString());
                  String complejidadDescripcion;
                if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("A")) {
                    complejidadDescripcion = "ALTA";
                } else if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("M")) {
                    complejidadDescripcion = "MEDIA";
                } else if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("B")) {
                    complejidadDescripcion = "BAJA";
                } else {
                    complejidadDescripcion = "";
                }
                
                reporteHistoricoOtDIRDto.setComplejidadServicio(complejidadDescripcion);
                String departamentoReport = "";
                if (((OtHhppMgl) orden[0]).getDireccionId() != null && ((OtHhppMgl) orden[0]).getDireccionId().getUbicacion()!=null
                        && ((OtHhppMgl) orden[0]).getDireccionId().getUbicacion().getGpoIdObj() != null
                        && ((OtHhppMgl) orden[0]).getDireccionId().getUbicacion().getGpoIdObj().getGeoGpoId() != null) {
                    BigDecimal ciudad = null;
                    for(GeograficoPoliticoMgl departamento : listaGeograficomgl){
                        if(departamento.getGpoId().equals(((OtHhppMgl) orden[0]).getDireccionId().getUbicacion().getGpoIdObj().getGeoGpoId())){
                            ciudad = departamento.getGeoGpoId();
                            break;
                        }
                    }
                     for(GeograficoPoliticoMgl departamento : listaGeograficomgl){
                        if(departamento.getGpoId().equals(ciudad)){
                            departamentoReport = departamento.getGpoNombre();
                            break;
                        }
                    }
                }
                 reporteHistoricoOtDIRDto.setDepartamento(departamentoReport);
                reporteHistoricoOtDIRDto.setUsuarioModOt(((OtHhppMgl) orden[0]).getUsuarioEdicion());
                //agendamiento
                if (((MglAgendaDireccion) orden[1]) != null) {

                    reporteHistoricoOtDIRDto.setOrden_RR(((MglAgendaDireccion) orden[1]).getIdOtenrr() == null ? "" : ((MglAgendaDireccion) orden[1]).getIdOtenrr());
                                      //sub tipo ordenes ofsc
                    for (CmtBasicaMgl cmtBasicaMgl : listaBasica) {
                        if (((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce() != null
                                && ((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                            reporteHistoricoOtDIRDto.setSubTipo_Orden_OFSC(((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce().trim());
                            break;
                        }
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaCreacion;
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaCreacion() != null) {
                        fechaCreacion = dateFormat.format(((MglAgendaDireccion) orden[1]).getFechaAgenda());
                    } else {
                        fechaCreacion = null;
                    }
                    reporteHistoricoOtDIRDto.setFecha_agenda_OFSC(fechaCreacion);
                    reporteHistoricoOtDIRDto.setUsuario_creacion_agenda_OFSC((((MglAgendaDireccion) orden[1]).getUsuarioCreacion() == null ? "" : ((MglAgendaDireccion) orden[1]).getUsuarioCreacion()));
                    String timeS = ((MglAgendaDireccion) orden[1]) == null ? "" : ((MglAgendaDireccion) orden[1]).getTimeSlot() == null ? "" :  ((MglAgendaDireccion) orden[1]).getTimeSlot();
                    if (timeS.equals("")) {
                        reporteHistoricoOtDIRDto.setTime_slot_OFSC(timeS);
                    } else {
                        timeS = timeS.replace("-", "_");
                        reporteHistoricoOtDIRDto.setTime_slot_OFSC(timeS);
                    }
                    reporteHistoricoOtDIRDto.setEstado_agenda_OFSC(((MglAgendaDireccion) orden[1]).getBasicaIdEstadoAgenda()== null ? "" : ((MglAgendaDireccion) orden[1]).getBasicaIdEstadoAgenda().getNombreBasica());
                    SimpleDateFormat fecha_inicia_agenda = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaInivioVt;
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaInivioVt() != null) {
                        fechaInivioVt = fecha_inicia_agenda.format(((MglAgendaDireccion) orden[1]).getFechaInivioVt());
                    } else {
                        fechaInivioVt = null;
                    }
                    reporteHistoricoOtDIRDto.setFecha_inicia_agenda_OFSC(fechaInivioVt);
                    SimpleDateFormat fecha_fin_agenda = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaFinVt;
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaFinVt() != null) {
                        fechaFinVt = fecha_fin_agenda.format(((MglAgendaDireccion) orden[1]).getFechaFinVt());
                    } else {
                        fechaFinVt = null;
                    }
                    reporteHistoricoOtDIRDto.setFecha_fin_agenda_OFSC(fechaFinVt);
                    reporteHistoricoOtDIRDto.setId_aliado_OFSC((((MglAgendaDireccion) orden[1]).getIdentificacionAliado() == null ? ""
                            : ((MglAgendaDireccion) orden[1]).getIdentificacionAliado()));
                    reporteHistoricoOtDIRDto.setNombre_aliado_OFSC((((MglAgendaDireccion) orden[1]) == null ? ""
                            : ((MglAgendaDireccion) orden[1]).getNombreAliado()));

                    reporteHistoricoOtDIRDto.setId_tecnico_aliado_OFSC((((MglAgendaDireccion) orden[1]).getIdentificacionTecnico() == null ? ""
                            : ((MglAgendaDireccion) orden[1]).getIdentificacionTecnico()));
                    reporteHistoricoOtDIRDto.setNombre_tecnico_aliado_OFSC((((MglAgendaDireccion) orden[1]).getNombreTecnico() == null ? ""
                            : ((MglAgendaDireccion) orden[1]).getNombreTecnico()));
                    reporteHistoricoOtDIRDto.setUltima_agenda_multiagenda((((MglAgendaDireccion) orden[1]).getUltimaAgenda() == null ? ""
                            : ((MglAgendaDireccion) orden[1]).getUltimaAgenda()));
                    reporteHistoricoOtDIRDto.setObservaciones_tecnico_OFSC((((MglAgendaDireccion) orden[1]).getObservacionesTecnico() == null ? ""
                            : ((MglAgendaDireccion) orden[1]).getObservacionesTecnico()));
                    reporteHistoricoOtDIRDto.setAppt_number_OFSC(((MglAgendaDireccion) orden[1]) == null ? ""
                        :((MglAgendaDireccion) orden[1]).getOfpsOtId());
                    
                    reporteHistoricoOtDIRDto.setUsuarioModAgenda(((MglAgendaDireccion) orden[1]).getUsuarioEdicion());
                    }
                // onyx
                if (((OnyxOtCmDir) orden[2]) != null) {

                    reporteHistoricoOtDIRDto.setNit_Cliente_Onyx((((OnyxOtCmDir) orden[2]).getNit_Cliente_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getNit_Cliente_Onyx()));
                    reporteHistoricoOtDIRDto.setNombre_Cliente_Onyx(((OnyxOtCmDir) orden[2]).getNombre_Cliente_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getNombre_Cliente_Onyx());
                    reporteHistoricoOtDIRDto.setNombre_Ot_Hija_Onyx(((OnyxOtCmDir) orden[2]).getNombre_Ot_Hija_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getNombre_Ot_Hija_Onyx());
                    reporteHistoricoOtDIRDto.setOnyx_Ot_Padre_Dir(((OnyxOtCmDir) orden[2]).getOnyx_Ot_Hija_Dir() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Hija_Dir());

                    SimpleDateFormat creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fecha_Creacion_Ot_Hija_Onyx;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                        fecha_Creacion_Ot_Hija_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx());
                    } else {
                        fecha_Creacion_Ot_Hija_Onyx = null;
                    }
                    reporteHistoricoOtDIRDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx);
                    reporteHistoricoOtDIRDto.setDireccion_Onyx(((OnyxOtCmDir) orden[2]).getDireccion_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getDireccion_Onyx());
                    reporteHistoricoOtDIRDto.setDescripcion_Onyx(((OnyxOtCmDir) orden[2]).getDescripcion_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getDescripcion_Onyx());
                    reporteHistoricoOtDIRDto.setSegmento_Onyx((((OnyxOtCmDir) orden[2]).getSegmento_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getSegmento_Onyx()));
                    reporteHistoricoOtDIRDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx());

                    reporteHistoricoOtDIRDto.setServicios_Onyx(((OnyxOtCmDir) orden[2]).getServicios_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getServicios_Onyx());
                    reporteHistoricoOtDIRDto.setRecurrente_Mensual_Onyx(((OnyxOtCmDir) orden[2]).getRecurrente_Mensual_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getRecurrente_Mensual_Onyx());
                    reporteHistoricoOtDIRDto.setCodigo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getCodigo_Servicio_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getCodigo_Servicio_Onyx());
                    reporteHistoricoOtDIRDto.setVendedor_Onyx(((OnyxOtCmDir) orden[2]).getVendedor_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getVendedor_Onyx());

                    reporteHistoricoOtDIRDto.setTelefono_Vendedor_Onyx(((OnyxOtCmDir) orden[2]).getTelefono_Vendedor_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getTelefono_Vendedor_Onyx());
                    reporteHistoricoOtDIRDto.setEstado_Ot_Hija_Onyx_Dir((((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getEstado_Ot_Hija_Onyx_Dir()));

                    reporteHistoricoOtDIRDto.setEstado_Ot_Padre_Onyx_Dir(((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getEstado_Ot_Padre_Onyx_Dir());
                    SimpleDateFormat compromiso_Ot_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fecha_Compromiso_Ot_Padre_Onyx = null;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null) {
                        fecha_Compromiso_Ot_Padre_Onyx = compromiso_Ot_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx());
                    } else {
                        fecha_Compromiso_Ot_Padre_Onyx = null;
                    }
                    reporteHistoricoOtDIRDto.setFecha_Compromiso_Ot_Padre_Onyx(fecha_Compromiso_Ot_Padre_Onyx);
                    reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_1_Onyx(((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_1_Onyx());

                    reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_2_Onyx(((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_2_Onyx());
                    reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_3_Onyx(((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_3_Onyx());
                    reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_4_Onyx(((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_4_Onyx());
                    reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_1_Onyx(((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_1_Onyx());
                    reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_2_Onyx(((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_2_Onyx());
                    reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_3_Onyx(((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_3_Onyx());
                    reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_4_Onyx(((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_4_Onyx());

                    reporteHistoricoOtDIRDto.setOnyx_Ot_Padre_Dir(((OnyxOtCmDir) orden[2]) == null ? "" : ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Padre_Dir());
                    SimpleDateFormat fecha_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fecha_Creacion_Ot_Padre_Onyx;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                        fecha_Creacion_Ot_Padre_Onyx = fecha_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx());
                    } else {
                        fecha_Creacion_Ot_Padre_Onyx = null;
                    }
                    reporteHistoricoOtDIRDto.setFecha_Creacion_Ot_Padre_Onyx(fecha_Creacion_Ot_Padre_Onyx);
                    reporteHistoricoOtDIRDto.setContacto_Tecnico_Ot_Padre_Onyx(((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx() == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx());
                    reporteHistoricoOtDIRDto.setTelefono_Tecnico_Ot_Padre_Onyx(((OnyxOtCmDir) orden[2]) == null ? ""
                            : ((OnyxOtCmDir) orden[2]).getTelefono_Tecnico_Ot_Padre_Onyx());
                }
                listaReporteOtDIRDto.add(reporteHistoricoOtDIRDto);
                
                
            }
            getEntityManager().clear();
            return listaReporteOtDIRDto;

        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        }
    }
       
       
       
         /**
     * M&eacute;todo para generar el reporte de ordenes de trabajo de CM
     *
     * @param sql {@link StringBuilder} objeto para generar el String de la
     * consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     *
     */
    private void generarSelectReporteHistoricoOtDIR(StringBuilder sql, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<BigDecimal> subtipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOSFC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<BigDecimal> listEstadosSelected = (List<BigDecimal>) params.get("listEstadosSelected");
        List<String> regionalList = (List<String>) params.get("listRegionalSelected");
        List<String> ciudadList = (List<String>) params.get("listRrCiudadesSelected");
        List<String> listSegmento = (List<String>) params.get("listSegmentoSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");
        String codigoProyecto = (String) params.get("codProyecto");
        List<BigDecimal> estadosList = (List<BigDecimal>) params.get("listEstadosOtCmDirSelected");
        

        // fecha de Creacion orden OT ,
        if (params.get("filtroFechas").equals(Constant.FECHA_CREACION_OT)) {
            if (params.get("fechaInicioOt") != null && (params.get("fechaFinOt") != null)) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND ot.fechaCreacion BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND  func('trunc', ot.fechaCreacion) = :fechaInicioOt");
                    }
                }
            }
        }
        // fecha creacion ot hija onyx FECHA_CREACION_OT_HIJA_ONYX
        if (params.get("filtroFechas").equals(Constant.FECHA_CREACION_OT_HIJA_ONYX)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', oyx.fecha_Creacion_Ot_Hija_Onyx) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', oyx.fecha_Creacion_Ot_Hija_Onyx) = :fechaInicioOt");
                    }
                }

            }
        }
        // fecha creacion onyx
        if (params.get("filtroFechas").equals(Constant.FECHA_CREACION_ONYX)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', oyx.fechaCreacion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', oyx.fechaCreacion) = :fechaInicioOt");
                    }
                }

            }
        }
        // fecha de agendamiento OFSC FECHA_AGENDA
        if (params.get("filtroFechas").equals(Constant.FECHA_AGENDACMIENTO_OFSC)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', ag.fechaAgenda) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag.fechaAgenda) = :fechaInicioOt");
                    }
                }
            }
        }
        //fecha asignacion del tecnico FECHA_INICIO_VT
        if (params.get("filtroFechas").equals(Constant.FECHA_ASIGNACION_TECNICO_OFSC)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', ag.fechaAsigTecnico) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag.fechaAsigTecnico) = :fechaInicioOt");
                    }
                }

            }
        }
        //fecha cierre de agenda FECHA_FIN_VT
        if (params.get("filtroFechas").equals(Constant.FECHA_CIERRE_AGENDA_OFSC)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', ag.fechaFinVt) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag.fechaFinVt) = :fechaInicioOt");
                    }
                }
            }
        }

        //fecha cancelacion de agenda, estado cancelada id=22998 
        if (params.get("filtroFechas").equals(Constant.FECHA_CANCELACION_AGENDA_OFSC)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', ag.fechaEdicion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag.fechaEdicion) = :fechaInicioOt");
                    }
                }
                CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
                CmtBasicaMgl agendaCancelada = null;
                try {
                    agendaCancelada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_CANCELADA);
                } catch (ApplicationException ex) {
                    String msgError = "Error al generar reporte ordenes trabajo: " + ex.getMessage();
                    LOGGER.error(msgError, ex);
                }
                if (agendaCancelada != null) {
                    sql.append(" AND  ag.basicaIdEstadoAgenda.basicaId  = ").append(agendaCancelada.getBasicaId()).append(" AND  ag.estadoRegistro = 1");
                }

            }
        }
        //fecha de reagendamiento ???? 
        if (params.get("filtroFechas").equals(Constant.FECHA_REAGENDAMIENTO_OFSC)) {
            if ((params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty())) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy ");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc',  ag.fechaReagenda) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag.fechaReagenda) = :fechaInicioOt");
                    }
                }
           
            CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
            CmtBasicaMgl agendaReagendada = null;
            try {
                agendaReagendada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_REAGENDADA);
            } catch (ApplicationException ex) {
                String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
                LOGGER.error(msgError, ex);
            }
            if (agendaReagendada != null) {
               sql.append(" AND  ag.basicaIdEstadoAgenda.basicaId  = ").append(agendaReagendada.getBasicaId()).append(" AND  ag.estadoRegistro = 1");
            }


            }
        }
        //fecha de suspencion
        if (params.get("filtroFechas").equals(Constant.FECHA_SUSPENSION_OFSC)) {
            if ((params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty())) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc',  ag.fechaSuspendeVt) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag.fechaSuspendeVt) = :fechaInicioOt");
                    }
                }

            }
        }
        // numero de OT
        if (params.get("otIni") != null && (params.get("otFin") != null)) {
            sql.append(" and ot.otHhppMgl.otHhppId BETWEEN :otIni and  :otFin ");
        }
        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            sql.append("  AND  ot.tipoOtHhppId.tipoOtId  IN :tipoOrdenList  ");
        }
        if (subtipoOrdenList != null && !subtipoOrdenList.isEmpty()) {
            sql.append("  AND  ot.tipoOtHhppId.tipoTrabajoOFSC.basicaId IN :subtipoOrdenList");
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            sql.append("  AND  ag.subTipoWorkFoce  IN :subOrdenList  ");
        }
        if (tipoOrdenListOSFC != null && !tipoOrdenListOSFC.isEmpty()) {
            sql.append("  AND  ot.tipoOtHhppId.tipoTrabajoOFSC.basicaId  IN :tipoOrdenListOSFC  ");
        }
        // estado interno
        if (estadosList != null && !estadosList.isEmpty()) {
            sql.append("  AND  ot.estadoInternoInicial.basicaId  IN :estadosList ");
        }
        // estado
        if (listEstadosSelected != null && !listEstadosSelected.isEmpty()) {
            sql.append("  AND  ot.estadoGeneral.basicaId  IN :listEstadosSelected ");
        }
        //CAMPOS NUEVOS 
        // regional MER 
        if (regionalList != null && !regionalList.isEmpty()) {
            sql.append("  AND  t.nodo.comId.regionalRr.codigoRr  IN :regionalList  ");
        }
        // ciudad MER .nodo.comId.codigoRr
        if (ciudadList != null && !ciudadList.isEmpty()) {
            sql.append("  AND  t.nodo.comId.codigoRr  IN :ciudadList  ");
        }
        // segmento 
        if (listSegmento != null && !listSegmento.isEmpty()) {
            sql.append("  AND oyx.segmento_Onyx  IN :listSegmento ");

        }

        // codigo de proyecto
        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            sql.append("  AND  oyx.codigoProyecto  = :codigoProyecto");

        }
        // Nit Cliente
        if (params.get("nitCliente") != null && !params.get("nitCliente").toString().isEmpty()) {
            sql.append("  AND oyx.nit_Cliente_Onyx  like :nitCliente");

        }
        // numero Ot Padre
        if (params.get("numOtOnyxPadre") != null && !params.get("numOtOnyxPadre").toString().isEmpty()) {
            sql.append("  AND  (oyx.Onyx_Ot_Padre_Cm  = :numOtOnyxPadre or oy.Onyx_Ot_Padre_Dir  = :numOtOnyxPadre )");

        }
        // numero Ot Hija
        if (params.get("numeroOtOnyxHija") != null && !params.get("numeroOtOnyxHija").toString().isEmpty()) {
            sql.append("  AND  (oyx.Onyx_Ot_Hija_Dir  = :numeroOtOnyxHija or oy.Onyx_Ot_Hija_Cm  = :numeroOtOnyxHija )");

        }
        // nombre del cliente
        if (params.get("nombreCliente") != null && !params.get("nombreCliente").toString().isEmpty()) {
            sql.append("  AND  UPPER(oyx.nombre_Cliente_Onyx)  like UPPER(:nombreCliente)");

        }

        // Tipo de solucion
        if (listaSolucion != null && !listaSolucion.isEmpty()) {
            sql.append("  AND  oyx.tipo_Servicio_Onyx   = :listaSolucion ");

        }
  
        

    }


    
     /**
     * M&eacute;todo para adicionar los par&aacute;metros de b&uacute;squeda en
     * en el reporte de ordenes de Cm
     *
     * @param q {@link Query} objeto de jpa para realizar la consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     */
    private void agregarParametrosDIR(Query q, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<BigDecimal> subtipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOSFC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<BigDecimal> listEstadosSelected = (List<BigDecimal>) params.get("listEstadosSelected");
        List<String> regionalList = (List<String>) params.get("listRegionalSelected");
        List<String> ciudadList = (List<String>) params.get("listRrCiudadesSelected");
        String valor = (String) params.get("valor");
        String codigoProyecto = (String) params.get("codProyecto");
        String nitCliente = (String) params.get("nitCliente");
        String numOtOnyxPadre = (String) params.get("numOtOnyxPadre");
        String numeroOtOnyxHija = (String) params.get("numeroOtOnyxHija");
        String nombreCliente = (String) params.get("nombreCliente");
        List<String> listSegmento = (List<String>) params.get("listSegmentoSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");
        List<BigDecimal> estadosList = (List<BigDecimal>) params.get("listEstadosOtCmDirSelected");
        
        
        
        
        //Direcciones
        // fecha de Creacion orden OT ,
        if (params.get("filtroFechas") != null && !params.get("filtroFechas").equals("")) {
            if (params.get("fechaInicioOt") != null && params.get("fechaFinOt") != null) {
                if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                    q.setParameter("fechaInicioOt", (Date) params.get("fechaInicioOt"));
                    q.setParameter("fechaFinOt", (Date) params.get("fechaFinOt"));
                } else {
                    q.setParameter("fechaInicioOt", (Date) params.get("fechaInicioOt"));
                }

            }
        }

        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            q.setParameter("tipoOrdenList", (tipoOrdenList.isEmpty()) ? null : tipoOrdenList);
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            q.setParameter("subOrdenList", (subOrdenList.isEmpty()) ? null : subOrdenList);
        }
        if (subtipoOrdenList != null && !subtipoOrdenList.isEmpty()) {
            q.setParameter("subtipoOrdenList", (subtipoOrdenList.isEmpty()) ? null : subtipoOrdenList);
        }
        if (listEstadosSelected != null && !listEstadosSelected.isEmpty()) {
            q.setParameter("listEstadosSelected", (listEstadosSelected.isEmpty()) ? null : listEstadosSelected);
        }
        if (tipoOrdenListOSFC != null && !tipoOrdenListOSFC.isEmpty()) {
            q.setParameter("tipoOrdenListOSFC", (tipoOrdenListOSFC.isEmpty()) ? null : tipoOrdenListOSFC);
        }
        if (estadosList != null && !estadosList.isEmpty()) {
            q.setParameter("estadosList", (estadosList.isEmpty()) ? null : estadosList);
        }

        if ((params.get("otIni") != null)) {
            q.setParameter("otIni",
                    (null == params.get("otIni")) ? null
                    : params.get("otIni"));
        }
        if ((params.get("otFin") != null)) {
            q.setParameter("otFin",
                    (null == params.get("otFin")) ? null
                    : params.get("otFin"));
        }
        // regional MER
        if (regionalList != null && !regionalList.isEmpty()) {
            q.setParameter("regionalList", (regionalList.isEmpty()) ? null : regionalList);
        }
        // ciudad MER
        if (ciudadList != null && !ciudadList.isEmpty()) {
            q.setParameter("ciudadList", (ciudadList.isEmpty()) ? null : ciudadList);
        }
        // Segmento
        if (listSegmento != null && !listSegmento.isEmpty()) {
            q.setParameter("listSegmento", (listSegmento.isEmpty()) ? null : listSegmento);
        }
        // Tipo Solucion
        if (listaSolucion != null && !listaSolucion.equals("")) {
            q.setParameter("listaSolucion", listaSolucion.equals("") ? "" : listaSolucion);
        }

        // codigo de proyecto
        if (codigoProyecto != null && !codigoProyecto.isEmpty()) {
            q.setParameter("codigoProyecto", valor.equals("") ? "" : valor);

        }
        // Nit Cliente
        if (nitCliente != null && !nitCliente.isEmpty()) {
            q.setParameter("nitCliente", valor.equals("") ? "" : valor);

        }
        // numero Ot Padre
        if (numOtOnyxPadre != null && !numOtOnyxPadre.isEmpty()) {
            q.setParameter("numOtOnyxPadre", valor.equals("") ? "" : valor);
        }
        // numero Ot Hija
        if (numeroOtOnyxHija != null && !numeroOtOnyxHija.isEmpty()) {
            q.setParameter("numeroOtOnyxHija", (valor.equals("")) ? "" : valor);
        }
        // nombre del cliente
        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            q.setParameter("nombreCliente", (valor.equals("")) ? "" : "%" + valor.toUpperCase() + "%");
        }
    }
       public int getCountgetReporteHistoricoOtDIR(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            StringBuilder sql2 = new StringBuilder();
            int resultCm;
            int resultDir;
            int total;
           
            // CCMM
            sql.append("SELECT distinct(count(1)) "
                + "FROM CmtOrdenTrabajoMgl o  "
                + "left join o.listAgendas a "
                + "left join o.listOnyx oy "
                + "left join o.listAuditoria ot_aud "
                + "left join a.listAuditoria  ag_aud "
                + "left join o.listOnyxAuditoria onyx_aud "
                + "WHERE (o.estadoRegistro is null or o.estadoRegistro = 1 )"
                + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");
             //DIRECCIONES  
               sql2.append("SELECT distinct(count(1)) "
                + "FROM OtHhppMgl o  "
                + "left join o.listAgendaDireccion a "
                + "left join o.listOnyx oy "
                + "left join o.listOtHhppMglAuditoria ot "
                + "left join a.listAgendaDireccionAuditoria ag   "
                + "left join o.listOnyxOtCmDirAuditoria oyx "
                + "left join o.tecnologiaBasicaList t "
                + "WHERE (o.estadoRegistro is null or o.estadoRegistro = 1 )"
                + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");
              
            generarSelectReporteHistoricoOtCm(sql, params, true);
            Query q = entityManager.createQuery(sql.toString());
            agregarParametrosHistorico(q, params, true);
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            resultCm = q.getSingleResult() == null ? 0 : ((Long) q.getSingleResult()).intValue();
            //DIRECCIONES  
            generarSelectReporteHistoricoOtDIR(sql2, params, true);
            Query q2 = entityManager.createQuery(sql2.toString());
            agregarParametrosDIR(q2, params, true);
            q2.setFirstResult(firstResult);
            q2.setMaxResults(maxResults);
            resultDir = q2.getSingleResult() == null ? 0 : ((Long) q2.getSingleResult()).intValue();
            total = resultCm + resultDir;
            getEntityManager().clear();
            return total;

        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        }
       }


    public List<ReporteHistoricoOtDIRDto> getReporteHistoricoOtDIR(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario, List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        List<ReporteHistoricoOtDIRDto> listReporteHistoricoOtDIRDto = new ArrayList<>();
        StringBuilder sql1 = new StringBuilder();
        StringBuilder sql2 = new StringBuilder();

        List<Object[]> listaHistOrdenesCm;
        List<Object[]> listaHistOrdenesDir;
        try {
            int primerResult = (Integer) params.get("inicioRegistros");
            int finalResult = (Integer) params.get("expLonPag");
            //cuentas matrices
            sql1.append("SELECT distinct o , ot_aud, a ,ag_aud, oy ,onyx_aud  "
                    + "FROM CmtOrdenTrabajoMgl o  "
                    + "left join o.listAgendas a "
                    + "left join o.listOnyx oy "
                    + "left join o.listAuditoria ot_aud "
                    + "left join a.listAuditoria  ag_aud "
                    + "left join o.listOnyxAuditoria onyx_aud "
                    + "WHERE (o.estadoRegistro is null or o.estadoRegistro = 1 )"
                    + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");

            generarSelectReporteHistoricoOtCm(sql1, params, true);
            Query q1 = entityManager.createQuery(sql1.toString());
            agregarParametrosHistorico(q1, params, true);
            q1.setFirstResult(primerResult);
            q1.setMaxResults(finalResult);
            listaHistOrdenesCm = (List<Object[]>) q1.getResultList();
            //direcciones
            sql2.append("SELECT distinct o, a,oy,ot, ag, oyx, t "
                    + "FROM OtHhppMgl o  "
                    + "left join o.listAgendaDireccion a "
                    + "left join o.listOnyx oy "
                    + "left join o.listOtHhppMglAuditoria ot "
                    + "left join a.listAgendaDireccionAuditoria ag   "
                    + "left join o.listOnyxOtCmDirAuditoria oyx "
                    + "left join o.tecnologiaBasicaList t "
                    + "WHERE (o.estadoRegistro is null or o.estadoRegistro = 1 )"
                    + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");

            generarSelectReporteHistoricoOtDIR(sql2, params, true);
            Query q2 = entityManager.createQuery(sql2.toString());
            agregarParametrosDIR(q2, params, true);
            q2.setFirstResult(primerResult);
            q2.setMaxResults(finalResult);
            listaHistOrdenesDir = (List<Object[]>) q2.getResultList();

            cargarListaHistOrdenesCM(listaHistOrdenesCm, listaBasicaCm, listaEstadosIntExt, listReporteHistoricoOtDIRDto, listacmtRegionalMgl, listacmtComunidadRr);
            cargarListaHistOrdenesDir(listaHistOrdenesDir, listaBasicaDir, listReporteHistoricoOtDIRDto);
            getEntityManager().clear();
            return listReporteHistoricoOtDIRDto;
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
     
     
     
    public void cargarListaHistOrdenesCM(List<Object[]> listaOrdenesHistoricoAgendas,
            List<CmtBasicaMgl> listaBasica, List<CmtEstadoIntxExtMgl> listaEstadosIntExt,
            List<ReporteHistoricoOtDIRDto> listaReporteHistoricoOtDIRDto, List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr) throws ApplicationException {

        try {

            if (listaOrdenesHistoricoAgendas != null && !listaOrdenesHistoricoAgendas.isEmpty()) {
             
                for (Object[] orden : listaOrdenesHistoricoAgendas) {                 

                    ReporteHistoricoOtDIRDto reporteHistoricoOtDIRDto = new ReporteHistoricoOtDIRDto();
                    // ordenes de cm
                    //ordenes
                    reporteHistoricoOtDIRDto.setOt_Id_Cm(((CmtOrdenTrabajoMgl) orden[0]).getIdOt().toString());
                    reporteHistoricoOtDIRDto.setTipo_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getTipoOtObj() != null
                            ? ((CmtOrdenTrabajoMgl) orden[0]).getTipoOtObj().getDescTipoOt() : "");
                    reporteHistoricoOtDIRDto.setSub_tipo_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTipoTrabajo() != null
                            ? ((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTipoTrabajo().getNombreBasica() : "");
                    reporteHistoricoOtDIRDto.setEstado_interno_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj() != null
                            ? ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj().getNombreBasica() : "");
                    // estado interno externo
                    if (listaEstadosIntExt != null && ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj() != null) {
                        for (CmtEstadoIntxExtMgl estado : listaEstadosIntExt) {
                            if (estado.getIdEstadoInt() != null && estado.getIdEstadoInt().getBasicaId() != null
                                    && ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj() != null && ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj().getBasicaId() != null) {
                                
                                if (((CmtOrdenTrabajoMgl) orden[0]).getEstadoGeneralOt() != null) {
                                    if (estado.getIdEstadoExt().getBasicaId().compareTo(((CmtOrdenTrabajoMgl) orden[0]).getEstadoGeneralOt()) == 0) {
                                        reporteHistoricoOtDIRDto.setEstadoOt(estado.getIdEstadoExt().getNombreBasica());
                                    }
                                }
                            }
                        }
                    }
                    SimpleDateFormat fecha_creacion_OT_MER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaCreacion;
                    if (((CmtOrdenTrabajoMgl) orden[0]) != null && ((CmtOrdenTrabajoMgl) orden[0]).getFechaCreacion() != null) {
                        fechaCreacion = fecha_creacion_OT_MER.format(((CmtOrdenTrabajoMgl) orden[0]).getFechaCreacion());
                        reporteHistoricoOtDIRDto.setFecha_creacion_OT_MER(fechaCreacion);
                    }
                    
                    reporteHistoricoOtDIRDto.setSegmento_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getSegmento() != null
                            ? ((CmtOrdenTrabajoMgl) orden[0]).getSegmento().getNombreBasica() : "");
                    reporteHistoricoOtDIRDto.setTecnologia_OT_MGL(((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTecnologia() != null
                            ? ((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTecnologia().getNombreBasica() : "");
                    reporteHistoricoOtDIRDto.setCmObj(((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null
                            ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getCuentaId().substring(7) : "");
                    if (((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal() != null
                            && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal().getDireccionObj() != null) {
                        reporteHistoricoOtDIRDto.setDireccionMer(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac());
                    }
                    reporteHistoricoOtDIRDto.setNombreCM(((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null
                            ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNombreCuenta() : "");
                    reporteHistoricoOtDIRDto.setCodigoCMR(((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null
                            ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNumeroCuenta().toString() : "");
                    reporteHistoricoOtDIRDto.setUsuario_Creacion_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getUsuarioCreacionId() != null
                            ? ((CmtOrdenTrabajoMgl) orden[0]).getUsuarioCreacionId().toString() : "");
                    reporteHistoricoOtDIRDto.setOnyx_Ot_Hija_Cm(((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija() != null
                            ? ((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija().toString() : "");

                    String complejidadDescripcion;
                    if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("A")) {
                        complejidadDescripcion = "ALTA";
                    } else if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("M")) {
                        complejidadDescripcion = "MEDIA";
                    } else if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("B")) {
                        complejidadDescripcion = "BAJA";
                    } else {
                        complejidadDescripcion = "";
                    }
                    reporteHistoricoOtDIRDto.setComplejidadServicio(complejidadDescripcion);
                    if (((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento() != null) {
                        reporteHistoricoOtDIRDto.setDepartamento(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento().getGpoNombre());
                    }
                    reporteHistoricoOtDIRDto.setUsuarioModOt(((CmtOrdenTrabajoMgl) orden[0]).getUsuarioEdicion());
                    reporteHistoricoOtDIRDto.setPersonaAtiendeSitio(((CmtOrdenTrabajoMgl) orden[0]).getPersonaRecVt());
                    reporteHistoricoOtDIRDto.setTelefonoAtiendeSitio(((CmtOrdenTrabajoMgl) orden[0]).getTelPerRecVt());

                    if (listacmtRegionalMgl != null && !listacmtRegionalMgl.isEmpty()) {
                        for (CmtRegionalRr regional : listacmtRegionalMgl) {
                            if (regional.getCodigoRr() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null) {
                                if (regional.getCodigoRr().equals(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDivision())) {
                                    reporteHistoricoOtDIRDto.setRegional(regional.getNombreRegional() + " " + "("
                                            + ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDivision() + ")");
                                }
                            }
                        }
                    }
                    if (listacmtComunidadRr != null && !listacmtComunidadRr.isEmpty()) {
                        for (CmtComunidadRr com : listacmtComunidadRr) {
                            if (com.getCodigoRr() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null) {
                                if (com.getCodigoRr().equals(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getComunidad())) {
                                    reporteHistoricoOtDIRDto.setCiudad(com.getNombreComunidad() + " " + "("
                                            + ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getComunidad() + ")");
                                }
                            }

                        }
                    }

                    // ot auditoria
                    if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]) != null) {
                        reporteHistoricoOtDIRDto.setSub_tipo_OT_MER_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getBasicaIdTipoTrabajo() == null ? "" : ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getBasicaIdTipoTrabajo().getNombreBasica());
                        reporteHistoricoOtDIRDto.setSegmento_OT_MER_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getSegmento().getNombreBasica());
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Hija_Cm_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getOnyxOtHija() == null ? "" : ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getOnyxOtHija().toString());

                        
                        SimpleDateFormat fecha_modificacion_OT_Aud = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                        String fecha_modificacion_OT_Aud_MER;
                        if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]) != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaEdicion() != null) {
                            fecha_modificacion_OT_Aud_MER = fecha_modificacion_OT_Aud.format(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaEdicion());
                            reporteHistoricoOtDIRDto.setFecha_Modificacion_Ot(fecha_modificacion_OT_Aud_MER);
                        }

                        
                        SimpleDateFormat fecha_creacion_OT_MER_Aud = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        String fechaCreacion_Mer_Aud;
                        if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]) != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaCreacion() != null) {
                            fechaCreacion_Mer_Aud = fecha_creacion_OT_MER_Aud.format(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaCreacion());
                            reporteHistoricoOtDIRDto.setFecha_Creacion_OT_MER_Aud(fechaCreacion_Mer_Aud);
                        }

                        reporteHistoricoOtDIRDto.setEstado_interno_OT_MER_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]) == null ? "" : ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getEstadoInternoObj().getNombreBasica());
                        String complejidadDescripcionAud;
                        if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio() != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio().equals("A")) {
                            complejidadDescripcionAud = "ALTA";
                        } else if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio() != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio().equals("M")) {
                            complejidadDescripcionAud = "MEDIA";
                        } else if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio() != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio().equals("B")) {
                            complejidadDescripcionAud = "BAJA";
                        } else {
                            complejidadDescripcionAud = "";
                        }
                        reporteHistoricoOtDIRDto.setComplejidadServicioAud(complejidadDescripcionAud);
                        reporteHistoricoOtDIRDto.setUsuarioModOtAud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getUsuarioEdicion() != null ? ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getUsuarioEdicion() : "");
                        // estado interno externo
                        if (listaEstadosIntExt != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getEstadoGeneralOt() != null) {
                            for (CmtEstadoIntxExtMgl estado : listaEstadosIntExt) {
                                if (estado.getIdEstadoInt() != null && estado.getIdEstadoInt().getBasicaId() != null && estado.getIdEstadoExt() != null
                                        && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getEstadoGeneralOt() != null && ((CmtOrdenTrabajoMgl) orden[0]).getEstadoGeneralOt() != null) {
                                    if (estado.getIdEstadoExt().getBasicaId().compareTo(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getEstadoGeneralOt()) == 0) {
                                        reporteHistoricoOtDIRDto.setEstadoOt_Aud(estado.getIdEstadoExt().getNombreBasica());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    //agendamiento
                    if (((CmtAgendamientoMgl) orden[2]) != null) {
                        reporteHistoricoOtDIRDto.setOrden_RR(((CmtAgendamientoMgl) orden[2]) != null
                                ? ((CmtAgendamientoMgl) orden[2]).getIdOtenrr() : "");
                        //sub tipo ordenes ofsc
                        if (listaBasica != null && listaBasica.isEmpty()) {
                            for (CmtBasicaMgl cmtBasicaMgl : listaBasica) {
                                if (((CmtAgendamientoMgl) orden[2]).getSubTipoWorkFoce() != null && cmtBasicaMgl.getCodigoBasica() != null) {
                                    if (((CmtAgendamientoMgl) orden[2]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                                        reporteHistoricoOtDIRDto.setSubTipo_Orden_OFSC(((CmtAgendamientoMgl) orden[2]).getSubTipoWorkFoce().trim());
                                        break;
                                    }
                                }
                            }
                        }
                        String fechaAgenda;
                        if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaAgenda() != null) {
                            String timeS = ((CmtAgendamientoMgl) orden[2]) != null ? ((CmtAgendamientoMgl) orden[2]).getTimeSlot() : "";
                            fechaAgenda = DateUtils.setTimeSlotFechaAgenda(((CmtAgendamientoMgl) orden[2]).getFechaAgenda(), timeS);
                            reporteHistoricoOtDIRDto.setFecha_agenda_OFSC(fechaAgenda);
                        }
                        reporteHistoricoOtDIRDto.setUsuario_creacion_agenda_OFSC((((CmtAgendamientoMgl) orden[2]) != null
                                ? ((CmtAgendamientoMgl) orden[2]).getUsuarioCreacion() : ""));
                        String timeS = ((CmtAgendamientoMgl) orden[2]) != null ? ((CmtAgendamientoMgl) orden[2]).getTimeSlot() : "";
                        if (timeS != null && !timeS.equals("")) {
                            if (timeS.contains("Durante")) {
                                reporteHistoricoOtDIRDto.setTime_slot_OFSC(timeS);
                            } else {
                                timeS = timeS.replace("-", "_");
                                reporteHistoricoOtDIRDto.setTime_slot_OFSC(timeS);
                            }
                        }
                        reporteHistoricoOtDIRDto.setAppt_number_OFSC(((CmtAgendamientoMgl) orden[2]) != null
                                ? ((CmtAgendamientoMgl) orden[2]).getOfpsOtId() : "");
                        reporteHistoricoOtDIRDto.setEstado_agenda_OFSC(((CmtAgendamientoMgl) orden[2]) != null
                                ? ((CmtAgendamientoMgl) orden[2]).getBasicaIdEstadoAgenda().getNombreBasica() : "");
                        SimpleDateFormat fecha_inicia_agenda = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                        String fechaInivioVt;
                        if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaInivioVt() != null) {
                            fechaInivioVt = fecha_inicia_agenda.format(((CmtAgendamientoMgl) orden[2]).getFechaInivioVt());
                            reporteHistoricoOtDIRDto.setFecha_inicia_agenda_OFSC(fechaInivioVt);
                        }

                        SimpleDateFormat fecha_fin_agenda = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                        String fechaFinVt;
                        if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaFinVt() != null) {
                            fechaFinVt = fecha_fin_agenda.format(((CmtAgendamientoMgl) orden[2]).getFechaFinVt());
                            reporteHistoricoOtDIRDto.setFecha_fin_agenda_OFSC(fechaFinVt);
                        }

                        reporteHistoricoOtDIRDto.setId_aliado_OFSC((((CmtAgendamientoMgl) orden[2]) != null
                                ? ((CmtAgendamientoMgl) orden[2]).getIdentificacionAliado() : ""));
                        reporteHistoricoOtDIRDto.setNombre_aliado_OFSC(((CmtAgendamientoMgl) orden[2]) != null
                                ? ((CmtAgendamientoMgl) orden[2]).getNombreAliado() : "");
                        reporteHistoricoOtDIRDto.setId_tecnico_aliado_OFSC((((CmtAgendamientoMgl) orden[2]) != null
                                ? ((CmtAgendamientoMgl) orden[2]).getIdentificacionTecnico() : ""));
                        reporteHistoricoOtDIRDto.setNombre_tecnico_aliado_OFSC((((CmtAgendamientoMgl) orden[2]) != null
                                ? ((CmtAgendamientoMgl) orden[2]).getNombreTecnico() : ""));
                        reporteHistoricoOtDIRDto.setUltima_agenda_multiagenda((((CmtAgendamientoMgl) orden[2]) != null
                                ? ((CmtAgendamientoMgl) orden[2]).getUltimaAgenda() : ""));
                        // observaciones 
                        String obs = (((CmtAgendamientoMgl) orden[2]) != null
                                ? ((CmtAgendamientoMgl) orden[2]).getObservacionesTecnico() : "");
                        if (obs != null && !obs.equals("")) {
                            obs = StringUtils.caracteresEspeciales(obs);
                            reporteHistoricoOtDIRDto.setObservaciones_tecnico_OFSC(obs);
                        }

                        SimpleDateFormat fechaAsigTecnico = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                        String fechaTecnico;
                        if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaAsigTecnico() != null) {
                            fechaTecnico = fechaAsigTecnico.format(((CmtAgendamientoMgl) orden[2]).getFechaAsigTecnico());
                            reporteHistoricoOtDIRDto.setFechaAsigTecnico(fechaTecnico);
                        }

                        reporteHistoricoOtDIRDto.setUsuarioModAgenda(((CmtAgendamientoMgl) orden[2]).getUsuarioEdicion());
                        // indicador de cumplimiento
                        String cumplimiento = "No se cumplio";
                        if (((OnyxOtCmDir) orden[4]) != null && ((OnyxOtCmDir) orden[4]).getFecha_Compromiso_Ot_Padre_Onyx() != null
                                && ((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaAgenda() != null) {
                            if (((CmtAgendamientoMgl) orden[2]).getUltimaAgenda() != null && ((CmtAgendamientoMgl) orden[2]).getUltimaAgenda().equals("Y")) {
                                if (((CmtAgendamientoMgl) orden[2]).getFechaAgenda().before(((OnyxOtCmDir) orden[4]).getFecha_Compromiso_Ot_Padre_Onyx())) {
                                    cumplimiento = "Si se cumplió";
                                }
                            }
                            reporteHistoricoOtDIRDto.setIndicadorCumplimiento(cumplimiento);
                        }
                        //resultado agenda
                        reporteHistoricoOtDIRDto.setResultadoOrden((((CmtAgendamientoMgl) orden[2]) != null
                                && ((CmtAgendamientoMgl) orden[2]).getBasicaIdrazones() != null
                                ? ((CmtAgendamientoMgl) orden[2]).getBasicaIdrazones().getNombreBasica() : ""));

                        //cantidad reagenda 
                        if (((CmtAgendamientoMgl) orden[2]).getFechaReagenda() != null
                                && ((CmtAgendamientoMgl) orden[2]).getRazonReagenda() != null) {
                            reporteHistoricoOtDIRDto.setCantReagenda(((CmtAgendamientoMgl) orden[2]).getCantAgendas());
                        }
                        //Motivos 
                        if (((CmtAgendamientoMgl) orden[2]).getMotivosReagenda() != null) {
                            reporteHistoricoOtDIRDto.setMotivosReagenda(((CmtAgendamientoMgl) orden[2]).getMotivosReagenda());
                        }

                        //tiempo ejecucion
                        if (((CmtAgendamientoMgl) orden[2]).getFechaInivioVt() != null && ((CmtAgendamientoMgl) orden[2]).getFechaFinVt() != null) {
                            String tiempoEjecucion = DateUtils.getHoraMinEntreFechasConFormato(((CmtAgendamientoMgl) orden[2]).getFechaInivioVt(), ((CmtAgendamientoMgl) orden[2]).getFechaFinVt());
                            reporteHistoricoOtDIRDto.setTiempoEjecucion(tiempoEjecucion);
                        }
                        //conveniencia
                        if (((CmtAgendamientoMgl) orden[2]).getConveniencia() != null) {
                            reporteHistoricoOtDIRDto.setConveniencia(((CmtAgendamientoMgl) orden[2]).getConveniencia());
                        }

                    }
                    // Auditoria Agendamiento
                    if (((CmtAgendaAuditoria) orden[3]) != null) {
                        //sub tipo ordenes ofscbasicaSubTipoOrdenes
                        if (listaBasica != null && listaBasica.isEmpty()) {
                            for (CmtBasicaMgl cmtBasicaMgl : listaBasica) {
                                if (((CmtAgendaAuditoria) orden[3]).getSubTipoWorkFoce() != null && cmtBasicaMgl.getCodigoBasica() != null) {
                                    if (((CmtAgendaAuditoria) orden[3]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                                        reporteHistoricoOtDIRDto.setSubTipo_Orden_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getSubTipoWorkFoce().trim());
                                        break;
                                    }
                                }
                            }
                        }

                        reporteHistoricoOtDIRDto.setEstado_agenda_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getBasicaIdEstadoAgenda().getNombreBasica() != null
                                ? ((CmtAgendaAuditoria) orden[3]).getBasicaIdEstadoAgenda().getNombreBasica() : "");
                        reporteHistoricoOtDIRDto.setId_aliado_OFSC_Aud((((CmtAgendaAuditoria) orden[3]).getIdentificacionAliado() != null
                                ? ((CmtAgendaAuditoria) orden[3]).getIdentificacionAliado() : ""));
                        reporteHistoricoOtDIRDto.setNombre_aliado_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getNombreAliado() != null
                                ? ((CmtAgendaAuditoria) orden[3]).getNombreAliado() : "");
                        reporteHistoricoOtDIRDto.setId_tecnico_aliado_OFSC_Aud((((CmtAgendaAuditoria) orden[3]).getIdentificacionTecnico() != null
                                ? ((CmtAgendaAuditoria) orden[3]).getIdentificacionTecnico() : ""));
                        reporteHistoricoOtDIRDto.setNombre_tecnico_aliado_OFSC_Aud((((CmtAgendaAuditoria) orden[3]).getNombreTecnico() != null
                                ? ((CmtAgendaAuditoria) orden[3]).getNombreTecnico() : ""));
                        reporteHistoricoOtDIRDto.setUsuarioModAgendaAud(((CmtAgendaAuditoria) orden[3]).getUsuarioEdicion());

                    }
                    // onyx
                    if (((OnyxOtCmDir) orden[4]) != null) {

                        reporteHistoricoOtDIRDto.setNit_Cliente_Onyx((((OnyxOtCmDir) orden[4]).getNit_Cliente_Onyx() != null
                                ? ((OnyxOtCmDir) orden[4]).getNit_Cliente_Onyx() : ""));
                        // nombreCiente
                        String nombreCienteOnyx = ((OnyxOtCmDir) orden[4]).getNombre_Cliente_Onyx();
                        if (nombreCienteOnyx != null && !nombreCienteOnyx.equals("")) {
                            nombreCienteOnyx = StringUtils.caracteresEspeciales(nombreCienteOnyx);
                            reporteHistoricoOtDIRDto.setNombre_Cliente_Onyx(nombreCienteOnyx);
                        }

                        // nombreOtHija 
                        String nombreOtHija = ((OnyxOtCmDir) orden[4]).getNombre_Ot_Hija_Onyx();
                        if (nombreOtHija != null && !nombreOtHija.equals("")) {
                            nombreOtHija = StringUtils.caracteresEspeciales(nombreOtHija);
                            reporteHistoricoOtDIRDto.setNombre_Ot_Hija_Onyx(nombreOtHija);
                        }

                        //ot hija direcciones
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Padre_Cm(((OnyxOtCmDir) orden[4]).getOnyx_Ot_Padre_Cm() != null
                                ? ((OnyxOtCmDir) orden[4]).getOnyx_Ot_Padre_Cm() : "");
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Hija_Cm(((OnyxOtCmDir) orden[4]).getOnyx_Ot_Hija_Cm() != null
                                ? ((OnyxOtCmDir) orden[4]).getOnyx_Ot_Hija_Cm() : "");
                        SimpleDateFormat creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String fecha_Creacion_Ot_Hija_Onyx;
                        if (((OnyxOtCmDir) orden[4]) != null && ((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                            fecha_Creacion_Ot_Hija_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx());
                            reporteHistoricoOtDIRDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx);
                        }

                        // fecha creacion onyx
                        String fecha_Creacion_Ot_Onyx = null;
                        if (((OnyxOtCmDir) orden[4]) != null && ((OnyxOtCmDir) orden[4]).getFechaCreacion() != null) {
                            fecha_Creacion_Ot_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[4]).getFechaCreacion());
                            reporteHistoricoOtDIRDto.setFechaCreacionOnyx(fecha_Creacion_Ot_Onyx);
                        }

                        reporteHistoricoOtDIRDto.setDescripcion_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getDescripcion_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setSegmento_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getSegmento_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[4]) != null
                                ? (((OnyxOtCmDir) orden[4]).getTipo_Servicio_Onyx()) : "");
                        reporteHistoricoOtDIRDto.setServicios_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getServicios_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setRecurrente_Mensual_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getRecurrente_Mensual_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setCodigo_Servicio_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getCodigo_Servicio_Onyx() : ""));
                        // vendedor_Onyx 
                        String vendedor_Onyx = ((OnyxOtCmDir) orden[4]).getVendedor_Onyx();
                        if (vendedor_Onyx != null && !vendedor_Onyx.equals("")) {
                            vendedor_Onyx = StringUtils.caracteresEspeciales(vendedor_Onyx);
                            reporteHistoricoOtDIRDto.setVendedor_Onyx(vendedor_Onyx);
                        }
                        // Telefono 
                        String telefonoVend = (((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getTelefono_Vendedor_Onyx() : "");
                        if (telefonoVend != null && !telefonoVend.equals("")) {
                            telefonoVend = StringUtils.caracteresEspeciales(telefonoVend);
                            reporteHistoricoOtDIRDto.setTelefono_Vendedor_Onyx(telefonoVend);
                        }

                        reporteHistoricoOtDIRDto.setEstado_Ot_Hija_Onyx_Cm((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getEstado_Ot_Hija_Onyx_Cm() : ""));
                        reporteHistoricoOtDIRDto.setEstado_Ot_Padre_Onyx_Cm((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getEstado_Ot_Padre_Onyx_Cm() : ""));
                        SimpleDateFormat compromiso_Ot_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String fecha_Compromiso_Ot_Padre_Onyx;
                        if (((OnyxOtCmDir) orden[4]) != null && ((OnyxOtCmDir) orden[4]).getFecha_Compromiso_Ot_Padre_Onyx() != null) {
                            fecha_Compromiso_Ot_Padre_Onyx = compromiso_Ot_Padre_Onyx.format(((OnyxOtCmDir) orden[4]).getFecha_Compromiso_Ot_Padre_Onyx());
                            reporteHistoricoOtDIRDto.setFecha_Compromiso_Ot_Padre_Onyx(fecha_Compromiso_Ot_Padre_Onyx);
                        }

                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_1_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_1_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_2_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_2_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_3_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_3_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_4_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_4_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_1_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_1_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_2_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_2_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_3_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_3_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_4_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_4_Onyx() : ""));

                        SimpleDateFormat fecha_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String fecha_Creacion_Ot_Padre_Onyx = null;
                        if (((OnyxOtCmDir) orden[4]) != null && ((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                            fecha_Creacion_Ot_Padre_Onyx = fecha_Padre_Onyx.format(((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Padre_Onyx());
                            reporteHistoricoOtDIRDto.setFecha_Creacion_Ot_Padre_Onyx(fecha_Creacion_Ot_Padre_Onyx);
                        }

                        reporteHistoricoOtDIRDto.setContacto_Tecnico_Ot_Padre_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getContacto_Tecnico_Ot_Padre_Onyx() : ""));
                        
                        // Telefono 
                        String contactoTec = (((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getContacto_Tecnico_Ot_Padre_Onyx(): "");
                        if (contactoTec != null && !contactoTec.equals("")) {
                            contactoTec = StringUtils.caracteresEspeciales(contactoTec);
                            reporteHistoricoOtDIRDto.setContacto_Tecnico_Ot_Padre_Onyx(contactoTec);
                        }
                        
                        // Telefono 
                        String telefono = (((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getTelefono_Tecnico_Ot_Padre_Onyx() : "");
                        if (telefono != null && !telefono.equals("")) {
                            telefono = StringUtils.caracteresEspeciales(telefono);
                            reporteHistoricoOtDIRDto.setTelefono_Tecnico_Ot_Padre_Onyx(telefono);
                        }

                        //CAMPOS NUEVOS OT DIRECCION
                        reporteHistoricoOtDIRDto.setCodigoProyecto((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getCodigoProyecto() : ""));
                        // direccion Onyx
                        String direccion = ((OnyxOtCmDir) orden[4]).getDireccion_Onyx();
                        if (direccion != null && !direccion.equals("")) {
                            direccion = StringUtils.caracteresEspeciales(direccion);
                            reporteHistoricoOtDIRDto.setDireccion_Onyx(direccion);
                        }

                        reporteHistoricoOtDIRDto.setContacto_Tecnico_Ot_Padre_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getContacto_Tecnico_Ot_Padre_Onyx() : ""));

                        //emailTecnico
                        String emailTecnico = ((OnyxOtCmDir) orden[4]).getEmailTec();
                        if (emailTecnico != null && !emailTecnico.equals("")) {
                            emailTecnico = StringUtils.caracteresEspeciales(emailTecnico);
                            reporteHistoricoOtDIRDto.setEmailCTec(emailTecnico);
                        }

                        reporteHistoricoOtDIRDto.setTelefono_Tecnico_Ot_Padre_Onyx((((OnyxOtCmDir) orden[4]) != null
                                ? ((OnyxOtCmDir) orden[4]).getCellTec() : ""));

                        //tiempo de programacion
                        if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaAgenda() != null
                                && ((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                            String fecha = DateUtils.getTiempoEntreFechasConFormato(((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx(),
                                    ((CmtAgendamientoMgl) orden[2]).getFechaCreacion());
                            reporteHistoricoOtDIRDto.setTiempoProgramacion(fecha);

                        }
                        //tiempo de atencion
                        if (((OnyxOtCmDir) orden[4]) != null && ((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx() != null
                                && ((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaAgenda() != null) {
                            String timeSlot = ((CmtAgendamientoMgl) orden[2]).getTimeSlot() == null ? ""
                                    : ((CmtAgendamientoMgl) orden[2]).getTimeSlot().contains("Durante") ? ""
                                    : ((CmtAgendamientoMgl) orden[2]).getTimeSlot();
                            if (timeSlot != null && !timeSlot.equals("")) {
                                if (timeSlot.contains("Durante")) {
                                    timeSlot = "00";
                                    String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((CmtAgendamientoMgl) orden[2]).getFechaAgenda(), ((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                    reporteHistoricoOtDIRDto.setTiempoAtencion(horasTiempoAtiende);
                                } else {
                                    timeSlot = ((CmtAgendamientoMgl) orden[2]).getTimeSlot().substring(0, 2);
                                    String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((CmtAgendamientoMgl) orden[2]).getFechaAgenda(), ((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                    reporteHistoricoOtDIRDto.setTiempoAtencion(horasTiempoAtiende);
                                }
                            }

                        }
                        //antiguedad de la agenda
                        if (((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                            String antiguedad = DateUtils.getTiempoEntreFechasConFormato(((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Padre_Onyx(), new Date());
                            reporteHistoricoOtDIRDto.setAntiguedadOrden(antiguedad);
                        }

                        //aliado Implementacion 
                        if (((OnyxOtCmDir) orden[4]).getaImplement() != null) {
                            reporteHistoricoOtDIRDto.setaImplement(((OnyxOtCmDir) orden[4]).getaImplement());
                        }
                        //tipo solucion
                        if (((OnyxOtCmDir) orden[4]).getTipo_Servicio_Onyx() != null) {
                            reporteHistoricoOtDIRDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[4]).getTipo_Servicio_Onyx());
                        }
                        //ciudad de facturacion
                        if (((OnyxOtCmDir) orden[4]).getCiudadFact() != null) {
                            reporteHistoricoOtDIRDto.setCiudadFact(((OnyxOtCmDir) orden[4]).getCiudadFact());
                        }

                    }
                    //auditoria onyx
                    if (((OnyxOtCmDirAuditoria) orden[5]) != null) {

                        reporteHistoricoOtDIRDto.setNit_Cliente_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]) == null ? ""
                                : ((OnyxOtCmDirAuditoria) orden[5]).getNit_Cliente_Onyx()));
                        // nombreCiente
                        String nombreCiente = ((OnyxOtCmDirAuditoria) orden[5]).getNombre_Cliente_Onyx();
                        if (nombreCiente != null && !nombreCiente.equals("")) {
                            nombreCiente = StringUtils.caracteresEspeciales(nombreCiente);
                        }
                        reporteHistoricoOtDIRDto.setNombre_Cliente_Onyx_Aud(nombreCiente);
                        // nombreOtHija 
                        String nombreOtHijaAud = ((OnyxOtCmDirAuditoria) orden[5]).getNombre_Ot_Hija_Onyx();
                        if (nombreOtHijaAud != null && !nombreOtHijaAud.equals("")) {
                            nombreOtHijaAud = StringUtils.caracteresEspeciales(nombreOtHijaAud);
                        }
                        reporteHistoricoOtDIRDto.setNombre_OT_Hija_Onyx_Aud(nombreOtHijaAud);
                        //ot onyx 
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Hija_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]) != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Hija_Cm() : ""));
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Padre_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]) != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Padre_Cm() : ""));
                        SimpleDateFormat format_Fecha_Creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                        String fecha_Creacion_Ot_Hija_Onyx_Aud;
                        if (((OnyxOtCmDirAuditoria) orden[5]) != null && (((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Hija_Onyx() != null)) {
                            fecha_Creacion_Ot_Hija_Onyx_Aud = format_Fecha_Creacion_Ot_Hija_Onyx.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Hija_Onyx());
                            reporteHistoricoOtDIRDto.setFecha_Creación_OT_Hija_Onyx_Aud(fecha_Creacion_Ot_Hija_Onyx_Aud);
                        }

                        SimpleDateFormat format_creacion_Ot_Onyx = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        String creacion_Ot_Onyx;
                        if (((OnyxOtCmDirAuditoria) orden[5]) != null && (((OnyxOtCmDirAuditoria) orden[5]).getFechaCreacion() != null)) {
                            creacion_Ot_Onyx = format_creacion_Ot_Onyx.format(((OnyxOtCmDirAuditoria) orden[5]).getFechaCreacion());
                            reporteHistoricoOtDIRDto.setFechaCreacionOnyx(creacion_Ot_Onyx);
                        }

                        reporteHistoricoOtDIRDto.setDescripcion_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getDescripcion_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getDescripcion_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setSegmento_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getSegmento_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getSegmento_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setTipo_Servicio_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getTipo_Servicio_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getTipo_Servicio_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setServicios_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getServicios_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getServicios_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setRecurrente_Mensual_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getRecurrente_Mensual_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getRecurrente_Mensual_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setCodigo_Servicio_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getCodigo_Servicio_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getCodigo_Servicio_Onyx() : ""));
                        
                         // vendedor_Onyx 
                        String vendedor_Onyx = ((OnyxOtCmDirAuditoria) orden[5]).getVendedor_Onyx();
                        if (vendedor_Onyx != null && !vendedor_Onyx.equals("")) {
                            vendedor_Onyx = StringUtils.caracteresEspeciales(vendedor_Onyx);
                            reporteHistoricoOtDIRDto.setVendedor_Onyx_Aud(vendedor_Onyx);
                        }
                        // Telefono 
                        String telefonoVend = ((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Vendedor_Onyx();
                        if (telefonoVend != null && !telefonoVend.equals("")) {
                            telefonoVend = StringUtils.caracteresEspeciales(telefonoVend);
                            reporteHistoricoOtDIRDto.setTelefono_Vendedor_Onyx_Aud(telefonoVend);

                        }

                        reporteHistoricoOtDIRDto.setEstado_Ot_Hija_Onyx_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Hija_Onyx_Cm() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Hija_Onyx_Cm() : ""));
                        reporteHistoricoOtDIRDto.setEstado_Ot_Padre_Onyx_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Padre_Onyx_Cm() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Padre_Onyx_Cm() : ""));
                        SimpleDateFormat compromiso_Ot_Padre_Onyx_Aud = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                        String fecha_Compromiso_Ot_Padre_Onyx_Aud = "";
                        if (((OnyxOtCmDirAuditoria) orden[5]) != null && (((OnyxOtCmDirAuditoria) orden[5]).getFecha_Compromiso_Ot_Padre_Onyx() != null)) {
                            fecha_Compromiso_Ot_Padre_Onyx_Aud = compromiso_Ot_Padre_Onyx_Aud.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Compromiso_Ot_Padre_Onyx());
                            reporteHistoricoOtDIRDto.setFecha_Compromiso_Ot_Padre_Onyx_Aud(fecha_Compromiso_Ot_Padre_Onyx_Aud);
                        }

                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_1_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_2_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_2_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_3_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_3_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_2_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_4_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_4_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_3_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_1_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_1_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_1_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_2_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_2_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_2_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_3_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_3_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_3_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_4_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_4_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_4_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Padre_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Padre_Cm() == null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Padre_Cm() : ""));
                        SimpleDateFormat fecha_Padre_Onyx_aud = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                        String fecha_Creacion_OT_Padre_Onyx_Aud;
                        if (((OnyxOtCmDirAuditoria) orden[5]) != null && (((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Padre_Onyx() != null)) {
                            fecha_Creacion_OT_Padre_Onyx_Aud = fecha_Padre_Onyx_aud.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Padre_Onyx());
                            reporteHistoricoOtDIRDto.setFecha_Creacion_OT_Padre_Onyx_Aud(fecha_Creacion_OT_Padre_Onyx_Aud);
                        }
                        
                        // Telefono 
                        String contactoTec = (((OnyxOtCmDirAuditoria) orden[5]) != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getContacto_Tecnico_Ot_Padre_Onyx(): "");
                        if (contactoTec != null && !contactoTec.equals("")) {
                            contactoTec = StringUtils.caracteresEspeciales(contactoTec);
                            reporteHistoricoOtDIRDto.setContacto_Tecnico_Ot_Padre_Onyx_Aud(contactoTec);
                        }

                        // Telefono 
                        String telefono = ((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Vendedor_Onyx();
                        if (telefono != null && !telefono.equals("")) {
                            telefono = StringUtils.caracteresEspeciales(telefono);
                            reporteHistoricoOtDIRDto.setTelefono_Tecnico_Ot_Padre_Onyx_Aud(telefono);

                        }
                        // campos nuevos

                        // direccion Onyx
                        String direccionAud = ((OnyxOtCmDirAuditoria) orden[5]).getDireccion_Onyx();
                        if (direccionAud != null && !direccionAud.equals("")) {
                            direccionAud = StringUtils.caracteresEspeciales(direccionAud);
                            reporteHistoricoOtDIRDto.setDireccion_Onyx_Aud(direccionAud);
                        }

                        //aliado
                        if (((OnyxOtCmDirAuditoria) orden[5]).getAliadoImp() != null) {
                            reporteHistoricoOtDIRDto.setaImplement(((OnyxOtCmDirAuditoria) orden[5]).getAliadoImp());
                        }
                        //regional
                        if (((OnyxOtCmDirAuditoria) orden[5]).getRegionalOrigen() != null) {
                            reporteHistoricoOtDIRDto.setRegional(((OnyxOtCmDirAuditoria) orden[5]).getRegionalOrigen());
                        }
                        //codigo proyecto
                        if (((OnyxOtCmDirAuditoria) orden[5]).getCodigoProyecto() != null) {
                            reporteHistoricoOtDIRDto.setCodigoProyecto(((OnyxOtCmDirAuditoria) orden[5]).getCodigoProyecto());
                        }
                        //email tecnico
                        String emailTecnico = ((OnyxOtCmDirAuditoria) orden[5]).getEmailTecnico();
                        if (emailTecnico != null && !emailTecnico.equals("")) {
                            emailTecnico = StringUtils.caracteresEspeciales(emailTecnico);
                            reporteHistoricoOtDIRDto.setEmailCTec(emailTecnico);
                        }
                        //ciudad
                        if (((OnyxOtCmDirAuditoria) orden[5]).getCiudadFact() != null) {
                            reporteHistoricoOtDIRDto.setCiudadFact(((OnyxOtCmDirAuditoria) orden[5]).getCiudadFact());
                        }

                    }
                    listaReporteHistoricoOtDIRDto.add(reporteHistoricoOtDIRDto);

                }
            }
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
     
    public void cargarListaHistOrdenesDir(List<Object[]> listaOrdenesHistoricoAgendas, List<CmtBasicaMgl> listaBasica, List<ReporteHistoricoOtDIRDto> listaReporteHistoricoOtDIRDto) throws ApplicationException {
  
        try {
            if (listaOrdenesHistoricoAgendas != null && !listaOrdenesHistoricoAgendas.isEmpty()) {

                for (Object[] orden : listaOrdenesHistoricoAgendas) {
                    ReporteHistoricoOtDIRDto reporteHistoricoOtDIRDto = new ReporteHistoricoOtDIRDto();
                    //ot_dir
                    reporteHistoricoOtDIRDto.setOt_Id_Cm(((OtHhppMgl) orden[0]).getOtHhppId() != null
                            ? ((OtHhppMgl) orden[0]).getOtHhppId().toString() : "");

                    reporteHistoricoOtDIRDto.setTipo_OT_MER(((OtHhppMgl) orden[0]).getTipoOtHhppId() != null
                            ? ((OtHhppMgl) orden[0]).getTipoOtHhppId().getNombreTipoOt() : "");
                    if (((OtHhppMgl) orden[0]).getTipoOtHhppId() != null && ((OtHhppMgl) orden[0]).getTipoOtHhppId().getTipoTrabajoOFSC() != null) {
                        reporteHistoricoOtDIRDto.setSub_tipo_OT_MER(((OtHhppMgl) orden[0]).getTipoOtHhppId().getTipoTrabajoOFSC().getNombreBasica());
                    }
                    if (((OtHhppMgl) orden[0]).getTipoOtHhppId() != null && ((OtHhppMgl) orden[0]).getTipoOtHhppId().getSubTipoOrdenOFSC() != null) {
                        reporteHistoricoOtDIRDto.setSubTipo_Orden_OFSC(((OtHhppMgl) orden[0]).getTipoOtHhppId().getSubTipoOrdenOFSC().getNombreBasica());
                    }
                    reporteHistoricoOtDIRDto.setEstado_interno_OT_MER(((OtHhppMgl) orden[0]).getEstadoInternoInicial() != null
                            ? ((OtHhppMgl) orden[0]).getEstadoInternoInicial().getNombreBasica() : "");
                    reporteHistoricoOtDIRDto.setEstadoOt(((OtHhppMgl) orden[0]).getEstadoGeneral() != null
                            ? ((OtHhppMgl) orden[0]).getEstadoGeneral().getNombreBasica() : "");

                    SimpleDateFormat fecha_creacion_OT_MER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    reporteHistoricoOtDIRDto.setFecha_creacion_OT_MER(((OtHhppMgl) orden[0]).getFechaCreacionOt() != null
                            ? fecha_creacion_OT_MER.format(((OtHhppMgl) orden[0]).getFechaCreacionOt()) : "");
                    

                    List<OtHhppTecnologiaMgl> listaTecnologias = ((OtHhppMgl) orden[0]).getTecnologiaBasicaList();
                    if (listaTecnologias != null && !listaTecnologias.isEmpty()) {
                        String listaTecno = "";
                        for (OtHhppTecnologiaMgl otHhppTecnologiaMgl : listaTecnologias) {
                            listaTecno += otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getNombreBasica().trim() + ",";
                        }
                        if (!listaTecno.isEmpty()) {
                            listaTecno = listaTecno.substring(0, listaTecno.length() - 1);
                        }
                        reporteHistoricoOtDIRDto.setTecnologia_OT_MGL(listaTecno);
                    }
                    reporteHistoricoOtDIRDto.setSegmento_OT_MER(((OtHhppMgl) orden[0]).getSegmento() == null
                            ? "" : ((OtHhppMgl) orden[0]).getSegmento().getNombreBasica());
                    // direccion mer
                    if (((OtHhppMgl) orden[0]).getSubDireccionId() != null) {
                        reporteHistoricoOtDIRDto.setDireccionMer(((OtHhppMgl) orden[0]).getSubDireccionId().getSdiFormatoIgac());
                    } else {
                        if (((OtHhppMgl) orden[0]).getDireccionId() != null) {
                            reporteHistoricoOtDIRDto.setDireccionMer(((OtHhppMgl) orden[0]).getDireccionId().getDirFormatoIgac());
                        }
                    }

                    reporteHistoricoOtDIRDto.setUsuario_Creacion_OT_MER((((OtHhppMgl) orden[0]).getUsuarioCreacion() != null
                            ? ((OtHhppMgl) orden[0]).getUsuarioCreacion() : ""));
                    reporteHistoricoOtDIRDto.setOnyx_Ot_Hija_Cm(((OtHhppMgl) orden[0]).getOnyxOtHija() != null
                            ? ((OtHhppMgl) orden[0]).getOnyxOtHija().toString() : "");
                    String complejidadDescripcion;
                    if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("A")) {
                        complejidadDescripcion = "ALTA";
                    } else if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("M")) {
                        complejidadDescripcion = "MEDIA";
                    } else if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("B")) {
                        complejidadDescripcion = "BAJA";
                    } else {
                        complejidadDescripcion = "";
                    }
                    reporteHistoricoOtDIRDto.setComplejidadServicio(complejidadDescripcion);
                    reporteHistoricoOtDIRDto.setUsuarioModOt(((OtHhppMgl) orden[0]).getUsuarioEdicion());

                    // auditoria ot direcciones
                    if (((OtHhppMglAuditoria) orden[3]) != null) {

                        SimpleDateFormat fecha_modificacion_OT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                        reporteHistoricoOtDIRDto.setFecha_Modificacion_Ot(((OtHhppMglAuditoria) orden[3]).getFechaEdicion() != null
                                ? fecha_modificacion_OT.format(((OtHhppMglAuditoria) orden[3]).getFechaEdicion()) : "");

                        reporteHistoricoOtDIRDto.setEstado_interno_OT_MER_Aud(((OtHhppMglAuditoria) orden[3]).getEstadoInternoInicial()!= null
                                ? ((OtHhppMglAuditoria) orden[3]).getEstadoInternoInicial().getNombreBasica() : "");
                        reporteHistoricoOtDIRDto.setEstadoOt_Aud(((OtHhppMglAuditoria) orden[3]).getEstadoGeneral() != null
                            ? ((OtHhppMglAuditoria) orden[3]).getEstadoGeneral().getNombreBasica() : "");
                        reporteHistoricoOtDIRDto.setFecha_Creacion_OT_MER_Aud(((OtHhppMglAuditoria) orden[3]).getFechaCreacion() != null
                                ? fecha_creacion_OT_MER.format(((OtHhppMglAuditoria) orden[3]).getFechaCreacion()) : "");
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Hija_Cm_Aud(((OtHhppMglAuditoria) orden[3]).getOnyxOtHija() != null
                                ? ((OtHhppMglAuditoria) orden[3]).getOnyxOtHija().toString() : "");
                        String complejidadDescripcionAud = "";
                        if (((OtHhppMglAuditoria) orden[3]).getComplejidadServicio() != null && ((OtHhppMglAuditoria) orden[3]).getComplejidadServicio().equals("A")) {
                            complejidadDescripcion = "ALTA";
                        } else if (((OtHhppMglAuditoria) orden[3]).getComplejidadServicio() != null && ((OtHhppMglAuditoria) orden[3]).getComplejidadServicio().equals("M")) {
                            complejidadDescripcionAud = "MEDIA";
                        } else if (((OtHhppMglAuditoria) orden[3]).getComplejidadServicio() != null && ((OtHhppMglAuditoria) orden[3]).getComplejidadServicio().equals("B")) {
                            complejidadDescripcionAud = "BAJA";
                        } else {
                            complejidadDescripcionAud = "";
                        }
                        reporteHistoricoOtDIRDto.setComplejidadServicio(complejidadDescripcionAud);
                        reporteHistoricoOtDIRDto.setUsuarioModOtAud(((OtHhppMglAuditoria) orden[3]).getUsuarioEdicion());
                    }

                    //agenda_Dir
                    if (((MglAgendaDireccion) orden[1]) != null) {
                        reporteHistoricoOtDIRDto.setOrden_RR((((MglAgendaDireccion) orden[1]).getIdOtenrr() == null
                                ? ((MglAgendaDireccion) orden[1]).getIdOtenrr() : ""));

                        //sub tipo ordenes ofsc
                        if (listaBasica != null && !listaBasica.isEmpty()) {
                            for (CmtBasicaMgl cmtBasicaMgl : listaBasica) {
                                if (cmtBasicaMgl.getCodigoBasica() != null && ((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce() != null) {
                                    if (((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                                        reporteHistoricoOtDIRDto.setSubTipo_Orden_OFSC(((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce().trim());
                                        break;
                                    }
                                }

                            }
                        }

                        String fechaAgenda;
                        if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaAgenda() != null) {
                            String timeS = ((MglAgendaDireccion) orden[1]) != null ? ((MglAgendaDireccion) orden[1]).getTimeSlot() : "";
                            fechaAgenda = DateUtils.setTimeSlotFechaAgenda(((MglAgendaDireccion) orden[1]).getFechaAgenda(), timeS);
                            reporteHistoricoOtDIRDto.setFecha_agenda_OFSC(fechaAgenda);
                        }

                        reporteHistoricoOtDIRDto.setUsuario_creacion_agenda_OFSC((((MglAgendaDireccion) orden[1]).getUsuarioCreacion() == null ? ""
                                : ((MglAgendaDireccion) orden[1]).getUsuarioCreacion()));
                        String timeS = (((MglAgendaDireccion) orden[1]).getTimeSlot() != null ? ((MglAgendaDireccion) orden[1]).getTimeSlot() : "");
                        if (timeS != null && !timeS.equals("")) {
                            if (timeS.contains("Durante")) {
                                reporteHistoricoOtDIRDto.setTime_slot_OFSC(timeS);
                            } else {
                                timeS = timeS.replace("-", "_");
                                reporteHistoricoOtDIRDto.setTime_slot_OFSC(timeS);
                            }
                        }
                        reporteHistoricoOtDIRDto.setEstado_agenda_OFSC((((MglAgendaDireccion) orden[1]).getBasicaIdEstadoAgenda() != null
                                ? ((MglAgendaDireccion) orden[1]).getBasicaIdEstadoAgenda().getNombreBasica() : ""));
                        SimpleDateFormat fecha_inicia_agenda = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        reporteHistoricoOtDIRDto.setFecha_inicia_agenda_OFSC(((MglAgendaDireccion) orden[1]).getFechaInivioVt() != null
                                ? fecha_inicia_agenda.format(((MglAgendaDireccion) orden[1]).getFechaInivioVt()) : "");

                        SimpleDateFormat fecha_fin_agenda = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        reporteHistoricoOtDIRDto.setFecha_fin_agenda_OFSC(((MglAgendaDireccion) orden[1]).getFechaFinVt() != null
                                ? fecha_fin_agenda.format(((MglAgendaDireccion) orden[1]).getFechaFinVt()) : "");

                        reporteHistoricoOtDIRDto.setId_aliado_OFSC(((MglAgendaDireccion) orden[1]).getIdentificacionAliado() != null
                                ? ((MglAgendaDireccion) orden[1]).getIdentificacionAliado() : "");
                        reporteHistoricoOtDIRDto.setNombre_aliado_OFSC(((MglAgendaDireccion) orden[1]).getNombreAliado() != null
                                ? ((MglAgendaDireccion) orden[1]).getNombreAliado() : "");
                        reporteHistoricoOtDIRDto.setId_tecnico_aliado_OFSC(((MglAgendaDireccion) orden[1]).getIdentificacionTecnico() != null
                                ? ((MglAgendaDireccion) orden[1]).getIdentificacionTecnico() : "");
                        reporteHistoricoOtDIRDto.setNombre_tecnico_aliado_OFSC(((MglAgendaDireccion) orden[1]).getNombreTecnico() == null
                                ? ((MglAgendaDireccion) orden[1]).getNombreTecnico() : "");
                        reporteHistoricoOtDIRDto.setUltima_agenda_multiagenda(((MglAgendaDireccion) orden[1]).getUltimaAgenda().equals("")
                                ? ((MglAgendaDireccion) orden[1]).getUltimaAgenda() : "");
                        // observaciones 
                        String obs = ((MglAgendaDireccion) orden[1]).getObservacionesTecnico();
                        if (obs != null && !obs.equals("")) {
                            obs = StringUtils.caracteresEspeciales(obs);
                            reporteHistoricoOtDIRDto.setObservaciones_tecnico_OFSC(obs);
                        }

                        reporteHistoricoOtDIRDto.setAppt_number_OFSC(((MglAgendaDireccion) orden[1]).getOfpsOtId().equals("")
                                ? ((MglAgendaDireccion) orden[1]).getOfpsOtId() : "");
                        reporteHistoricoOtDIRDto.setUsuarioModAgenda(((MglAgendaDireccion) orden[1]).getUsuarioEdicion());

                        //CAMPOS NUEVOS
                        reporteHistoricoOtDIRDto.setPersonaAtiendeSitio(((MglAgendaDireccion) orden[1]).getPersonaRecVt());
                        reporteHistoricoOtDIRDto.setTelefonoAtiendeSitio(((MglAgendaDireccion) orden[1]).getTelPerRecVt());
                        // tiempo de ejecucion
                        if (((MglAgendaDireccion) orden[1]).getFechaFinVt() != null && ((MglAgendaDireccion) orden[1]).getFechaInivioVt() != null) {
                            int tiempoEjecucion = (int) ((((MglAgendaDireccion) orden[1]).getFechaFinVt().getTime() - ((MglAgendaDireccion) orden[1]).getFechaInivioVt().getTime()) / (60 * 60 * 1000));
                            reporteHistoricoOtDIRDto.setTiempoEjecucion(Integer.toString(tiempoEjecucion));
                        }
                        //resultado agenda
                        reporteHistoricoOtDIRDto.setResultadoOrden((((MglAgendaDireccion) orden[1]).getBasicaIdrazones() != null
                                ? ((MglAgendaDireccion) orden[1]).getBasicaIdrazones().getNombreBasica() : ""));

                        //cantidad reagenda 
                        reporteHistoricoOtDIRDto.setResultadoOrden((((MglAgendaDireccion) orden[1]) != null
                                && ((MglAgendaDireccion) orden[1]).getBasicaIdrazones() != null
                                ? ((MglAgendaDireccion) orden[1]).getBasicaIdrazones().getNombreBasica() : ""));
                        //Motivos  
                        String razones = "";
                        List<MglAgendaDireccionAuditoria> listRasones = new ArrayList<>();
                        if (((MglAgendaDireccion) orden[1]).getListAgendaDireccionAuditoria() != null
                                && !((MglAgendaDireccion) orden[1]).getListAgendaDireccionAuditoria().isEmpty()) {
                            for (MglAgendaDireccionAuditoria motivos : ((MglAgendaDireccion) orden[1]).getListAgendaDireccionAuditoria()) {
                                if (motivos.getRazonReagenda() != null) {
                                    if (!listRasones.isEmpty()) {
                                        int cont = 0;
                                        for (MglAgendaDireccionAuditoria listanew : listRasones) {
                                            if (listanew.getTimeSlot() != null && !listanew.getTimeSlot().equals("")) {
                                                if (listanew.getTimeSlot().equals(motivos.getTimeSlot())) {
                                                    cont++;
                                                }
                                            }
                                        }
                                        if (cont == 0) {
                                            listRasones.add(motivos);
                                        }
                                    } else {
                                        listRasones.add(motivos);
                                    }
                                }
                            }
                        }
                        String listaMotivosFinal = "";
                        if (!listRasones.isEmpty()) {
                            for (MglAgendaDireccionAuditoria razon : listRasones) {
                                listaMotivosFinal += razon.getRazonReagenda() + ",";
                            }
                        }
                        if (!listaMotivosFinal.isEmpty()) {
                            if (listaMotivosFinal.endsWith(",")) {
                                listaMotivosFinal = listaMotivosFinal.substring(0, listaMotivosFinal.length() - 1);
                            }
                        }

                        reporteHistoricoOtDIRDto.setMotivosReagenda(listaMotivosFinal);

                        //tiempo ejecucion
                        if (((MglAgendaDireccion) orden[1]).getFechaFinVt() != null && ((MglAgendaDireccion) orden[1]).getFechaInivioVt() != null) {
                            int tiempoEjecucion = (int) ((((MglAgendaDireccion) orden[1]).getFechaFinVt().getTime()
                                    - ((MglAgendaDireccion) orden[1]).getFechaInivioVt().getTime()) / 86400000);
                            reporteHistoricoOtDIRDto.setTiempoEjecucion(Integer.toString(tiempoEjecucion));
                        }
                         //conveniencia
                        if (((MglAgendaDireccion) orden[1]).getConveniencia() != null) {
                            reporteHistoricoOtDIRDto.setConveniencia(((MglAgendaDireccion) orden[1]).getConveniencia());
                        }

                    }
                    // Agenda Auditoria
                    if (((MglAgendaDireccionAuditoria) orden[4]) != null) {

                        //sub tipo ordenes ofsc
                        if (listaBasica != null && !listaBasica.isEmpty()) {
                            for (CmtBasicaMgl cmtBasicaMgl : listaBasica) {
                                if (cmtBasicaMgl.getCodigoBasica() != null && ((MglAgendaDireccionAuditoria) orden[4]).getSubTipoWorkFoce() != null) {
                                    if (((MglAgendaDireccionAuditoria) orden[4]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                                        reporteHistoricoOtDIRDto.setSubTipo_Orden_OFSC_Aud(((MglAgendaDireccionAuditoria) orden[4]).getSubTipoWorkFoce().trim());
                                        break;
                                    }
                                }
                            }
                        }

                        reporteHistoricoOtDIRDto.setEstado_agenda_OFSC_Aud((((MglAgendaDireccionAuditoria) orden[4]).getBasicaIdEstadoAgenda() != null
                                ? ((MglAgendaDireccionAuditoria) orden[4]).getBasicaIdEstadoAgenda().getNombreBasica() : ""));
                        SimpleDateFormat fecha_inicia_agenda_Aud = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        reporteHistoricoOtDIRDto.setFecha_inicia_agenda_OFSC_Aud(((MglAgendaDireccionAuditoria) orden[4]).getFechaInivioVt() != null
                                ? fecha_inicia_agenda_Aud.format(((MglAgendaDireccionAuditoria) orden[4]).getFechaInivioVt()) : "");
                        SimpleDateFormat fecha_fin_agenda_Aud = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        reporteHistoricoOtDIRDto.setFecha_fin_agenda_OFSC_Aud(((MglAgendaDireccionAuditoria) orden[4]).getFechaFinVt() != null
                                ? fecha_fin_agenda_Aud.format(((MglAgendaDireccionAuditoria) orden[4]).getFechaFinVt()) : "");
                        reporteHistoricoOtDIRDto.setId_aliado_OFSC_Aud(((MglAgendaDireccionAuditoria) orden[4]).getIdentificacionAliado() != null
                                ? ((MglAgendaDireccionAuditoria) orden[4]).getIdentificacionAliado() : "");
                        reporteHistoricoOtDIRDto.setNombre_aliado_OFSC_Aud(((MglAgendaDireccionAuditoria) orden[4]).getNombreAliado() != null
                                ? ((MglAgendaDireccionAuditoria) orden[4]).getNombreAliado() : "");
                        reporteHistoricoOtDIRDto.setId_tecnico_aliado_OFSC_Aud(((MglAgendaDireccionAuditoria) orden[4]).getIdentificacionTecnico() != null
                                ? ((MglAgendaDireccionAuditoria) orden[4]).getIdentificacionTecnico() : "");
                        reporteHistoricoOtDIRDto.setNombre_tecnico_aliado_OFSC_Aud(((MglAgendaDireccionAuditoria) orden[4]).getNombreTecnico() != null
                                ? ((MglAgendaDireccionAuditoria) orden[4]).getNombreTecnico() : "");
                        reporteHistoricoOtDIRDto.setUsuarioModAgendaAud(((MglAgendaDireccionAuditoria) orden[4]).getUsuarioEdicion());
                    }
                    // onyx
                    if (((OnyxOtCmDir) orden[2]) != null) {

                        reporteHistoricoOtDIRDto.setNit_Cliente_Onyx((((OnyxOtCmDir) orden[2]).getNit_Cliente_Onyx() == null
                                ? ((OnyxOtCmDir) orden[2]).getNit_Cliente_Onyx() : ""));
                        // nombreCiente
                        String nombreCienteOnyx = ((OnyxOtCmDir) orden[2]).getNombre_Cliente_Onyx();
                        if (nombreCienteOnyx != null && !nombreCienteOnyx.equals("")) {
                            nombreCienteOnyx = StringUtils.caracteresEspeciales(nombreCienteOnyx);
                            reporteHistoricoOtDIRDto.setNombre_Cliente_Onyx(nombreCienteOnyx);
                        }

                        // nombreOtHija 
                        String nombreOtHija = ((OnyxOtCmDir) orden[2]).getNombre_Ot_Hija_Onyx();
                        if (nombreOtHija != null && !nombreOtHija.equals("")) {
                            nombreOtHija = StringUtils.caracteresEspeciales(nombreOtHija);
                            reporteHistoricoOtDIRDto.setNombre_Ot_Hija_Onyx(nombreOtHija);
                        }

                        //ot hija direcciones
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Padre_Cm(((OnyxOtCmDir) orden[2]).getOnyx_Ot_Padre_Dir() == null
                                ? ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Padre_Dir() : "");
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Hija_Cm(((OnyxOtCmDir) orden[2]).getOnyx_Ot_Hija_Dir() == null
                                ? ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Hija_Dir() : "");
                        SimpleDateFormat creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String fecha_Creacion_Ot_Hija_Onyx = null;
                        if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                            fecha_Creacion_Ot_Hija_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx());
                            reporteHistoricoOtDIRDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx);
                        }

                        // fecha creacion onyx
                        String fecha_Creacion_Ot_Onyx;
                        if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFechaCreacion() != null) {
                            fecha_Creacion_Ot_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[2]).getFechaCreacion());
                            reporteHistoricoOtDIRDto.setFechaCreacionOnyx(fecha_Creacion_Ot_Onyx);
                        }

                        reporteHistoricoOtDIRDto.setDescripcion_Onyx(((OnyxOtCmDir) orden[2]).getDescripcion_Onyx() != null
                                ? ((OnyxOtCmDir) orden[2]).getDescripcion_Onyx() : "");
                        reporteHistoricoOtDIRDto.setSegmento_Onyx((((OnyxOtCmDir) orden[2]).getSegmento_Onyx() != null
                                ? ((OnyxOtCmDir) orden[2]).getSegmento_Onyx() : ""));
                        reporteHistoricoOtDIRDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() != null
                                ? ((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() : "");

                        reporteHistoricoOtDIRDto.setServicios_Onyx(((OnyxOtCmDir) orden[2]).getServicios_Onyx() != null
                                ? ((OnyxOtCmDir) orden[2]).getServicios_Onyx() : "");
                        reporteHistoricoOtDIRDto.setRecurrente_Mensual_Onyx(((OnyxOtCmDir) orden[2]).getRecurrente_Mensual_Onyx() != null
                                ? ((OnyxOtCmDir) orden[2]).getRecurrente_Mensual_Onyx() : "");
                        reporteHistoricoOtDIRDto.setCodigo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getCodigo_Servicio_Onyx() != null
                                ? ((OnyxOtCmDir) orden[2]).getCodigo_Servicio_Onyx() : "");
                        // vendedor_Onyx 
                        String vendedor_Onyx = ((OnyxOtCmDir) orden[2]).getVendedor_Onyx();
                        if (vendedor_Onyx != null && !vendedor_Onyx.equals("")) {
                            vendedor_Onyx = StringUtils.caracteresEspeciales(vendedor_Onyx);
                            reporteHistoricoOtDIRDto.setVendedor_Onyx(vendedor_Onyx);
                        }


                        // Telefono 
                        String telefonoVend = ((OnyxOtCmDir) orden[2]).getTelefono_Vendedor_Onyx();
                        if (telefonoVend != null && !telefonoVend.equals("")) {
                            telefonoVend = StringUtils.caracteresEspeciales(telefonoVend);
                            reporteHistoricoOtDIRDto.setTelefono_Vendedor_Onyx(telefonoVend);
                        }

                        reporteHistoricoOtDIRDto.setEstado_Ot_Hija_Onyx_Cm((((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getEstado_Ot_Hija_Onyx_Dir() : ""));

                        reporteHistoricoOtDIRDto.setEstado_Ot_Padre_Onyx_Cm(((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getEstado_Ot_Padre_Onyx_Dir() : "");
                        SimpleDateFormat compromiso_Ot_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String fecha_Compromiso_Ot_Padre_Onyx;
                        if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null) {
                            fecha_Compromiso_Ot_Padre_Onyx = compromiso_Ot_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx());
                            reporteHistoricoOtDIRDto.setFecha_Compromiso_Ot_Padre_Onyx(fecha_Compromiso_Ot_Padre_Onyx);
                        }

                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_1_Onyx(((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_1_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_2_Onyx(((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_2_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_3_Onyx(((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_3_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_4_Onyx(((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_4_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_1_Onyx(((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_1_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_2_Onyx(((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_2_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_3_Onyx(((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_3_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_4_Onyx(((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_4_Onyx() : "");

                        SimpleDateFormat fecha_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String fecha_Creacion_Ot_Padre_Onyx;
                        if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                            fecha_Creacion_Ot_Padre_Onyx = fecha_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx());
                            reporteHistoricoOtDIRDto.setFecha_Creacion_Ot_Padre_Onyx(fecha_Creacion_Ot_Padre_Onyx);
                        }

                         // Telefono 
                        String contactoTec = ((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx();
                        if (contactoTec != null && !contactoTec.equals("")) {
                            contactoTec = StringUtils.caracteresEspeciales(contactoTec);
                            reporteHistoricoOtDIRDto.setContacto_Tecnico_Ot_Padre_Onyx(contactoTec);
                        }
                        
                        // Telefono 
                        String telefono = ((OnyxOtCmDir) orden[2]).getTelefono_Tecnico_Ot_Padre_Onyx();
                        if (telefono != null && !telefono.equals("")) {
                            telefono = StringUtils.caracteresEspeciales(telefono);
                            reporteHistoricoOtDIRDto.setTelefono_Tecnico_Ot_Padre_Onyx(telefono);
                        }

                        //CAMPOS NUEVOS OT DIRECCION
                        reporteHistoricoOtDIRDto.setCodigoProyecto((((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getCodigoProyecto() : ""));
                        // direccion Onyx
                        String direccion = ((OnyxOtCmDir) orden[2]).getDireccion_Onyx();
                        if (direccion != null && !direccion.equals("")) {
                            direccion = StringUtils.caracteresEspeciales(direccion);
                            reporteHistoricoOtDIRDto.setDireccion_Onyx(direccion);
                        }

                         //emailTecnico
                        String emailTecnico = ((OnyxOtCmDir) orden[2]).getEmailTec();
                        if (emailTecnico != null && !emailTecnico.equals("")) {
                            emailTecnico = StringUtils.caracteresEspeciales(emailTecnico);
                            reporteHistoricoOtDIRDto.setEmailCTec(emailTecnico);
                        }
                        reporteHistoricoOtDIRDto.setTelefono_Tecnico_Ot_Padre_Onyx((((OnyxOtCmDir) orden[2]) != null
                                ? ((OnyxOtCmDir) orden[2]).getCellTec() : ""));

                        // indicador de cumplimiento
                        String cumplimiento = "No se cumplio";
                        if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null
                                && ((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaAgenda() != null) {
                            if (((MglAgendaDireccion) orden[1]).getUltimaAgenda() != null && ((MglAgendaDireccion) orden[1]).getUltimaAgenda().equals("Y")) {
                                if (((MglAgendaDireccion) orden[1]).getFechaAgenda().before(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx())) {
                                    cumplimiento = "Si se cumplió";
                                }
                            }
                            reporteHistoricoOtDIRDto.setIndicadorCumplimiento(cumplimiento);
                        }
                        //tiempo de programacion
                        if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaAgenda() != null
                                && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                            String fecha = DateUtils.getTiempoEntreFechasConFormato(((MglAgendaDireccion) orden[1]).getFechaCreacion(), ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx());
                            reporteHistoricoOtDIRDto.setTiempoProgramacion(fecha);

                        }
                        //tiempo de atencion
                        if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null
                                && ((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaAgenda() != null) {
                            String timeSlot = ((MglAgendaDireccion) orden[1]).getTimeSlot() == null ? ""
                                    : ((MglAgendaDireccion) orden[1]).getTimeSlot().contains("Durante") ? ""
                                    : ((MglAgendaDireccion) orden[1]).getTimeSlot();
                            if (timeSlot != null && !timeSlot.equals("")) {
                                if (timeSlot.contains("Durante")) {
                                    timeSlot = "00";
                                    String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((MglAgendaDireccion) orden[1]).getFechaAgenda(), ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                    reporteHistoricoOtDIRDto.setTiempoAtencion(horasTiempoAtiende);
                                } else {
                                    timeSlot = ((MglAgendaDireccion) orden[1]).getTimeSlot().substring(0, 2);
                                    String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((MglAgendaDireccion) orden[1]).getFechaAgenda(), ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                    reporteHistoricoOtDIRDto.setTiempoAtencion(horasTiempoAtiende);
                                }
                            }
                        }
                        //antiguedad de la agenda
                        if (((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                            String antiguedad = DateUtils.getTiempoEntreFechasConFormato(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx(), new Date());
                            reporteHistoricoOtDIRDto.setAntiguedadOrden(antiguedad);
                        }

                        //aliado Implementacion 
                        if (((OnyxOtCmDir) orden[2]).getaImplement() != null) {
                            reporteHistoricoOtDIRDto.setaImplement(((OnyxOtCmDir) orden[2]).getaImplement());
                        }
                        //tipo solucion
                        if (((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() != null) {
                            reporteHistoricoOtDIRDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx());
                        }
                        //ciudad fact
                        if (((OnyxOtCmDir) orden[2]).getCiudadFact() != null) {
                            reporteHistoricoOtDIRDto.setCiudadFact(((OnyxOtCmDir) orden[2]).getCiudadFact());
                        }

                    }
                    if (((OnyxOtCmDirAuditoria) orden[5]) != null) {

                        reporteHistoricoOtDIRDto.setNit_Cliente_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getNit_Cliente_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getNit_Cliente_Onyx() : "");
                        // nombreCiente
                        String nombreCienteOnyx = ((OnyxOtCmDirAuditoria) orden[5]).getNombre_Cliente_Onyx();
                        if (nombreCienteOnyx != null && !nombreCienteOnyx.equals("")) {
                            nombreCienteOnyx = StringUtils.caracteresEspeciales(nombreCienteOnyx);
                            reporteHistoricoOtDIRDto.setNombre_Cliente_Onyx(nombreCienteOnyx);
                        }

                        // nombreOtHija 
                        String nombreOtHija = ((OnyxOtCmDirAuditoria) orden[5]).getNombre_Ot_Hija_Onyx();
                        if (nombreOtHija != null && !nombreOtHija.equals("")) {
                            nombreOtHija = StringUtils.caracteresEspeciales(nombreOtHija);
                        }
                        reporteHistoricoOtDIRDto.setNombre_OT_Hija_Onyx_Aud(nombreOtHija);
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Hija_Cm_Aud(((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Hija_Dir() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Hija_Dir() : "");
                        reporteHistoricoOtDIRDto.setOnyx_Ot_Padre_Cm_Aud(((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Padre_Dir() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Padre_Dir() : "");

                        SimpleDateFormat creacion_Ot_Hija_Onyx_Aud = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        reporteHistoricoOtDIRDto.setFecha_Creación_OT_Hija_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Hija_Onyx() != null
                                ? creacion_Ot_Hija_Onyx_Aud.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Hija_Onyx()) : ""));

                        SimpleDateFormat creacion_Ot_Padre_Onyx_Aud = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        reporteHistoricoOtDIRDto.setFecha_Creacion_OT_Padre_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Hija_Onyx() != null
                                ? creacion_Ot_Padre_Onyx_Aud.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Hija_Onyx()) : ""));

                        SimpleDateFormat creacion_Ot_Onyx = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        reporteHistoricoOtDIRDto.setFechaCreacionOnyx((((OnyxOtCmDirAuditoria) orden[5]).getFechaCreacion() != null
                                ? creacion_Ot_Onyx.format(((OnyxOtCmDirAuditoria) orden[5]).getFechaCreacion()) : ""));

                        reporteHistoricoOtDIRDto.setDescripcion_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getDescripcion_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getDescripcion_Onyx() : "");
                        reporteHistoricoOtDIRDto.setSegmento_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getSegmento_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getSegmento_Onyx() : "");
                        reporteHistoricoOtDIRDto.setTipo_Servicio_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getTipo_Servicio_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getTipo_Servicio_Onyx() : "");
                        reporteHistoricoOtDIRDto.setServicios_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getServicios_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getServicios_Onyx() : "");
                        reporteHistoricoOtDIRDto.setRecurrente_Mensual_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getRecurrente_Mensual_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getRecurrente_Mensual_Onyx() : "");
                        reporteHistoricoOtDIRDto.setCodigo_Servicio_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getCodigo_Servicio_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getCodigo_Servicio_Onyx() : "");
                        
                         // vendedor_Onyx 
                        String vendedor_Onyx = ((OnyxOtCmDirAuditoria) orden[5]).getVendedor_Onyx();
                        if (vendedor_Onyx != null && !vendedor_Onyx.equals("")) {
                            vendedor_Onyx = StringUtils.caracteresEspeciales(vendedor_Onyx);
                            reporteHistoricoOtDIRDto.setVendedor_Onyx_Aud(vendedor_Onyx);
                        }


                        // Telefono 
                        String telefonoVend = ((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Vendedor_Onyx();
                        if (telefonoVend != null && !telefonoVend.equals("")) {
                            telefonoVend = StringUtils.caracteresEspeciales(telefonoVend);
                            reporteHistoricoOtDIRDto.setTelefono_Vendedor_Onyx_Aud(telefonoVend);
                        }

                        reporteHistoricoOtDIRDto.setEstado_Ot_Hija_Onyx_Dir_Aud(((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Hija_Onyx_Dir() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Hija_Onyx_Dir() : "");
                        reporteHistoricoOtDIRDto.setEstado_Ot_Padre_Onyx_Dir_Aud(((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Padre_Onyx_Dir() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Padre_Onyx_Dir() : "");
                        SimpleDateFormat compromiso_Ot_Padre_Onyx_Aud = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        reporteHistoricoOtDIRDto.setFecha_Compromiso_Ot_Padre_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getFecha_Compromiso_Ot_Padre_Onyx() != null
                                ? compromiso_Ot_Padre_Onyx_Aud.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Compromiso_Ot_Padre_Onyx()) : ""));
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_1_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_2_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_2_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_2_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_3_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_3_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_3_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Hija_Resolucion_4_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_4_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_4_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_1_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_1_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_1_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_2_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_2_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_2_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_3_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_3_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_3_Onyx() : "");
                        reporteHistoricoOtDIRDto.setOt_Padre_Resolucion_4_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_4_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_4_Onyx() : "");
                        reporteHistoricoOtDIRDto.setContacto_Tecnico_Ot_Padre_Onyx_Aud(((OnyxOtCmDirAuditoria) orden[5]).getContacto_Tecnico_Ot_Padre_Onyx() != null
                                ? ((OnyxOtCmDirAuditoria) orden[5]).getContacto_Tecnico_Ot_Padre_Onyx() : "");
                        // Telefono 
                        String telefono = ((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Tecnico_Ot_Padre_Onyx();
                        if (telefono != null && !telefono.equals("")) {
                            telefono = StringUtils.caracteresEspeciales(telefono);
                            reporteHistoricoOtDIRDto.setTelefono_Tecnico_Ot_Padre_Onyx_Aud(telefono);
                        }
                        // campos nuevos

                        // direccion Onyx
                        String direccion = ((OnyxOtCmDirAuditoria) orden[5]).getDireccion_Onyx();
                        if (direccion != null && !direccion.equals("")) {
                            direccion = StringUtils.caracteresEspeciales(direccion);
                            reporteHistoricoOtDIRDto.setDireccion_Onyx_Aud(direccion);
                        }

                        //aliado
                        if (((OnyxOtCmDirAuditoria) orden[5]).getAliadoImp() != null) {
                            reporteHistoricoOtDIRDto.setaImplement(((OnyxOtCmDirAuditoria) orden[5]).getAliadoImp());
                        }
                        //regional
                        if (((OnyxOtCmDirAuditoria) orden[5]).getRegionalOrigen() != null) {
                            reporteHistoricoOtDIRDto.setRegional(((OnyxOtCmDirAuditoria) orden[5]).getRegionalOrigen());
                        }
                        //codigo proyecto
                        if (((OnyxOtCmDirAuditoria) orden[5]).getCodigoProyecto() != null) {
                            reporteHistoricoOtDIRDto.setCodigoProyecto(((OnyxOtCmDirAuditoria) orden[5]).getCodigoProyecto());
                        }
                         //emailTecnico
                        String emailTecnico = ((OnyxOtCmDirAuditoria) orden[5]).getEmailTecnico();
                        if (emailTecnico != null && !emailTecnico.equals("")) {
                            emailTecnico = StringUtils.caracteresEspeciales(emailTecnico);
                            reporteHistoricoOtDIRDto.setEmailCTec(emailTecnico);
                        }
                        //ciudad
                        if (((OnyxOtCmDirAuditoria) orden[5]).getCiudadFact() != null) {
                            reporteHistoricoOtDIRDto.setCiudadFact(((OnyxOtCmDirAuditoria) orden[5]).getCiudadFact());
                        }

                    }
                    // OtHhppTecnologiaMgl
                    if (((OtHhppTecnologiaMgl) orden[6]) != null) {
                        // CAMPOS NUEVOS
                        if (((OtHhppTecnologiaMgl) orden[6]).getNodo() != null && ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId() != null
                                && ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getRegionalRr() != null
                                && ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getRegionalRr().getNombreRegional() != null
                                && ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getRegionalRr().getCodigoRr() != null) {
                            reporteHistoricoOtDIRDto.setRegional(((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getRegionalRr().getNombreRegional()
                                    + " " + "(" + ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getRegionalRr().getCodigoRr() + ")");
                        }
                        if (((OtHhppTecnologiaMgl) orden[6]).getNodo() != null && ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId() != null
                                && ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getNombreComunidad() != null
                                && ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getCodigoRr() != null) {
                            reporteHistoricoOtDIRDto.setCiudad(((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getNombreComunidad()
                                    + " " + "(" + ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getCodigoRr() + ")");
                        }

                        if (((OtHhppTecnologiaMgl) orden[6]).getNodo() != null && ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId() != null
                                && ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getCiudad() != null
                                && ((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getCiudad().getGpoNombre() != null) {
                            reporteHistoricoOtDIRDto.setDepartamento(((OtHhppTecnologiaMgl) orden[6]).getNodo().getComId().getCiudad().getGpoNombre());
                        }

                    }
                    listaReporteHistoricoOtDIRDto.add(reporteHistoricoOtDIRDto);

                }
            }
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
         
    }
     
     

     /**
     * M&eacute;todo para generar el reporte de ordenes de trabajo de CM
     *
     * @param sql {@link StringBuilder} objeto para generar el String de la
     * consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     *
     */
    private void generarSelectReporteHistoricoOtCm(StringBuilder sql, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<BigDecimal> subTipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOFSC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<String> listEstadosSelected = (List<String>) params.get("listEstadosSelected");
        List<String> regionalList = (List<String>) params.get("listRegionalSelected");
        List<String> ciudadList = (List<String>) params.get("listRrCiudadesSelected");
        List<String> listSegmento = (List<String>) params.get("listSegmentoSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");
        List<String> listEstadosOtCmDirSelected = (List<String>) params.get("listEstadosOtCmDirSelected");
        String codigoProyecto = (String) params.get("codProyecto");
        List<BigDecimal> estadosList = (List<BigDecimal>) params.get("estadoInternolist");
        
        
        
        
        // fecha de Creacion orden OT ,
        if (params.get("filtroFechas").equals(Constant.FECHA_CREACION_OT)) {
            if (params.get("fechaInicioOt") != null && (params.get("fechaFinOt") != null)) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', ot_aud.fechaCreacion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ot_aud.fechaCreacion) = :fechaInicioOt");
                    }
                }

            }
        }

        // fecha creacion ot hija onyx FECHA_CREACION_OT_HIJA_ONYX
        if (params.get("filtroFechas").equals(Constant.FECHA_CREACION_OT_HIJA_ONYX)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', onyx_aud.fecha_Creacion_Ot_Hija_Onyx) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', onyx_aud.fecha_Creacion_Ot_Hija_Onyx) = :fechaInicioOt");
                    }
                }

            }
        }

        // fecha creacion onyx
        if (params.get("filtroFechas").equals(Constant.FECHA_CREACION_ONYX)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', onyx_aud.fechaCreacion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', onyx_aud.fechaCreacion) = :fechaInicioOt");
                    }
                }
            }
        }
        // fecha de agendamiento OFSC FECHA_AGENDA
        if (params.get("filtroFechas").equals(Constant.FECHA_AGENDACMIENTO_OFSC)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', ag_aud.fechaAgenda) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag_aud.fechaAgenda) = :fechaInicioOt");
                    }
                }
            }
        }
        //fecha asignacion del tecnico FECHA_INICIO_VT
        if (params.get("filtroFechas").equals(Constant.FECHA_ASIGNACION_TECNICO_OFSC)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', ag_aud.fechaAsigTecnico) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag_aud.fechaAsigTecnico) = :fechaInicioOt");
                    }
                }
            }
        }

        //fecha cierre de agenda FECHA_FIN_VT
        if (params.get("filtroFechas").equals(Constant.FECHA_CIERRE_AGENDA_OFSC)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', ag_aud.fechaFinVt) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag_aud.fechaFinVt) = :fechaInicioOt");
                    }
                }
            }
        }
        //fecha cancelacion de agenda, estado cancelada id=22998 
        if (params.get("filtroFechas").equals(Constant.FECHA_CANCELACION_AGENDA_OFSC)) {
            if (params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty()) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc', ag_aud.fechaEdicion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag_aud.fechaEdicion) = :fechaInicioOt");
                    }
                }
                CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
                CmtBasicaMgl agendaCancelada = null;
                try {
                    agendaCancelada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_CANCELADA);
                } catch (ApplicationException ex) {
                    String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
                    LOGGER.error(msgError, ex);
                }
                if (agendaCancelada != null) {
                    sql.append(" AND  ag_aud.basicaIdEstadoAgenda.basicaId  = ").append(agendaCancelada.getBasicaId());
                }

            }
        }
        //fecha de reagendamiento  
        if (params.get("filtroFechas").equals(Constant.FECHA_REAGENDAMIENTO_OFSC)) {
            if ((params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty())) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc',  ag_aud.fechaReagenda) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag_aud.fechaReagenda) = :fechaInicioOt");
                    }
                }
            }
        }
        //fecha de suspencion
        if (params.get("filtroFechas").equals(Constant.FECHA_SUSPENSION_OFSC)) {
            if ((params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty())) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc',  ag_aud.fechaSuspendeVt) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', ag_aud.fechaSuspendeVt) = :fechaInicioOt");
                    }
                }

            }
        }
        // numero de OT
        if (params.get("otIni") != null && (params.get("otFin") != null)) {
            sql.append(" and ot_aud.otIdObj.idOt BETWEEN :otIni and  :otFin ");
        }
        if (subTipoOrdenList != null && !subTipoOrdenList.isEmpty()) {
            sql.append("  AND  ot_aud.basicaIdTipoTrabajo.basicaId  IN :subTipoOrdenList  ");
        }
        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            sql.append("  AND  ot_aud.tipoOtObj.idTipoOt  IN :tipoOrdenList  ");
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            sql.append("  AND  ag_aud.subTipoWorkFoce  IN :subOrdenList  ");
        }
        if (tipoOrdenListOFSC != null && !tipoOrdenListOFSC.isEmpty()) {
            sql.append("  AND  ot_aud.basicaIdTipoTrabajo.basicaId  IN :tipoOrdenListOFSC  ");
        }
        // estados internos cm y dir
        if (listEstadosOtCmDirSelected != null && !listEstadosOtCmDirSelected.isEmpty()) {
            sql.append("  AND  ot_aud.estadoInternoObj.basicaId  IN :listEstadosOtCmDirSelected ");
        }
        if (listEstadosSelected != null && !listEstadosSelected.isEmpty()) {
            sql.append("  AND  ot_aud.estadoGeneralOt  IN :listEstadosSelected ");
        }
        
        // regional MER
        if (regionalList != null && !regionalList.isEmpty()) {
            sql.append("  AND  ot_aud.cmObj.division  IN :regionalList  ");
        }
        // ciudad MER
        if (ciudadList != null && !ciudadList.isEmpty()) {
            sql.append("  AND  ot_aud.cmObj.comunidad  IN :ciudadList  ");
        }
        // segmento 
        if (listSegmento != null && !listSegmento.isEmpty()) {
            sql.append("  AND oy.segmento_Onyx  IN :listSegmento ");
        }

        // codigo de proyecto
        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            sql.append("  AND  onyx_aud.codigoProyecto  = :codProyecto");
        }
        // Nit Cliente
        if (params.get("nitCliente") != null && !params.get("nitCliente").toString().isEmpty()) {
            sql.append("  AND onyx_aud.nit_Cliente_Onyx  like :nitCliente");

        }
        // numero Ot Padre
        if (params.get("numOtOnyxPadre") != null && !params.get("numOtOnyxPadre").toString().isEmpty()) {
            sql.append("  AND  (onyx_aud.Onyx_Ot_Padre_Cm  = :numOtOnyxPadre or oy.Onyx_Ot_Padre_Dir  = :numOtOnyxPadre )");

        }
        // numero Ot Hija
        if (params.get("numeroOtOnyxHija") != null && !params.get("numeroOtOnyxHija").toString().isEmpty()) {

            sql.append("  AND  (onyx_aud.Onyx_Ot_Hija_Dir  = :numeroOtOnyxHija or oy.Onyx_Ot_Hija_Cm  = :numeroOtOnyxHija )");

        }
        // nombre del cliente
        if (params.get("nombreCliente") != null && !params.get("nombreCliente").toString().isEmpty()) {

            sql.append("  AND  UPPER(onyx_aud.nombre_Cliente_Onyx)  like UPPER(:nombreCliente)");

        }

        // Tipo de solucion
        if (listaSolucion != null && !listaSolucion.equals("")) {
            sql.append("  AND  onyx_aud.tipo_Servicio_Onyx   = :listaSolucion ");

        }

        
    }
/**
     * M&eacute;todo para adicionar los par&aacute;metros de b&uacute;squeda en
     * en el reporte de ordenes de Cm
     *
     * @param q {@link Query} objeto de jpa para realizar la consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     */
    private void agregarParametrosHistorico(Query q, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> subTipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOFSC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<BigDecimal> estadosList = (List<BigDecimal>) params.get("estadoInternolist");
         List<String> regionalList = (List<String>) params.get("listRegionalSelected");
        List<String> ciudadList = (List<String>) params.get("listRrCiudadesSelected");
        String valor = (String) params.get("valor");
        String codigoProyecto = (String) params.get("codProyecto");
        String nitCliente = (String) params.get("nitCliente");
        String numOtOnyxPadre = (String) params.get("numOtOnyxPadre");
        String numeroOtOnyxHija = (String) params.get("numeroOtOnyxHija");
        String nombreCliente = (String) params.get("nombreCliente");
        List<String> listSegmento = (List<String>) params.get("listSegmentoSelected");
        List<String> listEstadosOtCmDirSelected = (List<String>) params.get("listEstadosOtCmDirSelected");
        List<BigDecimal> listEstadosSelected = (List<BigDecimal>) params.get("listEstadosSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");
        
        // cm
        // fecha de Creacion orden OT ,
        if (params.get("filtroFechas") != null && !params.get("filtroFechas").equals("")) {
            if (params.get("fechaInicioOt") != null && params.get("fechaFinOt") != null) {
                if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                    q.setParameter("fechaInicioOt", (Date) params.get("fechaInicioOt"));
                    q.setParameter("fechaFinOt", (Date) params.get("fechaFinOt"));
                } else {
                    q.setParameter("fechaInicioOt", (Date) params.get("fechaInicioOt"));
                }

            }
        }

        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            q.setParameter("tipoOrdenList", (tipoOrdenList.isEmpty()) ? null : tipoOrdenList);
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            q.setParameter("subOrdenList", (subOrdenList.isEmpty()) ? null : subOrdenList);
        }
        if (subTipoOrdenList != null && !subTipoOrdenList.isEmpty()) {
            q.setParameter("subTipoOrdenList", (tipoOrdenList.isEmpty()) ? null : subTipoOrdenList);
        }

        if (tipoOrdenListOFSC != null && !tipoOrdenListOFSC.isEmpty()) {
            q.setParameter("tipoOrdenListOFSC", (tipoOrdenListOFSC.isEmpty()) ? null : tipoOrdenListOFSC);
        }
        if (listEstadosOtCmDirSelected != null && !listEstadosOtCmDirSelected.isEmpty()) {
            q.setParameter("listEstadosOtCmDirSelected", (listEstadosOtCmDirSelected.isEmpty()) ? null : listEstadosOtCmDirSelected);
        }
        if (listEstadosSelected != null && !listEstadosSelected.isEmpty()) {
            q.setParameter("listEstadosSelected", (listEstadosSelected.isEmpty()) ? null : listEstadosSelected);
        }

        if ((params.get("otIni") != null)) {
            q.setParameter("otIni",
                    (null == params.get("otIni")) ? null
                    : params.get("otIni"));
        }
        if ((params.get("otFin") != null)) {
            q.setParameter("otFin",
                    (null == params.get("otFin")) ? null
                    : params.get("otFin"));
        }
        // regional MER
        if (regionalList != null && !regionalList.isEmpty()) {
            q.setParameter("regionalList", (regionalList.isEmpty()) ? null : regionalList);
        }
        // ciudad MER
        if (ciudadList != null && !ciudadList.isEmpty()) {
            q.setParameter("ciudadList", (ciudadList.isEmpty()) ? null : ciudadList);
        }
        // Segmento
        if (listSegmento != null && !listSegmento.isEmpty()) {
            q.setParameter("listSegmento", (listSegmento.isEmpty()) ? null : listSegmento);
        }

        // codigo de proyecto
        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            q.setParameter("codProyecto", valor.equals("") ? "" : valor);

        }
        // Nit Cliente
        if (nitCliente != null && !nitCliente.isEmpty()) {
            q.setParameter("nitCliente", valor.equals("") ? "" : valor);

        }
        // numero Ot Padre
        if (numOtOnyxPadre != null && !numOtOnyxPadre.isEmpty()) {
            q.setParameter("numOtOnyxPadre", valor.equals("") ? "" : valor);
        }
        // numero Ot Hija
        if (numeroOtOnyxHija != null && !numeroOtOnyxHija.isEmpty()) {
            q.setParameter("numeroOtOnyxHija", (valor.equals("")) ? "" : valor);
        }
        // nombre del cliente
        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            q.setParameter("nombreCliente", (valor.equals("")) ? "" : "%" + valor.toUpperCase() + "%");
        }

        // tipo solucion
        if (listaSolucion != null && !listaSolucion.isEmpty()) {
            q.setParameter("listaSolucion", (listaSolucion.isEmpty()) ? null : listaSolucion);
        }
    }
    
     public Integer getCountReporteHistoricoOtCm(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
  
        StringBuilder sql = new StringBuilder();
        List<Object[]> listaOrdenesHistoricoAgendas = null;

         sql.append("SELECT distinct o , ot_aud, a ,ag_aud, oy ,onyx_aud  "
                + "FROM CmtOrdenTrabajoMgl o  "
                + "left join o.listAgendas a "
                + "left join o.listOnyx oy "
                + "left join o.listAuditoria ot_aud "
                + "left join a.listAuditoria  ag_aud "
                + "left join oy.listAuditoria onyx_aud "
                + "WHERE (o.estadoRegistro is null or o.estadoRegistro = 1 )"
                + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");
        
        generarSelectReporteHistoricoOtCm(sql, params, true);
        Query q = entityManager.createQuery(sql.toString());
        agregarParametrosHistorico(q, params, true);
        q.setFirstResult(firstResult);
        q.setMaxResults(maxResults);
        listaOrdenesHistoricoAgendas = (List<Object[]>) q.getResultList();   

        Integer resultCount = 0;
        if (listaOrdenesHistoricoAgendas != null && !listaOrdenesHistoricoAgendas.isEmpty()) {
            resultCount = (Integer)listaOrdenesHistoricoAgendas.size();
        }
         getEntityManager().clear();
        return resultCount;        
        
    }
     
         
     

    public List<ReporteHistoricoOtCmDto> getReporteHistoricoOtCM(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        List<ReporteHistoricoOtCmDto> listReporteHistoricoOtCmDto = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        List<Object[]> listaOrdenesHistoricoAgendas = null;
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        CmtTipoBasicaMgl subtipoOden = cmtTipoBasicaMglDaoImpl.findByCodigoInternoApp(
                Constant.TIPO_BASICA_SUB_TIPO_ORDEN_OFSC);
        List<CmtBasicaMgl> listaBasica = cmtBasicaMglDaoImpl.findByTipoBasica(subtipoOden);
        
                sql.append("SELECT distinct o , ot_aud, a ,ag_aud, oy ,onyx_aud  "
                + "FROM CmtOrdenTrabajoMgl o  "
                + "left join o.listAgendas a "
                + "left join o.listOnyx oy "
                + "left join o.listAuditoria ot_aud "
                + "left join a.listAuditoria  ag_aud "
                + "left join oy.listAuditoria onyx_aud "
                + "WHERE (o.estadoRegistro is null or o.estadoRegistro = 1 )"
                + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");
        
        generarSelectReporteHistoricoOtCm(sql, params, true);
        Query q = entityManager.createQuery(sql.toString());
        agregarParametrosHistorico(q, params, true);
        q.setFirstResult(firstResult);
        q.setMaxResults(maxResults);
        listaOrdenesHistoricoAgendas = (List<Object[]>) q.getResultList();   
        
        for (Object[] orden : listaOrdenesHistoricoAgendas) {                       
            ReporteHistoricoOtCmDto reporteHistoricoOtCmDto = new ReporteHistoricoOtCmDto();
            // ordenes de cm
            reporteHistoricoOtCmDto.setOt_Id_Cm( ((CmtOrdenTrabajoMgl) orden[0]).getIdOt() != null 
                    ? ((CmtOrdenTrabajoMgl) orden[0]).getIdOt().toString() : "");
            reporteHistoricoOtCmDto.setTipo_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getTipoOtObj() != null 
                    ? ((CmtOrdenTrabajoMgl) orden[0]).getTipoOtObj().getDescTipoOt() : "");
            reporteHistoricoOtCmDto.setSub_tipo_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTipoTrabajo() != null 
                    ? ((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTipoTrabajo().getNombreBasica() : "" );
            reporteHistoricoOtCmDto.setEstado_interno_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj() != null 
                    ? ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj().getNombreBasica() : "");

            SimpleDateFormat fecha_creacion_OT_MER = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String fechaCreacion;
            if (((CmtOrdenTrabajoMgl) orden[0]) != null && ((CmtOrdenTrabajoMgl) orden[0]).getFechaCreacion() != null) {
                fechaCreacion = fecha_creacion_OT_MER.format(((CmtOrdenTrabajoMgl) orden[0]).getFechaCreacion());
            }else{
                fechaCreacion = "";
            } 
            
            SimpleDateFormat fecha_modificacion_OT_MER = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String fechaModificacion = null;
            if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]) != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaEdicion() != null) {
                fechaModificacion = fecha_modificacion_OT_MER.format(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaEdicion());
            }else{
                fechaModificacion = "";
            } 
            
            reporteHistoricoOtCmDto.setFecha_creacion_OT_MER(fechaCreacion);
            reporteHistoricoOtCmDto.setFechaModificacionOtMer(fechaModificacion);
            
            reporteHistoricoOtCmDto.setSegmento_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getSegmento() != null 
                    && ((CmtOrdenTrabajoMgl) orden[0]).getSegmento().getNombreBasica() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getSegmento().getNombreBasica() : "");
            reporteHistoricoOtCmDto.setTecnologia_OT_MGL(((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTecnologia() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTecnologia().getNombreBasica()
                    : "");
            reporteHistoricoOtCmDto.setCmObj(((CmtOrdenTrabajoMgl) orden[0]).getCmObj()!= null 
                    && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getCuentaId() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getCuentaId().substring(7): "");
            reporteHistoricoOtCmDto.setDireccionMer(((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null 
                    && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal() == null
                    && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal().getDireccionObj()!= null ? "" 
                    : ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac());
            reporteHistoricoOtCmDto.setNombreCM(((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null 
                    && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNombreCuenta() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNombreCuenta() : "");
            reporteHistoricoOtCmDto.setCodigoCMR(((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null 
                    && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNumeroCuenta() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNumeroCuenta().toString(): "");
            reporteHistoricoOtCmDto.setUsuario_Creacion_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getUsuarioCreacion()== null ? "" 
                    : ((CmtOrdenTrabajoMgl) orden[0]).getUsuarioCreacion());
            reporteHistoricoOtCmDto.setOnyx_Ot_Hija_Cm(((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija() == null 
                    && ((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija()== null ? "" : ((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija().toString());
            String complejidadDescripcion = "";
            if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("A")) {
                complejidadDescripcion = "ALTA";
            } else if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("M")) {
                complejidadDescripcion = "MEDIA";
            } else if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("B")) {
                complejidadDescripcion = "BAJA";
            } else {
                complejidadDescripcion = "";
            }
            reporteHistoricoOtCmDto.setComplejidadServicio(complejidadDescripcion);
            
            String departamento = "";
            if (((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento() != null) {
                ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento().getGpoNombre();
                departamento = (((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento().getGpoNombre());
            }
            reporteHistoricoOtCmDto.setDepartamento(departamento);
            reporteHistoricoOtCmDto.setUsuarioModOt(((CmtOrdenTrabajoMgl) orden[0]).getUsuarioEdicion());
            
            // ot auditoria
            if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]) != null) {
                reporteHistoricoOtCmDto.setSub_tipo_OT_MER_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getBasicaIdTipoTrabajo() != null 
                        ? ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getBasicaIdTipoTrabajo().getNombreBasica(): "");
                reporteHistoricoOtCmDto.setSegmento_OT_MER_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getSegmento() != null 
                        ? ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getSegmento().getNombreBasica(): "");
                reporteHistoricoOtCmDto.setOnyx_Ot_Hija_Cm_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getOnyxOtHija() != null 
                        ? ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getOnyxOtHija().toString() : "");
                SimpleDateFormat fecha_creacion_OT_MER_Aud = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fechaCreacion_Mer_Aud = "";
                if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]) != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaCreacion() != null) {
                    fechaCreacion_Mer_Aud = fecha_creacion_OT_MER_Aud.format(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaCreacion());
                }
                reporteHistoricoOtCmDto.setFecha_Creacion_OT_MER_Aud(fechaCreacion_Mer_Aud);
                reporteHistoricoOtCmDto.setEstado_interno_OT_MER_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]) == null 
                        && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getEstadoInternoObj()== null ? "" 
                        : ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getEstadoInternoObj().getNombreBasica());
                String complejidadDescripcionAud = "";
                if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio() != null 
                        && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio().equals("A")) {
                    complejidadDescripcionAud = "ALTA";
                } else if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio() != null 
                        && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio().equals("M")) {
                    complejidadDescripcionAud = "MEDIA";
                } else if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio() != null 
                        && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio().equals("B")) {
                    complejidadDescripcionAud = "BAJA";
                } else {
                    complejidadDescripcionAud = "";
                }
                reporteHistoricoOtCmDto.setComplejidadServicioAud(complejidadDescripcionAud);
                reporteHistoricoOtCmDto.setUsuarioModOtAud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getUsuarioEdicion() != null 
                        ? ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getUsuarioEdicion(): "");
            }
            // agendamiento
            if (((CmtAgendamientoMgl) orden[2]) != null) {
                reporteHistoricoOtCmDto.setOrden_RR(((CmtAgendamientoMgl) orden[2]).getIdOtenrr() == null 
                        ? ((CmtAgendamientoMgl) orden[2]).getIdOtenrr() :"" );
                    //sub tipo ordenes ofsc
                if (listaBasica != null && !listaBasica.isEmpty()) {
                    for (CmtBasicaMgl cmtBasicaMgl : listaBasica) {
                        if (cmtBasicaMgl.getCodigoBasica() != null && ((CmtAgendamientoMgl) orden[2]).getSubTipoWorkFoce() != null) {
                            if (((CmtAgendamientoMgl) orden[2]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                                reporteHistoricoOtCmDto.setSubTipo_Orden_OFSC(((CmtAgendamientoMgl) orden[2]).getSubTipoWorkFoce().trim());
                                break;
                            }
                        }

                    }
                }
                
                reporteHistoricoOtCmDto.setUsuario_creacion_agenda_OFSC((((CmtAgendamientoMgl) orden[2]).getUsuarioCreacion() != null 
                        ? ((CmtAgendamientoMgl) orden[2]).getUsuarioCreacion() : ""));
               
                String timeS = ((CmtAgendamientoMgl) orden[2]).getTimeSlot() != null ? ((CmtAgendamientoMgl) orden[2]).getTimeSlot(): "";
                if (timeS != null && !timeS.equals("")) {
                    if (timeS.contains("Durante")) {
                        reporteHistoricoOtCmDto.setTime_slot_OFSC(timeS);
                    } else {
                        timeS = timeS.replace("-", "_");
                        reporteHistoricoOtCmDto.setTime_slot_OFSC(timeS);
                    }
                }
                 
                reporteHistoricoOtCmDto.setAppt_number_OFSC(((CmtAgendamientoMgl) orden[2]).getOfpsOtId() != null 
                        ? ((CmtAgendamientoMgl) orden[2]).getOfpsOtId() : "");
                reporteHistoricoOtCmDto.setEstado_agenda_OFSC(((CmtAgendamientoMgl) orden[2]).getBasicaIdEstadoAgenda() != null 
                        && ((CmtAgendamientoMgl) orden[2]).getBasicaIdEstadoAgenda().getNombreBasica() != null 
                        ? ((CmtAgendamientoMgl) orden[2]).getBasicaIdEstadoAgenda().getNombreBasica() : "");

                SimpleDateFormat fecha_inicia_agenda = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fechaInivioVt = "";
                if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaInivioVt() != null) {
                    fechaInivioVt = fecha_inicia_agenda.format(((CmtAgendamientoMgl) orden[2]).getFechaInivioVt());
                } 
                reporteHistoricoOtCmDto.setFecha_inicia_agenda_OFSC(fechaInivioVt);
                
                SimpleDateFormat format_fecha_fin_agenda = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fechaFinVt = "";
                if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaFinVt() != null) {
                    fechaFinVt = format_fecha_fin_agenda.format(((CmtAgendamientoMgl) orden[2]).getFechaFinVt());
                }                
                reporteHistoricoOtCmDto.setFecha_fin_agenda_OFSC(fechaFinVt);
                
                reporteHistoricoOtCmDto.setId_aliado_OFSC(((CmtAgendamientoMgl) orden[2]).getIdentificacionAliado() != null 
                        ? ((CmtAgendamientoMgl) orden[2]).getIdentificacionAliado() : "");
                reporteHistoricoOtCmDto.setNombre_aliado_OFSC(((CmtAgendamientoMgl) orden[2]).getNombreAliado() != null 
                        ? ((CmtAgendamientoMgl) orden[2]).getNombreAliado() : "");
                reporteHistoricoOtCmDto.setId_tecnico_aliado_OFSC((((CmtAgendamientoMgl) orden[2]).getIdentificacionTecnico() != null 
                        ?  ((CmtAgendamientoMgl) orden[2]).getIdentificacionTecnico() :""));
                reporteHistoricoOtCmDto.setNombre_tecnico_aliado_OFSC((((CmtAgendamientoMgl) orden[2]).getNombreTecnico() != null 
                        ? ((CmtAgendamientoMgl) orden[2]).getNombreTecnico(): "" ));
                reporteHistoricoOtCmDto.setUltima_agenda_multiagenda(((CmtAgendamientoMgl) orden[2]).getUltimaAgenda() != null 
                        ? ((CmtAgendamientoMgl) orden[2]).getUltimaAgenda(): "" );
                
                String obs = (((CmtAgendamientoMgl) orden[2]).getObservacionesTecnico() == null ? ""
                        : ((CmtAgendamientoMgl) orden[2]).getObservacionesTecnico());
                if (obs != null && !obs.equals("")) {
                    obs = StringUtils.caracteresEspeciales(obs);
                    reporteHistoricoOtCmDto.setObservaciones_tecnico_OFSC(obs);
                }
                 SimpleDateFormat fecha_agenda = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fechaAgenda = "";
                if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaAgenda()!= null) {
                    fechaAgenda = fecha_agenda.format(((CmtAgendamientoMgl) orden[2]).getFechaAgenda());
                } 
                reporteHistoricoOtCmDto.setFecha_agenda_OFSC(fechaAgenda);
                reporteHistoricoOtCmDto.setUsuarioModAgenda(((CmtAgendamientoMgl) orden[2]).getUsuarioEdicion() != null 
                        ? ((CmtAgendamientoMgl) orden[2]).getUsuarioEdicion(): "");
              
            }
            if (((CmtAgendaAuditoria) orden[3]) != null) {
                
                //sub tipo ordenes ofsc
                if (listaBasica != null && !listaBasica.isEmpty()) {
                    for (CmtBasicaMgl cmtBasicaMgl : listaBasica) {
                        if (cmtBasicaMgl.getCodigoBasica() != null && ((CmtAgendaAuditoria) orden[3]).getSubTipoWorkFoce() != null) {
                            if (((CmtAgendaAuditoria) orden[3]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                                reporteHistoricoOtCmDto.setSubTipo_Orden_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getSubTipoWorkFoce().trim());
                                break;
                            }
                        }

                    }
                }
                
                reporteHistoricoOtCmDto.setEstado_agenda_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getBasicaIdEstadoAgenda() != null 
                       ? ((CmtAgendaAuditoria) orden[3]).getBasicaIdEstadoAgenda().getNombreBasica(): "" );
                reporteHistoricoOtCmDto.setId_aliado_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getIdentificacionAliado() != null 
                        ? ((CmtAgendaAuditoria) orden[3]).getIdentificacionAliado(): "");
                reporteHistoricoOtCmDto.setNombre_aliado_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getNombreAliado() != null 
                        ? ((CmtAgendaAuditoria) orden[3]).getNombreAliado() : "");
                reporteHistoricoOtCmDto.setId_tecnico_aliado_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getIdentificacionTecnico() != null
                        ? ((CmtAgendaAuditoria) orden[3]).getIdentificacionTecnico() : "");
                reporteHistoricoOtCmDto.setNombre_tecnico_aliado_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getNombreTecnico() != null
                        ? ((CmtAgendaAuditoria) orden[3]).getNombreTecnico(): "" );
                reporteHistoricoOtCmDto.setUsuarioModAgendaAud(((CmtAgendaAuditoria) orden[3]).getUsuarioEdicion());
            }
            // datos onyx
            if (((OnyxOtCmDir) orden[4]) != null) {
                reporteHistoricoOtCmDto.setNit_Cliente_Onyx(((OnyxOtCmDir) orden[4]).getNit_Cliente_Onyx() != null
                        ? ((OnyxOtCmDir) orden[4]).getNit_Cliente_Onyx() : "");
               
                String nombreCiente = (((OnyxOtCmDir) orden[4]).getNombre_Cliente_Onyx() != null
                        ? ((OnyxOtCmDir) orden[4]).getNombre_Cliente_Onyx() : "");
                if (nombreCiente != null && !nombreCiente.equals("")) {
                    nombreCiente = StringUtils.caracteresEspeciales(nombreCiente);
                    reporteHistoricoOtCmDto.setNombre_Cliente_Onyx(nombreCiente);
                }
                reporteHistoricoOtCmDto.setOnyx_Ot_Hija_Cm((((OnyxOtCmDir) orden[4]).getOnyx_Ot_Hija_Cm() != null 
                        ? "" : ((OnyxOtCmDir) orden[4]).getOnyx_Ot_Hija_Cm()));
                SimpleDateFormat creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fecha_Creacion_Ot_Hija_Onyx = "";
                if (((OnyxOtCmDir) orden[4]) != null && (((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx() != null)) {
                    fecha_Creacion_Ot_Hija_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx());
                } 
                reporteHistoricoOtCmDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx);
               
                String direccionOnyx = (((OnyxOtCmDir) orden[4]).getDireccion_Onyx() != null
                        ? ((OnyxOtCmDir) orden[4]).getDireccion_Onyx() : "");
                if (direccionOnyx != null && !direccionOnyx.equals("")) {
                    direccionOnyx = StringUtils.caracteresEspeciales(direccionOnyx);
                    reporteHistoricoOtCmDto.setDireccion_Onyx(direccionOnyx);
                }
                reporteHistoricoOtCmDto.setDescripcion_Onyx(((OnyxOtCmDir) orden[4]).getDescripcion_Onyx() != null 
                        ?  ((OnyxOtCmDir) orden[4]).getDescripcion_Onyx() : "");
                reporteHistoricoOtCmDto.setSegmento_Onyx(((OnyxOtCmDir) orden[4]).getSegmento_Onyx() != null 
                        ? ((OnyxOtCmDir) orden[4]).getSegmento_Onyx(): "" );
                reporteHistoricoOtCmDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[4]).getTipo_Servicio_Onyx() != null 
                        ? ((OnyxOtCmDir) orden[4]).getTipo_Servicio_Onyx() : "" );
                reporteHistoricoOtCmDto.setServicios_Onyx(((OnyxOtCmDir) orden[4]).getServicios_Onyx() != null 
                        ? ((OnyxOtCmDir) orden[4]).getServicios_Onyx() : "");
                reporteHistoricoOtCmDto.setRecurrente_Mensual_Onyx(((OnyxOtCmDir) orden[4]).getRecurrente_Mensual_Onyx() != null 
                        ? ((OnyxOtCmDir) orden[4]).getRecurrente_Mensual_Onyx() : "");
                reporteHistoricoOtCmDto.setCodigo_Servicio_Onyx(((OnyxOtCmDir) orden[4]).getCodigo_Servicio_Onyx() != null 
                        ? ((OnyxOtCmDir) orden[4]).getCodigo_Servicio_Onyx() : "");
                reporteHistoricoOtCmDto.setVendedor_Onyx(((OnyxOtCmDir) orden[4]).getVendedor_Onyx() != null 
                        ? ((OnyxOtCmDir) orden[4]).getVendedor_Onyx() : "");
               
                String telefonoVend = (((OnyxOtCmDir) orden[4]).getTelefono_Vendedor_Onyx() != null 
                        ?  ((OnyxOtCmDir) orden[4]).getTelefono_Vendedor_Onyx() : "");
                if (telefonoVend != null && !telefonoVend.equals("")) {
                    telefonoVend = StringUtils.caracteresEspeciales(telefonoVend);
                    reporteHistoricoOtCmDto.setTelefono_Vendedor_Onyx(telefonoVend);
                }

                reporteHistoricoOtCmDto.setEstado_Ot_Hija_Onyx_Cm(((OnyxOtCmDir) orden[4]).getEstado_Ot_Hija_Onyx_Cm() != null
                        ?  ((OnyxOtCmDir) orden[4]).getEstado_Ot_Hija_Onyx_Cm() : "");
                reporteHistoricoOtCmDto.setEstado_Ot_Padre_Onyx_Cm(((OnyxOtCmDir) orden[4]).getEstado_Ot_Padre_Onyx_Cm() != null 
                        ? ((OnyxOtCmDir) orden[4]).getEstado_Ot_Padre_Onyx_Cm(): "");
                SimpleDateFormat compromiso_Ot_Padre_Onyx = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fecha_Compromiso_Ot_Padre_Onyx = "";
                if (((OnyxOtCmDir) orden[4]) != null && (((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx() != null)) {
                    fecha_Compromiso_Ot_Padre_Onyx = compromiso_Ot_Padre_Onyx.format(((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Compromiso_Ot_Padre_Onyx(fecha_Compromiso_Ot_Padre_Onyx);
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_1_Onyx((((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_1_Onyx() != null 
                        ?  ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_1_Onyx() :""));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_2_Onyx((((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_2_Onyx() != null 
                        ? ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_1_Onyx(): ""));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_3_Onyx((((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_3_Onyx() != null  
                        ? ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_2_Onyx(): ""));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_4_Onyx((((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_4_Onyx()!= null  
                        ?  ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_3_Onyx(): ""));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_1_Onyx((((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_1_Onyx() != null  
                        ? ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_1_Onyx(): ""));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_2_Onyx((((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_2_Onyx() != null  
                        ? ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_2_Onyx(): ""));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_3_Onyx((((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_3_Onyx() != null  
                        ? ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_3_Onyx(): ""));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_4_Onyx((((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_4_Onyx()!= null  
                        ? ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_4_Onyx() : ""));

                reporteHistoricoOtCmDto.setOnyx_Ot_Padre_Cm((((OnyxOtCmDir) orden[4]).getOnyx_Ot_Padre_Cm() != null 
                        ?  ((OnyxOtCmDir) orden[4]).getOnyx_Ot_Padre_Cm() : ""));
                SimpleDateFormat fecha_Padre_Onyx = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fecha_Creacion_Ot_Padre_Onyx = "";
                if (((OnyxOtCmDir) orden[4]) != null && (((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Padre_Onyx() != null)) {
                    fecha_Creacion_Ot_Padre_Onyx = fecha_Padre_Onyx.format(((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Padre_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Creacion_Ot_Padre_Onyx(fecha_Creacion_Ot_Padre_Onyx);
                reporteHistoricoOtCmDto.setContacto_Tecnico_Ot_Padre_Onyx((((OnyxOtCmDir) orden[4]).getContacto_Tecnico_Ot_Padre_Onyx() != null 
                        ? ((OnyxOtCmDir) orden[4]).getContacto_Tecnico_Ot_Padre_Onyx(): ""));
               
                String telefono = ((((OnyxOtCmDir) orden[4]).getTelefono_Tecnico_Ot_Padre_Onyx() != null 
                        ? ((OnyxOtCmDir) orden[4]).getTelefono_Tecnico_Ot_Padre_Onyx(): ""));
                if (telefono != null && !telefono.equals("")) {
                    telefono = StringUtils.caracteresEspeciales(telefono);
                    reporteHistoricoOtCmDto.setTelefono_Tecnico_Ot_Padre_Onyx(telefono);
                }
            }
            //auditoria
            if (((OnyxOtCmDirAuditoria) orden[5]) != null) {

                reporteHistoricoOtCmDto.setNit_Cliente_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]) != null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getNit_Cliente_Onyx() : ""));
                reporteHistoricoOtCmDto.setNombre_Cliente_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]) != null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getNombre_Cliente_Onyx() : ""));
                reporteHistoricoOtCmDto.setOnyx_Ot_Hija_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]) != null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Hija_Cm(): ""));
                SimpleDateFormat format_Fecha_Creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                
                String fecha_Creacion_Ot_Hija_Onyx_Aud = "";
                if (((OnyxOtCmDirAuditoria) orden[5]) != null && (((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Hija_Onyx() != null)) {
                    fecha_Creacion_Ot_Hija_Onyx_Aud = format_Fecha_Creacion_Ot_Hija_Onyx.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Hija_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx_Aud);
                reporteHistoricoOtCmDto.setDireccion_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getDireccion_Onyx() != null  
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getDireccion_Onyx(): "" ));
                reporteHistoricoOtCmDto.setDescripcion_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getDescripcion_Onyx() != null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getDescripcion_Onyx() : "" ));
                reporteHistoricoOtCmDto.setSegmento_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getSegmento_Onyx() != null  
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getSegmento_Onyx() :"" ));
                reporteHistoricoOtCmDto.setTipo_Servicio_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getTipo_Servicio_Onyx()!= null  
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getTipo_Servicio_Onyx() : ""));
                reporteHistoricoOtCmDto.setServicios_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getServicios_Onyx() != null  
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getServicios_Onyx() : ""));
                reporteHistoricoOtCmDto.setRecurrente_Mensual_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getRecurrente_Mensual_Onyx() != null  
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getRecurrente_Mensual_Onyx() : ""));
                reporteHistoricoOtCmDto.setCodigo_Servicio_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getCodigo_Servicio_Onyx() != null  
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getCodigo_Servicio_Onyx() : ""));
                reporteHistoricoOtCmDto.setVendedor_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getVendedor_Onyx() != null  
                        ?((OnyxOtCmDirAuditoria) orden[5]).getVendedor_Onyx() : ""));
                reporteHistoricoOtCmDto.setTelefono_Vendedor_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Vendedor_Onyx() != null  
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Vendedor_Onyx() : ""));
                reporteHistoricoOtCmDto.setEstado_Ot_Hija_Onyx_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Hija_Onyx_Cm()!= null  
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Hija_Onyx_Cm() : ""));
                reporteHistoricoOtCmDto.setEstado_Ot_Padre_Onyx_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Padre_Onyx_Cm()!= null  
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Padre_Onyx_Cm() :"" ));
                SimpleDateFormat compromiso_Ot_Padre_Onyx_Aud = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                
                String fecha_Compromiso_Ot_Padre_Onyx_Aud = "";
                if (((OnyxOtCmDirAuditoria) orden[5]) != null && (((OnyxOtCmDirAuditoria) orden[5]).getFecha_Compromiso_Ot_Padre_Onyx() != null)) {
                    fecha_Compromiso_Ot_Padre_Onyx_Aud = compromiso_Ot_Padre_Onyx_Aud.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Compromiso_Ot_Padre_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Compromiso_Ot_Padre_Onyx_Aud(fecha_Compromiso_Ot_Padre_Onyx_Aud);
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_1_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx() != null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx() : ""));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_2_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_2_Onyx() != null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx() : ""));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_3_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_3_Onyx()!= null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_2_Onyx() : ""));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_4_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_4_Onyx() != null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_3_Onyx() : ""));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_1_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_1_Onyx() != null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_1_Onyx() : ""));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_2_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_2_Onyx() != null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_2_Onyx() : ""));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_3_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_3_Onyx() != null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_3_Onyx() : ""));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_4_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_4_Onyx() != null
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_4_Onyx() : ""));
                reporteHistoricoOtCmDto.setOnyx_Ot_Padre_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Padre_Cm() == null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Padre_Cm() : ""));
                SimpleDateFormat fecha_Padre_Onyx_aud = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                
                String fecha_Creacion_OT_Padre_Onyx_Aud = "";
                if (((OnyxOtCmDirAuditoria) orden[5]) != null && (((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Padre_Onyx() != null)) {
                    fecha_Creacion_OT_Padre_Onyx_Aud = fecha_Padre_Onyx_aud.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Padre_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Creacion_OT_Padre_Onyx_Aud(fecha_Creacion_OT_Padre_Onyx_Aud);
                reporteHistoricoOtCmDto.setContacto_Tecnico_Ot_Padre_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getContacto_Tecnico_Ot_Padre_Onyx()!= null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getContacto_Tecnico_Ot_Padre_Onyx(): ""));
                reporteHistoricoOtCmDto.setTelefono_Tecnico_Ot_Padre_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Tecnico_Ot_Padre_Onyx()!= null 
                        ? ((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Tecnico_Ot_Padre_Onyx(): ""));
            }
            listReporteHistoricoOtCmDto.add(reporteHistoricoOtCmDto);
            

        }
        getEntityManager().clear();
        return listReporteHistoricoOtCmDto;
    }
    
    
        }
