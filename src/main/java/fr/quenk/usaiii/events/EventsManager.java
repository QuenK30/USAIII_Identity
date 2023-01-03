package fr.quenk.usaiii.events;

import fr.quenk.usaiii.IDMain;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.sql.SQLException;

public class EventsManager {
    private static IDMain instance;
    public EventsManager(IDMain instance) {
        this.instance = instance;
    }
    public void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new IDPlayerJoin(instance), instance);
    }
}
