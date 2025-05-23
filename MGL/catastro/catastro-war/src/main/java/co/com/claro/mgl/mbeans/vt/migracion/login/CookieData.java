/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.migracion.login;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author gsantos1
 */
public class CookieData implements Serializable {

  private String idUsuario;
  private String usuario;
  private int perfil;
  private String nombre;
  private ArrayList<String> roles;
  private ArrayList<Integer> tiposTrabajo;
  private ArrayList<String> ciudades;
  private ArrayList<String> aliados;
  private String aliado;
  private ArrayList<String> parametros;

  public String getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public int getPerfil() {
    return perfil;
  }

  public void setPerfil(int perfil) {
    this.perfil = perfil;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public ArrayList<String> getRoles() {
    return roles;
  }

  public void setRoles(ArrayList<String> roles) {
    this.roles = roles;
  }

  public ArrayList<Integer> getTiposTrabajo() {
    return tiposTrabajo;
  }

  public void setTiposTrabajo(ArrayList<Integer> tiposTrabajo) {
    this.tiposTrabajo = tiposTrabajo;
  }

  public ArrayList<String> getCiudades() {
    return ciudades;
  }

  public void setCiudades(ArrayList<String> ciudades) {
    this.ciudades = ciudades;
  }

  public ArrayList<String> getAliados() {
    return aliados;
  }

  public void setAliados(ArrayList<String> aliados) {
    this.aliados = aliados;
  }

  public String getAliado() {
    return aliado;
  }

  public void setAliado(String aliado) {
    this.aliado = aliado;
  }

  public ArrayList<String> getParametros() {
    return parametros;
  }

  public void setParametros(ArrayList<String> parametros) {
    this.parametros = parametros;
  }
}
