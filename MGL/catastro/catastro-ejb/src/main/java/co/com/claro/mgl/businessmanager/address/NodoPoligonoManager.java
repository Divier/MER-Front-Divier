package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.NodoPoligonoDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NodoPoligono;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.Constant;
import java.math.BigDecimal;
import java.util.List;


/**
 * Manager que interact&uacute;a con el DAO de Nodos de Pol&iacute;gonos.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class NodoPoligonoManager {
    
    /** Data Access Object para Nodo Pol&iacute;gono. */
    private final NodoPoligonoDaoImpl nodoPoligonoDaoImpl = new NodoPoligonoDaoImpl();
    
    
    /**
     * Obtiene el listado de los Nodo Pol&iacute;gono asociados al Nodo V&eacute;rtice especificado.
     * 
     * @param idNodoVertice Identificador del Nodo V&eacute;rtice.
     * @return Listado de Nodos que conforman el Pol&iacute;gono.
     * @throws ApplicationException 
     */
    public List<NodoPoligono> findByNodoVertice(BigDecimal idNodoVertice) throws ApplicationException {
        return nodoPoligonoDaoImpl.findByNodoVertice(idNodoVertice);
    }
    
    
    /**
     * Consulta un Nodo Pol&iacute;gono a trav&eacute;s de su identificador.
     * 
     * @param id Identificador del Nodo Pol&iacute;gono.
     * @return Nodo Pol&iacute;gono.
     * @throws ApplicationException 
     */
    public NodoPoligono findById(BigDecimal id) throws ApplicationException {
        return nodoPoligonoDaoImpl.find(id);
    }
    
    
    /**
     * Inserta un nuevo Nodo Pol&iacute;gono.
     * 
     * @param n Nodo Pol&iacute;gono a crear.
     * @return Nodo Pol&iacute;gono creado.
     * @throws ApplicationException 
     */
    public NodoPoligono create(NodoPoligono n) throws ApplicationException {
        return nodoPoligonoDaoImpl.create(n);
    }
    
    
    /**
     * Actualiza un Nodo Pol&iacute;gono.
     * 
     * @param n Nodo Pol&iacute;gono a actualizar.
     * @return Nodo Pol&iacute;gono actualizado.
     * @throws ApplicationException 
     */
    public NodoPoligono update(NodoPoligono n) throws ApplicationException {
        return nodoPoligonoDaoImpl.update(n);
    }
    
    
    /**
     * Elimina un Nodo Pol&iacute;gono.
     * 
     * @param n Nodo Pol&iacute;gono a eliminar.
     * @return Nodo Pol&iacute;gono fue eliminado?
     * @throws ApplicationException 
     */
    public boolean delete(NodoPoligono n) throws ApplicationException {
        return nodoPoligonoDaoImpl.delete(n);
    }
    
    
    /**
     * Consulta el listado de todos los Nodos Pol&iacute;gono.
     * @return Lista de Nodo Pol&iacute;gono.
     * @throws ApplicationException 
     */
    public List<NodoPoligono> findAll() throws ApplicationException {
        return nodoPoligonoDaoImpl.findAllItems();
    }
    
    /**
     * Obtiene Poligonos de nodos por coordenadas de la direccion
     *
     * @param coordenadasDto contiene el codigo dane con el que se desea filtrar
     * @author Victor Bocanegra
     * @return List<NodoPoligono>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<NodoPoligono> findNodosByCoordenadasDir(String latitudDir,
            String longitdDir, int desviacionMts)
            throws ApplicationException {

        double latitudIngresada = Double.parseDouble(redondearCoordenada(latitudDir));
        double longitudIngresada = Double.parseDouble(redondearCoordenada(longitdDir));

        double latitudAumentadamts = latitudIngresada + (Constant.DISTANCIA_1_METRO_COORDENADA * desviacionMts);
        double longitudAumentadamts = longitudIngresada + (Constant.DISTANCIA_1_METRO_COORDENADA * desviacionMts);

        double latitudRestadaMts = latitudIngresada - (Constant.DISTANCIA_1_METRO_COORDENADA * desviacionMts);
        double longitudRestadaMts = longitudIngresada - (Constant.DISTANCIA_1_METRO_COORDENADA * desviacionMts);

        return nodoPoligonoDaoImpl.findNodosByCoordenadasDir(latitudRestadaMts, longitudRestadaMts, latitudAumentadamts, longitudAumentadamts);

    }
    
    /**
     * Función que corta una coordenada y le quita los últimos 3 digitos.En caso
     * de que falten digitos a la coordenada es rellenada con ceros.
     *
     * @author Victor Bocanegra
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
            return null;
        }
    }
    
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
    public NodoPoligono findNodosByCoordenadasDirTecnoAndCentro(String latitudDir,
            String longitdDir, CmtBasicaMgl tecnologia,
            GeograficoPoliticoMgl centro, int desviacionMts, NodoMgl nodoRef)
            throws ApplicationException {

        List<NodoPoligono> nodosPol;
        NodoPoligono nodoPoligonoReturn = null;

        if (redondearCoordenada(latitudDir) != null && redondearCoordenada(longitdDir) != null) {
            double latitudIngresada = Double.parseDouble(redondearCoordenada(latitudDir));
            double longitudIngresada = Double.parseDouble(redondearCoordenada(longitdDir));

            double latitudAumentadamts = latitudIngresada + (Constant.DISTANCIA_1_METRO_COORDENADA * desviacionMts);
            double longitudAumentadamts = longitudIngresada + (Constant.DISTANCIA_1_METRO_COORDENADA * desviacionMts);

            double latitudRestadaMts = latitudIngresada - (Constant.DISTANCIA_1_METRO_COORDENADA * desviacionMts);
            double longitudRestadaMts = longitudIngresada - (Constant.DISTANCIA_1_METRO_COORDENADA * desviacionMts);

            if (nodoRef != null) {
                nodosPol = nodoPoligonoDaoImpl.findNodosByCoordenadasDirTecnCentroAndDifNodo(latitudRestadaMts,
                        longitudRestadaMts, latitudAumentadamts, longitudAumentadamts, tecnologia, centro, nodoRef);
            } else {
                nodosPol = nodoPoligonoDaoImpl.findNodosByCoordenadasDirTecnoAndCentro(latitudRestadaMts,
                        longitudRestadaMts, latitudAumentadamts, longitudAumentadamts, tecnologia, centro);
            }

            if (nodosPol != null && !nodosPol.isEmpty()) {

                double min = 999999999;
                double dist;

                for (NodoPoligono nodoPoligono : nodosPol) {
                    double d = Math.hypot(latitudIngresada - nodoPoligono.getY().doubleValue(),
                            longitudIngresada - nodoPoligono.getX().doubleValue());

                    dist = d / Constant.DISTANCIA_1_METRO_COORDENADA;

                    if (dist < min) {
                        min = dist;
                        nodoPoligonoReturn = nodoPoligono;
                        nodoPoligonoReturn.setDistanciaMts((int) (min));
                    }
                }
            }
        }

        return nodoPoligonoReturn;

    }

}
