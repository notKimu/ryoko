package kimu.klossom.ryoko.commands.warps

import kimu.klossom.ryoko.providers.YamlProvider
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class DelWarp(private val plugin: Plugin) : Command("delwarp") {
    init {
        aliases = listOf("deletewarp", "remwarp", "removewarp")
        description = "Remove a saved warp!"
        usage = "/delwarp <name>"
        // permission = "ryoko.delwarp"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        if (sender !is Player || sender.player == null) {
            sender.sendPlainMessage("You need to be a player to use this command!");
            return false;
        }
        if (args == null || args.size != 1) {
            plugin.logger.info(usage);
            return false;
        }

        val player = requireNotNull(sender.player) {
            sender.sendPlainMessage("You need to be a player to use this command!");
            return false;
        }
        val warpName = args[0];


        val chosenWarp = requireNotNull(YamlProvider(plugin).getFile("warps").get("${player.name}.${warpName}")) {
            val noWarpFound: TextComponent = Component.text("The warp ")
                .color(NamedTextColor.RED)
                .append(Component.text(warpName, NamedTextColor.WHITE))
                .append(Component.text(" does not exist!"));

            player.sendMessage(noWarpFound);
            return false;
        };

        YamlProvider(plugin).set("warps", "${player.name}.${warpName}", null);
        return true;
    }
}