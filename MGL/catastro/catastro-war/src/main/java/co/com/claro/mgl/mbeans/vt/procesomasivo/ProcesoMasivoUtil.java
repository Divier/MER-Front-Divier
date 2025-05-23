/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.procesomasivo;

import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccionMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.data.Geografico;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 * Representar datos de proceso masivo
 * <p>
 * <p>
 * Con el fin de conocer la cabecera del archivo que se va a generar y la data
 * que se va a leer para convertirla en un objeto HhppMgl.
 *
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
class ProcesoMasivoUtil {
    
    private ProcesoMasivoUtil () {}
    
     private static final Logger LOGGER = LogManager.getLogger(ProcesoMasivoUtil.class);

    /**
     * Convertir los parámetros de entrada en un arreglo de objetos
     * <p>
     * Se convierten cada uno de los datos que contiene el parámetro hhppMgl con
     * direccionesMgl y Geografico, pasando cada uno de los datos a un arreglo
     * de objetos.
     *
     * @author becerraarmr
     * @param hhppMgl valor con la data principal
     * @param direccionMgl direccion relacionada con el hhppMgl
     * @param geografico valor relacionado con direccionMgl
     *
     * @return un arreglo de objetos.
     */
    public static Object[] valoresHeadersBasic(HhppMgl hhppMgl,
            DireccionMgl direccionMgl, CmtDireccionDetalladaMgl direccionDetalladaMgl) {
        
        String barrio = "";
        
        if (direccionDetalladaMgl!=null) {
            barrio = direccionDetalladaMgl.getBarrio()
                != null ? direccionDetalladaMgl.getBarrio() : "" ;
        }
        
         String drieccion="";
        if (hhppMgl.getSubDireccionObj()!=null) {
            drieccion=hhppMgl.getSubDireccionObj().getSdiFormatoIgac();
        } else if(hhppMgl.getDireccionObj()!=null){
             drieccion=hhppMgl.getDireccionObj().getDirFormatoIgac();
        } 
        
        Object[] header = {
            hhppMgl.getHhpId(), hhppMgl.getHhpIdrR(),
            drieccion, barrio};
        return header;
    }

    /**
     * Convertir los parámetros de entrada en un arreglo de objetos
     * <p>
     * Se convierten cada uno de los datos que contiene el parámetro hhppMgl con
     * direccionesMgl y Geografico, pasando cada uno de los datos a un arreglo
     * de objetos.
     *
     * @author Jonathan PEña
     * @param hhppMgl valor con la data principal
     * @param direccionMgl direccion relacionada con el hhppMgl
     * @param geografico valor relacionado con direccionMgl
     *
     * @return un arreglo de objetos.
     */
    public static Object[] valoresHeadersGeneral(HhppMgl hhppMgl,
            DireccionMgl direccionMgl, Geografico geografico) {

        String estrato = "";
        
        String nivelSocio = "";
        
        if (hhppMgl.getSubDireccionObj() != null) {
            estrato = hhppMgl.getSubDireccionObj().getSdiEstrato()!=null?
                      hhppMgl.getSubDireccionObj().getSdiEstrato().toString():"";
            
            nivelSocio =  hhppMgl.getSubDireccionObj().getSdiNivelSocioecono()!= null ?
                          hhppMgl.getSubDireccionObj().getSdiNivelSocioecono().toString():"";
            
        } else if(hhppMgl.getDireccionObj()!=null){
            estrato = hhppMgl.getDireccionObj().getDirEstrato() != null
                    ? hhppMgl.getDireccionObj().getDirEstrato().toString() :"";
            
            nivelSocio = hhppMgl.getDireccionObj().getDirNivelSocioecono() != null
                    ?    hhppMgl.getDireccionObj().getDirNivelSocioecono().toString() :"";
            
        } 
        String nodo =hhppMgl.getHhpUltUbicacion()==null? hhppMgl.getNodId().getNodCodigo():hhppMgl.getHhpUltUbicacion();
        
        // @cardenaslb
        //Lista de marcas del hhpp
        MarcasHhppMgl etiqueta = null;
        String nombreEtiqueta = "";
        BigDecimal idEtiquetaHhpp = null;
        if (hhppMgl.getMarcasHhppMgl() != null) {
            etiqueta = hhppMgl.getMarcasHhppMgl();
            if (etiqueta.getMhhId() != null) {
                nombreEtiqueta = etiqueta.getMarId().getMarCodigo();
            }
            if( etiqueta.getMarId() != null){
               idEtiquetaHhpp = etiqueta.getMhhId(); 
            }

        }
       
        Object[] header = {hhppMgl.getHhpComunidad(), hhppMgl.getHhpDivision(),
            hhppMgl.getHhpEstadoUnit(),nivelSocio,
            estrato, hhppMgl.getHhpVendedor(),
            hhppMgl.getHhpCodigoPostal(), hhppMgl.getHhpTipoAcomet(),
            nodo, hhppMgl.getHhpHeadEnd(),
            hhppMgl.getHhpTipo(),
            hhppMgl.getHhpTipoUnidad(), hhppMgl.getHhpTipoCblAcometida(), 
            nombreEtiqueta, "", idEtiquetaHhpp
        };
        return header;
    }
    
