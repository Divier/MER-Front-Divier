/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.dao.impl.NodoMglDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaNotasPlantaDto;
import co.com.claro.mgl.dtos.CmtFiltroConsultaPlantaDto;
import co.com.claro.mgl.dtos.HhppByNodoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.NotasPlantaMglFacade;
import co.com.claro.mgl.facade.NotasPlantaMglFacadeLocal;
import co.com.claro.mgl.facade.PlantaMglFacade;
import co.com.claro.mgl.facade.PlantaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.PlantaMgl;
import co.com.claro.mgl.jpa.entities.PlantaMglNotas;
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
@ManagedBean(name = "plantaMglBean")
@ViewScoped
public class PlantaMglBean {

    @EJB
    private PlantaMglFacadeLocal plantalMglFacade = new PlantaMglFacade();

    @EJB
    private NotasPlantaMglFacadeLocal notasPlantalMglFacade = new NotasPlantaMglFacade();
    
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;

    //Atributos usados 
    private String message;
    private HtmlDataTable dataTable = new HtmlDataTable();
    private List<PlantaMgl> plantaMglList;
    private PlantaMgl plantaMgl = null;
    private SecurityLogin securityLogin;
    
    private PlantaMgl plantaMglNuevo = null;
    private CmtFiltroConsultaPlantaDto cmtFiltroConsultaPlantaDto = new CmtFiltroConsultaPlantaDto();

    private String nota;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.
            getExternalContext().getSession(false);

    private static final Logger LOGGER = LogManager.getLogger(PlantaMglBean.class);

    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private String usuarioID = null;

    private String idSqlSelected;
    private int actual;                            //para saber la pagina actual
    private static final int numeroRegistrosConsulta = 15;
    private List<Integer> paginaList;
    private String pageActual;
    private String numPagina;
    private boolean guardado= false;
    
    //CONSTANTES
    private final String VALIDAROPCIONPLANTAIDROL = "VALIDAROPCIONPLANTAIDROL";
    private final String VALIDAROPCCREARPLANTAROL = "VALIDAROPCCREARPLANTAROL";

    //atributos usados en la vista individual para hsitorico notas
    // de la nota a crear o modificar
    private HtmlDataTable notasDataTable = new HtmlDataTable();
    private List<PlantaMglNotas> notasPlantaMglList;
    private CmtFiltroConsultaNotasPlantaDto cmtFiltroConsultaNotasPlantaDto
            = new CmtFiltroConsultaNotasPlantaDto();
    private List<Integer> notasPaginaList;
    private String notasNumPagina;
    
    private PlantaMglNotas plantaMglNotas = null;
    private int actualNota;
    private String pageActualNota;
    private String numPaginaNota;
    private static final int numeroRegistrosConsultaNota = 5;
    private BigDecimal idPlantaSelect;
    private String paginaTipo = "HE";
    private boolean mostrarInfoPlanta = false; 
    private boolean mostrarTablaNodos = true;
    
