package co.com.claro.mgl.mbeans.vt.migracion.beans;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.mbeans.vt.migracion.beans.util.JsfUtil;
import co.com.claro.mgl.mbeans.vt.migracion.beans.util.PaginationDataModel;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

public class ParametrosCallesController implements Serializable {

  /**
   * Para llevar seguimientos de algún error
   */
  private static final Logger LOGGER = LogManager.getLogger(ParametrosCallesController.class.getName());
  /**
   * Instancia seleccionada para Editar o para crear
   */
  private ParametrosCalles instancia;
  /**
   * Listado de registros encontrados en la base de datos
   */
  private DataModel items = null;
  /**
   * Facade para realizar la busqueda de datos
   */
  @EJB
  private ParametrosCallesFacadeLocal ejbParametrosCalleFacade;
  /**
   * Paginador del listado encontrado
   */
  private PaginationDataModel pagination;
  /**
   * Número del Item seleccionado
   */
  private int selectedItemIndex;
  /**
   * Tipo seleccionador para la parametrización de la consulta
   */
  private String tipoSeleccionado;
  /**
   * Tamaño del paginador
   */
  private int tamPagination = 10;
  /**
   * Un switch para determinar si puede o no crear un registro.
   */
  private boolean createTipo;

  /**
   * Crear la instancia
   * <p>
   * Crea una instancia
   *
   * @author becerraarmr
   */
  public ParametrosCallesController() {
  }

  /**
   * Buscar Instancia Seleccionada
   * <p>
   * Busca la instancia seleccionada
   *
   * @author becerraarmr
   *
   * @return la instancia nueva o la que está en uso.
   */
  public ParametrosCalles getSelected() {
    if (instancia == null) {
      instancia = new ParametrosCalles();
      selectedItemIndex = -1;
    }
    return instancia;
  }

  /**
   * Intermediar con la base de datos
   * <p>
   * Es el encargado de intermediar con la base de datos para los acciones de
   * consulta y de registro necesarias.
   *
   * @author becerraarmr
   *
   * @return ParametrosCallesFacadeLocal
   */
  private ParametrosCallesFacadeLocal getFacade() {
    return ejbParametrosCalleFacade;
  }

