package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.Marcas;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.ObjetoRrModificacion;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase ProcesarSolicitudNegocioModificacionMBean Extiende de BaseMBean
 * Implementa Serialización
 *
 * @author Nataly Orozco Torres
 * @version	1.0
 */
@SessionScoped
@ManagedBean(name = "procesarSolicitudModificacionMBean")
public class ProcesarSolicitudNegocioModificacionMBean extends BaseMBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(ProcesarSolicitudNegocioModificacionMBean.class);

    private static String NOMBRE_FUNCIONALIDAD = "PROCESAR SOLICITUD DE NEGOCIO MODIFICACION";
    private String nombreLog;
    private String seleccionar = "";
    private String son_tipoSolicitud = "";
    /**
     *
     */
    public List<SelectItem> lstTSolicitud = null;
    private List<Multivalor> tSolicitudes = null;
    private boolean renderedProcesar = false;
    private boolean renderedDescargar = false;
    private boolean showpopUp = false;
    private boolean disableProcesar = false;
    private String messagePopup = "¿Esta seguro que desea procesar las solicitudes de Modificación de HHPP y Generar archivo para RR?";
    private List<SolicitudNegocio> solicitudes = null;
    private List<SolicitudNegocio> solicitudesRr = null;
    private String nombreArchivo = "";
    private List<ObjetoRrModificacion> objetosRR = null;
    int contador = 2;
    int qSolicitudes = 0;

    /**
     *
     */
    public ProcesarSolicitudNegocioModificacionMBean() {
        super();

        lstTSolicitud = new ArrayList<SelectItem>();
        try {
            tSolicitudes = SolicitudNegocioDelegate.loadTiposSolicitudModificacion();
            for (Multivalor tsol : tSolicitudes) {
                SelectItem item = new SelectItem();
                item.setValue(tsol.getMulValor());
                item.setLabel(tsol.getDescripcion());
                lstTSolicitud.add(item);
            }
            SelectItem item1 = new SelectItem();
            item1.setValue(Constant.TIPO_SON_TODAS);
            item1.setLabel(Constant.TIPO_SON_TODAS);
            lstTSolicitud.add(item1);
        } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
        } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
        }
    }

    /**
     * Validar si el usuario escogio valor valido para Tipo de Solicitud a
     * procesar
     */
    public void onActionValidar() {
        String tsquery = "";
        showpopUp = false;
        if (son_tipoSolicitud.equals("0")) {
            message = "Debe seleccionar un Tipo de Solicitud para poder continuar.";
            contador++;
        } else {
            if (!son_tipoSolicitud.equals(Constant.TIPO_SON_TODAS)) {
                tsquery = son_tipoSolicitud;
            } else {
                tsquery = "MDF";
            }
            try {
                if (!son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIODIR)) {
                    solicitudes = SolicitudNegocioDelegate.querySolicitudesPorProcesar(tsquery);
                    nombreArchivo = calcularNombreArchivo();
                    if (solicitudes == null) {
                        message = "No hay solicitudes pendientes por procesar.";
                    } else {
                        renderedProcesar = true;
                        showpopUp = true;
                        qSolicitudes = solicitudes.size();
                        messagePopup = "¿Esta seguro que desea procesar : " + qSolicitudes + " solicitudes de creación de HHPP y Generar archivo para RR?";
                    }
                } else {
                    message = "Las solicitudes de tipo CAMBIO DE DIRECCION no son procesables.";
                }
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            }
        }
    }

    /*Crear HHPP de las solicitudes que estan en estado "GESTIONADA" y que no tienen un HHPP asociado modificandolas a estado ARCHIVO GENERADO
     * Rechazar las solicitudes que se encuentran en estado "GESTIONADA" pero que tienen un HHPP ya asociado.
     * Generar archivo para RR con las solicitudes a las cuales se les creo HHPP y modificar el nombre de archivo en la solicitud al nombre 
     * asignado a archivo para RR.
     **/
    /**
     *
     */
    public void onActionProcesar() {
        showpopUp = false;
        disableProcesar = true;
        try {
            if (solicitudes.size() > 0) {
                message = "Las " + qSolicitudes + " solicitudes estan siendo procesadas, por favor espere para descargar el archivo para RR.";
                message = SolicitudNegocioDelegate.procesarSolicitudesModificacion(solicitudes, NOMBRE_FUNCIONALIDAD, nombreArchivo, user.getUsuLogin());
                crearObjetosRR();
                renderedProcesar = false;
                renderedDescargar = true;
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        }
    }

    /*
     * Cancelar el procesamiento de solicitudes de modificación
     */
    /**
     *
     */
    public void onActionCancel() {

        renderedProcesar = false;
        renderedDescargar = false;
        showpopUp = false;
        disableProcesar = false;
        son_tipoSolicitud = "";
        message = "";
        solicitudes = null;
        solicitudesRr = null;
    }

    /**
     *
     */
    private void crearObjetosRR() {
        if (message.isEmpty()) {
            try {
                solicitudesRr = SolicitudNegocioDelegate.querySolicitudesParaRR(nombreArchivo);
                if (solicitudesRr != null && solicitudesRr.size() > 0) {
                    objetosRR = new ArrayList<ObjetoRrModificacion>();
                    for (SolicitudNegocio solicitud : solicitudesRr) {
                        ObjetoRrModificacion objRRMdf = new ObjetoRrModificacion();
                        objRRMdf.setAcct(Constant.FILE_RR_CTA);
                        String calle = Constant.VACIO_RR, apto = Constant.VACIO_RR;
                        String placa = "0-00";
                        Hhpp hhppSol = solicitud.getHhpp();
                        if (solicitud.getSonServinformacion() != null && !solicitud.getSonServinformacion().equals("") && solicitud.getSonServinformacion().length() > 0) {
                            String tipoDir = solicitud.getGeograficoPolitico().getGpoCodTipoDireccion();
                            List<String> callePlacaComplemento = obtenerDireccionFormatoRR(tipoDir, solicitud.getSonServinformacion(), solicitud.getGeografico());
                            //TODO validar q la calle no tenga más de 50 caracteres
                            calle = callePlacaComplemento.get(0);
                            placa = callePlacaComplemento.get(1);
                            //TODO validar q apto sea de tamaño 10, de lo contrario llenar con espacios.
                            apto = callePlacaComplemento.get(2);
                        } else if (solicitud.getSonFormatoIgac() != null && !solicitud.getSonFormatoIgac().equals("")) {
                            calle = solicitud.getSonFormatoIgac().trim();
                        } else if (solicitud.getSonNostandar() != null && !solicitud.getSonNostandar().equals("")) {
                            calle = solicitud.getSonNostandar().trim();
                        }
                        objRRMdf.setStrtnm(calle);
                        objRRMdf.setHomeno(placa);
                        objRRMdf.setAptno(apto);
                        objRRMdf.setCurcom(hhppSol.getNodo().getComunidad().getComId());
                        objRRMdf.setCurgrd(hhppSol.getNodo().getComunidad().getComId());
                        if (solicitud.getSonTipoSolicitud().equals(Constant.TIPO_SON_CAMBIONOD)) {
                            String comId = solicitud.getNodo().getComunidad().getComId();
                            objRRMdf.setNewcom(comId);
                            objRRMdf.setNewgrd(comId);
                            objRRMdf.setHendcd(solicitud.getNodo().getNodHeadend());
                            objRRMdf.setNodetp(solicitud.getNodo().getNodTipo().toString());
                            objRRMdf.setNodecd(solicitud.getNodo().getNodCodigo());
                            objRRMdf.setCablownr(comId);
                        } else {
                            String comId = hhppSol.getNodo().getComunidad().getComId();
                            objRRMdf.setNewcom(comId);
                            objRRMdf.setNewgrd(comId);
                            objRRMdf.setHendcd(hhppSol.getNodo().getNodHeadend());
                            objRRMdf.setNodetp(hhppSol.getNodo().getNodTipo().toString());
                            objRRMdf.setNodecd(hhppSol.getNodo().getNodCodigo());
                            objRRMdf.setCablownr(comId);
                        }
                        objRRMdf.setHendtp(Constant.FILE_RR_HE);
                        //TODO este campo debe tener el  Tipo del CMTS en la planta que debe tenerlo el nodo, se esta gestionando para Control de Cambios
                        objRRMdf.setCmtstp("CT");
                        //TODO este campo debe tener el Codigo del CMTS en la planta que debe tenerlo el nodo, se esta gestionando para Control de Cambios
                        objRRMdf.setCmtscd("xxxxxx");
                        String barrio = Constant.NG_RR;
                        String localidad = Constant.NG_RR;
                        if (solicitud.getGeografico() != null) {
                            barrio = solicitud.getGeografico().getGeoNombre();
                            localidad = solicitud.getGeografico().getGeografico().getGeoNombre();
                        }
                        objRRMdf.setBarrio(barrio);
                        objRRMdf.setSector(localidad);
                        if (solicitud.getSonTipoSolicitud().equals(Constant.TIPO_SON_CAMBIOEST)) {
                            objRRMdf.setEstrato(solicitud.getSonEstrato());
                        } else {
                            if (hhppSol.getSubDireccion() == null) {
                                if (hhppSol.getDireccion().getDirEstrato() != null) {
                                    objRRMdf.setEstrato(hhppSol.getDireccion().getDirEstrato().toString());
                                } else {
                                    objRRMdf.setEstrato(Constant.NG_RR);
                                }
                            } else {
                                if (hhppSol.getSubDireccion().getSdiEstrato() != null) {
                                    objRRMdf.setEstrato(hhppSol.getSubDireccion().getSdiEstrato().toString());
                                } else {
                                    objRRMdf.setEstrato(Constant.NG_RR);
                                }
                            }
                        }
                        objRRMdf.setDireccn(calle + " " + placa);
                        objRRMdf.setSeccion(solicitud.getSonLadomz());
                        objRRMdf.setManzana(solicitud.getSonManzanaCatastral());
                        objRRMdf.setPostcode(solicitud.getNodo().getComunidad().getComZipcode());
                        if (solicitud.getSonTipoSolicitud().equals(Constant.TIPO_SON_VERIFICACION)) {
                            objRRMdf.setStatus(solicitud.getSonEstadoUni());
                        } else {
                            objRRMdf.setStatus(hhppSol.getEstadoHhpp().getEhhId());
                        }
                        objRRMdf.setUnittype(hhppSol.getTipoHhpp().getThhId());
                        objRRMdf.setDroptype(hhppSol.getTipoConexionHhpp().getThcCodigo());
                        objRRMdf.setDropcabl(Constant.CABLE_RR);
                        objRRMdf.setDealercd(Constant.FILE_RR_VENDEDOR);
                        List<Marcas> marcasHhpp = null;
                        if (hhppSol.getMarcas() != null) {
                            marcasHhpp = hhppSol.getMarcas();
                        }
                        objRRMdf.setProbcd01(Constant.FILE_RR_VACIO);
                        objRRMdf.setProbcd02(Constant.FILE_RR_VACIO);
                        objRRMdf.setProbcd03(Constant.FILE_RR_VACIO);
                        objRRMdf.setProbcd04(Constant.FILE_RR_VACIO);
                        objRRMdf.setProbcd05(Constant.FILE_RR_VACIO);
                        objRRMdf.setProbcd06(Constant.FILE_RR_VACIO);
                        objRRMdf.setProbcd07(Constant.FILE_RR_VACIO);
                        objRRMdf.setProbcd08(Constant.FILE_RR_VACIO);
                        objRRMdf.setProbcd09(Constant.FILE_RR_VACIO);
                        if (marcasHhpp != null) {
                            for (int i = 0; i < marcasHhpp.size(); i++) {
                                Marcas m = marcasHhpp.get(i);
                                String cod_mar = "";
                                if (m.getMarCodigo() != null) {
                                    cod_mar = m.getMarCodigo();
                                }
                                if (i == 1) {
                                    objRRMdf.setProbcd01(cod_mar);
                                }
                                if (i == 2) {
                                    objRRMdf.setProbcd02(cod_mar);
                                }
                                if (i == 3) {
                                    objRRMdf.setProbcd03(cod_mar);
                                }
                                if (i == 4) {
                                    objRRMdf.setProbcd04(cod_mar);
                                }
                                if (i == 5) {
                                    objRRMdf.setProbcd05(cod_mar);
                                }
                                if (i == 6) {
                                    objRRMdf.setProbcd06(cod_mar);
                                }
                                if (i == 7) {
                                    objRRMdf.setProbcd07(cod_mar);
                                }
                                if (i == 8) {
                                    objRRMdf.setProbcd08(cod_mar);
                                }
                                if (i == 9) {
                                    objRRMdf.setProbcd09(cod_mar);
                                }
                                if (i >= 10) {
                                    i = marcasHhpp.size();
                                }
                            }
                        }
                        objRRMdf.setAdditn01(Constant.FILE_RR_ADD);
                        objRRMdf.setAdditn02(Constant.FILE_RR_ADD);
                        objRRMdf.setAdditn03(Constant.FILE_RR_ADD);
                        objRRMdf.setAdditn04(Constant.FILE_RR_ADD);
                        objRRMdf.setAdditn05(Constant.FILE_RR_ADD);
                        objRRMdf.setAdditn06(Constant.FILE_RR_ADD);
                        objRRMdf.setAdditn07(Constant.FILE_RR_ADD);
                        objRRMdf.setAdditn08(Constant.FILE_RR_ADD);
                        objRRMdf.setAdditn09(Constant.FILE_RR_ADD);
                        objRRMdf.setAdditn10(Constant.FILE_RR_ADD);
                        objetosRR.add(objRRMdf);
                    }
                }
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            }
        }
    }

    /**
     *
     * @return
     */
    private String calcularNombreArchivo() {
        String fileName = "ADCMODIFICACION";
        String endFileName = "";
        if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIODIR)) {
            endFileName = "-CD";
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_GRID)) {
            endFileName = "-IG";
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIONOD)) {
            endFileName = "-CN";
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIOEST)) {
            endFileName = "-CE";
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_VERIFICACION)) {
            endFileName = "-VH";
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_TODAS)) {
            endFileName = "-TO";
        }
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date fechaActual = new Date();
        fileName = fileName + dateFormat.format(fechaActual) + endFileName + ".csv";
        return fileName;
    }

    /**
     * Metodo que exporta un objetos RR a CSV
     */
    public void doExportObjectsToCSV() {
        crearObjetosRR();
        final StringBuffer sb = new StringBuffer();
        for (ObjetoRrModificacion data : this.objetosRR) {
            sb.append(data.getAcct());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getStrtnm());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getHomeno());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAptno());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getCurcom());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getCurgrd());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getNewcom());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getNewgrd());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getHendtp());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getHendcd());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getCmtstp());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getCmtscd());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getNodetp());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getNodecd());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getBarrio());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getEstrato());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getDireccn());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getSector());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getSeccion());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getManzana());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getPostcode());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getStatus());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getUnittype());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getDroptype());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getDropcabl());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getCablownr());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getDealercd());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getProbcd01());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getProbcd02());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getProbcd03());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getProbcd04());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getProbcd05());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getProbcd06());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getProbcd07());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getProbcd08());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getProbcd09());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAdditn01());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAdditn02());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAdditn03());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAdditn04());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAdditn05());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAdditn06());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAdditn07());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAdditn08());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAdditn09());
            sb.append(Constant.SEPARATOR);
            sb.append(data.getAdditn10());
            sb.append("\n");
        }
        byte[] csvData = null;
        try {
            csvData = sb.toString().getBytes("utf-8");
        } catch (final UnsupportedEncodingException ex) {
            message = "Se produjo un error al Escribir el archivo de modificación.";
            LOGGER.error("Error al momento de exportar los objetos a CSV. EX000: " + ex.getMessage(), ex);
        } catch (Exception e) {
            message = "Se produjo un error al Escribir el archivo de modificación: " + e.getMessage();
            LOGGER.error(message + e.getMessage(), e);
        }
        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String value = "attached; filename=\"" + nombreArchivo + "\"";
        response.setHeader("Content-disposition", value);
        response.setContentType("application/force.download");
        try {
            response.getOutputStream().write(csvData);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            context.responseComplete();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (final IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        }
    }

    /**
     *
     * @param tipoDir
     * @param codServinformacion
     * @param geografico
     * @return
     */
    private List<String> obtenerDireccionFormatoRR(String tipoDir, String codServinformacion, Geografico geografico) {
        List<String> callePlacaComplemento = null;
        if (tipoDir != null && codServinformacion.length() < 99) {
            if (tipoDir.toUpperCase().equals(Constant.CODIGO_TIPO_CIUDAD_MEDELLIN)) {
                callePlacaComplemento = obtenerDirRrTipoMedellin(new StringBuffer(codServinformacion));
            } else if (tipoDir.toUpperCase().equals(Constant.CODIGO_TIPO_CIUDAD_BOGOTA)) {
                callePlacaComplemento = obtenerDirRrTipoBogota(new StringBuffer(codServinformacion));
            } else if (tipoDir.toUpperCase().equals(Constant.CODIGO_TIPO_CIUDAD_CALI)) {
                callePlacaComplemento = obtenerDirRrTipoCali(new StringBuffer(codServinformacion));
            }
        } else if (codServinformacion.length() == 99) {
            callePlacaComplemento = obtenerDirRrTipoManzanaCasa(new StringBuffer(codServinformacion), geografico);
        }
        return callePlacaComplemento;
    }

    /**
     * Obtener calle placa y complemento en formato RR partiendo de el codigo de
     * direccion entregado por Servinformación cuando una direccion es de tipo
     * Bogota
     *
     * @param codServinformacion Codigo de direccion entregado por
     * Servinformación
     * @return Lista con calle, placa y complemento
     */
    private List<String> obtenerDirRrTipoBogota(StringBuffer codServinformacion) {
        List<String> callePlacaComplemento = new ArrayList<String>();
        String calle = "";
        String placa = "";
        String complemento = "";
        String placaDireccion = "";
        String letraCuadranteVPrincipal = "";
        String letraCuadranteVGeneradora = "";
        String nombreCalle = "";
        String numViaP = "", numViaG = "", letraViaP = "", letraViaG = "", bisViaP = "";
        String bisViaG = "", letraPosbisViaP = "", letraPosbisViaG = "";
        String nombre1Nivel = "", nombre2Nivel = "", nombre3Nivel = "";
        String nombre4Nivel = "", nombre5Nivel = "", nombre6Nivel = "";
        if (codServinformacion.length() == 78 || codServinformacion.length() == 79) {
            letraCuadranteVPrincipal = codServinformacion.substring(8, 9);
            if (letraCuadranteVPrincipal.equals("0")) {
                letraCuadranteVPrincipal = "";
            } else {
                letraCuadranteVPrincipal = traducirCardinalidad(letraCuadranteVPrincipal);
            }

            letraCuadranteVGeneradora = codServinformacion.substring(9, 10);
            if (letraCuadranteVGeneradora.equals("0")) {
                letraCuadranteVGeneradora = "";
            } else {
                letraCuadranteVGeneradora = traducirCardinalidad(letraCuadranteVGeneradora);
            }
            nombreCalle = codServinformacion.substring(10, 12);
            if (nombreCalle.equals("00")) {
                nombreCalle = "";
            } else {
                nombreCalle = traducirNombrePrincipal(nombreCalle);
            }

            numViaP = codServinformacion.substring(12, 15);
            numViaP = deleteZeros(numViaP);

            numViaG = codServinformacion.substring(15, 18);
            numViaG = deleteZeros(numViaG);

            letraViaP = codServinformacion.substring(18, 19);
            if (letraViaP.equals("0")) {
                letraViaP = "";
            }

            letraViaG = codServinformacion.substring(19, 20);
            if (letraViaG.equals("0")) {
                letraViaG = "";
            }

            bisViaP = codServinformacion.substring(20, 23);
            if (bisViaP.contains("0")) {
                bisViaP = "";
            }

            bisViaG = codServinformacion.substring(23, 26);
            if (bisViaG.contains("0")) {
                bisViaG = "";
            }

            letraPosbisViaP = codServinformacion.substring(26, 27);
            if (letraPosbisViaP.equals("0")) {
                letraPosbisViaP = "";
            }

            letraPosbisViaG = codServinformacion.substring(27, 28);
            if (letraPosbisViaG.equals("0")) {
                letraPosbisViaG = "";
            }
            if (codServinformacion.length() == 78) {
                placaDireccion = codServinformacion.substring(28, 30);
                placaDireccion = deleteZeros(placaDireccion);

                nombre1Nivel = codServinformacion.substring(30, 33);
                nombre1Nivel = deleteZeros(nombre1Nivel);
                nombre1Nivel = traducirAgregacion(nombre1Nivel);
                nombre1Nivel = nombre1Nivel + deleteZeros(codServinformacion.substring(33, 38));

                nombre2Nivel = codServinformacion.substring(38, 41);
                nombre2Nivel = deleteZeros(nombre2Nivel);
                nombre2Nivel = traducirAgregacion(nombre2Nivel);
                nombre2Nivel = nombre2Nivel + deleteZeros(codServinformacion.substring(41, 46));

                nombre3Nivel = codServinformacion.substring(46, 49);
                nombre3Nivel = deleteZeros(nombre3Nivel);
                nombre3Nivel = traducirAgregacion(nombre3Nivel);
                nombre3Nivel = nombre3Nivel + deleteZeros(codServinformacion.substring(49, 54));

                nombre4Nivel = codServinformacion.substring(54, 57);
                nombre4Nivel = deleteZeros(nombre4Nivel);
                nombre4Nivel = traducirAgregacion(nombre4Nivel);
                nombre4Nivel = nombre4Nivel + deleteZeros(codServinformacion.substring(57, 62));

                nombre5Nivel = codServinformacion.substring(62, 65);
                nombre5Nivel = deleteZeros(nombre5Nivel);
                nombre5Nivel = traducirAgregacion(nombre5Nivel);
                nombre5Nivel = nombre5Nivel + deleteZeros(codServinformacion.substring(65, 70));

                nombre6Nivel = codServinformacion.substring(70, 73);
                nombre6Nivel = deleteZeros(nombre6Nivel);
                nombre6Nivel = traducirAgregacion(nombre6Nivel);
                nombre5Nivel = nombre5Nivel + deleteZeros(codServinformacion.substring(73, 78));

            } else if (codServinformacion.length() == 79) {
                placaDireccion = codServinformacion.substring(28, 31);
                placaDireccion = deleteZeros(placaDireccion);

                nombre1Nivel = codServinformacion.substring(31, 34);
                nombre1Nivel = deleteZeros(nombre1Nivel);
                nombre1Nivel = traducirAgregacion(nombre1Nivel);
                nombre1Nivel = nombre1Nivel + deleteZeros(codServinformacion.substring(34, 39));

                nombre2Nivel = codServinformacion.substring(39, 42);
                nombre2Nivel = deleteZeros(nombre2Nivel);
                nombre2Nivel = traducirAgregacion(nombre2Nivel);
                nombre2Nivel = nombre2Nivel + deleteZeros(codServinformacion.substring(42, 47));

                nombre3Nivel = codServinformacion.substring(47, 50);
                nombre3Nivel = deleteZeros(nombre3Nivel);
                nombre3Nivel = traducirAgregacion(nombre3Nivel);
                nombre3Nivel = nombre3Nivel.trim();
                nombre3Nivel = nombre3Nivel + deleteZeros(codServinformacion.substring(50, 55));

                nombre4Nivel = codServinformacion.substring(55, 58);
                nombre4Nivel = deleteZeros(nombre4Nivel);
                nombre4Nivel = traducirAgregacion(nombre4Nivel);
                nombre4Nivel = nombre4Nivel + deleteZeros(codServinformacion.substring(58, 63));

                nombre5Nivel = codServinformacion.substring(63, 66);
                nombre5Nivel = deleteZeros(nombre5Nivel);
                nombre5Nivel = traducirAgregacion(nombre5Nivel);
                nombre5Nivel = nombre5Nivel.trim();
                nombre5Nivel = nombre5Nivel + deleteZeros(codServinformacion.substring(66, 71));

                nombre6Nivel = codServinformacion.substring(71, 74);
                nombre6Nivel = deleteZeros(nombre6Nivel);
                nombre6Nivel = traducirAgregacion(nombre6Nivel);
                nombre6Nivel = nombre6Nivel + deleteZeros(codServinformacion.substring(74, 79));
            }
        }
        calle = nombreCalle + " " + numViaP + letraViaP + bisViaP + letraPosbisViaP + letraCuadranteVGeneradora + letraCuadranteVPrincipal + " " + nombre3Nivel + " " + nombre5Nivel;
        placa = numViaG + letraViaG + bisViaG + letraPosbisViaG + "-" + placaDireccion;
        placa = adecuarTamanodiez(placa);
        complemento = nombre1Nivel + nombre2Nivel + nombre4Nivel + nombre6Nivel;
        complemento = adecuarTamanodiez(complemento);

        callePlacaComplemento.add(calle);
        callePlacaComplemento.add(placa);
        callePlacaComplemento.add(complemento);

        return callePlacaComplemento;
    }

    /**
     * Obtener calle placa y complemento en formato RR partiendo de el codigo de
     * direccion entregado por Servinformación para una dir tipo Cali
     *
     * @param codServinformacion Codigo de direccion entregado por
     * Servinformación
     * @return Lista con calle, placa y complemento
     */
    private List<String> obtenerDirRrTipoCali(StringBuffer codServinformacion) {
        List<String> callePlacaComplemento = new ArrayList<String>();
        String letraCuadranteVPrincipal = "", letraCuadranteVGeneradora = "", nombreCalle = "";
        String nombreGeneradora = "", numViaP = "", numViaG = "", letraViaP = "", letraViaG = "";
        String numPosLViaG = "", numPosLViaP = "", numCeros = "", bisViaP = "", bisViaG = "";
        String letraPosbisViaP = "", letraPosbisViaG = "", placaDireccion = "";
        String nombre1Nivel = "", nombre2Nivel = "", nombre3Nivel = "", nombre4Nivel = "", nombre5Nivel = "", nombre6Nivel = "";

        if (codServinformacion.length() == 87) {
            letraCuadranteVPrincipal = codServinformacion.substring(8, 9);
            if (letraCuadranteVPrincipal.equals("0")) {
                letraCuadranteVPrincipal = "";
            } else {
                letraCuadranteVPrincipal = traducirCardinalidad(letraCuadranteVPrincipal);
            }
            letraCuadranteVGeneradora = codServinformacion.substring(9, 10);
            if (letraCuadranteVGeneradora.equals("0")) {
                letraCuadranteVGeneradora = "";
            } else {
                letraCuadranteVGeneradora = traducirCardinalidad(letraCuadranteVGeneradora);
            }
            nombreCalle = codServinformacion.substring(10, 12);
            if (nombreCalle.equals("00")) {
                nombreCalle = "";
            } else {
                nombreCalle = traducirNombrePrincipal(nombreCalle);
            }

            nombreGeneradora = codServinformacion.substring(12, 14);
            if (nombreGeneradora.equals("00")) {
                nombreGeneradora = "";
            } else {
                nombreGeneradora = traducirNombrePrincipal(nombreGeneradora);
                if (nombreGeneradora.equals("TV") || nombreGeneradora.equals("TR")) {
                    nombreGeneradora = "";
                }
            }

            numViaP = codServinformacion.substring(14, 17);
            numViaP = deleteZeros(numViaP);

            numViaG = codServinformacion.substring(17, 20);
            numViaG = deleteZeros(numViaG);

            letraViaP = codServinformacion.substring(20, 21);
            if (letraViaP.equals("0")) {
                letraViaP = "";
            }

            letraViaG = codServinformacion.substring(21, 22);
            if (letraViaG.equals("0")) {
                letraViaG = "";
            }
            numPosLViaG = codServinformacion.substring(22, 24);
            numPosLViaG = deleteZeros(numPosLViaG);

            numPosLViaP = codServinformacion.substring(24, 26);
            numPosLViaP = deleteZeros(numPosLViaP);

            numCeros = codServinformacion.substring(26, 28);
            numCeros = deleteZeros(numCeros);

            bisViaP = codServinformacion.substring(28, 31);
            if (bisViaP.contains("0")) {
                bisViaP = "";
            }

            bisViaG = codServinformacion.substring(31, 34);
            if (bisViaG.contains("0")) {
                bisViaG = "";
            }

            letraPosbisViaP = codServinformacion.substring(34, 35);
            if (letraPosbisViaP.equals("0")) {
                letraPosbisViaP = "";
            }

            letraPosbisViaG = codServinformacion.substring(35, 36);
            if (letraPosbisViaG.equals("0")) {
                letraPosbisViaG = "";
            }

            placaDireccion = codServinformacion.substring(36, 39);
            placaDireccion = deleteZeros(placaDireccion);

            nombre1Nivel = codServinformacion.substring(39, 42);
            nombre1Nivel = deleteZeros(nombre1Nivel);
            nombre1Nivel = traducirAgregacion(nombre1Nivel);
            nombre1Nivel = nombre1Nivel + deleteZeros(codServinformacion.substring(42, 47));

            nombre2Nivel = codServinformacion.substring(47, 50);
            nombre2Nivel = deleteZeros(nombre2Nivel);
            nombre2Nivel = traducirAgregacion(nombre2Nivel);
            nombre2Nivel = nombre2Nivel + deleteZeros(codServinformacion.substring(50, 55));

            nombre3Nivel = codServinformacion.substring(55, 58);
            nombre3Nivel = deleteZeros(nombre3Nivel);
            nombre3Nivel = traducirAgregacion(nombre3Nivel);
            nombre3Nivel = nombre3Nivel + deleteZeros(codServinformacion.substring(58, 63));

            nombre4Nivel = codServinformacion.substring(63, 66);
            nombre4Nivel = deleteZeros(nombre4Nivel);
            nombre4Nivel = traducirAgregacion(nombre4Nivel);
            nombre4Nivel = nombre4Nivel + deleteZeros(codServinformacion.substring(66, 71));

            nombre5Nivel = codServinformacion.substring(71, 74);
            nombre5Nivel = deleteZeros(nombre5Nivel);
            nombre5Nivel = traducirAgregacion(nombre5Nivel);
            nombre5Nivel = nombre5Nivel + deleteZeros(codServinformacion.substring(74, 79));

            nombre6Nivel = codServinformacion.substring(79, 82);
            nombre6Nivel = deleteZeros(nombre6Nivel);
            nombre6Nivel = traducirAgregacion(nombre6Nivel);
            nombre6Nivel = nombre6Nivel + deleteZeros(codServinformacion.substring(82, 87));
        }

        String calle = nombreCalle + " " + numViaP + letraViaP + numPosLViaP + bisViaP + letraPosbisViaP + letraCuadranteVPrincipal
                + letraCuadranteVGeneradora + " " + nombre3Nivel + " " + nombre5Nivel;

        String placa = nombreGeneradora + numViaG + letraViaG + numPosLViaG + bisViaG + letraPosbisViaG + placaDireccion;
        placa = adecuarTamanodiez(placa);
        String complemento = nombre1Nivel + nombre2Nivel + nombre4Nivel + nombre6Nivel;
        complemento = adecuarTamanodiez(complemento);

        callePlacaComplemento.add(calle);
        callePlacaComplemento.add(placa);
        callePlacaComplemento.add(complemento);
        return callePlacaComplemento;
    }

    /**
     * Obtener calle placa y complemento en formato RR partiendo de el codigo de
     * direccion entregado por Servinformación para una dir tipo Cali
     *
     * @param codServinformacion Codigo de direccion entregado por
     * Servinformación
     * @return Lista con calle, placa y complemento
     */
    private List<String> obtenerDirRrTipoMedellin(StringBuffer codServinformacion) {
        List<String> callePlacaComplemento = new ArrayList<String>();
        String letraCuadranteVPrincipal = "", letraCuadranteVGeneradora = "", nombreCalle = "", numViaP = "", numViaG = "";
        String bisViaP = "", bisViaG = "", letraViaP = "", letraViaG = "", segLetraViaP = "", segLetraViaG = "", placaDireccion = "";
        String nombre1Nivel = "", nombre2Nivel = "", nombre3Nivel = "", nombre4Nivel = "", nombre5Nivel = "", nombre6Nivel = "";

        if (codServinformacion.length() == 79) {
            letraCuadranteVPrincipal = codServinformacion.substring(8, 9);
            if (letraCuadranteVPrincipal.equals("0")) {
                letraCuadranteVPrincipal = "";
            } else {
                letraCuadranteVPrincipal = traducirCardinalidad(letraCuadranteVPrincipal);
            }

            letraCuadranteVGeneradora = codServinformacion.substring(9, 10);
            if (letraCuadranteVGeneradora.equals("0")) {
                letraCuadranteVGeneradora = "";
            } else {
                letraCuadranteVGeneradora = traducirCardinalidad(letraCuadranteVGeneradora);
            }
            nombreCalle = codServinformacion.substring(10, 12);
            if (nombreCalle.equals("00")) {
                nombreCalle = "";
            }
            numViaP = codServinformacion.substring(12, 15);
            numViaP = deleteZeros(numViaP);

            numViaG = codServinformacion.substring(15, 18);
            numViaG = deleteZeros(numViaG);

            bisViaP = codServinformacion.substring(18, 21);
            if (bisViaP.contains("0")) {
                bisViaP = "";
            }

            bisViaG = codServinformacion.substring(21, 24);
            if (bisViaG.contains("0")) {
                bisViaG = "";
            }

            letraViaP = codServinformacion.substring(24, 25);
            if (letraViaP.equals("0")) {
                letraViaP = "";
            }

            letraViaG = codServinformacion.substring(25, 26);
            if (letraViaG.equals("0")) {
                letraViaG = "";
            }

            segLetraViaP = codServinformacion.substring(26, 27);
            if (segLetraViaP.equals("0")) {
                segLetraViaP = "";
            }

            segLetraViaG = codServinformacion.substring(27, 28);
            if (segLetraViaG.equals("0")) {
                segLetraViaG = "";
            }

            placaDireccion = codServinformacion.substring(28, 31);
            placaDireccion = deleteZeros(placaDireccion);

            nombre1Nivel = codServinformacion.substring(31, 34);
            nombre1Nivel = deleteZeros(nombre1Nivel);
            nombre1Nivel = traducirAgregacion(nombre1Nivel);
            nombre1Nivel = nombre1Nivel + deleteZeros(codServinformacion.substring(34, 39));

            nombre2Nivel = codServinformacion.substring(39, 42);
            nombre2Nivel = deleteZeros(nombre2Nivel);
            nombre2Nivel = traducirAgregacion(nombre2Nivel);
            nombre2Nivel = nombre2Nivel + deleteZeros(codServinformacion.substring(42, 47));

            nombre3Nivel = codServinformacion.substring(47, 50);
            nombre3Nivel = deleteZeros(nombre3Nivel);
            nombre3Nivel = traducirAgregacion(nombre3Nivel);
            nombre3Nivel = nombre3Nivel + deleteZeros(codServinformacion.substring(50, 55));

            nombre4Nivel = codServinformacion.substring(55, 58);
            nombre4Nivel = deleteZeros(nombre4Nivel);
            nombre4Nivel = traducirAgregacion(nombre4Nivel);
            nombre4Nivel = nombre4Nivel + deleteZeros(codServinformacion.substring(58, 63));

            nombre5Nivel = codServinformacion.substring(63, 66);
            nombre5Nivel = deleteZeros(nombre5Nivel);
            nombre5Nivel = traducirAgregacion(nombre5Nivel);
            nombre5Nivel = nombre5Nivel + deleteZeros(codServinformacion.substring(66, 71));

            nombre6Nivel = codServinformacion.substring(71, 74);
            nombre6Nivel = deleteZeros(nombre6Nivel);
            nombre6Nivel = traducirAgregacion(nombre6Nivel);
            nombre6Nivel = nombre6Nivel + deleteZeros(codServinformacion.substring(74, 79));

        }

        String calle = nombreCalle + " " + numViaP + letraViaP + segLetraViaP + bisViaP + letraCuadranteVPrincipal + letraCuadranteVGeneradora + " " + nombre3Nivel + " " + nombre5Nivel;
        String placa = numViaG + letraViaG + segLetraViaG + bisViaG + "-" + placaDireccion;
        placa = adecuarTamanodiez(placa);
        String complemento = nombre1Nivel + nombre2Nivel + nombre4Nivel + nombre6Nivel;
        complemento = adecuarTamanodiez(complemento);

        callePlacaComplemento.add(calle);
        callePlacaComplemento.add(placa);
        callePlacaComplemento.add(complemento);
        return callePlacaComplemento;
    }

    /**
     * Obtener calle placa y complemento en formato RR partiendo de el codigo de
     * direccion entregado por Servinformación para una dir tipo manzana Casa
     *
     * @param codServinformacion Codigo de direccion entregado por
     * Servinformación
     * @return Lista con calle, placa y complemento
     */
    private List<String> obtenerDirRrTipoManzanaCasa(StringBuffer codServinformacion, Geografico geografico) {
        List<String> callePlacaComplemento = new ArrayList<String>();
        String nombre1Nivel = "", nombre2Nivel = "", nombre3Nivel = "", nombre4Nivel = "", nombre5Nivel = "", nombre6Nivel = "";
        String nombre7Nivel = "", nombre8Nivel = "", nombre9Nivel = "", nombre10Nivel = "", nombre11Nivel = "", nombre12Nivel = "";

        if (codServinformacion.length() == 99) {
            nombre1Nivel = codServinformacion.substring(3, 6);
            nombre1Nivel = deleteZeros(nombre1Nivel);
            nombre1Nivel = traducirNivMzCS(nombre1Nivel);
            nombre1Nivel = nombre1Nivel + deleteZeros(codServinformacion.substring(6, 11));

            nombre2Nivel = codServinformacion.substring(11, 14);
            nombre2Nivel = deleteZeros(nombre2Nivel);
            nombre2Nivel = traducirNivMzCS(nombre2Nivel);
            nombre2Nivel = nombre2Nivel + deleteZeros(codServinformacion.substring(14, 19));

            nombre3Nivel = codServinformacion.substring(19, 22);
            nombre3Nivel = deleteZeros(nombre3Nivel);
            nombre3Nivel = traducirNivMzCS(nombre3Nivel);
            nombre3Nivel = nombre3Nivel + deleteZeros(codServinformacion.substring(22, 27));

            nombre4Nivel = codServinformacion.substring(27, 30);
            nombre4Nivel = deleteZeros(nombre4Nivel);
            nombre4Nivel = traducirNivMzCS(nombre4Nivel);
            nombre4Nivel = nombre4Nivel + deleteZeros(codServinformacion.substring(30, 35));

            nombre5Nivel = codServinformacion.substring(35, 38);
            nombre5Nivel = deleteZeros(nombre5Nivel);
            nombre5Nivel = traducirNivMzCS(nombre5Nivel);
            nombre5Nivel = nombre5Nivel + deleteZeros(codServinformacion.substring(38, 43));

            nombre6Nivel = codServinformacion.substring(43, 46);
            nombre6Nivel = deleteZeros(nombre6Nivel);
            nombre6Nivel = traducirNivMzCS(nombre6Nivel);
            nombre6Nivel = nombre6Nivel + deleteZeros(codServinformacion.substring(46, 51));

            nombre7Nivel = codServinformacion.substring(51, 54);
            nombre7Nivel = deleteZeros(nombre7Nivel);
            nombre7Nivel = traducirNivMzCS(nombre7Nivel);
            nombre7Nivel = nombre7Nivel + deleteZeros(codServinformacion.substring(54, 59));

            nombre8Nivel = codServinformacion.substring(59, 62);
            nombre8Nivel = deleteZeros(nombre8Nivel);
            nombre8Nivel = traducirNivMzCS(nombre8Nivel);
            nombre8Nivel = nombre8Nivel + deleteZeros(codServinformacion.substring(62, 67));

            nombre9Nivel = codServinformacion.substring(67, 70);
            nombre9Nivel = deleteZeros(nombre9Nivel);
            nombre9Nivel = traducirNivMzCS(nombre9Nivel);
            nombre9Nivel = nombre9Nivel + deleteZeros(codServinformacion.substring(70, 75));

            nombre10Nivel = codServinformacion.substring(75, 78);
            nombre10Nivel = deleteZeros(nombre10Nivel);
            nombre10Nivel = traducirNivMzCS(nombre10Nivel);
            nombre10Nivel = nombre10Nivel + deleteZeros(codServinformacion.substring(78, 83));

            nombre11Nivel = codServinformacion.substring(83, 86);
            nombre11Nivel = deleteZeros(nombre11Nivel);
            nombre11Nivel = traducirNivMzCS(nombre11Nivel);
            nombre11Nivel = nombre11Nivel + deleteZeros(codServinformacion.substring(86, 91));

            nombre12Nivel = codServinformacion.substring(91, 94);
            nombre12Nivel = deleteZeros(nombre12Nivel);
            nombre12Nivel = traducirNivMzCS(nombre12Nivel);
            nombre12Nivel = nombre12Nivel + deleteZeros(codServinformacion.substring(94, 98));
        }
        if (nombre1Nivel.contains("BARRIO")) {
            //TODO poner el barrio que entrega el GEO
            nombre1Nivel = geografico.getGeoNombre();
        }

        String calle = nombre1Nivel + nombre2Nivel + " " + nombre3Nivel + " " + nombre4Nivel + " " + nombre5Nivel;
        String placa = "0-00";
        placa = adecuarTamanodiez(placa);
        String complemento = nombre6Nivel + nombre7Nivel + nombre8Nivel + nombre9Nivel + nombre10Nivel + nombre11Nivel + nombre12Nivel;

        callePlacaComplemento.add(calle);
        callePlacaComplemento.add(placa);
        callePlacaComplemento.add(complemento);
        complemento = adecuarTamanodiez(complemento);
        return callePlacaComplemento;
    }

    /**
     *
     * @param value
     * @return
     */
    public String deleteZeros(String value) {
        int i, longitud = 0;
        longitud = value.length();
        String x = "0";
        String espacio = " ";
        String cadenaTotal = "";
        if (value.contains(x) || value.contains(espacio)) {
            for (i = 0; i < longitud; i++) {
                if (x.charAt(0) != value.charAt(i) && espacio.charAt(0) != value.charAt(i)) {
                    boolean num = Character.isDigit(value.charAt(i));
                    if (num) {
                        cadenaTotal += value.substring(i, longitud);
                        i = longitud;
                    } else {
                        cadenaTotal += value.charAt(i);
                    }
                }
            }
        } else {
            cadenaTotal = value;
        }
        return cadenaTotal;
    }

    /**
     *
     * @param valor
     * @return
     */
    private String traducirNombrePrincipal(String valor) {
        String respuesta = "";
        if (valor.equals("AC")) {
            respuesta = "AVCL";
        } else if (valor.equals("AK")) {
            respuesta = "AVCR";
        } else if (valor.equals("CAM")) {
            respuesta = "CAMINO";
        } else if (valor.equals("CQ")) {
            respuesta = "CIR";
        } else if (valor.equals("CT")) {
            respuesta = "CARRETERA";
        } else if (valor.equals("KR")) {
            respuesta = "CR";
        } else if (valor.equals("PJ")) {
            respuesta = "PASAJE";
        } else if (valor.equals("PS")) {
            respuesta = "PASEO";
        } else if (valor.equals("PT")) {
            respuesta = "PEATONAL";
        } else if (valor.equals("TV")) {
            respuesta = "TR";
        } else {
            respuesta = valor;
        }
        return respuesta;
    }

    /**
     *
     * @param valor
     * @return
     */
    private String traducirNivMzCS(String valor) {
        String respuesta = "";
        if (valor.equals("UL") || valor.equals("MT") || valor.equals("CO")) {
            respuesta = "UR";
        } else if (valor.equals("BR")) {
            respuesta = "BARRIO";
        } else if (valor.equals("CD")) {
            respuesta = "CIUDADELA";
        } else if (valor.equals("SM")) {
            respuesta = "SMZ";
        } else if (valor.equals("AG")) {
            respuesta = "AG";
        } else if (valor.equals("TO")) {
            respuesta = "TORRE";
        } else if (valor.equals("BQ")) {
            respuesta = "BL";
        } else if (valor.equals("ET")) {
            respuesta = "ETAPA";
        } else if (valor.equals("SC")) {
            respuesta = "SECTOR";
        } else if (valor.equals("CS")) {
            respuesta = "C";
        } else if (valor.equals("AP")) {
            respuesta = "";
        } else if (valor.equals("LT")) {
            respuesta = "LOTE";
        } else {
            respuesta = valor;
        }
        return respuesta;
    }

    /**
     *
     * @param valor
     * @return
     */
    private String traducirAgregacion(String valor) {
        String respuesta = "";
        if (valor.equals("CD")) {
            respuesta = "CIUDADELA";
        } else if (valor.equals("BR")) {
            respuesta = "BARRIO";
        } else if (valor.equals("UL") || valor.equals("MT") || valor.equals("CO")) {
            respuesta = "UR";
        } else if (valor.equals("SM")) {
            respuesta = "SMZ";
        } else if (valor.equals("SC")) {
            respuesta = "SECTOR";
        } else if (valor.equals("BQ")) {
            respuesta = "BL";
        } else if (valor.equals("CU")) {
            respuesta = "CELULA";
        } else if (valor.equals("TO")) {
            respuesta = "TORRE";
        } else if (valor.equals("LT")) {
            respuesta = "LOTE";
        } else if (valor.equals("AP")) {
            respuesta = "";
        } else if (valor.equals("CS")) {
            respuesta = "C";
        } else if (valor.equals("CV")) {
            respuesta = "CIRCUNVALAR";
        } else if (valor.equals("PI")) {
            respuesta = "PISO";
        } else if (valor.equals("BG") || valor.equals("DP")) {
            respuesta = "BODEGA";
        } else if (valor.equals("GS") || valor.equals("GA") || valor.equals("DS")) {
            respuesta = "SO";
        } else if (valor.equals("BL")) {
            respuesta = "BULEVAR";
        } else if (valor.equals("GT")) {
            respuesta = "GLORIETA";
        } else if (valor.equals("EX")) {
            respuesta = "EXT";
        } else if (valor.equals("ET")) {
            respuesta = "ETAPA";
        } else if (valor.equals("MN")) {
            respuesta = "MEZZANINE";
        } else if (valor.equals("SA")) {
            respuesta = "SALON";
        } else if (valor.equals("SCM") || valor.equals("AD") || valor.equals("PR")) {
            respuesta = "ADMIN";
        } else if (valor.equals("AN")) {
            respuesta = "CASA";
        } else if (valor.equals("ST") || valor.equals("PN")) {
            respuesta = "PH";
        } else if (valor.equals("LC")) {
            respuesta = "LOCAL";
        } else if (valor.equals("PQ")) {
            respuesta = "PARQUE";
        } else if (valor.equals("OF")) {
            respuesta = "OFC";
        } else if (valor.equals("CG")) {
            respuesta = "CORREGIMIENTO";
        } else if (valor.equals("CM")) {
            respuesta = "CAMINO";
        } else if (valor.equals("FI")) {
            respuesta = "FINCA";
        } else if (valor.equals("KM")) {
            respuesta = "KILOMETRO";
        } else if (valor.equals("VE")) {
            respuesta = "VD";
        } else if (valor.equals("VI")) {
            respuesta = "VIA";
        } else if (valor.equals("VT")) {
            respuesta = "VARIANTE";
        } else {
            respuesta = valor;
        }
        return respuesta;
    }

    /**
     *
     * @param valor
     * @return
     */
    private String adecuarTamanodiez(String valor) {
        String cadenaDiez = valor;
        if (cadenaDiez.length() == 0) {
            cadenaDiez = "          ";
        } else if (cadenaDiez.length() < 10) {
            while (cadenaDiez.length() < 10) {
                cadenaDiez = " " + cadenaDiez;
            }
        } else if (cadenaDiez.length() > 10) {
            cadenaDiez = cadenaDiez.trim();
        }
        return cadenaDiez;
    }

    /**
     * Modificar letra por cardinalidad equivalente
     *
     * @param cardinalidad letra q eqiuvale a un cardinal
     * @return nombre de cardinalidad a la que equivale el parametro recibido.
     */
    private String traducirCardinalidad(String cardinalidad) {
        String traduccion = "";
        if (cardinalidad.equals("E")) {
            traduccion = "ESTE";
        } else if (cardinalidad.equals("O")) {
            traduccion = "OESTE";
        } else if (cardinalidad.equals("N")) {
            traduccion = "NORTE";
        } else if (cardinalidad.equals("S")) {
            traduccion = "SUR";
        }
        return traduccion;
    }

    /**
     *
     * @return
     */
    public String getMessagePopup() {
        return messagePopup;
    }

    /**
     *
     * @param messagePopup
     */
    public void setMessagePopup(String messagePopup) {
        this.messagePopup = messagePopup;
    }

    /**
     *
     * @return
     */
    public String getSeleccionar() {
        return seleccionar;
    }

    /**
     *
     * @param seleccionar
     */
    public void setSeleccionar(String seleccionar) {
        this.seleccionar = seleccionar;
    }

    /**
     *
     * @return
     */
    public String getSon_tipoSolicitud() {
        return son_tipoSolicitud;
    }

    /**
     *
     * @param son_tipoSolicitud
     */
    public void setSon_tipoSolicitud(String son_tipoSolicitud) {
        this.son_tipoSolicitud = son_tipoSolicitud;
    }

    /**
     *
     * @return
     */
    public int getContador() {
        return contador;
    }

    /**
     *
     * @param contador
     */
    public void setContador(int contador) {
        this.contador = contador;
    }

    /**
     *
     * @return
     */
    public boolean isDisableProcesar() {
        return disableProcesar;
    }

    /**
     *
     * @param disableProcesar
     */
    public void setDisableProcesar(boolean disableProcesar) {
        this.disableProcesar = disableProcesar;
    }

    /**
     *
     * @return
     */
    public boolean isRenderedDescargar() {
        return renderedDescargar;
    }

    /**
     *
     * @param renderedDescargar
     */
    public void setRenderedDescargar(boolean renderedDescargar) {
        this.renderedDescargar = renderedDescargar;
    }

    /**
     *
     * @return
     */
    public boolean isRenderedProcesar() {
        return renderedProcesar;
    }

    /**
     *
     * @param renderedProcesar
     */
    public void setRenderedProcesar(boolean renderedProcesar) {
        this.renderedProcesar = renderedProcesar;
    }

    /**
     *
     * @return
     */
    public boolean isShowpopUp() {
        return showpopUp;
    }

    /**
     *
     * @param showpopUp
     */
    public void setShowpopUp(boolean showpopUp) {
        this.showpopUp = showpopUp;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getLstTSolicitud() {
        return lstTSolicitud;
    }

    /**
     *
     * @param lstTSolicitud
     */
    public void setLstTSolicitud(List<SelectItem> lstTSolicitud) {
        this.lstTSolicitud = lstTSolicitud;
    }
}