    //variable datatable de home pass pertenecientes al nodo ND
    private int numRegPaginacionHomePass = 20;
    private HtmlDataTable dataTableHhppByNodoDto = new HtmlDataTable();
    private List<HhppByNodoDto> listaHhppByNodoDto;//lista con todo el contenido de las paginas
    private List<HhppByNodoDto> listaHhppByNodoDtoPaginado;
    private HhppByNodoDto HhppByNodoDto = null;
    private List<Integer> nodoxhhppPaginaList;
    private Integer nodoxhhppNumPagina;
    /**
     *  campos para bloqueo de entrada de datos para la descripcion de la planta cuando el tipo 
     * de planta es ND el cual tiene que cargar la descripcion automaticamente.
     */
    private boolean bloqCampDescripCreacion = false;
    private boolean bloqCampDescripActualiza = false;
    //Metodos que se usan en este bean

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public PlantaMglBean() {
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
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en PlantaMglBean() class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en PlantaMglBean(). " + e.getMessage(), e, LOGGER);
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Intercambio de vistas">
    /**
     * Jonathan Peña Primer metodo que se ejecuta al abrir cualquera de las
     * vistas de regionales, carga los datos necesarios para la tabla, o para el
     * individual
     */
    @PostConstruct
    public void fillSqlList() {
        try {
            
            navegacion();
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
    }
    
    private boolean activarbotmodifielimi = true;// indicador botones de modificacion eliminacion true esta deshabilitado
    
    public void activarBotModifiElimi (){
        if (activarbotmodifielimi){
            activarbotmodifielimi = false;
        }else{
            activarbotmodifielimi = true;
        }
        return ;
    }
    
    public void regresarPadre(){
        plantaMgl = plantaMgl.getConfiguracionplantaparentid();//le asigno el padre para regresar
        plantaMglNuevo = null;
        navegacion();
    }
    public void navegacion(){
        try {
            bloqCampDescripCreacion = false;
            bloqCampDescripActualiza = false;
            mostrarTablaNodos = true;
            mostrarInfoPlanta = true;
        if (plantaMglNuevo == null){
                plantaMglNuevo = new PlantaMgl();
            }
        if(plantaMgl!=null){
                cmtFiltroConsultaPlantaDto.setPadrePlanta(plantaMgl);
            plantaMglNuevo.setConfiguracionplantaparentid( plantaMgl );
            if(plantaMgl.getLocationtype().compareToIgnoreCase("HE")==0){
                    cmtFiltroConsultaPlantaDto.setLocationType("CT");
            }else if(plantaMgl.getLocationtype().compareToIgnoreCase("CT")==0){
                    cmtFiltroConsultaPlantaDto.setLocationType("ND");
                    bloqCampDescripCreacion = true;
            }else if(plantaMgl.getLocationtype().compareToIgnoreCase("ND")==0){
                    bloqCampDescripActualiza = true;
                    mostrarTablaNodos = false;
                    listaHhppByNodoDto = new ArrayList<HhppByNodoDto>();
                    listaHhppByNodoDtoPaginado = new ArrayList<HhppByNodoDto>();
                    NodoMglManager nm = new NodoMglManager();
                    HhppMglManager hpm = new HhppMglManager();
                    GeograficoPoliticoManager gpm = new GeograficoPoliticoManager();
                    CmtDireccionDetalleMglManager ddm = new CmtDireccionDetalleMglManager();
                try{
                        NodoMgl nodoConsulta = nm.findByCodigo(plantaMgl.getLocationcode());//obtiene nodo
                        GeograficoPoliticoMgl geoPolCenPobl = gpm.findById(nodoConsulta.getGpoId());
                        GeograficoPoliticoMgl geoPolCiudad = gpm.findById(geoPolCenPobl.getGeoGpoId());
                        GeograficoPoliticoMgl geoPolDeparta = gpm.findById(geoPolCiudad.getGeoGpoId());
                        List<HhppMgl> listaHhpp = hpm.findByNodoMgl(nodoConsulta);//obtiene homepass
                        listaHhppByNodoDto.clear();
                    for (HhppMgl tempHhpp: listaHhpp){
                            HhppByNodoDto tempHhppByNodoDto = new HhppByNodoDto();
                            tempHhppByNodoDto.setNodoConsulta(nodoConsulta);
                            tempHhppByNodoDto.setHomepassConsulta(tempHhpp);
                        try{
                            tempHhppByNodoDto.setDirDetallaConsulta(ddm.findByHhPP(tempHhpp));
                        } catch (ApplicationException exDirDetallada) {
                            LOGGER.error("Error:PlantaMglBean:navegacion: obteniendo "
                                    + "direccion detallada; dirId: " + tempHhpp.getDirId());
                        }
                            tempHhppByNodoDto.setGeoPolCPobConsulta(geoPolCenPobl);
                            tempHhppByNodoDto.setGeoPolCiuConsulta(geoPolCiudad);
                            tempHhppByNodoDto.setGeoPolDeparConsulta(geoPolDeparta);
                            listaHhppByNodoDto.add(tempHhppByNodoDto);
                        }
                        int temporalTamaño = (listaHhppByNodoDto.size() / numRegPaginacionHomePass) + 1;
                        nodoxhhpppaginaactual = 0;
                        nodoxhhppPaginaList = new ArrayList<Integer>();
                        for (int i = 0; i < temporalTamaño; i++) {
                            nodoxhhppPaginaList.add(Integer.valueOf(i + 1));
                        }
                        nodoXhhppPageFirst();
                    } catch (ApplicationException nodoexception) {
                        LOGGER.error("Error:PlantaMglBean:navegacion: busqueda "
                                + "del nodo y ubicacion geo-politica. " + nodoexception);
                    }
                }
            } else {
                cmtFiltroConsultaPlantaDto.setPadrePlanta(null);
                cmtFiltroConsultaPlantaDto.setLocationType("HE");
                mostrarInfoPlanta = false;
                plantaMglNuevo.setConfiguracionplantaparentid(null);
            }
            plantaMglNuevo.setLocationtype(cmtFiltroConsultaPlantaDto.getLocationType());
            listInfoByPage(1);
            activarbotmodifielimi = true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en navegacion(). " + e.getMessage(), e, LOGGER);
        }
    }
    private int nodoxhhpppaginaactual;
    
    public void nodoXhhppPageFirst(){
        nodoxhhpppaginaactual = 1;
        cargarListaHhppByNodoDtoPaginado(nodoxhhpppaginaactual);
    }
    public void nodoXhhppPagePrevious(){
        if (nodoxhhpppaginaactual > 1){
            nodoxhhpppaginaactual--;
        }
        cargarListaHhppByNodoDtoPaginado(nodoxhhpppaginaactual);
    }
    public void nodoXhhppPageNext(){
        int maximaPagina = (listaHhppByNodoDto.size() / numRegPaginacionHomePass) + 1;
        if (nodoxhhpppaginaactual < maximaPagina){
            nodoxhhpppaginaactual++;
        }
        cargarListaHhppByNodoDtoPaginado(nodoxhhpppaginaactual);
    }
    public void nodoXhhppPageLast(){
        nodoxhhpppaginaactual = (listaHhppByNodoDto.size() / numRegPaginacionHomePass) + 1;
        cargarListaHhppByNodoDtoPaginado(nodoxhhpppaginaactual);
    }
    public void nodoxhhppIrPagina(){
        cargarListaHhppByNodoDtoPaginado(nodoxhhpppaginaactual);
    }
    /**
     *  va desde la pagina cero en adelante 
     * @param pagina
     **/
    public void cargarListaHhppByNodoDtoPaginado(int pagina){//desde pagina cero 
        pagina--;
        int numeroPaginasMaximo = (listaHhppByNodoDto.size() / numRegPaginacionHomePass);
        if (pagina > numeroPaginasMaximo){//evita salirse del tamaño de la paginacion
            pagina = numeroPaginasMaximo;
        }
        listaHhppByNodoDtoPaginado.clear();//limpio lista actual
        int desdeElemento = ( pagina * numRegPaginacionHomePass );
        if (desdeElemento > listaHhppByNodoDto.size()){
            desdeElemento = listaHhppByNodoDto.size() - numRegPaginacionHomePass;//asigna ultimos elementos
        }
        for (int i = 0;  i  < numRegPaginacionHomePass && (desdeElemento + i) < listaHhppByNodoDto.size() ; i++){
            listaHhppByNodoDtoPaginado.add( listaHhppByNodoDto.get( desdeElemento + i ) );
        }
    }
    /**
     * Diego Espinosa Acosta metodo para ir a la planta que selecionan en el datatable
     *
     */
    public void goPlantaSeleccionada() {
        try {
            plantaMgl = (PlantaMgl) dataTable.getRowData();
            idPlantaSelect = plantaMgl.getConfiguracionplantaid(); 
            plantaMglNuevo = null;
            navegacion();
        } catch (Exception e) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + " : " + e.getMessage();
            LOGGER.error(msgError, e);
        }
    }
    public void validaExistenciaNodoPlanta(){
        try {
            if (plantaMglNuevo.getLocationtype().trim().compareToIgnoreCase("ND") == 0) {
                NodoMgl resulBuscarNodo = null;
                String mensajeValidacion = null;
                NodoMglDaoImpl adminNodo = new NodoMglDaoImpl();
                try {
                    resulBuscarNodo = adminNodo.findByCodigo(plantaMglNuevo.getLocationcode().toUpperCase().trim());
                } catch (ApplicationException exnodo) {
                    mensajeValidacion = "Codigo nodo no encontrado.";
                    FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajeValidacion, ""));
                    LOGGER.error("Error:PlantaMglBean:validaExistenciaNodoPlanta: " + exnodo.getMessage());
                }
                if (resulBuscarNodo != null) {
                    plantaMglNuevo.setDescription(resulBuscarNodo.getNodNombre());
                } else {
                    mensajeValidacion = "Codigo nodo no encontrado.";
                    FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            mensajeValidacion, ""));
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }
    
