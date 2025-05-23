/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.services.util.Constant;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.rpc.ServiceException;
import net.telmex.pcml.service.Edificio;
import net.telmex.pcml.service.GetSuscriberOT;
import net.telmex.pcml.service.OrdenTrabajoVO;
import net.telmex.pcml.service.PcmService;
import net.telmex.pcml.service.PcmlLocator;
import net.telmex.pcml.service.PcmlService;
import net.telmex.pcml.service.Service;
import net.telmex.pcml.service.ServiceStruct;
import net.telmex.pcml.service.ServicesSummary;
import net.telmex.pcml.service.SuscriberInfoVO;
import net.telmex.pcml.service.UnidadStruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
public class PcmlManager {

    private static final Logger LOGGER = LogManager.getLogger(PcmlManager.class);
    private String urlServicePcml = "";

    public PcmlManager() {
        urlServicePcml = Utilidades.queryParametrosConfig(Constant.URL_SERVICE_PCML);
    }

    public String getCtaMatrizDir(String calle, String casa, String apto, String comunidad) throws ApplicationException {
        String result = "";
        try {
            PcmlLocator pcmlLocator = new PcmlLocator(urlServicePcml);
            String[] comunidades = {comunidad};
            PcmlService pcmlServicePort = pcmlLocator.getPcmlServicePort();
            UnidadStruct[] unidades = pcmlServicePort.getUnidades(calle, casa, apto, comunidades, 1);

            if (unidades != null
                    && unidades[0] != null
                    && unidades[0].getIdUnidad() != null) {
                LOGGER.error("IdUnidad: " + unidades[0].getIdUnidad());
                Edificio edificio = pcmlServicePort.getEdificio(unidades[0].getIdUnidad().toString());
                if (edificio != null
                        && edificio.getCodEdificio() != null
                        && !edificio.getCodEdificio().trim().isEmpty()) {
                    LOGGER.error("El HHPP tiene CM: " + edificio.getCodEdificio());
                    result = edificio.getCodEdificio();
                }
            }
        } catch (RemoteException | ServiceException e) {
            LOGGER.error("Error en PcmlManager - getCtaMatrizDir " + e.getMessage());
            throw new ApplicationException("Error en PcmlManager - getCtaMatrizDir " + e.getMessage());
        }
        return result;
    }

    public ArrayList<UnidadStructPcml> getUnidades(String calle, String casa, String apto, String comunidad) throws ApplicationException {
        ArrayList<UnidadStructPcml> unidades = new ArrayList<UnidadStructPcml>();
        try {
            PcmlLocator pcmlLocator = new PcmlLocator(urlServicePcml);
            String[] comunidades = {comunidad};
            PcmlService pcmlServicePort = pcmlLocator.getPcmlServicePort();
            UnidadStruct[] units = pcmlServicePort.getUnidades(calle, casa, apto, comunidades, 2000);
            if (units != null && units.length > 0) {
                for (UnidadStruct u : units) {
                    UnidadStructPcml unidad = convertUnidadStructToPcml(u);
                    unidades.add(unidad);
                }
            }

        } catch (RemoteException | ServiceException e) {
            LOGGER.error("Error en PcmlManager - getUnidades " + e.getMessage());
            throw new ApplicationException("Error en PcmlManager - getUnidades " + e.getMessage());
        }
        return unidades;
    }

