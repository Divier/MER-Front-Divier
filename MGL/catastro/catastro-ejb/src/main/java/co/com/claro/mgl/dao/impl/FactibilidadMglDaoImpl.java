/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtBasicaMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtDireccionDetalleMglDaoImpl;
import co.com.claro.mgl.dtos.CmtGeograficoPoliticoDto;
import co.com.claro.mgl.dtos.CmtReporteFactibilidadDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author bocanegravm
 */
public class FactibilidadMglDaoImpl extends GenericDaoImpl<FactibilidadMgl> {

    private static final Logger LOGGER = LogManager.getLogger(FactibilidadMglDaoImpl.class);
    
    /**
     * Metodo para consultar una factibilidad vigente en BD
     * Autor: Victor Bocanegra
     * @param idDetallada
     * @param fechaHoy
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<FactibilidadMgl> findFactibilidadVigByDetallada
        (BigDecimal idDetallada, Date fechaHoy) throws ApplicationException {

        try {
            Query query = entityManager.createQuery("SELECT f FROM FactibilidadMgl f  "
                    + " WHERE f.direccionDetalladaId =  :direccionDetalladaId "
                    + " AND f.fechaVencimiento >= :fechaHoy");

            query.setParameter("direccionDetalladaId", idDetallada);
            query.setParameter("fechaHoy", fechaHoy);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }
        
    /**
     * Metodo para consultar una factibilidad vigente en BD Autor: Victor
     * Bocanegra
     *
     * @param idFactibilidad
     * @param fechaHoy
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException exception de registros inexistentes
     */
    public FactibilidadMgl findFactibilidadVigById(BigDecimal idFactibilidad, Date fechaHoy) throws ApplicationException {

        try {
            Query query = entityManager.createQuery("SELECT f FROM FactibilidadMgl f  "
                    + " WHERE f.factibilidadId =  :factibilidadId "
                    + " AND f.fechaVencimiento >= :fechaHoy");

            query.setParameter("factibilidadId", idFactibilidad);
            query.setParameter("fechaHoy", fechaHoy);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (FactibilidadMgl) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar la factibilidad. EX000: " + e.getMessage(), e);
            return null;
        }
    }
    
