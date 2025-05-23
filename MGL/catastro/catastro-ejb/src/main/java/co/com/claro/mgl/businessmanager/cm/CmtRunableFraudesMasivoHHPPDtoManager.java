/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.MarcasMglManager;
import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.dtos.CmtFraudesMasivoHHPPDtoMgl;
import co.com.claro.mgl.dtos.FraudesHHPPMasivoDto;
import co.com.claro.mgl.entities.procesomasivo.CargueArchivoLogItem;
import co.com.claro.mgl.entities.procesomasivo.HhppCargueArchivoLog;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.manager.procesomasivo.CargueArchivoLogItemManager;
import co.com.claro.mgl.manager.procesomasivo.HhppCargueArchivoLogManager;
import co.com.claro.mgl.utils.Constant;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author enriquedm
 */
public class CmtRunableFraudesMasivoHHPPDtoManager implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(CmtRunableFraudesMasivoHHPPDtoManager.class);

    Date fechaInicio;
    Date fechaFin;
    BigDecimal estado;
    String usuario;
    Timer timer;
    String nombreArchivo;
    List<FraudesHHPPMasivoDto> listaModificacion;
    int perfil;
    private String headerFiltros;
    private String[] headerSel;
    HhppMglManager hhppMglManager;
    HashMap<CmtDireccionDetalladaMgl, List<MarcasMgl>> mapaDireccionesFraudesAdd;
    MarcasMglManager marcasMglManager = new MarcasMglManager();
    CargueArchivoLogItemManager calim = new CargueArchivoLogItemManager();
    CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
    HhppCargueArchivoLogManager cargueArchivoManager;
    HhppCargueArchivoLog cargueArchivoLog;

    private static final String LINEA = "Linea";
    private static final String ID_DIRECCION_DETALLADA = "ID Direccion Detallada";
    private static final String DEPARTAMENTO = "Departamento";
    private static final String MUNICIPIO = "Municipio";
    private static final String CENTRO_POBLADO = "Centro Poblado";
    private static final String NODO = "Nodo";
    private static final String DIRECCION = "Direccion";
    private static final String COD_MARCAS_FRAUDE = "Codigo Marcas de Fraude";
    private static final String COD_MARCAS_FRAUDE_NUEVA = "Codigo Marcas de Fraude Nueva";
    private static final String DETALLE = "Detalle";


    public CmtRunableFraudesMasivoHHPPDtoManager() {
    }

    public CmtRunableFraudesMasivoHHPPDtoManager(List<FraudesHHPPMasivoDto> listaModificacion, int perfil, String usuario, String nombreArchivo) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuario = usuario;
        this.timer = new Timer();
        this.perfil = perfil;
        this.nombreArchivo = nombreArchivo;
        hhppMglManager = new HhppMglManager();
        this.listaModificacion = listaModificacion;
    }

    @Override
    public void run() {
        int registros = 0;
        List<FraudesHHPPMasivoDto> listaFraudesHHPPMasivoDto = new ArrayList<FraudesHHPPMasivoDto>();
        List<FraudesHHPPMasivoDto> fmMgls = new ArrayList<FraudesHHPPMasivoDto>();

        try {
            CmtFraudesMasivoHHPPDtoMgl.cleanBeforeStart();
            CmtFraudesMasivoHHPPDtoMgl.startProcess(usuario);
        } catch (ApplicationException ex) {
            CmtFraudesMasivoHHPPDtoMgl.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        //consultar numero de registros y asiganr a siguiente linea
        registros = this.listaModificacion.size();

        if (registros > 0) {
            try {
                //Crear el archivo Log en BD
                cargueArchivoLog = crearArchivoLogBD();
                CmtFraudesMasivoHHPPDtoMgl.setUserRunningProcess(this.usuario);
                CmtFraudesMasivoHHPPDtoMgl.setNombreArchivo(this.nombreArchivo);
            } catch (ApplicationException e) {
                String msn = "Se produjo un error al crear el ArchivoLog en BD";
                LOGGER.error(msn + e.getMessage());
                return;
            }
        }

        if (registros > 0) {
            CmtFraudesMasivoHHPPDtoMgl.setNumeroRegistrosAProcesar(registros);

            if (listaModificacion.size() > 0) {
                    try {
                        int count = 0;
                        int cantFallido = 0;
                        for (FraudesHHPPMasivoDto m : this.listaModificacion) {
                            
                            if (m.getIdDireccionDetallada() != null && !m.getIdDireccionDetallada().equals(BigDecimal.ZERO)) {
                                CmtDireccionDetalladaMgl cmtIdDireccion = null;
                                cmtIdDireccion = buscarIdDireccion(m.getIdDireccionDetallada());
                                String barrio = "";
                                if (cmtIdDireccion != null) {
                                    
                                    if (cmtIdDireccion.getBarrio() != null && !cmtIdDireccion.getBarrio().isEmpty()) {
                                        barrio = cmtIdDireccion.getBarrio();
                                    }
                                    if (m.getCodMarcaNuevo() != null && !m.getCodMarcaNuevo().isEmpty()) {
                                        if (m.getCodMarcaNuevo().trim().length() > 1) {
                                            String[] marcas = m.getCodMarcaNuevo().trim().split("\\,");
                                            for (int i = 0; i < marcas.length; i++) {
                                                //filtrar Marcas para Fraudes
                                                MarcasMgl marcaMgl = null;
                                                if (!marcas[i].contains(",")) {
                                                    marcaMgl = buscarMarcas(marcas[i].toUpperCase());
                                                    if (marcaMgl != null) {
                                                        //Agregar marca
                                                        m.setProcesado(addMarca(cmtIdDireccion, marcaMgl));
                                                    } else {
                                                        cantFallido+=1;
                                                        m.setProcesado(false);
                                                        m.setDetalle(Constant.MSN_ERROR_MARCA);
                                                        break;
                                                    }
                                                }
                                            }
                                        } else {
                                            //filtrar Marcas para Fraudes
                                            MarcasMgl marcaMgl = buscarMarcas(m.getCodMarcaNuevo().toUpperCase());
                                            
                                            if (marcaMgl != null) {
                                                //Agregar marca
                                                m.setProcesado(addMarca(cmtIdDireccion, marcaMgl));
                                            } else {
                                                cantFallido+=1;
                                                m.setProcesado(false);
                                                m.setDetalle(Constant.MSN_ERROR_MARCA);
                                            }
                                        }
                                    } else {
                                        String msn = "La marca esta vacía";
                                        cantFallido+=1;
                                        m.setProcesado(false);
                                        m.setDetalle(msn);
                                        LOGGER.error(msn);
                                    }
                                } else {
                                    if (m.getDireccion() != null && !m.getDireccion().isEmpty()) {
                                        CmtDireccionDetalladaMgl cmtDireccion = null;
                                        cmtDireccion = buscarDireccion(m.getDireccion(), m.getCentroPoblado(), null);
                                        if (cmtDireccion != null && !cmtDireccion.equals("")) {
                                            CmtDireccionDetalladaMgl cmtIdDireccionMgl = null;
                                            cmtIdDireccionMgl = buscarIdDireccion(cmtDireccion.getDireccionDetalladaId());
                                            if (cmtIdDireccionMgl != null) {
                                                m.setIdDireccionDetallada(cmtDireccion.getDireccionDetalladaId());
                                                String[] marcas = m.getCodMarcaNuevo().trim().split("\\,");
                                                for (int i = 0; i < marcas.length; i++) {
                                                    //filtrar Marcas para Fraudes
                                                    //filtrar Marcas para Fraudes
                                                    MarcasMgl marcaMgl = buscarMarcas(marcas[i].toUpperCase());

                                                    if (marcaMgl != null) {
                                                        //Agregar marca
                                                        m.setProcesado(addMarca(cmtIdDireccionMgl, marcaMgl));
                                                    } else {
                                                        cantFallido += 1;
                                                        m.setProcesado(false);
                                                        m.setDetalle(Constant.MSN_ERROR_MARCA);
                                                    }
                                                }
                                            } else {
                                                cantFallido += 1;
                                                m.setProcesado(false);
                                                m.setDetalle(Constant.MSN_DIR_NO_VALIDADA);
                                            }

                                        } else {
                                            cantFallido += 1;
                                            m.setProcesado(false);
                                            m.setDetalle(Constant.MSN_DIR_NO_VALIDADA);
                                        }
                                    } else {
                                        cantFallido += 1;
                                        m.setProcesado(false);
                                        m.setDetalle(Constant.MSN_DIR_NO_VALIDADA);
                                    }
                                }
                            } else {
                                String barrio = "";
                                if (m.getDireccion() != null && !m.getDireccion().isEmpty()) {
                                    CmtDireccionDetalladaMgl cmtDireccion = null;
                                    cmtDireccion = buscarDireccion(m.getDireccion(), m.getCentroPoblado(),null);
                                    if (cmtDireccion != null && !cmtDireccion.equals("")) {
                                        CmtDireccionDetalladaMgl cmtIdDireccion = null;
                                        cmtIdDireccion = buscarIdDireccion(cmtDireccion.getDireccionDetalladaId());
                                        if (cmtIdDireccion != null) {
                                            m.setIdDireccionDetallada(cmtDireccion.getDireccionDetalladaId());
                                            String[] marcas = m.getCodMarcaNuevo().trim().split("\\,");
                                            for (int i = 0; i < marcas.length; i++) {
                                                //filtrar Marcas para Fraudes
                                                //filtrar Marcas para Fraudes
                                                MarcasMgl marcaMgl = buscarMarcas(marcas[i].toUpperCase());

                                                if (marcaMgl != null) {
                                                    //Agregar marca
                                                    m.setProcesado(addMarca(cmtIdDireccion, marcaMgl));
                                                } else {
                                                    cantFallido += 1;
                                                    m.setProcesado(false);
                                                    m.setDetalle(Constant.MSN_ERROR_MARCA);
                                                }
                                            }
                                        } else {
                                            cantFallido += 1;
                                            m.setProcesado(false);
                                            m.setDetalle(Constant.MSN_DIR_NO_VALIDADA);
                                        }

                                    } else {
                                        cantFallido += 1;
                                        m.setProcesado(false);
                                        m.setDetalle(Constant.MSN_DIR_NO_ENCONTRADA);
                                    }
                                } else {
                                    cantFallido += 1;
                                    m.setProcesado(false);
                                    m.setDetalle(Constant.MSN_DIR_NO_VALIDADA);
                                }
                            }
                            count += 1;
                            CmtFraudesMasivoHHPPDtoMgl.setNumeroRegistrosProcesados(count);
                            //Actualizar Registros procesados
                            cargueArchivoLog.setCantidadProcesada(new BigInteger("" + count));
                            cargueArchivoLog.setCantidadFallido(new BigInteger("" + cantFallido));
                            actualizarArchivoLogBD(cargueArchivoLog);

                        } //fin for
                        if (mapaDireccionesFraudesAdd != null && !mapaDireccionesFraudesAdd.isEmpty()
                                && mapaDireccionesFraudesAdd.size() > 0) {
                            //Guardar Marcas, enviar al servicio
                            guardarMarcasHHPP();
                            
                            try {
                                //Crear el detalle del archivo Log en BD
                                guardarCargueArchivosLogItemBD(listaModificacion, cargueArchivoLog.getIdArchivoLog());
                            } catch (ApplicationException ex) {
                                LOGGER.error("Se produjo error en el método guardarCargueArchivosLogItemBD " + ex.getMessage());
                            }
                        }else {
                            try {
                                //Crear el detalle del archivo Log en BD
                                guardarCargueArchivosLogItemBD(listaModificacion, cargueArchivoLog.getIdArchivoLog());
                            } catch (ApplicationException ex) {
                                LOGGER.error("Se produjo error en el método guardarCargueArchivosLogItemBD " + ex.getMessage());
                            }
                        }
                        fmMgls.addAll(this.listaModificacion);

                        CmtFraudesMasivoHHPPDtoMgl.setListFraudesHHPPMasivoDto(fmMgls);

                    } catch (ApplicationException ex) {
                        String msg = "Se produjo un error al actualizar el ArchivoLog en BD '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                        LOGGER.error(msg, ex);
                    }
            }
        }

        try {
            CmtFraudesMasivoHHPPDtoMgl.endProcess(fmMgls);
        } catch (ApplicationException ex) {
            CmtFraudesMasivoHHPPDtoMgl.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
    }

    /**
     * Diana -> Validar que la Id Direccion Detallada exista
     */
    private CmtDireccionDetalladaMgl buscarIdDireccion(BigDecimal idDireccionDetallada) {
        CmtDireccionDetalladaMgl direccion = null;
        try {
            direccion = cmtDireccionDetalleMglManager.findById(idDireccionDetallada);
            //Obtenemos los Hhpp de la Subdireccion  
            if (direccion.getSubDireccion() != null
                    && direccion.getSubDireccion().getSdiId() != null) {

                List<HhppMgl> hhhpSubDirList = hhppMglManager
                        .findHhppSubDireccion(direccion.getSubDireccion().getSdiId());

                if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                    if (hhhpSubDirList.get(0).getEstadoRegistro() == 1) {
                        direccion.setHhppExistente(true);
                        direccion.setHhppMgl(hhhpSubDirList.get(0));
                        //Obtener numero de cuenta matriz si tiene asociada
                        if (hhhpSubDirList.get(0).getHhppSubEdificioObj() != null && hhhpSubDirList.get(0).getHhppSubEdificioObj()
                                .getCmtCuentaMatrizMglObj() != null) {
                            //Asignamos el número de la cuenta matriz
                            direccion.setNumeroCuentaMatriz(hhhpSubDirList.get(0).getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getCuentaMatrizId()
                                    .toString());
                        }
                    }
                }
            } else {
                //Obtenemos los Hhpp de la Direccion principal    
                if (direccion.getDireccion() != null
                        && direccion.getDireccion().getDirId() != null) {

                    List<HhppMgl> hhhpDirList = hhppMglManager
                                    .findHhppDireccion(direccion.getDireccion().getDirId());

                    if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                        if (hhhpDirList.get(0).getEstadoRegistro() == 1) {
                            direccion.setHhppExistente(true);
                            direccion.setHhppMgl(hhhpDirList.get(0));
                            //Obtener numero de cuenta matriz si tiene asociada
                            if (hhhpDirList.get(0).getHhppSubEdificioObj() != null && hhhpDirList.get(0).getHhppSubEdificioObj()
                                    .getCmtCuentaMatrizMglObj() != null) {
                                //Asignamos el número de la cuenta matriz
                                direccion.setNumeroCuentaMatriz(hhhpDirList.get(0).getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getNumeroCuenta()
                                        .toString());
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en buscarIdDireccion. " + e.getMessage(), e);
        }
        return direccion;
    }

    /**
     * Diana -> Validar que la dirección texto exista
     */
    private CmtDireccionDetalladaMgl buscarDireccion(String direccion, String centroPoblado, String tipoDireccion) {
        List<CmtDireccionDetalladaMgl> direccionDet = null;

        try {
            GeograficoPoliticoManager geo = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl idCentroPoblado = geo.findGeoPolByNombreAndTipo(centroPoblado, "CENTRO POBLADO");
            direccionDet = cmtDireccionDetalleMglManager.findDireccionDetalladaByDireccionTexto(direccion, idCentroPoblado.getGpoId(), true, null, tipoDireccion);
        } catch (ApplicationException e) {
            LOGGER.error("Error en buscarDireccion. " + e.getMessage(), e);
        }

        if (direccionDet != null && !direccionDet.isEmpty()) {
            return direccionDet.get(0);
        }

        return null;
    }

    /**
     * Diana -> Validar que las marcas existan, y correspondan al Tipo_Marca
     * Fraude
     */
    public MarcasMgl buscarMarcas(String CodMarca) {
        try {
            MarcasMgl marcaMgl = null;
            List<MarcasMgl> marcasMgl = marcasMglManager.findByGrupoCodigo(Constant.TEC_TIPO_MARCA_FRAUDE);

            for (MarcasMgl mrc : marcasMgl) {
                if (mrc.getMarCodigo().equals(CodMarca)) {
                    marcaMgl = mrc;
                }
            }
            return marcaMgl;

        } catch (ApplicationException ex) {
            LOGGER.error("Se produjo error al consultar las marcas" + ex.getMessage());
            return null;
        }
    }

    /**
     * Diana -> Enviar marcas al servicio
     */
    public void guardarMarcasHHPP() {
        int[] regFallidos = new int[1];
        int[] regExitosos = new int[1];
        regFallidos[0]= cargueArchivoLog.getCantidadFallido().intValue();
        try {
            HhppMglManager hhppMglManager = new HhppMglManager();
            mapaDireccionesFraudesAdd.keySet().forEach((CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl) -> {

                try {
                    if (mapaDireccionesFraudesAdd.get(cmtDireccionDetalladaMgl).isEmpty()) {
                        return;
                    }

                    if (cmtDireccionDetalladaMgl.getHhppMgl() != null) {
                        CmtDefaultBasicResponse responseMarcas = hhppMglManager.agregarMarcasHhpp(
                                cmtDireccionDetalladaMgl.getHhppMgl(), mapaDireccionesFraudesAdd.get(cmtDireccionDetalladaMgl), this.usuario);
                        if (responseMarcas.getMessage() != null && !responseMarcas.getMessage().isEmpty() 
                                && responseMarcas.getMessageType().equals("E")) {
                            //mensajesError.append(" ").append(responseMarcas.getMessage());
                            if (listaModificacion != null && !listaModificacion.isEmpty()) {
                                listaModificacion.forEach((FraudesHHPPMasivoDto dto) -> {
                                    if (dto.getIdDireccionDetallada().equals(cmtDireccionDetalladaMgl.getDireccionDetalladaId())) {
                                        dto.setDetalle(dto.getDetalle() + ". WSError: " + responseMarcas.getMessage());
                                    }
                                });
                                //Actualizar Registros Fallidos
                                regFallidos[0] += 1;
                                cargueArchivoLog.setCantidadFallido(new BigInteger("" + regFallidos[0]));
                                actualizarArchivoLogBD(cargueArchivoLog);
                            }
                        } else {
                            if (listaModificacion != null && !listaModificacion.isEmpty()) {
                                listaModificacion.forEach((FraudesHHPPMasivoDto dto) -> {
                                    if (dto.getIdDireccionDetallada().equals(cmtDireccionDetalladaMgl.getDireccionDetalladaId())) {
                                        dto.setDetalle(dto.getDetalle() + " Proceso Exitoso.");
                                    }
                                });

                                //Actualizar Registros Exitosos
                                int num = 1;
                                int num2 = Integer.parseInt(cargueArchivoLog.getCantidadExitoso().toString());                                
                                int sum = (num + num2);

                                cargueArchivoLog.setCantidadExitoso(new BigInteger("" + sum));
                                actualizarArchivoLogBD(cargueArchivoLog);
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Se produjo error al realizar la actualización  Adicion de marcas en RR " + e.getMessage());
                }
            });
        } catch (Exception e) {
            LOGGER.error("Se produjo error al realizar la actualización de marcas en RR " + e.getMessage());
        }
    }

    /**
     * Diana -> Agregar marca a la tabla final para enviar las marcas al
     * servicio
     */
    public Boolean addMarca(CmtDireccionDetalladaMgl direccion, MarcasMgl marca) {
        try {
            if (mapaDireccionesFraudesAdd == null) {
                mapaDireccionesFraudesAdd = new HashMap<>(); 
            }
            
            if (!mapaDireccionesFraudesAdd.keySet().contains(direccion)) {
                mapaDireccionesFraudesAdd.put(direccion, new ArrayList<>());
            }
            mapaDireccionesFraudesAdd.get(direccion).add(marca);
            
        } catch (Exception e) {
            LOGGER.error("Se produjo error al agregar marcas en la lista del archivo " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Diana -> Guardar Archivo Log en BD
     */
    protected HhppCargueArchivoLog crearArchivoLogBD() throws ApplicationException {
        try {
            HhppCargueArchivoLog hhppCargueArchivoLog = new HhppCargueArchivoLog();
            hhppCargueArchivoLog.setNombreArchivoTcrm(null);
            hhppCargueArchivoLog.setEstado(Short.valueOf("1"));
            Date fechaRegistro = new Date();
            hhppCargueArchivoLog.setFechaRegistro(fechaRegistro);
            hhppCargueArchivoLog.setFechaModificacion(fechaRegistro);
            hhppCargueArchivoLog.setCantidadTotal(new BigInteger("" + listaModificacion.size()));
            hhppCargueArchivoLog.setCantidadProcesada(new BigInteger("" + 0));
            hhppCargueArchivoLog.setUsuario(usuario);
            hhppCargueArchivoLog.setUtilizoRollback("N");
            hhppCargueArchivoLog.setEnvioTcrm("N");
            hhppCargueArchivoLog.setTipoMod(0);
            hhppCargueArchivoLog.setNombreArchivoCargue(nombreArchivo);
            hhppCargueArchivoLog.setOrigen("FRAUDE");
            hhppCargueArchivoLog.setCantidadExitoso(new BigInteger("" + 0));
            hhppCargueArchivoLog.setCantidadFallido(new BigInteger("" + 0));

            hhppCargueArchivoLog = getCargueArchivoManager().create(hhppCargueArchivoLog);
            return hhppCargueArchivoLog;

        } catch (ApplicationException e) {
            String msn = Constant.MSG_REPORTE_NOMBRE_ARCHIVO;
            LOGGER.error(msn + ": " + e.getMessage());
            throw new ApplicationException(msn + ": " + e.getMessage());
        }
    }

    /**
     * Diana -> Guardar detalle de Archivo Log en BD
     */
    public void guardarCargueArchivosLogItemBD(List<FraudesHHPPMasivoDto> listaModificacion,
            long idArchivoLog) throws ApplicationException {
        for (FraudesHHPPMasivoDto item : listaModificacion) {
            CargueArchivoLogItem entity = new CargueArchivoLogItem();
            entity.setIdArchivoLog(idArchivoLog);
            entity.setInfo(DataOriginal(item));
            entity.setInfoMod(DataModificada(item).replace("null", ""));
            entity.setEstadoProceso(item.isProcesado() == true ? "PROCESADO" : "NO_PROCESADO");
            entity.setDetalle(item.getDetalle().replace("null", ""));
            Date fecha = new Date();
            entity.setFechaProcesamiento(fecha);
            entity.setFechaRegistro(fecha);
            entity.setIdComplemento(BigDecimal.ZERO);
            entity.setEncabezadoCampos(cabeceraModificadaCsv());
            entity.setLlegoRollback("N");
            entity.setModRoolback("N");
            entity.setNuevosValores(item.getCodMarcaNuevo());
            calim.create(entity);
        }
    }

    /**
     * Diana -> Actualizar cantidades del Archivo Log en BD
     */
    protected HhppCargueArchivoLog actualizarArchivoLogBD(HhppCargueArchivoLog cargueArchivoLog) throws ApplicationException {
        try {
            HhppCargueArchivoLog hhppCargueArchivoLog = new HhppCargueArchivoLog();
            hhppCargueArchivoLog = getCargueArchivoManager().update(cargueArchivoLog);
            return hhppCargueArchivoLog;

        } catch (ApplicationException e) {
            String msn = "Se actualizó las cantidades";
            LOGGER.error(msn + ": " + e.getMessage());
            throw new ApplicationException(msn + ": " + e.getMessage());
        }
    }

    /**
     * Diana -> Crear instancia HhppCargueArchivoLogManager
     */
    public HhppCargueArchivoLogManager getCargueArchivoManager() {
        if (cargueArchivoManager == null) {
            cargueArchivoManager = new HhppCargueArchivoLogManager();
        }
        return cargueArchivoManager;
    }

    /**
     * Diana -> método para generar la cabecera de archivo csv Original
     */
    private String cabeceraCsv() {
        StringBuilder registro = new StringBuilder();
        registro.append(LINEA).append(Constant.SEPARADOR).append(ID_DIRECCION_DETALLADA).append(Constant.SEPARADOR).append(DEPARTAMENTO).append(Constant.SEPARADOR).append(MUNICIPIO)
                .append(Constant.SEPARADOR).append(CENTRO_POBLADO).append(Constant.SEPARADOR).append(NODO).append(Constant.SEPARADOR).append(DIRECCION).append(Constant.SEPARADOR)
                .append(COD_MARCAS_FRAUDE);

        return registro.toString();
    }

    /**
     * Diana -> método para generar la cabecera de archivo csv Modificada
     */
    private String cabeceraModificadaCsv() {
        StringBuilder registro = new StringBuilder();
        registro.append(LINEA).append(Constant.SEPARADOR).append(ID_DIRECCION_DETALLADA).append(Constant.SEPARADOR).append(DEPARTAMENTO).append(Constant.SEPARADOR).append(MUNICIPIO)
                .append(Constant.SEPARADOR).append(CENTRO_POBLADO).append(Constant.SEPARADOR).append(NODO).append(Constant.SEPARADOR).append(DIRECCION).append(Constant.SEPARADOR)
                .append(COD_MARCAS_FRAUDE).append(Constant.SEPARADOR).append(DETALLE);

        return registro.toString();
    }

    /**
     * Diana -> método para generar el detalle del archivo csv Original
     *
     * @param listaModificacion
     */
    public String DataOriginal(FraudesHHPPMasivoDto listaModificacion) {
        String idDireccionDetallada = listaModificacion.getIdDireccionDetallada() != null ? listaModificacion.getIdDireccionDetallada().toString() : "";

        StringBuilder registro = new StringBuilder();
        registro.append(idDireccionDetallada).append(Constant.SEPARADOR).append(listaModificacion.getDepartamento())
                .append(Constant.SEPARADOR).append(listaModificacion.getMunicipio()).append(Constant.SEPARADOR).append(listaModificacion.getCentroPoblado())
                .append(Constant.SEPARADOR).append(listaModificacion.getNodo()).append(Constant.SEPARADOR).append(listaModificacion.getDireccion())
                .append(Constant.SEPARADOR).append(listaModificacion.getCodMarcaNuevo());
        return registro.toString();
    }

    /**
     * Diana -> método para generar la cabecera de archivo csv Modificada
     *
     * @param listaModificacion
     */
    public String DataModificada(FraudesHHPPMasivoDto listaModificacion) {
        String idDireccionDetallada = listaModificacion.getIdDireccionDetallada() != null ? listaModificacion.getIdDireccionDetallada().toString() : "";

        StringBuilder registro = new StringBuilder();
        registro.append(listaModificacion.getLinea()).append(Constant.SEPARADOR).append(idDireccionDetallada).append(Constant.SEPARADOR).append(listaModificacion.getDepartamento())
                .append(Constant.SEPARADOR).append(listaModificacion.getMunicipio()).append(Constant.SEPARADOR).append(listaModificacion.getCentroPoblado())
                .append(Constant.SEPARADOR).append(listaModificacion.getNodo()).append(Constant.SEPARADOR).append(listaModificacion.getDireccion())
                .append(Constant.SEPARADOR).append(listaModificacion.getCodMarcaNuevo())
                .append(Constant.SEPARADOR).append(listaModificacion.getDetalle());
        return registro.toString();
    }
}
