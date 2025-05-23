package co.com.claro.mgl.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utilitarios para funciones Aritm&eacute;ticas.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>)
 */
public class MathUtils {
    
    /**
     * Realiza el redondeo de un n&uacute;mero decimal.
     * 
     * @param value N&uacute;mero decimal a redondear.
     * @param places Cantidad de decimales.
     * @return N&uacute;mero redondeado.
     */
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException("Debe especificar el número de decimales como un número positivo.");
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
