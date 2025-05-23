/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.cmas400.ejb.request.HhppPaginationRequest;
import co.com.claro.cmas400.ejb.respons.HhppId;
import co.com.claro.cmas400.ejb.respons.HhppPaginationResponse;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dtos.HhppPaginationRequestDto;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientHhppPagination;
import co.com.claro.mgl.error.ApplicationException;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 *
 * @author Orlando Velasquez
 */
public class HhppPaginationManager {

    private ParametrosMglManager parametrosMglManager;
    private RestClientHhppPagination restClientHhppPagination;
    private String BASE_URI;

    private static final Logger LOGGER = LogManager.getLogger(HhppPaginationManager.class);

    public HhppPaginationManager() throws ApplicationException {
        parametrosMglManager = new ParametrosMglManager();
        BASE_URI = parametrosMglManager.findByAcronimo(
                ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                .iterator().next().getParValor();

        restClientHhppPagination = new RestClientHhppPagination(BASE_URI);

    }

    public HhppPaginationResponse findHhppRRPagination(
            HhppPaginationRequestDto hhppPaginationRequestDto, String usuario) throws ApplicationException {
        try {
            int solicitados = Integer.valueOf(hhppPaginationRequestDto.getCantidadRegistrosSolicitados());
            HhppPaginationRequest hhppPaginationRequest = new HhppPaginationRequest();
            hhppPaginationRequest.setDatein(hhppPaginationRequestDto.getFechaInicio());
            hhppPaginationRequest.setHourin(hhppPaginationRequestDto.getHoraInicio());
            hhppPaginationRequest.setDateen(hhppPaginationRequestDto.getFechaFin());
            hhppPaginationRequest.setHouren(hhppPaginationRequestDto.getHoraFin());
            hhppPaginationRequest.setCtdrge(hhppPaginationRequestDto.getCantidadRegistrosSolicitados());
            hhppPaginationRequest.setCtdrgr("0000000000");
            hhppPaginationRequest.setCtdrgs("0000000000");
            hhppPaginationRequest.setUnakyn("000000000");
            hhppPaginationRequest.setIduser(usuario);
            boolean terminar = true;
            HhppPaginationResponse hhppPaginationResponse = new HhppPaginationResponse();
            int num_request_max = 100;
            int num_request_i = 0;
            do{
                HhppPaginationResponse hhppPaginationResponseTemp = restClientHhppPagination.callWebServiceMethodPUT(
                        EnumeratorServiceName.PAGINATION_HHPP,
                        HhppPaginationResponse.class,
                        hhppPaginationRequest);
                if (hhppPaginationResponseTemp.getIdendm().equalsIgnoreCase("INS0000")) {
                    String ultimo = null;
                    for ( HhppId temporal: hhppPaginationResponseTemp.getArrunky() ){
                        hhppPaginationResponse.getArrunky().add(temporal);
                        ultimo = temporal.getHhppId();
                    }
                    if ( solicitados > hhppPaginationResponseTemp.getArrunky().size()){
                        terminar = false;
                    }else{
                        hhppPaginationRequest.setUnakyn(String.format("%9s", ultimo).replace(' ', '0'));
                    }   
                } else {//error en el servicio
                    hhppPaginationResponse = null;
                    terminar = false;
                    throw new ApplicationException( hhppPaginationResponseTemp.getIdendm()+":"+
                            hhppPaginationResponseTemp.getEndmsg() );
                }
                num_request_i++;
                if(num_request_i > num_request_max){
                    terminar = false;
                }
            }while ( terminar );
            return hhppPaginationResponse;
        } catch (UniformInterfaceException | IOException ex) {
            String errorProceso = "Error:HhppPaginationManager:findHhppRRPagination "+
                    ex.getMessage()+" Proceso masivo hhpp no termina satisfactoriamente. ";
            LOGGER.error(errorProceso);
            throw new ApplicationException( errorProceso , ex );
        } catch (Exception ex) {
            String errorProceso = "Error:HhppPaginationManager:findHhppRRPagination: "+
                    ex.getMessage()+" Proceso masivo hhpp";
            LOGGER.error(errorProceso);
            throw new ApplicationException( errorProceso , ex );
        }
    }
}
