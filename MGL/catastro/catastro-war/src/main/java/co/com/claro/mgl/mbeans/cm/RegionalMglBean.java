/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.CmtFiltroConsultaRegionalDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtRegionalMglFacade;
import co.com.claro.mgl.facade.cm.CmtRegionalMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author JPeña
 */
@ManagedBean(name = "regionalMglBean")
@ViewScoped
public class RegionalMglBean {
    
   @EJB
   private CmtRegionalMglFacadeLocal regionalMglFacade = new CmtRegionalMglFacade();
   @EJB
   private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;    
    
  //Atributos usados 
   private SecurityLogin securityLogin;
   private String message;
   private HtmlDataTable dataTable = new HtmlDataTable();
   private List<CmtRegionalRr> regionalMglList;
   private CmtRegionalRr regionalMgl=null;
   private CmtFiltroConsultaRegionalDto cmtFiltroConsultaRegionalDto = 
            new CmtFiltroConsultaRegionalDto();
    
   private FacesContext facesContext = FacesContext.getCurrentInstance();
   private HttpSession session = (HttpSession) facesContext.
            getExternalContext().getSession(false);
   
   private static final Logger LOGGER = LogManager.getLogger(RegionalMglBean.class);
   
   private HttpServletResponse response = (HttpServletResponse) 
           facesContext.getExternalContext().getResponse();
   private String usuarioVT = null;
   private String usuarioID = null; 
   
   private String idSqlSelected;
   private int actual;                            //para saber la pagina actual
   private static final int numeroRegistrosConsulta = 15;
   private List<Integer> paginaList;
   private String pageActual;
   private String numPagina;
   
   //Opciones agregadas para Rol
   private final String REGBTNNEW = "REGBTNNEW";
   private final String VALIDARIRROLREG = "VALIDARIRROLREG";
    
   //atributos usados en la vista individual para llenar los campos 
   // del regional a modificar
   
   private boolean guardado;  
    
    //Metodos que se usan en este bean
    
  //constructor
   public RegionalMglBean() {
        try {
            try {
                securityLogin = new SecurityLogin(facesContext);
                if (!securityLogin.isLogin()) {
                    if (!response.isCommitted()) {
                        response.sendRedirect(securityLogin.redireccionarLogin());
                    }
                    return;
                }
                usuarioVT = securityLogin.getLoginUser();
                if (usuarioVT == null) {
                    session.getAttribute("usuarioM");
                    usuarioVT = (String) session.getAttribute("usuarioM");
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioM", usuarioVT);
                }
                usuarioID = securityLogin.getIdUser();
                if (usuarioID == null) {
                    session.getAttribute("usuarioIDM");
                    usuarioID = (String) session.getAttribute("usuarioIDM");
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioIDM", usuarioID);
                }
            } catch (IOException ex) {
                LOGGER.error("Se generea error en RegionalMglBean class ...", ex);
            }
        } catch (Exception e) {
            String msgError = "Error en  " + ClassUtils.getCurrentMethodName(this.getClass()) + " " + e.getMessage();
            LOGGER.error(msgError, e);
        }
    }

