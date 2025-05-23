/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.app.dtos.AppCancelarAgendaOtRequest;
import co.com.claro.app.dtos.AppConsultarCapacityOtRequest;
import co.com.claro.app.dtos.AppConsultarAgendasOtRequest;
import co.com.claro.app.dtos.AppConsultarFactibilidadTrasladoRequest;
import co.com.claro.app.dtos.AppCrearAgendaOtRequest;
import co.com.claro.app.dtos.AppCrearOtRequest;
import co.com.claro.app.dtos.AppModificarOtRequest;
import co.com.claro.app.dtos.AppResponseCrearOtDto;
import co.com.claro.app.dtos.AppResponseUpdateOtDto;
import co.com.claro.app.dtos.AppResponsesAgendaDto;
import co.com.claro.app.dtos.AppResponsesGetCapacityDto;
import co.com.claro.app.dtos.AppReagendarOtRequest;
import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.error.ApplicationException;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.io.IOException;

/**
 *
 * @author bocanegravm
 */
public interface OtCMHhppMglFacadeLocal {

    /**
     * Metodo alterno de creacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Divier Casas
     * @version 1.0 revision
     * @param appCrearOtRequest
     * @return AppResponseCrearOtDto
     */
    AppResponseCrearOtDto createOrderSupport(AppCrearOtRequest appCrearOtRequest);
    
    /**
     * Creacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param appCrearOtRequest
     * @return AppResponseCrearOtDto
     */
    AppResponseCrearOtDto crearOtCcmmHhppApp(AppCrearOtRequest appCrearOtRequest);

    /**
     * Modificacion de una OT de CCMM/HHPP desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param appModificarOtRequest
     * @return AppResponseUpdateOtDto
     */
    AppResponseUpdateOtDto modificarOTCcmmHhppApp(AppModificarOtRequest appModificarOtRequest);

    /**
     * Consulta del capacity en OFSC de una OT desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param consultarCapacityOtDirRequest
     * @return AppResponsesGetCapacityDto
     */
    AppResponsesGetCapacityDto getCapacidadOtCcmmHhppApp(AppConsultarCapacityOtRequest consultarCapacityOtRequest)
            throws IOException;

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
    AppResponsesAgendaDto agendarOtCcmmHhppTMApp(AppCrearAgendaOtRequest crearAgendaOtRequest)
            throws UniformInterfaceException, IOException, ApplicationException;
    
    /**
     * Metodo para crear una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param crearAgendaOtDirRequest
     * @return AppResponsesAgendaDto
     */
    AppResponsesAgendaDto agendarOtCcmmHhppApp(AppCrearAgendaOtRequest crearAgendaOtRequest)
            throws UniformInterfaceException, IOException, ApplicationException;

    /**
     * Metodo para reagendar una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param reagendarOtRequest
     * @return AppResponsesAgendaDto
     */
    AppResponsesAgendaDto reagendarOtCcmmHhppApp(AppReagendarOtRequest reagendarOtRequest)
            throws ApplicationException;

    /**
     * Metodo para cancelar una agenda de Orden desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param cancelarAgeRequest
     * @return AppResponsesAgendaDto
     */
    AppResponsesAgendaDto cancelarAgendaOtCcmmHhppApp(AppCancelarAgendaOtRequest cancelarAgeRequest)
            throws ApplicationException;
    
        /**
     * Metodo para consultar las agendas by id Enlace desde el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param consultarAgendasOtRequest
     * @return AppResponsesAgendaDto
     */
    AppResponsesAgendaDto consultarAgendasOtCcmmHhppApp(AppConsultarAgendasOtRequest consultarAgendasOtRequest)
            throws ApplicationException;
    
      /**
     * Metodo para consultar la factibilidad de un traslado de direccion desde
     * el sistema APP
     *
     * @author Victor Bocanegra
     * @version 1.0 revision .
     * @param trasladoRequest
     * @return CmtDefaultBasicResponse
     */
    CmtDefaultBasicResponse consultarFactibilidadTrasladoApp(AppConsultarFactibilidadTrasladoRequest trasladoRequest)
            throws ApplicationException;

}
