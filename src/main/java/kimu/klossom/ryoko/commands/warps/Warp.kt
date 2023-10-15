package kimu.klossom.ryoko.commands.warps

import kimu.klossom.ryoko.providers.YamlProvider
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class Warp(private val plugin: Plugin) : Command("warp") {
    init {
        description = "View your warps or teleport to one of them"
        usage = "/warp <name?>"
        // permission = "ryoko.warp"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        if (sender !is Player || sender.player == null) {
            sender.sendPlainMessage("You need to be a player to use this command!");
            return false;
        }
        val player = requireNotNull(sender.player) {
            sender.sendPlainMessage("You need to be a player to use this command!");
            return false;
        }

        val warpFile = YamlProvider(plugin).getFile("warps");

        if (args.isNullOrEmpty()) {
            val playerWarps = requireNotNull(warpFile.getConfigurationSection(player.name)) {
                player.sendPlainMessage("You have no warps set!");
                return true;
            };

            val warpKeys = playerWarps.getKeys(false).joinToString(", ");
            val playerWarpNamesMessage: TextComponent = Component.text("Your warps: ")
                .color(NamedTextColor.WHITE)
                .append(Component.text(warpKeys, NamedTextColor.BLUE));

            player.sendMessage(playerWarpNamesMessage);
            return true;
        }


        val warpName = args[0];
        val warpLocation = requireNotNull(warpFile.get("${player.name}.${warpName}")) {
            val warpNotFound: TextComponent = Component.text("The warp ")
                .color(NamedTextColor.RED)
                .append(Component.text(warpName, NamedTextColor.WHITE))
                .append(Component.text(" does not exist!"));

            player.sendMessage(warpNotFound);
            return false;
        } as Location;

        val warpingMessage: TextComponent = Component.text("Sending you to ")
            .color(NamedTextColor.BLUE)
            .append(Component.text(warpName, NamedTextColor.WHITE))
            .append(Component.text("..."));

        player.sendMessage(warpingMessage);
        player.teleportAsync(warpLocation);
        return true;
    }
}