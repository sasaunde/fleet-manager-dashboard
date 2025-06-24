package com.fleetmanager.fleetmanager.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    @Test
    void testCarGetters() {
        com.fleetmanager.fleetmanager.model.Car car = new com.fleetmanager.fleetmanager.model.Car(
            "ABC123",
            "Toyota",
            "Camry",
            2022,
            12500,
            "25",
            "2025-06-10",
            "Low",
            false,
            "None"
        );

        assertEquals("ABC123", car.getLicensePlate());
        assertEquals("Toyota", car.getMake());
        assertEquals("Camry", car.getModel());
        assertEquals(2022, car.getYear());
        assertEquals(12500, car.getMileage());
        assertEquals("25", car.getFuelLevel());
        assertEquals("2025-06-10", car.getLastService());
        assertEquals("Low", car.getFuelStatus());
        assertFalse(car.isServiceDue());
        assertEquals("None", car.getMaintenance());
    }

    @Test
    void testCarConstructor() {
        com.fleetmanager.fleetmanager.model.Car car = new com.fleetmanager.fleetmanager.model.Car(
            "XYZ789",
            "Honda",
            "Civic",
            2023,
            5800,
            "75",
            "2025-05-20",
            "Good",
            true,
            "Brakes"
        );

        assertEquals("XYZ789", car.getLicensePlate());
        assertEquals("Honda", car.getMake());
        assertEquals("Civic", car.getModel());
        assertEquals(2023, car.getYear());
        assertEquals(5800, car.getMileage());
        assertEquals("75", car.getFuelLevel());
        assertEquals("2025-05-20", car.getLastService());
        assertEquals("Good", car.getFuelStatus());
        assertTrue(car.isServiceDue());
        assertEquals("Brakes", car.getMaintenance());
    }
}
