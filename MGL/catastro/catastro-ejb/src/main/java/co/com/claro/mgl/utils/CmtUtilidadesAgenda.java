package co.com.claro.mgl.utils;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author bocanegravm
 */
public class CmtUtilidadesAgenda {

    private static final Logger LOGGER = LogManager.getLogger(CmtUtilidadesAgenda.class);
    private static SecretKey keyAES;
    private  static final String algoritmo = "AES";
    private static final int keysize = 32;

    public CmtUtilidadesAgenda() {

    }

    public static String Encriptar(String texto) {

        String secretKey = Constant.CLAVE_ENCRIPTAR; //llave para encriptar datos
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOGGER.error("Error en Encriptar. " + e.getMessage(), e);
        }
        return base64EncryptedString;
    }

    public static String Desencriptar(String textoEncriptado) throws ApplicationException {

        String secretKey = Constant.CLAVE_ENCRIPTAR; //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(CmtUtilidadesAgenda.class) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al desencriptar. EX000: " + ex.getMessage(), ex);
        }
        return base64EncryptedString;
    }

    /**
     * Metodo para encriptar un texto
     *
     * @param String texto
     * @return String texto encriptado
     * @throws InvalidAlgorithmParameterException
     */
    public static String EncriptarAES256(String texto) throws ApplicationException {

        ParametrosMglManager manager = new ParametrosMglManager();
        ParametrosMgl keyPar = manager.findParametroMgl(Constant.KEY_EAF_ORACLE);

        if (keyPar == null) {
            throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.KEY_EAF_ORACLE);
        }

        String secretKey = keyPar.getParValor(); //llave para encriptar datos
        addKey(secretKey);

        String value = "";
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keyAES, ivspec);

            byte[] textobytes = texto.getBytes();
            byte[] cipherbytes = cipher.doFinal(textobytes);
            value = java.util.Base64.getEncoder().encodeToString(cipherbytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException ex) {
            throw new ApplicationException("Error al encriptar. EX000: " + ex.getMessage(), ex);
        }
        // TODO Auto-generated catch block
        return value;
    }

    /**
     * Metodo para desencriptar un texto
     *
     * @param texto Texto encriptado
     * @return String texto desencriptado
     */
    public static String DesencriptarAES256(String texto) throws ApplicationException {
        String str = "";
        try {
            ParametrosMglManager manager = new ParametrosMglManager();
            ParametrosMgl keyPar = manager.findParametroMgl(Constant.KEY_EAF_ORACLE);

            if (keyPar == null) {
                throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.KEY_EAF_ORACLE);
            }

            String secretKey = keyPar.getParValor(); //llave para encriptar datos
            addKey(secretKey);

            byte[] value = java.util.Base64.getDecoder().decode(texto);
            //cipher = Cipher.getInstance( algoritmo );
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //cipher.init( Cipher.DECRYPT_MODE, key );
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keyAES, ivspec);

            byte[] cipherbytes = cipher.doFinal(value);
            str = new String(cipherbytes);
        } catch (InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidAlgorithmParameterException ex) {
            throw new ApplicationException("Error al desencriptar. EX000: " + ex.getMessage(), ex);
        }
        return str;
    }

    public static void addKey(String value) {
        byte[] valuebytes = value.getBytes();
        keyAES = new SecretKeySpec(Arrays.copyOf(valuebytes, keysize), algoritmo);
    }

}
