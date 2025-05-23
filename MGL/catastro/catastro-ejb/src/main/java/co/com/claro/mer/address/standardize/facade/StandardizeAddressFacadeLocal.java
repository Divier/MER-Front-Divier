package co.com.claro.mer.address.standardize.facade;

import co.com.claro.mer.utils.enums.AddressTypeEnum;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;

/**
 * Fachada que define las operaciones para estandarizar una dirección.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
public interface StandardizeAddressFacadeLocal {

    /**
     * Método que se encarga de estandarizar una dirección.
     *
     * @param address Dirección que se va a estandarizar.
     * @param addressType Tipo de dirección que se va a estandarizar.
     * @return Dirección estandarizada.
     * @author Gildardo Mora
     */
    String standardizeAddress(CmtDireccionDetalladaMgl address, AddressTypeEnum addressType);

    /**
     * Método que se encarga de estandarizar una dirección.
     *
     * @param address Dirección que se va a estandarizar.
     * @param addressType Tipo de dirección que se va a estandarizar.
     * @param isMultiOrigin Indica si la dirección es de una ciudad multi origen.
     * @return Dirección estandarizada.
     * @author Gildardo Mora
     */
    String standardizeAddress(CmtDireccionDetalladaMgl address, AddressTypeEnum addressType, boolean isMultiOrigin);

}
