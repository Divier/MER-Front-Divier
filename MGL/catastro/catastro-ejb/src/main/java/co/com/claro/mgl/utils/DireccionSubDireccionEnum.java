package co.com.claro.mgl.utils;

/**
 *
 * @author valbuenayf
 */
public enum DireccionSubDireccionEnum {

    DIRECCION(1, "Direccion"),
    DIRECCION_SUB_DIRECCION(2, "Direccion y Sub Direccion");
    private Integer codgo;
    private String valor;

    private DireccionSubDireccionEnum(Integer codgo, String valor) {
        this.codgo = codgo;
        this.valor = valor;
    }

    public Integer getCodgo() {
        return codgo;
    }

    public String getValor() {
        return valor;
    }
}
