package com.example.sitevisor.Model.Entity;

public class Site {
    private int id;
    private String name;
    private String type;
    private String client;
    private String address;
    private String startDate;
    private String endDate;

    /**
     * Constructor for Site
     * @param id
     * @param name
     * @param type
     * @param client
     * @param address
     * @param startDate
     * @param endDate
     */
    public Site(int id, String name, String type, String client, String address, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.client = client;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getClient() { return client; }
    public String getAddress() { return address; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setClient(String client) { this.client = client; }
    public void setAddress(String address) { this.address = address; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}

