package co.com.claro.mgl.mbeans.cm;

import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtItemMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtItemVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificiosVtFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtVigenciaCostoItemMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtVisitaTecnicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtItemVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVigenciaCostoItemMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.services.util.ResourceEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.file.UploadedFile;

@ManagedBean(name = "itemVtBean")
@ViewScoped
public class ItemVtBean implements Serializable {

    @EJB
    private CmtItemMglFacadeLocal itemMglFacadeLocal;
    @EJB
    private CmtVigenciaCostoItemMglFacadeLocal vigenciaCostoItemMglFacadeLocal;
    @EJB
    private CmtItemVtMglFacadeLocal itemVtMglFacadeLocal;
    @EJB
    private CmtVisitaTecnicaMglFacadeLocal visitaTecnicaMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacade;
    @EJB
    private CmtSubEdificiosVtFacadeLocal subEdificiosVtFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    private static final Logger LOGGER = LogManager.getLogger(ItemVtBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private int filasPag = ConstantsCM.PAGINACION_SEIS_FILAS;
    private String usuarioVT = null;
    private int perfilVt = 0;
    public CmtVisitaTecnicaMgl vt;
    public CmtItemMgl item;
    public List<CmtItemMgl> itemList;
    public CmtItemVtMgl itemVt;
    public List<CmtItemVtMgl> itemVtList;
    public CmtVigenciaCostoItemMgl vigenciaCostoItem;
    public String selectedTab;
    private BigDecimal itemId;
    private String tipoItem;
    private BigDecimal costoTotalItemsVt;
    private BigDecimal unidadesTotalItemsVt;
    private UploadedFile uploadedFile;
    private BigDecimal ctoTotalInf;
    private BigDecimal costoTotalDiseno;
    private BigDecimal costoTotalAcometida;
    public List<CmtItemVtMgl> listaItemManoObra;
    public List<CmtItemVtMgl> listaItemMaterialManoObra;
    public List<CmtItemVtMgl> listaItemManoObraDiseno;
    public List<CmtItemVtMgl> listaItemMateriaObraDiseno;
    private List<CmtSubEdificiosVt> subEdificioVtList;
    private String urlArchivoSoporte = "";
    @EJB
    private ResourceEJBRemote resourceEJB;
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;
    private CmtArchivosVtMgl cmtArchivosVtMgl;
    private List<CmtArchivosVtMgl> lstArchivosVtMgls;
    private OtMglBean otMglBean;
    private CmtOrdenTrabajoMgl ot;
    private boolean activacionUCM;

    public ItemVtBean() {
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
            VisitaTecnicaBean vtMglBean = JSFUtil.getSessionBean(VisitaTecnicaBean.class);
            this.selectedTab = vtMglBean.getSelectedTab();
            this.vt = vtMglBean.getVt();
            if (vt == null) {
                String msn2 = "Error no hay una vt activa";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            }
        } catch (IOException e) {
            String msg = "Error al cargar Bean Items:...." + e.getMessage();
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {
            this.otMglBean = JSFUtil.getSessionBean(OtMglBean.class);
            ot = otMglBean.getOrdenTrabajo();
            visitaTecnicaMglFacadeLocal.setUser(usuarioVT, perfilVt);
            itemVtMglFacadeLocal.setUser(usuarioVT, perfilVt);
            if (this.selectedTab != null && this.selectedTab.equals("MANO_OBRA")) {
                tipoItem = Constant.TIPO_ITEM_MANO_OBRA;
                itemList = itemMglFacadeLocal.findByTipoItem(tipoItem);
                itemVtList = itemVtMglFacadeLocal.findItemByVtPaginado(actual, actual, vt, tipoItem);
            } else if (this.selectedTab != null && this.selectedTab.equals("MATERIALES")) {
                tipoItem = Constant.TIPO_ITEM_MATERIAL;
                itemList = itemMglFacadeLocal.findByTipoItem(tipoItem);
                itemVtList = itemVtMglFacadeLocal.findItemByVtPaginado(actual, actual, vt, tipoItem);
            } else if (this.selectedTab != null && this.selectedTab.equals("MTDISENO")) {
                tipoItem = Constant.TIPO_ITEM_MATERIAL_DISENO;
                itemList = itemMglFacadeLocal.findByTipoItem(tipoItem);
                itemVtList = itemVtMglFacadeLocal.findItemByVtPaginado(actual, actual, vt, tipoItem);
            } else if (this.selectedTab != null && this.selectedTab.equals("MODISENO")) {
                tipoItem = Constant.TIPO_ITEM_MANO_OBRA_DISENO;
                itemList = itemMglFacadeLocal.findByTipoItem(tipoItem);
                itemVtList = itemVtMglFacadeLocal.findItemByVtPaginado(actual, actual, vt, tipoItem);
            } else if (this.selectedTab != null && this.selectedTab.equals("COSTOS")) {
                listaItemManoObra = itemVtMglFacadeLocal.findItemVtByVt(vt, Constant.TIPO_ITEM_MANO_OBRA);
                listaItemMaterialManoObra = itemVtMglFacadeLocal.findItemVtByVt(vt, Constant.TIPO_ITEM_MATERIAL);
                listaItemManoObraDiseno = itemVtMglFacadeLocal.findItemVtByVt(vt, Constant.TIPO_ITEM_MANO_OBRA_DISENO);
                listaItemMateriaObraDiseno = itemVtMglFacadeLocal.findItemVtByVt(vt, Constant.TIPO_ITEM_MATERIAL_DISENO);
      
                cargarCostosTotales();
            }
            if (this.selectedTab != null && this.selectedTab.equalsIgnoreCase("COSTOS")) {
                lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findAllByVtxOrigen(vt, Constant.ORIGEN_CARGA_ARCHIVO_COSTOS);
            } else {
                lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findAllByVtxOrigen(vt, Constant.ORIGEN_CARGA_ARCHIVO_FINANCIERA);
            }
            
            if (session != null && session.getAttribute("activaUCM") != null) {
                activacionUCM = (boolean) session.getAttribute("activaUCM");
                session.removeAttribute("activaUCM");
            }

            validateItem();
            cargarCostosUnidadesTotales();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    private void cargarCostosUnidadesTotales() {
        try {
            costoTotalItemsVt = itemVtMglFacadeLocal.getTotalCosto(vt, tipoItem);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
            costoTotalItemsVt = BigDecimal.ZERO;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
        try {
            unidadesTotalItemsVt = itemVtMglFacadeLocal.getTotalUnidades(vt, tipoItem);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
            unidadesTotalItemsVt = BigDecimal.ZERO;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void addItemVt() {
        try {
            this.item = new CmtItemMgl();
            this.item.setIdItem(itemId);
            this.item = itemMglFacadeLocal.findId(item);

            this.vigenciaCostoItem = new CmtVigenciaCostoItemMgl();
            vigenciaCostoItem = vigenciaCostoItemMglFacadeLocal.findByItemVigencia(item);

            itemVt = new CmtItemVtMgl();
            itemVt.setVtObj(vt);
            itemVt.setCantidad(BigDecimal.ZERO);
            itemVt.setItemObj(this.item);
            itemVt.setVigenciaCostoItemObj(vigenciaCostoItem);

            if (itemVtMglFacadeLocal.findItemVtByItem(vt, item) != null) {
                String msn = "Este item ya ha sido agregado anteriormente a esta visita tecnica.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            } else {
                //validamos que todos los item tengan una catidad mayor a cero
                if (itemVtList != null && !itemVtList.isEmpty()) {
                    for (CmtItemVtMgl itVt : itemVtList) {
                        if (itVt.getCantidad().compareTo(BigDecimal.ONE) == -1) {
                            throw new ApplicationException("Existen Item con catidad Cero");
                        }
                    }
                }
                //Guardamos el item Seleccionado
                itemVt = itemVtMglFacadeLocal.createCm(itemVt);
                //Guardamos los cambios del listado de Items
                editAllItem();
                itemVtList = itemVtMglFacadeLocal.findItemByVtPaginado(actual, actual, vt, tipoItem);
            }

            cargarCostosUnidadesTotales();
        } catch (ApplicationException e) {
           FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);

         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void editItemVt(CmtItemVtMgl cmtItemVtMgl) {
        try {
            if ("0".equals(cmtItemVtMgl.getCantidad().toString())) {
                String msn = "Debe especificar una cantidad diferente de 0 para el Item";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            } else {
                itemVtMglFacadeLocal.update(cmtItemVtMgl);
                validateItem();
                String msn = "Item Actualizado con Exito";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }

            cargarCostosUnidadesTotales();
            if (this.selectedTab.equals("MANO_OBRA")) {
                vt.setCtoManoObra(costoTotalItemsVt);
            } else if (this.selectedTab.equals("MATERIALES")) {
                vt.setCtoMaterialesRed(costoTotalItemsVt);
            }
            vt = visitaTecnicaMglFacadeLocal.update(vt);
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Actualizando el registro : " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String actualizarCostoDiseno() {
        try {
            cargarArchivoVtAUCM(Constant.ORIGEN_CARGA_ARCHIVO_COSTOS);
            visitaTecnicaMglFacadeLocal.update(vt);
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Actualizando la VT: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
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
    
    public String armarUrl(CmtArchivosVtMgl archivosVtMgl) {

        // Documento original de UCM
        String urlOriginal = archivosVtMgl.getRutaArchivo();

        String requestContextPath = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestContextPath();

        // URL correspondiente al Servlet que descarga imagenes de CCMM desde UCM.
        urlOriginal = requestContextPath + "/view/MGL/document/download/" + urlOriginal;

        return urlArchivoSoporte = " <a href=\"" + urlOriginal
                + "\"  target=\"blank\">" + archivosVtMgl.getNombreArchivo() + "</a>";

    }
        
    
     public void eliminarArchivo(CmtArchivosVtMgl archivosVtMgl)  {
        try {
            boolean elimina = cmtArchivosVtMglFacadeLocal.delete(archivosVtMgl, usuarioVT, perfilVt);
            if(elimina){
                String msg = "Se elimino el registro " + archivosVtMgl.getIdArchivosVt() + " de la base de datos";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        msg, ""));
                LOGGER.error(msg);
                if (this.selectedTab.equalsIgnoreCase("COSTOS")) {
                    lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findAllByVtxOrigen(vt, Constant.ORIGEN_CARGA_ARCHIVO_COSTOS);
                } else {
                    lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findAllByVtxOrigen(vt, Constant.ORIGEN_CARGA_ARCHIVO_FINANCIERA);
                }
              
            }else{
            String msg = "Ocurrio un problema al tratar de eliminar el registro ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
                LOGGER.error(msg);    
            }
              VisitaTecnicaBean vtMglBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
              vtMglBean.cargarListas();
         } catch (ApplicationException ex) {
             FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + ex.getMessage(), ex, LOGGER);
         } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
         }
                     
    }


    public String editAllItem() {
        try {
            if (itemVtList != null && !itemVtList.isEmpty()) {
                //validamos que todos los item tengan una catidad mayor a cero
                for (CmtItemVtMgl itVt : itemVtList) {
                    if (itVt.getCantidad().compareTo(BigDecimal.ONE) == -1) {
                        throw new ApplicationException("Existen Item con catidad Cero");
                    }
                }
                //Actualizamos todos los items de la VT 
                for (CmtItemVtMgl itVt : itemVtList) {
                    itemVtMglFacadeLocal.update(itVt);
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "No Existen Materiales para actualizar", ""));
            }

            cargarCostosUnidadesTotales();
            if (this.selectedTab.equals("MANO_OBRA")) {
                vt.setCtoManoObra(costoTotalItemsVt);
            } else if (this.selectedTab.equals("MATERIALES")) {
                vt.setCtoMaterialesRed(costoTotalItemsVt);
            }
            vt = visitaTecnicaMglFacadeLocal.update(vt);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Actualizando el registro : " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void deleteItemVt(CmtItemVtMgl cmtItemVtMgl) {
        try {
            if (itemVtMglFacadeLocal.delete(cmtItemVtMgl)) {
                itemVtList = itemVtMglFacadeLocal.findItemByVtPaginado(actual, actual, vt, tipoItem);
                validateItem();
                String msn = "Registro Eliminado con Exito";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Eliminando el registro : " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }

        cargarCostosUnidadesTotales();
    }

    private String listInfoByPage(int page) {
        try {
            itemVtList = itemVtMglFacadeLocal.findItemByVtPaginado(page, filasPag, vt, tipoItem);
            actual = page;
            getPageActual();
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void validateItem()  {
        try {
            if (this.selectedTab != null && this.selectedTab.equals("MANO_OBRA")) {
                tipoItem = "MO";
            } else if (this.selectedTab != null && this.selectedTab.equals("MATERIALES")) {
                tipoItem = "MT";
            }
            itemVtList = itemVtMglFacadeLocal.findItemVtByVt(vt, tipoItem);
            if (itemVtList.size() > 0) {
                boolean hayErrorItem = false;
                for (CmtItemVtMgl item_ : itemVtList) {
                    if (item_.getCostoTotal() == null) {
                        hayErrorItem = true;
                    }
                }
                VisitaTecnicaBean vtMglBean = JSFUtil.getSessionBean(VisitaTecnicaBean.class);
                vtMglBean.setHayErrorItem(hayErrorItem);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error(ex);
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
            LOGGER.error(ex);
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
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
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
            LOGGER.error(ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }

    public int getTotalPaginas() {
        try {
            int totalPaginas;

            int pageSol = itemVtMglFacadeLocal.getCountByVt(vt, tipoItem);
            totalPaginas = (int) ((pageSol % filasPag != 0) ? (pageSol / filasPag) + 1 : pageSol / filasPag);

            return totalPaginas;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public void exportExcel() {
        try {
            if (itemVtList != null
                    && itemVtList.size() > 0) {
                //Libro en blanco           
                XSSFWorkbook workbook = new XSSFWorkbook();
                String sheetName = "Lista de Materiales y Mano de Obra";
                Object[] cabeceraDataGral = new Object[]{"CODIGO", "DESCRIPCION",
                    "UNIDAD", "COSTO", "CANTIDAD", "COSTO TOTAL"};

                Map<String, Object[]> mapDataEstado = new TreeMap<>();
                int fila = 1;
                //se ingresa la cabecera de los datos
                mapDataEstado.put(String.valueOf(fila++), cabeceraDataGral);
                for (CmtItemVtMgl e
                        : itemVtList) {
                    mapDataEstado.put(String.valueOf(fila++),
                            new Object[]{e.getItemObj().getCodigoItem(),
                        e.getItemObj().getDescItem(),
                        e.getItemObj().getIdUnidad().getDescUnidad(),
                        e.getVigenciaCostoItemObj().getCosto(),
                        e.getCantidad(),
                        e.getCostoTotal()});
                }
                fillSheetbook(workbook, sheetName, mapDataEstado);
                String fileName = "MaterialesManoObra";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response =
                        (HttpServletResponse) fc.getExternalContext().getResponse();
                response.reset();
                response.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

                response.setHeader("Content-Disposition", "attachment; filename=" + fileName
                        + formato.format(new Date()) + ".xlsx");
                OutputStream output = response.getOutputStream();

                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            } else {
                String msn = "No Existen Registros a exportar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (IOException e) {
            String msn = "Ocurrio un error en la creacion del archivo excel";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    private void fillSheetbook(XSSFWorkbook workbook, String sheetName,
            Map<String, Object[]> data)  {
        try {
            //Crea un libro en blanco
            XSSFSheet sheet = workbook.createSheet(sheetName);

            sheet.setDisplayGridlines(true);
            sheet.setPrintGridlines(true);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);

            CellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setLocked(false);

            //Itera sobre los datos que seran escritos en el libro
            int rownum = 0;
            for (int i = 1; i <= data.size(); i++) {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = data.get(Integer.toString(i));
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof BigDecimal) {
                        cell.setCellValue(((BigDecimal) obj).doubleValue());
                    }
                }
            }

        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    private void cargarCostosUnidadesTotales(String tipoItemVt) {
        try {
            costoTotalItemsVt = itemVtMglFacadeLocal.getTotalCosto(vt, tipoItemVt);
        } catch (ApplicationException e) {
            LOGGER.error(e);
            costoTotalItemsVt = BigDecimal.ZERO;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
        try {
            unidadesTotalItemsVt = itemVtMglFacadeLocal.getTotalUnidades(vt, tipoItemVt);
        } catch (ApplicationException e) {
            LOGGER.error(e);
            unidadesTotalItemsVt = BigDecimal.ZERO;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void actualizarCostosItemsVt(List<CmtItemVtMgl> listaItems, String tipoCosto) {
        try {
            if (listaItems != null && !listaItems.isEmpty()) {
                for (CmtItemVtMgl itVt : listaItems) {
                    if (itVt.getCantidad().compareTo(BigDecimal.ONE) == -1) {
                        throw new ApplicationException("Existen Item con catidad Cero");
                    }
                }
                for (CmtItemVtMgl itVt : listaItems) {
                    itemVtMglFacadeLocal.update(itVt);
                }

                if (tipoCosto.equals(Constant.TIPO_ITEM_MANO_OBRA)) {
                    cargarCostosUnidadesTotales(Constant.TIPO_ITEM_MANO_OBRA);
                    vt.setCtoManoObra(costoTotalItemsVt);
                } else if (tipoCosto.equals(Constant.TIPO_ITEM_MATERIAL)) {
                    cargarCostosUnidadesTotales(Constant.TIPO_ITEM_MATERIAL);
                    vt.setCtoMaterialesRed(costoTotalItemsVt);
                } else if (tipoCosto.equals(Constant.TIPO_ITEM_MANO_OBRA_DISENO)) {
                    cargarCostosUnidadesTotales(Constant.TIPO_ITEM_MANO_OBRA_DISENO);
                    vt.setCostoManoObraDiseno(costoTotalItemsVt);
                } else if (tipoCosto.equals(Constant.TIPO_ITEM_MATERIAL_DISENO)) {
                    cargarCostosUnidadesTotales(Constant.TIPO_ITEM_MATERIAL_DISENO);
                    vt.setCostoMaterialesDiseno(costoTotalItemsVt);
                }
                vt = visitaTecnicaMglFacadeLocal.update(vt);
            } else {
                vt = visitaTecnicaMglFacadeLocal.update(vt);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void actualizarCostosVT()  {

        try {

            if (otMglBean.tieneAgendasPendientes(ot)) {
                //bocanegravm validacion 
                //y cargue del archivo
                cargarArchivoVtAUCM(Constant.ORIGEN_CARGA_ARCHIVO_COSTOS);

                //valbuenayf inicio ajuste campo nodo 
                // validar nodos
                if (subEdificioVtList != null && !subEdificioVtList.isEmpty()) {
                    if (!validarListaNodos(subEdificioVtList)) {
                        return;
                    } else {
                        // actualizar nodos
                        if (!actulizarNodosSubEdificio(subEdificioVtList)) {
                            String msn = "Error al actualizar el nodo del sub edificio";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                            return;
                        }
                    }
                }
                //valbuenayf fin ajuste campo nodo 

                actualizarCostosItemsVt(listaItemManoObra, Constant.TIPO_ITEM_MANO_OBRA);
                actualizarCostosItemsVt(listaItemMaterialManoObra, Constant.TIPO_ITEM_MATERIAL);
                actualizarCostosItemsVt(listaItemManoObraDiseno, Constant.TIPO_ITEM_MANO_OBRA_DISENO);
                actualizarCostosItemsVt(listaItemMateriaObraDiseno, Constant.TIPO_ITEM_MATERIAL_DISENO);
                cargarCostosTotales();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
            } else {
                String msg = "La orden de trabajo No: "+ot.getIdOt()+" tiene agendas pendientes por cerrar";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
            }
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error actualizando los costos en la cuenta matriz: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void cargarCostosTotales() {
        try {
            ctoTotalInf = BigDecimal.ZERO;
            costoTotalDiseno = BigDecimal.ZERO;
            costoTotalAcometida = BigDecimal.ZERO;
            vt = visitaTecnicaMglFacadeLocal.findById(vt);
            if (vt.getCtoMaterialesRed() != null && vt.getCtoManoObra() != null) {
                ctoTotalInf = vt.getCtoMaterialesRed().add(vt.getCtoManoObra());
            }
            if (vt.getCostoManoObraDiseno() != null && vt.getCostoMaterialesDiseno() != null) {
                costoTotalDiseno = vt.getCostoManoObraDiseno().add(vt.getCostoMaterialesDiseno());
            }
            costoTotalAcometida = ctoTotalInf.add(costoTotalDiseno);
            vt.setCtoTotalAcometida(costoTotalAcometida);
            visitaTecnicaMglFacadeLocal.update(vt);
        } catch (ApplicationException e) {
            String msn2 = "Error al cargar los costos de la Visita Tecnica:...." + e.getMessage();
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * valbuenayf metodo para validar los nodos de los sub edificios
     *
     * @param subEdificio
     * @return
     * @throws ApplicationException
     */
    private boolean validarListaNodos(List<CmtSubEdificiosVt> subEdificio) {
        Integer linea = 1;
        boolean respuesta = true;
        try {
            subEdificioVtList = preSetListas(subEdificioVtList);
            for (CmtSubEdificiosVt subEdi : subEdificioVtList) {
                if (subEdi.getEstadoRegistro() == 1) {
                    if (subEdi.getTipoNivel1() != null
                            && subEdi.getTipoNivel1().getBasicaId() != null
                            && subEdi.getTipoNivel1().getNombreBasica() == null) {
                        CmtBasicaMgl level
                                = basicaMglFacadeLocal.findById(subEdi.getTipoNivel1());
                        subEdi.setTipoNivel1(level);
                    }
                    if (subEdi.getTipoNivel2() != null
                            && subEdi.getTipoNivel2().getBasicaId() != null
                            && subEdi.getTipoNivel2().getNombreBasica() == null) {
                        CmtBasicaMgl level
                                = basicaMglFacadeLocal.findById(subEdi.getTipoNivel2());
                        subEdi.setTipoNivel2(level);
                    }
                    if (subEdi.getTipoNivel3() != null
                            && subEdi.getTipoNivel3().getBasicaId() != null
                            && subEdi.getTipoNivel3().getNombreBasica() == null) {
                        CmtBasicaMgl level
                                = basicaMglFacadeLocal.findById(subEdi.getTipoNivel3());
                        subEdi.setTipoNivel3(level);
                    }
                    if (subEdi.getTipoNivel4() != null
                            && subEdi.getTipoNivel4().getBasicaId() != null
                            && subEdi.getTipoNivel4().getNombreBasica() == null) {
                        CmtBasicaMgl level
                                = basicaMglFacadeLocal.findById(subEdi.getTipoNivel4());
                        subEdi.setTipoNivel4(level);
                    }
                    if (subEdi.getTipoNivel5() != null
                            && subEdi.getTipoNivel5().getBasicaId() != null
                            && subEdi.getTipoNivel5().getNombreBasica() == null) {
                        CmtBasicaMgl level
                                = basicaMglFacadeLocal.findById(subEdi.getTipoNivel5());
                        subEdi.setTipoNivel5(level);
                    }
                }
            }

            for (CmtSubEdificiosVt subEdi : subEdificio) {
                boolean validar = false;
                if (subEdi.getEstadoRegistro() == 1) {
                    //valbuenayf inicio ajuste campo nodo
                    if (!subEdi.getCodigoNodo().equals("")) {
                        if (subEdi.getNodo() != null && subEdi.getNodo().getNodId() != null) {
                            if (!subEdi.getCodigoNodo().equals(subEdi.getNodo().getNodCodigo())) {
                                //si el codigo del nodo cambia se realiza la validacion 
                                validar = true;
                            }
                            validar = true;
                        } else {
                            //si no hay nodo asignado se realiza la validacion 
                            validar = true;
                        }

                        if (validar) {
                            BigDecimal idCentroPoblado = vt.getOtObj().getCmObj().getCentroPoblado().getGpoId();
                            BigDecimal idTecno = vt.getOtObj().getBasicaIdTecnologia().getBasicaId();
                            String auxCod = subEdi.getCodigoNodo();
                            NodoMgl nodo = nodoMglFacade.findByCodigoNodo(auxCod, idCentroPoblado, idTecno);

                            if (nodo != null && nodo.getNodId() != null) {
                                subEdi.setNodo(nodo);
                            } else {
                                respuesta = false;
                                subEdi.setNodo(null);
                                String MensajeComplementa = "";
                                if (auxCod != null && !auxCod.isEmpty()) {
                                    NodoMgl auxNodo = nodoMglFacade.findByCodigo(auxCod);
                                    if (auxNodo == null) {
                                        MensajeComplementa = "Nodo ".concat(auxCod).concat(" No existe");
                                    } else {
                                        MensajeComplementa = "El Nodo ".concat(auxCod).
                                                concat(" es de otra ciudad, O no es de la tecnologia de la OT");
                                    }

                                }
                                String msn2 = Constant.MSG_NODO_ERROR + "(" + linea + "). "
                                        .concat(MensajeComplementa);
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            }

                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Ud no ingreso el nodo", ""));
                        respuesta = false;
                    }
                    //valbuenayf fin ajuste campo nodo

                }
                linea += 1;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
        return respuesta;
    }

    /**
     * valbuenayf metodo para actulizar los nodos de los sub edificios
     *
     * @return
     */
    private boolean actulizarNodosSubEdificio(List<CmtSubEdificiosVt> subEdificio) {
        boolean respuesta = true;
        try {
            for (CmtSubEdificiosVt subEdi : subEdificio) {
               
                    subEdi = subEdificiosVtFacadeLocal.update(subEdi,usuarioVT,perfilVt);
                
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
        }
        return respuesta;
    }

    public List<CmtSubEdificiosVt> preSetListas(List<CmtSubEdificiosVt> subEdificioVtList) {
        try {
            for (CmtSubEdificiosVt subEdi : subEdificioVtList) {
                subEdi = preProcessEdificio(subEdi);
            }
            return subEdificioVtList;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en preSetListas: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en preSetListas: " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    private CmtSubEdificiosVt preProcessEdificio(CmtSubEdificiosVt subEdi) throws ApplicationException {
        try {
            if (subEdi.getTipoNivel1() != null
                    && subEdi.getTipoNivel1().getBasicaId() != null
                    && subEdi.getTipoNivel1().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                subEdi.setTipoNivel1(basicaMglFacadeLocal.findById(subEdi.getTipoNivel1().getBasicaId()));
            } else {
                subEdi.setTipoNivel1(null);
            }
            if (subEdi.getTipoNivel2() != null
                    && subEdi.getTipoNivel2().getBasicaId() != null
                    && subEdi.getTipoNivel2().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                subEdi.setTipoNivel2(basicaMglFacadeLocal.findById(subEdi.getTipoNivel2().getBasicaId()));
            } else {
                subEdi.setTipoNivel2(null);
            }
            if (subEdi.getTipoNivel3() != null
                    && subEdi.getTipoNivel3().getBasicaId() != null
                    && subEdi.getTipoNivel3().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                subEdi.setTipoNivel3(basicaMglFacadeLocal.findById(subEdi.getTipoNivel3().getBasicaId()));
            } else {
                subEdi.setTipoNivel3(null);
            }
            if (subEdi.getTipoNivel4() != null
                    && subEdi.getTipoNivel4().getBasicaId() != null
                    && subEdi.getTipoNivel4().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                subEdi.setTipoNivel4(basicaMglFacadeLocal.findById(subEdi.getTipoNivel4().getBasicaId()));
            } else {
                subEdi.setTipoNivel4(null);
            }
            if (subEdi.getTipoNivel5() != null
                    && subEdi.getTipoNivel5().getBasicaId() != null
                    && subEdi.getTipoNivel5().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                subEdi.setTipoNivel5(basicaMglFacadeLocal.findById(subEdi.getTipoNivel5().getBasicaId()));
            } else {
                subEdi.setTipoNivel5(null);
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw e;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
        return subEdi;
    }
    
   public void cargarArchivoVtAUCM(String origen)  {
          try{
        String usuario = usuarioVT;
        if (uploadedFile != null) {
               try {
                   boolean responseVt = visitaTecnicaMglFacadeLocal.
                           cargarArchivoVTxUCM(vt, uploadedFile, usuario, perfilVt, origen);

                   if (responseVt) {
                       String msg = "Archivo cargado correctamente";
                       FacesContext.getCurrentInstance().addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO,
                                       msg, ""));
                       lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findAllByVtxOrigen(vt, Constant.ORIGEN_CARGA_ARCHIVO_COSTOS);
                   } else {
                       String msg = "Ocurrio un error al guardar el archivo";
                       FacesContext.getCurrentInstance().addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                       msg, ""));

                   }
               } catch (ApplicationException | FileNotFoundException | MalformedURLException e) {
                   String msg = "Error al crear la solicitud: " + e.getMessage();
                   FacesContext.getCurrentInstance().addMessage(null,
                           new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                   msg, ""));
                   LOGGER.error(msg, e);
               }

           }
       } catch (SearchCuentasMatricesFault | UploadCuentasMatricesFault e) {
           FacesUtil.mostrarMensajeError("Se generea error en ItemVtBean class ..." + e.getMessage(), e, LOGGER);
       }

    }  

    public CmtItemMgl getItem() {
        return item;
    }

    public void setItem(CmtItemMgl item) {
        this.item = item;
    }

    public List<CmtItemMgl> getItemList() {
        return itemList;
    }

    public void setItemList(List<CmtItemMgl> itemList) {
        this.itemList = itemList;
    }

    public CmtItemVtMgl getItemVt() {
        return itemVt;
    }

    public void setItemVt(CmtItemVtMgl itemVt) {
        this.itemVt = itemVt;
    }

    public List<CmtItemVtMgl> getItemVtList() {
        return itemVtList;
    }

    public void setItemVtList(List<CmtItemVtMgl> itemVtList) {
        this.itemVtList = itemVtList;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public CmtVisitaTecnicaMgl getVt() {
        return vt;
    }

    public void setVt(CmtVisitaTecnicaMgl vt) {
        this.vt = vt;
    }

    public CmtVigenciaCostoItemMgl getVigenciaCostoItem() {
        return vigenciaCostoItem;
    }

    public void setVigenciaCostoItem(CmtVigenciaCostoItemMgl vigenciaCostoItem) {
        this.vigenciaCostoItem = vigenciaCostoItem;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public BigDecimal getItemId() {
        return itemId;
    }

    public void setItemId(BigDecimal itemId) {
        this.itemId = itemId;
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

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
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

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public BigDecimal getCostoTotalItemsVt() {
        return costoTotalItemsVt;
    }

    public void setCostoTotalItemsVt(BigDecimal costoTotalItemsVt) {
        this.costoTotalItemsVt = costoTotalItemsVt;
    }

    public BigDecimal getUnidadesTotalItemsVt() {
        return unidadesTotalItemsVt;
    }

    public void setUnidadesTotalItemsVt(BigDecimal unidadesTotalItemsVt) {
        this.unidadesTotalItemsVt = unidadesTotalItemsVt;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public BigDecimal getCtoTotalInf() {
        return ctoTotalInf;
    }

    public void setCtoTotalInf(BigDecimal ctoTotalInf) {
        this.ctoTotalInf = ctoTotalInf;
    }

    public BigDecimal getCostoTotalDiseno() {
        return costoTotalDiseno;
    }

    public void setCostoTotalDiseno(BigDecimal costoTotalDiseno) {
        this.costoTotalDiseno = costoTotalDiseno;
    }

    public BigDecimal getCostoTotalAcometida() {
        return costoTotalAcometida;
    }

    public void setCostoTotalAcometida(BigDecimal costoTotalAcometida) {
        this.costoTotalAcometida = costoTotalAcometida;
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

    public CmtArchivosVtMgl getCmtArchivosVtMgl() {
        return cmtArchivosVtMgl;
    }

    public void setCmtArchivosVtMgl(CmtArchivosVtMgl cmtArchivosVtMgl) {
        this.cmtArchivosVtMgl = cmtArchivosVtMgl;
    }

    public List<CmtArchivosVtMgl> getLstArchivosVtMgls() {
        return lstArchivosVtMgls;
    }

    public void setLstArchivosVtMgls(List<CmtArchivosVtMgl> lstArchivosVtMgls) {
        this.lstArchivosVtMgls = lstArchivosVtMgls;
    }

    public boolean isActivacionUCM() {
        return activacionUCM;
    }

    public void setActivacionUCM(boolean activacionUCM) {
        this.activacionUCM = activacionUCM;
    }
    
}