    public static Object[] valoresHeadersDetalladoEstrato(
            DireccionMgl direccionMgl, HhppMgl hhppMgl) {
        String estrato;
        String marcaPen;
        String fechaCreacion;
        String fechaUltMod;
                  
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        if (hhppMgl.getSubDireccionObj() != null) {
            estrato = hhppMgl.getSubDireccionObj().getSdiEstrato().toString();
        } else {
            estrato = hhppMgl.getDireccionObj().getDirEstrato().toString();
        }
        
        marcaPen = hhppMgl.getHhppOrigenFicha() != null ? hhppMgl.getHhppOrigenFicha():"";
        
        fechaCreacion = hhppMgl.getFechaCreacion() != null ? format.format(hhppMgl.getFechaCreacion()) : "";
        
        fechaUltMod = hhppMgl.getFechaEdicion() != null ? format.format(hhppMgl.getFechaEdicion()) : "";

        Object[] header = {hhppMgl.getHhpEstadoUnit(), estrato, "","",marcaPen, fechaCreacion, fechaUltMod};
        return header;
    }
    
    public static Object[] valoresHeadersDetalladoTipoVivienda( HhppMgl hhppMgl) {
        
        String tipoVivienda = "";
        String marcaPen = "";
        String fechaCreacion = null;
        String fechaUltMod = null;
                  
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        
        if (hhppMgl != null && hhppMgl.getThhId() != null && !hhppMgl.getThhId().isEmpty()) {
            tipoVivienda = hhppMgl.getThhId();
        }
        
        if (hhppMgl != null) {
            marcaPen = hhppMgl.getHhppOrigenFicha() != null ? hhppMgl.getHhppOrigenFicha() : "";
            fechaCreacion = hhppMgl.getFechaCreacion() != null ? format.format(hhppMgl.getFechaCreacion()) : "";   
            fechaUltMod = hhppMgl.getFechaEdicion() != null ? format.format(hhppMgl.getFechaEdicion()) : "";
        }
       
        
        Object[] header = {tipoVivienda, "", "", marcaPen, fechaCreacion, fechaUltMod};
        return header;
    }

    public static Object[] valoresHeadersDetalladoEstado(HhppMgl hhppMgl) {

        Object[] header = {hhppMgl.getHhpEstadoUnit(), ""};
        return header;
    }

    public static Object[] valoresHeadersDetalladoCobertura(
            HhppMgl hhppMgl) {
        String nodo = "";
        String marcaPen;
        String fechaCreacion;
        String fechaUltMod;
                  
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        
        if (hhppMgl.getHhpUltUbicacion() != null && !hhppMgl.
                getHhpUltUbicacion().isEmpty()) {
            nodo = hhppMgl.getHhpUltUbicacion();
        } else if (hhppMgl.getNodId() != null) {
            nodo = hhppMgl.getNodId().getNodCodigo();
        }
        marcaPen = hhppMgl.getHhppOrigenFicha() != null ? hhppMgl.getHhppOrigenFicha():"";
        fechaCreacion = hhppMgl.getFechaCreacion() != null ? format.format(hhppMgl.getFechaCreacion()) : "";   
        fechaUltMod = hhppMgl.getFechaEdicion() != null ? format.format(hhppMgl.getFechaEdicion()) : "";
       
        Object[] header = {nodo, "", "", marcaPen, fechaCreacion, fechaUltMod};
        return header;
    }
    
