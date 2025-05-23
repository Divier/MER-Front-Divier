/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.util;

/**
 *
 * @author Orlando Velasquez Objetivo: 
 * Clase data Ws 
 * Descripcion: Clase data Ws
 *
 *
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTechnologiesResponse", propOrder = {
    "XA_NODO_HFCUNI_SELECT",
    "XA_NODO_HFCBI_SELECT",
    "XA_NODO_FOG_SELECT",
    "XA_NODO_DTH_SELECT",
    "XA_NODO_FTTH_SELECT",
    "XA_NODO_FOU_SELECT",
    "XA_NODO_BTS_SELECT",
    "XA_NODO_WTH_SELECT",
    "XA_NODO_3G_SELECT",
    "XA_NODO_4G_SELECT",
    "XA_NODO_4.5G_SELECT",
    "XA_NODO_5G_SELECT",
    "XA_NODO_HFCUNI_AMPLIA",
    "XA_NODO_HFCBI_AMPLIA",
    "XA_NODO_FOG_AMPLIA",
    "XA_NODO_DTH_AMPLIA",
    "XA_NODO_FTTH_AMPLIA",
    "XA_NODO_FOU_AMPLIA",
    "XA_NODO_BTS_AMPLIA",
    "XA_NODO_WTH_AMPLIA",
    "XA_NODO_3G_AMPLIA",
    "XA_NODO_4G_AMPLIA",
    "XA_NODO_4.5G_AMPLIA",
    "XA_NODO_5G_AMPLIA"
})
public class GetTechnologiesResponse {

    @XmlElement(name = "XA_NODO_HFCUNI_SELECT")
    protected String XA_NodoHfcUniSelect;
    @XmlElement(name = "XA_NODO_HFCBI_SELECT")
    protected String XA_NodoHfcBiSelect;
    @XmlElement(name = "XA_NODO_FOG_SELECT")
    protected String XA_NodoFogSelect;
    @XmlElement(name = "XA_NODO_DTH_SELECT")
    protected String XA_NodoDthSelect;
    @XmlElement(name = "XA_NODO_FTTH_SELECT")
    protected String XA_NodoFtthSelect;
    @XmlElement(name = "XA_NODO_FOU_SELECT")
    protected String XA_NodoFouSelect;
    @XmlElement(name = "XA_NODO_BTS_SELECT")
    protected String XA_NodoBtsSelect;
    @XmlElement(name = "XA_NODO_WTH_SELECT")
    protected String XA_NodoWthSelect;
    @XmlElement(name = "XA_NODO_3G_SELECT")
    protected String XA_Nodo3GSelect;
    @XmlElement(name = "XA_NODO_4G_SELECT")
    protected String XA_Nodo4GSelect;
    @XmlElement(name = "XA_NODO_4.5G_SELECT")
    protected String XA_Nodo4_5GSelect;
    @XmlElement(name = "XA_NODO_5G_SELECT")
    protected String XA_Nodo5GSelect;
    @XmlElement(name = "XA_NODO_HFCUNI_AMPLIA")
    protected String XA_NodoHfcUniAmplia;
    @XmlElement(name = "XA_NODO_HFCBI_AMPLIA")
    protected String XA_NodoHfcBiAmplia;
    @XmlElement(name = "XA_NODO_FOG_AMPLIA")
    protected String XA_NodoFogAmplia;
    @XmlElement(name = "XA_NODO_DTH_AMPLIA")
    protected String XA_NodoDthAmplia;
    @XmlElement(name = "XA_NODO_FTTH_AMPLIA")
    protected String XA_NodoFtthAmplia;
    @XmlElement(name = "XA_NODO_FOU_AMPLIA")
    protected String XA_NodoFouAmplia;
    @XmlElement(name = "XA_NODO_BTS_AMPLIA")
    protected String XA_NodoBtsAmplia;
    @XmlElement(name = "XA_NODO_WTH_AMPLIA")
    protected String XA_NodoWthAmplia;
    @XmlElement(name = "XA_NODO_3G_AMPLIA")
    protected String XA_Nodo3GAmplia;
    @XmlElement(name = "XA_NODO_4G_AMPLIA")
    protected String XA_Nodo4GAmplia;
    @XmlElement(name = "XA_NODO_4.5G_AMPLIA")
    protected String XA_Nodo4_5GAmplia;
    @XmlElement(name = "XA_NODO_5G_AMPLIA")
    protected String XA_Nodo5GAmplia;
    
    public GetTechnologiesResponse() {
    }

    public GetTechnologiesResponse(
            String XA_NodoHfcUniSelect, String XA_NodoHfcBiSelect,
            String XA_NodoFogSelect, String XA_NodoDthSelect,
            String XA_NodoFtthSelect, String XA_NodoFouSelect,
            String XA_NodoBtsSelect, String XA_NodoWthSelect,
            String XA_Nodo3GSelect, String XA_Nodo4GSelect,
            String XA_Nodo4_5GSelect, String XA_Nodo5GSelect,
            String XA_NodoHfcUniAmplia, String XA_NodoHfcBiAmplia,
            String XA_NodoFogAmplia, String XA_NodoDthAmplia,
            String XA_NodoFtthAmplia, String XA_NodoFouAmplia,
            String XA_NodoBtsAmplia, String XA_NodoWthAmplia,
            String XA_Nodo3GAmplia, String XA_Nodo4GAmplia,
            String XA_Nodo4_5GAmplia, String XA_Nodo5GAmplia
    ) {
        this.XA_NodoHfcUniSelect = XA_NodoHfcUniSelect;
        this.XA_NodoHfcBiSelect = XA_NodoHfcBiSelect;
        this.XA_NodoFogSelect = XA_NodoFogSelect;
        this.XA_NodoDthSelect = XA_NodoDthSelect;
        this.XA_NodoFtthSelect = XA_NodoFtthSelect;
        this.XA_NodoFouSelect = XA_NodoFouSelect;
        this.XA_NodoBtsSelect = XA_NodoBtsSelect;
        this.XA_NodoWthSelect = XA_NodoWthSelect;
        this.XA_Nodo3GSelect = XA_Nodo3GSelect;
        this.XA_Nodo4GSelect = XA_Nodo4GSelect;
        this.XA_Nodo4_5GSelect = XA_Nodo4_5GSelect;
        this.XA_Nodo5GSelect = XA_Nodo5GSelect;
        this.XA_NodoHfcUniAmplia = XA_NodoHfcUniAmplia;
        this.XA_NodoHfcBiAmplia = XA_NodoHfcBiAmplia;
        this.XA_NodoFogAmplia = XA_NodoFogAmplia;
        this.XA_NodoDthAmplia = XA_NodoDthAmplia;
        this.XA_NodoFtthAmplia = XA_NodoFtthAmplia;
        this.XA_NodoFouAmplia = XA_NodoFouAmplia;
        this.XA_NodoBtsAmplia = XA_NodoBtsAmplia;
        this.XA_NodoWthAmplia = XA_NodoWthAmplia;
        this.XA_Nodo3GAmplia = XA_Nodo3GAmplia;
        this.XA_Nodo4GAmplia = XA_Nodo4GAmplia;
        this.XA_Nodo4_5GAmplia = XA_Nodo4_5GAmplia;
        this.XA_Nodo5GAmplia = XA_Nodo5GAmplia;
    }

    public String getXA_NodoHfcUniSelect() {
        return XA_NodoHfcUniSelect;
    }

    public void setXA_NodoHfcUniSelect(String XA_NODO_HFCUNI_SELECT) {
        this.XA_NodoHfcUniSelect = XA_NODO_HFCUNI_SELECT;
    }

    public String getXA_NodoHfcBiSelect() {
        return XA_NodoHfcBiSelect;
    }

    public void setXA_NodoHfcBiSelect(String XA_NODO_HFCBI_SELECT) {
        this.XA_NodoHfcBiSelect = XA_NODO_HFCBI_SELECT;
    }

    public String getXA_NodoFogSelect() {
        return XA_NodoFogSelect;
    }

    public void setXA_NodoFogSelect(String XA_NODO_FOG_SELECT) {
        this.XA_NodoFogSelect = XA_NODO_FOG_SELECT;
    }

    public String getXA_NodoDthSelect() {
        return XA_NodoDthSelect;
    }

    public void setXA_NodoDthSelect(String XA_NODO_DTH_SELECT) {
        this.XA_NodoDthSelect = XA_NODO_DTH_SELECT;
    }

    public String getXA_NodoFtthSelect() {
        return XA_NodoFtthSelect;
    }

    public void setXA_NodoFtthSelect(String XA_NODO_FTTH_SELECT) {
        this.XA_NodoFtthSelect = XA_NODO_FTTH_SELECT;
    }

    public String getXA_NodoFouSelect() {
        return XA_NodoFouSelect;
    }

    public void setXA_NodoFouSelect(String XA_NODO_FOU_SELECT) {
        this.XA_NodoFouSelect = XA_NODO_FOU_SELECT;
    }

    public String getXA_NodoBtsSelect() {
        return XA_NodoBtsSelect;
    }

    public void setXA_NodoBtsSelect(String XA_NODO_BTS_SELECT) {
        this.XA_NodoBtsSelect = XA_NODO_BTS_SELECT;
    }

    public String getXA_NodoWthSelect() {
        return XA_NodoWthSelect;
    }

    public void setXA_NodoWthSelect(String XA_NODO_WTH_SELECT) {
        this.XA_NodoWthSelect = XA_NODO_WTH_SELECT;
    }

    public String getXA_Nodo3GSelect() {
        return XA_Nodo3GSelect;
    }

    public void setXA_Nodo3GSelect(String XA_NODO_3G_SELECT) {
        this.XA_Nodo3GSelect = XA_NODO_3G_SELECT;
    }

    public String getXA_Nodo4GSelect() {
        return XA_Nodo4GSelect;
    }

    public void setXA_Nodo4GSelect(String XA_NODO_4G_SELECT) {
        this.XA_Nodo4GSelect = XA_NODO_4G_SELECT;
    }

    public String getXA_Nodo4_5GSelect() {
        return XA_Nodo4_5GSelect;
    }

    public void setXA_Nodo4_5GSelect(String XA_NODO_4_5G_SELECT) {
        this.XA_Nodo4_5GSelect = XA_NODO_4_5G_SELECT;
    }

    public String getXA_Nodo5GSelect() {
        return XA_Nodo5GSelect;
    }

    public void setXA_Nodo5GSelect(String XA_NODO_5G_SELECT) {
        this.XA_Nodo5GSelect = XA_NODO_5G_SELECT;
    }

    public String getXA_NodoHfcUniAmplia() {
        return XA_NodoHfcUniAmplia;
    }

    public void setXA_NodoHfcUniAmplia(String XA_NODO_HFCUNI_AMPLIA) {
        this.XA_NodoHfcUniAmplia = XA_NODO_HFCUNI_AMPLIA;
    }

    public String getXA_NodoHfcBiAmplia() {
        return XA_NodoHfcBiAmplia;
    }

    public void setXA_NodoHfcBiAmplia(String XA_NODO_HFCBI_AMPLIA) {
        this.XA_NodoHfcBiAmplia = XA_NODO_HFCBI_AMPLIA;
    }

    public String getXA_NodoFogAmplia() {
        return XA_NodoFogAmplia;
    }

    public void setXA_NodoFogAmplia(String XA_NODO_FOG_AMPLIA) {
        this.XA_NodoFogAmplia = XA_NODO_FOG_AMPLIA;
    }

    public String getXA_NodoDthAmplia() {
        return XA_NodoDthAmplia;
    }

    public void setXA_NodoDthAmplia(String XA_NODO_DTH_AMPLIA) {
        this.XA_NodoDthAmplia = XA_NODO_DTH_AMPLIA;
    }

    public String getXA_NodoFtthAmplia() {
        return XA_NodoFtthAmplia;
    }

    public void setXA_NodoFtthAmplia(String XA_NODO_FTTH_AMPLIA) {
        this.XA_NodoFtthAmplia = XA_NODO_FTTH_AMPLIA;
    }

    public String getXA_NodoFouAmplia() {
        return XA_NodoFouAmplia;
    }

    public void setXA_NodoFouAmplia(String XA_NODO_FOU_AMPLIA) {
        this.XA_NodoFouAmplia = XA_NODO_FOU_AMPLIA;
    }

    public String getXA_NodoBtsAmplia() {
        return XA_NodoBtsAmplia;
    }

    public void setXA_NodoBtsAmplia(String XA_NODO_BTS_AMPLIA) {
        this.XA_NodoBtsAmplia = XA_NODO_BTS_AMPLIA;
    }

    public String getXA_NodoWthAmplia() {
        return XA_NodoWthAmplia;
    }

    public void setXA_NodoWthAmplia(String XA_NODO_WTH_AMPLIA) {
        this.XA_NodoWthAmplia = XA_NODO_WTH_AMPLIA;
    }

    public String getXA_Nodo3GAmplia() {
        return XA_Nodo3GAmplia;
    }

    public void setXA_Nodo3GAmplia(String XA_NODO_3G_AMPLIA) {
        this.XA_Nodo3GAmplia = XA_NODO_3G_AMPLIA;
    }

    public String getXA_Nodo4GAmplia() {
        return XA_Nodo4GAmplia;
    }

    public void setXA_Nodo4GAmplia(String XA_NODO_4G_AMPLIA) {
        this.XA_Nodo4GAmplia = XA_NODO_4G_AMPLIA;
    }

    public String getXA_Nodo4_5GAmplia() {
        return XA_Nodo4_5GAmplia;
    }

    public void setXA_Nodo4_5GAmplia(String XA_NODO_4_5G_AMPLIA) {
        this.XA_Nodo4_5GAmplia = XA_NODO_4_5G_AMPLIA;
    }

    public String getXA_Nodo5GAmplia() {
        return XA_Nodo5GAmplia;
    }

    public void setXA_Nodo5GAmplia(String XA_NODO_5G_AMPLIA) {
        this.XA_Nodo5GAmplia = XA_NODO_5G_AMPLIA;
    }

}
