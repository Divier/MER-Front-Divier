/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;



import java.io.Serializable;
import java.util.List;


/**
 *
 * @author User
 */
public class UsuarioMgl implements Serializable{
    
    private String codPerfil;
    private String descripcion;
    private String estado;
    private String idPerfil;
    private String idUsuario;
    private List<RolMgl> listRoles;
    private String nombre;
    private String usuario;
    

        


    public UsuarioMgl() {
    }

    public List<RolMgl> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<RolMgl> listRoles) {
        this.listRoles = listRoles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    public String getCodPerfil() {
        return codPerfil;
    }

    public void setCodPerfil(String codPerfil) {
        this.codPerfil = codPerfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<RolMgl> getRoles() {
        return listRoles;
    }

    public void setRoles(List<RolMgl> listRoles) {
        this.listRoles = listRoles;
    }
        
}
