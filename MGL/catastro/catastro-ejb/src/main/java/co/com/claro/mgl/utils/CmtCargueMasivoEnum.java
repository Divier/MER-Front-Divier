package co.com.claro.mgl.utils;

/**
 *
 * @author valbuenayf
 */
public enum CmtCargueMasivoEnum {

    NMC("NMC", 1),
    CIA("CIA", 2),
    CIS("CIS", 3),
    ADM("ADM", 4),
    TPU("TPU", 5),
    TPD("TPD", 6),
    BTP("BTP", 7),
    BOD("BOD", 8),
    CND("CND", 9),
    ETI("ETI", 10),
    BTC("BTC", 11),
    BAE("BAE", 12),
    BTD("BTD", 13),
    TDE("TDE", 14),
    CON("CON", 15);
    private String valor;
    private Integer id;

    private CmtCargueMasivoEnum(String valor, Integer id) {
        this.valor = valor;
        this.id = id;
    }

    public static Integer getCodigoId(String codigo) {
        Integer respuesta = 0;
        for (CmtCargueMasivoEnum c : CmtCargueMasivoEnum.values()) {
            if (c.valor.equals(codigo)) {
                respuesta = c.id;
                break;
            }
        }
        return respuesta;
    }

    public String getValor() {
        return valor;
    }

    public Integer getId() {
        return id;
    }
}
