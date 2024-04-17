package com.example.sitevisor.Model.Manager;

import com.example.sitevisor.Model.Entity.Document;
import com.example.sitevisor.Model.Entity.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentManager {

    private Connection connection;

    public DocumentManager() {
        // Get a connection instance from the Manager class
        this.connection = Manager.getInstance().getConnection();
    }

    /**
     * Retrieves all documents from a specific site
     *
     * @param site the site for which to retrieve documents
     * @return a list of Document objects representing the documents
     */
    public List<Document> getAllDocumentsBySite(Site site) {
        List<Document> documents = new ArrayList<>();
        String query = "SELECT * FROM documents WHERE site_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, site.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String path = resultSet.getString("path");
                Document document = new Document(id, name, type, path, site);
                documents.add(document);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return documents;
    }

    /**
     * Verifies if a document with the same name and site already exists
     *
     * @param name the name of the document
     * @param site the site which the document is associated to
     * @return
     */
    public boolean getDocumentByNameAndSite(String name, Site site) {
        String query = "SELECT * FROM documents WHERE name = ? AND site_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, site.getId());
            ResultSet resultSet = statement.executeQuery();
            // True if there is one document with the same name and associated to the same site
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts a new document into the database
     *
     * @param document the document to be inserted
     * @return a boolean value indicating whether the document was inserted successfully
     */
    public boolean insertDocument(Document document) {
        boolean isInserted = false;
        String query = "INSERT INTO documents (name, type, path, site_id) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, document.getName());
            statement.setString(2, document.getType());
            statement.setString(3, document.getPath());
            statement.setInt(4, document.getSite().getId());
            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                isInserted = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isInserted;
    }

    /**
     * Deletes a document from the database
     *
     * @param documentId the id of the document to be deleted
     * @return a boolean value indicating whether the document was deleted successfully
     */
    public boolean deleteDocument(int documentId) {
        boolean isDeleted = false;
        String query = "DELETE FROM documents WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, documentId);
            statement.executeUpdate();
            if (statement.getUpdateCount() > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isDeleted;
    }

}
