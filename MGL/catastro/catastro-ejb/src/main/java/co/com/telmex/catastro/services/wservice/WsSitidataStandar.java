package co.com.telmex.catastro.services.wservice;

/**
 * Clase WsSitidataStandar
 * Extiende de javax.xml.rpc.Service
 *
 * @author 	Deiver Rovira. - Autogenerado
 * @version	1.0
 */
public interface WsSitidataStandar extends javax.xml.rpc.Service {

    /**
     * 
     * @return
     */
    public java.lang.String getwsSitidataStandarPortAddress();

    /**
     * 
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    public co.com.telmex.catastro.services.wservice.WsSitidataStandarPortType getwsSitidataStandarPort() throws javax.xml.rpc.ServiceException;

    /**
     * 
     * @param portAddress
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    public co.com.telmex.catastro.services.wservice.WsSitidataStandarPortType getwsSitidataStandarPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
