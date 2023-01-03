package fr.quenk.usaiii;

import fr.quenk.usaiii.db.DatabaseConnection;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PAPI extends PlaceholderExpansion {
    private IDMain instance;

    public PAPI(IDMain instance) {
        this.instance = instance;
    }
    @Override
    public String getAuthor() {
        return "QuenK";
    }

    @Override
    public String getIdentifier() {
        return "usaiiiidentity";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }
        final DatabaseConnection identityConnection = instance.getDatabaseManager().getIdentityConnection();
        try {
            final Connection connection = identityConnection.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM player_identity WHERE pseudo = ?");
            preparedStatement.setString(1, player.getName());
            final ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                if (identifier.equals("nom")) {
                    return rs.getString("name");
                }
                if (identifier.equals("prenom")) {
                    return rs.getString("firstname");
                }
                if (identifier.equals("prenom2")) {
                    return rs.getString("secondname");
                }
                if (identifier.equals("age")) {
                    return String.valueOf(rs.getInt("age"));
                }
                if (identifier.equals("sex")){
                    return rs.getString("sex");
                }
                if (identifier.equals("nationalite")){
                    return rs.getString("nationalite");
                }
                if (identifier.equals("id")){
                    return String.valueOf(rs.getLong("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
