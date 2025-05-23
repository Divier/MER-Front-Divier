/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.dao.impl.NodoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.rest.dtos.CmtBasicaMglDto;
import co.com.claro.mgl.rest.dtos.CmtComunidadRrDto;
import co.com.claro.mgl.rest.dtos.CmtRegionDto;
import co.com.claro.mgl.rest.dtos.GeograficoPoliticoDto;
import co.com.claro.mgl.rest.dtos.GpoCentroPlobladoMglDto;
import co.com.claro.mgl.rest.dtos.GpoCiudadMglDto;
import co.com.claro.mgl.rest.dtos.GpoDepartamentoMglDto;
import co.com.claro.mgl.rest.dtos.NodoMglDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class CtmNodesMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CtmNodesMglManager.class);
    private NodoMglDaoImpl dao = new NodoMglDaoImpl();
    private GeograficoPoliticoManager gpoDao = new GeograficoPoliticoManager();
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Metodo para obtener la informacion del nodo apartir de su codigo
     *
     * @param codigo
     * @return Notificacion de actualizacion del registro
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public NodoMglDto getNodeDataByCod(String codigo) throws ApplicationException {

        try {
            NodoMglDto nodoMgl = new NodoMglDto();
            Pattern codigoPtrn = Pattern.compile("^[a-zA-Z0-9]*$");
            Matcher mtch = codigoPtrn.matcher(codigo);
            if (mtch.matches()) {
                List<NodoMgl> listNodos = dao.getListNodoByCodigo(codigo);
                if (listNodos != null) {
                    if (!listNodos.isEmpty()) {
                        NodoMgl nodo = listNodos.get(0);
                        nodoMgl.setNombre(nodo.getNodNombre());
                        nodoMgl.setCodigo(nodo.getNodCodigo());
                        if (nodo.getNodFechaActivacion() != null) {
                            nodoMgl.setFechaActivacion(format.format(nodo.getNodFechaActivacion()));
                        }
                        if (nodo.getNodFechaCreacion() != null) {
                            nodoMgl.setFechaCreacion(format.format(nodo.getNodFechaCreacion()));
                        }
                        nodoMgl.setUsuarioCreacion(nodo.getNodUsuarioCreacion());
                        nodoMgl.setUsuarioModificacion(nodo.getNodUsuarioModificacion());
                        if (nodo.getNodFechaModificacion() != null) {
                            nodoMgl.setFechaModificacion(format.format(nodo.getNodFechaModificacion()));
                        }
                        nodoMgl.setHeadEnd(nodo.getNodHeadEnd());
                        nodoMgl.setCampo1(nodo.getNodCampoAdicional1());
                        nodoMgl.setCampo2(nodo.getNodCampoAdicional2());
                        nodoMgl.setCampo3(nodo.getNodCampoAdicional3());
                        nodoMgl.setCampo4(nodo.getNodCampoAdicional4());
                        nodoMgl.setCampo5(nodo.getNodCampoAdicional5());
                        nodoMgl.setEstadoRegistro(nodo.getEstadoRegistro());
                        nodoMgl.setPerfilCreacion(nodo.getPerfilCreacion());
                        nodoMgl.setPerfilEdicion(nodo.getPerfilEdicion());

                        if (nodo.getComId() != null) {
                            CmtComunidadRrDto comunidadRrDto = new CmtComunidadRrDto();
                            comunidadRrDto.setCodigo(nodo.getComId().getCodigoRr());
                            comunidadRrDto.setNombreComunidad(nodo.getComId().getNombreComunidad());

                            if (nodo.getComId().getRegionalRr() != null) {
                                CmtRegionDto cmtRegionDto = new CmtRegionDto();
                                cmtRegionDto.setRegionId(nodo.getComId().getRegionalRr().getRegionalRrId());
                                cmtRegionDto.setTechnicalCode(nodo.getComId().getRegionalRr().getCodigoRr());
                                cmtRegionDto.setName(nodo.getComId().getRegionalRr().getNombreRegional());
                                comunidadRrDto.setCmtRegional(cmtRegionDto);
                            }

                            nodoMgl.setCmtComunidad(comunidadRrDto);
                        }

                        GeograficoPoliticoDto geograficoPoliticoDto = new GeograficoPoliticoDto();
                        GpoCentroPlobladoMglDto gpCentroPoblado = new GpoCentroPlobladoMglDto();
                        GpoCiudadMglDto gpCiudad = new GpoCiudadMglDto();
                        GpoDepartamentoMglDto gpDepartamento = new GpoDepartamentoMglDto();

                        GeograficoPoliticoMgl gpoCentroPoblado = dao.findPoliticalGeographyNodoByCodNodo(codigo);

                        GeograficoPoliticoMgl gpoCiudad = new GeograficoPoliticoMgl();
                        if (gpoCentroPoblado != null) {
                            gpoCiudad = gpoDao.findCiudadByCentroPoblado(gpoCentroPoblado.getGeoGpoId());
                        }

                        GeograficoPoliticoMgl gpoDepartamento = new GeograficoPoliticoMgl();
                        if (gpoCiudad.getGpoId() == null) {
                            gpoDepartamento = gpoDao.findDepartamentoByCiudad(gpoCentroPoblado.getGeoGpoId());
                        } else {
                            gpoDepartamento = gpoDao.findDepartamentoByCiudad(gpoCiudad.getGeoGpoId());
                        }

                        if (gpoCentroPoblado.getGpoId() != null && gpoDepartamento.getGpoId() != null) {

                            gpCentroPoblado.setGpoNombre(gpoCentroPoblado.getGpoNombre());
                            gpCentroPoblado.setGpoCodigo(gpoCentroPoblado.getGeoCodigoDane());
                            gpDepartamento.setGpoNombre(gpoDepartamento.getGpoNombre());
                            gpDepartamento.setGpoCodigo(gpoDepartamento.getGeoCodigoDane());
                            gpCiudad.setGpoNombre(gpoCiudad.getGpoNombre());
                            gpCiudad.setGpoCodigo(gpoCiudad.getGeoCodigoDane());
                            gpCiudad.setDepartamento(gpDepartamento);
                            gpCentroPoblado.setCiudad(gpCiudad);

                        }
                        geograficoPoliticoDto.setCentroPoblado(gpCentroPoblado);

                        nodoMgl.setGeograficoPolitico(geograficoPoliticoDto);
                        //Consultar Area en BasicaMGL
                        CmtBasicaMglDto area = dao.findBasicasNodoByCodNodo(codigo, "areId.basicaId");
                        nodoMgl.setArea(area);
                        //Consultar Unidad en Gestion en BasicaMGL
                        CmtBasicaMglDto unidadGestion = dao.findBasicasNodoByCodNodo(codigo, "ugeId.basicaId");
                        nodoMgl.setUnidadGestion(unidadGestion);
                        //Consultar Zona en BasicaMGL
                        CmtBasicaMglDto zona = dao.findBasicasNodoByCodNodo(codigo, "zonId.basicaId");
                        nodoMgl.setZona(zona);
                        //Consultar Divicional en BasicaMGL
                        CmtBasicaMglDto divicional = dao.findBasicasNodoByCodNodo(codigo, "divId.basicaId");
                        nodoMgl.setDivisional(divicional);
                        //Consultar Distrito en BasicaMGL
                        CmtBasicaMglDto distrito = dao.findBasicasNodoByCodNodo(codigo, "disId.basicaId");
                        nodoMgl.setDistrito(distrito);
                        //Consultar estado
                        CmtBasicaMglDto estado = dao.findBasicasNodoByCodNodo(codigo, "estado.basicaId");
                        nodoMgl.setEstado(estado);
                        //Consultar Aliado    
                        CmtBasicaMglDto aliado = dao.findBasicasNodoByCodNodo(codigo, "aliadoId.basicaId");
                        nodoMgl.setAliado(aliado);
                        //Asignar Costo De Red
                        nodoMgl.setCostoRed(nodo.getCostoRed());
                        //Asignar Limites
                        nodoMgl.setLimites(nodo.getLimites());
                        nodoMgl.setMessage("Consulta Exitosa");
                        nodoMgl.setMessageType("I");
                    }
                } else {
                    nodoMgl.setMessage("No Se Encontro Ningun Nodo Asociado "
                            + "Al Codigo Proporcionado");
                    nodoMgl.setMessageType("E");
                }
            } else {
                nodoMgl.setMessage("No Se Permiten Caracteres Especiales");
                nodoMgl.setMessageType("E");
            }
            return nodoMgl;
        } catch (ApplicationException e) {
            LOGGER.error("Error consultando el nodo " + e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }

    }
}
