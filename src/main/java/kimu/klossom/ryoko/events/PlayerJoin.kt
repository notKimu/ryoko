package kimu.klossom.ryoko.events

import kimu.klossom.ryoko.providers.YamlProvider
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin


class PlayerJoin(private val plugin: Plugin) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent?) {
        if (event == null) return;

        val spawnConfig = YamlProvider(plugin).getFile("spawn-config");

        val spawnLocation = spawnConfig.getLocation("location");
        if (spawnLocation != null) {
            event.player.teleportAsync(spawnLocation);
        }

        event.joinMessage(null);
    }
}