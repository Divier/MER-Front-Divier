package co.com.claro.visitastecnicas.ws.proxy;

import co.com.claro.unitapi.wsclient.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for responseQueryRegularUnit complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="responseQueryRegularUnit">
 *   &lt;complexContent>
 *     &lt;extension base="{http://unit.telmex.net/}basicMessage">
 *       &lt;sequence>
 *         &lt;element name="ADRT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AKYN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="APTN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUDD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BLCK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BLDG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BLDX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CCDE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DCDE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DLRC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DROP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DRPC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ENTR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FLAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FLOR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GRID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HEND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HOM2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HOME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HSFX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LTYP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MGTX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MGTY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NBHX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OWNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PLOC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POST" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRBC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RSCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RSCP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SETR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SPIG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STNM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STRA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SUBS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TAGN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TRPF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UNOT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UTAP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UTYP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WONO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlRootElement(name = "ResponseQueryRegularUnit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseQueryRegularUnit", propOrder = {
    "adrt",
    "akyn",
    "aptn",
    "audd",
    "audt",
    "blck",
    "bldg",
    "bldx",
    "ccde",
    "dcde",
    "dlrc",
    "drop",
    "drpc",
    "entr",
    "flat",
    "flor",
    "grid",
    "hend",
    "hom2",
    "home",
    "hsfx",
    "ltyp",
    "node",
    "mgtx",
    "mgty",
    "messageText",
    "messageType",
    "nbhx",
    "ownr",
    "ploc",
    "post",
    "prbc",
    "proj",
    "rsch",
    "rscp",
    "setr",
    "spig",
    "stat",
    "stnm",
    "stra",
    "strn",
    "subs",
    "tagn",
    "trpf",
    "unot",
    "utap",
    "utyp",
    "wono"
})
public class ResponseQueryRegularUnit
        extends BasicMessage {

    @XmlElement(name = "ADRT")
    protected String adrt;
    @XmlElement(name = "AKYN")
    protected String akyn;
    @XmlElement(name = "APTN")
    protected String aptn;
    @XmlElement(name = "AUDD")
    protected String audd;
    @XmlElement(name = "AUDT")
    protected String audt;
    @XmlElement(name = "BLCK")
    protected String blck;
    @XmlElement(name = "BLDG")
    protected String bldg;
    @XmlElement(name = "BLDX")
    protected String bldx;
    @XmlElement(name = "CCDE")
    protected String ccde;
    @XmlElement(name = "DCDE")
    protected String dcde;
    @XmlElement(name = "DLRC")
    protected String dlrc;
    @XmlElement(name = "DROP")
    protected String drop;
    @XmlElement(name = "DRPC")
    protected String drpc;
    @XmlElement(name = "ENTR")
    protected String entr;
    @XmlElement(name = "FLAT")
    protected String flat;
    @XmlElement(name = "FLOR")
    protected String flor;
    @XmlElement(name = "GRID")
    protected String grid;
    @XmlElement(name = "HEND")
    protected String hend;
    @XmlElement(name = "HOM2")
    protected String hom2;
    @XmlElement(name = "HOME")
    protected String home;
    @XmlElement(name = "HSFX")
    protected String hsfx;
    @XmlElement(name = "LTYP")
    protected String ltyp;
    @XmlElement(name = "NODE")
    protected String node;
    @XmlElement(name = "MGTX")
    protected String mgtx;
    @XmlElement(name = "MGTY")
    protected String mgty;
    protected String messageText;
    protected String messageType;
    @XmlElement(name = "NBHX")
    protected String nbhx;
    @XmlElement(name = "OWNR")
    protected String ownr;
    @XmlElement(name = "PLOC")
    protected String ploc;
    @XmlElement(name = "POST")
    protected String post;
    @XmlElement(name = "PRBC")
    protected String prbc;
    @XmlElement(name = "PROJ")
    protected String proj;
    @XmlElement(name = "RSCH")
    protected String rsch;
    @XmlElement(name = "RSCP")
    protected String rscp;
    @XmlElement(name = "SETR")
    protected String setr;
    @XmlElement(name = "SPIG")
    protected String spig;
    @XmlElement(name = "STAT")
    protected String stat;
    @XmlElement(name = "STNM")
    protected String stnm;
    @XmlElement(name = "STRA")
    protected String stra;
    @XmlElement(name = "STRN")
    protected String strn;
    @XmlElement(name = "SUBS")
    protected String subs;
    @XmlElement(name = "TAGN")
    protected String tagn;
    @XmlElement(name = "TRPF")
    protected String trpf;
    @XmlElement(name = "UNOT")
    protected String unot;
    @XmlElement(name = "UTAP")
    protected String utap;
    @XmlElement(name = "UTYP")
    protected String utyp;
    @XmlElement(name = "WONO")
    protected String wono;

    /**
     * Gets the value of the adrt property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getADRT() {
        return adrt;
    }

    /**
     * Sets the value of the adrt property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setADRT(String value) {
        this.adrt = value;
    }

    /**
     * Gets the value of the akyn property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAKYN() {
        return akyn;
    }

    /**
     * Sets the value of the akyn property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAKYN(String value) {
        this.akyn = value;
    }

    /**
     * Gets the value of the aptn property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAPTN() {
        return aptn;
    }

    /**
     * Sets the value of the aptn property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAPTN(String value) {
        this.aptn = value;
    }

    /**
     * Gets the value of the audd property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAUDD() {
        return audd;
    }

    /**
     * Sets the value of the audd property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAUDD(String value) {
        this.audd = value;
    }

    /**
     * Gets the value of the audt property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAUDT() {
        return audt;
    }

    /**
     * Sets the value of the audt property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAUDT(String value) {
        this.audt = value;
    }

    /**
     * Gets the value of the blck property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getBLCK() {
        return blck;
    }

    /**
     * Sets the value of the blck property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setBLCK(String value) {
        this.blck = value;
    }

    /**
     * Gets the value of the bldg property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getBLDG() {
        return bldg;
    }

    /**
     * Sets the value of the bldg property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setBLDG(String value) {
        this.bldg = value;
    }

    /**
     * Gets the value of the bldx property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getBLDX() {
        return bldx;
    }

    /**
     * Sets the value of the bldx property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setBLDX(String value) {
        this.bldx = value;
    }

    /**
     * Gets the value of the ccde property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCCDE() {
        return ccde;
    }

    /**
     * Sets the value of the ccde property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCCDE(String value) {
        this.ccde = value;
    }

    /**
     * Gets the value of the dcde property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDCDE() {
        return dcde;
    }

    /**
     * Sets the value of the dcde property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDCDE(String value) {
        this.dcde = value;
    }

    /**
     * Gets the value of the dlrc property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDLRC() {
        return dlrc;
    }

    /**
     * Sets the value of the dlrc property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDLRC(String value) {
        this.dlrc = value;
    }

    /**
     * Gets the value of the drop property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDROP() {
        return drop;
    }

    /**
     * Sets the value of the drop property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDROP(String value) {
        this.drop = value;
    }

    /**
     * Gets the value of the drpc property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDRPC() {
        return drpc;
    }

    /**
     * Sets the value of the drpc property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDRPC(String value) {
        this.drpc = value;
    }

    /**
     * Gets the value of the entr property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getENTR() {
        return entr;
    }

    /**
     * Sets the value of the entr property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setENTR(String value) {
        this.entr = value;
    }

    /**
     * Gets the value of the flat property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFLAT() {
        return flat;
    }

    /**
     * Sets the value of the flat property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFLAT(String value) {
        this.flat = value;
    }

    /**
     * Gets the value of the flor property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFLOR() {
        return flor;
    }

    /**
     * Sets the value of the flor property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFLOR(String value) {
        this.flor = value;
    }

    /**
     * Gets the value of the grid property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getGRID() {
        return grid;
    }

    /**
     * Sets the value of the grid property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setGRID(String value) {
        this.grid = value;
    }

    /**
     * Gets the value of the hend property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getHEND() {
        return hend;
    }

    /**
     * Sets the value of the hend property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setHEND(String value) {
        this.hend = value;
    }

    /**
     * Gets the value of the hom2 property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getHOM2() {
        return hom2;
    }

    /**
     * Sets the value of the hom2 property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setHOM2(String value) {
        this.hom2 = value;
    }

    /**
     * Gets the value of the home property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getHOME() {
        return home;
    }

    /**
     * Sets the value of the home property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setHOME(String value) {
        this.home = value;
    }

    /**
     * Gets the value of the hsfx property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getHSFX() {
        return hsfx;
    }

    /**
     * Sets the value of the hsfx property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setHSFX(String value) {
        this.hsfx = value;
    }

    /**
     * Gets the value of the ltyp property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLTYP() {
        return ltyp;
    }

    /**
     * Sets the value of the ltyp property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setLTYP(String value) {
        this.ltyp = value;
    }

    /**
     * Gets the value of the node property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNODE() {
        return node;
    }

    /**
     * Sets the value of the node property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNODE(String value) {
        this.node = value;
    }

    /**
     * Gets the value of the mgtx property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMGTX() {
        return mgtx;
    }

    /**
     * Sets the value of the mgtx property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMGTX(String value) {
        this.mgtx = value;
    }

    /**
     * Gets the value of the mgty property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMGTY() {
        return mgty;
    }

    /**
     * Sets the value of the mgty property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMGTY(String value) {
        this.mgty = value;
    }

    /**
     * Gets the value of the messageText property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * Sets the value of the messageText property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMessageText(String value) {
        this.messageText = value;
    }

    /**
     * Gets the value of the messageType property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Sets the value of the messageType property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMessageType(String value) {
        this.messageType = value;
    }

    /**
     * Gets the value of the nbhx property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNBHX() {
        return nbhx;
    }

    /**
     * Sets the value of the nbhx property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNBHX(String value) {
        this.nbhx = value;
    }

    /**
     * Gets the value of the ownr property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getOWNR() {
        return ownr;
    }

    /**
     * Sets the value of the ownr property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setOWNR(String value) {
        this.ownr = value;
    }

    /**
     * Gets the value of the ploc property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPLOC() {
        return ploc;
    }

    /**
     * Sets the value of the ploc property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPLOC(String value) {
        this.ploc = value;
    }

    /**
     * Gets the value of the post property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPOST() {
        return post;
    }

    /**
     * Sets the value of the post property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPOST(String value) {
        this.post = value;
    }

    /**
     * Gets the value of the prbc property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPRBC() {
        return prbc;
    }

    /**
     * Sets the value of the prbc property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPRBC(String value) {
        this.prbc = value;
    }

    /**
     * Gets the value of the proj property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPROJ() {
        return proj;
    }

    /**
     * Sets the value of the proj property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPROJ(String value) {
        this.proj = value;
    }

    /**
     * Gets the value of the rsch property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRSCH() {
        return rsch;
    }

    /**
     * Sets the value of the rsch property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRSCH(String value) {
        this.rsch = value;
    }

    /**
     * Gets the value of the rscp property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRSCP() {
        return rscp;
    }

    /**
     * Sets the value of the rscp property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRSCP(String value) {
        this.rscp = value;
    }

    /**
     * Gets the value of the setr property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSETR() {
        return setr;
    }

    /**
     * Sets the value of the setr property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSETR(String value) {
        this.setr = value;
    }

    /**
     * Gets the value of the spig property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSPIG() {
        return spig;
    }

    /**
     * Sets the value of the spig property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSPIG(String value) {
        this.spig = value;
    }

    /**
     * Gets the value of the stat property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSTAT() {
        return stat;
    }

    /**
     * Sets the value of the stat property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSTAT(String value) {
        this.stat = value;
    }

    /**
     * Gets the value of the stnm property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSTNM() {
        return stnm;
    }

    /**
     * Sets the value of the stnm property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSTNM(String value) {
        this.stnm = value;
    }

    /**
     * Gets the value of the stra property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSTRA() {
        return stra;
    }

    /**
     * Sets the value of the stra property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSTRA(String value) {
        this.stra = value;
    }

    /**
     * Gets the value of the strn property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSTRN() {
        return strn;
    }

    /**
     * Sets the value of the strn property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSTRN(String value) {
        this.strn = value;
    }

    /**
     * Gets the value of the subs property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSUBS() {
        return subs;
    }

    /**
     * Sets the value of the subs property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSUBS(String value) {
        this.subs = value;
    }

    /**
     * Gets the value of the tagn property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTAGN() {
        return tagn;
    }

    /**
     * Sets the value of the tagn property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTAGN(String value) {
        this.tagn = value;
    }

    /**
     * Gets the value of the trpf property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTRPF() {
        return trpf;
    }

    /**
     * Sets the value of the trpf property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTRPF(String value) {
        this.trpf = value;
    }

    /**
     * Gets the value of the unot property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUNOT() {
        return unot;
    }

    /**
     * Sets the value of the unot property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUNOT(String value) {
        this.unot = value;
    }

    /**
     * Gets the value of the utap property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUTAP() {
        return utap;
    }

    /**
     * Sets the value of the utap property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUTAP(String value) {
        this.utap = value;
    }

    /**
     * Gets the value of the utyp property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUTYP() {
        return utyp;
    }

    /**
     * Sets the value of the utyp property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUTYP(String value) {
        this.utyp = value;
    }

    /**
     * Gets the value of the wono property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getWONO() {
        return wono;
    }

    /**
     * Sets the value of the wono property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setWONO(String value) {
        this.wono = value;
    }

}
