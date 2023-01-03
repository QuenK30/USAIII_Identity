package fr.quenk.usaiii.conversation;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class IdentityConversationSex extends StringPrompt {
    @Override
    public String getPromptText(ConversationContext context) {
        return "Quel est votre sexe ?";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        if (input.equalsIgnoreCase("homme") || input.equalsIgnoreCase("femme") && context.getForWhom() instanceof Player) {
            context.getForWhom().sendRawMessage("§aVotre sex est maintenant configuré sur: " + input);
            context.setSessionData("sex", input);
            return new IdentityConversationNationality();
        }else {
            if (context.getForWhom() instanceof Player) {
                if (input.equalsIgnoreCase("cancel")) {
                    context.getForWhom().sendRawMessage("§cVous avez annulé la configuration de votre identité.");
                    return Prompt.END_OF_CONVERSATION;
                } else {
                    context.getForWhom().sendRawMessage("§cVous devez choisir entre homme ou femme.");
                    return this;
                }
            }

        }

        return this;
    }
}