    public List<FactibilidadMgl> findFactibilidadByUsuario(String usuario) throws ApplicationException {

        try {
            Query query = entityManager.createQuery("SELECT f FROM FactibilidadMgl f  "
                    + " WHERE f.usuario =  :usuario "
                    + " AND f.fechaVencimiento >= :fechaHoy");

            query.setParameter("usuario", usuario);
            query.setParameter("fechaHoy", new Date());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public int findNumeroSolicitudes(String usuario) throws ApplicationException {
        int numeroSol = 0;
        try {
            Query query = entityManager.createQuery("SELECT COUNT(f) FROM FactibilidadMgl f  "
                    + " WHERE f.usuario =  :usuario " );

            query.setParameter("usuario", usuario);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            numeroSol = (int) query.getSingleResult();
            return numeroSol;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return numeroSol;
        }
    }
    
     public List<FactibilidadMgl> findFactibilidadByDirDetallada(BigDecimal dirDet) throws ApplicationException{
        try {
            Query query = entityManager.createQuery("SELECT f FROM FactibilidadMgl f  "
                    + " WHERE f.direccionDetalladaId =  :direccionDetalladaId ");

            query.setParameter("direccionDetalladaId", dirDet);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar listado de factibilidad. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
     }
     
     private String getGenerarQueryJPQL(
            String campo,
            List<String> listaTecnologias,
            List<String> listaNombreFactibilidad,
            Date fechaInicial, Date fechaFin1DiaMes) {

        StringBuilder queryJpql = new StringBuilder("");
        queryJpql.append("Select ");
        queryJpql.append(campo);
        queryJpql.append(" from DetalleFactibilidadMgl df, ");
        queryJpql.append("FactibilidadMgl f, CmtDireccionDetalladaMgl dt, ");
        queryJpql.append("DireccionMgl d, UbicacionMgl u ");
        queryJpql.append("where df.factibilidadMglObj.factibilidadId = f.factibilidadId ");
        queryJpql.append("AND f.direccionDetalladaId = dt.direccionDetalladaId ");
        queryJpql.append("AND dt.direccion.dirId = d.dirId ");
        queryJpql.append("AND d.ubicacion.ubiId = u.ubiId ");
        queryJpql.append("AND u.gpoIdObj.gpoId IN :listacentroPoblado ");

        if (listaTecnologias != null && (!listaTecnologias.isEmpty())) {
            queryJpql.append("and df.nombreTecnologia IN :listaTecnologias ");
        }
        if (listaNombreFactibilidad != null && (!listaNombreFactibilidad.isEmpty())) {
           queryJpql.append("and df.claseFactibilidad IN :listaNombreFactibilidad ");
        }
        if ((fechaInicial != null && fechaFin1DiaMes != null) && (fechaInicial.before(fechaFin1DiaMes))) {
            queryJpql.append("and df.factibilidadMglObj.fechaCreacion BETWEEN :fechaInicio and :fechaFinal ");
        }
        return queryJpql.toString();
    }
     
     
     /**
     * Metodo para consultar una factibilidad vigente en BD 
     * cardenaslb
     * Bocanegra
     *
     * @param params
     * @param pagina
     * @param totalResultados
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<CmtReporteFactibilidadDto> getReporte(HashMap<String, Object> params, int pagina, int totalResultados) throws ApplicationException {
        CmtReporteFactibilidadDto cmtReporteFactibilidadDto;
        CmtDireccionDetalleMglDaoImpl cmtDireccionDetalleMglDaoImpl = new CmtDireccionDetalleMglDaoImpl();
        CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
        HhppMglDaoImpl hhppMglDaoImpl = new HhppMglDaoImpl();
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();

        List<CmtReporteFactibilidadDto> listaCmtReporteFactibilidadDto = new ArrayList<>();
        List<String> listaTecnologias = (List<String>) params.get("tecnologia");
        List<String> listaIdCentroPoblado = (List<String>) params.get("centroPoblado");
        List<String> listaNombreFactibilidad = obtenerNombreFactibilidad((List<String>) params.get("estadoFactSelected"));
        Date fechaInicial = null;
        Date fechaFin1DiaMes = null;
        Date fechaFinal;

        if (((Date) params.get("fechaInicio") != null)) {
            fechaInicial = ((Date) params.get("fechaInicio"));
        }
        if (((Date) params.get("fechaFinal") != null)) {
            fechaFinal = ((Date) params.get("fechaFinal"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaFinal);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fechaFin1DiaMes = calendar.getTime();
        }

        try {
            final String campo = "df";
            final String queryString = getGenerarQueryJPQL(campo, listaTecnologias, listaNombreFactibilidad, fechaInicial, fechaFin1DiaMes);

            Query query = entityManager.createQuery(queryString);
            query.setFirstResult(pagina);
            query.setMaxResults(totalResultados);

            if (listaTecnologias != null) {
                if (!listaTecnologias.isEmpty()) {
                    query.setParameter("listaTecnologias", listaTecnologias);
                }
            }
            if (listaNombreFactibilidad != null) {
                if (!listaNombreFactibilidad.isEmpty()) {
                    query.setParameter("listaNombreFactibilidad", listaNombreFactibilidad);
                }
            }
            if ((fechaInicial != null && fechaFin1DiaMes != null)) {
                if (fechaInicial.before(fechaFin1DiaMes)) {
                    query.setParameter("fechaInicio", fechaInicial);
                }
            }
            if ((fechaInicial != null && fechaFin1DiaMes != null)) {
                if (fechaInicial.before(fechaFin1DiaMes)) {
                    query.setParameter("fechaFinal", fechaFin1DiaMes);
                }
            }
            if (listaIdCentroPoblado != null) {
                if (!listaIdCentroPoblado.isEmpty()) {
                    query.setParameter("listacentroPoblado", listaIdCentroPoblado);
                }
            }

            List<DetalleFactibilidadMgl> list = query.getResultList();

            List<CmtGeograficoPoliticoDto> ciudadesList = geograficoPoliticoManager.findListCiudades(listaIdCentroPoblado);
            if (list != null) {
                if (!list.isEmpty()) {
                    for (DetalleFactibilidadMgl detalleFactibilidad : list) {

                        cmtReporteFactibilidadDto = new CmtReporteFactibilidadDto();
                        CmtDireccionDetalladaMgl diDetallada = cmtDireccionDetalleMglDaoImpl.findByIdDetallada(detalleFactibilidad.getFactibilidadMglObj().getDireccionDetalladaId());

                        DrDireccion drDir;
                        String complemento;
                        String apartamento;
                        if (diDetallada != null) {
                            drDir = cmtDireccionDetalleMglManager.parseCmtDireccionDetalladaMglToDrDireccion(diDetallada);
                            complemento = getComplementoDireccion(drDir);
                            apartamento = getApartamentoDireccion(drDir);

                            CmtCuentaMatrizMgl cuentaMatrizMgl = null;

                            if (diDetallada.getCmtDireccion() != null) {
                                if (!diDetallada.getCmtDireccion().isEmpty()) {
                                    cuentaMatrizMgl = diDetallada.getCmtDireccion().get(0).getCuentaMatrizObj();
                                }
                            }

                            List<CmtSubEdificioMgl> listSubedificio;
                            CmtSubEdificioMgl cmtSubEdificioMgl;
                            String nombreSubedificio;

                            if (cuentaMatrizMgl != null) {
                                cmtReporteFactibilidadDto.setIsCm("CCMM");

                                //busqueda de subedificios de la cuenta matriz
                                listSubedificio = cuentaMatrizMgl.getListCmtSubEdificioMgl();

                                if (listSubedificio != null) {
                                    if (!listSubedificio.isEmpty()) {
                                        //Si es multiedificio
                                        cmtSubEdificioMgl = listSubedificio.get(0);
                                        nombreSubedificio = (listSubedificio.size() > 1) ? complemento.trim() : listSubedificio.get(0).getNombreSubedificio();

                                        cmtReporteFactibilidadDto.setIdMerCcmm(cmtSubEdificioMgl != null ? cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId().toString().trim() : "");
                                        cmtReporteFactibilidadDto.setIdRRCCMM(cmtSubEdificioMgl != null ? cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getNumeroCuenta().toString() : "");
                                        cmtReporteFactibilidadDto.setNombreCCMM(cmtSubEdificioMgl != null ? cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getNombreCuenta() : "");
                                        cmtReporteFactibilidadDto.setSubEdificio(nombreSubedificio);
                                    }
                                }
                            } else {
                                //si la direccion no es una CCMM  
                                cmtReporteFactibilidadDto.setIsCm("");
                                cmtReporteFactibilidadDto.setSubEdificio("");
                                cmtReporteFactibilidadDto.setIdMerCcmm("");
                                cmtReporteFactibilidadDto.setIdRRCCMM("");
                                cmtReporteFactibilidadDto.setNombreCCMM("");

                                SubDireccionMgl subDir = (diDetallada.getSubDireccion() != null) ? diDetallada.getSubDireccion() : null;
                                List<HhppMgl> listHhpp = hhppMglDaoImpl.findByDirAndSubDir(diDetallada.getDireccion(), subDir);

                                if (listHhpp != null && !listHhpp.isEmpty()) {
                                    cmtReporteFactibilidadDto.setCuentacliente(listHhpp.get(0).getSuscriptor() != null ? listHhpp.get(0).getSuscriptor() : "");
                                } else {
                                    cmtReporteFactibilidadDto.setCuentacliente("");
                                }

                            }

                            cmtReporteFactibilidadDto.setIdFactibilidad(detalleFactibilidad.getFactibilidadMglObj().getFactibilidadId() != null ? detalleFactibilidad.getFactibilidadMglObj().getFactibilidadId().toString() : null);
                            cmtReporteFactibilidadDto.setDirDetallada(detalleFactibilidad.getFactibilidadMglObj().getDireccionDetalladaId() != null ? detalleFactibilidad.getFactibilidadMglObj().getDireccionDetalladaId() : null);

                            if (diDetallada.getDireccion().getUbicacion() != null
                                    && diDetallada.getDireccion().getUbicacion().getGpoIdObj() != null) {

                                CmtGeograficoPoliticoDto ciudad = ciudadesList.stream()
                                        .filter(p -> p.getCentroPobladoId()
                                        .equals(diDetallada.getDireccion().getUbicacion().getGpoIdObj().getGpoId())).findFirst().get();
                                cmtReporteFactibilidadDto.setCentroPoblado(ciudad.getNombreCentro());
                                cmtReporteFactibilidadDto.setCiudad(ciudad.getNombreCiudad());
                                cmtReporteFactibilidadDto.setDepartamento(ciudad.getNombreDepartamento());

                            } else {
                                cmtReporteFactibilidadDto.setCentroPoblado("");
                                cmtReporteFactibilidadDto.setCiudad("");
                                cmtReporteFactibilidadDto.setDepartamento("");
                            }

                            cmtReporteFactibilidadDto.setComplemento(complemento);
                            cmtReporteFactibilidadDto.setApartamento(apartamento);

                            if (diDetallada.getDireccion().getUbicacion() != null && diDetallada.getDireccion().getUbicacion().getUbiLatitud() != null
                                    && !diDetallada.getDireccion().getUbicacion().getUbiLatitud().isEmpty()) {
                                cmtReporteFactibilidadDto.setCoordenadaLatitud(diDetallada.getDireccion().getUbicacion().getUbiLatitud());
                            } else {
                                cmtReporteFactibilidadDto.setCoordenadaLatitud("");
                            }

                            if (diDetallada.getDireccion().getUbicacion() != null && diDetallada.getDireccion().getUbicacion().getUbiLongitud() != null
                                    && !diDetallada.getDireccion().getUbicacion().getUbiLongitud().isEmpty()) {
                                cmtReporteFactibilidadDto.setCoordenadalongitud(diDetallada.getDireccion().getUbicacion().getUbiLongitud());
                            } else {
                                cmtReporteFactibilidadDto.setCoordenadalongitud("");
                            }

                            cmtReporteFactibilidadDto.setDireccionCompleta(diDetallada.getDireccionTexto());
                            cmtReporteFactibilidadDto.setFechahoraConsulta(detalleFactibilidad.getFactibilidadMglObj().getFechaCreacion() != null ? detalleFactibilidad.getFactibilidadMglObj().getFechaCreacion() : null);
                            cmtReporteFactibilidadDto.setFechaVencimiento(detalleFactibilidad.getFactibilidadMglObj().getFechaVencimiento() != null ? detalleFactibilidad.getFactibilidadMglObj().getFechaVencimiento() : null);
                            cmtReporteFactibilidadDto.setUsuario(detalleFactibilidad.getFactibilidadMglObj().getUsuario() != null ? detalleFactibilidad.getFactibilidadMglObj().getUsuario() : "");
                            cmtReporteFactibilidadDto.setTecnologia(detalleFactibilidad.getNombreTecnologia() != null ? detalleFactibilidad.getNombreTecnologia() : "");
                            cmtReporteFactibilidadDto.setsDS(detalleFactibilidad.getSds() != null ? detalleFactibilidad.getSds() : "");
                            cmtReporteFactibilidadDto.setNodoCobertura(detalleFactibilidad.getCodigoNodo() != null ? detalleFactibilidad.getCodigoNodo() : "");
                            cmtReporteFactibilidadDto.setFactibilidad(detalleFactibilidad.getClaseFactibilidad() != null ? detalleFactibilidad.getClaseFactibilidad() : "");
                            cmtReporteFactibilidadDto.setArrendatario(detalleFactibilidad.getNombreArrendatario() != null ? detalleFactibilidad.getNombreArrendatario() : "");
                            cmtReporteFactibilidadDto.setTiempoInstalacionUM(detalleFactibilidad.getTiempoUltimaLilla() == 0 ? "0" : String.valueOf(detalleFactibilidad.getTiempoUltimaLilla()));
                            cmtReporteFactibilidadDto.setEstadoTecnologia(detalleFactibilidad.getEstadoTecnologia() != null ? detalleFactibilidad.getEstadoTecnologia() : "");
                            cmtReporteFactibilidadDto.setNodoEstado(detalleFactibilidad.getEstadoNodo() != null ? detalleFactibilidad.getEstadoNodo() : "");
                            cmtReporteFactibilidadDto.setNodoAproximado(detalleFactibilidad.getNodoMglAproximado() != null ? detalleFactibilidad.getNodoMglAproximado().getNodCodigo() : "");
                            cmtReporteFactibilidadDto.setDistanciaNodoAproximado(detalleFactibilidad.getDistanciaNodoApro() == 0 ? "0" : String.valueOf(detalleFactibilidad.getDistanciaNodoApro()));
                            cmtReporteFactibilidadDto.setProyecto(detalleFactibilidad.getProyecto() != null ? detalleFactibilidad.getProyecto() : "");
                            /* Brief 98062 */
                            cmtReporteFactibilidadDto.setNodoSitiData(detalleFactibilidad.getNodoSitiData() != null ? detalleFactibilidad.getNodoSitiData() : "");
                            /* Cierre Brief 98062 */
                            /* Brief 117487  */
                            cmtReporteFactibilidadDto.setEstrato(diDetallada.getDireccion().getDirNivelSocioecono());
                            /* Cierre Brief 117487  */
                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                            String strDate = dateFormat.format((Date) detalleFactibilidad.getFactibilidadMglObj().getFechaCreacion());
                            cmtReporteFactibilidadDto.setFechaHoraConsultaReporte((strDate != null && !strDate.equals("")) ? strDate : "");
                            if (detalleFactibilidad.getFactibilidadMglObj().getFechaCreacion() != null && detalleFactibilidad.getFactibilidadMglObj().getFechaVencimiento() != null) {

                                if (detalleFactibilidad.getFactibilidadMglObj().getFechaCreacion().before(detalleFactibilidad.getFactibilidadMglObj().getFechaVencimiento())
                                        && detalleFactibilidad.getFactibilidadMglObj().getFechaVencimiento().after(new Date())) {
                                    cmtReporteFactibilidadDto.setIdFactibilidadVigente(detalleFactibilidad.getFactibilidadMglObj().getFactibilidadId().toString());
                                }
                            }

                            listaCmtReporteFactibilidadDto.add(cmtReporteFactibilidadDto);

                        }
                    }
                }

