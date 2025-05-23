/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;


import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificiosVtFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtVisitaTecnicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;
/**
 *
 * @author bocanegravm
 */

@ManagedBean(name = "itemAcoBean")
@ViewScoped
public class ItemAcoBean implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(ItemAcoBean.class);

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    public CmtVisitaTecnicaMgl vt;
    public CmtVisitaTecnicaMgl vtAco;
    private List<CmtSubEdificiosVt> subEdificioVtList;
    private BigDecimal costoTotalAcometidaPlaneado;
    private BigDecimal costoTotalAcometidaReal;
    private BigDecimal costoTotalDisenoPlaneado;
    private BigDecimal costoTotalDisenoReal;
    private BigDecimal ctoTotalInfPlaneado;
    private BigDecimal ctoTotalInfReal;
    private List<CmtArchivosVtMgl> lstArchivosAcoMgls;
    private CmtArchivosVtMgl cmtArchivosAcoMgl;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private String urlArchivoSoporte = "";
    private UploadedFile uploadedFile;
    private boolean habilitaActualizacionCcmm;
    private OtMglBean otMglBean;
    private CmtOrdenTrabajoMgl ordenTrabajoMgl;
    private boolean activacionUCM;
    
    @EJB
    private ResourceEJBRemote resourceEJB;
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;
    @EJB
    private CmtVisitaTecnicaMglFacadeLocal visitaTecnicaMglFacadeLocal;
    @EJB
    private CmtSubEdificiosVtFacadeLocal subEdificiosVtFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
   

    public ItemAcoBean() {
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
            
            vt = new CmtVisitaTecnicaMgl();
            
            this.otMglBean = JSFUtil.getSessionBean(OtMglBean.class);
            ordenTrabajoMgl = otMglBean.getOrdenTrabajo();

            if (session != null && session.getAttribute("acometidaSelected") != null) {
                vtAco = (CmtVisitaTecnicaMgl) session.getAttribute("acometidaSelected");
                session.removeAttribute("acometidaSelected");
            }

            if (session != null && session.getAttribute("activaUCM") != null) {
                activacionUCM = (boolean) session.getAttribute("activaUCM");
                session.removeAttribute("activaUCM");
            }

        } catch (IOException e) {
            String msg = "Error al cargar Bean Items:...." + e.getMessage();
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemAcoBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    

    @PostConstruct
    public void cargarListas() {
        try {
            visitaTecnicaMglFacadeLocal.setUser(usuarioVT, perfilVt);
            subEdificiosVtFacadeLocal.setUser(usuarioVT, perfilVt);
            if(ordenTrabajoMgl != null && ordenTrabajoMgl.getOrdenTrabajoReferencia()!= null){
                 //busco la ot de referencia
                 CmtOrdenTrabajoMgl otReferencia
                        = ordenTrabajoMglFacadeLocal.findOtById(ordenTrabajoMgl.getOrdenTrabajoReferencia());
                if (otReferencia != null && otReferencia.getIdOt() != null) {
                    //consulto la vt activa 
                    vt = visitaTecnicaMglFacadeLocal.findVTActiveByIdOt(otReferencia);
                    if (vtAco != null) {
                        lstArchivosAcoMgls = cmtArchivosVtMglFacadeLocal.findAllByVt(vtAco);
                    } else {
                        vtAco = new CmtVisitaTecnicaMgl();
                    }
                    if (vt.getIdVt() != null) {
                        subEdificioVtList = subEdificiosVtFacadeLocal.findByVt(vt);
                        cargarCostosTotales();
                        habilitaActualizacionCcmm = ordenTrabajoMglFacadeLocal.
                                validaProcesoOt(ordenTrabajoMgl,
                                        Constant.PARAMETRO_VALIDACION_OT_HABILITA_ACTUALIZA_CCMM);

                    }

                } else {
                    String mensaje = "El formulario de acometida requiere una orden de visita tecnica previa";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, mensaje, ""));
                }
            }


        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemAcoBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemAcoBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    
    private boolean validarArchivo() {
        boolean resultValidation = true;
        if (uploadedFile != null && uploadedFile.getFileName() != null) {
            String filename = uploadedFile.getFileName();
            Pattern p = Pattern.compile("[a-zA-Z|\\d|.]*");
            Matcher matcher = p.matcher(filename);
            if (!matcher.matches()) {
                resultValidation = false;
            }
            if (filename.length() > 120) {
                resultValidation = false;
            }
        }
        return resultValidation;
    }

    public String armarUrl(String nombreArchivo) {
        try { 
            if (nombreArchivo != null
                    && !nombreArchivo.isEmpty()) {

                    Parametros param = resourceEJB.queryParametros(ConstantsCM.RUTA_SAVE_IMAGE_CM);
                    String ruta = ""; //Temporal
                    if (param != null) {//Temporal
                        ruta = param.getValor();//Temporal
                    }    //Temporal
                     urlArchivoSoporte = " <a href=\"" +ruta+nombreArchivo
                        + "\"  target=\"blank\">" + nombreArchivo + "</a>";
            }
        } catch (ApplicationException e) {
            String msg = "Error al crear la url del archivo";
            FacesUtil.mostrarMensajeError(msg+ e.getMessage(), e, LOGGER);
            urlArchivoSoporte = "";
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemAcoBean class ..." + e.getMessage(), e, LOGGER);
        }
        return urlArchivoSoporte;
    }


    public void guardarCostosAcometida()  {

        try {
            
            if(vtAco.getIdVt() != null){
              //bocanegravm validacion 
              //y cargue del archivo
            cargarArchivoVtAUCM(Constant.ORIGEN_CARGA_ARCHIVO_ACOMETIDA);
            visitaTecnicaMglFacadeLocal.update(vtAco);
            cargarListas();  
            }else{
            vtAco.setVersionVt(1);
            vtAco.setEdificioManzana("E");
            vtAco.setMultiEdificio("U");
            vtAco.setPisoTorre("0");
            vtAco.setEstadoRegistro(1);
            vtAco.setEstadoVisitaTecnica(BigDecimal.ONE);
            vtAco.setOtObj(ordenTrabajoMgl);
            vtAco.setAporteFinanciero(new Long("0"));
            
            vtAco = visitaTecnicaMglFacadeLocal.createCm(vtAco);
            
            
            if (vtAco.getIdVt() != null) {
                cargarArchivoVtAUCM(Constant.ORIGEN_CARGA_ARCHIVO_ACOMETIDA);
                String mensaje = "Se ha creado el registro de acometida: "
                        + " " + vtAco.getIdVt() + "  asociado a la ot: " + ordenTrabajoMgl.getIdOt() + "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, mensaje, ""));
                cargarListas();
            } else {
                String mensaje = "Ocurrio un error al momento de guardar el registro";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, mensaje, ""));
            }  
                
          }
        } catch (ApplicationException | NumberFormatException e) {
            String msn = "Ocurrio un error actualizando los costos en la cuenta matriz: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } 
    }

    public void cargarCostosTotales() {
        try {
            ctoTotalInfPlaneado = BigDecimal.ZERO;
            costoTotalDisenoPlaneado = BigDecimal.ZERO;
            costoTotalAcometidaPlaneado = BigDecimal.ZERO;
            ctoTotalInfReal=BigDecimal.ZERO;
            costoTotalDisenoReal = BigDecimal.ZERO;
            costoTotalAcometidaReal = BigDecimal.ZERO;
            
            vt = visitaTecnicaMglFacadeLocal.findById(vt);
            if (vt.getCtoMaterialesRed() != null && vt.getCtoManoObra() != null) {
                ctoTotalInfPlaneado = vt.getCtoMaterialesRed().add(vt.getCtoManoObra());
            }
            if (vt.getCostoManoObraDiseno() != null && vt.getCostoMaterialesDiseno() != null) {
                costoTotalDisenoPlaneado = vt.getCostoManoObraDiseno().add(vt.getCostoMaterialesDiseno());
            }
            costoTotalAcometidaPlaneado = ctoTotalInfPlaneado.add(costoTotalDisenoPlaneado);
            vt.setCtoTotalAcometida(costoTotalAcometidaPlaneado);
            visitaTecnicaMglFacadeLocal.update(vt);
            //////////////////////////////////////////////////////////////////////////////////////////////
            
            if (vtAco.getCtoMaterialesRed() != null && vtAco.getCtoManoObra() != null) {
                ctoTotalInfReal = vtAco.getCtoMaterialesRed().add(vtAco.getCtoManoObra());
            }
            if (vtAco.getCostoManoObraDiseno() != null && vtAco.getCostoMaterialesDiseno() != null) {
                costoTotalDisenoReal = vtAco.getCostoManoObraDiseno().add(vtAco.getCostoMaterialesDiseno());
            }

            costoTotalAcometidaReal = ctoTotalInfReal.add(costoTotalDisenoReal);
            vtAco.setCtoTotalAcometida(costoTotalAcometidaReal);
            
            if (vtAco.getIdVt() != null) {
                visitaTecnicaMglFacadeLocal.update(vtAco);
            }
            
            
        } catch (ApplicationException e) {
            String msn2 = "Error al cargar los costos de la Visita Tecnica:....";
           FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemAcoBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    
    public void actualizarCostosCcmm() {
        try {
            List<Boolean> respuestas = visitaTecnicaMglFacadeLocal.
                    actualizarCostosAcometidaCcmm(subEdificioVtList, vtAco, usuarioVT, perfilVt);

            if (respuestas.size() > 0) {
                if (respuestas.size() == 1) {
                    String msn = "Se realiza con exito la actualizacion de los "
                            + "costos de acometida en el edificio general "
                            + "de la ccmm: " + vtAco.getOtObj().getCmObj().getNumeroCuenta() + "";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
                    LOGGER.error(msn);
                } else {
                    String msn = "Se realiza con exito la actualizacion de los "
                            + "costos de acometida en el edificio general y subedificios vt "
                            + "de la ccmm: " + vtAco.getOtObj().getCmObj().getNumeroCuenta() + "";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
                    LOGGER.error(msn);

                }
            } else {
                String msn = "No se realiza ninguna actualizacion de costos"
                        + "de acometida por favor verificar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, null));
                LOGGER.error(msn);
            }
        } catch (ApplicationException e) {
            String msn2 = "Error al actualizar los costos de acometida:....";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemAcoBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    
    public void cargarArchivoVtAUCM(String origen)  {
          
        String usuario = usuarioVT;
        if (uploadedFile != null && uploadedFile.getFileName() != null) {
         try {
           boolean responseVt = visitaTecnicaMglFacadeLocal.
                   cargarArchivoVTxUCM(vtAco, uploadedFile, usuario, perfilVt, origen);

                if (responseVt) {
                    String msg = "Archivo cargado correctamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            msg, ""));
                    cargarListas();    
                } else {
                    String msg = "Ocurrio un error al guardar el archivo";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));

                }
            } catch (ApplicationException | FileNotFoundException | 
                    MalformedURLException | UploadCuentasMatricesFault | SearchCuentasMatricesFault e) {
                String msg = "Error al crear la solictud";
                FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
            }
        } 

    }
    
     public void eliminarArchivo(CmtArchivosVtMgl archivosVtMgl) {
        try {
            boolean elimina = cmtArchivosVtMglFacadeLocal.delete(archivosVtMgl, usuarioVT, perfilVt);
            if(elimina){
                String msg = "Se elimino el registro " + archivosVtMgl.getIdArchivosVt() + " de la base de datos";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        msg, ""));
                LOGGER.error(msg);
             lstArchivosAcoMgls = cmtArchivosVtMglFacadeLocal.findAllByVtxOrigen(vtAco,Constant.ORIGEN_CARGA_ARCHIVO_ACOMETIDA);
            }else{
            String msg = "Ocurrio un problema al tratar de eliminar el registro ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
                LOGGER.error(msg);    
            }
         } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Se generea error en ItemAcoBean class ..." + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Se generea error en ItemAcoBean class ..." + e.getMessage(), e, LOGGER);
         }
                     
    }
     
    public boolean validarBotonGuardarCostos() {

       boolean respuesta;
       
        if (vtAco == null ) {
             respuesta = true;      
        } else {
            if (vtAco.getOtObj() != null && vtAco.getOtObj().getEstadoInternoObj() != null
                    && vtAco.getOtObj().getEstadoInternoObj().getIdentificadorInternoApp() != null
                    && (vtAco.getOtObj().getEstadoInternoObj().getIdentificadorInternoApp().equalsIgnoreCase("@BCER")
                    || vtAco.getOtObj().getEstadoInternoObj().getIdentificadorInternoApp().equalsIgnoreCase("@BCAN"))) {
                respuesta = false;
            } else {
                respuesta = true;
            }
        }
       return  respuesta;
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

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<CmtSubEdificiosVt> getSubEdificioVtList() {
        return subEdificioVtList;
    }

    public void setSubEdificioVtList(List<CmtSubEdificiosVt> subEdificioVtList) {
        this.subEdificioVtList = subEdificioVtList;
    }

    public String getUrlArchivoSoporte() {
        return urlArchivoSoporte;
    }

    public void setUrlArchivoSoporte(String urlArchivoSoporte) {
        this.urlArchivoSoporte = urlArchivoSoporte;
    }

    public CmtVisitaTecnicaMgl getVt() {
        return vt;
    }

    public void setVt(CmtVisitaTecnicaMgl vt) {
        this.vt = vt;
    }
    
    public CmtVisitaTecnicaMgl getVtAco() {
        return vtAco;
    }

    public void setVtAco(CmtVisitaTecnicaMgl vtAco) {
        this.vtAco = vtAco;
    }

    public BigDecimal getCostoTotalAcometidaPlaneado() {
        return costoTotalAcometidaPlaneado;
    }

    public void setCostoTotalAcometidaPlaneado(BigDecimal costoTotalAcometidaPlaneado) {
        this.costoTotalAcometidaPlaneado = costoTotalAcometidaPlaneado;
    }

    public BigDecimal getCostoTotalAcometidaReal() {
        return costoTotalAcometidaReal;
    }

    public void setCostoTotalAcometidaReal(BigDecimal costoTotalAcometidaReal) {
        this.costoTotalAcometidaReal = costoTotalAcometidaReal;
    }

    public BigDecimal getCostoTotalDisenoPlaneado() {
        return costoTotalDisenoPlaneado;
    }

    public void setCostoTotalDisenoPlaneado(BigDecimal costoTotalDisenoPlaneado) {
        this.costoTotalDisenoPlaneado = costoTotalDisenoPlaneado;
    }

    public BigDecimal getCostoTotalDisenoReal() {
        return costoTotalDisenoReal;
    }

    public void setCostoTotalDisenoReal(BigDecimal costoTotalDisenoReal) {
        this.costoTotalDisenoReal = costoTotalDisenoReal;
    }

    public BigDecimal getCtoTotalInfPlaneado() {
        return ctoTotalInfPlaneado;
    }

    public void setCtoTotalInfPlaneado(BigDecimal ctoTotalInfPlaneado) {
        this.ctoTotalInfPlaneado = ctoTotalInfPlaneado;
    }

    public BigDecimal getCtoTotalInfReal() {
        return ctoTotalInfReal;
    }

    public void setCtoTotalInfReal(BigDecimal ctoTotalInfReal) {
        this.ctoTotalInfReal = ctoTotalInfReal;
    }

    public List<CmtArchivosVtMgl> getLstArchivosAcoMgls() {
        return lstArchivosAcoMgls;
    }

    public void setLstArchivosAcoMgls(List<CmtArchivosVtMgl> lstArchivosAcoMgls) {
        this.lstArchivosAcoMgls = lstArchivosAcoMgls;
    }

    public CmtArchivosVtMgl getCmtArchivosAcoMgl() {
        return cmtArchivosAcoMgl;
    }

    public void setCmtArchivosAcoMgl(CmtArchivosVtMgl cmtArchivosAcoMgl) {
        this.cmtArchivosAcoMgl = cmtArchivosAcoMgl;
    }

    public boolean isHabilitaActualizacionCcmm() {
        return habilitaActualizacionCcmm;
    }

    public void setHabilitaActualizacionCcmm(boolean habilitaActualizacionCcmm) {
        this.habilitaActualizacionCcmm = habilitaActualizacionCcmm;
    }
    
    public String armarUrlFin(CmtArchivosVtMgl archivosVtMgl) {

        // Documento original de UCM
        String urlOriginal = archivosVtMgl.getRutaArchivo();

        String requestContextPath = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestContextPath();

        // URL correspondiente al Servlet que descarga imagenes de CCMM desde UCM.
        urlOriginal = requestContextPath + "/view/MGL/document/download/" + urlOriginal;

        return urlArchivoSoporte = " <a href=\"" + urlOriginal
                + "\"  target=\"blank\">" + archivosVtMgl.getNombreArchivo() + "</a>";

    }

    public boolean isActivacionUCM() {
        return activacionUCM;
    }

    public void setActivacionUCM(boolean activacionUCM) {
        this.activacionUCM = activacionUCM;
    }
    
}
