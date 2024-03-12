package Model.Entity;

public class Document {
    private int id;
    private String name;
    private String type;
    private String path;
    private int siteId;

    /**
     * Constructor for Document
     * @param id
     * @param name
     * @param type
     * @param path
     * @param siteId
     */
    public Document(int id, String name, String type, String path, int siteId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.path = path;
        this.siteId = siteId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getPath() { return path; }
    public int getSiteId() { return siteId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setPath(String path) { this.path = path; }
    public void setSiteId(int siteId) { this.siteId = siteId; }
}