     public static Object[] valoresHeadersDetalladoEtiqueta(HhppMgl hhppMgl) {

        String marcaPen = "";
        String fechaCreacion = null;
        String fechaUltMod = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        MarcasHhppMgl etiqueta;
        String nombreEtiqueta = "";
        BigDecimal idEtiquetaHhpp = null;

        if (hhppMgl != null) {
            marcaPen = hhppMgl.getHhppOrigenFicha() != null ? hhppMgl.getHhppOrigenFicha() : "";
            fechaCreacion = hhppMgl.getFechaCreacion() != null ? format.format(hhppMgl.getFechaCreacion()) : "";
            fechaUltMod = hhppMgl.getFechaEdicion() != null ? format.format(hhppMgl.getFechaEdicion()) : "";

            if (hhppMgl.getMarcasHhppMgl() != null) {
                etiqueta = hhppMgl.getMarcasHhppMgl();
                if (etiqueta.getMhhId() != null) {
                    nombreEtiqueta = etiqueta.getMarId().getMarCodigo();
                }
                if (etiqueta.getMarId() != null) {
                    idEtiquetaHhpp = etiqueta.getMhhId();
                }

            }
        }

        Object[] header = {nombreEtiqueta, "", "", idEtiquetaHhpp, marcaPen, fechaCreacion, fechaUltMod};
        return header;
    }

    public static Object[] valoresHeaders(Object[] datos, Object dataIndex) {
        Object[] datos1 = new Object[datos.length + 1];
        int inha = 0;
        for (Object header : datos) {
            datos1[inha] = header;
            inha++;
        }
        datos1[inha] = dataIndex;
        return datos1;
    }

    /**
     * Cargar los nombres de los filtros con que se hace el reporte.
     * <p>
     * Se carga cada uno de los valores que hacen parte del filtro en el que se
     * puede realizar reporte.
     *
     * @author becerraarmr
     * @return el String[] con los valores de los filtros.
     */
    public static String[] filtrosHeaders() {
        String[] filtrosHeaders = {"Tipo Reporte", "Tipo Tecnologia", "Departamento",
            "Ciudad", "Centro Poblado", "Nodo", "Atributo", "ValorAtributo"};
        return filtrosHeaders;
    }

    /**
     * Cargar la cabecera del archivo que se crea al realizar el reporte.
     * <p>
     * Se carga cada uno de los campos que tendrá cada columna como cabecera
     * para poder realizar el archivo de excel.
     *
     * @author becerraarmr
     * @return el String[] correspondiente.
     *
     */
    public static String[] header() {
        String aux = headerBasic();
        return aux.split(",");
    }

    public static String[] headerGeneralSplit() {
        String aux = headerGeneral();
        return aux.split(",");
    }
    
    
    public static String headerBasic() {
        String aux = "hhpId,HhpIdrR,HhpDireccion,Barrio";
        return aux;
    }
    
    public static String headerGeneral() {
        String aux = "HhpComunidad,HhpDivision,"
                + "HhpEstadoUnit,Niv_Socio,Estrato," 
                + "HhpVendedor,HhpCodigoPostal,"
                + "HhpTipoAcomet,NodoId,HhpHeadEnd,"
                + "HhpTipo,HhpTipoUnidad,"
                + "HhpTipoCblAcometida,Etiqueta,Notas,EtiquetaId,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux;
    }
    
    public static String headerGeneralTecnologiaFtth() {
        String aux = "HhpComunidad,HhpDivision,"
                + "HhpEstadoUnit,Niv_Socio,Estrato," 
                + "HhpVendedor,HhpCodigoPostal,"
                + "HhpTipoAcomet,NodoId,HhpHeadEnd,"
                + "HhpTipo,HhpTipoUnidad,"
                + "HhpTipoCblAcometida,Etiqueta,Notas,EtiquetaId,Origen,Fecha_Creación,Fecha_Ultima_Modificación,NAP";
        return aux;
    }
    
    public static String headerDetalladoEstrato() {
        String aux = "HhpEstadoUnit,Estrato,Estrato_Nuevo,Notas,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux;
    }
     public static String headerDetalladoEstado() {
        String aux = "HhpEstadoUnit,HhpEstadoUnit_Nuevo,Notas,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux;
    }
     public static String headerDetalladoCobertura() {
        String aux = "NodoId,NodoId_Nuevo,Notas,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux;
    }
     
