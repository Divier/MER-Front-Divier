/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtHorarioRestriccionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgw.agenda.restrition_ccmm.RestritionCcmmRequest;
import co.com.claro.mgw.agenda.restrition_ccmm.SchedulerRestrictionResponse;
import co.com.claro.mgw.agenda.restrition_ccmm.SchedulerRestritionCcmmDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Admin
 */
public class CmtHorarioRestriccionMglManager {

    
    private static final Logger LOGGER = LogManager.getLogger(CmtHorarioRestriccionMglManager.class);
    
    public List<CmtHorarioRestriccionMgl> findAll() throws ApplicationException {
        List<CmtHorarioRestriccionMgl> resulList;
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        resulList = cmtHorarioRestriccionMglDaoImpl.findAll();
        return resulList;
    }

    public CmtHorarioRestriccionMgl create(CmtHorarioRestriccionMgl cmtHorarioRestriccionMgl, String mUser, int mPerfil) throws ApplicationException {
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        return cmtHorarioRestriccionMglDaoImpl.createCm(cmtHorarioRestriccionMgl, mUser, mPerfil);
    }

    public boolean delete(CmtHorarioRestriccionMgl horario, String usuario, int perfil) throws ApplicationException {
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        return cmtHorarioRestriccionMglDaoImpl.deleteCm(horario, usuario, perfil);
    }

    public boolean deleteByCompania(CmtCompaniaMgl compania, String user, int perfil) throws ApplicationException {
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        return cmtHorarioRestriccionMglDaoImpl.deleteByCompania(compania, user, perfil);
    }

    public boolean deleteByCuentaMatrizId(BigDecimal cuentaMatrizId, String user, int perfil) throws ApplicationException {
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        return cmtHorarioRestriccionMglDaoImpl.deleteByCuentaMatrizId(cuentaMatrizId, user, perfil);
    }

    public CmtHorarioRestriccionMgl findById(BigDecimal Id) throws ApplicationException {
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        return cmtHorarioRestriccionMglDaoImpl.find(Id);
    }

    public List<CmtHorarioRestriccionMgl> findByHorarioCompania(CmtCompaniaMgl compania) throws ApplicationException {
        List<CmtHorarioRestriccionMgl> resulList;
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        resulList = cmtHorarioRestriccionMglDaoImpl.findByHorarioCompania(compania);
        return resulList;
    }

    public List<CmtHorarioRestriccionMgl> findByCuentaMatrizId(BigDecimal cuentaMatrizId) throws ApplicationException {
        List<CmtHorarioRestriccionMgl> resulList;
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        resulList = cmtHorarioRestriccionMglDaoImpl.findByHorarioCuentaMatrizId(cuentaMatrizId);
        return resulList;
    }

    public List<CmtHorarioRestriccionMgl> findBySubEdificioId(BigDecimal subEdificio) throws ApplicationException {
        List<CmtHorarioRestriccionMgl> resulList;
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        resulList = cmtHorarioRestriccionMglDaoImpl.findByHorarioSubEdificioId(subEdificio);
        return resulList;
    }

    public CmtHorarioRestriccionMgl update(CmtHorarioRestriccionMgl horario, String usuario, int perfil) throws ApplicationException {
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        return cmtHorarioRestriccionMglDaoImpl.updateCm(horario, usuario, perfil);
    }

    public ArrayList<CmtHorarioRestriccionMgl> findBySolicitud(CmtSolicitudCmMgl solicitudCm) throws ApplicationException {
        ArrayList<CmtHorarioRestriccionMgl> resulList = null;
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        List<CmtHorarioRestriccionMgl> list = cmtHorarioRestriccionMglDaoImpl.findBySolicitud(solicitudCm);
        if (list != null && !list.isEmpty()) {
            resulList = new ArrayList<CmtHorarioRestriccionMgl>();
            for (CmtHorarioRestriccionMgl h : list) {
                resulList.add(h);
            }
        }
        return resulList;
    }

    public boolean deleteBySolicitud(CmtSolicitudCmMgl solicitudCm, String user, int perfil) throws ApplicationException {
        CmtHorarioRestriccionMglDaoImpl cmtHorarioRestriccionMglDaoImpl = new CmtHorarioRestriccionMglDaoImpl();
        return cmtHorarioRestriccionMglDaoImpl.deleteBySolicitud(solicitudCm, user, perfil);
    }

