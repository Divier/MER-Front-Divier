package co.com.claro.mgl.utils;

/**
 *
 * @author valbuenayf
 */
public enum CmtCoverEnum {

    NODO_UNO("BI", "NODO UNO"),
    NODO_DOS("UNI", "NODO DOS"),
    NODO_TRES("FOG", "NODO TRES"),
    NODO_DTH("DTH", "NODO DTH"),
    NODO_MOVIL("MOV", "NODO MOVIL"),
    NODO_FTTH("FTTH", "NODO FTTH"),
    NODO_FTTX("FTTX", "NODO FTTX"),
    NODO_WIFI("LTE", "NODO WIFI"),
    NODO_FOU("FOU", "NODO FOU"),
    NODO_RFO("RFO", "NODO RFO"),
    //Nuevas Coberturas JDHT    
    NODO_ZONA_GPON_DISENIADO("ZONA_GPON_DISENIADO", "ZONA GPON DISENIADO"),
    NODO_ZONA_MICRO_ONDAS("ZONA_MICRO_ONDAS", "NODO ZONA MICRO ONDAS"),
    NODO_ZONA_3G("ZONA_3G", "ZONA 3G"),
    NODO_ZONA_4G("ZONA_4G", "ZONA 4G"),
    NODO_ZONA_COBERTURA_CAVS("ZONA_COBERTURA_CAVS", "ZONA COBERTURA CAVS"),    
    NODO_ZONA_COBERTURA_ULTIMA_MILLA("ZONA_COBERTURA_ULTIMA_MILLA", "ZONA COBERTURA ULTIMA MILLA"),
    NODO_ZONA_CURRIER("ZONA_CURRIER", "ZONA CURRIER"),
    NODO_ZONA_5G("NODO_ZONA_5G", "ZONA 5G"),
    NODO_ZONA_UNIFILAR("UNI", "ZONA_UNIFILAR");
    
    private String codigo;
    private String valor;

    private CmtCoverEnum(String codigo, String valor) {
        this.codigo = codigo;
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getValor() {
        return valor;
    }
}
