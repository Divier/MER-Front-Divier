package co.com.claro.mgl.util.cm;

/**
 *
 * @author bocanegravm
 */
public class CmtAgendamientoMglConstantes {

    //ASIGNAR RECURSO
    public static int ID_MENSAJE_OK = 1;
    public static String DESCRIPCION_MENSAJE_OK = "SE RECIBE INFORMACION COMPLETA DEL TECNICO";
    public static String CODIGO_OK = "0";
    public static String STATUS_OK = "OK";
    public static int ID_MENSAJE_FALTA = 2;
    public static String DESCRIPCION_MENSAJE_FALTA = "INFORMACION INCOMPLETA PARA ACTUALIZAR AGENDA";
    public static String CODIGO_FALLA = "F";
    public static String STATUS_FALTA = "FALLA";
    public static String DESCRIPCION_MENSAJE_NO_TECNICO = "NO HAY INFORMACION DEL TECNICO EN ETA";
    //INICIAR VISITA    
    public static String DESCRIPCION_MENSAJE_OK_IV = "SE RECIBE INFORMACION COMPLETA DEL INICIO DE LA VISITA";
    //SUSPENDER VISITA    
    public static String DESCRIPCION_MENSAJE_OK_SV = "SE RECIBE INFORMACION COMPLETA DE LA SUSPENCION DE LA VISITA";
    //NODONE  
    public static String DESCRIPCION_MENSAJE_OK_NODONE = "SE RECIBE INFORMACION COMPLETA DE LA ACTIVIDAD NO REALIZADA";
    public static String DESCRIPCION_MENSAJE_NO_HAY_RAZON = "NO SE ENCUENTRA CONFIGURADA UNA RAZON PARA ESTE CODIGO EN MGL";
    //HARDCLOSE  
    public static String DESCRIPCION_MENSAJE_OK_HARDCLOSE = "SE RECIBE INFORMACION COMPLETA DEL CIERRE DE LA VT";
    //NO_ENCUENTRA_LA AGENDA
    public static int ID_MENSAJE_FALLA = 3;
    public static String DESCRIPCION_MENSAJE_FALLA_NO_ENCUENTRA = "NO SE ENCUENTRA LA AGENDA EN MGL";
    public static String DESCRIPCION_MENSAJE_FALLA_NO_ACTUALIZA = "OCURRIO UN ERROR AL ACTUALIZAR LA AGENDA EN MGL";
    public static String RESOURCES = "resources/";
    public static String ACTIVITIES = "activities/";
    

}
