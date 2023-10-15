package kimu.klossom.ryoko.commands.warps

import io.github.crackthecodeabhi.kreds.connection.KredsClient
import kimu.klossom.ryoko.providers.YamlProvider
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class SetWarp(private val plugin: Plugin) : Command("setwarp") {
    init {
        aliases = listOf("addwarp", "createwarp")
        description = "Set a new warp location!"
        usage = "/setwarp <name>"
        // permission = "ryoko.createwarp"
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
        plugin.logger.info(player.effectivePermissions.toString());

        val warpName = args[0];

        YamlProvider(plugin).set("warps", "${player.name}.$warpName", player.location);

        val warpSetMessage: TextComponent = Component.text("The warp ")
            .color(NamedTextColor.WHITE)
            .append(Component.text(warpName, NamedTextColor.BLUE))
            .append(Component.text(" was set!"));

        player.sendMessage(warpSetMessage);
        return true;
    }
}