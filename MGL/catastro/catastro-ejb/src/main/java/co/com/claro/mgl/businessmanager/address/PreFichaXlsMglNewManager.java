/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.dao.impl.PreFichaXlsMglNewDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroPrefichasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMglNew;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import co.com.telmex.catastro.services.comun.Constantes;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manager para operaciones en TEC_PREFICHA_XLS_NEW
 *
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */
public class PreFichaXlsMglNewManager {

    private static final Logger LOGGER = LogManager.getLogger(PreFichaXlsMglNewManager.class);

    public List<PreFichaXlsMglNew> savePreFichaXlsNewList(PreFichaMglNew preFichaMgl, List<PreFichaXlsMglNew> prefichaXlsList) throws ApplicationException {

        for (PreFichaXlsMglNew pfx : prefichaXlsList) {
            pfx.setPrfId(preFichaMgl.getPrfId());
            pfx.setIdPfx(BigDecimal.ZERO);
            pfx.setFechaCreacion(preFichaMgl.getFechaCreacion());
            pfx.setFechaEdicion(preFichaMgl.getFechaEdicion());
            pfx.setUsuarioCreacion(preFichaMgl.getUsuarioCreacion());
            pfx.setUsuarioEdicion(preFichaMgl.getUsuarioEdicion());
            pfx.setEstadoRegistro(1);

        }
        PreFichaXlsMglNewDaoImpl daoImpl = new PreFichaXlsMglNewDaoImpl();
        return daoImpl.create(prefichaXlsList);

    }

