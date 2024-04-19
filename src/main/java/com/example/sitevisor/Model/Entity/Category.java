package com.example.sitevisor.Model.Entity;

/**
 * Category is an entity that has an id, name and a reference to site
 */
public class Category {

    /**
     * Properties
     */
    private int id;
    private String name;
    private Site site;

    /**
     * Constructor for Category
     * @param id
     * @param name
     * @param site
     */
    public Category(int id, String name, Site site) {
        this.id = id;
        this.name = name;
        this.site = site;
    }

    /**
     * Getters
     */
    public int getId() { return id; }
    public String getName() { return name; }
    public Site getSite() { return site; }

    /**
     * Setters
     */
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSite(Site site) { this.site = site; }
}
