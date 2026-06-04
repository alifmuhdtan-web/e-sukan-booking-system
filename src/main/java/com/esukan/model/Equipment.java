package com.esukan.model;

public class Equipment {
    private int id;
    private String name;
    private String type;
    private int quantity;
    private String status;

    
    
    public Equipment() {}

    
    public Equipment(int id, String name, String type, int quantity, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.status = status;
    }

    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
