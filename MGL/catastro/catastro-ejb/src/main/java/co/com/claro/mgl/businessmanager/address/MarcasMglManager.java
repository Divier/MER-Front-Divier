/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.MarcasMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author Admin
 */
public class MarcasMglManager {
    
        MarcasMglDaoImpl marcasMglDaoImpl = new MarcasMglDaoImpl();

    public List<MarcasMgl> findAll() throws ApplicationException {
        List<MarcasMgl> result;
        MarcasMglDaoImpl marcasMglDaoImpl1 = new MarcasMglDaoImpl();
        result = marcasMglDaoImpl1.findAll();
        return result;
    }
    
    /**
     * Buscar las marcas que estan asignadas a un HHPP
     * 
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl hhpp que se quiere buscar
     * @return Lista de marcas que estan asignadas a ese hhpp
     * @throws ApplicationException 
     */
    public List<MarcasMgl> findMarcasMglByHhpp(HhppMgl hhppMgl) throws ApplicationException{
        return marcasMglDaoImpl.findMarcasMglByHhpp(hhppMgl);
    }
    
        /**
     * Buscar las marcas por codigo
     * 
     * @author Juan David Hernandez     
     * @param codigoMarca
     * @return
     * @throws ApplicationException 
     */
    public MarcasMgl findMarcasMglByCodigo(String codigoMarca) throws ApplicationException{
        return marcasMglDaoImpl.findMarcasMglByCodigo(codigoMarca);
    }
    
    
            /**
     * Buscar las marcas por codigo
     * 
     * @author @cardenaslb   
     * @param IdMarcasMgl
     * @return
     * @throws ApplicationException 
     */
    public MarcasMgl findById(BigDecimal IdMarcasMgl) throws ApplicationException{
        return marcasMglDaoImpl.findById(IdMarcasMgl);
    }
    
    public List<MarcasMgl> findByGrupoCodigo(String codigo) throws ApplicationException{
        return marcasMglDaoImpl.findByGrupoCodigo(codigo);
    }
    
    

}
