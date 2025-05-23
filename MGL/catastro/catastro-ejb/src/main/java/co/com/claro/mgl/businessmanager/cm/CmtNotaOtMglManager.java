/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtNotaOtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 * Manager Nota Orden de Trabajo. Contiene la logica de negocio de las notas de
 * ordenes de trabajo en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtNotaOtMglManager {
    
    /**
     * Crea una nota asociadas a una Ordene de Trabajo.Permite realizar la
 creacion de una nota de una Ordene de Trabajo en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param notaOtMgl nota de la orden de trabajo
     * @param usuario
     * @param perfil
     * @return nota creada en el repositorio
     * @throws ApplicationException
     */
    public CmtNotaOtMgl crear(CmtNotaOtMgl notaOtMgl, String usuario, int perfil) throws ApplicationException {
        CmtNotaOtMglDaoImpl dao = new CmtNotaOtMglDaoImpl();
        return dao.createCm(notaOtMgl, usuario, perfil);
    }
    
    /**
     * Busca las notas asociadas a una Ordene de Trabajo. Permite realizar la
     * busqueda de las notas de una Ordene de Trabajo en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param ordenTrabajo orden de trabajo
     * @return notas asociadas a una orden de trabajo
     * @throws ApplicationException
     */
    public List<CmtNotaOtMgl> findByOt(CmtOrdenTrabajoMgl ordenTrabajo) throws ApplicationException {
        CmtNotaOtMglDaoImpl dao = new CmtNotaOtMglDaoImpl();
        return dao.findByOt(ordenTrabajo);
    }
    
    /**
     * Busca las notas asociadas a una Cuenta Matriz.Permite realizar la
 busqueda de las notas de una Cuenta Matriz en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatriz Cuenta Matriz
     * @param tipoNota
     * @return notas asociadas a una Cuenta Matriz
     * @throws ApplicationException
     */
    public List<CmtNotaOtMgl> findByCm(CmtCuentaMatrizMgl cuentaMatriz,BigDecimal tipoNota) throws ApplicationException {
        CmtNotaOtMglDaoImpl dao = new CmtNotaOtMglDaoImpl();
        return dao.findByCm(cuentaMatriz,tipoNota);
    }
}
