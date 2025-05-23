package co.com.claro.cmas400.ejb.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

/**
 * Representa la petición hacia el servicio
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/08/16
 */
@Getter
@ToString
public class RequestEmail {

    @JsonProperty("headerRequest")
    private HeaderEmailRequest headerRequest;

    @JsonProperty("message")
    private String message;

    private static final String TRANSACTION_ID = "transactionId431";
    private static final String IP_APPLIICATION = "ipApplication435";
    private static final String TRACEABILITY_ID = "traceabilityId436";

    /**
     * Constructor del request
     *
     * @param message {@link String} Mensaje procesado con la platilla
     *                para enviar el cuerpo del correo.
     * @param system  {@link String} Usuario requerido para hacer la petición al servicio.
     * @param user    {@link String} credencial requerida para realizar el envío del correo.
     * @param pass    {@link String} Credencial para consumir el servicio de envío de Correo
     */
    public RequestEmail(String message, String system, String user, String pass) {
        this.message = message;
        this.headerRequest = new HeaderEmailRequest();
        this.headerRequest.setTransactionId(TRANSACTION_ID);
        this.headerRequest.setIpApplication(IP_APPLIICATION);
        this.headerRequest.setTraceabilityId(TRACEABILITY_ID);
        Instant instant = Instant.now();
        String fechaHoraEnvio = instant.toString().replace("Z", "");
        this.headerRequest.setRequestDate(fechaHoraEnvio);
        this.headerRequest.setSystem(system);
        this.headerRequest.setUser(user);
        this.headerRequest.setPassword(pass);
    }
}
