package kimu.klossom.ryoko.checks

import kimu.klossom.ryoko.providers.*
import org.bukkit.plugin.Plugin

class MainChecks(private val plugin: Plugin) {
    fun moduleLoader() {
        CoreModule(plugin).load();

        if (plugin.config.getBoolean(ModuleTypes.SocialModule)) {
            SocialModule(plugin).load();
        }
        if (plugin.config.getBoolean(ModuleTypes.WarpModule)) {
            WarpModule(plugin).load();
        }
        if (plugin.config.getBoolean(ModuleTypes.TpaModule)) {
            TpaModule(plugin).load();
        }
        if (plugin.config.getBoolean(ModuleTypes.HomeModule)) {
            HomeModule(plugin).load();
        }
    }
}