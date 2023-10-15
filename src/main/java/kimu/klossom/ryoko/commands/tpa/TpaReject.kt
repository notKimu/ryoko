package kimu.klossom.ryoko.commands.tpa

import kimu.klossom.ryoko.providers.RamDatabaseProvider
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class TpaReject(private val plugin: Plugin) : Command("tpareject"){
    init {
        aliases = listOf("tpr")
        description = "Reject a players petition to teleport to you"
        usage = "/tpareject <player>"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendPlainMessage("You need to be a player to use this command!");
            return false;
        }
        if (args == null || args.size != 1) {
            sender.sendPlainMessage(usageMessage);
            return false;
        }

        val requestingPlayerName = args[0];
        if (requestingPlayerName == sender.name) {
            val cantRejectTeleportToSelf = Component.text("How'd you cancel a teleport request to yourself? Im curious.")
                .color(NamedTextColor.RED);

            sender.sendMessage(cantRejectTeleportToSelf);
            return false;
        }

        val requestingPlayer = requireNotNull(plugin.server.getPlayer(requestingPlayerName)) {
            val playerNotFound = Component.text("I can't find the player ")
                .color(NamedTextColor.RED)
                .append(Component.text(requestingPlayerName, NamedTextColor.WHITE))
                .append(Component.text("!"));

            sender.sendMessage(playerNotFound);
            return false;
        };

        val teleportPetition = RamDatabaseProvider.get("tpa:${requestingPlayerName}");
        if (teleportPetition == null || teleportPetition != sender.name) {
            val noTpaRequestFound = Component.text("Doesn't look like ")
                .color(NamedTextColor.RED)
                .append(Component.text(requestingPlayerName, NamedTextColor.WHITE))
                .append(Component.text(" has asked you for a teleport..."));

            sender.sendMessage(noTpaRequestFound);
            return false;
        }

        RamDatabaseProvider.remove("tpa:${requestingPlayerName}");
        val cancelledTpaRequest = Component.text("Oof, ")
            .color(NamedTextColor.RED)
            .append(Component.text(sender.name, NamedTextColor.WHITE))
            .append(Component.text(" rejected your tpa request..."));

        requestingPlayer.sendMessage(cancelledTpaRequest);
        return true
    }

}