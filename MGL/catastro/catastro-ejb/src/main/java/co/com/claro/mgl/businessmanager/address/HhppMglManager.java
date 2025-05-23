/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.businessmanager.cm.*;
import co.com.claro.mgl.businessmanager.ptlus.UsuariosServicesManager;
import co.com.claro.mgl.dao.impl.DrDireccionDaoImpl;
import co.com.claro.mgl.dao.impl.HhppMglDaoImpl;
import co.com.claro.mgl.dao.impl.TecEstadoFlujosTecDaoImpl;
import co.com.claro.mgl.dtos.*;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.rest.dtos.CmtAddressGeneralResponseDto;
import co.com.claro.mgl.rest.dtos.CmtRequestHhppByCoordinatesDto;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import co.com.claro.visitasTecnicas.business.DetalleDireccionManager;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import co.com.claro.visitasTecnicas.facade.ParametrosMultivalorEJB;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.UnidadByInfo;
import co.com.telmex.catastro.services.comun.Constantes;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.services.georeferencia.AddressEJB;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.exept.ExceptionDB;
import net.telmex.pcml.service.PcmlLocator;
import net.telmex.pcml.service.PcmlService;
import net.telmex.pcml.service.SuscriberVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Admin
 */
public class HhppMglManager {

    private HhppMglDaoImpl hMhppglDaoImpl = new HhppMglDaoImpl();
    private final TecEstadoFlujosTecDaoImpl tecEstadoFlujosTecDaoImpl = new TecEstadoFlujosTecDaoImpl();
    private final EstadoHhppMglManager estadoHhppMglManager = new EstadoHhppMglManager();
    private final MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
    private final MarcasMglManager marcasMglManager = new MarcasMglManager();
    private final NotasAdicionalesMglManager notasAdicionalesMglManager = new NotasAdicionalesMglManager();
    private final ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
    private final CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
    private List<CmtPestaniaHhppDetalleDto> listaDetalladadaPaginada;

    private static final Logger LOGGER = LogManager.getLogger(HhppMglManager.class);

    public List<HhppMgl> findAll() throws ApplicationException {
        List<HhppMgl> result;
        HhppMglDaoImpl hMhppglDaoImpl1 = new HhppMglDaoImpl();
        result = hMhppglDaoImpl1.findAll();
        return result;
    }

    public HhppMgl update(HhppMgl hhppMgl) throws ApplicationException {

        return hMhppglDaoImpl.update(hhppMgl);
    }

