package fr.quenk.usaiii.conversation;

import fr.quenk.usaiii.IDMain;
import fr.quenk.usaiii.db.DatabaseConnection;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdentityDone implements Prompt {
    @Override
    public String getPromptText(ConversationContext context) {
        Conversable conversable = context.getForWhom();
        String name = context.getSessionData("name").toString();
        String firstname = context.getSessionData("firstname").toString();
        String secondname = context.getSessionData("secondname").toString();
        String age = context.getSessionData("age").toString();
        String nationality = context.getSessionData("nationality").toString();
        String sex = context.getSessionData("sex").toString();
        String pName = context.getSessionData("pName").toString();

        conversable.sendRawMessage("§aVotre identité est maintenant configurée.");
        conversable.sendRawMessage("§aNom: " + name);
        conversable.sendRawMessage("§aPrénom: " + firstname);
        conversable.sendRawMessage("§aDeuxième prénom: " + secondname);
        conversable.sendRawMessage("§aAge: " + age);
        conversable.sendRawMessage("§aNationalité: "+ nationality);
        conversable.sendRawMessage("§aSex: "+sex);

        final DatabaseConnection identityConnection = IDMain.getInstance().getDatabaseManager().getIdentityConnection();
        try {
            final Connection connection = identityConnection.getConnection();
                //? = id, player_name, firstname, secondname, name, sex, age
                final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player_identity SET name = ?, firstname = ?, secondname = ?, age = ?, sex = ?, nationalite = ? WHERE pseudo = ?");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, firstname);
                preparedStatement.setString(3, secondname);
                preparedStatement.setInt(4, Integer.parseInt(age));
                preparedStatement.setString(5, sex);
                preparedStatement.setString(6, nationality);
                preparedStatement.setString(7, pName);

                preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "§aVotre identité est maintenant configurée.";
    }

    @Override
    public boolean blocksForInput(ConversationContext context) {
        return false;
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        return END_OF_CONVERSATION;
    }
}