     public static String headerDetalladoTipoVivienda() {
        String aux = "HhpTipoUnidad,HhpTipoUnidad_Nuevo,Notas,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux;
    }
     
      public static String headerDetalladoEtiqueta() {
        String aux = "Etiqueta,Etiqueta_Nueva,Notas,Etiqueta_Id,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux;
    }
    
    public static String[] headerInhabilitar() {
        String aux = headerBasic();
        aux += ",Inhabilitar";
        return aux.split(",");
    }
    
     public static String[] fusionHeader(String[] headerEntrada,String headerAgregar) {
        String aux = "";
        
         for (String aux2:headerEntrada) {
             aux+=aux2+",";
         }
        aux += headerAgregar;
        return aux.split(",");
    }

    /**
     * Cargar la cabecera de direcciones
     *
     * Se prepara la cabecera de direcciones según los atributos que tiene el
     * objeto DrDireccionesMgl, exceptuando algunos campos.
     *
     * @author becerraarmr
     *
     * @return el String[] correspondiente.
     */
    public static String[] headerDirecciones() {
        String aux = headerDireccionesGeneral();
        return aux.split(",");
    }

    public static String[] headerDireccionesInhabilitar() {
        String aux = headerDireccionesGeneral();
        aux += "Inhabilitar";
        return aux.split(",");
    }

