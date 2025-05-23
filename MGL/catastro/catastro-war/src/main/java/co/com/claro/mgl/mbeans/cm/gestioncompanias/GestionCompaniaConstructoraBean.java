package co.com.claro.mgl.mbeans.cm.gestioncompanias;

import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import co.com.claro.mgl.mbeans.cm.CuentaMatrizBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * @author ADMIN
 */
@ManagedBean(name = "gestionCompaniaConstructoraBean")
@ViewScoped
public class GestionCompaniaConstructoraBean implements Serializable {

    private static final String TABCOMPANIACCMM = "TABCOMPANIACCMM";
    @EJB
    private CmtCompaniaMglFacadeLocal companiaFacade;
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioFacade;
    @EJB
    private CmtHorarioRestriccionMglFacadeLocal horarioRestriccionFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacade;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoMglFacadeLocal;
    @EJB
    private AddressEJBRemote addressEJBRemote;
    @EJB
    private CmtEstablecimientoMglFacadeLocal cmtEstablecimientoMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    private static final Logger LOGGER = LogManager.getLogger(GestionCompaniaConstructoraBean.class);
    private int tipo = ConstantsCM.TIPO_COMPANIA_ID_CONSTRUCTORAS;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private CuentaMatrizBean cuentaMatrizBean = null;
    private CmtSubEdificioMgl cmtSubEdificioMgl = null;
    private CmtSubEdificioMgl cmtSubEdificioMglTemp = null;
    private List<CmtCompaniaMgl> listCmtCompaniaMgl = null;
    private CmtCuentaMatrizMgl cmtCuentaMatrizMgl = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private List<CmtBasicaMgl> listBasicaTiposEstablesimiento;
    private CmtEstablecimientoCmMgl establecimientoCmMgl;

    private ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion;
    private List<GeograficoPoliticoMgl> listGeograficoDepartamentos;
    private List<GeograficoPoliticoMgl> listGeograficoCiudades;
    private List<GeograficoPoliticoMgl> listGeograficoCentrosPoblados;
    private List<CmtSubEdificioMgl> listCmtSubEdificioMgl;
    private boolean botonCrearHabilitado = true;
    private ArrayList<String> listBarrios = new ArrayList<String>();
    private CmtEstablecimientoCmMgl establecimientoNuevo;
    private List<CmtEstablecimientoCmMgl> listCmtEstablecimientoCmMgls = new ArrayList<CmtEstablecimientoCmMgl>();
    private String estiloObligatorio = "<font color='red'>*</font>";
    private List<AuditoriaDto> informacionAuditoria;
    private SecurityLogin securityLogin;

    public enum Presentar implements Serializable {

        COMPANIA("Compania"),
        HORARIO("Horario"),
        AUDITORIAEST("AuditoriaEst"),
        ESTABLESIMIENTOS("Establesimientos");
        private String view;

        private Presentar(String view) {
            this.view = view;
        }

        public String getView() {
            return view;
        }
    }

    Presentar vistas = Presentar.COMPANIA;

