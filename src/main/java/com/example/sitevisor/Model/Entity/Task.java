package com.example.sitevisor.Model.Entity;

/**
 * Task is an entity that represents a task of a subcategory.
 */
public class Task {
    private int id;
    private String name;
    private String description;
    private Subcategory subcategory;

    /**
     * Constructor for Task
     * @param id
     * @param name
     * @param description
     * @param subcategory
     */
    public Task(int id, String name, String description, Subcategory subcategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subcategory = subcategory;
    }

    /**
     * Getters
     */
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Subcategory getSubcategory() { return subcategory; }

    /**
     * Setters
     */
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setSubcategory(Subcategory subcategory) { this.subcategory = subcategory; }
}
