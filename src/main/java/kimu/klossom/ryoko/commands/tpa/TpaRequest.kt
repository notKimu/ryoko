package kimu.klossom.ryoko.commands.tpa

import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.RamDatabaseProvider
import kimu.klossom.ryoko.checks.CommandChecks
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class TpaRequest(private val plugin: Plugin) : Command("tpa") {
    init {
        aliases = listOf("tparequest", "tpask")
        description = "Teleport to a player (if they accept)"
        usage = "/tpa <player>"
        // permission = "ryoko.tpa"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        };
        if (args == null || args.size != 1) {
            sender.sendPlainMessage(usage);
            return false;
        }

        val targetPlayerName = args[0];
        val targetPlayer = requireNotNull(CommandChecks.getOnlinePlayer(player, targetPlayerName, false)) {
            return false;
        }

        RamDatabaseProvider.set("tpa:${sender.name}", targetPlayerName);

        // Create background task to delete the request after 80 seconds
        GlobalScope.launch {
            delay(80000);
            RamDatabaseProvider.remove("tpa:${sender.name}");
        }

        val incomingTpaRequestMessage = MessageProvider.getMessage(MessageType.TpaIncoming)
            .replace("{player}", player.name);
        val requestedTpaMessage = MessageProvider.getMessage(MessageType.TpaRequested)
            .replace("{player}", targetPlayerName);

        targetPlayer.sendRichMessage(incomingTpaRequestMessage);
        player.sendRichMessage(requestedTpaMessage);
        return true
    }
}