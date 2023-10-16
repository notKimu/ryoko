package kimu.klossom.ryoko.commands.warps

import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.YamlProvider
import kimu.klossom.ryoko.utils.checks.CommandChecks
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class WarpSet(private val plugin: Plugin) : Command("warpadd") {
    init {
        aliases = listOf("warpset", "warpcreate")
        description = "Set a new warp location!"
        usage = "/warpadd <name>"
        // permission = "ryoko.warpadd"
    }
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        }
        if (args == null || args.size != 1) {
            player.sendPlainMessage(usage);
            return false;
        }

        val warpName = args[0];
        YamlProvider(plugin).set("warps", "${player.name}.$warpName", player.location);

        val warpSetMessage = MessageProvider.getMessage(MessageType.WarpSet)
            .replace("{warp}", warpName);

        player.sendRichMessage(warpSetMessage);
        return true;
    }
}