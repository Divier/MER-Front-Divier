package co.com.telmex.catastro.services.wservice;

/**
 * Clase WsSitidataStandarBindingStub
 * Extiende java.rmi.Remote 
 *
 * @author 	Deiver Rovira. - Autogenerado
 * @version	1.0
 */
public interface WsSitidataStandarPortType extends java.rmi.Remote {

    /**
     * 
     * @param address
     * @param state
     * @param city
     * @param district
     * @param user
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    public co.com.telmex.catastro.services.wservice.ResponseArrayFromWS enrich(java.lang.String address, java.lang.String state, java.lang.String city, java.lang.String district, java.lang.String user, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 
     * @param address
     * @param state
     * @param city
     * @param district
     * @param user
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    public co.com.telmex.catastro.services.wservice.ResponseArrayFromWS enrichAssist(java.lang.String address, java.lang.String state, java.lang.String city, java.lang.String district, java.lang.String user, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 
     * @param address
     * @param state
     * @param city
     * @param district
     * @param user
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    public co.com.telmex.catastro.services.wservice.ResponseArrayFromWS enrichAssistComplement(java.lang.String address, java.lang.String state, java.lang.String city, java.lang.String district, java.lang.String user, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 
     * @param xmlSend
     * @param user
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String enrichXmlBatch(java.lang.String xmlSend, java.lang.String user, java.lang.String password) throws java.rmi.RemoteException;
    
}
