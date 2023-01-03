package fr.quenk.usaiii.conversation;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class IdentityConversationName extends StringPrompt {

    @Override
    public String getPromptText(ConversationContext context) {
        return "Quel est votre nom ?";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        if (context.getForWhom() instanceof Player) {
            if (input.equalsIgnoreCase("cancel")) {
                context.getForWhom().sendRawMessage("§cVous avez annulé la configuration de votre identité.");
                return Prompt.END_OF_CONVERSATION;
            }else {
                context.getForWhom().sendRawMessage("§aVotre nom est maintenant configuré sur: " + input);
                context.setSessionData("name", input);
                context.setSessionData("pName", ((Player) context.getForWhom()).getName());
                return new IdentityConversationFirstname();
            }
        }
        return this;
    }
}
