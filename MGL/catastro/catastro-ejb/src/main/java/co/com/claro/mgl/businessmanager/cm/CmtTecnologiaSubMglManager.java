package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtBasicaMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtTecnologiaSubMglDaoImpl;
import co.com.claro.mgl.dtos.CmtPenetracionCMDto;
import co.com.claro.mgl.dtos.CmtPestanaPenetracionDto;
import co.com.claro.mgl.dtos.HhppTecnologiaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtFlujosInicialesCm;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 *
 * @author rodriguezluimWS
 */
@Log4j2
@NoArgsConstructor
public class CmtTecnologiaSubMglManager {

    private final CmtTecnologiaSubMglDaoImpl cmtTecnologiaSubMglDaoImpl = new CmtTecnologiaSubMglDaoImpl();
    CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
    //Autor: Victor Bocanegra
    CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
    private static final String NO_APLICA = "NA";
    private static final BigDecimal VAL_DEFECTO_ZERO = new BigDecimal("0");


    public CmtTecnologiaSubMgl findById(String idTecnologiaSub) throws ApplicationException {
        return cmtTecnologiaSubMglDaoImpl.find(idTecnologiaSub);
    }

    /**
     * Permite la actualizacion de los costos de la tabla sub tecnologia Sumando
     * todas las OT realizadas y guardandolo en el objeto TegnologiaSub de la
     * cuenta general de la cuenta matriz
     *
     * @author rodiguezluim
     * @param cuentaMatriz cuenta matriz para buscar las ordenes de trabajo que
     * la afectan.
     * @param tecnologia Tecnologia de las ordenes de trabajo la cual se quiere
     * calcular los costos
     * @param cmtVisitaTecnicaMgl
     * @param perfil
     * @param usuario
     * @throws ApplicationException
     */
    public void actualizarCostosTecnologiaSubEdificioGeneral(CmtCuentaMatrizMgl cuentaMatriz, CmtBasicaMgl tecnologia,
            CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl, String usuario, int perfil) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
        CmtVisitaTecnicaMglManager cmtVisitaTecnicaMglManager = new CmtVisitaTecnicaMglManager();
        CmtOrdenTrabajoMglManager cmtOrdenTrabajoMglManager = new CmtOrdenTrabajoMglManager();

        BigDecimal costosTotalesVT = BigDecimal.ZERO;
        Date fechaVisitaTecnica = null;
        CmtOrdenTrabajoMgl ultimaOrdenVT = null;

