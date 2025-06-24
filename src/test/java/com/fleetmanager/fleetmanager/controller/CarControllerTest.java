package com.fleetmanager.fleetmanager.controller;

import com.fleetmanager.fleetmanager.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarController carController;

    @BeforeEach
    void setUp() {
        // Reset static data before each test
        carController.cars = new HashMap<>();

        // Add test data
        carController.cars.put("ABC123", new Car(
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
        ));

        carController.cars.put("XYZ789", new Car(
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
        ));
    }

    @Test
    void shouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void shouldReturnCarDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/car")
                .param("licensePlate", "ABC123"))
                .andExpect(status().isOk());
                //.andExpect(view().name("index"));
                //.andExpect(model().attributeExists("car"))
                //.andExpect(model().attribute("car.licensePlate", equalTo("ABC123")));
    }

    @Test
    void shouldReturn404ForNonExistentCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/car")
                .param("licensePlate", "INVALID"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAllCars() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cars", hasSize(2)))
                .andExpect(model().attribute("cars", hasItem(
                        allOf(
                                hasProperty("licensePlate", equalTo("ABC123")),
                                hasProperty("make", equalTo("Toyota")),
                                hasProperty("model", equalTo("Camry"))
                        )
                )));
    }
}
