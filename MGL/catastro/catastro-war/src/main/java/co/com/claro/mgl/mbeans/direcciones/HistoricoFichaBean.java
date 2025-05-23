package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ExportFichasMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ExportFichasMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.DataArchivoExportFichas;
import co.com.claro.mgl.mbeans.util.ExportArchivosFichas;
import co.com.claro.mgl.mbeans.util.PrimeFacesUtil;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.MailSender;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.activation.DataSource;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@ManagedBean(name = "historicoFichaBean" )
@ViewScoped
@Log4j2
public class HistoricoFichaBean implements Serializable{
    
    private List<ExportFichasMgl> historicosFichas;
    private List<GeograficoPoliticoMgl> listadoDepartamentos;
    private List<GeograficoPoliticoMgl> listadoCiudades;
    private List<GeograficoPoliticoMgl> listadoCentrosPoblado;
    private List<CmtBasicaMgl> listadoTiposTecnologia;
    private List<NodoMgl> listadoNodos;
    private ExportFichasMgl filtro;
    
    //PAGINATOR
    private int actual;
    private int filasPag = ConstantsCM.PAGINACION_DIEZ_FILAS;
    private List<Integer> paginaList;
    private String pageActual;
    private String numPagina = "1";
    
    @EJB
    private ExportFichasMglFacadeLocal exportFichasMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @Inject
    private BlockReportServBean blockReportServBean;
    
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    
    private String usuarioVT = null;
    private int perfilVT = 0;
      
    public HistoricoFichaBean(){
          try {
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }
    
    @PostConstruct
    public void init(){
        try {
            historicosFichas = new ArrayList<ExportFichasMgl>();
            listadoDepartamentos = new ArrayList<>();
            listadoCiudades = new ArrayList<>();
            listadoCentrosPoblado = new ArrayList<>();
            listadoTiposTecnologia = new ArrayList<>();
            listadoNodos = new ArrayList<>();
            listadoDepartamentos = geograficoPoliticoMglFacadeLocal.findDptos(); 
            listadoTiposTecnologia =  cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA));
            filtro = new ExportFichasMgl();
            consultarHistoricoFichas(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en init. ", e, LOGGER);
        }
    }
    