        // Busca todas las ordenes de trabajo de una ccmm y tecnologia especifica
        List<CmtOrdenTrabajoMgl> listaOrdenes = cmtOrdenTrabajoMglManager.findByCcmmAndTecnologia(cuentaMatriz, tecnologia);
        for (CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl : listaOrdenes) {

            if (cmtOrdenTrabajoMgl.getTipoOtObj().getEsTipoVT().equalsIgnoreCase("Y")) {
                //Busca la visista tecnica activa de cada una de las ordenes de trabajo
                CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
                if (cmtEstadoxFlujoMglManager.finalizoEstadosxFlujoDto(
                        cmtOrdenTrabajoMgl.getTipoOtObj().getTipoFlujoOt(),
                        cmtOrdenTrabajoMgl.getEstadoInternoObj(), tecnologia)
                        || (cmtOrdenTrabajoMgl.getIdOt().compareTo(cmtVisitaTecnicaMgl.getOtObj().getIdOt()) == 0)) {
                    CmtVisitaTecnicaMgl visitaTecnica = cmtVisitaTecnicaMglManager.findVTActiveByIdOt(cmtOrdenTrabajoMgl);
                    if (visitaTecnica != null) {
                        // Suma iterativamente los costos de cada visita tecnica activa
                        costosTotalesVT = costosTotalesVT.add(visitaTecnica.getCtoManoObra()
                                == null ? BigDecimal.ZERO : visitaTecnica.getCtoManoObra());
                        costosTotalesVT = costosTotalesVT.add(visitaTecnica.getCtoMaterialesRed()
                                == null ? BigDecimal.ZERO : visitaTecnica.getCtoMaterialesRed());
                        costosTotalesVT = costosTotalesVT.add(visitaTecnica.getCostoManoObraDiseno()
                                == null ? BigDecimal.ZERO : visitaTecnica.getCostoManoObraDiseno());
                        costosTotalesVT = costosTotalesVT.add(visitaTecnica.getCostoMaterialesDiseno()
                                == null ? BigDecimal.ZERO : visitaTecnica.getCostoMaterialesDiseno());
                        fechaVisitaTecnica = cmtOrdenTrabajoMgl.getFechaProgramacion();
                        ultimaOrdenVT = cmtOrdenTrabajoMgl;
                    }
                }
            }

        }
        //Busca el sub edificio General y valida que tenga tecnologia sub
        if (!cuentaMatriz.isUnicoSubEdificioBoolean()) {
            CmtSubEdificioMgl subEdificio = cmtSubEdificioMglManager.findSubEdificioGeneralByCuentaMatriz(cuentaMatriz);
            
                CmtTecnologiaSubMgl tecnologiaSub = cmtTecnologiaSubMglDaoImpl.findBySubEdificioTecnologia(subEdificio, tecnologia);
                if (tecnologiaSub.getTecnoSubedificioId() != null) {
                    //Actualiza CMT_TECNOLOGIA_SUB
                    tecnologiaSub.setFlagVisitaTecnica("Y");
                    tecnologiaSub.setFechaVisitaTecnica(fechaVisitaTecnica);
                    tecnologiaSub.setCostoVt(costosTotalesVT);
                    tecnologiaSub.setCostoVisitaTecnica(costosTotalesVT);
                    tecnologiaSub.setOrdenTrabajo(ultimaOrdenVT);
                    tecnologiaSub.setVisitaTecnica(cmtVisitaTecnicaMgl);
                    cmtTecnologiaSubMglDaoImpl.updateCm(tecnologiaSub, usuario, perfil);
               } else {
                //Crear CMT_TECNOLOGIA_SUB
                tecnologiaSub.setSubedificioId(subEdificio);
                CmtBasicaMgl cmtBasicaMglEstadoTecnologia = new CmtBasicaMgl();
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                basicaMgl.setBasicaId(BigDecimal.ONE);
                cmtBasicaMglEstadoTecnologia.setBasicaId(BigDecimal.ONE);
                tecnologiaSub.setBasicaIdEstadosTec(cmtBasicaMglEstadoTecnologia);
                tecnologiaSub.setBasicaIdTecnologias(tecnologia);
                tecnologiaSub.setNodoId(subEdificio.getNodoObj());
                tecnologiaSub.setFechaCreacion(new Date());
                tecnologiaSub.setUsuarioCreacion(subEdificio.getUsuarioCreacion());
                tecnologiaSub.setFlagVisitaTecnica("Y");
                tecnologiaSub.setFechaVisitaTecnica(fechaVisitaTecnica);
                tecnologiaSub.setOrdenTrabajo(ultimaOrdenVT);
                tecnologiaSub.setCostoVt(costosTotalesVT);
                tecnologiaSub.setCostoVisitaTecnica(costosTotalesVT);
                tecnologiaSub.setCostoAcometida(BigDecimal.ZERO);

                tecnologiaSub.setTipoConfDistribObj(basicaMgl);
                tecnologiaSub.setAlimentElectObj(basicaMgl);
                tecnologiaSub.setTipoDistribucionObj(basicaMgl);
                tecnologiaSub.setManejoAscensoresObj(basicaMgl);
                tecnologiaSub.setVoltaje("NA");
                tecnologiaSub.setOtroTipoDistribucion("NA");
                tecnologiaSub.setTapsExternos("NA");
                tecnologiaSub.setMarca("NA");
                tecnologiaSub.setTelefono("0000000");
                tecnologiaSub.setAcondicionamientos("NA");
                tecnologiaSub.setVisitaTecnica(cmtVisitaTecnicaMgl);
                cmtTecnologiaSubMglDaoImpl.createCm(tecnologiaSub, usuario, perfil);
            }
            
        }
    }

    /**
     * Permite la actualizacion de los costos de cada una de las sub tecnologias
     * afectadas por una orden de trabajo, y almacenadolas en su respectiba
     * tecnologia sub de acuerdo al edificio
     *
     * @author rodiguezluim
     * @param ordenTrabajo Orden de trabajo qque cambio de estado y se desean
     * actualizar los costos de las Tecnologias sub
     * @param usuario
     * @param perfil
     * @throws ApplicationException
     */
    public void actualizarCostosSubEdificios(CmtOrdenTrabajoMgl ordenTrabajo,
            String usuario, int perfil) throws ApplicationException {
        CmtVisitaTecnicaMglManager cmtVisitaTecnicaMglManager = new CmtVisitaTecnicaMglManager();
        CmtSubEdificiosVtManager cmtSubEdificiosVtManager = new CmtSubEdificiosVtManager();
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();

        //Busca la visista tecnica activa de la ordene de trabajo
        CmtVisitaTecnicaMgl visitaTecnica = cmtVisitaTecnicaMglManager.findVTActiveByIdOt(ordenTrabajo);
        if (visitaTecnica != null) {
            List<CmtSubEdificiosVt> subEdificiosVt = cmtSubEdificiosVtManager.findByVt(visitaTecnica);
            Date fechaVisitaTecnica = visitaTecnica.getFechaEntregaEdificio();
            
            if (subEdificiosVt != null && subEdificiosVt.size() > 0) {
                // Suma los costos de la visita tecnica y los divide en el total de sub edificios VT
            BigDecimal costosTotalesVT = BigDecimal.ZERO;
            costosTotalesVT = costosTotalesVT.add(visitaTecnica.getCtoManoObra()== null ? new BigDecimal(BigInteger.ZERO) : visitaTecnica.getCtoManoObra());
            costosTotalesVT = costosTotalesVT.add(visitaTecnica.getCtoMaterialesRed() == null ? new BigDecimal(BigInteger.ZERO) : visitaTecnica.getCtoMaterialesRed());
            costosTotalesVT = costosTotalesVT.add(visitaTecnica.getCostoManoObraDiseno() == null ? new BigDecimal(BigInteger.ZERO) : visitaTecnica.getCostoManoObraDiseno());
            costosTotalesVT = costosTotalesVT.add(visitaTecnica.getCostoMaterialesDiseno() == null ? new BigDecimal(BigInteger.ZERO) : visitaTecnica.getCostoMaterialesDiseno());
            
            BigDecimal costosPorEdificioVT = BigDecimal.ZERO;      
            if(!costosTotalesVT.equals(BigDecimal.ZERO) && subEdificiosVt.size() > 0 ){
                  costosPorEdificioVT = costosTotalesVT.divide(new BigDecimal(subEdificiosVt.size()), RoundingMode.FLOOR);            
            }            
           
                for (CmtSubEdificiosVt cmtSubEdificiosVt : subEdificiosVt) {
                    if (cmtSubEdificiosVt.getSubEdificioObj() != null
                            && cmtSubEdificiosVt.getSubEdificioObj().getSubEdificioId() != BigDecimal.ZERO) {
                        BigDecimal idSubEdificio = cmtSubEdificiosVt.getSubEdificioObj().getSubEdificioId();
                        CmtSubEdificioMgl subEdificio = cmtSubEdificioMglManager.findById(idSubEdificio);
                        
                           CmtTecnologiaSubMgl tecnologiaSub = cmtTecnologiaSubMglDaoImpl.findBySubEdificioTecnologia(subEdificio, ordenTrabajo.getBasicaIdTecnologia());
                        if (tecnologiaSub != null && tecnologiaSub.getTecnoSubedificioId() != null) {
                            //Actualiza CMT_TECNOLOGIA_SUB
                            tecnologiaSub.setCostoVt(subEdificio.getCmtCuentaMatrizMglObj().isUnicoSubEdificioBoolean()
                                    ? costosTotalesVT : costosPorEdificioVT);
                            tecnologiaSub.setFlagVisitaTecnica("Y");
                            tecnologiaSub.setFechaVisitaTecnica(fechaVisitaTecnica);
                            cmtTecnologiaSubMglDaoImpl.updateCm(tecnologiaSub, usuario, perfil);
                        } else {
                            //Crear CMT_TECNOLOGIA_SUB
                            crearTecnologiaSub(usuario, perfil, subEdificio, visitaTecnica, ordenTrabajo, costosTotalesVT,
                                    costosPorEdificioVT, cmtSubEdificiosVt);
                        }

                    } else {
                        //Crear CMT_SUB_EDIFICIO
                        CmtBasicaMgl estadoSubEdificio = new CmtBasicaMgl();
                        estadoSubEdificio.setBasicaId(cmtBasicaMglDaoImpl.
                                findByCodigoInternoApp(Constant.BASICA_ESTADO_CON_VISITA_TECNICA).getBasicaId());

                        CmtSubEdificioMgl subEdificio = new CmtSubEdificiosVt().mapearCamposCmtSubEdificioMgl(cmtSubEdificiosVt, estadoSubEdificio);
                        subEdificio.setCmtCuentaMatrizMglObj(ordenTrabajo.getCmObj());
                        subEdificio.setUsuarioCreacion(visitaTecnica.getUsuarioCreacion());
                        subEdificio.setFechaCreacion(new Date());
                        subEdificio = cmtSubEdificioMglManager.create(subEdificio);

                        //Actualizar el sub edificio VT con el id del nuevo subedificio
                        cmtSubEdificiosVt.setSubEdificioObj(subEdificio);
                        cmtSubEdificiosVtManager.update(cmtSubEdificiosVt, usuario, perfil);
                        crearTecnologiaSub(usuario, perfil, subEdificio, visitaTecnica, ordenTrabajo,
                                costosTotalesVT, costosPorEdificioVT, cmtSubEdificiosVt);
                    }
                }
                
                
            }
        }
    }

    private void crearTecnologiaSub(String usuario, int perfil, CmtSubEdificioMgl subEdificio,
            CmtVisitaTecnicaMgl visitaTecnica, CmtOrdenTrabajoMgl ordenTrabajo,
            BigDecimal costosTotalesVT, BigDecimal costosPorEdificioVT, CmtSubEdificiosVt cmtSubEdificiosVt) throws ApplicationException {
        CmtTecnologiaSubMgl tecnologiaSub = generarTecnologiaSub(subEdificio, visitaTecnica, ordenTrabajo, cmtSubEdificiosVt);
        if (subEdificio.getCmtCuentaMatrizMglObj().isUnicoSubEdificioBoolean()) {
            tecnologiaSub.setCostoVt(costosTotalesVT);
            tecnologiaSub.setCostoVisitaTecnica(costosTotalesVT);
        } else {
            tecnologiaSub.setCostoVt(costosPorEdificioVT);
            tecnologiaSub.setCostoVisitaTecnica(costosPorEdificioVT);
        }
        cmtTecnologiaSubMglDaoImpl.createCm(tecnologiaSub, usuario, perfil);
    }

    public CmtTecnologiaSubMgl generarTecnologiaSub(CmtSubEdificioMgl subEdificio,
            CmtVisitaTecnicaMgl visitaTecnica, CmtOrdenTrabajoMgl ordenTrabajo,
            CmtSubEdificiosVt cmtSubEdificiosVt) throws ApplicationException {
        //Crear CMT_TECNOLOGIA_SUB
        CmtBasicaMgl basicaMglTipoFlujo = cmtSubEdificiosVt.getVtObj().getOtObj().getTipoOtObj().getTipoFlujoOt();
        CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = estadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(basicaMglTipoFlujo,
                cmtSubEdificiosVt.getVtObj().getOtObj().getEstadoInternoObj(),
                cmtSubEdificiosVt.getVtObj().getOtObj().getBasicaIdTecnologia());
        
        if(cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj() != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId() != null ){

        CmtTecnologiaSubMgl tecnologiaSub = new CmtTecnologiaSubMgl();
        tecnologiaSub.setBasicaIdEstadosTec(cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
        tecnologiaSub.setSubedificioId(subEdificio);
        tecnologiaSub.setBasicaIdTecnologias(ordenTrabajo.getBasicaIdTecnologia());
        tecnologiaSub.setNodoId(cmtSubEdificiosVt.getNodo());
        tecnologiaSub.setFechaCreacion(new Date());
        tecnologiaSub.setUsuarioCreacion(visitaTecnica.getUsuarioCreacion());
        tecnologiaSub.setVisitaTecnica(visitaTecnica);
        tecnologiaSub.setOrdenTrabajo(ordenTrabajo);
        tecnologiaSub.setFlagVisitaTecnica("Y");
        tecnologiaSub.setFechaVisitaTecnica(visitaTecnica.getFechaEntregaEdificio());
        tecnologiaSub.setCostoAcometida(BigDecimal.ZERO);
        return tecnologiaSub;
        }else{
              throw new ApplicationException("No se encontró el estado al que cambio el flujo para el "
                      + "estado interno seleccionado al cual debe cambiar en esta tecnología.");
        }
    }

    
    public CmtTecnologiaSubMgl generarTecnologiaSubRedFo(CmtSubEdificioMgl subEdificio,
            CmtOrdenTrabajoMgl ordenTrabajo,CmtBasicaMgl cambiaEstadoCmA,String usuarioCreacion) throws ApplicationException {
        //Crear CMT_TECNOLOGIA_SUB
        CmtBasicaMglManager basicaMglManager=new CmtBasicaMglManager();
        CmtTecnologiaSubMgl tecnologiaSub = new CmtTecnologiaSubMgl();
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager
                = new CmtTecnologiaSubMglManager();
        tecnologiaSub.setBasicaIdEstadosTec(cambiaEstadoCmA);
        tecnologiaSub.setSubedificioId(subEdificio);
        tecnologiaSub.setBasicaIdTecnologias(ordenTrabajo.getBasicaIdTecnologia());
        NodoMgl nodoMgl = null;
        
        /*Inicio proseso busqueda nodo
            Forma 1: de las tecnologias Gpon O Unifilar
            Forma 2: de las Cobertura Gpon o unifilar Direccion
            Forma 3: de la Georefenciacion
        
            Forma I:
        */ 
        CmtBasicaMgl CmtBasicaMglTecFoGp = basicaMglManager.
                findByCodigoInternoApp(Constant.FIBRA_OP_GPON);
        CmtBasicaMgl CmtBasicaMglTecFoUn = basicaMglManager.
                findByCodigoInternoApp(Constant.FIBRA_OP_UNI);       
        List<CmtTecnologiaSubMgl> cmtTecnologiaSubMglGpList
                = cmtTecnologiaSubMglManager.findTecnoSubBySubEdiTec(subEdificio, CmtBasicaMglTecFoGp);
        List<CmtTecnologiaSubMgl> cmtTecnologiaSubMglUnList
                = cmtTecnologiaSubMglManager.findTecnoSubBySubEdiTec(subEdificio, CmtBasicaMglTecFoUn);
                                
        if (cmtTecnologiaSubMglGpList != null && !cmtTecnologiaSubMglGpList.isEmpty()) {
            nodoMgl = cmtTecnologiaSubMglGpList.get(0).getNodoId();
        } else if (cmtTecnologiaSubMglUnList != null && !cmtTecnologiaSubMglUnList.isEmpty()) {
            nodoMgl = cmtTecnologiaSubMglUnList.get(0).getNodoId();
        }
        
        //Forma II:
       if (nodoMgl == null) {
            NodoMglManager nodoMglManager = new NodoMglManager();
            String NodoGpnString
                    = subEdificio.getCuentaMatrizObj().getDireccionPrincipal().
                           getDireccionObj().getDirNodotres();
            String NodoUniString
                    = subEdificio.getCuentaMatrizObj().getDireccionPrincipal().
                           getDireccionObj().getGeoZonaUnifilar();
            if (NodoGpnString != null && !NodoGpnString.isEmpty()) {
                nodoMgl = nodoMglManager.findByCodigo(NodoGpnString);
            } else if (NodoUniString != null && !NodoUniString.isEmpty()) {
                nodoMgl = nodoMglManager.findByCodigo(NodoUniString);
           }
       }
        
        //Forma III:
        if (nodoMgl == null) {
            NodoMglManager nodoMglManager=new NodoMglManager();
            AddressRequest requestSrv = new AddressRequest();
            DireccionMgl direccionMglCcmm = subEdificio.getCmtCuentaMatrizMglObj().
                    getDireccionPrincipal().getDireccionObj();
            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            requestSrv.setAddress(direccionMglCcmm.getDirFormatoIgac());
            requestSrv.setCodDaneVt(direccionMglCcmm.getUbicacion().getGpoIdObj().
                    getGeoCodigoDane());
            requestSrv.setLevel("C");
            
            if (direccionMglCcmm.getUbicacion() != null 
                    && direccionMglCcmm.getUbicacion().getGeoIdObj() != null
                    && direccionMglCcmm.getUbicacion().getGeoIdObj().getGeoNombre() != null) {
                requestSrv.setNeighborhood(direccionMglCcmm.getUbicacion().getGeoIdObj().
                        getGeoNombre());
            }

            requestSrv.setCity(direccionMglCcmm.getUbicacion().getGpoIdObj().
                    getGpoNombre());
            
            GeograficoPoliticoManager geograficoPoliticoMglManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl geograficoMglMunicipio = geograficoPoliticoMglManager.
                    findById(direccionMglCcmm.getUbicacion().getGpoIdObj().getGeoGpoId());
            GeograficoPoliticoMgl geograficoMgldepartamento=geograficoPoliticoMglManager.
                    findById(geograficoMglMunicipio.getGeoGpoId());
            requestSrv.setState(geograficoMgldepartamento.getGpoNombre());                    
            AddressService addressServiceDir = addressEJBRemote.queryAddress(requestSrv);
            
            String NodoGpnString = addressServiceDir.getNodoTres();
            String NodoUniString = addressServiceDir.getNodoZonaUnifilar();
            if (NodoGpnString != null && !NodoGpnString.isEmpty()) {
                nodoMgl = nodoMglManager.findByCodigo(NodoGpnString);
            } else if (NodoUniString != null && !NodoUniString.isEmpty()) {
                nodoMgl = nodoMglManager.findByCodigo(NodoUniString);
           }                        
        }

        if (nodoMgl == null) {
            throw new ApplicationException("No se encontro Nodo tipo RED FO para crear la Tecnologia");
        }
        NodoMglManager nodoMglManager = new NodoMglManager();
        String CodigonodoRed = "";
        if (nodoMgl.getNodNombre() != null && !nodoMgl.getNodNombre().isEmpty()) {
            CodigonodoRed = nodoMgl.getNodCodigo().substring(
                1, nodoMgl.getNodCodigo().length());
        }
        NodoMgl nodoMglRedFo = null;
        if(CodigonodoRed != null && !CodigonodoRed.isEmpty()){
            nodoMglRedFo = nodoMglManager.findByCodigo("R".concat(CodigonodoRed));
        }
                
        if (nodoMglRedFo == null) {
            throw new ApplicationException("No se encontro Nodo tipo RED FO para crear la Tecnologia");
        }
        
        
        tecnologiaSub.setNodoId(nodoMglRedFo);
        tecnologiaSub.setFechaCreacion(new Date());
        tecnologiaSub.setUsuarioCreacion(usuarioCreacion);
        tecnologiaSub.setVisitaTecnica(null);
        tecnologiaSub.setOrdenTrabajo(ordenTrabajo);
        tecnologiaSub.setFlagVisitaTecnica("Y");
        tecnologiaSub.setFechaVisitaTecnica(null);
        tecnologiaSub.setCostoAcometida(BigDecimal.ZERO);        
        tecnologiaSub.setCostoVt(VAL_DEFECTO_ZERO);
        tecnologiaSub.setCostoAcometida(VAL_DEFECTO_ZERO);
        tecnologiaSub.setFlagVisitaTecnica("N");
        tecnologiaSub.setVoltaje("NA");
        tecnologiaSub.setOtroTipoDistribucion(NO_APLICA);
        tecnologiaSub.setTapsExternos(NO_APLICA);
        tecnologiaSub.setMarca(NO_APLICA);
        tecnologiaSub.setTelefono("0000000");
        tecnologiaSub.setAcondicionamientos(NO_APLICA);
        return tecnologiaSub;
    }
    
    
    /**
     * Actualizar el campo META de las tecnologis sub edificio correspondientes
     * a una VT activa
     *
     * @author rodiguezluim
     * @param visitaTecnica
     * @param usuario
     * @param perfil
     * @throws ApplicationException
     */
    public void guardarMetaSubEdificios(CmtVisitaTecnicaMgl visitaTecnica, String usuario, int perfil) throws ApplicationException {
        CmtSubEdificiosVtManager cmtSubEdificiosVtManager = new CmtSubEdificiosVtManager();
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();

        List<CmtSubEdificiosVt> subEdificiosVt = cmtSubEdificiosVtManager.findByVt(visitaTecnica);
        for (CmtSubEdificiosVt cmtSubEdificiosVt : subEdificiosVt) {
            BigDecimal idSubEdificio = cmtSubEdificiosVt.getSubEdificioObj().getSubEdificioId();
            CmtSubEdificioMgl subEdificio = cmtSubEdificioMglManager.findById(idSubEdificio);
            CmtTecnologiaSubMgl tecnologiaSub = cmtTecnologiaSubMglDaoImpl.findBySubEdificioTecnologia(subEdificio, visitaTecnica.getOtObj().getBasicaIdTecnologia());
            if (tecnologiaSub != null && tecnologiaSub.getTecnoSubedificioId() != null) {
                     tecnologiaSub.setMeta(visitaTecnica.getMeta());
                cmtTecnologiaSubMglDaoImpl.updateCm(tecnologiaSub, usuario, perfil);
            }
        }
    }

    /**
     * Actualizar el campo FECHA HABILITACION de las tecnologis sub edificio
     * correspondientes a una VT activa
     *
     * @author rodiguezluim
     * @param visitaTecnica
     * @param usuario
     * @param perfil
     * @throws ApplicationException
     */
    public void guardarFechaHabilitacionSubEdificios(CmtVisitaTecnicaMgl visitaTecnica, String usuario, int perfil) throws ApplicationException {
        CmtSubEdificiosVtManager cmtSubEdificiosVtManager = new CmtSubEdificiosVtManager();
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();

        List<CmtSubEdificiosVt> subEdificiosVt = cmtSubEdificiosVtManager.findByVt(visitaTecnica);
        for (CmtSubEdificiosVt cmtSubEdificiosVt : subEdificiosVt) {
            BigDecimal idSubEdificio = cmtSubEdificiosVt.getSubEdificioObj().getSubEdificioId();
            CmtSubEdificioMgl subEdificio = cmtSubEdificioMglManager.findById(idSubEdificio);
            CmtTecnologiaSubMgl tecnologiaSub = cmtTecnologiaSubMglDaoImpl.findBySubEdificioTecnologia(subEdificio, visitaTecnica.getOtObj().getBasicaIdTecnologia());
            if(tecnologiaSub != null && tecnologiaSub.getTecnoSubedificioId()!= null){
            tecnologiaSub.setFechaHabilitacion(visitaTecnica.getFechaEntregaEdificio());
            cmtTecnologiaSubMglDaoImpl.updateCm(tecnologiaSub, usuario, perfil);
            }
        }
    }

    /**
     * Actualizar el campo TIEMPO DE RECUPERACION de las tecnologis sub edificio
     * correspondientes a una VT activa
     *
     * @author rodiguezluim
     * @param visitaTecnica
     * @param usuario
     * @param perfil
     * @throws ApplicationException
     */
    public void guardarTiempoRecuperacionSubEdificios(CmtVisitaTecnicaMgl visitaTecnica, String usuario, int perfil) throws ApplicationException {
        CmtSubEdificiosVtManager cmtSubEdificiosVtManager = new CmtSubEdificiosVtManager();
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();

        List<CmtSubEdificiosVt> subEdificiosVt = cmtSubEdificiosVtManager.findByVt(visitaTecnica);
        for (CmtSubEdificiosVt cmtSubEdificiosVt : subEdificiosVt) {
            BigDecimal idSubEdificio = cmtSubEdificiosVt.getSubEdificioObj().getSubEdificioId();
            CmtSubEdificioMgl subEdificio = cmtSubEdificioMglManager.findById(idSubEdificio);
            CmtTecnologiaSubMgl tecnologiaSub = cmtTecnologiaSubMglDaoImpl.findBySubEdificioTecnologia(subEdificio, visitaTecnica.getOtObj().getBasicaIdTecnologia());
           if(tecnologiaSub != null && tecnologiaSub.getTecnoSubedificioId() != null){
            tecnologiaSub.setTiempoRecuperacion(visitaTecnica.getTiempoRecuperacion());
            cmtTecnologiaSubMglDaoImpl.updateCm(tecnologiaSub, usuario, perfil);
           }
        }
    }

    /**
     * Busca las tecnologias asociadas a un subedificio.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl SubEdificio
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    public List<CmtTecnologiaSubMgl> findTecnoSubBySubEdi(
            CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        return cmtTecnologiaSubMglDaoImpl.findTecnoSubBySubEdi(cmtSubEdificioMgl);
    }

    /**
     * Busca las tecnologias asociadas a un subedificio por tecnologia.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl
     * @param cmtBasicaMgl
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    public List<CmtTecnologiaSubMgl> findTecnoSubBySubEdiTec(CmtSubEdificioMgl cmtSubEdificioMgl, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {
        return cmtTecnologiaSubMglDaoImpl.findTecnoSubBySubEdiTec(cmtSubEdificioMgl, cmtBasicaMgl);
    }

    /**
     * Busca las tecnologias asociadas a un subedificio por tecnologia.
     *
     * @author cardenaslb
     * @param params
     * @param cmtSubEdificioMgl
     * @param maxResults
     * @param paginaSelected
     * @param findBy
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    public CmtPestanaPenetracionDto findListTecnologiasPenetracionSubCM(HashMap<String, Object> params, int paginaSelected,
            int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        CmtPestanaPenetracionDto cmtPestanaPenetracionDto = new CmtPestanaPenetracionDto();

        switch (findBy) {
            case CUENTA_MATRIZ:
                cmtPestanaPenetracionDto = getListaPenetracionCM(params, paginaSelected, maxResults, cmtSubEdificioMgl, findBy);
                break;
            case SUB_EDIFICIO:
                cmtPestanaPenetracionDto = getListaPenetracionSub(params, paginaSelected, maxResults, cmtSubEdificioMgl, findBy);
                break;
            case CUENTA_MATRIZ_SOLO_CONTAR:
                cmtPestanaPenetracionDto.setNumRegistros(cmtTecnologiaSubMglDaoImpl.countListPenetracionTecCM(params,
                        cmtSubEdificioMgl, findBy));
                break;
            case SUB_EDIFICIO_SOLO_CONTAR:
                cmtPestanaPenetracionDto.setNumRegistros(cmtTecnologiaSubMglDaoImpl.countListPenetracionTecSub(params,
                        cmtSubEdificioMgl, findBy));
                break;
        }
        return cmtPestanaPenetracionDto;
    }

   /**
     * Metodo que construye el DTO CmtPenetracionCMDto con el conteo de hhpp y
     * clientes activos
     *
     * @param params
     * @param paginaSelected
     * @param cmtSubEdificioMgl subedificio o cuenta matriz
     * @param maxResults
     * @param findBy
     *
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtPestanaPenetracionDto getListaPenetracionCM(HashMap<String, Object> params, int paginaSelected,
            int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        CmtPestanaPenetracionDto cmtPestanaPenetracionDto = new CmtPestanaPenetracionDto();
        CmtPenetracionCMDto cmtPenetracionCMDto;
        List<CmtTecnologiaSubMgl> listaTecnologiasPenetracion = cmtTecnologiaSubMglDaoImpl.findListPenetracionTecCM(params, paginaSelected, maxResults, cmtSubEdificioMgl, findBy);
        Collections.sort(listaTecnologiasPenetracion, (CmtTecnologiaSubMgl obj1, CmtTecnologiaSubMgl obj2) -> obj1.getBasicaIdTecnologias().getBasicaId().compareTo(obj2.getBasicaIdTecnologias().getBasicaId()));
  
        List<CmtPenetracionCMDto> listaTecnologias = new ArrayList<>();
        
        if (!listaTecnologiasPenetracion.isEmpty()) {
            for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : listaTecnologiasPenetracion) {
                cmtPenetracionCMDto = new CmtPenetracionCMDto();
                HhppTecnologiaDto hhppxTec = cmtTecnologiaSubMglDaoImpl.countHhppTecCM(cmtTecnologiaSubMgl, findBy);
                HhppTecnologiaDto clientesActivosxTec = cmtTecnologiaSubMglDaoImpl.countClientesActivosCM(cmtTecnologiaSubMgl.getSubedificioId(), findBy);
                cmtPenetracionCMDto.setTecnologia(cmtTecnologiaSubMgl.getBasicaIdTecnologias() != null ? cmtTecnologiaSubMgl.getBasicaIdTecnologias().getNombreBasica() : "");
                cmtPenetracionCMDto.setTecnologiaBasicaId(cmtTecnologiaSubMgl.getBasicaIdTecnologias() != null ? cmtTecnologiaSubMgl.getBasicaIdTecnologias().getBasicaId() : null);
                cmtPenetracionCMDto.setEstadoTecnologia(cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getNombreBasica());
                cmtPenetracionCMDto.setCostoVT(cmtTecnologiaSubMgl.getCostoVt());
                cmtPenetracionCMDto.setCostoAcometida(cmtTecnologiaSubMgl.getCostoAcometida());
                cmtPenetracionCMDto.setCantHhpp(hhppxTec.getTotalHhpp() == 0 ? 0 : hhppxTec.getTotalHhpp());
                cmtPenetracionCMDto.setTotalClientes(clientesActivosxTec.getTotalClientesActivos() == 0 ? 0 : clientesActivosxTec.getTotalClientesActivos());
                cmtPenetracionCMDto.setRentaMensual(clientesActivosxTec.getRentaMensualCfm() == null ? BigDecimal.ZERO : clientesActivosxTec.getRentaMensualCfm());
                cmtPenetracionCMDto.setIdVt(cmtTecnologiaSubMgl.getVisitaTecnica() == null || cmtTecnologiaSubMgl.getVisitaTecnica().getIdVt() == null ? "" : cmtTecnologiaSubMgl.getVisitaTecnica().getIdVt().toString());
                cmtPenetracionCMDto.setPorcPenetracion(hhppxTec.getTotalHhpp() == 0 || clientesActivosxTec.getTotalClientesActivos() == 0 ? BigDecimal.ZERO
                        : (new BigDecimal(new BigDecimal(clientesActivosxTec.getTotalClientesActivos()).doubleValue() / (new BigDecimal(hhppxTec.getTotalHhpp())).doubleValue())).setScale(2, RoundingMode.CEILING));
                cmtPenetracionCMDto.setCumplimiento(cmtTecnologiaSubMgl.getMeta() == null || cmtTecnologiaSubMgl.getMeta().equals(BigDecimal.ZERO) || clientesActivosxTec.getTotalClientesActivos() == 0 ? BigDecimal.ZERO
                        : (new BigDecimal((new BigDecimal(clientesActivosxTec.getTotalClientesActivos())).doubleValue() / (cmtTecnologiaSubMgl.getMeta().doubleValue()))).setScale(2, RoundingMode.CEILING));
                cmtPenetracionCMDto.setMeta(cmtTecnologiaSubMgl.getMeta() == null ? BigDecimal.ZERO : cmtTecnologiaSubMgl.getMeta());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                cmtPenetracionCMDto.setFechaHabilitacion(cmtTecnologiaSubMgl.getFechaHabilitacion() != null ? dateFormat.format(cmtTecnologiaSubMgl.getFechaHabilitacion()):"");
                cmtPenetracionCMDto.setTiempoRecuperacion(cmtTecnologiaSubMgl.getTiempoRecuperacion() == null ? BigDecimal.ZERO : cmtTecnologiaSubMgl.getTiempoRecuperacion());
                cmtPenetracionCMDto.setCmtTecnologiaSubMgl(cmtTecnologiaSubMgl);
                listaTecnologias.add(cmtPenetracionCMDto);
                cmtPestanaPenetracionDto.setListaPenetracionTecnologias(listaTecnologias);
            }
        }

        return cmtPestanaPenetracionDto;
    }
	
    /**
     * Metodo que construye el DTO CmtPenetracionCMDto con conteo de hhpp,
     * clientes activos
     *
     * @param params
     * @param paginaSelected
     * @param cmtSubEdificioMgl subedificio o cuenta matriz
     * @param maxResults
     * @param findBy
     *
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtPestanaPenetracionDto getListaPenetracionSub(HashMap<String, Object> params, int paginaSelected,
            int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        CmtPestanaPenetracionDto cmtPestanaPenetracionDto = new CmtPestanaPenetracionDto();
        CmtPenetracionCMDto cmtPenetracionCMDto;
        // lista de registros en TecnologiaSubedificio segun el subedificio
        List<CmtTecnologiaSubMgl> listaTecnologiasPenetracion = cmtTecnologiaSubMglDaoImpl.findListPenetracionTecCM(params, paginaSelected, maxResults, cmtSubEdificioMgl, findBy);
        List<CmtPenetracionCMDto> listaTecnologias = new ArrayList<>();

        for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : listaTecnologiasPenetracion) {
            cmtPenetracionCMDto = new CmtPenetracionCMDto();
            //lista de hhpp creados
            HhppTecnologiaDto hhppxTec = cmtTecnologiaSubMglDaoImpl.countHhppTecSub(cmtTecnologiaSubMgl, findBy);
            //lista de hhpp activos
            HhppTecnologiaDto clientesActivosxTec = cmtTecnologiaSubMglDaoImpl.countClientesActivos(cmtTecnologiaSubMgl, findBy);
            cmtPenetracionCMDto.setTecnologia(cmtTecnologiaSubMgl.getBasicaIdTecnologias() != null ? cmtTecnologiaSubMgl.getBasicaIdTecnologias().getNombreBasica() : "");
            cmtPenetracionCMDto.setTecnologiaBasicaId(cmtTecnologiaSubMgl.getBasicaIdTecnologias() != null ? cmtTecnologiaSubMgl.getBasicaIdTecnologias().getBasicaId() : null);
            cmtPenetracionCMDto.setEstadoTecnologia(cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getNombreBasica());
            cmtPenetracionCMDto.setCostoVT(cmtTecnologiaSubMgl.getCostoVt());
            cmtPenetracionCMDto.setCostoAcometida(cmtTecnologiaSubMgl.getCostoAcometida());
            cmtPenetracionCMDto.setCantHhpp(hhppxTec.getTotalHhpp() == 0 ? 0 : hhppxTec.getTotalHhpp());
            cmtPenetracionCMDto.setTotalClientes(clientesActivosxTec.getTotalClientesActivos() == 0 ? 0 : clientesActivosxTec.getTotalClientesActivos());
            cmtPenetracionCMDto.setRentaMensual(clientesActivosxTec.getRentaMensualCfm() == null ? BigDecimal.ZERO : clientesActivosxTec.getRentaMensualCfm());
            cmtPenetracionCMDto.setIdVt(cmtTecnologiaSubMgl.getVisitaTecnica() == null || cmtTecnologiaSubMgl.getVisitaTecnica().getIdVt() == null ? "" : cmtTecnologiaSubMgl.getVisitaTecnica().getIdVt().toString());
            int totalHhpp = 0;
            if (hhppxTec.getTotalHhpp() != 0) {
                totalHhpp = hhppxTec.getTotalHhpp();
                cmtPenetracionCMDto.setCantHhpp(totalHhpp);
            }
            int totalHhppActivos = 0;
            if (clientesActivosxTec.getTotalClientesActivos()  != 0) {
                totalHhppActivos = clientesActivosxTec.getTotalClientesActivos();
                cmtPenetracionCMDto.setTotalClientes(totalHhppActivos);
            }
            BigDecimal activos = null;
            BigDecimal totales = null;
            int compromiso = 0;
            BigDecimal meta = null;
            BigDecimal penetracion = null;
            BigDecimal porcCumplido = null;
            if (totalHhppActivos != 0 && totalHhpp != 0) {
                activos = new BigDecimal(totalHhppActivos * 100);
                totales = new BigDecimal(totalHhpp);
                if (activos != null && totales != null) {
                    penetracion = activos.divide(totales, 2, RoundingMode.HALF_UP);
                }

            } else {
                penetracion = new BigDecimal(BigInteger.ZERO);
            } 
            BigDecimal metaEdif = null;
            if (cmtTecnologiaSubMgl.getMeta() != null && cmtTecnologiaSubMgl.getMeta().compareTo(new BigDecimal(BigInteger.ZERO))== 1) {
                
                metaEdif = cmtTecnologiaSubMgl.getMeta();
                meta = metaEdif.setScale(2, BigDecimal.ROUND_HALF_EVEN);
              
                if (activos != null &&  meta.compareTo(new BigDecimal(BigInteger.ZERO))== 1) {
                    porcCumplido = activos.divide(meta, 2, RoundingMode.HALF_UP);
                }
            }

          
            cmtPenetracionCMDto.setMeta(meta == null ? new BigDecimal(BigInteger.ZERO) : meta);
            cmtPenetracionCMDto.setPorcPenetracion(penetracion);
            cmtPenetracionCMDto.setCumplimiento(porcCumplido == null ? new BigDecimal(BigInteger.ZERO) : porcCumplido);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            cmtPenetracionCMDto.setFechaHabilitacion(cmtTecnologiaSubMgl.getFechaHabilitacion() != null ? dateFormat.format(cmtTecnologiaSubMgl.getFechaHabilitacion()) : "");
            cmtPenetracionCMDto.setTiempoRecuperacion(cmtTecnologiaSubMgl.getTiempoRecuperacion() == null ? BigDecimal.ZERO : cmtTecnologiaSubMgl.getTiempoRecuperacion());
            cmtPenetracionCMDto.setIdOt(cmtTecnologiaSubMgl.getOrdenTrabajo() != null ? cmtTecnologiaSubMgl.getOrdenTrabajo().getIdOt() : BigDecimal.ZERO);
            cmtPenetracionCMDto.setCmtTecnologiaSubMgl(cmtTecnologiaSubMgl);
            listaTecnologias.add(cmtPenetracionCMDto);
        }
          cmtPestanaPenetracionDto.setListaPenetracionTecnologias(listaTecnologias);

        return cmtPestanaPenetracionDto;
    }

    /**
     * Conteo de la lista de Tecnologias en CM para paginacion
     *
     * @author cardenaslb
     * @param params parametros de busqueda
     * @param cmtSubEdificioMgl SubEdificio
     * @param findBy findBy parametro que indica para CM o SubEdificio
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    public int countListPenetracionTecCM(HashMap<String, Object> params, CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        return cmtTecnologiaSubMglDaoImpl.countListPenetracionTecCM(params, cmtSubEdificioMgl, findBy);
    }

    /**
     * Conteo de la lista de Tecnologias en SubEdificio para paginacion
     *
     * @author cardenaslb
     * @param params parametros de busqueda
     * @param cmtSubEdificioMgl SubEdificio
     * @param findBy findBy parametro que indica para CM o SubEdificio
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    public int countListPenetracionTecSub(HashMap<String, Object> params, CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        return cmtTecnologiaSubMglDaoImpl.countListPenetracionTecSub(params, cmtSubEdificioMgl, findBy);
    }

    public CmtTecnologiaSubMgl crear(CmtTecnologiaSubMgl tecnologiaSub, String usuario, int perfil)
            throws ApplicationException {
        return cmtTecnologiaSubMglDaoImpl.createCm(tecnologiaSub, usuario, perfil);
    }

    public CmtTecnologiaSubMgl actualizar(CmtTecnologiaSubMgl tecnologiaSub, String usuario, int perfil)
            throws ApplicationException {
        return cmtTecnologiaSubMglDaoImpl.updateCm(tecnologiaSub, usuario, perfil);
    }

    /**
     * Metodo que crear una tecnologia asociada a un subedificio VT
     *
     * @param cmtSubEdificiosVt
     * @param cmtOrdenTrabajoMgl
     * @param idVt
     * @param usuarioCrea
     * @param perfilCrea
     * @return CmtTecnologiaSubMgl
     * @author Victor Bocanegra
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtTecnologiaSubMgl crearTecSub(CmtSubEdificiosVt cmtSubEdificiosVt,
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl, BigDecimal idVt, String usuarioCrea, int perfilCrea) throws ApplicationException {

        CmtTecnologiaSubMgl cmtTecnologiaSubMgl = new CmtTecnologiaSubMgl();
        cmtTecnologiaSubMgl.setSubedificioId(cmtSubEdificiosVt.getSubEdificioObj());
        cmtTecnologiaSubMgl.setBasicaIdTecnologias(cmtOrdenTrabajoMgl.getBasicaIdTecnologia());
        cmtTecnologiaSubMgl.setNodoId(cmtSubEdificiosVt.getNodo());
        cmtTecnologiaSubMgl.setFechaCreacion(new Date());
        CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl = new CmtVisitaTecnicaMgl();
        cmtVisitaTecnicaMgl.setIdVt(idVt);
        cmtTecnologiaSubMgl.setVisitaTecnica(cmtVisitaTecnicaMgl);
        cmtTecnologiaSubMgl.setOrdenTrabajo(cmtOrdenTrabajoMgl);
        BigDecimal costoAco = BigDecimal.ZERO;
        cmtTecnologiaSubMgl.setCostoAcometida(costoAco);
        cmtTecnologiaSubMgl.setFlagVisitaTecnica("N");

        return cmtTecnologiaSubMglDaoImpl.createCm(cmtTecnologiaSubMgl, usuarioCrea, perfilCrea);
    }

    /**
     * Metodo que lista los sub edificios sin una tecnologia en espesifico que
     * aplicarian para una Orden
     *
     * @param cmtOrdenTrabajoMgl
     * @return List<CmtSubEdificioMgl>
     * @author Carlos Leonardo Villamil Hitss
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtSubEdificioMgl> getSubEdificiosWithOutTecnology(
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) throws ApplicationException {
        List<CmtSubEdificioMgl> cmtSubEdificioMgls = new ArrayList<>();
        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        CmtFlujosInicialesCmManager cmtFlujosInicialesCmManager = new CmtFlujosInicialesCmManager();
        List<CmtFlujosInicialesCm> listaEstadosInicialesFlujoCM = new ArrayList<>();
        // multiedificio
        if (!cmtOrdenTrabajoMgl.getCmObj().isUnicoSubEdificioBoolean()) { 
            if(cmtOrdenTrabajoMgl.getCmObj().
                    getSubEdificiosMglNoGeneral().isEmpty()){
                         cmtSubEdificioMgls.add( cmtOrdenTrabajoMgl.getCmObj().getSubEdificioGeneral());
                  
            } else {
                for (CmtSubEdificioMgl subEdificioMgl : cmtOrdenTrabajoMgl.getCmObj().
                        getSubEdificiosMglNoGeneral()) {
                    if (subEdificioMgl.getListTecnologiasSub() != null
                            && !subEdificioMgl.getListTecnologiasSub().isEmpty()) {
                        boolean encontroTecnologia = false;
                                for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : subEdificioMgl.getListTecnologiasSub()) {

                            if (cmtTecnologiaSubMgl.getEstadoRegistro() == 1
                                    && cmtTecnologiaSubMgl.getBasicaIdTecnologias().getBasicaId().compareTo(
                                    cmtOrdenTrabajoMgl.getBasicaIdTecnologia().getBasicaId()) == 0) {
                                encontroTecnologia = true;
                                  CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglManager.getEstadoInicialFlujo(cmtOrdenTrabajoMgl.getTipoOtObj().getTipoFlujoOt(), cmtOrdenTrabajoMgl.getBasicaIdTecnologia());
                                 listaEstadosInicialesFlujoCM =  cmtFlujosInicialesCmManager.getEstadosIniCMByEstadoxFlujoId(cmtEstadoxFlujoMgl);
                                if (listaEstadosInicialesFlujoCM != null && !listaEstadosInicialesFlujoCM.isEmpty()) {
                                    for (CmtFlujosInicialesCm estadoFlujoCm : listaEstadosInicialesFlujoCM) {
                                        if (estadoFlujoCm.getIdBasicaEstadoCm().getBasicaId().compareTo(
                                                cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getBasicaId()) == 0) {
                                            cmtSubEdificioMgls.add(subEdificioMgl);
                                        } else {
                                            if (!estadoFlujoCm.getIdBasicaEstadoCm().getBasicaId()
                                                    .equals(cmtBasicaMglDaoImpl.findByCodigoInternoApp(
                                                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId())) {
                                                cmtSubEdificioMgls.add(subEdificioMgl);
                                                  break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (!encontroTecnologia) {
                            cmtSubEdificioMgls.add(subEdificioMgl);
                        }
                    } else {
                        cmtSubEdificioMgls.add(subEdificioMgl);
                    }
                }
            }

        } else {
            // unico edificio
            if (cmtOrdenTrabajoMgl.getCmObj().getSubedificioUnicoSubedificio().getListTecnologiasSub() != null
                    && !cmtOrdenTrabajoMgl.getCmObj().getSubedificioUnicoSubedificio().getListTecnologiasSub().isEmpty()) {
                boolean encontroTecnologia = false;
              for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : cmtOrdenTrabajoMgl.getCmObj().
                        getSubedificioUnicoSubedificio().getListTecnologiasSub()) {
                  // se valida si la tecnologia de la ot es la misma de la ccmm
                    if (cmtTecnologiaSubMgl.getEstadoRegistro() == 1
                            && cmtTecnologiaSubMgl.getBasicaIdTecnologias().getBasicaId().compareTo(
                            cmtOrdenTrabajoMgl.getBasicaIdTecnologia().getBasicaId()) == 0) {
                        encontroTecnologia = true;
                        CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglManager.getEstadoInicialFlujo(cmtOrdenTrabajoMgl.getTipoOtObj().getTipoFlujoOt(), cmtOrdenTrabajoMgl.getBasicaIdTecnologia());
                       listaEstadosInicialesFlujoCM = cmtFlujosInicialesCmManager.getEstadosIniCMByEstadoxFlujoId(cmtEstadoxFlujoMgl);
                      
                       if (listaEstadosInicialesFlujoCM != null && !listaEstadosInicialesFlujoCM.isEmpty()) {
                            for (CmtFlujosInicialesCm estadoFlujoCm : listaEstadosInicialesFlujoCM) {
                                 // se valida si el estado inicial del tipo de trabajo es igual al de la tecnologia de la ccmm
                                if (estadoFlujoCm.getIdBasicaEstadoCm().getBasicaId().compareTo(
                                        cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getBasicaId()) == 0) {
                                    cmtSubEdificioMgls.add(cmtOrdenTrabajoMgl.getCmObj().getSubedificioUnicoSubedificio());
                                } else {
                            if (!estadoFlujoCm.getIdBasicaEstadoCm().getBasicaId()
                                            .equals(cmtBasicaMglDaoImpl.findByCodigoInternoApp(
                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId())){
                                        cmtSubEdificioMgls.add(cmtOrdenTrabajoMgl.getCmObj().getSubedificioUnicoSubedificio());
                                           break;
                                    }
                         
                                }
                            } 
                        }                       
                    }
                    
                }
                if (!encontroTecnologia) {
                    cmtSubEdificioMgls.add(cmtOrdenTrabajoMgl.getCmObj().getSubedificioUnicoSubedificio());
                }
            } else {
                CmtSubEdificioMgl unicoedificio = null;
                if (cmtOrdenTrabajoMgl.getCmObj().getListCmtSubEdificioMgl() != null && !cmtOrdenTrabajoMgl.getCmObj().getListCmtSubEdificioMgl().isEmpty()) {
                    for (CmtSubEdificioMgl sub : cmtOrdenTrabajoMgl.getCmObj().getListCmtSubEdificioMgl()) {
                        if (sub.getEstadoRegistro() == 1) {
                            unicoedificio = sub;
                        }
                    }
                }
                cmtSubEdificioMgls.add(unicoedificio);
            }
        }
        //Inicio de ajuste para recorrer la lista y eliminar registros duplicados
        Set<CmtSubEdificioMgl> subEdificio = new HashSet<CmtSubEdificioMgl>(cmtSubEdificioMgls);
        cmtSubEdificioMgls.clear();
        cmtSubEdificioMgls.addAll(subEdificio);
        //Fin del ajuste de registros duplicados
            Collections.sort(cmtSubEdificioMgls, (CmtSubEdificioMgl a, CmtSubEdificioMgl b) -> a.getSubEdificioId().compareTo(b.getSubEdificioId()));
        
        return cmtSubEdificioMgls;
    }

    /**
     * valbuenayf metodo para realizar el reporte de cuentamatriz para el cargue
     * masivo
     *
     * @param params
     * @param first
     * @param pageSize
     * @return
     */
    public List<CmtTecnologiaSubMgl> findReporteGeneralDetallado(Map<String, Object> params, int first, int pageSize) {
        CmtTecnologiaSubMglDaoImpl cmtTecnologiaDao = new CmtTecnologiaSubMglDaoImpl();
        return cmtTecnologiaDao.findReporteGeneralDetallado(params, first, pageSize);
    }

    /**
     * valbuenayf metodo para contar la cantidad de registros del reporte de
     * cuentamatriz para el cargue masivo
     *
     * @param params
     * @return
     */
    public Integer findCountRepGeneralDetallado(Map<String, Object> params) {
        CmtTecnologiaSubMglDaoImpl cmtTecnologiaDao = new CmtTecnologiaSubMglDaoImpl();
        return cmtTecnologiaDao.findCountRepGeneralDetallado(params);
    }

    /**
     * valbuenayf metodo para buscar CmtTecnologiaSubMgl por el id
     *
     * @param idTecnologiaSub
     * @return
     */
    public CmtTecnologiaSubMgl findIdTecnoSub(BigDecimal idTecnologiaSub) {
        return cmtTecnologiaSubMglDaoImpl.findIdTecnoSub(idTecnologiaSub);
    }

    /**
     * Autor: Victor Bocanegra Metodo para la creacion las tecnologias
     * subedificio configuradas en la gestion de la creacion de una CM
     *
     * @param datosGestion
     * @param cmtSubEdificioMgl
     * @param usuario
     * @param perfil
     */
    public void crearTecnSubXGestion(Map<CmtBasicaMgl, NodoMgl> datosGestion,
            CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil) {

        CmtTipoBasicaMgl cmtTipoBasicaMgl;
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaExtMglManager cmtBasicaExtMglManager = new CmtBasicaExtMglManager();
        try {

            cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
            CmtBasicaMgl basicaMglEstMult = new CmtBasicaMgl();
            basicaMglEstMult.setBasicaId(basicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId());
            basicaMglEstMult = basicaMglManager.findById(basicaMglEstMult);

            for (Map.Entry<CmtBasicaMgl, NodoMgl> n : datosGestion.entrySet()) {

                if (cmtSubEdificioMgl.getEstadoSubEdificioObj() != null && cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null &&
                        cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                                equals(basicaMglEstMult.getIdentificadorInternoApp())) {

                    CmtTecnologiaSubMgl cmtTecnologiaSubMgl = new CmtTecnologiaSubMgl();
                    cmtTecnologiaSubMgl.setSubedificioId(cmtSubEdificioMgl);
                    cmtTecnologiaSubMgl.setBasicaIdTecnologias(n.getKey());
                    cmtTecnologiaSubMgl.setNodoId(n.getValue());
                    cmtTecnologiaSubMgl.setFechaCreacion(new Date());
                    cmtTecnologiaSubMgl.setUsuarioCreacion(usuario);
                    cmtTecnologiaSubMgl.setPerfilCreacion(perfil);

                    if (!cmtTecnologiaSubMgl.getBasicaIdTecnologias().
                            getIdentificadorInternoApp()
                            .equals(Constant.RED_FO)) {
                        cmtTecnologiaSubMgl.setBasicaIdEstadosTec(basicaMglEstMult);
                    } else {                        
                        cmtTecnologiaSubMgl.setBasicaIdEstadosTec(
                        cmtTipoBasicaMgl.
                                findBasicaByCampoEntidadAs400ExtendidoAndValorExtendido
                                (Constant.RED_FO,"Y")
                        );
                    }

                    cmtTecnologiaSubMgl.setCostoVt(VAL_DEFECTO_ZERO);
                    cmtTecnologiaSubMgl.setCostoAcometida(VAL_DEFECTO_ZERO);
                    cmtTecnologiaSubMgl.setFlagVisitaTecnica("N");
                    cmtTecnologiaSubMgl.setVoltaje("NA");
                    cmtTecnologiaSubMgl.setOtroTipoDistribucion(NO_APLICA);
                    cmtTecnologiaSubMgl.setTapsExternos(NO_APLICA);
                    cmtTecnologiaSubMgl.setMarca(NO_APLICA);
                    cmtTecnologiaSubMgl.setTelefono("0000000");
                    cmtTecnologiaSubMgl.setAcondicionamientos(NO_APLICA);

                    //Crea el registro del estado de tecnología en CMT_TECNOLOGIA_SUB
                    cmtTecnologiaSubMgl = crear(cmtTecnologiaSubMgl, usuario, perfil);

                    if (cmtTecnologiaSubMgl.getTecnoSubedificioId() != null) {
                        LOGGER.info("TECNOLOGIA SUBEDIFICIO AL GENERAL "
                                + "CREADA SATISFACTORIAMENTE");
                    } else {

                        LOGGER.error("OCURRIO UN ERROR "
                                + "AL MOMENTO DE CREAR EL REGISTRO");
                    }
                    
                    if(cmtSubEdificioMgl.getListTecnologiasSub() != null){
                        cmtSubEdificioMgl.getListTecnologiasSub().add(cmtTecnologiaSubMgl);
                    }else{
                        cmtSubEdificioMgl.setListTecnologiasSub(new ArrayList<>());
                        cmtSubEdificioMgl.getListTecnologiasSub().add(cmtTecnologiaSubMgl);
                    }

                } else {
                    if (!cmtTipoBasicaMgl.getListCmtTipoBasicaExtMgl().isEmpty()) {
                        for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl : cmtTipoBasicaMgl.getListCmtTipoBasicaExtMgl()) {
                            if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().equalsIgnoreCase
                                    (n.getKey().getIdentificadorInternoApp())) {

                                List<CmtBasicaExtMgl> estadosDeLaTecno = cmtBasicaExtMglManager.findByTipoBasicaExt(cmtTipoBasicaExtMgl);
                                //Valido cual es el estado igual al del subedificio y que este en 'Y'
                                List<CmtBasicaExtMgl> estadosValidosTecnologia = estadosDeLaTecno.stream()
                                        .filter(Objects::nonNull)
                                        .filter(cmtBasicaExtMgl -> Objects.nonNull(Optional.of(cmtBasicaExtMgl)
                                                .map(CmtBasicaExtMgl::getIdBasicaObj)
                                                .map(CmtBasicaMgl::getBasicaId).orElse(null))
                                                && cmtBasicaExtMgl.getValorExtendido() != null
                                                && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")
                                                && !(cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400()
                                                .equalsIgnoreCase(Constant.RED_FO)
                                                && !cmtSubEdificioMgl.getCmtCuentaMatrizMglObj()
                                                .getUnicoEdif().equalsIgnoreCase("SI")))
                                        .collect(Collectors.toList());

                                boolean isTechFtth = Optional.ofNullable(n.getKey())
                                        .map(CmtBasicaMgl::getCodigoBasica).orElse(StringUtils.EMPTY).equals("FTTH");

                                if (isTechFtth) {
                                    //Filtrar estados no validos para FTTH, para evitar duplicidades.
                                    estadosValidosTecnologia = estadosValidosTecnologia.stream()
                                            .filter(estado -> StringUtils.isNotBlank(
                                                    estado.getIdBasicaObj().getIdentificadorInternoApp()))
                                            .filter(estado -> !estado.getIdBasicaObj()
                                                    .getIdentificadorInternoApp().equals("NA"))
                                            .collect(Collectors.toList());
                                }

                                for (CmtBasicaExtMgl cmtBasicaExtMgl : estadosValidosTecnologia) {
                                            //Crea la tecnologia sub para la tecnologia que aplique
                                            CmtTecnologiaSubMgl cmtTecnologiaSubMgl = new CmtTecnologiaSubMgl();
                                            cmtTecnologiaSubMgl.setSubedificioId(cmtSubEdificioMgl);
                                            cmtTecnologiaSubMgl.setBasicaIdTecnologias(n.getKey());
                                            cmtTecnologiaSubMgl.setNodoId(n.getValue());
                                            cmtTecnologiaSubMgl.setFechaCreacion(new Date());
                                            cmtTecnologiaSubMgl.setUsuarioCreacion(usuario);
                                            cmtTecnologiaSubMgl.setPerfilCreacion(perfil);

                                            cmtTecnologiaSubMgl.setBasicaIdEstadosTec(cmtBasicaExtMgl.getIdBasicaObj());

                                            cmtTecnologiaSubMgl.setCostoVt(VAL_DEFECTO_ZERO);
                                            cmtTecnologiaSubMgl.setCostoAcometida(VAL_DEFECTO_ZERO);
                                            cmtTecnologiaSubMgl.setFlagVisitaTecnica("N");
                                            cmtTecnologiaSubMgl.setVoltaje("NA");
                                            cmtTecnologiaSubMgl.setOtroTipoDistribucion(NO_APLICA);
                                            cmtTecnologiaSubMgl.setTapsExternos(NO_APLICA);
                                            cmtTecnologiaSubMgl.setMarca(NO_APLICA);
                                            cmtTecnologiaSubMgl.setTelefono("0000000");
                                            cmtTecnologiaSubMgl.setAcondicionamientos(NO_APLICA);

                                            cmtTecnologiaSubMgl = crear(cmtTecnologiaSubMgl, usuario, perfil);

                                            if (cmtTecnologiaSubMgl.getTecnoSubedificioId() != null) {
                                                LOGGER.info("TECNOLOGIA SUBEDIFICIO "
                                                        + "CREADA SATISFACTORIAMENTE");
                                            } else {

                                                LOGGER.error("OCURRIO UN ERROR "
                                                        + "AL MOMENTO DE CREAR EL REGISTRO");
                                            }
                                            
                                            if (cmtSubEdificioMgl.getListTecnologiasSub() != null) {
                                                cmtSubEdificioMgl.getListTecnologiasSub().add(cmtTecnologiaSubMgl);
                                            } else {
                                                cmtSubEdificioMgl.setListTecnologiasSub(new ArrayList<>());
                                                cmtSubEdificioMgl.getListTecnologiasSub().add(cmtTecnologiaSubMgl);
                                            }

                                }
                            }

                        }
                    }
                }

            }

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }


    }

    /**
     * Buscar una sub tecnologia basandose en un subedificio y la tecnologia
     *
     * @author rodriguezluim
     * @param cmtSubEdificioMgl
     * @param cmtBasicaMgl
     * @return Un elemento de tipo CmtTecnologiaSub
     */
    public CmtTecnologiaSubMgl findBySubEdificioTecnologia
        (CmtSubEdificioMgl cmtSubEdificioMgl, CmtBasicaMgl cmtBasicaMgl){

        return cmtTecnologiaSubMglDaoImpl.findBySubEdificioTecnologia(cmtSubEdificioMgl, cmtBasicaMgl);

    }

    /**
     * Borra una tecnologia asociada a un subedificio
     *
     * @author Victor Bocanegra
     * @param cmtTecnologiaSubMgl
     * @param subEdificio Condicional por el cual voy a borrar
     * @param usuario
     * @param perfil
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void deleteSubEdificioTecnologia(CmtTecnologiaSubMgl cmtTecnologiaSubMgl, String usuario, int perfil)
            throws ApplicationException {

        cmtTecnologiaSubMglDaoImpl.deleteCm(cmtTecnologiaSubMgl, usuario, perfil);

    }

    /**
     * Actualiza una tecnologia sub asociada a un subedificio
     *
     * @author Victor Bocanegra
     * @param cmtTecnologiaSubMgl objeto que se va actualizar
     * @param usuario
     * @param perfil
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public CmtTecnologiaSubMgl updateTecnoSub(CmtTecnologiaSubMgl cmtTecnologiaSubMgl,
            String usuarioMod, int perfilMod) throws ApplicationException {
        return cmtTecnologiaSubMglDaoImpl.updateCm(cmtTecnologiaSubMgl, usuarioMod, perfilMod);
    }
    
      /**
     * Permite la actualizacion de los costos de la tabla sub tecnologia Sumando
     * todas las OT realizadas y guardandolo en el objeto TegnologiaSub de la
     * cuenta general de la cuenta matriz
     *
     * @author Victor Bocanegra
     * @param cuentaMatriz cuenta matriz para buscar las ordenes de trabajo que
     * la afectan.
     * @param tecnologia Tecnologia de la orden de trabajo a la cual se quiere
     * actualizar los costos
     * @param acometidareal costos de acometida real
     * @param usuario
     * @param perfil
     * @return 
     * @throws ApplicationException
     */
    public boolean actualizarCostosAcometidasTecnologiaSubEdificioGeneral(CmtCuentaMatrizMgl cuentaMatriz, 
            CmtBasicaMgl tecnologia,
            CmtVisitaTecnicaMgl acometidareal, String usuario, int perfil)
            throws ApplicationException {

        BigDecimal costosTotalesAco = BigDecimal.ZERO;
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
        CmtOrdenTrabajoMglManager cmtOrdenTrabajoMglManager = new CmtOrdenTrabajoMglManager();
        CmtVisitaTecnicaMglManager cmtVisitaTecnicaMglManager = new CmtVisitaTecnicaMglManager();

        boolean respuesta = false;
        
        // Busca todas las ordenes de trabajo de una ccmm y tecnologia especifica
        List<CmtOrdenTrabajoMgl> listaOrdenes = cmtOrdenTrabajoMglManager.findByCcmmAndTecnologia(cuentaMatriz, tecnologia);
        for (CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl : listaOrdenes) {

            if (!cmtOrdenTrabajoMgl.getTipoOtObj().getEsTipoVT().equalsIgnoreCase("Y")) {
                //Busca la visista tecnica activa de cada una de las ordenes de trabajo
                CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
                if (acometidareal != null) {
                    if (cmtEstadoxFlujoMglManager.finalizoEstadosxFlujoDto(
                            cmtOrdenTrabajoMgl.getTipoOtObj().getTipoFlujoOt(),
                            cmtOrdenTrabajoMgl.getEstadoInternoObj(), tecnologia)
                            || (cmtOrdenTrabajoMgl.getIdOt().compareTo(acometidareal.getOtObj().getIdOt()) == 0)) {
                       CmtVisitaTecnicaMgl visitaTecnica = cmtVisitaTecnicaMglManager.
                                    findVTActiveByIdOt(cmtOrdenTrabajoMgl);
                        if (visitaTecnica != null) {
                            // Suma iterativamente los costos de cada visita tecnica activa
                            costosTotalesAco = costosTotalesAco.add(visitaTecnica.getCtoManoObra()
                                    == null ? BigDecimal.ZERO : visitaTecnica.getCtoManoObra());
                            costosTotalesAco = costosTotalesAco.add(visitaTecnica.getCtoMaterialesRed()
                                    == null ? BigDecimal.ZERO : visitaTecnica.getCtoMaterialesRed());
                            costosTotalesAco = costosTotalesAco.add(visitaTecnica.getCostoManoObraDiseno()
                                    == null ? BigDecimal.ZERO : visitaTecnica.getCostoManoObraDiseno());
                            costosTotalesAco = costosTotalesAco.add(visitaTecnica.getCostoMaterialesDiseno()
                                    == null ? BigDecimal.ZERO : visitaTecnica.getCostoMaterialesDiseno());
                        }
                    }
                }
            }
        }
        //Busca el sub edificio General y valida que tenga tecnologia sub
        if (!cuentaMatriz.isUnicoSubEdificioBoolean()) {
            CmtSubEdificioMgl subEdificio = cmtSubEdificioMglManager.findSubEdificioGeneralByCuentaMatriz(cuentaMatriz);
           
              CmtTecnologiaSubMgl tecnologiaSub = cmtTecnologiaSubMglDaoImpl.findBySubEdificioTecnologia(subEdificio, tecnologia);
            if (tecnologiaSub != null && tecnologiaSub.getTecnoSubedificioId() != null) {
                //Actualiza CMT_TECNOLOGIA_SUB
                tecnologiaSub.setCostoAcometida(costosTotalesAco);
                cmtTecnologiaSubMglDaoImpl.updateCm(tecnologiaSub, usuario, perfil);
                respuesta = true;
            }
           
        }
        return respuesta;
    }

    /**
     * Permite la actualizacion de los costos totales de acometida de cada una
     * de las sub tecnologias afectadas por una orden de trabajo, y
     * almacenadolas en su respectiva tecnologia sub de acuerdo al edificio
     *
     * @author Victor Bocanegra
     * @param subEdificiosVtList lista de subedificios para actualizar los
     * costos de las Tecnologias sub
     * @param acometidaReal costos reales de la acometida
     * @param usuario usuario que realiza la operacion
     * @param perfil del usuario
     * @return 
     * @throws ApplicationException
     */
    public boolean actualizarCostosAcometidaSubEdificios(List<CmtSubEdificiosVt> subEdificiosVtList,
            CmtVisitaTecnicaMgl acometidaReal, String usuario, int perfil) throws ApplicationException {

        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
        boolean respuesta = false;
        // Suma los costos totales de la acometida  y los divide en el total de sub edificios VT
        BigDecimal costosTotalesAco = BigDecimal.ZERO;
        costosTotalesAco = costosTotalesAco.add(acometidaReal.getCtoManoObra());
        costosTotalesAco = costosTotalesAco.add(acometidaReal.getCtoMaterialesRed());
        costosTotalesAco = costosTotalesAco.add(acometidaReal.getCostoManoObraDiseno());
        costosTotalesAco = costosTotalesAco.add(acometidaReal.getCostoMaterialesDiseno());
        BigDecimal costosPorEdificioVT = costosTotalesAco.divide(new BigDecimal(subEdificiosVtList.size()), RoundingMode.FLOOR);

        for (CmtSubEdificiosVt cmtSubEdificiosVt : subEdificiosVtList) {
            if (cmtSubEdificiosVt.getSubEdificioObj() != null
                    && cmtSubEdificiosVt.getSubEdificioObj().
                    getSubEdificioId() != BigDecimal.ZERO) {

                BigDecimal idSubEdificio = cmtSubEdificiosVt.getSubEdificioObj().getSubEdificioId();
                CmtSubEdificioMgl subEdificio = cmtSubEdificioMglManager.findById(idSubEdificio);

                CmtTecnologiaSubMgl tecnologiaSub
                        = cmtTecnologiaSubMglDaoImpl.findBySubEdificioTecnologia(subEdificio, acometidaReal.getOtObj().getBasicaIdTecnologia());
                if (tecnologiaSub != null && tecnologiaSub.getTecnoSubedificioId() != null) {
                    //Actualiza CMT_TECNOLOGIA_SUB
                    tecnologiaSub.setCostoAcometida(subEdificio.getCmtCuentaMatrizMglObj().isUnicoSubEdificioBoolean()
                            ? costosTotalesAco : costosPorEdificioVT);
                    cmtTecnologiaSubMglDaoImpl.updateCm(tecnologiaSub, usuario, perfil);
                    respuesta = true;
                }

            }
        }
        return respuesta;
    }
    
    /**
     * Busca las tecnologias asociadas a un nodo.
     *
     * @author Victor Bocanegra
     * @param nodo
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    public List<CmtTecnologiaSubMgl> findTecnoSubByNodo(NodoMgl nodo) throws ApplicationException {

        return cmtTecnologiaSubMglDaoImpl.findTecnoSubByNodo(nodo);
    }
    
    
        /**
     * Busca las tecnologias asociadas a un subedificio por tecnologia.
     *
     * @author cardenaslb
     * @param params
     * @param cmtTecnologiaSubMgl
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    public CmtPestanaPenetracionDto findListCMxTecnologias(HashMap<String, Object> params, CmtTecnologiaSubMgl cmtTecnologiaSubMgl) throws ApplicationException {
        CmtPestanaPenetracionDto cmtPestanaPenetracionDto = new CmtPestanaPenetracionDto();
    
        return cmtPestanaPenetracionDto;
    }
    
     /**
     * Metodo que construye el DTO CmtPenetracionCMDto con el conteo de hhpp y
     * clientes activos
     *
     * @param params
     *
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtPestanaPenetracionDto getListaCMxTec(HashMap<String, Object> params) throws ApplicationException {
        
        /**
         * Variables
         */
        List<Object[]> listaCMxTecno;
        CmtPenetracionCMDto cmtPenetracionCMDto;
        CmtPestanaPenetracionDto cmtPestanaPenetracionDto = new CmtPestanaPenetracionDto();
        List<CmtPenetracionCMDto> listaTecnologias = new ArrayList<CmtPenetracionCMDto>();
        CmtTecnologiaSubMglDaoImpl cmtTecnologiaSubMglDaoImpl1 = new CmtTecnologiaSubMglDaoImpl();
        // lista de cuenta matrices
        listaCMxTecno = cmtTecnologiaSubMglDaoImpl1.findListCMxTecno(params);

        /**
         * Se recorre la lista para calcular los valores de retorno al Export
         */
        for (Object[] cmtCuentaMatrizMgl : listaCMxTecno) {
            
            cmtPenetracionCMDto = new CmtPenetracionCMDto();
            
            cmtPenetracionCMDto.setIdCm(((BigDecimal) cmtCuentaMatrizMgl[4]).intValue()+"");
            cmtPenetracionCMDto.setNumeroCuentaCm(((BigDecimal) cmtCuentaMatrizMgl[5]).intValue()+"");
            cmtPenetracionCMDto.setNombreCm((String) cmtCuentaMatrizMgl[6]);
            cmtPenetracionCMDto.setNombreTorre((String) cmtCuentaMatrizMgl[7]);
            cmtPenetracionCMDto.setDireccionCm((String) cmtCuentaMatrizMgl[8]);
            cmtPenetracionCMDto.setBarrio((String) cmtCuentaMatrizMgl[9]);
            cmtPenetracionCMDto.setCiudad((String) cmtCuentaMatrizMgl[10]);
            cmtPenetracionCMDto.setCentroPoblado((String) cmtCuentaMatrizMgl[11]);
            cmtPenetracionCMDto.setNumeroTorres(((BigDecimal) cmtCuentaMatrizMgl[12]).intValue());
            cmtPenetracionCMDto.setNumeroPisos(((BigDecimal) cmtCuentaMatrizMgl[13]).intValue());
            cmtPenetracionCMDto.setNodo((String) cmtCuentaMatrizMgl[2]);
            cmtPenetracionCMDto.setEstrato((String) cmtCuentaMatrizMgl[14]);
            cmtPenetracionCMDto.setTecnologia((String) cmtCuentaMatrizMgl[15]);
            cmtPenetracionCMDto.setEstadoTecnologia((String) cmtCuentaMatrizMgl[16]);
            int totalHhpp = 0;
            if (cmtCuentaMatrizMgl[17] != null) {
                totalHhpp = ((BigDecimal) cmtCuentaMatrizMgl[17]).intValue();
                cmtPenetracionCMDto.setCantHhpp(((BigDecimal) cmtCuentaMatrizMgl[17]).intValue());
            }
            int totalHhppActivos = 0;
            if (cmtCuentaMatrizMgl[18] != null) {
                totalHhppActivos = ((BigDecimal) cmtCuentaMatrizMgl[18]).intValue();
                cmtPenetracionCMDto.setTotalClientes(((BigDecimal) cmtCuentaMatrizMgl[18]).intValue());
            }
            BigDecimal activos = null;
            BigDecimal totales = null;
            int compromiso = 0;
            BigDecimal meta = null;
            BigDecimal penetracion = null;
            BigDecimal porcCumplido = null;
            if (totalHhppActivos != 0 && totalHhpp != 0) {
                activos = new BigDecimal(totalHhppActivos * 100);
                totales = new BigDecimal(totalHhpp);
                if (totales != null && activos != null) {
                    if (cmtCuentaMatrizMgl[7] != null && !cmtCuentaMatrizMgl[7].equals("MULTIEDIFICIO")) {
                        penetracion = activos.divide(totales, 2, RoundingMode.HALF_UP);
                    }
                }

            } else {
                penetracion = new BigDecimal(BigInteger.ZERO);
            } 
            BigDecimal metaEdif = null;
            if (cmtCuentaMatrizMgl[21] != null) {
                metaEdif = (BigDecimal) cmtCuentaMatrizMgl[21];
                meta = metaEdif.setScale(2, BigDecimal.ROUND_HALF_EVEN);
              
                if (activos != null && meta != null) {
                    porcCumplido = activos.divide(meta, 2, RoundingMode.HALF_UP);
                }
            }
            cmtPenetracionCMDto.setMeta(meta == null ? new BigDecimal(BigInteger.ZERO) :meta );
             if (cmtCuentaMatrizMgl[7] != null && !cmtCuentaMatrizMgl[7].equals("MULTIEDIFICIO")) {
                  cmtPenetracionCMDto.setPorcPenetracion(penetracion);
             }
            cmtPenetracionCMDto.setCumplimiento(porcCumplido == null ?  new BigDecimal(BigInteger.ZERO): porcCumplido);
            cmtPenetracionCMDto.setCostoAcometida((BigDecimal) cmtCuentaMatrizMgl[22]);
            cmtPenetracionCMDto.setIdVt(cmtCuentaMatrizMgl[23]==null ? "" :((BigDecimal) cmtCuentaMatrizMgl[23]).intValue()+"");
            cmtPenetracionCMDto.setIdOt((BigDecimal) cmtCuentaMatrizMgl[24]);
            cmtPenetracionCMDto.setCostoVT((BigDecimal) cmtCuentaMatrizMgl[25]);
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            
            String dateString = "";
            if(cmtCuentaMatrizMgl[26]!=null){
                dateString = formatter.format((Date) cmtCuentaMatrizMgl[26]);
            }
            cmtPenetracionCMDto.setFechaHabilitacion(cmtCuentaMatrizMgl[27]==null ? "" :(dateString));
            cmtPenetracionCMDto.setTiempoRecuperacion((BigDecimal) cmtCuentaMatrizMgl[27]);
            cmtPenetracionCMDto.setRentaMensual((BigDecimal) cmtCuentaMatrizMgl[28]);
            
            listaTecnologias.add(cmtPenetracionCMDto);
            
            cmtPestanaPenetracionDto.setListaPenetracionTecnologias(listaTecnologias);
        }
        return cmtPestanaPenetracionDto;


    }
    
    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */    
    private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    public CmtTecnologiaSubMgl crearTecnSubXPenetracion(CmtSubEdificioMgl subEdificioMgl, NodoMgl nodoSeleccionado, CmtBasicaMgl basicaIdEstadosTec, String usuarioVT, int perfilVT) {
        
        CmtTecnologiaSubMgl cmtTecnologiaSubMgl = new CmtTecnologiaSubMgl();
        try {  
            cmtTecnologiaSubMgl.setSubedificioId(subEdificioMgl);
            cmtTecnologiaSubMgl.setBasicaIdTecnologias(nodoSeleccionado.getNodTipo());
            cmtTecnologiaSubMgl.setNodoId(nodoSeleccionado);
            cmtTecnologiaSubMgl.setFechaCreacion(new Date());
            cmtTecnologiaSubMgl.setUsuarioCreacion(usuarioVT);
            cmtTecnologiaSubMgl.setPerfilCreacion(perfilVT);

            cmtTecnologiaSubMgl.setBasicaIdEstadosTec(basicaIdEstadosTec);

            cmtTecnologiaSubMgl.setCostoVt(VAL_DEFECTO_ZERO);
            cmtTecnologiaSubMgl.setCostoAcometida(VAL_DEFECTO_ZERO);
            cmtTecnologiaSubMgl.setFlagVisitaTecnica("N");
            cmtTecnologiaSubMgl.setVoltaje("NA");
            cmtTecnologiaSubMgl.setOtroTipoDistribucion(NO_APLICA);
            cmtTecnologiaSubMgl.setTapsExternos(NO_APLICA);
            cmtTecnologiaSubMgl.setMarca(NO_APLICA);
            cmtTecnologiaSubMgl.setTelefono("0000000");
            cmtTecnologiaSubMgl.setAcondicionamientos(NO_APLICA);
            CmtTecnologiaSubMglManager tecSubManager = new CmtTecnologiaSubMglManager();
            cmtTecnologiaSubMgl = tecSubManager.crear(cmtTecnologiaSubMgl, usuarioVT, perfilVT);
            if (subEdificioMgl.getListTecnologiasSub() != null) {
                subEdificioMgl.getListTecnologiasSub().add(cmtTecnologiaSubMgl);
            } else {
                subEdificioMgl.setListTecnologiasSub(new ArrayList<>());
                subEdificioMgl.getListTecnologiasSub().add(cmtTecnologiaSubMgl);
        }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }
       return cmtTecnologiaSubMgl;
    }
}
