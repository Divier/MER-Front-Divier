/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm.gestioncompanias;

import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import co.com.claro.mgl.mbeans.cm.CuentaMatrizBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@ManagedBean(name = "gestionCompaniaAdmonBean")
@ViewScoped
public class GestionCompaniaAdmonBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(GestionCompaniaAdmonBean.class);
    private static final String TABCOMPANIACCMM = "TABCOMPANIACCMM";
    private int tipo = ConstantsCM.TIPO_COMPANIA_ID_ADMINISTRACION;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private CuentaMatrizBean cuentaMatrizBean = null;
    private CmtSubEdificioMgl cmtSubEdificioMgl = null;
    private CmtSubEdificioMgl cmtSubEdificioMglTemp = null;
    private CmtCuentaMatrizMgl CmtCuentaMatrizMgl = null;
    private List<CmtCompaniaMgl> listCmtCompaniaMgl = null;
    private List<CmtSubEdificioMgl> listCmtSubEdificioMgl = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    @EJB
    CmtCompaniaMglFacadeLocal companiaFacade;
    @EJB
    CmtSubEdificioMglFacadeLocal subEdificioFacade;
    @EJB
    CmtHorarioRestriccionMglFacadeLocal horarioRestriccionFacadeLocal;
    @EJB
    CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    private ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private  SecurityLogin securityLogin;

    public enum Presentar implements Serializable {

        COMPANIA("Compania"),
        ADMINISTRADOR("Administrador"),
        HORARIO("Horario");
        private String view;

        private Presentar(String view) {
            this.view = view;
        }

        public String getView() {
            return view;
        }
    }
    Presentar vistas;

    /**
     * Creates a new instance of GestionCompaniaAdmonBean
     */
    public GestionCompaniaAdmonBean() {
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
            LOGGER.error("Se generea error en gestion Compania Adminstracion class ..." + ex);
       } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAdmonBean() " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {
        try {
            companiaFacade.setUser(usuarioVT, perfilVt);
            subEdificioFacade.setUser(usuarioVT, perfilVt);
            if (cmtSubEdificioMglTemp == null) {
                CmtCuentaMatrizMgl = this.cuentaMatrizBean.getCuentaMatriz();
                listCmtSubEdificioMgl = subEdificioFacade.findSubEdificioByCuentaMatriz(CmtCuentaMatrizMgl);
                if (listCmtSubEdificioMgl.size() == 1) {
                    
                if (listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj() == null) {
                    listCmtSubEdificioMgl.get(0).setCompaniaAdministracionObj(new CmtCompaniaMgl());
                    if (listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().getTipoCompaniaObj() == null) {
                        listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                        if (listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                            listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION);
                        }
                    }
                } else if (listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().getTipoCompaniaObj() == null) {
                    listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                    listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                    if (listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                        listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION);
                    }
                }
                if (listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId().equals(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION)) {
                    vistas = Presentar.COMPANIA;
                } else {
                    vistas = Presentar.ADMINISTRADOR;
                    cmtSubEdificioMgl.setAdministrador("");
                }

                FiltroConsultaCompaniasDto filtroConsulta = new FiltroConsultaCompaniasDto();
                filtroConsulta.setTipoCompania(listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId());
                filtroConsulta.setEstado("Y");
                if (listCmtSubEdificioMgl.get(0).getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId().equals(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION)) {
                    filtroConsulta.setMunicipio(listCmtSubEdificioMgl.get(0).getCmtCuentaMatrizMglObj().getMunicipio().getGpoId());
                }
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
                if (cmtSubEdificioMgl.getCompaniaAdministracionObj() == null) {
                    cmtSubEdificioMgl.setCompaniaAdministracionObj(new CmtCompaniaMgl());
                    if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj() == null) {
                        cmtSubEdificioMgl.getCompaniaAdministracionObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                        if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                            cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION);
                        }
                    }
                } else if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj() == null) {
                    cmtSubEdificioMgl.getCompaniaAdministracionObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                    cmtSubEdificioMgl.getCompaniaAdministracionObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                    if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                        cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION);
                    }
                }
                if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId().equals(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION)) {
                    vistas = Presentar.COMPANIA;
                } else {
                    vistas = Presentar.ADMINISTRADOR;
                    cmtSubEdificioMgl.setAdministrador("");
                }

                FiltroConsultaCompaniasDto filtroConsulta = new FiltroConsultaCompaniasDto();
                filtroConsulta.setTipoCompania(cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId());
                filtroConsulta.setEstado("Y");
                if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId().equals(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION)) {
                    filtroConsulta.setMunicipio(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getMunicipio().getGpoId());
                }
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
            LOGGER.error("Se generea error en gestion Compania Adminstracion class ..." + ex);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAdmonBean: init() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Método para validar si el usuario tiene permisos para editar compañías administración en CCMM
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
     * Método para validar si el usuario tiene permisos para eliminar horarios en compañías administración de CCMM
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
            CmtCompaniaMgl cmtCompaniaMgl = new CmtCompaniaMgl();
            if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getCompaniaId().compareTo(BigDecimal.ZERO) != 0) {
                cmtCompaniaMgl.setCompaniaId(cmtSubEdificioMgl.getCompaniaAdministracionObj().getCompaniaId());
                cmtCompaniaMgl = companiaFacade.findById(cmtCompaniaMgl);
                cmtSubEdificioMgl.setCompaniaAdministracionObj(cmtCompaniaMgl);
            }
        } catch (ApplicationException ex) {
            String msn2 = "Error al buscar la informacion del compañia " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error("Se generea error en gestion Compania Adminstracion ..." + ex);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAdmonBean: asignarCompania() " + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarCompanias() {
        if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId().equals(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION)) {
            cmtSubEdificioMgl.setCompaniaAdministracionObj(new CmtCompaniaMgl());
            cmtSubEdificioMgl.getCompaniaAdministracionObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
            cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION);
            vistas = Presentar.COMPANIA;
        } else {
            cmtSubEdificioMgl.setCompaniaAdministracionObj(new CmtCompaniaMgl());
            vistas = Presentar.ADMINISTRADOR;
            cmtSubEdificioMgl.setAdministrador("");
            cmtSubEdificioMgl.getCompaniaAdministracionObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
            cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ADMON_NATURAL);
        }

        try {
            FiltroConsultaCompaniasDto filtroConsulta = new FiltroConsultaCompaniasDto();
            filtroConsulta.setTipoCompania(cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId());
            filtroConsulta.setEstado("Y");
            if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId().equals(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION)) {
                filtroConsulta.setMunicipio(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getMunicipio().getGpoId());
            }
            listCmtCompaniaMgl = companiaFacade.findByfiltro(filtroConsulta,false);
            Collections.sort(listCmtCompaniaMgl,
                    new Comparator<CmtCompaniaMgl>() {
                @Override
                public int compare(CmtCompaniaMgl f1, CmtCompaniaMgl f2) {
                    String nameCompanyA = f1.getNombreCompania().trim();
                    String nameCompanyB = f2.getNombreCompania().trim();
                    return nameCompanyA.compareTo(nameCompanyB);
                }
            });

        } catch (ApplicationException ex) {
            String msn2 = "Error al consultar compañias " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error("Se generea error en gestion Compania Adminstracion ..." + ex);
          } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAdmonBean: consultarCompanias() " + e.getMessage(), e, LOGGER);
        }
    }

    public String guardarAsociacion() {
        try {
            if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getCompaniaId().compareTo(BigDecimal.ZERO) == 0) {                
                String msn2 = "Debe seleccionar una Compañia para asociarla a la CM";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn2, null));
                return "";
            }
            subEdificioFacade.updateCompania(cmtSubEdificioMgl, usuarioVT, perfilVt);
            cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMglFacadeLocal.findById(cuentaMatrizBean.getCuentaMatriz()));
            if (cmtSubEdificioMgl.getCompaniaAdministracionObj() == null) {
                cmtSubEdificioMgl.setCompaniaAdministracionObj(new CmtCompaniaMgl());
                cmtSubEdificioMgl.getCompaniaAdministracionObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION);
            }
            String msn2 = "Asociacion exitosa";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn2, null));
        } catch (ApplicationException ex) {
            String msn2 = "Error al asociar " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error("Se generea error en gestion Compania Adminstracion ..." + ex);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAdmonBean: guardarAsociacion() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String irHorarios() {
        if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getCompaniaId().compareTo(BigDecimal.ZERO) == 0) {
            String msn2 = "Debe seleccionar una compañia";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            return null;
        }
        vistas = Presentar.HORARIO;
        consultarHorariosRes();
        return null;
    }

    public String volverDeHorarios() {
        if (cmtSubEdificioMgl.getCompaniaAdministracionObj().getTipoCompaniaObj().getTipoCompaniaId().equals(ConstantsCM.TIPO_COMPANIA_ADMINISTRACION)) {
            vistas = Presentar.COMPANIA;
        } else {
            vistas = Presentar.ADMINISTRADOR;
            cmtSubEdificioMgl.setAdministrador("");
        }
        return null;
    }

    private void consultarHorariosRes() {
        List<CmtHorarioRestriccionMgl> alhorarios = null;
        try {
            alhorarios = horarioRestriccionFacadeLocal.findByHorarioCompania(cmtSubEdificioMgl.getCompaniaAdministracionObj());
        } catch (ApplicationException ex) {
            LOGGER.error("Error al consultar los Horarios de restriccion ..." + ex);
            String msn2 = "Error al consultar info horarios " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            return;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAdmonBean: consultarHorariosRes() " + e.getMessage(), e, LOGGER);
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

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public CmtSubEdificioMgl getCmtSubEdificioMglTemp() {
        return cmtSubEdificioMglTemp;
    }

    public void setCmtSubEdificioMglTemp(CmtSubEdificioMgl cmtSubEdificioMglTemp) {
        this.cmtSubEdificioMglTemp = cmtSubEdificioMglTemp;
    }
    
}
