package net.telmex.pcml.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PcmlServiceProxy implements net.telmex.pcml.service.PcmlService {
    
    private static final Logger LOGGER = LogManager.getLogger(PcmlServiceProxy.class);

    private String _endpoint = null;
    private net.telmex.pcml.service.PcmlService pcmlService = null;

    public PcmlServiceProxy() {
        _initPcmlServiceProxy();
    }

    public PcmlServiceProxy(String endpoint) {
        _endpoint = endpoint;
        _initPcmlServiceProxy();
    }

    private void _initPcmlServiceProxy() {
        try {
            pcmlService = (new net.telmex.pcml.service.PcmlLocator()).getPcmlServicePort();
            if (pcmlService != null) {
                if (_endpoint != null) {
                    ((javax.xml.rpc.Stub) pcmlService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
                } else {
                    _endpoint = (String) ((javax.xml.rpc.Stub) pcmlService)._getProperty("javax.xml.rpc.service.endpoint.address");
                }
            }

        } catch (javax.xml.rpc.ServiceException e) {
            LOGGER.error("Error en _initPcmlServiceProxy. " +e.getMessage(), e);  
        }
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (pcmlService != null) {
            ((javax.xml.rpc.Stub) pcmlService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        }

    }

    public net.telmex.pcml.service.PcmlService getPcmlService() {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService;
    }

    @Override
    public net.telmex.pcml.service.Unidad getUnit(java.lang.String unit) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getUnit(unit);
    }

    @Override
    public net.telmex.pcml.service.NotasNuncaServicio getNotas(net.telmex.pcml.service.NotasNuncaServicio arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getNotas(arg0);
    }

    @Override
    public net.telmex.pcml.service.UnidadStruct getUnidad(java.lang.Long idUnidad) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getUnidad(idUnidad);
    }

    @Override
    public net.telmex.pcml.service.Edificio getEdificio(java.lang.String codEdificio) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getEdificio(codEdificio);
    }

    @Override
    public net.telmex.pcml.service.UbicationInventoryVO[] getConstant(java.lang.String code) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getConstant(code);
    }

    @Override
    public net.telmex.pcml.service.Vecino[] findVecinos(net.telmex.pcml.service.BusquedaVecinos arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.findVecinos(arg0);
    }

    @Override
    public net.telmex.pcml.service.InsertarServicio agregarServicio(net.telmex.pcml.service.InsertarServicio arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.agregarServicio(arg0);
    }

    @Override
    public net.telmex.pcml.service.QuitarServicio eliminarServicio(net.telmex.pcml.service.QuitarServicio arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.eliminarServicio(arg0);
    }

    @Override
    public net.telmex.pcml.service.OrdenTrabajoVO[] getWorkOrders(java.lang.String account) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getWorkOrders(account);
    }

    @Override
    public net.telmex.pcml.service.LlamadaVO[] getServiceCalls(java.lang.String account) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getServiceCalls(account);
    }

    @Override
    public net.telmex.pcml.service.CreditHistory[] getCreditHistoryXCustomer(java.lang.String account, java.lang.String dateInit, java.lang.String dateEnd) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getCreditHistoryXCustomer(account, dateInit, dateEnd);
    }

    @Override
    public java.lang.String changeOTInstallationDate(long arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.changeOTInstallationDate(arg0, arg1, arg2);
    }

    @Override
    public java.lang.String updateDXData(long arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, java.lang.String arg4, java.lang.String arg5) throws java.rmi.RemoteException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateDXData(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    @Override
    public java.lang.String changeOTStatus(long arg0, java.lang.String arg1) throws java.rmi.RemoteException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.changeOTStatus(arg0, arg1);
    }

    @Override
    public net.telmex.pcml.service.Vecino[] getNeighbors(java.lang.String street, java.lang.String home, java.lang.String apartment, java.lang.String community, java.lang.String division, java.lang.String unitType) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getNeighbors(street, home, apartment, community, division, unitType);
    }

    @Override
    public java.lang.String phoneLineMovement(long oldAccount, long newAccount, long phoneNumber) throws java.rmi.RemoteException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.phoneLineMovement(oldAccount, newAccount, phoneNumber);
    }

    @Override
    public net.telmex.pcml.service.WorkOrderInformation getWorkOrderInformation(long workOrder) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getWorkOrderInformation(workOrder);
    }

    @Override
    public net.telmex.pcml.service.Message resetUser(java.lang.String user, java.lang.String document, java.lang.String issueDate) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.resetUser(user, document, issueDate);
    }

    @Override
    public java.lang.String updatePortfolioState(java.lang.String account, java.lang.String line, java.lang.String newState) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updatePortfolioState(account, line, newState);
    }

    @Override
    public net.telmex.pcml.service.ResultadoCierreOT workOrderCompletion(java.lang.String accion, java.lang.String ordenTrabajo, java.lang.String tipo, java.lang.String fabricante, java.lang.String serial, java.lang.String servicio, java.lang.String cantidadServicios) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.workOrderCompletion(accion, ordenTrabajo, tipo, fabricante, serial, servicio, cantidadServicios);
    }

    @Override
    public net.telmex.pcml.service.ServicesSummary getServicesSummary(java.lang.String account) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getServicesSummary(account);
    }

    @Override
    public net.telmex.pcml.service.ReconnectionData reconnect(java.lang.String account) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.reconnect(account);
    }

    @Override
    public java.lang.String advancePayment(long ordenTrabajo, java.lang.String mensajeDataCredito, java.lang.String fechaVencimiento) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.advancePayment(ordenTrabajo, mensajeDataCredito, fechaVencimiento);
    }

    @Override
    public java.lang.String createInventory(java.lang.String ic, java.lang.String mc, java.lang.String serial, java.lang.String estado) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.createInventory(ic, mc, serial, estado);
    }

    @Override
    public java.lang.String updateSpecialNoteSuscriber(long cuenta, java.lang.String tipoNota, java.lang.String descripcionNota, java.lang.String[] detalleNota, java.lang.String nombreUsuario) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateSpecialNoteSuscriber(cuenta, tipoNota, descripcionNota, detalleNota, nombreUsuario);
    }

    @Override
    public java.lang.String updateNoteUnit(long cuenta, java.lang.String[] detalleNota) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateNoteUnit(cuenta, detalleNota);
    }

    @Override
    public java.lang.String updateAdressTap(long cuenta, java.lang.String tapDireccionable, java.lang.String etiqueta, java.lang.String mensaje) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateAdressTap(cuenta, tapDireccionable, etiqueta, mensaje);
    }

    @Override
    public java.lang.String updateAdressTapByOT(java.lang.String ordenTrabajo, java.lang.String tapDireccionable, java.lang.String etiqueta, java.lang.String mensaje) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateAdressTapByOT(ordenTrabajo, tapDireccionable, etiqueta, mensaje);
    }

    @Override
    public java.lang.String[] findTaps(java.lang.String acountUnit) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.findTaps(acountUnit);
    }

    @Override
    public java.lang.String updateStateField(java.lang.String workOrder, java.lang.String stateField) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateStateField(workOrder, stateField);
    }

    @Override
    public java.lang.String[] findItemSale(java.lang.String workOrder) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.findItemSale(workOrder);
    }

    @Override
    public java.lang.String updateOTCanceled(java.lang.Long workOrder, java.lang.Long acount, java.lang.Long date, java.lang.Long time) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateOTCanceled(workOrder, acount, date, time);
    }

    @Override
    public net.telmex.pcml.service.SuscriberVO[] getSuscriberInfo(java.lang.String type, java.lang.String valor, java.lang.String parameter) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getSuscriberInfo(type, valor, parameter);
    }

    @Override
    public net.telmex.pcml.service.SuscriberInfoVO[] getSuscriberInfo2(java.lang.String type, java.lang.String valor, java.lang.String parameter) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getSuscriberInfo2(type, valor, parameter);
    }

    @Override
    public net.telmex.pcml.service.LlamadaServicioStruct getOTAndServiceCallByAccount(java.lang.String cuenta) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getOTAndServiceCallByAccount(cuenta);
    }

    @Override
    public net.telmex.pcml.service.LlamadaVO[] getServiceCallByAccount(java.lang.String cuenta) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getServiceCallByAccount(cuenta);
    }

    @Override
    public net.telmex.pcml.service.OrdenTrabajoVO[] getOTByAccount(java.lang.String cuenta) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getOTByAccount(cuenta);
    }

    @Override
    public net.telmex.pcml.service.Asesor getAsesor2(java.lang.String codAsesor, java.lang.String documento) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getAsesor2(codAsesor, documento);
    }

    @Override
    public net.telmex.pcml.service.Asesor getAsesor(java.lang.String codAsesor) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getAsesor(codAsesor);
    }

    @Override
    public net.telmex.pcml.service.AsesorDesc getAsesorDesc(java.lang.String codAsesor) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getAsesorDesc(codAsesor);
    }

    @Override
    public net.telmex.pcml.service.MacCons getWifiMac(java.lang.String cuenta) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getWifiMac(cuenta);
    }

    @Override
    public net.telmex.pcml.service.PurchaseInfoVO getPurchase(java.lang.String cuenta, java.lang.String ordenTrabajo, java.lang.String cuotas) throws java.rmi.RemoteException, net.telmex.pcml.service.StringUtilException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getPurchase(cuenta, ordenTrabajo, cuotas);
    }

    @Override
    public net.telmex.pcml.service.UbicationInventoryInfoVO getUbicationInventory(java.lang.String codigo) throws java.rmi.RemoteException, net.telmex.pcml.service.StringUtilException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getUbicationInventory(codigo);
    }

    @Override
    public net.telmex.pcml.service.UnidadStruct[] getUnidadesByComunidad(java.lang.String calle, java.lang.String casa, java.lang.String apto, java.lang.String comunidad, int limit) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getUnidadesByComunidad(calle, casa, apto, comunidad, limit);
    }

    @Override
    public net.telmex.pcml.service.UnidadStruct[] getUnidades(java.lang.String calle, java.lang.String casa, java.lang.String apto, java.lang.String[] comunidad, int limit) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getUnidades(calle, casa, apto, comunidad, limit);
    }

    @Override
    public net.telmex.pcml.service.UnidadStruct[] getNeighborByAddress(java.lang.String calle, int placa, java.lang.String strComPlaca, java.lang.String[] comunidades, int limit) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getNeighborByAddress(calle, placa, strComPlaca, comunidades, limit);
    }

    @Override
    public net.telmex.pcml.service.BlackListStruct getBlackListSuscriver(java.lang.String tipoDoc, java.lang.String numeroDoc) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getBlackListSuscriver(tipoDoc, numeroDoc);
    }

    @Override
    public net.telmex.pcml.service.UpdateSubscriber updateSubscriber2(net.telmex.pcml.service.UpdateSubscriber arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateSubscriber2(arg0);
    }

    @Override
    public net.telmex.pcml.service.OutletInfo[] getOutlet(java.lang.String workOrder) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getOutlet(workOrder);
    }

    @Override
    public net.telmex.pcml.service.ScheduleStruct[] getSchedules(net.telmex.pcml.service.Schedule schedule) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getSchedules(schedule);
    }

    @Override
    public net.telmex.pcml.service.ScheduleStruct[] getSchedules2(net.telmex.pcml.service.Schedule2 schedule) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getSchedules2(schedule);
    }

    @Override
    public net.telmex.pcml.service.AdjustPurchaseInfoVO applyAdjustPurchase(java.lang.String cuenta, java.lang.String ordenTrabajo) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.applyAdjustPurchase(cuenta, ordenTrabajo);
    }

    @Override
    public net.telmex.pcml.service.InventarioInfoVO getInventoryOT(java.lang.String account, java.lang.String ot) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getInventoryOT(account, ot);
    }

    @Override
    public net.telmex.pcml.service.GetSuscriberOT getSubscriberOt(java.lang.String account) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getSubscriberOt(account);
    }

    @Override
    public net.telmex.pcml.service.ServiceStruct[] getServicesBySchedules(net.telmex.pcml.service.PcmService service) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getServicesBySchedules(service);
    }

    @Override
    public net.telmex.pcml.service.CampaniaStruct[] getCampanias(java.lang.String date, java.lang.String typeCamp, java.lang.String division, java.lang.String community, java.lang.String rate) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getCampanias(date, typeCamp, division, community, rate);
    }

    @Override
    public net.telmex.pcml.service.ServiceStructAmount[] getServicesAmount(net.telmex.pcml.service.PcmlServiceAmount parameters) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getServicesAmount(parameters);
    }

    @Override
    public net.telmex.pcml.service.UpdateAditionalInfoSubscriber getUpdateAditionalInfoSubscriber(net.telmex.pcml.service.UpdateAditionalInfoSubscriber parameters) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getUpdateAditionalInfoSubscriber(parameters);
    }

    @Override
    public net.telmex.pcml.service.WorkOrderStruct getLastWorkOrderStruct(java.math.BigDecimal acount, java.lang.String otType, java.lang.String otState) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getLastWorkOrderStruct(acount, otType, otState);
    }

    @Override
    public net.telmex.pcml.service.UpdateDate getPCMLUpdateDate(net.telmex.pcml.service.UpdateDate parameters) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getPCMLUpdateDate(parameters);
    }

    @Override
    public net.telmex.pcml.service.MaterialVO[] getMaterial(java.lang.String tarifa, java.lang.String division, java.lang.String comunidad, java.lang.String itemClass) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getMaterial(tarifa, division, comunidad, itemClass);
    }

    @Override
    public net.telmex.pcml.service.DateDXSuscriberStruct getDateDXSubscribers(java.lang.Long idUnidad) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getDateDXSubscribers(idUnidad);
    }

    @Override
    public net.telmex.pcml.service.VentaMultiplayInfoResponse guardarVentaMultiplay(java.lang.String custCode, java.lang.String cuenta, java.lang.String servicio, java.lang.String vendedorCodigo, java.lang.String vendedorCentroCosto, java.lang.String vendedorDocumento, java.lang.String paqueteCodigo, java.lang.String planCodigo, java.lang.String planNombre, java.lang.String paqueteDatosCodigo, java.lang.String idActivacion, java.lang.String msisdnAsignado, java.lang.String fechaVenta, java.lang.String fechaPermanencia, java.lang.String fechaOt, java.lang.String numeroOt, java.lang.String itemClass, java.lang.String manufactureClass, java.lang.String estado) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.guardarVentaMultiplay(custCode, cuenta, servicio, vendedorCodigo, vendedorCentroCosto, vendedorDocumento, paqueteCodigo, planCodigo, planNombre, paqueteDatosCodigo, idActivacion, msisdnAsignado, fechaVenta, fechaPermanencia, fechaOt, numeroOt, itemClass, manufactureClass, estado);
    }

    @Override
    public net.telmex.pcml.service.DescuentoVentaPurchaseVO[] getDescuentosVentaPurchase(java.lang.String cuenta, java.lang.String orderWp, java.lang.String info1, java.lang.String info2) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getDescuentosVentaPurchase(cuenta, orderWp, info1, info2);
    }

    @Override
    public net.telmex.pcml.service.EntregaDomicilioVO[] getEntregaDomicilios(java.lang.String zicmc) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getEntregaDomicilios(zicmc);
    }

    @Override
    public net.telmex.pcml.service.AdministradorNotasVO crearNota(java.lang.String cuenta, java.lang.String usuario, java.lang.String tipoNota, java.lang.String descripcion, java.lang.String accion, java.lang.String nota) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.crearNota(cuenta, usuario, tipoNota, descripcion, accion, nota);
    }

    @Override
    public net.telmex.pcml.service.AdministradorNotasVO adicionarLineasNota(java.lang.String cuenta, java.lang.String usuario, java.lang.String numNota, java.lang.String accion, java.lang.String nota) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.adicionarLineasNota(cuenta, usuario, numNota, accion, nota);
    }

    @Override
    public java.lang.String getEstratoCuenta(java.lang.String cuenta) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getEstratoCuenta(cuenta);
    }

    @Override
    public net.telmex.pcml.service.TarifasCuentaVO getTarifasCuenta(java.lang.String cuenta, java.lang.String numeroOt) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getTarifasCuenta(cuenta, numeroOt);
    }

    @Override
    public net.telmex.pcml.service.ValorCuotasPurchase getPrecioYCuota(net.telmex.pcml.service.ValorCuotasPurchase arg0) throws java.rmi.RemoteException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getPrecioYCuota(arg0);
    }

    @Override
    public java.lang.String getCategoriaJurisdiccion(java.lang.String itemClass, java.lang.String manufacturerClass) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getCategoriaJurisdiccion(itemClass, manufacturerClass);
    }

    @Override
    public net.telmex.pcml.service.PcmlCampaign insertCampaigns(net.telmex.pcml.service.PcmlCampaign arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.insertCampaigns(arg0);
    }

    @Override
    public net.telmex.pcml.service.UpdateBillingInfo updateBillingInfoClient(net.telmex.pcml.service.UpdateBillingInfo arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateBillingInfoClient(arg0);
    }

    @Override
    public net.telmex.pcml.service.InfoSale updateInfoSale(net.telmex.pcml.service.InfoSale arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateInfoSale(arg0);
    }

    @Override
    public net.telmex.pcml.service.RegistroPago crearRegistroPago(net.telmex.pcml.service.RegistroPago arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.crearRegistroPago(arg0);
    }

    @Override
    public net.telmex.pcml.service.ConsultaLlamadaYOrdenTrabajo consultaLlamadaYOrdenW(net.telmex.pcml.service.ConsultaLlamadaYOrdenTrabajo arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.consultaLlamadaYOrdenW(arg0);
    }

    @Override
    public net.telmex.pcml.service.SubscriberInfo updateSubscriberInfo(net.telmex.pcml.service.SubscriberInfo arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.updateSubscriberInfo(arg0);
    }

    @Override
    public net.telmex.pcml.service.SubscriberType getSubscriberType(net.telmex.pcml.service.SubscriberType arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getSubscriberType(arg0);
    }

    @Override
    public net.telmex.pcml.service.TipoLinea setTipoLinea(net.telmex.pcml.service.TipoLinea arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.setTipoLinea(arg0);
    }

    @Override
    public net.telmex.pcml.service.ConsultaCuentaMatriz consultarCM(net.telmex.pcml.service.ConsultaCuentaMatriz arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.consultarCM(arg0);
    }

    @Override
    public net.telmex.pcml.service.UnidadInternetXDias consultaUnidadConInternetUltimosXDias(java.lang.String unidad, java.lang.String dias) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.consultaUnidadConInternetUltimosXDias(unidad, dias);
    }

    @Override
    public net.telmex.pcml.service.CambioPass cambiarPassword(net.telmex.pcml.service.CambioPass arg0) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.cambiarPassword(arg0);
    }

    @Override
    public net.telmex.pcml.service.CampaignAccount getCampaignOTTAccount(java.lang.String cuenta) throws java.rmi.RemoteException, net.telmex.pcml.service.PcmlException {
        if (pcmlService == null) {
            _initPcmlServiceProxy();
        }
        return pcmlService.getCampaignOTTAccount(cuenta);
    }

}
