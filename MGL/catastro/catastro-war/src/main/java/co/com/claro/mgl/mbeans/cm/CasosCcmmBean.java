/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;
import co.com.claro.mgl.dtos.FiltroInformacionTecnicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.amx.schema.operation.getmassivefailurerequest.v1.CiVO;
import com.amx.schema.operation.getmassivefailurerequest.v1.GetMassiveFailureResponse;
import com.amx.schema.operation.getmassivefailurerequest.v1.MassiveFailureResponse;
import com.amx.schema.operation.getmassivefailurerequest.v1.NoteVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author bocanegravm
 */
@ManagedBean(name = "casosCcmmBean")
@ViewScoped
public class CasosCcmmBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(CasosCcmmBean.class);
    private String usuarioVT = null;
    private int perfilVT = 0;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private CuentaMatrizBean cuentaMatrizBean;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private FiltroInformacionTecnicaDto filtroInformacionTecnicaDto;
    private int actual;
    private int actualAfec;
    private String numPagina = "1";
    private String numPaginaAfec = "1";
    private boolean pintarPaginado = true;
    private List<Integer> paginaList;
    private List<Integer> paginaListAfec;
    private String pageActual;
    private String pageActualAfec;

    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
    private List<MassiveFailureResponse> lstCasosCcmm;
    private List<MassiveFailureResponse> lstCasosCcmmAux;
    private List<NoteVO> notasCaso;
    private List<NoteVO> notasCasoAux;
    private List<CiVO> affectedCcmm;
    private List<CiVO> affectedCcmmAux;
    private MassiveFailureResponse casoSelected;
    private GetMassiveFailureResponse responseServicio;
    private CmtCuentaMatrizMgl cuentaMatrizMgl;
    private CmtSubEdificioMgl subEdificioMgl;
    private boolean verDetalle = false;
    private boolean verCasos = true;
    public boolean mostrarPopupSub;
    public boolean mostrarPopupAfec;

    public CasosCcmmBean() {
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
           if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cuentaMatrizMgl = this.cuentaMatrizBean.getCuentaMatriz();
            subEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en Casos ccmm class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en Casos ccmm class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            responseServicio = cmtCuentaMatrizMglFacadeLocal.casosCcmm(cuentaMatrizMgl);
            if (responseServicio != null) {
                lstCasosCcmmAux = responseServicio.getMassiveFailureResponse();
            }

            MassiveFailureResponse[] arraysMassiveFailureResponses = null;
            if (lstCasosCcmmAux != null) {
                arraysMassiveFailureResponses = new MassiveFailureResponse[lstCasosCcmmAux.size()];
                int i = 0;
                for (MassiveFailureResponse responseCasos : lstCasosCcmmAux) {
                    arraysMassiveFailureResponses[i] = responseCasos;
                    i++;
                }
                Arrays.sort(arraysMassiveFailureResponses);
            }
            
            if (arraysMassiveFailureResponses != null && arraysMassiveFailureResponses.length > 0) {
                lstCasosCcmm = new ArrayList<MassiveFailureResponse>();
                for (int j = 0; j < arraysMassiveFailureResponses.length; j++) {

                    lstCasosCcmm.add(arraysMassiveFailureResponses[j]);
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error Inicializando en CasosCcmmBean :  init() ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error Inicializando en CasosCcmmBean :  init() ..." + e.getMessage(), e, LOGGER);
        }
    }
 
    public void verDetalle(MassiveFailureResponse casoFailureResponse) {
        try {

            notasCasoAux = casoFailureResponse.getAdvancedNotes();
            affectedCcmmAux = casoFailureResponse.getAffectedCI();
            casoSelected = casoFailureResponse;
            listInfoByPage(1);
            listInfoByPageAfec(1);
            verDetalle = true;
            verCasos = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error Inicializando en CasosCcmmBean :  verDetalle() ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void volverList() {
        
        verDetalle = false;
        verCasos = true;
    }
    
    public void verDetalleCaso() {

        mostrarPopupSub = true;
    }
    
     public void verAfectacionesCcmm() {

        mostrarPopupAfec = true;
    }

    public void cerrarPopup() {

        mostrarPopupSub = false;
    }
    
     public void cerrarPopupAfec() {

        mostrarPopupAfec = false;
    }

    
    public Date convertirFecha(XMLGregorianCalendar fecha){
        Date fechaDate = null;
        try {
            if (fecha != null) {
                fechaDate = fecha.toGregorianCalendar().getTime();
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en Casos ccmm class ..." + e.getMessage(), e, LOGGER);
        }
        return fechaDate;
    }

    public String crearNota() {

        String msn = "Falta la firma para crear nota nueva";

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));

        return "";
    }
    
     public void listInfoByPage(int page) {
         try {
             actual = page;
             getTotalPaginas();

             int firstResult = 0;
             if (page > 1) {
                 firstResult = (filasPag * (page - 1));
             }

             int maxResult;
             if ((firstResult + filasPag) > notasCasoAux.size()) {
                 maxResult = notasCasoAux.size();
             } else {
                 maxResult = (firstResult + filasPag);
             }

             notasCaso = new ArrayList<NoteVO>();
             for (int k = firstResult; k < maxResult; k++) {
                 notasCaso.add(notasCasoAux.get(k));
             }
         } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Se generea error en Casos ccmm class ..." + e.getMessage(), e, LOGGER);
         }
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error en CasosCcmmBean: pageFirst() " + ex.getMessage(), ex);
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
        } catch (Exception ex) {
            LOGGER.error("Error en CasosCcmmBean: pagePrevious() " + ex.getMessage(), ex);
        }
    }
    

    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en CasosCcmmBean: irPagina() "  + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en CasosCcmmBean: irPagina() " + e.getMessage(), e, LOGGER);
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
        } catch (Exception ex) {
            LOGGER.error("Error en CasosCcmmBean: pageNext() " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error en CasosCcmmBean: pageLast() " + ex.getMessage(), ex);
        }

    }
    
     public int getTotalPaginas() {
        try {
            //obtener la lista original para conocer su tamaño total                 
            int pageSol = notasCasoAux.size();
            return (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + ex.getMessage(), ""));
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
            FacesUtil.mostrarMensajeError("Error en CasosCcmmBean: getPageActual() "  + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }
    
    ////////////////////Afectaciones
    
     public void listInfoByPageAfec(int page) {
         try {
             actualAfec = page;
             getTotalPaginasAfec();

             int firstResult = 0;
             if (page > 1) {
                 firstResult = (filasPag * (page - 1));
             }

             int maxResult;
             if ((firstResult + filasPag) > affectedCcmmAux.size()) {
                 maxResult = affectedCcmmAux.size();
             } else {
                 maxResult = (firstResult + filasPag);
             }

             affectedCcmm = new ArrayList<CiVO>();
             for (int k = firstResult; k < maxResult; k++) {
                 affectedCcmm.add(affectedCcmmAux.get(k));
             }
         } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error en CasosCcmmBean: listInfoByPageAfec() " + e.getMessage(), e, LOGGER);
         }
    }

    public void pageFirstAfec() {
        try {
            listInfoByPageAfec(1);
        } catch (Exception ex) {
            LOGGER.error("Error en CasosCcmmBean: pageFirstAfec() "  + ex.getMessage(), ex);
        }
    }

    public void pagePreviousAfec() {
        try {
            int totalPaginas = getTotalPaginasAfec();
            int nuevaPageActual = actualAfec - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageAfec(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error en CasosCcmmBean: pagePreviousAfec() "  + ex.getMessage(), ex);
        }
    }
    

    public void irPaginaAfec() {
        try {
            int totalPaginas = getTotalPaginasAfec();
            int nuevaPageActual = Integer.parseInt(numPaginaAfec);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageAfec(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en CasosCcmmBean: irPaginaAfec() "  + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en CasosCcmmBean: irPaginaAfec() "  + e.getMessage(), e, LOGGER);
        }
    }

    public void pageNextAfec() {
        try {
            int totalPaginas = getTotalPaginasAfec();
            int nuevaPageActual = actualAfec + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageAfec(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error en CasosCcmmBean: pageNextAfec() " + ex.getMessage(), ex);
              FacesUtil.mostrarMensajeError("Error en CasosCcmmBean: pageNextAfec() "  + ex.getMessage(), ex, LOGGER);
        }
    }

    public void pageLastAfec() {
        try {
            int totalPaginas = getTotalPaginasAfec();
            listInfoByPageAfec(totalPaginas);
        } catch (Exception ex) {
             FacesUtil.mostrarMensajeError("Error en CasosCcmmBean: pageLastAfec() "  + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Error en CasosCcmmBean: pageLastAfec() " + ex.getMessage(), ex);
        }

    }
    
     public int getTotalPaginasAfec() {
        try {
            //obtener la lista original para conocer su tamaño total                 
            int pageSol = affectedCcmmAux.size();
            return (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError ="Error en CasosCcmmBean: getTotalPaginasAfec() ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + ex.getMessage(), ""));
        }
        return 1;
    }

    public String getPageActualAfec() {
        try {
            paginaListAfec = new ArrayList<Integer>();
            int totalPaginas = getTotalPaginasAfec();
            for (int i = 1; i <= totalPaginas; i++) {
                paginaListAfec.add(i);
            }
            pageActualAfec = String.valueOf(actualAfec) + " de "
                    + String.valueOf(totalPaginas);

            if (numPaginaAfec == null) {
                numPaginaAfec = "1";
            }
            numPaginaAfec = String.valueOf(actualAfec);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en CasosCcmmBean: getPageActualAfec() "+ e.getMessage(), e, LOGGER);
        }
        return pageActualAfec;
    }

    public int getActualAfec() {
        return actualAfec;
    }

    public void setActualAfec(int actualAfec) {
        this.actualAfec = actualAfec;
    }

    public String getNumPaginaAfec() {
        return numPaginaAfec;
    }

    public void setNumPaginaAfec(String numPaginaAfec) {
        this.numPaginaAfec = numPaginaAfec;
    }

    public List<Integer> getPaginaListAfec() {
        return paginaListAfec;
    }

    public void setPaginaListAfec(List<Integer> paginaListAfec) {
        this.paginaListAfec = paginaListAfec;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public FiltroInformacionTecnicaDto getFiltroInformacionTecnicaDto() {
        return filtroInformacionTecnicaDto;
    }

    public void setFiltroInformacionTecnicaDto(FiltroInformacionTecnicaDto filtroInformacionTecnicaDto) {
        this.filtroInformacionTecnicaDto = filtroInformacionTecnicaDto;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public boolean isPintarPaginado() {
        return pintarPaginado;
    }

    public void setPintarPaginado(boolean pintarPaginado) {
        this.pintarPaginado = pintarPaginado;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    //////////////////////////////////////////////////////////////
    public List<MassiveFailureResponse> getLstCasosCcmm() {
        return lstCasosCcmm;
    }

    public void setLstCasosCcmm(List<MassiveFailureResponse> lstCasosCcmm) {
        this.lstCasosCcmm = lstCasosCcmm;
    }

    public CmtSubEdificioMgl getSubEdificioMgl() {
        return subEdificioMgl;
    }

    public void setSubEdificioMgl(CmtSubEdificioMgl subEdificioMgl) {
        this.subEdificioMgl = subEdificioMgl;
    }

    public List<NoteVO> getNotasCaso() {
        return notasCaso;
    }

    public void setNotasCaso(List<NoteVO> notasCaso) {
        this.notasCaso = notasCaso;
    }

    public List<CiVO> getAffectedCcmm() {
        return affectedCcmm;
    }

    public void setAffectedCcmm(List<CiVO> affectedCcmm) {
        this.affectedCcmm = affectedCcmm;
    }

    public MassiveFailureResponse getCasoSelected() {
        return casoSelected;
    }

    public void setCasoSelected(MassiveFailureResponse casoSelected) {
        this.casoSelected = casoSelected;
    }

    public boolean isVerDetalle() {
        return verDetalle;
    }

    public void setVerDetalle(boolean verDetalle) {
        this.verDetalle = verDetalle;
    }

    public boolean isVerCasos() {
        return verCasos;
    }

    public void setVerCasos(boolean verCasos) {
        this.verCasos = verCasos;
    }

    public boolean isMostrarPopupSub() {
        return mostrarPopupSub;
    }

    public void setMostrarPopupSub(boolean mostrarPopupSub) {
        this.mostrarPopupSub = mostrarPopupSub;
    }

    public boolean isMostrarPopupAfec() {
        return mostrarPopupAfec;
    }

    public void setMostrarPopupAfec(boolean mostrarPopupAfec) {
        this.mostrarPopupAfec = mostrarPopupAfec;
    }

}