    public ArrayList<OrdenTrabajoVO> getWorkOrders(String cta, String calle, String casa, String apto, String comunidad) throws ApplicationException {
        ArrayList<OrdenTrabajoVO> workOrdersList = new ArrayList<OrdenTrabajoVO>();
        try {
            PcmlLocator pcmlLocator = new PcmlLocator(urlServicePcml);
            PcmlService pcmlServicePort = pcmlLocator.getPcmlServicePort();
            OrdenTrabajoVO[] workOrdes = pcmlServicePort.getWorkOrders(cta);
            if (workOrdes != null && workOrdes.length > 0) {
                for (OrdenTrabajoVO wo : workOrdes) {
                    if (wo.getEstado() != null && !wo.getEstado().isEmpty() && wo.getEstado().equalsIgnoreCase("A")) {
                        workOrdersList.add(wo);
                    }
                }
            }

        } catch (RemoteException | ServiceException e) {
            LOGGER.error("Error en PcmlManager - getWorkOrders " + e.getMessage());
            throw new ApplicationException("Error en PcmlManager - getWorkOrders " + e.getMessage());
        }
        return workOrdersList;
    }

    public OrdenTrabajoVO getLastOTByAccount(String cuenta) throws ApplicationException {
        OrdenTrabajoVO ordenTrabajoVO = new OrdenTrabajoVO();
        ordenTrabajoVO.setIdOrdenServicio("0");
        try {
            PcmlLocator pcmlLocator = new PcmlLocator(urlServicePcml);
            PcmlService pcmlServicePort = pcmlLocator.getPcmlServicePort();
            OrdenTrabajoVO[] workOrdes = pcmlServicePort.getOTByAccount(cuenta);
            if (workOrdes != null && workOrdes.length > 0) {
                for (OrdenTrabajoVO wo : workOrdes) {

                    if (wo.getEstado() != null && !wo.getEstado().isEmpty() && !wo.getIdOrdenServicio().trim().equals("")) {
                        int numeroOtLista = Integer.parseInt(wo.getIdOrdenServicio());
                        int numeroOtUltimo = Integer.parseInt(ordenTrabajoVO.getIdOrdenServicio());
                        if (numeroOtLista >= numeroOtUltimo) {
                            ordenTrabajoVO = wo;
                        }
                    }
                }
            }
        } catch (NumberFormatException | RemoteException | ServiceException e) {
            LOGGER.error("Error en PcmlManager - getWorkOrders " + e.getMessage());
            throw new ApplicationException("Error en PcmlManager - getWorkOrders " + e.getMessage());
        }
        if (ordenTrabajoVO.getIdOrdenServicio().equals("0")) {
            ordenTrabajoVO.setIdOrdenServicio("");
        }
        return ordenTrabajoVO;
    }

    public ArrayList<SuscriberInfoVO> getSuscriberInfo2(String cta) throws ApplicationException {
        ArrayList<SuscriberInfoVO> suscriberInfo = new ArrayList<SuscriberInfoVO>();
        try {
            PcmlLocator pcmlLocator = new PcmlLocator(urlServicePcml);
            PcmlService pcmlServicePort = pcmlLocator.getPcmlServicePort();
            SuscriberInfoVO[] suscriberInfo2 = pcmlServicePort.getSuscriberInfo2("SU", cta, null);
            suscriberInfo.addAll(Arrays.asList(suscriberInfo2));
        } catch (RemoteException | ServiceException e) {
            LOGGER.error("Error en PcmlManager - getSuscriberInfo2 " + e.getMessage());
            throw new ApplicationException("Error en PcmlManager - getSuscriberInfo2 " + e.getMessage());
        }
        return suscriberInfo;
    }

    public String getInstalledServises(String cta, String comunidad, String division) throws ApplicationException {
        StringBuilder serviciosInstalados = new StringBuilder();
        ArrayList<ServiceStruct> listServises = getServicesSummaryGetServicesBySchedules(cta, comunidad, division);
        for (ServiceStruct ss : listServises) {
            serviciosInstalados.append(ss.getServiceName() + ";  ");
        }
        return serviciosInstalados.toString();
    }

