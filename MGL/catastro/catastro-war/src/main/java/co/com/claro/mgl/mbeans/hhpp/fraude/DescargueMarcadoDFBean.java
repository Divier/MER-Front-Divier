/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.hhpp.fraude;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.MarcasHhppMglManager;
import co.com.claro.mgl.businessmanager.address.MarcasMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.dao.impl.MarcasMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.mbeans.direcciones.ConsultaHhppBean;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author castrofo
 */
@ManagedBean(name = "descargueMarcadoDFBean")
@ViewScoped
@Log4j2
public class DescargueMarcadoDFBean extends ConsultaHhppBean {

    private List<CmtDireccionDetalladaMgl> direccionFraudesList;
    private List<CmtDireccionDetalladaMgl> direccionFraudesListMuestra;
    private HashMap<CmtDireccionDetalladaMgl, List<MarcasMgl>> mapaDireccionesFraudesAdd;
    private HashMap<CmtDireccionDetalladaMgl, List<MarcasMgl>> mapaDireccionesFraudesRemove;
    private List<MarcasMgl> listaMarcasFraude;
    private static final String CODIGOS_MARCA_FRAUDES = "F,G";
    private String pageActualFra;
    private int actualFra = 1;
    private String numPaginaFra = "1";
    private List<Integer> paginaListFra;
    private boolean desmarcar;
    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
    private static int NUM_REGISTROS_PAGINA = 50;
    private static String[] NOM_COLUMNAS = {"Id Direccion detallada",
        "Departamento", " Municipio", " Centro poblado", " Nodo",
        " Direccion", "Codigo de la marca de fraude"
    };
    CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();
    MarcasMglManager marcasMglManager = new MarcasMglManager();
    
    //Opciones agregadas para Rol
    private final String BTDESARCHMARDF = "BTDESARCHMARDF";   
    private final String BTDESARCHDESMARDF = "BTDESARCHDESMARDF";   
    
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @Inject
    private BlockReportServBean blockReportServBean;

    @PostConstruct
    public void init() {
        super.init();
        direccionFraudesList = new ArrayList<>();
        direccionFraudesListMuestra = new ArrayList<>();
        mapaDireccionesFraudesAdd = new HashMap<>();
        mapaDireccionesFraudesRemove = new HashMap<>();
        listaMarcasFraude = new ArrayList<>();
        try {
            MarcasMglDaoImpl marcasMglDaoImpl = new MarcasMglDaoImpl();
            String[] codigos = CODIGOS_MARCA_FRAUDES.split(",");
            listaMarcasFraude = marcasMglDaoImpl.findByGrupoCodigo(Constant.TEC_TIPO_MARCA_FRAUDE);

        } catch (ApplicationException e) {
            LOGGER.error("Se produjo error al Cargar las Marcas de Fraude " + e.getMessage());
        }
    }

