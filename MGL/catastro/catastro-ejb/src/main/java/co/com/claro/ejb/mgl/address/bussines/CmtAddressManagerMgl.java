/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ejb.mgl.address.bussines;

import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.SubDireccionMglManager;
import co.com.claro.mgl.businessmanager.address.UbicacionMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.ejb.mgl.address.dto.CmtAddressToCreateProces;
import co.com.claro.ejb.mgl.address.dto.CmtCreateAddressPeticionItemDto;
import co.com.claro.ejb.mgl.address.dto.CmtCreateAddressRequestDto;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que maneja la nueva logica de direcciones implemtada en su totalidad JEE
 * incluye el manejo de direccion y subdirccion
 * @author villamilc Hitss
 */
public class CmtAddressManagerMgl {

    DireccionMglManager direccionMglManager = new DireccionMglManager();
    SubDireccionMglManager subDireccionMglManager= new SubDireccionMglManager();
    CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager =new CmtDireccionDetalleMglManager();
    UbicacionMglManager ubicacionMglManager=new UbicacionMglManager();
    GeograficoMglManager geograficoMglManager= new GeograficoMglManager();
    GeograficoPoliticoManager geograficoPoliticoManager=new GeograficoPoliticoManager();
    CmtGeoreferenciadorManagerMgl cmtGeoreferenciadorManagerMgl= new CmtGeoreferenciadorManagerMgl();
    public CmtDireccionDetalladaMgl createAdress(
            CmtCreateAddressRequestDto createAddressRequestDto) 
            throws ApplicationException {
        if(createAddressRequestDto==null || 
                createAddressRequestDto.getCmtCreateAddressDtoList()==null ||
                createAddressRequestDto.getCmtCreateAddressDtoList().isEmpty()){
            throw new ApplicationException("No se puede procesar la creacion de direcciones,"
                    .concat(" no se enviaron direcciones para procesar."));
            
        }
        List<CmtAddressToCreateProces> cmtAddressToCreateProcesList=new ArrayList<>();
        for(CmtCreateAddressPeticionItemDto addressPeticionItemDto:createAddressRequestDto.getCmtCreateAddressDtoList()){
            CmtAddressToCreateProces addressToCreateProces=new CmtAddressToCreateProces(addressPeticionItemDto);
            GeograficoPoliticoMgl geograficoPoliticoMglCentroPoblado= geograficoPoliticoManager.
                    findCentroPobladoCodDane(addressPeticionItemDto.getPopulatedCenterDaneCode());
            if (geograficoPoliticoMglCentroPoblado == null) {
                addressToCreateProces.setNoProcess(true);
                addressToCreateProces.setMessage("Codigo dane no existe");
            } else {
                if (geograficoPoliticoMglCentroPoblado.getGpoMultiorigen().equals("1") &&
                        addressToCreateProces.getSplitAddressMglDto().getBarrio() == null ||
                        addressToCreateProces.getSplitAddressMglDto().getBarrio().isEmpty()) {
                    addressToCreateProces.setNoProcess(true);
                    addressToCreateProces.setMessage("El barrio es obligatorio para esta direccion"
                            .concat(", La ciudad es multiorigen"));
                }
            }
            addressToCreateProces.setGeograficoPoliticoMgl(geograficoPoliticoMglCentroPoblado);
                cmtAddressToCreateProcesList.add(addressToCreateProces);
        }
        cmtGeoreferenciadorManagerMgl.getGeoToAddressList(cmtAddressToCreateProcesList);    
        return null;
    }
}
