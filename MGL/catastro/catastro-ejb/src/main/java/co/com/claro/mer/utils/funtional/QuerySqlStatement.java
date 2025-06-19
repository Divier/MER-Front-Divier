package co.com.claro.mer.utils.funtional;

/**
 * Interfaz funcional para manejar sentencias SQL condicionales.
 * Permite agregar una sentencia SQL a un StringBuilder
 * si una condición booleana es verdadera.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/05/29
 */
@FunctionalInterface
public interface QuerySqlStatement {

    /**
     * Método que acepta una condición booleana, una sentencia SQL y un StringBuilder.
     *
     * @param condition Condición booleana que determina si se agrega la sentencia SQL.
     * @param sql Sentencia SQL que se agregará al StringBuilder si la condición es verdadera.
     * @param queryStr StringBuilder al que se le agregará la sentencia SQL si se cumple la condición.
     * @author Gildardo Mora
     */
    void accept(Boolean condition, String sql, StringBuilder queryStr);

    /**
     * Método estático para crear una instancia por defecto
     * @return Una instancia de QuerySqlStatement que agrega la condición SQL si se cumple
     */
    static QuerySqlStatement addStatement() {
        return (condition, sql, queryStr) -> {
            if (Boolean.TRUE.equals(condition)) {
                queryStr.append(sql);
            }
        };
    }

}
