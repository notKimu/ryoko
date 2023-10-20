package kimu.klossom.ryoko.commands.spawn

import kimu.klossom.ryoko.checks.CommandChecks
import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.MessageType
import kimu.klossom.ryoko.providers.YamlProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class SpawnSet(private val plugin: Plugin) : Command("spawnset") {
    init {
        description = "Set the location where players spawn!"
        usage = "/spawnset"
        // permission = "ryoko.spawnset"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        val player = requireNotNull(CommandChecks.isSenderPlayer(sender)) {
            return false;
        }

        val playerLocation = player.location;

        YamlProvider(plugin).set("spawn-config", "location", playerLocation);

        val spawnPointSetMessage = MessageProvider.getMessage(MessageType.SpawnPointSet)
            .replace("{location}", "X: ${playerLocation.x}, Y: ${playerLocation.y}, Z: ${playerLocation.z}")

        player.sendRichMessage(spawnPointSetMessage);
        return true;
    }
}