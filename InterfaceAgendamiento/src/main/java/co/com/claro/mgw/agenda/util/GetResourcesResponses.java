/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.util;

/**
 *
 * @author bocanegravm
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws

 *
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResourcesResponses", propOrder = {
    "resourceId",
    "name",
    "XR_IdAliado"
})
public class GetResourcesResponses {

    @XmlElement(name = "resourceId")
    protected String resourceId;
    @XmlElement(name = "name")
    protected String name;
    @XmlElement(name = "XR_IdAliado")
    protected String XR_IdAliado;
   

    public GetResourcesResponses()
    {}

    public GetResourcesResponses
    (
         String resourceId, String name,
         String XR_IdAliado
    )
    {
        this.resourceId = resourceId;
        this.name = name;
        this.XR_IdAliado = XR_IdAliado;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXR_IdAliado() {
        return XR_IdAliado;
    }

    public void setXR_IdAliado(String XR_IdAliado) {
        this.XR_IdAliado = XR_IdAliado;
    }
    
}
