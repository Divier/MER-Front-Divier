package co.com.claro.mer.email.utils;

import co.com.claro.mer.email.application.constant.EmailConstants;
import co.com.claro.mer.email.domain.models.dto.EmailBaseDto;
import co.com.claro.mgl.error.ApplicationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utilitario para apoyar generación del mensaje con la estructura requerida
 * para ser enviado por correo.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/13
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailMessageUtil {

    /**
     * Genera el mensaje formateado para enviar por correo.
     *
     * @param addressList    {@link List<String>} Lista de direcciones de correo de destinatarios
     * @param attachments    {@code Map<String, String>} Información de archivos adjuntos
     * @param messageContent {@link String} Mensaje a incluir en el envío del correo
     * @param emailSubject   {@link String} Asunto del correo.
     * @param profileIdParam {@link String[]}  Perfiles de protocolos de correo
     * @return {@link String} Retorna el mensaje formateado para ser enviado por correo.
     * @throws ApplicationException Excepción en caso de error al generar el mensaje.
     * @author Gildardo Mora
     */
    public static String generateMessage(List<String> addressList, Map<String, String> attachments,
            String messageContent, String emailSubject, String[] profileIdParam) throws ApplicationException {
        try {
            // Construir el array de objetos de cliente
            JSONArray customers = buildCustomersArray(addressList);
            // Construir la configuración de la caja de mensajes
            JSONObject messageBoxConf = new JSONObject();
            messageBoxConf.put(EmailConstants.MESSAGE_CHANNEL_KEY, EmailConstants.MESSAGE_CHANNEL_VALUE);
            messageBoxConf.put(EmailConstants.MESSAGE_BOX_KEY, customers);
            JSONArray messageBox = new JSONArray();
            messageBox.put(messageBoxConf);
            // Construir el array de archivos adjuntos
            JSONArray attach = buildAttachmentsArray(attachments);
            // Construir el objeto de mensaje principal
            JSONObject msg = buildMessageObject(messageBox, attach, messageContent,
                    emailSubject, profileIdParam);
            return msg.toString();
        } catch (JSONException ex) {
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    /**
     * Construye el array de objetos de cliente basado en las direcciones de correo electrónico proporcionadas.
     *
     * @param addressList Lista de direcciones de correo electrónico.
     * @return JSONArray que contiene objetos de cliente.
     * @author Gildardo Mora
     */
    private static JSONArray buildCustomersArray(List<String> addressList) {
        JSONArray customers = new JSONArray();
        addressList.forEach(addressEmail -> {
            JSONObject customer = new JSONObject();
            customer.put(EmailConstants.CUSTOMER_ID_KEY, EmailConstants.CUSTOMER_ID_VALUE);
            customer.put(EmailConstants.CUSTOMER_BOX_KEY, addressEmail);
            customers.put(customer);
        });

        return customers;
    }

    /**
     * Construye el array de objetos de archivo adjunto basado en el mapa de archivos adjuntos proporcionado.
     *
     * @param attachments Mapa de archivos adjuntos (pares de nombre-contenido).
     * @return JSONArray que contiene objetos de archivo adjunto.
     * @author Gildardo Mora
     */
    private static JSONArray buildAttachmentsArray(Map<String, String> attachments) {
        JSONArray attach = new JSONArray();
        if (attachments != null) {
            return new JSONArray(attachments.entrySet().stream()
                    .map(entry -> {
                        JSONObject attached = new JSONObject();
                        attached.put(EmailConstants.NAME_KEY, entry.getKey());
                        attached.put(EmailConstants.TYPE_KEY, EmailConstants.TYPE_VALUE);
                        attached.put(EmailConstants.ENCODE_KEY, EmailConstants.ENCODE_VALUE);
                        attached.put(EmailConstants.CONTENT_KEY, entry.getValue());
                        return attached;
                    })
                    .collect(Collectors.toList()));
        }

        return attach;
    }

    /**
     * Construye el objeto de mensaje principal basado en los parámetros proporcionados.
     *
     * @param messageBox     JSONArray que contiene la configuración de la caja de mensajes.
     * @param attach         JSONArray que contiene archivos adjuntos.
     * @param messageContent Contenido del mensaje de correo electrónico.
     * @param emailSubject   Asunto del correo electrónico.
     * @param profileIdParam Array de identificadores de perfil.
     * @return JSONObject que representa el objeto de mensaje principal.
     * @throws JSONException En caso de que haya un error en el procesamiento JSON.
     * @author Gildardo Mora
     */
    private static JSONObject buildMessageObject(JSONArray messageBox, JSONArray attach,
            String messageContent, String emailSubject, String[] profileIdParam) {
        JSONObject msg = new JSONObject();
        msg.put(EmailConstants.PUSH_TYPE_KEY, EmailConstants.PUSH_TYPE_VALUE);
        msg.put(EmailConstants.TYPE_CUSTOMER_KEY, EmailConstants.TYPE_CUSTOMER_VALUE);
        msg.put(EmailConstants.MESSAGE_BOX_KEY, messageBox);
        msg.put(EmailConstants.PROFILE_ID_KEY, new JSONArray(Arrays.asList(profileIdParam)));
        msg.put(EmailConstants.COMUNICATION_TYPE_KEY, EmailConstants.COMUNICATION_TYPE_VALUE);
        msg.put(EmailConstants.COMMUNICATION_ORIGIN_KEY, EmailConstants.COMMUNICATION_ORIGIN_VALUE);
        msg.put(EmailConstants.DELIVERY_RECEIPTS_KEY, EmailConstants.DELIVERY_RECEIPTS_VALUE);
        msg.put(EmailConstants.CONTENT_TYPE_KEY, EmailConstants.CONTENT_TYPE_VALUE);
        msg.put(EmailConstants.MESSAGE_CONTENT_KEY, messageContent);
        msg.put(EmailConstants.SUBJECT_CONTENT_KEY, emailSubject);

        if (!attach.isEmpty()) {
            msg.put(EmailConstants.ATTACH_KEY, attach);
        }

        return msg;
    }

    /**
     * Realiza el proceso de reemplazo de valor de variables definidas en
     * una cadena de texto, a partir de mapa recibido con sus referencias.
     *
     * @param texto              {@link String}
     * @param mapaLlaveReemplazo {@code Map<String, String>}
     * @return {@link String} Retorna la cadena de texto con los valores de las variables reemplazados.
     * @author Gildardo Mora
     */
    public static String reemplazarTexto(String texto, Map<String, String> mapaLlaveReemplazo) {
        for (Map.Entry<String, String> entry : mapaLlaveReemplazo.entrySet()) {
            String llave = entry.getKey();
            String valor = entry.getValue();
            texto = texto.replace(llave, StringUtils.isBlank(valor) ? "NA" : valor);
        }

        return texto;
    }

    /**
     * Genera el mapa con los valores a procesar para generar la estructura del
     * mensaje a enviar por correo.
     *
     * @param emailDto EmailDetailsReportDto
     * @return {@code Map<String, String>}
     * @author Gildardo Mora
     */
    public static Map<String, String> generateBodyEmailMap(EmailBaseDto emailDto) {
        Map<String, String> bodyEmailMap = new HashMap<>();
        //Captura de fecha y hora
        LocalDateTime localDateTime = LocalDateTime.now();
        String horaMinutos = localDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        String fecha = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.of("America/Bogota")));
        //asignar valores a los atributos leídos por la plantilla
        bodyEmailMap.put(EmailConstants.MODULO_ATRIBUTO, emailDto.getModule());
        bodyEmailMap.put(EmailConstants.USUARIO_SESION_ATRIBUTO, emailDto.getSessionUser());
        bodyEmailMap.put(EmailConstants.NOMBRE_USUARIO_SESION_ATRIBUTO, emailDto.getSessionUserName());
        bodyEmailMap.put(EmailConstants.HORA_MINUTOS_ATRIBUTO, horaMinutos);
        bodyEmailMap.put(EmailConstants.FECHA_ATRIBUTO, fecha);
        bodyEmailMap.put(EmailConstants.MENSAJE_ATRIBUTO, emailDto.getMessage());
        return bodyEmailMap;
    }
}
