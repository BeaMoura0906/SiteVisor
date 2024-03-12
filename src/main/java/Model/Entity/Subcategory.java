package Model.Entity;

public class Subcategory {
    private int id;
    private String name;
    private int categoryId;

    /**
     * Constructor for SubCategory
     * @param id
     * @param name
     * @param categoryId
     */
    public Subcategory(int id, String name, int categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCategoryId() { return categoryId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
}

