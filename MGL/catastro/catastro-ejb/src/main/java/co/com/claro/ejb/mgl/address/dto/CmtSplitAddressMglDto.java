/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ejb.mgl.address.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que se utiliza para encapsular los datos de una direccion tabulada
 * @author villamilc hitss
 */
@XmlRootElement(name = "cmtSplitAddressMgl")
public class CmtSplitAddressMglDto {
    @XmlElement
    private String idTipoDireccion;
    @XmlElement
    private String dirPrincAlt;
    @XmlElement
    private String barrio;
    @XmlElement
    private String tipoViaPrincipal;
    @XmlElement
    private String numViaPrincipal;
    @XmlElement
    private String ltViaPrincipal;
    @XmlElement
    private String nlPostViaP;
    @XmlElement
    private String bisViaPrincipal;
    @XmlElement
    private String cuadViaPrincipal;
    @XmlElement
    private String tipoViaGeneradora;
    @XmlElement
    private String numViaGeneradora;
    @XmlElement
    private String ltViaGeneradora;
    @XmlElement
    private String nlPostViaG;
    @XmlElement
    private String bisViaGeneradora;
    @XmlElement
    private String cuadViaGeneradora;
    @XmlElement
    private String placaDireccion;
    @XmlElement
    private String cpTipoNivel1;
    @XmlElement
    private String cpTipoNivel2;
    @XmlElement
    private String cpTipoNivel3;
    @XmlElement
    private String cpTipoNivel4;
    @XmlElement
    private String cpTipoNivel5;
    @XmlElement
    private String cpTipoNivel6;
    @XmlElement
    private String cpValorNivel1;
    @XmlElement
    private String cpValorNivel2;
    @XmlElement
    private String cpValorNivel3;
    @XmlElement
    private String cpValorNivel4;
    @XmlElement
    private String cpValorNivel5;
    @XmlElement
    private String cpValorNivel6;
    @XmlElement
    private String mzTipoNivel1;
    @XmlElement
    private String mzTipoNivel2;
    @XmlElement
    private String mzTipoNivel3;
    @XmlElement
    private String mzTipoNivel4;
    @XmlElement
    private String mzTipoNivel5;
    @XmlElement
    private String mzValorNivel1;
    @XmlElement
    private String mzValorNivel2;
    @XmlElement
    private String mzValorNivel3;
    @XmlElement
    private String mzValorNivel4;
    @XmlElement
    private String mzValorNivel5;
    @XmlElement
    private String idDirCatastro;
    @XmlElement
    private String mzTipoNivel6;
    @XmlElement
    private String mzValorNivel6;
    @XmlElement
    private String itTipoPlaca;
    @XmlElement
    private String itValorPlaca;
    @XmlElement
    private String estadoDirGeo;
    @XmlElement
    private String letra3g;
    @XmlElement
    private String direccionTexto;

   /*
    * getIdTipoDireccion
    * @return String
    */ 
    public String getIdTipoDireccion() {
        return idTipoDireccion;
    }

   /*
    * setIdTipoDireccion
    * @Param idTipoDireccion
    */ 
    public void setIdTipoDireccion(String idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }

   /*
    * getDirPrincAlt
    * @return String
    */ 
    public String getDirPrincAlt() {
        return dirPrincAlt;
    }

    /*
    * setDirPrincAlt
    * @Param dirPrincAlt
    */ 
    public void setDirPrincAlt(String dirPrincAlt) {
        this.dirPrincAlt = dirPrincAlt;
    }

   /*
    * getBarrio
    * @return String
    */ 
    public String getBarrio() {
        return barrio;
    }

   /*
    * setBarrio
    * @Param barrio
    */     
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

   /*
    * getTipoViaPrincipal
    * @return String
    */ 
    public String getTipoViaPrincipal() {
        return tipoViaPrincipal;
    }

   /*
    * setTipoViaPrincipal
    * @Param tipoViaPrincipal
    */         
    public void setTipoViaPrincipal(String tipoViaPrincipal) {
        this.tipoViaPrincipal = tipoViaPrincipal;
    }

   /*
    * getNumViaPrincipal
    * @return String
    */ 
    public String getNumViaPrincipal() {
        return numViaPrincipal;
    }

   /*
    * setNumViaPrincipal
    * @Param numViaPrincipal
    */         
    public void setNumViaPrincipal(String numViaPrincipal) {
        this.numViaPrincipal = numViaPrincipal;
    }

   /*
    * getLtViaPrincipal
    * @return String
    */ 
    public String getLtViaPrincipal() {
        return ltViaPrincipal;
    }


   /*
    * setLtViaPrincipal
    * @Param ltViaPrincipal
    */    
    public void setLtViaPrincipal(String ltViaPrincipal) {
        this.ltViaPrincipal = ltViaPrincipal;
    }

