package com.example.sitevisor.Model.Entity;

public class Document {
    private int id;
    private String name;
    private String type;
    private String path;
    private Site site;

    /**
     * Constructor for Document
     * @param id
     * @param name
     * @param type
     * @param path
     * @param site
     */
    public Document(int id, String name, String type, String path, Site site) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.path = path;
        this.site = site;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getPath() { return path; }
    public Site getSite() { return site; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setPath(String path) { this.path = path; }
    public void setSite(Site site) { this.site = site; }
    public String toString() { return name + "." + type; }
}
