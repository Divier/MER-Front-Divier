package co.com.claro.mer.address.standardize.facade;

import co.com.claro.mer.address.standardize.application.services.StandardizeBmAddressService;
import co.com.claro.mer.address.standardize.application.services.StandardizeCkAddressService;
import co.com.claro.mer.address.standardize.application.services.StandardizeItAddressService;
import co.com.claro.mer.address.standardize.application.usecases.StandardizeAddressBmUseCaseImpl;
import co.com.claro.mer.address.standardize.application.usecases.StandardizeAddressCkUseCaseImpl;
import co.com.claro.mer.address.standardize.application.usecases.StandardizeAddressItUseCaseImpl;
import co.com.claro.mer.address.standardize.application.usecases.StandardizeComplementUseCaseImpl;
import co.com.claro.mer.address.standardize.domain.models.dto.BmAddressStructureDto;
import co.com.claro.mer.address.standardize.domain.models.dto.CkAddressStructureDto;
import co.com.claro.mer.address.standardize.domain.models.dto.ItAddressStructureDto;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeAddressBmUseCase;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeAddressCkUseCase;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeAddressItUseCase;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeComplementUseCase;
import co.com.claro.mer.address.standardize.mapper.DireccionDetalladaToBmAddressMapper;
import co.com.claro.mer.address.standardize.mapper.DireccionDetalladaToCkAddressMapper;
import co.com.claro.mer.address.standardize.mapper.DireccionDetalladaToItAddressMapper;
import co.com.claro.mer.utils.enums.AddressTypeEnum;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;

import javax.ejb.Stateless;
import java.util.Objects;

/**
 * Clase que implementa los procesos para estandarizar una dirección.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
@Stateless
public class StandardizeAddressFacade implements StandardizeAddressFacadeLocal {

    /**
     * Método que se encarga de estandarizar una dirección.
     *
     * @param detailedAddress Dirección que se va a estandarizar.
     * @param addressType Tipo de dirección que se va a estandarizar.
     * @return Dirección estandarizada.
     * @author Gildardo Mora
     */
    @Override
    public String standardizeAddress(CmtDireccionDetalladaMgl detailedAddress, AddressTypeEnum addressType) {
        IStandardizeComplementUseCase standardizeComplementUseCase = new StandardizeComplementUseCaseImpl();

        switch (addressType) {
            case CK:
                return getStandardizeCkAddress(detailedAddress, standardizeComplementUseCase, false);
            case BM:
                return getStandardizeBmAddress(detailedAddress, standardizeComplementUseCase);
            case IT:
                return getStandardizeItAddress(detailedAddress, standardizeComplementUseCase);
        }

        return "";
    }

    /**
     * Método que se encarga de estandarizar una dirección.
     *
     * @param address Dirección que se va a estandarizar.
     * @param addressType Tipo de dirección que se va a estandarizar.
     * @param isMultiOrigin Indica si la dirección es de una ciudad multi origen.
     * @return Dirección estandarizada.
     * @author Gildardo Mora
     */
    @Override
    public String standardizeAddress(CmtDireccionDetalladaMgl address, AddressTypeEnum addressType, boolean isMultiOrigin) {
        if (isMultiOrigin && AddressTypeEnum.CK.equals(addressType)) {
            IStandardizeComplementUseCase standardizeComplementUseCase = new StandardizeComplementUseCaseImpl();
            return getStandardizeCkAddress(address, standardizeComplementUseCase, true);
        }

        return standardizeAddress(address, addressType);
    }

    /**
     * Método que se encarga de estandarizar una dirección de tipo IT.
     * @param detailedAddress Dirección que se va a estandarizar.
     * @param standardizeComplementUseCase Caso de uso para estandarizar complementos.
     * @return Dirección estandarizada.
     * @author Gildardo Mora
     */
    private String getStandardizeItAddress(CmtDireccionDetalladaMgl detailedAddress, IStandardizeComplementUseCase standardizeComplementUseCase) {
        IStandardizeAddressItUseCase standardizeAddressItUseCase = new StandardizeAddressItUseCaseImpl();
        StandardizeItAddressService standardizeItAddress = new StandardizeItAddressService(standardizeAddressItUseCase, standardizeComplementUseCase);
        DireccionDetalladaToItAddressMapper itMapper = new DireccionDetalladaToItAddressMapper();
        ItAddressStructureDto itAddressStructureDto = itMapper.apply(detailedAddress);
        return standardizeItAddress.standardizeItAddress(itAddressStructureDto);
    }

    /**
     * Método que se encarga de estandarizar una dirección de tipo BM.
     * @param detailedAddress Dirección que se va a estandarizar.
     * @param standardizeComplementUseCase Caso de uso para estandarizar complementos.
     * @return Dirección estandarizada.
     * @author Gildardo Mora
     */
    private String getStandardizeBmAddress(CmtDireccionDetalladaMgl detailedAddress, IStandardizeComplementUseCase standardizeComplementUseCase) {
        IStandardizeAddressBmUseCase standardizeAddressBmUseCase = new StandardizeAddressBmUseCaseImpl();
        StandardizeBmAddressService standardizeBmAddressService = new StandardizeBmAddressService(standardizeAddressBmUseCase, standardizeComplementUseCase);
        DireccionDetalladaToBmAddressMapper bmMapper = new DireccionDetalladaToBmAddressMapper();
        BmAddressStructureDto bmAddressStructureDto = bmMapper.apply(detailedAddress);
        return standardizeBmAddressService.standardizeBmAddress(bmAddressStructureDto);
    }

    /**
     * Método que se encarga de estandarizar una dirección de tipo CK.
     * @param detailedAddress Dirección que se va a estandarizar.
     * @param standardizeComplementUseCase Caso de uso para estandarizar complementos.
     * @return Dirección estandarizada.
     * @author Gildardo Mora
     */
    private String getStandardizeCkAddress(CmtDireccionDetalladaMgl detailedAddress, IStandardizeComplementUseCase standardizeComplementUseCase, boolean isMultiOrigin) {
        DireccionDetalladaToCkAddressMapper ckMapper = new DireccionDetalladaToCkAddressMapper();
        CkAddressStructureDto ckAddressStructureDto = ckMapper.apply(detailedAddress);
        IStandardizeAddressCkUseCase standardizeAddressCkUseCase = new StandardizeAddressCkUseCaseImpl();
        StandardizeCkAddressService standardizeAddress = new StandardizeCkAddressService(standardizeAddressCkUseCase, standardizeComplementUseCase);
        String address = standardizeAddress.standardizeCkAddress(ckAddressStructureDto, isMultiOrigin);

        if (Objects.nonNull(detailedAddress.getItValorPlaca()) && detailedAddress.getItValorPlaca().toUpperCase().indexOf("CD") == 0
                && Objects.nonNull(ckAddressStructureDto.getMainTrackType()) && ckAddressStructureDto.getMainTrackType().toUpperCase().indexOf("CD") == 0) {
            address = getStandardizeItAddress(detailedAddress, standardizeComplementUseCase).trim()
            + " " + detailedAddress.getPlacaDireccion();
        }
        
        return address;
    }

}
