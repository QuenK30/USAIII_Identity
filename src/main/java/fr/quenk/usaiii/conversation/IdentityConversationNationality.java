package fr.quenk.usaiii.conversation;

import fr.quenk.usaiii.IDMain;
import fr.quenk.usaiii.db.DatabaseConnection;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.SQLException;

public class IdentityConversationNationality extends StringPrompt {
    @Override
    public String getPromptText(ConversationContext context) {
        return "Quel est votre nationalité ?";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        if (context.getForWhom() instanceof Player) {
            if (input.equalsIgnoreCase("cancel")) {
                context.getForWhom().sendRawMessage("§cVous avez annulé la configuration de votre identité.");
                return Prompt.END_OF_CONVERSATION;
            } else {
                context.getForWhom().sendRawMessage("§aVotre nationalité est maintenant configuré sur: " + input);
                context.setSessionData("nationality", input);
                return new IdentityDone();
            }
        }
        return this;
    }
}
