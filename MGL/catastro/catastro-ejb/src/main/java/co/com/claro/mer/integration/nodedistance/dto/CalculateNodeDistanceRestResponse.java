package co.com.claro.mer.integration.nodedistance.dto;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
public class CalculateNodeDistanceRestResponse {

    private Cursor cursor;
    private String code;
    private String message;
    private String errorcode;

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void seterrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    @Override
    public String toString() {
        return "CalculateNodeDistanceRestResponse{" + "cursor=" + cursor.toString() + ", code=" + code + ", message=" + message + ", errorcode=" + errorcode + '}';
    }
}