    public static String headerDireccionesGeneral() {
        Class p = DrDireccionMgl.class;
        Field[] f = p.getDeclaredFields();
        StringBuilder aux = new StringBuilder();
        aux.append("Notas,");
        for (int i = 0; i < f.length; i++) {
            Field field = f[i];
            if ("serialVersionUID".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("Id".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("IdSolicitud".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("Estrato".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("EstadoDirGeo".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("IdDirCatastro".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_primaryKey".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_listener".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_fetchGroup".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_shouldRefreshFetchGroup".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_session".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_cacheKey".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_relationshipInfo".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_href".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_links".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("fechaCreacion".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if("usuarioCreacion".equalsIgnoreCase(field.getName())){
                continue;
            }
            if("perfilCreacion".equalsIgnoreCase(field.getName())){
                continue;
            }
            if("fechaEdicion".equalsIgnoreCase(field.getName())){
                continue;
            }
            if("usuarioEdicion".equalsIgnoreCase(field.getName())){
                continue;
            }
            if("perfilEdicion".equalsIgnoreCase(field.getName())){
                continue;
            }
            if("estadoRegistro".equalsIgnoreCase(field.getName())){
                continue;
            }
            aux.append(field.getName());
            if (i != f.length) {
                aux.append(",");
            }
        }
        aux.append("Origen");
        aux.append(",");
        aux.append("Fecha_Creación");
        aux.append(",");
        aux.append("Fecha_Ultima_Modificación");
        aux.append(",");
        
        return aux.toString();
    }

    /**
     * Cargar la data de HhppMgl
     * <p>
     * Recibe un vector e intenta setearlos a cada uno de los parámetros que
     * corresponden al HhppMgl
     *
     * @author becerraarmr
     * @param data vector a convertir.
     *
     * @return null o el HhppMgl correspondiente.
     */
    public static HhppMgl cargaData(String[] data) {
        if (data == null) {
            return null;//no hay data
        }
        if (data.length != header().length) {
            return null;// no tiene el mismo tamaño
        }
        BigDecimal auxHhpId = getBigDecimal(data[0]);
        if (auxHhpId == null) {
            return null;//no se validó el id del hhpp
        }
        HhppMgl hhppMgl = new HhppMgl();

        hhppMgl.setHhpId(auxHhpId);
        hhppMgl.setHhpIdrR(data[1]);
        hhppMgl.setHhpCalle(data[2]);
        hhppMgl.setHhpPlaca(data[3]);
        hhppMgl.setHhpApart(data[4]);
        hhppMgl.setHhpComunidad(data[8]);
        hhppMgl.setHhpDivision(data[9]);
        hhppMgl.setHhpEstadoUnit(data[10]);
        hhppMgl.setHhpVendedor(data[11]);
        hhppMgl.setHhpCodigoPostal(data[12]);
        hhppMgl.setHhpTipoAcomet(data[13]);
        hhppMgl.setHhpUltUbicacion(data[14]);
        hhppMgl.setHhpHeadEnd(data[15]);
        hhppMgl.setHhpTipo(data[16]);
        hhppMgl.setHhpEdificio(data[17]);
        hhppMgl.setHhpTipoUnidad(data[18]);
        hhppMgl.setHhpTipoCblAcometida(data[19]);

        return hhppMgl;
    }

    /**
     * Convertir el valor a BigDecimal
     * <p>
     * Toma un valor String y trata de convertirlo a BigDecimal
     *
     * @author becerraarmr
     * @param valor parámetro que se revisa para convertir
     *
     * @return un null o el BigDecimal representativo.
     */
    public static BigDecimal getBigDecimal(String valor) {
        try {
            return (valor == null || valor.isEmpty()) ? null : new BigDecimal(valor);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método getBigDecimal : " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * Convertir el valor a Integer
     * <p>
     * Toma un valor String y trata de convertirlo a Integer
     *
     * @author becerraarmr
     * @param valor parámetro que se revisa para convertir
     *
     * @return un null o el Integer representativo.
     */
    public static Integer getInteger(String valor) {
        try {
            return new Integer(valor);
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método getInteger:" + e.getMessage();
            LOGGER.error(msg);
            return null;
        }catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método getInteger:" + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * Verificar si es cabecera
     * <p>
     * Verifica el parametro header y establece si es o no cabecera.
     *
     * @author becerraarmr
     * @param headerIn vector con la data a verificar
     *
     * @return un true o un false según la verificación.
     */
    public static boolean isHeaders(String[] headerIn) {
        if (headerIn == null) {
            return false;// no hay data
        }
        String[] aux;

        if (headerIn.length == header().length) {
            aux = header();
        } else if (headerIn.length == (headerDirecciones().length + header().length)) {
            aux = new String[headerDirecciones().length + header().length];
            int i = 0;
            for (; i < header().length; i++) {
                aux[i] = header()[i];
            }

            for (int j = 0; j < headerDirecciones().length; i++, j++) {
                aux[i] = headerDirecciones()[j];
            }
        } else {
            return false;//no tiene la cabeceras correctas
        }

        for (int i = 0; i < aux.length; i++) {
            if (!aux[i].equals(headerIn[i])) {
                return false;//Columna no válida
            }
        }
        return true;//Validada correctamente
    }

    /**
     * Cargar el SelectItem Se carga el arreglo SelectItem con los datos
     * correspondientes.
     *
     * @author becerraarmr
     * @param entities lista de entidades
     * @param selectOne si puede o no ingresar dato nulo.
     *
     * @return un SelectItem[]
     */
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
            if (x instanceof CmtBasicaMgl) {
                CmtBasicaMgl aux = (CmtBasicaMgl) x;
                items[i++] = new SelectItem(aux.getBasicaId() + "", aux.getNombreBasica());
            } else if (x instanceof GeograficoPoliticoMgl) {
                GeograficoPoliticoMgl aux = (GeograficoPoliticoMgl) x;
                items[i++] = new SelectItem(aux.getGpoId() + "", aux.getGpoNombre());
            } else if (x instanceof NodoMgl) {
                NodoMgl aux = (NodoMgl) x;
                items[i++] = new SelectItem(aux.getNodId(), aux.getNodCodigo());
            } else if (x instanceof RrRegionales) {
                RrRegionales aux = (RrRegionales) x;
                items[i++] = new SelectItem(aux.getCodigo(), aux.getNombre());
            } else if (x instanceof RrCiudades) {
                RrCiudades aux = (RrCiudades) x;
                items[i++] = new SelectItem(aux.getCodigo(), aux.getNombre());
            } else if (x instanceof AddressSuggested) {
                AddressSuggested aux = (AddressSuggested) x;
                items[i++] = new SelectItem(aux.getNeighborhood(), aux.getNeighborhood());
            } else if (x instanceof TipoHhppMgl) {
                TipoHhppMgl aux = (TipoHhppMgl) x;
                items[i++] = new SelectItem(aux.getThhID(), aux.getThhID() + " - " + aux.getThhValor());
            }else if (x instanceof MarcasMgl) {
                MarcasMgl aux = (MarcasMgl) x;
                items[i++] = new SelectItem(aux.getMarId(),  aux.getMarNombre());
            }
        }
        return items;
    }

}
