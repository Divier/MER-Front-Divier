/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

/**
 *
 * @author User
 */
public class RequestLoginAutentica {

    private String usuario;
    private String password;
    private String idApp;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the idApp
     */
    public String getIdApp() {
        return idApp;
    }

    /**
     * @param idApp the idApp to set
     */
    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }
}
