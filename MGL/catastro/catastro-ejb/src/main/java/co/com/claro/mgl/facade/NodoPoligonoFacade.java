package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.NodoPoligonoManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NodoPoligono;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;


/**
 * Bean de sesi&oacute;n para el manejo de Nodos de Pol&iacute;gonos.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@Stateless
public class NodoPoligonoFacade implements NodoPoligonoFacadeLocal {
    
    /** Administrador de Nodo Pol&iacute;gono. */
    private final NodoPoligonoManager nodoPoligonoManager;
    
    
    /**
     * Constructor.
     */
    public NodoPoligonoFacade() {
        this.nodoPoligonoManager = new NodoPoligonoManager();
    }
    
    
    /*
     * (non-Javadoc)
     * @see co.com.claro.mgl.businessmanager.address.NodoPoligonoManager#findByNodoVertice(java.math.BigDecimal)
     */
    @Override
    public List<NodoPoligono> findByNodoVertice(BigDecimal idNodoVertice) throws ApplicationException {
        return ( nodoPoligonoManager.findByNodoVertice(idNodoVertice) );
    }
    
    
    /*
     * (non-Javadoc)
     * @see co.com.claro.mgl.businessmanager.address.NodoPoligonoManager#findAll()
     */
    @Override
    public List<NodoPoligono> findAll() throws ApplicationException {
        return ( nodoPoligonoManager.findAll() );
    }
    
    
    /*
     * (non-Javadoc)
     * @see co.com.claro.mgl.businessmanager.address.NodoPoligonoManager#create(co.com.claro.mgl.jpa.entities.NodoPoligono)
     */
    @Override
    public NodoPoligono create(NodoPoligono n) throws ApplicationException {
        return ( nodoPoligonoManager.create(n) );
    }
    
    
    /*
     * (non-Javadoc)
     * @see co.com.claro.mgl.businessmanager.address.NodoPoligonoManager#update(co.com.claro.mgl.jpa.entities.NodoPoligono)
     */
    @Override
    public NodoPoligono update(NodoPoligono n) throws ApplicationException {
        return ( nodoPoligonoManager.update(n) );
    }
    
    
    /*
     * (non-Javadoc)
     * @see co.com.claro.mgl.businessmanager.address.NodoPoligonoManager#delete(co.com.claro.mgl.jpa.entities.NodoPoligono)
     */
    @Override
    public boolean delete(NodoPoligono n) throws ApplicationException {
        return ( nodoPoligonoManager.delete(n) );
    }
    
    
    /*
     * (non-Javadoc)
     * @see co.com.claro.mgl.businessmanager.address.NodoPoligonoManager#findById(java.math.BigDecimal)
     */
    @Override
    public NodoPoligono findById(NodoPoligono n) throws ApplicationException {
        NodoPoligono nodoPoligono = null;
        
        if (n != null && n.getNodoPoligonoId() != null) {
            nodoPoligono = nodoPoligonoManager.findById(n.getNodoPoligonoId());
        }
        
        return ( nodoPoligono );
    }
    
    /**
     * Obtiene Poligonos de nodos por coordenadas de la direccion
     *
     * @param coordenadasDto contiene el codigo dane con el que se desea filtrar
     * @author Victor Bocanegra
     * @return List<NodoPoligono>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<NodoPoligono> findNodosByCoordenadasDir(String latitudDir,
            String longitdDir, int desviacionMts)
            throws ApplicationException {

        return nodoPoligonoManager.findNodosByCoordenadasDir(latitudDir, longitdDir, desviacionMts);
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
    @Override
    public NodoPoligono findNodosByCoordenadasDirTecnoAndCentro(String latitudDir,
            String longitdDir, CmtBasicaMgl tecnologia,
            GeograficoPoliticoMgl centro, int desviacionMts, NodoMgl nodoRef)
            throws ApplicationException {
        return nodoPoligonoManager.findNodosByCoordenadasDirTecnoAndCentro
        (latitudDir, longitdDir, tecnologia, centro, desviacionMts, nodoRef);
    }

}
