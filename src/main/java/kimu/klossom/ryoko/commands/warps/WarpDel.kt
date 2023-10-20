package kimu.klossom.ryoko.commands.warps

import kimu.klossom.ryoko.checks.CommandChecks
import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.YamlProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class WarpDel(private val plugin: Plugin) : Command("warpdel") {
    init {
        aliases = listOf("warprem", "warpremove", "warperase")
        description = "Remove a saved warp!"
        usage = "/warpdel <name>"
        // permission = "ryoko.warpdel"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        }
        if (args == null || args.size != 1) {
            player.sendPlainMessage(usage);
            return false;
        }

        val warpFile = YamlProvider(plugin).getFile("warps");

        val warpName = args[0];
        val chosenWarp = requireNotNull(warpFile.get(warpName)) {
            val warpNotFoundMessage = MessageProvider.getMessage(MessageType.WarpNotFound)
                .replace("{warp}", warpName);

            player.sendRichMessage(warpNotFoundMessage);
            return false;
        };

        warpFile.set(warpName, null);
        YamlProvider(plugin).save(warpFile);

        val removedWarpMessage = MessageProvider.getMessage(MessageType.WarpNotFound)
            .replace("{warp}", warpName);

        player.sendRichMessage(removedWarpMessage);
        return true;
    }
}