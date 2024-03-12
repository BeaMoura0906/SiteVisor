package Model.Entity;

public class Task {
    private int id;
    private String name;
    private String description;
    private int subcategoryId;

    /**
     * Constructor for Task
     * @param id
     * @param name
     * @param description
     * @param subcategoryId
     */
    public Task(int id, String name, String description, int subcategoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subcategoryId = subcategoryId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getSubcategoryId() { return subcategoryId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setSubcategoryId(int subcategoryId) { this.subcategoryId = subcategoryId; }
}
