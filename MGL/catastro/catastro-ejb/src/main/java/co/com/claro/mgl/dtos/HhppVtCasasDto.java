/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;

/**
 *
 * @author User
 */
public class HhppVtCasasDto {
      
       
        private CmtSubEdificiosVt cmtSubEdificiosVtSelected;
        private String direccionSub;
        private String nombreSub;
        private String opcionNivel4;
        private String valorNivel4;
        private String opcionNivel5;
        private String valorNivel5;
        private String opcionNivel6;
        private String valorNivel6;
        private String itTipoPlaca6;
        private String itValorPlaca6;
         private String direccionAValidar;
        private int procesado = 0;

        public String getOpcionNivel5() {
            return opcionNivel5;
        }

        public void setOpcionNivel5(String opcionNivel5) {
            this.opcionNivel5 = opcionNivel5;
        }

        public String getValorNivel5() {
            return valorNivel5;
        }

        public void setValorNivel5(String valorNivel5) {
            this.valorNivel5 = valorNivel5;
        }

        public String getOpcionNivel6() {
            return opcionNivel6;
        }

        public void setOpcionNivel6(String opcionNivel6) {
            this.opcionNivel6 = opcionNivel6;
        }

        public String getValorNivel6() {
            return valorNivel6;
        }

        public void setValorNivel6(String valorNivel6) {
            this.valorNivel6 = valorNivel6;
        }

        public String getDireccionSub() {
            return direccionSub;
        }

        public void setDireccionSub(String direccionSub) {
            this.direccionSub = direccionSub;
        }

        public String getNombreSub() {
            return nombreSub;
        }

        public void setNombreSub(String nombreSub) {
            this.nombreSub = nombreSub;
        }

    public String getOpcionNivel4() {
        return opcionNivel4;
    }

    public void setOpcionNivel4(String opcionNivel4) {
        this.opcionNivel4 = opcionNivel4;
    }

    public String getValorNivel4() {
        return valorNivel4;
    }

    public void setValorNivel4(String valorNivel4) {
        this.valorNivel4 = valorNivel4;
    }

    public CmtSubEdificiosVt getCmtSubEdificiosVtSelected() {
        return cmtSubEdificiosVtSelected;
    }

    public void setCmtSubEdificiosVtSelected(CmtSubEdificiosVt cmtSubEdificiosVtSelected) {
        this.cmtSubEdificiosVtSelected = cmtSubEdificiosVtSelected;
    }

    public int getProcesado() {
        return procesado;
    }

    public void setProcesado(int procesado) {
        this.procesado = procesado;
    }

    public String getItTipoPlaca6() {
        return itTipoPlaca6;
    }

    public void setItTipoPlaca6(String itTipoPlaca6) {
        this.itTipoPlaca6 = itTipoPlaca6;
    }

    public String getItValorPlaca6() {
        return itValorPlaca6;
    }

    public void setItValorPlaca6(String itValorPlaca6) {
        this.itValorPlaca6 = itValorPlaca6;
    }

    public String getDireccionAValidar() {
        return direccionAValidar;
    }

    public void setDireccionAValidar(String direccionAValidar) {
        this.direccionAValidar = direccionAValidar;
    }


        
        
        
}
