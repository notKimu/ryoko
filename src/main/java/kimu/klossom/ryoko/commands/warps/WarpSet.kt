package kimu.klossom.ryoko.commands.warps

import kimu.klossom.ryoko.checks.CommandChecks
import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.YamlProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class WarpSet(private val plugin: Plugin) : Command("warpset") {
    init {
        aliases = listOf("warpadd", "warpcreate")
        description = "Set a new warp location!"
        usage = "/warpset <name>"
        // permission = "ryoko.warpset"
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
        YamlProvider(plugin).set("warps", warpName, player.location);

        val warpSetMessage = MessageProvider.getMessage(MessageType.WarpSet)
            .replace("{warp}", warpName);

        player.sendRichMessage(warpSetMessage);
        return true;
    }
}