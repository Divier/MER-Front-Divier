package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Clase Menu
 * Implementa Serialización.
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal menId;
    private Menu menuParte;
    private List<Menu> menus;
    private String menNombre;
    private BigInteger menOrden;
    private String menModulo;
    private String menEnlace;
    private Date menFechaCreacion;
    private String menUsuarioCreacion;
    private Date menFechaModificacion;
    private String menUsuarioModificacion;

    /**
     * Constructor.
     */
    public Menu() {
    }

    /**
     * Constructor con parámetros.
     * @param menId
     */
    public Menu(BigDecimal menId) {
        this.menId = menId;
    }

    /**
     * Obtiene el Id del menú.
     * @return Entero con el Id del menú.
     */
    public BigDecimal getMenId() {
        return menId;
    }

    /**
     * Establece el Id del menú.
     * @param menId Entero con el Id del Menú.
     */
    public void setMenId(BigDecimal menId) {
        this.menId = menId;
    }

    /**
     * Obtiene una sección del menú.
     * @return Objeto Menu con la información de la parte.
     */
    public Menu getMenuParte() {
        return menuParte;
    }

    /**
     * Establece la parte del menú.
     * @param menuParte Objeto Menu con la información.
     */
    public void setMenuParte(Menu menuParte) {
        this.menuParte = menuParte;
    }

    /**
     * Obtiene los Menús existentes.
     * @return Lista de objetos Menu con la información.
     */
    public List<Menu> getMenus() {
        return menus;
    }

    /**
     * Establece los menús existentes.
     * @param menus Lista de Objetos Menu con la información.
     */
    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    /**
     * Obtiene el nombre del menú.
     * @return Cadena con el nombre del menú.
     */
    public String getMenNombre() {
        return menNombre;
    }

    /**
     * Establece el nombre del menú.
     * @param menNombre Cadena con el nombre del Menú.
     */
    public void setMenNombre(String menNombre) {
        this.menNombre = menNombre;
    }

    /**
     * Obtiene el orden en el que se mostrará el menú.
     * @return Entero con la posición de Orden del menú.
     */
    public BigInteger getMenOrden() {
        return menOrden;
    }

    /**
     * Establece el orden del menú
     * @param menOrden Entero con la posición.
     */
    public void setMenOrden(BigInteger menOrden) {
        this.menOrden = menOrden;
    }

    /**
     * Obtiene el nombre del modulo.
     * @return Cadena con el nombre del modulo.
     */
    public String getMenModulo() {
        return menModulo;
    }

    /**
     * Establece el nombre del modulo.
     * @param menModulo Cadena con el nombre del modulo.
     */
    public void setMenModulo(String menModulo) {
        this.menModulo = menModulo;
    }

    /**
     * Obtiene el enlace.
     * @return Cadena con el enlace.
     */
    public String getMenEnlace() {
        return menEnlace;
    }

    /**
     * Establece el enlace.
     * @param menEnlace Cadena con el enlace.
     */
    public void setMenEnlace(String menEnlace) {
        this.menEnlace = menEnlace;
    }

    /**
     * Obtiene la fecha de creación.
     * @return Fecha de creación.
     */
    public Date getMenFechaCreacion() {
        return menFechaCreacion;
    }

    /**
     * Establece la fecha de creación
     * @param menFechaCreacion Fecha de creación.
     */
    public void setMenFechaCreacion(Date menFechaCreacion) {
        this.menFechaCreacion = menFechaCreacion;
    }

    /**
     * Obtiene el usuario que realiza la creación.
     * @return Cadena con el Usuario que realiza la creación.
     */
    public String getMenUsuarioCreacion() {
        return menUsuarioCreacion;
    }

    /**
     * Establece el usuario que realiza la creación.
     * @param menUsuarioCreacion Cadena con el usuario que realiza la creación.
     */
    public void setMenUsuarioCreacion(String menUsuarioCreacion) {
        this.menUsuarioCreacion = menUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     * @return Fecha de modificación.
     */
    public Date getMenFechaModificacion() {
        return menFechaModificacion;
    }

    /**
     * Establece la fecha de modificación.
     * @param menFechaModificacion Fecha de modificación.
     */
    public void setMenFechaModificacion(Date menFechaModificacion) {
        this.menFechaModificacion = menFechaModificacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     * @return Cadena con el nombre del usuario.
     */
    public String getMenUsuarioModificacion() {
        return menUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación
     * @param menUsuarioModificacion Cadena con el usuario que realiza la modificación
     */
    public void setMenUsuarioModificacion(String menUsuarioModificacion) {
        this.menUsuarioModificacion = menUsuarioModificacion;
    }

    /**
     * Obtiene información para auditoría
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        return "Menu:" + "menId=" + menId + ", menuParte=" + menuParte
                + ", menus=" + menus + ", menNombre=" + menNombre
                + ", menOrden=" + menOrden + ", menModulo=" + menModulo
                + ", menEnlace=" + menEnlace + '.';
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "Menu:" + "menId=" + menId + ", menuParte=" + menuParte
                + ", menus=" + menus + ", menNombre=" + menNombre
                + ", menOrden=" + menOrden + ", menModulo=" + menModulo
                + ", menEnlace=" + menEnlace + '.';
    }
}