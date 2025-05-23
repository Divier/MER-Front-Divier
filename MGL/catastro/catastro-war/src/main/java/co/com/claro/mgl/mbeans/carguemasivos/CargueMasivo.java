/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.carguemasivos;

import co.com.claro.mer.dtos.request.procedure.TblLineasMasivoResquestDto;
import co.com.claro.mer.dtos.response.procedure.TblLineasMasivoResponseDto;
import co.com.claro.mer.dtos.response.service.RolPortalResponseDto;
import co.com.claro.mer.dtos.sp.cursors.OpcionesCarguesDto;
import co.com.claro.mer.utils.*;
import co.com.claro.mer.dtos.sp.cursors.CamposFiltrosDto;
import co.com.claro.mer.dtos.sp.cursors.CargueInformacionDto;
import co.com.claro.mer.dtos.sp.cursors.InfoGeneralCargueDto;
import co.com.claro.mer.utils.constants.ConstantsCargueMasivo;
import co.com.claro.mer.utils.constants.SessionConstants;
import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.procesomasivo.CarguesMasivosFacadeLocal;
import co.com.claro.mgl.utils.CsvUtils;
import co.com.claro.mgl.utils.SFTPConnector;
import com.jcraft.jsch.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import com.oracle.xmlns.odi.odiinvoke.client.OdiInvokeClient;
import com.oracle.xmlns.odi.odiinvoke.dto.request.OdiCredentialType;
import com.oracle.xmlns.odi.odiinvoke.dto.request.OdiStartLoadPlanRequest;
import com.oracle.xmlns.odi.odiinvoke.dto.request.StartLoadPlanRequestType;
import com.oracle.xmlns.odi.odiinvoke.dto.response.OdiStartLoadPlanResponse;
import com.oracle.xmlns.odi.odiinvoke.factory.ObjectFactoryOdiInvoke;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static co.com.claro.mer.utils.constants.ConstantsCargueMasivo.*;

