package co.com.claro.mgl.mbeans.vt.migracion.beans.util;

import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.TipoReporte;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.TipoSolicitud;
import co.com.claro.mgl.mbeans.vt.migracion.login.CookieData;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class JsfUtil {
    
  public static Map<String, BigDecimal> getRegionales(
          List<CmtRegionalRr> lista) {
    Map<String, BigDecimal> maps = new LinkedHashMap();
    maps.put("---", null); //label, value
    
    for (CmtRegionalRr item : lista) {
      String label = item.getNombreRegional();
      BigDecimal value = item.getRegionalRrId();
      maps.put(label, value);
    }
    return maps;
  }

  public static Map<String, BigDecimal> getCiudades(
          List<CmtComunidadRr> lista) {
    Map<String, BigDecimal> maps = new LinkedHashMap();
    maps.put("---", null); //label, value
    for (CmtComunidadRr item : lista) {
      String label = item.getNombreComunidad();
      BigDecimal value = item.getComunidadRrId();
      maps.put(label, value);
    }
    return maps;
  }

    public static Map<String, String> getCmtTipoSolicitudMgl() {
        Map<String, String> maps = new LinkedHashMap();
        maps.put("---", null); //label, value

        TipoSolicitud crearHhpp = TipoSolicitud.VALOR_TIPO_SOL_CREACION_HHPP;
        TipoSolicitud cambioDir = TipoSolicitud.VALOR_TIPO_SOL_CAMBIO_DIR;
        TipoSolicitud cambioEst = TipoSolicitud.VALOR_TIPO_SOL_CAMBIO_ESTRATO;
        TipoSolicitud replanVT = TipoSolicitud.VALOR_TIPO_SOL_REPLANTEAMIENTO;
        TipoSolicitud viabilidad = TipoSolicitud.VALOR_TIPO_SOL_VIABILIDAD_INTERNET;
        TipoSolicitud cambioEstM = TipoSolicitud.VALOR_TIPO_CAMBIO_ESTRATO_MG;
        TipoSolicitud vtEdiCon = TipoSolicitud.VALOR_TIPO_VT_EDIFICIO_CONJUNTO;
        TipoSolicitud vtCasas = TipoSolicitud.VALOR_TIPO_VT_CASAS;
        TipoSolicitud creacionCm = TipoSolicitud.VALOR_TIPO_CREACION_CM;
        TipoSolicitud creacionHhppCm = TipoSolicitud.VALOR_TIPO_CREACION_HHPP_CM;
        TipoSolicitud modEliCm = TipoSolicitud.VALOR_TIPO_MOD_ELI_CM;
        TipoSolicitud reaHhpp = TipoSolicitud.VALOR_TIPO_REACTIVACION_HHPP;
        TipoSolicitud valCober = TipoSolicitud.VALOR_TIPO_SOL_VALIDACION_COBERTURA;
        TipoSolicitud valorEscala = TipoSolicitud.VALOR_TIPO_ESCALAMIENTOS_HHPP;

        maps.put("CAMBIO DIRECCION HHPP", cambioDir.getValor());
        maps.put("CAMBIO ESTRATO", cambioEst.getValor());
        maps.put("CREACION HHPP",crearHhpp.getValor());
        
        maps.put("VT REPLANTEAMIENTO", replanVT.getValor());
        maps.put("VIABILIDAD INTERNET", viabilidad.getValor());
        maps.put("CAMBIO ESTRATO MIG",cambioEstM.getValor());
        
        maps.put("VT EDIFICIO/CONJUNTO",vtEdiCon.getValor());
        maps.put("VT CASAS",vtCasas.getValor());
        maps.put("CREACION CM",creacionCm.getValor());
        maps.put("CREACION HHPP CM",creacionHhppCm.getValor());
        maps.put("MODIFICACION/ELIMINACION CM",modEliCm.getValor());
        maps.put("REACTIVACION HHPP",reaHhpp.getValor());
        maps.put("VALIDACION COBERTURA",valCober.getValor());
        maps.put("ESCALAMIENTOS HHPP",valorEscala.getValor());

        return maps;
    }

  public static Map<String, TipoReporte> getTipoReportes() {
    Map<String, TipoReporte> maps = new LinkedHashMap();
    maps.put("---", null); //label, value
    for (TipoReporte item : TipoReporte.values()) {
      maps.put(item.getValor(), item);
    }
    return maps;
  }

  public static Map<String, String> getParametrosCalles(
          List<ParametrosCalles> lista) {
    Map<String, String> maps = new LinkedHashMap();
    maps.put("---", null); //label, value
    for (ParametrosCalles item : lista) {
      String label = item.getDescripcion();
      String value = item.getIdParametro();
      maps.put(label, value);
    }
    return maps;
  }

  public static SelectItem[] getSelectItems(
          List<?> entities, boolean selectOne) {
    int size = selectOne ? entities.size() + 1 : entities.size();
    SelectItem[] items = new SelectItem[size];
    int i = 0;
    if (selectOne) {
      items[0] = new SelectItem("", "---");
      i++;
    }
    for (Object x : entities) {
      items[i++] = new SelectItem(x, x.toString());
    }
    return items;
  }

  public static void addErrorMessage(Exception ex, String defaultMsg) {
    String msg = ex.getLocalizedMessage();
    if (msg != null && msg.length() > 0) {
      addErrorMessage(msg);
    } else {
      addErrorMessage(defaultMsg);
    }
  }

  public static void addErrorMessages(List<String> messages) {
    for (String message : messages) {
      addErrorMessage(message);
    }
  }

  public static void addErrorMessage(String msg) {
    FacesMessage facesMsg = new FacesMessage(
            FacesMessage.SEVERITY_ERROR, msg, msg);
    FacesContext.getCurrentInstance().addMessage(null, facesMsg);
  }

  public static void addSuccessMessage(String msg) {
    FacesMessage facesMsg = new FacesMessage(
            FacesMessage.SEVERITY_INFO, msg, msg);
    FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
  }

  public static String getRequestParameter(String key) {
    return FacesContext.getCurrentInstance().getExternalContext().
            getRequestParameterMap().get(key);
  }

  public static Object getObjectFromRequestParameter(
          String requestParameterName,
          Converter converter, UIComponent component) {
    String theId = JsfUtil.getRequestParameter(requestParameterName);
    return converter.getAsObject(FacesContext.getCurrentInstance(),
            component, theId);
  }

  public static CookieData getCokieData(String key) {
    return (CookieData) FacesContext.getCurrentInstance().
            getExternalContext().getSessionMap().get(key);
  }

  /**
   * Verificar si es entero
   * <p>
   * Verifica que el valor que llega es un número.
   *
   * @author becerraarmr
   * @param valor valor String a evaluar
   *
   * @return resultado Ineger o null de la validación.
   */
  public static Integer valorInteger(String valor) {
    if (valor != null) {
      char c[] = valor.toCharArray();
      for (char d : c) {
        if (d < '0' || d > '9') {
          return null;
        }
      }
      return Integer.valueOf(valor);
    }
    return null;
  }

  /**
   * Verificar si es BigDecimal
   * <p>
   * Verifica que el valor que llega es un número.
   *
   * @author becerraarmr
   * @param valor valor String a evaluar
   *
   * @return resultado Ineger o null de la validación.
   */
  public static BigDecimal valorBigDecimal(String valor) {
    if (valor != null) {
      if (!valor.isEmpty()) {
        char c[] = valor.toCharArray();
        for (char d : c) {
          if (d < '0' || d > '9') {
            return null;
          }
        }
        return new BigDecimal(valor);
      }
    }
    return null;
  }

}
