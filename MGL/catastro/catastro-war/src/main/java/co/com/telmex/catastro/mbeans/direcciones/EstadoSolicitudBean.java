/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.facade.DireccionRRFacadeLocal;
import co.com.claro.mgl.facade.DrDireccionFacadeLocal;
import co.com.claro.mgl.facade.ModificacionDirFacadeLocal;
import co.com.claro.mgl.facade.PcmlFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.SubDireccionMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.ModificacionDir;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Bean de la Pantalla Estado Solicitud. Backing Bean Pantalla Estado Solicitud.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Log4j2
@ViewScoped
@ManagedBean(name = "estadoSolicitudBean")
public class EstadoSolicitudBean {

    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response =
            (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String idSolicitud;
    private String tipoSolicitudSelected;
    private String formularioGrestion;
    private Solicitud solicitudSelected;
    private boolean finalizada = false;
    private static final String ESTADO_FINALIZADO = "FINALIZADO";
    private static final String ROL_GESTIONAR_VC = "NVTGE5";
    private static final String ROL_GESTIONAR_HHPP_UNI = "NVTGE4";
    private DireccionRREntity direccionRREntity;
    private List<UnidadStructPcml> unidadesPredList;
    private List<ModificacionDir> modificacionDirList;
    private DireccionMgl direccionMgl;
    private ArrayList<String> nodosGeoList;
    @Getter
    @Setter
    private String barrioGeo;
    @Getter
    @Setter
    private boolean perfilGestionador;

    /* ---------------------------------------- */
    @EJB
    private DrDireccionFacadeLocal drDireccionFacade;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;
    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private PcmlFacadeLocal pcmlFacadeLocal;
    @EJB
    private SubDireccionMglFacadeLocal subDirMglFacadeLocal;
    @EJB
    private DireccionMglFacadeLocal dirMglFacadeLocal;
    @EJB
    private ModificacionDirFacadeLocal modificacionDirFacadeLocal;

    public EstadoSolicitudBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect(
                        securityLogin.redireccionarLogin());
                return;
            }
            idSolicitud = securityLogin.getIdSolSelected();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ValidarDirUnoAUnoMBean class ..." + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error consultando la solicitud" + e.getMessage() , e, LOGGER);
        }
    }

    @PostConstruct
    private void loadDataSolicitud() {
        try {
            if (idSolicitud != null && !idSolicitud.trim().isEmpty()) {
                solicitudSelected = solicitudFacadeLocal.findById(new BigDecimal(idSolicitud));
                if (solicitudSelected != null) {
                    perfilGestionador = validaPerfilGestionar(solicitudSelected);
                    if (solicitudSelected.getTipo() != null
                            && !solicitudSelected.getTipo().trim().isEmpty()) {
                        formularioGrestion =
                                solicitudSelected.getTipo().trim().
                                equalsIgnoreCase("VTCASA")
                                ? "Gestion_VC.jsp" : "Gestion_HHPPUNI.jsp";
                    }
                    finalizada = solicitudSelected.getEstado().
                            equalsIgnoreCase(ESTADO_FINALIZADO);
                    ComponenteDireccionMBean componenteDireccionBean = (ComponenteDireccionMBean) facesContext.getApplication()
                            .getExpressionFactory()
                            .createValueExpression(facesContext.getELContext(),
                                    "#{componenteDireccionMBean}",
                                    ComponenteDireccionMBean.class)
                            .getValue(facesContext.getELContext());

                    DrDireccion drDirec = drDireccionFacade.
                            findByIdSolicitud(solicitudSelected.getIdSolicitud());

                    componenteDireccionBean.loadDirDetalle(drDirec);
                    componenteDireccionBean.setReadOnly(true);
                    barrioGeo = componenteDireccionBean.getBarrioTxtBM();
                    //verificamos la existencia de HHPP sobre la misma calle y unidad  
                    DetalleDireccionEntity direccionEntity =
                            componenteDireccionBean.fillDetalleDireccionEntity();
                    if (componenteDireccionBean.validarMultiorigen(
                            solicitudSelected.getCiudad())) {
                        direccionEntity.setMultiOrigen("1");
                    } else {
                        direccionEntity.setMultiOrigen("0");
                    }
                    componenteDireccionBean.setVerificacionCasa(false);
                    if (solicitudSelected.getTipo() != null
                            && !solicitudSelected.getTipo().trim().isEmpty()
                            && solicitudSelected.getTipo().equalsIgnoreCase("VTCASA")) {
                        componenteDireccionBean.setVerificacionCasa(true);
                    }

                    direccionRREntity =
                            direccionRRFacadeLocal.
                            generarDirFormatoRR(direccionEntity);

                    if (direccionRREntity != null
                            && direccionRREntity.getCalle() != null
                            && !direccionRREntity.getCalle().trim().isEmpty()
                            && direccionRREntity.getNumeroUnidad() != null
                            && !direccionRREntity.getNumeroUnidad().
                            trim().isEmpty()
                            && direccionRREntity.getNumeroApartamento() != null
                            && !direccionRREntity.getNumeroApartamento().
                            trim().isEmpty()) {
                        unidadesPredList =
                                pcmlFacadeLocal.getUnidades(
                                direccionRREntity.getCalle(),
                                direccionRREntity.getNumeroUnidad(),
                                "",
                                solicitudSelected.getCiudad());
                        modificacionDirList =
                                modificacionDirFacadeLocal.findByIdSolicitud(
                                solicitudSelected.getIdSolicitud());

                    }
                    //buscamos la direccion de la solicitud
                    direccionMgl = new DireccionMgl();
                    if (drDirec != null && drDirec.getIdDirCatastro() != null
                            && !drDirec.getIdDirCatastro().trim().isEmpty()) {
                        BigDecimal dirId;
                        if (drDirec.getIdDirCatastro().
                                toUpperCase().startsWith("S")) {
                            SubDireccionMgl subDir = new SubDireccionMgl();
                            BigDecimal idSubDir = new BigDecimal(
                                    drDirec.getIdDirCatastro().substring(1));
                            subDir.setSdiId(idSubDir);
                            subDir = subDirMglFacadeLocal.findById(subDir);
                            dirId = subDir.getDirId();
                        } else {
                            dirId = new BigDecimal(
                                    drDirec.getIdDirCatastro().substring(1));
                        }
                        direccionMgl.setDirId(dirId);
                        direccionMgl = dirMglFacadeLocal.findById(direccionMgl);
                        nodosGeoList = new ArrayList<>();
                        if (direccionMgl.getDirNodouno() != null
                                && !direccionMgl.getDirNodouno().
                                trim().isEmpty()) {
                            nodosGeoList.add(direccionMgl.getDirNodouno());
                        }
                        if (direccionMgl.getDirNododos() != null
                                && !direccionMgl.getDirNododos().
                                trim().isEmpty()) {
                            nodosGeoList.add(direccionMgl.getDirNododos());
                        }
                        if (direccionMgl.getDirNodotres() != null
                                && !direccionMgl.getDirNodotres().trim().isEmpty()) {
                            nodosGeoList.add(direccionMgl.getDirNodotres());
                        }
                    }

                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("loadDataSolicitud-Error consultando la solicitud" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("loadDataSolicitud-Error consultando la solicitud" + e.getMessage() , e, LOGGER);
        }
    }

    private boolean validaPerfilGestionar(Solicitud solicitud) throws ApplicationException {
        boolean result = false;
        if (solicitud.getTipo().trim().
                equalsIgnoreCase("VTCASA")) {
            result = securityLogin.usuarioTieneRoll(ROL_GESTIONAR_VC);
        } else if (solicitud.getTipo().trim().
                equalsIgnoreCase("CREACION HHPP UNIDI")) {
            result = securityLogin.usuarioTieneRoll(ROL_GESTIONAR_HHPP_UNI);
        }
        return result;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Solicitud getSolicitudSelected() {
        return solicitudSelected;
    }

    public void setSolicitudSelected(Solicitud solicitudSelected) {
        this.solicitudSelected = solicitudSelected;
    }

    public String getTipoSolicitudSelected() {
        return tipoSolicitudSelected;
    }

    public void setTipoSolicitudSelected(String tipoSolicitudSelected) {
        this.tipoSolicitudSelected = tipoSolicitudSelected;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public String getFormularioGrestion() {
        return formularioGrestion;
    }

    public void setFormularioGrestion(String formularioGrestion) {
        this.formularioGrestion = formularioGrestion;
    }

    public DireccionRREntity getDireccionRREntity() {
        return direccionRREntity;
    }

    public void setDireccionRREntity(DireccionRREntity direccionRREntity) {
        this.direccionRREntity = direccionRREntity;
    }

    public List<UnidadStructPcml> getUnidadesPredList() {
        return unidadesPredList;
    }

    public void setUnidadesPredList(List<UnidadStructPcml> unidadesPredList) {
        this.unidadesPredList = unidadesPredList;
    }

    public DireccionMgl getDireccionMgl() {
        return direccionMgl;
    }

    public void setDireccionMgl(DireccionMgl direccionMgl) {
        this.direccionMgl = direccionMgl;
    }

    public ArrayList<String> getNodosGeoList() {
        return nodosGeoList;
    }

    public void setNodosGeoList(ArrayList<String> nodosGeoList) {
        this.nodosGeoList = nodosGeoList;
    }

    public List<ModificacionDir> getModificacionDirList() {
        return modificacionDirList;
    }

    public void setModificacionDirList(List<ModificacionDir> modificacionDirList) {
        this.modificacionDirList = modificacionDirList;
    }

}
