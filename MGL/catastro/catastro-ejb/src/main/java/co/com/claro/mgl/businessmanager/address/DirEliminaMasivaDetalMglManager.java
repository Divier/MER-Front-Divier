/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.DirEliminaMasivaDetalMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DirEliminaMasivaDetalMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DirEliminaMasivaDetalMglManager {

    public List<DirEliminaMasivaDetalMgl> findAll() throws ApplicationException {

        List<DirEliminaMasivaDetalMgl> resulList;
        DirEliminaMasivaDetalMglDaoImpl dirEliminaMasivaDetalMglDaoImpl = new DirEliminaMasivaDetalMglDaoImpl();

        resulList = dirEliminaMasivaDetalMglDaoImpl.findAll();

        return resulList;
    }

    public DirEliminaMasivaDetalMgl create(DirEliminaMasivaDetalMgl dirEliminaMasivaDetalMgl) throws ApplicationException {
        DirEliminaMasivaDetalMglDaoImpl dirEliminaMasivaDetalMglDaoImpl = new DirEliminaMasivaDetalMglDaoImpl();
        return dirEliminaMasivaDetalMglDaoImpl.create(dirEliminaMasivaDetalMgl);

    }

    public List<DirEliminaMasivaDetalMgl> findByLemId(BigDecimal lemId) throws ApplicationException {

        List<DirEliminaMasivaDetalMgl> resultList;
        DirEliminaMasivaDetalMglDaoImpl dirEliminaMasivaDetalMglDaoImpl = new DirEliminaMasivaDetalMglDaoImpl();
        resultList = dirEliminaMasivaDetalMglDaoImpl.findByLemId(lemId);

        return resultList;
    }
}
