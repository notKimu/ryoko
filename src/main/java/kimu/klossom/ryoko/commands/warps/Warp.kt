package kimu.klossom.ryoko.commands.warps

import kimu.klossom.ryoko.checks.CommandChecks
import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.YamlProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class Warp(private val plugin: Plugin) : Command("warp") {
    init {
        description = "Teleport to a warp location!"
        usage = "/warp <name?>"
        // permission = "ryoko.warp"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        }

        val warpFile = YamlProvider(plugin).getFile("warps");

        if (args.isNullOrEmpty()) {
            val warpKeys = requireNotNull(warpFile.getKeys(false)) {
                val noWarpsSetMessage = MessageProvider.getMessage(MessageType.WarpListEmpty);

                player.sendRichMessage(noWarpsSetMessage);
                return true;
            };

            val availableWarpsMessage = MessageProvider.getMessage(MessageType.WarpList)
                .replace("{warps}", warpKeys.joinToString(", "));

            player.sendRichMessage(availableWarpsMessage);
            return true;
        }


        val warpName = args[0];
        val warpLocation = requireNotNull(warpFile.getLocation(warpName)) {
            val warpNotFoundMessage = MessageProvider.getMessage(MessageType.WarpNotFound)
                .replace("{warp}", warpName);

            player.sendRichMessage(warpNotFoundMessage);
            return false;
        }

        val warpingMessage = MessageProvider.getMessage(MessageType.Warping)
            .replace("{warp}", warpName);

        player.sendRichMessage(warpingMessage);
        player.teleportAsync(warpLocation);
        return true;
    }
}