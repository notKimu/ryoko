package kimu.klossom.ryoko.commands.messages

import kimu.klossom.ryoko.providers.MessageProvider
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class SendPrivateMessage(private val plugin: Plugin) : Command("dm") {
    init {
        aliases = listOf("direct", "send", "private")
        description = "Send a private message to someone"
        usage = "/dm <player> <message>"
        // permission = "ryoko.dm"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendPlainMessage("You need to be a player to use this command!");
            return false;
        }
        if (args == null || args.size < 2) {
            sender.sendPlainMessage(usageMessage);
            return false;
        }

        val playerName = args[0];
        if (playerName == sender.name) {
            val cantSendMessageToSelf: TextComponent = Component.text("You can't send a message to yourself...")
                .color(NamedTextColor.RED);

            sender.sendMessage(cantSendMessageToSelf);
            return false;
        }

        val targetPlayer = requireNotNull(plugin.server.getPlayer(playerName)) {
            val playerNotFound: TextComponent = Component.text("I can't find the player ")
                .color(NamedTextColor.RED)
                .append(Component.text(playerName, NamedTextColor.WHITE))
                .append(Component.text(" !"));

            sender.sendMessage(playerNotFound);
            return false;
        };

        val directMessageContent = args.slice(1..<args.size).joinToString(" ");
        val directMessageFormatted = MessageProvider.getMessage("direct-message-prefix")
            .replace("{player}", sender.name)
            .replace("{message}", directMessageContent);

        targetPlayer.sendPlainMessage(directMessageFormatted);
        return true;
    }
}