/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.atencionInmediata.agenda.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abrirIframeRequest", propOrder = {
    "ciudad",
    "latitude",
    "longitude",
    "Node",
    "duration",
    "activityType",
    "XA_WorkOrderSubtype",
    "XA_Red",
    "Canal"
})
public class RequestAbrirIframe {


    protected String ciudad;

    protected double latitude;

    protected double longitude;

    protected String Node;

    protected String duration;
    
    protected String activityType;
    
    protected String XA_WorkOrderSubtype;
    
    protected String XA_Red;
    
    protected String Canal;

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public String getNode() {
        return Node;
    }

    public void setNode(String Node) {
        this.Node = Node;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getXA_WorkOrderSubtype() {
        return XA_WorkOrderSubtype;
    }

    public void setXA_WorkOrderSubtype(String XA_WorkOrderSubtype) {
        this.XA_WorkOrderSubtype = XA_WorkOrderSubtype;
    }

    public String getXA_Red() {
        return XA_Red;
    }

    public void setXA_Red(String XA_Red) {
        this.XA_Red = XA_Red;
    }

    public String getCanal() {
        return Canal;
    }

    public void setCanal(String Canal) {
        this.Canal = Canal;
    }

   
 

   
}
