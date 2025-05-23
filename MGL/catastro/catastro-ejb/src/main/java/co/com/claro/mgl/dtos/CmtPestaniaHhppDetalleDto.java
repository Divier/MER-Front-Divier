/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class CmtPestaniaHhppDetalleDto  implements Comparable<CmtPestaniaHhppDetalleDto>{

    List<HhppMgl> listaEstadosHHPP;
    CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl;
    HhppMgl hhppMglLista;
    String direccion;
    String torre;
    CmtBasicaMgl cmtBasicaMgl;
    String estado;
    String estrato;
    String suscriptor;
    String nodo;
    String bid;
    String estadoBid;
    String uni;
    String estadoUni;
    String fog;
    String estadoFog;
    String fou;
    String estadoFou;
    String ftt;
    String estadoFtt;
    String dth;
    String estadoDth;
    String mov;
    String estadoMov;
    String lte;
    String estadoLte;

    public List<HhppMgl> getListaEstadosHHPP() {
        return listaEstadosHHPP;
    }

    public void setListaEstadosHHPP(List<HhppMgl> listaEstadosHHPP) {
        this.listaEstadosHHPP = listaEstadosHHPP;
    }
    

    public String getTorre() {
        return torre;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

 

    public String getSuscriptor() {
        return suscriptor;
    }

    public void setSuscriptor(String suscriptor) {
        this.suscriptor = suscriptor;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CmtDireccionDetalladaMgl getCmtDireccionDetalladaMgl() {
        return cmtDireccionDetalladaMgl;
    }

    public void setCmtDireccionDetalladaMgl(CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl) {
        this.cmtDireccionDetalladaMgl = cmtDireccionDetalladaMgl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CmtBasicaMgl getCmtBasicaMgl() {
        return cmtBasicaMgl;
    }

    public void setCmtBasicaMgl(CmtBasicaMgl cmtBasicaMgl) {
        this.cmtBasicaMgl = cmtBasicaMgl;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

 

    public String getFog() {
        return fog;
    }

    public void setFog(String fog) {
        this.fog = fog;
    }

    public String getFou() {
        return fou;
    }

    public void setFou(String fou) {
        this.fou = fou;
    }

    public String getFtt() {
        return ftt;
    }

    public void setFtt(String ftt) {
        this.ftt = ftt;
    }

    public String getDth() {
        return dth;
    }

    public void setDth(String dth) {
        this.dth = dth;
    }

    public String getMov() {
        return mov;
    }

    public void setMov(String mov) {
        this.mov = mov;
    }

    public String getLte() {
        return lte;
    }

    public void setLte(String lte) {
        this.lte = lte;
    }

    public String getEstadoBid() {
        return estadoBid;
    }

    public void setEstadoBid(String estadoBid) {
        this.estadoBid = estadoBid;
    }

    public String getEstadoUni() {
        return estadoUni;
    }

    public void setEstadoUni(String estadoUni) {
        this.estadoUni = estadoUni;
    }

    public String getEstadoFog() {
        return estadoFog;
    }

    public void setEstadoFog(String estadoFog) {
        this.estadoFog = estadoFog;
    }

    public String getEstadoFou() {
        return estadoFou;
    }

    public void setEstadoFou(String estadoFou) {
        this.estadoFou = estadoFou;
    }

    public String getEstadoFtt() {
        return estadoFtt;
    }

    public void setEstadoFtt(String estadoFtt) {
        this.estadoFtt = estadoFtt;
    }

    public String getEstadoDth() {
        return estadoDth;
    }

    public void setEstadoDth(String estadoDth) {
        this.estadoDth = estadoDth;
    }

    public String getEstadoMov() {
        return estadoMov;
    }

    public void setEstadoMov(String estadoMov) {
        this.estadoMov = estadoMov;
    }

    public String getEstadoLte() {
        return estadoLte;
    }

    public void setEstadoLte(String estadoLte) {
        this.estadoLte = estadoLte;
    }

    @Override
    public int compareTo(CmtPestaniaHhppDetalleDto o) {
        if ((this.getHhppMglLista().getSubDireccionObj() == null) || (this.getHhppMglLista().getDireccionObj()== null)) {
            return (this.getHhppMglLista().getSubDireccionObj() == null && this.getHhppMglLista().getDireccionObj() == null) ? 0 : -1;
        }
        if (o.getHhppMglLista().getSubDireccionObj() == null || o.getHhppMglLista().getDireccionObj() == null) {
            return 1;
        }
        String a = new String(String.valueOf(this.getHhppMglLista().getHhpApart()));
        String b = new String(String.valueOf(o.getHhppMglLista().getHhpApart()));
        return a.compareTo(b);
    }
    
  

    public HhppMgl getHhppMglLista() {
        return hhppMglLista;
    }

    public void setHhppMglLista(HhppMgl hhppMglLista) {
        this.hhppMglLista = hhppMglLista;
    }
    
    
    

}
