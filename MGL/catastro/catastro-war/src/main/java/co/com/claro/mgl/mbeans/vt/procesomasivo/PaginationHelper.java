package co.com.claro.mgl.mbeans.vt.procesomasivo;

import co.com.claro.mgl.error.ApplicationException;
import java.util.List;

/**
 * Para representar el paginador
 * 
 * <p>
 * Representa la estructura del paginador que establece en una lista List<T>
 *
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 * @param <T>
 */
public abstract class PaginationHelper<T> {
  /**
   * Tamaño de la página
   */
  private final int pageSize;
  /**
   * Para contrar las páginas
   */
  private int page;

  /**
   * Crear la instancia
   * <p>
   * Crea una instancia PaginationHelper
   * 
   * @author becerraarmr
   * @param pageSize el tamaño de la página 
   */
  public PaginationHelper(int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * Contar la cantidad de registros
   * 
   * Se cuenta la cantida de registros que se encuentran en la consulta.
   * @author becerraarmr
   * @return un entero con el valor establecido.
   */
  public abstract int getItemsCount();

  /**
   * Crear el listado con la data
   * 
   * Se crea el listado con la data según la paginación establecida
   * @return un lista con la data 
   * @throws co.com.claro.mgl.error.ApplicationException 
   */
  public abstract List<T> createPageData() throws ApplicationException;

  /**
   * Buscar el primer item de la página
   * 
   * Se busca el primer item de la página que está en funcionamiento.
   * @author becerraarmr
   * @return un entero con el valor
   */
  public int getPageFirstItem() {
    return page * pageSize;
  }

  /**
   * Buscar el último valor de la página 
   * 
   * Se busca el último valor de la página que está en funcionamiento
   * @author becerraarmr
   * @return un entero con el valor
   */
  public int getPageLastItem() {
    int i = getPageFirstItem() + pageSize - 1;
    int count = getItemsCount() - 1;
    if (i > count) {
      i = count;
    }
    if (i < 0) {
      i = 0;
    }
    return i;
  }

  /**
   * Buscar si hay más página
   * 
   * Según la página donde se encuentre, establece si hay no una página 
   * siguiente.
   * @author becerraarmr
   * @return un true o un false con el valor correspondiente
   */
  public boolean isHasNextPage() {
    return (page + 1) * pageSize + 1 <= getItemsCount();
  }

  /**
   * Buscar la página siguiente
   * 
   * Solicita aumentar a la página siguiente.
   * @author becerraarmr
   * 
   */
  public void nextPage() {
    if (isHasNextPage()) {
      page++;
    }
  }

  /**
   * Buscar página anterior
   * 
   * Establece si hay página anterior
   * @author becerraarmr
   * @return true si lo hay y false en caso contrario
   */
  public boolean isHasPreviousPage() {
    return page > 0;
  }

  /**
   * Volver a la página anterior
   * Vuelve a la página anterior disminuyendo el atributo page.
   * 
   * @author becerraarmr
   */
  public void previousPage() {
    if (isHasPreviousPage()) {
      page--;
    }
  }

  /**
   * Mostrar el valor del atributo pageSize.
   * 
   * Muestra el valor del atributo pageSize que correponde 
   * al tamaño de la página.
   * 
   * @author becerraarmr
   * 
   * @return un entero con el valor correspondiente.
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Mostrar la página
   * 
   * Muestra el valor que tiene la página en ese momento.
   * @author becerraarmr
   * @return entero con el valor representativo.
   */
  public int getPage() {
    return page;
  }
  
  

}
