/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtHorarioRestriccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
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
 * @author Admin
 */
@ManagedBean(name = "horariosRestriccionBean")
@ViewScoped
public class HorariosRestriccionBean implements Serializable {

    private String usuarioVT = null;
    private int perfilVt = 0;
    private BigDecimal cuentaMatrizId;
    private ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion;
    @EJB
    private CmtHorarioRestriccionMglFacadeLocal horarioRestriccionFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private CmtSubEdificioMgl subEdificioMgl;
    private    VisitaTecnicaBean vtMglBean;
    private static final Logger LOGGER = LogManager.getLogger(HorariosRestriccionBean.class);
    private SecurityLogin securityLogin;

    public HorariosRestriccionBean() {
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
            vtMglBean = JSFUtil.getSessionBean(VisitaTecnicaBean.class);
        } catch (IOException e) {
             FacesUtil.mostrarMensajeError(HorariosRestriccionBean.class.getName() + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en HorariosRestriccionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            horarioRestriccionFacadeLocal.setUser(usuarioVT, perfilVt);
            FacesContext contx = FacesContext.getCurrentInstance();
            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cuentaMatrizId = cuentaMatrizBean.cuentaMatriz.getCuentaMatrizId();

            subEdificioMgl = cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            consultarHorarioRestriccion();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en HorariosRestriccionBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en HorariosRestriccionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Método para validar si el usuario tiene permisos para adicionar horarios en la CCMM
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrear() {
        try {
            return ValidacionUtil.validarVisualizacion("TABHORARIOCCMM",
                    ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de creación de notas. ", e);
        }

        return false;
    }

    /**
     * Método para validar si el usuario tiene permisos para eliminar horarios en la CCMM
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolEliminar() {
        try {
            return ValidacionUtil.validarVisualizacion("TABHORARIOCCMM",
                    ValidacionUtil.OPC_BORRADO, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de eliminación de horarios. ", e);
        }

        return false;
    }


    public void consultarHorarioRestriccion()  {
        try {
            List<CmtHorarioRestriccionMgl> alhorarios = horarioRestriccionFacadeLocal.findBySubEdificioId(subEdificioMgl.getSubEdificioId());
            horarioRestriccion = new ArrayList<CmtHorarioRestriccionMgl>();
            if (!alhorarios.isEmpty() && alhorarios.size() > 0) {
                for (CmtHorarioRestriccionMgl h : alhorarios) {
                    horarioRestriccion.add(h);
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en HorariosRestriccionBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en HorariosRestriccionBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void guardarHorarioRestriccion()  {
        try {

            BigDecimal idCuentaMatriz = cuentaMatrizId;
            CmtHorarioRestriccionMgl cmtHorarioRestriccionMgl;

            if (session.getAttribute("ComponenteHorario") != null) {
                horarioRestriccion = (ArrayList<CmtHorarioRestriccionMgl>) session.getAttribute("ComponenteHorario");
                session.removeAttribute("ComponenteHorario");
                if (horarioRestriccion != null && !horarioRestriccion.isEmpty() && horarioRestriccion.size() == 2) {
                    if (horarioRestriccion.get(0).getDiaInicio().compareTo(horarioRestriccion.get(1).getDiaInicio()) == 0
                            && horarioRestriccion.get(0).getDiaFin().compareTo(horarioRestriccion.get(1).getDiaFin()) == 0
                            && horarioRestriccion.get(0).getHoraInicio().equalsIgnoreCase(horarioRestriccion.get(1).getHoraInicio())
                            && horarioRestriccion.get(0).getHoraFin().equalsIgnoreCase(horarioRestriccion.get(1).getHoraFin())) {
                        horarioRestriccion.remove(1);
                    }

                }

            }

            if (cuentaMatrizId != null) {

                if (!horarioRestriccionFacadeLocal.deleteByCuentaMatrizId(idCuentaMatriz)) {
                    String msn = "Ocurrio un error al actualizar el horario ";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
                try {
                    if (horarioRestriccion != null && !horarioRestriccion.isEmpty()) {
                        CmtCuentaMatrizMgl cmtCuentaMatrizMgl = new CmtCuentaMatrizMgl();
                        cmtCuentaMatrizMgl.setCuentaMatrizId(cuentaMatrizId);
                        for (CmtHorarioRestriccionMgl hr : horarioRestriccion) {
                            hr.setCuentaMatrizObj(cmtCuentaMatrizMgl);
                            hr.setHorRestriccionId(null);
                            hr.setSubEdificioObj(subEdificioMgl);
                            horarioRestriccionFacadeLocal.create(hr);
                        }
                    } //else {
                    // }
                    consultarHorarioRestriccion();
                    String msn = "Horario actualizado con éxito";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                } catch (ApplicationException ex) {

                    String msn = "Error actualizando horario";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }

            } else {
                String msn = "No puede agregar horario si la cuenta matriz no existe.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en HorariosRestriccionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public ArrayList<CmtHorarioRestriccionMgl> getHorarioRestriccion() {
        return horarioRestriccion;
    }

    public void setHorarioRestriccion(ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion) {
        this.horarioRestriccion = horarioRestriccion;
    }

    public BigDecimal getCuentaMatrizId() {
        return cuentaMatrizId;
    }

    public void setCuentaMatrizId(BigDecimal cuentaMatrizId) {
        this.cuentaMatrizId = cuentaMatrizId;
    }

    public CmtSubEdificioMgl getSubEdificioMgl() {
        return subEdificioMgl;
    }

    public void setSubEdificioMgl(CmtSubEdificioMgl subEdificioMgl) {
        this.subEdificioMgl = subEdificioMgl;
    }

    public VisitaTecnicaBean getVtMglBean() {
        return vtMglBean;
    }

    public void setVtMglBean(VisitaTecnicaBean vtMglBean) {
        this.vtMglBean = vtMglBean;
    }
    
    
    

}
