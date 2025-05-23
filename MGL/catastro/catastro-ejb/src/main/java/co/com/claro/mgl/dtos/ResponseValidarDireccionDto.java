/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMglNew;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public class ResponseValidarDireccionDto {

    private DrDireccion drDireccion;
    private String direccion;
    private String direccionAntigua;
    private List<String> Barrios = new LinkedList<String>();
    private boolean intradusible = false;
    private boolean mostrarFormulario = false;
    private ArrayList<String> validationMessages = new ArrayList<String>();
    private Map<String, String> validationMessages2 = new HashMap<String, String>();
    private AddressService addressService = new AddressService();
    private DireccionRREntity direccionRREntity = new DireccionRREntity();
    private boolean existe = false;
    private boolean multiOrigen = false;
    private boolean validacionExitosa = false;
    private boolean antigua = false;
    private String barrioGeo;
    private String direccionRespuestaGeo;
    private boolean dirTabulada = false;
    private PreFichaXlsMgl prefichaSelected;
    private PreFichaXlsMglNew prefichaSelectedNew;

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<String> getBarrios() {
        return Barrios;
    }

    public void setBarrios(List<String> Barrios) {
        this.Barrios = Barrios;
    }

    public boolean isIntradusible() {
        return intradusible;
    }

    public void setIntradusible(boolean intradusible) {
        this.intradusible = intradusible;
    }

    public boolean isMostrarFormulario() {
        return mostrarFormulario;
    }

    public void setMostrarFormulario(boolean mostrarFormulario) {
        this.mostrarFormulario = mostrarFormulario;
    }

    public ArrayList<String> getValidationMessages() {
        return validationMessages;
    }

    public void setValidationMessages(ArrayList<String> validationMessages) {
        this.validationMessages = validationMessages;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public DireccionRREntity getDireccionRREntity() {
        return direccionRREntity;
    }

    public void setDireccionRREntity(DireccionRREntity direccionRREntity) {
        this.direccionRREntity = direccionRREntity;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public boolean isMultiOrigen() {
        return multiOrigen;
    }

    public void setMultiOrigen(boolean multiOrigen) {
        this.multiOrigen = multiOrigen;
    }

    public boolean isValidacionExitosa() {
        return validacionExitosa;
    }

    public void setValidacionExitosa(boolean validacionExitosa) {
        this.validacionExitosa = validacionExitosa;
    }

    public String getDireccionAntigua() {
        return direccionAntigua;
    }

    public void setDireccionAntigua(String direccionAntigua) {
        this.direccionAntigua = direccionAntigua;
    }

    public boolean isAntigua() {
        return antigua;
    }

    public void setAntigua(boolean antigua) {
        this.antigua = antigua;
    }

    public Map<String, String> getValidationMessages2() {
        return validationMessages2;
    }

    public void setValidationMessages2(Map<String, String> validationMessages2) {
        this.validationMessages2 = validationMessages2;
    }

    public String getBarrioGeo() {
        return barrioGeo;
    }

    public void setBarrioGeo(String barrioGeo) {
        this.barrioGeo = barrioGeo;
    }

    public String getDireccionRespuestaGeo() {
        return direccionRespuestaGeo;
    }

    public void setDireccionRespuestaGeo(String direccionRespuestaGeo) {
        this.direccionRespuestaGeo = direccionRespuestaGeo;
    }

    public boolean isDirTabulada() {
        return dirTabulada;
    }

    public void setDirTabulada(boolean dirTabulada) {
        this.dirTabulada = dirTabulada;
    }

    public PreFichaXlsMgl getPrefichaSelected() {
        return prefichaSelected;
    }

    public void setPrefichaSelected(PreFichaXlsMgl prefichaSelected) {
        this.prefichaSelected = prefichaSelected;
    }

    public PreFichaXlsMglNew getPrefichaSelectedNew() {
        return prefichaSelectedNew;
    }

    public void setPrefichaSelectedNew(PreFichaXlsMglNew prefichaSelected) {
        this.prefichaSelectedNew = prefichaSelected;
    }
}
