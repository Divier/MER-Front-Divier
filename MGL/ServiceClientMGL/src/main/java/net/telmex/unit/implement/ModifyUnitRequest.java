
package net.telmex.unit.implement;

import net.telmex.unit.dto.BasicMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para modifyUnitRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="modifyUnitRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://unit.telmex.net/}basicMessage">
 *       &lt;sequence>
 *         &lt;element name="adrt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aptn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="audd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="audt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="blck" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bldg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bldx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caby" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ccde" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dcde" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dlrc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="drop" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="drpc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hom2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="home" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hsfx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nbhx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="post" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prbc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="setr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stnm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stra" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trpf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="utap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="utyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyUnitRequest", propOrder = {
    "adrt",
    "aptn",
    "audd",
    "audt",
    "blck",
    "bldg",
    "bldx",
    "caby",
    "ccde",
    "dcde",
    "dlrc",
    "drop",
    "drpc",
    "entr",
    "flat",
    "flor",
    "grid",
    "hom2",
    "home",
    "hsfx",
    "nbhx",
    "ownr",
    "post",
    "prbc",
    "proj",
    "setr",
    "spig",
    "stat",
    "stnm",
    "stra",
    "tagn",
    "trpf",
    "utap",
    "utyp"
})
public class ModifyUnitRequest
    extends BasicMessage
{

    protected String adrt;
    protected String aptn;
    protected String audd;
    protected String audt;
    protected String blck;
    protected String bldg;
    protected String bldx;
    protected String caby;
    protected String ccde;
    protected String dcde;
    protected String dlrc;
    protected String drop;
    protected String drpc;
    protected String entr;
    protected String flat;
    protected String flor;
    protected String grid;
    protected String hom2;
    protected String home;
    protected String hsfx;
    protected String nbhx;
    protected String ownr;
    protected String post;
    protected String prbc;
    protected String proj;
    protected String setr;
    protected String spig;
    protected String stat;
    protected String stnm;
    protected String stra;
    protected String tagn;
    protected String trpf;
    protected String utap;
    protected String utyp;

    /**
     * Obtiene el valor de la propiedad adrt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdrt() {
        return adrt;
    }

    /**
     * Define el valor de la propiedad adrt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdrt(String value) {
        this.adrt = value;
    }

    /**
     * Obtiene el valor de la propiedad aptn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAptn() {
        return aptn;
    }

    /**
     * Define el valor de la propiedad aptn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAptn(String value) {
        this.aptn = value;
    }

    /**
     * Obtiene el valor de la propiedad audd.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudd() {
        return audd;
    }

    /**
     * Define el valor de la propiedad audd.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudd(String value) {
        this.audd = value;
    }

    /**
     * Obtiene el valor de la propiedad audt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudt() {
        return audt;
    }

    /**
     * Define el valor de la propiedad audt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudt(String value) {
        this.audt = value;
    }

    /**
     * Obtiene el valor de la propiedad blck.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlck() {
        return blck;
    }

    /**
     * Define el valor de la propiedad blck.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlck(String value) {
        this.blck = value;
    }

    /**
     * Obtiene el valor de la propiedad bldg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBldg() {
        return bldg;
    }

    /**
     * Define el valor de la propiedad bldg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBldg(String value) {
        this.bldg = value;
    }

    /**
     * Obtiene el valor de la propiedad bldx.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBldx() {
        return bldx;
    }

    /**
     * Define el valor de la propiedad bldx.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBldx(String value) {
        this.bldx = value;
    }

    /**
     * Obtiene el valor de la propiedad caby.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaby() {
        return caby;
    }

    /**
     * Define el valor de la propiedad caby.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaby(String value) {
        this.caby = value;
    }

    /**
     * Obtiene el valor de la propiedad ccde.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcde() {
        return ccde;
    }

    /**
     * Define el valor de la propiedad ccde.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcde(String value) {
        this.ccde = value;
    }

    /**
     * Obtiene el valor de la propiedad dcde.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDcde() {
        return dcde;
    }

    /**
     * Define el valor de la propiedad dcde.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDcde(String value) {
        this.dcde = value;
    }

    /**
     * Obtiene el valor de la propiedad dlrc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDlrc() {
        return dlrc;
    }

    /**
     * Define el valor de la propiedad dlrc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDlrc(String value) {
        this.dlrc = value;
    }

    /**
     * Obtiene el valor de la propiedad drop.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrop() {
        return drop;
    }

    /**
     * Define el valor de la propiedad drop.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrop(String value) {
        this.drop = value;
    }

    /**
     * Obtiene el valor de la propiedad drpc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrpc() {
        return drpc;
    }

    /**
     * Define el valor de la propiedad drpc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrpc(String value) {
        this.drpc = value;
    }

    /**
     * Obtiene el valor de la propiedad entr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntr() {
        return entr;
    }

    /**
     * Define el valor de la propiedad entr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntr(String value) {
        this.entr = value;
    }

    /**
     * Obtiene el valor de la propiedad flat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlat() {
        return flat;
    }

    /**
     * Define el valor de la propiedad flat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlat(String value) {
        this.flat = value;
    }

    /**
     * Obtiene el valor de la propiedad flor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlor() {
        return flor;
    }

    /**
     * Define el valor de la propiedad flor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlor(String value) {
        this.flor = value;
    }

    /**
     * Obtiene el valor de la propiedad grid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrid() {
        return grid;
    }

    /**
     * Define el valor de la propiedad grid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrid(String value) {
        this.grid = value;
    }

    /**
     * Obtiene el valor de la propiedad hom2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHom2() {
        return hom2;
    }

    /**
     * Define el valor de la propiedad hom2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHom2(String value) {
        this.hom2 = value;
    }

    /**
     * Obtiene el valor de la propiedad home.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHome() {
        return home;
    }

    /**
     * Define el valor de la propiedad home.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHome(String value) {
        this.home = value;
    }

    /**
     * Obtiene el valor de la propiedad hsfx.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHsfx() {
        return hsfx;
    }

    /**
     * Define el valor de la propiedad hsfx.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHsfx(String value) {
        this.hsfx = value;
    }

    /**
     * Obtiene el valor de la propiedad nbhx.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNbhx() {
        return nbhx;
    }

    /**
     * Define el valor de la propiedad nbhx.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNbhx(String value) {
        this.nbhx = value;
    }

    /**
     * Obtiene el valor de la propiedad ownr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnr() {
        return ownr;
    }

    /**
     * Define el valor de la propiedad ownr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnr(String value) {
        this.ownr = value;
    }

    /**
     * Obtiene el valor de la propiedad post.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPost() {
        return post;
    }

    /**
     * Define el valor de la propiedad post.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPost(String value) {
        this.post = value;
    }

    /**
     * Obtiene el valor de la propiedad prbc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrbc() {
        return prbc;
    }

    /**
     * Define el valor de la propiedad prbc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrbc(String value) {
        this.prbc = value;
    }

    /**
     * Obtiene el valor de la propiedad proj.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProj() {
        return proj;
    }

    /**
     * Define el valor de la propiedad proj.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProj(String value) {
        this.proj = value;
    }

    /**
     * Obtiene el valor de la propiedad setr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetr() {
        return setr;
    }

    /**
     * Define el valor de la propiedad setr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetr(String value) {
        this.setr = value;
    }

    /**
     * Obtiene el valor de la propiedad spig.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpig() {
        return spig;
    }

    /**
     * Define el valor de la propiedad spig.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpig(String value) {
        this.spig = value;
    }

    /**
     * Obtiene el valor de la propiedad stat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStat() {
        return stat;
    }

    /**
     * Define el valor de la propiedad stat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStat(String value) {
        this.stat = value;
    }

    /**
     * Obtiene el valor de la propiedad stnm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStnm() {
        return stnm;
    }

    /**
     * Define el valor de la propiedad stnm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStnm(String value) {
        this.stnm = value;
    }

    /**
     * Obtiene el valor de la propiedad stra.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStra() {
        return stra;
    }

    /**
     * Define el valor de la propiedad stra.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStra(String value) {
        this.stra = value;
    }

    /**
     * Obtiene el valor de la propiedad tagn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagn() {
        return tagn;
    }

    /**
     * Define el valor de la propiedad tagn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagn(String value) {
        this.tagn = value;
    }

    /**
     * Obtiene el valor de la propiedad trpf.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrpf() {
        return trpf;
    }

    /**
     * Define el valor de la propiedad trpf.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrpf(String value) {
        this.trpf = value;
    }

    /**
     * Obtiene el valor de la propiedad utap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtap() {
        return utap;
    }

    /**
     * Define el valor de la propiedad utap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtap(String value) {
        this.utap = value;
    }

    /**
     * Obtiene el valor de la propiedad utyp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtyp() {
        return utyp;
    }

    /**
     * Define el valor de la propiedad utyp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtyp(String value) {
        this.utyp = value;
    }

}
