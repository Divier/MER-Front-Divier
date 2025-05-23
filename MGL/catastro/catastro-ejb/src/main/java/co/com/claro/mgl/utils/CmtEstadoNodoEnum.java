package co.com.claro.mgl.utils;

/**
 *
 * @author valbuenayf
 */
public enum CmtEstadoNodoEnum {

    ACTIVO("A", "ACTIVO"),
    INACTIVO("I", "INACTIVO"),
    PREVENTA("P", "PREVENTA");
    private String codigo;
    private String valor;

    private CmtEstadoNodoEnum(String codigo, String valor) {
        this.codigo = codigo;
        this.valor = valor;
    }

    public static String getNameEstadoNodo(String codTipoRed) {

        String respueta = "";

        if (codTipoRed.equals(ACTIVO.getCodigo())) {
            respueta = ACTIVO.getValor();
        } else if (codTipoRed.equals(INACTIVO.getCodigo())) {
            respueta = INACTIVO.getValor();
        }
        if (codTipoRed.equals(PREVENTA.getCodigo())) {
            respueta = PREVENTA.getValor();
        }
        return respueta;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getValor() {
        return valor;
    }
}
