/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "USUARIOS", schema = "GESTIONNEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u "),
    @NamedQuery(name = "Usuarios.findUsuarioByUsuario", query = "SELECT u FROM Usuarios u  WHERE LOWER(u.usuario) = LOWER(:usuario)")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)

    @Column(name = "ID_USUARIO", nullable = false)
    private BigDecimal idUsuario;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "PASS")
    private String pass;
    @Column(name = "FECHA_PASS")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaPass;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "COD_CARGO")
    private BigDecimal codCargo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERFIL", nullable = false)
    private UsPerfil perfil;
    @Column(name = "IDMOVIL")
    private BigDecimal idMovil;
    @Column(name = "IDALIADO")
    private BigDecimal idAliado;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "VENDEDOR")
    private String vendedor;
    @Column(name = "FECHA_CAMBIO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCambio;
    @Column(name = "USU_MODIFICO")
    private BigDecimal usuModifico;
    @Column(name = "USUARIORR")
    private String usuarioRr;
    @Column(name = "IDDISTRIBUIDOR")
    private BigDecimal idDistribuidor;
    @Column(name = "USUARIODOMAIN")
    private String usuarioDomain;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USPOLIEDRO")
    private String usPoliedro;
    @Column(name = "USVENTA")
    private String usVenta;

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getFechaPass() {
        return fechaPass;
    }

    public void setFechaPass(Date fechaPass) {
        this.fechaPass = fechaPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(BigDecimal codCargo) {
        this.codCargo = codCargo;
    }

    public UsPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(UsPerfil perfil) {
        this.perfil = perfil;
    }

    public BigDecimal getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(BigDecimal idMovil) {
        this.idMovil = idMovil;
    }

    public BigDecimal getIdAliado() {
        return idAliado;
    }

    public void setIdAliado(BigDecimal idAliado) {
        this.idAliado = idAliado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public BigDecimal getUsuModifico() {
        return usuModifico;
    }

    public void setUsuModifico(BigDecimal usuModifico) {
        this.usuModifico = usuModifico;
    }

    public String getUsuarioRr() {
        return usuarioRr;
    }

    public void setUsuarioRr(String usuarioRr) {
        this.usuarioRr = usuarioRr;
    }

    public BigDecimal getIdDistribuidor() {
        return idDistribuidor;
    }

    public void setIdDistribuidor(BigDecimal idDistribuidor) {
        this.idDistribuidor = idDistribuidor;
    }

    public String getUsuarioDomain() {
        return usuarioDomain;
    }

    public void setUsuarioDomain(String usuarioDomain) {
        this.usuarioDomain = usuarioDomain;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsPoliedro() {
        return usPoliedro;
    }

    public void setUsPoliedro(String usPoliedro) {
        this.usPoliedro = usPoliedro;
    }

    public String getUsVenta() {
        return usVenta;
    }

    public void setUsVenta(String usVenta) {
        this.usVenta = usVenta;
    }
    
    @Override
    public String toString(){
        return "["+this.usuario+"]"+this.nombre;
    }
}
