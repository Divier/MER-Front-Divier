package co.com.claro.visitasTecnicas.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.DetalleDireccionManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * objetivo de la clase. 
 * Facade le las funciones de negocio de Detalle direccion.
 *
 * @author Carlos Leonardo Villamil
 * @versión 1.00.000
 */
@Stateless(name = "detalleDireccionEJB", mappedName = "detalleDireccionEJB", description = "detalleDireccion")
@Remote({DetalleDireccionEJBRemote.class})
public class DetalleDireccionEJB implements DetalleDireccionEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(DetalleDireccionEJB.class);
    
    private static final String ID = "ID";
    private static final String IDSOLICITUD = "ID_SOLICITUD";
    private static final String IDTIPODIRECCION = "ID_TIPO_DIRECCION";
    private static final String DIRPRINCALT = "DIR_PRINC_ALT";
    private static final String BARRIO = "BARRIO";
    private static final String TIPOVIAPRINCIPAL = "TIPO_VIA_PRINCIPAL";
    private static final String NUMVIAPRINCIPAL = "NUM_VIA_PRINCIPAL";
    private static final String LTVIAPRINCIPAL = "LT_VIA_PRINCIPAL";
    private static final String NLPOSTVIAP = "NL_POST_VIA_P";
    private static final String BISVIAPRINCIPAL = "BIS_VIA_PRINCIPAL";
    private static final String CUADVIAPRINCIPAL = "CUAD_VIA_PRINCIPAL";
    private static final String TIPOVIAGENERADORA = "TIPO_VIA_GENERADORA";
    private static final String NUMVIAGENERADORA = "NUM_VIA_GENERADORA";
    private static final String LTVIAGENERADORA = "LT_VIA_GENERADORA";
    private static final String NLPOSTVIAG = "NL_POST_VIA_G";
    private static final String BISVIAGENERADORA = "BIS_VIA_GENERADORA";
    private static final String CUADVIAGENERADORA = "CUAD_VIA_GENERADORA";
    private static final String PLACADIRECCION = "PLACA_DIRECCION";
    private static final String CPTIPONIVEL1 = "CP_TIPO_NIVEL1";
    private static final String CPTIPONIVEL2 = "CP_TIPO_NIVEL2";
    private static final String CPTIPONIVEL3 = "CP_TIPO_NIVEL3";
    private static final String CPTIPONIVEL4 = "CP_TIPO_NIVEL4";
    private static final String CPTIPONIVEL5 = "CP_TIPO_NIVEL5";
    private static final String CPTIPONIVEL6 = "CP_TIPO_NIVEL6";
    private static final String CPVALORNIVEL1 = "CP_VALOR_NIVEL1";
    private static final String CPVALORNIVEL2 = "CP_VALOR_NIVEL2";
    private static final String CPVALORNIVEL3 = "CP_VALOR_NIVEL3";
    private static final String CPVALORNIVEL4 = "CP_VALOR_NIVEL4";
    private static final String CPVALORNIVEL5 = "CP_VALOR_NIVEL5";
    private static final String CPVALORNIVEL6 = "CP_VALOR_NIVEL6";
    private static final String MZTIPONIVEL1 = "MZ_TIPO_NIVEL1";
    private static final String MZTIPONIVEL2 = "MZ_TIPO_NIVEL2";
    private static final String MZTIPONIVEL3 = "MZ_TIPO_NIVEL3";
    private static final String MZTIPONIVEL4 = "MZ_TIPO_NIVEL4";
    private static final String MZTIPONIVEL5 = "MZ_TIPO_NIVEL5";
    private static final String MZVALORNIVEL1 = "MZ_VALOR_NIVEL1";
    private static final String MZVALORNIVEL2 = "MZ_VALOR_NIVEL2";
    private static final String MZVALORNIVEL3 = "MZ_VALOR_NIVEL3";
    private static final String MZVALORNIVEL4 = "MZ_VALOR_NIVEL4";
    private static final String MZVALORNIVEL5 = "MZ_VALOR_NIVEL5";
    private static final String DESCTIPODIR = "DESC_TIPO_DIR";
    private static final String IDDIRCATASTRO = "ID_DIR_CATASTRO";
    private static final String ITTIPOPLACA = "IT_TIPO_PLACA";
    private static final String ITVALORPLACA = "IT_VALOR_PLACA";
    private static final String MZTIPONIVEL6 = "MZ_TIPO_NIVEL6";
    private static final String MZVALORNIVEL6 = "MZ_VALOR_NIVEL6";
    private static final String ESTRATO = "ESTRATO";
    private static final String ESTADO_DIR = "ESTADO_DIR_GEO";
    private static final String LETRA_3G = "LETRA3G";

    @Override
    public List<DetalleDireccionEntity> consultarDireccionPorSolicitud(String idSolicitud) throws ApplicationException {

        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList lista = adb.outDataList("detalle1", idSolicitud);
            DireccionUtil.closeConnection(adb);
            return fillList(lista);
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la dirección por solicitud. EX000: " + e.getMessage(), e);
        }        
    }

    private List<DetalleDireccionEntity> fillList(DataList lista) {
        List<DetalleDireccionEntity> listaDirecciones = new ArrayList<DetalleDireccionEntity>();

        if (lista != null) {
            for (DataObject obj : lista.getList()) {
                DetalleDireccionEntity detalle = new DetalleDireccionEntity();
                detalle.setId(obj.getBigDecimal(ID));
                detalle.setIdsolicitud(obj.getBigDecimal(IDSOLICITUD));
                detalle.setIdtipodireccion(obj.getString(IDTIPODIRECCION));
                detalle.setDirprincalt(obj.getString(DIRPRINCALT));
                detalle.setBarrio(obj.getString(BARRIO));
                detalle.setTipoviaprincipal(obj.getString(TIPOVIAPRINCIPAL));
                detalle.setNumviaprincipal(obj.getString(NUMVIAPRINCIPAL));
                detalle.setLtviaprincipal(obj.getString(LTVIAPRINCIPAL));
                detalle.setNlpostviap(obj.getString(NLPOSTVIAP));
                detalle.setBisviaprincipal(obj.getString(BISVIAPRINCIPAL));
                detalle.setCuadviaprincipal(obj.getString(CUADVIAPRINCIPAL));
                detalle.setTipoviageneradora(obj.getString(TIPOVIAGENERADORA));
                detalle.setNumviageneradora(obj.getString(NUMVIAGENERADORA));
                detalle.setLtviageneradora(obj.getString(LTVIAGENERADORA));
                detalle.setNlpostviag(obj.getString(NLPOSTVIAG));
                detalle.setBisviageneradora(obj.getString(BISVIAGENERADORA));
                detalle.setCuadviageneradora(obj.getString(CUADVIAGENERADORA));
                detalle.setPlacadireccion(obj.getString(PLACADIRECCION));
                detalle.setCptiponivel1(obj.getString(CPTIPONIVEL1));
                detalle.setCptiponivel2(obj.getString(CPTIPONIVEL2));
                detalle.setCptiponivel3(obj.getString(CPTIPONIVEL3));
                detalle.setCptiponivel4(obj.getString(CPTIPONIVEL4));
                detalle.setCptiponivel5(obj.getString(CPTIPONIVEL5));
                detalle.setCptiponivel6(obj.getString(CPTIPONIVEL6));
                detalle.setCpvalornivel1(obj.getString(CPVALORNIVEL1));
                detalle.setCpvalornivel2(obj.getString(CPVALORNIVEL2));
                detalle.setCpvalornivel3(obj.getString(CPVALORNIVEL3));
                detalle.setCpvalornivel4(obj.getString(CPVALORNIVEL4));
                detalle.setCpvalornivel5(obj.getString(CPVALORNIVEL5));
                detalle.setCpvalornivel6(obj.getString(CPVALORNIVEL6));
                detalle.setMztiponivel1(obj.getString(MZTIPONIVEL1));
                detalle.setMztiponivel2(obj.getString(MZTIPONIVEL2));
                detalle.setMztiponivel3(obj.getString(MZTIPONIVEL3));
                detalle.setMztiponivel4(obj.getString(MZTIPONIVEL4));
                detalle.setMztiponivel5(obj.getString(MZTIPONIVEL5));
                detalle.setMzvalornivel1(obj.getString(MZVALORNIVEL1));
                detalle.setMzvalornivel2(obj.getString(MZVALORNIVEL2));
                detalle.setMzvalornivel3(obj.getString(MZVALORNIVEL3));
                detalle.setMzvalornivel4(obj.getString(MZVALORNIVEL4));
                detalle.setMzvalornivel5(obj.getString(MZVALORNIVEL5));
                detalle.setDescTipoDir(obj.getString(DESCTIPODIR));
                detalle.setIdDirCatastro(obj.getString(IDDIRCATASTRO));
                detalle.setItTipoPlaca(obj.getString(ITTIPOPLACA));
                detalle.setItValorPlaca(obj.getString(ITVALORPLACA));
                detalle.setMztiponivel6(obj.getString(MZTIPONIVEL6));
                detalle.setMzvalornivel6(obj.getString(MZVALORNIVEL6));
                detalle.setEstrato(obj.getString(ESTRATO));
                detalle.setEstadoDir(obj.getString(ESTADO_DIR));
                detalle.setLetra3g(obj.getString(LETRA_3G));
                listaDirecciones.add(detalle);
            }
        }

        return listaDirecciones;
    }
    /**
     * Descripción del objetivo del método.Llamada de entrada a la clase para
 convertir la direccion a la clase DetalleDireccionEntity, Llama a borrado
 de variables y Convertir ladireccion.
     *     
     * @author Carlos Leonardo Villamil
     * @param longAddressCodeGeo Codigo de Geo hasta la placa.
     * @param shortAddressCodeGeo Codigo de Geo hasta el complemento.
     * @return DetalleDireccionEntity Direccion tabulada en sus componentes.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public DetalleDireccionEntity conversionADetalleDireccion
            (String longAddressCodeGeo, String shortAddressCodeGeo, 
            BigDecimal city, String barrio) throws ApplicationException {
        DetalleDireccionManager DetalleDireccionManager=new DetalleDireccionManager();
        return DetalleDireccionManager.conversionADetalleDireccion
                (longAddressCodeGeo,shortAddressCodeGeo, city,barrio);            
    }

    @Override
    public String ValidarTipoDireccion(String codGeo, BigDecimal city, String origen) throws ApplicationException {
        DetalleDireccionManager DetalleDireccionManager=new DetalleDireccionManager();
        return DetalleDireccionManager.ValidarTipoDireccion(codGeo, city, origen);
    }
    
    
}
