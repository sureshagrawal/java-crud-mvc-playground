package com.nsgacademy.crudmvc.model;

public class StudentFilter {

    private String search;
    private String sortBy = "id";   // default
    private String sortDir = "asc"; // default

    public boolean hasSearch() {
        return search != null && !search.trim().isEmpty();
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        // allow only safe columns
        if (sortBy == null) return;

        switch (sortBy) {
            case "id":
            case "name":
            case "email":
            case "mobile":
                this.sortBy = sortBy;
                break;
            default:
                this.sortBy = "id";
        }
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        if ("desc".equalsIgnoreCase(sortDir)) {
            this.sortDir = "desc";
        } else {
            this.sortDir = "asc";
        }
    }
}
