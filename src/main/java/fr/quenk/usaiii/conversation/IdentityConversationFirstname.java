package fr.quenk.usaiii.conversation;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class IdentityConversationFirstname extends StringPrompt {
    @Override
    public String getPromptText(ConversationContext context) {
        return "Quel est votre prénom ?";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        if (context.getForWhom() instanceof Player) {
                if (input.equalsIgnoreCase("cancel")){
                    context.getForWhom().sendRawMessage("§cVous avez annulé la configuration de votre identité.");
                    return Prompt.END_OF_CONVERSATION;
            }else {
                context.getForWhom().sendRawMessage("§aVotre prénom est maintenant configuré sur: " + input);
                    context.setSessionData("firstname", input);
                    return new IdentityConversationSecondName();
                }
        }
        return this;
    }
}
