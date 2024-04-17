package com.example.sitevisor.Model.Manager;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.sitevisor.Model.Entity.Site;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

public class SiteManagerTest {
    private SiteManager siteManager;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeUpdate()).thenReturn(1);

        Manager mockManager = mock(Manager.class);
        when(mockManager.getConnection()).thenReturn(mockConnection);

        Field instanceField = Manager.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, mockManager);

        siteManager = new SiteManager();
    }

    @Test
    public void testGetAllSites() throws SQLException {
        // Simulate two results, then end the loop
        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("Site 1", "Site 2");
        when(mockResultSet.getString("type")).thenReturn("Type 1", "Type 2");
        when(mockResultSet.getString("client")).thenReturn("Client 1", "Client 2");
        when(mockResultSet.getString("address")).thenReturn("Address 1", "Address 2");
        when(mockResultSet.getString("start_date")).thenReturn("2022-01-01", "2022-01-02");
        when(mockResultSet.getString("end_date")).thenReturn("2022-01-03", "2022-01-04");

        List<Site> sites = siteManager.getAllSites();

        assertEquals(2, sites.size());
        assertEquals("Site 1", sites.get(0).getName());
        assertEquals("Site 2", sites.get(1).getName());
    }

    @Test
    public void testGetSiteById() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("name")).thenReturn("Site 1");

        Site site = siteManager.getSiteById(1);

        assertNotNull(site);
        assertEquals("Site 1", site.getName());
    }

    @Test
    public void testInsertSite() throws SQLException {
        Site site = new Site(1, "New Site", "Type", "Client", "Address", "2021-01-01", "2021-12-31");

        boolean result = siteManager.insertSite(site);

        assertTrue(result);
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    public void testUpdateSite() throws SQLException {
        Site site = new Site(1, "Updated Site", "Type", "Client", "Address", "2021-01-01", "2021-12-31");

        boolean result = siteManager.updateSite(site);

        assertTrue(result);
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteSite() throws SQLException {
        boolean result = siteManager.deleteSite(1);

        assertTrue(result);
        verify(mockStatement, times(1)).executeUpdate();
    }

}