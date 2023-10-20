package kimu.klossom.ryoko.commands.homes

import kimu.klossom.ryoko.checks.CommandChecks
import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.YamlProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class Home(private val plugin: Plugin) : Command("home") {
    init {
        description = "View your homes or teleport to one of them"
        usage = "/home <name?>"
        // permission = "ryoko.home"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        }

        val homesFile = YamlProvider(plugin).getFile("homes");

        if (args.isNullOrEmpty()) {
            val playerHomes = requireNotNull(homesFile.getConfigurationSection(player.name)) {
                val noHomesSetMessage = MessageProvider.getMessage(MessageType.HomeListEmpty);

                player.sendRichMessage(noHomesSetMessage);
                return true;
            };

            val homeKeys = playerHomes.getKeys(false).joinToString(", ");
            if (homeKeys.isEmpty()) {
                val noHomesSetMessage = MessageProvider.getMessage(MessageType.HomeListEmpty);

                player.sendRichMessage(noHomesSetMessage);
                return true;
            }

            val playerHomeKeysMessage = MessageProvider.getMessage(MessageType.HomeList)
                .replace("{homes}", homeKeys);

            player.sendRichMessage(playerHomeKeysMessage);
            return true;
        }


        val homeName = args[0];
        val homeLocation = requireNotNull(homesFile.getLocation("${player.name}.${homeName}")) {
            val homeNotFoundMessage = MessageProvider.getMessage(MessageType.HomeNotFound)
                .replace("{home}", homeName);

            player.sendRichMessage(homeNotFoundMessage);
            return false;
        }

        val travellingMessage = MessageProvider.getMessage(MessageType.HomeTeleporting)
            .replace("{home}", homeName);

        player.sendRichMessage(travellingMessage);
        player.teleportAsync(homeLocation);
        return true;
    }
}