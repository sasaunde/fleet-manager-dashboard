package com.fleetmanager.fleetmanager.model;

public class Car {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private int mileage;
    private String fuelLevel;
    private String lastService;
    private String fuelStatus;
    private boolean serviceDue;
    private String maintenance;

    public Car(String licensePlate, String make, String model, int year, int mileage,
              String fuelLevel, String lastService, String fuelStatus,
              boolean serviceDue, String maintenance) {
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.fuelLevel = fuelLevel;
        this.lastService = lastService;
        this.fuelStatus = fuelStatus;
        this.serviceDue = serviceDue;
        this.maintenance = maintenance;
    }

    // Getters
    public String getLicensePlate() { return licensePlate; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public int getMileage() { return mileage; }
    public String getFuelLevel() { return fuelLevel; }
    public String getLastService() { return lastService; }
    public String getFuelStatus() { return fuelStatus; }
    public boolean isServiceDue() { return serviceDue; }
    public String getMaintenance() { return maintenance; }
}
