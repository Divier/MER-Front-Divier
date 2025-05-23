/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.app.dtos.AppCancelarAgendaOtRequest;
import co.com.claro.app.dtos.AppConsultarAgendasOtRequest;
import co.com.claro.app.dtos.AppConsultarCapacityOtRequest;
import co.com.claro.app.dtos.AppConsultarFactibilidadTrasladoRequest;
import co.com.claro.app.dtos.AppCrearAgendaOtRequest;
import co.com.claro.app.dtos.AppCrearOtRequest;
import co.com.claro.app.dtos.AppModificarOtRequest;
import co.com.claro.app.dtos.AppReagendarOtRequest;
import co.com.claro.app.dtos.AppResponseCrearOtDto;
import co.com.claro.app.dtos.AppResponseUpdateOtDto;
import co.com.claro.app.dtos.AppResponsesAgendaDto;
import co.com.claro.app.dtos.AppResponsesGetCapacityDto;
import co.com.claro.mgl.businessmanager.address.OtCMHhppMglManager;
import co.com.claro.mgl.businessmanager.address.OtCMHhppTMMglManager;
import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.error.ApplicationException;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.io.IOException;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class OtCMHhppMglFacade implements OtCMHhppMglFacadeLocal {

    /**
     * Metodo alterno de creacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Divier Casas
     * @version 1.0 revision
     * @param appCrearOtRequest
     * @return AppResponseCrearOtDto
     */
    @Override
    public AppResponseCrearOtDto createOrderSupport(AppCrearOtRequest appCrearOtRequest) {

        OtCMHhppTMMglManager otCMHhppTMMglManager = new OtCMHhppTMMglManager();
        return otCMHhppTMMglManager.createOrderSupport(appCrearOtRequest);
    }
    
    /**
     * Creacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param appCrearOtRequest
     * @return AppResponseCrearOtDto
     */
    @Override
    public AppResponseCrearOtDto crearOtCcmmHhppApp(AppCrearOtRequest appCrearOtRequest) {

        OtCMHhppMglManager otCMHhppMglManager = new OtCMHhppMglManager();
        return otCMHhppMglManager.crearOtCcmmHhppApp(appCrearOtRequest);
    }

    /**
     * Modificacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param appModificarOtRequest
     * @return AppResponseUpdateOtDto
     */
    @Override
    public AppResponseUpdateOtDto modificarOTCcmmHhppApp(AppModificarOtRequest appModificarOtRequest) {

        OtCMHhppMglManager otCMHhppMglManager = new OtCMHhppMglManager();
        return otCMHhppMglManager.modificarOTCcmmHhppApp(appModificarOtRequest);
    }

    /**
     * Consulta del capacity en OFSC de una OT desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param consultarCapacityOtDirRequest
     * @return AppResponsesGetCapacityDto
     */
    @Override
    public AppResponsesGetCapacityDto getCapacidadOtCcmmHhppApp(AppConsultarCapacityOtRequest consultarCapacityOtRequest) throws IOException {

        OtCMHhppMglManager otCMHhppMglManager = new OtCMHhppMglManager();
        return otCMHhppMglManager.getCapacidadOtCcmmHhppApp(consultarCapacityOtRequest);
    }

    /**
     * Metodo para crear una agenda de Orden desde el sistema APP
     *
     * @author Divier Casas
     * @version 1.0 revision .
     * @param crearAgendaOtRequest
     * @return AppResponsesAgendaDto
     * @throws java.io.IOException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public AppResponsesAgendaDto agendarOtCcmmHhppTMApp(AppCrearAgendaOtRequest crearAgendaOtRequest)
            throws UniformInterfaceException, IOException, ApplicationException {

        OtCMHhppTMMglManager otCMHhppTMMglManager = new OtCMHhppTMMglManager();
        return otCMHhppTMMglManager.agendarOtCcmmHhppTMApp(crearAgendaOtRequest);
    }
    
    /**
     * Metodo para crear una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param crearAgendaOtDirRequest
     * @return AppResponsesAgendaDto
     */
    @Override
    public AppResponsesAgendaDto agendarOtCcmmHhppApp(AppCrearAgendaOtRequest crearAgendaOtRequest)
            throws UniformInterfaceException, IOException, ApplicationException {

        OtCMHhppMglManager otCMHhppMglManager = new OtCMHhppMglManager();
        return otCMHhppMglManager.agendarOtCcmmHhppApp(crearAgendaOtRequest);
    }

    /**
     * Metodo para reagendar una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param reagendarOtRequest
     * @return AppResponsesAgendaDto
     */
    @Override
    public AppResponsesAgendaDto reagendarOtCcmmHhppApp(AppReagendarOtRequest reagendarOtRequest)
            throws ApplicationException {

        OtCMHhppMglManager otCMHhppMglManager = new OtCMHhppMglManager();
        return otCMHhppMglManager.reagendarOtCcmmHhppApp(reagendarOtRequest);

    }

    /**
     * Metodo para cancelar una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param cancelarAgeRequest
     * @return AppResponsesAgendaDto
     */
    @Override
    public AppResponsesAgendaDto cancelarAgendaOtCcmmHhppApp(AppCancelarAgendaOtRequest cancelarAgeRequest)
            throws ApplicationException {
        OtCMHhppMglManager otCMHhppMglManager = new OtCMHhppMglManager();
        return otCMHhppMglManager.cancelarAgendaOtCcmmHhppApp(cancelarAgeRequest);
    }

    /**
     * Metodo para consultar las agendas by id Enlace desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param consultarAgendasOtRequest
     * @return AppResponsesAgendaDto
     */
    @Override
    public AppResponsesAgendaDto consultarAgendasOtCcmmHhppApp(AppConsultarAgendasOtRequest consultarAgendasOtRequest)
            throws ApplicationException {
        OtCMHhppMglManager otCMHhppMglManager = new OtCMHhppMglManager();
        return otCMHhppMglManager.consultarAgendasOtCcmmHhppApp(consultarAgendasOtRequest);
    }

    /**
     * Metodo para consultar la factibilidad de un traslado de direccion desde
     * el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param trasladoRequest
     * @return CmtDefaultBasicResponse
     */
    @Override
    public CmtDefaultBasicResponse consultarFactibilidadTrasladoApp(AppConsultarFactibilidadTrasladoRequest trasladoRequest)
            throws ApplicationException {
        OtCMHhppMglManager otCMHhppMglManager = new OtCMHhppMglManager();
        return otCMHhppMglManager.consultarFactibilidadTrasladoApp(trasladoRequest);
    }
}
