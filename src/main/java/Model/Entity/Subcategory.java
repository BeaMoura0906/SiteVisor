package Model.Entity;

public class Subcategory {
    private int id;
    private String name;
    private Category category;

    /**
     * Constructor for SubCategory
     * @param id
     * @param name
     * @param category
     */
    public Subcategory(int id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public Category getCategory() { return category; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(Category category) { this.category = category; }
}

