package kimu.klossom.ryoko.checks

import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CommandChecks {
    fun isSenderPlayer(sender: CommandSender): Player? {
        if (sender !is Player) {
            sender.sendPlainMessage(MessageProvider.getMessage(MessageType.PlayerOnlyCommand));
            return null;
        }

        return sender as Player;
    }

    fun getOnlinePlayer(sender: CommandSender, playerName: String): Player? {
        val onlinePlayer = requireNotNull(sender.server.getPlayer(playerName)) {
            val playerNotOnlineMessage = MessageProvider.getMessage(MessageType.PlayerNotOnline)
                .replace("{player}", playerName);

            sender.sendRichMessage(playerNotOnlineMessage);
            return null;
        }

        return onlinePlayer;
    }

    fun getOnlinePlayer(sender: Player, playerName: String, allowSelf: Boolean): Player? {
        if (!allowSelf && playerName == sender.name) {
            sender.sendRichMessage(MessageProvider.getMessage(MessageType.PlayerRequestedSelf));
            return null;
        }

        val onlinePlayer = requireNotNull(sender.server.getPlayer(playerName)) {
            val playerNotOnlineMessage = MessageProvider.getMessage(MessageType.PlayerNotOnline)
                .replace("{player}", playerName);

            sender.sendRichMessage(playerNotOnlineMessage);
            return null;
        }

        return onlinePlayer;
    }
}
