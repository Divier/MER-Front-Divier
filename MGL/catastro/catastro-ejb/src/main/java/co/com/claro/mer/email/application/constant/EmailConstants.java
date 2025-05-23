package co.com.claro.mer.email.application.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.json.JSONArray;

import java.util.Collections;

/**
 * Constantes asociadas a los procesos de envío de correos.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/10
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailConstants {

    public static final String PROFILE_ID_KEY = "profileId";
    public static final String COMUNICATION_TYPE_KEY = "communicationType";
    public static final JSONArray COMUNICATION_TYPE_VALUE = new JSONArray(Collections.singletonList("REGULATORIO"));
    public static final String CUSTOMER_ID_KEY = "customerId";
    public static final String CUSTOMER_ID_VALUE = "9F1AA44D-B90F-E811-80ED-FA163E10DFBE";
    public static final String CUSTOMER_BOX_KEY = "customerBox";
    public static final String MESSAGE_CHANNEL_KEY = "messageChannel";
    public static final String MESSAGE_CHANNEL_VALUE = "SMTP";
    public static final String MESSAGE_BOX_KEY = "messageBox";
    public static final String NAME_KEY = "name";
    public static final String TYPE_KEY = "type";
    public static final String TYPE_VALUE = "FILE";
    public static final String ENCODE_KEY = "encode";
    public static final String ENCODE_VALUE = "BASE64";
    public static final String CONTENT_KEY = "content";
    public static final String PUSH_TYPE_KEY = "pushType";
    public static final String PUSH_TYPE_VALUE = "SINGLE";
    public static final String COMMUNICATION_ORIGIN_KEY = "communicationOrigin";
    public static final String COMMUNICATION_ORIGIN_VALUE = "TCRM";
    public static final String DELIVERY_RECEIPTS_KEY = "deliveryReceipts";
    public static final String DELIVERY_RECEIPTS_VALUE = "NO";
    public static final String CONTENT_TYPE_KEY = "contentType";
    public static final String CONTENT_TYPE_VALUE = "MESSAGE";
    public static final String MESSAGE_CONTENT_KEY = "messageContent";
    public static final String SUBJECT_CONTENT_KEY = "subject";
    public static final String ATTACH_KEY = "attach";
    public static final String TYPE_CUSTOMER_KEY = "typeCostumer";
    public static final String TYPE_CUSTOMER_VALUE = "9F1AA44D-B90F-E811-80ED-FA163E10DFBE";
    public static final String PROFILE_PARAM = "SMTP_FS_TCRM1,SMS_FS_TCRM1";
    /* Manejo de plantillas HTML */
    public static final String USUARIO_SESION_ATRIBUTO = "%usuario";
    public static final String NOMBRE_USUARIO_SESION_ATRIBUTO = "%nombreUsuario";
    public static final String HORA_MINUTOS_ATRIBUTO = "%horaMinutos";
    public static final String MENSAJE_ATRIBUTO = "%mensaje";
    public static final String MENSAJE_ERROR_ATRIBUTO = "%mensajeError";
    public static final String MODULO_ATRIBUTO = "%modulo";
    public static final String FECHA_ATRIBUTO = "%fecha";
    public static final String NOMBRE_SERVICIO_ATRIBUTO = "%nombreServicio";
    public static final String UUID_ATRIBUTO = "%uuid";
}