                if (!listaCmtReporteFactibilidadDto.isEmpty()) {
                    getEntityManager().clear();
                    return listaCmtReporteFactibilidadDto;
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    public String getComplementoDireccion(DrDireccion drDirec) {
        String dirResult = "";

        if (drDirec.getCpTipoNivel1() != null
                && !drDirec.getCpTipoNivel1().isEmpty()
                && drDirec.getCpValorNivel1() != null
                && !drDirec.getCpValorNivel1().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel1()
                    + " " + drDirec.getCpValorNivel1() + " ";
        }

        if (drDirec.getCpTipoNivel2() != null
                && !drDirec.getCpTipoNivel2().isEmpty()
                && drDirec.getCpValorNivel2()
                != null && !drDirec.getCpValorNivel2().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel2()
                    + " " + drDirec.getCpValorNivel2() + " ";
        }

        if (drDirec.getCpTipoNivel3() != null
                && !drDirec.getCpTipoNivel3().isEmpty()
                && drDirec.getCpValorNivel3()
                != null && !drDirec.getCpValorNivel3().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel3()
                    + " " + drDirec.getCpValorNivel3() + " ";
        }

        if (drDirec.getCpTipoNivel4() != null
                && !drDirec.getCpTipoNivel4().isEmpty()
                && drDirec.getCpValorNivel4()
                != null && !drDirec.getCpValorNivel4().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel4()
                    + " " + drDirec.getCpValorNivel4() + " ";
        }

        return dirResult;
    }
         
         public String getApartamentoDireccion(DrDireccion drDirec) {
        String dirResult = "";

        if (drDirec.getCpTipoNivel1() != null
                && !drDirec.getCpTipoNivel1().isEmpty()
                && drDirec.getCpValorNivel1() != null
                && !drDirec.getCpValorNivel1().isEmpty()) {
            dirResult += " ";
        }

        if (drDirec.getCpTipoNivel2() != null
                && !drDirec.getCpTipoNivel2().isEmpty()
                && drDirec.getCpValorNivel2()
                != null && !drDirec.getCpValorNivel2().isEmpty()) {
            dirResult += " ";
        }

        if (drDirec.getCpTipoNivel3() != null
                && !drDirec.getCpTipoNivel3().isEmpty()
                && drDirec.getCpValorNivel3()
                != null && !drDirec.getCpValorNivel3().isEmpty()) {
            dirResult += " ";
        }

        if (drDirec.getCpTipoNivel4() != null
                && !drDirec.getCpTipoNivel4().isEmpty()
                && drDirec.getCpValorNivel4()
                != null && !drDirec.getCpValorNivel4().isEmpty()) {
            dirResult += " ";
        }

        if (drDirec.getCpTipoNivel5() != null
                && !drDirec.getCpTipoNivel5().isEmpty()
                && drDirec.getCpValorNivel5()
                != null && !drDirec.getCpValorNivel5().isEmpty()) {

            if (drDirec.getCpTipoNivel5()
                    .equalsIgnoreCase(Constant.OPT_CASA_PISO)) {

                dirResult += "CASA" + " " + drDirec.getCpValorNivel5() + " ";

            } else if (drDirec.getCpTipoNivel5()
                    .equalsIgnoreCase(Constant.OPT_PISO_INTERIOR)
                    || drDirec.getCpTipoNivel5()
                            .equalsIgnoreCase(Constant.OPT_PISO_LOCAL)
                    || drDirec.getCpTipoNivel5()
                            .equalsIgnoreCase(Constant.OPT_PISO_APTO)) {

                dirResult += "PISO" + " " + drDirec.getCpValorNivel5() + " ";
            } else {
                dirResult += drDirec.getCpTipoNivel5()
                        + " " + drDirec.getCpValorNivel5() + " ";
            }

        }
        // @author Juan David Hernandez 
        if (drDirec.getCpTipoNivel5() != null
                && !drDirec.getCpTipoNivel5().isEmpty()
                && (drDirec.getCpValorNivel5() == null
                || drDirec.getCpValorNivel5().isEmpty())) {
            dirResult += "CASA";
        }
        if (drDirec.getBarrioTxtBM() != null
                && !drDirec.getBarrioTxtBM().isEmpty()) {
            dirResult += "Barrio" + " " + drDirec.getBarrioTxtBM();
        }

        if (drDirec.getCpTipoNivel6() != null
                && !drDirec.getCpTipoNivel6().isEmpty()
                && drDirec.getCpValorNivel6() != null
                && !drDirec.getCpValorNivel6().isEmpty()) {
            dirResult += drDirec.getCpTipoNivel6() + " "
                    + drDirec.getCpValorNivel6() + " ";
        }
        return dirResult;
    }
         
         
    public List<String> obtenerCodigoBasicaTecList(List<String> listaIdTecnologiasSelected) throws ApplicationException {
        List<CmtBasicaMgl> listaBasicaTecnologias;
        List<String> listaNombreCodigoBasica = new ArrayList<>();
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        listaBasicaTecnologias = cmtBasicaMglDaoImpl.findByTipoBasica(new BigDecimal(Constant.TIPO_BASICA_ID));
        if (!listaBasicaTecnologias.isEmpty() && !listaIdTecnologiasSelected.isEmpty()) {
            for (CmtBasicaMgl cmtBasicaMgl : listaBasicaTecnologias) {
                for (String codigo : listaIdTecnologiasSelected) {
                    BigDecimal codigoTec = new BigDecimal(codigo);
                    if (cmtBasicaMgl.getBasicaId().compareTo((codigoTec))== 0) {
                        listaNombreCodigoBasica.add(cmtBasicaMgl.getCodigoBasica());
                    }
                }
            }
        }
        return listaNombreCodigoBasica;
    }
    
    public List<BigDecimal> obtenerIdCentroPobladoList(List<String> listaIdCentroPobladoSelected) throws ApplicationException{
        List<BigDecimal> listaidCentroPoblado = new ArrayList<>();
        GeograficoPoliticoDaoImpl geograficoPoliticoDaoImpl = new GeograficoPoliticoDaoImpl();
        List<GeograficoPoliticoMgl> listaCentrosPoblados = geograficoPoliticoDaoImpl.findAll();
        if (!listaIdCentroPobladoSelected.isEmpty() && !listaCentrosPoblados.isEmpty()) {
            for (String centroPobaldo : listaIdCentroPobladoSelected) {
                for (GeograficoPoliticoMgl codigo : listaCentrosPoblados) {
                    if (centroPobaldo.equals(codigo.getGpoNombre()) && codigo.getGpoTipo().equals("CENTRO POBLADO") ) {
                        listaidCentroPoblado.add(codigo.getGpoId());
                    }
                }
            }
        }
        return  listaidCentroPoblado;
        
    }
    public List<String> obtenerNombreFactibilidad(List<String> listaFactibilidades) throws ApplicationException {
        List<String> listaNombreFactibidad = new ArrayList<>();
        if (!listaFactibilidades.isEmpty()) {
            for (String factibilidad : listaFactibilidades) {
                if (factibilidad != null) {
                    if (factibilidad.contains("NEGATIVA")) {
                        factibilidad = "NEGATIVA";
                        listaNombreFactibidad.add(factibilidad);
                    } else if (factibilidad.contains("POSITIVA")) {
                        factibilidad = "POSITIVA";
                        listaNombreFactibidad.add(factibilidad);
                    } else {
                        factibilidad = "PREDIO NO UBICADO";
                        listaNombreFactibidad.add(factibilidad);
                    }
                }

            }
        }
        return listaNombreFactibidad;

    }

    /**
     * Metodo para consultar una factibilidad por idFactibilidad en BD Autor:
     * Victor Bocanegra
     *
     * @param idFactibilidad
     * @return {@link List<FactibilidadMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public FactibilidadMgl findFactibilidadById(BigDecimal idFactibilidad) throws ApplicationException {

        try {
            Query query = entityManager.createQuery("SELECT f FROM FactibilidadMgl f  "
                    + " WHERE f.factibilidadId =  :factibilidadId ");

            query.setParameter("factibilidadId", idFactibilidad);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0

            return (FactibilidadMgl) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar la factibilidad. EX000: " + e.getMessage(), e);
            return null;
        }
    }
  
    public int countgetReporte(HashMap<String, Object> params) throws ApplicationException {
        List<String> listaTecnologias = (List<String>) params.get("tecnologia");
        List<String> listaNombreFactibilidad = obtenerNombreFactibilidad((List<String>) params.get("estadoFactSelected"));
        List<String> listaIdCentroPoblado = (List<String>) params.get("centroPoblado");

        Date fechaInicial = null;
        Date fechaFin1DiaMes = null;
        Date fechaFinal;

        if (((Date) params.get("fechaInicio") != null)) { 
            fechaInicial = ((Date) params.get("fechaInicio"));
        }
        if (((Date) params.get("fechaFinal") != null)) {
            fechaFinal = ((Date) params.get("fechaFinal"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaFinal);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fechaFin1DiaMes = calendar.getTime();
        }
        
        final String count = "count(1)";
        final String queryJpql = this.getGenerarQueryJPQL(count, listaTecnologias, listaNombreFactibilidad, fechaInicial, fechaFin1DiaMes);
        Query query = entityManager.createQuery(queryJpql);

        if (listaTecnologias != null) {
            if (!listaTecnologias.isEmpty()) {
                query.setParameter("listaTecnologias", listaTecnologias);
            }
        }
        if (listaNombreFactibilidad != null) {
            if (!listaNombreFactibilidad.isEmpty()) {
                query.setParameter("listaNombreFactibilidad", listaNombreFactibilidad);
            }
        }
        if ((fechaInicial != null && fechaFin1DiaMes != null)) {
            if (fechaInicial.before(fechaFin1DiaMes)) {
                query.setParameter("fechaInicio", fechaInicial);
            }
        }
        if ((fechaInicial != null && fechaFin1DiaMes != null)) {
            if (fechaInicial.before(fechaFin1DiaMes)) {
                query.setParameter("fechaFinal", fechaFin1DiaMes);
            }
        }
        if (listaIdCentroPoblado != null) {
            if (!listaIdCentroPoblado.isEmpty()) {
                query.setParameter("listacentroPoblado", listaIdCentroPoblado);
            }
        }

        Long cantidadReporteFactibilidad = (Long) query.getSingleResult();
        return cantidadReporteFactibilidad.intValue();
    }
}
