package com.kis.model.system;

import java.util.List;

/**
 * Created by yks on 15-10-9.
 */
public class Page<T> {
    private List<T> list;
    private int size=20;
    private int currentPage=1;
    private long totals;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotals() {
        return totals;
    }

    public void setTotals(long totals) {
        this.totals = totals;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