    /**
     * Metodo para realizar el cambio de Apto
     *
     * @author Miguel Barrios
     * @param p Objeto prefichaXlsNew
     * @param hhpp HomePass Mgl
     * @param usuarioVt Usuario que realiza la operacion
     * @param perfilVt Perfil del usuario que realiza la operacion
     * @return Indica si la operacion fue exitosa
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean cambioAptoDireccionFichas(PreFichaXlsMglNew p, HhppMgl hhpp, String usuarioVt, int perfilVt) throws ApplicationException {
        try {
            DetalleDireccionEntity detalleDireccionEntity = p.getDrDireccionList().get(0).convertToDetalleDireccionEntity();
            DireccionRRManager direccionRRMana = new DireccionRRManager(detalleDireccionEntity);
            DireccionRREntity direccionRREntity = direccionRRMana.getDireccion();

            HhppMglManager hhppMglManager = new HhppMglManager();
            NodoMglManager nodoMglManager = new NodoMglManager();

            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
            String direccionFormatoRR = "[" + direccionRREntity.getCalle() + "]" + "[" + direccionRREntity.getNumeroUnidad() + "]" + "[" + direccionRREntity.getNumeroApartamento() + "]";
            String unitType = direccionRRManager.obtenerTipoEdificio(direccionFormatoRR, "VERIFICACION_CASAS", "FICHAS");

            //Para realizar cambio de direccion la solicitud debe traer asociado el Hhpp que se le desea cambiar la dir
            if (!p.getHhppAsociados().isEmpty()) {

                HhppMgl hhppAntiguo = hhppMglManager.findById(hhpp.getHhpId());

                if (p.getNuevoApto() != null && !p.getNuevoApto().isEmpty()) {
                    hhppAntiguo.setThhId(unitType);
                }

                boolean habilitarRR = false;
                ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
                ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                    habilitarRR = true;
                }

                //CAMBIO DE APTO EN RR
                //Cambio a subDirecciones del Hhpp y RR debe estar encendido 
                if (habilitarRR && hhppAntiguo != null && hhppAntiguo.getSubDireccionObj() != null
                        && hhppAntiguo.getSubDireccionObj().getSdiId() != null) {

                    //Se realiza busqueda de todas las subdireccion del Hhpp para hacer actualización de datos del hhpp en todas sus tecnologias
                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                            .findHhppSubDireccion(hhppAntiguo.getSubDireccionObj().getSdiId());
                    actualizarHHPP(hhhpSubDirList, direccionRRManager, direccionRREntity, hhppAntiguo);
                } else {
                    //Obtenemos los Hhpp de la Direccion principal y RR debe estar encendido   
                    if (hhppAntiguo != null && hhppAntiguo.getDireccionObj() != null
                            && hhppAntiguo.getDireccionObj().getDirId() != null && habilitarRR) {

                        //Obtenemos los Hhpp de la Direccion principal  
                        List<HhppMgl> hhhpDirList = hhppMglManager.findHhppDireccion(hhppAntiguo.getDireccionObj().getDirId());

                        actualizarHHPP(hhhpDirList, direccionRRManager, direccionRREntity, hhppAntiguo);
                    }
                }
                NodoMgl nodo = nodoMglManager.findByCodigo(p.getNodo());

                cambioDireccionMgl(detalleDireccionEntity,
                        hhppAntiguo,
                        nodo.getGpoId(),
                        usuarioVt, perfilVt);
                return true;
            } else {
                throw new ApplicationException("No fue posible realizar el cambio de apto debido a que "
                        + "no se encuentra el idHhpp asociado");
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en Actualiza HHPP casa. " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Metodo para realizar el cambio de Apto en MGL
     *
     * @author Miguel Barrios          
     * @param direccionRREntity Entidad con el formato de la direccion en RR
     * @param hhppAntiguo HomePass que se quiere actualizar
     * @param gpoId geografico politico id centro problado
     * @param usuarioVt Usuario de la transaccion
     * @param perfilVt Perfil de la transaccion    
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    private void cambioDireccionMgl(DetalleDireccionEntity detalleDireccionEntity, HhppMgl hhppAntiguo, BigDecimal gpoId, String usuarioVt, int perfilVt) throws ApplicationException {
        // CAMBIO DE APTO EN MGL
        if (hhppAntiguo != null) {
            CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
            //Cargue de los niveles del nuevo apto                    
            UnidadStructPcml unidadModificadaNuevoApto = new UnidadStructPcml();
            unidadModificadaNuevoApto.setTipoNivel5(detalleDireccionEntity.getCptiponivel5() != null
                    ? detalleDireccionEntity.getCptiponivel5() : null);
            unidadModificadaNuevoApto.setTipoNivel6(detalleDireccionEntity.getCptiponivel6() != null
                    ? detalleDireccionEntity.getCptiponivel6() : null);
            unidadModificadaNuevoApto.setValorNivel5(detalleDireccionEntity.getCpvalornivel5() != null
                    ? detalleDireccionEntity.getCpvalornivel5() : null);
            unidadModificadaNuevoApto.setValorNivel6(detalleDireccionEntity.getCpvalornivel6() != null
                    ? detalleDireccionEntity.getCpvalornivel6() : null);

            cmtDireccionDetalleMglManager.cambiarAptoDireccionDetalladaHhpp(hhppAntiguo, unidadModificadaNuevoApto,
                    gpoId, usuarioVt, perfilVt);

        } else {
            throw new ApplicationException("No fue posible realizar el cambio de dirección en MGL");
        }
    }

    /**
     * Metodo para realizar el cambio de Apto en RR y Mer
     *
     * @author Miguel Barrios
     * @param hhppList Listado de HomePass para actualizar
     * @param direccionRRManager Entidad para realizar operaciones con RR
     * @param direccionRREntity Entidad con el formato de la direccion en RR
     * @param hhppAntiguo HomePass que se quiere actualizar
     * @return Indica si la operacion fue exitosa
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    private void actualizarHHPP(List<HhppMgl> hhppList, DireccionRRManager direccionRRManager, DireccionRREntity direccionRREntity, HhppMgl hhppAntiguo) throws ApplicationException {
        if (hhppList != null && !hhppList.isEmpty()) {
            for (HhppMgl hhppMglSubDireccion : hhppList) {
                //Si la subDireccion tiene id RR se procede a hacer el cambio de Dir
                if (hhppMglSubDireccion.getHhpIdrR() != null
                        && !hhppMglSubDireccion.getHhpIdrR().isEmpty()) {

                    //Consume servicio que busca hhpp por Id de RR
                    HhppResponseRR hhppResponseRR = direccionRRManager.getHhppByIdRR(hhppMglSubDireccion.getHhpIdrR());
                    // si tiene id RR el hhpp y cuenta con la informacion poblada en comunidad rr para el nodo
                    validarDireccionHhppRR(hhppResponseRR, hhppMglSubDireccion);

                    procesarTecnologiasHhppRR(hhppResponseRR, hhppMglSubDireccion, direccionRREntity, hhppAntiguo);
                } else {
                    LOGGER.error("La dirección o subdirección no cuenta con idRR para realizar operaciones en RR");
                    throw new ApplicationException("La dirección o subdirección no cuenta con idRR para realizar operaciones en RR");
                }
            }
        } else {
            throw new ApplicationException("Ocurrio un error en la consulta de direciones/subdirecciones del hhpp, "
                    + "no es posible realizar el cambio de dirección en RR");
        }
    }
    
    /**
     * Metodo validar comunidad y division del HHPP RR
     *
     * @author Miguel Barrios
     * @param hhppResponseRR HHPP RR
     * @param hhppMglSubDireccion HomePass Mgl Encontrado por direccion o subdireccion         
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    private void validarDireccionHhppRR(HhppResponseRR hhppResponseRR, HhppMgl hhppMglSubDireccion) throws ApplicationException {

        Predicate<String> noNuloNiVacio = str -> str != null && !str.trim().isEmpty();

        if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {
            // Validación para todas las propiedades
            boolean esValido = noNuloNiVacio.test(hhppResponseRR.getComunidad())
                    && noNuloNiVacio.test(hhppResponseRR.getDivision());

            if (!esValido) {
                throw new ApplicationException("RR se encuentra encendido y la dirección/subdirección tiene IdRR pero el nodo de la dirección/subdirección"
                        + " no cuenta con la comunidad y regional RR asociada en la base de datos para la tecnología "
                        + hhppMglSubDireccion.getNodId().getNodTipo().getNombreBasica());
            }
        } else {
            if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje() != null && !hhppResponseRR.getTipoMensaje().trim().isEmpty()) {
                throw new ApplicationException(hhppResponseRR.getMensaje());
            } else {
                throw new ApplicationException("Ocurrio un error intentando "
                        + "consumir el servicio de consulta de hhpp en RR ");
            }
        }
    }
    
    /**
     * Metodo para realizar el cambio de Apto
     *
     * @author Miguel Barrios
     * @param hhppResponseRR HHPP RR
     * @param hhpp HomePass Mgl Encontrado por direccion o subdireccion
     * @param direccionRREntity Entidad direccion RR
     * @param hhppAntiguo Hhpp a cambiar          
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    private void procesarTecnologiasHhppRR(HhppResponseRR hhppResponseRR, HhppMgl hhppMglSubDireccion, DireccionRREntity direccionRREntity, HhppMgl hhppAntiguo) throws ApplicationException {
        DireccionRRManager direccionRRManager = new DireccionRRManager(true);
        //Validación para saber si sincroniza con RR la tecnologia del Hhpp
        if (hhppMglSubDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl() != null
                && !hhppMglSubDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

            for (CmtBasicaExtMgl cmtBasicaExtMgl
                    : hhppMglSubDireccion.getNodId().getNodTipo().getListCmtBasicaExtMgl()) {
                if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                        equalsIgnoreCase(Constant.SINCRONIZA_RR)
                        && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {

                    //cardenaslb
                    HhppMgl hhppCambioTipoVivienda = new HhppMgl();
                    hhppCambioTipoVivienda.setHhpCalle(hhppResponseRR.getStreet());
                    hhppCambioTipoVivienda.setHhpApart(hhppResponseRR.getApartamento());
                    hhppCambioTipoVivienda.setHhpPlaca(hhppResponseRR.getHouse());
                    hhppCambioTipoVivienda.setHhpDivision(hhppResponseRR.getDivision());
                    hhppCambioTipoVivienda.setHhpComunidad(hhppResponseRR.getComunidad());
                    hhppCambioTipoVivienda.setThhId(hhppAntiguo.getThhId());

                    direccionRRManager.cambioTipoUnidadViviendaHhppRR(hhppCambioTipoVivienda);

                    //mover de apartamento
                    direccionRRManager.cambiarEdificioHhppRrInspira(
                            hhppResponseRR.getComunidad().trim(),
                            hhppResponseRR.getDivision().trim(),
                            hhppResponseRR.getStreet(),
                            hhppResponseRR.getHouse(),
                            hhppResponseRR.getApartamento(), //apartamento antiguo
                            direccionRREntity.getNumeroApartamento(), //apartamento nuevo
                            hhppMglSubDireccion.getNodId().getComId());
                }
            }
        }
    }

    /**
     * Metodo para propagar transaccion creacion HHPP
     *
     * @author Miguel Barrios
     * @param direccionDetallada Direccion creada en Mgl
     * @param drDireccionFor Instancia de Drdireccion
     * @param usuario Usuario que ejecuta la transaccion
     * @param carpeta Carpeta de la solicitud
     * @param nvlSocioEconomico Estrato de la direccion
     * @param nodoMglHhpp Nodo de transaccion
     * @param amp Amp de la ficha
     * @return Direcion creada en MER-RR
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DireccionRREntity crearHHPPFichas(CmtDireccionDetalladaMgl direccionDetallada, DrDireccion drDireccionFor, String usuario, String carpeta, String nvlSocioEconomico, NodoMgl nodoMglHhpp, String amp) throws ApplicationException {
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        DetalleDireccionEntity detalleDireccionEntity;
        detalleDireccionEntity = drDireccionFor.convertToDetalleDireccionEntity();
        //obtenemos el centro poblado de la solicitud para conocer si la ciudad es multiorigen
        GeograficoPoliticoMgl centroPobladoSolicitud = geograficoPoliticoManager.findById(nodoMglHhpp.getGpoId());

        //Conocer si es multi-origen
        detalleDireccionEntity.setMultiOrigen(centroPobladoSolicitud.getGpoMultiorigen());
        //obtener la direccion en formato RR
        DireccionRRManager direccionRRManager = new DireccionRRManager(detalleDireccionEntity);

        if (drDireccionFor.getEstrato() != null && !drDireccionFor.getEstrato().isEmpty() && direccionDetallada != null) {
            actualizarEstratoDireccion(direccionDetallada, drDireccionFor, carpeta, direccionRRManager.getDireccion());
            LOGGER.info("Cambio de estrato realizado");
        }

        return direccionRRManager.registrarHHPP_Inspira_Independiente_New(
                nodoMglHhpp.getNodCodigo(), nodoMglHhpp.getNodCodigo(),
                usuario, carpeta,
                Constant.FUNCIONALIDAD_DIRECCIONES,
                nvlSocioEconomico, false,
                null,
                "HHPP CREADO",
                usuario,
                nodoMglHhpp.getGpoId(),
                true,
                drDireccionFor.getTipoViviendaHHPP(),
                nodoMglHhpp, amp);
    }

    /**
     * Metodo para normalizar el estrato resultante
     *
     * @author Miguel Barrios
     * @param nvlSocioEconomico Estrato de la direccion
     * @param tipoSol Tipo de la solicitud
     * @param tipoEdificio Tipo del edificio
     * @return Direcion creada en MER-RR
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    private String obtenerEstrato(String nvlSocioEconomico, String tipoSol, String tipoEdificio) {
        String estrato = nvlSocioEconomico;

        if (tipoSol.equalsIgnoreCase(Constantes.TIPOSOL_PYMES)
                || tipoEdificio.equalsIgnoreCase("K")
                || tipoEdificio.equalsIgnoreCase("O")
                || tipoEdificio.equalsIgnoreCase("L")) {
            estrato = "0";//NR
        }
        return estrato;
    }

    /**
     * Metodo para validar estrato resultante
     *
     * @author Miguel Barrios
     * @param estrato Estrato a validar
     * @return Estrato valido     
     */
    private String validarEstrato(String estrato) {
        String estratoGenerado = "0";
        
        if (estrato != null && !estrato.equals("") && (estrato.equals("NG") || estrato.equals("NR"))) {
            estratoGenerado = "-1";
        }
        
        return estratoGenerado;
    }

