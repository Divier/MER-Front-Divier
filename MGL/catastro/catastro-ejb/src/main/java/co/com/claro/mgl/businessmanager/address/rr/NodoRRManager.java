/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address.rr;

import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.dao.impl.rr.NodoRRDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.rr.NodoRR;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.business.NodoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 *
 * @author User
 */
public class NodoRRManager {

    private static final Logger LOGGER = LogManager.getLogger(NodoRRManager.class);

    public NodoRR findNodo(String codigoNodo) throws ApplicationException {
        NodoRRDaoImpl nodoRRDaoImpl = new NodoRRDaoImpl();
        NodoRR result = nodoRRDaoImpl.find(codigoNodo);
        return result;
    }

    public boolean isNodoRRCertificado(String codNodo, String user) throws ApplicationException {
        try {

            boolean isCertificado = false;
	    String niac =Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO;
            NodoRRDaoImpl nodoRRDaoImpl = new NodoRRDaoImpl();
            NodoRR nodoRr = nodoRRDaoImpl.find(codNodo.toUpperCase());
            CmtBasicaMgl estado = null;
            NodoMglManager nodoMglManager = new NodoMglManager();


            if (nodoRr != null) {
                LOGGER.info("NODO Encontrado en RR: " + nodoRr.getId());

                estado = nodoRr.getEstado();
                if (estado != null && !estado.getIdentificadorInternoApp().trim().isEmpty()
                           && !estado.getIdentificadorInternoApp().equals(niac)) {
                    isCertificado = true;
                } else {
                    isCertificado = false;
                }
                //buscamos en el nodo en la tabla RR_NODO del repositorio para obtener la comunidad del nodo                
                NodoMgl nodoMglFind = nodoMglManager.findByCodigo(nodoRr.getId());
                if (nodoMglFind != null) {

                    if (nodoMglFind.getGpoId() != null) {

                        nodoMglFind.setEstado(estado);
                        if (isCertificado) {
                            nodoMglFind.setNodFechaActivacion(nodoRr.getFechaApertura());
                        }
                        nodoMglFind.setNodFechaModificacion(new Date());
                        nodoMglFind.setNodUsuarioModificacion(user);
                        nodoMglManager.update(nodoMglFind);

                    } else {
                        LOGGER.error("ciudad no encontrada en Geografico_politico: ");
                    }

                } else {
                    LOGGER.error("Nodo no encontrado en RR_NODO en el repositorio: " + nodoRr.getId());
                }
            }
            return isCertificado;
        } catch (ApplicationException e) {
            LOGGER.error("Error en isNodoRRcertificado. " +e.getMessage(), e);  
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Retorna el objeto nodo desde la Tabla RR_NODO en BD repositorio.Recibe como argumento el codigo del nodo
     *
     * @param codNodo codigo alfanumerico del nodo
     * @return objeto co.com.claro.visitasTecnicas.business.NodoRR desde la tabla RR_NODO
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public co.com.claro.visitasTecnicas.business.NodoRR getRRNodoFromDB(String codNodo) throws ApplicationException {
        //buscamos en el nodo en la tabla RR_NODO del repositorio para obtener la comunidad del nodo 
        NodoManager nodoRRManagerDB = new NodoManager();
        return nodoRRManagerDB.queryNodoRR(codNodo);
    }
}
