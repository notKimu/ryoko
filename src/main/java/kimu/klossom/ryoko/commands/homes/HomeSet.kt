package kimu.klossom.ryoko.commands.homes

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class HomeSet(private val plugin: Plugin) : Command("homeset") {
    init {
        aliases = listOf("homeadd")
        description = "Set a new home at your location!"
        usage = "/homeset <name>"
        // permission = "ryoko.homeset"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        TODO("Not implemented");
    }
}