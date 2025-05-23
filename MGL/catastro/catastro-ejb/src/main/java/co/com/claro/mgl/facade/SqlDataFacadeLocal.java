/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.jpa.entities.SqlData;
import java.util.List;

/**
 *
 * @author User
 */
public interface SqlDataFacadeLocal extends BaseFacadeLocal<SqlData>{

    public List<SqlData> findSqlDataById(String idSqlData);

}
