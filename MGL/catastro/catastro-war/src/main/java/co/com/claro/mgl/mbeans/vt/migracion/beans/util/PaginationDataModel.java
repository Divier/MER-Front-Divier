package co.com.claro.mgl.mbeans.vt.migracion.beans.util;

import javax.faces.model.DataModel;

public abstract class PaginationDataModel<T> extends Pagination{

  public PaginationDataModel(int pageSize) {
    super(pageSize);
  }
  public abstract DataModel createPageData();
}
