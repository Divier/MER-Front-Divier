/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.ConfCreahppAutoDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ConfCreahppAuto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import java.util.List;

/**
 * Manager de la configuracion de para creacion de HHPP. Permite obtener la
 * informacion para conocer la configuracion de las ciudades para la creacion
 * autmatica de los HHPP desde la solicitud.
 *
 * @author Johnnatan Ortiz
 * @versión 1.00.000
 */
public class ConfCreahppAutoManager {

    /**
     * Obtien la configuracion para una ciudad.Permite obtener los datos de
 configuracion para la creacion automatica de un HHPP en la creacion de la
 solicitud para una ciudad determinada.
     *
     * @author Johnnatan Ortiz
     * @param city ciudad de la cual se desea consultar la configuracion.
     * @return configuracion de creacion automatica de la ciudad.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ConfCreahppAuto findByIdCity(GeograficoPoliticoMgl city)
            throws ApplicationException {
        ConfCreahppAutoDaoImpl daoImpl = new ConfCreahppAutoDaoImpl();
        return daoImpl.findByIdCity(city);
    }

    /**
     * Obtien la configuracion para una comunidad.Permite obtener los datos de
 configuracion para la creacion automatica de un HHPP en la creacion de la
 solicitud para una comunidad determinada.
     *
     * @author Johnnatan Ortiz
     * @param codComunidad codigo de la comunidad de la cual se desea consultar
     * la configuracion.
     * @return configuracion de creacion automatica de la ciudad.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ConfCreahppAuto findByCodComunidad(String codComunidad)
            throws ApplicationException {
        GeograficoPoliticoManager geograficoPoliticoManager =
                new GeograficoPoliticoManager();
        GeograficoPoliticoMgl geoPolMgl =
                geograficoPoliticoManager.findCityByCodComunidad(codComunidad);
        return findByIdCity(geoPolMgl);
    }

    /**
     * Obtiene todas las configuraciones.Permite obtener los datos de
 configuracion almacenados.
     *
     * @author Johnnatan Ortiz
     * @return configuracion de creacion automatica de la ciudad.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<ConfCreahppAuto> findAll()
            throws ApplicationException {
        ConfCreahppAutoDaoImpl daoImpl = new ConfCreahppAutoDaoImpl();
        return daoImpl.findAll();
    }

    /**
     * Crea la configuracion para una ciudad.Permite crear los datos de
 configuracion para la creacion automatica de un HHPP en la creacion de la
 solicitud para una ciudad determinada.
     *
     * @author Johnnatan Ortiz
     * @param conf
     * @param city configuracion de la ciudad que se desea actualizar.
     * @return configuracion de creacion automatica de la ciudad.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ApplicationException.
     */
    public ConfCreahppAuto createConfiguracion(ConfCreahppAuto conf)
            throws ApplicationException {
        ConfCreahppAutoDaoImpl daoImpl = new ConfCreahppAutoDaoImpl();
        return daoImpl.create(conf);
    }

    /**
     * Actualiza la configuracion para una ciudad.Permite actualizar los datos
 de configuracion para la creacion automatica de un HHPP en la creacion de
 la solicitud para una ciudad determinada.
     *
     * @author Johnnatan Ortiz
     * @param conf
     * @param city configuracion de la ciudad que se desea actualizar.
     * @return configuracion de creacion automatica de la ciudad.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ApplicationException.
     */
    public ConfCreahppAuto updateConfiguracion(ConfCreahppAuto conf)
            throws ApplicationException {
        ConfCreahppAutoDaoImpl daoImpl = new ConfCreahppAutoDaoImpl();
        return daoImpl.update(conf);
    }

    /**
     * Actualiza la configuracion para una ciudad.Permite actualizar los datos
 de configuracion para la creacion automatica de un HHPP en la creacion de
 la solicitud para una ciudad determinada.
     *
     * @author Johnnatan Ortiz
     * @param conf
     * @param city configuracion de la ciudad que se desea actualizar.
     * @return configuracion de creacion automatica de la ciudad.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ApplicationException.
     */
    public boolean deleteConfiguracion(ConfCreahppAuto conf)
            throws ApplicationException {
        ConfCreahppAutoDaoImpl daoImpl = new ConfCreahppAutoDaoImpl();
        return daoImpl.delete(conf);
    }
}
