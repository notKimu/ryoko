package kimu.klossom.ryoko.commands.warps

import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.YamlProvider
import kimu.klossom.ryoko.utils.checks.CommandChecks
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class Warp(private val plugin: Plugin) : Command("warp") {
    init {
        description = "View your warps or teleport to one of them"
        usage = "/warp <name?>"
        // permission = "ryoko.warp"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        }

        val warpFile = YamlProvider(plugin).getFile("warps");

        if (args.isNullOrEmpty()) {
            val playerWarps = requireNotNull(warpFile.getConfigurationSection(player.name)) {
                val noWarpsSetMessage = MessageProvider.getMessage(MessageType.WarpListEmpty);

                player.sendRichMessage(noWarpsSetMessage);
                return true;
            };

            val warpKeys = playerWarps.getKeys(false).joinToString(", ");
            val playerWarpKeysMessage = MessageProvider.getMessage(MessageType.WarpList)
                .replace("{warps}", warpKeys);

            player.sendRichMessage(playerWarpKeysMessage);
            return true;
        }


        val warpName = args[0];
        val warpLocation = requireNotNull(warpFile.get("${player.name}.${warpName}")) {
            val warpNotFoundMessage = MessageProvider.getMessage(MessageType.WarpNotFound)
                .replace("{warp}", warpName);

            player.sendRichMessage(warpNotFoundMessage);
            return false;
        } as Location;

        val warpingMessage = MessageProvider.getMessage(MessageType.Warping)
            .replace("{warp}", warpName);

        player.sendRichMessage(warpingMessage);
        player.teleportAsync(warpLocation);
        return true;
    }
}