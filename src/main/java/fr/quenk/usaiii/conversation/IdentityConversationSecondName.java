package fr.quenk.usaiii.conversation;


import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class IdentityConversationSecondName extends StringPrompt {
    @Override
    public String getPromptText(ConversationContext context) {
        return "Quel est votre deuxième prénom ? (Si vous n'en avez pas, tapez \"none\")";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {

        if(context.getForWhom() instanceof Player) {
            context.setSessionData("secondname", input);
            if (input.equalsIgnoreCase("cancel")) {
                context.getForWhom().sendRawMessage("§cVous avez annulé la configuration de votre identité.");
                return Prompt.END_OF_CONVERSATION;
            } else if (input.equalsIgnoreCase("none")) {
                context.getForWhom().sendRawMessage("§aVous n'avez pas de deuxième prénom.");
                context.setSessionData("secondname", "null");
                return new IdentityConversationAge();
            }
            context.getForWhom().sendRawMessage("§aVotre deuxième prénom est maintenant configuré sur: " + input);
            context.setSessionData("secondname", input);
            return new IdentityConversationAge();
        }
        return this;
    }
}
