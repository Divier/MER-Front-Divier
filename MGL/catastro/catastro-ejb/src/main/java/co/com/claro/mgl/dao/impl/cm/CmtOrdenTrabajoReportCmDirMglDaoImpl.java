/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.ReporteOtCMDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
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
import java.util.Locale;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author cardenaslb
 */
public class CmtOrdenTrabajoReportCmDirMglDaoImpl extends GenericDaoImpl<CmtOrdenTrabajoMgl>{
    
     private static final Logger LOGGER = LogManager.getLogger(CmtOrdenTrabajoReportCmDirMglDaoImpl.class);
    
     public int getCountReporteEstadoActualOtCM(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            StringBuilder sql2 = new StringBuilder();
            int resultCm = 0;
            int resultDir = 0;
            int total = 0;
           
             //CCMM
             sql.append("SELECT count(1) "
                    + "FROM CmtOrdenTrabajoMgl o "
                    + "left join o.listAgendas a  "
                    + "left join o.listOnyx oy "
                    + "WHERE "
                    + " (a.ordenTrabajo is null or a.ordenTrabajo is not null) "
                    + "and ( oy.ot_Id_Cm is null or oy.ot_Id_Cm is not null ) "
                    + "and (o.estadoRegistro is null or o.estadoRegistro = 1 ) "
                    + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");
            //DIRECCIONES  
            sql2.append("SELECT count(1) "
                    + "FROM OtHhppMgl o "
                    + "left join o.listAgendaDireccion a "
                    + "left join o.listOnyx oy "
                    + "left join o.tecnologiaBasicaList t "
                    + "WHERE "
                    + "(a.ordenTrabajo is null or a.ordenTrabajo is not null) "
                    + "and ( oy.ot_Direccion_Id is null or  oy.ot_Direccion_Id is not null ) "
                    + "and ( t.otHhppId is null or t.otHhppId is not null ) "
                    + "and (o.estadoRegistro is null or o.estadoRegistro = 1 ) "
                    + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");

            generarSelectReporteOtCm(sql, params, true);
            Query q = entityManager.createQuery(sql.toString());
            agregarParametros(q, params, true);
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            resultCm = q.getSingleResult() == null ? 0 : ((Long) q.getSingleResult()).intValue();
            //DIRECCIONES  
            generarSelectReporteOtDIR(sql2, params, true);
            Query q2 = entityManager.createQuery(sql2.toString());
            agregarParametrosDIR(q2, params, true);
            q2.setFirstResult(firstResult);
            q2.setMaxResults(maxResults);
            resultDir = q2.getSingleResult() == null ? 0 : ((Long) q2.getSingleResult()).intValue();
            total = resultCm +resultDir;
            getEntityManager().clear();
            return total;

        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        }catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<ReporteOtCMDto> getReporteEstadoActualOtCM(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario,List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            StringBuilder sql2 = new StringBuilder();
            List<ReporteOtCMDto> listaReporteOtCMDto = new ArrayList<ReporteOtCMDto>();
            List<Object[]> listaOrdenesAgendas = null;
            List<Object[]> listaOrdenesAgendasDir = null;
            int primerResult = (int) params.get("inicioRegistros");
            int finalResult = (int) params.get("expLonPag");
            // CCMM
             sql.append("SELECT o, a,oy "
                    + "FROM CmtOrdenTrabajoMgl o "
                    + "left join o.listAgendas a  "
                    + "left join o.listOnyx oy "
                    + "WHERE "
                    + " (a.ordenTrabajo is null or a.ordenTrabajo is not null)"
                    + "and ( oy.ot_Id_Cm is null or oy.ot_Id_Cm is not null ) "
                    + "and (o.estadoRegistro is null or o.estadoRegistro = 1 ) "
                    + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");
            //DIRECCIONES  
             sql2.append("SELECT o,a,oy, t "
                    + "FROM OtHhppMgl o "
                    + "left join o.listAgendaDireccion a "
                    + "left join o.listOnyx oy "
                    + "left join o.tecnologiaBasicaList t "
                    + "WHERE "
                    + "(a.ordenTrabajo is null or a.ordenTrabajo is not null) "
                    + "and ( oy.ot_Direccion_Id is null or  oy.ot_Direccion_Id is not null ) "
                    + "and ( t.otHhppId is null or t.otHhppId is not null ) "
                    + "and (o.estadoRegistro is null or o.estadoRegistro = 1 ) "
                    + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");

            
            generarSelectReporteOtCm(sql, params, true);
            Query q = entityManager.createQuery(sql.toString());
            agregarParametros(q, params, true);
            q.setFirstResult(primerResult);
            q.setMaxResults(finalResult);
            listaOrdenesAgendas = (List<Object[]>) q.getResultList();
           // DIRECCIONES  
            generarSelectReporteOtDIR(sql2, params, true);
            Query q2 = entityManager.createQuery(sql2.toString());
            agregarParametrosDIR(q2, params, true);
            q2.setFirstResult(primerResult);
            q2.setMaxResults(finalResult);
            listaOrdenesAgendasDir = (List<Object[]>) q2.getResultList();
           
            cargarListaOrdenesCM(listaOrdenesAgendas, listaBasicaCm, listaReporteOtCMDto, listaEstadosIntExt, listacmtRegionalMgl, listacmtComunidadRr);
            cargarListaOrdenesDir(listaOrdenesAgendasDir, listaBasicaDir, listaReporteOtCMDto);
            getEntityManager().clear();
            return listaReporteOtCMDto;

        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        }catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
      public void cargarListaOrdenesCM(List<Object[]> listaOrdenesCm, List<CmtBasicaMgl> listaBasica, 
            List<ReporteOtCMDto> listaReporteOtCMDto, List<CmtEstadoIntxExtMgl> listaEstadosIntExt,
            List<CmtRegionalRr> listacmtRegionalMgl,List<CmtComunidadRr> listacmtComunidadRr) throws ApplicationException {
        //CUENTAS MATRICES
        if (listaOrdenesCm != null && !listaOrdenesCm.isEmpty()) {

            for (Object[] orden : listaOrdenesCm) {

                ReporteOtCMDto reporteOtCMDto = new ReporteOtCMDto();
                //ordenes
                reporteOtCMDto.setOt_Id_Cm(((CmtOrdenTrabajoMgl) orden[0]).getIdOt().toString());
                reporteOtCMDto.setTipo_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getTipoOtObj() != null 
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getTipoOtObj().getDescTipoOt() :"");
                reporteOtCMDto.setSub_tipo_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTipoTrabajo()  != null 
                        ?  ((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTipoTrabajo().getNombreBasica() :"");
                reporteOtCMDto.setEstado_interno_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj()  != null 
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj().getNombreBasica() : "");
                // estado interno externo
                if (listaEstadosIntExt != null && ((CmtOrdenTrabajoMgl) orden[0]).getEstadoGeneralOt()!= null) {
                    for (CmtEstadoIntxExtMgl estado : listaEstadosIntExt) {
                        if(estado.getIdEstadoInt() != null && estado.getIdEstadoInt().getBasicaId()!= null && estado.getIdEstadoExt() != null  
                                &&((CmtOrdenTrabajoMgl) orden[0]).getEstadoGeneralOt() != null && ((CmtOrdenTrabajoMgl) orden[0]).getEstadoGeneralOt() != null )
                            if(estado.getIdEstadoExt().getBasicaId().compareTo(((CmtOrdenTrabajoMgl) orden[0]).getEstadoGeneralOt())== 0){
                            reporteOtCMDto.setEstadoOt(estado.getIdEstadoExt().getNombreBasica());
                            break;
                        }
                    }
                }
                SimpleDateFormat fecha_creacion_OT_MER = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
                String fechaCreacion = null;
                if (((CmtOrdenTrabajoMgl) orden[0]) != null && ((CmtOrdenTrabajoMgl) orden[0]).getFechaCreacion() != null) {
                    fechaCreacion = fecha_creacion_OT_MER.format(((CmtOrdenTrabajoMgl) orden[0]).getFechaCreacion());
                    reporteOtCMDto.setFecha_creacion_OT_MER(fechaCreacion);
                }
                
                SimpleDateFormat fecha_Modificacion_OT_MER = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
                String fechaModificacion = null;
                if (((CmtOrdenTrabajoMgl) orden[0]) != null && ((CmtOrdenTrabajoMgl) orden[0]).getFechaEdicion()!= null) {
                    fechaModificacion = fecha_Modificacion_OT_MER.format(((CmtOrdenTrabajoMgl) orden[0]).getFechaEdicion());
                    reporteOtCMDto.setFecha_modificacion_Ot(fechaModificacion);
                }
               
                reporteOtCMDto.setSegmento_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getSegmento()  != null 
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getSegmento().getNombreBasica() :"");
                reporteOtCMDto.setTecnologia_OT_MGL(((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTecnologia() != null 
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTecnologia().getNombreBasica() :"");
                reporteOtCMDto.setCmObj(((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getCuentaId().substring(7): "");
                if (((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal() != null
                        && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal().getDireccionObj() != null) {
                    reporteOtCMDto.setDireccionMER(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac());
                }
                reporteOtCMDto.setNombreCMMER(((CmtOrdenTrabajoMgl) orden[0]).getCmObj()  != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNombreCuenta() :"");
                reporteOtCMDto.setCodigoCMR(((CmtOrdenTrabajoMgl) orden[0]).getCmObj()  != null 
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNumeroCuenta().toString(): "");
                reporteOtCMDto.setUsuario_Creacion_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getUsuarioCreacionId()  != null 
                        ?  ((CmtOrdenTrabajoMgl) orden[0]).getUsuarioCreacionId().toString() : "");
                reporteOtCMDto.setOnyx_Ot_Hija_Cm(((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija() != null 
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija().toString() : "");
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
                reporteOtCMDto.setComplejidadServicio(complejidadDescripcion);
                if (((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento() != null) {
                    reporteOtCMDto.setDepartamento(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento().getGpoNombre());
                }
                reporteOtCMDto.setUsuarioModOt(((CmtOrdenTrabajoMgl) orden[0]).getUsuarioEdicion());
                 // campos nuevos
                reporteOtCMDto.setPersonaAtiendeSitio(((CmtOrdenTrabajoMgl) orden[0]).getPersonaRecVt());
                reporteOtCMDto.setTelefonoAtiendeSitio(((CmtOrdenTrabajoMgl) orden[0]).getTelPerRecVt());
                // regional
                if (listacmtRegionalMgl != null && !listacmtRegionalMgl.isEmpty()) {
                    for (CmtRegionalRr regional : listacmtRegionalMgl) {
                        if (regional.getCodigoRr() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj()!= null
                                && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDivision()!= null
                                && regional.getNombreRegional() != null) {
                            if (regional.getCodigoRr().equals(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDivision())) {
                                reporteOtCMDto.setRegional(regional.getNombreRegional() + " " + "("
                                        + ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDivision() + ")");
                            }
                        }
                    }
                }
                // ciudad
                if (listacmtComunidadRr != null && !listacmtComunidadRr.isEmpty()) {
                    for (CmtComunidadRr com : listacmtComunidadRr) {
                        if (com.getCodigoRr() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null
                                && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getComunidad() != null
                                && com.getNombreComunidad() != null) {
                            if (com.getCodigoRr().equals(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getComunidad())) {
                                reporteOtCMDto.setCiudad(com.getNombreComunidad() + " " + "("
                                        + ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getComunidad() + ")");
                            }
                        }

                    }
                }
               
                //agendamiento
                if (((CmtAgendamientoMgl) orden[1]) != null) {
                    reporteOtCMDto.setOrden_RR(((CmtAgendamientoMgl) orden[1]) != null 
                            ? ((CmtAgendamientoMgl) orden[1]).getIdOtenrr() :"" );
                    //sub tipo ordenes ofsc
                    if (listaBasica != null && !listaBasica.isEmpty()) {
                        for (CmtBasicaMgl cmtBasicaMgl : listaBasica) {
                            if (((CmtAgendamientoMgl) orden[1]).getSubTipoWorkFoce() != null && cmtBasicaMgl.getCodigoBasica() != null) {
                                if (((CmtAgendamientoMgl) orden[1]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                                    reporteOtCMDto.setSubTipo_Orden_OFSC(((CmtAgendamientoMgl) orden[1]).getSubTipoWorkFoce().trim());
                                    break;
                                }
                            }
                        }
                    }

                    String fechaAgenda = null;
                    if (((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaAgenda() != null) {
                        String timeS = ((CmtAgendamientoMgl) orden[1]) != null ? ((CmtAgendamientoMgl) orden[1]).getTimeSlot() : "";
                        fechaAgenda = DateUtils.setTimeSlotFechaAgenda(((CmtAgendamientoMgl) orden[1]).getFechaAgenda(), timeS);
                        reporteOtCMDto.setFecha_agenda_OFSC(fechaAgenda);
                    }
                   
                    reporteOtCMDto.setUsuario_creacion_agenda_OFSC((((CmtAgendamientoMgl) orden[1]) != null 
                            ? ((CmtAgendamientoMgl) orden[1]).getUsuarioCreacion() : ""));
                    String timeS = ((CmtAgendamientoMgl) orden[1]) != null ? ((CmtAgendamientoMgl) orden[1]).getTimeSlot() : "";
                    if (timeS != null && !timeS.equals("") ) {
                        if(timeS.contains("Durante")){
                             reporteOtCMDto.setTime_slot_OFSC(timeS);
                        }else{
                             timeS = timeS.replace("-", "_");
                        reporteOtCMDto.setTime_slot_OFSC(timeS);
                        }
                    }
                    reporteOtCMDto.setAppt_number_OFSC(((CmtAgendamientoMgl) orden[1]) != null  
                            ? ((CmtAgendamientoMgl) orden[1]).getOfpsOtId() : "");
                    reporteOtCMDto.setEstado_agenda_OFSC(((CmtAgendamientoMgl) orden[1]) != null  
                            ? ((CmtAgendamientoMgl) orden[1]).getBasicaIdEstadoAgenda().getNombreBasica() : "");
                    SimpleDateFormat fecha_inicia_agenda = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fechaInivioVt = null;
                    if (((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaInivioVt() != null) {
                        fechaInivioVt = fecha_inicia_agenda.format(((CmtAgendamientoMgl) orden[1]).getFechaInivioVt());
                        reporteOtCMDto.setFecha_inicia_agenda_OFSC(fechaInivioVt);
                    }
                   
                    SimpleDateFormat fecha_fin_agenda = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fechaFinVt = null;
                    if (((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaFinVt() != null) {
                        fechaFinVt = fecha_fin_agenda.format(((CmtAgendamientoMgl) orden[1]).getFechaFinVt());
                        reporteOtCMDto.setFecha_fin_agenda_OFSC(fechaFinVt);
                    }
                   
                    reporteOtCMDto.setId_aliado_OFSC((((CmtAgendamientoMgl) orden[1]) != null 
                            ? ((CmtAgendamientoMgl) orden[1]).getIdentificacionAliado() : ""));
                    reporteOtCMDto.setNombre_aliado_OFSC(((CmtAgendamientoMgl) orden[1]) != null 
                            ?  ((CmtAgendamientoMgl) orden[1]).getNombreAliado() : "");
                    reporteOtCMDto.setId_tecnico_aliado_OFSC((((CmtAgendamientoMgl) orden[1]) != null 
                            ? ((CmtAgendamientoMgl) orden[1]).getIdentificacionTecnico() : ""));
                    reporteOtCMDto.setNombre_tecnico_aliado_OFSC((((CmtAgendamientoMgl) orden[1]) != null 
                            ? ((CmtAgendamientoMgl) orden[1]).getNombreTecnico() : ""));
                    reporteOtCMDto.setUltima_agenda_multiagenda((((CmtAgendamientoMgl) orden[1]) != null 
                            ? ((CmtAgendamientoMgl) orden[1]).getUltimaAgenda() : ""));
                    // observaciones 
                    String obs = (((CmtAgendamientoMgl) orden[1]) != null 
                            ?  ((CmtAgendamientoMgl) orden[1]).getObservacionesTecnico() : "");
                    if (obs != null && !obs.equals("")) {
                       obs = StringUtils.caracteresEspeciales(obs);
                        reporteOtCMDto.setObservaciones_tecnico_OFSC(obs);
                    }
                    
                    SimpleDateFormat fechaAsigTecnico = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaTecnico = null;
                    if (((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaAsigTecnico() != null) {
                        fechaTecnico = fechaAsigTecnico.format(((CmtAgendamientoMgl) orden[1]).getFechaAsigTecnico());
                        reporteOtCMDto.setFechaAsigTecnico(fechaTecnico);
                    }
                   
                    reporteOtCMDto.setUsuarioModAgenda(((CmtAgendamientoMgl) orden[1]).getUsuarioEdicion());
                    // indicador de cumplimiento
                    String cumplimiento = "No se cumplio";
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null
                            && ((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaAgenda() != null) {
                        if (((CmtAgendamientoMgl) orden[1]).getUltimaAgenda() != null && ((CmtAgendamientoMgl) orden[1]).getUltimaAgenda().equals("Y")) {
                            if (((CmtAgendamientoMgl) orden[1]).getFechaAgenda().before(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx())) {
                                cumplimiento = "Si se cumplió";
                            }
                        }
                        reporteOtCMDto.setIndicadorCumplimiento(cumplimiento);
                    }
                    //resultado agenda
                    reporteOtCMDto.setResultadoOrden(((CmtAgendamientoMgl) orden[1]).getBasicaIdrazones() != null 
                            ? ((CmtAgendamientoMgl) orden[1]).getBasicaIdrazones().getNombreBasica() : "" );

                    //cantidad reagenda 
                    if (((CmtAgendamientoMgl) orden[1]).getCantAgendas() != null) {
                        reporteOtCMDto.setCantReagenda(((CmtAgendamientoMgl) orden[1]).getCantAgendas());
                    }
                    //Motivos 
                    if (((CmtAgendamientoMgl) orden[1]).getMotivosReagenda() != null) {
                        reporteOtCMDto.setMotivosReagenda(((CmtAgendamientoMgl) orden[1]).getMotivosReagenda());
                    }

                    //tiempo ejecucion
                    if (((CmtAgendamientoMgl) orden[1]).getFechaInivioVt() != null 
                            && ((CmtAgendamientoMgl) orden[1]).getFechaFinVt() != null) {
                        String tiempoEjecucion = DateUtils.getHoraMinEntreFechasConFormato(
                                ((CmtAgendamientoMgl) orden[1]).getFechaInivioVt(), ((CmtAgendamientoMgl) orden[1]).getFechaFinVt());
                        reporteOtCMDto.setTiempoEjecucion(tiempoEjecucion);
                    }
                    //conveniencia
                    if (((CmtAgendamientoMgl) orden[1]).getConveniencia() != null) {
                        reporteOtCMDto.setConveniencia(((CmtAgendamientoMgl) orden[1]).getConveniencia());
                    }

                }
                // Onyx
                if (((OnyxOtCmDir) orden[2]) != null) {
                    reporteOtCMDto.setNit_Cliente_Onyx((((OnyxOtCmDir) orden[2])  != null 
                            ?  ((OnyxOtCmDir) orden[2]).getNit_Cliente_Onyx() : ""));
                    // nombreCiente
                    String nombreCiente = (((OnyxOtCmDir) orden[2]) != null  
                            ?  ((OnyxOtCmDir) orden[2]).getNombre_Cliente_Onyx() : "");
                    if (nombreCiente != null && !nombreCiente.equals("")) {
                        nombreCiente = StringUtils.caracteresEspeciales(nombreCiente);
                        reporteOtCMDto.setNombre_Cliente_Onyx(nombreCiente);
                    }
                    
                   
                    // nombreOtHija 
                    String nombreOtHija = (((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getNombre_Ot_Hija_Onyx() : "");
                    if (nombreOtHija != null && !nombreOtHija.equals("")) {
                       nombreOtHija = StringUtils.caracteresEspeciales(nombreOtHija);
                       reporteOtCMDto.setNombre_Ot_Hija_Onyx(nombreOtHija);
                    }
                    reporteOtCMDto.setOnyx_Ot_Hija_Cm((((OnyxOtCmDir) orden[2]) != null  
                            ? ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Hija_Cm() : "" ));
                    reporteOtCMDto.setOnyx_Ot_Padre_Cm(((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Padre_Cm() : "");
                    SimpleDateFormat creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fecha_Creacion_Ot_Hija_Onyx = null;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                        fecha_Creacion_Ot_Hija_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx());
                         reporteOtCMDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx);
                    }
                   
                        
                    reporteOtCMDto.setDescripcion_Onyx((((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getDescripcion_Onyx() : ""));
                    reporteOtCMDto.setSegmento_Onyx((((OnyxOtCmDir) orden[2]) != null  
                            ? ((OnyxOtCmDir) orden[2]).getSegmento_Onyx() : ""));
                    reporteOtCMDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]) != null  
                            ? (((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx()) : "");
                    reporteOtCMDto.setServicios_Onyx((((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getServicios_Onyx() : ""));
                    reporteOtCMDto.setRecurrente_Mensual_Onyx((((OnyxOtCmDir) orden[2])  != null  
                            ?  ((OnyxOtCmDir) orden[2]).getRecurrente_Mensual_Onyx() : ""));
                    reporteOtCMDto.setCodigo_Servicio_Onyx((((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getCodigo_Servicio_Onyx() : ""));
                    String vendedor = ((OnyxOtCmDir) orden[2]).getVendedor_Onyx()!= null 
                            ? ((OnyxOtCmDir) orden[2]).getVendedor_Onyx(): "";
                     reporteOtCMDto.setVendedor_Onyx(StringUtils.caracteresEspeciales(vendedor));
                         // Telefono 
                    String telefonoVend = (((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getTelefono_Vendedor_Onyx() : "");
                    if (telefonoVend != null && !telefonoVend.equals("")) {
                        telefonoVend = StringUtils.caracteresEspeciales(telefonoVend);
                         reporteOtCMDto.setTelefono_Vendedor_Onyx(telefonoVend);
                    }
                    reporteOtCMDto.setEstado_Ot_Hija_Onyx_Cm((((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getEstado_Ot_Hija_Onyx_Cm(): ""));
                    reporteOtCMDto.setEstado_Ot_Padre_Onyx_Cm((((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getEstado_Ot_Padre_Onyx_Cm(): ""));
                    SimpleDateFormat compromiso_Ot_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
                    String fecha_Compromiso_Ot_Padre_Onyx = null;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null) {
                        fecha_Compromiso_Ot_Padre_Onyx = compromiso_Ot_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx());
                        reporteOtCMDto.setFecha_Compromiso_Ot_Padre_Onyx(fecha_Compromiso_Ot_Padre_Onyx);
                    }
                    
                    reporteOtCMDto.setOt_Hija_Resolucion_1_Onyx((((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_1_Onyx() : ""));
                    reporteOtCMDto.setOt_Hija_Resolucion_2_Onyx((((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_2_Onyx(): ""));
                    reporteOtCMDto.setOt_Hija_Resolucion_3_Onyx((((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_3_Onyx(): ""));
                    reporteOtCMDto.setOt_Hija_Resolucion_4_Onyx((((OnyxOtCmDir) orden[2])  != null   
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_4_Onyx(): ""));
                    reporteOtCMDto.setOt_Padre_Resolucion_1_Onyx((((OnyxOtCmDir) orden[2])  != null  
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_1_Onyx(): ""));
                    reporteOtCMDto.setOt_Padre_Resolucion_2_Onyx((((OnyxOtCmDir) orden[2])  != null   
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_2_Onyx(): ""));
                    reporteOtCMDto.setOt_Padre_Resolucion_3_Onyx((((OnyxOtCmDir) orden[2])  != null   
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_3_Onyx(): ""));
                    reporteOtCMDto.setOt_Padre_Resolucion_4_Onyx((((OnyxOtCmDir) orden[2])  != null   
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_4_Onyx(): ""));
                    SimpleDateFormat fecha_Creacion_Onyx = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
                    String fecha_Creacion_Ot_Onyx = null;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFechaCreacion()!= null) {
                        fecha_Creacion_Ot_Onyx = fecha_Creacion_Onyx.format(((OnyxOtCmDir) orden[2]).getFechaCreacion());
                         reporteOtCMDto.setFechaCreacionOtOnyx(fecha_Creacion_Ot_Onyx);
                    }
                    
                    SimpleDateFormat fecha_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
                    String fecha_Creacion_Ot_Padre_Onyx = null;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                        fecha_Creacion_Ot_Padre_Onyx = fecha_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx());
                        reporteOtCMDto.setFecha_Creacion_Ot_Padre_Onyx(fecha_Creacion_Ot_Padre_Onyx);
                    }
                    String contactoTec = (((OnyxOtCmDir) orden[2])  != null   
                            ? ((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx(): "");
                   
                    if (contactoTec != null && !contactoTec.equals("")) {
                        contactoTec = StringUtils.caracteresEspeciales(contactoTec);
                        reporteOtCMDto.setContacto_Tecnico_Ot_Padre_Onyx(contactoTec);
                    }
                     // Telefono 
                    String telefono = (((OnyxOtCmDir) orden[2])  != null   
                            ? ((OnyxOtCmDir) orden[2]).getTelefono_Tecnico_Ot_Padre_Onyx(): "");
                    if (telefono != null && !telefono.equals("")) {
                        telefono = StringUtils.caracteresEspeciales(telefono);
                        reporteOtCMDto.setTelefono_Tecnico_Ot_Padre_Onyx(telefono);
                    }

                    //CAMPOS NUEVOS ONYX
                    reporteOtCMDto.setCodigoProyecto((((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getCodigoProyecto(): ""));
                    // direccion Onyx
                    String direccion = ((OnyxOtCmDir) orden[2]).getDireccion_Onyx();
                    if(direccion != null &&  !direccion.equals("") ){
                        direccion = StringUtils.caracteresEspeciales(direccion);
                        reporteOtCMDto.setDireccionFact(direccion);
                    }
                    
                    
                   
                    reporteOtCMDto.setContactoTecnicoOTP((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx() : ""));
                    reporteOtCMDto.setEmailCTec((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getEmailTec() : ""));
                    //emailTecnico
                    String emailTecnico = ((OnyxOtCmDir) orden[2]).getEmailTec();
                    if (emailTecnico != null && !emailTecnico.equals("")) {
                        emailTecnico = StringUtils.caracteresEspeciales(emailTecnico);
                        reporteOtCMDto.setEmailCTec(emailTecnico);
                    }

                    //tiempo de programacion
                    if (((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaAgenda()!= null 
                            && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                      String fecha =  DateUtils.getHoraMinEntreFechasConFormato(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(),
                              ((CmtAgendamientoMgl) orden[1]).getFechaCreacion());
                      reporteOtCMDto.setTiempoProgramacion(fecha);

                    }
                    //tiempo de atencion
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null
                            && ((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaAgenda() != null) {
                        String timeSlot = ((CmtAgendamientoMgl) orden[1]).getTimeSlot() == null ? ""
                                : ((CmtAgendamientoMgl) orden[1]).getTimeSlot().contains("Durante") ? ""
                                : ((CmtAgendamientoMgl) orden[1]).getTimeSlot();
                        if (timeSlot != null && !timeSlot.equals("")) {
                            if (timeSlot.contains("Durante")) {
                                timeSlot = "00";
                                String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((CmtAgendamientoMgl) orden[1]).getFechaAgenda(), 
                                        ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                reporteOtCMDto.setTiempoAtencion(horasTiempoAtiende);
                            } else {
                                timeSlot = ((CmtAgendamientoMgl) orden[1]).getTimeSlot().substring(0, 2);
                                String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((CmtAgendamientoMgl) orden[1]).getFechaAgenda(), ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                reporteOtCMDto.setTiempoAtencion(horasTiempoAtiende);
                            }
                        }
                    }

                    //antiguedad de la agenda
                    if (((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                         String antiguedad =  DateUtils.getTiempoEntreFechasConFormato(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx(),
                                 new Date());
                        reporteOtCMDto.setAntiguedadOrden(antiguedad);
                    }

                    //aliado Implementacion 
                    if (((OnyxOtCmDir) orden[2]).getaImplement() != null) {
                        reporteOtCMDto.setaImplement(((OnyxOtCmDir) orden[2]).getaImplement());
                    }
                    //tipo solucion
                    if (((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() != null ) {
                        reporteOtCMDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx());
                    }
                    //tipo solucion
                    if (((OnyxOtCmDir) orden[2]).getCiudadFact()!= null ) {
                        reporteOtCMDto.setCiudadFacturacion(((OnyxOtCmDir) orden[2]).getCiudadFact());
                    }

                }
                listaReporteOtCMDto.add(reporteOtCMDto);

            }
        }

    }
      
       public void cargarListaOrdenesDir(List<Object[]> listaOrdenesDir, List<CmtBasicaMgl> listaBasicaDir, List<ReporteOtCMDto> listaReporteOtCMDto) throws ApplicationException {
      
        //DIRECCIONES
        if (listaOrdenesDir != null && !listaOrdenesDir.isEmpty()) {
            for (Object[] orden : listaOrdenesDir) {

                ReporteOtCMDto reporteOtCMDto = new ReporteOtCMDto();
                if (((OtHhppMgl) orden[0]) != null) {

                    reporteOtCMDto.setOt_Id_Cm(((OtHhppMgl)orden[0]).getOtHhppId()!= null 
                            ? ((OtHhppMgl)orden[0]).getOtHhppId().toString(): "");
                    reporteOtCMDto.setTipo_OT_MER(((OtHhppMgl)orden[0]).getTipoOtHhppId() != null  
                        ? ((OtHhppMgl)orden[0]).getTipoOtHhppId().getNombreTipoOt() : "");
                    reporteOtCMDto.setSub_tipo_OT_MER(((OtHhppMgl)orden[0]).getTipoOtHhppId()!=null 
                        && ((OtHhppMgl)orden[0]).getTipoOtHhppId().getTipoTrabajoOFSC() != null 
                        ? ((OtHhppMgl)orden[0]).getTipoOtHhppId().getTipoTrabajoOFSC().getNombreBasica(): "");
                    reporteOtCMDto.setSubTipo_Orden_OFSC(((OtHhppMgl)orden[0]).getTipoOtHhppId() != null 
                        && ((OtHhppMgl)orden[0]).getTipoOtHhppId().getSubTipoOrdenOFSC() !=null 
                        ? ((OtHhppMgl)orden[0]).getTipoOtHhppId().getSubTipoOrdenOFSC().getNombreBasica(): "");
                    reporteOtCMDto.setEstadoOt(((OtHhppMgl)orden[0]).getEstadoGeneral() != null 
                        ? ((OtHhppMgl)orden[0]).getEstadoGeneral().getNombreBasica() : "");
                    reporteOtCMDto.setEstado_interno_OT_MER(((OtHhppMgl) orden[0]).getEstadoInternoInicial()== null ? "" 
                            : ((OtHhppMgl) orden[0]).getEstadoInternoInicial().getNombreBasica());
                    SimpleDateFormat fecha_creacion_OT_MER = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String getFechaCreacionMer = null;
                    if (((OtHhppMgl) orden[0]) != null && ((OtHhppMgl) orden[0]).getFechaCreacionOt()!= null) {
                        getFechaCreacionMer = fecha_creacion_OT_MER.format(((OtHhppMgl) orden[0]).getFechaCreacionOt());
                        reporteOtCMDto.setFecha_creacion_OT_MER(getFechaCreacionMer);
                    } 
                    
                    SimpleDateFormat fecha_Modificacion_OT_MER = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
                    String fechaModificacion = null;
                    if (((OtHhppMgl) orden[0]) != null && ((OtHhppMgl) orden[0]).getFechaEdicion() != null) {
                        fechaModificacion = fecha_Modificacion_OT_MER.format(((OtHhppMgl) orden[0]).getFechaEdicion());
                        reporteOtCMDto.setFecha_modificacion_Ot(fechaModificacion);
                    }
                    
                    String listaTecno = "";
                    List<OtHhppTecnologiaMgl> listaTecnologias;
                    listaTecnologias = ((OtHhppMgl) orden[0]).getTecnologiaBasicaList();
                    if (listaTecnologias != null && !listaTecnologias.isEmpty()) {
                        for (OtHhppTecnologiaMgl otHhppTecnologiaMgl : listaTecnologias) {
                            if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId() != null) {
                                listaTecno += otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getNombreBasica() + ",";
                            }
                        }
                        if (!listaTecno.isEmpty()) {
                            listaTecno = listaTecno.substring(0, listaTecno.length() - 1);
                        }
                         reporteOtCMDto.setTecnologia_OT_MGL(listaTecno);
                    }
                   
                    if(((OtHhppMgl) orden[0]).getSubDireccionId()!= null){
                         reporteOtCMDto.setDireccionMER(((OtHhppMgl) orden[0]).getSubDireccionId().getSdiFormatoIgac());
                    }else{
                        if(((OtHhppMgl) orden[0]).getDireccionId() != null){
                            reporteOtCMDto.setDireccionMER(((OtHhppMgl) orden[0]).getDireccionId().getDirFormatoIgac());
                        }
                    }
                  
                    reporteOtCMDto.setUsuario_Creacion_OT_MER((((OtHhppMgl) orden[0]).getUsuarioCreacion() != null 
                            ? ((OtHhppMgl) orden[0]).getUsuarioCreacion() : ""));
                    reporteOtCMDto.setOnyx_Ot_Hija_Cm(((OtHhppMgl) orden[0]).getOnyxOtHija() != null 
                            ? ((OtHhppMgl) orden[0]).getOnyxOtHija().toString() : "");
                    String complejidadDescripcion = "";
                    if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("A")) {
                        complejidadDescripcion = "ALTA";
                    } else if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("M")) {
                        complejidadDescripcion = "MEDIA";
                    } else if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("B")) {
                        complejidadDescripcion = "BAJA";
                    } else {
                        complejidadDescripcion = "";
                    }

                    reporteOtCMDto.setComplejidadServicio(complejidadDescripcion);
                    reporteOtCMDto.setUsuarioModOt(((OtHhppMgl) orden[0]).getUsuarioEdicion());
                    reporteOtCMDto.setSegmento_OT_MER(((OtHhppMgl) orden[0]).getSegmento() != null 
                            ? ((OtHhppMgl) orden[0]).getSegmento().getNombreBasica() : "");

                }


                //agendamiento
                if (((MglAgendaDireccion) orden[1]) != null) {

                    reporteOtCMDto.setOrden_RR(((MglAgendaDireccion) orden[1]).getIdOtenrr() != null
                            ? ((MglAgendaDireccion) orden[1]).getIdOtenrr() : "");
                    //sub tipo ordenes ofsc
                    if (listaBasicaDir != null && !listaBasicaDir.isEmpty()) {
                        for (CmtBasicaMgl cmtBasicaMgl : listaBasicaDir) {
                            if (cmtBasicaMgl.getCodigoBasica() != null && ((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce() != null) {
                                if (((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                                    reporteOtCMDto.setSubTipo_Orden_OFSC(((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce().trim());
                                    break;
                                }
                            }

                        }
                    }

                    String fechaAgenda = null;
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaAgenda() != null) {
                        String timeS = ((MglAgendaDireccion) orden[1]) != null ? ((MglAgendaDireccion) orden[1]).getTimeSlot() : "";
                        fechaAgenda = DateUtils.setTimeSlotFechaAgenda(((MglAgendaDireccion) orden[1]).getFechaAgenda(), timeS);
                        reporteOtCMDto.setFecha_agenda_OFSC(fechaAgenda);
                    }
                   
                    reporteOtCMDto.setUsuario_creacion_agenda_OFSC((((MglAgendaDireccion) orden[1]).getUsuarioCreacion() != null 
                            ?  ((MglAgendaDireccion) orden[1]).getUsuarioCreacion() : ""));
                     String timeS = ((MglAgendaDireccion) orden[1]) != null ? ((MglAgendaDireccion) orden[1]).getTimeSlot() : "" ;
                    if (timeS != null && !timeS.equals("")) {
                        if (timeS.contains("Durante")) {
                            reporteOtCMDto.setTime_slot_OFSC(timeS);
                        } else {
                            timeS = timeS.replace("-", "_");
                            reporteOtCMDto.setTime_slot_OFSC(timeS);
                        }
                    }
                    reporteOtCMDto.setEstado_agenda_OFSC(((MglAgendaDireccion) orden[1]).getBasicaIdEstadoAgenda() != null 
                            ? ((MglAgendaDireccion) orden[1]).getBasicaIdEstadoAgenda().getNombreBasica() : "");
                    SimpleDateFormat fecha_inicia_agenda = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fechaInivioVt = null;
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaInivioVt() != null) {
                        fechaInivioVt = fecha_inicia_agenda.format(((MglAgendaDireccion) orden[1]).getFechaInivioVt());
                         reporteOtCMDto.setFecha_inicia_agenda_OFSC(fechaInivioVt);
                    } 
                   
                    SimpleDateFormat fecha_fin_agenda = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fechaFinVt = null;
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaFinVt() != null) {
                        fechaFinVt = fecha_fin_agenda.format(((MglAgendaDireccion) orden[1]).getFechaFinVt());
                         reporteOtCMDto.setFecha_fin_agenda_OFSC(fechaFinVt);
                    }
                   
                    reporteOtCMDto.setId_aliado_OFSC((((MglAgendaDireccion) orden[1]).getIdentificacionAliado() != null 
                            ? ((MglAgendaDireccion) orden[1]).getIdentificacionAliado(): ""));
                    reporteOtCMDto.setNombre_aliado_OFSC((((MglAgendaDireccion) orden[1])  != null  
                            ? ((MglAgendaDireccion) orden[1]).getNombreAliado(): ""));

                    reporteOtCMDto.setId_tecnico_aliado_OFSC((((MglAgendaDireccion) orden[1]).getIdentificacionTecnico()  != null  
                            ? ((MglAgendaDireccion) orden[1]).getIdentificacionTecnico(): ""));
                    reporteOtCMDto.setNombre_tecnico_aliado_OFSC((((MglAgendaDireccion) orden[1]).getNombreTecnico()  != null  
                            ? ((MglAgendaDireccion) orden[1]).getNombreTecnico(): ""));
                    reporteOtCMDto.setUltima_agenda_multiagenda((((MglAgendaDireccion) orden[1]).getUltimaAgenda()  != null  
                            ? ((MglAgendaDireccion) orden[1]).getUltimaAgenda(): ""));
                    // observaciones 
                    String obs = ((MglAgendaDireccion) orden[1]).getObservacionesTecnico();
                    if (obs != null && !obs.equals("")) {
                         obs = StringUtils.caracteresEspeciales(obs);
                         reporteOtCMDto.setObservaciones_tecnico_OFSC(obs);
                    }
                   
                    reporteOtCMDto.setAppt_number_OFSC(((MglAgendaDireccion) orden[1])  != null  
                            ? ((MglAgendaDireccion) orden[1]).getOfpsOtId() : "");

                    reporteOtCMDto.setUsuarioModAgenda(((MglAgendaDireccion) orden[1]).getUsuarioEdicion());

                    //CAMPOS NUEVOS
                    reporteOtCMDto.setPersonaAtiendeSitio(((MglAgendaDireccion) orden[1]).getPersonaRecVt());
                    reporteOtCMDto.setTelefonoAtiendeSitio(((MglAgendaDireccion) orden[1]).getTelPerRecVt());
                    //tiempo ejecucion
                    if (((MglAgendaDireccion) orden[1]).getFechaInivioVt() != null && ((MglAgendaDireccion) orden[1]).getFechaFinVt() != null) {
                        String tiempoEjecucion = DateUtils.getHoraMinEntreFechasConFormato(((MglAgendaDireccion) orden[1]).getFechaInivioVt(), ((MglAgendaDireccion) orden[1]).getFechaFinVt());
                        reporteOtCMDto.setTiempoEjecucion(tiempoEjecucion);
                    }
                    //resultado agenda
                    reporteOtCMDto.setResultadoOrden((((MglAgendaDireccion) orden[1]) != null  
                            && ((MglAgendaDireccion) orden[1]).getBasicaIdrazones() != null
                            ? ((MglAgendaDireccion) orden[1]).getBasicaIdrazones().getNombreBasica() : "" ));

                    //cantidad reagenda 
                    if (((MglAgendaDireccion) orden[1]).getCantAgendas() != null) {
                        reporteOtCMDto.setCantReagenda(((MglAgendaDireccion) orden[1]).getCantAgendas());
                    }
                    //Motivos
                    if (((MglAgendaDireccion) orden[1]).getMotivosReagenda() != null) {
                        reporteOtCMDto.setMotivosReagenda(((MglAgendaDireccion) orden[1]).getMotivosReagenda());
                    }

                    //tiempo ejecucion
                    if (((MglAgendaDireccion) orden[1]).getFechaInivioVt() != null && ((MglAgendaDireccion) orden[1]).getFechaFinVt() != null) {
                        String tiempoEjecucion = DateUtils.getHoraMinEntreFechasConFormato(((MglAgendaDireccion) orden[1]).getFechaInivioVt(), ((MglAgendaDireccion) orden[1]).getFechaFinVt());
                        reporteOtCMDto.setTiempoEjecucion(tiempoEjecucion);
                    }
                    //conveniencia
                    if (((MglAgendaDireccion) orden[1]).getConveniencia() != null) {
                        reporteOtCMDto.setConveniencia(((MglAgendaDireccion) orden[1]).getConveniencia());
                    }
                }
                // onyx
                if (((OnyxOtCmDir) orden[2]) != null) {

                    reporteOtCMDto.setNit_Cliente_Onyx((((OnyxOtCmDir) orden[2]).getNit_Cliente_Onyx()  != null  
                            ? ((OnyxOtCmDir) orden[2]).getNit_Cliente_Onyx() : ""));
                    // nombreCiente
                    String nombreCienteOnyx = ((OnyxOtCmDir) orden[2]).getNombre_Cliente_Onyx() != null  
                            ? ((OnyxOtCmDir) orden[2]).getNombre_Cliente_Onyx() : "";
                    if (nombreCienteOnyx != null && !nombreCienteOnyx.equals("")) {
                         nombreCienteOnyx = StringUtils.caracteresEspeciales(nombreCienteOnyx);
                    }
                    reporteOtCMDto.setNombre_Cliente_Onyx(nombreCienteOnyx);
                    // nombreOtHija 
                    String nombreOtHija = ((OnyxOtCmDir) orden[2]).getNombre_Ot_Hija_Onyx();
                    if (nombreOtHija != null && !nombreOtHija.equals("")) {
                        nombreOtHija = StringUtils.caracteresEspeciales(nombreOtHija);
                    }
                    reporteOtCMDto.setNombre_Ot_Hija_Onyx(nombreOtHija);
                    //ot hija direcciones
                    reporteOtCMDto.setOnyx_Ot_Padre_Cm(((OnyxOtCmDir) orden[2]).getOnyx_Ot_Hija_Dir() != null  
                            ? ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Padre_Dir() : "");
                    reporteOtCMDto.setOnyx_Ot_Hija_Cm(((OnyxOtCmDir) orden[2]).getOnyx_Ot_Padre_Dir() != null  
                            ? ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Hija_Dir() : "");
                    SimpleDateFormat creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fecha_Creacion_Ot_Hija_Onyx = null;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                        fecha_Creacion_Ot_Hija_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx());
                        reporteOtCMDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx);
                    } 
                   
                    // fecha creacion onyx
                    String fecha_Creacion_Ot_Onyx = null;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFechaCreacion() != null) {
                        fecha_Creacion_Ot_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[2]).getFechaCreacion());
                          reporteOtCMDto.setFechaCreacionOtOnyx(fecha_Creacion_Ot_Onyx);
                    } 
  
                    reporteOtCMDto.setDescripcion_Onyx(((OnyxOtCmDir) orden[2]).getDescripcion_Onyx() != null 
                            ? ((OnyxOtCmDir) orden[2]).getDescripcion_Onyx() : "");
                    reporteOtCMDto.setSegmento_Onyx((((OnyxOtCmDir) orden[2]).getSegmento_Onyx() != null 
                            ? ((OnyxOtCmDir) orden[2]).getSegmento_Onyx(): ""));
                    reporteOtCMDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() != null 
                            ? ((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx(): "" );
                    reporteOtCMDto.setServicios_Onyx(((OnyxOtCmDir) orden[2]).getServicios_Onyx() != null 
                            ? ((OnyxOtCmDir) orden[2]).getServicios_Onyx() : "");
                    reporteOtCMDto.setRecurrente_Mensual_Onyx(((OnyxOtCmDir) orden[2]).getRecurrente_Mensual_Onyx() != null 
                            ? ((OnyxOtCmDir) orden[2]).getRecurrente_Mensual_Onyx() : "");
                    reporteOtCMDto.setCodigo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getCodigo_Servicio_Onyx() != null 
                            ? ((OnyxOtCmDir) orden[2]).getCodigo_Servicio_Onyx() : "");
                    // vendedor Onyx
                    String vendedorOnyx = ((OnyxOtCmDir) orden[2]).getVendedor_Onyx() != null ? ((OnyxOtCmDir) orden[2]).getVendedor_Onyx() : "";
                    if (vendedorOnyx != null && !vendedorOnyx.equals("")) {
                          vendedorOnyx = StringUtils.caracteresEspeciales(vendedorOnyx);
                          reporteOtCMDto.setVendedor_Onyx(vendedorOnyx);
                    }
                    
                    // Telefono 
                    String telefonoVend = ((OnyxOtCmDir) orden[2]).getTelefono_Vendedor_Onyx() != null 
                            ? ((OnyxOtCmDir) orden[2]).getTelefono_Vendedor_Onyx() : "";
                    if (telefonoVend != null && !telefonoVend.equals("")) {
                          telefonoVend = StringUtils.caracteresEspeciales(telefonoVend);
                          reporteOtCMDto.setTelefono_Vendedor_Onyx(telefonoVend);
                    }
                    
                    reporteOtCMDto.setEstado_Ot_Hija_Onyx_Cm((((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getEstado_Ot_Hija_Onyx_Dir() : ""));
                    reporteOtCMDto.setEstado_Ot_Padre_Onyx_Cm(((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getEstado_Ot_Padre_Onyx_Dir() : "");
                    SimpleDateFormat compromiso_Ot_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fecha_Compromiso_Ot_Padre_Onyx = null;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null) {
                        fecha_Compromiso_Ot_Padre_Onyx = compromiso_Ot_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx());
                         reporteOtCMDto.setFecha_Compromiso_Ot_Padre_Onyx(fecha_Compromiso_Ot_Padre_Onyx);
                    } 
                   
                    reporteOtCMDto.setOt_Hija_Resolucion_1_Onyx(((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_1_Onyx() : "");
                    reporteOtCMDto.setOt_Hija_Resolucion_2_Onyx(((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_2_Onyx() : "");
                    reporteOtCMDto.setOt_Hija_Resolucion_3_Onyx(((OnyxOtCmDir) orden[2]) !=null 
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_3_Onyx() : "");
                    reporteOtCMDto.setOt_Hija_Resolucion_4_Onyx(((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_4_Onyx() : "");
                    reporteOtCMDto.setOt_Padre_Resolucion_1_Onyx(((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_1_Onyx() : "");
                    reporteOtCMDto.setOt_Padre_Resolucion_2_Onyx(((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_2_Onyx() : "");
                    reporteOtCMDto.setOt_Padre_Resolucion_3_Onyx(((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_3_Onyx() : "");
                    reporteOtCMDto.setOt_Padre_Resolucion_4_Onyx(((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_4_Onyx() : "");
                    
                    SimpleDateFormat fecha_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fecha_Creacion_Ot_Padre_Onyx = null;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                        fecha_Creacion_Ot_Padre_Onyx = fecha_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx());
                        reporteOtCMDto.setFecha_Creacion_Ot_Padre_Onyx(fecha_Creacion_Ot_Padre_Onyx);
                    } 
                   
                    
                    String contactoTec = (((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx() : "");

                    if (contactoTec != null && !contactoTec.equals("")) {
                        contactoTec = StringUtils.caracteresEspeciales(contactoTec);
                        reporteOtCMDto.setContacto_Tecnico_Ot_Padre_Onyx(contactoTec);
                    }
                    // Telefono 
                    String telefono = ((OnyxOtCmDir) orden[2]).getTelefono_Tecnico_Ot_Padre_Onyx()!= null 
                            ? ((OnyxOtCmDir) orden[2]).getTelefono_Tecnico_Ot_Padre_Onyx():"";;
                    if (telefono != null && !telefono.equals("")) {
                           telefono = StringUtils.caracteresEspeciales(telefono);
                           reporteOtCMDto.setTelefono_Tecnico_Ot_Padre_Onyx(telefono);
                    }
                    
                   

                    //CAMPOS NUEVOS OT DIRECCION
                    reporteOtCMDto.setCodigoProyecto((((OnyxOtCmDir) orden[2]) != null 
                            ? ((OnyxOtCmDir) orden[2]).getCodigoProyecto() : ""));
                    // direccion Onyx
                    String direccion = ((OnyxOtCmDir) orden[2]).getDireccion_Onyx()!= null 
                            ? ((OnyxOtCmDir) orden[2]).getDireccion_Onyx(): "";
                    if (direccion != null && !direccion.equals("")) {
                        direccion = StringUtils.caracteresEspeciales(direccion);
                        reporteOtCMDto.setDireccionFact(direccion);
                    }
                   
                    reporteOtCMDto.setContactoTecnicoOTP((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx() : ""));

                    //emailTecnico
                    String emailTecnico = ((OnyxOtCmDir) orden[2]).getEmailTec();
                    if (emailTecnico != null && !emailTecnico.equals("")) {
                        emailTecnico = StringUtils.caracteresEspeciales(emailTecnico);
                        reporteOtCMDto.setEmailCTec(emailTecnico);
                    }
                   
                  // indicador de cumplimiento
                    String cumplimiento = "No se cumplio";
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null
                            && ((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaAgenda() != null) {
                        if (((MglAgendaDireccion) orden[1]).getUltimaAgenda() != null && ((MglAgendaDireccion) orden[1]).getUltimaAgenda().equals("Y")) {
                            if (((MglAgendaDireccion) orden[1]).getFechaAgenda().before(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx())) {
                                cumplimiento = "Si se cumplió";
                            }
                        }
                        reporteOtCMDto.setIndicadorCumplimiento(cumplimiento);
                    }
                    //tiempo de programacion
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaAgenda()!= null 
                            && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                      String fecha =  DateUtils.getTiempoEntreFechasAgendOnyx(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(),
                              ((MglAgendaDireccion) orden[1]).getFechaCreacion(),((MglAgendaDireccion) orden[1]).getTimeSlot());
                      reporteOtCMDto.setTiempoProgramacion(fecha);

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
                                reporteOtCMDto.setTiempoAtencion(horasTiempoAtiende);
                            } else {
                                timeSlot = ((MglAgendaDireccion) orden[1]).getTimeSlot().substring(0, 2);
                                String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((MglAgendaDireccion) orden[1]).getFechaAgenda(), ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                reporteOtCMDto.setTiempoAtencion(horasTiempoAtiende);
                            }
                        }

                    }
                    //antiguedad de la agenda
                    if (((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                        String antiguedad = DateUtils.getTiempoEntreFechasConFormato(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx(),new Date());
                        reporteOtCMDto.setAntiguedadOrden(antiguedad);
                    }

                    //aliado Implementacion 
                    if (((OnyxOtCmDir) orden[2]).getaImplement() != null) {
                        reporteOtCMDto.setaImplement(((OnyxOtCmDir) orden[2]).getaImplement());
                    }
                    //tipo solucion
                    if (((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() != null ) {
                        reporteOtCMDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx());
                    }
                    

                }
                // OtHhppTecnologiaMgl
                if (((OtHhppTecnologiaMgl) orden[3]) != null) {
                    // CAMPOS NUEVOS
                    if (((OtHhppTecnologiaMgl) orden[3]).getNodo() != null && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getRegionalRr() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getRegionalRr().getNombreRegional() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getRegionalRr().getCodigoRr() != null) {
                        reporteOtCMDto.setRegional(((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getRegionalRr().getNombreRegional()
                                + " " + "(" + ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getRegionalRr().getCodigoRr() + ")");
                    }
                    if (((OtHhppTecnologiaMgl) orden[3]).getNodo() != null && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getNombreComunidad()!= null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getCodigoRr() != null) {
                        reporteOtCMDto.setCiudad(((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getNombreComunidad()
                                + " " + "(" + ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getCodigoRr() + ")");
                    }

                    if (((OtHhppTecnologiaMgl) orden[3]).getNodo() != null && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getCiudad() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getCiudad().getGpoNombre() != null) {
                        reporteOtCMDto.setDepartamento(((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getCiudad().getGpoNombre());
                    }

                }
                listaReporteOtCMDto.add(reporteOtCMDto);

            }
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
    private void generarSelectReporteOtCm(StringBuilder sql, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> subTipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOFSC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<String> regionalList = (List<String>) params.get("listRegionalSelected");
        List<String> ciudadList = (List<String>) params.get("listRrCiudadesSelected");
        List<String> listSegmento = (List<String>) params.get("listSegmentoSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");
        List<String> listEstadosOtCmDirSelected = (List<String>) params.get("listEstadosOtCmDirSelected");
        String codigoProyecto = (String) params.get("codProyecto");
        List<BigDecimal> listEstadosSelected = (List<BigDecimal>) params.get("listEstadosSelected");

        // fecha de Creacion orden OT ,
        if (params.get("filtroFechas").equals(Constant.FECHA_CREACION_OT)) {
            if (params.get("fechaInicioOt") != null && (params.get("fechaFinOt") != null)) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc',o.fechaCreacion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND  func('trunc', o.fechaCreacion) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', oy.fecha_Creacion_Ot_Hija_Onyx) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', oy.fecha_Creacion_Ot_Hija_Onyx) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', oy.fechaCreacion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', oy.fechaCreacion) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', a.fechaAgenda) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaAgenda) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', a.fechaAsigTecnico) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaAsigTecnico) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', a.fechaFinVt) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaFinVt) = :fechaInicioOt");
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
                        sql.append("AND func('trunc', a.fechaEdicion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaEdicion) = :fechaInicioOt");
                    }
                }
                CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
                CmtBasicaMgl agendaCancelada = null;
                try {
                    agendaCancelada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_CANCELADA);
                } catch (ApplicationException ex) {
                    String msgError = "Error al generar reporte, búsqueda de agenda: " + ex.getMessage();
                    LOGGER.error(msgError, ex);
                }
                if (agendaCancelada != null) {
                    sql.append(" AND  a.basicaIdEstadoAgenda.basicaId  = ").append(agendaCancelada.getBasicaId());
                }

            }
        }
        //fecha de reagendamiento ???? 
        if (params.get("filtroFechas").equals(Constant.FECHA_REAGENDAMIENTO_OFSC)) {
            if ((params.get("fechaInicioOt") != null && !params.get("fechaFinOt").toString().isEmpty())) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND func('trunc',  a.fechaReagenda) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaReagenda) = :fechaInicioOt");
                    }
                }
                //'22959'
                CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
                CmtBasicaMgl agendaReagendada = null;
                try {
                    agendaReagendada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_REAGENDADA);
                } catch (ApplicationException ex) {
                    String msgError = "Error al generar reporte, reagenda: " + ex.getMessage();
                    LOGGER.error(msgError, ex);
                }
                if (agendaReagendada != null) {
                    sql.append(" AND  a.basicaIdEstadoAgenda.basicaId  = ").append(agendaReagendada.getBasicaId()).append(" AND  a.estadoRegistro = 1");
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
                        sql.append(" AND func('trunc',  a.fechaSuspendeVt) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaSuspendeVt) = :fechaInicioOt");
                    }
                }

            }
        }
        
        // numero de OT
        if (params.get("otIni") != null && (params.get("otFin") != null)) {
            sql.append(" and o.idOt BETWEEN :otIni and  :otFin ");
        }
        if (subTipoOrdenList != null && !subTipoOrdenList.isEmpty()) {
            sql.append("  AND  O.basicaIdTipoTrabajo.basicaId  IN :subTipoOrdenList  ");
        }
        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            sql.append("  AND  O.tipoOtObj.idTipoOt  IN :tipoOrdenList  ");
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            sql.append("  AND  a.subTipoWorkFoce  IN :subOrdenList  ");
        }
        if (tipoOrdenListOFSC != null && !tipoOrdenListOFSC.isEmpty()) {
            sql.append("  AND  O.basicaIdTipoTrabajo.basicaId  IN :tipoOrdenListOFSC  ");
        }
        // estado interno
        if (listEstadosOtCmDirSelected != null && !listEstadosOtCmDirSelected.isEmpty()) {
            sql.append("  AND  O.estadoInternoObj.basicaId  IN :listEstadosOtCmDirSelected ");
        }
        // estado Prueba
        if (listEstadosSelected != null && !listEstadosSelected.isEmpty()) {
            sql.append("  AND  O.estadoGeneralOt  IN :listEstadosSelected ");
        }
        
        // regional MER
        if (regionalList != null && !regionalList.isEmpty()) {
            sql.append("  AND  o.cmObj.division  IN :regionalList  ");
        }
        // ciudad MER
        if (ciudadList != null && !ciudadList.isEmpty()) {
            sql.append("  AND  o.cmObj.comunidad  IN :ciudadList  ");
        }
        // segmento 
        if (listSegmento != null && !listSegmento.isEmpty()) {
            sql.append("  AND oy.segmento_Onyx  IN :listSegmento ");
        }

        // codigo de proyecto
        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            sql.append("  AND  oy.codigoProyecto  = :codProyecto");
        }
        // Nit Cliente
        if (params.get("nitCliente") != null && !params.get("nitCliente").toString().isEmpty()) {
            sql.append("  AND oy.nit_Cliente_Onyx  like :nitCliente");

        }
        // numero Ot Padre
        if (params.get("numOtOnyxPadre") != null && !params.get("numOtOnyxPadre").toString().isEmpty()) {
            sql.append("  AND  (oy.Onyx_Ot_Padre_Cm  = :numOtOnyxPadre or oy.Onyx_Ot_Padre_Dir  = :numOtOnyxPadre )");

        }
        // numero Ot Hija
        if (params.get("numeroOtOnyxHija") != null && !params.get("numeroOtOnyxHija").toString().isEmpty()) {

            sql.append("  AND  (oy.Onyx_Ot_Hija_Dir  = :numeroOtOnyxHija or oy.Onyx_Ot_Hija_Cm  = :numeroOtOnyxHija )");

        }
        // nombre del cliente
        if (params.get("nombreCliente") != null && !params.get("nombreCliente").toString().isEmpty()) {

            sql.append("  AND  oy.nombre_Cliente_Onyx  like :nombreCliente");

        }

        // Tipo de solucion
        if (listaSolucion != null && !listaSolucion.equals("")) {
            sql.append("  AND  oy.tipo_Servicio_Onyx  = :listaSolucion ");

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
    private void agregarParametros(Query q, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> subTipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOFSC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
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
        List<String> listEstadosOtCmDirSelected = (List<String>) params.get("listEstadosOtCmDirSelected");
         String listaSolucion = (String) params.get("tipoSolucionSelected");
         

        // fecha de Creacion orden OT ,
        if (params.get("filtroFechas")!= null && !params.get("filtroFechas").equals("")) {
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
            q.setParameter("subTipoOrdenList", (subTipoOrdenList.isEmpty()) ? null : subTipoOrdenList);
        }
        // estados inyternos
        if (listEstadosOtCmDirSelected != null && !listEstadosOtCmDirSelected.isEmpty()) {
            q.setParameter("listEstadosOtCmDirSelected", (listEstadosOtCmDirSelected.isEmpty()) ? null : listEstadosOtCmDirSelected);
        }
        if (tipoOrdenListOFSC != null && !tipoOrdenListOFSC.isEmpty()) {
            q.setParameter("tipoOrdenListOFSC", (tipoOrdenListOFSC.isEmpty()) ? null : tipoOrdenListOFSC);
        }
        // estados
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
        if (numeroOtOnyxHija!= null && !numeroOtOnyxHija.isEmpty()) {
             q.setParameter("numeroOtOnyxHija", (valor.equals("")) ? "" : valor);
        }
        // nombre del cliente
        if (nombreCliente != null && !nombreCliente.isEmpty()) {
           q.setParameter("nombreCliente", (valor.equals("")) ? "" : "%" + valor.toUpperCase()+ "%");
        }
         // tipo solucion
        if (listaSolucion != null && !listaSolucion.isEmpty()) {
           q.setParameter("listaSolucion", (listaSolucion.isEmpty()) ? null : listaSolucion);
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
    private void generarSelectReporteOtDIR(StringBuilder sql, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<BigDecimal> subtipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOSFC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<BigDecimal> listEstadosSelected = (List<BigDecimal>) params.get("listEstadosSelected");
        List<String> regionalList = (List<String>) params.get("listRegionalSelected");
        List<String> ciudadList = (List<String>) params.get("listRrCiudadesSelected");
        List<String> listSegmento = (List<String>) params.get("listSegmentoSelected");
        List<String> listEstadosOtCmDirSelected = (List<String>) params.get("listEstadosOtCmDirSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");
        String codigoProyecto = (String) params.get("codProyecto");


        // fecha de Creacion orden OT ,
        if (params.get("filtroFechas").equals(Constant.FECHA_CREACION_OT)) {
            if (params.get("fechaInicioOt") != null && (params.get("fechaFinOt") != null)) {
                SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
                String diaIni = diaInicial.format(params.get("fechaInicioOt"));
                SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
                String diaFinal = diaFin.format(params.get("fechaFinOt"));
                if (diaIni != null && diaFinal != null) {
                    if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                        sql.append(" AND o.fechaCreacion BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND  func('trunc', o.fechaCreacion) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', oy.fecha_Creacion_Ot_Hija_Onyx) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', oy.fecha_Creacion_Ot_Hija_Onyx) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', oy.fechaCreacion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', oy.fechaCreacion) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', a.fechaAgenda) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaAgenda) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', a.fechaAsigTecnico) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaAsigTecnico) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', a.fechaFinVt) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaFinVt) = :fechaInicioOt");
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
                        sql.append(" AND func('trunc', a.fechaEdicion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaEdicion) = :fechaInicioOt");
                    }
                }
                CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
                CmtBasicaMgl agendaCancelada = null;
                try {
                    agendaCancelada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_CANCELADA);
                } catch (ApplicationException ex) {
                    String msgError = "Error en :" + ClassUtils.getCurrentMethodName(this.getClass()) + " " + ex.getMessage();
                    LOGGER.error(msgError, ex);
                }
                if (agendaCancelada != null) {
                    sql.append(" AND  a.basicaIdEstadoAgenda.basicaId  = ").append(agendaCancelada.getBasicaId()).append(" AND  a.estadoRegistro = 1");
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
                        sql.append(" AND func('trunc',  a.fechaReagenda) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaReagenda) = :fechaInicioOt");
                    }
                }

                CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
                CmtBasicaMgl agendaReagendada = null;
                try {
                    agendaReagendada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_REAGENDADA);
                } catch (ApplicationException ex) {
                    String msgError = "Error en :" + ClassUtils.getCurrentMethodName(this.getClass()) + " " + ex.getMessage();
                    LOGGER.error(msgError, ex);
                }
                if (agendaReagendada != null) {
                    sql.append(" AND  a.basicaIdEstadoAgenda.basicaId  = ").append(agendaReagendada.getBasicaId()).append(" AND  a.estadoRegistro = 1");
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
                        sql.append(" AND func('trunc',  a.fechaSuspendeVt) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    } else {
                        sql.append(" AND func('trunc', a.fechaSuspendeVt) = :fechaInicioOt");
                    }
                }

            }

        }
        // numero de OT
        if (params.get("otIni") != null && (params.get("otFin") != null)) {
            sql.append(" and o.otHhppId BETWEEN :otIni and  :otFin ");
        }
        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            sql.append("  AND  o.tipoOtHhppId.tipoOtId  IN :tipoOrdenList  ");
        }
        if (subtipoOrdenList != null && !subtipoOrdenList.isEmpty()) {
            sql.append("  AND  O.tipoOtHhppId.tipoTrabajoOFSC.basicaId IN :subtipoOrdenList");
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            sql.append("  AND  a.subTipoWorkFoce  IN :subOrdenList  ");
        }
        if (tipoOrdenListOSFC != null && !tipoOrdenListOSFC.isEmpty()) {
            sql.append("  AND  O.tipoOtHhppId.tipoTrabajoOFSC.basicaId  IN :tipoOrdenListOSFC  ");
        }
       // estado interno
        if (listEstadosOtCmDirSelected != null && !listEstadosOtCmDirSelected.isEmpty()) {
            sql.append("  AND  O.estadoInternoInicial.basicaId  IN :listEstadosOtCmDirSelected ");
        }
         // estados
        if (listEstadosSelected != null && !listEstadosSelected.isEmpty()) {
            sql.append("  AND  O.estadoGeneral.basicaId  IN :listEstadosSelected ");
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
            sql.append("  AND oy.segmento_Onyx  IN :listSegmento ");

        }

        // codigo de proyecto
        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            sql.append("  AND  oy.codigoProyecto  = :codigoProyecto");

        }
        // Nit Cliente
        if (params.get("nitCliente") != null && !params.get("nitCliente").toString().isEmpty()) {
            sql.append("  AND oy.nit_Cliente_Onyx  like :nitCliente");

        }
        // numero Ot Padre
        if (params.get("numOtOnyxPadre") != null && !params.get("numOtOnyxPadre").toString().isEmpty()) {
            sql.append("  AND  (oy.Onyx_Ot_Padre_Cm  = :numOtOnyxPadre or oy.Onyx_Ot_Padre_Dir  = :numOtOnyxPadre )");

        }
        // numero Ot Hija
        if (params.get("numeroOtOnyxHija") != null && !params.get("numeroOtOnyxHija").toString().isEmpty()) {
            sql.append("  AND  (oy.Onyx_Ot_Hija_Dir  = :numeroOtOnyxHija or oy.Onyx_Ot_Hija_Cm  = :numeroOtOnyxHija )");

        }
        // nombre del cliente
        if (params.get("nombreCliente") != null && !params.get("nombreCliente").toString().isEmpty()) {
            sql.append("  AND  UPPER(oy.nombre_Cliente_Onyx)  like UPPER(:nombreCliente)");

        }

       // Tipo de solucion
        if (listaSolucion != null && !listaSolucion.isEmpty()) {
            sql.append("  AND  oy.tipo_Servicio_Onyx   = :listaSolucion ");

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
        List<String> listEstadosOtCmDirSelected = (List<String>) params.get("listEstadosOtCmDirSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");

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
        if (listEstadosOtCmDirSelected != null && !listEstadosOtCmDirSelected.isEmpty()) {
            q.setParameter("listEstadosOtCmDirSelected", (listEstadosOtCmDirSelected.isEmpty()) ? null : listEstadosOtCmDirSelected);
        }
        if (tipoOrdenListOSFC != null && !tipoOrdenListOSFC.isEmpty()) {
            q.setParameter("tipoOrdenListOSFC", (tipoOrdenListOSFC.isEmpty()) ? null : tipoOrdenListOSFC);
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

    }