    public ArrayList<ServiceStruct> getServicesSummaryGetServicesBySchedules(String cta, String comunidad, String division) throws ApplicationException {
        ArrayList<ServiceStruct> serviceStruct = new ArrayList<ServiceStruct>();
        String rateSchedule = "";
        try {
            PcmlLocator pcmlLocator = new PcmlLocator(urlServicePcml);
            PcmlService pcmlServicePort = pcmlLocator.getPcmlServicePort();
            ServicesSummary servicesSummary = pcmlServicePort.getServicesSummary(cta);
            rateSchedule = servicesSummary.getRateSchedule();
            Service[] services = servicesSummary.getServices();

            PcmService service = new PcmService();
            service.setComunidad(comunidad);
            service.setDivision(division);
            service.setTarifa(rateSchedule);
            ServiceStruct[] servicesSchedules = pcmlServicePort.getServicesBySchedules(service);

            for (Service s : services) {
                for (ServiceStruct sh : servicesSchedules) {
                    if (s.getCode().equals(sh.getServiceCode())) {
                        serviceStruct.add(sh);
                        break;
                    }
                }
            }

        } catch (RemoteException | ServiceException e) {
            LOGGER.error("Error en PcmlManager - getServicesSummaryGetServicesBySchedules " + e.getMessage());
            throw new ApplicationException("Error en getServicesSummaryGetServicesBySchedules - getServicesSummaryGetServicesBySchedules " + e.getMessage());
        }
        return serviceStruct;
    }

    public UnidadStructPcml convertUnidadStructToPcml(UnidadStruct u) {
        UnidadStructPcml unidad = new UnidadStructPcml();

        unidad.setAptoUnidad(u.getAptoUnidad());
        unidad.setCalleUnidad(u.getCalleUnidad());
        unidad.setCasaUnidad(u.getCasaUnidad());
        unidad.setCodPostUnit(u.getCodPostUnit());
        unidad.setComunidad(u.getComunidad());
        unidad.setCuentaP(u.getCuentaP());
        unidad.setDescEstado(u.getDescEstado());
        unidad.setDescEstadoNodo(u.getDescEstadoNodo());
        unidad.setDivUnidad(u.getDivUnidad());
        unidad.setError(u.getError());
        unidad.setEstadUnidadad(u.getEstadUnidadad());
        unidad.setEstratoUnidad(u.getEstratoUnidad());
        unidad.setIdUnidad(u.getIdUnidad());
        unidad.setInformacion(u.getInformacion());
        unidad.setMensajeP(u.getMensajeP());
        unidad.setNodUnidad(u.getNodUnidad());
        unidad.setNomBarrio(u.getNomBarrio());
        unidad.setNomComunidad(u.getNomComunidad());
        unidad.setNomDivUnidad(u.getNomDivUnidad());
        unidad.setNomUnidad(u.getNomUnidad());
        unidad.setTipoRed(u.getTipoRed());

        return unidad;
    }

    public String findCmByCuenta(String cuenta) throws RemoteException, ServiceException {
        PcmlLocator pcmlLocator = new PcmlLocator(urlServicePcml);
        PcmlService pcmlServicePort = pcmlLocator.getPcmlServicePort();
        GetSuscriberOT getSuscriberOT = pcmlServicePort.getSubscriberOt(cuenta);
        if (getSuscriberOT != null) {
            String idUnidad = getSuscriberOT.getAddressKey().toString();
            Edificio edificio = pcmlServicePort.getEdificio(idUnidad);
            return (edificio != null && edificio.getCodEdificio() != null) ? edificio.getCodEdificio() : null;
        } else {
            return null;
        }
    }

    public Edificio getEdificio(String idUnidad) throws ApplicationException  {
        Edificio edificio=null;
        try {
            PcmlLocator pcmlLocator = new PcmlLocator(urlServicePcml);
            PcmlService pcmlServicePort;
            pcmlServicePort = pcmlLocator.getPcmlServicePort();
            edificio = pcmlServicePort.getEdificio(idUnidad);
        } catch (ServiceException | RemoteException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            
            throw new  ApplicationException(msg, ex);
        }
        return edificio;
    }
}
