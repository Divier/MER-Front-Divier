package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NodoPoligono;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;


/**
 * Interfaz local para el manejo de Nodos de Pol&iacute;gono.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@Local
public interface NodoPoligonoFacadeLocal extends BaseFacadeLocal<NodoPoligono> {
    
    /**
     * Obtiene el listado de los Nodo Pol&iacute;gono asociados al Nodo V&eacute;rtice especificado.
     * 
     * @param idNodoVertice Identificador del Nodo V&eacute;rtice.
     * @return Listado de Nodos que conforman el Pol&iacute;gono.
     * @throws ApplicationException 
     */
    List<NodoPoligono> findByNodoVertice(BigDecimal idNodoVertice) throws ApplicationException;
 
    /**
     * Obtiene Poligonos de nodos por coordenadas de la direccion
     *
     * @param coordenadasDto contiene el codigo dane con el que se desea filtrar
     * @author Victor Bocanegra
     * @return List<NodoPoligono>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    List<NodoPoligono> findNodosByCoordenadasDir(String latitudDir,
            String longitdDir, int desviacionMts)
            throws ApplicationException;

    /**
     * Obtiene Poligonos de nodos por coordenadas de la direccion-Tecnologia y
     * centro poblado
     *
     * @param latitudDir
     * @param longitdDir
     * @param tecnologia
     * @param centro
     * @param desviacionMts
     * @param nodoRef
     * @author Victor Bocanegra
     * @return NodoPoligono
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    NodoPoligono findNodosByCoordenadasDirTecnoAndCentro(String latitudDir,
            String longitdDir, CmtBasicaMgl tecnologia,
            GeograficoPoliticoMgl centro, int desviacionMts, NodoMgl nodoRef)
            throws ApplicationException;

}
