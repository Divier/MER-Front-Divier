package co.com.claro.mgl.dtos;


/**
 * Data Transfer Object para el manejo de par&aacute;metros en el m&oacute;dulo de consulta de coberturas.
 * 
 * @author Camilo Miranda (<i>miradaca</i>).
 */
public class OpcionIdNombreDTO {
    
    /** Identificador del Par&aacute;metro. */
    private String idParametro;
    /** Descripci&oacute;n del Par&aacute;metro. */
    private String descripcion;
    /** Tipo de Par&aacute;metro. */
    private String idTipo;

    public String getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(String idParametro) {
        this.idParametro = idParametro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }
}
