package co.com.claro.mgl.utils;

import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author ADMIN
 * @param <O>
 * @param <A>
 */
public class UtilsCMAuditoria<O, A> {//A->Auditoria O->Original
    
    public List<AuditoriaDto> construirAuditoria(O o)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<AuditoriaDto> listAudituriaDto = new ArrayList<>();
        Class<?> myEntity = o.getClass();
        boolean hasChange = false;
        Field[] fields = myEntity.getDeclaredFields();
        String nombreMetodoAuditoria = "getListAuditoria";
        Method methodListAuditoria = myEntity.getMethod(nombreMetodoAuditoria);
        List<A> listA = (List<A>) methodListAuditoria.invoke(o);
        Method method = null;
        Method methodAuditoria = null;
        AuditoriaDto audituriaDto = new AuditoriaDto();
        AuditoriaDto audituriaDtoPrimero = new AuditoriaDto();
        A a = traerUltimaAuditoria(listA);
        audituriaDtoPrimero = construirAuditoriaprimera(o, a);
        listAudituriaDto.add(audituriaDtoPrimero);
        if (listA == null) {
            return listAudituriaDto;
        }
        if (a == null) {
            return listAudituriaDto;
        }
        Class<?> myEntityAuditoria = a.getClass();
        for (Field field : fields) {
            String nombreMetodo = "get" + field.getName().substring(0, 1).toUpperCase()
                    + field.getName().substring(1, field.getName().length());
            Object valueActual = null;
            Object valueAnterior = null;
            Transient annotation = field.getAnnotation(Transient.class);
            if (!field.getName().equalsIgnoreCase("fechaCreacion")
                    && !field.getName().equalsIgnoreCase("fechaEdicion")
                    && !field.getName().equalsIgnoreCase("usuarioCreacion")
                    && !field.getName().equalsIgnoreCase("usuarioEdicion")
                    && !field.getName().equalsIgnoreCase("perfilCreacion")
                    && !field.getName().equalsIgnoreCase("perfilEdicion")
                    && !field.getName().equalsIgnoreCase("serialVersionUID")
                    && !field.getName().equalsIgnoreCase("justificacion")
                    && !field.getName().toUpperCase().contains("Auditoria".toUpperCase())
                    && !field.getType().equals(ArrayList.class)
                    && !field.getType().equals(List.class)
                    && annotation == null
                    && !field.getName().toUpperCase().startsWith("_".toUpperCase())) {
                method = myEntity.getMethod(nombreMetodo);
                        methodAuditoria = myEntityAuditoria.getMethod(nombreMetodo);      
                valueActual = method.invoke(o);
                valueAnterior = methodAuditoria.invoke(a);
             
                if (field.getType() == BigDecimal.class) {
                    
                    BigDecimal valorAnteriorTipoDato = new BigDecimal("0");
                    if (valueAnterior != null) {
                        valorAnteriorTipoDato = (BigDecimal) valueAnterior;
                    }
                    
                     BigDecimal valueActualTipoDato = new BigDecimal("0");
                    if (valueActual != null) {
                        valueActualTipoDato = (BigDecimal) valueActual;
                    }
                            
                    if (!valueActualTipoDato.equals(valorAnteriorTipoDato)) {
                        audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                        audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ":  " + valueActualTipoDato.toString() + " ; <BR>");
                        hasChange = true;
                    }
                    continue;
                }
                if (field.getType() == String.class) {
                    
                    String valorAnteriorTipoDato = "";
                    if (valueAnterior != null) {
                        valorAnteriorTipoDato = (String) valueAnterior;
                    } 
                   
                    String valueActualTipoDato = "";
                    if (valueActual != null) {
                        valueActualTipoDato = (String) valueActual;
                    }
                            
                    if (!valueActualTipoDato.equals(valorAnteriorTipoDato)) {
                        audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  " + valorAnteriorTipoDato + " ; <BR>");
                        audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ":  " + valueActualTipoDato + " ; <BR>");
                        hasChange = true;
                    }
                    continue;
                }