   /*
    * getNlPostViaP
    * @return String
    */ 
    public String getNlPostViaP() {
        return nlPostViaP;
    }


   /*
    * setNlPostViaP
    * @Param nlPostViaP
    */    
    public void setNlPostViaP(String nlPostViaP) {
        this.nlPostViaP = nlPostViaP;
    }

   /*
    * getBisViaPrincipal
    * @return String
    */ 
    public String getBisViaPrincipal() {
        return bisViaPrincipal;
    }

   /*
    * setBisViaPrincipal
    * @Param bisViaPrincipal
    */
    public void setBisViaPrincipal(String bisViaPrincipal) {
        this.bisViaPrincipal = bisViaPrincipal;
    }

   /*
    * getCuadViaPrincipal
    * @return String
    */ 
    public String getCuadViaPrincipal() {
        return cuadViaPrincipal;
    }

   /*
    * setCuadViaPrincipal
    * @Param cuadViaPrincipal
    */
    public void setCuadViaPrincipal(String cuadViaPrincipal) {
        this.cuadViaPrincipal = cuadViaPrincipal;
    }

   /*
    * getTipoViaGeneradora
    * @return String
    */ 
    public String getTipoViaGeneradora() {
        return tipoViaGeneradora;
    }

   /*
    * setTipoViaGeneradora
    * @Param tipoViaGeneradora
    */
    public void setTipoViaGeneradora(String tipoViaGeneradora) {
        this.tipoViaGeneradora = tipoViaGeneradora;
    }

   /*
    * getIdTipoDireccion
    * @return String
    */ 
    public String getNumViaGeneradora() {
        return numViaGeneradora;
    }

   /*
    * setNumViaGeneradora
    * @Param numViaGeneradora
    */
    public void setNumViaGeneradora(String numViaGeneradora) {
        this.numViaGeneradora = numViaGeneradora;
    }

   /*
    * getIdTipoDireccion
    * @return String
    */ 
    public String getLtViaGeneradora() {
        return ltViaGeneradora;
    }

   /*
    * getIdTipoDireccion
    * @Param ltViaGeneradora
    */
    public void setLtViaGeneradora(String ltViaGeneradora) {
        this.ltViaGeneradora = ltViaGeneradora;
    }

   /*
    * getNlPostViaG
    * @return String
    */ 
    public String getNlPostViaG() {
        return nlPostViaG;
    }


   /*
    * setNlPostViaG
    * @Param nlPostViaG
    */    
    public void setNlPostViaG(String nlPostViaG) {
        this.nlPostViaG = nlPostViaG;
    }

   /*
    * getBisViaGeneradora
    * @return String
    */ 
    public String getBisViaGeneradora() {
        return bisViaGeneradora;
    }


   /*
    * setBisViaGeneradora
    * @Param bisViaGeneradora
    */    
    public void setBisViaGeneradora(String bisViaGeneradora) {
        this.bisViaGeneradora = bisViaGeneradora;
    }

   /*
    * getCuadViaGeneradora
    * @return String
    */ 
    public String getCuadViaGeneradora() {
        return cuadViaGeneradora;
    }


   /*
    * setCuadViaGeneradora
    * @Param cuadViaGeneradora
    */    
    public void setCuadViaGeneradora(String cuadViaGeneradora) {
        this.cuadViaGeneradora = cuadViaGeneradora;
    }

   /*
    * getPlacaDireccion
    * @return String
    */ 
    public String getPlacaDireccion() {
        return placaDireccion;
    }


   /*
    * setPlacaDireccion
    * @Param placaDireccion
    */    
    public void setPlacaDireccion(String placaDireccion) {
        this.placaDireccion = placaDireccion;
    }

   /*
    * getCpTipoNivel1
    * @return String
    */ 
    public String getCpTipoNivel1() {
        return cpTipoNivel1;
    }

   /*
    * setCpTipoNivel1
    * @Param cpTipoNivel1
    */
    public void setCpTipoNivel1(String cpTipoNivel1) {
        this.cpTipoNivel1 = cpTipoNivel1;
    }

   /*
    * getCpTipoNivel2
    * @return String
    */ 
    public String getCpTipoNivel2() {
        return cpTipoNivel2;
    }

   /*
    * setCpTipoNivel2
    * @Param cpTipoNivel2
    */
    public void setCpTipoNivel2(String cpTipoNivel2) {
        this.cpTipoNivel2 = cpTipoNivel2;
    }

   /*
    * getCpTipoNivel3
    * @return String
    */ 
    public String getCpTipoNivel3() {
        return cpTipoNivel3;
    }

   /*
    * setCpTipoNivel3
    * @Param cpTipoNivel3
    */
    public void setCpTipoNivel3(String cpTipoNivel3) {
        this.cpTipoNivel3 = cpTipoNivel3;
    }

