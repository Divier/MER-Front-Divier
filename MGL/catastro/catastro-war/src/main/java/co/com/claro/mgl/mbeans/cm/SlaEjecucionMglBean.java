/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.constantes.cobertura.Constants;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.TipoOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoOtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.DetalleSlaEjeMglFacadeLocal;
import co.com.claro.mgl.facade.cm.SlaEjecucionMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.DetalleSlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
 * @author bocanegravm
 */
@ManagedBean(name = "slaEjecucionMglBean")
@ViewScoped
public class SlaEjecucionMglBean implements Serializable {

    @EJB
    private SlaEjecucionMglFacadeLocal slaEjecucionMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private TipoOtHhppMglFacadeLocal tipoOtHhppMglFacadeLocal;
    @EJB
    private CmtTipoOtMglFacadeLocal cmtTipoOtMglFacadeLocal;
    @EJB
    private DetalleSlaEjeMglFacadeLocal detalleSlaEjeMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;

    private static final Logger LOGGER = LogManager.getLogger(SlaEjecucionMglBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private Integer perfilVt;
    private SecurityLogin securityLogin;
    private List<SlaEjecucionMgl> lstSlaEjecucionMgl;
    private boolean mostrarListaSlaEjecucion;
    private int filasPag15 = Constants.PAGINACION_QUINCE_FILAS;
    private SlaEjecucionMgl slaEjecucionMglSelected;
    private final String FORMULARIO = "SLAEJECUCION";
    private final String NUEVO = "NUEVO";
    private final String DETALLE = "DETALLE";
    private final String LOG = "LOG";
    private final String ELIMINARSLAEJE = "ELIMINARSLAEJE";
    private final String ELIMINARDETSLA = "ELIMINARDETSLA";
    private final String CREARSLAEJE = "CREARSLAEJE";
    private final String CREARDETSLA = "CREARDETSLA";
    private final String EDITAR = "EDITAR";
    private final String ROLOPCNEWSLA = "ROLOPCNEWSLA";
    private final String ROLOPCEJESLA = "ROLOPCEJESLA";
    private final String ROLOPCDELSLA = "ROLOPCDELSLA";
    private boolean renderAuditoria;
    private boolean mostrarAdminSlaEjecucion;
    private SlaEjecucionMgl slaEjecucionMgl;
    private boolean botonCrear;
    private boolean botonMod;
    private List<AuditoriaDto> informacionAuditoria = null;
    private boolean habilitaTecno;
    private BigDecimal idTecno;
    private boolean habilitaTipo;
    private boolean botonCrearSlaEje;
    private List<CmtBasicaMgl> tecnologiaList;
    private boolean mostrarListaSlaEjecucionDetalle;
    private BigDecimal idSubTipoCcmm;
    private BigDecimal idSubTipoHhpp;
    private List<CmtTipoOtMgl> listSubTipoOtCcmm;
    private List<TipoOtHhppMgl> listSubTipoOtUnidad;
    private DetalleSlaEjecucionMgl detalleSlaEjecucionMgl;
    private int sla;
    private List<DetalleSlaEjecucionMgl> lstDetalleSlaEjecucionMgl;
    private boolean controlLabelCcmmComboDetalle;
    private boolean controlCcmmComboDetalle;
    private boolean controlLabelHhppComboDetalle;
    private boolean controlHhppComboDetalle;
    private int sequenciaOpe;
    private BigDecimal idEstCcmm;
    private List<CmtBasicaMgl> listEstadosCCMM;
    private Integer ultimoElemento = -1;

    /**
     * Creates a new instance of SlaEjecucionMglBean
     */
    public SlaEjecucionMglBean() {
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
            FacesUtil.mostrarMensajeError("Error en SlaEjecucionMglBean:" + e.getMessage() + " ", e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SlaEjecucionMglBean: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            lstSlaEjecucionMgl = slaEjecucionMglFacadeLocal.findAll();
            mostrarListaSlaEjecucion = true;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en SlaEjecucionMglBean: " + ex.getMessage() + " ", ex, LOGGER);
        }
    }

    public void irSlaEjecucionDetalle(SlaEjecucionMgl slaEjecucionMglSel) throws ApplicationException {

        if (slaEjecucionMglSel != null) {
            slaEjecucionMgl = slaEjecucionMglSel;
            obtenerListTecnologias();
            idTecno = slaEjecucionMgl.getBasicaIdTecnologia().getBasicaId();
            lstDetalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.findBySlaEjecucion(slaEjecucionMgl, true);
            if (slaEjecucionMgl.getTipoEjecucion().equalsIgnoreCase("CCMM")) {
                controlLabelCcmmComboDetalle = true;
                controlCcmmComboDetalle = true;
                controlLabelHhppComboDetalle = false;
                controlHhppComboDetalle = false;
                listSubTipoOtCcmm = cmtTipoOtMglFacadeLocal
                        .findSubTipoOtByTecno(slaEjecucionMgl.getBasicaIdTecnologia());
                sequenciaOpe = 0;
                sla = 0;
                idSubTipoCcmm = null;
                idEstCcmm = null;

                CmtTipoBasicaMgl cmtTipoBasicaMgl = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);

                listEstadosCCMM = basicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);

            } else if (slaEjecucionMgl.getTipoEjecucion().equalsIgnoreCase("UNIDAD")) {
                controlLabelCcmmComboDetalle = false;
                controlCcmmComboDetalle = false;
                controlLabelHhppComboDetalle = true;
                controlHhppComboDetalle = true;
                listSubTipoOtUnidad = tipoOtHhppMglFacadeLocal.findAll();
                listEstadosCCMM = null;
                sequenciaOpe = 0;
                sla = 0;
                idSubTipoHhpp = null;
            }
            mostrarListaSlaEjecucionDetalle = true;
            mostrarAdminSlaEjecucion = true;
            mostrarListaSlaEjecucion = false;
            habilitaTecno = true;
            habilitaTipo = true;
            botonCrear = false;
            botonMod = true;
        }

    }

    public void eliminarSlaEjecucionList(SlaEjecucionMgl slaEjecucionMglElimina) throws ApplicationException {

        boolean elimina = slaEjecucionMglFacadeLocal.delete(slaEjecucionMglElimina, usuarioVT, perfilVt);
        if (elimina) {
            String msnError = "Registro eliminado satisfatoriamente";
            info(msnError);
            lstSlaEjecucionMgl = slaEjecucionMglFacadeLocal.findAll();
        } else {
            String msnError = "Currio un error  eliminando el registro";
            error(msnError);
        }
    }

    public void eliminardetalleSlaEjeList(DetalleSlaEjecucionMgl detalleSlaEjecucionMgl)
            throws ApplicationException {

        boolean elimina = detalleSlaEjeMglFacadeLocal.delete(detalleSlaEjecucionMgl, usuarioVT, perfilVt);
        if (elimina) {
            String msnError = "Registro eliminado satisfatoriamente";
            info(msnError);
            lstDetalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.findBySlaEjecucion(slaEjecucionMgl, true);
        } else {
            String msnError = "Currio un error  eliminando el registro";
            error(msnError);
        }
    }

    public void volverPaneListSlaEjecucion() throws ApplicationException {

        mostrarListaSlaEjecucion = true;
        mostrarAdminSlaEjecucion = false;
        mostrarListaSlaEjecucionDetalle = false;
        lstSlaEjecucionMgl = slaEjecucionMglFacadeLocal.findAll();

    }

    public void irToCrearSlaEjecucion() throws ApplicationException {

        slaEjecucionMgl = new SlaEjecucionMgl();
        mostrarAdminSlaEjecucion = true;
        mostrarListaSlaEjecucion = false;
        habilitaTecno = false;
        habilitaTipo = false;
        obtenerListTecnologias();
        botonCrearSlaEje = true;
        botonMod = false;
        idSubTipoCcmm = null;
        idSubTipoHhpp = null;
        idEstCcmm = null;
        idTecno = null;
    }

    public void obtenerListTecnologias() throws ApplicationException {

        CmtTipoBasicaMgl tipoBasicaTecnologias;
        tipoBasicaTecnologias = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                Constant.TIPO_BASICA_TECNOLOGIA);
        tecnologiaList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologias);

    }

    public void crearSlaEjecucion() throws ApplicationException {

        if (idTecno == null && slaEjecucionMgl.getTipoEjecucion() == null) {
            String msg = "Debe seleccionar una tecnologia y un tipo de ejecucion "
                    + " para la creacion del registro.";
            warn(msg);
        } else if (idTecno == null) {
            String msg = "Debe seleccionar una tecnologia para la creacion del registro.";
            warn(msg);
        } else if (slaEjecucionMgl.getTipoEjecucion() == null) {
            String msg = "Debe seleccionar un tipo de ejecucion para la creacion del registro.";
            warn(msg);
        } else {
            CmtBasicaMgl tecnologia = basicaMglFacadeLocal.findById(idTecno);
            if (tecnologia != null) {
                SlaEjecucionMgl consulta = slaEjecucionMglFacadeLocal.findByTecnoAndEjecucion(tecnologia,
                        slaEjecucionMgl.getTipoEjecucion());
                if (consulta != null && consulta.getSlaEjecucionId() != null) {
                    String msg = "Existe el Sla de ejecucion:  "
                            + "" + consulta.getSlaEjecucionId() + " con la misma configuracion .";
                    warn(msg);
                } else {
                    slaEjecucionMgl.setBasicaIdTecnologia(tecnologia);
                    slaEjecucionMgl = slaEjecucionMglFacadeLocal.create(slaEjecucionMgl, usuarioVT, perfilVt);
                    if (slaEjecucionMgl.getSlaEjecucionId() != null) {
                        String msg = "Sla de ejecucion creado satisfatoriamente .";
                        info(msg);
                        habilitaTipo = true;
                        habilitaTecno = true;
                        mostrarListaSlaEjecucionDetalle = true;
                        lstDetalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.findBySlaEjecucion(slaEjecucionMgl, true);
                        sla = 0;
                        sequenciaOpe = 0;
                        if (slaEjecucionMgl.getTipoEjecucion().equalsIgnoreCase("CCMM")) {
                            listSubTipoOtCcmm = cmtTipoOtMglFacadeLocal
                                    .findSubTipoOtByTecno(slaEjecucionMgl.getBasicaIdTecnologia());
                            controlCcmmComboDetalle = true;
                            controlLabelCcmmComboDetalle = true;
                            controlLabelHhppComboDetalle = false;
                            controlHhppComboDetalle = false;
                            CmtTipoBasicaMgl cmtTipoBasicaMgl = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);

                            listEstadosCCMM = basicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
                        } else if (slaEjecucionMgl.getTipoEjecucion().equalsIgnoreCase("UNIDAD")) {
                            listSubTipoOtUnidad = tipoOtHhppMglFacadeLocal.findAll();
                            controlLabelHhppComboDetalle = true;
                            controlHhppComboDetalle = true;
                            controlCcmmComboDetalle = false;
                            controlLabelCcmmComboDetalle = false;
                            listEstadosCCMM = null;
                        }
                    } else {
                        String msg = "Ocurrio un error al momento de crear el registro";
                        error(msg);
                    }
                }
            } else {
                String msg = "No se encontro una tecnologia para el id: " + idTecno + ".";
                warn(msg);
            }
        }
    }

    /**
     * <b>Mensaje de Advertencia</b>.
     *
     * M&eacute;todo que muestra el mensaje de advertencia, que llega como
     * par&aacute;metro, en la vista.
     *
     * @param mensaje Mensaje a mostrar en pantalla.
     */
    private void warn(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, ""));
    }

    /**
     * <b>Mensaje de Informaci&oacute;n</b>.
     *
     * metodo que muestra el mensaje de informacion, que llega como parametro,
     * en la vista
     *
     * @author Victor Bocanegra
     * @param mensaje
     */
    public void info(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensaje));
    }

    /**
     * <b>Mensaje de Error</b>.
     *
     * M&eacute;todo que muestra el mensaje de error, que llega como
     * par&aacute;metro, en la vista.
     *
     * @param mensaje Mensaje a mostrar en pantalla.
     */
    private void error(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
    }

    public void mostrarAuditoria(SlaEjecucionMgl slaEjecucionMglSel) {
        if (slaEjecucionMglSel != null) {
            try {
                informacionAuditoria = slaEjecucionMglFacadeLocal.construirAuditoria(slaEjecucionMglSel);
                renderAuditoria = true;
                slaEjecucionMglSelected = slaEjecucionMglSel;
                mostrarListaSlaEjecucion = false;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                String msn = "Se presenta error al mostrar la auditoria";
                FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            }
        } else {
            String msn = "No se encontro informacion de auditoria";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }

    }

    private boolean setSecuenciaRegistro(DetalleSlaEjecucionMgl detalleSlaEjecucionMgl) throws ApplicationException {
        boolean resultInsert = false;
        List<DetalleSlaEjecucionMgl> lstDetalleSlaEjecucionMglTemp = new ArrayList<>();
        lstDetalleSlaEjecucionMglTemp = lstDetalleSlaEjecucionMgl;

        if (this.slaEjecucionMgl != null) {
            if (!lstDetalleSlaEjecucionMglTemp.isEmpty()) {
                Integer posicion = lstDetalleSlaEjecucionMglTemp.size() - 1;
                
                Integer slaSecuenciaLst = lstDetalleSlaEjecucionMglTemp.get(posicion).getSeqProceso();
                Integer slaSecuenciaIns = detalleSlaEjecucionMgl.getSeqProceso();
                resultInsert = (slaSecuenciaIns == (slaSecuenciaLst + 1));
                ultimoElemento = slaSecuenciaLst;
            }
        }
        if (lstDetalleSlaEjecucionMglTemp.isEmpty()) {
            resultInsert = true;
        }
        
        return resultInsert;
    }

    public void crearDetalleSlaEjecucion() throws ApplicationException {

        detalleSlaEjecucionMgl = new DetalleSlaEjecucionMgl();

        if (slaEjecucionMgl != null && slaEjecucionMgl.getTipoEjecucion().equalsIgnoreCase("CCMM")) {
            if (sequenciaOpe == 0 && idSubTipoCcmm == null && sla == 0 && idEstCcmm == null) {
                String msg = "Debe ingresar un numero de sequencia  operacion, "
                        + "un sub tipo de trabajo, un Sla y un estado de CCMM valido";
                warn(msg);
            } else if (sequenciaOpe == 0) {
                String msg = "Debe ingresar un numero de sequencia de operacion valido";
                warn(msg);
            } else if (idSubTipoCcmm == null) {
                String msg = "Debe ingresar un sub tipo de trabajo  valido";
                warn(msg);
            } else if (sla == 0) {
                String msg = "Debe ingresar un tiempo de sla  valido";
                warn(msg);
            } else if (idEstCcmm == null) {
                String msg = "Debe ingresar un estado de CCMM valido";
                warn(msg);
            } else {
                detalleSlaEjecucionMgl.setSlaEjecucionMgl(slaEjecucionMgl);
                detalleSlaEjecucionMgl.setSla(sla);
                detalleSlaEjecucionMgl.setSeqProceso(sequenciaOpe);

                boolean secuenciaInsercion = this.setSecuenciaRegistro(detalleSlaEjecucionMgl);
                if (secuenciaInsercion) {
                    CmtBasicaMgl estadoCcmm = basicaMglFacadeLocal.findById(idEstCcmm);
                    detalleSlaEjecucionMgl.setEstadoCcmm(estadoCcmm);
                    CmtTipoOtMgl tipoOtCCmm = cmtTipoOtMglFacadeLocal.findById(idSubTipoCcmm);
                    if (tipoOtCCmm != null) {
                        detalleSlaEjecucionMgl.setSubTipoOtCCMM(tipoOtCCmm);
                        DetalleSlaEjecucionMgl detalleSlaEje = detalleSlaEjeMglFacadeLocal
                                .findBySlaEjecucionAndTipoEje(slaEjecucionMgl, tipoOtCCmm, null);
                        if (detalleSlaEje == null) {
                            lstDetalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.findBySlaEjecucion(slaEjecucionMgl,
                                    false);
                            int control = 0;
                            if (lstDetalleSlaEjecucionMgl != null && !lstDetalleSlaEjecucionMgl.isEmpty()) {
                                for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl1 : lstDetalleSlaEjecucionMgl) {
                                    if (detalleSlaEjecucionMgl1.getSeqProceso() == sequenciaOpe) {
                                        control++;
                                    }
                                }
                                if (control > 0) {
                                    String msg = "Ya existe un registro con sequencia de operacion numero: " + sequenciaOpe
                                            + "";
                                    warn(msg);
                                } else {
                                    detalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.create(detalleSlaEjecucionMgl,
                                            usuarioVT, perfilVt);
                                    if (detalleSlaEjecucionMgl.getDetSlaEjecucionId() != null) {
                                        String msg = "Detalle de Sla de ejecucion creado satisfatoriamente";
                                        info(msg);
                                        sla = 0;
                                        sequenciaOpe = 0;
                                        idSubTipoCcmm = null;
                                        idEstCcmm = null;
                                        lstDetalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.findBySlaEjecucion(slaEjecucionMgl, true);

                                    } else {
                                        String msg = "Ocurrio un error al momento de crear el detalle de Sla";
                                        error(msg);
                                    }
                                }
                            } else {
                                detalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.create(detalleSlaEjecucionMgl,
                                        usuarioVT, perfilVt);
                                if (detalleSlaEjecucionMgl.getDetSlaEjecucionId() != null) {
                                    String msg = "Detalle de Sla de ejecucion creado satisfatoriamente";
                                    info(msg);
                                    sla = 0;
                                    sequenciaOpe = 0;
                                    idSubTipoCcmm = null;
                                    idEstCcmm = null;
                                    lstDetalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.findBySlaEjecucion(slaEjecucionMgl, true);
                                } else {
                                    String msg = "Ocurrio un error al momento de crear el detalle de Sla";
                                    error(msg);
                                }
                            }
                        } else {
                            String msg = "Ya existe un detalle de ejecucion con el subTipo:" + tipoOtCCmm.getDescTipoOt()
                                    + "";
                            error(msg);
                        }
                    }
                } else {
                    String msg = "No es permitido insertar el detalle SLA, 'Secuencia Operacion' no es secuencial"; 
                    warn((ultimoElemento == -1) ? msg : (msg + ", Ultimo elemento : " + ultimoElemento));
                    ultimoElemento = -1;
                }
            }
        } else if (slaEjecucionMgl != null && slaEjecucionMgl.getTipoEjecucion().equalsIgnoreCase("UNIDAD")) {

            if (sequenciaOpe == 0 && idSubTipoHhpp == null && sla == 0) {
                String msg = "Debe ingresar un numero de sequencia  operacion, "
                        + "un sub tipo de trabajo y un Sla valido";
                warn(msg);
            } else if (sequenciaOpe == 0) {
                String msg = "Debe ingresar un numero de sequencia de operacion valido";
                warn(msg);
            } else if (idSubTipoHhpp == null) {
                String msg = "Debe ingresar un sub tipo de trabajo  valido";
                warn(msg);
            } else if (sla == 0) {
                String msg = "Debe ingresar un tiempo de sla  valido";
                warn(msg);
            } else {
                detalleSlaEjecucionMgl.setSlaEjecucionMgl(slaEjecucionMgl);
                detalleSlaEjecucionMgl.setSla(sla);
                detalleSlaEjecucionMgl.setSeqProceso(sequenciaOpe);

                boolean secuenciaInsercion = this.setSecuenciaRegistro(detalleSlaEjecucionMgl);
                if (secuenciaInsercion) {
                    TipoOtHhppMgl tipoOtHhppMgl = tipoOtHhppMglFacadeLocal.findTipoOtHhppById(idSubTipoHhpp);
                    if (tipoOtHhppMgl != null) {
                        detalleSlaEjecucionMgl.setSubTipoOtUnidad(tipoOtHhppMgl);
                        DetalleSlaEjecucionMgl detalleSlaEje = detalleSlaEjeMglFacadeLocal
                                .findBySlaEjecucionAndTipoEje(slaEjecucionMgl, null, tipoOtHhppMgl);
                        if (detalleSlaEje == null) {
                            lstDetalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.findBySlaEjecucion(slaEjecucionMgl,
                                    false);
                            int control = 0;
                            if (lstDetalleSlaEjecucionMgl != null && !lstDetalleSlaEjecucionMgl.isEmpty()) {
                                for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl1 : lstDetalleSlaEjecucionMgl) {
                                    if (detalleSlaEjecucionMgl1.getSeqProceso() == sequenciaOpe) {
                                        control++;
                                    }
                                }
                                if (control > 0) {
                                    String msg = "Ya existe un registro con sequencia de operacion numero: " + sequenciaOpe
                                            + "";
                                    warn(msg);
                                } else {
                                    detalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.create(detalleSlaEjecucionMgl,
                                            usuarioVT, perfilVt);
                                    if (detalleSlaEjecucionMgl.getDetSlaEjecucionId() != null) {
                                        String msg = "Detalle de Sla de ejecucion creado satisfatoriamente";
                                        info(msg);
                                        sla = 0;
                                        sequenciaOpe = 0;
                                        idSubTipoHhpp = null;
                                        lstDetalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.findBySlaEjecucion(slaEjecucionMgl, true);
                                    } else {
                                        String msg = "Ocurrio un error al momento de crear el detalle de Sla";
                                        error(msg);
                                    }
                                }
                            } else {

                                detalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.create(detalleSlaEjecucionMgl,
                                        usuarioVT, perfilVt);
                                if (detalleSlaEjecucionMgl.getDetSlaEjecucionId() != null) {
                                    String msg = "Detalle de Sla de ejecucion creado satisfatoriamente";
                                    info(msg);
                                    sla = 0;
                                    sequenciaOpe = 0;
                                    idSubTipoHhpp = null;
                                    lstDetalleSlaEjecucionMgl = detalleSlaEjeMglFacadeLocal.findBySlaEjecucionPaginated();
                                } else {
                                    String msg = "Ocurrio un error al momento de crear el detalle de Sla";
                                    error(msg);
                                }
                            }
                        } else {
                            String msg = "Ya existe un detalle de ejecucion con el subTipo:"
                                    + tipoOtHhppMgl.getNombreTipoOt() + "";
                            error(msg);
                        }
                    }
                } else {
                    String msg = "No es permitido insertar el detalle SLA, 'Secuencia Operacion' no es secuencial"; 
                    warn((ultimoElemento == -1) ? msg : (msg + ", Ultimo elemento : " + ultimoElemento));
                    ultimoElemento = -1;
                }
            }
        }
    }

    private boolean validarPermisos(String accion) {
        try {
            return ValidacionUtil.validarVisualizacionRol(FORMULARIO, accion, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar permisos. EX000 " + e.getMessage(), e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION, ""));
            return false;
        }
    }

    public void ocultarAuditoria() {

        renderAuditoria = false;
        mostrarListaSlaEjecucion = true;
        mostrarAdminSlaEjecucion = false;

    }

    public boolean validarNuevo() {
        return validarPermisos(NUEVO);
    }

    public boolean validarDetalle() {
        return validarPermisos(DETALLE);
    }

    public boolean validarLOG() {
        return validarPermisos(LOG);
    }

    public boolean validarEliminarSLAEjecucion() {
        return validarPermisos(ELIMINARSLAEJE);
    }

    public boolean validarEliminarDetSlaEjecucion() {
        return validarPermisos(ELIMINARDETSLA);
    }

    public boolean validarCrearDetSla() {
        return validarPermisos(CREARDETSLA);
    }

    public boolean validarCrearSlaEjecucion() {
        return validarPermisos(CREARSLAEJE);
    }

    public boolean validarUpdate() {
        return validarPermisos(EDITAR);
    }

    public boolean validarNuevoRol() {
        return validarEdicion(ROLOPCNEWSLA);
    }

    public boolean validarIdEjecucionRol() {
        return validarEdicion(ROLOPCEJESLA);
    }

    public boolean validarEliminarRol() {
        return validarEdicion(ROLOPCDELSLA);
    }

    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION,
                    cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public List<SlaEjecucionMgl> getLstSlaEjecucionMgl() {
        return lstSlaEjecucionMgl;
    }

    public void setLstSlaEjecucionMgl(List<SlaEjecucionMgl> lstSlaEjecucionMgl) {
        this.lstSlaEjecucionMgl = lstSlaEjecucionMgl;
    }

    public boolean isMostrarListaSlaEjecucion() {
        return mostrarListaSlaEjecucion;
    }

    public void setMostrarListaSlaEjecucion(boolean mostrarListaSlaEjecucion) {
        this.mostrarListaSlaEjecucion = mostrarListaSlaEjecucion;
    }

    public int getFilasPag15() {
        return filasPag15;
    }

    public void setFilasPag15(int filasPag15) {
        this.filasPag15 = filasPag15;
    }

    public SlaEjecucionMgl getSlaEjecucionMglSelected() {
        return slaEjecucionMglSelected;
    }

    public void setSlaEjecucionMglSelected(SlaEjecucionMgl slaEjecucionMglSelected) {
        this.slaEjecucionMglSelected = slaEjecucionMglSelected;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }

    public boolean isMostrarAdminSlaEjecucion() {
        return mostrarAdminSlaEjecucion;
    }

    public void setMostrarAdminSlaEjecucion(boolean mostrarAdminSlaEjecucion) {
        this.mostrarAdminSlaEjecucion = mostrarAdminSlaEjecucion;
    }

    public SlaEjecucionMgl getSlaEjecucionMgl() {
        return slaEjecucionMgl;
    }

    public void setSlaEjecucionMgl(SlaEjecucionMgl slaEjecucionMgl) {
        this.slaEjecucionMgl = slaEjecucionMgl;
    }

    public boolean isBotonCrear() {
        return botonCrear;
    }

    public void setBotonCrear(boolean botonCrear) {
        this.botonCrear = botonCrear;
    }

    public boolean isBotonMod() {
        return botonMod;
    }

    public void setBotonMod(boolean botonMod) {
        this.botonMod = botonMod;
    }

    public List<AuditoriaDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
    }

    public boolean isHabilitaTecno() {
        return habilitaTecno;
    }

    public void setHabilitaTecno(boolean habilitaTecno) {
        this.habilitaTecno = habilitaTecno;
    }

    public BigDecimal getIdTecno() {
        return idTecno;
    }

    public void setIdTecno(BigDecimal idTecno) {
        this.idTecno = idTecno;
    }

    public boolean isHabilitaTipo() {
        return habilitaTipo;
    }

    public void setHabilitaTipo(boolean habilitaTipo) {
        this.habilitaTipo = habilitaTipo;
    }

    public boolean isBotonCrearSlaEje() {
        return botonCrearSlaEje;
    }

    public void setBotonCrearSlaEje(boolean botonCrearSlaEje) {
        this.botonCrearSlaEje = botonCrearSlaEje;
    }

    public List<CmtBasicaMgl> getTecnologiaList() {
        return tecnologiaList;
    }

    public void setTecnologiaList(List<CmtBasicaMgl> tecnologiaList) {
        this.tecnologiaList = tecnologiaList;
    }

    public boolean isMostrarListaSlaEjecucionDetalle() {
        return mostrarListaSlaEjecucionDetalle;
    }

    public void setMostrarListaSlaEjecucionDetalle(boolean mostrarListaSlaEjecucionDetalle) {
        this.mostrarListaSlaEjecucionDetalle = mostrarListaSlaEjecucionDetalle;
    }

    public BigDecimal getIdSubTipoCcmm() {
        return idSubTipoCcmm;
    }

    public void setIdSubTipoCcmm(BigDecimal idSubTipoCcmm) {
        this.idSubTipoCcmm = idSubTipoCcmm;
    }

    public BigDecimal getIdSubTipoHhpp() {
        return idSubTipoHhpp;
    }

    public void setIdSubTipoHhpp(BigDecimal idSubTipoHhpp) {
        this.idSubTipoHhpp = idSubTipoHhpp;
    }

    public List<CmtTipoOtMgl> getListSubTipoOtCcmm() {
        return listSubTipoOtCcmm;
    }

    public void setListSubTipoOtCcmm(List<CmtTipoOtMgl> listSubTipoOtCcmm) {
        this.listSubTipoOtCcmm = listSubTipoOtCcmm;
    }

    public List<TipoOtHhppMgl> getListSubTipoOtUnidad() {
        return listSubTipoOtUnidad;
    }

    public void setListSubTipoOtUnidad(List<TipoOtHhppMgl> listSubTipoOtUnidad) {
        this.listSubTipoOtUnidad = listSubTipoOtUnidad;
    }

    public DetalleSlaEjecucionMgl getDetalleSlaEjecucionMgl() {
        return detalleSlaEjecucionMgl;
    }

    public void setDetalleSlaEjecucionMgl(DetalleSlaEjecucionMgl detalleSlaEjecucionMgl) {
        this.detalleSlaEjecucionMgl = detalleSlaEjecucionMgl;
    }

    public int getSla() {
        return sla;
    }

    public void setSla(int sla) {
        this.sla = sla;
    }

    public List<DetalleSlaEjecucionMgl> getLstDetalleSlaEjecucionMgl() {
        return lstDetalleSlaEjecucionMgl;
    }

    public void setLstDetalleSlaEjecucionMgl(List<DetalleSlaEjecucionMgl> lstDetalleSlaEjecucionMgl) {
        this.lstDetalleSlaEjecucionMgl = lstDetalleSlaEjecucionMgl;
    }

    public boolean isControlLabelCcmmComboDetalle() {
        return controlLabelCcmmComboDetalle;
    }

    public void setControlLabelCcmmComboDetalle(boolean controlLabelCcmmComboDetalle) {
        this.controlLabelCcmmComboDetalle = controlLabelCcmmComboDetalle;
    }

    public boolean isControlCcmmComboDetalle() {
        return controlCcmmComboDetalle;
    }

    public void setControlCcmmComboDetalle(boolean controlCcmmComboDetalle) {
        this.controlCcmmComboDetalle = controlCcmmComboDetalle;
    }

    public boolean isControlLabelHhppComboDetalle() {
        return controlLabelHhppComboDetalle;
    }

    public void setControlLabelHhppComboDetalle(boolean controlLabelHhppComboDetalle) {
        this.controlLabelHhppComboDetalle = controlLabelHhppComboDetalle;
    }

    public boolean isControlHhppComboDetalle() {
        return controlHhppComboDetalle;
    }

    public void setControlHhppComboDetalle(boolean controlHhppComboDetalle) {
        this.controlHhppComboDetalle = controlHhppComboDetalle;
    }

    public int getSequenciaOpe() {
        return sequenciaOpe;
    }

    public void setSequenciaOpe(int sequenciaOpe) {
        this.sequenciaOpe = sequenciaOpe;
    }

    public BigDecimal getIdEstCcmm() {
        return idEstCcmm;
    }

    public void setIdEstCcmm(BigDecimal idEstCcmm) {
        this.idEstCcmm = idEstCcmm;
    }

    public List<CmtBasicaMgl> getListEstadosCCMM() {
        return listEstadosCCMM;
    }

    public void setListEstadosCCMM(List<CmtBasicaMgl> listEstadosCCMM) {
        this.listEstadosCCMM = listEstadosCCMM;
    }
}
