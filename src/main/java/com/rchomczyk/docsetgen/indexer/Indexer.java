package com.rchomczyk.docsetgen.indexer;

import com.rchomczyk.docsetgen.javadoc.JavadocEntry;

import java.io.File;
import java.sql.*;

public class Indexer {

    private final Connection connection;

    public Indexer(File targetFileIndex) {
        this.connection = this.establishConnection(targetFileIndex);
    }

    public void configure() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE searchIndex(id INTEGER PRIMARY KEY, name TEXT, type TEXT, path TEXT);");
            statement.executeUpdate("CREATE UNIQUE INDEX anchor ON searchIndex (name, type, path);");
        } catch (SQLException exception) {
            throw new IndexerException("Couldn't configure the sqlite database.", exception);
        }
    }

    public void populate(JavadocEntry entry) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR IGNORE INTO searchIndex(name, type, path) VALUES (?, ?, ?);")) {
            preparedStatement.setString(1, entry.name());
            preparedStatement.setString(2, entry.type().getName());
            preparedStatement.setString(3, entry.path());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new IndexerException("Couldn't populate the sqlite database.", exception);
        }
    }

    private Connection establishConnection(File targetFileIndex) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException exception) {
            throw new IndexerException("Couldn't initialize the sqlite's driver.", exception);
        }

        try {
            return DriverManager.getConnection("jdbc:sqlite:" + targetFileIndex.getAbsolutePath());
        } catch (SQLException exception) {
            throw new IndexerException("Couldn't establish a connection to the sqlite database.", exception);
        }
    }
}
