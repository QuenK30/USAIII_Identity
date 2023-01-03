package fr.quenk.usaiii.events;

import fr.quenk.usaiii.IDMain;
import fr.quenk.usaiii.db.DatabaseConnection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

public class IDPlayerJoin implements Listener {
    private IDMain instance;

    public IDPlayerJoin(IDMain instance) {
        this.instance = instance;
    }
    static HashMap<Player,Long> playerID = new HashMap<>();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        final String playerName = player.getName();
        final DatabaseConnection identityConnection = instance.getDatabaseManager().getIdentityConnection();
        try {
            final Connection connection = identityConnection.getConnection();

            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT pseudo,id FROM player_identity WHERE pseudo = ?");
            preparedStatement.setString(1, playerName);
            final ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Identity > " + playerName + " est connecté");
            if (rs.next()) {
                System.out.println("Identity > " + playerName + " c'est connecté avec l'ID " + rs.getLong("id"));
                putIDNumber(player);
            } else {
                createIdentity(player);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createIdentity(Player player) {
        final String playerName = player.getName();
        final DatabaseConnection identityConnection = instance.getDatabaseManager().getIdentityConnection();
        try {
            final Connection connection = identityConnection.getConnection();
            //? = id, player_name, firstname, secondname, name, sex, age
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player_identity VALUES (?,?,?,?,?,?,?,?)");
            preparedStatement.setLong(1, randomIDNumber(getMinIDNumber(), getMaxIDNumber()));
            putIDNumber(player);
            preparedStatement.setString(2, playerName);
            preparedStatement.setString(3, "null");
            preparedStatement.setString(4, "null");
            preparedStatement.setString(5, "null");
            preparedStatement.setString(6, "null");
            preparedStatement.setInt(7, 0);
            preparedStatement.setString(8, "null");
            preparedStatement.executeUpdate();

            System.out.println("Identity > Le joueur " + playerName + " a été ajouté à la base de données avec l'ID "+playerID.get(player));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void putIDNumber(Player player){
        final DatabaseConnection identityConnection = instance.getDatabaseManager().getIdentityConnection();
        final Connection connection;
        try {
            connection = identityConnection.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM player_identity WHERE pseudo = ?");
            preparedStatement.setString(1, player.getName());
            final ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                playerID.put(player, rs.getLong("id"));
            }else {
                System.out.println("Identity > Le joueur " + player.getName() + " n'a pas d'identité");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long randomIDNumber(long lowerBound, long upperBound) {
        Random random = new Random();
        long range = upperBound - lowerBound;
        long randomLong = Math.floorMod(random.nextLong(), range);
        while (isIDNumberAlreadyUsed(randomLong)) {
            randomLong = Math.floorMod(random.nextLong(), range);
        }
        System.out.println("ID > ID number generated : " + randomLong);
        return lowerBound + randomLong;
    }

    private boolean isIDNumberAlreadyUsed(long idNumber){
        final DatabaseConnection identityConnection = instance.getDatabaseManager().getIdentityConnection();
        try {
            final Connection connection = identityConnection.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM player_identity WHERE id = ?");
            preparedStatement.setLong(1, idNumber);
            final ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long getMinIDNumber() {
        long idMin;
        try {
            idMin = instance.getConfig().getLong("identity.range.min");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        return idMin;
    }
    private long getMaxIDNumber() {
        long idMax;
        try {
            idMax = instance.getConfig().getLong("identity.range.max");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        return idMax;
    }

}
