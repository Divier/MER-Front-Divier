/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.business.NodoManager;
import co.com.claro.visitasTecnicas.business.NodoRR;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.telmex.catastro.data.Nodo;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author User
 */
@Slf4j
public class FichaManager {

    public boolean crearHHPPinRR(PreFichaXlsMgl fichaXlsMgl, Nodo nodo) throws ApplicationException {

        boolean result = false;
        try {
            NodoManager nodoManager = new NodoManager();

            //buscamos el nodo el la tabla RR_NODOS para obtener la comunidad y la division
            NodoRR nodoRR = nodoManager.queryNodoRR(nodo.getNodCodigo());
            if (nodoRR == null) {
                throw new ApplicationException("El Nodo " + nodo.getNodCodigo() + " no existe");
            }
            String comunidad = nodoRR.getCodCiudad();

            if (fichaXlsMgl.getFichaGeorreferenciaMgl() != null
                    && (fichaXlsMgl.getFichaGeorreferenciaMgl().getIdAddress() == null
                    || fichaXlsMgl.getFichaGeorreferenciaMgl().getIdAddress().trim().isEmpty()
                    || fichaXlsMgl.getFichaGeorreferenciaMgl().getIdAddress().trim().equalsIgnoreCase("0"))) {

                //si la direccion no ha sio creada en el repositorio, se debe crear  
                NegocioParamMultivalor negocioParamMultivalor = new NegocioParamMultivalor();
                CityEntity cityEntity = negocioParamMultivalor.consultaDptoCiudad(comunidad);
                LOGGER.info(cityEntity.toString());
            }

        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage(), e);
        }
        return result;

    }

}