                if (field.getType() == Date.class) {
                   
                    Date valorAnteriorTipoDato = null;
                    if (valueAnterior != null) {
                        valorAnteriorTipoDato = (Date) valueAnterior;
                    }
                    
                    Date valueActualTipoDato = null;
                    if (valueActual != null) {
                        valueActualTipoDato = (Date) valueActual;
                    }
                    
                    if (valueActualTipoDato != null && valorAnteriorTipoDato != null) {

                        if (!valueActualTipoDato.equals(valorAnteriorTipoDato)) {
                            audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                            audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ":  " + valueActualTipoDato.toString() + " ; <BR>");
                            hasChange = true;
                        }
                    } else if (valueActualTipoDato != null && valorAnteriorTipoDato == null) {

                        audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  Sin Fecha ; <BR>");
                        audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ":  " + valueActualTipoDato.toString() + " ; <BR>");
                        hasChange = true;
                    } else if (valorAnteriorTipoDato != null && valueActualTipoDato == null) {

                        audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                        audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ": Sin Fecha  ; <BR>");
                        hasChange = true;
                    }
                    continue;
                }
                
                String valorAnteriorTipoDato = "";
                if (valueAnterior != null) {
                    valorAnteriorTipoDato = valueAnterior.toString();
                }
                
                String valueActualTipoDato = "";
                if (valueActual != null) {
                    valueActualTipoDato = valueActual.toString();
                }

                if (!valueActualTipoDato.equals(valorAnteriorTipoDato)) {
                    audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  " + valorAnteriorTipoDato + " ; <BR>");
                    audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ":  " + valueActualTipoDato + " ; <BR>");
                    hasChange = true;
                }

            }

            if (!field.getName().equalsIgnoreCase("serialVersionUID")
                    && !field.getName().toUpperCase().contains("Auditoria".toUpperCase())
                    && !field.getName().toUpperCase().startsWith("_".toUpperCase())
                    && !field.getType().equals(ArrayList.class)
                    && !field.getType().equals(List.class)
                    && (annotation == null || field.getName().equalsIgnoreCase("fechaCreacion") 
                    || field.getName().equalsIgnoreCase("usuarioCreacion")
                    || field.getName().equalsIgnoreCase("fechaEdicion")
                    || field.getName().equalsIgnoreCase("usuarioEdicion")
                    || field.getName().equalsIgnoreCase("justificacion")) 
                    ) {
                method = myEntity.getMethod(nombreMetodo);
                valueActual = method.invoke(o);
            }
            if (field.getName().equalsIgnoreCase("fechaCreacion")) {

                audituriaDto.setFechaCreacion((Date) valueActual);
            }
            if (field.getName().equalsIgnoreCase("usuarioCreacion")) {
                audituriaDto.setUsuarioCreacion((String) valueActual);
            }
            if (field.getName().equalsIgnoreCase("fechaEdicion")) {
                audituriaDto.setFechaModificacion((Date) valueActual);
            }
            if (field.getName().equalsIgnoreCase("usuarioEdicion")) {
                audituriaDto.setUsuarioModificacion((String) valueActual);
            }
            if (field.getName().equalsIgnoreCase("justificacion")) {
                audituriaDto.setJustificacion((String) valueActual);
            }
        }

        if (listA.size() > 0) {
            HashMap<BigDecimal, A> map = new HashMap<BigDecimal, A>();
            List<BigDecimal> listaOrdenadaDeId = ordenarAuditoria(listA, map);

            for (int iterator = 0; iterator < listaOrdenadaDeId.size(); iterator++) {
                if (iterator < (listaOrdenadaDeId.size() - 1)) {
                    AuditoriaDto audituriaDtoAuditoria = CompararAuditoriaRegistroAnterior(map.get(listaOrdenadaDeId.get(iterator + 1)), map.get(listaOrdenadaDeId.get(iterator)), fields);
                    if (audituriaDtoAuditoria != null) {
                        listAudituriaDto.add(audituriaDtoAuditoria);
                    }
                }
            }
        }
        if (hasChange) {
            listAudituriaDto.add(audituriaDto);
        }
        
        //JDHT se ordena por fecha de creacion
        if(listAudituriaDto != null && !listAudituriaDto.isEmpty()){            
              Collections.sort(listAudituriaDto,
                    new Comparator<AuditoriaDto>() {
                @Override
                public int compare(AuditoriaDto f1, AuditoriaDto f2) {
                    return f1.getFechaCreacion().compareTo(f2.getFechaCreacion());
                }
            });
              
            Collections.reverse(listAudituriaDto);  
              
        }
        return listAudituriaDto;
    }

    public AuditoriaDto construirAuditoriaprimera(O o, A a)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?> myEntity = o.getClass();
        Field[] fields = myEntity.getDeclaredFields();
        Method method = null;
        Method methodAuditoria = null;
        AuditoriaDto audituriaDtoPrimero = new AuditoriaDto();
        Class<?> myEntityAuditoria;
        if (a != null) {
            myEntityAuditoria = a.getClass();
        } else {
            myEntityAuditoria = null;
        }
        for (Field field : fields) {
            String nombreMetodo = "get" + field.getName().substring(0, 1).toUpperCase()
                    + field.getName().substring(1, field.getName().length());
            Object valueActual = null;
            Object valueAnterior = null;
            Transient annotation = field.getAnnotation(Transient.class);
            if (!field.getName().equalsIgnoreCase("fechaCreacion")
                    && !field.getName().equalsIgnoreCase("fechaEdicion")
                    && !field.getName().equalsIgnoreCase("usuarioCreacion")
                    && !field.getName().equalsIgnoreCase("usuarioEdicion")
                    && !field.getName().equalsIgnoreCase("perfilCreacion")
                    && !field.getName().equalsIgnoreCase("perfilEdicion")
                    && !field.getName().equalsIgnoreCase("serialVersionUID")
                    && !field.getName().equalsIgnoreCase("justificacion")
                    && !field.getName().toUpperCase().contains("Auditoria".toUpperCase())
                    && !field.getType().equals(ArrayList.class)
                    && !field.getType().equals(List.class)
                    && annotation==null
                    && !field.getName().toUpperCase().startsWith("_".toUpperCase())) {

                method = myEntity.getMethod(nombreMetodo);
                if (myEntityAuditoria != null) {
                        methodAuditoria = myEntityAuditoria.getMethod(nombreMetodo);
                }
                if (methodAuditoria != null) {
                    valueAnterior = methodAuditoria.invoke(a);
                }
                valueActual = method.invoke(o);

                if (valueActual == null && valueAnterior == null) {
                    continue;
                }
                if (field.getType() == BigDecimal.class) {
                    if (valueAnterior != null) {
                        BigDecimal valorAnteriorTipoDato = (BigDecimal) valueAnterior;
                        audituriaDtoPrimero.setDespues(audituriaDtoPrimero.getDespues() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                    } else {
                        if (valueActual != null) {
                            BigDecimal valorAnteriorTipoDato = (BigDecimal) valueActual;
                            audituriaDtoPrimero.setDespues(audituriaDtoPrimero.getDespues() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                        }
                    }
                    continue;
                }
                if (field.getType() == String.class) {
                    if (valueAnterior != null) {
                        String valorAnteriorTipoDato = (String) valueAnterior;
                        audituriaDtoPrimero.setDespues(audituriaDtoPrimero.getDespues() + field.getName() + ":  " + valorAnteriorTipoDato + " ; <BR>");
                    } else {
                        if (valueActual != null) {
                            String valorAnteriorTipoDato = (String) valueActual;
                            audituriaDtoPrimero.setDespues(audituriaDtoPrimero.getDespues() + field.getName() + ":  " + valorAnteriorTipoDato + " ; <BR>");
                        }
                    }
                    continue;
                }

                if (field.getType() == Date.class) {
                    if (valueAnterior != null) {
                        Date valorAnteriorTipoDato = (Date) valueAnterior;
                        audituriaDtoPrimero.setDespues(audituriaDtoPrimero.getDespues() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                    } else {
                        if (valueActual != null) {
                            Date valorAnteriorTipoDato = (Date) valueActual;
                            audituriaDtoPrimero.setDespues(audituriaDtoPrimero.getDespues() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                        }
                    }
                    continue;
                }
                if (valueAnterior != null) {
                    String valorAnteriorTipoDato = valueAnterior.toString();
                    audituriaDtoPrimero.setDespues(audituriaDtoPrimero.getDespues() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                } else {
                    if (valueActual != null) {
                        String valorAnteriorTipoDato = valueActual.toString();
                        audituriaDtoPrimero.setDespues(audituriaDtoPrimero.getDespues() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                    }
                }

            }

            if (!field.getName().equalsIgnoreCase("serialVersionUID")
                    && !field.getName().toUpperCase().contains("Auditoria".toUpperCase())
                    && !field.getName().toUpperCase().startsWith("_".toUpperCase())
                    && !field.getType().equals(ArrayList.class)
                    && !field.getType().equals(List.class)
                    && (annotation == null || field.getName().equalsIgnoreCase("fechaCreacion") 
                    || field.getName().equalsIgnoreCase("usuarioCreacion")
                    || field.getName().equalsIgnoreCase("fechaEdicion")
                    || field.getName().equalsIgnoreCase("usuarioEdicion")
                    || field.getName().equalsIgnoreCase("justificacion"))
                    ) {
                if (myEntityAuditoria != null) {
                        methodAuditoria = myEntityAuditoria.getMethod(nombreMetodo);                 
                    if (methodAuditoria != null) {
                        valueAnterior = methodAuditoria.invoke(a);
                    } else {
                        method = myEntity.getMethod(nombreMetodo);
                        valueAnterior = method.invoke(o);
                    }
                } else {
                    method = myEntity.getMethod(nombreMetodo);
                    valueAnterior = method.invoke(o);
                }
            }
            if (field.getName().equalsIgnoreCase("fechaCreacion")) {
                audituriaDtoPrimero.setFechaCreacion((Date) valueAnterior);
            }
            if (field.getName().equalsIgnoreCase("usuarioCreacion")) {
                audituriaDtoPrimero.setUsuarioCreacion((String) valueAnterior);
            }
            if (field.getName().equalsIgnoreCase("justificacion")) {
                audituriaDtoPrimero.setJustificacion((String) valueAnterior);
            }
            
        }       
        return audituriaDtoPrimero;
    }

    private AuditoriaDto CompararAuditoriaRegistroAnterior(A registroNuevoA, A registroAnteriorA, Field[] fieldsA)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        Class<?> myEntity;
        boolean hasChange = false;
        AuditoriaDto audituriaDto = new AuditoriaDto();

        myEntity = registroNuevoA.getClass();


        Field[] fields = fieldsA;
        Method method;


        for (Field field : fields) {
            String nombreMetodo = "get" + field.getName().substring(0, 1).toUpperCase()
                    + field.getName().substring(1, field.getName().length());
            Object valueActual = null;
            Object valueAnterior;
            Transient annotation = field.getAnnotation(Transient.class);
            if (!field.getName().equalsIgnoreCase("fechaCreacion")
                    && !field.getName().equalsIgnoreCase("fechaEdicion")
                    && !field.getName().equalsIgnoreCase("usuarioCreacion")
                    && !field.getName().equalsIgnoreCase("usuarioEdicion")
                    && !field.getName().equalsIgnoreCase("perfilCreacion")
                    && !field.getName().equalsIgnoreCase("perfilEdicion")
                    && !field.getName().equalsIgnoreCase("serialVersionUID")
                    && !field.getName().equalsIgnoreCase("justificacion")
                    && !field.getType().equals(ArrayList.class)
                    && !field.getType().equals(List.class)
                    && !field.getName().toUpperCase().contains("Auditoria".toUpperCase())
                    && !field.getName().toUpperCase().startsWith("_".toUpperCase())
                    && annotation == null ) {
                method = myEntity.getMethod(nombreMetodo);
                valueActual = method.invoke(registroNuevoA);
                valueAnterior = method.invoke(registroAnteriorA);
                
                if (field.getType() == BigDecimal.class) {
                   
                    BigDecimal valorAnteriorTipoDato = new BigDecimal("0");
                    if (valueAnterior != null) {
                        valorAnteriorTipoDato = (BigDecimal) valueAnterior;
                    }

                    BigDecimal valueActualTipoDato = new BigDecimal("0");
                    if (valueActual != null) {
                        valueActualTipoDato = (BigDecimal) valueActual;
                    }
                   
                    if (!valueActualTipoDato.equals(valorAnteriorTipoDato)) {
                        audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                        audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ":  " + valueActualTipoDato.toString() + " ; <BR>");
                        hasChange = true;
                    }
                    continue;
                }
                if (field.getType() == String.class) {
                     
                    String valorAnteriorTipoDato ="";
                    if(valueAnterior != null){
                     valorAnteriorTipoDato = (String) valueAnterior;    
                    }
                    
                    String valueActualTipoDato ="";
                    if(valueActual != null){
                     valueActualTipoDato = (String) valueActual;    
                    }
                    
                    if (!valueActualTipoDato.equals(valorAnteriorTipoDato)) {
                        audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  " + valorAnteriorTipoDato + " ; <BR>");
                        audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ":  " + valueActualTipoDato + " ; <BR>");
                        hasChange = true;
                    }
                    continue;
                }

                if (field.getType() == Date.class) {
                    
                    Date valorAnteriorTipoDato = null;
                    if (valueAnterior != null) {
                        valorAnteriorTipoDato = (Date) valueAnterior;
                    }

                    Date valueActualTipoDato = null;
                    if (valueActual != null) {
                        valueActualTipoDato = (Date) valueActual;
                    }

                    if (valueActualTipoDato != null && valorAnteriorTipoDato != null) {

                        if (!valueActualTipoDato.equals(valorAnteriorTipoDato)) {
                            audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                            audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ":  " + valueActualTipoDato.toString() + " ; <BR>");
                            hasChange = true;
                        }
                    } else if (valueActualTipoDato != null && valorAnteriorTipoDato == null) {

                        audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  Sin Fecha ; <BR>");
                        audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ":  " + valueActualTipoDato.toString() + " ; <BR>");
                        hasChange = true;
                    } else if (valorAnteriorTipoDato != null && valueActualTipoDato == null) {

                        audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  " + valorAnteriorTipoDato.toString() + " ; <BR>");
                        audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ": Sin Fecha  ; <BR>");
                        hasChange = true;
                    }
                    
                    continue;
                }
                String valorAnteriorTipoDato = "";
                if (valueAnterior != null) {
                    valorAnteriorTipoDato = valueAnterior.toString();
                }

                String valueActualTipoDato = "";
                if (valueActual != null) {
                    valueActualTipoDato = valueActual.toString();
                }

                if (!valueActualTipoDato.equals(valorAnteriorTipoDato)) {
                    audituriaDto.setAntes(audituriaDto.getAntes() + field.getName() + ":  " + valorAnteriorTipoDato + " ; <BR>");
                    audituriaDto.setDespues(audituriaDto.getDespues() + field.getName() + ":  " + valueActualTipoDato + " ; <BR>");
                    hasChange = true;
                }

            }

            if (!field.getName().equalsIgnoreCase("serialVersionUID")
                    && !field.getName().toUpperCase().contains("Auditoria".toUpperCase())
                    && !field.getName().toUpperCase().startsWith("_".toUpperCase())
                    && !field.getType().equals(ArrayList.class)
                    && !field.getType().equals(List.class)   
                    && (annotation == null || field.getName().equalsIgnoreCase("fechaCreacion") 
                    || field.getName().equalsIgnoreCase("usuarioCreacion")
                    || field.getName().equalsIgnoreCase("fechaEdicion")
                    || field.getName().equalsIgnoreCase("usuarioEdicion")
                    || field.getName().equalsIgnoreCase("justificacion"))
                    ) {
                method = myEntity.getMethod(nombreMetodo);
                valueActual = method.invoke(registroNuevoA);
            }
            if (field.getName().equalsIgnoreCase("fechaCreacion")) {
                audituriaDto.setFechaCreacion((Date) valueActual);
            }
            if (field.getName().equalsIgnoreCase("usuarioCreacion")) {
                audituriaDto.setUsuarioCreacion((String) valueActual);
            }
            if (field.getName().equalsIgnoreCase("fechaEdicion")) {
                audituriaDto.setFechaModificacion((Date) valueActual);
            }
            if (field.getName().equalsIgnoreCase("usuarioEdicion")) {
                audituriaDto.setUsuarioModificacion((String) valueActual);
            }
            if (field.getName().equalsIgnoreCase("justificacion")) {
                audituriaDto.setJustificacion((String) valueActual);
            }

        }
        if (!hasChange) {
            audituriaDto = null;
        }

        return audituriaDto;
    }

    private A traerUltimaAuditoria(List<A> listA)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        A returnObj = null;
        Class<?> myEntity = null;
        if (listA.size() > 0) {
            A instaciaA = (A) listA.get(0);
            myEntity = instaciaA.getClass();
            Field[] fields = myEntity.getDeclaredFields();
            Field primaryField = null;
            for (Field field : fields) {
                if (field.isAnnotationPresent(Id.class)) {
                    primaryField = field;
                    break;
                }
            }
            if (primaryField != null) {
                BigDecimal valorMayor = BigDecimal.ZERO;
                for (A regAuditoria : listA) {
                    String nombreMetodo = "get" + primaryField.getName().substring(0, 1).toUpperCase()
                            + primaryField.getName().substring(1, primaryField.getName().length());
                    Method method = myEntity.getMethod(nombreMetodo);
                    BigDecimal value = (BigDecimal) method.invoke(regAuditoria);
                    if (valorMayor.compareTo(value) == -1) {
                        valorMayor = value;
                        returnObj = regAuditoria;
                    }
                }
            }

        }
        return returnObj;
    }

    private List<BigDecimal> ordenarAuditoria(List<A> listA, HashMap<BigDecimal, A> mapA)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (mapA == null) {
            mapA = new HashMap<BigDecimal, A>();
        }
        ArrayList<BigDecimal> listaOrdenadaId = new ArrayList<BigDecimal>();
        Class<?> myEntity = null;
        if (listA.size() > 0) {
            A instaciaA = (A) listA.get(0);
            myEntity = instaciaA.getClass();
            Field[] fields = myEntity.getDeclaredFields();
            Field primaryField = null;
            for (Field field : fields) {
                if (field.isAnnotationPresent(Id.class)) {
                    primaryField = field;
                    break;
                }
            }
            if (primaryField != null) {
                for (A regAuditoria : listA) {
                    String nombreMetodo = "get" + primaryField.getName().substring(0, 1).toUpperCase()
                            + primaryField.getName().substring(1, primaryField.getName().length());
                    Method method = myEntity.getMethod(nombreMetodo);
                    BigDecimal value = (BigDecimal) method.invoke(regAuditoria);
                    listaOrdenadaId.add(value);
                    mapA.put(value, regAuditoria);
                }
            }

            Collections.sort(listaOrdenadaId, new Comparator<BigDecimal>() {
                @Override
                public int compare(BigDecimal a, BigDecimal b) {
                    return a.compareTo(b);
                }
            });

        }
        return listaOrdenadaId;
    }
}
