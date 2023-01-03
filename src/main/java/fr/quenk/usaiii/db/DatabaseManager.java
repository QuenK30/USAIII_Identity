package fr.quenk.usaiii.db;

import fr.quenk.usaiii.IDMain;

import java.sql.SQLException;

public class DatabaseManager {
    private IDMain plugin;
    private DatabaseConnection identityConnection;



    public DatabaseManager(IDMain plugin) {
        this.plugin = plugin;
        this.identityConnection = new DatabaseConnection(new DatabaseCredentials(plugin.getConfig().getString("database.host"),plugin.getConfig().getString("database.port"),plugin.getConfig().getString("database.database"),plugin.getConfig().getString("database.user"),plugin.getConfig().getString("database.password")));
    }

    public void close(){
        try {
            this.identityConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DatabaseConnection getIdentityConnection() {
        return identityConnection;
    }
}
