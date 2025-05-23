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
@ManagedBean(name = "gestionCompaniaAscensoresBean")
@ViewScoped
public class GestionCompaniaAscensoresBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(GestionCompaniaAscensoresBean.class);
    private static final String TABCOMPANIACCMM = "TABCOMPANIACCMM";
    private int tipo = ConstantsCM.TIPO_COMPANIA_ID_ASCENSORES;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private CuentaMatrizBean cuentaMatrizBean = null;
    private CmtSubEdificioMgl cmtSubEdificioMgl = null;
    private CmtSubEdificioMgl cmtSubEdificioMglTemp = null;
    private List<CmtCompaniaMgl> listCmtCompaniaMgl = null;
    private List<CmtSubEdificioMgl> listCmtSubEdificioMgl = null;
    private CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
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
        HORARIO("Horario");
        private String view;

        private Presentar(String view) {
            this.view = view;
        }

        public String getView() {
            return view;
        }
    }
    Presentar vistas = Presentar.COMPANIA;

    public GestionCompaniaAscensoresBean() {
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
            LOGGER.error("Se generea error en gestion Compania Ascensores ..." + ex);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAscensoresBean " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {
        try {
            companiaFacade.setUser(usuarioVT, perfilVt);
            subEdificioFacade.setUser(usuarioVT, perfilVt);

            if (cmtSubEdificioMglTemp == null) {
                cmtCuentaMatrizMgl = this.cuentaMatrizBean.getCuentaMatriz();
                listCmtSubEdificioMgl = subEdificioFacade.findSubEdificioByCuentaMatriz(cmtCuentaMatrizMgl);
                if (listCmtSubEdificioMgl.size() == 1) {
                    if ( listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj() == null) {
                        listCmtSubEdificioMgl.get(0).setCompaniaAscensorObj(new CmtCompaniaMgl());
                        if (listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj().getTipoCompaniaObj() == null) {
                            listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                            if (listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                                listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ASCENSORES);
                            }
                        }
                    } else if (listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj().getTipoCompaniaObj() == null) {
                        listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                        listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                        if (listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                            listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ASCENSORES);
                        }
                    }

                    FiltroConsultaCompaniasDto filtroConsulta = new FiltroConsultaCompaniasDto();
                    filtroConsulta.setTipoCompania(listCmtSubEdificioMgl.get(0).getCompaniaAscensorObj().getTipoCompaniaObj().getTipoCompaniaId());
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
                if (cmtSubEdificioMgl.getCompaniaAscensorObj() == null) {
                    cmtSubEdificioMgl.setCompaniaAscensorObj(new CmtCompaniaMgl());
                    if (cmtSubEdificioMgl.getCompaniaAscensorObj().getTipoCompaniaObj() == null) {
                        cmtSubEdificioMgl.getCompaniaAscensorObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                        if (cmtSubEdificioMgl.getCompaniaAscensorObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                            cmtSubEdificioMgl.getCompaniaAscensorObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ASCENSORES);
                        }
                    }
                } else if (cmtSubEdificioMgl.getCompaniaAscensorObj().getTipoCompaniaObj() == null) {
                    cmtSubEdificioMgl.getCompaniaAscensorObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                    cmtSubEdificioMgl.getCompaniaAscensorObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                    if (cmtSubEdificioMgl.getCompaniaAscensorObj().getTipoCompaniaObj().getTipoCompaniaId() == null) {
                        cmtSubEdificioMgl.getCompaniaAscensorObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ASCENSORES);
                    }
                }

                FiltroConsultaCompaniasDto filtroConsulta = new FiltroConsultaCompaniasDto();
                filtroConsulta.setTipoCompania(cmtSubEdificioMgl.getCompaniaAscensorObj().getTipoCompaniaObj().getTipoCompaniaId());
                filtroConsulta.setMunicipio(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getMunicipio().getGpoId());
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
            LOGGER.error("Se generea error en gestion Compania Ascensores ..." + ex);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAscensoresBean: init() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Método para validar si el usuario tiene permisos para editar compañías Ascensores en CCMM
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolEditar() {
        try {
             return ValidacionUtil.validarVisualizacion(TABCOMPANIACCMM,
                    ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de edicion de compañias en CCMM. " , e);
        }

        return false;
    }

    /**
     * Método para validar si el usuario tiene permisos para eliminar horarios en compañías ascensores de CCMM
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolEliminar() {
        try {
            return ValidacionUtil.validarVisualizacion(TABCOMPANIACCMM,
                    ValidacionUtil.OPC_BORRADO, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos para eliminar horarios en compañías de CCMM " , e);
        }

        return false;
    }


    public void asignarCompania() {
        try {
            if (cmtSubEdificioMgl.getCompaniaAscensorObj().getCompaniaId().compareTo(BigDecimal.ZERO) != 0) {

                CmtCompaniaMgl cmtCompaniaMgl = new CmtCompaniaMgl();
                cmtCompaniaMgl.setCompaniaId(cmtSubEdificioMgl.getCompaniaAscensorObj().getCompaniaId());
                cmtCompaniaMgl = companiaFacade.findById(cmtCompaniaMgl);
                cmtSubEdificioMgl.setCompaniaAscensorObj(cmtCompaniaMgl);
            }
        } catch (ApplicationException ex) {
            String msn2 = "Error al buscar la informacion del compañia " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error("Se generea error en gestion Compania Ascensores ..." + ex);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAscensoresBean: asignarCompania() " + e.getMessage(), e, LOGGER);
        }
    }

    public String guardarAsociacion() {
        try {
            if (cmtSubEdificioMgl.getCompaniaAscensorObj().getCompaniaId().compareTo(BigDecimal.ZERO) == 0) {
                cmtSubEdificioMgl.setCompaniaAscensorObj(null);
            }
            subEdificioFacade.update(cmtSubEdificioMgl);
            cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMglFacadeLocal.findById(cuentaMatrizBean.getCuentaMatriz()));
            if (cmtSubEdificioMgl.getCompaniaAscensorObj() == null) {
                cmtSubEdificioMgl.setCompaniaAscensorObj(new CmtCompaniaMgl());
                cmtSubEdificioMgl.getCompaniaAscensorObj().setTipoCompaniaObj(new CmtTipoCompaniaMgl());
                cmtSubEdificioMgl.getCompaniaAscensorObj().getTipoCompaniaObj().setTipoCompaniaId(ConstantsCM.TIPO_COMPANIA_ASCENSORES);
            }

            String msn2 = "Asociacion exitosa";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn2, null));
        } catch (ApplicationException ex) {
            String msn2 = "Error al asociar " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error("Se generea error en gestion Compania Ascensores ..." + ex);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAscensoresBean: guardarAsociacion() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String irHorarios() {
        if (cmtSubEdificioMgl.getCompaniaAscensorObj().getCompaniaId().compareTo(BigDecimal.ZERO) == 0) {
            String msn2 = "Debe seleccionar una compañia";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            return null;
        }

        vistas = Presentar.HORARIO;
        consultarHorariosRes();
        return null;
    }

    public String volverDeHorarios() {
        vistas = Presentar.COMPANIA;
        return null;
    }

    private void consultarHorariosRes() {
        List<CmtHorarioRestriccionMgl> alhorarios = null;
        try {
            alhorarios = horarioRestriccionFacadeLocal.findByHorarioCompania(cmtSubEdificioMgl.getCompaniaAscensorObj());
        } catch (ApplicationException ex) {
            LOGGER.error("Error al consultar los Horarios de restriccion ..." + ex);
            String msn2 = "Error al consultar info horarios " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            return;
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GestionCompaniaAscensoresBean: consultarHorariosRes() " + e.getMessage(), e, LOGGER);
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
}
