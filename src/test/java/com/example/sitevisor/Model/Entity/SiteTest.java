package com.example.sitevisor.Model.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SiteTest {

    @Test
    public void testConstructorAndGetters() {
        int id = 1;
        String name = "Example Site";
        String type = "Type";
        String client = "Client";
        String address = "Address";
        String startDate = "2024-01-01";
        String endDate = "2024-12-31";

        Site site = new Site(id, name, type, client, address, startDate, endDate);

        assertEquals(id, site.getId());
        assertEquals(name, site.getName());
        assertEquals(type, site.getType());
        assertEquals(client, site.getClient());
        assertEquals(address, site.getAddress());
        assertEquals(startDate, site.getStartDate());
        assertEquals(endDate, site.getEndDate());
    }

    @Test
    public void testSetters() {
        Site site = new Site(1, "Example Site", "Type", "Client", "Address", "2024-01-01", "2024-12-31");

        // Modify values
        int newId = 2;
        String newName = "New Site Name";
        String newType = "New Type";
        String newClient = "New Client";
        String newAddress = "New Address";
        String newStartDate = "2025-01-01";
        String newEndDate = "2025-12-31";

        site.setId(newId);
        site.setName(newName);
        site.setType(newType);
        site.setClient(newClient);
        site.setAddress(newAddress);
        site.setStartDate(newStartDate);
        site.setEndDate(newEndDate);

        // Check if values have been modified correctly
        assertEquals(newId, site.getId());
        assertEquals(newName, site.getName());
        assertEquals(newType, site.getType());
        assertEquals(newClient, site.getClient());
        assertEquals(newAddress, site.getAddress());
        assertEquals(newStartDate, site.getStartDate());
        assertEquals(newEndDate, site.getEndDate());
    }
}
