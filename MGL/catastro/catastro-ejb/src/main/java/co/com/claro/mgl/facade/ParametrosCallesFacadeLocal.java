package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import java.util.List;

/**
 *
 * @author Parzifal de León
 */
public interface ParametrosCallesFacadeLocal extends BaseFacadeLocal<ParametrosCalles> {

  public List<ParametrosCalles> findByTipo(String tipo) throws ApplicationException;

  public List<ParametrosCalles> findByTipoPaginacion(int page, int maxResult, String tipo) throws ApplicationException;

    public int countByTipo(String tipo) throws ApplicationException;

  public List<ParametrosCalles> findRange(int[] i);

  public List<ParametrosCalles> findRangeByIdTipo(int[] i, String valor)
          throws ApplicationException;

  public ParametrosCalles edit(ParametrosCalles current);

  public boolean remove(ParametrosCalles current);

  public ParametrosCalles find(String id) throws ApplicationException;

  public List<String> findAllGroupByTipo() throws ApplicationException;
  
}
