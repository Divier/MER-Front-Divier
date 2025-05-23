package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCompaniaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtVisitaTecnicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 */
@ManagedBean(name = "constructoraVtBean")
@ViewScoped
public class ConstructoraVtBean implements Serializable {

    @EJB
    private CmtVisitaTecnicaMglFacadeLocal visitaTecnicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtCompaniaMglFacadeLocal cmtCompaniaMglFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    private static final Logger LOGGER = LogManager.getLogger(ConstructoraVtBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private int perfilVt = 0;
    private CmtVisitaTecnicaMgl vt;
    private CmtBasicaMgl origenDatos;
    private List<CmtBasicaMgl> origenDatosList;
    private CmtBasicaMgl tipoProyecto;
    private List<CmtBasicaMgl> tipoProyectoList;
    private List<CmtCompaniaMgl> listcompaniaConstructoras;
    private CmtCompaniaMgl constructora;
    private OtMglBean otMglBean;
    private CmtOrdenTrabajoMgl ot;

    public ConstructoraVtBean() {
        SecurityLogin securityLogin;
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();

        } catch (IOException e) {
            String msn2 = "Error al cargar Bean Constructora:....";
            FacesUtil.mostrarMensajeError(msn2+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructoraVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            visitaTecnicaMglFacadeLocal.setUser(usuarioVT, perfilVt);
            subEdificioMglFacadeLocal.setUser(usuarioVT, perfilVt);
            this.otMglBean = (OtMglBean) JSFUtil.getSessionBean(OtMglBean.class);
            ot = otMglBean.getOrdenTrabajo();
            //cargamos la VT que se creando o editando
            VisitaTecnicaBean vtMglBean =
                    (VisitaTecnicaBean) JSFUtil.
                    getSessionBean(VisitaTecnicaBean.class);
            this.vt = vtMglBean.getVt();
            if (vt == null) {
                String msn2 = "Error no hay una Visita Tecnica activa";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            }
            //Cargamos las compañias constructoras
            FiltroConsultaCompaniasDto filtroConsultaCompaniasDto = new FiltroConsultaCompaniasDto();
            filtroConsultaCompaniasDto.setEstado(Constant.REGISTRO_ACTIVO_VALUE);
            filtroConsultaCompaniasDto.setDepartamento(vt.getOtObj().getCmObj().getDepartamento().getGpoId());
            filtroConsultaCompaniasDto.setMunicipio(vt.getOtObj().getCmObj().getMunicipio().getGpoId());
            filtroConsultaCompaniasDto.setTipoCompania(Constant.TIPO_COMPANIA_ID_CONSTRUCTORAS);
            listcompaniaConstructoras = cmtCompaniaMglFacadeLocal.findByfiltro(filtroConsultaCompaniasDto,false);
            //cargamos lo tipos de origen de datos 
            CmtTipoBasicaMgl tipoOrigenDatos;
            tipoOrigenDatos=cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ORIGEN_DE_DATOS);
            origenDatosList = basicaMglFacadeLocal.findByTipoBasica(tipoOrigenDatos);
            //cagamos los tipos de proyecto
            CmtTipoBasicaMgl tipoProy;
            tipoProy=cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_DE_PROYECTO);
            tipoProyectoList = basicaMglFacadeLocal.findByTipoBasica(tipoProy);
            cargarDataVt();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructoraVtBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructoraVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void cargarDataVt(){
        try {
            if (vt.getOtObj() != null && vt.getOtObj().getCmObj() != null
                    && vt.getOtObj().getCmObj().getSubEdificioGeneral() != null) {
                //Si la cm de la Vt tiene una compañia constructora la asignamos
                if (vt.getCompaniaConstructora() == null) {
                    if (vt.getOtObj().getCmObj().getSubEdificioGeneral().
                            getCompaniaConstructoraObj() != null) {
                        constructora = new CmtCompaniaMgl();
                        constructora.setCompaniaId(vt.getOtObj().getCmObj().
                                getSubEdificioGeneral().getCompaniaConstructoraObj().getCompaniaId());
                        vt.setCompaniaConstructora(constructora);
                    } else {
                        constructora = new CmtCompaniaMgl();
                        constructora.setCompaniaId(BigDecimal.ZERO);
                        vt.setCompaniaConstructora(constructora);
                    }
                }
                //asignamos el origen de datos de la CM
                if (vt.getOrigenDatos() == null) {
                    if (vt.getOtObj().getCmObj().getSubEdificioGeneral().
                            getOrigenDatosObj() != null) {
                        origenDatos = new CmtBasicaMgl();
                        origenDatos.setBasicaId(vt.getOtObj().getCmObj().
                                getSubEdificioGeneral().getOrigenDatosObj().getBasicaId());
                        vt.setOrigenDatos(origenDatos);
                    } else {
                        origenDatos = new CmtBasicaMgl();
                        origenDatos.setBasicaId(BigDecimal.ZERO);
                        vt.setOrigenDatos(origenDatos);
                    }
                }
                //asignamos el tipo de proyecto de la CM
                if (vt.getTipoProyecto() == null) {
                    if (vt.getOtObj().getCmObj().getSubEdificioGeneral().
                            getTipoProyectoObj() != null) {
                        tipoProyecto = new CmtBasicaMgl();
                        tipoProyecto.setBasicaId(vt.getOtObj().getCmObj().
                                getSubEdificioGeneral().getTipoProyectoObj().getBasicaId());
                        vt.setTipoProyecto(tipoProyecto);
                    } else {
                        tipoProyecto = new CmtBasicaMgl();
                        tipoProyecto.setBasicaId(BigDecimal.ZERO);
                        vt.setTipoProyecto(tipoProyecto);
                    }
                }
                if (vt.getFechaEntregaEdificio() == null){
                    if (vt.getOtObj().getCmObj().getSubEdificioGeneral().
                            getFechaEntregaEdificio() != null){
                        vt.setFechaEntregaEdificio(vt.getOtObj().getCmObj().
                                getSubEdificioGeneral().getFechaEntregaEdificio());
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructoraVtBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructoraVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String editarVt()  {
        try {

            if (otMglBean.tieneAgendasPendientes(ot)) {
                vt = visitaTecnicaMglFacadeLocal.update(vt);
                vt = visitaTecnicaMglFacadeLocal.findById(vt);
                cargarDataVt();
                //cargamos la VT que se creando o editando
                VisitaTecnicaBean vtMglBean =
                        (VisitaTecnicaBean) JSFUtil.
                        getSessionBean(VisitaTecnicaBean.class);
                vtMglBean.setVt(vt);
                vtMglBean.cargarListas();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        Constant.MSN_PROCESO_EXITOSO, ""));
            } else {
                String msg = "La orden de trabajo No: "+ot.getIdOt()+" tiene agendas pendientes por cerrar";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
                return null;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError( Constant.MSN_ERROR_PROCESO + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructoraVtBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public int getPerfilVt() {
        return perfilVt;
    }

    public void setPerfilVt(int perfilVt) {
        this.perfilVt = perfilVt;
    }

    public CmtVisitaTecnicaMgl getVt() {
        return vt;
    }

    public void setVt(CmtVisitaTecnicaMgl vt) {
        this.vt = vt;
    }

    public List<CmtCompaniaMgl> getListcompaniaConstructoras() {
        return listcompaniaConstructoras;
    }

    public void setListcompaniaConstructoras(List<CmtCompaniaMgl> listcompaniaConstructoras) {
        this.listcompaniaConstructoras = listcompaniaConstructoras;
    }

    public CmtCompaniaMgl getConstructora() {
        return constructora;
    }

    public void setConstructora(CmtCompaniaMgl constructora) {
        this.constructora = constructora;
    }

    public List<CmtBasicaMgl> getOrigenDatosList() {
        return origenDatosList;
    }

    public void setOrigenDatosList(List<CmtBasicaMgl> origenDatosList) {
        this.origenDatosList = origenDatosList;
    }

    public CmtBasicaMgl getOrigenDatos() {
        return origenDatos;
    }

    public void setOrigenDatos(CmtBasicaMgl origenDatos) {
        this.origenDatos = origenDatos;
    }

    public CmtBasicaMgl getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(CmtBasicaMgl tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public List<CmtBasicaMgl> getTipoProyectoList() {
        return tipoProyectoList;
    }

    public void setTipoProyectoList(List<CmtBasicaMgl> tipoProyectoList) {
        this.tipoProyectoList = tipoProyectoList;
    }
}
