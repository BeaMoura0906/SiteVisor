package com.example.sitevisor.Model.Manager;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.*;
import java.sql.*;

public class ManagerTest {
    private Manager manager;
    private Connection mockConnection;

    @BeforeEach
    public void setUp() throws Exception {
        mockConnection = mock(Connection.class);

        Field instance = Manager.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);

        Field conn = Manager.class.getDeclaredField("connection");
        conn.setAccessible(true);
        conn.set(Manager.getInstance(), mockConnection);
    }

    @Test
    public void testGetConnection() throws SQLException {
        assertNotNull(Manager.getInstance().getConnection());
        verify(mockConnection, never()).close();
    }

    @Test
    public void testCloseConnection() throws SQLException {
        Manager.getInstance().closeConnection();
        verify(mockConnection).close();
    }
}