   //metodos que se encargan de las vistas
    /**
     * Jonathan Peña
     * Primer metodo que se ejecuta al abrir cualquera de las vistas 
     * de regionales, carga los datos necesarios para la tabla, o para el individual
     */
  @PostConstruct
    public void fillSqlList() {
        
        try{
        CmtRegionalRr idRegionalMgl = (CmtRegionalRr) session.getAttribute("idRegionalMgl");
            session.removeAttribute("idRegionalMgl");
            if (idRegionalMgl != null) {
                regionalMgl = idRegionalMgl;
            } else {
                regionalMgl = new CmtRegionalRr();
                
                regionalMgl.setRegionalRrId(BigDecimal.ZERO);
            }
            regionalMglList = new ArrayList<CmtRegionalRr>();
            listInfoByPage(1);
        
        } catch(Exception ex){
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
    
    
    }
  
    /**
     * Jonathan Peña
     * Metodo encargado de pasar cambiar de vista, desde la vista de la lista 
     * de regionales hacia la  vista individual de cada Regional
     * @return 
     */
     public String goActualizar() {
        try {
            regionalMgl = (CmtRegionalRr) dataTable.getRowData();
            session.setAttribute("idRegionalMgl", regionalMgl);
            return "regionalMglView";
        } catch (Exception e) {
            String msgError = "Error en  " + ClassUtils.getCurrentMethodName(this.getClass()) + " " + e.getMessage();
            LOGGER.error(msgError, e);
            if (session == null) {
                return "regionalMglListView";
            }
        }
        return null;
    }
    
     
     //metodos crud en mgl
     
     /**
     * Jonathan Peña
     * Metodo encargado de crear el regional es el que llama a la fachada
     * de regionales para seguir con la insercion 
     */
     public void crearRegionalMgl(){
        
         try {
           regionalMgl.setRegionalRrId(null);
           regionalMgl.setEstadoRegistro(1);
            if (configurarRegional()) {
                regionalMgl.setUsuarioCreacion(usuarioVT);
                regionalMgl.setFechaCreacion(new Date());
                regionalMgl = regionalMglFacade.create(regionalMgl);
                if (regionalMgl!=null) {
                                    setGuardado(false);
                message = "Proceso exitoso: se ha creado la regional  <b>" +
                        regionalMgl.getNombreRegional()+ "</b> satisfactoriamente";
                }else{
                    message = "Proceso fallido: no se ha creado la regional  " 
                         + regionalMgl.getNombreRegional()+ " satisfactoriamente";                    
                }             
            }
            session.setAttribute("message", message);
            FacesContext.getCurrentInstance().addMessage(
                 null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
         } catch (ApplicationException e) {
             message = "Proceso falló: ";
             FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
             session.setAttribute("message", message);
         } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error en RegionalMglBean:crearRegionalMgl(). " + e.getMessage(), e, LOGGER);
         }
     
     }
     
     /**
     * Jonathan Peña
     * Metodo encargado de actualizar el regional es el que llama a la fachada
     * de regionales para seguir con la actualizacion 
     */
     public void actualizarRegionalMgl(){
         
          try {
          
            if (configurarRegional()) {
                regionalMgl.setUsuarioEdicion(usuarioVT);
                regionalMgl.setFechaEdicion(new Date());
                regionalMgl = regionalMglFacade.update(regionalMgl);
                if (regionalMgl!=null) {
                                    setGuardado(false);
                message = "Proceso exitoso: se ha actualizado la regional  <b>" +
                        regionalMgl.getNombreRegional()+ "</b>  "
                        + "satisfactoriamente";
                }else{
                    message = "Proceso fallido: no se ha actualizado la regional" 
                         + regionalMgl.getNombreRegional()+ " satisfactoriamente";                    
                }             
            }
            session.setAttribute("message", message);
            FacesContext.getCurrentInstance().addMessage(
               null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
         } catch (ApplicationException e) {
             message = "Proceso falló: ";
             FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
             session.setAttribute("message", message);
         } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error en RegionalMglBean:actualizarRegionalMgl() " + e.getMessage(), e, LOGGER);
         }
         
     
     }
     
     /**
     * Jonathan Peña
     * Metodo encargado de eliminar el regional es el que llama a la fachada
     * de regionales para seguir con la eliminacion 
     */
     public void eliminarRegionalMgl(){
             try {
            regionalMglFacade.delete(regionalMgl);
            
            message = "Proceso exitoso: Se ha eliminado la regional:  <b>" + 
                    regionalMgl.getNombreRegional()+ "</b> satisfatoriamente";
            
            FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
            
            session.setAttribute("message", message);
            session.setAttribute("message", message);
         } catch (ApplicationException e) {
             message = "Proceso falló: ";
             FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
             session.setAttribute("message", message);
         } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error en RegionalMglBean:eliminarRegionalMgl(). " + e.getMessage(), e, LOGGER);
         }

     }
     
