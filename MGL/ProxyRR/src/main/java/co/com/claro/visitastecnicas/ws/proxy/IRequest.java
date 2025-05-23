/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.soap.SOAPMessage;

/**
 *
 * @author user
 */
public interface IRequest {

    SOAPMessage createSOAPRequest(String url) throws Exception;

    public SOAPMessage createSOAPRequestHhPp(String url) throws Exception;
}
