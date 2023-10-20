package kimu.klossom.ryoko.commands.homes

import kimu.klossom.ryoko.checks.CommandChecks
import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.YamlProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class HomeDel(private val plugin: Plugin) : Command("homedel") {
    init {
        aliases = listOf("homerem", "homerase")
        description = "Delete a saved house location!"
        usage = "/homedel <name>"
        // permission = "ryoko.homedel"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        }
        if (args == null || args.size != 1) {
            player.sendPlainMessage(usage);
            return false;
        }

        val homeFile = YamlProvider(plugin).getFile("homes");

        val homeName = args[0];
        val chosenWarp = requireNotNull(homeFile.get("${player.name}.${homeName}")) {
            val homeNotFoundMessage = MessageProvider.getMessage(MessageType.HomeNotFound)
                .replace("{home}", homeName);

            player.sendRichMessage(homeNotFoundMessage);
            return false;
        };

        homeFile.set("${player.name}.${homeName}", null);
        YamlProvider(plugin).save(homeFile);

        val removedHomeMessage = MessageProvider.getMessage(MessageType.HomeDelete)
            .replace("{home}", homeName);

        player.sendRichMessage(removedHomeMessage);
        return true;
    }
}