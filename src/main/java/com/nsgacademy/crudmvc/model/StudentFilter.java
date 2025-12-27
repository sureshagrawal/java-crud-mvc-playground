package com.nsgacademy.crudmvc.model;

public class StudentFilter {

    private String search;

    public boolean hasSearch() {
        return search != null && !search.trim().isEmpty();
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