  /**
   * Buscar el paginador correspondiente
   * <p>
   * Busca el paginador correspondiente.
   *
   * @author becerraarmr
   *
   * @return PaginationThread correspondiente
   */
  public PaginationDataModel getPagination() {
    if (pagination == null) {
      pagination = new PaginationDataModel(tamPagination) {
        @Override
        public int getItemsCount() {
          //Contar según un parámetro
          try {
            return getFacade().countByTipo(tipoSeleccionado);
          } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new Error(e.getMessage());
          } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new Error(e.getMessage());
          }
        }

        @Override
        public DataModel createPageData() {
          //Buscar por rango según un parámetro
          try {
            return new ListDataModel(getFacade().
                    findRangeByIdTipo(new int[]{getPageFirstItem(),
              getPageLastItem()}, tipoSeleccionado));
          } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new Error(e.getMessage());
          }catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new Error(e.getMessage());
          }
        }
      };
    }
    return pagination;
  }

  /**
   * Preparar para listar
   * <p>
   * Prepara el model para recrear la lista y muestra la página encarga de
   * de realizar la visualización de los datos.
   *
   * @author becerraarmr
   *
   * @return "List"
   */
  public String prepareList() {
    recreateModel();
    return "/view/MGL/VT/Migracion/Parametros/Lista?faces-redirect=true";
  }

  /**
   * Preparar para crear un registro
   * <p>
   * Prepara las variables para crear un registro en la base de datos. En este
   * caso prepara para crear un tipo.
   *
   * @author becerraarmr
   *
   * @return "Create"
   */
  public String prepareCreate() {
    return prepareCreate(true);
  }

  /**
   * Preparar para crear un registro
   * <p>
   * Prepara las variables para crear un registro en la base de datos. En este
   * caso prepara para crear un dato.
   *
   * @author becerraarmr
   *
   * @return "Create"
   */
  public String prepareCreateDato() {
    return prepareCreate(false);
  }

  /**
   * Preparar para crear un registro de forma general
   * <p>
   * Prepara las variables para crear un registro en la base de datos teniendo
   * en cuenta el tipo que va a crear.
   *
   * @author becerraarmr
   * @param createTipo
   *
   * @return "Create"
   */
  private String prepareCreate(boolean createTipo) {
    this.createTipo = createTipo;
    instancia = new ParametrosCalles();
    selectedItemIndex = -1;
    return "Crear";
  }

  /**
   * Crear el registro en la base de datos
   * <p>
   * Crea el registro en la base de datos informando del resultado de la
   * solicitud de registro y prepara las variables para registrar de nuevo
   *
   * @author becerraarmr
   *
   * @return null si no puede registrar el dato en la base de datos y "Create"
   *         si puede registrarlo bien.
   *
   */
  public String create() {
    try {
      if (instancia != null) {
        ParametrosCalles pc = getFacade().find(instancia.getIdParametro());
        if (pc == null) {
          getFacade().create(instancia);
          JsfUtil.addSuccessMessage("Proceso Exitoso");
          return prepareCreate(this.createTipo);
        } else {
          JsfUtil.addErrorMessage("No se puede realizar su petición, porque hay "
                  + "un registro con el identificador "+instancia.getIdParametro());
        }
      }
    } catch (ApplicationException ex) {
		String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			LOGGER.error(msg);
      JsfUtil.addErrorMessage(ex, "Proceso no a sido Exitoso");
    }catch (Exception ex) {
		String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			LOGGER.error(msg);
      JsfUtil.addErrorMessage(ex, "Proceso no a sido Exitoso");
    }
	
    return "";
  }

  /**
   * Preparar para editar un registro seleccionado de la tabla.
   * <p>
   * Captura el registro que se va a editar o actualizar estable cual es el
   * indice seleccionado.
   *
   * @author becerraarmr
   *
   * @return "Edit"
   */
  public String prepareEdit() {
    instancia = (ParametrosCalles) getItems().getRowData();
    selectedItemIndex = pagination.getPageFirstItem()
            + getItems().getRowIndex();
    return "Editar";
  }

  /**
   * Actualizar registro en la base de datos
   * <p>
   * Actualiza un registro en la base de datos para nuestro caso solo
   * se actualiza la descripción.
   *
   * @author becerraarmr
   *
   * @return "List"
   */
  public String update() {
    try {
      getFacade().edit(instancia);
      JsfUtil.addSuccessMessage("Registro fué correctamente actualizado!");
      return "Lista";
    } catch (Exception e) {
		String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
      JsfUtil.addErrorMessage("Registro no se pudo actualizar");
      return null;
    }
  }

  /**
   * Preparar el escenario para eliminar
   * <p>
   * Prepara el escenarioa para eliminar un registro del escenario.
   *
   * @author becerraarmr
   *
   * @return "List"
   */
  public String destroy() {
    instancia = (ParametrosCalles) getItems().getRowData();
    selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    performDestroy();
    recreatePagination();
    recreateModel();
    return "Lista";
  }

  /**
   * Eliminar el registro del modelo y recrear el modelo
   * <p>
   * Elimina el registro del model y actualiza para visualizar en pantalla
   *
   * @return
   */
  public String destroyAndView() {
    performDestroy();
    recreateModel();
    updateCurrentItem();
    recreateModel();
    return "Lista";
  }

  /**
   * Eliminar registro de la base de datos
   * <p>
   * Elimina el registro de la base de datos
   *
   * @author becerraarmr
   *
   */
  private void performDestroy() {
    try {
      getFacade().remove(instancia);
      JsfUtil.addSuccessMessage("Registro eliminado de forma correcta");
    } catch (Exception e) {
		String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
      JsfUtil.addSuccessMessage("Registro no se pudo eliminar de forma correcta");
    }
  }

  /**
   * Actualizar el item seleccionado
   * <p>
   * Actualiza el item seleccionado con el fin de mantener seguimiento del
   * indice que lo representa y del valor.
   *
   * @author becerraarmr
   */
  private void updateCurrentItem() {
      
    int count = 1;
    if (selectedItemIndex >= count) {
      // selected index cannot be bigger than number of items:
      selectedItemIndex = count - 1;
      // go to previous page if last page disappeared:
      if (pagination.getPageFirstItem() >= count) {
        pagination.previousPage();
      }
    }
    if (selectedItemIndex >= 0) {
      try {
        instancia = getFacade().
                findRangeByIdTipo(new int[]{selectedItemIndex, selectedItemIndex
          + 1}, tipoSeleccionado).get(0);
      } catch (ApplicationException e) {
        String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        throw new Error(e.getMessage());
      }catch (Exception e) {
        String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        throw new Error(e.getMessage());
      }
    }
  }

  /**
   * Mostrar el items de registros cargados
   * <p>
   * Muestra el ítem de registros cargados según sean los cargados
   *
   * @author becerraarmr
   *
   * @return DataModel con los registros
   */
  public DataModel getItems() {
    if (items == null) {
      items = getPagination().createPageData();
    }
    return items;
  }

  /**
   * Recrear el Modelo
   * <p>
   * Se pone en null los items del modelo para que el paginador invoque de nuevo
   * la busqueda de datos.
   *
   * @author becerraarmr
   */
  private void recreateModel() {
    items = null;
  }

  /**
   * Recrear la paginación
   * <p>
   * Se pone en null la paginación para poder realizar la carga del paginador
   * y sus respectivos métodos.
   *
   * @author becerraarmr
   */
  private void recreatePagination() {
    pagination = null;
  }

  /**
   * Consultar los tipos a mostrar para los filtros
   * <p>
   * Se realiza la consulta con el fin de encontrar todos los tipos.
   *
   * @return un Map con los datos encontrados.
   *
   * @throws ApplicationException si hay algún error en la consulta con la base
   *                              de datos.
   */
  public Map<String, String> getTipos() throws ApplicationException {
    List<String> list = ejbParametrosCalleFacade.findAllGroupByTipo();
    Map<String, String> tipos = new LinkedHashMap();
    tipos.put("", ""); //label, value
    for (String item : list) {
      tipos.put(item, item);
    }
    return tipos;
  }

  /**
   * Detectar el tipo seleccionado
   * <p>
   * Se captura el dato seleccionado y se recrea el modelo para poder realizar
   * la carga de nuevo.
   *
   * @author becerraarmr
   *
   * @param event evento ocurrido.
   */
  public void tipoCodeChanged(ValueChangeEvent event) {
    this.tipoSeleccionado = event.getNewValue().toString();
    recreateModel();
  }

  /**
   * Buscar el siguiente página para mostrar
   * <p>
   * Busca la página siguiente con los datos correspondientes.
   *
   * @author becerraarmr
   *
   * @return "List"
   */
  public String next() {
    getPagination().nextPage();
    recreateModel();
    return "Lista";
  }

  /**
   * Buscar la página anterior con los registros
   * <p>
   * Busca la página anterior con los registros correspondientes.
   *
   * @author becerraarmr
   *
   * @return "List"
   */
  public String previous() {
    getPagination().previousPage();
    recreateModel();
    return "Lista";
  }

  /**
   * Busca el inicio de la página con los registros correspondientes
   * <p>
   * Buscar el inicio de la página con los registros.
   *
   * @author becerraarmr
   *
   * @return "List"
   */
  public String start() {
    getPagination().start();
    recreateModel();
    return "Lista";
  }

  /**
   * Busca el final de la página
   * <p>
   * Busca la última página que corresponde al paginador.
   *
   * @author becerraarmr
   *
   * @return "List"
   */
  public String end() {
    getPagination().end();
    recreateModel();
    return "Lista";
  }

  /**
   * Buscar el valor del atributo.
   * <p>
   * Busca el valor del atributo correspondiente.
   *
   * @author becerraarmr
   *
   * @return el valor que representa el tipoSeleccionado
   */
  public String getTipoSeleccionado() {
    return tipoSeleccionado;
  }

  /**
   * Actualizar el valor del atributo.
   * <p>
   * Actualiza el valor del atributo correspondiente.
   *
   * @author becerraarmr
   *
   * @param tipoSeleccionado actualizar
   */
  public void setTipoSeleccionado(String tipoSeleccionado) {
    this.tipoSeleccionado = tipoSeleccionado;
  }

  /**
   * Buscar el valor del atributo.
   * <p>
   * Busca el valor del atributo correspondiente.
   *
   * @author becerraarmr
   *
   * @return el valor que representa el atributo
   */
  public int getTamPagination() {
    return tamPagination;
  }

  /**
   * Actualizar el valor del atributo.
   * <p>
   * Actualiza el valor del atributo correspondiente.
   *
   * @author becerraarmr
   *
   * @param tamPagination valor actualizar
   */
  public void setTamPagination(int tamPagination) {
    this.tamPagination = tamPagination;
  }

  /**
   * Recargar la paginación
   * <p>
   * Se recarga la visalización de la página con el tamaño correspondiente
   *
   * @author becerraarmr
   *
   * @return "List"
   */
  public String recargarPagination() {
    recreatePagination();
    recreateModel();
    return "Lista";
  }

  /**
   * Conocer el valor del createTipo
   * <p>
   * Conoce el valor del createTipo con el valor correspondiente.
   *
   * @author becerraarmr
   * @return true para crear tipo y false para crear dato *
   */
  public boolean isCreateTipo() {
    return createTipo;
  }

}
