
package co.com.claro.direccion.mbeans.vetoNodos;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.RrCiudadesFacadeLocal;
import co.com.claro.mgl.facade.RrRegionalesFacadeLocal;
import co.com.claro.mgl.facade.VetoCanalMglFacadeLocal;
import co.com.claro.mgl.facade.VetoCiudadMglFacadeLocal;
import co.com.claro.mgl.facade.VetoMglFacadeLocal;
import co.com.claro.mgl.facade.VetoNodosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuariosPortalFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.VetoCanalMgl;
import co.com.claro.mgl.jpa.entities.VetoCiudadMgl;
import co.com.claro.mgl.jpa.entities.VetoMgl;
import co.com.claro.mgl.jpa.entities.VetoNodosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
 * @author Juan David Hernandez
 */
@ViewScoped
@ManagedBean(name = "vetoNodosBean")
public class VetoNodosBean implements Serializable {

    private String usuarioVT = null;
    private SecurityLogin securityLogin;
    private Date fechaInicio=null;
    private Date fechaFin=null;
    private int perfilVt = 0;
    private BigDecimal centroPoblado;
    private boolean canalAllSelected;
    private boolean nodosAllSelected;
    private boolean ciudadAllSelected;   
    private boolean venta;
    private boolean creacion;
    private List<CmtBasicaMgl> tipoTecnologiaList;
    private List<CmtRegionalRr> divisionList;
    private List<RrCiudades> comunidadList;
    private List<GeograficoPoliticoMgl> departamentoList;
    private List<GeograficoPoliticoMgl> ciudadesList;
    private List<GeograficoPoliticoMgl> ciudadList;
    private List<GeograficoPoliticoMgl> ciudadPaginadaList;
    private List<GeograficoPoliticoMgl> ciudadesVetoList;
    private List<GeograficoPoliticoMgl> centroPobladoList;
    private List<ParametrosCalles> canalList;
    private List<ParametrosCalles> canalVetoList;
    private List<ParametrosCalles> canalVetoPaginadaList; 
    private List<NodoMgl> nodoList;
    private List<NodoMgl> nodoVetoList;   
    private List<NodoMgl> nodoVetoPaginadaList;  
    private List<Integer> paginaListCanal;
    private List<Integer> paginaListCiudad;
    private List<Integer> paginaListVetoNodo;
    private List<Integer> paginaListVetoCiudad;
    private List<Integer> paginaListVetoCanal;
    private List<Integer> paginaListNodo;
    private List<VetoMgl> vetoList;
    private List<Integer> paginaListVeto;
    private String nodoBuscado;
    private String vetoBuscado;
    private String politica;
    private String tipoCargue;
    private String tipoTecnologia;
    private String division;
    private String departamento;
    private String ciudad; 
    private String comunidad;
    private String inicioPagina = "<< -";
    private String anteriorPagina ="< -";
    private String finPagina ="- >>";
    private String siguientePagina ="- >";
    private String correoElectronico;
    private String pageActualNodo;
    private String numPaginaNodo = "1";   
    private String pageActualCiudad;
    private String numPaginaCiudad = "1"; 
    private String numPaginaVeto = "1"; 
    private String pageActualCanal;
    private String numPaginaCanal = "1";
    private String pageActualVetoNodo;
    private String numPaginaVetoNodo = "1"; 
    private String pageActualVetoCiudad;
    private String pageActualVeto;
    private String numPaginaVetoCiudad = "1";
    private String pageActualVetoCanal;
    private String numPaginaVetoCanal = "1"; 
    private int actualVetoNodo;
    private int actualVetoCiudad;
    private int actualNodo;
    private int actualCiudad;
    private int actualVeto;
    private int actualCanal; 
    private int actualVetoCanal;    
    private int filasPag = ConstantsCM.PAGINACION_QUINCE_FILAS;
    private String regresarMenu = "<- Regresar Menú";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) 
            facesContext.getExternalContext().getResponse();
    private HttpSession session = (HttpSession) 
            facesContext.getExternalContext().getSession(false);
    private static final Logger LOGGER = LogManager.getLogger(VetoNodosBean.class);
    
    //Opciones agregadas para Rol
    private final String BTVTNOCRPOVET = "BTVTNOCRPOVET";
    private final String BTVTNOEDDET = "BTVTNOEDDET";
    
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private RrRegionalesFacadeLocal rrRegionalesFacadeLocal;
    @EJB
    private RrCiudadesFacadeLocal rrCiudadesFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacade;
    @EJB
    private VetoMglFacadeLocal vetoMglFacadeLocal;
    @EJB
    private VetoNodosMglFacadeLocal vetoNodosMglFacadeLocal;
    @EJB
    private VetoCiudadMglFacadeLocal vetoCiudadMglFacadeLocal;
    @EJB
    private VetoCanalMglFacadeLocal vetoCanalMglFacadeLocal;
    @EJB
    private UsuariosPortalFacadeLocal usuariosPortalFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
   

    public VetoNodosBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect
                     (securityLogin.redireccionarLogin());
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();

            if (usuarioVT == null) {
                response.sendRedirect
                     (securityLogin.redireccionarLogin());
            }
        } catch (IOException e ) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Se genera error Validando Autenticacion" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Se genera error Validando Autenticacion" + e.getMessage(), e, LOGGER);
        }
    }
    
    
    @PostConstruct
    private void init() {
        try {
            vetoNodosMglFacadeLocal.setUser(usuarioVT, perfilVt);
            vetoCiudadMglFacadeLocal.setUser(usuarioVT, perfilVt);
            vetoCanalMglFacadeLocal.setUser(usuarioVT, perfilVt);
            vetoMglFacadeLocal.setUser(usuarioVT, perfilVt);
            nodoVetoList = new ArrayList<NodoMgl>();
            ciudadesVetoList = new ArrayList<GeograficoPoliticoMgl>();
            canalVetoList = new ArrayList<ParametrosCalles>();
            nodoVetoPaginadaList = new ArrayList<NodoMgl>();
            ciudadPaginadaList = new ArrayList<GeograficoPoliticoMgl>();
            canalVetoPaginadaList  = new ArrayList<ParametrosCalles>();
            obtenerListadoVeto(1);
            obtenerTipoTecnologiaList();           
            obtenerDepartamentoList();
            vetarCanalesAutomatico();
            obtenerListadoCanal(1);
            
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al realizar cargue de configuración de listados" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al realizar cargue de configuración de listados" + e.getMessage(), e, LOGGER);
        }
    }
    
      /**
     * Función que guarda el listado de vetos en la base de datos
     *
     * @author Juan David Hernandez   
     * @param numeroPolitica   
     * @return    
     */
    public boolean validarPoliticaVetoRepetida(String numeroPolitica){
        try {        
            VetoMgl vetoPolitica 
                    = vetoMglFacadeLocal.findByPolitica(numeroPolitica);
            if (vetoPolitica != null) {
                String msnError = "Ya existe una politica creada con el"
                        + " identificador de politica ingresado. ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                        msnError, ""));
                return false;
            }
            return true;            
        } catch (ApplicationException ex) {
             FacesUtil.mostrarMensajeError("Error al mostarAuditoria. " + ex.getMessage() , ex, LOGGER);
            return false;
        } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al mostarAuditoria. " + e.getMessage() , e, LOGGER);  
        }
        return false;

    }
    
    
    /**
     * Función que guarda el listado de vetos en la base de datos
     *
     * @author Juan David Hernandez   
     * @throws co.com.claro.mgl.error.ApplicationException   
     */
    public void crearVeto() {
        try {   
            if (validarDatosVeto() && validarPoliticaVetoRepetida(politica)) {
                if ((nodoVetoList != null && !nodoVetoList.isEmpty()
                        || (canalVetoList != null && !canalVetoList.isEmpty())
                        || (ciudadesVetoList != null 
                        && !ciudadesVetoList.isEmpty()))) {
                    
                    // Se crea el la politica de Veto en la base de datos
                    VetoMgl veto = new VetoMgl ();
                    veto.setNumeroPolitica(politica.toUpperCase());
                    veto.setCorreo(correoElectronico);
                    
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                    String fecha = formatoFecha.format(fechaInicio);
                    Date fechaInicioDate = formatoFecha.parse(fecha);            
                    veto.setFechaInicio(fechaInicioDate);
         
                    //crearFecha Fin año 3000 si no se ingresa fecha fin
                    if (fechaFin == null) {                       
                        fechaFin = formatoFecha.parse("01/01/3000");
                        veto.setFechaFin(fechaFin);
                    } else {                        
                        String fechaFins = formatoFecha.format(fechaFin);
                        Date fechaFinDate = formatoFecha.parse(fechaFins);
                        veto.setFechaFin(fechaFinDate);
                    }                   
                    
                    veto.setTipoVeto(validarTipoVeto());
                    vetoMglFacadeLocal.crearVeto(veto);
                    
                    /* Si se creó el Veto procedemos a crear el listado de
                     * nodos vetados */
                    if (nodoVetoList != null && !nodoVetoList.isEmpty()
                            && veto.getVetoId() != null) {

                        for (NodoMgl nodo : nodoVetoList) {
                            VetoNodosMgl vetoNodoMgl = new VetoNodosMgl();
                            vetoNodoMgl.setVetoId(veto);
                            vetoNodoMgl.setNodoMgl(nodo);
                            vetoNodosMglFacadeLocal.createVetoNodos(vetoNodoMgl);
                        }
                        
                      String msnError = "Los nodos fueron vetados "
                              + "correctamente. ";
                     FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, msnError,""));
                        
                    }
                          
                   List<GeograficoPoliticoMgl> centroPobladoVetoList = new ArrayList();  
                    /* Si se creó el Veto procedemos a crear el listado de
                     * ciudades vetados */
                    if (ciudadesVetoList != null && !ciudadesVetoList.isEmpty()
                            && veto.getVetoId() != null){     
                       
                       /*recorremos las ciudades seleccionadas para realizar
                        * busqueda de sus respectivos centros poblados para estos
                        * ser vetados*/
                      for (GeograficoPoliticoMgl ciudad : ciudadesVetoList) {                          
                    
                            VetoCiudadMgl vetoCiudad = new VetoCiudadMgl();
                            vetoCiudad.setVetoId(veto);
                            vetoCiudad.setCentroPobladoId(ciudad);  
                            vetoCiudadMglFacadeLocal.createVetoCiudad(vetoCiudad);                              
                                          
                        }
                        String msnError = "Los centro poblados"
                                + " fueron vetados correctamente. ";
                     FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, msnError,""));
                    }                     
                    
                    if(canalVetoList != null && !canalVetoList.isEmpty() 
                            && veto.getVetoId() != null){
                        
                        for (ParametrosCalles ciudad : canalVetoList) {
                            VetoCanalMgl vetoCanal = new VetoCanalMgl();
                            vetoCanal.setVetoId(veto);
                            vetoCanal.setCanalId(ciudad);
                            vetoCanalMglFacadeLocal.createVetoCanal(vetoCanal);
                        }
                     String msnError = "Los canales fueron vetados "
                             + "correctamente. ";
                     FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, msnError,""));
                    }                  
                  
                   limpiarPantalla();
                   obtenerListadoVeto(1);
                }else{
                     String msnError = "No existen elementos para vetar. ";
                     FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msnError,""));                   
                }              
            }  
        } catch (ApplicationException | ParseException e) {
            FacesUtil.mostrarMensajeError("Error al intentar crear un veto en la base de datos" + e.getMessage() , e, LOGGER); 
         } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al intentar crear un veto en la base de datos" + e.getMessage() , e, LOGGER);  
        }
    }
    
       
     /**
     * Función que valida que los datos a crear en el veto esten completos
     *
     * @author Juan David Hernandez  
     * return 0 ninguno 1 venta 2 creacion 3 ambos
     * @return 
     */
    public String validarTipoVeto(){
        try {            
             if(venta && !creacion){
                    return "1"; 
               }else{
                   if(!venta && creacion){
                       return "2";
                   }else{
                       if(venta && creacion){
                           return "3";
                       }
                   }
               } 
            
            return "0";            
        } catch (Exception e) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg,e);
            return "0";
        }
    }
    
     /**
     * Función que valida que los datos a crear en el veto esten completos
     *
     * @author Juan David Hernandez   
     * @return    

     */
    public boolean validarDatosVeto() {
        try {          
            if (!venta && !creacion) {
                String msnError = "Debe seleccionar "
                        + "un tipo de "
                        + "Veto. VENTA ó CREACIÓN HHPP.";
                FacesContext.getCurrentInstance()
                        .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msnError, ""));
                return false;
            }else{
               if (politica == null || politica.trim().isEmpty()) {
                String msnError = "Debe ingresar una de politica por favor ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError,
                        ""));
                return false;
            }else{
                   if (politica.length() > 10) {
                       String msnError = "El número de la politica no debe superar los 10 caracteres ";
                       FacesContext.getCurrentInstance().addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_WARN, msnError,
                               ""));
                       return false;
                   }
               else {
                if (fechaInicio == null) {
                    String msnError = "Debe ingresar una FECHA INICIO del veto";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msnError,
                            ""));
                    return false;
                } else {
                    if (correoElectronico == null || correoElectronico.trim().isEmpty()) {
                        String msnError = "Debe ingresar un CORREO ELECTRÓNICO.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                msnError, ""));
                        return false;
                    } else {
                        if (!correoElectronico.contains("@") 
                                || !correoElectronico.contains(".")) {
                            String msnError = "Debe ingresar un CORREO "
                                    + "ELECTRÓNICO Válido.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                    msnError, ""));
                            return false;
                        } else {
                            
                            if (correoElectronico.length() > 50) {
                                String msnError = "El correo no debe superar los 50 caracteres.";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                                return false;
                            } else {

                            if (!validarFecha(fechaInicio)) {
                                String msnError = "La FEHCA INICIO ingresada"
                                        + " no es válida.";
                                FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                                return false;
                            } else {
                                if (fechaFin != null) {
                                    if (!validarFecha(fechaFin)) {
                                        String msnError = "La FEHCA FIN "
                                                + "ingresada no es valida.";
                                        FacesContext.getCurrentInstance()
                                                .addMessage(null,
                                                new FacesMessage
                                                (FacesMessage.SEVERITY_WARN,
                                                msnError, ""));
                                        return false;
                                    }else{
                                        if(fechaInicio != null && fechaFin != null){
                                            SimpleDateFormat formatoFecha 
                                                    = new 
                                                    SimpleDateFormat("dd/MM/yyyy");                                            
                                            String date1
                                                    = formatoFecha
                                                    .format(fechaInicio);
                                            String date2 = 
                                                    formatoFecha.format(fechaFin);
                                            int diferencia = 
                                                    dateComparator(date2, date1);
                                            if(diferencia < 0){
                                              String msnError = "La FECHA FIN "
                                                + "no puede ser anterior a "
                                                      + "la FECHA INICIO.";
                                        FacesContext.getCurrentInstance()
                                                .addMessage(null,
                                                new FacesMessage
                                                (FacesMessage.SEVERITY_WARN,
                                                msnError, ""));
                                        return false;  
                                            }
                                        }
                                    }
                                } 
                            }
                        }
                        }
                    }
                }
                }
            }
         }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al validar datos para creación de Veto ", e);
            String msnError = "Error al validar datos para creación de Veto ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(), ""));
            return false;
        }
    }
    
     /**
     * Función que validar que la fecha ingresada sea una fecha valida.
     * 
     * @author Juan David Hernandez
     * @param fecha
     * @return 
     */
    public boolean validarFecha(Date fecha){
        try {

            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            String date = formatoFecha.format(fecha);
            
            formatoFecha.setLenient(false);
            formatoFecha.parse(date);
            
            return true; 
            
        }  catch (ParseException e) {
            FacesUtil.mostrarMensajeError("Error al intentar validar una fecha " + e.getMessage() , e, LOGGER);  
            return false;
        } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al intentar validar una fecha " + e.getMessage() , e, LOGGER);  
        }
        return false;
    }
    
        /**
     * Función que validar si una fecha es superior a otra.
     *
     * @author Juan David Hernandez
     * @param _endDate
     * @return 
     */
    public int dateComparator(String _firstDate, String _endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
            sdf.setLenient(false);
            Date firstDate = (Date) sdf.parse(_firstDate);
            Date endDate = (Date) sdf.parse(_endDate);
            return firstDate.compareTo(endDate);
        } catch (ParseException e) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg,e);
            return -100;
        }
    }

    
       /**
     * Función que busca el nodo ingresado.
     *
     * @author Juan David Hernandez   
     */ 
    public void buscarNodo(){
        try {
            if(nodoBuscado.trim() != null && !nodoBuscado.trim().isEmpty()){
              
            NodoMgl nodo = nodoMglFacadeLocal
                    .findByCodigo(nodoBuscado.trim().toUpperCase());
                if (nodo != null) {
                    nodoList = new ArrayList();
                    nodoList.add(nodo);
                }else{
                  String msnError = "No se encontró ninguno nodo con los datos"
                          + " ingresados.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));  
                }
          }else{
                 String msnError = "Debe ingresar un nodo para este ser buscado.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
            }
        } catch (Exception e) {
            LOGGER.error("Error al buscar Nodo ", e);
            String msnError = "Error al buscar Nodo";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(), ""));
        }
    }
    
        /**
     * Función que busca el nodo ingresado.
     *
     * @author Juan David Hernandez   
     */ 
    public void buscarVeto() {
        try {
            if (vetoBuscado.trim() != null && !vetoBuscado.trim().isEmpty()) {

                VetoMgl veto = vetoMglFacadeLocal.findByPolitica(vetoBuscado.toUpperCase());

                if (veto != null) {
                    vetoList = new ArrayList();
                    vetoList.add(veto);
                    if (vetoList != null && !vetoList.isEmpty()) {
                        for (VetoMgl vetoMgl : vetoList) {
                            SimpleDateFormat formatoFecha
                                    = new SimpleDateFormat("dd/MM/yyyy");
                            String date1 = formatoFecha
                                    .format(vetoMgl.getFechaFin());
                            String date2 =
                                    formatoFecha.format(new Date());
                            int diferencia =
                                    dateComparator(date2, date1);
                            if (diferencia <= 0) {
                                vetoMgl.setVigencia("VIGENTE");
                            } else {
                                vetoMgl.setVigencia("NO VIGENTE");
                            }
                        }
                    }
                } else {
                    String msnError = "No se encontró ninguno veto con la politica"
                            + " ingresada.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
                }
            } else {
                String msnError = "Debe ingresar una politica para este ser buscada.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al buscar veto " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al buscar veto " + e.getMessage(), e, LOGGER);
        }
    }
    
      /**
     * Función que limpiar el veto buscado y carga todos.
     *
     * @author Juan David Hernandez   
     */ 
    public void limpiarBusquedaVeto(){
        try {
           vetoBuscado = ""; 
           obtenerListadoVeto(1);
        } catch (Exception e) {
            LOGGER.error("Error al buscar veto ", e);
            String msnError = "Error al buscar veto";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError
                    + e.getMessage(), ""));
        }
    }
    
    
    
    
    
    
    
     /**
     * Función que  redirecciona al detalle del Veto
     *
     * @author Juan David Hernandez   
     * @param vetoSeleccionado   
     */ 
    public void detallarVeto(VetoMgl vetoSeleccionado){
        try {
            if(vetoSeleccionado != null){
               
                VetoNodosSessionBean vetoNodosSessionBean =
                            (VetoNodosSessionBean) 
                            JSFUtil.getBean("vetoNodosSessionBean");
                vetoNodosSessionBean.setVetoSeleccionado(vetoSeleccionado);
               
                FacesUtil.navegarAPagina(
                        "/view/MGL/VT/vetoNodos/vetoNodosVt/detalleVetoNodo.jsf");
                
            }else{
               String msnError = "Error al intentar detallar el Veto"
                       + " seleccionado. Intente nuevamente por favor. ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError, "")); 
            }
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al intentar detallar Veto " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al intentar detallar Veto " + e.getMessage(), e, LOGGER);
        }
    }
    
    
    
      /**
     * Obtiene el listado de tecnologias de la base de datos
     *
     * @author Juan David Hernandez   
     * @throws co.com.claro.mgl.error.ApplicationException   
     */
    public void obtenerTipoTecnologiaList()  {
        try {
            CmtTipoBasicaMgl tipoBasicaTipoTecnologia;
            tipoBasicaTipoTecnologia =
                    cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            tipoTecnologiaList = cmtBasicaMglFacadeLocal
                    .findByTipoBasica(tipoBasicaTipoTecnologia);
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al  obtener listado de tecnologías" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al  obtener listado de tecnologías" + e.getMessage(), e, LOGGER);
        }
    }

     /**
     * Obtiene el listado de divisiones de la base de datos por tecnología.
     *
     * @author Juan David Hernandez   
     * @throws co.com.claro.mgl.error.ApplicationException   
     */
    public void obtenerDivisionList()  {
        try {
             divisionList = rrRegionalesFacadeLocal.findRegionales();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al  obtener listado de tecnologías" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al  obtener listado de tecnologías" + e.getMessage(), e, LOGGER);
        }
    }
    
      /**
     * Obtiene el listado de comunidades de la base de datos por división.
     *
     * @author Juan David Hernandez   
     */
    public void obtenerComunidadesList(){
        try {
            ciudad = "";
            departamento = "";
            comunidad = "";
            centroPoblado = null;         
            ciudadesList = new ArrayList();
            centroPobladoList = new ArrayList();
            ciudadList = new ArrayList();
            nodoList = new ArrayList();
         if (division != null && !division.isEmpty()) {
            comunidadList = rrCiudadesFacadeLocal.findByCodregional(division);
         }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de comunidades " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de comunidades " + e.getMessage(), e, LOGGER);
        }
    }
    
    
      /**
     * Obtiene el listado de departamentos de la base de datos.
     *
     * @author Juan David Hernandez   
     */
    public void obtenerDepartamentoList() {
        try {
            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de departamentos " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de departamentos " + e.getMessage(), e, LOGGER);
        } 
    }
    
      /**
     * Obtiene el listado de departamentos de la base de datos.
     *
     * @author Juan David Hernandez   
     */
    public void obtenerCiudadesList() {
        try {
            if (departamento != null 
                    && !departamento.isEmpty()) {      
               
                //Obtenemos el listado de ciudades para el filtro de la pantalla
                ciudadesList = 
                        geograficoPoliticoMglFacadeLocal.findCiudades
                        (new BigDecimal(departamento));
                nodoList = new ArrayList();
                centroPobladoList = new ArrayList();
                //Obtenemos el listado de ciudad para la tabla de vetos
            }
            ciudadList = new ArrayList();
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de departamentos " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de departamentos " + e.getMessage(), e, LOGGER);
        }
    }
        
        
     /**
     * Obtiene el listado de centro poblado por ciudad de la base de datos.
     *
     * @author Juan David Hernandez   
     */
    public void obtenerCentroPobladoList() {
        try {            
            if (ciudad != null 
                    && !ciudad.isEmpty()) {                 
                 centroPobladoList = geograficoPoliticoMglFacadeLocal
                        .findCentroPoblado(new BigDecimal(ciudad)); 
                 obtenerListadoNodo(1);
                 obtenerListadoCiudad(1);
            }else{
               centroPobladoList = new ArrayList();  
               nodoList = new ArrayList();
            } 
      
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de centro poblado " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado de centro poblado " + e.getMessage(), e, LOGGER);
        }
    }
    
      /**
     * Función que obtiene asigna el valor del departamento y la ciudad al
     * ser seleccionada una comunidad.
     *
     * @author Juan David Hernandez   
     */
    public void obtenerCiudadDepartamentoByComunidad() {
        try {
            departamento = "";
            ciudad = "";
            centroPoblado = null;         
            centroPobladoList = new ArrayList();
            ciudadList = new ArrayList();
            nodoList = new ArrayList();
            if (comunidad != null && !comunidad.isEmpty()) {
                GeograficoPoliticoMgl geograficoPoliticoMgl
                        = geograficoPoliticoMglFacadeLocal
                        .findCityByComundidad(comunidad);
                if (geograficoPoliticoMgl != null) {                   
                    obtenerDepartamentoList();                   
                    departamento = geograficoPoliticoMgl.getGeoGpoId() + "";
                    obtenerCiudadesList();
                    ciudad = geograficoPoliticoMgl.getGpoId() + "";
                    obtenerCentroPobladoList();
                    obtenerListadoNodo(1);
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado ciudad y departamento "+ e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al obtener listado ciudad y departamento "+ e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función utilizada para validar los campos minimos para realizar 
     * un filtro de búsqueda   
     * 
     * @author Juan David Hernandez   
     * return verdadero si no se encuentra ningun dato nulo
     * @return 
     */
    public boolean validarDatosFiltros(){
        try {
            if(tipoTecnologia == null 
                    || tipoTecnologia.isEmpty()){
                String msnError = "Debe seleccionar Tipo de Tecnología.  ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError, ""));
                return false;
            }         
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al validar datos del filtro para búsqueda ", e);
            String msnError = "Error al validar datos del filtro para búsqueda  ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + e.getMessage(), ""));
            return false;
        }
    }
    
     /**
     * Función utilizada para limpiar los filtros de la pantalla
     * 
     * @author Juan David Hernandez   
     */
    public void limpiarFiltros(){
        try {
            nodoBuscado = "";
            politica = "";
            correoElectronico = "";           
            division = "";
            comunidad = "";
            tipoTecnologia = "";
            ciudad = "";
            departamento = "";
            centroPoblado = null;
            tipoCargue = "";           
            ciudadesList = new ArrayList();
            comunidadList = new ArrayList();            
            centroPobladoList = new ArrayList();     
            nodoVetoList = new ArrayList();  
            ciudadesVetoList = new ArrayList();      
            nodoVetoPaginadaList = new ArrayList();   
            ciudadPaginadaList = new ArrayList();    
            fechaInicio = null;
            fechaFin = null;
            venta = false;
            creacion = false;
        } catch (Exception e) {
            LOGGER.error("Error al intentar limpiar formulario ", e);
            String msnError = "Error al intentar limpiar formulario  ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + e.getMessage(), ""));
        }
    }
    
     /**
     * Función utilizada para limpiar toda la pantalla del usuario
     * 
     * @author Juan David Hernandez   
     */
    public void limpiarPantalla() {
        try {
            limpiarFiltros();
            nodoList = new ArrayList();
            nodoVetoList = new ArrayList();
            ciudadList = new ArrayList();           
            ciudadesVetoList = new ArrayList();        
            canalVetoList = new ArrayList();      
            nodosAllSelected = false;
            ciudadAllSelected = false;
            canalAllSelected = false;   
            vetoBuscado = ""; 
            obtenerListadoCanal(1);
            obtenerListadoVeto(1);
        } catch (Exception e) {
            LOGGER.error("Error al intentar limpiar formulario ", e);
            String msnError = "Error al intentar limpiar formulario  ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + e.getMessage(), ""));
        }
    }
    
     
    
      /**
     * Función utilizada seleccionar todos los nodos del listado en pantalla.
     *
     * @author Juan David Hernandez
     */
    public void seleccionarTodoNodos(){
        try {
            if(nodoList != null && !nodoList.isEmpty()){
                for (NodoMgl nodo : nodoList) {
                    if(nodo.isSelected()){
                       nodo.setSelected(false); 
                    }else {
                       nodo.setSelected(true);                       
                    }                                    
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar todos los nodos de la"
                    + " lista ", e);
            String msn = "Error al seleccionar todos los nodos de la lista ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        }
    }
    
          /**
     * Función utilizada seleccionar todas las ciudades del listado en pantalla.
     *
     * @author Juan David Hernandez
     */
    public void seleccionarTodoCiudades(){
        try {
            if(ciudadList != null && !ciudadList.isEmpty()){
                for (GeograficoPoliticoMgl ciudad : ciudadList) {
                    if(ciudad.isSelected()){
                       ciudad.setSelected(false); 
                    }else {
                       ciudad.setSelected(true);   
                    }                                    
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar todas las ciudades de la"
                    + " lista ", e);
            String msn = "Error al seleccionar todas las ciudades de la lista ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        }
    }
    
          /**
     * Función utilizada seleccionar todos los canales del listado en pantalla.
     *
     * @author Juan David Hernandez
     */
    public void seleccionarTodoCanales(){
        try {
            if(canalList != null && !canalList.isEmpty()){
                for (ParametrosCalles canal : canalList) {
                    if(canal.isSelected()){
                       canal.setSelected(false); 
                    }else {
                       canal.setSelected(true);   
                    }                                    
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar todos los canales de la "
                    + "lista ", e);
            String msn = "Error al seleccionar todos los canales de la lista ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        }
    }
    
      /**
     * Función utilizada seleccionar todos los nodos del listado en pantalla.
     *
     * @author Juan David Hernandez
     */
    public void seleccionarTodoVetoNodos(){
        try {
            if(nodoVetoList != null && !nodoVetoList.isEmpty()){
                for (NodoMgl nodo : nodoVetoList) {
                    if(nodo.isSelected()){
                       nodo.setSelected(false); 
                    }else {
                       nodo.setSelected(true);   
                    }                                    
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar todos los nodos de la"
                    + " lista ", e);
            String msn = "Error al seleccionar todos los nodos de la lista ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        }
    }
    
          /**
     * Función utilizada seleccionar todas las ciudades del listado en pantalla.
     *
     * @author Juan David Hernandez
     */
    public void seleccionarTodoVetoCiudades(){
        try {
            if(ciudadesVetoList != null && !ciudadesVetoList.isEmpty()){
                for (GeograficoPoliticoMgl ciudad : ciudadesVetoList) {
                    if(ciudad.isSelected()){
                       ciudad.setSelected(false); 
                    }else {
                       ciudad.setSelected(true);   
                    }                                    
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar todas las ciudades de la"
                    + " lista ", e);
            String msn = "Error al seleccionar todas las ciudades de la lista ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        }
    }
    
          /**
     * Función utilizada seleccionar todos los canales del listado en pantalla.
     *
     * @author Juan David Hernandez
     */
    public void seleccionarTodoVetoCanales(){
        try {
            if(canalVetoList != null && !canalVetoList.isEmpty()){
                for (ParametrosCalles canal : canalVetoList) {
                    if(canal.isSelected()){
                       canal.setSelected(false); 
                    }else {
                       canal.setSelected(true);   
                    }                                    
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar todos los canales de la "
                    + "lista ", e);
            String msn = "Error al seleccionar todos los canales de la lista ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        }
    }  
    
         /**
     * Función utilizada agregar los nodos seleccionado al listado de 
     * nodos vetados.
     *
     * @author Juan David Hernandez
     */
    public void agregarVetoNodoButton(){
        try {         
            if (nodoList != null && !nodoList.isEmpty()) {
                int seleccion = 0;
                for (NodoMgl nodo : nodoList) {
                    if (nodo.isSelected()) {
                        seleccion++;
                        if (validarVetoNodoRepetido(nodo)) {
                            nodoVetoList.add(nodo.clone());
                            nodo.setSelected(false);                           
                        }
                    }
                }
                if(seleccion < 1){
                    String msn = "No se seleccionó ningun elemento para vetar. ";
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, "")); 
                }
                obtenerListadoVetoNodo(1);

                if (nodosAllSelected) {
                    nodosAllSelected = false;
                }
            }else{
                String msn = "No existen elementos para vetar seleccionados. ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, ""));
            }
        } catch (CloneNotSupportedException e) {
            FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
        }
    }

     /**
     * Función utilizada agregar los nodos seleccionado al listado de 
     * nodos vetados.
     *
     * @author Juan David Hernandez
     */
    public void agregarVetoNodo(){
        try {         
            if (nodoList != null && !nodoList.isEmpty()) {
                for (NodoMgl nodo : nodoList) {
                    if (nodo.isSelected()) {                        
                        if (validarVetoNodoRepetido(nodo)) {
                            nodoVetoList.add(nodo.clone());
                            nodo.setSelected(false);
                        }
                    }
                }
                obtenerListadoVetoNodo(1);
                if (nodosAllSelected) {
                    nodosAllSelected = false;
                }
            }
        } catch (CloneNotSupportedException e) {
            FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
        }
    }
    
     /**
     * Función utilizada para validar que no se esten agregando nodos vetado
     * repetidos
     *
     * @author Juan David Hernandez
     * @param nodoSeleccionado
     * @return 
     */
    public boolean validarVetoNodoRepetido(NodoMgl nodoSeleccionado){
        try {
            if(nodoVetoList != null && !nodoVetoList.isEmpty()){
                for (NodoMgl nodo : nodoVetoList) {
                    if(nodo.getNodId().equals(nodoSeleccionado.getNodId())){
                        String msn = "El veto " + nodo.getNodNombre() + 
                            " Ya se encuentra en el listado de Nodos a Vetar y "
                                  + "no fue agregado al listado." ;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, ""));
                        return false;
                    }
                } 
           }
            return true;            
        } catch (Exception e) {
                 LOGGER.error("Error al validar veto nodo repetido ", e);
            String msn = "Error al validar veto nodo repetido  ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
            return false;
        }
    }
    
      /**
     * Función utilizada para validar que no se esten agregando nodos vetado
     * repetidos
     *
     * @author Juan David Hernandez
     * @param ciudadSeleccionada
     * @return 
     */
    public boolean validarVetoCiudadRepetido(GeograficoPoliticoMgl
            ciudadSeleccionada){
        try {
            if(ciudadesVetoList != null && !ciudadesVetoList.isEmpty()){
                for (GeograficoPoliticoMgl cityVeto : ciudadesVetoList) {
                    if(cityVeto.getGpoId().equals(ciudadSeleccionada.getGpoId())){
                        String msn = "La ciudad " + cityVeto.getGpoNombre() + 
                            " Ya se encuentra en el listado de Ciudades "
                                + "a Vetar y no fue agregado al listado." ;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, ""));
                        return false;
                    }
                } 
           }
            return true;            
        } catch (Exception e) {
                 LOGGER.error("Error al validar veto nodo repetido ", e);
            String msn = "Error al validar veto nodo repetido  ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
            return false;
        }
    }
    
     /**
     * Función utilizada para validar que no se esten agregando nodos vetado
     * repetidos
     *
     * @author Juan David Hernandez
     * @param canalSeleccionado
     * @return 
     */
    public boolean validarVetoCanalRepetido(ParametrosCalles
            canalSeleccionado){
        try {
            if(canalVetoList != null && !canalVetoList.isEmpty()){
                for (ParametrosCalles canal : canalVetoList) {
                    if(canal.getIdParametro()
                            .equals(canalSeleccionado.getIdParametro())){
                          String msn = "El canal " + canal.getDescripcion() + 
                            " Ya se encuentra en el listado de Canales a Vetar y "
                                  + "no fue agregado al listado. " ;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, ""));
                        return false;
                    }
                } 
           }
            return true;            
        } catch (Exception e) {
                 LOGGER.error("Error al validar veto nodo repetido ", e);
            String msn = "Error al validar veto nodo repetido  ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
            return false;
        }
    }
    
       /**
     * Función utilizada agregar las ciudades seleccionadas al listado de 
     * ciudades vetadas.
     *
     * @author Juan David Hernandez
     */
      public void agregarVetoCiudad(boolean paginando){
        try {
            if (ciudadList != null && !ciudadList.isEmpty()) {
                int seleccion = 0;
                for (GeograficoPoliticoMgl city : ciudadList) {
                    if (city.isSelected()) {
                        seleccion++;
                        if (validarVetoCiudadRepetido(city)) {
                            ciudadesVetoList.add(city.clone());
                            city.setSelected(false);
                        }
                    }
                      obtenerListadoVetoCiudad(1);
                }
                if(seleccion < 1 && !paginando){
                    String msn = "No se seleccionó ningun elemento para vetar. ";
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, "")); 
                }
                if (ciudadAllSelected) {
                    ciudadAllSelected = false;
                }
            }else{
                String msn = "No existen elementos para vetar seleccionados. ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, ""));
            }   
            
          } catch (CloneNotSupportedException e) {
              FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
          } catch (Exception e) {
              FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
          }
    }
      
       /**
     * Función utilizada agregar las ciudades seleccionadas al listado de 
     * ciudades vetadas.
     *
     * @author Juan David Hernandez
     */
      public void agregarVetoCiudadButton(){
        try {
            if (ciudadList != null && !ciudadList.isEmpty()) {
                 int seleccion = 0;
                for (GeograficoPoliticoMgl ciudad : ciudadList) {
                    if (ciudad.isSelected()) {
                        seleccion++;
                        if (validarVetoCiudadRepetido(ciudad)) {
                            ciudadesVetoList.add(ciudad.clone());
                            ciudad.setSelected(false);                        
                        }
                    }
                }
                if(seleccion < 1){
                    String msn = "No se seleccionó ningun elemento para vetar. ";
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, "")); 
                }
                obtenerListadoVetoCiudad(1);
                
                if (ciudadAllSelected) {
                    ciudadAllSelected = false;
                }
            }else{
                String msn = "No existen elementos para vetar seleccionados. ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, ""));
            }   
            
          } catch (CloneNotSupportedException e) {
              FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
          } catch (Exception e) {
              FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
          }
    }
      
          /**
     * Función utilizada agregar los canales seleccionado al listado de 
     * canales vetados.
     *
     * @author Juan David Hernandez
     */
     public void agregarVetoCanalButton(){
        try {
            if (canalList != null && !canalList.isEmpty()) {
                 int seleccion = 0;
                for (ParametrosCalles canal : canalList) {
                    if (canal.isSelected() ) {
                         seleccion++;
                        if(validarVetoCanalRepetido(canal)){
                        canalVetoList.add(canal.clone());
                        canal.setSelected(false);                      
                    }
                  }
                }
                  if(seleccion < 1){
                    String msn = "No se seleccionó ningun elemento para vetar. ";
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, "")); 
                }
                obtenerListadoVetoCanal(1);
                if (canalAllSelected) {
                    canalAllSelected = false;
                }
            }else{
                String msn = "No existen elementos para vetar seleccionados. ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, ""));
            }  
         } catch (CloneNotSupportedException e) {
             FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos ", e, LOGGER);
         } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
         }
    }  
      
      /**
     * Función utilizada agregar los canales seleccionado al listado de 
     * canales vetados.
     *
     * @author Juan David Hernandez
     */
     public void agregarVetoCanal(boolean paginando){
        try {
            if (canalList != null && !canalList.isEmpty()) {
                int seleccion = 0;
                for (ParametrosCalles canal : canalList) {
                    if (canal.isSelected() ) {
                        seleccion++;
                        if(validarVetoCanalRepetido(canal)){
                        canalVetoList.add(canal.clone());
                        canal.setSelected(false);
                    }
                  }
                }
                if(seleccion < 1 && !paginando){
                    String msn = "No se seleccionó ningun elemento para vetar. ";
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, "")); 
                }
                obtenerListadoVetoCanal(1);
                if (canalAllSelected) {
                    canalAllSelected = false;
                }
            }else{
                String msn = "No existen elementos para vetar seleccionados. ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msn, ""));
            }
         } catch (CloneNotSupportedException e) {
             FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error al agregar listados a tablas de Vetos " + e.getMessage(), e, LOGGER);
         }
    }  
     
     /**
     * Función utilizada eliminar nodos del listado de nodos vetados 
     *
     * @author Juan David Hernandez
     * @param nodoVetado
     */
     public void eliminarVetoNodo(NodoMgl nodoVetado){
         try {
             if (nodoVetoList != null && !nodoVetoList.isEmpty()) {
                 for (NodoMgl nodo : nodoVetoList) {
                     if (nodo.getNodId().equals(nodoVetado.getNodId())) {
                         nodoVetoList.remove(nodo);
                         break;
                     }
                 }
             }
             
               if (nodoVetoPaginadaList != null && !nodoVetoPaginadaList.isEmpty()) {
                 for (NodoMgl nodo : nodoVetoPaginadaList) {
                     if (nodo.getNodId().equals(nodoVetado.getNodId())) {
                         nodoVetoPaginadaList.remove(nodo);
                         break;
                     }
                 }
             }
               obtenerListadoVetoNodo(actualVetoNodo);
         } catch (Exception e) {
             LOGGER.error("Error al eliminar Nodo del listado de Nodos Vetados"
                     + " ", e);
            String msn = "Error al eliminar Nodo del listado de Nodos Vetados";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
         }
     }
     
        /**
     * Función utilizada eliminar ciudades del listado de nodos vetados 
     *
     * @author Juan David Hernandez
     * @param ciudadVetada
     */
     public void eliminarVetoCiudad(GeograficoPoliticoMgl ciudadVetada){
         try {
               if (ciudadesVetoList != null && !ciudadesVetoList.isEmpty()) {

                 for (GeograficoPoliticoMgl ciudad : ciudadesVetoList) {
                     if (ciudad.getGpoId().equals(ciudadVetada.getGpoId())) {
                         ciudadesVetoList.remove(ciudad);
                          break;
                     }
                 }
             }
               
           if (ciudadPaginadaList != null && !ciudadPaginadaList.isEmpty()) {

                 for (GeograficoPoliticoMgl ciudad : ciudadPaginadaList) {
                     if (ciudad.getGpoId().equals(ciudadVetada.getGpoId())) {
                         ciudadPaginadaList.remove(ciudad);
                          break;
                     }
                 }
             }             
         } catch (Exception e) {
             LOGGER.error("Error al eliminar Nodo del listado de Nodos Vetados"
                     + " ", e);
            String msn = "Error al eliminar Nodo del listado de Nodos Vetados";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
         }
     }
     
        /**
     * Función utilizada eliminar canales del listado de canales vetados 
     *
     * @author Juan David Hernandez
     * @param canalVetado
     */
     public void eliminarVetoCanal(ParametrosCalles canalVetado){
         try {
             if (canalVetoList != null && !canalVetoList.isEmpty()) {

                 for (ParametrosCalles canal : canalVetoList) {
                     if (canal.getIdParametro()
                             .equals(canalVetado.getIdParametro())) {
                         canalVetoList.remove(canal);
                         break;
                     }
                 }
             }
             
                 if (canalVetoPaginadaList != null && !canalVetoPaginadaList.isEmpty()) {

                 for (ParametrosCalles canal : canalVetoPaginadaList) {
                     if (canal.getIdParametro()
                             .equals(canalVetado.getIdParametro())) {
                         canalVetoPaginadaList.remove(canal);
                         break;
                     }
                 }
             }
             
         } catch (Exception e) {
             LOGGER.error("Error al eliminar Nodo del listado de Nodos Vetados"
                     + " ", e);
            String msn = "Error al eliminar Nodo del listado de Nodos Vetados";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
         }
     }
     
    
    
    
    
    
     /**
     * Función que realiza paginación de la tabla.
     * 
     * @param  page;
     * @author Juan David Hernandez
     * @return 
     */
    public String obtenerListadoNodo(int page) {
        try {    
            
            actualNodo = page;
            getPageActualNodos();
            
            if (centroPoblado != null && (tipoTecnologia != null && !tipoTecnologia.isEmpty())) {
              CmtBasicaMgl atributoBasica = getCmtBasicaMgl(tipoTecnologia);
                nodoList = nodoMglFacadeLocal
                        .findNodosCentroPobladoAndTipoTecnologia(page, filasPag, 
                        centroPoblado, atributoBasica);
            } else {
                if(tipoTecnologia != null && !tipoTecnologia.isEmpty() 
                        && (ciudad != null&& !ciudad.isEmpty()))
                {
                CmtBasicaMgl atributoBasica = getCmtBasicaMgl(tipoTecnologia);
                nodoList = nodoMglFacadeLocal
                        .findNodosCiudadAndTipoTecnologia(page, filasPag, 
                        new BigDecimal(ciudad), atributoBasica);
                }else{
                    nodoList = new ArrayList();
                }
            }
            

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación  " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación  " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    
     /**
     * Función que carga la primera página de resultados
     * 
     * @author Juan David Hernandez
     */
    public void pageFirstNodo() {
        try {
            agregarVetoNodo();
            obtenerListadoNodo(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     * 
     * @author Juan David Hernandez
     */
    public void pagePreviousNodo() {
        try {            
            int totalPaginas = getTotalPaginasNodo();
            int nuevaPageActual = actualNodo - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            agregarVetoNodo();
            obtenerListadoNodo(nuevaPageActual);
        } catch (Exception ex) {
             LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

     /**
     * Función que permite ir directamente a la página seleccionada
     * de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void irPaginaNodo() {
        try {           
            int totalPaginas = getTotalPaginasNodo();
            int nuevaPageActual = Integer.parseInt(numPaginaNodo);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            agregarVetoNodo();
            obtenerListadoNodo(nuevaPageActual);
        } catch (NumberFormatException ex) {
             FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void pageNextNodo() {
        try {          
            int totalPaginas = getTotalPaginasNodo();
            int nuevaPageActual = actualNodo + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            agregarVetoNodo(); 
            obtenerListadoNodo(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     * 
     *@author Juan David Hernandez
     */
    public void pageLastNodo() {
        try {
            agregarVetoNodo();
            int totalPaginas = getTotalPaginasNodo();
            obtenerListadoNodo(totalPaginas);
        } catch (Exception ex) {
          LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginasNodo() {
        try {
            int totalPaginas = 0;            
            int pageSol = 1;
            if (centroPoblado != null 
                    && (tipoTecnologia != null && !tipoTecnologia.isEmpty())) {
                    CmtBasicaMgl tipoTecnologiaBasica=getCmtBasicaMgl(tipoTecnologia);
                pageSol = nodoMglFacadeLocal
                        .countNodosCentroPobladoAndTipoTecnologia
                        (centroPoblado, tipoTecnologiaBasica);
            } else {
              if(tipoTecnologia != null && !tipoTecnologia.isEmpty() 
                        && (ciudad != null&& !ciudad.isEmpty())){
                CmtBasicaMgl tipoTecnologiaBasica=getCmtBasicaMgl(tipoTecnologia);
                    pageSol = nodoMglFacadeLocal
                      .countNodosCiudadAndTipoTecnologia(new BigDecimal(ciudad),
                            tipoTecnologiaBasica);
                }
            }
             
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public String getPageActualNodos() {
        paginaListNodo = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasNodo();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListNodo.add(i);
        }
        pageActualNodo = String.valueOf(actualNodo) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaNodo == null) {
            numPaginaNodo = "1";
        }
        numPaginaNodo = String.valueOf(actualNodo);
        
        return pageActualNodo;
    }
    
     /**
     * Función que realiza paginación de la tabla de ciudades.
     * 
     * @param  page;
     * @author Juan David Hernandez
     * @return 
     */
    public String obtenerListadoCiudad(int page) {
        try {       
            actualCiudad = page;
            getPageActualCiudad();   
            if(ciudad != null && !ciudad.isEmpty()){
              
                ciudadList = geograficoPoliticoMglFacadeLocal
                        .findCiudadesPaginacion(page, ConstantsCM.PAGINACION_QUINCE_FILAS, new BigDecimal(ciudad)); 
            }else{
                ciudadList = new ArrayList();
                ciudadesList = new ArrayList();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación de ciudad" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación de ciudad" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    
     /**
     * Función que carga la primera página de resultados de ciudades
     * 
     * @author Juan David Hernandez
     */
    public void pageFirstCiudad() {
        try {
            agregarVetoCiudad(true);
            obtenerListadoCiudad(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     * 
     * @author Juan David Hernandez
     */
    public void pagePreviousCiudad() {
        try {
            int totalPaginas = getTotalPaginasCiudad();
            int nuevaPageActual = actualCiudad - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            agregarVetoCiudad(true);
            obtenerListadoCiudad(nuevaPageActual);
        } catch (Exception ex) {
             LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

     /**
     * Función que permite ir directamente a la página seleccionada
     * de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void irPaginaCiudad() {
        try {
            int totalPaginas = getTotalPaginasCiudad();
            int nuevaPageActual = Integer.parseInt(numPaginaCiudad);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            agregarVetoCiudad(true);
            obtenerListadoCiudad(nuevaPageActual);
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void pageNextCiudad() {
        try {
            int totalPaginas = getTotalPaginasCiudad();
            int nuevaPageActual = actualCiudad + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            agregarVetoCiudad(true);
            obtenerListadoCiudad(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     * 
     *@author Juan David Hernandez
     */
    public void pageLastCiudad() {
        try {
            agregarVetoCiudad(true);
            int totalPaginas = getTotalPaginasCiudad();            
            obtenerListadoCiudad(totalPaginas);
        } catch (Exception ex) {
          LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginasCiudad() {
        try {
            
            int totalPaginas = 0;  
            if(departamento != null && !departamento.isEmpty()){
            int pageSol = geograficoPoliticoMglFacadeLocal
                    .countCiudades(new BigDecimal(ciudad));
             
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            }
            return totalPaginas;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public String getPageActualCiudad() {
        paginaListCiudad = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasCiudad();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListCiudad.add(i);
        }
        pageActualCiudad = String.valueOf(actualCiudad) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaCiudad == null) {
            numPaginaCiudad = "1";
        }
        numPaginaCiudad = String.valueOf(actualCiudad);
        
        return pageActualCiudad;
    }

     /**
     * Función que realiza paginación de la tabla.
     * 
     * @param  page;
     * @author Juan David Hernandez
     * @return 
     */
    public String obtenerListadoCanal(int page) {
        try {       
            actualCanal = page;
            getPageActualCanal();
            canalList =
                    parametrosCallesFacade.findByTipoPaginacion(page, filasPag,
                    "TIPO_SOLICITUD");    
         
           //remueve del listado de canales los 3 que no deben ser vetados nunca
           if (canalList != null && !canalList.isEmpty()) {
                List<ParametrosCalles> areasList =   copiaCanalesList(canalList);
                for (ParametrosCalles parametro : areasList) {
                    if (parametro.getIdParametro().equalsIgnoreCase("CORTESIA")
                            || parametro.getIdParametro().equalsIgnoreCase("TRASLADOS")
                            || parametro.getIdParametro().equalsIgnoreCase("PYMES")) {
                        canalList.remove(parametro);
                    }
                }
            }
            
   
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación " + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
    public void vetarCanalesAutomatico(){
        try {
            canalList =
                    parametrosCallesFacade.findByTipo("TIPO_SOLICITUD");
            
            if(canalList != null && !canalList.isEmpty()){
                    //remueve del listado de canales los 3 que no deben ser vetados nunca              
                    List<ParametrosCalles> areasList = copiaCanalesList(canalList);
                    for (ParametrosCalles parametro : areasList) {
                        if (parametro.getIdParametro().equalsIgnoreCase("CORTESIA")
                                || parametro.getIdParametro().equalsIgnoreCase("TRASLADOS")
                                || parametro.getIdParametro().equalsIgnoreCase("PYMES")) {
                            canalList.remove(parametro);
                        }
                    }
                    
                for (ParametrosCalles canal : canalList) {
                    canal.setSelected(true);
                }
                agregarVetoCanal(true);
            }
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en listado de canales " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listado de canales" + e.getMessage(), e, LOGGER);
        }
    }
    
     /**
     * Función que realiza copia del listado de canales para
     * poderle eliminar elementos de su interior
     *      
     * @author Juan David Hernandez
     * @param canalList
     * @return 
     */
    public List<ParametrosCalles> copiaCanalesList(List<ParametrosCalles> canalList) {
        try {       
             List<ParametrosCalles> areasList = new ArrayList();
            if(canalList != null && !canalList.isEmpty()){               
                for (ParametrosCalles parametrosCalles : canalList) {
                    areasList.add(parametrosCalles);
                }                
            }
            return areasList;   
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listado de canales" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    
     /**
     * Función que carga la primera página de resultados
     * 
     * @author Juan David Hernandez
     */
    public void pageFirstCanal() {
        try {
            agregarVetoCanal(true);
            obtenerListadoCanal(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     * 
     * @author Juan David Hernandez
     */
    public void pagePreviousCanal() {
        try {
            int totalPaginas = getTotalPaginasCanal();
            int nuevaPageActual = actualCanal - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            agregarVetoCanal(true);
            obtenerListadoCanal(nuevaPageActual);
        } catch (Exception ex) {
             LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

     /**
     * Función que permite ir directamente a la página seleccionada
     * de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void irPaginaCanal() {
        try {
            int totalPaginas = getTotalPaginasCanal();
            int nuevaPageActual = Integer.parseInt(numPaginaCanal);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            agregarVetoCanal(true);
            obtenerListadoCanal(nuevaPageActual);
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void pageNextCanal() {
        try {
            int totalPaginas = getTotalPaginasCanal();
            int nuevaPageActual = actualCanal + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            agregarVetoCanal(true);
            obtenerListadoCanal(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     * 
     *@author Juan David Hernandez
     */
    public void pageLastCanal() {
        try {
            agregarVetoCanal(true);
            int totalPaginas = getTotalPaginasCanal();
            obtenerListadoCanal(totalPaginas);
        } catch (Exception ex) {
          LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginasCanal() {
        try {
            int totalPaginas = 0;
            int pageSol = parametrosCallesFacade.countByTipo("TIPO_SOLICITUD");
             
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public String getPageActualCanal() {
        paginaListCanal = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasCanal();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListCanal.add(i);
        }
        pageActualCanal = String.valueOf(actualCanal) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaCanal == null) {
            numPaginaCanal = "1";
        }
        numPaginaCanal = String.valueOf(actualCanal);
        
        return pageActualCanal;
    }    
    
     /**
     * Función que realiza paginación de la tabla.
     * 
     * @param  page;
     * @author Juan David Hernandez
     * @return 
     */
    public String obtenerListadoVetoNodo(int page) {
        try {       
            if (nodoVetoList != null && !nodoVetoList.isEmpty()) {
                actualVetoNodo = page;
                getPageActualVetoNodos();

                int firstResult = 0;
                if (page > 1) {
                    firstResult = (filasPag * (page - 1));
                }

                //Obtenemos el rango de la paginación
                int maxResult = 0;
                if ((firstResult + filasPag) > nodoVetoList.size()) {
                    maxResult = nodoVetoList.size();
                } else {
                    maxResult = (firstResult + filasPag);
                }
              
               /*Obtenemos los objetos que se encuentran en el rango de
                * la paginación y los guardarmos en la lista que se va a 
                * desplegar en pantalla*/
                nodoVetoPaginadaList = new ArrayList<NodoMgl>();
                for (int i = firstResult; i < maxResult; i++) {
                    nodoVetoPaginadaList.add(nodoVetoList.get(i));
                }
            }

        } catch (Exception e) {
             LOGGER.error("Error en lista de paginación ", e);
            String msnError = "Error en lista de paginación";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + e.getMessage(), ""));
        }
        return null;
    }

    
     /**
     * Función que carga la primera página de resultados
     * 
     * @author Juan David Hernandez
     */
    public void pageFirstVetoNodo() {
        try {
            obtenerListadoVetoNodo(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     * 
     * @author Juan David Hernandez
     */
    public void pagePreviousVetoNodo() {
        try {
            int totalPaginas = getTotalPaginasVetoNodo();
            int nuevaPageActual = actualVetoNodo - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            obtenerListadoVetoNodo(nuevaPageActual);
        } catch (Exception ex) {
             LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

     /**
     * Función que permite ir directamente a la página seleccionada
     * de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void irPaginaVetoNodo() {
        try {
            int totalPaginas = getTotalPaginasVetoNodo();
            int nuevaPageActual = Integer.parseInt(numPaginaVetoNodo);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoNodo(nuevaPageActual);
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void pageNextVetoNodo() {
        try {
            int totalPaginas = getTotalPaginasVetoNodo();
            int nuevaPageActual = actualVetoNodo + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoNodo(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     * 
     *@author Juan David Hernandez
     */
    public void pageLastVetoNodo() {
        try {
            int totalPaginas = getTotalPaginasVetoNodo();
            obtenerListadoVetoNodo(totalPaginas);
        } catch (Exception ex) {
          LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginasVetoNodo() {
        try {
            int totalPaginas = 0;
            int pageSol = nodoVetoList.size();           
             
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public String getPageActualVetoNodos() {
        paginaListVetoNodo = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasVetoNodo();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListVetoNodo.add(i);
        }
        pageActualVetoNodo = String.valueOf(actualVetoNodo) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaVetoNodo == null) {
            numPaginaVetoNodo = "1";
        }
        numPaginaVetoNodo = String.valueOf(actualVetoNodo);
        
        return pageActualVetoNodo;
    }
    
     /**
     * Función que realiza paginación de la tabla de ciudades.
     * 
     * @param  page;
     * @author Juan David Hernandez
     * @return 
     */
    public String obtenerListadoVetoCiudad(int page) {
        try {                   
             if (ciudadesVetoList != null && !ciudadesVetoList.isEmpty()) {
                 actualVetoCiudad = page;
                 getPageActualVetoCiudades();

                int firstResult = 0;
                if (page > 1) {
                    firstResult = (filasPag * (page - 1));
                }

                //Obtenemos el rango de la paginación
                int maxResult = 0;
                if ((firstResult + filasPag) > ciudadesVetoList.size()) {
                    maxResult = ciudadesVetoList.size();
                } else {
                    maxResult = (firstResult + filasPag);
                }
              
               /*Obtenemos los objetos que se encuentran en el rango de
                * la paginación y los guardarmos en la lista que se va a 
                * desplegar en pantalla*/
                ciudadPaginadaList = new ArrayList<GeograficoPoliticoMgl>();
                for (int i = firstResult; i < maxResult; i++) {
                    ciudadPaginadaList.add(ciudadesVetoList.get(i));
                }
            }

        } catch (Exception e) {
             LOGGER.error("Error en lista de paginación de ciudad", e);
            String msnError = "Error en lista de paginación de ciudad";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + e.getMessage(), ""));
        }
        return null;
    }

    
     /**
     * Función que carga la primera página de resultados de ciudades
     * 
     * @author Juan David Hernandez
     */
    public void pageFirstVetoCiudad() {
        try {
            obtenerListadoVetoCiudad(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     * 
     * @author Juan David Hernandez
     */
    public void pagePreviousVetoCiudad() {
        try {
            int totalPaginas = getTotalPaginasVetoCiudad();
            int nuevaPageActual = actualVetoCiudad - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            obtenerListadoVetoCiudad(nuevaPageActual);
        } catch (Exception ex) {
             LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

     /**
     * Función que permite ir directamente a la página seleccionada
     * de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void irPaginaVetoCiudad() {
        try {
            int totalPaginas = getTotalPaginasVetoCiudad();
            int nuevaPageActual = Integer.parseInt(numPaginaVetoCiudad);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoCiudad(nuevaPageActual);
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void pageNextVetoCiudad() {
        try {
            int totalPaginas = getTotalPaginasVetoCiudad();
            int nuevaPageActual = actualVetoCiudad + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoCiudad(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     * 
     *@author Juan David Hernandez
     */
    public void pageLastVetoCiudad() {
        try {
            int totalPaginas = getTotalPaginasVetoCiudad();
            obtenerListadoVetoCiudad(totalPaginas);
        } catch (Exception ex) {
          LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginasVetoCiudad() {
        try {
            int totalPaginas = 0;          
            int pageSol = ciudadesVetoList.size();
             
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public String getPageActualVetoCiudades() {
        paginaListVetoCiudad = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasVetoCiudad();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListVetoCiudad.add(i);
        }
        pageActualVetoCiudad = String.valueOf(actualVetoCiudad) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaVetoCiudad == null) {
            numPaginaVetoCiudad = "1";
        }
        numPaginaVetoCiudad = String.valueOf(actualVetoCiudad);
        
        return pageActualVetoCiudad;
    }
    /********************************************************/
    
        
     /**
     * Función que realiza paginación de la tabla.
     * 
     * @param  page;
     * @author Juan David Hernandez
     * @return 
     */
    public String obtenerListadoVetoCanal(int page) {
        try {       
            if (canalVetoList != null && !canalVetoList.isEmpty()) {
                actualVetoCanal = page;
                getPageActualVetoCanales(); 

                int firstResult = 0;
                if (page > 1) {
                    firstResult = (filasPag * (page - 1));
                }

                //Obtenemos el rango de la paginación
                int maxResult = 0;
                if ((firstResult + filasPag) > canalVetoList.size()) {
                    maxResult = canalVetoList.size();
                } else {
                    maxResult = (firstResult + filasPag);
                }
              
               /*Obtenemos los objetos que se encuentran en el rango de
                * la paginación y los guardarmos en la lista que se va a 
                * desplegar en pantalla*/
                canalVetoPaginadaList = new ArrayList<ParametrosCalles>();
                for (int i = firstResult; i < maxResult; i++) {
                    canalVetoPaginadaList.add(canalVetoList.get(i));
                }
            }
   
        } catch (Exception e) {
             LOGGER.error("Error en lista de paginación ", e);
            String msnError = "Error en lista de paginación";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + e.getMessage(), ""));
        }
        return null;
    }

    
     /**
     * Función que carga la primera página de resultados
     * 
     * @author Juan David Hernandez
     */
    public void pageFirstVetoCanal() {
        try {
            obtenerListadoVetoCanal(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     * 
     * @author Juan David Hernandez
     */
    public void pagePreviousVetoCanal() {
        try {
            int totalPaginas = getTotalPaginasVetoCanal();
            int nuevaPageActual = actualVetoCanal - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            obtenerListadoVetoCanal(nuevaPageActual);
        } catch (Exception ex) {
             LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

     /**
     * Función que permite ir directamente a la página seleccionada
     * de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void irPaginaVetoCanal() {
        try {
            int totalPaginas = getTotalPaginasVetoCanal();
            int nuevaPageActual = Integer.parseInt(numPaginaVetoCanal);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoCanal(nuevaPageActual);
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void pageNextVetoCanal() {
        try {
            int totalPaginas = getTotalPaginasVetoCanal();
            int nuevaPageActual = actualVetoCanal + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoCanal(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     * 
     *@author Juan David Hernandez
     */
    public void pageLastVetoCanal() {
        try {
            int totalPaginas = getTotalPaginasVetoCanal();
            obtenerListadoVetoCanal(totalPaginas);
        } catch (Exception ex) {
          LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginasVetoCanal() {
        try {
            int totalPaginas = 0;
            int pageSol = canalVetoList.size();
             
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public String getPageActualVetoCanales() {
        paginaListVetoCanal = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasVetoCanal();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListVetoCanal.add(i);
        }
        pageActualVetoCanal = String.valueOf(actualVetoCanal) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaVetoCanal == null) {
            numPaginaVetoCanal = "1";
        }
        numPaginaVetoCanal = String.valueOf(actualVetoCanal);
        
        return pageActualVetoCanal;
    }
    
    
    
     /**
     * Función que realiza paginación de la tabla de Vetos.
     * 
     * @param  page;
     * @author Juan David Hernandez
     * @return 
     */
    public String obtenerListadoVeto(int page) {
        try {       
            actualVeto = page;
            getPageActualVeto();      
                vetoList = vetoMglFacadeLocal
                        .findAllVetoPaginadaList(page, filasPag);  
                if(vetoList != null && !vetoList.isEmpty()){
    
                    for (VetoMgl vetoMgl : vetoList) {
                        SimpleDateFormat formatoFecha
                                = new SimpleDateFormat("dd/MM/yyyy");
                        String date1 = formatoFecha
                                .format(vetoMgl.getFechaFin());
                        String date2 =
                                formatoFecha.format(new Date());
                        int diferencia =
                                dateComparator(date2, date1);
                        if (diferencia <= 0) {
                          vetoMgl.setVigencia("VIGENTE");

                        }else{
                          vetoMgl.setVigencia("NO VIGENTE");  
                        }
                    }
                }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación de ciudad" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación de ciudad" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    
     /**
     * Función que carga la primera página de resultados de ciudades
     * 
     * @author Juan David Hernandez
     */
    public void pageFirstVeto() {
        try {  
            obtenerListadoVeto(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     * 
     * @author Juan David Hernandez
     */
    public void pagePreviousVeto() {
        try {
            int totalPaginas = getTotalPaginasVeto();
            int nuevaPageActual = actualVeto - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }         
            obtenerListadoVeto(nuevaPageActual);
        } catch (Exception ex) {
             LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

     /**
     * Función que permite ir directamente a la página seleccionada
     * de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void irPaginaVeto() {
        try {
            int totalPaginas = getTotalPaginasVeto();
            int nuevaPageActual = Integer.parseInt(numPaginaVeto);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }      
            obtenerListadoVeto(nuevaPageActual);
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void pageNextVeto() {
        try {
            int totalPaginas = getTotalPaginasVeto();
            int nuevaPageActual = actualVeto + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }           
            obtenerListadoVeto(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     * 
     *@author Juan David Hernandez
     */
    public void pageLastVeto() {
        try {            
            int totalPaginas = getTotalPaginasVeto();            
            obtenerListadoVeto(totalPaginas);
        } catch (Exception ex) {
          LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginasVeto() {
        try {            
            int totalPaginas = 0;  
          
            int pageSol = vetoMglFacadeLocal.countAllVetoList();
            
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            
            return totalPaginas;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public String getPageActualVeto() {
        paginaListVeto = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasVeto();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListVeto.add(i);
        }
        pageActualVeto = String.valueOf(actualVeto) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaVeto == null) {
            numPaginaVeto = "1";
        }
        numPaginaVeto = String.valueOf(actualVeto);
        
        return pageActualVeto;
    }
    
     /**
     * Función que redirecciona al menú principal del a aplicación.
     * 
     *@author Juan David Hernandez
     */
    public void redirecToMenu () 
    {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Visitas_Tecnicas/faces/Gestion.jsp");
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error al itentar regresar al menú"
                    + " principal" + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al itentar regresar al menú"
                    + " principal" + e.getMessage(), e, LOGGER);
        }     
    }
    
        
     /**
     * Función utilizada para limpiar listado de nodos cargados
     * 
     * @author Juan David Hernandez   
     */
    public void limpiarListadoNodos(){
        nodoList = new ArrayList();       
    }
    
     /**
     * Función utilizada para limpiar listado de Ciudades cargadas
     * 
     * @author Juan David Hernandez   
     */
     public void limpiarListadoCiudades(){
        ciudadList = new ArrayList();       
    }
     
      /**
     * Función utilizada para limpiar listado de canales cargados
     * 
     * @author Juan David Hernandez   
     */
      public void limpiarListadoCanales(){
        canalList = new ArrayList();       
    }
      
     /**
     * Función utilizada para limpiar listado de nodos cargados
     * 
     * @author Juan David Hernandez   
     */
    public void limpiarListadoVetoNodos(){
        nodoVetoList = new ArrayList();  
        nodoVetoPaginadaList = new ArrayList();
    }
    
     /**
     * Función utilizada para limpiar listado de Ciudades cargadas
     * 
     * @author Juan David Hernandez   
     */
     public void limpiarListadoVetoCiudades(){
        ciudadesVetoList = new ArrayList();   
        ciudadPaginadaList = new ArrayList();  
    }
     
      /**
     * Función utilizada para limpiar listado de canales cargados
     * 
     * @author Juan David Hernandez   
     */
      public void limpiarListadoVetoCanales(){
        canalVetoList = new ArrayList();    
        canalVetoPaginadaList = new ArrayList();
    }  
    
    // Validar Opciones por Rol    
    public boolean validarOpcionCrearPoliticaVeto() {
        return validarEdicionRol(BTVTNOCRPOVET);
    }
    
    public boolean validarOpcionDetallarEditar() {
        return validarEdicionRol(BTVTNOEDDET);
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
 
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getTipoTecnologia() {
        return tipoTecnologia;
    }

    public void setTipoTecnologia(String tipoTecnologia) {
        this.tipoTecnologia = tipoTecnologia;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public BigDecimal getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(BigDecimal centroPoblado) {
        this.centroPoblado = centroPoblado;
    }


    public List<CmtBasicaMgl> getTipoTecnologiaList() {
        return tipoTecnologiaList;
    }

    public void setTipoTecnologiaList(List<CmtBasicaMgl> tipoTecnologiaList) {
        this.tipoTecnologiaList = tipoTecnologiaList;
    }

    public List<CmtRegionalRr> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<CmtRegionalRr> divisionList) {
        this.divisionList = divisionList;
    }

    public List<RrCiudades> getComunidadList() {
        return comunidadList;
    }

    public void setComunidadList(List<RrCiudades> comunidadList) {
        this.comunidadList = comunidadList;
    }

    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public List<GeograficoPoliticoMgl> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<GeograficoPoliticoMgl> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public List<NodoMgl> getNodoList() {
        return nodoList;
    }

    public void setNodoList(List<NodoMgl> nodoList) {
        this.nodoList = nodoList;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public List<ParametrosCalles> getCanalList() {
        return canalList;
    }

    public void setCanalList(List<ParametrosCalles> canalList) {
        this.canalList = canalList;
    }

    public String getTipoCargue() {
        return tipoCargue;
    }

    public void setTipoCargue(String tipoCargue) {
        this.tipoCargue = tipoCargue;
    }

    public List<NodoMgl> getNodoVetoList() {
        return nodoVetoList;
    }

    public void setNodoVetoList(List<NodoMgl> nodoVetoList) {
        this.nodoVetoList = nodoVetoList;
    }

    public String getPageActualNodo() {
        return pageActualNodo;
    }

    public void setPageActualNodo(String pageActualNodo) {
        this.pageActualNodo = pageActualNodo;
    }

    public String getNumPaginaNodo() {
        return numPaginaNodo;
    }

    public void setNumPaginaNodo(String numPaginaNodo) {
        this.numPaginaNodo = numPaginaNodo;
    }

    public String getInicioPagina() {
        return inicioPagina;
    }

    public void setInicioPagina(String inicioPagina) {
        this.inicioPagina = inicioPagina;
    }

    public String getAnteriorPagina() {
        return anteriorPagina;
    }

    public void setAnteriorPagina(String anteriorPagina) {
        this.anteriorPagina = anteriorPagina;
    }

    public String getFinPagina() {
        return finPagina;
    }

    public void setFinPagina(String finPagina) {
        this.finPagina = finPagina;
    }

    public String getSiguientePagina() {
        return siguientePagina;
    }

    public void setSiguientePagina(String siguientePagina) {
        this.siguientePagina = siguientePagina;
    }

    public List<Integer> getPaginaListNodo() {
        return paginaListNodo;
    }

    public void setPaginaListNodo(List<Integer> paginaListNodo) {
        this.paginaListNodo = paginaListNodo;
    }

    public int getActualNodo() {
        return actualNodo;
    }

    public void setActualNodo(int actualNodo) {
        this.actualNodo = actualNodo;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public String getRegresarMenu() {
        return regresarMenu;
    }

    public void setRegresarMenu(String regresarMenu) {
        this.regresarMenu = regresarMenu;
    }

    public String getNumPaginaCiudad() {
        return numPaginaCiudad;
    }

    public void setNumPaginaCiudad(String numPaginaCiudad) {
        this.numPaginaCiudad = numPaginaCiudad;
    }

    public List<Integer> getPaginaListCiudad() {
        return paginaListCiudad;
    }

    public void setPaginaListCiudad(List<Integer> paginaListCiudad) {
        this.paginaListCiudad = paginaListCiudad;
    }

    public int getActualCiudad() {
        return actualCiudad;
    }

    public void setActualCiudad(int actualCiudad) {
        this.actualCiudad = actualCiudad;
    }

    public String getNumPaginaCanal() {
        return numPaginaCanal;
    }

    public void setNumPaginaCanal(String numPaginaCanal) {
        this.numPaginaCanal = numPaginaCanal;
    }

    public List<Integer> getPaginaListCanal() {
        return paginaListCanal;
    }

    public void setPaginaListCanal(List<Integer> paginaListCanal) {
        this.paginaListCanal = paginaListCanal;
    }

    public int getActualCanal() {
        return actualCanal;
    }

    public void setActualCanal(int actualCanal) {
        this.actualCanal = actualCanal;
    }

    public List<GeograficoPoliticoMgl> getCiudadesVetoList() {
        return ciudadesVetoList;
    }

    public void setCiudadesVetoList(List<GeograficoPoliticoMgl> ciudadesVetoList) {
        this.ciudadesVetoList = ciudadesVetoList;
    }

    public List<ParametrosCalles> getCanalVetoList() {
        return canalVetoList;
    }

    public void setCanalVetoList(List<ParametrosCalles> canalVetoList) {
        this.canalVetoList = canalVetoList;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPageActualVetoNodo() {
        return pageActualVetoNodo;
    }

    public void setPageActualVetoNodo(String pageActualVetoNodo) {
        this.pageActualVetoNodo = pageActualVetoNodo;
    }

    public String getNumPaginaVetoNodo() {
        return numPaginaVetoNodo;
    }

    public void setNumPaginaVetoNodo(String numPaginaVetoNodo) {
        this.numPaginaVetoNodo = numPaginaVetoNodo;
    }

    public List<Integer> getPaginaListVetoNodo() {
        return paginaListVetoNodo;
    }

    public void setPaginaListVetoNodo(List<Integer> paginaListVetoNodo) {
        this.paginaListVetoNodo = paginaListVetoNodo;
    }

    public int getActualVetoNodo() {
        return actualVetoNodo;
    }

    public void setActualVetoNodo(int actualVetoNodo) {
        this.actualVetoNodo = actualVetoNodo;
    }

    public String getPageActualVetoCiudad() {
        return pageActualVetoCiudad;
    }

    public void setPageActualVetoCiudad(String pageActualVetoCiudad) {
        this.pageActualVetoCiudad = pageActualVetoCiudad;
    }

    public String getNumPaginaVetoCiudad() {
        return numPaginaVetoCiudad;
    }

    public void setNumPaginaVetoCiudad(String numPaginaVetoCiudad) {
        this.numPaginaVetoCiudad = numPaginaVetoCiudad;
    }

    public List<Integer> getPaginaListVetoCiudad() {
        return paginaListVetoCiudad;
    }

    public void setPaginaListVetoCiudad(List<Integer> paginaListVetoCiudad) {
        this.paginaListVetoCiudad = paginaListVetoCiudad;
    }

    public int getActualVetoCiudad() {
        return actualVetoCiudad;
    }

    public void setActualVetoCiudad(int actualVetoCiudad) {
        this.actualVetoCiudad = actualVetoCiudad;
    }

    public String getPageActualVetoCanal() {
        return pageActualVetoCanal;
    }

    public void setPageActualVetoCanal(String pageActualVetoCanal) {
        this.pageActualVetoCanal = pageActualVetoCanal;
    }

    public String getNumPaginaVetoCanal() {
        return numPaginaVetoCanal;
    }

    public void setNumPaginaVetoCanal(String numPaginaVetoCanal) {
        this.numPaginaVetoCanal = numPaginaVetoCanal;
    }

    public List<Integer> getPaginaListVetoCanal() {
        return paginaListVetoCanal;
    }

    public void setPaginaListVetoCanal(List<Integer> paginaListVetoCanal) {
        this.paginaListVetoCanal = paginaListVetoCanal;
    }

    public int getActualVetoCanal() {
        return actualVetoCanal;
    }

    public void setActualVetoCanal(int actualVetoCanal) {
        this.actualVetoCanal = actualVetoCanal;
    }

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }
    
    public String getPolitica() {
        return politica;
    }

    public void setPolitica(String politica) {
        this.politica = politica;
    }

    public boolean isCanalAllSelected() {
        return canalAllSelected;
    }

    public void setCanalAllSelected(boolean canalAllSelected) {
        this.canalAllSelected = canalAllSelected;
    }

    public boolean isNodosAllSelected() {
        return nodosAllSelected;
    }

    public void setNodosAllSelected(boolean nodosAllSelected) {
        this.nodosAllSelected = nodosAllSelected;
    }

    public boolean isCiudadAllSelected() {
        return ciudadAllSelected;
    }

    public void setCiudadAllSelected(boolean ciudadAllSelected) {
        this.ciudadAllSelected = ciudadAllSelected;
    }

    public List<GeograficoPoliticoMgl> getCiudadPaginadaList() {
        return ciudadPaginadaList;
    }

    public void setCiudadPaginadaList(List<GeograficoPoliticoMgl> ciudadPaginadaList) {
        this.ciudadPaginadaList = ciudadPaginadaList;
    }

    public List<ParametrosCalles> getCanalVetoPaginadaList() {
        return canalVetoPaginadaList;
    }

    public void setCanalVetoPaginadaList(List<ParametrosCalles> canalVetoPaginadaList) {
        this.canalVetoPaginadaList = canalVetoPaginadaList;
    }

    public List<NodoMgl> getNodoVetoPaginadaList() {
        return nodoVetoPaginadaList;
    }

    public void setNodoVetoPaginadaList(List<NodoMgl> nodoVetoPaginadaList) {
        this.nodoVetoPaginadaList = nodoVetoPaginadaList;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<VetoMgl> getVetoList() {
        return vetoList;
    }

    public void setVetoList(List<VetoMgl> vetoList) {
        this.vetoList = vetoList;
    }

    public String getNodoBuscado() {
        return nodoBuscado;
    }

    public void setNodoBuscado(String nodoBuscado) {
        this.nodoBuscado = nodoBuscado;
    }

    public List<Integer> getPaginaListVeto() {
        return paginaListVeto;
    }

    public void setPaginaListVeto(List<Integer> paginaListVeto) {
        this.paginaListVeto = paginaListVeto;
    }

    public String getNumPaginaVeto() {
        return numPaginaVeto;
    }

    public void setNumPaginaVeto(String numPaginaVeto) {
        this.numPaginaVeto = numPaginaVeto;
    }

    public int getActualVeto() {
        return actualVeto;
    }

    public void setActualVeto(int actualVeto) {
        this.actualVeto = actualVeto;
    }

    public boolean isVenta() {
        return venta;
    }

    public void setVenta(boolean venta) {
        this.venta = venta;
    }

    public boolean isCreacion() {
        return creacion;
    }

    public void setCreacion(boolean creacion) {
        this.creacion = creacion;
    }

    public String getVetoBuscado() {
        return vetoBuscado;
    }

    public void setVetoBuscado(String vetoBuscado) {
        this.vetoBuscado = vetoBuscado;
    }
    
    /**
   * Buscar la básica
   * <p>
   * Busca el objeto CmtBasicaMgl correpondiente según el parámetro insertado.
   *
   * @author becerraarmr
   * @param idBasica
   *
   * @return
   */
  public CmtBasicaMgl getCmtBasicaMgl(String idBasica) {
    BigDecimal valor;
    try {
      valor=new BigDecimal(idBasica);
      CmtBasicaMgl basica = cmtBasicaMglFacadeLocal.findById(valor);
      if (basica != null) {
          return basica;
        }
    } catch (ApplicationException e) {
        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
        LOGGER.error(msg, e);
    }
    return null;
  }
  
}
