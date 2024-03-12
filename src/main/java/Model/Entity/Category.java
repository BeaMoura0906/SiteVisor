package Model.Entity;

public class Category {
    private int id;
    private String name;
    private int siteId;

    /**
     * Constructor for Category
     * @param id
     * @param name
     * @param siteId
     */
    public Category(int id, String name, int siteId) {
        this.id = id;
        this.name = name;
        this.siteId = siteId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getSiteId() { return siteId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSiteId(int siteId) { this.siteId = siteId; }
}
