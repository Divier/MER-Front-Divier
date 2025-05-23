/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.components;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jrodriguez
 * @param <T>
 */
public abstract class LazyDataTableModel<T> {

    private List<T> data;
    private List<Integer> pageNavigator = new ArrayList<Integer>();
    private int targetNavigationPage;
    private int currentPage;
    private long totalRecords;
    //@TODO: use a properties file
    public static final int PAGE_SIZE = 15;

    public LazyDataTableModel() {
        currentPage = 1;
        data = new ArrayList<T>();
    }

    public void moveForward() {
        currentPage++;
    }

    public void moveBack() {
        if (currentPage != 1) {
            currentPage--;
        }
    }

    public void moveToPage(final int page) {
        currentPage = page;
    }

    public void moveToFirstPage() {
        currentPage = 1;
    }

    public void moveToLastPage() {
        currentPage = (int) (totalRecords / PAGE_SIZE);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public String getLabelForCurrentPage() {
        return String.format("%d de %d", currentPage, getTotalPages());
    }

    public boolean isContextOnFirstPage() {
        return currentPage == 1;
    }

    public boolean isContextOnLastPage() {
        return currentPage == getTotalPages();
    }

    private int getTotalPages() {
        return (int) (totalRecords / PAGE_SIZE);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(final List<T> data) {
        this.data = data;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;

    }

    public int getTargetNavigationPage() {
        return targetNavigationPage;
    }

    public void setTargetNavigationPage(int targetNavigationPage) {
        this.targetNavigationPage = targetNavigationPage;
    }

    public List<Integer> getPageNavigator() {
        return pageNavigator;
    }

    public void initPageNavigator(final long totalRecords) {
        setTotalRecords(totalRecords);
        pageNavigator = new ArrayList<Integer>();
        for (int i = 1; i <= getTotalPages(); i++) {
            pageNavigator.add(i);
        }
    }
}