/**
 * Clase Bean que permite controlar la iteracion que tiene el usuario con la vista
 * Cargar Masivo de Archivos
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
@ManagedBean(name = "carguemasivo")
@ViewScoped
public class CargueMasivo implements Serializable{

    private String tipoCarga;
    private String message;
    //Manejo deArchivos
    private StreamedContent file;
    private UploadedFile cargaFile;
    private StreamedContent fileDownload;
    @Getter
    @Setter
    public Date fechaActual = new Date();

    @EJB
    CarguesMasivosFacadeLocal servicio;

    @Setter
    @Getter
    List<OpcionesCarguesDto> listaTiposCarga = new ArrayList<>(); //primer SP
    List<InfoGeneralCargueDto> listaInformacionCargue = new ArrayList<>();  //Segundo SP
    List<CargueInformacionDto> listaInformacionCargados = new ArrayList<>();//Tercer SP
    List<CamposFiltrosDto> listaInfoFiltros = new ArrayList<>();//cuarto SP

    List<TblLineasMasivoResponseDto> respuestaConsultaFiltro = new ArrayList<>();//quinto


    //Datos de las consultas que pinta en las talas de filtros y cargue
    List<CargueInformacionDto> registrosCargue = new ArrayList<>();//auxiliares
    List<CargueInformacionDto> registrosFiltro = new ArrayList<>();//auxiliares

    private TblLineasMasivoResponseDto listTblLineasMasivoResponse = new TblLineasMasivoResponseDto(); // Programar Consulta. Obtiene primer linMasivoId

    //datos de la informacion de la consulta filtros
    BigDecimal masivoIdConsulta= null;
    String TipoConsulta;
    String rutaInConsulta;
    String rutaOutConsulta;
    String estNomArchivoConsulta;
    BigDecimal contRegConsulta;
    String TablaConsulta;
    String DelimConsulta;
    String RolConsulta;
    @Setter
    @Getter
    private String pBaseConsulta;
    @Getter
    @Setter
    private boolean puedeCosultar;
    @Setter
    @Getter
    String rutaInCargue;
    //datos de la informacion del cargue
    BigDecimal masivoIdCargue= null;
    String TipoCargue;

    String rutaOutCargue;
    String estNomArchivoCargue;
    BigDecimal contRegCargue;
    String TablaCargue;
    String DelimCargue;
    String RolCargue;
    @Getter
    @Setter
    String pBaseCargue;
    @Getter
    @Setter
    private BigDecimal encabezadoCargue;
    @Getter
    @Setter
    private BigDecimal encabezadoConsulta;

    @Getter
    @Setter
    private BigDecimal validacionEstNomArchivoCargue;

    @Getter
    @Setter
    private boolean puedeCargar;

    private CargueInformacionDto selectedRegistro;

    private String previousPage = null; // Validar si han ejecutado F5 o Reload de la pagina web
    private boolean recargoPaginaF5 = false; // Validar si han ejecutado F5 o Reload de la pagina web


    @PostConstruct
    public void init() {
        this.tipoCarga="";
        this.puedeCargar = Boolean.FALSE;
        this.puedeCosultar = Boolean.FALSE;
        try {

            List<String> roles = SesionUtil.getRoles().stream()
                    .map(RolPortalResponseDto::getCodRol)
                    .filter(codRol -> codRol.startsWith(SessionConstants.PREFIJO_ROLES_CARGUE_MASIVO))
                    .collect(Collectors.toList());

            String cadenaRoles = StringToolUtils.convertListToString(roles, DelimitadorEnum.PIPE, true);

            listaTiposCarga=servicio.contblMasivosNombresSp(cadenaRoles);//nombre del tipo de carga
        } catch (ApplicationException ex) {
            Logger.getLogger(CargueMasivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRutaOutConsulta() {
        return rutaOutConsulta;
    }

    public void setRutaOutConsulta(String rutaOutConsulta) {
        this.rutaOutConsulta = rutaOutConsulta;
    }

    /**
     * Permite ejecutar un evento asociado a la seleccion de tipo de cargue. Para obtener
     * Departamentos, Ciudades y demas filtros para poder programar una consulta. La seleccion del tipo de cargue
     * tambien es necesario para poder cargar y procesar archivos.
     *
     */
    public void consultaInformacionInicial()  {
        try {
            validarAciones();
            limpiarVariablesIniciales();

            if (StringUtils.isBlank(tipoCarga)) return;

            listaInformacionCargue=servicio.contblMasivosSp(tipoCarga);//consulta de las tablas general
            for(int i=0;i<listaInformacionCargue.size();i++){
                if(listaInformacionCargue.get(i).getTipo().equals(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CONSULTA)){
                    masivoIdConsulta = listaInformacionCargue.get(i).getMasivoId();
                    TipoConsulta = listaInformacionCargue.get(i).getTipo();
                    rutaInConsulta = listaInformacionCargue.get(i).getRutaIn();
                    rutaOutConsulta = listaInformacionCargue.get(i).getRutaOut();
                    estNomArchivoConsulta = listaInformacionCargue.get(i).getEstNomArchivo();
                    contRegConsulta = listaInformacionCargue.get(i).getContReg();
                    TablaConsulta = listaInformacionCargue.get(i).getTabla();
                    DelimConsulta = listaInformacionCargue.get(i).getDelim();
                    RolConsulta = listaInformacionCargue.get(i).getRol();
                    pBaseConsulta = listaInformacionCargue.get(i).getPBase();
                    setEncabezadoConsulta(listaInformacionCargue.get(i).getEncabezado());
                }else{
                    masivoIdCargue = listaInformacionCargue.get(i).getMasivoId();
                    TipoCargue = listaInformacionCargue.get(i).getTipo();
                    rutaInCargue = listaInformacionCargue.get(i).getRutaIn();
                    rutaOutCargue = listaInformacionCargue.get(i).getRutaOut();
                    estNomArchivoCargue = listaInformacionCargue.get(i).getEstNomArchivo();
                    contRegCargue = listaInformacionCargue.get(i).getContReg();
                    TablaCargue = listaInformacionCargue.get(i).getTabla();
                    DelimCargue = listaInformacionCargue.get(i).getDelim();
                    RolCargue = listaInformacionCargue.get(i).getRol();
                    pBaseCargue = listaInformacionCargue.get(i).getPBase();
                    setEncabezadoCargue(listaInformacionCargue.get(i).getEncabezado());
                    validacionEstNomArchivoCargue = listaInformacionCargue.get(i).getValidacionEstNomArchivo();
                }
            }
            listaInformacionCargados=servicio.contblLineasMasivoSp(tipoCarga, "", null); //lista de los registros de filtro y cargue
            for(int i=0;i<listaInformacionCargados.size();i++){
                if(listaInformacionCargados.get(i).getTipo().equals(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CONSULTA)){
                    registrosFiltro.add(new CargueInformacionDto(
                            listaInformacionCargados.get(i).getLinMasivoId(),
                            listaInformacionCargados.get(i).getNombre(),
                            listaInformacionCargados.get(i).getArc(),
                            listaInformacionCargados.get(i).getArcOut(),
                            listaInformacionCargados.get(i).getTipo(),
                            listaInformacionCargados.get(i).getFechaReg(),
                            listaInformacionCargados.get(i).getFiltro(),
                            listaInformacionCargados.get(i).getEstado(),
                            listaInformacionCargados.get(i).getTotReg() ));
                }else{
                    registrosCargue.add(new CargueInformacionDto(
                            listaInformacionCargados.get(i).getLinMasivoId(),
                            listaInformacionCargados.get(i).getNombre(),
                            listaInformacionCargados.get(i).getArc(),
                            listaInformacionCargados.get(i).getArcOut(),
                            listaInformacionCargados.get(i).getTipo(),
                            listaInformacionCargados.get(i).getFechaReg(),
                            listaInformacionCargados.get(i).getFiltro(),
                            listaInformacionCargados.get(i).getEstado(),
                            listaInformacionCargados.get(i).getTotReg() ));
                }
            }

            if (puedeCosultar) {
                listaInfoFiltros=servicio.conFiltrosSp(masivoIdConsulta);
            }
        } catch (ApplicationException ex) {
            Logger.getLogger(CargueMasivo.class.getName()).log(Level.SEVERE, null, ex);
            // mostrar mensaje de error diciendo que no se pudo cargar la lista de tipos de cargue
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    CarguesMasivosMensajeria.ERROR_PARAMETRIZACION_FILTROS, ""));
        }
    }

    private void validarAciones() {
        int opcionesCargue = listaTiposCarga.stream()
                .filter(cargues -> cargues.getNombreCargue().equals(tipoCarga))
                .map(OpcionesCarguesDto::getAcciones)
                .findFirst()
                .orElse(0);

        if (opcionesCargue == 1) {
            puedeCosultar = Boolean.FALSE;
            puedeCargar = Boolean.TRUE;
        } else if (opcionesCargue == 2) {
            puedeCosultar = Boolean.TRUE;
            puedeCargar = Boolean.FALSE;
        } else if (opcionesCargue == 3) {
            puedeCosultar = Boolean.TRUE;
            puedeCargar = Boolean.TRUE;
        }else{
            puedeCosultar = Boolean.FALSE;
            puedeCargar = Boolean.FALSE;
        }

    }

    /**
     * Consulta la listas de elementos para los filtros de tipo seleccion
     *
     * @param filtroSeleccionado {@link CamposFiltrosDto} Filtro seleccionado
     * @throws ApplicationException Excepción de la App
     * @autor Manuel Hernández Rivas
     */
    public void consultaLista(CamposFiltrosDto filtroSeleccionado) {
        if (StringUtils.isNotBlank(filtroSeleccionado.getValor())) {
            listaInfoFiltros.forEach(proximoFiltro -> {
                if ((proximoFiltro.getIdColumnaPadre().compareTo(filtroSeleccionado.getIdColumna()) == 0) &&
                        filtroSeleccionado.getHtml_e().equals(TIPO_SELECCION)) {
                    try {
                        proximoFiltro.setListaItemsFIltro(servicio.consultarListasFiltros(proximoFiltro.getIdColumna(), filtroSeleccionado.getValor()));
                        limpiarListasHijas(proximoFiltro);
                    } catch (ApplicationException ex) {

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Error al consultar la lista de filtros",
                                ex.getMessage()));
                    }

                }
            });
        }
    }

    /**
     * Limpia las listas hijas de los filtros seleccionados
     * @param filtroPadre {@link CamposFiltrosDto} Filtro padre
     * @author Manuel Hernández Rivas
     */
    public void limpiarListasHijas(CamposFiltrosDto filtroPadre) {
        BigDecimal idColumnaPadre = filtroPadre.getIdColumna();
        for (CamposFiltrosDto filtro : listaInfoFiltros) {
            if (filtro.getIdColumnaPadre().compareTo(idColumnaPadre) == 0) {
                filtro.setListaItemsFIltro(null);
                idColumnaPadre = filtro.getIdColumna();
            }
        }
    }



    /**
     * Permite generar archivo CSV de acuerdo con la informacion retornada
     * por los componentes de ODI y Base de Datos
     */
    public void generarCsv(){
        try{

            if (estNomArchivoCargue != null) {
                Calendar fecha = Calendar.getInstance();
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();

                Date date = new Date();
                SimpleDateFormat DateFor = new SimpleDateFormat("ddMMyyyy");
                String stringDate= DateFor.format(date);

                String primeraparte = "";

                primeraparte = stringDate;

                String segundaparte = String.format("%02d%02d%02d", fecha.get(Calendar.HOUR_OF_DAY), fecha.get(Calendar.MINUTE), fecha.get(Calendar.SECOND));

                response.setContentType("text/csv");
                fc.responseComplete();
                String p = estNomArchivoCargue.replaceAll("ddmmaaaa", primeraparte);
                String nombre = p.replaceAll("hhmmss", segundaparte);
                response.setHeader("Content-disposition", "attachment; filename=\""+nombre+ "\"");
                ServletOutputStream output = response.getOutputStream();
                Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"base cargue: "+pBaseCargue+"   "+ estNomArchivoCargue);
                String s = pBaseCargue;
                InputStream is = new ByteArrayInputStream(s.getBytes("UTF-8"));
                int nextChar;
                while ((nextChar = is.read()) != -1) {
                    output.write(nextChar);
                }
                output.flush();
                output.close();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        CarguesMasivosMensajeria.CARGUE_MASIVO_DEBE_SELECCIONAR_PLANTILLA, ""));
            }

        }catch (IOException ex) {
            Logger.getLogger(CargueMasivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Permite actualizar solo componentes visuales de la aplicacion web cuando refrescan
     * la pagina ya sea utilizando el F5 o click en el icono del navegador
     *
     * @throws ApplicationException Excepción de la App
     */
    public void validarRecargarPaingaF5() throws ApplicationException {
        String idVista = CarguesMasivosMensajeria.CARGUE_MASIVO_VIEW_DIRECCIONES_CARGUE_MASIVO_PAGINA;
        String msg = "";
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        previousPage = viewRoot.getViewId();

        if (idVista.equals(previousPage)) {
            recargoPaginaF5 = true;
            FacesMessage msgSecurity = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    CarguesMasivosMensajeria.CARGUE_MASIVO_MENSAJERIA_MENSAJE_INFORMATIVO, CarguesMasivosMensajeria.CARGUE_MASIVO_MENSAJERIA_REFRESH);
            PrimeFaces.current().dialog().showMessageDynamic(msgSecurity);
            // Ejecutamos la consulta para refrescar el elemento lista consulta
            this.consultarRegistrosSegunEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CONSULTA);
            // Ejecutamos la consulta para refrescar el elemento lista cargue
            this.consultarRegistrosSegunEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CARGUE);
        } else {
            recargoPaginaF5 = false;
        }
    }

    /**
     * Permite programar una consulta de acuerdo al tipo de carga seleccionado y los
     * filtros seleccionados. La obligatoriedad de los filtros es parametrizable desde base de datos
     *
     * @throws ApplicationException Excepción de la App
     */
    public void programarConsulta() {

        StringBuilder filtroLogico= new StringBuilder();
        StringBuilder filtroTraducidos = new StringBuilder();
        String listaFiltrosObligatorios = validarFiltrosObligatorios();

        try {
            if ( StringUtils.isNotBlank(listaFiltrosObligatorios)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        String.format(CarguesMasivosMensajeria.CARGUE_MASIVO_FILTRO_OBLIGATORIO_PROGRAMAR_CONSULTA, listaFiltrosObligatorios), ""));
                return;
            }

            if (listaInfoFiltros.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        CarguesMasivosMensajeria.CARGUE_MASIVO_NO_CARGARON_LISTAS_PARA_CONSULTA_PROGRAMADA, ""));
                return;
            }


            for (CamposFiltrosDto camposFiltros : listaInfoFiltros){
                if (StringUtils.isNotBlank(camposFiltros.getValor()) || camposFiltros.getFecha() != null) {
                    Map<String, String> mapFiltros = CarguesMasivosUtils.generarCadenaFilro(camposFiltros);
                    filtroLogico.append(mapFiltros.get(FILTRO_LOGICO));
                    filtroTraducidos.append(mapFiltros.get(FILTRO_LEGIBLE));
                }
            }

            Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO, () -> filtroLogico + "        " + masivoIdConsulta);
            if (StringUtils.isNotBlank(filtroLogico) && StringUtils.isNotBlank(filtroTraducidos)) {

                TblLineasMasivoResquestDto resquestDto = new TblLineasMasivoResquestDto(
                        CarguesMasivosMensajeria.CARGUE_MASIVO_ACCION_ODI_INSERTAR,
                        masivoIdConsulta,
                        CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CONSULTA,
                        "",
                        filtroLogico.toString(),
                        filtroTraducidos.toString(),
                        null,
                        SesionUtil.getUsuarioLogueado(),
                        CarguesMasivosMensajeria.CARGUE_MASIVO_MGL_PARAMETROS_ODI_ACTREPNOD,
                        null,"","");

                listTblLineasMasivoResponse = servicio.tblLineasMasivoSp(resquestDto);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        CarguesMasivosMensajeria.CARGUE_MASIVO_MENSAJERIA_SELECCIONAR_CAMPO_CONSULTA, ""));
                return;
            }
            consultarRegistrosSegunEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CONSULTA);
        } catch (ApplicationException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    CarguesMasivosMensajeria.ERROR_PROGRAMAR_CONSULTA, ""));
        }
    }

    /**
     * Permite procesar el archivo seleccionado y cargarlos al sistema para enviarlo a un FTP, ser procesado
     * por ODI y luego verse reflejada la informacion en las tablas de base de datos del sistema.
     *
     * @param usuario {@link String}
     * @throws ApplicationException Excepción de la App
     */
    public void procesarArchivo(String usuario) throws ApplicationException, IOException, IllegalAccessException, JSchException, SftpException{
        Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"Metodo: procesarArchivo");

        TblLineasMasivoResponseDto respuestaIngresoConsulta;//quinto
        if (cargaFile.getFileName() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    CarguesMasivosMensajeria.CARGUE_MASIVO_NO_HAY_ARCHIVO_PARA_PROCESAR, ""));
            return;
        }

        boolean ejecutarProcesarArchivo = validarTipoCargaYProcesarArchivo(tipoCarga, cargaFile);

        if (ejecutarProcesarArchivo) {
            int conteofilas = contarFilas(cargaFile.getInputStream());
            if (conteofilas <= contRegCargue.intValue()) {
                Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,masivoIdCargue + "    " + cargaFile.getFileName() + "      " + conteofilas + "     " + usuario);

                TblLineasMasivoResquestDto resquestDto = new TblLineasMasivoResquestDto(
                        CarguesMasivosMensajeria.CARGUE_MASIVO_ACCION_ODI_INSERTAR,
                        masivoIdCargue,
                        CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CARGUE,
                        cargaFile.getFileName(), "",null,
                        BigDecimal.valueOf(conteofilas),usuario,
                        CarguesMasivosMensajeria.CARGUE_MASIVO_MGL_PARAMETROS_ODI_ACTREPNOD,
                        null,"","");

                respuestaIngresoConsulta = servicio.tblLineasMasivoSp(resquestDto);
                if (!respuestaIngresoConsulta.getPoCodigo().equals(RESULTADO_EXITOSO) ){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            respuestaIngresoConsulta.getPoResultado(), ""));
                    return;
                }

                SFTPConnector sshConnector = new SFTPConnector();
                sshConnector.connect(ConstantsCargueMasivo.USERNAME, ConstantsCargueMasivo.PASSWORD, ConstantsCargueMasivo.HOST, ConstantsCargueMasivo.PORT);
                sshConnector.addfile(rutaInCargue, cargaFile.getInputStream(), cargaFile.getFileName());
                sshConnector.disconnect();
                consultarRegistrosSegunEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CARGUE);

                Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO, Utils.toJson(respuestaIngresoConsulta));
                Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO, respuestaIngresoConsulta.getPoOdiUser() + "  " + respuestaIngresoConsulta.getPoOdiPassword() + "  " + respuestaIngresoConsulta.getPoWorkRepository() + "  " + respuestaIngresoConsulta.getPoLoadPlanName() + "  " + respuestaIngresoConsulta.getPoContext() + "  " + respuestaIngresoConsulta.getPoLogLevel() + "  " + respuestaIngresoConsulta.getWsdl());

                if (validarRespuestaTblLineasMasivoSp(respuestaIngresoConsulta)){
                    try {
                        executeInvokeStartLoadPlan(respuestaIngresoConsulta.getPoOdiUser(), respuestaIngresoConsulta.getPoOdiPassword(), respuestaIngresoConsulta.getPoWorkRepository(), respuestaIngresoConsulta.getPoLoadPlanName(), respuestaIngresoConsulta.getPoContext(), respuestaIngresoConsulta.getPoLogLevel(), respuestaIngresoConsulta.getWsdl());
                    } catch (Exception e) {

                        resquestDto = new TblLineasMasivoResquestDto();
                        resquestDto.setAccion(CarguesMasivosMensajeria.CARGUE_MASIVO_ACCION_ODI_ACTUALIZAR);
                        resquestDto.setLinMasivoId(new BigDecimal(respuestaIngresoConsulta.getPoMasivoId()));
                        resquestDto.setEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_ERROR_INTERNO);
                        resquestDto.setObserv("No se pudo consumir el servicio ODI");

                        servicio.tblLineasMasivoSp(resquestDto);

                        // Ejecutamos la consulta para refrescar el elemento lista cargue
                        this.consultarRegistrosSegunEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CARGUE);

                        Logger.getLogger(CargueMasivo.class.getName()).log(Level.SEVERE, null, e.getMessage());
                    }
                }


            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        String.format(CarguesMasivosMensajeria.CARGUE_MASIVO_DOCUMENTO_TIENE_MAS_REGISTROS_DE_LO_PERMITIDO, contRegCargue.intValue()), ""));
            }
        }

    }

    /**
     * Permite obtener el archivo resultante de las validaciones y demas procedimientos
     *
     * @param id {@String}
     * @param nombreArchivo {@String}
     * @throws IOException, JSchException, SftpException, ApplicationException Excepción de la App
     */
    public void procesadoConErrores(int id,String nombreArchivo) throws IOException, JSchException, SftpException, ApplicationException{
        try{

            String columnasArchivo = "";
            String saltoDeLinea = "\r\n";
            InputStream streamColumnasArchivo = null;
            InputStream streamSaltoDeLinea = null;


            if (!nombreArchivo.isEmpty()) {

                List<InfoGeneralCargueDto> listaInformacionCargue = servicio.contblMasivosSp(tipoCarga);//consulta de las tablas general
                if (!listaInformacionCargue.isEmpty()) {
                    rutaOutCargue = listaInformacionCargue.get(0).getRutaOut();
                    columnasArchivo = listaInformacionCargue.get(0).getPBase();
                }

                if (!nombreArchivo.equals("") || nombreArchivo != null) {

                    //Nombre columnas
                    streamColumnasArchivo = new ByteArrayInputStream(columnasArchivo.getBytes(Charset.forName("UTF-8")));
                    streamSaltoDeLinea = new ByteArrayInputStream(saltoDeLinea.getBytes(Charset.forName("UTF-8")));

                    FacesContext fc = FacesContext.getCurrentInstance();
                    SFTPConnector sshConnector = new SFTPConnector();
                    HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                    for (int i = 0; i < registrosCargue.size(); i++) {
                        if (registrosCargue.get(i).getLinMasivoId().intValue() == id) {
                            response.setContentType("text/csv");
                            fc.responseComplete();
                            response.setHeader("Content-disposition", "attachment; filename=\""+nombreArchivo + "\"");
                            ServletOutputStream output = response.getOutputStream();
                            InputStream is = sshConnector.getfile(ConstantsCargueMasivo.USERNAME, ConstantsCargueMasivo.HOST, ConstantsCargueMasivo.PORT, ConstantsCargueMasivo.PASSWORD, rutaOutCargue + nombreArchivo);
                            Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,is.available() + "     " + is.available());


                            if (encabezadoCargue.intValue() == 1 && StringUtils.isNotBlank(columnasArchivo)) {
                                // Escritura nombre columnas archivo
                                int nextCharColumnas;
                                while ((nextCharColumnas = streamColumnasArchivo.read()) != -1) {
                                    output.write(nextCharColumnas);
                                }

                                // Escritura Salto de Linea del Archivo
                                int nextCharSaltoDeLinea;
                                while ((nextCharSaltoDeLinea = streamSaltoDeLinea.read()) != -1) {
                                    output.write(nextCharSaltoDeLinea);
                                }
                            }


                            int nextChar;
                            while ((nextChar = is.read()) != -1) {
                                output.write(nextChar);
                            }

                            is.close();
                            output.flush();
                            output.close();
                        }
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        CarguesMasivosMensajeria.CARGUE_MASIVO_ERROR_PROCESANDO_ODI_O_BASE_DATOS, ""));

            }

        }catch(javax.faces.el.EvaluationException e){
            Logger.getLogger(CargueMasivo.class.getName()).log(Level.SEVERE, null, CarguesMasivosMensajeria.CARGUE_MASIVO_EL_ARCHIVO_NO_EXISTE_EN_RUTA_ESPECIFICADA+e);
        }
    }

    /**
     * Permite ejecutar el cliente encargado de consumir el servicio ODI
     *
     * @param odiuser {@String}
     * @param odipassword {@String}
     * @param workrepository {@String}
     * @param loadplan {@String}
     * @param odicontext {@String}
     * @param odiloglevel {@String}
     * @param wsdl {@String}
     *
     * @throws ApplicationException Excepción de la App
     */
    private boolean executeInvokeStartLoadPlan(String odiuser,String odipassword,String workrepository,String loadplan,String odicontext,String odiloglevel,String wsdl) throws ApplicationException {

        Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"Metodo: executeInvokeStartLoadPlan");
        Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"odiuser:" + odiuser);
        Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"odipassword:" + odipassword);
        Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"workrepository:" + workrepository);
        Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"loadplan:" + loadplan);
        Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"odicontext:" + odicontext);
        Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"odiloglevel:" + odiloglevel);
        Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"wsdl:" + wsdl);

        ObjectFactoryOdiInvoke objectFactory = new ObjectFactoryOdiInvoke();
        try {
            OdiCredentialType credentialType = objectFactory.createOdiCredentialType();
            credentialType.setOdiUser(odiuser);
            credentialType.setOdiPassword(odipassword);
            credentialType.setWorkRepository(workrepository);

            Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,Utils.toJson(credentialType));

            StartLoadPlanRequestType loadPlanRequestType = objectFactory.createStartLoadPlanRequestType();
            loadPlanRequestType.setLoadPlanName(loadplan);
            loadPlanRequestType.setContext(odicontext);
            loadPlanRequestType.setLogLevel(Integer.parseInt(odiloglevel));

            OdiStartLoadPlanRequest loadPlanRequest = objectFactory.createOdiStartLoadPlanRequest();
            loadPlanRequest.setCredentials(credentialType);
            loadPlanRequest.setStartLoadPlanRequest(loadPlanRequestType);

            Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,Utils.toJson(loadPlanRequest));

            if (!StringUtils.isEmpty(wsdl)) {
                OdiInvokeClient odiInvokeClient = new OdiInvokeClient(wsdl);
                OdiStartLoadPlanResponse response = odiInvokeClient.invokeStartLoadPlan(loadPlanRequest);

                if (response.getExecutionInfo().getStartedRunInformation().getOdiLoadPlanInstanceId() > Long.MIN_VALUE) {
                    Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"Response: " + String.valueOf(response.getExecutionInfo().getStartedRunInformation().getOdiLoadPlanInstanceId()));
                    return true;
                }
            }
        } catch (Exception e) {

            Logger.getLogger(CargueMasivo.class.getName()).log(Level.SEVERE, null, e);

            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    /**
     * Permite realizar la solicitud al cliente de ODI y procesar la informacion del cargue en las tablas
     * del sistema de MER (Cargues Masivos)
     *
     * @param estado {@String}
     * @param usuario {@String}
     * @param filtro {@String}
     * @param registroSeleccionado {@CargueInformacionDto}
     *
     * @throws ApplicationException Excepción de la App
     */
    public void ConsumirServicioOdi(String estado,String usuario,String filtro, CargueInformacionDto registroSeleccionado) throws ApplicationException{

        if (registroSeleccionado != null) {

            if (estado.equals(CarguesMasivosMensajeria.CARGUE_MASIVO_EN_PROCESO)) {
                //Agregamos funcion para actualizar el estado del registro seleccionado

                TblLineasMasivoResquestDto resquestDto = new TblLineasMasivoResquestDto();
                resquestDto.setAccion(CarguesMasivosMensajeria.CARGUE_MASIVO_ACCION_ODI_ACTUALIZAR);
                resquestDto.setLinMasivoId(registroSeleccionado.getLinMasivoId());
                resquestDto.setEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_EN_PROCESO_ODI);

                servicio.tblLineasMasivoSp(resquestDto);

                if (listTblLineasMasivoResponse.getPoCodigo().equals(new BigDecimal(0))) {
                    Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO, listTblLineasMasivoResponse.getPoOdiUser() + "  " + listTblLineasMasivoResponse.getPoOdiPassword() + "  " + listTblLineasMasivoResponse.getPoWorkRepository() + "  " + listTblLineasMasivoResponse.getPoLoadPlanName() + "  " + listTblLineasMasivoResponse.getPoContext() + "  " + listTblLineasMasivoResponse.getPoLogLevel() + "  " + listTblLineasMasivoResponse.getWsdl());
                    try {
                        executeInvokeStartLoadPlan(listTblLineasMasivoResponse.getPoOdiUser(), listTblLineasMasivoResponse.getPoOdiPassword(), listTblLineasMasivoResponse.getPoWorkRepository(), listTblLineasMasivoResponse.getPoLoadPlanName(), listTblLineasMasivoResponse.getPoContext(), listTblLineasMasivoResponse.getPoLogLevel(), listTblLineasMasivoResponse.getWsdl());
                    } catch (Exception e) {

                        resquestDto = new TblLineasMasivoResquestDto();
                        resquestDto.setAccion(CarguesMasivosMensajeria.CARGUE_MASIVO_ACCION_ODI_ACTUALIZAR);
                        resquestDto.setLinMasivoId(registroSeleccionado.getLinMasivoId());
                        resquestDto.setEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_ERROR_INTERNO);

                        servicio.tblLineasMasivoSp(resquestDto);


                        // Ejecutamos la consulta para refrescar el elemento lista cargue
                        this.consultarRegistrosSegunEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CARGUE);

                        Logger.getLogger(CargueMasivo.class.getName()).log(Level.SEVERE, null, e.getMessage());
                    }

                }

                consultarRegistrosSegunEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CONSULTA);
            }else{
                consultarRegistrosSegunEstado(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CONSULTA);
            }
        }


    }

    /**
     * Permite refrescar los estados de las solicitudes realizadas al sistema ODI. Permite
     * ver si el registro esta en "POR PROCESAR", "PROCESADO CON ERRORES", "ERROR INTERNO", "PROCESADO"
     *
     * @param tipo {@String}
     * @throws ApplicationException Excepción de la App
     */
    public void consultarRegistrosSegunEstado(String tipo) throws ApplicationException {
        if(tipo.equals(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CARGUE)){
            registrosCargue.clear();
        }else{
            registrosFiltro.clear();
        }
        listaInformacionCargados = servicio.contblLineasMasivoSp(tipoCarga, tipo, null); //lista de los registros de filtro y cargue
        for (int i = 0; i < listaInformacionCargados.size(); i++) {
            if (listaInformacionCargados.get(i).getTipo().equals(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CARGUE)) {
                registrosCargue.add(new CargueInformacionDto(
                        listaInformacionCargados.get(i).getLinMasivoId(),
                        listaInformacionCargados.get(i).getNombre(),
                        listaInformacionCargados.get(i).getArc(),
                        listaInformacionCargados.get(i).getArcOut(),
                        listaInformacionCargados.get(i).getTipo(),
                        listaInformacionCargados.get(i).getFechaReg(),
                        listaInformacionCargados.get(i).getFiltro(),
                        listaInformacionCargados.get(i).getEstado(),
                        listaInformacionCargados.get(i).getTotReg()));
            }else{
                registrosFiltro.add(new CargueInformacionDto(
                        listaInformacionCargados.get(i).getLinMasivoId(),
                        listaInformacionCargados.get(i).getNombre(),
                        listaInformacionCargados.get(i).getArc(),
                        listaInformacionCargados.get(i).getArcOut(),
                        listaInformacionCargados.get(i).getTipo(),
                        listaInformacionCargados.get(i).getFechaReg(),
                        listaInformacionCargados.get(i).getFiltro(),
                        listaInformacionCargados.get(i).getEstado(),
                        listaInformacionCargados.get(i).getTotReg()));
            }
        }
    }

    /**
     * Permite obtener el numero de filas de los archivos a procesar.
     *
     * @param in {InputStream}
     * @return {int}
     * @throws IOException Excepción de la App
     */
    public int contarFilas(InputStream in) throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        int i=0;
        while ((line = br.readLine()) != null) {
            sb.append(line + System.lineSeparator());
            i++;
        }
        return i;
    }

    /**
     * Permite limpiar las variables para volver a iniciar el proceso de forma limpia
     *
     */
    public void limpiarVariablesIniciales(){
        registrosCargue.clear();
        registrosFiltro.clear();
        listaInfoFiltros.clear();
        listaInformacionCargue.clear();
        listaInformacionCargados.clear();
        masivoIdConsulta= null;
        TipoConsulta = "";
        rutaInConsulta= "";
        rutaOutConsulta = "";
        estNomArchivoConsulta = "";
        contRegConsulta = null;
        TablaConsulta = "";
        DelimConsulta = "";
        RolConsulta = "";
        pBaseConsulta = "";
        masivoIdCargue= null;
        TipoCargue = "";
        rutaInCargue = "";
        rutaOutCargue = "";
        estNomArchivoCargue = "";
        contRegCargue = null;
        TablaCargue = "";
        DelimCargue = "";
        RolCargue = "";
        pBaseCargue = "";
        file = null;
        cargaFile = null;
        previousPage = null;
        recargoPaginaF5 = false;
    }





    /**
     * Permite descargar el archivo generado ubicado en el FTP
     *
     * @param id {int}
     * @param nombreArchivo {String}
     * @param selectedRegistro {CargueInformacionDto}
     *
     * @throws IOException, JSchException, SftpException, ApplicationException Excepción de la App
     */
    public void descargarArchivo(int id,String rutaOutCargue, String nombreArchivo, CargueInformacionDto selectedRegistro, String columnasArchivo, BigDecimal escribirCabecera) throws IOException, JSchException, SftpException{
        try{
            String saltoDeLinea = "\r\n";
            InputStream streamColumnasArchivo = null;
            InputStream streamSaltoDeLinea = null;


            if (selectedRegistro != null
                    && (selectedRegistro.getTipo().equals(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CONSULTA)
                    || selectedRegistro.getTipo().equals(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CARGUE))
                    && selectedRegistro.getTotReg() != BigDecimal.ZERO) {

                if (!nombreArchivo.isEmpty()) {

                    Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"USERNAME:"+ ConstantsCargueMasivo.USERNAME);
                    Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"HOST:"+ ConstantsCargueMasivo.HOST);
                    Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"PORT:"+ ConstantsCargueMasivo.PORT);
                    Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"PASSWORD:"+ ConstantsCargueMasivo.PASSWORD);
                    Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"rutaOutCargue:"+rutaOutCargue);
                    Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"nombreArchivo:"+nombreArchivo);
                    Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,"columnasArchivo:"+columnasArchivo);

                    if (!nombreArchivo.equals("") || nombreArchivo != null) {

                        //Nombre columnas
                        streamColumnasArchivo = new ByteArrayInputStream(columnasArchivo.getBytes(Charset.forName("UTF-8")));
                        streamSaltoDeLinea = new ByteArrayInputStream(saltoDeLinea.getBytes(Charset.forName("UTF-8")));

                        FacesContext fc = FacesContext.getCurrentInstance();
                        SFTPConnector sshConnector = new SFTPConnector();
                        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();

                        if (selectedRegistro != null) {
                            if (selectedRegistro.getLinMasivoId().intValue() == id) {
                                response.setContentType("text/csv");
                                fc.responseComplete();
                                response.setHeader("Content-disposition", "attachment; filename=\""+nombreArchivo + "\"");
                                ServletOutputStream output = response.getOutputStream();
                                InputStream is = sshConnector.getfile(ConstantsCargueMasivo.USERNAME, ConstantsCargueMasivo.HOST, ConstantsCargueMasivo.PORT, ConstantsCargueMasivo.PASSWORD, rutaOutCargue + nombreArchivo);
                                Logger.getLogger(CargueMasivo.class.getName()).log(Level.INFO,is.available() + "     " + is.available());


                                if (escribirCabecera.intValue() == 1 && StringUtils.isNotBlank(columnasArchivo)) {
                                    // Escritura nombre columnas archivo
                                    int nextCharColumnas;
                                    while ((nextCharColumnas = streamColumnasArchivo.read()) != -1) {
                                        output.write(nextCharColumnas);
                                    }

                                    // Escritura Salto de Linea del Archivo
                                    int nextCharSaltoDeLinea;
                                    while ((nextCharSaltoDeLinea = streamSaltoDeLinea.read()) != -1) {
                                        output.write(nextCharSaltoDeLinea);
                                    }
                                }

                                //Escritura cuerpo del archivo
                                int nextChar;
                                while ((nextChar = is.read()) != -1) {
                                    output.write(nextChar);
                                }

                                is.close();
                                output.flush();
                                output.close();
                            }
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            CarguesMasivosMensajeria.CARGUE_MASIVO_ERROR_PROCESANDO_ODI_O_BASE_DATOS, ""));
                }
            } else if (selectedRegistro != null
                    && (selectedRegistro.getTipo().equals(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CONSULTA)
                    || selectedRegistro.getTipo().equals(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CARGUE))
                    && selectedRegistro.getTotReg() == BigDecimal.ZERO) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        CarguesMasivosMensajeria.CARGUE_MASIVO_MENSAJE_FILTROS_NO_GENERO_REGISTROS, ""));
            }

        }catch(javax.faces.el.EvaluationException e){
            Logger.getLogger(CargueMasivo.class.getName()).log(Level.SEVERE, null, CarguesMasivosMensajeria.CARGUE_MASIVO_EL_ARCHIVO_NO_EXISTE_EN_RUTA_ESPECIFICADA+e);
        }
    }

    public List<CamposFiltrosDto> getListaInfoFiltros() {
        return listaInfoFiltros;
    }

    public void setListaInfoFiltros(List<CamposFiltrosDto> listaInfoFiltros) {
        this.listaInfoFiltros = listaInfoFiltros;
    }

    public CargueInformacionDto getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(CargueInformacionDto selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public UploadedFile getCargaFile() {
        return cargaFile;
    }

    public void setCargaFile(UploadedFile cargaFile) {
        this.cargaFile = cargaFile;
    }

    public boolean validarregistroscsv(){
        return true;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public List<InfoGeneralCargueDto> getListaInformacionCargue() {
        return listaInformacionCargue;
    }

    public void setListaInformacionCargue(List<InfoGeneralCargueDto> listaInformacionCargue) {
        this.listaInformacionCargue = listaInformacionCargue;
    }

    public List<CargueInformacionDto> getListaInformacionCargados() {
        return listaInformacionCargados;
    }

    public void setListaInformacionCargados(List<CargueInformacionDto> listaInformacionCargados) {
        this.listaInformacionCargados = listaInformacionCargados;
    }

    public List<CargueInformacionDto> getRegistrosCargue() {
        return registrosCargue;
    }

    public void setRegistrosCargue(List<CargueInformacionDto> registrosCargue) {
        this.registrosCargue = registrosCargue;
    }

    public List<CargueInformacionDto> getRegistrosFiltro() {
        return registrosFiltro;
    }

    public void setRegistrosFiltro(List<CargueInformacionDto> registrosFiltro) {
        this.registrosFiltro = registrosFiltro;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    public boolean isRecargoPaginaF5() {
        return recargoPaginaF5;
    }

    public void setRecargoPaginaF5(boolean recargoPaginaF5) {
        this.recargoPaginaF5 = recargoPaginaF5;
    }

    /**
     * Permite validar que los filtros obligatorios no esten vacios
     *
     * @return {String} Lista de filtros obligatorios vacios
     * @author Manuel Hernandez Rivas
     */
    public String validarFiltrosObligatorios(){
        StringBuilder listaFiltrosObligatorios = new StringBuilder("");
        listaInfoFiltros.forEach(infoFiltro -> {
            if (infoFiltro.getObligatoriedad().equals(FILTRO_OBLIGATORIO)
                    && (StringUtils.isBlank(infoFiltro.getValor())
                    && infoFiltro.getFecha() == null)) {
                listaFiltrosObligatorios.append(infoFiltro.getNombreColumna()).append(";");
            }
        });

        return listaFiltrosObligatorios.toString();
    }


    /**
     * Permite validar que el tipo de carga seleccionado sea acorde con el tipo de archivo
     * a cargar. Para permitir que el archivo sea procesado y enviar la peticion a ODI
     *
     * @param tipoCarga {String}
     * @param nombreArchivo {String}
     */
    public boolean validarTipoCargaYProcesarArchivo (String tipoCarga, UploadedFile file) {
        String caracterUnderScored = "_";
        String[] ArrayTipoCarga = null;
        if (tipoCarga.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    CarguesMasivosMensajeria.CARGUE_MASIVO_PROCESAR_ARCHIVO_TIPO_CARGUE_OBLIGATORIO, ""));
            return false;
        }


        if (!CsvUtils.isCsvFile(file.getFileName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    CarguesMasivosMensajeria.CARGUE_MASIVO_FORMATO_ARCHIVO_INVALIDO, ""));
            return false;
        }

        if (!CsvUtils.validarCabecera(file, pBaseCargue, DelimCargue)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    CarguesMasivosMensajeria.CARGUE_MASIVO_MENSAJE_ESTRUCTURA_ARCHIVO_INVALIDA, ""));
            return false;
        }

        if (validacionEstNomArchivoCargue.intValue() == 1){
            if (CarguesMasivosUtils.validarNombreArchivo(file.getFileName(), estNomArchivoCargue)) {
                return true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        CarguesMasivosMensajeria.CARGUE_MASIVO_NOMBRE_ARCHIVO_INVALIDO, ""));
                return false;
            }
        }else{
            if (tipoCarga.contains(caracterUnderScored)) {
                ArrayTipoCarga = tipoCarga.split(caracterUnderScored);
                if (!file.getFileName().toUpperCase().contains(ArrayTipoCarga[ArrayTipoCarga.length - 1].toUpperCase())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            CarguesMasivosMensajeria.CARGUE_MASIVO_PROCESAR_ARCHIVO_Y_TIPO_CARGO_NO_COINCIDEN, ""));
                    return false;
                }
            }
        }

        return true;
    }

    private boolean validarRespuestaTblLineasMasivoSp(TblLineasMasivoResponseDto responseDto){
        return StringUtils.isNotBlank(responseDto.getWsdl())
                && StringUtils.isNotBlank(responseDto.getPoOdiUser())
                && StringUtils.isNotBlank(responseDto.getPoOdiPassword())
                && StringUtils.isNotBlank(responseDto.getPoWorkRepository())
                && StringUtils.isNotBlank(responseDto.getPoLoadPlanName())
                && StringUtils.isNotBlank(responseDto.getPoContext())
                && StringUtils.isNotBlank(responseDto.getPoLogLevel());
    }

}
