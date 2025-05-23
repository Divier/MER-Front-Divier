package co.com.claro.mgl.dtos;

/**
 *
 * @author corredorje
 */
public class FiltroConsultaHomologacionRazonesDto {
    
    private int idHomologacionSeleccionado;;
    private String codOFSCSeleccionado;
    private String descOFSCSeleccionado;
    private String codONYXSeleccionado;
    private String descONYXSeleccionado;

    public int getIdHomologacionSeleccionado() {
        return idHomologacionSeleccionado;
    }

    public void setIdHomologacionSeleccionado(int idHomologacionSeleccionado) {
        this.idHomologacionSeleccionado = idHomologacionSeleccionado;
    }

    public String getCodOFSCSeleccionado() {
        return codOFSCSeleccionado;
    }

    public void setCodOFSCSeleccionado(String codOFSCSeleccionado) {
        this.codOFSCSeleccionado = codOFSCSeleccionado;
    }

    public String getDescOFSCSeleccionado() {
        return descOFSCSeleccionado;
    }

    public void setDescOFSCSeleccionado(String descOFSCSeleccionado) {
        this.descOFSCSeleccionado = descOFSCSeleccionado;
    }

    public String getCodONYXSeleccionado() {
        return codONYXSeleccionado;
    }

    public void setCodONYXSeleccionado(String codONYXSeleccionado) {
        this.codONYXSeleccionado = codONYXSeleccionado;
    }

    public String getDescONYXSeleccionado() {
        return descONYXSeleccionado;
    }

    public void setDescONYXSeleccionado(String descONYXSeleccionado) {
        this.descONYXSeleccionado = descONYXSeleccionado;
    } 
}
