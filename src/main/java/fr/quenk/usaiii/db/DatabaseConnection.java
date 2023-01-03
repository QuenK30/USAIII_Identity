package fr.quenk.usaiii.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final DatabaseCredentials credentials;
    private Connection connection;

    public DatabaseConnection(DatabaseCredentials credentials) {
        this.credentials = credentials;
        this.connect();
    }

    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(credentials.toURI(), credentials.getUsername(), credentials.getPassword());
            System.out.println("Identity > Connected to database");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws SQLException {
        if (this.connection != null) {
                this.connection.close();
                System.out.println("IdentityDB > Connection closed");
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.connection != null) {
            if (!this.connection.isClosed()) {
                return this.connection;
            }
        }
        connect();
        return this.connection;
    }
    
}