   /*
    * getCpTipoNivel4
    * @return String
    */ 
    public String getCpTipoNivel4() {
        return cpTipoNivel4;
    }

   /*
    * setCpTipoNivel4
    * @Param cpTipoNivel4
    */
    public void setCpTipoNivel4(String cpTipoNivel4) {
        this.cpTipoNivel4 = cpTipoNivel4;
    }

   /*
    * getCpTipoNivel5
    * @return String
    */ 
    public String getCpTipoNivel5() {
        return cpTipoNivel5;
    }

   /*
    * setCpTipoNivel5
    * @Param cpTipoNivel5
    */
    public void setCpTipoNivel5(String cpTipoNivel5) {
        this.cpTipoNivel5 = cpTipoNivel5;
    }

   /*
    * getCpTipoNivel6
    * @return String
    */ 
    public String getCpTipoNivel6() {
        return cpTipoNivel6;
    }

   /*
    * setCpTipoNivel6
    * @Param cpTipoNivel6
    */
    public void setCpTipoNivel6(String cpTipoNivel6) {
        this.cpTipoNivel6 = cpTipoNivel6;
    }

   /*
    * getCpValorNivel1
    * @return String
    */ 
    public String getCpValorNivel1() {
        return cpValorNivel1;
    }

   /*
    * setCpValorNivel1
    * @Param cpValorNivel1
    */
    public void setCpValorNivel1(String cpValorNivel1) {
        this.cpValorNivel1 = cpValorNivel1;
    }

   /*
    * getCpValorNivel2
    * @return String
    */ 
    public String getCpValorNivel2() {
        return cpValorNivel2;
    }

   /*
    * setCpValorNivel2
    * @Param cpValorNivel2
    */
    public void setCpValorNivel2(String cpValorNivel2) {
        this.cpValorNivel2 = cpValorNivel2;
    }

   /*
    * getCpValorNivel3
    * @return String
    */ 
    public String getCpValorNivel3() {
        return cpValorNivel3;
    }

   /*
    * setCpValorNivel3
    * @Param cpValorNivel3
    */
    public void setCpValorNivel3(String cpValorNivel3) {
        this.cpValorNivel3 = cpValorNivel3;
    }

   /*
    * getCpValorNivel4
    * @return String
    */ 
    public String getCpValorNivel4() {
        return cpValorNivel4;
    }

   /*
    * setCpValorNivel4
    * @Param cpValorNivel4
    */
    public void setCpValorNivel4(String cpValorNivel4) {
        this.cpValorNivel4 = cpValorNivel4;
    }

   /*
    * setCpValorNivel5
    * @return String
    */ 
    public String getCpValorNivel5() {
        return cpValorNivel5;
    }

   /*
    * setCpValorNivel5
    * @Param cpValorNivel5
    */
    public void setCpValorNivel5(String cpValorNivel5) {
        this.cpValorNivel5 = cpValorNivel5;
    }


    
    
    /*
    * getCpValorNivel6
    * @return String
    */ 
    public String getCpValorNivel6() {
        return cpValorNivel6;
    }

   /*
    * setCpValorNivel6
    * @Param cpValorNivel6
    */
    public void setCpValorNivel6(String cpValorNivel6) {
        this.cpValorNivel6 = cpValorNivel6;
    }

   /*
    * getMzTipoNivel1
    * @return String
    */ 
    public String getMzTipoNivel1() {
        return mzTipoNivel1;
    }

   /*
    * setMzTipoNivel1
    * @Param mzTipoNivel1
    */
    public void setMzTipoNivel1(String mzTipoNivel1) {
        this.mzTipoNivel1 = mzTipoNivel1;
    }

   /*
    * getMzTipoNivel2
    * @return String
    */ 
    public String getMzTipoNivel2() {
        return mzTipoNivel2;
    }

   /*
    * setMzTipoNivel2
    * @Param mzTipoNivel2
    */
    public void setMzTipoNivel2(String mzTipoNivel2) {
        this.mzTipoNivel2 = mzTipoNivel2;
    }

   /*
    * getMzTipoNivel3
    * @return String
    */ 
    public String getMzTipoNivel3() {
        return mzTipoNivel3;
    }

   /*
    * setMzTipoNivel3
    * @Param mzTipoNivel3
    */
    public void setMzTipoNivel3(String mzTipoNivel3) {
        this.mzTipoNivel3 = mzTipoNivel3;
    }

   /*
    * getMzTipoNivel4
    * @return String
    */ 
    public String getMzTipoNivel4() {
        return mzTipoNivel4;
    }

   /*
    * setMzTipoNivel4
    * @Param mzTipoNivel4
    */
    public void setMzTipoNivel4(String mzTipoNivel4) {
        this.mzTipoNivel4 = mzTipoNivel4;
    }

   /*
    * getMzTipoNivel5
    * @return String
    */ 
    public String getMzTipoNivel5() {
        return mzTipoNivel5;
    }