     /**
     * Jonathan Peña
     * Metodo para verificar que el regional tenga los datos correspondientes
     * @return boolean define si los datos estan o no.
     */
      private boolean configurarRegional() {
          
        if (regionalMgl.getCodigoRr()== null || regionalMgl.getCodigoRr().trim().isEmpty()) {
            message = "Campo Nombre regional es requerido";
            return false;
        }

        if (regionalMgl.getCodigoRr()== null || regionalMgl.getCodigoRr().trim().isEmpty()) {
            message = "Campo Código nodo es requerido";
            return false;
        }

        return true;
    }
     
     
     
     
     
    //metodos que se encargan de la vista de la tabla.
    public String listInfoByPage(int page) {
        try {
            PaginacionDto<CmtRegionalRr> paginacionDto =
                    regionalMglFacade.findAllPaginado(page, numeroRegistrosConsulta,
                                                  cmtFiltroConsultaRegionalDto);
            regionalMglList = paginacionDto.getListResultado();
            actual = page;
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RegionalMglBean:listInfoByPage() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void findByFiltro() {
        pageFirst();
    }

    public void pageFirst() {
        listInfoByPage(1);
    }
    
    public void pagePrevious() {
        int totalPaginas = getTotalPaginas();
        int nuevaPageActual = actual - 1;
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        if (nuevaPageActual <= 0) {
            nuevaPageActual = 1;
        }
        listInfoByPage(nuevaPageActual);
    }
    
    public int getTotalPaginas() {
        try {
            PaginacionDto<CmtRegionalRr> paginacionDto =
                    regionalMglFacade.findAllPaginado(0, numeroRegistrosConsulta,
                                                     cmtFiltroConsultaRegionalDto);
            Long count = paginacionDto.getNumPaginas();
            int totalPaginas = (int) ((count % numeroRegistrosConsulta != 0)
                    ? (count / numeroRegistrosConsulta) + 1
                    : count / numeroRegistrosConsulta);
            return totalPaginas;
        } catch (ApplicationException e) {
            String msn = "Error al RegionalMglBean. ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RegionalMglBean:getTotalPaginas() " + e.getMessage(), e, LOGGER);
        }
        return 1;
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
            FacesUtil.mostrarMensajeError("Error en RegionalMglBean:getPageActual(). " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error en RegionalMglBean:irPagina(). " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error en RegionalMglBean:pageNext() " + e.getMessage(), e, LOGGER);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RegionalMglBean:pageNext() " + e.getMessage(), e, LOGGER);
        }
    }
 
    // Validar Opciones por Rol    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(REGBTNNEW);
    }
    
    public boolean validarOpcionIr() {
        return validarEdicionRol(VALIDARIRROLREG);
    }
    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ParametrizarOperadorBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    
    public String nuevoRegionalMgl() {
        regionalMgl = null;
        regionalMgl = new CmtRegionalRr();
        regionalMgl.setRegionalRrId(BigDecimal.ZERO);
        setGuardado(true);
        return "regionalMglView";
    }
    
    //getters y setters de los atributos

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public List<CmtRegionalRr> getRegionalMglList() {
        return regionalMglList;
    }

    public void setRegionalMglList(List<CmtRegionalRr> regionalMglList) {
        this.regionalMglList = regionalMglList;
    }

    public CmtRegionalRr getRegionalMgl() {
        return regionalMgl;
    }

    public void setRegionalMgl(CmtRegionalRr regionalMgl) {
        this.regionalMgl = regionalMgl;
    }

    public String getIdSqlSelected() {
        return idSqlSelected;
    }

    public void setIdSqlSelected(String idSqlSelected) {
        this.idSqlSelected = idSqlSelected;
    }

    public CmtFiltroConsultaRegionalDto getCmtFiltroConsultaRegionalDto() {
        return cmtFiltroConsultaRegionalDto;
    }

    public void setCmtFiltroConsultaRegionalDto(CmtFiltroConsultaRegionalDto cmtFiltroConsultaRegionalDto) {
        this.cmtFiltroConsultaRegionalDto = cmtFiltroConsultaRegionalDto;
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

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }
    
    
    
}