    public GestionCompaniaConstructoraBean() {
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
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cmtSubEdificioMglTemp = cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
        } catch (IOException ex) {
            LOGGER.error("Se generea error en gestion Compania Constructoras ..." + ex);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            companiaFacade.setUser(usuarioVT, perfilVt);
            subEdificioFacade.setUser(usuarioVT, perfilVt);
            cmtEstablecimientoMglFacadeLocal.setUser(usuarioVT, perfilVt);

            if (cmtSubEdificioMglTemp == null) {
                cmtCuentaMatrizMgl = this.cuentaMatrizBean.getCuentaMatriz();
                listCmtSubEdificioMgl = subEdificioFacade.findSubEdificioByCuentaMatriz(cmtCuentaMatrizMgl);
                if (listCmtSubEdificioMgl.size() == 1) {
                    if (listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj() == null) {
                        listCmtSubEdificioMgl.get(0).setCompaniaConstructoraObj(new CmtCompaniaMgl());
                        if (listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj().getTipoCompaniaObj() == null) {
                            listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                            if (listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                                listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_CONSTRUCTORAS);
                            }
                        }
                    } else if (listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj().getTipoCompaniaObj() == null) {
                        listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                        listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                        if (listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                            listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_CONSTRUCTORAS);
                        }
                    }

                    FiltroConsultaCompaniasDto filtroConsulta = new FiltroConsultaCompaniasDto();
                    filtroConsulta.setTipoCompania(listCmtSubEdificioMgl.get(0).getCompaniaConstructoraObj().getTipoCompaniaObj().getTipoCompaniaId());
                    filtroConsulta.setMunicipio(listCmtSubEdificioMgl.get(0).getCmtCuentaMatrizMglObj().getMunicipio().getGpoId());
                    filtroConsulta.setEstado("Y");
                    listCmtCompaniaMgl = companiaFacade.findByfiltro(filtroConsulta,false);
                    Collections.sort(listCmtCompaniaMgl,
                            new Comparator<CmtCompaniaMgl>() {
                        @Override
                        public int compare(CmtCompaniaMgl f1, CmtCompaniaMgl f2) {
                            return f1.getNombreCompania().compareTo(f2.getNombreCompania());
                        }
                    });
                }
            } else {
                cmtSubEdificioMgl = subEdificioFacade.findById(cmtSubEdificioMglTemp);
                if (cmtSubEdificioMgl.getCompaniaConstructoraObj() == null) {
                    cmtSubEdificioMgl.setCompaniaConstructoraObj(new CmtCompaniaMgl());
                    if (cmtSubEdificioMgl.getCompaniaConstructoraObj().getTipoCompaniaObj() == null) {
                        cmtSubEdificioMgl.getCompaniaConstructoraObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                        if (cmtSubEdificioMgl.getCompaniaConstructoraObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                            cmtSubEdificioMgl.getCompaniaConstructoraObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ASCENSORES);
                        }
                    }
                } else if (cmtSubEdificioMgl.getCompaniaConstructoraObj().getTipoCompaniaObj() == null) {
                    cmtSubEdificioMgl.getCompaniaConstructoraObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                    cmtSubEdificioMgl.getCompaniaConstructoraObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                    if (cmtSubEdificioMgl.getCompaniaConstructoraObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                        cmtSubEdificioMgl.getCompaniaConstructoraObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ASCENSORES);
                    }
                }

                FiltroConsultaCompaniasDto filtroConsulta = new FiltroConsultaCompaniasDto();
                filtroConsulta.setTipoCompania(cmtSubEdificioMgl.getCompaniaConstructoraObj().getTipoCompaniaObj().getTipoCompaniaId());
                filtroConsulta.setMunicipio(cmtSubEdificioMgl.getCompaniaConstructoraObj().getMunicipio() != null ? cmtSubEdificioMgl.getCompaniaConstructoraObj().getMunicipio().getGpoId():null);
                filtroConsulta.setEstado("Y");
                listCmtCompaniaMgl = companiaFacade.findByfiltro(filtroConsulta,false);
                Collections.sort(listCmtCompaniaMgl,
                        new Comparator<CmtCompaniaMgl>() {
                    @Override
                    public int compare(CmtCompaniaMgl f1, CmtCompaniaMgl f2) {
                        return f1.getNombreCompania().compareTo(f2.getNombreCompania());
                    }
                });
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Se generea error en gestion Compania Constructoras ..." + ex);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: init() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Método para validar si el usuario tiene permisos para editar compañías constructoras en CCMM
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolEditar() {
        try {
            return ValidacionUtil.validarVisualizacion(TABCOMPANIACCMM,
                    ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de edicion de compañias en CCMM. ", e);
        }

        return false;
    }

    /**
     * Método para validar si el usuario tiene permisos para eliminar horarios en compañías constructoras de CCMM
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolEliminar() {
        try {
            return ValidacionUtil.validarVisualizacion(TABCOMPANIACCMM,
                    ValidacionUtil.OPC_BORRADO, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos para eliminar horarios en compañías de CCMM ", e);
        }

        return false;
    }

    public void asignarCompania() {
        try {
            if (cmtSubEdificioMgl.getCompaniaConstructoraObj().getCompaniaId().compareTo(BigDecimal.ZERO) != 0) {
                CmtCompaniaMgl cmtCompaniaMgl = new CmtCompaniaMgl();
                cmtCompaniaMgl.setCompaniaId(cmtSubEdificioMgl.getCompaniaConstructoraObj().getCompaniaId());
                cmtCompaniaMgl = companiaFacade.findById(cmtCompaniaMgl);
                cmtSubEdificioMgl.setCompaniaConstructoraObj(cmtCompaniaMgl);
            }
        } catch (ApplicationException ex) {
            String msn2 = "Error al buscar la informacion del compañia " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error("Se generea error en gestion Compania Constructoras ..." + ex);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: asignarCompania() " + e.getMessage(), e, LOGGER);
        }
    }

    public String guardarAsociacion() {
        try {
            if (cmtSubEdificioMgl.getCompaniaConstructoraObj().getCompaniaId().compareTo(BigDecimal.ZERO) == 0) {
                cmtSubEdificioMgl.setCompaniaConstructoraObj(null);
            }
            subEdificioFacade.update(cmtSubEdificioMgl);
            cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMglFacadeLocal.findById(cuentaMatrizBean.getCuentaMatriz()));

            if (cmtSubEdificioMgl.getCompaniaConstructoraObj() == null) {
                cmtSubEdificioMgl.setCompaniaConstructoraObj(new CmtCompaniaMgl());
                cmtSubEdificioMgl.getCompaniaConstructoraObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                cmtSubEdificioMgl.getCompaniaConstructoraObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_CONSTRUCTORAS);
            }

            String msn2 = "Asociacion exitosa";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn2, null));
        } catch (ApplicationException ex) {
            String msn2 = "Error al asociar " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error("Se generea error en gestion Compania Constructoras ..." + ex);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: guardarAsociacion() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String irHorarios() {
        if (cmtSubEdificioMgl.getCompaniaConstructoraObj().getCompaniaId().compareTo(BigDecimal.ZERO) == 0) {
            String msn2 = "Debe seleccionar una compañia";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            return null;
        }
        vistas = Presentar.HORARIO;
        consultarHorariosRes();
        return null;
    }

    public String irEstablesimientos() {
        try {
            vistas = Presentar.ESTABLESIMIENTOS;
            CmtTipoBasicaMgl basicatTipoMgl;
            basicatTipoMgl = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_ESTABLESIMIENTO);
            listBasicaTiposEstablesimiento = basicaMglFacade.findByTipoBasica(basicatTipoMgl);
            listGeograficoDepartamentos = geograficoMglFacadeLocal.findDptos();
            crearNuevo();
        } catch (ApplicationException ex) {
            LOGGER.error("Se generea error en gestion Compania Constructoras ..." + ex);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: irEstablesimientos() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String volverEstablesimientos() {
        vistas = Presentar.ESTABLESIMIENTOS;
        return null;
    }

    public String volverACompania() {
        vistas = Presentar.COMPANIA;
        return null;
    }

    private void consultarHorariosRes() {
        List<CmtHorarioRestriccionMgl> alhorarios = null;
        try {
            alhorarios = horarioRestriccionFacadeLocal.findByHorarioCompania(cmtSubEdificioMgl.getCompaniaConstructoraObj());
        } catch (ApplicationException ex) {
            LOGGER.error("Error al consultar los Horarios de restriccion ..." + ex);
            String msn2 = "Error al consultar info horarios " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            return;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: consultarHorariosRes() " + e.getMessage(), e, LOGGER);
        }

        horarioRestriccion = null;
        if (alhorarios != null) {
            horarioRestriccion = new ArrayList<CmtHorarioRestriccionMgl>();

            if (!alhorarios.isEmpty() && alhorarios.size() > 0) {
                for (CmtHorarioRestriccionMgl cmtHorarioRestriccionMgl : alhorarios) {
                    horarioRestriccion.add(cmtHorarioRestriccionMgl);
                }
            }
        }
    }

    public void asignarEstablesimiento() {
        try {
            establecimientoNuevo.setTipoEstbObj(basicaMglFacade.findById(establecimientoNuevo.getTipoEstbObj().getBasicaId()));
        } catch (ApplicationException ex) {
            LOGGER.error("Error al consultar los Horarios de restriccion ..." + ex);
            String msn2 = "Error al asignar el establesimiento " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: asignarEstablesimiento() " + e.getMessage(), e, LOGGER);
        }
    }

    public String crearNuevo() {
        establecimientoNuevo = new CmtEstablecimientoCmMgl();
        establecimientoNuevo.setTipoEstbObj(new CmtBasicaMgl());
        establecimientoNuevo.setDepartamentoObj(new GeograficoPoliticoMgl());
        establecimientoNuevo.setCiudadObj(new GeograficoPoliticoMgl());
        establecimientoNuevo.setCentroPobladoObj(new GeograficoPoliticoMgl());
        establecimientoNuevo.getDepartamentoObj().setGpoId(BigDecimal.ZERO);
        establecimientoNuevo.getCiudadObj().setGpoId(BigDecimal.ZERO);
        establecimientoNuevo.getCentroPobladoObj().setGpoId(BigDecimal.ZERO);
        establecimientoNuevo.getTipoEstbObj().setBasicaId(BigDecimal.ZERO);
        establecimientoNuevo.setEstadoRegistro(1);
        listGeograficoCiudades = new ArrayList<GeograficoPoliticoMgl>();
        listGeograficoCentrosPoblados = new ArrayList<GeograficoPoliticoMgl>();
        listBarrios = new ArrayList<String>();
        botonCrearHabilitado = false;
        listCmtEstablecimientoCmMgls = cmtEstablecimientoMglFacadeLocal.finBySubEdificio(cmtSubEdificioMgl);
        return null;
    }

    public String guardarNuevo() {
        try {
            establecimientoNuevo.setSubEdificioObj(cmtSubEdificioMglTemp);
            establecimientoNuevo.setCompaniaObj(cmtSubEdificioMglTemp.getCompaniaConstructoraObj());
            CmtBasicaMgl basicatMgl = new CmtBasicaMgl();
            basicatMgl.setBasicaId(establecimientoNuevo.getTipoEstbObj().getBasicaId());
            establecimientoNuevo.setTipoEstbObj(basicaMglFacade.findById(basicatMgl));
            cmtEstablecimientoMglFacadeLocal.create(establecimientoNuevo);
            listCmtEstablecimientoCmMgls.add(establecimientoNuevo);
            crearNuevo();
            String msn2 = "Operacion culminada con exito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn2, null));
        } catch (ApplicationException ex) {
            String msn2 = "Error al guardar el Establesimiento " + ex.getMessage();
            LOGGER.error(msn2 + ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: guardarNuevo() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String editarEstablesimiento(String establesimiento) {
        try {
            CmtEstablecimientoCmMgl establecimientoCmMglUpdate;
            establecimientoCmMglUpdate = cmtEstablecimientoMglFacadeLocal.finById(new BigDecimal(establesimiento));
            establecimientoNuevo = establecimientoCmMglUpdate;
            listGeograficoCentrosPoblados = geograficoMglFacadeLocal.findCentrosPoblados(establecimientoNuevo.getCiudadObj().getGpoId());
            listGeograficoCiudades = geograficoMglFacadeLocal.findCiudades(establecimientoNuevo.getDepartamentoObj().getGpoId());
            botonCrearHabilitado = true;
            listBarrios = new ArrayList<String>();
            if (establecimientoNuevo.getBarrio() != null && !establecimientoNuevo.getBarrio().isEmpty()) {
                listBarrios.add(establecimientoNuevo.getBarrio());
            }
        } catch (ApplicationException ex) {
            String msn2 = "Error al guardar el Establesimiento " + ex.getMessage();
            LOGGER.error(msn2 + ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: editarEstablesimiento() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String guardarCambio() {
        try {
            cmtEstablecimientoMglFacadeLocal.update(establecimientoNuevo);
            listCmtEstablecimientoCmMgls.remove(establecimientoNuevo);
            listCmtEstablecimientoCmMgls.add(establecimientoNuevo);
            crearNuevo();
            String msn2 = "Operacion culminada con exito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn2, null));
        } catch (ApplicationException ex) {
            String msn2 = "Error al guardar el Establesimiento " + ex.getMessage();
            LOGGER.error(msn2 + ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
          } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: guardarCambio() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String eliminarEstablesimiento(String establesimiento) {
        try {
            CmtEstablecimientoCmMgl establecimientoCmMglDelete;
            establecimientoCmMglDelete = cmtEstablecimientoMglFacadeLocal.finById(new BigDecimal(establesimiento));
            cmtEstablecimientoMglFacadeLocal.delete(establecimientoCmMglDelete);
            listCmtEstablecimientoCmMgls.remove(establecimientoCmMglDelete);
            crearNuevo();
            String msn2 = "Operacion culminada con exito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn2, null));
        } catch (ApplicationException ex) {
            String msn2 = "Error al guardar el Establesimiento " + ex.getMessage();
            LOGGER.error(msn2 + ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: eliminarEstablesimiento() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void consultarCiudades()  {
        try {
            establecimientoNuevo.setCiudadObj(new GeograficoPoliticoMgl());
            establecimientoNuevo.setCentroPobladoObj(new GeograficoPoliticoMgl());
            establecimientoNuevo.getCiudadObj().setGpoId(BigDecimal.ZERO);
            establecimientoNuevo.getCentroPobladoObj().setGpoId(BigDecimal.ZERO);
            if (establecimientoNuevo.getDepartamentoObj() == null) {
                establecimientoNuevo.setDepartamentoObj(new GeograficoPoliticoMgl());
                establecimientoNuevo.getDepartamentoObj().setGpoId(BigDecimal.ZERO);
            }
            listGeograficoCiudades = geograficoMglFacadeLocal.findCiudades(establecimientoNuevo.getDepartamentoObj().getGpoId());
            if (listGeograficoCiudades == null) {
                listGeograficoCiudades = new ArrayList<GeograficoPoliticoMgl>();
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: consultarCiudades() " + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarCentrosPoblados()  {
        try {
            establecimientoNuevo.setCentroPobladoObj(new GeograficoPoliticoMgl());
            establecimientoNuevo.getCentroPobladoObj().setGpoId(BigDecimal.ZERO);
            if (establecimientoNuevo.getCiudadObj() == null) {
                establecimientoNuevo.setCiudadObj(new GeograficoPoliticoMgl());
                establecimientoNuevo.getCiudadObj().setGpoId(BigDecimal.ZERO);
            }
            listGeograficoCentrosPoblados = geograficoMglFacadeLocal.findCentrosPoblados(establecimientoNuevo.getCiudadObj().getGpoId());
            if (listGeograficoCentrosPoblados == null) {
                listGeograficoCentrosPoblados = new ArrayList<GeograficoPoliticoMgl>();
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: consultarCentrosPoblados() " + e.getMessage(), e, LOGGER);
        }
    }

    public void asignarCentroPoblado()  {
    }

    public String validarDireccion() {
        try {
            if (establecimientoNuevo.getDireccion() == null || establecimientoNuevo.getDireccion().equals("")) {
                String msn = "Debe dijitar una dirección";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            if (establecimientoNuevo.getCentroPobladoObj() == null || establecimientoNuevo.getCentroPobladoObj().getGpoId() == null) {
                String msn = "Para validar la direccion se requiere el departamento, ciudad y centro poblado.";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            establecimientoNuevo.setDepartamentoObj(geograficoMglFacadeLocal.findById(establecimientoNuevo.getDepartamentoObj().getGpoId()));
            establecimientoNuevo.setCiudadObj(geograficoMglFacadeLocal.findById(establecimientoNuevo.getCiudadObj().getGpoId()));
            establecimientoNuevo.setCentroPobladoObj(geograficoMglFacadeLocal.findById(establecimientoNuevo.getCentroPobladoObj().getGpoId()));

            AddressService responseSrv;
            AddressRequest requestSrv = new AddressRequest();
            requestSrv.setCity(establecimientoNuevo.getCiudadObj().getGpoNombre());
            requestSrv.setState(establecimientoNuevo.getDepartamentoObj().getGpoNombre());
            requestSrv.setAddress(establecimientoNuevo.getDireccion());
            requestSrv.setCodDaneVt(establecimientoNuevo.getCiudadObj().getGeoCodigoDane());
            requestSrv.setLevel(co.com.telmex.catastro.services.util.Constant.TIPO_CONSULTA_COMPLETA);

            responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);
            if (responseSrv.getTraslate().equalsIgnoreCase("true")) {
                establecimientoNuevo.setDireccion(responseSrv.getAddress());
            }
            listBarrios.clear();
            List<AddressSuggested> barrios = responseSrv.getAddressSuggested();
            if (barrios != null && barrios.size() > 0) {
                for (AddressSuggested addressSuggested : barrios) {
                    if (responseSrv.getAddress().trim().equalsIgnoreCase(addressSuggested.getAddress().trim())) {
                        listBarrios.add(addressSuggested.getNeighborhood());
                    }
                }
            }
            if (responseSrv.getExist().trim().equalsIgnoreCase("false")) {
                String msn = "La dirección no existe. Si la direccion es de tipo [barrio Manzana] "
                        + "asegúrese de haber digitado el barrio en la direccion";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                if (responseSrv.getTraslate().trim().equalsIgnoreCase("true")) {
                    establecimientoNuevo.setDireccion(responseSrv.getAddress());
                }
            } else {
                String msn = "La dirección existe, si en el campo barrios hay informacion seleccione uno";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                if (responseSrv.getTraslate().trim().equalsIgnoreCase("true")) {
                    establecimientoNuevo.setDireccion(responseSrv.getAddress());
                }
            }
            botonCrearHabilitado = true;
        } catch (ApplicationException ex) {
            botonCrearHabilitado = false;
            LOGGER.error(ex.getMessage());
            String msn = "Error en la validación de dirección";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ex.getMessage()));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: validarDireccion() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String irAuditoriaEstablesimiento(CmtEstablecimientoCmMgl establesimiento) {
        try {
            vistas = Presentar.AUDITORIAEST;
            informacionAuditoria = cmtEstablecimientoMglFacadeLocal.construirAuditoria(establesimiento);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            LOGGER.error(ex.getMessage());
            String msn = "Error Cargando auditoria";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ex.getMessage()));
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaConstructoraBean: irAuditoriaEstablesimiento() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void avisaCambiaDireccion() {
        botonCrearHabilitado = false;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Presentar getVistas() {
        return vistas;
    }

    public void setVistas(Presentar vistas) {
        this.vistas = vistas;
    }

    public CmtSubEdificioMgl getCmtSubEdificioMgl() {
        return cmtSubEdificioMgl;
    }

    public void setCmtSubEdificioMgl(CmtSubEdificioMgl cmtSubEdificioMgl) {
        this.cmtSubEdificioMgl = cmtSubEdificioMgl;
    }

    public List<CmtCompaniaMgl> getListCmtCompaniaMgl() {
        return listCmtCompaniaMgl;
    }

    public void setListCmtCompaniaMgl(List<CmtCompaniaMgl> listCmtCompaniaMgl) {
        this.listCmtCompaniaMgl = listCmtCompaniaMgl;
    }

    public ArrayList<CmtHorarioRestriccionMgl> getHorarioRestriccion() {
        return horarioRestriccion;
    }

    public void setHorarioRestriccion(ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion) {
        this.horarioRestriccion = horarioRestriccion;
    }

    public List<CmtBasicaMgl> getListBasicaTiposEstablesimiento() {
        return listBasicaTiposEstablesimiento;
    }

    public void setListBasicaTiposEstablesimiento(List<CmtBasicaMgl> listBasicaTiposEstablesimiento) {
        this.listBasicaTiposEstablesimiento = listBasicaTiposEstablesimiento;
    }

    public CmtEstablecimientoCmMgl getEstablecimientoCmMgl() {
        return establecimientoCmMgl;
    }

    public void setEstablecimientoCmMgl(CmtEstablecimientoCmMgl establecimientoCmMgl) {
        this.establecimientoCmMgl = establecimientoCmMgl;
    }

    public List<GeograficoPoliticoMgl> getListGeograficoDepartamentos() {
        return listGeograficoDepartamentos;
    }

    public void setListGeograficoDepartamentos(List<GeograficoPoliticoMgl> listGeograficoDepartamentos) {
        this.listGeograficoDepartamentos = listGeograficoDepartamentos;
    }

    public List<GeograficoPoliticoMgl> getListGeograficoCiudades() {
        return listGeograficoCiudades;
    }

    public void setListGeograficoCiudades(List<GeograficoPoliticoMgl> listGeograficoCiudades) {
        this.listGeograficoCiudades = listGeograficoCiudades;
    }

    public List<GeograficoPoliticoMgl> getListGeograficoCentrosPoblados() {
        return listGeograficoCentrosPoblados;
    }

    public void setListGeograficoCentrosPoblados(List<GeograficoPoliticoMgl> listGeograficoCentrosPoblados) {
        this.listGeograficoCentrosPoblados = listGeograficoCentrosPoblados;
    }

    public boolean isBotonCrearHabilitado() {
        return botonCrearHabilitado;
    }

    public void setBotonCrearHabilitado(boolean botonCrearHabilitado) {
        this.botonCrearHabilitado = botonCrearHabilitado;
    }

    public ArrayList<String> getListBarrios() {
        return listBarrios;
    }

    public void setListBarrios(ArrayList<String> listBarrios) {
        this.listBarrios = listBarrios;
    }

    public CmtEstablecimientoCmMgl getEstablecimientoNuevo() {
        return establecimientoNuevo;
    }

    public void setEstablecimientoNuevo(CmtEstablecimientoCmMgl establecimientoNuevo) {
        this.establecimientoNuevo = establecimientoNuevo;
    }

    public List<CmtEstablecimientoCmMgl> getListCmtEstablecimientoCmMgls() {
        return listCmtEstablecimientoCmMgls;
    }

    public void setListCmtEstablecimientoCmMgls(List<CmtEstablecimientoCmMgl> listCmtEstablecimientoCmMgls) {
        this.listCmtEstablecimientoCmMgls = listCmtEstablecimientoCmMgls;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public List<AuditoriaDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
    }

}
