/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.ParametrosMglDaoImpl;
import co.com.claro.mgl.dtos.CmtParamentrosComplejosDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.BuildSQL;
import java.util.ArrayList;
import java.util.List;

/**
 * Contener los parametros.
 * Contiener los parametros completos de la aplicacion.
 * 
 * @author User.
 * @version 1.0 revision 17/05/2017.
 */
public class ParametrosMglManager {

    ParametrosMglDaoImpl parametrosMglDaoImpl = new ParametrosMglDaoImpl();

    
    
    public  ParametrosMglManager() {
        
    }
    /**
     * Consultar todos los parametros de la aplicacion.
     * Consulta todos los parametros de la aplicacion.
     * 
     * @author User.
     * @version 1.0 revision 17/05/2017.
     * @return La lista de parametros de la aplicacion.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public List<ParametrosMgl> findAll()
            throws ApplicationException {
        List<ParametrosMgl> result;
        result = parametrosMglDaoImpl.findAll();
        return result;
    }

    /**
     * Actualizar los parametros.Actualiza el parametro.
     * 
     * @author User.
     * @version 1.0 revision 17/05/2017.
     * @param parametrosMgl Parametro a actualizar.
     * @return El parametro actualizado.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception Lanza la excepción cuando ocurra un error en a 
     * ejecucion de la sentencia.
     * @throws com.jlcg.db.exept.ExceptionDB
     */
    public ParametrosMgl update(ParametrosMgl parametrosMgl)
            throws ApplicationException, ExceptionDB {
        ParametrosMgl resutl = parametrosMglDaoImpl.update(parametrosMgl);
        BuildSQL.load();
        return resutl;
    }

    /**
     * Crear los parametros.Crea el parametro.
     * 
     * @author User.
     * @version 1.0 revision 17/05/2017.
     * @param parametrosMgl Parametro a crear.
     * @return El parametro creado.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception Lanza la excepción cuando ocurra un error en a 
     * ejecucion de la sentencia.
     * @throws com.jlcg.db.exept.ExceptionDB
     */
    public ParametrosMgl create(ParametrosMgl parametrosMgl)
            throws ApplicationException, ExceptionDB {
        ParametrosMgl resutl = parametrosMglDaoImpl.create(parametrosMgl);
        BuildSQL.load();
        return resutl;
    }

    /**
     * Borrar los parametros.Borra el parametro.
     * 
     * @author User.
     * @version 1.0 revision 17/05/2017.
     * @param parametrosMgl Parametro a borra.
     * @return True cuando el parámetro fue correctamente borrado.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception Lanza la excepción cuando ocurra un error en a 
     * ejecucion de la sentencia.
     * @throws com.jlcg.db.exept.ExceptionDB
     */
    public boolean delete(ParametrosMgl parametrosMgl)
            throws ApplicationException, ExceptionDB {
        boolean resutl = parametrosMglDaoImpl.delete(parametrosMgl);
        BuildSQL.load();
        return resutl;
    }

    public ParametrosMgl findById(ParametrosMgl parametrosMgl)
            throws ApplicationException {
        return null;
    }

    /**
     * Buscar un parametro por el acronico.
     * Busca un parametro por el acronico.
     * 
     * @author User.
     * @version 1.0 revision 17/05/2017.
     * @param acronimo Criterio de busqueda.
     * @return Lista de parametros que correspondan al acronimo dado.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en 
     * a ejecucion de la sentencia.
     */
    public List<ParametrosMgl> findByAcronimo(String acronimo)
            throws ApplicationException {
        return parametrosMglDaoImpl.findByAcronimo(acronimo);
    }
    
    /**
     * Buscar un parametro por el acronico.
     * Busca un parametro por el acronico.
     * 
     * @author becerraarmr
     * @param acronimo Acrónimo a buscar
     * @return null o ParametrosMgl correspondiente
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en 
     * a ejecucion de la sentencia.
     */
    public ParametrosMgl findParametroMgl(String acronimo)
            throws ApplicationException {
        return parametrosMglDaoImpl.findParametroMgl(acronimo);
    }

    /**
     * Busca un parametro complejo por Key. 
     * Permite buscar un parametro
     * complejo y retornar solo la Key buscada.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param acronimo palabra clave acronimo para buscar el parametro complejo
     * @param keyToFind Key buscada dentro del parametro complejo
     * @return parametro complejo buscado por la key
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en 
     * a ejecucion de la sentencia.
     */
    public CmtParamentrosComplejosDto findComplejoByKey(
            String acronimo, String keyToFind)
            throws ApplicationException {
        List<CmtParamentrosComplejosDto> paramComplejoList =
                findComplejo(acronimo);
        if (paramComplejoList != null && !paramComplejoList.isEmpty()){
            for(CmtParamentrosComplejosDto param : paramComplejoList){
                if (param.getKey() != null
                        && !param.getKey().trim().isEmpty()
                        && param.getKey().trim().equalsIgnoreCase(
                        keyToFind)){
                    return param;
                }
            }
        }
        return null;        
    }
    
