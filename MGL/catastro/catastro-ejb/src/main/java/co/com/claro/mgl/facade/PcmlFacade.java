
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.PcmlManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import java.util.ArrayList;
import javax.ejb.Stateless;
import net.telmex.pcml.service.Edificio;
import net.telmex.pcml.service.OrdenTrabajoVO;
import net.telmex.pcml.service.ServiceStruct;
import net.telmex.pcml.service.SuscriberInfoVO;

/**
 *
 * @author User
 */
@Stateless
public class PcmlFacade implements PcmlFacadeLocal {

    @Override
    public String getCtaMatrizDir(String calle, String casa, String apto, String comunidad) throws ApplicationException {

        PcmlManager pcmlManager = new PcmlManager();
        return pcmlManager.getCtaMatrizDir(calle, casa, apto, comunidad);

    }

    @Override
    public ArrayList<UnidadStructPcml> getUnidades(String calle, String casa, String apto, String comunidad) throws ApplicationException {
        PcmlManager pcmlManager = new PcmlManager();
        return pcmlManager.getUnidades(calle, casa, apto, comunidad);
    }

    @Override
    public ArrayList<OrdenTrabajoVO> getWorkOrders(String cta, String calle, String casa, String apto, String comunidad) throws ApplicationException {
        PcmlManager pcmlManager = new PcmlManager();
        return pcmlManager.getWorkOrders(cta, calle, casa, apto, comunidad);
    }

    @Override
    public ArrayList<SuscriberInfoVO> getSuscriberInfo2(String cta) throws ApplicationException {
        PcmlManager pcmlManager = new PcmlManager();
        return pcmlManager.getSuscriberInfo2(cta);

    }

    @Override
    public ArrayList<ServiceStruct> getServicesSummaryGetServicesBySchedules(String cta, String comunidad, String division) throws ApplicationException {
        PcmlManager pcmlManager = new PcmlManager();
        return pcmlManager.getServicesSummaryGetServicesBySchedules(cta, comunidad, division);
    }
    
    @Override
    public Edificio getEdificio(String idUnidad) throws ApplicationException{
        PcmlManager pcmlManager = new PcmlManager();
        return pcmlManager.getEdificio(idUnidad);
    }
    
}
