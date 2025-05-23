/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.hhpp.fraude;

import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.MarcasHhppMglManager;
import co.com.claro.mgl.dao.impl.MarcasMglDaoImpl;
import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.mbeans.direcciones.ConsultaHhppBean;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author castrofo
 */
@ManagedBean(name = "fraudesHHPPUnoUno")
@ViewScoped
public class FraudesHHPPUnoUno extends ConsultaHhppBean {

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

    private List<CmtDireccionDetalladaMgl> direccionFraudesList;
    private List<CmtDireccionDetalladaMgl> direccionFraudesListMuestra;
    private HashMap<CmtDireccionDetalladaMgl, List<MarcasMgl>> mapaDireccionesFraudesAdd;
    private HashMap<CmtDireccionDetalladaMgl, List<MarcasMgl>> mapaDireccionesFraudesRemove;
    private static final Logger LOGGER = LogManager.getLogger(FraudesHHPPUnoUno.class);
    private List<MarcasMgl> listaMarcasFraude;
    private static final String CODIGOS_MARCA_FRAUDES = "F,G";

    private String pageActualFra;
    private int actualFra = 1;
    private String numPaginaFra = "1";

    private List<Integer> paginaListFra;
    
    //Opciones agregadas para Rol
    private final String BTNMADIRFRA = "BTNMADIRFRA";  
    private final String BTNDESMADIRFRA = "BTNDESMADIRFRA";  

