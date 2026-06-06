package com.esukan.model; // Sesuaikan mengikut nama package asal projek korang

public class Facility {
    private int id;
    private String name;
    private String type; // Contoh: PC Room, Console Zone, Tournament Stage
    private String description;
    private double pricePerHour;
    private String status; // Contoh: Available, Maintenance, Booked

    // Constructor Kosong
    public Facility() {}

    // Constructor Penuh
    public Facility(int id, String name, String type, String description, double pricePerHour, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.pricePerHour = pricePerHour;
        this.status = status;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}