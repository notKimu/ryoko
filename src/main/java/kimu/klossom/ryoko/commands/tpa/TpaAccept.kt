package kimu.klossom.ryoko.commands.tpa

import kimu.klossom.ryoko.providers.RamDatabaseProvider
import kimu.klossom.ryoko.utils.checks.CommandChecks
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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
        val player = requireNotNull(CommandChecks().isSenderPlayer(sender)) {
            sender.sendPlainMessage("You need to be a player to use this command!");
            return false;
        };

        if (args == null || args.size != 1) {
            sender.sendPlainMessage(usageMessage);
            return false;
        }

        val requestingPlayerName = args[0];
        if (requestingPlayerName == player.name) {
            return false;
        }

        val requestingPlayer = requireNotNull(plugin.server.getPlayer(args[0])) {
            val playerNotFound = Component.text("Doesn't look like ")
                .color(NamedTextColor.RED)
                .append(Component.text(requestingPlayerName, NamedTextColor.WHITE))
                .append(Component.text("!"));
            player.sendPlainMessage("${args[0]} is not online!");
            return false;
        }

        val externalPlayerRequest = RamDatabaseProvider.get("tpa:${requestingPlayer.name}");
        if (externalPlayerRequest == null || externalPlayerRequest != player.name) {
            val playerNotFound = Component.text("Doesn't look like ")
                .color(NamedTextColor.RED)
                .append(Component.text(requestingPlayerName, NamedTextColor.WHITE))
                .append(Component.text("!"));

            player.sendPlainMessage("Doesn't look like ${args[0]} has sent you a tpa request...");
            return false;
        }

        requestingPlayer.sendPlainMessage("${player.name} accepted your request! Teleporting...");
        requestingPlayer.teleportAsync(player.location);
        return true;
    }
}