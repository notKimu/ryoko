package kimu.klossom.ryoko.commands.tpa

import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.RamDatabaseProvider
import kimu.klossom.ryoko.checks.CommandChecks
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class TpaAccept(private val plugin: Plugin) : Command("tpaccept") {
    init {
        aliases = listOf("tpc")
        description = "Accept the tpa request of a player"
        usage = "/tpaccept <player>"
        // permission = "ryoko.tpaccept"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        }
        if (args == null || args.size != 1) {
            player.sendPlainMessage(usage);
            return false;
        }

        val requestingPlayerName = args[0];
        val requestingPlayer = requireNotNull(CommandChecks.getOnlinePlayer(player, requestingPlayerName, false)) {
            return false;
        }

        val externalPlayerRequest = RamDatabaseProvider.get("tpa:${requestingPlayerName}");
        if (externalPlayerRequest == null || externalPlayerRequest != player.name) {
            val playerNotFoundMessage = MessageProvider.getMessage(MessageType.TpaAcceptNonExistent)
                .replace("{player}", requestingPlayerName);

            requestingPlayer.sendRichMessage(playerNotFoundMessage);
            return false;
        }

        val playerAcceptedTpaRequestMessage = MessageProvider.getMessage(MessageType.TpaAccepted)
            .replace("{player}", player.name);

        requestingPlayer.sendRichMessage(playerAcceptedTpaRequestMessage);
        requestingPlayer.teleportAsync(player.location);
        return true;
    }
}