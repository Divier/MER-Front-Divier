/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;


import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.mgl.dtos.EstratoxTorreDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBlackListMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.HhppEstrato;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "infoGeneralBean")
@ViewScoped
public class InfoGeneralBean implements Serializable {

    private String usuarioVT = null;
    private int perfilVT = 0;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private final HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private static final Logger LOGGER = LogManager.getLogger(InfoGeneralBean.class);
    private CuentaMatrizBean cuentaMatrizBean;
    private CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
    private CmtSubEdificioMgl selectedCmtSubEdificioMgl;
    private List<HhppEstrato> resumenHhppByEstratoList;
    @EJB
    private HhppMglFacadeLocal hhppMglFacade;
    private HashMap<String, Object> params;
    private List<EstratoxTorreDto> listaEstratoxTorreDto;
    private String listDireccionesSeparadas="";
    private String listBlackListSeparadas="";
    private List<BigDecimal> listBlackListIds;
    private List<BigDecimal> listDireccionesIds;
    @EJB
    private CmtBlackListMglFacadeLocal blackListMglFacadeLocal;
    private List<CmtBlackListMgl> listBlackList;
    @EJB
    private CmtDireccionMglFacadeLocal cmtDireccionMglFacadeLocal;
    private List<CmtDireccionMgl> listDireccionesAlternas;
    @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal  cmtCuentaMatrizMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private ResourceEJBRemote resourceEJB;

    
    private UploadedFile uploadedFile;
    private String urlArchivoImagen = "";
    private BufferedImage image;
    private  int numPisosTotal = 0;
    private boolean activacionUCM;
    