    private boolean desmarcar;
    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
    
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    @PostConstruct
    @Override
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
    public FraudesHHPPUnoUno() {
        super();
        if (response.isCommitted()) {
            try {
                HttpSession session = (HttpSession) FacesUtil.getExternalContext().getSession(false);
                if (session.getAttribute("tipofraude") == null) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                } else if (session.getAttribute("tipofraude").equals("desmarcar")) {
                    desmarcar = session.getAttribute("tipofraude").equals("desmarcar");
                }

            } catch (IOException ex) {
                FacesUtil.mostrarMensajeError("Error: " + ex.getMessage(), ex, LOGGER);
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

    public void volverTodasMarcasOriginal() {
        for (CmtDireccionDetalladaMgl direccion : this.getDireccionFraudesList()) {
            
        if (mapaDireccionesFraudesAdd.containsKey(direccion)) {
            mapaDireccionesFraudesAdd.remove(direccion);
        }
        if (mapaDireccionesFraudesRemove.containsKey(direccion)) {
            mapaDireccionesFraudesRemove.remove(direccion);
        }
        }
    }

    public void addMarcaColorAll(MarcasMgl marca) throws ApplicationException {
        boolean remove = true;
        String colorMarca = verificarColor(marca);
        int countRemove = 0;
        int countMarcaActiva = 0;
        int countAdd = 0;
        for (CmtDireccionDetalladaMgl dir : direccionFraudesList) {
            
           List<MarcasHhppMgl> listaMarcasHhpp =  marcasHhppMglManager.findMarcasHhppMglidHhpp(dir.getHhppMgl().getHhpId());
                        
            if (listaMarcasHhpp != null && !listaMarcasHhpp.isEmpty()) {
                for (MarcasHhppMgl marcaHHPP : listaMarcasHhpp) {
                    if (marcaHHPP.getMarId().getMarId().equals(marca.getMarId()) && marcaHHPP.getEstadoRegistro() == 1
                            && (!mapaDireccionesFraudesRemove.containsKey(dir)
                            || !mapaDireccionesFraudesRemove.get(dir).contains(marca))) {
                        countMarcaActiva++;
                    }
                }
            }
            
            if (mapaDireccionesFraudesAdd.containsKey(dir)
                    && mapaDireccionesFraudesAdd.get(dir).contains(marca)) {
                countMarcaActiva++;
            }
        }

        boolean removeA = countMarcaActiva >= direccionFraudesList.size();

        direccionFraudesList.forEach((ob) -> {
            addMarca(ob, marca, removeA, colorMarca);
        });
    }

    public void addMarca(CmtDireccionDetalladaMgl direccion, MarcasMgl marca) {
        addMarca(direccion, marca, true, "");
    }

    public void addMarca(CmtDireccionDetalladaMgl direccion, MarcasMgl marca, boolean removeList, String colorMarca) {
        if (direccion.getHhppMgl() == null) {
            return;
        }
        if (!colorMarca.equals("")) {
            if (desmarcar) {
                for (MarcasHhppMgl marcaHHPP : direccion.getHhppMgl().getListMarcasHhpp()) {
                    if (marcaHHPP.getMarId().getMarId().equals(marca.getMarId()) && marcaHHPP.getEstadoRegistro() == 1) {
                        if (!mapaDireccionesFraudesRemove.keySet().contains(direccion)) {
                            mapaDireccionesFraudesRemove.put(direccion, new ArrayList<>());
                        }
                        if (!mapaDireccionesFraudesRemove.get(direccion).contains(marca)) {
                            mapaDireccionesFraudesRemove.get(direccion).add(marca);
                        }
                    }
                }
            } else if (removeList) {
                for (MarcasHhppMgl marcaHHPP : direccion.getHhppMgl().getListMarcasHhpp()) {
                    if (marcaHHPP.getMarId().getMarId().equals(marca.getMarId()) && marcaHHPP.getEstadoRegistro() == 1) {
                        if (!mapaDireccionesFraudesRemove.keySet().contains(direccion)) {
                            mapaDireccionesFraudesRemove.put(direccion, new ArrayList<>());
                        }
                        if (!mapaDireccionesFraudesRemove.get(direccion).contains(marca)) {
                            mapaDireccionesFraudesRemove.get(direccion).add(marca);
                        }
                        return;
                    }
                }
                if (!mapaDireccionesFraudesAdd.keySet().contains(direccion)) {
                    mapaDireccionesFraudesAdd.put(direccion, new ArrayList<>());
                }
                if (mapaDireccionesFraudesAdd.get(direccion).contains(marca)) {
                    mapaDireccionesFraudesAdd.get(direccion).remove(marca);
                }
            } else {
                for (MarcasHhppMgl marcaHHPP : direccion.getHhppMgl().getListMarcasHhpp()) {
                    if (marcaHHPP.getMarId().getMarId().equals(marca.getMarId()) && marcaHHPP.getEstadoRegistro() == 1) {
                        if (!mapaDireccionesFraudesRemove.keySet().contains(direccion)) {
                            mapaDireccionesFraudesRemove.put(direccion, new ArrayList<>());
                        }
                        if (mapaDireccionesFraudesRemove.get(direccion).contains(marca)) {
                            mapaDireccionesFraudesRemove.get(direccion).remove(marca);
                        }
                        return;
                    }
                }
                if (!mapaDireccionesFraudesAdd.keySet().contains(direccion)) {
                    mapaDireccionesFraudesAdd.put(direccion, new ArrayList<>());
                }
                if (!mapaDireccionesFraudesAdd.get(direccion).contains(marca)) {
                    mapaDireccionesFraudesAdd.get(direccion).add(marca);
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
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al Mover Pagina " + ex.getMessage(), ex, LOGGER);
        }
    }
    
    public void moverPaginaAFraudes() {
        try {
            if (this.getDireccionDetalladaList() != null && !this.getDireccionDetalladaList().isEmpty()) {
                for (CmtDireccionDetalladaMgl direccion : this.getDireccionDetalladaList()) {
                    if (direccion.getHhppMgl() != null) {
                        //Se crea lista para consultar las marcas del HHPP y tenerlas al instante
                        List<MarcasHhppMgl> listMarcasHhppMgl = new ArrayList<>();
                        listMarcasHhppMgl = marcasHhppMglManager.findMarcasHhppMglidHhpp(direccion.getHhppMgl().getHhpId());
                       
                        direccion.getHhppMgl().setListMarcasHhpp(listMarcasHhppMgl);
                        
                    }
                }
                direccionFraudesList.addAll(this.getDireccionDetalladaList());
                StringBuilder stb = new StringBuilder();
                direccionFraudesList.removeIf((CmtDireccionDetalladaMgl dir) -> {
                    if (dir.getHhppMgl() == null) {
                        stb.append("|").append(dir.getDireccionTexto()).append(" ");
                        return true;
                    } else {
                        return false;
                    }
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

        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al Mover Pagina " + ex.getMessage(), ex, LOGGER);
        }
    }

    public void borrarListaFraudes() {
        if (direccionFraudesList != null && !direccionFraudesList.isEmpty()) {
            this.getDireccionDetalladaList().addAll(direccionFraudesList);
            this.getDireccionDetalladaBusquedaOriginalList().addAll(direccionFraudesList);
            mapaDireccionesFraudesAdd = new HashMap<>();
            mapaDireccionesFraudesRemove = new HashMap<>();
            direccionFraudesList = new ArrayList<>();
            direccionFraudesListMuestra = new ArrayList<>();
            listInfoByPageFra(1);
            super.listInfoByPage(1, getDireccionDetalladaList());
        } else {
            String msnError = "No hay registros para borrar";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
        }
    }

    public void borrarListaBusqueda() {
        setDireccionDetalladaList(new ArrayList<>());
        this.setDireccionDetalladaBusquedaOriginalList(new ArrayList<>());
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

    public void guardarMarcasHHPP() {
        try {
            HhppMglManager hhppMglManager = new HhppMglManager();
            StringBuilder mensajesError = new StringBuilder();
            if (!desmarcar) {
                mapaDireccionesFraudesAdd.keySet().forEach((cmtDireccionDetalladaMgl) -> {
                    try {
                        if (mapaDireccionesFraudesAdd.get(cmtDireccionDetalladaMgl).isEmpty()) {
                            return;
                        }
                            CmtDefaultBasicResponse responseMarcas = hhppMglManager.agregarMarcasHhpp(
                                cmtDireccionDetalladaMgl.getHhppMgl(), mapaDireccionesFraudesAdd.get(cmtDireccionDetalladaMgl), this.usuarioVT);
                        if (responseMarcas.getMessage() != null && !responseMarcas.getMessage().isEmpty() && responseMarcas.getMessageType().equals("E")) {
                            mensajesError.append(" ").append(responseMarcas.getMessage());
                        }
                    } catch (Exception e) {
                        LOGGER.error("Se produjo error al realizar la actualización  Adicion de marcas en RR " + e.getMessage());
                        FacesUtil.mostrarMensajeError("Error en Guardar Marcas Adicion . " + e.getMessage(), e, LOGGER);
                    }
                });
            }else {
                mapaDireccionesFraudesRemove.keySet().forEach((cmtDireccionDetalladaMgl) -> {
                    try {
                        if (mapaDireccionesFraudesRemove.get(cmtDireccionDetalladaMgl).isEmpty()) {
                            return;
                        }
                        CmtDefaultBasicResponse responseMarcas = hhppMglManager.eliminarMarcasHhpp(
                                cmtDireccionDetalladaMgl.getHhppMgl(), mapaDireccionesFraudesRemove.get(cmtDireccionDetalladaMgl));
                        if (responseMarcas.getMessage() != null && !responseMarcas.getMessage().isEmpty() && responseMarcas.getMessageType().equals("E")) {
                            mensajesError.append(" ").append(responseMarcas.getMessage()).append(" \n");
                        }
                    } catch (Exception e) {
                        LOGGER.error("Se produjo error al realizar la actualización de marcas en RR " + e.getMessage());
                        FacesUtil.mostrarMensajeError("Error en Guardar Marcas Quitar. " + e.getMessage(), e, LOGGER);
                    }
                });
            }
            FacesUtil.mostrarMensaje("Proceso Terminado ");
            if (!mensajesError.toString().isEmpty()) {
                FacesUtil.mostrarMensajeWarn("Errores en el Proceso " + mensajesError.toString());
            }

        } catch (Exception e) {
            LOGGER.error("Se produjo error al realizar la actualización de marcas en RR " + e.getMessage());
            FacesUtil.mostrarMensajeError("Error en Guardar Marcas . " + e.getMessage(), e, LOGGER);
        }
    }

    @Override
    public void buscarHhpp() {
        //si la direccion es construida por el panel de direccion tabulada
        asignarDireccionConstruidaPorTabulada();
        super.buscarHhpp(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void asignarDireccionConstruidaPorTabulada() {
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
                    
                        obj.getHhppMgl().setListMarcasHhpp(listMarcasHhppMgl);
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

    public void addDireccionAFraudes(CmtDireccionDetalladaMgl direccion) throws ApplicationException{
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
    
    // Validar Opciones por Rol    
    public boolean validarOpcionMarcarDirFraudulentas() {
        return validarEdicionRol(BTNMADIRFRA);
    }
    
    public boolean validarOpcionDesmarcarDirFraudulentas() {
        return validarEdicionRol(BTNDESMADIRFRA);
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

}
