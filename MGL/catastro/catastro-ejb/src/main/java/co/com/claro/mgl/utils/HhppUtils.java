package co.com.claro.mgl.utils;

import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.SubDireccionMglManager;
import co.com.claro.mgl.dtos.CmtDireccionDetalladaMglDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Utilitarios relacionados con <b>Direcciones y HHPP</b>.
 *
 * @author Camilo Miranda (<i>mirandaca</i>)
 */
public class HhppUtils {

    /**
     * Logger del aplicativo.
     */
    private static final Logger LOGGER = LogManager.getLogger(HhppUtils.class);
    
    /**
     * Mapa de Direcciones por su identificador.
     */
    private final static Map<BigDecimal, DireccionMgl> mapDirecciones = new HashMap<>();
    /**
     * Mapa de Subdirecciones por su identificador.
     */
    private final static Map<BigDecimal, SubDireccionMgl> mapSubdirecciones = new HashMap<>();
    
    /**
     * Manager para operaciones de HHPP.
     */
    private static final HhppMglManager hhppMglManager = new HhppMglManager();
    /**
     * Manager para operaciones de Geogr&aacute;fico Pol&iacute;tico.
     */
    private static final GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
    /**
     * Manager para operaciones con Direcciones.
     */
    private static final DireccionMglManager direccionMglManager = new DireccionMglManager();
    /**
     * Manager para operaciones con Subdirecciones.
     */
    private static final SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
    
    
    /**
     * Funci&oacute;n que obtiene par&aacute;metros espec&iacute;ficos que se
     * muestran para un <i>HHPP</i>.
     *
     * @param cmtDireccionDetalladaMgl Entidad de Direcci&oacute;n Detallada a
     * la cual se le obtendr&aacute; la informaci&oacute;n adicional.
     * @throws ApplicationException
     */
    public static void obtenerParametrosHhpp(CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl)
            throws ApplicationException {
        try {
            if (cmtDireccionDetalladaMgl != null) {

                //Obtenemos los Hhpp de la Subdireccion  
                if (cmtDireccionDetalladaMgl.getSubDireccion() != null
                        && cmtDireccionDetalladaMgl.getSubDireccion().getSdiId() != null) {

                    List<HhppMgl> hhhpSubDirList = hhppMglManager
                            .findHhppSubDireccion(cmtDireccionDetalladaMgl.getSubDireccion().getSdiId());

                    if (hhhpSubDirList != null && !hhhpSubDirList.isEmpty()) {
                        if (hhhpSubDirList.get(0).getEstadoRegistro() == 1) {
                            cmtDireccionDetalladaMgl.setHhppExistente(true);
                            cmtDireccionDetalladaMgl.setHhppMgl(hhhpSubDirList.get(0));
                            //Obtener numero de cuenta matriz si tiene asociada
                            if (hhhpSubDirList.get(0).getHhppSubEdificioObj() != null && hhhpSubDirList.get(0).getHhppSubEdificioObj()
                                    .getCmtCuentaMatrizMglObj() != null) {
                                //Asignamos el número de la cuenta matriz
                                cmtDireccionDetalladaMgl.setNumeroCuentaMatriz(hhhpSubDirList.get(0).getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getCuentaMatrizId()
                                        .toString());
                            }
                        }
                    }
                } else {
                    //Obtenemos los Hhpp de la Direccion principal    
                    if (cmtDireccionDetalladaMgl.getDireccion() != null
                            && cmtDireccionDetalladaMgl.getDireccion().getDirId() != null) {

                        List<HhppMgl> hhhpDirList
                                = hhppMglManager
                                        .findHhppDireccion(cmtDireccionDetalladaMgl.getDireccion().getDirId());

                        if (hhhpDirList != null && !hhhpDirList.isEmpty()) {
                            if (hhhpDirList.get(0).getEstadoRegistro() == 1) {
                                cmtDireccionDetalladaMgl.setHhppExistente(true);
                                cmtDireccionDetalladaMgl.setHhppMgl(hhhpDirList.get(0));
                                //Obtener numero de cuenta matriz si tiene asociada
                                if (hhhpDirList.get(0).getHhppSubEdificioObj() != null && hhhpDirList.get(0).getHhppSubEdificioObj()
                                        .getCmtCuentaMatrizMglObj() != null) {
                                    //Asignamos el número de la cuenta matriz
                                    cmtDireccionDetalladaMgl.setNumeroCuentaMatriz(hhhpDirList.get(0).getHhppSubEdificioObj().getCmtCuentaMatrizMglObj().getNumeroCuenta()
                                            .toString());
                                }
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            String msgError = "Error al momento de obtener los Parámetros del HHPP: " + e.getMessage();
            LOGGER.error(msgError);
            throw (e);
        } catch (Exception e) {
            String msgError = "Error al momento de obtener los Parámetros del HHPP: " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Funci&oacute;n que obtiene par&aacute;metros espec&iacute;ficos que se
     * muestran para un <i>HHPP</i>.
     *
     * @param cmtDireccionDetalladaDto DTO de Direcci&oacute;n Detallada a la
     * cual se le obtendr&aacute; la informaci&oacute;n adicional.
     * @throws ApplicationException
     */
    public static void obtenerParametrosHhpp(CmtDireccionDetalladaMglDto cmtDireccionDetalladaDto)
            throws ApplicationException {
        try {
            if (cmtDireccionDetalladaDto != null && cmtDireccionDetalladaDto.getDireccionDetalladaId() != null) {

                // Crear una entidad y asignar la direccion:
                CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl = new CmtDireccionDetalladaMgl();
                cmtDireccionDetalladaMgl.setDireccionDetalladaId(cmtDireccionDetalladaDto.getDireccionDetalladaId());
                
                // Buscar el objeto direccion:
                DireccionMgl direccionMgl = mapDirecciones.get(cmtDireccionDetalladaDto.getDireccionId());
                if (direccionMgl == null) {
                    List<DireccionMgl> direccionesMgl = 
                            direccionMglManager.findByDirId(cmtDireccionDetalladaDto.getDireccionId());
                    if (direccionesMgl != null && !direccionesMgl.isEmpty()) {
                        direccionMgl = direccionesMgl.get(0);
                        // Adicionar la direccion al mapa:
                        mapDirecciones.put(cmtDireccionDetalladaDto.getDireccionId(), direccionMgl);
                    }
                }
                
                // Buscar el objeto subdireccion:
                SubDireccionMgl subDireccionMgl = mapSubdirecciones.get(cmtDireccionDetalladaDto.getSubdireccionId());
                if (subDireccionMgl == null) {
                    subDireccionMgl = subDireccionMglManager.findById(cmtDireccionDetalladaDto.getSubdireccionId());
                    if (subDireccionMgl != null) {
                        mapSubdirecciones.put(cmtDireccionDetalladaDto.getSubdireccionId(), subDireccionMgl);
                    }
                }
                
                if (direccionMgl != null || subDireccionMgl != null) {
                    cmtDireccionDetalladaMgl.setDireccion(direccionMgl);
                    cmtDireccionDetalladaMgl.setSubDireccion(subDireccionMgl);
                    
                    // Asignar los parametros a la entidad de Direccion Detallada:
                    obtenerParametrosHhpp(cmtDireccionDetalladaMgl);
                    
                    
                    // Establecer los parametros faltantes al DTO:
                    cmtDireccionDetalladaDto.setHhppExistente(cmtDireccionDetalladaMgl.isHhppExistente());
                    cmtDireccionDetalladaDto.setCiudad(cmtDireccionDetalladaMgl.getCiudad());
                    cmtDireccionDetalladaDto.setNumeroCuentaMatriz(cmtDireccionDetalladaMgl.getNumeroCuentaMatriz()); 
                    cmtDireccionDetalladaDto.setHhppMgl(cmtDireccionDetalladaMgl.getHhppMgl()); 
                    cmtDireccionDetalladaDto.setNombreSubedificio(cmtDireccionDetalladaMgl.getNombreSubedificio());
                }
            }
        } catch (ApplicationException e) {
            String msgError = "Error al momento de obtener los Parámetros del HHPP: " + e.getMessage();
            LOGGER.error(msgError);
            throw (e);
        } catch (Exception e) {
            String msgError = "Error al momento de obtener los Parámetros del HHPP: " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);
        }
    }
}