    public InfoGeneralBean() {
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
            selectedCmtSubEdificioMgl = new CmtSubEdificioMgl();

            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);

        } catch (IOException ex) {
            LOGGER.error("Se generea error en InfoGeneralBean class ...", ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }

    }

    @PostConstruct
    public void cargarListas() {
        try {
            cmtSubEdificioMglFacadeLocal.setUser(usuarioVT, perfilVT);
            selectedCmtSubEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            getEstratosCmSub();
            getBlackListxSubedificio();
            getDireccionesAlternas();
            getNumPisosTotalCM();
            if (session != null && session.getAttribute("activaUCM") != null) {
                activacionUCM = (boolean) session.getAttribute("activaUCM");
                session.removeAttribute("activaUCM");
            }

            if (cuentaMatrizBean.getCuentaMatriz() != null) {
                if (cuentaMatrizBean.getCuentaMatriz().getImgCuenta() != null
                        && !cuentaMatrizBean.getCuentaMatriz().getImgCuenta().isEmpty()) {
                    urlArchivoImagen=cuentaMatrizBean.getCuentaMatriz().getImgCuenta();
                }

            }
           
        } catch (ApplicationException e) {
            LOGGER.error("Error al cargar listas. EX000 " + e.getMessage(), e);
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
    }

    public String getEstratosCmSub() {
        try {
            if (selectedCmtSubEdificioMgl != null && selectedCmtSubEdificioMgl.getListHhpp() != null) {
                if (selectedCmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                        && selectedCmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                    listaEstratoxTorreDto = hhppMglFacade.findByHhppEstratoSubCM(selectedCmtSubEdificioMgl,
                            Constant.FIND_HHPP_BY.CUENTA_MATRIZ);

                } else {
                    listaEstratoxTorreDto = hhppMglFacade.findByHhppEstratoSubCM(selectedCmtSubEdificioMgl,
                            Constant.FIND_HHPP_BY.SUB_EDIFICIO);
                }
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error al traer estratos de cuenta matriz en subedificio. EX000 " + e.getMessage(), e);
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
        return "";
    }
    
        public int getNumPisosTotalCM() {
         
        try {
            int max=0;
            
            for (CmtSubEdificioMgl  torre :this.cuentaMatrizBean.cuentaMatriz.getListCmtSubEdificioMglActivos()){
                if(torre.getPisos() > max){
                    max =torre.getPisos();
                }
            }
          numPisosTotal=max;

        } catch (Exception e) {
            LOGGER.error("Error al traer el numero de pisos total en cuenta matriz. EX000 " + e.getMessage(), e);
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
        return numPisosTotal;
    }

    public void buscar() {
        try {
            params = new HashMap<>();
            params.put("all", "1");

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));

        }
    }

    public String getBlackListxSubedificio() {
      
        try {
            listBlackList = blackListMglFacadeLocal.findBySubEdificio(selectedCmtSubEdificioMgl);
            for (CmtBlackListMgl black : listBlackList) {
                if (black.getBlackListObj() != null) {
                    listBlackListSeparadas =  listBlackListSeparadas + black.getBlackListObj().getNombreBasica()+"\n";
                }

            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return listBlackListSeparadas;
    }

    public String getDireccionesAlternas() {
       
        try {
            if (selectedCmtSubEdificioMgl != null) {
                 listDireccionesAlternas = cmtDireccionMglFacadeLocal.findDireccionesByCuentaMatriz(selectedCmtSubEdificioMgl.getCuentaMatrizObj());
                for (CmtDireccionMgl dir : listDireccionesAlternas) {
                    if (dir.getDireccionObj() != null && dir.getTdiId() == 1) {
                        listDireccionesSeparadas = listDireccionesSeparadas + dir.getDireccionObj().getDirFormatoIgac() + "\n";
                    }

                }
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return listDireccionesSeparadas;
    }

    public void validarSubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl) {
        if (cmtSubEdificioMgl.getEstadoSubEdificioObj().
                getIdentificadorInternoApp().equalsIgnoreCase
                (Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
            try {
                getEstratosxTorreCM(cmtCuentaMatrizMgl.getSubEdificiosMglNoGeneral());
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
            }
        } else {

            getEstratosxTorre(cmtSubEdificioMgl);
        }

    }

    public List<HhppEstrato> getEstratosxTorreCM(List<CmtSubEdificioMgl> subedificio) {
        ArrayList<Integer> listSubedificios = new ArrayList<>();
        List<HhppMgl> listahhpp = null;
        Hashtable hashLista = new Hashtable();
        for (CmtSubEdificioMgl torre : subedificio) {

            listahhpp = torre.getListHhpp();
            for (HhppMgl hhpp : listahhpp) {
                if (hhpp.getSubDireccionObj() != null) {
                    if (hhpp.getSubDireccionObj().getSdiEstrato() != null || hhpp.getSubDireccionObj().getSdiNivelSocioecono() != null) {
                        if (hhpp.getSubDireccionObj().getSdiEstrato() != null) {
                            LOGGER.error("Torre: " + torre.getNombreSubedificio() + "id" + hhpp.getHhpId() + "estrato: " + hhpp.getDireccionObj().getDirEstrato().intValueExact());
                            listSubedificios.add((Integer) hhpp.getSubDireccionObj().getSdiEstrato().intValueExact());
                        } else {
                            LOGGER.error("Torre: " + torre.getNombreSubedificio() + "id" + hhpp.getHhpId() + "estrato: " + hhpp.getDireccionObj().getDirNivelSocioecono().intValueExact());
                            listSubedificios.add((Integer) hhpp.getSubDireccionObj().getSdiNivelSocioecono().intValueExact());
                        }
                    }
                }
            }
        }
        for (Object item : listSubedificios) {
            if (hashLista.containsKey(item)) {
                hashLista.put(item, (Integer) hashLista.get(item) + 1);
            } else {
                hashLista.put(item, 1);
            }
        }

        Enumeration e = hashLista.keys();
        Object clave;
        Object valor;
        HhppEstrato hhppxestratos;
        resumenHhppByEstratoList = new ArrayList<>();
        while (e.hasMoreElements()) {
            clave = e.nextElement();
            valor = hashLista.get(clave);
            hhppxestratos = new HhppEstrato();
            hhppxestratos.setNumeroHhpp((Integer) valor);
            hhppxestratos.setEstratoHhpp((Integer) clave);
            resumenHhppByEstratoList.add(hhppxestratos);

        }

        return resumenHhppByEstratoList;
    }

    public List<HhppEstrato> getEstratosxTorre(CmtSubEdificioMgl subedificio) {
        ArrayList<Integer> listSubedificios = new ArrayList<>();
        List<HhppMgl> listahhpp = null;
        Hashtable hashLista = new Hashtable();
        for (CmtSubEdificioMgl torre : cmtCuentaMatrizMgl.getListCmtSubEdificioMglActivos()) {
            if (torre.getSubEdificioId().intValueExact() == subedificio.getSubEdificioId().intValueExact()) {
                listahhpp = torre.getListHhpp();
            }
        }

        for (HhppMgl hhpp : listahhpp) {
            if (hhpp.getSubDireccionObj() != null) {
                if (hhpp.getSubDireccionObj().getSdiEstrato() != null || hhpp.getSubDireccionObj().getSdiNivelSocioecono() != null) {
                    if (hhpp.getSubDireccionObj().getSdiEstrato() != null) {
                        LOGGER.error("Torre: " + "estrato: " + hhpp.getSubDireccionObj().getSdiEstrato().intValueExact());
                        listSubedificios.add((Integer) hhpp.getDireccionObj().getDirEstrato().intValueExact());
                    } else {
                        LOGGER.error("Torre: " + "estrato: " + hhpp.getSubDireccionObj().getSdiNivelSocioecono().intValueExact());
                        listSubedificios.add((Integer) hhpp.getSubDireccionObj().getSdiNivelSocioecono().intValueExact());
                    }
                }
            }

        }

        for (Object item : listSubedificios) {
            if (hashLista.containsKey(item)) {
                hashLista.put(item, (Integer) hashLista.get(item) + 1);
            } else {
                hashLista.put(item, 1);
            }
        }
        Enumeration e = hashLista.keys();
        Object clave;
        Object valor;
        HhppEstrato hhppxestratos;
        resumenHhppByEstratoList = new ArrayList<>();
        while (e.hasMoreElements()) {
            clave = e.nextElement();
            valor = hashLista.get(clave);
            hhppxestratos = new HhppEstrato();
            hhppxestratos.setNumeroHhpp((Integer) valor);
            hhppxestratos.setEstratoHhpp((Integer) clave);
            resumenHhppByEstratoList.add(hhppxestratos);

        }
        return resumenHhppByEstratoList;
    }
    /**
     * Realiza la carga de un archivo a UCM (gestor documental).
     * 
     * @throws IOException 
     */
    public void cargarArchivoAUCM() throws IOException {

        String usuario = usuarioVT;
        if (uploadedFile != null && uploadedFile.getFileName() != null) {
            CmtCuentaMatrizMgl cm = cuentaMatrizBean.getCuentaMatriz();

            try {
                String extension = FilenameUtils.getExtension(uploadedFile.getFileName());

                if (!validarExtArchivo(extension)) {
                    String msg = "El archivo no tiene formato de imagen";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));

                    return;
                }
                InputStream is = uploadedFile.getInputStream();
                BufferedImage img = ImageIO.read(is);

                /*
                 *  300 x 200 sería el tamaño recomendable
                 */
                int width = img.getWidth();
                int height = img.getHeight();
                if (width > 300 || height > 200) {
                    String msg = "El archivo no tiene las dimensiones establecidas: 300px * 200px";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));

                    return;
                }

                boolean responseCm = cmtCuentaMatrizMglFacadeLocal.
                        cargarImagenCMxUCM(cm, uploadedFile, usuario, perfilVT);

                if (responseCm) {
                    urlArchivoImagen = cm.getImgCuenta();
                    String msg = "Archivo cargado correctamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msg, ""));

                } else {
                    String msg = "Ocurrio un error al guardar el archivo";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));

                }
            } catch (SearchCuentasMatricesFault | UploadCuentasMatricesFault | ApplicationException | IOException e) {
                FacesUtil.mostrarMensajeError("Error al cargar la imagen: " + e.getMessage(), e, LOGGER);
            }

        } else {
            String msg = "Debe seleccionar una imagen para guardar";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));

        }

    }
    
        
    public boolean validarExtArchivo(String extension) {

        boolean respuesta = false;
        int valido = 0;

        for (String valor : ConstantsCM.LISTA_EXTENSIONES) {

            if (valor.equalsIgnoreCase(extension)) {
                valido++;
            }

        }

        if (valido > 0) {
            respuesta = true;
        }

        return respuesta;

    }


        
    public String armarUrl(String nombreArchivo) {


        try {

            if (nombreArchivo != null
                    && !nombreArchivo.isEmpty()) {
                String server = "";
                String path = "";
                String url = "";

                Parametros param = resourceEJB.queryParametros(ConstantsCM.PROPERTY_SERVER_PATH);
                if (param != null) {
                    server = param.getValor();
                }
                param = resourceEJB.queryParametros(ConstantsCM.PROPERTY_PATH_PATH_IMA_CM);
                if (param != null) {
                    path = param.getValor();
                }
                param = resourceEJB.queryParametros(ConstantsCM.PROPERTY_URL_PATH);
                if (param != null) {
                    url = param.getValor();
                }
                urlArchivoImagen =  url
                        + (int) Math.floor(Math.random() * (-1000000000))
                        + server + path + nombreArchivo;


            }
        } catch (ApplicationException ex) {
            String msg = "Error al crear la url de la imagen" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msg, ""));
            LOGGER.error(msg);
            urlArchivoImagen = "";
        }
        return urlArchivoImagen;
    }
    
    public boolean isGeneral(CmtSubEdificioMgl subEdificioMgl) {

        CmtBasicaMgl multiedificio;
        boolean result = false;
        try {
            multiedificio = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            if (multiedificio != null) {
                if (multiedificio.getBasicaId() != null && subEdificioMgl != null 
                        && subEdificioMgl.getEstadoSubEdificioObj() != null 
                        && subEdificioMgl.getEstadoSubEdificioObj().getBasicaId() != null
                        && subEdificioMgl.getEstadoSubEdificioObj().getBasicaId().
                        compareTo(multiedificio.getBasicaId()) == 0) {
                    result = true;
                } else {
                    if (subEdificioMgl != null 
                            && subEdificioMgl.getCmtCuentaMatrizMglObj() != null
                            && isUnicoEdificio(subEdificioMgl.getCmtCuentaMatrizMglObj().
                            getCuentaMatrizId())) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return result;
    }
    
    private boolean isUnicoEdificio(BigDecimal idCuentaMatriz) {
        if (idCuentaMatriz != null) {
            try {
                CmtCuentaMatrizMgl cuentaMatrizMgl = cmtCuentaMatrizMglFacadeLocal.findByIdCM(idCuentaMatriz);
                if (cuentaMatrizMgl.isUnicoSubEdificioBoolean()) {
                    return true;
                } else {
                    return false;
                }
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
            }
        }
        return false;
    }
    
    
    
    /**
     * Obtiene la URL asociada a la Imagen de la Cuenta Matriz, correspondiente
     * al Servlet interno de consulta de documentos en UCM.
     * 
     * @return URL de consulta de la imagen de la Cuenta Matriz.
     */
    public String obtenerUrlUcmImagenCCMM() {
        String url = "";
        
        if (selectedCmtSubEdificioMgl != null 
                && urlArchivoImagen != null && !urlArchivoImagen.isEmpty()
                && selectedCmtSubEdificioMgl.getCmtCuentaMatrizMglObj() != null
                && selectedCmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId() != null) {
            
            BigDecimal idCuentaMatriz = 
                    selectedCmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId();
            
            
            // Imagen original de UCM
            String urlOriginal = urlArchivoImagen;

            String requestContextPath = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestContextPath();

            // URL correspondiente al Servlet que descarga imagenes de CCMM desde UCM.
            url = requestContextPath + "/view/MGL/CM/image/" + idCuentaMatriz;
        }
        
        return (url);
    }
    
    
    public List<EstratoxTorreDto> getListaEstratoxTorreDto() {
        return listaEstratoxTorreDto;
    }

    public void setListaEstratoxTorreDto(List<EstratoxTorreDto> listaEstratoxTorreDto) {
        this.listaEstratoxTorreDto = listaEstratoxTorreDto;
    }

    public CuentaMatrizBean getCuentaMatrizBean() {
        return cuentaMatrizBean;
    }

    public void setCuentaMatrizBean(CuentaMatrizBean cuentaMatrizBean) {
        this.cuentaMatrizBean = cuentaMatrizBean;
    }

    public CmtCuentaMatrizMgl getCmtCuentaMatrizMgl() {
        return cmtCuentaMatrizMgl;
    }

    public void setCmtCuentaMatrizMgl(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) {
        this.cmtCuentaMatrizMgl = cmtCuentaMatrizMgl;
    }

    public CmtSubEdificioMgl getSelectedCmtSubEdificioMgl() {
        return selectedCmtSubEdificioMgl;
    }

    public void setSelectedCmtSubEdificioMgl(CmtSubEdificioMgl selectedCmtSubEdificioMgl) {
        this.selectedCmtSubEdificioMgl = selectedCmtSubEdificioMgl;
    }

    public List<HhppEstrato> getResumenHhppByEstratoList() {
        return resumenHhppByEstratoList;
    }

    public void setResumenHhppByEstratoList(List<HhppEstrato> resumenHhppByEstratoList) {
        this.resumenHhppByEstratoList = resumenHhppByEstratoList;
    }

    public String getListDireccionesSeparadas() {
        return listDireccionesSeparadas;
    }

    public void setListDireccionesSeparadas(String listDireccionesSeparadas) {
        this.listDireccionesSeparadas = listDireccionesSeparadas;
    }

    public String getListBlackListSeparadas() {
        return listBlackListSeparadas;
    }

    public void setListBlackListSeparadas(String listBlackListSeparadas) {
        this.listBlackListSeparadas = listBlackListSeparadas;
    }

    public List<BigDecimal> getListBlackListIds() {
        return listBlackListIds;
    }

    public void setListBlackListIds(List<BigDecimal> listBlackListIds) {
        this.listBlackListIds = listBlackListIds;
    }

    public List<BigDecimal> getListDireccionesIds() {
        return listDireccionesIds;
    }

    public void setListDireccionesIds(List<BigDecimal> listDireccionesIds) {
        this.listDireccionesIds = listDireccionesIds;
    }

    public List<CmtBlackListMgl> getListBlackList() {
        return listBlackList;
    }

    public void setListBlackList(List<CmtBlackListMgl> listBlackList) {
        this.listBlackList = listBlackList;
    }

    public List<CmtDireccionMgl> getListDireccionesAlternas() {
        return listDireccionesAlternas;
    }

    public void setListDireccionesAlternas(List<CmtDireccionMgl> listDireccionesAlternas) {
        this.listDireccionesAlternas = listDireccionesAlternas;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getUrlArchivoImagen() {
        return urlArchivoImagen;
    }

    public void setUrlArchivoImagen(String urlArchivoImagen) {
        this.urlArchivoImagen = urlArchivoImagen;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getNumPisosTotal() {
        return numPisosTotal;
    }

    public void setNumPisosTotal(int numPisosTotal) {
        this.numPisosTotal = numPisosTotal;
    }

    public boolean isActivacionUCM() {
        return activacionUCM;
    }

    public void setActivacionUCM(boolean activacionUCM) {
        this.activacionUCM = activacionUCM;
    }

    
}