    public boolean validarPlantaMgl( PlantaMgl entraPlanta ){
         boolean estado = true;
        try{
           
            String mensajeValidacion = null;
            if (entraPlanta.getLocationcode() == null || entraPlanta.getLocationcode().isEmpty()) {
                mensajeValidacion = "Campo codigo de ubicacion es obligatorio.";
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajeValidacion, ""));
                estado = false;
            } else {
                if (entraPlanta.getLocationtype().trim().compareToIgnoreCase("ND") == 0) {
                    NodoMglDaoImpl adminNodo = new NodoMglDaoImpl();
                    NodoMgl resulBuscarNodo = null;
                    try {
                        resulBuscarNodo = adminNodo.findByCodigo(entraPlanta.getLocationcode().toUpperCase().trim());
                    } catch (ApplicationException exnodo) {
                        resulBuscarNodo = null;
                    }
                    if (resulBuscarNodo == null) {
                        mensajeValidacion = "Codigo nodo no encontrado.";
                        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensajeValidacion, ""));
                        estado = false;
                    } else {
                        entraPlanta.setLocationcode(entraPlanta.getLocationcode().toUpperCase().trim());
                    }
                }
            }
            if (entraPlanta.getDescription() == null || entraPlanta.getDescription().isEmpty()) {
                mensajeValidacion = "Campo descripcion es obligatorio.";
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajeValidacion, ""));
                estado = false;
            }
            if (entraPlanta.getHour24() == null || entraPlanta.getHour24().compareTo(BigDecimal.ZERO) == 0) {
                mensajeValidacion = "Campo 24 horas es obligatorio.";
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajeValidacion, ""));
                estado = false;
            }
            if (entraPlanta.getHour48() == null || entraPlanta.getHour48().compareTo(BigDecimal.ZERO) == 0) {
                mensajeValidacion = "Campo 48 horas es obligatorio.";
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajeValidacion, ""));
                estado = false;
            }
            if (entraPlanta.getWeek() == null || entraPlanta.getWeek().compareTo(BigDecimal.ZERO) == 0) {
                mensajeValidacion = "Campo semana es obligatorio.";
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajeValidacion, ""));
                estado = false;
            }
            if (entraPlanta.getMonth() == null || entraPlanta.getMonth().compareTo(BigDecimal.ZERO) == 0) {
                mensajeValidacion = "Campo mes es obligatorio.";
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajeValidacion, ""));
                estado = false;
            }
            if (entraPlanta.getYear() == null || entraPlanta.getYear().compareTo(BigDecimal.ZERO) == 0) {
                mensajeValidacion = "Campo año es obligatorio.";
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensajeValidacion, ""));
                estado = false;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
        return estado;// si la validacion es correcta
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="crearPlanta">
    /**
     * Jonathan Peña Metodo encargado de crear el regional es el que llama a la
     * fachada de regionales para seguir con la insercion
     *
     */
    public void crearPlantaMgl() {
        try {
            if ( validarPlantaMgl(plantaMglNuevo) ){
                String padreTipo = (plantaMgl!=null) ? plantaMgl.getLocationtype() : null ;
                String padreCodigo = (plantaMgl!=null) ? plantaMgl.getLocationcode() : null ;
                PlantaMgl plantaMglExiste = plantalMglFacade.findByTypeAndCode(
                    plantaMglNuevo.getLocationtype(), plantaMglNuevo.getLocationcode(),
                    padreTipo, padreCodigo );
                if (plantaMglExiste==null){
                    PlantaMgl plantaMglCrear = plantaMglNuevo.clone();
                    plantaMglCrear.setConfiguracionplantaid(null);
                    plantaMglCrear.setEstadoRegistro(1);
                    plantaMglCrear.setUsuarioCreacion(usuarioVT);
                    plantaMglCrear.setFechaCreacion(new Date());
                    plantaMglCrear = plantalMglFacade.create(plantaMglCrear);
                    message = "Proceso exitoso: se ha creado la planta  <b>"+
                            plantaMglCrear.getLocationtype() + ":"
                            + plantaMglCrear.getLocationcode() + " ("
                            + plantaMglCrear.getDescription() + ") </b>  "
                            + "satisfactoriamente";
                    FacesContext.getCurrentInstance().addMessage(
                            null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
                    if (plantaMglCrear.getLocationtype().trim().compareToIgnoreCase("ND")==0){
                        NodoMglDaoImpl adminNodo = new NodoMglDaoImpl();
                        NodoMgl resulBuscarNodo = null;
                        try {
                            resulBuscarNodo = adminNodo.findByCodigo(plantaMglNuevo.getLocationcode().toUpperCase().trim());
                            resulBuscarNodo.setNodHeadEnd(plantaMglCrear.getConfiguracionplantaparentid().getConfiguracionplantaparentid().getLocationcode());
                            adminNodo.update(resulBuscarNodo);
                        } catch (ApplicationException exnodo) {
                            LOGGER.error(" Clase: PlantaMglBean; Metodo: crearPlantaMgl;"
                                    + " Descripcion: Creando la planta "
                                    + plantaMglCrear.getLocationtype()+":"
                                    + plantaMglCrear.getLocationcode()
                                    + " Mensaje: "+ exnodo.getMessage());
                        }
                    }
                    plantaMglNuevo = new PlantaMgl();//limpiar los campos
                    plantaMglNuevo.setLocationtype(plantaMglCrear.getLocationtype());
                    plantaMglNuevo.setConfiguracionplantaparentid(plantaMgl);
                }else{
                    message = "Proceso fallo: Ya Existe la planta <b> " + plantaMglNuevo.getLocationtype() +":"+
                            plantaMglNuevo.getLocationcode()+ "</b>";
                    FacesContext.getCurrentInstance().addMessage( null, 
                            new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
                }
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            message = "Proceso falló 2 : " + ex;
        } catch (CloneNotSupportedException exclone){
            FacesUtil.mostrarMensajeError("Error al copiar datos planta: " + exclone.getMessage(), exclone);
        } catch ( Exception exgeneral ){
            FacesUtil.mostrarMensajeError("Error general Creacion planta: " + exgeneral.getMessage(), exgeneral);
        }
        navegacion();
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ActualizarPlanta">
    /**
     * Jonathan Peña Metodo encargado de actualizar el regional es el que llama
     * a la fachada de regionales para seguir con la actualizacion
     *
     */
    public void actualizarPlantaMgl() {
        try {
            if ( validarPlantaMgl(plantaMgl)){
                plantaMgl.setUsuarioModificacion(usuarioVT);
                plantaMgl.setFechaModificacion(new Date());
                plantaMgl =  plantalMglFacade.update(plantaMgl);
                if (plantaMgl != null){
                    setGuardado(true);
                    message = "Proceso exitoso: se Actualizo la planta  <b>"+ 
                            plantaMgl.getLocationtype() + ":"
                            + plantaMgl.getLocationcode() + " ("
                            + plantaMgl.getDescription() + ") </b> satisfactoriamente. ";
                    FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en PlantaMglBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="eliminarPlanta">
    /**
     * Jonathan Peña Metodo encargado de eliminar el regional es el que llama a
     * la fachada de regionales para seguir con la eliminacion
     *
     */
    public void eliminarPlantaMgl() {
        try {
            boolean eliminar = false;
            if ( plantaMgl.getLocationtype().trim().compareToIgnoreCase("ND") != 0 ){
                if (plantaMglList.size() == 0){
                    eliminar = true;
                }else{
                    message = "Eliminacion no es posible mientras tenga nodos hijos; planta "+
                        plantaMgl.getLocationtype() + ":"
                            + plantaMgl.getLocationcode() + " ("
                            + plantaMgl.getDescription() + ")";
                    FacesContext.getCurrentInstance().addMessage(
                            null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
                }
            }else{
                eliminar = true;
            }
            if (eliminar){
                PlantaMgl temporalPadre = plantaMgl.getConfiguracionplantaparentid();
                if (plantalMglFacade.delete(plantaMgl)){
                    message = "Proceso exitoso: Se ha eliminado la planta:  <b>"+
                            plantaMgl.getLocationtype() + ":"+ plantaMgl.getLocationcode() + " ("+ 
                            plantaMgl.getDescription() + ") </b> satisfatoriamente. ";
                    FacesContext.getCurrentInstance().addMessage( null, 
                            new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
                    plantaMglNuevo = null;
                    plantaMgl = temporalPadre;//le asigno el padre para regresar pagina anterior
                }else{
                    message = "Proceso fallido: la planta:  <b>"+
                            plantaMgl.getLocationtype() + ":"+ plantaMgl.getLocationcode() + " ("+ 
                            plantaMgl.getDescription() + " )</b> consultar el log "
                            + "del sistema; para mayor informacion. ";
                    FacesContext.getCurrentInstance().addMessage( null, 
                            new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
                }
                navegacion();
            }
        } catch (ApplicationException e) {
            message = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }

    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Paginacion Plantas">
    public String listInfoByPage(int page) {
        try {
            PaginacionDto<PlantaMgl> paginacionDto
                    = plantalMglFacade.findAllPaginado(page, numeroRegistrosConsulta,
                            cmtFiltroConsultaPlantaDto);
            plantaMglList = paginacionDto.getListResultado();
            actual = page;
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void findByFiltro() {
        pageFirst();
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

    public int getTotalPaginas() {
        try {
            PaginacionDto<PlantaMgl> paginacionDto
                    = plantalMglFacade.findAllPaginado(0, numeroRegistrosConsulta,
                            cmtFiltroConsultaPlantaDto);
            Long count = paginacionDto.getNumPaginas();
            int totalPaginas = (int) ((count % numeroRegistrosConsulta != 0)
                    ? (count / numeroRegistrosConsulta) + 1
                    : count / numeroRegistrosConsulta);
            return totalPaginas;
        } catch (ApplicationException e) {
            String msn = "Se generea al cargar lista de Plantas:";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Crear Nota">
    
    public void crearNotaPlanta(PlantaMgl plantaMgl){
        try{
            if (!nota.isEmpty()) {
                PlantaMglNotas plantaMglNotas = new PlantaMglNotas();
                plantaMglNotas.setNote(nota);
                plantaMglNotas.setConfiguracionplantaid(plantaMgl);
                plantaMglNotas.setEstadoRegistro(1);
                notasPlantalMglFacade.create(plantaMglNotas);
            }
            
        } catch (ApplicationException e) {
            message = "Proceso falló  : ";
            FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
        
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Paginacion Notas Planta">
    public String notasListInfoByPage(int page) {
        try {
            PaginacionDto<PlantaMglNotas> paginacionDto
                    = notasPlantalMglFacade.findAllPaginado(page, numeroRegistrosConsultaNota,
                            cmtFiltroConsultaNotasPlantaDto,
                            plantaMgl.getConfiguracionplantaid());
            notasPlantaMglList = paginacionDto.getListResultado();
            actualNota = page;
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void notasFindByFiltro() {
        notasPageFirst();
    }

    public void notasPageFirst() {
        notasListInfoByPage(1);
    }

    public void pagePreviousNotas() {
        int totalPaginas = getTotalPaginas();
        int nuevaPageActual = actualNota - 1;
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        if (nuevaPageActual <= 0) {
            nuevaPageActual = 1;
        }
        notasListInfoByPage(nuevaPageActual);
    }

    public int notasGetTotalPaginas() {
        try {
            PaginacionDto<PlantaMglNotas> paginacionDto
                    = notasPlantalMglFacade.findAllPaginado(0, numeroRegistrosConsultaNota,
                            cmtFiltroConsultaNotasPlantaDto,
                            plantaMgl.getConfiguracionplantaid());
            Long count = paginacionDto.getNumPaginas();
            int totalPaginas = (int) ((count % numeroRegistrosConsultaNota != 0)
                    ? (count / numeroRegistrosConsultaNota) + 1
                    : count / numeroRegistrosConsultaNota);
            return totalPaginas;
        } catch (ApplicationException e) {
            String msn = "Se generea al cargar lista de Notas de planta:";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public String getPageActualNotas() {
        try {
            notasPaginaList = new ArrayList<Integer>();
            int totalPaginas = notasGetTotalPaginas();
            for (int i = 1; i <= totalPaginas; i++) {
                notasPaginaList.add(i);
            }
            pageActualNota = String.valueOf(actualNota) + " de "
                    + String.valueOf(totalPaginas);

            if (numPaginaNota == null) {
                numPaginaNota = "1";
            }
            numPaginaNota = String.valueOf(actualNota);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
        return pageActualNota;
    }

    public void notasIrPagina() {
        try {
            int totalPaginas = notasGetTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPaginaNota);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            notasListInfoByPage(nuevaPageActual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

    public void notasPageNext() {
        try {
            int totalPaginas = notasGetTotalPaginas();
            int nuevaPageActual = actualNota + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            notasListInfoByPage(nuevaPageActual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

    public void notasPageLast() {
        try {
            int totalPaginas = notasGetTotalPaginas();
            notasListInfoByPage(totalPaginas);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PlantaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

//</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="getters and setters">
    public String nuevoPlantaMgl() {
        plantaMgl = null;
        plantaMgl = new PlantaMgl();
        plantaMgl.setConfiguracionplantaid(BigDecimal.ZERO);
        setGuardado(true);
        return null;
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

    public String getIdSqlSelected() {
        return idSqlSelected;
    }

    public void setIdSqlSelected(String idSqlSelected) {
        this.idSqlSelected = idSqlSelected;
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

    public List<HhppByNodoDto> getListaHhppByNodoDto() {
        return listaHhppByNodoDto;
    }

    public void setListaHhppByNodoDto(List<HhppByNodoDto> listaHhppByNodoDto) {
        this.listaHhppByNodoDto = listaHhppByNodoDto;
    }

    public HhppByNodoDto getHhppByNodoDto() {
        return HhppByNodoDto;
    }

    public void setHhppByNodoDto(HhppByNodoDto HhppByNodoDto) {
        this.HhppByNodoDto = HhppByNodoDto;
    }
    
    
    
    public List<PlantaMgl> getPlantaMglList() {
        return plantaMglList;
    }

    public void setPlantaMglList(List<PlantaMgl> plantaMglList) {
        this.plantaMglList = plantaMglList;
    }
    
    public PlantaMgl getPlantaMgl() {
        return plantaMgl;
    }

    public void setPlantaMgl(PlantaMgl PlantaMgl) {
        this.plantaMgl = PlantaMgl;
    }

    public CmtFiltroConsultaPlantaDto getCmtFiltroConsultaPlantaDto() {
        return cmtFiltroConsultaPlantaDto;
    }

    public void setCmtFiltroConsultaPlantaDto(CmtFiltroConsultaPlantaDto cmtFiltroConsultaPlantaDto) {
        this.cmtFiltroConsultaPlantaDto = cmtFiltroConsultaPlantaDto;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String Nota) {
        this.nota = Nota;
    }

    public PlantaMglFacadeLocal getPlantalMglFacade() {
        return plantalMglFacade;
    }

    public void setPlantalMglFacade(PlantaMglFacadeLocal plantalMglFacade) {
        this.plantalMglFacade = plantalMglFacade;
    }





    public HtmlDataTable getNotasDataTable() {
        return notasDataTable;
    }

    public void setNotasDataTable(HtmlDataTable notasDataTable) {
        this.notasDataTable = notasDataTable;
    }

    public List<PlantaMglNotas> getNotasPlantaMglList() {
        return notasPlantaMglList;
    }

    public void setNotasPlantaMglList(List<PlantaMglNotas> notasPlantaMglList) {
        this.notasPlantaMglList = notasPlantaMglList;
    }

    public CmtFiltroConsultaNotasPlantaDto getCmtFiltroConsultaNotasPlantaDto() {
        return cmtFiltroConsultaNotasPlantaDto;
    }

    public void setCmtFiltroConsultaNotasPlantaDto(CmtFiltroConsultaNotasPlantaDto cmtFiltroConsultaNotasPlantaDto) {
        this.cmtFiltroConsultaNotasPlantaDto = cmtFiltroConsultaNotasPlantaDto;
    }

    public List<Integer> getNotasPaginaList() {
        return notasPaginaList;
    }

    public void setNotasPaginaList(List<Integer> notasPaginaList) {
        this.notasPaginaList = notasPaginaList;
    }

    public String getNotasNumPagina() {
        return notasNumPagina;
    }

    public void setNotasNumPagina(String notasNumPagina) {
        this.notasNumPagina = notasNumPagina;
    }

    public PlantaMglNotas getPlantaMglNotas() {
        return plantaMglNotas;
    }

    public void setPlantaMglNotas(PlantaMglNotas plantaMglNotas) {
        this.plantaMglNotas = plantaMglNotas;
    }
    
//</editor-fold>
    
    public boolean validarPlantaIdRol() {
        return validarEdicion(VALIDAROPCIONPLANTAIDROL);
    }
    
    public boolean validarCrearRol() {
        return validarEdicion(VALIDAROPCCREARPLANTAROL);
    }
    
    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public BigDecimal getIdPlantaSelect() {
        return idPlantaSelect;
    }

    public void setIdPlantaSelect(BigDecimal idPlantaSelect) {
        this.idPlantaSelect = idPlantaSelect;
    }

    public String getPaginaTipo() {
        return paginaTipo;
    }

    public void setPaginaTipo(String paginaTipo) {
        this.paginaTipo = paginaTipo;
    }

    public boolean isMostrarInfoPlanta() {
        return mostrarInfoPlanta;
    }

    public void setMostrarInfoPlanta(boolean mostrarInfoPlanta) {
        this.mostrarInfoPlanta = mostrarInfoPlanta;
    }

    public PlantaMgl getPlantaMglNuevo() {
        return plantaMglNuevo;
    }

    public void setPlantaMglNuevo(PlantaMgl plantaMglNuevo) {
        this.plantaMglNuevo = plantaMglNuevo;
    }

    public boolean isActivarbotmodifielimi() {
        return activarbotmodifielimi;
    }

    public void setActivarbotmodifielimi(boolean activarbotmodifielimi) {
        this.activarbotmodifielimi = activarbotmodifielimi;
    }

    public boolean isMostrarTablaNodos() {
        return mostrarTablaNodos;
    }

    public void setMostrarTablaNodos(boolean mostrarTablaNodos) {
        this.mostrarTablaNodos = mostrarTablaNodos;
    }

    public HtmlDataTable getDataTableHhppByNodoDto() {
        return dataTableHhppByNodoDto;
    }

    public void setDataTableHhppByNodoDto(HtmlDataTable dataTableHhppByNodoDto) {
        this.dataTableHhppByNodoDto = dataTableHhppByNodoDto;
    }

    public int getNumRegPaginacionHomePass() {
        return numRegPaginacionHomePass;
    }

    public void setNumRegPaginacionHomePass(int numRegPaginacionHomePass) {
        this.numRegPaginacionHomePass = numRegPaginacionHomePass;
    }

    public List<HhppByNodoDto> getListaHhppByNodoDtoPaginado() {
        return listaHhppByNodoDtoPaginado;
    }

    public void setListaHhppByNodoDtoPaginado(List<HhppByNodoDto> listaHhppByNodoDtoPaginado) {
        this.listaHhppByNodoDtoPaginado = listaHhppByNodoDtoPaginado;
    }

    public int getNodoxhhpppaginaactual() {
        return nodoxhhpppaginaactual;
    }

    public void setNodoxhhpppaginaactual(int nodoxhhpppaginaactual) {
        this.nodoxhhpppaginaactual = nodoxhhpppaginaactual;
    }

    public List<Integer> getNodoxhhppPaginaList() {
        return nodoxhhppPaginaList;
    }

    public void setNodoxhhppPaginaList(List<Integer> nodoxhhppPaginaList) {
        this.nodoxhhppPaginaList = nodoxhhppPaginaList;
    }

    public Integer getNodoxhhppNumPagina() {
        return nodoxhhppNumPagina;
    }

    public void setNodoxhhppNumPagina(Integer nodoxhhppNumPagina) {
        this.nodoxhhppNumPagina = nodoxhhppNumPagina;
    }

    public boolean isBloqCampDescripCreacion() {
        return bloqCampDescripCreacion;
    }

    public void setBloqCampDescripCreacion(boolean bloqCampDescripCreacion) {
        this.bloqCampDescripCreacion = bloqCampDescripCreacion;
    }

    public boolean isBloqCampDescripActualiza() {
        return bloqCampDescripActualiza;
    }

    public void setBloqCampDescripActualiza(boolean bloqCampDescripActualiza) {
        this.bloqCampDescripActualiza = bloqCampDescripActualiza;
    }
    
    
}
