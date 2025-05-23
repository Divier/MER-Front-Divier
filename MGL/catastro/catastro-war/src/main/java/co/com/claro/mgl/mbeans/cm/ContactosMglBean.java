/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtContactosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtContactosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
 * @author rodriguezluim
 */
@ManagedBean(name = "contactosMglBean")
@ViewScoped
public class ContactosMglBean {

    @EJB
    private CmtContactosMglFacadeLocal cmtContactosMglFacadeLocal;
    //Parametros requeridos para la captura de datos de loggeo
    private static final Logger LOGGER = LogManager.getLogger(ContactosMglBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private int perfilVT = 0;
    //Parametros para el funcionamiento del bean
    private OtMglBean ordenTrabajoBean;
    private CmtOrdenTrabajoMgl ordenTrabajoMgl;
    private List<CmtContactosMgl> listaContactos;
    private CmtContactosMgl nuevoContacto;
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private static final int numeroRegistrosConsulta = 10;
    private int actual;
    private final String FORMULARIO = "OTTABCONTACTOS";
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    private SecurityLogin securityLogin;
    private boolean mostrarPanelCrearCont;
    private boolean mostrarListaCont;
    private String estiloObligatorio = "<font color='red'>*</font>";

    public ContactosMglBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    @PostConstruct
    public void init() {
        try {
            cmtContactosMglFacadeLocal.setUser(usuarioVT, perfilVT);

            ordenTrabajoBean = JSFUtil.getSessionBean(OtMglBean.class);
            ordenTrabajoMgl = ordenTrabajoBean.getOrdenTrabajo();
            listaContactos = cmtContactosMglFacadeLocal.findByOrdenTrabajo(ordenTrabajoMgl);
            nuevoContacto = new CmtContactosMgl();
            mostrarListaCont = true;
            mostrarPanelCrearCont = false;
            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Método para validar si el usuario tiene permisos para crear contacto en la orden de trabajo de la CCMM
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrear() {
        try {
            return ValidacionUtil.validarVisualizacion("TABCONTACTOOTCCMM",
                    ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de creación de contacto en OT CCMM. " , e);
        }

        return false;
    }

    public void guardarContacto() {
        try {
            if (nuevoContacto.getComentario().length() > 1999) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "El campo 'Notas Descriptivas' no puede superar 2000 caracteres", ""));
                return;
            }
            if (nuevoContacto.getTelefonoPrincipal() == null
                    || !(nuevoContacto.getTelefonoPrincipal().toString().trim().length() == 7
                    || nuevoContacto.getTelefonoPrincipal().toString().trim().length() == 10)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "El campo telefono principal debe ser de 10 o 7 digitos", ""));
                return;
            }
            if (nuevoContacto.getTelefonoSecundario() == null
                    || !(nuevoContacto.getTelefonoSecundario().toString().trim().length() == 7
                    || nuevoContacto.getTelefonoSecundario().toString().trim().length() == 10)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "El campo telefono secundario debe ser de 10 o 7 digitos", ""));
                return;
            }
            nuevoContacto.setCcmmId(ordenTrabajoMgl.getCmObj());
            nuevoContacto.setOtId(ordenTrabajoMgl);
            nuevoContacto.setEstadoRegistro(1);
            cmtContactosMglFacadeLocal.create(nuevoContacto);

            listaContactos = cmtContactosMglFacadeLocal.findByOrdenTrabajo(ordenTrabajoMgl);
            nuevoContacto = new CmtContactosMgl();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Se creo el nuevo contacto satisfactoriamente", ""));

            mostrarListaCont = true;
            mostrarPanelCrearCont = false;

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void eliminarContacto(CmtContactosMgl contacto) {
        try {
            if (cmtContactosMglFacadeLocal.eliminarContacto(contacto) != null) {
                String msn = "Se elimino satisfactoriamente el registro";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));

                init();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Se presentó un error al procesar la petición. Por favor intente más tarde", ""));

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String listInfoByPage(int page) {
        try {
            if (ordenTrabajoMgl.getIdOt() != null) {
                PaginacionDto<CmtContactosMgl> paginacionDto = cmtContactosMglFacadeLocal.
                        findAllPaginado(page, numeroRegistrosConsulta, ordenTrabajoMgl);

                listaContactos = paginacionDto.getListResultado();
                actual = page;
            } else {
                FacesUtil.mostrarMensajeWarn("Se requiere crear una Ot previamente");
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void findByFiltro() {
        try {
            pageFirst();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void pageFirst() {
        try {

            listInfoByPage(1);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void pagePrevious() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String getPageActual() {
        try {
            paginaList = new ArrayList<Integer>();
            int totalPaginas = getTotalPaginas();
            for (int i = 1; i <= totalPaginas; i++) {
                paginaList.add(i);

            }
            pageActual = String.valueOf(actual) + " de "
                    + String.valueOf(totalPaginas);

            if (numPagina == null) {
                numPagina = "1";
            }
            numPagina = String.valueOf(actual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void pageLast() {
        try {

            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public int getTotalPaginas() {
        try {
            PaginacionDto<CmtContactosMgl> paginacionDto = cmtContactosMglFacadeLocal.
                    findAllPaginado(0, numeroRegistrosConsulta, ordenTrabajoMgl);

            Long count = paginacionDto.getNumPaginas();
            int totalPaginas = (int) ((count % numeroRegistrosConsulta != 0)
                    ? (count / numeroRegistrosConsulta) + 1
                    : count / numeroRegistrosConsulta);
            return totalPaginas;
        } catch (ApplicationException e) {
            String msn = "Se genera error al cargar lista de Nodos:";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public void nuevoContactoCrear() {
        try {
            if (ordenTrabajoMgl.getIdOt() != null) {
                if (ordenTrabajoBean.tieneAgendasPendientes(ordenTrabajoMgl)) {
                    mostrarPanelCrearCont = true;
                    mostrarListaCont = false;
                } else {
                    String msg = "La orden de trabajo tiene agendas pendientes por cerrar";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                }
            } else {
                String msg = "Se requiere crear una Ot previamente";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msg, ""));
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void volver() {

        mostrarPanelCrearCont = false;
        mostrarListaCont = true;
    }

    public boolean validarCreacion() {
        return validarPermisos(ValidacionUtil.OPC_CREACION);
    }

    public boolean validarBorrado() {
        return validarPermisos(ValidacionUtil.OPC_BORRADO);
    }

    private boolean validarPermisos(String accion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, accion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ContactosMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public List<CmtContactosMgl> getListaContactos() {
        return listaContactos;
    }

    public void setListaContactos(List<CmtContactosMgl> listaContactos) {
        this.listaContactos = listaContactos;
    }

    public CmtContactosMgl getNuevoContacto() {
        return nuevoContacto;
    }

    public void setNuevoContacto(CmtContactosMgl nuevoContacto) {
        this.nuevoContacto = nuevoContacto;
    }

    public CmtOrdenTrabajoMgl getOrdenTrabajoMgl() {
        return ordenTrabajoMgl;
    }

    public OtMglBean getOrdenTrabajoBean() {
        return ordenTrabajoBean;
    }

    public void setOrdenTrabajoBean(OtMglBean ordenTrabajoBean) {
        this.ordenTrabajoBean = ordenTrabajoBean;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public boolean isMostrarPanelCrearCont() {
        return mostrarPanelCrearCont;
    }

    public void setMostrarPanelCrearCont(boolean mostrarPanelCrearCont) {
        this.mostrarPanelCrearCont = mostrarPanelCrearCont;
    }

    public boolean isMostrarListaCont() {
        return mostrarListaCont;
    }

    public void setMostrarListaCont(boolean mostrarListaCont) {
        this.mostrarListaCont = mostrarListaCont;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

}
