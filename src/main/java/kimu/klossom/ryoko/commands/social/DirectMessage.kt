package kimu.klossom.ryoko.commands.social

import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.checks.CommandChecks
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class DirectMessage(private val plugin: Plugin) : Command("dm") {
    init {
        aliases = listOf("direct", "send", "private")
        description = "Send a private message to someone"
        usage = "/dm <player> <message>"
        // permission = "ryoko.dm"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        }
        if (args == null || args.size < 2) {
            sender.sendPlainMessage(usage);
            return false;
        }

        val targetPlayerName = args[0];
        val targetPlayer = requireNotNull(CommandChecks.getOnlinePlayer(player, targetPlayerName, false)) {
            return false;
        }

        val directMessageContent = args.slice(1..<args.size).joinToString(" ");
        val directMessageFormatted = MessageProvider.getMessage(MessageType.DirectMessagePrefix)
            .replace("{player}", player.name)
            .replace("{message}", directMessageContent);

        targetPlayer.sendRichMessage(directMessageFormatted);
        return true;
    }
}