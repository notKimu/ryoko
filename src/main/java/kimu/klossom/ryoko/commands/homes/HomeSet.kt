package kimu.klossom.ryoko.commands.homes

import kimu.klossom.ryoko.checks.CommandChecks
import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.YamlProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class HomeSet(private val plugin: Plugin) : Command("homeset") {
    init {
        aliases = listOf("homeadd", "homenew")
        description = "Set up a new home location!"
        usage = "/homeset <name>"
        // permission = "ryoko.homeset"
    }
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        }
        if (args == null || args.size != 1) {
            player.sendPlainMessage(usage);
            return false;
        }

        val homeName = args[0];
        YamlProvider(plugin).set("homes", "${player.name}.$homeName", player.location);

        val homeSetMessage = MessageProvider.getMessage(MessageType.HomeSet)
            .replace("{home}", homeName);

        player.sendRichMessage(homeSetMessage);
        return true;
    }
}