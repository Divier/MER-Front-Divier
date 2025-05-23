/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.util;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMglNew;

/**
 *
 * @author castrofo
 */
public class AuxExportFichas {

    public static final int TIPO_CCMM = 1;
    public static final int TIPO_HHPP = 2;

    private int tipoRegistro;

    private PreFichaXlsMgl preficha;
    private PreFichaXlsMglNew prefichaNew;
    private String direccionHHPP;
    private DrDireccion drDireccion;
    private HhppMgl hhppMGL;

    public AuxExportFichas(int tipoRegistro, PreFichaXlsMgl preficha, String direccionHHPP) {
        this.tipoRegistro = tipoRegistro;
        this.preficha = preficha;
        this.direccionHHPP = direccionHHPP;
    }

    public AuxExportFichas(int tipoRegistro, PreFichaXlsMgl preficha, String direccionHHPP, DrDireccion drDireccion) {
        this.tipoRegistro = tipoRegistro;
        this.preficha = preficha;
        this.drDireccion = drDireccion;
        this.direccionHHPP = direccionHHPP;
    }

    public AuxExportFichas(int tipoRegistro, PreFichaXlsMgl preficha, String direccionHHPP, HhppMgl hhppMGL) {
        this.tipoRegistro = tipoRegistro;
        this.preficha = preficha;
        this.hhppMGL = hhppMGL;
        this.direccionHHPP = direccionHHPP;
    }

    //CONSTRUCTORES NUEVA IMPL
    public AuxExportFichas(int tipoRegistro, PreFichaXlsMglNew preficha, String direccionHHPP) {
        this.tipoRegistro = tipoRegistro;
        this.prefichaNew = preficha;
        this.direccionHHPP = direccionHHPP;
    }

    public AuxExportFichas(int tipoRegistro, PreFichaXlsMglNew preficha, String direccionHHPP, DrDireccion drDireccion) {
        this.tipoRegistro = tipoRegistro;
        this.prefichaNew = preficha;
        this.drDireccion = drDireccion;
        this.direccionHHPP = direccionHHPP;
    }

    public AuxExportFichas(int tipoRegistro, PreFichaXlsMglNew preficha, String direccionHHPP, HhppMgl hhppMGL) {
        this.tipoRegistro = tipoRegistro;
        this.prefichaNew = preficha;
        this.hhppMGL = hhppMGL;
        this.direccionHHPP = direccionHHPP;
    }

    public int getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(int tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public PreFichaXlsMgl getPreficha() {
        return preficha;
    }

    public void setPreficha(PreFichaXlsMgl preficha) {
        this.preficha = preficha;
    }

    public PreFichaXlsMglNew getPrefichaNew() {
        return prefichaNew;
    }

    public void setPrefichaNew(PreFichaXlsMglNew prefichaNew) {
        this.prefichaNew = prefichaNew;
    }

    public String getDireccionHHPP() {
        return direccionHHPP;
    }

    public void setDireccionHHPP(String direccionHHPP) {
        this.direccionHHPP = direccionHHPP;
    }

    /**
     * @return the drDireccion
     */
    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    /**
     * @param drDireccion the drDireccion to set
     */
    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    /**
     * @return the hhppMGL
     */
    public HhppMgl getHhppMGL() {
        return hhppMGL;
    }

    /**
     * @param hhppMGL the hhppMGL to set
     */
    public void setHhppMGL(HhppMgl hhppMGL) {
        this.hhppMGL = hhppMGL;
    }

}
