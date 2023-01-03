package fr.quenk.usaiii.cmd;

import fr.quenk.usaiii.IDMain;
import fr.quenk.usaiii.conversation.IdentityConversationName;
import fr.quenk.usaiii.db.DatabaseConnection;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IDCommand implements CommandExecutor {
    private final IDMain instance;
    public IDCommand(IDMain instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("usaiii.identity.config") || player.isOp()) {
                if (args.length == 0) {
                    player.sendMessage("§aRécupération de l'identité en cours...");
                    getId(player);
                }else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("config")) {
                        //remise a null de l'identité
                        player.sendMessage("§aRemise a zero de l'identité en cours...");
                        resetId(player);
                        //Lancement de la configuration de l'identité
                        player.sendMessage(IDMain.getNPCPrefix() + "§fBonjour, je me présente, je suis le" + IDMain.getNPCName() + "! Je vais enregistrer votre identité.");
                        player.sendMessage(IDMain.getNPCPrefix() + "§fPour commencer, quel est votre nom ?");
                        player.sendMessage(IDMain.getNPCPrefix() + "§fPour annuler, tapez §ccancel§f.");
                        ConversationFactory factory = new ConversationFactory(instance);
                        factory.withFirstPrompt(new IdentityConversationName())
                                .withLocalEcho(false);
                        Conversation conversation = factory.buildConversation(player);
                        conversation.begin();
                    }else if (args[0].equalsIgnoreCase("reset")){
                        player.sendMessage("§aRemise a zero de l'identité en cours...");
                        resetId(player);
                    }
                    }else {
                        player.sendMessage("§cErreur: Commande inconnue");
                    }
            }else {
                player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande");
            }
        }
        return false;
    }


    //Conversation avec le joueur



    private void resetId(Player player) {
        final String playerName = player.getName();
        final DatabaseConnection identityConnection = instance.getDatabaseManager().getIdentityConnection();
        try {
            final Connection connection = identityConnection.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player_identity SET name = ?, firstname = ?, secondname = ?, age = ?, sex = ?, nationalite = ? WHERE pseudo = ?");
            preparedStatement.setString(1, "null");
            preparedStatement.setString(2, "null");
            preparedStatement.setString(3, "null");
            preparedStatement.setInt(4, 0);
            preparedStatement.setString(5, "null");
            preparedStatement.setString(6, "null");
            preparedStatement.setString(7, playerName);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void getId(Player player){
        player.sendMessage("§aRécupération de l'identité en cours...");
        final String playerName = player.getName();
        player.sendMessage("§aNom: " +PlaceholderAPI.setPlaceholders(player, "%usaiiiidentity_nom%"));
        player.sendMessage("§aPrénom: " +PlaceholderAPI.setPlaceholders(player, "%usaiiiidentity_prenom%"));
        if (PlaceholderAPI.setPlaceholders(player, "%usaiiiidentity_nom2%").equalsIgnoreCase("null")) {
            player.sendMessage("§aDeuxième nom: §cAucun");
        }else {
            player.sendMessage("§aDeuxième prénom: " +PlaceholderAPI.setPlaceholders(player, "%usaiiiidentity_prenom2%"));
        }
        player.sendMessage("§aAge: " +PlaceholderAPI.setPlaceholders(player, "%usaiiiidentity_age%"));
        player.sendMessage("§aSexe: " +PlaceholderAPI.setPlaceholders(player, "%usaiiiidentity_sex%"));
        player.sendMessage("§aNationalité: " +PlaceholderAPI.setPlaceholders(player, "%usaiiiidentity_nationalite%"));
    }

}
