package co.com.claro.mgl.mbeans.vt.migracion.beans.util;

import java.util.List;

public abstract class PaginationList<T> extends Pagination{

  public PaginationList(int pageSize) {
    super(pageSize);
  }
  public abstract List<T> createPageData();
}
