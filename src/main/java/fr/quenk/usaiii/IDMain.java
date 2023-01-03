package fr.quenk.usaiii;

import fr.quenk.usaiii.cmd.IDCommand;
import fr.quenk.usaiii.db.DatabaseManager;
import fr.quenk.usaiii.events.EventsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class IDMain extends JavaPlugin {
    private static IDMain instance;
    private DatabaseManager databaseManager;
    public static IDMain getInstance() {
        return instance;
    }
    public static String getNPCPrefix() {
        return instance.getConfig().getString("npc.prefix");
    }
    public static String getNPCName() {
        return instance.getConfig().getString("npc.name");
    }
    @Override
    public void onLoad() {
        instance = this;
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();

        //Initialize PAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                new EventsManager(this).registerEvents();
                new PAPI(this).register();
        } else {
            System.out.println("PlaceholderAPI isn't installed on this server!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        this.databaseManager = new DatabaseManager(this);
        getCommand("carteid").setExecutor(new IDCommand(this));
    }

    @Override
    public void onDisable() {
        this.databaseManager.close();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