    public HhppMgl actualizarNodoHhpp(HhppMgl hhppMgl, String solicitud, String tipoSolicitud, String usuario) throws ApplicationException {
        try {
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            HhppMgl hhpp = null;
            String tipoSolNE = "4";
            String estadoNew = "";
            if (hhppMgl.getNodId() != null
                    && hhppMgl.getNodId().getNodCodigo() != null
                    && !hhppMgl.getNodId().getNodCodigo().toUpperCase().contains("NFI")) {
                tipoSolNE = tipoSolNE.concat("3");
                estadoNew = "E";
            }

            String resultado = null;
            if (hhppMgl.getHhpIdrR() != null && habilitarRR) {
                HhppResponseRR responseHhppRR = direccionRRManager.getHhppByIdRR(hhppMgl.getHhpIdrR());
                if (responseHhppRR.getTipoMensaje() != null
                        && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {
                    String comunidad = responseHhppRR.getComunidad();
                    String division = responseHhppRR.getDivision();
                    String calle = responseHhppRR.getStreet();
                    String placa = responseHhppRR.getHouse();
                    String apartamento = responseHhppRR.getApartamento();

                    resultado = direccionRRManager.cambioEstadoNodoHHPP(placa, calle, apartamento,
                            comunidad, division, hhppMgl.getNodId().getNodCodigo(), estadoNew, tipoSolNE,
                            solicitud, usuario, tipoSolicitud, hhppMgl);
                } else {
                    LOGGER.error("Ocurrio un error consultando la data del hhpp en RR");
                }
            }

            if (resultado == null) {
                hhpp = hMhppglDaoImpl.update(hhppMgl);
            } else {
                throw new ApplicationException(resultado + " (" + hhppMgl.getNodId().getNodCodigo() + ")");
            }

            return hhpp;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public HhppMgl create(HhppMgl hhppMgl) throws ApplicationException {

        return hMhppglDaoImpl.create(hhppMgl);

    }

    public boolean delete(HhppMgl hhppMgl) throws ApplicationException {

        return hMhppglDaoImpl.delete(hhppMgl);

    }

    public HhppMgl findById(BigDecimal idHhpp) throws ApplicationException {

        return hMhppglDaoImpl.find(idHhpp);

    }

    public List<HhppMgl> findHhppByCodNodo(String nodoHhpp) throws ApplicationException {
        NodoMglManager nodoMglManager = new NodoMglManager();

        List<NodoMgl> nodoMglList = nodoMglManager.findByLikeCodigo(nodoHhpp);

        return hMhppglDaoImpl.findHhppByInNodo(nodoMglList);
    }

    public HhppMgl findHhppByRRFields(Hhpp hhpp) {

        return hMhppglDaoImpl.findHhppByRRFieldsInRepository(hhpp);
    }

    public List<HhppMgl> findHhppBySelect(BigDecimal geo, BigDecimal blackLis, String tipoUnidad, String estadoUnidad, String calle, String placa, String apartamento, BigDecimal hhppId, NodoMgl nodo, Date fechaCreacion, String comunidad, String division) throws ApplicationException {

        List<HhppMgl> HhppMglListGeo = null;
        if (geo != null) {
            HhppMglListGeo = hMhppglDaoImpl.findHhppByGeoId(geo);
        }

        List<HhppMgl> HhppMglList = hMhppglDaoImpl.findHhppBySelect(geo, blackLis, tipoUnidad, estadoUnidad, calle, placa, apartamento, hhppId, nodo, fechaCreacion, comunidad, division);

        List<HhppMgl> HhppList = new ArrayList<>();
        if (geo != null && HhppMglList != null) {
            for (HhppMgl hp : HhppMglListGeo) {
                for (HhppMgl hhpp : HhppMglList) {
                    if (hp.getHhpId() == hhpp.getHhpId()) {
                        HhppList.add(hp);
                        break;
                    }
                }
            }
            HhppMglList = (!HhppList.isEmpty()) ? HhppList : new ArrayList<>();
        }

        if (geo != null && tipoUnidad == null && estadoUnidad == null && calle == null && placa == null && apartamento == null && hhppId == null && nodo == null && fechaCreacion == null) {
            HhppMglList = HhppMglListGeo;
        }

        MarcasHhppMglManager nodoMglManager = new MarcasHhppMglManager();

        if (blackLis != null) {
            List<MarcasHhppMgl> marcasHhppMglList = nodoMglManager.findMarcasHhppByHhppMgl(HhppMglList, blackLis);

            List<BigDecimal> hhppList = null;
            if (marcasHhppMglList != null && !marcasHhppMglList.isEmpty()) {
                hhppList = new ArrayList<>();
                for (MarcasHhppMgl h : marcasHhppMglList) {
                    hhppList.add(h.getHhpp().getHhpId());
                }
            }

            return hMhppglDaoImpl.findHhppByInHhppListId(hhppList);
        }
        return HhppMglList;
    }

    public List<HhppMgl> findHhppByIdRR(String hhpIdrR) {
        return hMhppglDaoImpl.findHhppByIdRR(hhpIdrR);
    }

    /**
     * Permite busqueda en reposotorio por id RR
     *
     * @param HhpIdrR
     *
     * @return HhppMgl
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     * @throws Exception
     */
    public HhppMgl findHhppByIdRROne(String hhpIdrR) {
        return hMhppglDaoImpl.findHhppByIdRROne(hhpIdrR);
    }

    public List<HhppMgl> findHhppBySubEdificioId(BigDecimal hhppSubEdificioId) {
        return hMhppglDaoImpl.findHhppBySubEdificioId(hhppSubEdificioId);
    }

    public PaginacionDto<HhppMgl> findBySubOrCM(int paginaSelected,
            int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            FiltroConsultaHhppDto filtroConsultaHhppDto,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        PcmlManager pcmlManager = new PcmlManager();
        PaginacionDto<HhppMgl> resultado = new PaginacionDto<>();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        switch (findBy) {
            case CUENTA_MATRIZ:
                resultado.setNumPaginas(hMhppglDaoImpl.countByCm(
                        cmtSubEdificioMgl.getCmtCuentaMatrizMglObj(),
                        filtroConsultaHhppDto));
                resultado.setListResultado(hMhppglDaoImpl.findPaginacion(
                        firstResult, maxResults,
                        cmtSubEdificioMgl.getCmtCuentaMatrizMglObj(),
                        filtroConsultaHhppDto));
                break;
            case SUB_EDIFICIO:
                resultado.setNumPaginas(hMhppglDaoImpl.countBySubCm(
                        cmtSubEdificioMgl, filtroConsultaHhppDto));
                resultado.setListResultado(hMhppglDaoImpl.findPaginacionSub(
                        firstResult, maxResults,
                        cmtSubEdificioMgl, filtroConsultaHhppDto));
                break;
            case CUENTA_MATRIZ_SOLO_CONTAR:
                resultado.setNumPaginas(hMhppglDaoImpl.countByCm(
                        cmtSubEdificioMgl.getCmtCuentaMatrizMglObj(),
                        filtroConsultaHhppDto));
                break;
            case SUB_EDIFICIO_SOLO_CONTAR:
                resultado.setNumPaginas(hMhppglDaoImpl.countBySubCm(
                        cmtSubEdificioMgl, filtroConsultaHhppDto));
                break;
        }
        if (resultado.getListResultado() != null
                && !resultado.getListResultado().isEmpty()) {
            for (HhppMgl hhppMgl : resultado.getListResultado()) {
                List<UnidadStructPcml> unidadStructPcmlsList
                        = pcmlManager.getUnidades(
                                hhppMgl.getHhpCalle(),
                                hhppMgl.getHhpPlaca(),
                                hhppMgl.getHhpApart(),
                                hhppMgl.getHhpComunidad());
                if (unidadStructPcmlsList != null && !unidadStructPcmlsList.isEmpty()) {
                    hhppMgl.setCuenta(unidadStructPcmlsList.get(0).getCuentaP());
                    hhppMgl.setOrdenTrabajoVO(pcmlManager.
                            getLastOTByAccount(hhppMgl.getCuenta()));
                    if (hhppMgl.getCuenta().equals("0")) {
                        hhppMgl.setCuenta("");
                    }
                }
            }
        }
        return resultado;
    }

    public CmtPestaniaHhppDto findByHhppSubOrCM(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto,
            int paginaSelected, int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        CmtPestaniaHhppDto cmtPestaniaHhppDto = new CmtPestaniaHhppDto();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        hMhppglDaoImpl = new HhppMglDaoImpl();
        List<HhppMgl> listaTecnologiaHabilitada;

        switch (findBy) {
            case CUENTA_MATRIZ:
                listaTecnologiaHabilitada = hMhppglDaoImpl.findListHhppCM(filtroBusquedaDirecccionDto, firstResult, maxResults, cmtSubEdificioMgl);
                cmtPestaniaHhppDto = getHhpp(listaTecnologiaHabilitada, cmtSubEdificioMgl, findBy, firstResult, maxResults);
                break;
            case SUB_EDIFICIO:
                listaTecnologiaHabilitada = hMhppglDaoImpl.findListHhppSub(filtroBusquedaDirecccionDto, firstResult, maxResults, cmtSubEdificioMgl);
                if (!listaTecnologiaHabilitada.isEmpty()) {
                    cmtPestaniaHhppDto = getHhpp(listaTecnologiaHabilitada, cmtSubEdificioMgl, findBy, firstResult, maxResults);
                }
                break;
            case CUENTA_MATRIZ_SOLO_CONTAR:
                cmtPestaniaHhppDto.setNumPag(hMhppglDaoImpl.countListHhppCM(filtroBusquedaDirecccionDto,
                        cmtSubEdificioMgl));
                break;
            case SUB_EDIFICIO_SOLO_CONTAR:
                cmtPestaniaHhppDto.setNumPag(hMhppglDaoImpl.countListHhppSubEdif(filtroBusquedaDirecccionDto,
                        cmtSubEdificioMgl));
                break;
        }
        return cmtPestaniaHhppDto;
    }

    public int countListHhppSubEdif(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto, CmtSubEdificioMgl cmtSubEdiicioMgl) throws ApplicationException {
        return hMhppglDaoImpl.countListHhppSubEdif(filtroBusquedaDirecccionDto, cmtSubEdiicioMgl);
    }

    public int countListHhppCM(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto, CmtSubEdificioMgl cmtSubEdiicioMgl) throws ApplicationException {
        return hMhppglDaoImpl.countListHhppCM(filtroBusquedaDirecccionDto, cmtSubEdiicioMgl);
    }

    public CmtPestaniaHhppDto getHhppTecnologias(List<BigDecimal> listaCmtDireccionDetalladaMgl, CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY findBy, int firstResult, int maxResults) throws ApplicationException {

        CmtPestaniaHhppDto cmtPestaniaHhppDto;
        List<CmtPestaniaHhppDetalleDto> listPestaniaHhppDetalleDto = new ArrayList<>();
        CmtPestaniaHhppDetalleDto cmtPestaniaHhppDetalleDto;
        CmtTipoBasicaMgl cmtTipoBasicaTec = new CmtTipoBasicaMgl();
        cmtTipoBasicaTec.setTipoBasicaId(cmtTipoBasicaMglManager.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA).getTipoBasicaId());
        CmtDireccionDetalladaMgl dirDeta;
        int resultadoMaximo = 0;

        List<HhppMgl> listaHhppMglSub = null;
        for (BigDecimal cmtDireccionDetalladaMgl : listaCmtDireccionDetalladaMgl) {
            CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
            dirDeta = cmtDireccionDetalleMglManager.buscarDireccionIdDireccion(cmtDireccionDetalladaMgl);

            cmtPestaniaHhppDetalleDto = new CmtPestaniaHhppDetalleDto();
            cmtPestaniaHhppDetalleDto.setCmtDireccionDetalladaMgl(dirDeta);
            String torre;
            String suscriptor;
            String estrato;
            BigDecimal subdireccioId = null;
            if (dirDeta != null && dirDeta.getSubDireccion() != null) {
                if (dirDeta.getSubDireccion().getSdiId() != null) {
                    subdireccioId = dirDeta.getSubDireccion().getSdiId();
                    listaHhppMglSub = hMhppglDaoImpl.findHhppBySubDirId(subdireccioId);
                }
            } else {
                if (dirDeta != null
                        && dirDeta.getDireccion() != null && dirDeta.getDireccion().getDirId() != null) {
                    listaHhppMglSub = hMhppglDaoImpl.findHhppByDirIdSubDirId(dirDeta.getDireccion().getDirId(), subdireccioId);
                }

            }

            // consultar en la tabla hhpp las tecnologias de cada uno de ellos
            if (listaHhppMglSub != null && !listaHhppMglSub.isEmpty()) {
                for (HhppMgl hhppMgl : listaHhppMglSub) {

                    cmtPestaniaHhppDetalleDto.setCmtBasicaMgl(hhppMgl.getNodId().getNodTipo());
                    cmtPestaniaHhppDetalleDto.setEstrato(hhppMgl.getSubDireccionObj() != null ? hhppMgl.getSubDireccionObj().getSdiEstrato().toString() : hhppMgl.getDireccionObj().getDirEstrato().toString());
                    cmtPestaniaHhppDetalleDto.setNodo(hhppMgl.getNodId().getNodCodigo());
                    cmtPestaniaHhppDetalleDto.setCmtBasicaMgl(hhppMgl.getHhppSubEdificioObj() != null ? hhppMgl.getHhppSubEdificioObj().getEstadoSubEdificioObj() : null);
                    switch (hhppMgl.getNodId().getNodTipo().getIdentificadorInternoApp()) {
                        case Constant.DTH:
                            cmtPestaniaHhppDetalleDto.setDth(hhppMgl.getNodId().getNodCodigo());
                            cmtPestaniaHhppDetalleDto.setEstadoDth(hhppMgl.getEhhId() != null ? hhppMgl.getEhhId().getEhhID() : "");
                            break;
                        case Constant.FIBRA_FTTTH:
                            cmtPestaniaHhppDetalleDto.setFtt(hhppMgl.getNodId().getNodCodigo());
                            cmtPestaniaHhppDetalleDto.setEstadoFtt(hhppMgl.getEhhId() != null ? hhppMgl.getEhhId().getEhhID() : "");
                            break;
                        case Constant.FIBRA_OP_GPON:
                            cmtPestaniaHhppDetalleDto.setFog(hhppMgl.getNodId().getNodCodigo());
                            cmtPestaniaHhppDetalleDto.setEstadoFog(hhppMgl.getEhhId() != null ? hhppMgl.getEhhId().getEhhID() : "");
                            break;
                        case Constant.FIBRA_OP_UNI:
                            cmtPestaniaHhppDetalleDto.setFou(hhppMgl.getNodId().getNodCodigo());
                            cmtPestaniaHhppDetalleDto.setEstadoFou(hhppMgl.getEhhId() != null ? hhppMgl.getEhhId().getEhhID() : "");
                            break;
                        case Constant.HFC_BID:
                            cmtPestaniaHhppDetalleDto.setBid(hhppMgl.getNodId().getNodCodigo());
                            cmtPestaniaHhppDetalleDto.setEstadoBid(hhppMgl.getEhhId() != null ? hhppMgl.getEhhId().getEhhID() : "");
                            break;
                        case Constant.HFC_UNI:
                            cmtPestaniaHhppDetalleDto.setUni(hhppMgl.getNodId().getNodCodigo());
                            cmtPestaniaHhppDetalleDto.setEstadoUni(hhppMgl.getEhhId() != null ? hhppMgl.getEhhId().getEhhID() : "");
                            break;
                        case Constant.LTE_INTERNET:
                            cmtPestaniaHhppDetalleDto.setLte(hhppMgl.getNodId().getNodCodigo());
                            cmtPestaniaHhppDetalleDto.setEstadoLte(hhppMgl.getEhhId() != null ? hhppMgl.getEhhId().getEhhID() : "");
                            break;
                        default:
                            // movil
                            cmtPestaniaHhppDetalleDto.setMov(hhppMgl.getNodId().getNodCodigo());
                            cmtPestaniaHhppDetalleDto.setEstadoMov(hhppMgl.getEhhId().getEhhID());
                            break;
                    }
                    if (hhppMgl.getHhppSubEdificioObj() != null) {
                        torre = hhppMgl.getHhppSubEdificioObj().getNombreSubedificio();
                        cmtPestaniaHhppDetalleDto.setTorre(torre);
                        suscriptor = hhppMgl.getSuscriptor();
                        cmtPestaniaHhppDetalleDto.setSuscriptor(suscriptor);
                        if (hhppMgl.getSubDireccionObj() != null) {
                            if ((hhppMgl.getSubDireccionObj().getSdiEstrato() != null) || (hhppMgl.getSubDireccionObj().getSdiNivelSocioecono() != null)) {
                                if (hhppMgl.getSubDireccionObj().getSdiEstrato() != null) {
                                    estrato = hhppMgl.getSubDireccionObj().getSdiEstrato().toString();
                                    cmtPestaniaHhppDetalleDto.setEstrato(estrato);
                                }
                            }
                        } else {
                            if ((hhppMgl.getDireccionObj().getDirEstrato() != null) || (hhppMgl.getDireccionObj().getDirNivelSocioecono() != null)) {
                                if (hhppMgl.getDireccionObj().getDirEstrato() != null) {
                                    estrato = hhppMgl.getDireccionObj().getDirEstrato().toString();
                                    cmtPestaniaHhppDetalleDto.setEstrato(estrato);
                                }
                            }
                        }
                    }

                }
            }

            listPestaniaHhppDetalleDto.add(cmtPestaniaHhppDetalleDto);
        }

        // se clasifican los hhpp salaventas y campamentos y se dejan de primero en la lista ordenada(listaOrdenadaDirDetallada)
        // se extraen los nombres de los subedificios en una lista de string (listaNombreSubEdif)
        List<String> listaNombreSubEdif = new ArrayList<>();
        List<CmtPestaniaHhppDetalleDto> listaOrdenadaDirDetallada = new ArrayList<>();
        for (CmtPestaniaHhppDetalleDto listaDetallada : listPestaniaHhppDetalleDto) {
            listaOrdenadaDirDetallada.add(listaDetallada);
            if (listaDetallada.getCmtBasicaMgl() != null && !listaDetallada.getCmtBasicaMgl().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                listaNombreSubEdif.add(listaDetallada.getTorre());
            } else {
                int i = 0;
                listaNombreSubEdif.add(i, listaDetallada.getTorre());
                i++;
            }
        }

        Collections.sort(listaOrdenadaDirDetallada, (CmtPestaniaHhppDetalleDto a, CmtPestaniaHhppDetalleDto b) -> {
            if (a.getTorre() == null) {
                return (b.getTorre() == null) ? 0 : -1;
            }

            if (b.getTorre() == null) {
                return 0;
            }
            return a.getTorre().compareTo(b.getTorre());
        });
        // se ordenan las subedificios
        Collections.sort(listaNombreSubEdif);

        // se extraen los nombres de las torres sin repetir 
        HashSet<String> listaEdificiosUnicos = new HashSet<>(listaNombreSubEdif);

        // se recorren toda la lista de detallada contra la lista de snombres de subedificios 
        //y se extraen las direcciones de cada torre para luego almacenarla en la lista final(listaFinalDetallada) 
        List<CmtPestaniaHhppDetalleDto> listaParcialDetallada;
        List<CmtPestaniaHhppDetalleDto> listaFinalDetallada = new ArrayList<>();
        for (String subedificio : listaEdificiosUnicos) {
            listaParcialDetallada = new ArrayList<>();
            for (CmtPestaniaHhppDetalleDto detalleFinal : listaOrdenadaDirDetallada) {
                if (subedificio.equals(detalleFinal.getTorre())) {
                    listaParcialDetallada.add(detalleFinal);
                }
            }
            // se ordena el nivel 5 o complemento de una especifica torre
            Collections.sort(listaParcialDetallada, (CmtPestaniaHhppDetalleDto a, CmtPestaniaHhppDetalleDto b) -> {
                String complementoA = a.getCmtDireccionDetalladaMgl().getCpTipoNivel5() + " " + a.getCmtDireccionDetalladaMgl().getCpValorNivel5();
                String complementoB = b.getCmtDireccionDetalladaMgl().getCpTipoNivel5() + " " + b.getCmtDireccionDetalladaMgl().getCpValorNivel5();

                return complementoA.compareTo(complementoB);
            });
            listaFinalDetallada.addAll(listaParcialDetallada);

        }
        Collections.sort(listaFinalDetallada, (CmtPestaniaHhppDetalleDto a, CmtPestaniaHhppDetalleDto b) -> {
            if (a.getTorre() == null) {
                return (b.getTorre() == null) ? 0 : -1;
            }
            if (b.getTorre() == null) {
                return 0;
            }
            return a.getTorre().compareTo(b.getTorre());
        });

        // se posiciona los hhpp salaventas complementos en las primeras posiciones de la lista
        List<CmtPestaniaHhppDetalleDto> listaDetalladaHHPP = new ArrayList<>();
        listaFinalDetallada.forEach((listaOrdenadaCM) -> {
            if (listaOrdenadaCM.getCmtBasicaMgl() != null && !listaOrdenadaCM.getCmtBasicaMgl().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                listaDetalladaHHPP.add(listaOrdenadaCM);
            } else {
                int i = 0;
                listaDetalladaHHPP.add(i, listaOrdenadaCM);
                i++;
            }
        });

        // se maneja la lista ordenada de forma paginada
        if (!listaDetalladaHHPP.isEmpty()) {
            if ((firstResult + Constant.PAGINACION_OCHO_FILAS) > listaDetalladaHHPP.size()) {
                if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId() != null
                        && cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                        && !cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                                equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                    firstResult = 0;
                }
                resultadoMaximo = listaDetalladaHHPP.size();
            } else {
                if (cmtSubEdificioMgl.getCuentaMatrizObj().getListCmtSubEdificioMglActivos().size() > 1 && cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId() != null
                        && cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                        && !cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                                equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                    firstResult = 0;
                    resultadoMaximo = (firstResult + Constant.PAGINACION_OCHO_FILAS);
                } else {

                    resultadoMaximo = listaDetalladaHHPP.size();
                }

            }

            listaDetalladadaPaginada = new ArrayList<>();
            for (int k = firstResult; k < resultadoMaximo; k++) {
                listaDetalladadaPaginada.add(listaDetalladaHHPP.get(k));
            }
        }

        cmtPestaniaHhppDto = new CmtPestaniaHhppDto();
        cmtPestaniaHhppDto.setListaPestaniaHhppDet(listaDetalladadaPaginada);

        return cmtPestaniaHhppDto;
    }

    public String getInstalledServises(String cta, String comunidad, String division) throws ApplicationException {
        PcmlManager pcmlManager = new PcmlManager();
        return pcmlManager.getInstalledServises(cta, comunidad, division);
    }

    public List<AuditoriaDto> construirAuditoria(HhppMgl hhppMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<HhppMgl, HhppAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<>();
        return utilsCMAuditoria.construirAuditoria(hhppMgl);
    }

    /**
     * Agregar marcas a un HHPP existente.Permite agregar uno o varias marcas a
     * un hhpp existente en la plataforma
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl objeto con el id del hhpp
     * @param listaMarcasMgl lista de marcas que se desean agregar al hhpp una
     * lista de tipo marcas hhpps
     * @param usuario
     *
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    public CmtDefaultBasicResponse agregarMarcasHhpp(HhppMgl hhppMgl, List<MarcasMgl> listaMarcasMgl,
            String usuario) {
        CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
        int contador = 0;
        List<MarcasMgl> aux = new ArrayList<>();
        try {

            if (hhppMgl != null && hhppMgl.getHhpId() != BigDecimal.ZERO) {
                //Se crea lista para agregar marca a todas las tecnologias asociadas al HHPP
                List<HhppMgl> listHhppMgl = hMhppglDaoImpl.findByDirAndSubDir(hhppMgl.getDireccionObj(), hhppMgl.getSubDireccionObj());
                if (listHhppMgl != null && !listHhppMgl.isEmpty()) {
                    for (HhppMgl hhppBuscado : listHhppMgl) {
                        //@author JDHT
                        if (hhppBuscado != null) {
                            //validacion de codigo de marca existente
                            if (listaMarcasMgl != null && !listaMarcasMgl.isEmpty()) {
                                for (MarcasMgl marcasMgl : listaMarcasMgl) {
                                    if (marcasMgl.getMarCodigo() != null
                                            && !marcasMgl.getMarCodigo().isEmpty()) {

                                        MarcasMgl marca = marcasMglManager
                                                .findMarcasMglByCodigo(marcasMgl.getMarCodigo().toUpperCase());

                                        if (marca == null) {
                                            response.setMessageType("E");
                                            response.setMessage("La marca de codigo " + marcasMgl.getMarCodigo()
                                                    + " no se encuentra registrada en la base de datos para ser agregada al hhpp. Verifique por favor.");
                                            return response;
                                        }

                                        //validacion de marca agregada anteriormente en listado de marca que contiene el hhpp
                                        if (hhppBuscado.getListMarcasHhpp() != null && !hhppBuscado.getListMarcasHhpp().isEmpty()) {
                                            for (MarcasHhppMgl m : hhppBuscado.getListMarcasHhpp()) {
                                                if (m.getMarId() != null && m.getMarId().getMarCodigo() != null
                                                        && !m.getMarId().getMarCodigo().isEmpty()
                                                        && m.getEstadoRegistro() == 1
                                                        && m.getMarId().getMarCodigo().equalsIgnoreCase(marcasMgl.getMarCodigo())) {
                                                    aux.add(marcasMgl);
                                                    response.setMessageType("E");
                                                    response.setMessage("La marca de codigo " + marcasMgl.getMarCodigo()
                                                            + " ya se encuentra asociada al hhpp anteriormente. (RPTD)");
                                                    return response;
                                                }
                                            }
                                        }
                                    } else {
                                        response.setMessageType("E");
                                        response.setMessage("Ninguna de las marcas que desea asociar el codigo puede ir vacia, verifique por favor.");
                                        return response;
                                    }
                                }
                            } else {
                                response.setMessageType("E");
                                response.setMessage("El listado de marcas que desea asociar al hhpp se encuentra vacio, verifique por favor.");
                                return response;
                            }

                            listaMarcasMgl.removeAll(aux);
                            StringBuilder strIds = new StringBuilder();
                            Iterator<MarcasMgl> itMarcas = listaMarcasMgl.iterator();
                            while (itMarcas.hasNext()) {
                                MarcasMgl marca = itMarcas.next();
                                if (marca != null && marca.getMarCodigo() != null && !marca.getMarCodigo().isEmpty()) {
                                    MarcasMgl marcaMgl = marcasMglManager.findMarcasMglByCodigo(marca.getMarCodigo().toUpperCase());
                                    if (marcaMgl != null && marcaMgl.getMarId() != null) {
                                        MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                                        marcasHhppMgl.setMhhFechaCreacion(new Date());
                                        marcasHhppMgl.setHhpp(hhppBuscado);
                                        marcasHhppMgl.setMarId(marcaMgl);
                                        marcasHhppMgl.setMhhUsuarioCreacion(usuario);
                                        marcasHhppMgl.setEstadoRegistro(1);

                                        marcasHhppMglManager.create(marcasHhppMgl);
                                        contador++;

                                        strIds.append(marcaMgl.getMarCodigo());
                                        if (itMarcas.hasNext()) {
                                            strIds.append(",");
                                        }
                                    }
                                }
                            }

                            String ids = strIds.toString().endsWith(",") ? strIds.toString().substring(0, strIds.length() - 1) : strIds.toString();

                            if (contador > 0) {
                                boolean enviarInformacionRR = false;
                                ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                    enviarInformacionRR = true;
                                }
                                if (enviarInformacionRR
                                        && hhppBuscado.getHhpIdrR() != null
                                        && !hhppBuscado.getHhpIdrR().isEmpty()) {

                                    DireccionRRManager drrm = new DireccionRRManager(true);
                                    //se busca de nuevo el hhpp para tener todas las marcas actualizadas al instante  
                                    List<MarcasMgl> marcasMgls = marcasMglManager.findMarcasMglByHhpp(hhppBuscado);
                                    if (marcasMgls != null && !marcasMgls.isEmpty()) {
                                        drrm.editarMarcasHhppRR(hhppBuscado, marcasMgls);
                                    }
                                }

                                response.setMessageType("I");
                                response.setMessage("Se agregaron correctamente " + contador + " marca(s) al HHPP " + hhppBuscado.getHhpId() + " (" + ids + ").");
                            } else {
                                response.setMessageType("E");
                                response.setMessage("No existen registros de marcas para adicionar asociados al HHPP " + hhppBuscado.getHhpId() + ".");
                            }

                        } else {
                            response.setMessageType("E");
                            response.setMessage("No se puede encontrar el HHPP con el id " + hhppMgl.getHhpId());
                        }
                    }
                } else {
                    response.setMessageType("E");
                    response.setMessage("No se encontro HHPP con la direccionId " + hhppMgl.getDirId());
                }

            } else {
                response.setMessageType("E");
                response.setMessage("Verifique que los datos necesarios esten seteados en el objeto request");
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en agregarMarcasHhpp: " + e.getMessage(), e);
            response.setMessageType("E");
            response.setMessage("Se genero un error al momento de agregar marcas HHPP: " + e.getMessage());
        }
        return response;
    }

    public CmtDefaultBasicResponse agregarMarcasHhppFichasNodos(HhppMgl hhppMgl, List<MarcasMgl> listaMarcasMgl) {
        CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
        int contador = 0;
        String ERROR = "E";
        try {
            if (hhppMgl != null && hhppMgl.getHhpId() != BigDecimal.ZERO) {
                HhppMgl hhppBuscado = hMhppglDaoImpl.findById(hhppMgl.getHhpId());

                if (hhppBuscado != null) {
                    //validacion de codigo de marca existente
                    if (listaMarcasMgl != null && !listaMarcasMgl.isEmpty()) {
                        for (MarcasMgl marcasMgl : listaMarcasMgl) {
                            if (marcasMgl.getMarCodigo() != null
                                    && !marcasMgl.getMarCodigo().isEmpty()) {

                                MarcasMgl marca = marcasMglManager
                                        .findMarcasMglByCodigo(marcasMgl.getMarCodigo().toUpperCase());

                                if (marca == null) {
                                    response.setMessageType(ERROR);
                                    response.setMessage("La marca de codigo " + marcasMgl.getMarCodigo()
                                            + " no se encuentra registrada en la base de datos para ser agregada al hhpp. Verifique por favor");
                                    return response;
                                }

                                //validacion de marca agregada anteriormente en listado de marca que contiene el hhpp
                                if (hhppBuscado.getListMarcasHhpp() != null && !hhppBuscado.getListMarcasHhpp().isEmpty()) {
                                    for (MarcasHhppMgl marcasHhppMgl : hhppBuscado.getListMarcasHhpp()) {
                                        marcasHhppMglManager.delete(marcasHhppMgl);
                                    }
                                }
                            } else {
                                response.setMessageType(ERROR);
                                response.setMessage("Ninguna de las marcas que desea asociar puede ir vacia, verifique por favor.");
                                return response;
                            }
                        }
                    } else {
                        response.setMessageType(ERROR);
                        response.setMessage("El listado de marcas que desea asociar al hhpp se encuentra vacio, verifique por favor.");
                        return response;
                    }

                    StringBuilder strIds = new StringBuilder();
                    Iterator<MarcasMgl> itMarcas = listaMarcasMgl.iterator();
                    while (itMarcas.hasNext()) {
                        MarcasMgl marca = itMarcas.next();
                        if (marca != null && marca.getMarCodigo() != null && !marca.getMarCodigo().isEmpty()) {
                            MarcasMgl marcaMgl = marcasMglManager.findMarcasMglByCodigo(marca.getMarCodigo().toUpperCase());
                            if (marcaMgl != null && marcaMgl.getMarId() != null) {
                                MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                                marcasHhppMgl.setMhhFechaCreacion(new Date());
                                marcasHhppMgl.setHhpp(hhppBuscado);
                                marcasHhppMgl.setMarId(marcaMgl);
                                marcasHhppMgl.setMhhUsuarioCreacion("SERVICE");
                                marcasHhppMgl.setEstadoRegistro(1);
                                marcasHhppMglManager.create(marcasHhppMgl);
                                contador++;

                                strIds.append(marcaMgl.getMarCodigo());
                                if (itMarcas.hasNext()) {
                                    strIds.append(",");
                                }
                            }
                        }
                    }

                    String ids = strIds.toString().endsWith(",") ? strIds.toString().substring(0, strIds.length() - 1) : strIds.toString();

                    if (contador > 0) {
                        boolean enviarInformacionRR = false;
                        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                            enviarInformacionRR = true;
                        }
                        if (enviarInformacionRR
                                && hhppBuscado.getHhpIdrR() != null
                                && !hhppBuscado.getHhpIdrR().isEmpty()) {

                            DireccionRRManager drrm = new DireccionRRManager(true);
                            //se busca de nuevo el hhpp para tener todas las marcas actualizadas al instante  
                            List<MarcasMgl> marcasMgls = marcasMglManager.findMarcasMglByHhpp(hhppBuscado);
                            if (marcasMgls != null && !marcasMgls.isEmpty()) {
                                drrm.editarMarcasHhppRR(hhppBuscado, marcasMgls);
                            }
                        }

                        String EXITOSO = "I";
                        response.setMessageType(EXITOSO);
                        response.setMessage("Se agregaron correctamente " + contador + " marca(s) al HHPP " + hhppBuscado.getHhpId() + " (" + ids + ").");
                    } else {
                        response.setMessageType(ERROR);
                        response.setMessage("No existen registros de marcas para adicionar asociados al HHPP " + hhppBuscado.getHhpId() + ".");
                    }

                } else {
                    response.setMessageType(ERROR);
                    response.setMessage("No se puede encontrar el HHPP con el id " + hhppMgl.getHhpId());
                }

            } else {
                response.setMessageType(ERROR);
                response.setMessage("Verifique que los datos necesarios esten seteados en el objeto request");
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en agregarMarcasHhppFichasNodos. " + e.getMessage(), e);
            response.setMessageType(ERROR);
            response.setMessage("Se generó error al momento de agregar marcas HHPP Fichas Nodo: " + e.getMessage());
        }
        return response;
    }

    /**
     * eliminar marcas a un HHPP existente.Permite eliminar uno o varias marcas
     * a un hhpp existente en la plataforma
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl objeto con el id del hhpp
     * @param listaMarcasMgl
     *
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    public CmtDefaultBasicResponse eliminarMarcasHhpp(HhppMgl hhppMgl, List<MarcasMgl> listaMarcasMgl) {
        CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
        int contador = 0;
        try {
            if (hhppMgl != null && hhppMgl.getHhpId() != BigDecimal.ZERO) {
                //Se crea lista para eliminar marcas de todas las tecnologias asociadas al HHPP
                List<HhppMgl> listHhppMgl = hMhppglDaoImpl.findByDirAndSubDir(hhppMgl.getDireccionObj(), hhppMgl.getSubDireccionObj());
                if (listHhppMgl != null && !listHhppMgl.isEmpty()) {
                    for (HhppMgl hhppBuscado : listHhppMgl) {
                        //@author JDHT
                        if (hhppBuscado != null) {
                            if (listaMarcasMgl != null && !listaMarcasMgl.isEmpty()) {

                                for (MarcasMgl marca : listaMarcasMgl) {
                                    if (marca != null && marca.getMarCodigo() != null && !marca.getMarCodigo().isEmpty()) {
                                        MarcasMgl marcaMgl = marcasMglManager.findMarcasMglByCodigo(marca.getMarCodigo().toUpperCase());
                                        if (marcaMgl == null) {
                                            response.setMessageType("E");
                                            response.setMessage("La marca de codigo " + marca.getMarCodigo()
                                                    + " no se encuentra registrada en la base de datos para ser agregada al hhpp. Verifique por favor");
                                            return response;
                                        }
                                        List<MarcasHhppMgl> marcasHhppMgls = marcasHhppMglManager.findMarcasHhppByHhppAndMarca(hhppBuscado, marcaMgl);

                                        if (marcasHhppMgls != null && !marcasHhppMgls.isEmpty()) {

                                            int contExiste = 0;
                                            for (MarcasHhppMgl marc : marcasHhppMgls) {
                                                if (marcaMgl.getMarCodigo().equals(marc.getMarId().getMarCodigo())) {
                                                    contExiste++;
                                                }
                                            }
                                            if (contExiste == 0) {
                                                response.setMessageType("E");
                                                response.setMessage("La marca de codigo " + marca.getMarCodigo()
                                                        + " que desea eliminar no se encuentra asociada al hhpp. Verifique por favor");
                                                return response;
                                            }

                                            StringBuilder strIds = new StringBuilder();
                                            Iterator<MarcasHhppMgl> itMarcas = marcasHhppMgls.iterator();
                                            while (itMarcas.hasNext()) {
                                                MarcasHhppMgl m = itMarcas.next();
                                                if (m.getEstadoRegistro() == 1) {
                                                    m.setEstadoRegistro(0);
                                                    marcasHhppMglManager.update(m);
                                                    contador++;
                                                    strIds.append(m.getMarId().getMarCodigo());
                                                    if (itMarcas.hasNext()) {
                                                        strIds.append(",");
                                                    }
                                                }
                                            }

                                            String ids = strIds.toString().endsWith(",") ? strIds.toString().substring(0, strIds.length() - 1) : strIds.toString();

                                            if (contador > 0) {
                                                response.setMessageType("I");
                                                response.setMessage("Se eliminaron correctamente " + contador + " marca(s) del HHPP " + hhppBuscado.getHhpId() + " (" + ids + ").");
                                            } else {
                                                response.setMessageType("E");
                                                response.setMessage("No existen registros de marcas para eliminar asociados al HHPP " + hhppBuscado.getHhpId() + ".");
                                            }
                                        } else {
                                            response.setMessageType("E");
                                            response.setMessage("No fueron encontradas marcas asociadas al hhpp para ser eliminadas");
                                        }
                                    } else {
                                        response.setMessageType("E");
                                        response.setMessage("Verifique que los datos necesarios esten seteados en el objeto request");
                                    }

                                }

                                boolean enviarInformacionRR = false;
                                ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                    enviarInformacionRR = true;
                                }
                                if (enviarInformacionRR) {
                                    DireccionRRManager drrm = new DireccionRRManager(true);
                                    List<MarcasMgl> marcasMgls = marcasMglManager.findMarcasMglByHhpp(hhppBuscado);
                                    drrm.editarMarcasHhppRR(hhppBuscado, marcasMgls);
                                }
                            } else {
                                response.setMessageType("E");
                                response.setMessage("El listado de Marca que desea eliminar se encuentra vacio, verifique por favor");
                                return response;
                            }
                        } else {

                            response.setMessageType("E");
                            response.setMessage("El hhpp no fue encontrado con el id " + hhppMgl.getHhpId());
                            return response;
                        }
                    }
                } else {
                    response.setMessageType("E");
                    response.setMessage("No se encontro HHPP con la direccionId " + hhppMgl.getDirId());
                }

            } else {
                response.setMessageType("E");
                response.setMessage("Verifique que los datos necesarios esten seteados en el objeto request");
            }

        } catch (ApplicationException e) {
            response.setMessageType("E");
            response.setMessage("Se genero una exception " + e.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
        }
        return response;
    }

    /**
     * Agregar notas a un HHPP existente. Permite agregar uno o varias notas a
     * un hhpp existente en la plataforma
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl objeto con el id del hhpp
     * @param listaNotasHhppMgls lista de notas que se desean agregar al hhpp
     *
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    public CmtDefaultBasicResponse agregarNotasHhpp(HhppMgl hhppMgl, List<NotasAdicionalesMgl> listaNotasHhppMgls) {
        CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
        int contador = 0;
        try {
            if (hhppMgl != null && hhppMgl.getHhpId() != BigDecimal.ZERO) {
                HhppMgl hhppBuscado = hMhppglDaoImpl.findById(hhppMgl.getHhpId());
                if (hhppBuscado != null) {
                    for (NotasAdicionalesMgl notasAdicionalesMgl : listaNotasHhppMgls) {
                        if (notasAdicionalesMgl != null) {
                            notasAdicionalesMgl.setFechaCreacion(new Date());
                            notasAdicionalesMgl.setHhppId(hhppBuscado.getHhpId().toString());
                            notasAdicionalesMglManager.create(notasAdicionalesMgl);
                            contador++;
                        }
                    }
                    ParametrosMgl parametrosMgl = parametrosMglManager.findByAcronimo(Constantes.ENVIAR_INFO_DIRECCION_RR).get(0);
                    boolean enviarInformacionRR = Boolean.parseBoolean(parametrosMgl.getParValor());
                    if (enviarInformacionRR) {
                        DireccionRRManager drrm = new DireccionRRManager(true);
                        drrm.agregarNotasHhppRR(hhppBuscado, listaNotasHhppMgls);
                    }
                    response.setMessageType("");
                    response.setMessage("Se agregaron correctamente " + contador + " notas adicionales al HHPP " + hhppBuscado.getHhpId());
                } else {
                    response.setMessageType("ERROR");
                    response.setMessage("No se puede encontrar el HHPP con el id " + hhppMgl.getHhpId());
                }
            } else {
                response.setMessageType("ERROR");
                response.setMessage("Verifique que los datos necesarios esten seteados en el objeto request");
            }
        } catch (ApplicationException e) {
            response.setMessageType("ERROR");
            response.setMessage("Se genero una exception " + e.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
        }
        return response;
    }

    public List<UnidadByInfo> getHhppByCoordinates(String longitude,
            String latitude, int deviationMtr, int unitsNumber, String comunidad) {
        NegocioParamMultivalor param = new NegocioParamMultivalor();
        PcmlManager pcmlManager = new PcmlManager();
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        UnidadByInfo unidades = new UnidadByInfo();
        List<UnidadByInfo> unidaByInfo = new ArrayList<>();
        BigDecimal idGeo = null;
        try {
            CityEntity city = param.consultaDptoCiudad(comunidad);
            GeograficoPoliticoMgl geo = geograficoPoliticoManager.
                    findCityByCodDane(city.getCodDane());
            idGeo = geo.getGpoId();
        } catch (ApplicationException e) {
            LOGGER.error("Error en getHhppByCoordinates. " + e.getMessage(), e);
        }
        List<HhppMgl> HhppMglList = hMhppglDaoImpl.getHhppByCoordinates(
                longitude, latitude, deviationMtr, unitsNumber, idGeo);
        Iterator HhppMglListIt = HhppMglList.iterator();
        PcmlLocator pcmlLocator = new PcmlLocator(
                Utilidades.queryParametrosConfig(co.com.telmex.catastro.services.util.Constant.URL_SERVICE_PCML));
        PcmlService pcmlServicePort;
        try {
            pcmlServicePort = pcmlLocator.getPcmlServicePort();
            while (HhppMglListIt.hasNext()) {
                Object[] hhpp = (Object[]) HhppMglListIt.next();
                ArrayList<UnidadStructPcml> listUnidades
                        = pcmlManager.getUnidades((String) hhpp[13],
                                (String) hhpp[14], null, comunidad);
                SuscriberVO[] listCuenta;
                for (UnidadStructPcml j : listUnidades) {
                    UnidadByInfo unidad = new UnidadByInfo();
                    HhppResponseRR regulaUnitResponse;
                    listCuenta = pcmlServicePort.getSuscriberInfo("SU",
                            j.getCuentaP(), null);
                    regulaUnitResponse = hMhppglDaoImpl.getHhPpRr(
                            (String) hhpp[17], (String) hhpp[16],
                            j.getAptoUnidad(), (String) hhpp[14], (String) hhpp[13]);
                    unidad.setUnidadStructPcml(j);
                    unidad.setEstado(
                            regulaUnitResponse.isExistenciaUnidad()
                            ? "Activo" : "Inactivo");
                    if (listCuenta != null) {
                        unidad.setCuenta(listCuenta[0]);
                    }
                    unidad.setMensaje("Completo");
                    unidaByInfo.add(unidad);
                }
            }
            return unidaByInfo;
        } catch (RemoteException | ApplicationException | ServiceException e) {
            LOGGER.error("Error en getHhppByCoordinates. " + e.getMessage(), e);
        }
        unidades.setEstado("3");
        unidades.setMensaje("No se encontro en el perimetro");
        unidaByInfo.add(unidades);
        return unidaByInfo;
    }

    public List<UnidadByInfo> getHhppByDireccion(String direccion, String comunidad, String barrio) {
        PcmlManager pcmlManager = new PcmlManager();
        UnidadByInfo unidades = new UnidadByInfo();
        List<UnidadByInfo> unidaByInfo = new ArrayList<>();
        //obtener los datos basico para la georeferencia
        // 1) Obtenemos all a partir de la comunidad
        NegocioParamMultivalor param = new NegocioParamMultivalor();
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        try {
            CityEntity city = param.consultaDptoCiudad(comunidad);
            // 2) Validamos si es multiorigen          
            GeograficoPoliticoMgl geo = geograficoPoliticoManager.findCityByCodDane(city.getCodDane());
            // 2.1) Preguntamos si trae el barrio y es multiOrigen  
            if ("1".equals(geo.getGpoMultiorigen())
                    && (barrio == null || barrio.equals("null") || !barrio.isEmpty())) {
                //Por vafor escriba el barrio ya que este es multi origen 
                unidades.setEstado("1");
                unidades.setMensaje("Por vafor escriba el barrio ya que este es multiorigen");
                unidaByInfo.add(unidades);
                return unidaByInfo;
            }
            if (barrio == null || "null".equalsIgnoreCase(barrio)) {
                barrio = "";
            }
            // 3) Metodo para obtener el GeoRefenciador
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setAddress(direccion);
            addressRequest.setCity(city.getCityName());
            addressRequest.setCodDaneVt(city.getCodDane());
            addressRequest.setLevel("C");
            addressRequest.setState(city.getDpto());
            AddressEJB adressEjb = new AddressEJB();
            AddressService geoReferencia = adressEjb.queryAddressEnrich(addressRequest);
            if (geoReferencia.getTraslate().equals("false")) {
                unidades.setEstado("2");
                unidades.setMensaje("Esta direccion no es posible traducirla");
                unidaByInfo.add(unidades);
                return unidaByInfo;
            }
            // convertimos la direccion a partir de la geo referenciacion
            // String longAddressCodeGeo, String shortAddressCodeGeo, BigDecimal city, String barrio
            DetalleDireccionManager detalleDireccion = new DetalleDireccionManager();
            DetalleDireccionEntity detalleDire = detalleDireccion.conversionADetalleDireccion(geoReferencia.getAddressCodeFound(), geoReferencia.getAddressCode(), geo.getGpoId(), barrio);
            if (detalleDire == null) {
                unidades.setEstado("2");
                unidades.setMensaje("Esta direccion no es posible traducirla");
                unidaByInfo.add(unidades);
                return unidaByInfo;
            }
            //Obtenemos la decision
            RrCiudadesManager ciudadesManager = new RrCiudadesManager();
            RrCiudades ciudades = ciudadesManager.findById(comunidad);
            detalleDire.setBarrio(barrio);
            detalleDire.setIdtipodireccion("CK");
            detalleDire.setMultiOrigen(geo.getGpoMultiorigen());
            DireccionRRManager direccionesRR = new DireccionRRManager(detalleDire, "", null);
            DireccionRREntity direRR = direccionesRR.getDireccion();
            PcmlLocator pcmlLocator = new PcmlLocator(
                    Utilidades.queryParametrosConfig(
                            co.com.telmex.catastro.services.util.Constant.URL_SERVICE_PCML));
            PcmlService pcmlServicePort = pcmlLocator.getPcmlServicePort();
            ArrayList<UnidadStructPcml> listUnidades
                    = pcmlManager.getUnidades(direRR.getCalle(),
                            direRR.getNumeroUnidad(), null, comunidad);
            SuscriberVO[] listCuenta;

            if (listUnidades.isEmpty()) {
                unidades.setEstado("3");
                unidades.setMensaje("Direccion no existe");
                unidaByInfo.add(unidades);
                return unidaByInfo;
            }

            for (UnidadStructPcml j : listUnidades) {
                UnidadByInfo unidad = new UnidadByInfo();
                listCuenta = pcmlServicePort.getSuscriberInfo("SU", j.getCuentaP(), null);
                HhppResponseRR regulaUnitResponse = hMhppglDaoImpl.getHhPpRr(
                        ciudades.getCodregional(), comunidad, j.getAptoUnidad(),
                        direRR.getNumeroUnidad(), direRR.getCalle());
                unidad.setEstado(
                        regulaUnitResponse.isExistenciaUnidad()
                        ? "Activo" : "Inactivo");
                unidad.setUnidadStructPcml(j);
                if (listCuenta != null) {
                    unidad.setCuenta(listCuenta[0]);
                }
                unidad.setMensaje("Completo");
                unidaByInfo.add(unidad);
            }

            return unidaByInfo;
        } catch (Exception e) {
            LOGGER.error("Error en getHhppByDirection. " + e.getMessage(), e);
        }
        unidades.setEstado("3");
        unidades.setMensaje("Direccion no existe");
        unidaByInfo.add(unidades);
        return unidaByInfo;

    }

    public boolean validacionNiveles(String nivel, String tipo) {
        ParametrosCallesManager parametros = new ParametrosCallesManager();
        List<ParametrosCalles> setData = parametros.findByTipo(tipo);
        return (setData.stream().anyMatch((p) -> (p.getDescripcion().toUpperCase().equals(nivel.toUpperCase().trim()))));
    }

    public String getCreateSolicitud(String idUser, String comunidad,
            String tipoSolicitud, String tipoOrigenSol, String barrio,
            String direccion, String nivel1, String nivel2, String nivel3,
            String nivel4, String nivel5, String nivel6, String value1,
            String value2, String value3, String value4, String value5,
            String value6) throws ApplicationException {
        Solicitud solicitar = new Solicitud();
        DrDireccion dir = new DrDireccion();

        // validacion de niveles 1-4 es la misma para U y B
        if (!("".equals(nivel1) && "".equals(value1))) {
            if ((!validacionNiveles(nivel1, "DIR_SUB_EDIFICIO") )&& !"".equals(value1)) {
                return "Nivel 1 incosistente";
            }
        }
        dir.setCpTipoNivel1(nivel1);
        dir.setCpValorNivel1(value1);

        if (!("".equals(nivel2.trim()) && "".equals(value2))) {
            if ((!validacionNiveles(nivel2, "DIR_SUB_EDIFICIO")) && !"".equals(value2)) {
                return "Nivel 2 incosistente";
            }
        }
        dir.setCpTipoNivel2(nivel2);
        dir.setCpValorNivel2(value2);

        if (!("".equals(nivel3.trim()) && "".equals(value3))) {
            if ((!validacionNiveles(nivel3, "DIR_SUB_EDIFICIO")) && !"".equals(value3)) {
                return "Nivel 3 incosistente";
            }
        }
        dir.setCpTipoNivel3(nivel3);
        dir.setCpValorNivel3(value3);

        if (!("".equals(nivel4.trim()) && "".equals(value4))) {
            if ((!validacionNiveles(nivel4, "DIR_SUB_EDIFICIO")) && !"".equals(value4)) {
                return "Nivel 4 incosistente";
            }
        }
        dir.setCpTipoNivel4(nivel4);
        dir.setCpValorNivel4(value4);

        ParametrosCallesManager parametros = new ParametrosCallesManager();
        List<ParametrosCalles> setData = parametros.findByTipo("TIPO_SOLICITUD");
        boolean flag = false;
        tipoOrigenSol = tipoOrigenSol.toUpperCase().trim();
        for (ParametrosCalles p : setData) {
            if (p.getDescripcion().toUpperCase().equals(tipoOrigenSol)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return "Error el tipo de solicitud no esta acorde con el tipo de comunidad";
        }

        RrCiudadesManager ciudades = new RrCiudadesManager();
        RrCiudades ciud = ciudades.findById(comunidad);
        RrRegionalesManager regional = new RrRegionalesManager();
        RrRegionales rRegional = regional.findById(ciud.getCodregional());
        tipoSolicitud = tipoSolicitud.toUpperCase().trim();

        // validacion de nivel 5 es diferente  para U y B
        if (tipoSolicitud.equals("CREACION HHPP UNIDI") && rRegional.getUnibi().equals("U")) {
            if ((!validacionNiveles(nivel5, "DIR_NUM_APTO")) || "".equals(nivel5.trim())) {
                return "Nivel 5 incosistente";
            }
            if (!"CASA".equals(nivel5.toUpperCase().trim())) {
                if ("".equals(value5.trim())) {
                    return "Nivel 5 incosistente";
                }
            }

        } else {
            if (tipoSolicitud.equals("VTCASA") && rRegional.getUnibi().equals("B")) {
                if ((!validacionNiveles(nivel5, "DIR_NIVEL_5_VC")) || "".equals(nivel5.trim())) {
                    return "Nivel 5 incosistente";
                }
                if (!"CASA".equals(nivel5.toUpperCase().trim()) && !"FUENTE".equals(nivel5.toUpperCase().trim())) {
                    if ("".equals(value5.trim())) {
                        return "Nivel 5 incosistente";
                    }
                }
            } else {
                return "Error el tipo de solicitud no esta acorde con el tipo de comunidad";
            }
            // validacion de nivel 6 es la misma  para U y B    
            if (nivel5.toUpperCase().trim().contains("+")) {
                if ((!validacionNiveles(nivel6, "DIR_NUM_APTOC"))) {
                    return "Nivel 6 incosistente";
                }
            }
            if (!("".equals(nivel6.trim()) && "".equals(value6))) {
                if ((!validacionNiveles(nivel6, "DIR_NUM_APTOC")) && !"".equals(value6)) {
                    return "Nivel 6 incosistente";
                }
            }

        }
        dir.setCpTipoNivel5(nivel5);
        dir.setCpValorNivel5(value5);
        dir.setCpTipoNivel6(nivel6);
        dir.setCpValorNivel6(value6);
        solicitar.setTipo(tipoSolicitud);
        solicitar.setCuentaMatriz("0");
        solicitar.setEstado("PENDIENTE");
        solicitar.setDireccion(direccion);

        UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
        UsuariosServicesDTO resUser
                = usuariosManager.consultaInfoUserPorCedula(idUser);

        solicitar.setContacto(resUser.getNombre());
        solicitar.setTelContacto(resUser.getTelefono());
        solicitar.setSolicitante(resUser.getNombre());
        solicitar.setCorreo(resUser.getEmail());
        solicitar.setTelSolicitante(resUser.getTelefono());
        if (resUser.getCedula() != null) {
            solicitar.setIdSolicitante(new BigDecimal(resUser.getCedula()));
        }

        Calendar fecha = Calendar.getInstance();
        solicitar.setFechaIngreso(fecha.getTime());
        solicitar.setCiudad(comunidad);
        solicitar.setRegional(rRegional.getCodigo());
        solicitar.setTipoVivienda("CASA");
        solicitar.setNumPuerta(direccion);
        solicitar.setTipoSol(tipoOrigenSol);
        solicitar.setCambioDir("0");
        solicitar.setBarrio(barrio);
        SolicitudManager solitudManager = new SolicitudManager();
        NegocioParamMultivalor param = new NegocioParamMultivalor();
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        CityEntity city;
        Solicitud sol = null;
        try {
            city = param.consultaDptoCiudad(comunidad);
            // 2) Validamos si es multiorigen          
            GeograficoPoliticoMgl geo = geograficoPoliticoManager.findCityByCodDane(city.getCodDane());
            // 2.1) Preguntamos si trae el barrio y es multiOrigen  
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setAddress(direccion);
            addressRequest.setCity(city.getCityName());
            addressRequest.setCodDaneVt(city.getCodDane());
            addressRequest.setLevel("C");
            addressRequest.setNeighborhood(barrio);
            addressRequest.setState(city.getDpto());
            AddressEJB adressEjb = new AddressEJB();
            //georeferencia
            AddressService geoReferencia = adressEjb.queryAddressEnrich(addressRequest);

            if (geoReferencia.getTraslate().equals("false")) {
                return "Esta direccion no es posible traducirla";
            }
            String idDireccion = geoReferencia.getIdaddress();
            if (idDireccion == null || "0".equals(idDireccion) || "".equals(idDireccion)) {
                if ("1".equals(geo.getGpoMultiorigen()) && (barrio == null || barrio.equals("null")  || barrio.equals(""))) {
                    //Por vafor escriba el barrio ya que este es multi origen 
                    // si la ciudad es multiorigen envio barrio si no es multi origen no envio nada
                    addressRequest.setNeighborhood("");
                } else {
                    addressRequest.setNeighborhood(barrio);
                }
                ResponseMessage response = adressEjb.createAddress(addressRequest, resUser.getUsuario(), co.com.telmex.catastro.services.comun.Constantes.NOMBRE_FUNCIONALIDAD, comunidad);
                idDireccion = response.getIdaddress();
            }
            // convertimos la direccion a partir de la geo referenciacion
            // String longAddressCodeGeo, String shortAddressCodeGeo, BigDecimal city, String barrio
            DetalleDireccionManager detalleDireccion = new DetalleDireccionManager();
            DetalleDireccionEntity detalleDire = detalleDireccion.conversionADetalleDireccion(geoReferencia.getAddressCodeFound(), geoReferencia.getAddressCode(), geo.getGpoId(), barrio);
            if (detalleDire == null) {
                return "Esta direccion no es posible traducirla";
            }
            //Obtenemos la decision
            detalleDire.setDirprincalt("P");
            detalleDire.setLtviaprincipal(("".equals(detalleDire.getLtviaprincipal())) ? null : "'" + detalleDire.getLtviaprincipal() + "'");
            detalleDire.setNlpostviap(("".equals(detalleDire.getNlpostviap())) ? null : "'" + detalleDire.getNlpostviap() + "'");
            detalleDire.setBisviaprincipal(("".equals(detalleDire.getBisviaprincipal())) ? null : "'" + detalleDire.getBisviaprincipal() + "'");
            detalleDire.setCuadviaprincipal(("".equals(detalleDire.getCuadviaprincipal())) ? null : "'" + detalleDire.getCuadviaprincipal() + "'");
            detalleDire.setTipoviageneradora(("".equals(detalleDire.getTipoviageneradora())) ? null : "'" + detalleDire.getTipoviageneradora() + "'");
            detalleDire.setNumviageneradora(("".equals(detalleDire.getNumviageneradora())) ? null : "'" + detalleDire.getNumviageneradora() + "'");
            detalleDire.setLtviageneradora(("".equals(detalleDire.getLtviageneradora())) ? null : "'" + detalleDire.getLtviageneradora() + "'");
            detalleDire.setNlpostviag(("".equals(detalleDire.getNlpostviag())) ? null : "'" + detalleDire.getNlpostviag() + "'");
            detalleDire.setBisviageneradora(("".equals(detalleDire.getBisviageneradora())) ? null : "'" + detalleDire.getBisviageneradora() + "'");
            detalleDire.setCuadviageneradora(("".equals(detalleDire.getCuadviageneradora())) ? null : "'" + detalleDire.getCuadviageneradora() + "'");
            detalleDire.setPlacadireccion(("".equals(detalleDire.getPlacadireccion())) ? null : "'" + detalleDire.getPlacadireccion() + "'");
            detalleDire.setCptiponivel1(("".equals(dir.getCpTipoNivel1())) ? null : "'" + dir.getCpTipoNivel1() + "'");
            detalleDire.setCptiponivel2(("".equals(dir.getCpTipoNivel2())) ? null : "'" + dir.getCpTipoNivel2() + "'");
            detalleDire.setCptiponivel3(("".equals(dir.getCpTipoNivel3())) ? null : "'" + dir.getCpTipoNivel3() + "'");
            detalleDire.setCptiponivel4(("".equals(dir.getCpTipoNivel4())) ? null : "'" + dir.getCpTipoNivel4() + "'");
            detalleDire.setCptiponivel5(("".equals(dir.getCpTipoNivel5())) ? null : "'" + dir.getCpTipoNivel5() + "'");
            detalleDire.setCptiponivel6(("".equals(dir.getCpTipoNivel6())) ? null : "'" + dir.getCpTipoNivel6() + "'");
            detalleDire.setCpvalornivel1(("".equals(dir.getCpValorNivel1())) ? null : "'" + dir.getCpValorNivel1() + "'");
            detalleDire.setCpvalornivel2(("".equals(dir.getCpValorNivel2())) ? null : "'" + dir.getCpValorNivel2() + "'");
            detalleDire.setCpvalornivel3(("".equals(dir.getCpValorNivel3())) ? null : "'" + dir.getCpValorNivel3() + "'");
            detalleDire.setCpvalornivel4(("".equals(dir.getCpValorNivel4())) ? null : "'" + dir.getCpValorNivel4() + "'");
            detalleDire.setCpvalornivel5(("".equals(dir.getCpValorNivel5())) ? null : "'" + dir.getCpValorNivel5() + "'");
            detalleDire.setCpvalornivel6(("".equals(dir.getCpValorNivel6())) ? null : "'" + dir.getCpValorNivel6() + "'");
            detalleDire.setMztiponivel1(("".equals(detalleDire.getMztiponivel1())) ? null : detalleDire.getMztiponivel1());
            detalleDire.setMztiponivel2(("".equals(detalleDire.getMztiponivel2())) ? null : detalleDire.getMztiponivel2());
            detalleDire.setMztiponivel3(("".equals(detalleDire.getMztiponivel3())) ? null : detalleDire.getMztiponivel3());
            detalleDire.setMztiponivel4(("".equals(detalleDire.getMztiponivel4())) ? null : detalleDire.getMztiponivel4());
            detalleDire.setMztiponivel5(("".equals(detalleDire.getMztiponivel5())) ? null : detalleDire.getMztiponivel5());
            detalleDire.setMzvalornivel1(("".equals(detalleDire.getMzvalornivel1())) ? null : detalleDire.getMzvalornivel1());
            detalleDire.setMzvalornivel2(("".equals(detalleDire.getMzvalornivel2())) ? null : detalleDire.getMzvalornivel2());
            detalleDire.setMzvalornivel3(("".equals(detalleDire.getMzvalornivel3())) ? null : detalleDire.getMzvalornivel3());
            detalleDire.setMzvalornivel4(("".equals(detalleDire.getMzvalornivel4())) ? null : detalleDire.getMzvalornivel4());
            detalleDire.setMzvalornivel5(("".equals(detalleDire.getMzvalornivel5())) ? null : detalleDire.getMzvalornivel5());
            detalleDire.setTipoviaprincipal(("".equals(detalleDire.getTipoviaprincipal())) ? null : "'" + detalleDire.getTipoviaprincipal() + "'");

            detalleDire.setBarrio(barrio);
            detalleDire.setIdtipodireccion("'" + "CK" + "'");
            detalleDire.setMultiOrigen(geo.getGpoMultiorigen());
            detalleDire.setIdDirCatastro("'" + idDireccion + "'");
            //crea la solicitud
            //validacio de la direccion no este entramite
            ParametrosMultivalorEJB parametrosMultivalorEjb = new ParametrosMultivalorEJB();
            BigDecimal resutValidation = parametrosMultivalorEjb.isSolicitudInProcess(detalleDire, comunidad);
            if (BigDecimal.ZERO.compareTo(resutValidation) != 0) {
                return "Esta direccion tiene una solicitud en proceso: " + resutValidation;
            }

            detalleDire = detalleDireccion.conversionADetalleDireccion(geoReferencia.getAddressCodeFound(), geoReferencia.getAddressCode(), geo.getGpoId(), barrio);
            detalleDire.setDirprincalt("P");
            detalleDire.setBarrio(barrio);
            detalleDire.setIdtipodireccion("CK");
            detalleDire.setMultiOrigen(geo.getGpoMultiorigen());
            detalleDire.setIdDirCatastro(idDireccion);
            detalleDire.setCptiponivel1(("".equals(dir.getCpTipoNivel1())) ? null : dir.getCpTipoNivel1());
            detalleDire.setCptiponivel2(("".equals(dir.getCpTipoNivel2())) ? null : dir.getCpTipoNivel2());
            detalleDire.setCptiponivel3(("".equals(dir.getCpTipoNivel3())) ? null : dir.getCpTipoNivel3());
            detalleDire.setCptiponivel4(("".equals(dir.getCpTipoNivel4())) ? null : dir.getCpTipoNivel4());
            detalleDire.setCptiponivel5(("".equals(dir.getCpTipoNivel5())) ? null : dir.getCpTipoNivel5());
            detalleDire.setCptiponivel6(("".equals(dir.getCpTipoNivel6())) ? null : dir.getCpTipoNivel6());
            detalleDire.setCpvalornivel1(("".equals(dir.getCpValorNivel1())) ? null : dir.getCpValorNivel1());
            detalleDire.setCpvalornivel2(("".equals(dir.getCpValorNivel2())) ? null : dir.getCpValorNivel2());
            detalleDire.setCpvalornivel3(("".equals(dir.getCpValorNivel3())) ? null : dir.getCpValorNivel3());
            detalleDire.setCpvalornivel4(("".equals(dir.getCpValorNivel4())) ? null : dir.getCpValorNivel4());
            detalleDire.setCpvalornivel5(("".equals(dir.getCpValorNivel5())) ? null : dir.getCpValorNivel5());
            detalleDire.setCpvalornivel6(("".equals(dir.getCpValorNivel6())) ? null : dir.getCpValorNivel6());

            sol = solitudManager.crear(solicitar);
            detalleDire.setIdsolicitud(sol.getIdSolicitud());

            DrDireccionDaoImpl drDireccionDaoImpl = new DrDireccionDaoImpl();

            dir.setIdSolicitud(sol != null ? sol.getIdSolicitud() : null);
            dir.setEstadoDirGeo(geoReferencia.getState());
            dir.obtenerFromDetalleDireccionEntity(detalleDire);
            dir.setDirPrincAlt("P");
            dir.setIdDirCatastro(idDireccion);
            drDireccionDaoImpl.create(dir);
        } catch (Exception e) {
            LOGGER.error("Error en getCreatedSolicitud: " + e.getMessage(), e);
        }
        return sol != null ? sol.getIdSolicitud().toString() : null;
    }

    /**
     * Valida si Existe la unidad.Función utilizada para validar si la unidad
     * existe
     *
     * @param cityEntity objeto en el que viene cargada la direccion que se
     * desea validar su existencia
     * @param centroPobladoId id del centro poblado en el que se encuentra la
     * dirección.
     * @param tipoAccion tipo de accion de la solicitud para validar. 3.
     * reactivación.
     * @param tecnologiaBasicaId
     *
     * @return verdarero si la unidad Existe en Repositorio
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public HhppMgl validaExistenciaHhpp(CityEntity cityEntity,
            BigDecimal centroPobladoId, String tipoAccion, BigDecimal tecnologiaBasicaId) throws ApplicationException {

        if (cityEntity != null && cityEntity.getDireccionPrincipal() != null) {

            CmtDireccionDetalleMglManager direccionDetalleMglManager = new CmtDireccionDetalleMglManager();
            List<HhppMgl> hhppList = direccionDetalleMglManager
                    .findHhppByDireccion(cityEntity.getDireccionPrincipal(), centroPobladoId, false,
                            0, 0, false);

            if ((hhppList == null || hhppList.isEmpty())
                    && cityEntity.getDireccionRespuestaGeo() != null
                    && !cityEntity.getDireccionRespuestaGeo().isEmpty()) {

                CmtDireccionDetalleMglManager direccionDetalleManager = new CmtDireccionDetalleMglManager();

                List<CmtDireccionDetalladaMgl> direccionDetalladaList
                        = direccionDetalleManager
                                .buscarDireccionDetalladaNivelesComplementosByDireccionTexto(centroPobladoId,
                                        cityEntity.getDireccionPrincipal(), cityEntity.getDireccionRespuestaGeo());

                //las direcciones detalladas encontradas extraemos la direccion y subdireccion
                if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                    hhppList = obtenerHhppByDireccionDetallaList(direccionDetalladaList);
                }
            }

            //Creacion de hhpp
            if (tipoAccion.equals(Constant.RR_DIR_CREA_HHPP_0)) {
                if (hhppList != null && !hhppList.isEmpty()) {
                    for (HhppMgl hhppMgl : hhppList) {
                        if (hhppMgl.getEstadoRegistro() == 1
                                && hhppMgl.getNodId() != null
                                && hhppMgl.getNodId().getNodTipo() != null
                                && hhppMgl.getNodId().getNodTipo().getBasicaId()
                                        .equals(tecnologiaBasicaId)) {
                            return hhppMgl;
                        }
                    }
                }
            }

            // cambio de dirección // no importa la tecnologia seleccionada
            if (tipoAccion.equals(Constant.RR_DIR_CAMB_HHPP_1) && hhppList != null && !hhppList.isEmpty()) {
                return hhppList.get(0);
            }

            /*realiza recorrido sobre todas las direcciones encontradas pero 
             * valida la que contenga la misma tecnología para reactivarla*/
            //reactivación de hhpp
            if (tipoAccion.equals(Constant.RR_DIR_REAC_HHPP_3)) {
                if (hhppList != null && !hhppList.isEmpty()) {
                    for (HhppMgl hhppMgl : hhppList) {
                        if (hhppMgl.getEstadoRegistro() == 0
                                && hhppMgl.getNodId() != null
                                && hhppMgl.getNodId().getNodTipo() != null
                                && hhppMgl.getNodId().getNodTipo().getBasicaId()
                                        .equals(tecnologiaBasicaId)) {
                            return hhppMgl;
                        }
                    }
                }
            }

            //valida existencia de HHPP real para poder crear el HHPP virtual
            if (tipoAccion.equals(Constant.TRASLADO_HHPP_BLOQUEADO_5) && CollectionUtils.isNotEmpty(hhppList)) {

                return hhppList.stream()
                        .filter(hhpp -> hhpp.getEstadoRegistro() == 1
                                && hhpp.getNodId() != null
                                && hhpp.getNodId().getNodTipo() != null
                                && hhpp.getNodId().getNodTipo().getBasicaId().equals(tecnologiaBasicaId))
                        .findFirst().orElse(null);
            }

            return null;
        } else {
            throw new ApplicationException("ocurrio un error realizando la consulta de "
                    + "validación de existencia de la unidad");
        }
    }

    public HhppMgl validaExistenciaHhppFichas(DrDireccion drDireccion,
            BigDecimal centroPobladoId, String tipoAccion, BigDecimal tecnologiaBasicaId) throws ApplicationException {

        if (drDireccion != null) {

            CmtDireccionDetalleMglManager direccionDetalleMglManager = new CmtDireccionDetalleMglManager();
            List<HhppMgl> hhppList = direccionDetalleMglManager
                    .findHhppByDireccion(drDireccion, centroPobladoId, false,
                            0, 0, false);

            //Creacion de hhpp
            if (tipoAccion.equals(Constant.RR_DIR_CREA_HHPP_0)) {
                if (hhppList != null && !hhppList.isEmpty()) {
                    for (HhppMgl hhppMgl : hhppList) {
                        if (hhppMgl.getNodId() != null && hhppMgl.getNodId().getNodTipo() != null
                                && hhppMgl.getNodId().getNodTipo().getBasicaId().equals(tecnologiaBasicaId)) {
                            return hhppMgl;
                        }
                    }
                }
            }
            return null;
        } else {
            throw new ApplicationException("ocurrio un error realizando la consulta de "
                    + "validación de existencia de la unidad");
        }

    }

    public HhppMgl findHhppByRRFieldsInRepository(HhppMgl hhpp) {
        try {
            return hMhppglDaoImpl.findHhppByRRFieldsInRepository(hhpp);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            return null;
        }
    }

    public List<HhppAuditoriaMgl> findHhppAuditoriaByIdHhppList(HhppMgl hhpp) {
        try {
            return hMhppglDaoImpl.findHhppAuditoriaByIdHhppList(hhpp);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            return null;
        }
    }

    /**
     * Obtiene Hhpp por dirección
     *
     * @param direccion la dirección construida que deseamos buscarle hhpp
     * asociados
     * @param gpo ciudad por la que se desea filtrar
     * @param suscriptor número de suscriptor
     * @param paginaSelected
     * @param maxResults
     *
     * @return listado de hhpp asociados a la dirección buscada
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<HhppMgl> findHhppByDireccion(String direccion, BigDecimal gpo,
            String suscriptor, int paginaSelected, int maxResults) {
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return hMhppglDaoImpl.findHhppByDireccion(direccion, gpo, suscriptor,
                firstResult, maxResults);
    }

    /**
     * Cuenta Hhpp por dirección
     *
     * @param direccion la dirección construida que deseamos buscarle hhpp
     * asociados
     * @param gpo ciudad por la que se desea filtrar
     * @param suscriptor número de suscriptor
     *
     * @return resultado del conteo de hhpp
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int countHhppByDireccion(String direccion, BigDecimal gpo, String suscriptor) {
        return hMhppglDaoImpl.countHhppByDireccion(direccion, gpo, suscriptor);
    }

    public List<HhppMgl> findByNodoMgl(NodoMgl nodoMgl) throws ApplicationException {
        return hMhppglDaoImpl.findByNodoMgl(nodoMgl);
    }

    /**
     * Buscar Hhpp Busca los hhpp que correspondan según los filtros como mínimo
     * debe ser diferente de null la ciudad y el tipo de tecnología.
     *
     * @author becerraarmr
     * @param idCiudad ciudad de geográfico
     * @param idCentroPoblado el centro poblado
     * @param idNodo un nodo de la ciudad o del centro poblado
     * @param tipoTecnologia el tipo de tecnología relacionadas
     * @param nombreBasica nombre del atributo del hhpp
     * @param valorAtributo valor del atributo a buscar, puede ser String y
     * DrDirecciones
     * @param fechaInicial - fecha Inicial para inhabilitar
     * @param range el rango de busqueda del valor.
     * @param fechaFinal - fecha final para inhabilitar.
     * @param idHhpp id del hhpp
     * @param inhabilitar
     * @param creadoXFicha
     * @param etiqueta
     * @param idCcmmMgl
     * @param idCcmmRr
     *
     * @return el entero con el valor encontrado
     *
     * @throws ApplicationException si hay algún error en la busqueda
     */
    public List<HhppMgl> findHhpp(BigDecimal idCiudad, BigDecimal idCentroPoblado,
            BigDecimal idNodo, BigDecimal tipoTecnologia,
            String nombreBasica, Object valorAtributo,
            Date fechaInicial, Date fechaFinal,
            int[] range, BigDecimal idHhpp, boolean inhabilitar,
            boolean creadoXFicha, BigDecimal etiqueta, BigDecimal idCcmmMgl,
            BigDecimal idCcmmRr) throws ApplicationException {

        List<HhppMgl> lstresultado = null;
        List<HhppMgl> lstresultadoTemp;

        if (valorAtributo instanceof DrDireccion) {
            lstresultadoTemp = hMhppglDaoImpl.findHhppDireccion(idCiudad, idCentroPoblado, idNodo, tipoTecnologia, (DrDireccion) valorAtributo, fechaInicial, fechaFinal, range, idHhpp, etiqueta);
        } else {
            lstresultadoTemp = hMhppglDaoImpl.findHhpp(idCiudad, idCentroPoblado, idNodo, tipoTecnologia, nombreBasica, (String) valorAtributo, fechaInicial, fechaFinal, range, idHhpp, etiqueta, idCcmmMgl, idCcmmRr);
        }

        if (inhabilitar) {
            if (lstresultadoTemp != null && !lstresultadoTemp.isEmpty()) {
                OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
                List<OtHhppMgl> lstHhppMgls;
                lstresultado = new ArrayList<>();
                for (HhppMgl hhppMgl : lstresultadoTemp) {
                    BigDecimal idSubDir = null;
                    if (hhppMgl.getSubDireccionObj() != null) {
                        idSubDir = hhppMgl.getSubDireccionObj().getSdiId();
                    }
                    lstHhppMgls = otHhppMglManager.findAllOtHhppByDireccionDetalladaId(0, 0,
                            hhppMgl.getDireccionObj().getDirId(), idSubDir, null);
                    if (lstHhppMgls != null && !lstHhppMgls.isEmpty()) {
                        OtHhppMgl otHhppMgl = lstHhppMgls.get(0);
                        hhppMgl.setEstadoOt(otHhppMgl.getEstadoGeneral() != null ? otHhppMgl.getEstadoGeneral().getNombreBasica() : "NA");
                        if (creadoXFicha) {
                            if (hhppMgl.getHhppOrigenFicha() != null) {
                                lstresultado.add(hhppMgl);
                            }
                        } else {
                            lstresultado.add(hhppMgl);
                        }
                    } else {
                        hhppMgl.setEstadoOt("SIN OT");
                        if (creadoXFicha) {
                            if (hhppMgl.getHhppOrigenFicha() != null) {
                                if (etiqueta != null) {
                                    List<MarcasHhppMgl> listaMarcasHhp = hhppMgl.getListMarcasHhpp();
                                    for (MarcasHhppMgl marcasHhppMgl : listaMarcasHhp) {
                                        if (marcasHhppMgl.getMarId() != null && marcasHhppMgl.getMarId().getMarId() != null) {
                                            if (marcasHhppMgl.getMarId().getMarId().compareTo(etiqueta) == 0) {
                                                hhppMgl.setMarcasHhppMgl(marcasHhppMgl);
                                            }
                                        }
                                    }
                                }
                                lstresultado.add(hhppMgl);
                            }
                        } else {
                            if (etiqueta != null) {
                                List<MarcasHhppMgl> listaMarcasHhp = hhppMgl.getListMarcasHhpp();
                                for (MarcasHhppMgl marcasHhppMgl : listaMarcasHhp) {
                                    if (marcasHhppMgl.getMarId() != null && marcasHhppMgl.getMarId().getMarId() != null) {
                                        if (marcasHhppMgl.getMarId().getMarId().compareTo(etiqueta) == 0) {
                                            hhppMgl.setMarcasHhppMgl(marcasHhppMgl);
                                        }
                                    }
                                }
                            }
                            lstresultado.add(hhppMgl);
                        }
                    }

                }

            }
        } else {
            if (lstresultadoTemp != null && !lstresultadoTemp.isEmpty()) {
                lstresultado = new ArrayList<>();
                for (HhppMgl hhppMgl : lstresultadoTemp) {
                    if (creadoXFicha) {
                        if (hhppMgl.getHhppOrigenFicha() != null) {
                            if (etiqueta != null) {
                                List<MarcasHhppMgl> listaMarcasHhp = hhppMgl.getListMarcasHhpp();
                                for (MarcasHhppMgl marcasHhppMgl : listaMarcasHhp) {
                                    if (marcasHhppMgl.getMarId() != null && marcasHhppMgl.getMarId().getMarId() != null) {
                                        if (marcasHhppMgl.getMarId().getMarId().compareTo(etiqueta) == 0) {
                                            hhppMgl.setMarcasHhppMgl(marcasHhppMgl);
                                        }
                                    }
                                }
                            }
                            lstresultado.add(hhppMgl);
                        }
                    } else {
                        if (etiqueta != null) {
                            List<MarcasHhppMgl> listaMarcasHhp = hhppMgl.getListMarcasHhpp();
                            for (MarcasHhppMgl marcasHhppMgl : listaMarcasHhp) {
                                if (marcasHhppMgl.getMarId() != null && marcasHhppMgl.getMarId().getMarId() != null) {
                                    if (marcasHhppMgl.getMarId().getMarId().compareTo(etiqueta) == 0) {
                                        hhppMgl.setMarcasHhppMgl(marcasHhppMgl);
                                    }
                                }
                            }
                        }
                        lstresultado.add(hhppMgl);
                    }
                }
            }
        }
        return lstresultado;
    }

    /**
     * Contar Hhpp Cuenta los hhpp que correspondan según los filtros como
     * mínimo debe ser diferente de null la ciudad y el tipo de tecnología.
     *
     * @author becerraarmr
     * @param idCiudad ciudad de geográfico
     * @param idCentroPoblado el centro poblado
     * @param idNodo un nodo de la ciudad o del centro poblado
     * @param tipoTecnologia el tipo de tecnología relacionadas
     * @param nombreBasica nombre del atributo del hhpp
     * @param valorAtributo valor del atributo a buscar, puede ser String y
     * DrDirecciones
     * @param fechaInicial - fecha inicial de inhabilitar
     * @param fechaFin - fecha final de inhabilitar.
     * @param idHhpp id del hhpp
     * @param inhabilitar
     * @param creadoXFicha
     * @param etiqueta
     * @param idCcmmMgl
     * @param idCcmmRr
     *
     * @return el entero con el valor encontrado
     *
     * @throws ApplicationException si hay algún error en la busqueda
     */
    public int countHhpp(BigDecimal idCiudad, BigDecimal idCentroPoblado, BigDecimal idNodo,
            BigDecimal tipoTecnologia, String nombreBasica, Object valorAtributo,
            Date fechaInicial, Date fechaFin, BigDecimal idHhpp, boolean inhabilitar,
            boolean creadoXFicha, BigDecimal etiqueta, BigDecimal idCcmmMgl, BigDecimal idCcmmRr) throws ApplicationException {

        int resultado = 0;

        List<HhppMgl> lstHhppMgls = this.findHhpp(idCiudad, idCentroPoblado, idNodo,
                tipoTecnologia, nombreBasica, valorAtributo, fechaInicial, fechaFin,
                null, idHhpp, inhabilitar, creadoXFicha, etiqueta, idCcmmMgl, idCcmmRr);

        resultado = lstHhppMgls != null && !lstHhppMgls.isEmpty() ? lstHhppMgls.size() : 0;

        return resultado;
    }

    /**
     * valbuenayf Metodo para buscar los hhpp de un id subdireccion
     *
     * @param idSubDireccion
     *
     * @return
     */
    public List<HhppMgl> findHhppSubDireccion(BigDecimal idSubDireccion) {
        try {
            return hMhppglDaoImpl.findHhppSubDireccion(idSubDireccion);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            return new ArrayList<>();
        }
    }

    /**
     * valbuenayf Metodo para buscar los hhpp de un id direccion
     *
     * @param idDireccion
     *
     * @return
     */
    public List<HhppMgl> findHhppDireccion(BigDecimal idDireccion) {
        try {
            return hMhppglDaoImpl.findHhppDireccion(idDireccion);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            return new ArrayList<>();
        }
    }
    
        /**
     * valbuenayf Metodo para buscar los hhpp de un id subdireccion
     *
     * @param HhpCalle
     * @param HhpPlaca
     * @param HhpApart
     * @param HhpComunidad
     * @param HhpDivision
     * @return 
     */
    public List<HhppMgl> findHhppNap(String HhpCalle, String HhpPlaca,String HhpComunidad) {
        try {
            return hMhppglDaoImpl.findHhppNap(HhpCalle,HhpPlaca,HhpComunidad);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            return new ArrayList<>();
        }
    }

    public ResponseCreateHhppDto crearHhppRRAndRepoFromCcMm(String nivel5, String valorNivel5,
            String nivel6, String valorNivel6, CmtSubEdificioMgl cmtSubEdificioMgl,
            String usuario, String solicitudId, String solicitudTipoId,
            String observaciones, String razon, CmtTecnologiaSubMgl tecnologiaSub, CmtHhppVtMgl cmtHhppVtMgl, int perfil) throws ApplicationException {

        ResponseCreateHhppDto responseCreateHhppDto = new ResponseCreateHhppDto();
        try {

            AddressEJB addressEJB = new AddressEJB();
            CmtDireccionMgl cmtdireccionMgl;
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtSubEdificioMglManager subEdificioManager;
            DrDireccionManager direccionManager = new DrDireccionManager();
            CmtTipoBasicaMgl cmtTipoBasicaMglCon;
            cmtTipoBasicaMglCon = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_SUBEDIFICIO);
            List<CmtBasicaMgl> tipoSubEdificioList = cmtBasicaMglManager.findByTipoBasica(cmtTipoBasicaMglCon);
            String direccionCrearGeo;
            DrDireccion drDireccion = new DrDireccion();
            DetalleDireccionEntity direccionEntity;
            DireccionRREntity direccionRREntity;
            CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
            ParametrosMgl parametrosMgl = parametrosMglManager.findByAcronimo(Constant.NIVELES_CASAS_CON_DIRECCION).get(0);
            String nivel = parametrosMgl.getParValor();
            if (!nivel.equals("")) {
                String[] stringniv = nivel.split("\\|");
                List<String> niveles = Arrays.asList(stringniv);
                if (cmtHhppVtMgl.getOpcionNivel5() != null
                        && !cmtHhppVtMgl.getOpcionNivel5().isEmpty()
                        && niveles.contains(cmtHhppVtMgl.getOpcionNivel5())) {
                    nivel = cmtHhppVtMgl.getOpcionNivel5();
                }
            }

            if (cmtSubEdificioMgl.getListDireccionesMgl() == null
                    || cmtSubEdificioMgl.getListDireccionesMgl().isEmpty()) {
                // desde la solicitud de creacion de hhpp sin entrada en el subedificio
                if (!razon.contains(("CM"))) {
                    String[] nombreSubEdificioSep = null;
                    String nombreValor = "";
                    if (cmtSubEdificioMgl.getListDireccionesMgl().isEmpty()) {
                        // se cargan los campos del DrDireccion
                        // cargar el DrDireccion direccion de la cuenta matriz
                        cmtdireccionMgl = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal();
                        drDireccion.obtenerFromCmtDireccionMgl(cmtdireccionMgl);
                        // cargar el DrDireccion el  Barrio
                        if (cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio() != null
                                && !cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio().isEmpty()) {
                            drDireccion.setBarrio(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio());
                        }
                        // Subedificio multiedifcio
                        if (cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getBasicaId().
                                compareTo(cmtBasicaMglManager.
                                        findByCodigoInternoApp(
                                                Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) == 0) {
                            //metodo para separar el tipoNivel del valor 
                            nombreSubEdificioSep = cmtSubEdificioMgl.getNombreSubedificio().split(" ");
                            nombreValor = "";
                            for (int i = 0; i < nombreSubEdificioSep.length; i++) {
                                if (i != 0) {
                                    nombreValor += nombreSubEdificioSep[i] + " ";
                                }
                            }
                            drDireccion.setCpTipoNivel1(nombreSubEdificioSep[0]);
                            drDireccion.setCpValorNivel1(nombreValor);
                        }

                        drDireccion.setCpTipoNivel5(nivel5);
                        drDireccion.setCpValorNivel5(valorNivel5);
                        if (nivel6 != null && !nivel6.trim().isEmpty()) {
                            drDireccion.setCpTipoNivel6(nivel6);
                            drDireccion.setCpValorNivel6(valorNivel6);
                        }
                        // convertir a direccionEntity en base al DrDireccion
                        direccionEntity = drDireccion.convertToDetalleDireccionEntity();
                        // convertir a String en base al DrDireccion
                        direccionCrearGeo = direccionManager.getDireccion(drDireccion);
                    } else {
                        // desde la solicitud de creacion de hhpp con entrada en el subedificio
                        cmtdireccionMgl = cmtSubEdificioMgl.getListDireccionesMgl().get(0);
                        drDireccion.obtenerFromCmtDireccionMgl(cmtdireccionMgl);
                        if (cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio() != null
                                && !cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio().isEmpty()) {
                            drDireccion.setBarrio(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio());
                        }
                        if (cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getBasicaId().
                                compareTo(cmtBasicaMglManager.
                                        findByCodigoInternoApp(
                                                Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) == 0) {
                            //metodo para separar el tipoNivel del valor  
                            nombreSubEdificioSep = cmtSubEdificioMgl.getNombreSubedificio().split(" ");
                            nombreValor = "";
                            for (int i = 0; i < nombreSubEdificioSep.length; i++) {
                                if (i != 0) {
                                    nombreValor += nombreSubEdificioSep[i] + " ";
                                }
                            }
                        }
                        drDireccion.setCpTipoNivel1(nombreSubEdificioSep[0]);
                        drDireccion.setCpValorNivel1(nombreValor);
                        drDireccion.setCpTipoNivel5(nivel5);
                        drDireccion.setCpValorNivel5(valorNivel5);
                        if (nivel6 != null && !nivel6.trim().isEmpty()) {
                            drDireccion.setCpTipoNivel6(nivel6);
                            drDireccion.setCpValorNivel6(valorNivel6);
                        }
                        direccionEntity = drDireccion.convertToDetalleDireccionEntity();
                        direccionCrearGeo = direccionManager.getDireccion(drDireccion);
                    }

                } else {
                    // Casas con su propia direccion id almacenado en  TEC_SOLICITUD_DIRECCION         
                    if (cmtHhppVtMgl.getIdrDireccionCasa() != null) {
                        //consultar la drDireccion de la casa en la tabla  TEC_SOLICITUD_DIRECCION
                        drDireccion = direccionManager.findByIdSolicitud(cmtHhppVtMgl.getIdrDireccionCasa());

                        // carga de DrDireccion niveles 5 y 6
                        if (razon.contains(("CM"))) {
                            if (cmtHhppVtMgl.getOpcionNivel5().equals(Constant.OPCION_NIVEL_OTROS)) {
                                drDireccion.setCpTipoNivel5(Constant.OPCION_NIVEL_OTROS);
                                drDireccion.setCpValorNivel5(valorNivel5.toUpperCase());
                            } else if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel) && cmtHhppVtMgl.getValorNivel5() == null) {
                                if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId().
                                        compareTo(cmtBasicaMglManager.
                                                findByCodigoInternoApp(
                                                        Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) == 0) {
                                    drDireccion.setCpTipoNivel5(nivel5);
                                    drDireccion.setCpValorNivel5(null);
                                } else {
                                    drDireccion.setCpTipoNivel5(nivel5);
                                    drDireccion.setCpValorNivel5(null);
                                }

                            } else {
                                drDireccion.setCpTipoNivel5(nivel5.toUpperCase());
                                drDireccion.setCpValorNivel5(valorNivel5);
                            }
                        }
                        drDireccion.setBarrio(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio());
                        //metodo para separar el tipoNivel del valor  
                        String[] nombreSubEdificioSep = cmtSubEdificioMgl.getNombreSubedificio().split(" ");
                        StringBuilder nombreValor = new StringBuilder("");
                        for (int i = 0; i < nombreSubEdificioSep.length; i++) {
                            if (i != 0) {
                                nombreValor.append(nombreSubEdificioSep[i] + " ");
                            }
                        }
                        // cargar nivel 5 de las casas con su propia direccion 
                        if (cmtHhppVtMgl.getValorNivel5() == null && cmtHhppVtMgl.getOpcionNivel5().equals(nivel)) {
                            drDireccion.setCpTipoNivel1(nombreSubEdificioSep[0]);
                            drDireccion.setCpValorNivel1(nombreValor.toString());
                        } else {
                            if (!cmtHhppVtMgl.getValorNivel5().equals("SALAVENTAS") || (!cmtHhppVtMgl.getValorNivel5().equals("CAMPAMENTO"))) {
                                drDireccion.setCpTipoNivel1(nombreSubEdificioSep[0]);
                                drDireccion.setCpValorNivel1(nombreValor.toString());
                            }
                        }

                        direccionEntity = drDireccion.convertToDetalleDireccionEntity();
                        direccionCrearGeo = direccionManager.getDireccion(drDireccion);

                    } else {
                        // se cargan los campos del DrDireccion
                        // cargar el DrDireccion direccion de la cuenta matriz 
                        cmtdireccionMgl = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal();
                        drDireccion.obtenerFromCmtDireccionMgl(cmtdireccionMgl);
                        // cargar el DrDireccion con el barrio
                        if (cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio() != null
                                && !cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio().isEmpty()) {
                            drDireccion.setBarrio(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio());
                        }
                        subEdificioManager = new CmtSubEdificioMglManager();
                        CmtNombreSubEdificioDivDto divDto = subEdificioManager.obtenerNombreDivididoSubEd(cmtSubEdificioMgl, tipoSubEdificioList);
                        if (divDto != null) {
                            if (divDto.getNivel1() != null && !divDto.getNivel1().isEmpty()) {
                                if (divDto.getValorNivel1() != null && !divDto.getValorNivel1().isEmpty()) {
                                    drDireccion.setCpTipoNivel1(divDto.getNivel1());
                                    drDireccion.setCpValorNivel1(divDto.getValorNivel1());
                                }
                            }
                            if (divDto.getNivel2() != null && !divDto.getNivel2().isEmpty()) {
                                if (divDto.getValorNivel2() != null && !divDto.getValorNivel2().isEmpty()) {
                                    drDireccion.setCpTipoNivel2(divDto.getNivel2());
                                    drDireccion.setCpValorNivel2(divDto.getValorNivel2());
                                }
                            }
                            if (divDto.getNivel3() != null && !divDto.getNivel3().isEmpty()) {
                                if (divDto.getValorNivel3() != null && !divDto.getValorNivel3().isEmpty()) {
                                    drDireccion.setCpTipoNivel3(divDto.getNivel3());
                                    drDireccion.setCpValorNivel3(divDto.getValorNivel3());
                                }
                            }
                            if (divDto.getNivel4() != null && !divDto.getNivel4().isEmpty()) {
                                if (divDto.getValorNivel4() != null && !divDto.getValorNivel4().isEmpty()) {
                                    drDireccion.setCpTipoNivel4(divDto.getNivel4());
                                    drDireccion.setCpValorNivel4(divDto.getValorNivel4());
                                }
                            }
                        }

                        // para  Multiedifico carga DrDireccion de niveles 5 y 6
                        if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId().compareTo(cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) == 0) {
                            drDireccion.setCpTipoNivel5(nivel5);
                            drDireccion.setCpValorNivel5(valorNivel5);
                            if (nivel6 != null && !nivel6.trim().isEmpty()) {
                                drDireccion.setCpTipoNivel6(nivel6);
                                drDireccion.setCpValorNivel6(valorNivel6);
                            }

                            direccionEntity = drDireccion.convertToDetalleDireccionEntity();
                            direccionCrearGeo = direccionManager.getDireccion(drDireccion);

                        } else {

                            if (razon.contains(("CM"))) {
                                // Casos especiales otros e el apartamento y recortar a 10 caracteres 
                                if (cmtHhppVtMgl.getOpcionNivel5().equals(Constant.OPCION_NIVEL_OTROS)) {
                                    drDireccion.setCpTipoNivel5(Constant.OPCION_NIVEL_OTROS);
                                    drDireccion.setCpValorNivel5(valorNivel5.toUpperCase());
                                } else if (cmtHhppVtMgl.getIdrDireccionCasa() != null) {

                                    if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId().
                                            compareTo(cmtBasicaMglManager.
                                                    findByCodigoInternoApp(
                                                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) == 0) {
                                        drDireccion.setCpTipoNivel5(nivel5);
                                        drDireccion.setCpValorNivel5(null);
                                    } else {
                                        drDireccion.setCpTipoNivel5(nivel5);
                                        drDireccion.setCpValorNivel5(null);
                                        drDireccion.setCpTipoNivel1(null);
                                        drDireccion.setCpValorNivel1(null);
                                    }

                                } else {
                                    drDireccion.setCpTipoNivel5(nivel5);
                                    drDireccion.setCpValorNivel5(valorNivel5);
                                }
                            } else {
                                drDireccion.setCpTipoNivel5(nivel5);
                                drDireccion.setCpValorNivel5(valorNivel5);
                            }

                            if (nivel6 != null && !nivel6.trim().isEmpty()) {
                                drDireccion.setCpTipoNivel6(nivel6);
                                drDireccion.setCpValorNivel6(valorNivel6);
                            }

                            direccionEntity = drDireccion.convertToDetalleDireccionEntity();
                            direccionCrearGeo = direccionManager.getDireccion(drDireccion);
                        }
                    }
                }

                // sin direcciones 
            } else {
                // hhpp que vienen de la opcion creacion y modificacion de hhpp uniderreccional
                if (!razon.contains(("CM"))) {
                    cmtdireccionMgl = cmtSubEdificioMgl.getListDireccionesMgl().get(0);
                    drDireccion.obtenerFromCmtDireccionMgl(cmtdireccionMgl);
                    if (cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio() != null
                            && !cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio().isEmpty()) {
                        drDireccion.setBarrio(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio());
                    }

                    if (cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getBasicaId().
                            compareTo(cmtBasicaMglManager.
                                    findByCodigoInternoApp(
                                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) == 0) {
                        // obtener el nombre del subedificio para settear a drdireccion
                        String[] nombreSubEdificioSep = cmtSubEdificioMgl.getNombreSubedificio().split(" ");
                        StringBuilder nombreValor = new StringBuilder("");
                        for (int i = 0; i < nombreSubEdificioSep.length; i++) {
                            if (i != 0) {
                                nombreValor.append(nombreSubEdificioSep[i] + " ");
                            }
                        }
                        // colocar a drdireccion los niveles para conjunto de casa con su propia direccion
                        drDireccion.setCpTipoNivel1(nombreSubEdificioSep[0]);
                        drDireccion.setCpValorNivel1(nombreValor.toString());
                    }
                    drDireccion.setCpTipoNivel5(nivel5);
                    drDireccion.setCpValorNivel5(valorNivel5);
                    if (nivel6 != null && !nivel6.trim().isEmpty()) {
                        drDireccion.setCpTipoNivel6(nivel6);
                        drDireccion.setCpValorNivel6(valorNivel6);
                    }
                    if (nivel6 != null && !nivel6.trim().isEmpty()) {
                        drDireccion.setCpTipoNivel6(nivel6);
                        drDireccion.setCpValorNivel6(valorNivel6);
                    }
                } else {
                    if (cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(nivel) && (cmtHhppVtMgl.getValorNivel5() == null
                            || cmtHhppVtMgl.getValorNivel5().isEmpty())) {

                        drDireccion = direccionManager.findByIdSolicitud(cmtHhppVtMgl.getIdrDireccionCasa());
                        drDireccion.setBarrio(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio());
                        if (cmtHhppVtMgl != null) {
                            if (razon.contains(("CM"))) {
                                if (cmtHhppVtMgl.getOpcionNivel5().equals(Constant.OPCION_NIVEL_OTROS)) {
                                    drDireccion.setCpTipoNivel5(Constant.OPCION_NIVEL_OTROS);
                                    drDireccion.setCpValorNivel5(valorNivel5.toUpperCase());
                                } else if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel) && cmtHhppVtMgl.getValorNivel5() == null) {
                                    if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId().
                                            compareTo(cmtBasicaMglManager.
                                                    findByCodigoInternoApp(
                                                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) == 0) {
                                        drDireccion.setCpTipoNivel5(nivel5);
                                        drDireccion.setCpValorNivel5(null);
                                    } else {
                                        drDireccion.setCpTipoNivel5(nivel5);
                                        drDireccion.setCpValorNivel5(null);
                                        drDireccion.setCpTipoNivel1(null);
                                        drDireccion.setCpValorNivel1(null);
                                    }

                                } else {
                                    drDireccion.setCpTipoNivel5(nivel5);
                                    drDireccion.setCpValorNivel5(valorNivel5);
                                }
                            }
                        }

                        if (nivel6 != null && !nivel6.trim().isEmpty()) {
                            drDireccion.setCpTipoNivel6(nivel6);
                            drDireccion.setCpValorNivel6(valorNivel6);
                        }
                    } else {
                        cmtdireccionMgl = cmtDireccionMglManager.findByIdSubEdificio(cmtSubEdificioMgl.getSubEdificioId());
                        drDireccion.obtenerFromCmtDireccionMgl(cmtdireccionMgl);
                        //casa con su propia direccion, se establece el barrio por el Geo y no por la CM

                        // carga de DrDireccion niveles 5 y 6
                        if (cmtHhppVtMgl != null) {
                            if (razon.contains(("CM"))) {
                                if (cmtHhppVtMgl.getOpcionNivel5() != null && !cmtHhppVtMgl.getOpcionNivel5().isEmpty()
                                        && cmtHhppVtMgl.getOpcionNivel5().equals(Constant.OPCION_NIVEL_OTROS)) {
                                    drDireccion.setCpTipoNivel5(Constant.OPCION_NIVEL_OTROS);
                                    drDireccion.setCpValorNivel5(valorNivel5.toUpperCase());
                                } else if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel)
                                        && (cmtHhppVtMgl.getValorNivel5() == null || cmtHhppVtMgl.getValorNivel5().isEmpty())) {

                                    if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId().
                                            compareTo(cmtBasicaMglManager.
                                                    findByCodigoInternoApp(
                                                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) == 0) {
                                        drDireccion.setCpTipoNivel5(nivel5);
                                        drDireccion.setCpValorNivel5(cmtHhppVtMgl.getUnidadRr());
                                    } else {
                                        drDireccion.setCpTipoNivel5(nivel5);
                                        drDireccion.setCpValorNivel5(null);
                                        drDireccion.setCpTipoNivel1(null);
                                        drDireccion.setCpValorNivel1(null);
                                    }
                                } else {
                                    drDireccion.setCpTipoNivel5(nivel5);
                                    drDireccion.setCpValorNivel5(valorNivel5);
                                    if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId().
                                            compareTo(cmtBasicaMglManager.
                                                    findByCodigoInternoApp(
                                                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) == 0) {
                                        // obtener el nombre del subedificio para settear a drdireccion
                                        String[] nombreSubEdificioSep = cmtSubEdificioMgl.getNombreSubedificio().split(" ");
                                        String nombreValor = "";
                                        for (int i = 0; i < nombreSubEdificioSep.length; i++) {
                                            if (i != 0) {
                                                nombreValor += nombreSubEdificioSep[i] + " ";
                                            }
                                        }
                                        // colocar a drdireccion los niveles para conjunto de casa con su propia direccion
                                        if (cmtHhppVtMgl.getValorNivel5() == null && cmtHhppVtMgl.getOpcionNivel5().equals(nivel)) {
                                            drDireccion.setCpTipoNivel1(nombreSubEdificioSep[0]);
                                            drDireccion.setCpValorNivel1(nombreValor);
                                        } else {
                                            // colocar a drdireccion los niveles cuando no sea salaventas campamento ya que estos estan en la cm
                                            if (!cmtHhppVtMgl.getValorNivel5().equals("SALAVENTAS") || (!cmtHhppVtMgl.getValorNivel5().equals("CAMPAMENTO"))) {
                                                drDireccion.setCpTipoNivel1(nombreSubEdificioSep[0]);
                                                drDireccion.setCpValorNivel1(nombreValor);
                                            }
                                        }
                                    }

                                }
                            }
                        }

                        if (nivel6 != null && !nivel6.trim().isEmpty()) {
                            drDireccion.setCpTipoNivel6(nivel6);
                            drDireccion.setCpValorNivel6(valorNivel6);
                        }

                    }
                }

                direccionCrearGeo = direccionManager.getDireccion(drDireccion);
                direccionEntity = drDireccion.convertToDetalleDireccionEntity();
            }

            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setLevel("C");
            addressRequest.setState(cmtSubEdificioMgl.getCuentaMatrizObj().getDepartamento().getGpoNombre());
            addressRequest.setCity(cmtSubEdificioMgl.getCuentaMatrizObj().getCentroPoblado().getGpoNombre());
            addressRequest.setCodDaneVt(cmtSubEdificioMgl.getCuentaMatrizObj().getCentroPoblado().getGeoCodigoDane());
            if (razon.contains(("CM"))) {
                if (cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(nivel) && (cmtHhppVtMgl.getValorNivel5() != null)) {

                    if (cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio() != null
                            && !cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio().isEmpty()) {
                        drDireccion.setBarrio(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio());
                    }
                }
            }

            if (cmtSubEdificioMgl.getCuentaMatrizObj().getCentroPoblado().getGpoMultiorigen().equalsIgnoreCase("1")
                    && drDireccion.getBarrio() != null) {
                addressRequest.setNeighborhood(drDireccion.getBarrio());
            } else {
                if (drDireccion.getBarrio() == null
                        || drDireccion.getBarrio().isEmpty()) {
                    drDireccion.setBarrio("-");
                    addressRequest.setNeighborhood(drDireccion.getBarrio());
                } else {
                    addressRequest.setNeighborhood(drDireccion.getBarrio());
                }

            }
            direccionEntity.setBarrio(drDireccion.getBarrio());
            String idCatastroDireccion = null;
            addressRequest.setAddress(direccionCrearGeo);

            ResponseMessage responseMessage = addressEJB.createAddress(addressRequest, usuario, "MGL-CM", "true", drDireccion);
            List<HhppMgl> listaHhpp = null;
            if (responseMessage.getMessageType() != null && responseMessage.getMessageType().equalsIgnoreCase("ERROR")) {
                if (responseMessage.getIdaddress() != null && !responseMessage.getIdaddress().isEmpty()) {
                    if (responseMessage.getIdaddress().toUpperCase().contains("D")
                            || responseMessage.getIdaddress().toUpperCase().contains("S")) {
                        idCatastroDireccion = responseMessage.getIdaddress();//DIRECCION ENCONTRADA EN MGL
                        // si el createAddress responde Error, el tipo de direccion S o D y el idCatastro
                        // se busca en  MGL si el hhpp Existe con esa direccion
                        if (idCatastroDireccion != null) {
                            HhppMglManager hhppMglManager = new HhppMglManager();
                            listaHhpp = hhppMglManager.findHhppBySubDirId(new BigDecimal(idCatastroDireccion.substring(1, idCatastroDireccion.length())));
                        } else {
                            responseCreateHhppDto.setCreacionExitosa(false);
                            return responseCreateHhppDto;
                        }
                        // se valida si el campo idHHPPRR es null se envia a crear el hHPP 
                        // de lo contrario no se crea el HHPP
                        if (listaHhpp != null && !listaHhpp.isEmpty()) {
                            if (listaHhpp.get(0).getHhpIdrR() == null) {
                                // direccion existente pero sin hhpp asociado 
                                direccionRREntity = guardarHHPPRR_MGL(idCatastroDireccion, cmtHhppVtMgl, valorNivel5, direccionEntity, cmtSubEdificioMgl, tecnologiaSub,
                                        perfil, usuario, solicitudId, solicitudTipoId, observaciones, razon, drDireccion);
                                if (direccionRREntity.isResptRegistroHHPPRR()) {
                                    responseCreateHhppDto.setCreacionExitosa(true);
                                    return responseCreateHhppDto;
                                } else {
                                    responseCreateHhppDto.setCreacionExitosa(false);
                                    ArrayList<String> mensaje = new ArrayList<>();
                                    mensaje.add(direccionRREntity.getMensaje());
                                    responseCreateHhppDto.setValidationMessages(mensaje);
                                    return responseCreateHhppDto;
                                }

                            } else {
                                direccionRREntity = guardarHHPPRR_MGL(idCatastroDireccion, cmtHhppVtMgl, valorNivel5, direccionEntity, cmtSubEdificioMgl, tecnologiaSub,
                                        perfil, usuario, solicitudId, solicitudTipoId, observaciones, razon, drDireccion);
                                if (direccionRREntity.isResptRegistroHHPPRR()) {
                                    responseCreateHhppDto.setCreacionExitosa(true);
                                    ArrayList<String> mensaje = new ArrayList<>();
                                    mensaje.add(direccionRREntity.getMensaje());
                                    responseCreateHhppDto.setValidationMessages(mensaje);
                                    return responseCreateHhppDto;
                                }
                            }
                        } else {
                            direccionRREntity = guardarHHPPRR_MGL(idCatastroDireccion, cmtHhppVtMgl, valorNivel5, direccionEntity, cmtSubEdificioMgl, tecnologiaSub,
                                    perfil, usuario, solicitudId, solicitudTipoId, observaciones, razon, drDireccion);
                            if (direccionRREntity.isResptRegistroHHPPRR()) {
                                responseCreateHhppDto.setCreacionExitosa(true);
                                ArrayList<String> mensaje = new ArrayList<>();
                                mensaje.add(direccionRREntity.getMensaje());
                                responseCreateHhppDto.setValidationMessages(mensaje);
                                return responseCreateHhppDto;
                            } else {
                                responseCreateHhppDto.setCreacionExitosa(false);
                                ArrayList<String> mensaje = new ArrayList<>();
                                mensaje.add(direccionRREntity.getMensaje());
                                responseCreateHhppDto.setValidationMessages(mensaje);
                                return responseCreateHhppDto;
                            }
                        }

                    }
                } else {
                    direccionRREntity = guardarHHPPRR_MGL(idCatastroDireccion, cmtHhppVtMgl, valorNivel5, direccionEntity, cmtSubEdificioMgl, tecnologiaSub,
                            perfil, usuario, solicitudId, solicitudTipoId, observaciones, razon, drDireccion);
                    if (direccionRREntity.isResptRegistroHHPPRR()) {
                        responseCreateHhppDto.setCreacionExitosa(true);
                        ArrayList<String> mensaje = new ArrayList<>();
                        mensaje.add(direccionRREntity.getMensaje());
                        responseCreateHhppDto.setValidationMessages(mensaje);
                        return responseCreateHhppDto;
                    }
                }
                //cuando el createAddress no genera error 
            } else if (responseMessage.getIdaddress().toUpperCase().contains("D")
                    || responseMessage.getIdaddress().toUpperCase().contains("S")) {
                idCatastroDireccion = responseMessage.getIdaddress();//NO EXISTE DIRECCION

                // se busca en  MGL si el hhpp Existe con esa direccion
                if (idCatastroDireccion != null) {
                    HhppMglManager hhppMglManager = new HhppMglManager();
                    listaHhpp = hhppMglManager.findHhppBySubDirId(new BigDecimal(idCatastroDireccion.substring(1, idCatastroDireccion.length())));
                } else {
                    responseCreateHhppDto.setCreacionExitosa(false);
                    return responseCreateHhppDto;
                }
                if (listaHhpp != null && !listaHhpp.isEmpty()) {
                    if (listaHhpp.get(0).getHhpIdrR() == null) {
                        // direccion existente pero sin hhpp asociado 
                        direccionRREntity = guardarHHPPRR_MGL(idCatastroDireccion, cmtHhppVtMgl, valorNivel5, direccionEntity,
                                cmtSubEdificioMgl, tecnologiaSub,
                                perfil, usuario, solicitudId, solicitudTipoId, observaciones, razon, drDireccion);
                        if (direccionRREntity.isResptRegistroHHPPRR()) {
                            responseCreateHhppDto.setCreacionExitosa(true);
                            return responseCreateHhppDto;
                        } else {
                            responseCreateHhppDto.setCreacionExitosa(false);
                            ArrayList<String> mensaje = new ArrayList<>();
                            mensaje.add(direccionRREntity.getMensaje());
                            responseCreateHhppDto.setValidationMessages(mensaje);
                            return responseCreateHhppDto;
                        }

                    } else {
                        // retorna false porque ya existe un hhpp asociado a esa direccion
                        responseCreateHhppDto.setCreacionExitosa(false);
                        return responseCreateHhppDto;
                    }
                } else {
                    // se crea el hhpp por que no existe aun cmtHhppVtMgl
                    direccionRREntity = guardarHHPPRR_MGL(idCatastroDireccion, cmtHhppVtMgl, valorNivel5, direccionEntity, cmtSubEdificioMgl, tecnologiaSub,
                            perfil, usuario, solicitudId, solicitudTipoId, observaciones, razon, drDireccion);
                    if (direccionRREntity.isResptRegistroHHPPRR()) {
                        responseCreateHhppDto.setCreacionExitosa(true);
                        ArrayList<String> mensaje = new ArrayList<>();
                        mensaje.add(direccionRREntity.getMensaje());
                        responseCreateHhppDto.setValidationMessages(mensaje);
                        return responseCreateHhppDto;
                    } else {
                        responseCreateHhppDto.setCreacionExitosa(false);
                        ArrayList<String> mensaje = new ArrayList<>();
                        mensaje.add(direccionRREntity.getMensaje());
                        responseCreateHhppDto.setValidationMessages(mensaje);
                        return responseCreateHhppDto;
                    }
                }
            }
        } catch (ApplicationException | ExceptionDB | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
            throw new ApplicationException(ex.getMessage(), ex);

        }

        return responseCreateHhppDto;

    }

    public DireccionRREntity guardarHHPPRR_MGL(String idCatastroDireccion, CmtHhppVtMgl cmtHhppVtMgl, String valorNivel5,
            DetalleDireccionEntity direccionEntity, CmtSubEdificioMgl cmtSubEdificioMgl, CmtTecnologiaSubMgl tecnologiaSub,
            int perfil, String usuario, String solicitudId, String solicitudTipoId, String observaciones,
            String razon, DrDireccion drDireccion)
            throws ApplicationException {
        DireccionRREntity direccionRREntity = new DireccionRREntity();
        direccionEntity.setIdDirCatastro(idCatastroDireccion);
        direccionEntity.setMultiOrigen(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoMultiorigen());
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        ParametrosMgl parametrosMgl = parametrosMglManager.findByAcronimo(Constant.NIVELES_CASAS_CON_DIRECCION).get(0);
        String nivel = "";
        if (parametrosMgl != null) {
            nivel = parametrosMgl.getParValor();
        }
        if (!nivel.equals("")) {
            List<String> niveles = Arrays.asList(nivel.split("\\|"));
            if (cmtHhppVtMgl.getOpcionNivel5() != null && !cmtHhppVtMgl.getOpcionNivel5().isEmpty()) {
                if (niveles.contains(cmtHhppVtMgl.getOpcionNivel5())) {
                    nivel = cmtHhppVtMgl.getOpcionNivel5();
                }
            }
        }
        if (razon.contains("CM")) {
            if (cmtHhppVtMgl.getOpcionNivel5().equals(Constant.OPCION_NIVEL_OTROS)) {
                if (direccionEntity.getCpvalornivel5().length() > 10) {
                    direccionEntity.setCptiponivel5(Constant.OPCION_NIVEL_OTROS);
                    direccionEntity.setCpvalornivel5(cmtHhppVtMgl.getNombreValidoRR());
                } else {
                    direccionEntity.setCptiponivel5(Constant.OPCION_NIVEL_OTROS);
                    direccionEntity.setCpvalornivel5(valorNivel5);
                }
            }
        } else {
            direccionEntity.setCptiponivel5(direccionEntity.getCptiponivel5());
            direccionEntity.setCpvalornivel5(valorNivel5);
        }

        String nodo = null;
        DireccionRRManager direccionRRManager;
        direccionRRManager = new DireccionRRManager(direccionEntity);

        if (tecnologiaSub != null && tecnologiaSub.getNodoId() != null) {
            nodo = tecnologiaSub.getNodoId().getNodCodigo();
        } else if (cmtHhppVtMgl != null && cmtHhppVtMgl.getSubEdificioVtObj() != null
                && cmtHhppVtMgl.getSubEdificioVtObj().getNodo() != null
                && cmtHhppVtMgl.getSubEdificioVtObj().getNodo().getNodCodigo() != null) {
            nodo = cmtHhppVtMgl.getSubEdificioVtObj().getNodo().getNodCodigo();

        } else {
            nodo = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getNodoObj().getNodCodigo();
        }

        String codCiudad = cmtSubEdificioMgl.getCuentaMatrizObj().getComunidad();
        String carpeta = Constant.CARPETA_CUENTA_MATRIZ;

        //Validamos si el nodo es NFI buscamos la cobertura entregada por el geo
        if (nodo.toUpperCase().contains("NFI")
                && idCatastroDireccion != null
                && !idCatastroDireccion.trim().isEmpty()) {
            BigDecimal dirId = new BigDecimal(idCatastroDireccion.substring(1));
            boolean isDireccion = idCatastroDireccion.toUpperCase().startsWith("D");
            boolean isBidireccional = carpeta.equalsIgnoreCase(Constant.CARPETA_BIDIRECCIONAL);
            DireccionMgl direccionMgl = null;
            SubDireccionMgl subDireccionMgl = null;

            SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
            DireccionMglManager direccionMglManager = new DireccionMglManager();

            if (isDireccion) {
                direccionMgl = new DireccionMgl();
                direccionMgl.setDirId(dirId);
                direccionMgl = direccionMglManager.findById(direccionMgl);
            } else {
                subDireccionMgl = subDireccionMglManager.findById(dirId);
            }
            if (isBidireccional) {
                if (isDireccion) {
                    if (direccionMgl != null
                            && direccionMgl.getDirNodouno() != null
                            && !direccionMgl.getDirNodouno().trim().isEmpty()) {
                        nodo = direccionMgl.getDirNodouno();
                    }
                } else if (subDireccionMgl != null
                        && subDireccionMgl.getSdiNodouno() != null
                        && !subDireccionMgl.getSdiNodouno().trim().isEmpty()) {
                    nodo = subDireccionMgl.getSdiNodouno();
                }
            } else if (isDireccion) {
                if (direccionMgl != null) {
                    if (direccionMgl.getDirNododos() != null
                            && !direccionMgl.getDirNododos().trim().isEmpty()) {
                        nodo = direccionMgl.getDirNododos();
                    } else if (direccionMgl.getDirNodotres() != null
                            && !direccionMgl.getDirNodotres().trim().isEmpty()) {
                        nodo = direccionMgl.getDirNodotres();
                    }
                }
            } else if (subDireccionMgl != null) {
                if (subDireccionMgl.getSdiNododos() != null
                        && !subDireccionMgl.getSdiNododos().trim().isEmpty()) {
                    nodo = subDireccionMgl.getSdiNododos();
                } else if (subDireccionMgl.getSdiNodotres() != null
                        && !subDireccionMgl.getSdiNodotres().trim().isEmpty()) {
                    nodo = subDireccionMgl.getSdiNodotres();
                }
            }
        }
        // @CARDENASLB
        // Ajuste estrato NR para niveles LOCAL, BODEGA, OFICINA
        String estrato = "";
        if (direccionEntity.getCptiponivel5().equalsIgnoreCase("LOCAL")
                || direccionEntity.getCptiponivel5().equalsIgnoreCase("BODEGA")
                || direccionEntity.getCptiponivel5().equalsIgnoreCase("OFICINA")
                || direccionEntity.getCptiponivel5().equalsIgnoreCase("PISO + LOCAL")
                || direccionEntity.getCptiponivel5().equalsIgnoreCase("PISO + CAJERO")) {
            estrato = "NR";
        } else {
            if (cmtSubEdificioMgl.getEstrato() != null) {
                estrato = cmtSubEdificioMgl.getEstrato().getNombreBasica().substring(8).trim();
                estrato = estrato.equals("0") ? "NG" : estrato;
            } else {
                estrato = "NG";
            }
        }

        //TODO: obtener Informacion direccion antigua.
        if (cmtSubEdificioMgl.getListDireccionesMgl() == null
                || cmtSubEdificioMgl.getListDireccionesMgl().isEmpty()) {

            // escenario normal creacion de hhpp por solicitud de modificacion 
            if (!razon.contains(("CM"))) {
                direccionRREntity = direccionRRManager.registrarHHPP(nodo,
                        nodo, usuario, carpeta, "MGL-CM",
                        estrato, false, solicitudId, solicitudTipoId,
                        codCiudad, observaciones, usuario, razon, "", "", "", "", cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoId(), cmtHhppVtMgl);
            } else // Torres sin direcciones en la entrada pero con subedificios con su propia direccion
            if (cmtHhppVtMgl != null && cmtHhppVtMgl.getCalleRr() != null && cmtHhppVtMgl.getUnidadRr() != null) {

                if (cmtHhppVtMgl.getSubEdificioVtObj().getTipoNivel1().getNombreBasica().equals(cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_TORRE).getNombreBasica())) {
                    // casas con su propia direccion
                    if (cmtHhppVtMgl.getSubEdificioVtObj().getSubEdificioObj().getCmtCuentaMatrizMglObj().isUnicoSubEdificioBoolean()) {
                        String calle = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                        String unidad = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                        direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal(), nodo,
                                nodo, usuario, carpeta, "MGL-CM",
                                estrato, false, solicitudId, solicitudTipoId,
                                codCiudad, observaciones, usuario, razon, "", "", "", "", calle, unidad, cmtHhppVtMgl);

                    } else {
                        // casa con su propia direccion y sin direccion en la entrada
                        String calle = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                        String unidad = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                        direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal(), nodo,
                                nodo, usuario, carpeta, "MGL-CM",
                                estrato, false, solicitudId, solicitudTipoId,
                                codCiudad, observaciones, usuario, razon, "", "", "", "", calle, unidad, cmtHhppVtMgl);
                    }

                } else if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel) && cmtHhppVtMgl.getOpcionNivel5() != null) {

                    // Conjunto de casas con su propia direccion
                    // calle de la cuenta matriz, unidad va la unidad de la cuenta matriz, y el apartamento ira la placa de 
                    // de la direccion de la casa 
                    if (cmtSubEdificioMgl.getListDireccionesMgl() == null || cmtSubEdificioMgl.getListDireccionesMgl().isEmpty()) {
                        String calle;
                        if (cmtHhppVtMgl.getSubEdificioVtObj() != null) {
                            calle = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr() + " " + cmtHhppVtMgl.getSubEdificioVtObj().getNombramientoRr();
                        } else {
                            calle = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                        }
                        String unidadCasa = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                        String tipoSubEdificio = drDireccion.getCpTipoNivel1();
                        direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal(), nodo,
                                nodo, usuario, carpeta, "MGL-CM",
                                estrato, false, solicitudId, solicitudTipoId,
                                codCiudad, observaciones, usuario, razon, "", "", "", tipoSubEdificio, calle, unidadCasa, cmtHhppVtMgl);
                    } else {
                        // Conjunto de casas con  direccion en la entrada y con direccion propia
                        String calle = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr() + " " + "EN" + " " + cmtHhppVtMgl.getSubEdificioVtObj().getCalleRr() + " " + cmtHhppVtMgl.getSubEdificioVtObj().getUnidadRr();
                        String unidadCasa = cmtHhppVtMgl.getUnidadRr();
                        direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal(), nodo,
                                nodo, usuario, carpeta, "MGL-CM",
                                estrato, false, solicitudId, solicitudTipoId,
                                codCiudad, observaciones, usuario, razon, "", "", "", drDireccion.getCpTipoNivel1(), calle, unidadCasa, cmtHhppVtMgl);
                    }
                } else {

                    // escenario de batallon es sin direccion en el subedificio
                    // validar que la direccion del subedificio sea null
                    String calle = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                    String unidad = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                    direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal(), nodo,
                            nodo, usuario, carpeta, "MGL-CM",
                            estrato, false, solicitudId, solicitudTipoId,
                            codCiudad, observaciones, usuario, razon, "", "", "", "", calle, unidad, cmtHhppVtMgl);
                }
            } else {
                // escenarios otros apartamento con solo 10 digitos hacia RR
                if (cmtHhppVtMgl != null && cmtHhppVtMgl.getNombreValidoRR() != null) {
                    String calle = null;
                    String unidad = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                    direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl,
                            cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal(), nodo,
                            nodo, usuario, carpeta, "MGL-CM",
                            estrato, false, solicitudId, solicitudTipoId,
                            codCiudad, observaciones, usuario, razon, "", "", "", valorNivel5, calle, unidad, cmtHhppVtMgl);
                } else {
                    // escenario normal hhpp automaticos
                    direccionRREntity = direccionRRManager.registrarHHPP(nodo,
                            nodo, usuario, carpeta, "MGL-CM",
                            estrato, false, solicitudId, solicitudTipoId,
                            codCiudad, observaciones, usuario, razon, "", "", "", "", cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoId(), cmtHhppVtMgl);
                }

            }

        } else {
            // obetener direcciones del subedificio

            if (!razon.contains(("CM"))) {
                //creacion de hhpp  por solicitude de hhpp unidireccionales 
                for (CmtDireccionMgl direccion : cmtSubEdificioMgl.getListDireccionesMgl()) {
                    if (direccion.getTdiId() == Constant.ID_TIPO_DIRECCION_PRINCIPAL) {
                        direccionRREntity = direccionRRManager.registrarHHPP(nodo,
                                nodo, usuario, carpeta, "MGL-CM",
                                estrato, false, solicitudId, solicitudTipoId,
                                codCiudad, observaciones, usuario, razon, "", "", "", "", cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoId(), cmtHhppVtMgl);
                    }
                }
            } else {
                // edificios con direccion en la entrada
                for (CmtDireccionMgl direccion : cmtSubEdificioMgl.getListDireccionesMgl()) {
                    if (direccion.getTdiId() == Constant.ID_TIPO_DIRECCION_SUBEDIFICIO) {
                        if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel) && cmtHhppVtMgl.getOpcionNivel5() == null) {

                            // casas sin direccion en la entrada y con su propia direccion
                            if (cmtSubEdificioMgl.getListDireccionesMgl() == null || cmtSubEdificioMgl.getListDireccionesMgl().isEmpty()) {
                                String calle = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                                String unidadCasa = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                                direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, direccion, nodo,
                                        nodo, usuario, carpeta, "MGL-CM",
                                        estrato, false, solicitudId, solicitudTipoId,
                                        codCiudad, observaciones, usuario, razon, "", "", "", "", calle, unidadCasa, cmtHhppVtMgl);
                            } else {
                                // casas con direccion en la entrada y con su propia direccion
                                String calle = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr() + " " + "EN" + " " + cmtHhppVtMgl.getSubEdificioVtObj().getCalleRr() + " " + cmtHhppVtMgl.getSubEdificioVtObj().getUnidadRr();
                                String unidadCasa = cmtHhppVtMgl.getOpcionNivel5();
                                direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, direccion, nodo,
                                        nodo, usuario, carpeta, "MGL-CM",
                                        estrato, false, solicitudId, solicitudTipoId,
                                        codCiudad, observaciones, usuario, razon, "", "", "", "", calle, unidadCasa, cmtHhppVtMgl);
                            }
                        } else if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel) && cmtHhppVtMgl.getOpcionNivel5() == null) {
                            // batallones sin direccion
                            if (cmtSubEdificioMgl.getListDireccionesMgl() == null || cmtSubEdificioMgl.getListDireccionesMgl().isEmpty()) {
                                String calle = cmtHhppVtMgl.getSubEdificioVtObj().getCalleRr();
                                String unidad = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                                direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, direccion, nodo,
                                        nodo, usuario, carpeta, "MGL-CM",
                                        estrato, false, solicitudId, solicitudTipoId,
                                        codCiudad, observaciones, usuario, razon, "", "", "", "", calle, unidad, cmtHhppVtMgl);
                            } else {
                                String calle;
                                String unidad;
                                String calleSinEnt = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                                String ent = "EN" + " " + cmtHhppVtMgl.getSubEdificioVtObj().getCalleRr() + " " + cmtHhppVtMgl.getSubEdificioVtObj().getUnidadRr();
                                calle = calleSinEnt + " " + ent;
                                unidad = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                                direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, direccion, nodo,
                                        nodo, usuario, carpeta, "MGL-CM",
                                        estrato, false, solicitudId, solicitudTipoId,
                                        codCiudad, observaciones, usuario, razon, "", "", "", "", calle, unidad, cmtHhppVtMgl);
                            }

                        } else {
                            // torres sin  direccion en la entrada 
                            if (cmtSubEdificioMgl.getListDireccionesMgl() == null || cmtSubEdificioMgl.getListDireccionesMgl().isEmpty()) {
                                String calle = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                                String unidad = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                                direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, direccion, nodo,
                                        nodo, usuario, carpeta, "MGL-CM",
                                        estrato, false, solicitudId, solicitudTipoId,
                                        codCiudad, observaciones, usuario, razon, "", "", "", "", calle, unidad, cmtHhppVtMgl);
                            } else {
                                //  subedificios con direccion en la entrada, casa y escenarios otros
                                // torres con direccion en la entrada OK

                                direccionRREntity = direccionRRManager.registrarHHPPSubPropiaDireccion(cmtSubEdificioMgl, direccion, nodo,
                                        nodo, usuario, carpeta, "MGL-CM",
                                        estrato, false, solicitudId, solicitudTipoId,
                                        codCiudad, observaciones, usuario, razon, "", "", "", "", "", "", cmtHhppVtMgl);
                            }
                        }

                    } else {
                        direccionRREntity = direccionRRManager.registrarHHPP(nodo,
                                nodo, usuario, carpeta, "MGL-CM",
                                estrato, false, solicitudId, solicitudTipoId,
                                codCiudad, observaciones, usuario, razon, "", "", "", "", cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoId(), cmtHhppVtMgl);
                    }
                }
            }

        }
        if (direccionRRManager.getDireccion().getIdHhpp() != null) {
            BigDecimal idSaveHhpp = new BigDecimal(direccionRRManager.getDireccion().getIdHhpp());
            HhppMglManager hhppMglManager = new HhppMglManager();
            HhppMgl hhppMglSave = hhppMglManager.findById(idSaveHhpp);
            if (!razon.contains(("CM"))) {
                hhppMglSave.setHhpCalle(direccionRREntity.getCalle());
                hhppMglSave.setHhpPlaca(direccionRREntity.getNumeroUnidad());
                hhppMglSave.setHhpApart(direccionRREntity.getNumeroApartamento());
            } else {
                if (cmtHhppVtMgl != null && cmtHhppVtMgl.getIdrDireccionCasa() != null) {
                    if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel) && cmtHhppVtMgl.getValorNivel5() == null) {
                        hhppMglSave.setHhpCalle(direccionRREntity.getCalle());
                    } else {
                        hhppMglSave.setHhpCalle(cmtHhppVtMgl.getCalleRr());
                    }

                    hhppMglSave.setHhpPlaca(cmtHhppVtMgl.getUnidadRr());
                    hhppMglSave.setHhpApart(cmtHhppVtMgl.getOpcionNivel5());
                } else if (cmtHhppVtMgl != null && cmtHhppVtMgl.getOpcionNivel5().equals(Constant.OPCION_NIVEL_OTROS)) {
                    hhppMglSave.setHhpApart(cmtHhppVtMgl.getValorNivel5());
                } else {
                    hhppMglSave.setHhpCalle(direccionRREntity.getCalle());
                    hhppMglSave.setHhpPlaca(direccionRREntity.getNumeroUnidad());
                    hhppMglSave.setHhpApart(direccionRREntity.getNumeroApartamento());
                }

            }

            hhppMglSave.setHhppSubEdificioObj(cmtSubEdificioMgl);
            hhppMglSave.setCmtTecnologiaSubId(tecnologiaSub);
            hhppMglManager.updateHhppMgl(hhppMglSave, usuario, perfil);
            BigDecimal estratoDireccion;
            if (estrato.equalsIgnoreCase("NR")) {
                estratoDireccion = new BigDecimal("0");
            } else if (estrato.equalsIgnoreCase("NG")) {
                estratoDireccion = new BigDecimal("-1");
            } else {
                if (estrato.isEmpty() || estrato.equalsIgnoreCase("")) {
                    estratoDireccion = new BigDecimal("-1");
                } else {
                    estratoDireccion = new BigDecimal(estrato);
                }

            }

            if (hhppMglSave.getSubDireccionObj() != null) {
                SubDireccionMglManager subDireccionMglManager1 = new SubDireccionMglManager();
                hhppMglSave.getSubDireccionObj().setSdiEstrato(estratoDireccion);
                subDireccionMglManager1.update(hhppMglSave.getSubDireccionObj());
            } else {
                DireccionMglManager direccionMglManager1 = new DireccionMglManager();
                hhppMglSave.getDireccionObj().setDirEstrato(estratoDireccion);
                direccionMglManager1.update(hhppMglSave.getDireccionObj());
            }
            direccionRREntity.setResptRegistroHHPPRR(true);
            direccionRREntity.setMensaje("Registro exitoso");
        } else {
            direccionRREntity.setResptRegistroHHPPRR(false);
        }
        return direccionRREntity;

    }

    /**
     * Cambiar el estado de un HHPP. Permite realizar el cambio de estado de
     * hhpp teniendo en cuenta reglas para los cambios de estado
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param estadoHhpp objeto estado hhpp para realizar el cambio
     *
     * @return Retorna el objeto estado hhpp que quedo asignado al hhpp
     * (CAB0065) Solamente cambios entre "N", "P", "D", y "E" son permitidos.
     */
    public CmtDefaultBasicResponse cambioEstadoHhpp(HhppMgl hhppMgl,
            CmtEstadoHhppDto estadoHhpp) {
        CmtDefaultBasicResponse response = new CmtDefaultBasicResponse();
        HhppMgl hhppBuscado;
        boolean actualizar = false;
        String estado = "";
        String usuario = "";
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();

        try {
            if (estadoHhpp != null) {

                if (estadoHhpp.getUser() == null || estadoHhpp.getUser().isEmpty()) {
                    response.setMessageType("E");
                    response.setMessage("Debe ingresar el usuario.");
                    return response;
                } else {
                    usuario = estadoHhpp.getUser();
                }

                if (estadoHhpp.getSourceAplication() == null || estadoHhpp.getSourceAplication().isEmpty()) {
                    response.setMessageType("E");
                    response.setMessage("Debe ingresar el SourceAplication. (TCRM)");
                    return response;
                }

                if (estadoHhpp.getDestinationAplication() == null || estadoHhpp.getDestinationAplication().isEmpty()) {
                    response.setMessageType("E");
                    response.setMessage("Debe ingresar el DestinationAplication. (MGL)");
                    return response;
                }

                if (estadoHhpp.getIdHhppRequest() == null || estadoHhpp.getIdHhppRequest().equals(BigDecimal.ZERO)) {
                    response.setMessageType("E");
                    response.setMessage("Debe ingresar el idHhppRequest que desea cambiar de estado");
                    return response;
                }

                if (estadoHhpp.getIdCasoTcrmRequest() == null || estadoHhpp.getIdCasoTcrmRequest().equals(BigDecimal.ZERO)) {
                    response.setMessageType("E");
                    response.setMessage("Debe ingresar el IdCasoTcrmRequest.");
                    return response;
                }

                if (estadoHhpp.getEstadoHhpp() == null) {
                    response.setMessageType("E");
                    response.setMessage("Debe ingresar el EstadoHhpp.");
                    return response;
                } else {
                    if (estadoHhpp.getEstadoHhpp().getEhhID() == null
                            || estadoHhpp.getEstadoHhpp().getEhhID().isEmpty()) {
                        response.setMessageType("E");
                        response.setMessage("Debe ingresar el EhhId en EstadoHhpp");
                        return response;
                    }

                    EstadoHhppMglManager manager = new EstadoHhppMglManager();
                    List<EstadoHhppMgl> estadoHhppList = manager.findAll();

                    if (estadoHhppList != null && !estadoHhppList.isEmpty()) {
                        boolean estadoExistente = false;
                        for (EstadoHhppMgl estadoHhppMgl : estadoHhppList) {
                            if (estadoHhppMgl.getEhhID().equalsIgnoreCase(estadoHhpp.getEstadoHhpp().getEhhID())) {
                                estadoExistente = true;
                                break;
                            }
                        }

                        if (!estadoExistente) {
                            response.setMessageType("E");
                            response.setMessage("Debe ingresar un estado valido de 2 caracteres: PO=Potencial, PR=Precableado, CS=Con servicio, SU=Suspendido, "
                                    + "DF=Desconexion Fisica, DE=desconectado.");
                            return response;
                        }
                    }
                }

                if (hhppMgl != null && hhppMgl.getHhpId() != BigDecimal.ZERO && estadoHhpp != null) {

                    if (usuario != null && !usuario.isEmpty()) {

                        hhppBuscado = hMhppglDaoImpl.findById(hhppMgl.getHhpId());

                        if (hhppBuscado != null) {

                            EstadoHhppMgl estadoBuscado = estadoHhppMglManager.find(estadoHhpp.getEstadoHhpp().getEhhID());

                            if (estadoBuscado != null) {
                                if (validarFlujoEstado(hhppBuscado, hhppBuscado.getEhhId(), estadoHhpp.getEstadoHhpp())) {

                                    if (estadoHhpp.getEstadoHhpp().getEhhID().equalsIgnoreCase("PR") || estadoHhpp.getEstadoHhpp().getEhhID().equalsIgnoreCase("PO")) {

                                        hhppBuscado.setEhhId(estadoBuscado);
                                        hhppBuscado.setHhpEstadoUnit(estadoBuscado.getEhhID());
                                        hhppBuscado.setSuscriptor("");
                                        hhppBuscado.setFechaEdicion(new Date());
                                        hMhppglDaoImpl.update(hhppBuscado);
                                        CmtBasicaMgl estadoHhppMgl = null;
                                        String codigoRr = "";
                                        EstadoHhppMgl estadoRr = new EstadoHhppMgl();

                                        if (estadoHhpp.getEstadoHhpp().getEhhID().equalsIgnoreCase("PR")) {
                                            //Homologacion
                                            estadoHhppMgl = cmtBasicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_PRECABLEADO);

                                            if (estadoHhppMgl != null) {
                                                codigoRr = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppMgl);

                                                ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                                                boolean habilitarRR = false;
                                                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                                    habilitarRR = true;
                                                }
                                                //Se pregunta si el hhpp tiene ID RR para ir actualizar el hhpp allá.
                                                if (!(hhppBuscado != null && hhppBuscado.getHhpIdrR() != null && !hhppBuscado.getHhpIdrR().isEmpty())) {
                                                    habilitarRR = false;
                                                }

                                                if (habilitarRR) {
                                                    DireccionRRManager drrm = new DireccionRRManager(true);
                                                    String estadoMer = estadoHhpp.getEstadoHhpp().getEhhID();
                                                    drrm.cambioEstadoHhppRR(hhppBuscado, codigoRr, estadoMer, usuario);
                                                    response.setMessageType("I");
                                                    response.setMessage("Se modifico el estado del HHPP, "
                                                            + "el nuevo estado en MGL es:  "
                                                            + estadoHhpp.getEstadoHhpp().getEhhID() + ", en RR: " + estadoRr.getEhhID() + "");
                                                }
                                            }
                                        } else if (estadoHhpp.getEstadoHhpp().getEhhID().equalsIgnoreCase("DE")) {
                                            //Homologacion
                                            estadoHhppMgl = cmtBasicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_DESCONECTADO);

                                            if (estadoHhppMgl != null) {
                                                codigoRr = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppMgl);
                                                estadoRr.setEhhID(codigoRr);

                                                ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                                                boolean habilitarRR = false;
                                                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                                    habilitarRR = true;
                                                }
                                                //Se pregunta si el hhpp tiene ID RR para ir actualizar el hhpp allá.
                                                if (!(hhppBuscado != null && hhppBuscado.getHhpIdrR() != null && !hhppBuscado.getHhpIdrR().isEmpty())) {
                                                    habilitarRR = false;
                                                }
                                                if (habilitarRR) {
                                                    DireccionRRManager drrm = new DireccionRRManager(true);
                                                    String estadoMer = estadoHhpp.getEstadoHhpp().getEhhID();
                                                    drrm.cambioEstadoHhppRR(hhppBuscado, codigoRr, estadoMer, usuario);
                                                    response.setMessageType("I");
                                                    response.setMessage("Se modifico el estado del HHPP, "
                                                            + "el nuevo estado en MGL es:  "
                                                            + estadoHhpp.getEstadoHhpp().getEhhID() + ", en RR: " + estadoRr.getEhhID() + "");
                                                }
                                            }
                                        } else if (estadoHhpp.getEstadoHhpp().getEhhID().equalsIgnoreCase("PO")) {
                                            //Homologacion
                                            estadoHhppMgl = cmtBasicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_POTENCIAL);

                                            if (estadoHhppMgl != null) {
                                                codigoRr = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppMgl);
                                                estadoRr.setEhhID(codigoRr);

                                                ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                                                boolean habilitarRR = false;
                                                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                                    habilitarRR = true;
                                                }
                                                // Se pregunta si el hhpp tiene ID RR para ir actualizar el hhpp allá.
                                                if (!(hhppBuscado != null && hhppBuscado.getHhpIdrR() != null
                                                        && !hhppBuscado.getHhpIdrR().isEmpty())) {
                                                    habilitarRR = false;
                                                }
                                                if (habilitarRR) {
                                                    DireccionRRManager drrm = new DireccionRRManager(true);
                                                    String estadoMer = estadoHhpp.getEstadoHhpp().getEhhID();
                                                    drrm.cambioEstadoHhppRR(hhppBuscado, codigoRr, estadoMer, usuario);
                                                    response.setMessageType("I");
                                                    response.setMessage("Se modifico el estado del HHPP, "
                                                            + "el nuevo estado en MGL es:  "
                                                            + estadoHhpp.getEstadoHhpp().getEhhID() + ", en RR: " + estadoRr.getEhhID() + "");
                                                }
                                            }
                                        }

                                    } else //////////////////////////CAMBIAR ESTADO SUBSCRIPTOR///////////////////////////////
                                    if (estadoHhpp.getEstadoHhpp().getEhhID().equals("CS")
                                            && estadoHhpp.getSubscriptor() != null
                                            && !estadoHhpp.getSubscriptor().isEmpty()) {

                                        hhppBuscado.setSuscriptor(estadoHhpp.getSubscriptor());
                                        hhppBuscado.setEhhId(estadoBuscado);
                                        hhppBuscado.setHhpEstadoUnit(estadoBuscado.getEhhID());
                                        actualizar = true;
                                        estado = estadoHhpp.getEstadoHhpp().getEhhID();

                                    } else if (hhppBuscado.getEhhId().getEhhID().equals("SU")
                                            && estadoHhpp.getEstadoHhpp().getEhhID().equals("CS")) {

                                        hhppBuscado.setEhhId(estadoBuscado);
                                        hhppBuscado.setHhpEstadoUnit(estadoBuscado.getEhhID());
                                        actualizar = true;
                                        estado = estadoHhpp.getEstadoHhpp().getEhhID();

                                    } else if (estadoHhpp.getEstadoHhpp().getEhhID().equals("SU")
                                            || estadoHhpp.getEstadoHhpp().getEhhID().equals("DF")) {

                                        hhppBuscado.setEhhId(estadoBuscado);
                                        hhppBuscado.setHhpEstadoUnit(estadoBuscado.getEhhID());
                                        actualizar = true;
                                        estado = estadoHhpp.getEstadoHhpp().getEhhID();

                                    } else if (!estadoHhpp.getEstadoHhpp().getEhhID().equals("SU")
                                            && !estadoHhpp.getEstadoHhpp().getEhhID().equals("DF")
                                            && !estadoHhpp.getEstadoHhpp().getEhhID().equals("CS")) {

                                        hhppBuscado.setSuscriptor(null);
                                        hhppBuscado.setEhhId(estadoBuscado);
                                        hhppBuscado.setHhpEstadoUnit(estadoBuscado.getEhhID());
                                        actualizar = true;
                                        estado = estadoHhpp.getEstadoHhpp().getEhhID();

                                    } else {

                                        response.setMessageType("E");
                                        response.setMessage("No se pudo realizar el cambio de estado, es necesario asociar un subscriptor");
                                    }
                                } else {
                                    response.setMessageType("E");
                                    response.setMessage("El flujo de: " + hhppBuscado.getEhhId().getEhhID() + " a " + estadoHhpp.getEstadoHhpp().getEhhID()
                                            + " no es valido.");
                                }

                                if (actualizar) {

                                    hhppBuscado.setFechaEdicion(new Date());
                                    hMhppglDaoImpl.update(hhppBuscado);
                                    response.setMessageType("I");
                                    response.setMessage("Se modifico el estado del HHPP, el nuevo estado es " + hhppBuscado.getEhhId().getEhhID());

                                    String basica = "";

                                    if (estado.equalsIgnoreCase("DE")) {
                                        basica = co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_DESCONECTADO;
                                    } else if (estado.equalsIgnoreCase("PR")) {
                                        basica = co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_PRECABLEADO;
                                    } else if (estado.equalsIgnoreCase("SU")) {
                                        basica = co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_SUSPENDIDO;
                                    } else if (estado.equalsIgnoreCase("CS")) {
                                        basica = co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_INSERVICE;
                                    } else if (estado.equalsIgnoreCase("PO")) {
                                        basica = co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_POTENCIAL;
                                    } else if (estado.equalsIgnoreCase("DF")) {
                                        basica = co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_DES_FISICA;
                                    }

                                    //Validar si RR se encuentra encendido
                                    ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                                    boolean habilitarRR = false;
                                    if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                        habilitarRR = true;
                                    }
                                    //Se pregunta si el hhpp tiene ID RR para ir actualizar el hhpp allá.
                                    if (!(hhppBuscado != null && hhppBuscado.getHhpIdrR() != null
                                            && !hhppBuscado.getHhpIdrR().isEmpty())) {
                                        habilitarRR = false;
                                    }

                                    CmtBasicaMgl estadoHhppMgl = cmtBasicaMglManager.findByCodigoInternoApp(basica);

                                    if (habilitarRR) {

                                        EstadoHhppMgl estadoRr = new EstadoHhppMgl();
                                        String estadoRrEquivalente = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppMgl);
                                        estadoRr.setEhhID(estadoRrEquivalente);

                                        DireccionRRManager drrm = new DireccionRRManager(true);
                                        drrm.cambioEstadoHhppRR(hhppBuscado, estadoRrEquivalente, estado, estadoHhpp.getUser());
                                        response.setMessage("Se modifico el estado del HHPP, "
                                                + "el nuevo estado en MGL es:  "
                                                + estadoHhpp.getEstadoHhpp().getEhhID() + ", en RR: " + estadoRr.getEhhID() + "");
                                    }
                                }

                            } else {
                                response.setMessageType("E");
                                response.setMessage("El estado ingresado no se encontró en la base de datos");
                                return response;
                            }
                        } else {
                            response.setMessageType("E");
                            response.setMessage("No se puede encontrar el HHPP con el id " + hhppMgl.getHhpId());
                            return response;
                        }
                    } else {
                        response.setMessageType("E");
                        response.setMessage("Verifique sus datos, el usuario es requerido");
                        return response;
                    }
                } else {
                    response.setMessageType("E");
                    response.setMessage("Verifique que los datos necesarios esten seteados en el objeto request");
                    return response;
                }
            } else {
                response.setMessageType("E");
                response.setMessage("Verifique que los datos necesarios esten seteados en el objeto request");
                return response;
            }

        } catch (ApplicationException e) {
            response.setMessageType("E");
            response.setMessage("Se genero una exception " + e.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
        }
        return response;
    }

    public boolean validarFlujoEstado(HhppMgl hhppMgl, EstadoHhppMgl actual, EstadoHhppMgl nuevo) {

        boolean sePuede = false;
        try {
            NodoMglManager nodoMglManager = new NodoMglManager();
            NodoMgl nodo = nodoMglManager.findById(hhppMgl.getNodId().getNodId());
            BigDecimal codigoTecnologia = nodo.getNodTipo().getBasicaId();
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtBasicaMgl basicaEstado = cmtBasicaMglManager.findById(codigoTecnologia);

            TecEstadosFlujosTecHab tec = tecEstadoFlujosTecDaoImpl.
                    findNextEstate(basicaEstado.getIdentificadorInternoApp(), actual.getEhhID(), nuevo.getEhhID());

            if (tec != null) {

                sePuede = true;

            }

        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return sePuede;
    }

    public List<EstratoxTorreDto> findByHhppEstratoSubCM(CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {

        List<EstratoxTorreDto> listaHHPPEstrato = null;
        List<SubDireccionMgl> listaSubDireccion;
        switch (findBy) {
            case CUENTA_MATRIZ:
                listaSubDireccion = hMhppglDaoImpl.findListHhppCM(cmtSubEdificioMgl);
                listaHHPPEstrato = getHHPPEstratoCM(listaSubDireccion);
                break;
            case SUB_EDIFICIO:
                listaSubDireccion = hMhppglDaoImpl.findListHhppSub(cmtSubEdificioMgl);
                listaHHPPEstrato = getHHPPEstratoCM(listaSubDireccion);
                break;
        }
        return listaHHPPEstrato;

    }

    public List<EstratoxTorreDto> getHHPPEstratoCM(List<SubDireccionMgl> subDireccionMgl) {
        ArrayList<Integer> listSubedificios = new ArrayList<>();
        Hashtable hashLista = new Hashtable();
        List<EstratoxTorreDto> resumenHhppByEstratoList;
        for (SubDireccionMgl hhpp : subDireccionMgl) {
            if (hhpp.getSdiEstrato() != null || hhpp.getSdiNivelSocioecono() != null) {
                if (hhpp.getSdiEstrato() != null) {
                    listSubedificios.add(hhpp.getSdiEstrato().intValueExact());
                } else {
                    listSubedificios.add((Integer) hhpp.getSdiNivelSocioecono().intValueExact());
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
        resumenHhppByEstratoList = new ArrayList<>();
        while (e.hasMoreElements()) {
            clave = e.nextElement();
            valor = hashLista.get(clave);
            EstratoxTorreDto hhppxestratos = new EstratoxTorreDto();
            hhppxestratos.setNumeroHhpp((Integer) valor);
            hhppxestratos.setEstratoHhpp((Integer) clave);
            resumenHhppByEstratoList.add(hhppxestratos);

        }
        return resumenHhppByEstratoList;
    }

    /**
     * Consulta de hhpp existentes por cuenta matriz
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} cuenta matriz a
     * consultar
     *
     * @return {@link Integer} Cantidad de registros encontrados
     */
    public Integer cantidadHhppCuentaMatriz(CmtCuentaMatrizMgl cuentaMatrizMgl) {
        return hMhppglDaoImpl.cantidadHhppCuentaMatriz(cuentaMatrizMgl);
    }

    /**
     * Consulta de n&uacute;mero de HHPP activos
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} Cuenta matriz a
     * verificar
     *
     * @return {@link Integer} Cantidad de registros activos encontrados
     *
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public Integer cantidadHhppEnServicio(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {
        return hMhppglDaoImpl.cantidadHhppEnServicio(cuentaMatrizMgl);
    }

    /**
     * M&eacute;todo para realizar borrado l&oacute;gico de un HHPP
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} cuenta matriz con los
     * HHPP a eliminar
     * @param user {@link String} Usuario que env&iacue;a la transacci&oacute;n
     * @param perfil {@link Integer} Perfil del usuario que env&iacue;a la
     * transacci&oacute;n
     *
     * @return {@link List}&lt;{@link HhppMgl} listado de HHPP que no se van a
     * eliminar
     *
     * @throws ApplicationException Excepci&oacute;n lanzanda durante el proceso
     */
    public List<HhppMgl> eliminarHhppCuentaMatriz(CmtCuentaMatrizMgl cuentaMatrizMgl,
            String user, Integer perfil) throws ApplicationException {
        List<HhppMgl> hhppMgl = hMhppglDaoImpl.obtenerHhppCM(cuentaMatrizMgl);
        List<HhppMgl> hhppNoProcesados = new ArrayList<>();
        for (HhppMgl hhpp : hhppMgl) {
            if (!cambiarDireccionRr(hhpp, user, perfil)) {
                hhppNoProcesados.add(hhpp);
            }
        }
        return hhppNoProcesados;
    }

    /**
     * M&eacute;todo para modificar la direcci&oacute;n de hhpp y hacer borrado
     * l&oacute;gico del registro
     *
     * direcci&oacute;n del hhpp
     *
     * @param hhpp {@link HhppMgl} objeto a eliminar
     * @param user {@link String} Usuario que env&iacute;a la transacci&oacute;n
     * @param perfil {@link Integer} Perfil del usuario que env&iacute;a la
     * transacci&oacute;n
     *
     * @return boolean resultado de la transacci&oacute;n
     */
    private boolean cambiarDireccionRr(HhppMgl hhpp, String user, Integer perfil) {
        try {

            DireccionRRManager manager = new DireccionRRManager(true);
            String comunidad = hhpp.getHhpComunidad();
            String division = hhpp.getHhpDivision();
            String placa = hhpp.getHhpPlaca();
            String calle = hhpp.getHhpCalle();
            String apartamento = hhpp.getHhpApart();

            if (hhpp.getHhpIdrR() != null) {
                HhppResponseRR responseHhppRR = manager.getHhppByIdRR(hhpp.getHhpIdrR());
                if (responseHhppRR.getTipoMensaje() != null
                        && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {
                    comunidad = responseHhppRR.getComunidad();
                    division = responseHhppRR.getDivision();
                    calle = responseHhppRR.getStreet();
                    placa = responseHhppRR.getHouse();
                    apartamento = responseHhppRR.getApartamento();
                } else {
                    LOGGER.error("Ocurrio un error consultando la data del hhpp en RR.");

                }
            }
            manager.cambiarHHPPRR(
                    comunidad,
                    division,
                    placa,
                    calle,
                    apartamento,
                    "NFI", "TVC", "85-06",
                    "CL 400", hhpp.getHhpIdrR(), "");
            hMhppglDaoImpl.deleteCm(hhpp, user, perfil);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error en cambiarDireccionRr. " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Busca un HHPP en repositorio por Id de Dirección.
     *
     * @param dirId
     * @return HhppMgl si la unidad Existe en el repositorio
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<HhppMgl> findHhppByDirId(BigDecimal dirId) {

        return hMhppglDaoImpl.findHhppByDirId(dirId);
    }

    /**
     * Busca un HHPP en repositorio por Id de Dirección.
     *
     * @param subDirId
     * @return HhppMgl si la unidad Existe en el repositorio
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<HhppMgl> findHhppBySubDirId(BigDecimal subDirId) {

        return hMhppglDaoImpl.findHhppBySubDirId(subDirId);
    }

    /**
     * Obtiene listado de Hhpp por Suscriptor
     *
     * @param suscriptor por el cual se desea filtrar hhpp
     * @return listado de hhpp
     * @author Juan David Hernandez Torres
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<HhppMgl> findHhppBySuscriptor(String suscriptor) {
        return hMhppglDaoImpl.findHhppBySuscriptor(suscriptor);
    }

    /**
     * Autor: Victor Bocanegra
     *
     * @param cmtDireccionSolicitudMgl
     * @param cmtBasicaMgl
     * @param consultaUnidades
     * @return List<NodoMgl> lista de nodos cercanos.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<NodoMgl> retornaNodosCercano(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl,
            CmtBasicaMgl cmtBasicaMgl, boolean consultaUnidades) throws ApplicationException {

        List<NodoMgl> nodosCercanos = null;
        List<NodoMgl> nodosCerca = new ArrayList<>();
        List<HhppMgl> lsthhppMgl = null;

        if (cmtDireccionSolicitudMgl != null) {
            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null && cmtDireccionSolicitudMgl.getCodTipoDir().equalsIgnoreCase(Constant.ADDRESS_TIPO_CK)) {
                lsthhppMgl = hMhppglDaoImpl.findHhppByDirDetalladaCK(cmtDireccionSolicitudMgl, false);
            } else if (cmtDireccionSolicitudMgl.getCodTipoDir() != null && cmtDireccionSolicitudMgl.getCodTipoDir().equalsIgnoreCase(Constant.ADDRESS_TIPO_BM)) {
                lsthhppMgl = hMhppglDaoImpl.findHhppByDirDetalladaBM(cmtDireccionSolicitudMgl, false);
            } else {
                lsthhppMgl = hMhppglDaoImpl.findHhppByDirDetalladaIT(cmtDireccionSolicitudMgl, false);
            }
        }

        if (lsthhppMgl != null && !lsthhppMgl.isEmpty()) {

            nodosCercanos = new ArrayList<>();
            for (HhppMgl hhppMgl : lsthhppMgl) {
                try {

                    NodoMgl nodoMgl = hhppMgl.getNodId().clone();

                    if (nodoMgl.getNodTipo() != null) {

                        String DTH = "DTH";
                        String FIBRA_FTTTH = "Fibra FTTH";
                        String HFC_BID = "HFC Bidireccional";
                        String HFC_UNI = "HFC Unidireccional";
                        if (cmtBasicaMgl.getNombreBasica().equals(DTH)
                                && nodoMgl.getNodTipo().getNombreBasica().equals(DTH)) {

                            if (hhppMgl.getSubDireccionObj() != null
                                    && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                                nodoMgl.setDireccionStr(hhppMgl.getSubDireccionObj().getSdiFormatoIgac());
                            } else {
                                //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac  
                                if (hhppMgl.getDireccionObj() != null
                                        && hhppMgl.getDireccionObj().getDirId() != null) {
                                    nodoMgl.setDireccionStr(hhppMgl.getDireccionObj().getDirFormatoIgac());
                                }
                            }
                            nodosCercanos.add(nodoMgl);
                        } else if (cmtBasicaMgl.getNombreBasica().equals(FIBRA_FTTTH)
                                && nodoMgl.getNodTipo().getNombreBasica().equals(FIBRA_FTTTH)) {
                            if (hhppMgl.getSubDireccionObj() != null
                                    && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                                nodoMgl.setDireccionStr(hhppMgl.getSubDireccionObj().getSdiFormatoIgac());
                            } else {
                                //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac  
                                if (hhppMgl.getDireccionObj() != null
                                        && hhppMgl.getDireccionObj().getDirId() != null) {
                                    nodoMgl.setDireccionStr(hhppMgl.getDireccionObj().getDirFormatoIgac());
                                }
                            }
                            nodosCercanos.add(nodoMgl);
                        } else if (cmtBasicaMgl.getNombreBasica().equals(HFC_BID)
                                && nodoMgl.getNodTipo().getNombreBasica().equals(HFC_BID)) {
                            if (hhppMgl.getSubDireccionObj() != null
                                    && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                                nodoMgl.setDireccionStr(hhppMgl.getSubDireccionObj().getSdiFormatoIgac());
                            } else {
                                //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac  
                                if (hhppMgl.getDireccionObj() != null
                                        && hhppMgl.getDireccionObj().getDirId() != null) {
                                    nodoMgl.setDireccionStr(hhppMgl.getDireccionObj().getDirFormatoIgac());
                                }
                            }
                            nodosCercanos.add(nodoMgl);
                        } else if (cmtBasicaMgl.getNombreBasica().equals(HFC_UNI)
                                && nodoMgl.getNodTipo().getNombreBasica().equals(HFC_UNI)) {
                            if (hhppMgl.getSubDireccionObj() != null
                                    && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                                nodoMgl.setDireccionStr(hhppMgl.getSubDireccionObj().getSdiFormatoIgac());
                            } else {
                                //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac  
                                if (hhppMgl.getDireccionObj() != null
                                        && hhppMgl.getDireccionObj().getDirId() != null) {
                                    nodoMgl.setDireccionStr(hhppMgl.getDireccionObj().getDirFormatoIgac());
                                }
                            }
                            nodosCercanos.add(nodoMgl);
                        }
                    }
                } catch (CloneNotSupportedException e) {
                    LOGGER.error("Error en retornarNodosCercano. " + e.getMessage(), e);
                }
            }
            Map<BigDecimal, NodoMgl> lstNodos = new HashMap<>(nodosCercanos.size());

            for (NodoMgl n : nodosCercanos) {
                lstNodos.put(n.getNodId(), n);
            }
            for (Map.Entry<BigDecimal, NodoMgl> n : lstNodos.entrySet()) {
                nodosCerca.add(n.getValue());

            }
        }
        return nodosCerca;

    }

    /**
     * Autor: Victor Bocanegra Borrado logico de un Hhpp
     *
     * @param hhppMgl
     * @param usuario
     * @param perfil
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean deleteCm(HhppMgl hhppMgl, String usuario, int perfil)
            throws ApplicationException {

        return hMhppglDaoImpl.deleteCm(hhppMgl, usuario, perfil);

    }

    /**
     * Autor: Juan David Hernandez Actualiza un registro de hhpp
     *
     * @param hhppMgl
     * @param usuario
     * @param perfil
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public HhppMgl updateHhppMgl(HhppMgl hhppMgl, String usuario, int perfil) throws ApplicationException {
        return hMhppglDaoImpl.updateCm(hhppMgl, usuario, perfil);
    }

    /**
     * Cambia el estado del registro de un HHPP en repositoriol para dar a esto
     * como una reactivación.
     *
     * @param hhppId id del hhpp que se desea reactivar
     * @param usuario que realiza la reactivación del hhpp
     * @param perfil que realiza la reactivación del hhpp
     *
     * return true si se actualiza correctamente el registro.
     *
     * @author Juan David Hernandez
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean reactivarHhpp(BigDecimal hhppId, String usuario, int perfil) throws ApplicationException {

        HhppMgl hhppMgl = findById(hhppId);

        if (hhppMgl == null) {
            return false;
        } else {
            hhppMgl.setEstadoRegistro(1);
            EstadoHhppMgl estadoHhppMgl = new EstadoHhppMgl();
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtBasicaMgl estPo = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_EST_HHPP_TIPO_POTENCIAL);
            estadoHhppMgl.setEhhID(estPo.getCodigoBasica()); //"PO"
            hhppMgl.setEhhId(estadoHhppMgl);
            hhppMgl.setFechaEdicion(new Date());
            hhppMgl.setUsuarioEdicion(usuario);
            hhppMgl.setPerfilEdicion(perfil);
            hMhppglDaoImpl.update(hhppMgl);
            return true;
        }
    }

    /**
     * Autor: Juan David Hernandez
     *
     * @param direccionDetalladaMgl
     * @param cmtBasicaMgl
     * @param centroPobladoId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws java.lang.CloneNotSupportedException
     */
    public List<NodoMgl> obtenerNodosCercanoSolicitudHhpp(CmtDireccionDetalladaMgl direccionDetalladaMgl, CmtBasicaMgl cmtBasicaMgl, BigDecimal centroPobladoId) throws Exception {

        CmtDireccionDetalladaMgl direccionDetallada = direccionDetalladaMgl.clone();
        CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
        DrDireccion drDireccion = cmtDireccionDetalleMglManager.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetallada);

        //Se ponen en null los valores para enviar la direccion hasta la placa
        drDireccion.setCpTipoNivel1(null);
        drDireccion.setCpTipoNivel2(null);
        drDireccion.setCpTipoNivel3(null);
        drDireccion.setCpTipoNivel4(null);
        drDireccion.setCpTipoNivel5(null);
        drDireccion.setCpTipoNivel6(null);
        drDireccion.setCpValorNivel1(null);
        drDireccion.setCpValorNivel2(null);
        drDireccion.setCpValorNivel3(null);
        drDireccion.setCpValorNivel4(null);
        drDireccion.setCpValorNivel5(null);
        drDireccion.setCpValorNivel6(null);
        drDireccion.setPlacaDireccion(null);
        drDireccion.setMzValorNivel5(null);
        drDireccion.setItValorPlaca(null);

        List<NodoMgl> nodosCercanos = null;
        List<HhppMgl> lsthhppMgl;

        lsthhppMgl = cmtDireccionDetalleMglManager
                .findHhppByDireccionNodoCercano(drDireccion, centroPobladoId, true);

        if (lsthhppMgl != null && !lsthhppMgl.isEmpty()) {

            nodosCercanos = new ArrayList<>();
            for (HhppMgl hhppMgl : lsthhppMgl) {

                NodoMgl nodoMgl;

                if (hhppMgl.getNodId().getNodTipo() != null) {
                    if (cmtBasicaMgl.getIdentificadorInternoApp().equals(Constant.DTH)
                            && hhppMgl.getNodId().getNodTipo().getIdentificadorInternoApp().equals(Constant.DTH)) {

                        nodoMgl = hhppMgl.getNodId().clone();
                        //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac
                        if (hhppMgl.getSubDireccionObj() != null
                                && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                            nodoMgl.setDireccionStr(hhppMgl.getSubDireccionObj().getSdiFormatoIgac());
                        } else {
                            //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac  
                            if (hhppMgl.getDireccionObj() != null
                                    && hhppMgl.getDireccionObj().getDirId() != null) {
                                nodoMgl.setDireccionStr(hhppMgl.getDireccionObj().getDirFormatoIgac());
                            }
                        }

                        nodosCercanos.add(nodoMgl);

                    } else if (cmtBasicaMgl.getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH)
                            && hhppMgl.getNodId().getNodTipo().getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH)) {

                        nodoMgl = hhppMgl.getNodId().clone();
                        //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac
                        if (hhppMgl.getSubDireccionObj() != null
                                && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                            nodoMgl.setDireccionStr(hhppMgl.getSubDireccionObj().getSdiFormatoIgac());
                        } else {
                            //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac  
                            if (hhppMgl.getDireccionObj() != null
                                    && hhppMgl.getDireccionObj().getDirId() != null) {
                                nodoMgl.setDireccionStr(hhppMgl.getDireccionObj().getDirFormatoIgac());
                            }
                        }

                        nodosCercanos.add(nodoMgl);

                    } else if (cmtBasicaMgl.getIdentificadorInternoApp().equals(Constant.HFC_BID)
                            && hhppMgl.getNodId().getNodTipo().getIdentificadorInternoApp().equals(Constant.HFC_BID)) {

                        nodoMgl = hhppMgl.getNodId().clone();
                        //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac
                        if (hhppMgl.getSubDireccionObj() != null
                                && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                            nodoMgl.setDireccionStr(hhppMgl.getSubDireccionObj().getSdiFormatoIgac());
                        } else {
                            //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac  
                            if (hhppMgl.getDireccionObj() != null
                                    && hhppMgl.getDireccionObj().getDirId() != null) {
                                nodoMgl.setDireccionStr(hhppMgl.getDireccionObj().getDirFormatoIgac());
                            }
                        }
                        nodosCercanos.add(nodoMgl);

                    } else if (cmtBasicaMgl.getIdentificadorInternoApp().equals(Constant.HFC_UNI)
                            && hhppMgl.getNodId().getNodTipo().getIdentificadorInternoApp().equals(Constant.HFC_UNI)) {

                        nodoMgl = hhppMgl.getNodId().clone();
                        //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac
                        if (hhppMgl.getSubDireccionObj() != null
                                && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                            nodoMgl.setDireccionStr(hhppMgl.getSubDireccionObj().getSdiFormatoIgac());
                        } else {
                            //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac  
                            if (hhppMgl.getDireccionObj() != null
                                    && hhppMgl.getDireccionObj().getDirId() != null) {
                                nodoMgl.setDireccionStr(hhppMgl.getDireccionObj().getDirFormatoIgac());
                            }
                        }

                        nodosCercanos.add(nodoMgl);

                    } else if (cmtBasicaMgl.getIdentificadorInternoApp().equals(Constant.FIBRA_OP_GPON)
                            && hhppMgl.getNodId().getNodTipo().getIdentificadorInternoApp().equals(Constant.FIBRA_OP_GPON)) {

                        nodoMgl = hhppMgl.getNodId().clone();
                        //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac
                        if (hhppMgl.getSubDireccionObj() != null
                                && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                            nodoMgl.setDireccionStr(hhppMgl.getSubDireccionObj().getSdiFormatoIgac());
                        } else {
                            //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac  
                            if (hhppMgl.getDireccionObj() != null
                                    && hhppMgl.getDireccionObj().getDirId() != null) {
                                nodoMgl.setDireccionStr(hhppMgl.getDireccionObj().getDirFormatoIgac());
                            }
                        }
                        nodosCercanos.add(nodoMgl);

                    } else if (cmtBasicaMgl.getIdentificadorInternoApp().equals(Constant.FIBRA_OP_UNI)
                            && hhppMgl.getNodId().getNodTipo().getIdentificadorInternoApp().equals(Constant.FIBRA_OP_UNI)) {

                        nodoMgl = hhppMgl.getNodId().clone();
                        //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac
                        if (hhppMgl.getSubDireccionObj() != null
                                && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                            nodoMgl.setDireccionStr(hhppMgl.getSubDireccionObj().getSdiFormatoIgac());
                        } else {
                            //Se obtiene el id de la sub direccion si tiene para obtener su direccion en formato igac  
                            if (hhppMgl.getDireccionObj() != null
                                    && hhppMgl.getDireccionObj().getDirId() != null) {
                                nodoMgl.setDireccionStr(hhppMgl.getDireccionObj().getDirFormatoIgac());
                            }
                        }
                        nodosCercanos.add(nodoMgl);
                    }
                }
            }
        }
        return nodosCercanos;
    }

    public List<HhppMgl> findBy_SubEdifi_TecSubEdifi(BigDecimal idSubEdifi, BigDecimal idTecSubEdifi) {
        return hMhppglDaoImpl.findBy_SubEdifi_TecSubEdifi(idSubEdifi, idTecSubEdifi);
    }

    /**
     * obtiene el Hhpp asociado a la Ot
     *
     * @author Orlando Velasquez
     * @param otHhpp orden de trabajo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public HhppMgl findHhppByDirAndSubDir(OtHhppMgl otHhpp) {

        return hMhppglDaoImpl.findHhppByDirAndSubDir(otHhpp);
    }

    /**
     * obtiene los Hhpp asociados a la direccion y subdireccion.
     *
     * @author Orlando Velasquez
     * @param dir id de la direccion
     * @param subDir id de la subdireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public List<HhppMgl> findByDirAndSubDir(DireccionMgl dir, SubDireccionMgl subDir) {

        return hMhppglDaoImpl.findByDirAndSubDir(dir, subDir);
    }

    /**
     * obtiene listado de hhpp por coordenada a un rango de desviación.
     *
     * @author Juan David Hernandez
     * @param coordenadasDto
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public CmtAddressGeneralResponseDto findHhppByCoordenadas(CmtRequestHhppByCoordinatesDto coordenadasDto) throws ApplicationException {
        CmtAddressGeneralResponseDto respuesta = new CmtAddressGeneralResponseDto();
        try {

            //validacion codigo dane obligatorio
            if (coordenadasDto == null || coordenadasDto.getCiudad() == null
                    || coordenadasDto.getCiudad().isEmpty()) {
                respuesta.setMessage("Debe ingresar el codigo dane de la ciudad por favor.");
                respuesta.setMessageType("E");
                return respuesta;
            } else {
                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                GeograficoPoliticoMgl ciudadGpo = geograficoPoliticoManager.findCiudadCodDane(coordenadasDto.getCiudad());

                if (ciudadGpo == null) {
                    respuesta.setMessage(Constant.MSG_DIR_CODIGO_DANE_ERROR);
                    respuesta.setMessageType("E");
                    return respuesta;
                }
            }
            //validación metros desviacion
            if (coordenadasDto.getDeviationMtr() == 0) {
                respuesta.setMessage("Debe ingresar el numero de metros de desviacion.");
                respuesta.setMessageType("E");
                return respuesta;
            }
            //validación latitud
            if (coordenadasDto.getLatitude() == null || coordenadasDto.getLatitude().isEmpty()) {
                respuesta.setMessage("Debe ingresar la latitud.");
                respuesta.setMessageType("E");
                return respuesta;
            } else {
                if (coordenadasDto.getLatitude().contains(",")) {
                    respuesta.setMessage("La latitud debe ser ingresada con Coma ',' y no con Punto '.'");
                    respuesta.setMessageType("E");
                    return respuesta;
                }
            }
            //validación latitud
            if (coordenadasDto.getLongitude() == null || coordenadasDto.getLongitude().isEmpty()) {
                respuesta.setMessage("Debe ingresar la longitud.");
                respuesta.setMessageType("E");
                return respuesta;
            } else {
                if (coordenadasDto.getLongitude().contains(",")) {
                    respuesta.setMessage("La longitud debe ser ingresada con Coma ',' y no con Punto '.'");
                    respuesta.setMessageType("E");
                    return respuesta;
                }
            }

            if (coordenadasDto.getUnitsNumber() == 0) {
                respuesta.setMessage("Debe ingresar el numero de unidades que desea obtener de la busqueda.");
                respuesta.setMessageType("E");
                return respuesta;
            }

            CmtDireccionDetalleMglManager direccionDetalleMglManager = new CmtDireccionDetalleMglManager();

            double latitudIngresada = Double.parseDouble(redondearCoordenada(coordenadasDto.getLatitude()));
            double longitudIngresada = Double.parseDouble(redondearCoordenada(coordenadasDto.getLongitude()));
            double latitudAumentada500mts = latitudIngresada + (Constant.DISTANCIA_1_METRO_COORDENADA * coordenadasDto.getDeviationMtr());
            double longitudAumentada500mts = longitudIngresada + (Constant.DISTANCIA_1_METRO_COORDENADA * coordenadasDto.getDeviationMtr());

            List<CmtDireccionDetalladaMgl> direccionDetalladaList = direccionDetalleMglManager
                    .findDireccionDetalladaByCoordenadas(latitudIngresada, longitudIngresada,
                            latitudAumentada500mts, longitudAumentada500mts,
                            coordenadasDto.getCiudad());

            if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {
                List<CmtDireccionDetalladaMgl> direccionDetalladaCercanaList = new ArrayList();

                for (CmtDireccionDetalladaMgl direccionDetallada : direccionDetalladaList) {

                    if (direccionDetallada.getDireccion() != null
                            && direccionDetallada.getDireccion().getUbicacion() != null
                            && direccionDetallada.getDireccion().getUbicacion().getUbiLatitud() != null
                            && direccionDetallada.getDireccion().getUbicacion().getUbiLongitud() != null) {

                        //valida el numero de unidades que desea recibir el usuario
                        if (direccionDetalladaCercanaList.size() < coordenadasDto.getUnitsNumber()) {
                            direccionDetalladaCercanaList.add(direccionDetallada);
                        }
                    }
                }

                if (direccionDetalladaCercanaList.isEmpty()) {
                    respuesta.setMessage("No se encontraron direcciones de hhpp con desviacion de "
                            + coordenadasDto.getDeviationMtr() + " metros a su alrededor");
                    respuesta.setMessageType("E");
                    return respuesta;
                } else {
                    respuesta.setListAddresses(direccionDetalleMglManager
                            .parseCmtDireccionDetalladaMglToCmtAddressGeneralDto(direccionDetalladaCercanaList));
                    GeograficoPoliticoManager geo = new GeograficoPoliticoManager();
                    GeograficoPoliticoMgl centroPoblado = geo.findCityByCodDaneCp(coordenadasDto.getCiudad());
                    respuesta.setCentroPoblado(centroPoblado.getGpoNombre());
                    respuesta.setIdCentroPoblado(centroPoblado.getGpoId().toString());
                    respuesta.setMessage("Direccion(es) encontrada(s) a una desviacion de "
                            + coordenadasDto.getDeviationMtr() + " metros a su alrededor");
                    respuesta.setMessageType("I");
                    return respuesta;
                }

            } else {
                respuesta.setMessage("No se encontraron direcciones con la informacion ingresada");
                respuesta.setMessageType("E");
                return respuesta;
            }

        } catch (ApplicationException | NumberFormatException e) {
            respuesta.setMessage("Error al realizar la busqueda de"
                    + " direcciones por coordenada debido a :" + e.getMessage());
            respuesta.setMessageType("E");
            String msg = "Error al realizar la busqueda de"
                    + " direcciones por coordenada :" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            return respuesta;
        }
    }

    /**
     * obtiene la distancia en metros entre 2 coordenadas geograficas
     *
     * @author Juan David Hernandez
     * @param latitud_1
     * @param longitud_1
     * @param longitud_2
     * @param latitud_2
     * @return distancia en metros
     *
     */
    public double calcularDistanciaCoordenadasMtrs(double latitud_1, double longitud_1,
            double latitud_2, double longitud_2) {

        double EARTH_RADIUS = 6371; // radio de la tierra en KM.
        double dLat = toRadians(latitud_2 - latitud_1);
        double dLon = toRadians(longitud_2 - longitud_1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(toRadians(latitud_1)) * Math.cos(toRadians(latitud_2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = EARTH_RADIUS * c;

        return d; //se multiplica por mil para retornar el resultado en Metros 
    }

    public double toRadians(double degrees) {
        return degrees * (Math.PI / 180);
    }

    /**
     * Función que corta una coordenada y le quita los últimos 3 digitos.En caso
     * de que falten digitos a la coordenada es rellenada con ceros.
     *
     * @author Juan David Hernandez
     * @param coordenadaOriginal
     * @return
     */
    public String cortarCoordenada(String coordenadaOriginal) {
        try {

            if (coordenadaOriginal != null && !coordenadaOriginal.equalsIgnoreCase("0")) {
                // tamaño de la coordenada antes del punto
                int tamañoCordenadaAntesPunto = coordenadaOriginal.lastIndexOf(".");
                // valor de la coordenada despues del punto, agregar 1 para omitir el punto
                String coordenadaPostPunto = coordenadaOriginal.substring(tamañoCordenadaAntesPunto + 1);
                // tamaño de la coordenada despues del punto que debe ser 8
                int tamañoCoordenada = coordenadaPostPunto.length();
                //se igualan las coordenada para agregarle ceros a la derecha en caso de que falten
                String coordenadaCortada = coordenadaOriginal;
                //Se realiza resta para conocer cuantos punto hay que agregar
                int cantidadCerosAgregar = 8 - tamañoCoordenada;
                //se realiza for para agregar los ceros faltantes para completa 8
                if (cantidadCerosAgregar != 0) {
                    for (int i = 0; i < cantidadCerosAgregar; i++) {
                        coordenadaCortada += "0";
                    }
                }
                /* se obtiene el index de la coordenada despues de rellenarla de ceros (si fue necesario)
                 de quitar 3 digitos que es como se va a cortar la coordenada*/
                int indexCoordenadaCortada = coordenadaCortada.length() - 3;
                String coordenadaCoortadaFinal = coordenadaCortada.substring(0, indexCoordenadaCortada);
                return coordenadaCoortadaFinal;
            }
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            return null;
        }
    }

    /**
     * Función que corta una coordenada y le quita los últimos 3 digitos.En caso
     * de que falten digitos a la coordenada es rellenada con ceros.
     *
     * @author Juan David Hernandez
     * @param coordenadaOriginal
     * @return
     */
    public String redondearCoordenada(String coordenadaOriginal) {
        try {

            if (coordenadaOriginal != null && !coordenadaOriginal.equalsIgnoreCase("0")) {
                // tamaño de la coordenada antes del punto
                int tamañoCordenadaAntesPunto = coordenadaOriginal.lastIndexOf(".");
                // valor de la coordenada despues del punto, agregar 1 para omitir el punto
                String coordenadaPostPunto = coordenadaOriginal.substring(tamañoCordenadaAntesPunto + 1);
                // tamaño de la coordenada despues del punto que debe ser 8
                int tamañoCoordenada = coordenadaPostPunto.length();
                //se igualan las coordenada para agregarle ceros a la derecha en caso de que falten
                String coordenadaRedondeada = coordenadaOriginal;
                //Se realiza resta para conocer cuantos punto hay que agregar
                int cantidadCerosAgregar = 8 - tamañoCoordenada;
                //se realiza for para agregar los ceros faltantes para completa 8
                if (cantidadCerosAgregar != 0) {
                    for (int i = 0; i < cantidadCerosAgregar; i++) {
                        coordenadaRedondeada += "0";
                    }
                }

                return coordenadaRedondeada;
            }
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            return null;
        }
    }

    /**
     * Busca un HHPP en repositorio por Id de Dirección y subdireccion.
     *
     * @param dirId
     * @param subDirId id del objeto subdireccion por el que se desea buscar
     * @return HhppMgl si la unidad Existe en el repositorio
     * @author Victor Bocanegra
     */
    public List<HhppMgl> findHhppByDirIdSubDirId(BigDecimal dirId, BigDecimal subDirId) {

        return hMhppglDaoImpl.findHhppByDirIdSubDirId(dirId, subDirId);
    }

    /**
     * Autor: Victor Bocanegra Consulta todos los hhpp por direccion detallada
     * BM(Barrio-Manzana)
     *
     * @param cmtDireccionSolicitudMgl
     * @param consultaUnidades
     * @return List<HhppMgl> Cantidad de registros activos encontrados
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public List<HhppMgl> findHhppByDirDetalladaBM(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl,
            boolean consultaUnidades) throws ApplicationException {

        return hMhppglDaoImpl.findHhppByDirDetalladaBM(cmtDireccionSolicitudMgl, consultaUnidades);
    }

    public CmtPestaniaHhppDto getHhpp(List<HhppMgl> listaHhpp, CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY findBy, int firstResult, int maxResults) throws ApplicationException {
        try {
            CmtPestaniaHhppDto cmtPestaniaHhppDto;
            List<CmtPestaniaHhppDetalleDto> listPestaniaHhppDetalleDto = new ArrayList<>();
            List<SubDireccionMgl> subDireccionMglHhpp = new ArrayList<>();
            List<DireccionMgl> direccionMglHhpp = new ArrayList<>();
            listaHhpp.forEach(hhppmgl -> procesarDatosDireccionesYsubDireccionesHhpp(hhppmgl, subDireccionMglHhpp, listPestaniaHhppDetalleDto, direccionMglHhpp));
            // se clasifican los hhpp salaventas y campamentos y se dejan de primero en la lista ordenada(listaOrdenadaDirDetallada)
            // se extraen los nombres de los subedificios en una lista de string (listaNombreSubEdif)
            List<String> listaNombreSubEdif = new ArrayList<>();
            List<CmtPestaniaHhppDetalleDto> listaOrdenadaDirDetallada = new ArrayList<>();

            for (CmtPestaniaHhppDetalleDto listaDetallada : listPestaniaHhppDetalleDto) {
                listaOrdenadaDirDetallada.add(listaDetallada);

                if (listaDetallada.getCmtBasicaMgl() != null
                        && !Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO.equals(listaDetallada.getCmtBasicaMgl().getIdentificadorInternoApp())) {
                    listaNombreSubEdif.add(listaDetallada.getTorre());
                } else {
                    int i = 0;
                    listaNombreSubEdif.add(i, listaDetallada.getTorre());
                }
            }

            listaOrdenadaDirDetallada.sort(Comparator.comparing(CmtPestaniaHhppDetalleDto::getTorre, Comparator.nullsFirst(Comparator.naturalOrder())));
            // se ordenan las subedificios
            Collections.sort(listaNombreSubEdif);
            // se extraen los nombres de las torres sin repetir
            HashSet<String> listaEdificiosUnicos = new HashSet<>(listaNombreSubEdif);
            // se recorren toda la lista de detallada contra la lista de nombres de subedificios
            //y se extraen las direcciones de cada torre para luego almacenarla en la lista final(listaFinalDetallada)
            // Agrupamos los datos por torre
            Map<String, List<CmtPestaniaHhppDetalleDto>> agrupadosPorTorre = listaOrdenadaDirDetallada.stream()
                    .collect(Collectors.groupingBy(
                            detalle -> detalle.getTorre() != null ? detalle.getTorre() : ""
                    ));

            // Procesamos cada grupo y generamos la lista final ordenada
            List<CmtPestaniaHhppDetalleDto> listaFinalDetallada = listaEdificiosUnicos.stream()
                    .flatMap(subedificio ->
                        // Obtenemos los detalles para este subedificio y los ordenamos por apartamento
                         agrupadosPorTorre.getOrDefault(subedificio, Collections.emptyList())
                                .stream()
                                .sorted(Comparator.comparing(
                                        detalle -> Optional.ofNullable(detalle.getHhppMglLista())
                                                .map(HhppMgl::getHhpApart)
                                                .orElse(""))
                                )
                    )
                    // Ordenar primero por torre, con torres nulas al principio
                    .sorted(Comparator.comparing(
                            CmtPestaniaHhppDetalleDto::getTorre,
                            Comparator.nullsFirst(String::compareTo)))
                    .collect(Collectors.toList());

            // se posiciona los hhpp salaventas complementos en las primeras posiciones de la lista
            List<CmtPestaniaHhppDetalleDto> listaDetalladaHHPP = new ArrayList<>();

            for (CmtPestaniaHhppDetalleDto listaOrdenadaCM : listaFinalDetallada) {
                if (listaOrdenadaCM.getCmtBasicaMgl() != null && !Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO.
                        equals(listaOrdenadaCM.getCmtBasicaMgl().getIdentificadorInternoApp())) {
                    listaDetalladaHHPP.add(listaOrdenadaCM);
                } else {
                    int i = 0;
                    listaDetalladaHHPP.add(i, listaOrdenadaCM);
                }
            }

            paginarListaHHPP(cmtSubEdificioMgl, firstResult, listaDetalladaHHPP, maxResults);
            cmtPestaniaHhppDto = new CmtPestaniaHhppDto();
            cmtPestaniaHhppDto.setListaPestaniaHhppDet(listaDetalladadaPaginada);
            return cmtPestaniaHhppDto;
        } catch (Exception e) {
            String msg = "Error al obtener la información de HHPP en " + findBy + ": " + e.getMessage();
            LOGGER.error(msg, e);
            throw new ApplicationException(msg);
        }
    }

    /**
     * Función que permite paginar la lista de hhpp
     *
     * @param cmtSubEdificioMgl {@link CmtSubEdificioMgl}
     * @param firstResult {@link Integer}
     * @param listaDetalladaHHPP {@link List<CmtPestaniaHhppDetalleDto>}
     * @param maxResults {@link Integer}
     * @author Gildardo Mora
     */
    private void paginarListaHHPP(CmtSubEdificioMgl cmtSubEdificioMgl, int firstResult, List<CmtPestaniaHhppDetalleDto> listaDetalladaHHPP, int maxResults) {
        // se maneja la lista ordenada de forma paginada
        if (listaDetalladaHHPP.isEmpty()) {
            listaDetalladadaPaginada = new ArrayList<>();
            return;
        }

        boolean esEdificioUnico = Optional.ofNullable(cmtSubEdificioMgl)
                .map(CmtSubEdificioMgl::getCuentaMatrizObj)
                .map(cuenta -> cuenta.getListCmtSubEdificioMglActivos().size() == 1
                        && Optional.ofNullable(cmtSubEdificioMgl.getEstadoSubEdificioObj())
                        .map(estado -> estado.getBasicaId() != null
                                && estado.getIdentificadorInternoApp() != null
                                && !Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO.equals(estado.getIdentificadorInternoApp()))
                        .orElse(false))
                .orElse(false);

        int tamanioPaginacion = maxResults > 0 ? maxResults : Constant.PAGINACION_OCHO_FILAS;
        int valorMaximo = esEdificioUnico ? firstResult + tamanioPaginacion : listaDetalladaHHPP.size();
        int maxIndex = Math.max(firstResult + tamanioPaginacion, valorMaximo);
        maxIndex = Math.min(maxIndex, listaDetalladaHHPP.size());

        listaDetalladadaPaginada = firstResult < maxIndex
                ? listaDetalladaHHPP.stream()
                .skip(firstResult)
                .limit((long) maxIndex - firstResult)
                .collect(Collectors.toList())
                : new ArrayList<>();
    }

    /**
     * Procesa los datos de direcciones y subdirecciones para el HHPP
     *
     * @param hhppmgl {@link HhppMgl}
     * @param subDireccionMglHhpp {@link List<SubDireccionMgl>}
     * @param listPestaniaHhppDetalleDto {@link List<CmtPestaniaHhppDetalleDto>}
     * @param direccionMglHhpp {@link List<DireccionMgl>}
     * @author Gildardo Mora
     */
    private void procesarDatosDireccionesYsubDireccionesHhpp(HhppMgl hhppmgl, List<SubDireccionMgl> subDireccionMglHhpp,
                                                             List<CmtPestaniaHhppDetalleDto> listPestaniaHhppDetalleDto, List<DireccionMgl> direccionMglHhpp) {
        CmtPestaniaHhppDetalleDto cmtPestaniaHhppDetalleDto;
        // Subdirecciones
        if (hhppmgl.getSubDireccionObj() != null) {

            if (!subDireccionMglHhpp.contains(hhppmgl.getSubDireccionObj()) && hhppmgl.getHhppSubEdificioObj() != null) {
                //cargar lista de subdirecciones
                subDireccionMglHhpp.add(hhppmgl.getSubDireccionObj());
                // cargar valores
                cmtPestaniaHhppDetalleDto = cargarListaHhpp(hhppmgl);
                listPestaniaHhppDetalleDto.add(cmtPestaniaHhppDetalleDto);
            }
            return;
        }

        //direcciones
        //lista vacia
        if (direccionMglHhpp.isEmpty()) {

            if (hhppmgl.getDirId() != null && hhppmgl.getDireccionObj() != null) {
                // cargar lista de direcciones
                direccionMglHhpp.add(hhppmgl.getDireccionObj());
                // cargar valores
                cmtPestaniaHhppDetalleDto = cargarListaHhpp(hhppmgl);
                listPestaniaHhppDetalleDto.add(cmtPestaniaHhppDetalleDto);
            }
            return;
        }

        //lista con elementos direcciones
        if (hhppmgl.getDirId() != null) {
            boolean notExist = direccionMglHhpp.stream()
                    .noneMatch(direccionMgl -> direccionMgl.getDirId().compareTo(hhppmgl.getDirId()) == 0);

            if (notExist && hhppmgl.getDireccionObj() != null) {
                // cargar lista de direcciones
                direccionMglHhpp.add(hhppmgl.getDireccionObj());
                // cargar valores
                cmtPestaniaHhppDetalleDto = cargarListaHhpp(hhppmgl);
                listPestaniaHhppDetalleDto.add(cmtPestaniaHhppDetalleDto);
            }
        }
    }

    /**
     * Carga la lista de hhpp
     * @param hhppMgl {@link HhppMgl}
     * @return {@link CmtPestaniaHhppDetalleDto}
     * @author Gildardo Mora
     */
    private CmtPestaniaHhppDetalleDto cargarListaHhpp(HhppMgl hhppMgl) {
        CmtPestaniaHhppDetalleDto detalleHHpp = new CmtPestaniaHhppDetalleDto();
        // hhpp, basica 
        detalleHHpp.setHhppMglLista(hhppMgl);
        Optional.ofNullable(hhppMgl.getNodId())
                .map(NodoMgl::getNodTipo)
                .ifPresent(detalleHHpp::setCmtBasicaMgl);
        // subedificio
        Optional.ofNullable(hhppMgl.getHhppSubEdificioObj())
                .map(CmtSubEdificioMgl::getNombreSubedificio)
                .ifPresent(detalleHHpp::setTorre);
        // Suscriptor
        detalleHHpp.setSuscriptor(hhppMgl.getSuscriptor());
        Optional<SubDireccionMgl> subDireccionOpt = Optional.ofNullable(hhppMgl.getSubDireccionObj());

        // estrato
        if (subDireccionOpt.isPresent()) {
            SubDireccionMgl subDireccion = subDireccionOpt.get();
            Optional.ofNullable(subDireccion.getSdiEstrato())
                    .ifPresent(estrato -> detalleHHpp.setEstrato(estrato.toString()));
        } else {
            Optional.ofNullable(hhppMgl.getDireccionObj())
                    .flatMap(direccion -> Optional.ofNullable(direccion.getDirEstrato()))
                    .ifPresent(estrato -> detalleHHpp.setEstrato(estrato.toString()));
        }

        // lista de hhpp, Nodo , Estado 
        List<HhppMgl> nodosHhpp = hMhppglDaoImpl.findHhppByDirIdSubDirId(hhppMgl.getDirId(), hhppMgl.getSdiId());

        if (Objects.isNull(nodosHhpp)) {
            String msg = "No se encontraron nodos para el HHPP: " + Optional.ofNullable(hhppMgl.getHhpId())
                    .map(BigDecimal::toString).orElse("");
            LOGGER.warn(msg);
            return detalleHHpp;
        }

        nodosHhpp.stream()
                .filter(nodo -> nodo.getCmtTecnologiaSubId() != null
                        && nodo.getCmtTecnologiaSubId().getTecnoSubedificioId() != null)
                .forEach(nodo -> {
                    String identificador = nodo.getNodId().getNodTipo().getIdentificadorInternoApp();
                    String codigo = nodo.getNodId().getNodCodigo();
                    String estado = nodo.getEhhId() != null ? nodo.getEhhId().getEhhID() : "";

                    switch (identificador) {
                        case Constant.DTH:
                            detalleHHpp.setDth(codigo);
                            detalleHHpp.setEstadoDth(estado);
                            break;
                        case Constant.FIBRA_FTTTH:
                            detalleHHpp.setFtt(codigo);
                            detalleHHpp.setEstadoFtt(estado);
                            break;
                        case Constant.FIBRA_OP_GPON:
                            detalleHHpp.setFog(codigo);
                            detalleHHpp.setEstadoFog(estado);
                            break;
                        case Constant.FIBRA_OP_UNI:
                            detalleHHpp.setFou(codigo);
                            detalleHHpp.setEstadoFou(estado);
                            break;
                        case Constant.HFC_BID:
                            detalleHHpp.setBid(codigo);
                            detalleHHpp.setEstadoBid(estado);
                            break;
                        case Constant.HFC_UNI:
                            detalleHHpp.setUni(codigo);
                            detalleHHpp.setEstadoUni(estado);
                            break;
                        case Constant.LTE_INTERNET:
                        case Constant.RED_FO:
                            detalleHHpp.setLte(codigo);
                            detalleHHpp.setEstadoLte(estado);
                            break;
                        default:
                            detalleHHpp.setMov(codigo);
                            detalleHHpp.setEstadoMov(estado);
                            break;
                    }
                });

        return detalleHHpp;
    }

    /**
     * Busca un listado de HHPP apartir de un listado de direcciones detalladas
     *
     * @param direccionDetalladaList
     * @return List<HhppMgl> listado de hhpp correspondiente a las direcciones
     * detalladas recibidas
     * @author Juan David Hernandez Torres
     */
    public List<HhppMgl> obtenerHhppByDireccionDetallaList(List<CmtDireccionDetalladaMgl> direccionDetalladaList) {
        try {
            List<HhppMgl> hhppList = new ArrayList();
            //las direcciones detalladas encontradas extraemos la direccion y subdireccion
            if (direccionDetalladaList != null && !direccionDetalladaList.isEmpty()) {

                for (CmtDireccionDetalladaMgl dirDetallada : direccionDetalladaList) {
                    //Obtenemos los Hhpp de la Subdireccion  
                    if (dirDetallada.getSubDireccion() != null
                            && dirDetallada.getSubDireccion().getSdiId() != null) {

                        List<HhppMgl> hhhpSubDirList = findHhppSubDireccion(dirDetallada.getSubDireccion().getSdiId());

                        if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                            hhhpSubDirList.stream().filter((hhppSubMgl) -> (!hhppList.contains(hhppSubMgl))).map((hhppSubMgl) -> {
                                hhppSubMgl.setBarrioHhpp(dirDetallada.getBarrio() != null
                                        ? dirDetallada.getBarrio() : null);
                                return hhppSubMgl;
                            }).map((hhppSubMgl) -> {
                                hhppSubMgl.setDireccionDetalladaHhpp(dirDetallada.getDireccionTexto());
                                return hhppSubMgl;
                            }).forEachOrdered((hhppSubMgl) -> {
                                hhppList.add(hhppSubMgl);
                            });
                        }

                    } else {
                        //Obtenemos los Hhpp de la Direccion principal    
                        if (dirDetallada.getDireccion() != null
                                && dirDetallada.getDireccion().getDirId() != null) {

                            List<HhppMgl> hhhpDirList
                                    = findHhppDireccion(dirDetallada.getDireccion().getDirId());

                            if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                                hhhpDirList.stream().filter((hhppMgl) -> (!hhppList.contains(hhppMgl))).map((hhppMgl) -> {
                                    hhppMgl.setBarrioHhpp(dirDetallada.getBarrio() != null
                                            ? dirDetallada.getBarrio() : null);
                                    return hhppMgl;
                                }).map((hhppMgl) -> {
                                    hhppMgl.setDireccionDetalladaHhpp(dirDetallada.getDireccionTexto());
                                    return hhppMgl;
                                }).forEachOrdered((hhppMgl) -> {
                                    hhppList.add(hhppMgl);
                                });
                            }
                        }
                    }

                }
            }
            return hhppList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            return null;
        }
    }

    /**
     * Metodo para obtener listado de hhpp por codigo del nodo buscando primero
     * el nodo obteniendo el id y despues filtrando por id en tabla
     *
     * @author Juan David Hernandez
     * @param codigoNodo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<HhppMgl> findHhppByNodo(String codigoNodo) throws ApplicationException {
        NodoMglManager nodoMglManager = new NodoMglManager();

        NodoMgl nodoMgl = nodoMglManager.findByCodigo(codigoNodo);
        if (nodoMgl != null) {
            return hMhppglDaoImpl.findHhppByNodoId(nodoMgl.getNodId());
        } else {
            return null;
        }
    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de su Nodo,
     * Direcci&oacute;n y SubDirecci&oacute;n.
     *
     * @param idNodo Identificador del Nodo.
     * @param idDireccion Identificador de la Direcci&oacute;n.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n.
     * @return Listado de HHPP coincidente con el Nodo, Direcci&oacute;n y
     * SubDirecci&oacute;n.
     * @throws ApplicationException
     */
    public List<HhppMgl> findHhppByNodoDireccionYSubDireccion(BigDecimal idNodo, BigDecimal idDireccion, BigDecimal idSubDireccion)
            throws ApplicationException {
        return hMhppglDaoImpl.findHhppByNodoDireccionYSubDireccion(idNodo, idDireccion, idSubDireccion);
    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de una cuenta
     * matriz
     *
     * @param cuentaMatriz Identificador del Nodo.
     * @return Listado de HHPP coincidente con la cuenta matriz.
     * @throws ApplicationException
     */
    public List<HhppMgl> obtenerHhppCM(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {

        List<HhppMgl> lstHhppMgls = hMhppglDaoImpl.obtenerHhppCM(cuentaMatriz);
        List<HhppMgl> retorno;
        if (lstHhppMgls != null && !lstHhppMgls.isEmpty()) {
            retorno = new ArrayList<>();
            lstHhppMgls.stream().map((hhppMgl) -> {
                if (hhppMgl.getDireccionObj() != null && hhppMgl.getSubDireccionObj() != null) {
                    hhppMgl.setDireccionDetalladaHhpp(hhppMgl.getSubDireccionObj().getDireccionDetallada().getDireccionTexto());
                } else if (hhppMgl.getDireccionObj() != null && hhppMgl.getSubDireccionObj() == null) {
                    hhppMgl.setDireccionDetalladaHhpp(hhppMgl.getDireccionObj().getDireccionDetallada().getDireccionTexto());
                }
                return hhppMgl;
            }).forEachOrdered((hhppMgl) -> {
                retorno.add(hhppMgl);
            });
        }
        return lstHhppMgls;
    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos unica direccion y subdireccion
     * no repetidos por varias tecnologias .
     *
     * @param cuentaMatriz
     * @return Listado de HHPP
     * @throws ApplicationException
     */
    public List<HhppMgl> obtenerHhppCMUnicaDirAndSub(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {

        List<HhppMgl> lstHhppMgls = hMhppglDaoImpl.obtenerHhppCMUnicaDirAndSub(cuentaMatriz);
        List<HhppMgl> retorno = null;

        if (lstHhppMgls != null && !lstHhppMgls.isEmpty()) {

            Iterator HhppMglListIt = lstHhppMgls.iterator();
            retorno = new ArrayList<>();
            while (HhppMglListIt.hasNext()) {
                Object[] hhpp = (Object[]) HhppMglListIt.next();
                BigDecimal idDireccion = (BigDecimal) hhpp[0];
                BigDecimal idSubDireccion = (BigDecimal) hhpp[1];
                List<HhppMgl> hhppMgl = hMhppglDaoImpl.findHhppByDirIdSubDirId(idDireccion, idSubDireccion);
                if (hhppMgl != null && !hhppMgl.isEmpty()) {
                    HhppMgl hhppMgl1 = hhppMgl.get(0);
                    if (hhppMgl1.getDireccionObj() != null && hhppMgl1.getSubDireccionObj() != null) {
                        hhppMgl1.setDireccionDetalladaHhpp(hhppMgl1.getSubDireccionObj().getDireccionDetallada().getDireccionTexto());
                    } else if (hhppMgl1.getDireccionObj() != null && hhppMgl1.getSubDireccionObj() == null) {
                        hhppMgl1.setDireccionDetalladaHhpp(hhppMgl1.getDireccionObj().getDireccionDetallada().getDireccionTexto());
                    }
                    retorno.add(hhppMgl1);
                }
            }
        }
        return retorno;

    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos unica direccion y subdireccion
     * no repetidos por varias tecnologias por id subedificio .
     *
     * @param HhppSubEdificioId
     * @return Listado de HHPP
     * @throws ApplicationException
     */
    public List<HhppMgl> findHhppBySubEdificioIdUnicaDirAndSub(BigDecimal HhppSubEdificioId) {

        List<HhppMgl> lstHhppMgls = hMhppglDaoImpl.findHhppBySubEdificioIdUnicaDirAndSub(HhppSubEdificioId);
        List<HhppMgl> retorno = null;
        if (lstHhppMgls != null && !lstHhppMgls.isEmpty()) {
            Iterator HhppMglListIt = lstHhppMgls.iterator();
            retorno = new ArrayList<>();
            while (HhppMglListIt.hasNext()) {
                Object[] hhpp = (Object[]) HhppMglListIt.next();
                BigDecimal idDireccion = (BigDecimal) hhpp[0];
                BigDecimal idSubDireccion = (BigDecimal) hhpp[1];
                List<HhppMgl> hhppMgl = hMhppglDaoImpl.findHhppByDirIdSubDirId(idDireccion, idSubDireccion);
                if (hhppMgl != null && !hhppMgl.isEmpty()) {
                    HhppMgl hhppMgl1 = hhppMgl.get(0);
                    if (hhppMgl1.getDireccionObj() != null && hhppMgl1.getSubDireccionObj() != null) {
                        hhppMgl1.setDireccionDetalladaHhpp(hhppMgl1.getSubDireccionObj().getDireccionDetallada().getDireccionTexto());
                    } else if (hhppMgl1.getDireccionObj() != null && hhppMgl1.getSubDireccionObj() == null) {
                        hhppMgl1.setDireccionDetalladaHhpp(hhppMgl1.getDireccionObj().getDireccionDetallada().getDireccionTexto());
                    }
                    retorno.add(hhppMgl1);
                }
            }
        }
        return retorno;
    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de su Nodo,
     * Direcci&oacute;n y SubDirecci&oacute;n. Autor: Victor Bocanegra
     *
     * @param idNodo Identificador del Nodo.
     * @param idDireccion Identificador de la Direcci&oacute;n.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n.
     * @return Listado de HHPP coincidente con el Nodo, Direcci&oacute;n y
     * SubDirecci&oacute;n.
     * @throws ApplicationException
     */
    public List<HhppMgl> findHhppByNodoDireccionAndSubDireccion(BigDecimal idNodo, BigDecimal idDireccion, BigDecimal idSubDireccion) throws ApplicationException {

        return hMhppglDaoImpl.findHhppByNodoDireccionAndSubDireccion(idNodo, idDireccion, idSubDireccion);
    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de la
     * tecnologia del Nodo, Direcci&oacute;n y SubDirecci&oacute;n. Autor:
     * Victor Bocanegra
     *
     * @param tecnologia Identificador de la tecnologia.
     * @param idDireccion Identificador de la Direcci&oacute;n.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n.
     * @return Listado de HHPP coincidente con la Tecnologia, Direcci&oacute;n y
     * SubDirecci&oacute;n.
     * @throws ApplicationException
     */
    public List<HhppMgl> findHhppByTecnoAndDireccionAndSubDireccion(BigDecimal tecnologia, BigDecimal idDireccion, BigDecimal idSubDireccion)
            throws ApplicationException {

        return hMhppglDaoImpl.findHhppByTecnoAndDireccionAndSubDireccion(tecnologia, idDireccion, idSubDireccion);
    }
}
