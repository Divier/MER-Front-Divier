/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package co.com.claro.mgl.mbeans.vt.migracion.beans.util;

/**
 * Objetivo:
 *
 * Descripción:
 * 
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
public abstract class Pagination {
  private final int pageSize;
  private int page;

  public Pagination(int pageSize) {
    this.pageSize = pageSize;
  }

  public abstract int getItemsCount();

  
  public int getPageFirstItem() {
    return page * pageSize;
  }

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

  public boolean isHasNextPage() {
    return (page + 1) * pageSize + 1 <= getItemsCount();
  }

  public void nextPage() {
    if (isHasNextPage()) {
      page++;
    }
  }

  public boolean isHasPreviousPage() {
    return page > 0;
  }

  public void previousPage() {
    if (isHasPreviousPage()) {
      page--;
    }
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getPage() {
    return page;
  }
  
  public void start() {
    while(isHasPreviousPage()) {
      page--;
    }
  }
  
  public void end() {
    while(isHasNextPage()) {
      page++;
    }
  }

}