    public ArrayList<CmtHorarioRestriccionMgl> findByVt(CmtVisitaTecnicaMgl vt) throws ApplicationException {
        ArrayList<CmtHorarioRestriccionMgl> resulList = null;
        CmtHorarioRestriccionMglDaoImpl dao = new CmtHorarioRestriccionMglDaoImpl();
        List<CmtHorarioRestriccionMgl> list = dao.findByVt(vt);
        if (list != null && !list.isEmpty()) {
            resulList = new ArrayList<CmtHorarioRestriccionMgl>();
            for (CmtHorarioRestriccionMgl h : list) {
                resulList.add(h);
            }
        }
        return resulList;
    }

    public boolean deleteByVt(CmtVisitaTecnicaMgl vt, String user, int perfil) throws ApplicationException {
        CmtHorarioRestriccionMglDaoImpl dao = new CmtHorarioRestriccionMglDaoImpl();
        return dao.deleteByVt(vt, user, perfil);
    }

    /**
     * metodo para consultar las restricciones de horario en una ccmm o
     * subedificio Autor: victor bocanegra
     *
     * @param restritionCcmmRequest
     * @return SchedulerRestrictionResponse
     */
 public SchedulerRestrictionResponse schedulerCcmmRestrition(RestritionCcmmRequest restritionCcmmRequest) {

        SchedulerRestrictionResponse response = new SchedulerRestrictionResponse();
         List<SchedulerRestritionCcmmDto> horariosPerRestricciones = new ArrayList<SchedulerRestritionCcmmDto>();
         List<SchedulerRestritionCcmmDto> horariosNomarcaRestricciones= new ArrayList<SchedulerRestritionCcmmDto>();

        if (restritionCcmmRequest != null && restritionCcmmRequest.getHhppId() == null
                && restritionCcmmRequest.getSubedificioId() == null) {
            response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);
            response.setHorariosPerRestricciones(horariosPerRestricciones);
            response.setMessageId(0);
            response.setCodigo("E");
            response.setDescription("Debe ingresar al menos uno de los 2 valores");

        } else if (restritionCcmmRequest != null && restritionCcmmRequest.getHhppId().compareTo(BigDecimal.ZERO) == 0
                && restritionCcmmRequest.getSubedificioId().compareTo(BigDecimal.ZERO) == 0) {
            response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);
            response.setHorariosPerRestricciones(horariosPerRestricciones);
            response.setMessageId(0);
            response.setCodigo("E");
            response.setDescription("Debe ingresar un valor diferente de '0'");

        } else if (restritionCcmmRequest != null && restritionCcmmRequest.getHhppId().compareTo(BigDecimal.ZERO) != 0) {
            if (restritionCcmmRequest.getSubedificioId().compareTo(BigDecimal.ZERO) != 0) {
                response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);
                response.setHorariosPerRestricciones(horariosPerRestricciones);
                response.setMessageId(0);
                response.setCodigo("E");
                response.setDescription("No se puede realizar la consulta con los 2 valores");

            } else {
                try {
                    //CONSULTA POR HHPP
                    HhppMglManager hhppMglManager = new HhppMglManager();
                    HhppMgl hhppMgl = hhppMglManager.findById(restritionCcmmRequest.getHhppId());
                    if (hhppMgl != null && hhppMgl.getHhpId() != null) {
                        CmtSubEdificioMgl cmtSubEdificioMgl = hhppMgl.getHhppSubEdificioObj();
                        if (cmtSubEdificioMgl != null) {
                            List<CmtHorarioRestriccionMgl> horarioRestriccionMgls =
                                    findBySubEdificioId(cmtSubEdificioMgl.getSubEdificioId());
                            response = calcularHorariosRestriccion(horarioRestriccionMgls);
                        } else {
                        response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);
                        response.setHorariosPerRestricciones(horariosPerRestricciones);    
                        response.setMessageId(0);
                        response.setCodigo("E");
                        response.setDescription("No existe el subedificio asociado al hhpp ingresado"); 
                        }  
                    } else {
                        response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);
                        response.setHorariosPerRestricciones(horariosPerRestricciones);
                        response.setMessageId(0);
                        response.setCodigo("E");
                        response.setDescription("No existe el hhpp ingresado");
                    }
                } catch (ApplicationException ex) {
					String msg = "Se produjo un error al momento de ejecutar el método '"+
					ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
					LOGGER.error(msg);
                    response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);
                    response.setHorariosPerRestricciones(horariosPerRestricciones); 
                    response.setMessageId(0);
                    response.setCodigo("E");
                    response.setDescription(ex.getMessage());
                }
            }


        } else if (restritionCcmmRequest != null && restritionCcmmRequest.getSubedificioId().compareTo(BigDecimal.ZERO) != 0) {
            if (restritionCcmmRequest.getHhppId().compareTo(BigDecimal.ZERO) != 0) {
                response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);
                response.setHorariosPerRestricciones(horariosPerRestricciones);
                response.setMessageId(0);
                response.setCodigo("E");
                response.setDescription("No se puede realizar la consulta con los 2 valores");

            } else {
                //CONSULTA POR SUBEDIFICIO
                try {
                    CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
                    CmtSubEdificioMgl cmtSubEdificioMgl = cmtSubEdificioMglManager.
                            findById(restritionCcmmRequest.getSubedificioId());
                    if (cmtSubEdificioMgl != null && cmtSubEdificioMgl.getSubEdificioId() != null) {
                        List<CmtHorarioRestriccionMgl> horarioRestriccionMgls =
                                findBySubEdificioId(restritionCcmmRequest.getSubedificioId());
                        response = calcularHorariosRestriccion(horarioRestriccionMgls);
                    } else {
                        response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);
                        response.setHorariosPerRestricciones(horariosPerRestricciones);
                        response.setMessageId(0);
                        response.setCodigo("E");
                        response.setDescription("No existe el subedificio ingresado");
                    }

                } catch (ApplicationException ex) {
                        response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);
                        response.setHorariosPerRestricciones(horariosPerRestricciones);
                        response.setMessageId(0);
                        response.setCodigo("E");
                        response.setDescription(ex.getMessage());
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                        LOGGER.error(msg);
                }

            }
        }

        return response;
    }

    public SchedulerRestrictionResponse calcularHorariosRestriccion(List<CmtHorarioRestriccionMgl> consulta) {

        SchedulerRestrictionResponse response = new SchedulerRestrictionResponse();

        //Verificamos que los registros no sean iguales
        if (consulta.size() > 0) {
            consulta = listaHorariosUnificada(consulta);

            List<Datosdias> datosD = new ArrayList<Datosdias>();

            for (CmtHorarioRestriccionMgl cmtHorarioRestriccionMgl : consulta) {


                if (cmtHorarioRestriccionMgl.getDiaInicio().toString().
                        equalsIgnoreCase(cmtHorarioRestriccionMgl.getDiaFin().toString())) {
                    Datosdias da = new Datosdias();
                    da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                    da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                    da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                    da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                    datosD.add(da);
                } else {

                    if (cmtHorarioRestriccionMgl.getDiaInicio().toString().equalsIgnoreCase("LUNES")) {

                        if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("MARTES")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("MIERCOLES")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MARTES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("JUEVES")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MARTES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MIERCOLES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("VIERNES")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MARTES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MIERCOLES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("JUEVES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("SABADO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MARTES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MIERCOLES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("JUEVES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("VIERNES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("DOMINGO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MARTES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MIERCOLES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("JUEVES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("VIERNES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("SABADO");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        }
                    } else if (cmtHorarioRestriccionMgl.getDiaInicio().toString().equalsIgnoreCase("MARTES")) {

                        if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("MIERCOLES")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("JUEVES")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MIERCOLES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("VIERNES")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MIERCOLES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("JUEVES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("SABADO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MIERCOLES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("JUEVES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("VIERNES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("DOMINGO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("MIERCOLES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("JUEVES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("VIERNES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("SABADO");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);
                        }

                    } else if (cmtHorarioRestriccionMgl.getDiaInicio().toString().equalsIgnoreCase("MIERCOLES")) {

                        if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("JUEVES")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("VIERNES")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("JUEVES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("SABADO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("JUEVES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("VIERNES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("DOMINGO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("JUEVES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("VIERNES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("SABADO");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        }
                    } else if (cmtHorarioRestriccionMgl.getDiaInicio().toString().equalsIgnoreCase("JUEVES")) {

                        if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("VIERNES")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("SABADO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("VIERNES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("DOMINGO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("VIERNES");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("SABADO");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);
                        }

                    } else if (cmtHorarioRestriccionMgl.getDiaInicio().toString().equalsIgnoreCase("VIERNES")) {

                        if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("SABADO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                        } else if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("DOMINGO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia("SABADO");
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);
                        }

                    } else if (cmtHorarioRestriccionMgl.getDiaInicio().toString().equalsIgnoreCase("SABADO")) {

                        if (cmtHorarioRestriccionMgl.getDiaFin().toString().equalsIgnoreCase("DOMINGO")) {

                            Datosdias da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaInicio().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);

                            da = new Datosdias();
                            da.setDia(cmtHorarioRestriccionMgl.getDiaFin().toString());
                            da.setFranja(cmtHorarioRestriccionMgl.getHoraInicio() + ";" + cmtHorarioRestriccionMgl.getHoraFin());
                            da.setTipoRes(cmtHorarioRestriccionMgl.getTipoRestriccion().toString());
                            da.setDescripcion(cmtHorarioRestriccionMgl.getRazonRestriccion());
                            datosD.add(da);
                        }

                    }

                }
            }
            response = calculoXdias(datosD);
            response.setMessageId(1);
            response.setDescription("Consulta Exitosa");
            response.setCodigo("I");

        } else {
            List<SchedulerRestritionCcmmDto> horariosNomarcaRestricciones =
                    new ArrayList<SchedulerRestritionCcmmDto>();

            SchedulerRestritionCcmmDto dto = null;

            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("LUNES");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);

            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("MARTES");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);

            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("MIERCOLES");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);

            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("JUEVES");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);

            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("VIERNES");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);

            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("SABADO");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);

            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("DOMINGO");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);

            response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);
            response.setMessageId(1);
            response.setDescription("Consulta Exitosa");
            response.setCodigo("I");
        }

        return response;
    }

    public List<CmtHorarioRestriccionMgl> listaHorariosUnificada(List<CmtHorarioRestriccionMgl> consulta) {


        List<String> listaBase = new ArrayList<String>();
        List<String> listaCompara = null;
        List<CmtHorarioRestriccionMgl> resultado = new ArrayList<CmtHorarioRestriccionMgl>();

        int i = 0;

        listaBase.add(consulta.get(0).getDiaInicio().toString());
        listaBase.add(consulta.get(0).getDiaFin().toString());
        listaBase.add(consulta.get(0).getHoraInicio());
        listaBase.add(consulta.get(0).getHoraFin());

        resultado.add(consulta.get(0));

        for (CmtHorarioRestriccionMgl horarioRestriccionMgl : consulta) {

            if (i > 0) {
                listaCompara = new ArrayList<String>();
                listaCompara.add(horarioRestriccionMgl.getDiaInicio().toString());
                listaCompara.add(horarioRestriccionMgl.getDiaFin().toString());
                listaCompara.add(horarioRestriccionMgl.getHoraInicio());
                listaCompara.add(horarioRestriccionMgl.getHoraFin());

                Collections.sort(listaBase);
                Collections.sort(listaCompara);

                if (listaBase.equals(listaCompara)) {

                    System.err.println("LISTAS SON IGUALES");

                } else {
                    System.err.println("LISTAS NO SON IGUALES");
                    resultado.add(horarioRestriccionMgl);

                }


            }
            i++;
        }
        return resultado;

    }
    
    
    
    class Datosdias {

        String dia;
        String franja;
        String tipoRes;
        String descripcion;

        public String getDia() {
            return dia;
        }

        public void setDia(String dia) {
            this.dia = dia;
        }

        public String getFranja() {
            return franja;
        }

        public void setFranja(String franja) {
            this.franja = franja;
        }

        public String getTipoRes() {
            return tipoRes;
        }

        public void setTipoRes(String tipoRes) {
            this.tipoRes = tipoRes;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }
    
    SchedulerRestrictionResponse calculoXdias(List<Datosdias> datosD) {

        SchedulerRestrictionResponse response = new SchedulerRestrictionResponse();
        List<Datosdias> lunes = new ArrayList<Datosdias>();
        List<Datosdias> martes = new ArrayList<Datosdias>();
        List<Datosdias> miercoles = new ArrayList<Datosdias>();
        List<Datosdias> jueves = new ArrayList<Datosdias>();
        List<Datosdias> viernes = new ArrayList<Datosdias>();
        List<Datosdias> sabado = new ArrayList<Datosdias>();
        List<Datosdias> domingo = new ArrayList<Datosdias>();

        for (Datosdias datosdias : datosD) {
            int dia = 0;

            if (datosdias.getDia().equalsIgnoreCase("LUNES")) {
                dia = 1;
            } else if (datosdias.getDia().equalsIgnoreCase("MARTES")) {
                dia = 2;
            } else if (datosdias.getDia().equalsIgnoreCase("MIERCOLES")) {
                dia = 3;
            } else if (datosdias.getDia().equalsIgnoreCase("JUEVES")) {
                dia = 4;
            } else if (datosdias.getDia().equalsIgnoreCase("VIERNES")) {
                dia = 5;
            } else if (datosdias.getDia().equalsIgnoreCase("SABADO")) {
                dia = 6;
            } else if (datosdias.getDia().equalsIgnoreCase("DOMINGO")) {
                dia = 7;
            }
            switch (dia) {
                case 1:
                    Datosdias diaLunes = new Datosdias();
                    diaLunes.setFranja(datosdias.getFranja());
                    diaLunes.setTipoRes(datosdias.getTipoRes());
                    diaLunes.setDescripcion(datosdias.getDescripcion());
                    diaLunes.setDia("LUNES");
                    lunes.add(diaLunes);
                    break;
                case 2:
                    Datosdias diaMartes = new Datosdias();
                    diaMartes.setFranja(datosdias.getFranja());
                    diaMartes.setTipoRes(datosdias.getTipoRes());
                    diaMartes.setDescripcion(datosdias.getDescripcion());
                    diaMartes.setDia("MARTES");
                    martes.add(diaMartes);
                    break;
                case 3:
                    Datosdias diaMiercoles = new Datosdias();
                    diaMiercoles.setFranja(datosdias.getFranja());
                    diaMiercoles.setTipoRes(datosdias.getTipoRes());
                    diaMiercoles.setDescripcion(datosdias.getDescripcion());
                    diaMiercoles.setDia("MIERCOLES");
                    miercoles.add(diaMiercoles);

                    break;
                case 4:
                    Datosdias diaJueves = new Datosdias();
                    diaJueves.setFranja(datosdias.getFranja());
                    diaJueves.setTipoRes(datosdias.getTipoRes());
                    diaJueves.setDescripcion(datosdias.getDescripcion());
                    diaJueves.setDia("JUEVES");
                    jueves.add(diaJueves);

                    break;
                case 5:
                    Datosdias diaViernes = new Datosdias();
                    diaViernes.setFranja(datosdias.getFranja());
                    diaViernes.setTipoRes(datosdias.getTipoRes());
                    diaViernes.setDescripcion(datosdias.getDescripcion());
                    diaViernes.setDia("VIERNES");
                    viernes.add(diaViernes);

                    break;
                case 6:
                    Datosdias diaSabado = new Datosdias();
                    diaSabado.setFranja(datosdias.getFranja());
                    diaSabado.setTipoRes(datosdias.getTipoRes());
                    diaSabado.setDescripcion(datosdias.getDescripcion());
                    diaSabado.setDia("SABADO");
                    sabado.add(diaSabado);

                    break;
                case 7:
                    Datosdias diaDomingo = new Datosdias();
                    diaDomingo.setFranja(datosdias.getFranja());
                    diaDomingo.setTipoRes(datosdias.getTipoRes());
                    diaDomingo.setDescripcion(datosdias.getDescripcion());
                    diaDomingo.setDia("DOMINGO");
                    domingo.add(diaDomingo);

                    break;
                default:
                    break;
            }

        }
        List<SchedulerRestritionCcmmDto> horariosPerRestricciones = new ArrayList<SchedulerRestritionCcmmDto>();
        List<SchedulerRestritionCcmmDto> horariosNomarcaRestricciones = new ArrayList<SchedulerRestritionCcmmDto>();


        //RECORREMOS LAS LISTAS POR DIAS
        SchedulerRestritionCcmmDto dto = null;

        if (lunes.size() > 0) {
            calculoXdiasHorario(lunes, horariosPerRestricciones, horariosNomarcaRestricciones);
        } else {
            //NO HAY  NADA PARA EL LUNES
            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("LUNES");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);
        }

        if (martes.size() > 0) {
            calculoXdiasHorario(martes, horariosPerRestricciones, horariosNomarcaRestricciones);
        } else {
            //NO HAY  NADA PARA EL MARTES 
            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("MARTES");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);
        }

        if (miercoles.size() > 0) {
            calculoXdiasHorario(miercoles, horariosPerRestricciones, horariosNomarcaRestricciones);
        } else {
            //NO HAY  NADA PARA EL MIERCOLES 
            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("MIERCOLES");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);
        }

        if (jueves.size() > 0) {
            calculoXdiasHorario(jueves, horariosPerRestricciones, horariosNomarcaRestricciones);
        } else {
            //NO HAY  NADA PARA EL JUEVES
            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("JUEVES");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);
        }

        if (viernes.size() > 0) {
            calculoXdiasHorario(viernes, horariosPerRestricciones, horariosNomarcaRestricciones);
        } else {
            //NO HAY  NADA PARA EL VIERNES   
            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("VIERNES");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);
        }

        if (sabado.size() > 0) {
            calculoXdiasHorario(sabado, horariosPerRestricciones, horariosNomarcaRestricciones);
        } else {
            //NO HAY  NADA PARA EL SABADO
            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("SABADO");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);
        }

        if (domingo.size() > 0) {
            calculoXdiasHorario(domingo, horariosPerRestricciones, horariosNomarcaRestricciones);
        } else {
            //NO HAY  NADA PARA EL DOMINGO 
            dto = new SchedulerRestritionCcmmDto();
            dto.setDia("DOMINGO");
            dto.setHoraMinutoInicial("0100");
            dto.setHoraMinutoFinal("2330");
            dto.setTipoRestriccion(Constant.NO_MARCADO);
            horariosNomarcaRestricciones.add(dto);
        }

        response.setHorariosPerRestricciones(horariosPerRestricciones);
        response.setHorariosNomarcaRestricciones(horariosNomarcaRestricciones);


        return response;
    }

    void calculoXdiasHorario(List<Datosdias> datos,
            List<SchedulerRestritionCcmmDto> horariosPerRestricciones,
            List<SchedulerRestritionCcmmDto> horariosNomarcaRestricciones) {


        SchedulerRestritionCcmmDto dto = null;

        List<String> horarios = tiemposCcmm();

        String razon = null;
        String parteIniRes = "";
        String parteFinRes = "";
        String parteIniNd = "";
        String parteFinNd = "";
        String parteIniD = "";
        String parteFinD = "";
        String dia = datos.get(0).getDia();

        for (Datosdias datosdias : datos) {


            if (datosdias.getTipoRes().equalsIgnoreCase(Constant.TIPO_RES_RESTRICCION)) {

                String[] partsRes = datosdias.getFranja().split(";");
                parteIniRes = partsRes[0];
                parteFinRes = partsRes[1];
                razon = datosdias.getDescripcion();

            } else if (datosdias.getTipoRes().equalsIgnoreCase(Constant.TIPO_RES_DISPONIBLE)) {

                String[] partsDis = datosdias.getFranja().split(";");
                parteIniD = partsDis[0];
                parteFinD = partsDis[1];

            } else if (datosdias.getTipoRes().equalsIgnoreCase(Constant.TIPO_RES_NO_DISPONIBLE)) {

                String[] partsNdis = datosdias.getFranja().split(";");
                parteIniNd = partsNdis[0];
                parteFinNd = partsNdis[1];

            }

        }

        int parteFi = 100;
        int parteIni = 0;

        for (String hora : horarios) {

            String[] parts = hora.split(";");
            String part1 = parts[0];
            String part2 = parts[1];
            parteIni = Integer.parseInt(part1);

            if (parteIni == parteFi) {

                if (part1.equalsIgnoreCase(parteIniD)) {
                    System.err.println("NO CARGA LISTA");
                    parteFi = Integer.parseInt(parteFinD);

                } else if (part1.equalsIgnoreCase(parteIniNd)) {
                    dto = new SchedulerRestritionCcmmDto();
                    dto.setDia(dia);
                    dto.setDescripcionRestricion(razon);
                    dto.setHoraMinutoInicial(part1);
                    dto.setHoraMinutoFinal(parteFinNd);
                    dto.setTipoRestriccion(Constant.TIPO_RES_NO_DISPONIBLE);
                    horariosNomarcaRestricciones.add(dto);

                    parteFi = Integer.parseInt(parteFinNd);

                } else if (part1.equalsIgnoreCase(parteIniRes)) {
                    dto = new SchedulerRestritionCcmmDto();
                    dto.setDia(dia);
                    dto.setDescripcionRestricion(razon);
                    dto.setHoraMinutoInicial(part1);
                    dto.setHoraMinutoFinal(parteFinRes);
                    dto.setTipoRestriccion(Constant.TIPO_RES_RESTRICCION);
                    horariosPerRestricciones.add(dto);

                    parteFi = Integer.parseInt(parteFinRes);

                } else {
                    //NO MARCADO
                    dto = new SchedulerRestritionCcmmDto();
                    dto.setDia(dia);
                    dto.setHoraMinutoInicial(part1);
                    dto.setHoraMinutoFinal(part2);
                    dto.setTipoRestriccion(Constant.NO_MARCADO);
                    horariosNomarcaRestricciones.add(dto);
                    parteFi = Integer.parseInt(part2);

                }

            }


        }

    }

    public List<String> tiemposCcmm() {

        List<String> horaMinutos = new ArrayList<String>();

        horaMinutos.add("0100;0130");
        horaMinutos.add("0130;0200");
        horaMinutos.add("0200;0230");
        horaMinutos.add("0230;0300");
        horaMinutos.add("0300;0330");
        horaMinutos.add("0330;0400");
        horaMinutos.add("0400;0430");
        horaMinutos.add("0430;0500");
        horaMinutos.add("0500;0530");
        horaMinutos.add("0530;0600");
        horaMinutos.add("0600;0630");
        horaMinutos.add("0630;0700");
        horaMinutos.add("0700;0730");
        horaMinutos.add("0730;0800");
        horaMinutos.add("0800;0830");
        horaMinutos.add("0830;0900");
        horaMinutos.add("0900;0930");
        horaMinutos.add("0930;1000");
        horaMinutos.add("1000;1030");
        horaMinutos.add("1030;1100");
        horaMinutos.add("1100;1130");
        horaMinutos.add("1130;1200");
        horaMinutos.add("1200;1230");
        horaMinutos.add("1230;1300");
        horaMinutos.add("1300;1330");
        horaMinutos.add("1330;1400");
        horaMinutos.add("1400;1430");
        horaMinutos.add("1430;1500");
        horaMinutos.add("1500;1530");
        horaMinutos.add("1530;1600");
        horaMinutos.add("1600;1630");
        horaMinutos.add("1630;1700");
        horaMinutos.add("1700;1730");
        horaMinutos.add("1730;1800");
        horaMinutos.add("1800;1830");
        horaMinutos.add("1830;1900");
        horaMinutos.add("1900;1930");
        horaMinutos.add("1930;2000");
        horaMinutos.add("2000;2030");
        horaMinutos.add("2030;2100");
        horaMinutos.add("2100;2130");
        horaMinutos.add("2130;2200");
        horaMinutos.add("2200;2230");
        horaMinutos.add("2230;2300");
        horaMinutos.add("2300;2330");
        horaMinutos.add("2330;2400");

        return horaMinutos;
    }


}