    /**
     * Metodo para actualizar estrato dentro de la transaccion
     *
     * @author Miguel Barrios
     * @param direccionDetallada Direccion creada en Mgl
     * @param estratoSeleccionado Estrato a actualizar
     * @param tipoSol Carpeta de la solicitud
     * @param direccionRREntity instancia entidad RR
     * @return Direcion creada en MER-RR
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    private void actualizarEstratoDireccion(CmtDireccionDetalladaMgl direccionDetallada, DrDireccion estratoSeleccionado, String tipoSol, DireccionRREntity direccionRREntity) throws ApplicationException {
        SubDireccionMglManager sDirManager = new SubDireccionMglManager();
        DireccionMglManager dirManager = new DireccionMglManager();
        DireccionRRManager rrManager = new DireccionRRManager(true);

        String tipoEdificio = rrManager.obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(),
                Constant.FUNCIONALIDAD_DIRECCIONES,
                tipoSol);

        //se obtiene los registros de direccion y sub direccion para actualizar el estrato de cada uno si es una subdireccion
        if (direccionDetallada != null && direccionDetallada.getDireccion() != null && direccionDetallada.getSubDireccion() != null) {
            SubDireccionMgl subDireccion = sDirManager.findById(direccionDetallada.getSubDireccion().getDirId());
            if (subDireccion != null) {
                String estrato;
                estrato = obtenerEstrato(estratoSeleccionado.getEstrato(),
                        tipoSol, tipoEdificio);

                subDireccion.setSdiEstrato(new BigDecimal(validarEstrato(estrato)));
                sDirManager.update(subDireccion);
            }
        } else {
            if (direccionDetallada != null && direccionDetallada.getDireccion() != null) {
                DireccionMgl direccion1 = dirManager.findById(direccionDetallada.getDireccion());
                if (direccion1 != null) {
                    String estrato;
                    estrato = obtenerEstrato(estratoSeleccionado.getEstrato(),
                            tipoSol, tipoEdificio);

                    direccion1.setDirEstrato(new BigDecimal(validarEstrato(estrato)));
                    dirManager.update(direccion1);
                }
            }
        }
    }

    public List<PreFichaXlsMglNew> getListXLSNewByPrefichaFase(BigDecimal idPreficha, String fase) {
        PreFichaXlsMglNewDaoImpl daoImpl = new PreFichaXlsMglNewDaoImpl();
        return daoImpl.getListXLSByPrefichaFase(idPreficha, fase);
    }

    public List<PreFichaXlsMglNew> getListadoPrefichasNewPorTab(BigDecimal idPreficha, String fase, String tab, CmtFiltroPrefichasDto filtros) {
        PreFichaXlsMglNewDaoImpl daoImpl = new PreFichaXlsMglNewDaoImpl();
        return daoImpl.getListadoPrefichasPorTab(idPreficha, fase, tab, filtros);
    }

    public void acualizarPrefichaXlsNew(PreFichaGeorreferenciaMglNew preFichaGeoActual, PreFichaGeorreferenciaMglNew preFichaGeorreferenciaMglNueva, PreFichaXlsMglNew xls, boolean isNoProcesados) {
        PreFichaXlsMglNewDaoImpl daoImpl = new PreFichaXlsMglNewDaoImpl();
        daoImpl.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, xls, isNoProcesados);
        //Actualizar XLS
        //Actualizar Georeferenciacion

    }

    public List<PreFichaTxtMglNew> getListadoPrefichasTxtNewPorTab(BigDecimal idPreficha) {
        PreFichaXlsMglNewDaoImpl daoImpl = new PreFichaXlsMglNewDaoImpl();
        return daoImpl.getListadoPrefichasTxtPorTab(idPreficha);
    }

    public List<PreFichaXlsMglNew> getListadoXlsNewByFaseAndIdPfAndPestana(BigDecimal idPreficha, String fase, String tab) {
        PreFichaXlsMglNewDaoImpl daoImpl = new PreFichaXlsMglNewDaoImpl();
        return daoImpl.getListadoByFaseAndIdPfAndPestana(idPreficha, fase, tab);
    }
}
