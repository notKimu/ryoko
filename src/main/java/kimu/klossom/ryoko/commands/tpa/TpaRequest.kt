package kimu.klossom.ryoko.commands.tpa

import kimu.klossom.ryoko.providers.RamDatabaseProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class TpaRequest(private val plugin: Plugin) : Command("tpa") {
    init {
        aliases = listOf("tparequest")
        description = "Teleport to a player (if they accept)"
        usage = "/tpa <player>"
        // permission = "ryoko.tpa"
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

        val targetPlayerName = args[0];
        if (targetPlayerName == sender.name) {
            val cantTeleportToSelf = Component.text("Why would you want to teleport to yourself...")
                .color(NamedTextColor.RED);

            sender.sendMessage(cantTeleportToSelf);
            return false;
        }

        val targetPlayer = requireNotNull(plugin.server.getPlayer(targetPlayerName)) {
            val playerNotFound = Component.text("I can't find the player ")
                .color(NamedTextColor.RED)
                .append(Component.text(targetPlayerName, NamedTextColor.WHITE))
                .append(Component.text("!"));

            sender.sendMessage(playerNotFound);
            return false;
        };

        RamDatabaseProvider.set("tpa:${sender.name}", targetPlayerName);
        // Create background task to delete the request after 80 seconds
        GlobalScope.launch {
            delay(80000);
            RamDatabaseProvider.remove("tpa:${sender.name}");
        }

        val gotTpaRequest = Component.text("You got a teleport request from ")
            .color(NamedTextColor.BLUE)
            .append(Component.text(targetPlayerName, NamedTextColor.WHITE))
            .append(Component.text(" !\n"))
            .append(Component.text("Accept ", NamedTextColor.GREEN))
            .clickEvent(ClickEvent.runCommand("tpaccept $targetPlayerName"))
            .append(Component.text("Reject ", NamedTextColor.GREEN))
            .clickEvent(ClickEvent.runCommand("tpareject $targetPlayerName"))
            .append(Component.text("Ignore after 80s", NamedTextColor.GRAY));
        targetPlayer.sendMessage(gotTpaRequest)
        return true
    }
}