    /**
     * Busca un parametro complejo por el acronimo. 
     * Permite buscar un parametro complejo y retornar la lista de parametros.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param acronimo palabra clave acronimo para buscar el parametro complejo
     * @return parametro complejo buscado por la key
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en 
     * a ejecucion de la sentencia.
     */
    public List<CmtParamentrosComplejosDto> findComplejo(String acronimo)
            throws ApplicationException {
        CmtParamentrosComplejosDto complejosDto;
        List<CmtParamentrosComplejosDto> complejosDtoList = null;
        List<ParametrosMgl> parametrosList;
        parametrosList = findByAcronimo(acronimo);
        if (parametrosList != null && parametrosList.size() > 0) {
            ParametrosMgl parametrosMgl = parametrosList.get(0);
            if (parametrosMgl != null && parametrosMgl.getParValor() != null) {
                String parametro = parametrosMgl.getParValor().trim();
                if (parametro != null && !parametro.isEmpty()) {
                    if (parametro.contains("|")) {
                        String[] parSplit = parametro.split("\\|");
                        complejosDtoList = new ArrayList<>();
                        for (String cad : parSplit) {
                            if (cad != null && !cad.isEmpty()) {
                                if (cad.contains("=")) {
                                    String[] keyValue = cad.split("=");
                                    complejosDto = new CmtParamentrosComplejosDto();
                                    complejosDto.setValue(cad);
                                    complejosDto.setKey(keyValue[0]);
                                    complejosDto.setValue(keyValue[1]);
                                } else {
                                    complejosDto = new CmtParamentrosComplejosDto();
                                    complejosDto.setValue(cad);
                                }
                                complejosDtoList.add(complejosDto);
                            }

                        }
                    } else {
                        complejosDtoList = new ArrayList<>();
                        complejosDto = new CmtParamentrosComplejosDto();
                        complejosDto.setValue(parametro);
                        complejosDtoList.add(complejosDto);
                    }
                }
            }
        }
        if (complejosDtoList != null && complejosDtoList.isEmpty()) {
            complejosDtoList = null;
        }
        return complejosDtoList;
    }
    
    /**
     * Busca un parametro complejo por el acronimo. Permite buscar un parametro
     * complejo y retornar la lista de parametros.
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param acronimo palabra clave acronimo para buscar el parametro complejo
     * @return parametro complejo buscado por la key
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public List<CmtParamentrosComplejosDto> findComplejoWithComas(String acronimo)
            throws ApplicationException {
        CmtParamentrosComplejosDto complejosDto;
        List<CmtParamentrosComplejosDto> complejosDtoList = null;
        List<ParametrosMgl> parametrosList;
        parametrosList = findByAcronimo(acronimo);
        if (parametrosList != null && parametrosList.size() > 0) {
            ParametrosMgl parametrosMgl = parametrosList.get(0);
            if (parametrosMgl != null && parametrosMgl.getParValor() != null) {
                String parametro = parametrosMgl.getParValor().trim();
                if (parametro != null && !parametro.isEmpty()) {
                    if (parametro.contains("|")) {
                        String[] parSplit = parametro.split("\\|");
                        complejosDtoList = new ArrayList<>();
                        for (String cad : parSplit) {
                            if (cad != null && !cad.isEmpty()) {
                                if (cad.contains("=")) {
                                    String[] keyValue = cad.split("=");
                                    if (keyValue[1].contains(",")) {
                                        String[] comaValue = keyValue[1].split(",");
                                        for (String coma : comaValue) {
                                            if (coma != null && !coma.isEmpty()) {
                                                complejosDto = new CmtParamentrosComplejosDto();
                                                complejosDto.setKey(keyValue[0]);
                                                complejosDto.setValue(coma);
                                                complejosDtoList.add(complejosDto);
                                            }
                                        }
                                    } else {
                                        complejosDto = new CmtParamentrosComplejosDto();
                                        complejosDto.setKey(keyValue[0]);
                                        complejosDto.setValue(keyValue[1]);
                                        complejosDtoList.add(complejosDto);
                                    }

                                } else {
                                    complejosDto = new CmtParamentrosComplejosDto();
                                    complejosDto.setValue(cad);
                                    complejosDtoList.add(complejosDto);
                                }
                            }

                        }
                    } else {
                        complejosDtoList = new ArrayList<>();
                        complejosDto = new CmtParamentrosComplejosDto();
                        complejosDto.setValue(parametro);
                        complejosDtoList.add(complejosDto);
                    }
                }
            }
        }
        if (complejosDtoList != null && complejosDtoList.isEmpty()) {
            complejosDtoList = null;
        }
        return complejosDtoList;
    }
    
    /**
     * valbuenayf Metodo para buscar un parametro por el nombre
     *
     * @param nombre
     * @return
     * @throws ApplicationException
     */
    public ParametrosMgl findByAcronimoName(String nombre)
            throws ApplicationException {
        return parametrosMglDaoImpl.findByAcronimoName(nombre);
    }
   
    /**
     * Buscar lista de parametros por tipo de parametro
     *
     * @author bocanegravm
     * @param tipoParam tipo parametro a buscar
     * @return List<ParametrosMgl>
     * @throws ApplicationException is hay errores en la busqueda en la base de
     * datos
     */
    public List<ParametrosMgl> findByTipoParametro(String tipoParam) throws ApplicationException {
        return parametrosMglDaoImpl.findByTipoParametro(tipoParam);
    }
    
    /**
     * Buscar lista de parametros por tipo de parametro y acronimo
     *
     * @author bocanegravm
     * @param acronimo a buscar
     * @param tipoParam tipo parametro a buscar
     * @return List<ParametrosMgl>
     * @throws ApplicationException is hay errores en la busqueda en la base de
     * datos
     */
    public List<ParametrosMgl> findByAcronimoAndTipoParam(String acronimo, String tipoParam) throws ApplicationException {
        return parametrosMglDaoImpl.findByAcronimoAndTipoParam(acronimo, tipoParam);
    }
}
