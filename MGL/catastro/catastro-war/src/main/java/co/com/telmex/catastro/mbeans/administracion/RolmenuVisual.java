package co.com.telmex.catastro.mbeans.administracion;

import java.math.BigDecimal;

/**
 * Clase RolmenuVisual
 *
 * @author 	Nataly Orozco Torres.
 * @version	1.0
 */
public class RolmenuVisual {

    BigDecimal idGmenu;
    String nameGMenu;
    BigDecimal idFuncionalidad;
    String nameFuncionalidad;
    boolean status;

    /**
     * 
     * @return
     */
    public BigDecimal getIdFuncionalidad() {
        return idFuncionalidad;
    }

    /**
     * 
     * @param idFuncionalidad
     */
    public void setIdFuncionalidad(BigDecimal idFuncionalidad) {
        this.idFuncionalidad = idFuncionalidad;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getIdGmenu() {
        return idGmenu;
    }

    /**
     * 
     * @param idGmenu
     */
    public void setIdGmenu(BigDecimal idGmenu) {
        this.idGmenu = idGmenu;
    }

    /**
     * 
     * @return
     */
    public String getNameFuncionalidad() {
        return nameFuncionalidad;
    }

    /**
     * 
     * @param nameFuncionalidad
     */
    public void setNameFuncionalidad(String nameFuncionalidad) {
        this.nameFuncionalidad = nameFuncionalidad;
    }

    /**
     * 
     * @return
     */
    public String getNameGMenu() {
        return nameGMenu;
    }

    /**
     * 
     * @param nameGMenu
     */
    public void setNameGMenu(String nameGMenu) {
        this.nameGMenu = nameGMenu;
    }

    /**
     * 
     * @return
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * 
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
}
