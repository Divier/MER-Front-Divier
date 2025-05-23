/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import java.util.ArrayList;
import net.telmex.pcml.service.Edificio;
import net.telmex.pcml.service.OrdenTrabajoVO;
import net.telmex.pcml.service.ServiceStruct;
import net.telmex.pcml.service.SuscriberInfoVO;

/**
 *
 * @author User
 */
public interface PcmlFacadeLocal {

    String getCtaMatrizDir(String calle, String casa, String apto, String comunidad) throws ApplicationException;

    ArrayList<UnidadStructPcml> getUnidades(String calle, String casa, String apto, String comunidad) throws ApplicationException;

    ArrayList<OrdenTrabajoVO> getWorkOrders(String cta, String calle, String casa, String apto, String comunidad) throws ApplicationException;

    ArrayList<SuscriberInfoVO> getSuscriberInfo2(String cta) throws ApplicationException;

    ArrayList<ServiceStruct> getServicesSummaryGetServicesBySchedules(String cta, String comunidad, String division) throws ApplicationException;
    
    Edificio getEdificio(String idUnidad) throws ApplicationException;
}
