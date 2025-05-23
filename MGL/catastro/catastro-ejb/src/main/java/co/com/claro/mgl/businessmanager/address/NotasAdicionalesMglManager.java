/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;
import co.com.claro.mgl.dao.impl.NotasAdicionalesMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NotasAdicionalesMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NotasAdicionalesMglManager {

    NotasAdicionalesMglDaoImpl notasAdicionalesMglDaoImpl = new NotasAdicionalesMglDaoImpl();
    
    public List<NotasAdicionalesMgl> findAll() throws ApplicationException {
        List<NotasAdicionalesMgl> result;
        NotasAdicionalesMglDaoImpl notasAdicionalesMglDaoImpl1 = new NotasAdicionalesMglDaoImpl();
        result = notasAdicionalesMglDaoImpl1.findAll();
        return result;
    }
    
    public NotasAdicionalesMgl create(NotasAdicionalesMgl notasAddMgl) throws ApplicationException {
        return notasAdicionalesMglDaoImpl.create(notasAddMgl);
    }
    
    /**
     * yfvalbuena metodo para listar las notas dicionales de un hhpp
     *
     * @param hhpId
     * @return
     * @throws ApplicationException
     */
    public List<NotasAdicionalesMgl> findNotasAdicionalesIdHhpp(BigDecimal hhpId) throws ApplicationException {
        List<NotasAdicionalesMgl> respuesta;
        NotasAdicionalesMglDaoImpl notasAdicionalesMglDaoImpl1 = new NotasAdicionalesMglDaoImpl();
        respuesta = notasAdicionalesMglDaoImpl1.findNotasAdicionalesIdHhpp(hhpId);
        return respuesta;
    }
}