    /**
     * Creates a new instance of FraudesHHPPUnoUno
     */
    public DescargueMarcadoDFBean() {
        super();
        if (response.isCommitted()) {
            try {
                HttpSession session = (HttpSession) FacesUtil.getExternalContext().getSession(false);
                if (session.getAttribute("tipoDescargue") == null) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                } else if (session.getAttribute("tipoDescargue").equals("desmarcado")){
                    desmarcar = session.getAttribute("tipoDescargue").equals("desmarcado");
                }

            } catch (IOException ex) {
                LOGGER.error("Se produjo error al Cargar las Marcas de Fraude " + ex.getMessage());
            }
        }
    }

    public void volverMarcasOriginal(CmtDireccionDetalladaMgl direccion) {
        if (mapaDireccionesFraudesAdd.containsKey(direccion)) {
            mapaDireccionesFraudesAdd.remove(direccion);
        }
        if (mapaDireccionesFraudesRemove.containsKey(direccion)) {
            mapaDireccionesFraudesRemove.remove(direccion);
        }
    }

    public void addMarca(CmtDireccionDetalladaMgl direccion, MarcasMgl marca) {
        addMarca(direccion, marca, true, "");
    }

    public void addMarca(CmtDireccionDetalladaMgl direccion, MarcasMgl marca, boolean removeList, String colorMarca) {
        if (direccion.getHhppMgl() == null) {
            return;
        }
        if (!colorMarca.equals("")) {
            for (MarcasHhppMgl marcaHHPP : direccion.getHhppMgl().getListMarcasHhpp()) {
                if (removeList) {
                    if (desmarcar) {
                        if (marcaHHPP.getMarId().getMarId().equals(marca.getMarId()) && marcaHHPP.getEstadoRegistro() == 1) {
                            if (!mapaDireccionesFraudesRemove.keySet().contains(direccion)) {
                                mapaDireccionesFraudesRemove.put(direccion, new ArrayList<>());
                            }
                            if (!mapaDireccionesFraudesRemove.get(direccion).contains(marca)) {
                                mapaDireccionesFraudesRemove.get(direccion).add(marca);
                            }
                        }
                        return;
                    }
                    if (marcaHHPP.getMarId().getMarId().equals(marca.getMarId()) && marcaHHPP.getEstadoRegistro() == 1) {
                        if (!mapaDireccionesFraudesRemove.keySet().contains(direccion)) {
                            mapaDireccionesFraudesRemove.put(direccion, new ArrayList<>());
                        }
                        if (!mapaDireccionesFraudesRemove.get(direccion).contains(marca)) {
                            mapaDireccionesFraudesRemove.get(direccion).add(marca);
                        }
                        return;
                    }
                    if (!desmarcar) {
                        if (!mapaDireccionesFraudesAdd.keySet().contains(direccion)) {
                            mapaDireccionesFraudesAdd.put(direccion, new ArrayList<>());
                        }
                        if (mapaDireccionesFraudesAdd.get(direccion).contains(marca)) {
                            mapaDireccionesFraudesAdd.get(direccion).remove(marca);

                        }
                    }
                } else {
                    if (marcaHHPP.getMarId().getMarId().equals(marca.getMarId()) && marcaHHPP.getEstadoRegistro() == 1) {
                        if (!mapaDireccionesFraudesRemove.keySet().contains(direccion)) {
                            mapaDireccionesFraudesRemove.put(direccion, new ArrayList<>());
                        }
                        if (mapaDireccionesFraudesRemove.get(direccion).contains(marca)) {
                            mapaDireccionesFraudesRemove.get(direccion).remove(marca);
                        }
                        return;
                    }
                    if (!desmarcar) {
                        if (!mapaDireccionesFraudesAdd.keySet().contains(direccion)) {
                            mapaDireccionesFraudesAdd.put(direccion, new ArrayList<>());
                        }
                        if (!mapaDireccionesFraudesAdd.get(direccion).contains(marca)) {
                            mapaDireccionesFraudesAdd.get(direccion).add(marca);
                        }
                    }
                }
            }
        } else {
            for (MarcasHhppMgl marcaHHPP : direccion.getHhppMgl().getListMarcasHhpp()) {
                if (marcaHHPP.getMarId().getMarId().equals(marca.getMarId()) && marcaHHPP.getEstadoRegistro() == 1) {
                    if (!mapaDireccionesFraudesRemove.keySet().contains(direccion)) {
                        mapaDireccionesFraudesRemove.put(direccion, new ArrayList<>());
                    }
                    if (mapaDireccionesFraudesRemove.get(direccion).contains(marca)) {
                        if (removeList) {
                            mapaDireccionesFraudesRemove.get(direccion).remove(marca);
                        }
                    } else {
                        mapaDireccionesFraudesRemove.get(direccion).add(marca);
                    }
                    return;
                }
            }
            if (!desmarcar) {
                if (!mapaDireccionesFraudesAdd.keySet().contains(direccion)) {
                    mapaDireccionesFraudesAdd.put(direccion, new ArrayList<>());
                }
                if (mapaDireccionesFraudesAdd.get(direccion).contains(marca)) {
                    if (removeList) {
                        mapaDireccionesFraudesAdd.get(direccion).remove(marca);
                    }
                } else {
                    mapaDireccionesFraudesAdd.get(direccion).add(marca);
                }
            }
        }

    }

    public void moverListaAFraudes() {
        try {
            if (getCodigoNodo() != null) {
                this.direccionFraudesList.addAll(this.getDireccionDetalladaList());
                this.getDireccionDetalladaList().removeAll(direccionFraudesList);
                listInfoByPage(getActual(), this.direccionFraudesList);
                listInfoByPageFra(getActualFra());
            } else {
                this.direccionFraudesList.addAll(this.getDireccionDetalladaList());
                StringBuilder stb = new StringBuilder();
                this.direccionFraudesList.removeIf((CmtDireccionDetalladaMgl dir) -> {
                    if (dir.getHhppMgl() == null) {
                        stb.append("|").append(dir.getDireccionTexto()).append(" ");
                        return true;
                    }
                    return false;
                });
                if (!stb.toString().isEmpty()) {
                    FacesUtil.mostrarMensajeWarn("Las direcciones  : " + stb.toString() + " no tienen HHPP asociados no se pueden pasar.");
                }
                this.getDireccionDetalladaList().removeAll(direccionFraudesList);
                listInfoByPage(getActual(), getDireccionDetalladaBusquedaOriginalList());
                listInfoByPageFra(getActualFra());

            }

        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al Mover Pagina " + ex.getMessage(), ex, LOGGER);
        }
    }
    
    
        /**
     * Función mueve una pagina de direcciones al listado de direcciones de fraudes
     *
     * @author Juan David Hernandez
     */
        public void moverPaginaAFraudes() {
        try {
            if (getCodigoNodo() != null) {
                
                if (this.getDireccionDetalladaBusquedaOriginalList() != null && 
                   !this.getDireccionDetalladaBusquedaOriginalList().isEmpty()) {
                    direccionDetalladaList.clear();
                    for (CmtDireccionDetalladaMgl direccion : this.getDireccionDetalladaList()) {
                        if (direccion.getHhppMgl() != null) {
                            //Se crea lista para consultar las marcas del HHPP y tenerlas al instante
                            List<MarcasHhppMgl> listMarcasHhppMgl = new ArrayList<>();
                            listMarcasHhppMgl = marcasHhppMglManager.findMarcasHhppMglidHhpp(direccion.getHhppMgl().getHhpId());
                            
                                direccion.getHhppMgl().setListMarcasHhpp(listMarcasHhppMgl);
                            
                        }
                    }
                    //se pasan los elementos al listado completo de fraudes
                    direccionDetalladaList.addAll(direccionDetalladaBusquedaOriginalList);
                    obtenerParametrosHhppListado();
                    direccionFraudesList.addAll(this.getDireccionDetalladaList());
                    //se remueven del listado de direccion de la busqueda original
                    StringBuilder stb = new StringBuilder();
                    direccionFraudesList.removeIf((CmtDireccionDetalladaMgl dir) -> {
                        if (dir.getHhppMgl() == null) {
                            stb.append("|").append(dir.getDireccionTexto()).append(" ");
                            return true;
                        }
                        return false;
                    });
                    this.direccionDetalladaList.removeIf((CmtDireccionDetalladaMgl dir) -> {
                        return (dir.getHhppMgl() != null);
                    });
                    if (!stb.toString().isEmpty()) {
                        FacesUtil.mostrarMensajeWarn("Las direcciones  : " + stb.toString() + " no tienen HHPP asociados no se pueden pasar.");
                    }
                    this.getDireccionDetalladaBusquedaOriginalList().removeAll(direccionFraudesList);
                    if (this.getDireccionDetalladaBusquedaOriginalList() != null && !this.getDireccionDetalladaBusquedaOriginalList().isEmpty()) {
                        super.listInfoByPage(getActual() - 1 > 0 ? getActual() - 1 : 1, this.getDireccionDetalladaBusquedaOriginalList());
                        listInfoByPageFra(getActualFra());
                    } else {
                        this.setDireccionDetalladaList(new ArrayList());
                    }
                    listInfoByPageFra(getActualFra());
                    getPageActualFra();
                } else {
                    String msnError = "No hay registros para pasar mover pagina";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                }

            } else {
                if (this.getDireccionDetalladaBusquedaOriginalList() != null && 
                        !this.getDireccionDetalladaBusquedaOriginalList().isEmpty()) {
                direccionDetalladaList.clear();
                for (CmtDireccionDetalladaMgl direccion : this.getDireccionDetalladaBusquedaOriginalList()) {
                    if (direccion.getHhppMgl() != null) {
                        //Se crea lista para consultar las marcas del HHPP y tenerlas al instante
                        List<MarcasHhppMgl> listMarcasHhppMgl = new ArrayList<>();
                        listMarcasHhppMgl = marcasHhppMglManager.findMarcasHhppMglidHhpp(direccion.getHhppMgl().getHhpId());
                        
                            direccion.getHhppMgl().setListMarcasHhpp(listMarcasHhppMgl);
                        
                    }
                }
                direccionDetalladaList.addAll(direccionDetalladaBusquedaOriginalList);
                obtenerParametrosHhppListado();
                direccionFraudesList.addAll(this.getDireccionDetalladaList());
                StringBuilder stb = new StringBuilder();
                direccionFraudesList.removeIf((CmtDireccionDetalladaMgl dir) -> {
                    if (dir.getHhppMgl() == null) {
                        stb.append("|").append(dir.getDireccionTexto()).append(" ");
                        return true;
                    }
                    return false;
                });
                this.direccionDetalladaList.removeIf((CmtDireccionDetalladaMgl dir) -> {
                    return (dir.getHhppMgl() != null);
                });
                if (!stb.toString().isEmpty()) {
                    FacesUtil.mostrarMensajeWarn("Las direcciones  : " + stb.toString() + " no tienen HHPP asociados no se pueden pasar.");
                }
                
                this.getDireccionDetalladaBusquedaOriginalList().removeAll(direccionFraudesList);
                if (this.getDireccionDetalladaBusquedaOriginalList() != null && !this.getDireccionDetalladaBusquedaOriginalList().isEmpty()) {
                    super.listInfoByPage(getActual() - 1 > 0 ? getActual() - 1 : 1, this.getDireccionDetalladaBusquedaOriginalList());
                }
                listInfoByPageFra(getActualFra());
                getPageActualFra();
                } else {
                    String msnError = "No hay registros para pasar mover pagina";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                }
            }

        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al Mover Pagina " + ex.getMessage(), ex, LOGGER);
        }
    }

    public void borrarListaFraudes() {
        if (direccionFraudesList != null && !direccionFraudesList.isEmpty()) {
            this.getDireccionDetalladaBusquedaOriginalList().addAll(direccionFraudesList);
            mapaDireccionesFraudesAdd = new HashMap<>();
            mapaDireccionesFraudesRemove = new HashMap<>();
            direccionFraudesList = new ArrayList<>();
            direccionFraudesListMuestra = new ArrayList<>();
            listInfoByPageFra(1);
            super.listInfoByPage(1, getDireccionDetalladaBusquedaOriginalList());
        } else {
            String msnError = "No hay registros para borrar";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
        }
    }

    public void borrarListaBusqueda() {
        setDireccionDetalladaList(new ArrayList<>());
        setDireccionDetalladaBusquedaOriginalList(new ArrayList<>());
    }

    public String verificarColor(MarcasMgl marca) {
        for (CmtDireccionDetalladaMgl dir : direccionFraudesList) {
            if (dir.getHhppMgl() == null) {
                continue;
            }
            for (MarcasHhppMgl marcasHhppMgl : dir.getHhppMgl().getListMarcasHhpp()) {
                if (marcasHhppMgl.getEstadoRegistro() != 0 && marcasHhppMgl.getMarId().getMarCodigo().equals(marca.getMarCodigo())) {
                    if (mapaDireccionesFraudesRemove.get(dir) != null && mapaDireccionesFraudesRemove.get(dir).contains(marca)) {
                        continue;
                    }
                    return "red";
                }
            }
        }
        for (CmtDireccionDetalladaMgl dir : mapaDireccionesFraudesAdd.keySet()) {
            if (dir.getHhppMgl() == null) {
                continue;
            }
            for (MarcasMgl marcasHhppMgl : mapaDireccionesFraudesAdd.get(dir)) {
                if (marcasHhppMgl.getMarCodigo().equals(marca.getMarCodigo())) {
                    return "red";
                }
            }
        }
        return "green";
    }

    public String verificarColor(CmtDireccionDetalladaMgl direccion, MarcasMgl marca) {
        if (direccion.getHhppMgl() == null || direccion.getHhppMgl().getListMarcasHhpp() == null) {
            return "blue";
        }
        for (MarcasHhppMgl marcaHHPP : direccion.getHhppMgl().getListMarcasHhpp()) {
            if (marcaHHPP.getMarId().getMarId().equals(marca.getMarId()) && marcaHHPP.getEstadoRegistro() == 1) {
                if (mapaDireccionesFraudesRemove.get(direccion) != null && mapaDireccionesFraudesRemove.get(direccion).contains(marca)) {
                    return "green";
                }
                return "red";
            }
        }
        if (mapaDireccionesFraudesAdd.get(direccion) != null && mapaDireccionesFraudesAdd.get(direccion).contains(marca)) {
            return "red";
        }
        return "green";
    }

    @Override
    public void buscarHhpp() {
        try {
            //si la direccion es construida por el panel de direccion tabulada
            asignarDireccionConstruidaPorTabulada(); 

            //validacion de criterios de busqueda obligatorios
            if (validarCriteriosDeBusquedaObligatorios()) {
                //filtro por barrio y/o nodo sin direccion construida
                if (getBarrio() != null && !getBarrio().isEmpty()
                        && (this.responseConstruirDireccion == null || this.responseConstruirDireccion.getDrDireccion() == null)) {

                    if (getCentroPobladoSeleccionado() != null) {
                        this.direccionDetalladaBusquedaOriginalList = new ArrayList();
                        this.setDireccionDetalladaBusquedaOriginalList(getCmtDireccionDetalleMglFacadeLocal()
                                .buscarDireccionDetalladaByBarrioNodo(getCentroPobladoSeleccionado().getGpoId(),
                                        getBarrio().toUpperCase(),
                                        getTipoDireccion() != null ? getTipoDireccion() : null,
                                        getCodigoNodo() != null && !getCodigoNodo().isEmpty() ? getCodigoNodo() : null));

                        if (this.getDireccionDetalladaBusquedaOriginalList() != null
                                && !this.getDireccionDetalladaBusquedaOriginalList().isEmpty()) {

                            if (isDesmarcar()) {
                                //filtro de direccion que solo sean fraudulentas
                                this.setDireccionDetalladaBusquedaOriginalList(filtrarDireccionSoloFraudulentas(this.getDireccionDetalladaBusquedaOriginalList()));
                            }
                            if (this.getDireccionDetalladaBusquedaOriginalList() != null
                                    && !this.getDireccionDetalladaBusquedaOriginalList().isEmpty()) {

                                super.listInfoByPage(1, this.getDireccionDetalladaBusquedaOriginalList());
                                super.showPanelBusquedaDireccion = false;
                                super.showFooter = true;

                                String msnError = "Direcciones encontradas satisfactoriamente.";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                msnError, ""));
                            } else {
                                this.direccionDetalladaList = new ArrayList();
                                String msnError = "No se encontraron resultados con los criterios de busqueda ingresados";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msnError, ""));
                                super.showFooter = false;
                            }
                        } else {
                            this.direccionDetalladaList = new ArrayList();
                            String msnError = "No se encontraron resultados con los criterios de busqueda ingresados";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msnError, ""));
                            super.showFooter = false;
                        }

                    } else {
                        String msnError = "Para filtrar por Barrio es necesario seleccionar un centro poblado";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                    }

                } else {
                    //ingresa cuando se construyo una direccion
                    if ((this.responseConstruirDireccion != null && this.responseConstruirDireccion.getDrDireccion() != null)) {

                        super.buscarHhpp();
                        List<CmtDireccionDetalladaMgl> direccionDetalladaFiltroList = new ArrayList<CmtDireccionDetalladaMgl>();

                        if (this.getDireccionDetalladaBusquedaOriginalList() != null && !this.getDireccionDetalladaBusquedaOriginalList().isEmpty()) {

                            if (isDesmarcar()) {
                                //filtro de direccion que solo sean fraudulentas
                                this.setDireccionDetalladaBusquedaOriginalList(filtrarDireccionSoloFraudulentas(this.getDireccionDetalladaBusquedaOriginalList()));
                            }
                            if (this.getDireccionDetalladaBusquedaOriginalList() != null 
                                    && !this.getDireccionDetalladaBusquedaOriginalList().isEmpty() 
                                    && this.getDireccionDetalladaBusquedaOriginalList().size()>0) {

                                //si se construyó una direccion y se ingreso barrio o nodo
                                if ((getCodigoNodo() != null && !getCodigoNodo().isEmpty()) || (getBarrio() != null && !getBarrio().isEmpty())) {

                                    for (CmtDireccionDetalladaMgl direccionDetalladaMgl : this.getDireccionDetalladaBusquedaOriginalList()) {
                                        //si se ingreso nodo y barrio filtra los resultados de las direccion por los 2 valores
                                        if (getCodigoNodo() != null && !getCodigoNodo().isEmpty() && getBarrio() != null && !getBarrio().isEmpty()) {

                                            if (direccionDetalladaMgl.getHhppMgl() != null && direccionDetalladaMgl.getHhppMgl().getEstadoRegistro() == 1
                                                    && direccionDetalladaMgl.getHhppMgl().getNodId() != null
                                                    && direccionDetalladaMgl.getHhppMgl().getNodId().getNodCodigo().toUpperCase().equalsIgnoreCase(getCodigoNodo().toUpperCase())
                                                    && direccionDetalladaMgl.getBarrio() != null && !direccionDetalladaMgl.getBarrio().isEmpty()
                                                    && direccionDetalladaMgl.getBarrio().toUpperCase().equalsIgnoreCase(getBarrio().toUpperCase())) {
                                                //si la direccion coincide con el nodo y el barrio digitados
                                                direccionDetalladaFiltroList.add(direccionDetalladaMgl);
                                            }
                                        } else {
                                            //si se construyo una dirección y se selecciono un nodo
                                            if (getCodigoNodo() != null && !getCodigoNodo().isEmpty()) {
                                                if (direccionDetalladaMgl.getHhppMgl() != null && direccionDetalladaMgl.getHhppMgl().getEstadoRegistro() == 1
                                                        && direccionDetalladaMgl.getHhppMgl().getNodId() != null
                                                        && direccionDetalladaMgl.getHhppMgl().getNodId().getNodCodigo() != null
                                                        && !direccionDetalladaMgl.getHhppMgl().getNodId().getNodCodigo().isEmpty()
                                                        && direccionDetalladaMgl.getHhppMgl().getNodId().getNodCodigo().toUpperCase().equalsIgnoreCase(getCodigoNodo().toUpperCase())) {
                                                    //si la direccion coincide con el nodo y el barrio digitados
                                                    direccionDetalladaFiltroList.add(direccionDetalladaMgl);
                                                }
                                            } else {
                                                //si se construyo una dirección y se ingreso un barrio
                                                if (direccionDetalladaMgl.getBarrio() != null && !direccionDetalladaMgl.getBarrio().isEmpty()
                                                        && getBarrio() != null && !getBarrio().isEmpty()) {

                                                    if (direccionDetalladaMgl.getBarrio().toUpperCase().equalsIgnoreCase(getBarrio().toUpperCase())) {
                                                        //si la direccion coincide con el nodo y el barrio digitados
                                                        direccionDetalladaFiltroList.add(direccionDetalladaMgl);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    //se limpia la lista de direccion para dejarla solo con las que cumplieron el filtro
                                    this.direccionDetalladaBusquedaOriginalList = new ArrayList();
                                    this.direccionDetalladaList = new ArrayList();
                                    if (direccionDetalladaFiltroList != null && !direccionDetalladaFiltroList.isEmpty()) {
                                        for (CmtDireccionDetalladaMgl direccionfiltradaPorNodo : direccionDetalladaFiltroList) {
                                            this.getDireccionDetalladaBusquedaOriginalList().add(direccionfiltradaPorNodo.clone());
                                        }
                                        super.listInfoByPage(1, this.getDireccionDetalladaBusquedaOriginalList());
                                    } else {
                                        this.direccionDetalladaBusquedaOriginalList = new ArrayList();
                                        this.direccionDetalladaList = new ArrayList();
                                        String msnError = "No se encontraron resultados con los criterios de busqueda ingresados.";
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        msnError, ""));

                                    }

                                }

                            } else {
                                //limpieza de resultados anteriores
                                this.direccionDetalladaBusquedaOriginalList = new ArrayList();
                                this.direccionDetalladaList = new ArrayList();
                                String msnError = "No se obtuvieron resultados con la dirección"
                                            + " construida.";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    msnError, ""));
                                    showPanelBusquedaDireccion = true;
                                    return;
                            }

                        } else {
                            //limpieza de resultados anteriores
                            this.direccionDetalladaBusquedaOriginalList = new ArrayList();
                            this.direccionDetalladaList = new ArrayList();
                        }

                    } else {
                        //JDHT Si se selecciona un nodo y no se crea ninguna dirección
                        if (getCodigoNodo() != null && !getCodigoNodo().isEmpty() && (responseConstruirDireccion == null
                                || responseConstruirDireccion.getDrDireccion() == null)) {
                            this.direccionDetalladaBusquedaOriginalList = new ArrayList();
                            this.direccionDetalladaList = new ArrayList();

                            //busqueda de direccion detalladas apartir de los hhpp encontrados por nodo sin direccion construida  
                            this.getDireccionDetalladaBusquedaOriginalList().addAll(obtenerDireccionDetallaByHhppList(getHhppMglFacadeLocal().findHhppByNodo(getCodigoNodo())));
                            
                            if (this.getDireccionDetalladaBusquedaOriginalList() != null
                                    && !this.getDireccionDetalladaBusquedaOriginalList().isEmpty()) {
                                if (isDesmarcar()) {
                                    //filtro de direccion que solo sean fraudulentas
                                    this.setDireccionDetalladaBusquedaOriginalList(filtrarDireccionSoloFraudulentas(this.getDireccionDetalladaBusquedaOriginalList()));
                                }
                            }

                            if (this.getDireccionDetalladaBusquedaOriginalList() != null
                                    && !this.getDireccionDetalladaBusquedaOriginalList().isEmpty()) {
                                super.listInfoByPage(1, this.getDireccionDetalladaBusquedaOriginalList());
                                super.showPanelBusquedaDireccion = false;
                                super.showFooter = true;
                                String msnError = "Direcciones encontradas satisfactoriamente.";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                msnError, ""));
                            } else {
                                this.direccionDetalladaBusquedaOriginalList = new ArrayList();
                                this.direccionDetalladaList = new ArrayList();
                                String msnError = "No se encontraron resultados con el nodo seleccionado";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                msnError, ""));
                                super.showFooter = false;
                            }
                        } else {
                            super.buscarHhpp();
                            super.listInfoByPage(1, this.getDireccionDetalladaBusquedaOriginalList());
                        }
                    }
                }
            }

        } catch (CloneNotSupportedException | ApplicationException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
    
    public void asignarDireccionConstruidaPorTabulada(){
        try {
            if (!this.notGeoReferenciado) {
                if (this.drDireccion == null) {
                    String msnError = "Es necesario construir una dirección con el panel de dirección tabulada.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                    return;
                }
                responseConstruirDireccion.setDrDireccion(drDireccion);
            }
        } catch (Exception e) {
             String msnError = "Error al asignar el drDireccion cuando se crea por direccion tabulada." 
                     + e.getMessage();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msnError, ""));
        }
    }
    
    public boolean validarCriteriosDeBusquedaObligatorios() {
        try {
            if ((getBarrio() != null && !getBarrio().isEmpty())
                    || (getCodigoNodo() != null && !getCodigoNodo().isEmpty())
                    || (this.responseConstruirDireccion != null && this.responseConstruirDireccion.getDrDireccion() != null)) {
                return true;
            } else {
                String msnError = "Debe ingresar un criterio de busqueda por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                return false;
            }
        } catch (Exception e) {
            String msnError = "Error al validar datos obligatorios de busqueda.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
            return false;
        }
    }

    @Override
    public void listInfoByPage(int page, List<CmtDireccionDetalladaMgl> direccionDetalladaListCompletaOriginal) {
        super.listInfoByPage(page, this.getDireccionDetalladaBusquedaOriginalList());
        this.getDireccionDetalladaList().removeIf(dir -> {
            return direccionFraudesList.contains(dir);
        });
        try {
            obtenerParametrosHhppListadoOriginal();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPage obteniendo parametros", e, LOGGER);
        }
        if (desmarcar) {
            this.getDireccionDetalladaBusquedaOriginalList().removeIf((CmtDireccionDetalladaMgl obj) -> {
                boolean ret = true;
                if (obj.getHhppMgl() != null) {
                    //Se crea lista para consultar las marcas del HHPP y tenerlas al instante
                    List<MarcasHhppMgl> listMarcasHhppMgl = new ArrayList<>();
                    try {
                        listMarcasHhppMgl = marcasHhppMglManager.findMarcasHhppMglidHhpp(obj.getHhppMgl().getHhpId());
                    } catch (Exception e) {
                        FacesUtil.mostrarMensajeError("Error en listInfoByPage consultando las marcas", e, LOGGER);
                    }
                    for (MarcasHhppMgl marcHHPP : obj.getHhppMgl().getListMarcasHhpp()) {
                        if (marcHHPP.getEstadoRegistro() == 1 && marcHHPP.getMarId().getTmaId().getCodigo().equals(Constant.TEC_TIPO_MARCA_FRAUDE)) {
                            ret = false;
                        }
                    }
                }
                return ret;
            });
            super.listInfoByPage(page, this.getDireccionDetalladaBusquedaOriginalList());
            this.getDireccionDetalladaList().removeIf(dir -> {
                return direccionFraudesList.contains(dir);
            });
        }
    }

    /**
     * Función que filtra las direcciones ya consultada para dejar solo las que
     * estan marcadas como fraudulentas
     *
     * @author Juan David Hernandez
     * @param direccionDetalladaOriginalList
     * @param nodoSeleccionado
     * @return
     */
    public List<CmtDireccionDetalladaMgl> filtrarDireccionSoloFraudulentas(List<CmtDireccionDetalladaMgl> direccionDetalladaOriginalList) {
        List<CmtDireccionDetalladaMgl> direccionDetalladaFiltradaList = new ArrayList();
        try {
            if (direccionDetalladaOriginalList != null && !direccionDetalladaOriginalList.isEmpty()) {
                HhppMglManager hhppMglManager = new HhppMglManager();

                direccionDetalladaOriginalList.forEach((dirDetallada) -> {
                    if (dirDetallada.getSubDireccion() != null
                            && dirDetallada.getSubDireccion().getSdiId() != null) {

                        List<HhppMgl> hhhpSubDirList = hhppMglManager
                                .findHhppSubDireccion(dirDetallada.getSubDireccion().getSdiId());

                        //Si la direccion cuenta con hhpp se agrega al listado filtrado
                        if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {

                            if (hhhpSubDirList.get(0).getEstadoRegistro() == 1
                                    && hhhpSubDirList.get(0).getListMarcasHhpp() != null
                                    && !hhhpSubDirList.get(0).getListMarcasHhpp().isEmpty()) {

                                for (MarcasHhppMgl marcasHhppMgl : hhhpSubDirList.get(0).getListMarcasHhpp()) {
                                    if (marcasHhppMgl.getMarId() != null
                                            && marcasHhppMgl.getMarId().getTmaId() != null && marcasHhppMgl.getEstadoRegistro() == 1
                                            && marcasHhppMgl.getMarId().getTmaId().getCodigo().equalsIgnoreCase(Constant.TEC_TIPO_MARCA_FRAUDE)) {
                                        boolean bandListaAgr = false;
                                        if (!direccionDetalladaFiltradaList.isEmpty()) {
                                            for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : direccionDetalladaFiltradaList) {
                                                if (cmtDireccionDetalladaMgl.getDireccionDetalladaId() != null
                                                        && cmtDireccionDetalladaMgl.getDireccionDetalladaId().equals(dirDetallada.getDireccionDetalladaId())) {
                                                    bandListaAgr = true;
                                                    break;
                                                }
                                            }
                                        }

                                        //se agrega la direccion filtrada ya que si esta marcada como fraudulenta
                                        if (!bandListaAgr) {
                                            direccionDetalladaFiltradaList.add(dirDetallada);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //Obtenemos los Hhpp de la Direccion principal    
                        if (dirDetallada.getDireccion() != null
                                && dirDetallada.getDireccion().getDirId() != null) {

                            List<HhppMgl> hhhpDirList
                                    = hhppMglManager
                                            .findHhppDireccion(dirDetallada.getDireccion().getDirId());

                            if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                                if (hhhpDirList.get(0).getEstadoRegistro() == 1
                                        && hhhpDirList.get(0).getListMarcasHhpp() != null
                                        && !hhhpDirList.get(0).getListMarcasHhpp().isEmpty()) {

                                    hhhpDirList.get(0).getListMarcasHhpp().stream().filter((marcasHhppMgl) -> (marcasHhppMgl.getMarId() != null
                                            && marcasHhppMgl.getMarId().getTmaId() != null && marcasHhppMgl.getEstadoRegistro() == 1
                                            && marcasHhppMgl.getMarId().getTmaId().getCodigo().equalsIgnoreCase(Constant.TEC_TIPO_MARCA_FRAUDE))).forEachOrdered((_item) -> {
                                        boolean bandListaAgr = false;
                                        if (!direccionDetalladaFiltradaList.isEmpty()) {
                                            for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : direccionDetalladaFiltradaList) {
                                                if (cmtDireccionDetalladaMgl.getDireccionDetalladaId() != null
                                                        && cmtDireccionDetalladaMgl.getDireccionDetalladaId().equals(dirDetallada.getDireccionDetalladaId())) {
                                                    bandListaAgr = true;
                                                    break;
                                                }
                                            }
                                        }

                                        //se agrega la direccion filtrada ya que si esta marcada como fraudulenta
                                        if (!bandListaAgr) {
                                            direccionDetalladaFiltradaList.add(dirDetallada);
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }

            return direccionDetalladaFiltradaList;

        } catch (Exception e) {
            String msnError = "Error al filtrar direccion fraudulentas.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
            return null;
        }
    }

    /**
     * Función obtiene el listado de direccion detalladas apartir de un listado
     * de hhpp
     *
     * @author Juan David Hernandez
     * @param hhppList
     * @return
     */
    public List<CmtDireccionDetalladaMgl> obtenerDireccionDetallaByHhppList(List<HhppMgl> hhppList) {
        try {
            List<CmtDireccionDetalladaMgl> resultDireccionDetalladaList = new ArrayList();
            if (hhppList != null && !hhppList.isEmpty()) {
                for (HhppMgl hhppMgl : hhppList) {
                    BigDecimal dirId = null;
                    BigDecimal sdirId = null;
                    if (hhppMgl.getDireccionObj() != null) {
                        dirId = hhppMgl.getDireccionObj().getDirId();
                    }
                    if (hhppMgl.getSubDireccionObj() != null) {
                        sdirId = hhppMgl.getSubDireccionObj().getSdiId();
                    }
                    List<CmtDireccionDetalladaMgl> direccionDetalladaList
                            = direccionDetalladaManager.findDireccionDetallaByDirIdSdirId(dirId, sdirId);
                    if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                        for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : direccionDetalladaList) {
                            resultDireccionDetalladaList.add(cmtDireccionDetalladaMgl);
                        }
                    }
                }
            }
            return resultDireccionDetalladaList;
        } catch (ApplicationException e) {
            return null;
        }

    }

    public void addDireccionAFraudes(CmtDireccionDetalladaMgl direccion) throws ApplicationException{
        try{
        if (direccion.getHhppMgl() == null) {
            FacesUtil.mostrarMensajeWarn("La direccion : " + direccion.getDireccionTexto() + " no tiene HHPP Asociado no se puede pasar.");
            return;
        }
        //Se crea lista para consultar las marcas del HHPP y tenerlas al instante
        List<MarcasHhppMgl> listMarcasHhppMgl = new ArrayList<>();
        listMarcasHhppMgl = marcasHhppMglManager.findMarcasHhppMglidHhpp(direccion.getHhppMgl().getHhpId());
        
            direccion.getHhppMgl().setListMarcasHhpp(listMarcasHhppMgl);
        
        this.getDireccionDetalladaList().remove(direccion);
        direccionFraudesList.add(direccion);
        this.getDireccionDetalladaBusquedaOriginalList().remove(direccion);
        listInfoByPageFra(getActualFra());
        getPageActualFra();
        }catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al Mover Dirección " + ex.getMessage(), ex, LOGGER);
        }
    }

    public void removeDireccionFraudes(CmtDireccionDetalladaMgl direccion) {
        direccionFraudesList.remove(direccion);
        direccionFraudesListMuestra.remove(direccion);
        listInfoByPageFra(getActualFra());
        getPageActualFra();
        this.getDireccionDetalladaList().add(direccion);
        this.getDireccionDetalladaBusquedaOriginalList().add(direccion);
    }

    public String listInfoByPageFra(int page) {
        try {
            if (page == 0) {
                page = 1;
            }
            setActualFra(page);
            if (direccionFraudesList != null && !direccionFraudesList.isEmpty()) {
                int fin = page * filasPag5;
                int inicio = fin - filasPag5;
                fin = fin > direccionFraudesList.size() ? direccionFraudesList.size() : fin;
                inicio = inicio <= 0 ? 0 : inicio;
                direccionFraudesListMuestra = new ArrayList<>();
                for (int i = inicio; i < fin; i++) {
                    direccionFraudesListMuestra.add(direccionFraudesList.get(i));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en listInfoByPageFra. ", e);
        }
        return null;
    }

    public void pageFirstFra() {
        try {
            listInfoByPageFra(1);
            getPageActualFra();
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en pageFirst, redireccionando a primera pagina. ", ex, LOGGER);
        }
    }

    public void pagePreviousFra() {
        try {
            int totalPaginas = getTotalPaginasFra();
            int nuevaPageActual = getActualFra() - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageFra(nuevaPageActual);
            getPageActualFra();
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en pagePrevious, direccionando a la página anterior. ", ex, LOGGER);
        }
    }

    public void irPaginaFra() {
        try {
            int totalPaginas = getTotalPaginasFra();
            int nuevaPageActual = Integer.parseInt(getNumPaginaFra());
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageFra(nuevaPageActual);
            getPageActualFra();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irPagina. ", e, LOGGER);
        }
    }

    public void pageNextFra() {
        try {
            int totalPaginas = getTotalPaginasFra();
            int nuevaPageActual = getActualFra() + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageFra(nuevaPageActual);
            getPageActualFra();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNext, direccionando a la siguiente página ", e, LOGGER);
        }
    }

    public void pageLastFra() {
        try {
            int totalPaginas = getTotalPaginasFra();
            listInfoByPageFra(totalPaginas);
            getPageActualFra();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageLast, direccionando a la última página ", e, LOGGER);
        }
    }

    public int getTotalPaginasFra() {
        try {
            if (direccionFraudesList.isEmpty()) {
                return 1;
            }
            int totalPaginas;
            int pageSol = direccionFraudesList.size();

            totalPaginas = (int) ((pageSol % filasPag5 != 0)
                    ? (pageSol / filasPag5) + 1 : pageSol / filasPag5);

            return totalPaginas;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas, redireccionand página. ", e, LOGGER);
        }
        return 1;
    }

    public List<CmtDireccionDetalladaMgl> getDireccionFraudesList() {
        return direccionFraudesList;
    }

    public void setDireccionFraudesList(List<CmtDireccionDetalladaMgl> direccionFraudesList) {
        this.direccionFraudesList = direccionFraudesList;
    }

    public List<MarcasMgl> getListaMarcasFraude() {
        return listaMarcasFraude;
    }

    public void setListaMarcasFraude(List<MarcasMgl> listaMarcasFraude) {
        this.listaMarcasFraude = listaMarcasFraude;
    }

    public String getPageActualFra() {
        paginaListFra = new ArrayList<>();
        int totalPaginas = getTotalPaginasFra();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListFra.add(i);
        }
        pageActualFra = String.valueOf(actualFra) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaFra == null) {
            numPaginaFra = "1";
        }
        numPaginaFra = String.valueOf(actualFra);
        return pageActualFra;
    }

    public String descargarArchivo() {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("[Descargue de Archivo para Marcado de DF]")) return StringUtils.EMPTY;

            final StringBuffer sb = new StringBuffer();
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");
            List<CmtDireccionDetalladaMgl> listaDireccionesDetalladasConmarcas = new ArrayList<CmtDireccionDetalladaMgl>();
            if (!direccionFraudesList.isEmpty()) {
                int numeroRegistros = direccionFraudesList.size();
                byte[] csvData = null;
                if (numeroRegistros > 0) {
                    for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                        sb.append(NOM_COLUMNAS[j]);
                        if (j < NOM_COLUMNAS.length) {
                            sb.append(";");
                        }
                    }

                    // filtro para desmarcacion
                    if (desmarcar) {
                        for (CmtDireccionDetalladaMgl cmt : direccionFraudesList) {
                            if (cmt.getHhppMgl().getListMarcasHhpp() != null && !cmt.getHhppMgl().getListMarcasHhpp().isEmpty()) {
                                for (MarcasHhppMgl tecMarcas : cmt.getHhppMgl().getListMarcasHhpp()) {
                                    if (tecMarcas.getEstadoRegistro() == 1) {
                                        listaDireccionesDetalladasConmarcas.add(cmt);
                                        break;
                                    }
                                }
                            }
                        }
                        direccionFraudesList.clear();
                        for (CmtDireccionDetalladaMgl direccionesconMarca : listaDireccionesDetalladasConmarcas) {
                            direccionFraudesList.add(direccionesconMarca.clone());
                        }

                    }
                    sb.append("\n");

                    for (CmtDireccionDetalladaMgl cmt : direccionFraudesList) {                        
                        if (cmt.getHhppMgl() != null) {
                            //Se crea lista para consultar las marcas del HHPP y tenerlas al instante
                            List<MarcasHhppMgl> listMarcasHhppMgl = new ArrayList<>();
                            listMarcasHhppMgl = marcasHhppMglManager.findMarcasHhppMglidHhpp(cmt.getHhppMgl().getHhpId());
                            
                                cmt.getHhppMgl().setListMarcasHhpp(listMarcasHhppMgl);
                            
                        }
                        String idDirDet = cmt.getDireccionDetalladaId().toString() == null ? " " : cmt.getDireccionDetalladaId().toString();
                        sb.append(idDirDet);
                        sb.append(";");
                        String depart = cmt.getDepartamento() == null ? " " : StringUtils.stripAccents(cmt.getDepartamento());
                        sb.append(depart);
                        sb.append(";");
                        String municipio = cmt.getCiudad() == null ? " " : StringUtils.stripAccents(cmt.getCiudad());
                        sb.append(municipio);
                        sb.append(";");
                        String centroPoblado = cmt.getDireccion().getUbicacion().getGpoIdObj().getGpoNombre() == null ? " " : StringUtils.stripAccents(cmt.getDireccion().getUbicacion().getGpoIdObj().getGpoNombre());
                        sb.append(centroPoblado);
                        sb.append(";");
                        if (cmt.getHhppMgl() != null) {
                            
                             //JDHT si se ingreso nodo como filtro se obtiene el nodo del hhpp correspondiente al filtrado asi tenga varias tecnologias
                            if(getCodigoNodo() != null && !getCodigoNodo().isEmpty() ){    
                                HhppMgl hhppNodoSeleccionado = obtenerHhppNodoFiltrado(cmt, getCodigoNodo());
                                //si se encuentra el hhpp con el nodo seleccionado lo asigna.
                                if(hhppNodoSeleccionado != null){
                                    cmt.setHhppMgl(hhppNodoSeleccionado);
                                }
                            }
                            
                            
                            if (cmt.getHhppMgl().getNodId() != null) {
                                String nodo = cmt.getHhppMgl().getNodId().getNodCodigo() == null ? " " : StringUtils.stripAccents(cmt.getHhppMgl().getNodId().getNodCodigo());
                                sb.append(nodo);
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }
                        } else {
                            sb.append("");
                            sb.append(";");
                        }
                        String direccion = cmt.getDireccionTexto() == null ? " " : StringUtils.stripAccents(cmt.getDireccionTexto());
                        sb.append(direccion);
                        sb.append(";");
                        if (cmt.getHhppMgl() != null) {
                            String marcaDF = "";
                            if (cmt.getHhppMgl().getListMarcasHhpp() != null) {
                                for (MarcasHhppMgl marcasHhppMgl : cmt.getHhppMgl().getListMarcasHhpp()) {
                                    if (marcasHhppMgl.getMarId().getTmaId() != null && marcasHhppMgl.getEstadoRegistro() == 1) {
                                        if (marcasHhppMgl.getMarId().getTmaId().getCodigo().equals(Constant.TEC_TIPO_MARCA_FRAUDE)) {
                                            marcaDF += marcasHhppMgl.getMarId().getMarCodigo() + ",";
                                        }
                                    }

                                }
                            }
                            if (!marcaDF.isEmpty()) {
                                marcaDF = marcaDF.substring(0, marcaDF.length() - 1);
                            }
                            sb.append(marcaDF);
                        } else {
                            sb.append(";");
                        }
                        sb.append("\n");
                    }

                }
                csvData = sb.toString().getBytes();
                String todayStr = formato.format(new Date());
                String fileName = "";
                if (desmarcar) {
                    fileName = "DescargueDesmarcado_DF" + "_" + todayStr + "." + "csv";
                } else {
                    fileName = "DescargueMarcado_DF" + "_" + todayStr + "." + "csv";
                }
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                response.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response.setContentType("application/force.download");
                response.getOutputStream().write(csvData);
                response.setCharacterEncoding("UTF-8");
                response.getOutputStream().flush();
                response.getOutputStream().close();
                fc.responseComplete();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron registros para el reporte", ""));
            }

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en descargarArchivo:DescargueMarcadoDFBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en descargarArchivo:DescargueMarcadoDFBean. " + e.getMessage(), e, LOGGER);
        }
        return "case1";
    }

    public void listInfoDirDes(int page, List<CmtDireccionDetalladaMgl> direccionDetalladaListCompletaOriginal) {
        this.getDireccionDetalladaList().removeIf(dir -> {
            return direccionFraudesList.contains(dir);
        });
        try {
            obtenerParametrosHhppListadoOriginal();
        } catch (ApplicationException ex) {
        }

    }
    
        /**
     * Función obtiene el hhpp del nodo seleccionado de la direccion detallada recibida
     *
     * @author Juan David Hernandez
     * @param direccionDetalladaFraude
     * @param nodoSeleccionado
     * @return HhppMgl
     */
    public HhppMgl obtenerHhppNodoFiltrado(CmtDireccionDetalladaMgl direccionDetalladaFraude, String nodoSeleccionado) {
        try {
            if (direccionDetalladaFraude != null && nodoSeleccionado != null && !nodoSeleccionado.isEmpty()) {
                //Obtenemos los Hhpp de la Subdireccion  
                if (direccionDetalladaFraude.getSubDireccion() != null
                        && direccionDetalladaFraude.getSubDireccion().getSdiId() != null) {

                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                            .findHhppSubDireccion(direccionDetalladaFraude.getSubDireccion().getSdiId());

                    if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                        for (HhppMgl hhppMglSubDireccion : hhhpSubDirList) {
                            if (hhppMglSubDireccion.getNodId().getNodCodigo().toUpperCase().equalsIgnoreCase(nodoSeleccionado.toUpperCase())) {
                                return hhppMglSubDireccion;
                            }
                        }
                    }
                    return null;
                } else {
                    //Obtenemos los Hhpp de la Direccion principal    
                    if (direccionDetalladaFraude.getDireccion() != null
                            && direccionDetalladaFraude.getDireccion().getDirId() != null) {

                        List<HhppMgl> hhhpDirList
                                = hhppMglManager
                                        .findHhppDireccion(direccionDetalladaFraude.getDireccion().getDirId());

                        if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                            for (HhppMgl hhppMglDireccion : hhhpDirList) {
                                if (hhppMglDireccion.getNodId().getNodCodigo().toUpperCase().equalsIgnoreCase(nodoSeleccionado.toUpperCase())) {
                                    return hhppMglDireccion;
                                }
                            }
                        }
                    }
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    	
    // Validar Opciones por Rol    
    public boolean validarOpcionDescArchivoMarcar() {
        return validarEdicionRol(BTDESARCHMARDF);
    }
    
    public boolean validarOpcionDescArchivoDesmarcar() {
        return validarEdicionRol(BTDESARCHDESMARDF);
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
            FacesUtil.mostrarMensajeError("Error al SubtipoOtVtTecBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public int getActualFra() {
        return actualFra;
    }

    public void setActualFra(int actualFra) {
        this.actualFra = actualFra;
    }

    public String getNumPaginaFra() {
        return numPaginaFra;
    }

    public void setNumPaginaFra(String numPaginaFra) {
        this.numPaginaFra = numPaginaFra;
    }

    public void setPageActualFra(String pageActualFra) {
        this.pageActualFra = pageActualFra;
    }

    public List<CmtDireccionDetalladaMgl> getDireccionFraudesListMuestra() {
        return direccionFraudesListMuestra;
    }

    public void setDireccionFraudesListMuestra(List<CmtDireccionDetalladaMgl> direccionFraudesListMuestra) {
        this.direccionFraudesListMuestra = direccionFraudesListMuestra;
    }

    public List<Integer> getPaginaListFra() {
        return paginaListFra;
    }

    public void setPaginaListFra(List<Integer> paginaListFra) {
        this.paginaListFra = paginaListFra;
    }

    public boolean isDesmarcar() {
        return desmarcar;
    }

    public void setDesmarcar(boolean desmarcar) {
        this.desmarcar = desmarcar;
    }

}