   /*
    * setMzTipoNivel5
    * @Param mzTipoNivel5
    */
    public void setMzTipoNivel5(String mzTipoNivel5) {
        this.mzTipoNivel5 = mzTipoNivel5;
    }


    /*
    * getMzValorNivel1
    * @return String
    */ 
    public String getMzValorNivel1() {
        return mzValorNivel1;
    }

   /*
    * setMzValorNivel1
    * @Param mzValorNivel1
    */
    public void setMzValorNivel1(String mzValorNivel1) {
        this.mzValorNivel1 = mzValorNivel1;
    }


    /*
    * getMzValorNivel2
    * @return String
    */ 
    public String getMzValorNivel2() {
        return mzValorNivel2;
    }

   /*
    * setMzValorNivel2
    * @Param mzValorNivel2
    */
    public void setMzValorNivel2(String mzValorNivel2) {
        this.mzValorNivel2 = mzValorNivel2;
    }

   /*
    * getMzValorNivel3
    * @return String
    */ 
    public String getMzValorNivel3() {
        return mzValorNivel3;
    }

   /*
    * setMzValorNivel3
    * @Param mzValorNivel3
    */
    public void setMzValorNivel3(String mzValorNivel3) {
        this.mzValorNivel3 = mzValorNivel3;
    }


    /*
    * getMzValorNivel4
    * @return String
    */ 
    public String getMzValorNivel4() {
        return mzValorNivel4;
    }

   /*
    * setMzValorNivel4
    * @Param mzValorNivel4
    */
    public void setMzValorNivel4(String mzValorNivel4) {
        this.mzValorNivel4 = mzValorNivel4;
    }

   /*
    * getIdDirCatastro
    * @return String
    */ 
    public String getMzValorNivel5() {
        return mzValorNivel5;
    }

   /*
    * setMzValorNivel5
    * @Param mzValorNivel5
    */
    public void setMzValorNivel5(String mzValorNivel5) {
        this.mzValorNivel5 = mzValorNivel5;
    }

   /*
    * getIdTipoDireccion
    * @return String
    */ 
    public String getIdDirCatastro() {
        return idDirCatastro;
    }

   /*
    * setIdDirCatastro
    * @Param idDirCatastro
    */
    public void setIdDirCatastro(String idDirCatastro) {
        this.idDirCatastro = idDirCatastro;
    }

   /*
    * getMzTipoNivel6
    * @return String
    */ 
    public String getMzTipoNivel6() {
        return mzTipoNivel6;
    }

   /*
    * setMzTipoNivel6
    * @Param mzTipoNivel6
    */
    public void setMzTipoNivel6(String mzTipoNivel6) {
        this.mzTipoNivel6 = mzTipoNivel6;
    }

    
   /*
    * getMzValorNivel6
    * @return String
    */ 
    public String getMzValorNivel6() {
        return mzValorNivel6;
    }

   /*
    * setMzValorNivel6
    * @Param mzValorNivel6
    */
    public void setMzValorNivel6(String mzValorNivel6) {
        this.mzValorNivel6 = mzValorNivel6;
    }

   /*
    * getItTipoPlaca
    * @return String
    */ 
    
    public String getItTipoPlaca() {
        return itTipoPlaca;
    }

   /*
    * setItTipoPlaca
    * @Param itTipoPlaca
    */
    public void setItTipoPlaca(String itTipoPlaca) {
        this.itTipoPlaca = itTipoPlaca;
    }

   /*
    * getItValorPlaca
    * @return String
    */     
    public String getItValorPlaca() {
        return itValorPlaca;
    }

   /*
    * setItValorPlaca
    * @Param itValorPlaca
    */    
    public void setItValorPlaca(String itValorPlaca) {
        this.itValorPlaca = itValorPlaca;
    }

   /*
    * getEstadoDirGeo
    * @return String
    */ 
    
    public String getEstadoDirGeo() {
        return estadoDirGeo;
    }


   /*
    * setEstadoDirGeo
    * @Param estadoDirGeo
    */    
    public void setEstadoDirGeo(String estadoDirGeo) {
        this.estadoDirGeo = estadoDirGeo;
    }
   /*
    * getLetra3g
    * @return String
    */ 

    public String getLetra3g() {
        return letra3g;
    }


   /*
    * setLetra3g
    * @Param letra3g
    */    
    public void setLetra3g(String letra3g) {
        this.letra3g = letra3g;
    }
   /*
    * getDireccionTexto
    * @return String
    */ 

    
    public String getDireccionTexto() {
        return direccionTexto;
    }


   /*
    * setDireccionTexto
    * @Param direccionTexto
    */    
    public void setDireccionTexto(String direccionTexto) {
        this.direccionTexto = direccionTexto;
    }

    
    
}
