package kimu.klossom.ryoko.commands.tpa

import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.RamDatabaseProvider
import kimu.klossom.ryoko.utils.checks.CommandChecks
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class TpaReject(private val plugin: Plugin) : Command("tpareject"){
    init {
        aliases = listOf("tpr")
        description = "Reject a players petition to teleport to you"
        usage = "/tpareject <player>"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        };
        if (args == null || args.size != 1) {
            player.sendPlainMessage(usage);
            return false;
        }

        val requestingPlayerName = args[0];
        val requestingPlayer = requireNotNull(CommandChecks.getOnlinePlayer(player, requestingPlayerName, false)) {
            return false;
        }

        val teleportPetition = RamDatabaseProvider.get("tpa:${requestingPlayerName}");
        if (teleportPetition == null || teleportPetition != player.name) {
            val noTpaRequestFoundMessage = MessageProvider.getMessage(MessageType.TpaRejectNonExistent)
                .replace("{player}", requestingPlayerName);

            player.sendRichMessage(noTpaRequestFoundMessage);
            return false;
        }

        RamDatabaseProvider.remove("tpa:${requestingPlayerName}");

        val requestGotRejectedMessage = MessageProvider.getMessage(MessageType.TpaGotRejected)
            .replace("{player}", player.name);
        val successfullyRejectedMessage = MessageProvider.getMessage(MessageType.TpaRejected)
            .replace("{player}", requestingPlayerName);

        requestingPlayer.sendRichMessage(requestGotRejectedMessage);
        player.sendRichMessage(successfullyRejectedMessage);
        return true
    }

}