    public void consultarHistoricoFichas(int page){
        try {
            actual = page;
            getPageActual();
            historicosFichas = exportFichasMglFacadeLocal.findListadoHistoricosFicha(filtro , page , filasPag);
            PrimeFacesUtil.update("frm_historico_ficha:list_historico");
        } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error en consultarHistoricoFichas. ", e, LOGGER);
        }
    }
    
    public String getPageActual() {
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
        
        return pageActual;
    }
    
    public int getTotalPaginas() {
        try {
            int totalPaginas;
            int pageSol = exportFichasMglFacadeLocal.countListadoHistoricosFicha(filtro);
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + e.getMessage() , e, LOGGER);
        }
        return 1;
    }
    
     public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            consultarHistoricoFichas(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage() , e, LOGGER);
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
            consultarHistoricoFichas(nuevaPageActual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la página anterior" + e.getMessage() , e, LOGGER);
        }
    }
    
    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            consultarHistoricoFichas(nuevaPageActual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la siguiente página" + e.getMessage() , e, LOGGER);
        }
    }
    
    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            consultarHistoricoFichas(totalPaginas);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a la última página" + ex.getMessage() , ex, LOGGER);
        }
    }
    
    
    public void cargarListadoCiudades(AjaxBehaviorEvent event){
        try {
           if(filtro.getDepartamento().getGpoId().intValue() == 0){
               listadoCiudades = new ArrayList<>();
               listadoCentrosPoblado= new ArrayList<>();
           }else{
                listadoCiudades = geograficoPoliticoMglFacadeLocal.findCiudades(filtro.getDepartamento().getGpoId());
           }
           consultarHistoricoFichas(1);
        }catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error en cargarListadoCiudades. ", e, LOGGER);
        }
    }
    
    public void cargarListadoCentroPoblado(AjaxBehaviorEvent event){
        try {
           if(filtro.getCiudad().getGpoId().intValue() == 0){
               listadoCentrosPoblado= new ArrayList<>();
           }else{
               listadoCentrosPoblado = geograficoPoliticoMglFacadeLocal.findCentrosPoblados(filtro.getCiudad().getGpoId());
           }
           consultarHistoricoFichas(1);
        }catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error en cargarListadoCentroPoblado. ", e, LOGGER);
        }
    }
    
    public void cargarListadoNodos(){
        try {
            if(filtro.getTipoTecnologiaId().getBasicaId().intValue() != 0 && filtro.getCentroPoblado().getGpoId().intValue() != 0){
                CmtBasicaMgl tecnologia = cmtBasicaMglFacadeLocal.findById(filtro.getTipoTecnologiaId().getBasicaId());
                listadoNodos = nodoMglFacadeLocal.findNodosCentroPobladoAndTipoTecnologia(filtro.getCentroPoblado().getGpoId(), tecnologia);
            }
             consultarHistoricoFichas(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarListadoNodos. ", e, LOGGER);
        }
    }
    
    
    public void irADetalleHistorico(ExportFichasMgl historico) throws IOException{
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpSession session = (HttpSession) context.getSession(false);
            session.setAttribute("historicoExportFicha", historico);
            context.redirect(context.getRequestContextPath() +"/view/ficha/detalleHistoricoFicha.xhtml");
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en irADetalleHistorico. ", e, LOGGER);
        }
        
    }
    
    public void enviarCorreoConInforme(ExportFichasMgl historico){
        try {
            ParametrosMgl asunto = new ParametrosMgl();
            ParametrosMgl cuerpo = new ParametrosMgl();
            List<CmtBasicaMgl> listadoCorreosDestino = new ArrayList<>();
            asunto = parametrosMglFacadeLocal.findByAcronimoName("ASUNTO_CORREO_FICHAS_INFORME");
            cuerpo = parametrosMglFacadeLocal.findByAcronimoName("CUERPO_CORREO_FICHAS_INFORME");
            listadoCorreosDestino = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMglFacadeLocal.findByNombreTipoBasica("CORREOS FICHAS"));
            List<String> destinatarios = new ArrayList<>();
            List<String> copias = new ArrayList<>();
            if(listadoCorreosDestino != null && !listadoCorreosDestino.isEmpty()){
                for (CmtBasicaMgl correos : listadoCorreosDestino) {
                    if(correos.getDescripcion() != null && !correos.getDescripcion().isEmpty() && correos.getDescripcion().contains(";")){
                        String[] correosParametrizados = correos.getDescripcion().split("\\;");
                        for (int i = 0; i < correosParametrizados.length; i++) {
                            if(i > 0){
                                if(esCorreoElectronico(correosParametrizados[i])){
                                    copias.add(correosParametrizados[i]);
                                }
                            }else{
                                if(esCorreoElectronico(correosParametrizados[i])){
                                    destinatarios.add(correosParametrizados[i]);
                                }
                            }
                        }
                    }else{
                        if(esCorreoElectronico(correos.getDescripcion())){
                            destinatarios.add(correos.getDescripcion());
                        }
                    }
                }
            }
            InternetAddress[] destinatariosInforme;
            InternetAddress[] copiasInforme;
            InternetAddress[] bcc = new InternetAddress[0];
            if(!destinatarios.isEmpty()){
                destinatariosInforme = new InternetAddress[destinatarios.size()];
                for (int i = 0; i < destinatarios.size(); i++) {
                    destinatariosInforme[i] = new InternetAddress(destinatarios.get(i));
                }
            }else{
                destinatariosInforme = new InternetAddress[0];
            }
            if(!copias.isEmpty()){
                copiasInforme = new InternetAddress[copias.size()];
                for(int i = 0; i < copias.size(); i++) {
                    copiasInforme[i] = new InternetAddress(copias.get(i));
                }
            }else{
                copiasInforme = new InternetAddress[0];
            }
            
            if (destinatarios != null && !destinatarios.isEmpty()) {
                ParametrosMgl correoServer = parametrosMglFacadeLocal.findByAcronimoName(
                        ParametrosMerEnum.MAIL_SMTPSERVER.getAcronimo());
                ParametrosMgl mailFrom = parametrosMglFacadeLocal.findByAcronimoName(
                        ParametrosMerEnum.MAIL_FROM.getAcronimo());

                DataArchivoExportFichas datos = new Gson().fromJson(historico.getTextoArchivo(), DataArchivoExportFichas.class);
                String nombreArchivo = historico.getNombreArchivo();
                ExportArchivosFichas exportador = new ExportArchivosFichas(geograficoPoliticoMglFacadeLocal);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                exportador.generarArchivoCorreo(output, datos);
                String asuntoCorreo = asunto.getParValor();
                String cuerpoCorreo = cuerpo.getParValor();
                if(asuntoCorreo.contains("$P{NombreArchivo}")){
                   asuntoCorreo = asuntoCorreo.replace("$P{NombreArchivo}", historico.getNombreArchivo());
                }
                if(cuerpoCorreo.contains("$P{Nodo}")){
                   cuerpoCorreo = cuerpoCorreo.replace("$P{Nodo}", historico.getNodo().getNodCodigo());
                }
                DataSource adjunto = new ByteArrayDataSource(output.toByteArray(), "application/vnd.ms-excel");
                boolean enviado= MailSender.enviarCorreo(correoServer.getParValor(), mailFrom.getParValor(), destinatariosInforme, copiasInforme, bcc,
                        asuntoCorreo, cuerpoCorreo, true, true, adjunto, nombreArchivo, true);
                if(enviado){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo Enviado de Manera Correcta", ""));
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No hay destinatarios parametrizados para enviar correo", ""));
            }
            

        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en enviarCorreoConInforme. "+e.getMessage(), e, LOGGER);
        }catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en enviarCorreoConInforme. "+e.getMessage(), e, LOGGER);
        }
    }
    
    public boolean esCorreoElectronico(String correo){
        if(correo.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")){
            return true;
        }else{
            return false;
        }
    }
    
    public void descargaArchivoHistorico(ExportFichasMgl historico) throws IOException{
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Descargar Histórico Export Fichas")) return;

            DataArchivoExportFichas datos = new Gson().fromJson(historico.getTextoArchivo(), DataArchivoExportFichas.class);
            String nombreArchivo = historico.getNombreArchivo();
            ExportArchivosFichas exportador = new ExportArchivosFichas(geograficoPoliticoMglFacadeLocal);
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.reset();
            response1.setContentType("application/vnd.ms-excel");
            response1.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);
            exportador.generarArchivo(response1.getOutputStream(),datos);
            fc.responseComplete();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en descargaArchivoHistorico. ", e, LOGGER);
        }
        
    }

    public List<ExportFichasMgl> getHistoricosFichas() {
        return historicosFichas;
    }

    public void setHistoricosFichas(List<ExportFichasMgl> historicosFichas) {
        this.historicosFichas = historicosFichas;
    }

    public List<GeograficoPoliticoMgl> getListadoDepartamentos() {
        return listadoDepartamentos;
    }

    public void setListadoDepartamentos(List<GeograficoPoliticoMgl> listadoDepartamentos) {
        this.listadoDepartamentos = listadoDepartamentos;
    }

    public List<GeograficoPoliticoMgl> getListadoCiudades() {
        return listadoCiudades;
    }

    public void setListadoCiudades(List<GeograficoPoliticoMgl> listadoCiudades) {
        this.listadoCiudades = listadoCiudades;
    }

    public List<GeograficoPoliticoMgl> getListadoCentrosPoblado() {
        return listadoCentrosPoblado;
    }

    public void setListadoCentrosPoblado(List<GeograficoPoliticoMgl> listadoCentrosPoblado) {
        this.listadoCentrosPoblado = listadoCentrosPoblado;
    }

    public ExportFichasMgl getFiltro() {
        if(filtro == null){
            filtro = new ExportFichasMgl();
        }
        return filtro;
    }

    public void setFiltro(ExportFichasMgl filtro) {
        this.filtro = filtro;
    }

    public List<CmtBasicaMgl> getListadoTiposTecnologia() {
        return listadoTiposTecnologia;
    }

    public void setListadoTiposTecnologia(List<CmtBasicaMgl> listadoTiposTecnologia) {
        this.listadoTiposTecnologia = listadoTiposTecnologia;
    }

    public List<NodoMgl> getListadoNodos() {
        return listadoNodos;
    }

    public void setListadoNodos(List<NodoMgl> listadoNodos) {
        this.listadoNodos = listadoNodos;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }


    public void setPageActual(String pageActual) {
        this.pageActual = pageActual;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }
    
    //Opciones agregadas para Rol
    private final String CORBTNHEF = "CORBTNHEF";    
    
    // Validar Opciones por Rol    
    public boolean validarOpcionCorreo() {
        return validarEdicionRol(CORBTNHEF);
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
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    
}
