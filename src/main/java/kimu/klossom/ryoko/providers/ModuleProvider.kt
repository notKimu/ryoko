package kimu.klossom.ryoko.providers

import kimu.klossom.ryoko.commands.homes.Home
import kimu.klossom.ryoko.commands.homes.HomeDel
import kimu.klossom.ryoko.commands.homes.HomeSet
import kimu.klossom.ryoko.commands.social.DirectMessage
import kimu.klossom.ryoko.commands.spawn.SpawnSet
import kimu.klossom.ryoko.commands.tpa.TpaAccept
import kimu.klossom.ryoko.commands.tpa.TpaReject
import kimu.klossom.ryoko.commands.tpa.TpaRequest
import kimu.klossom.ryoko.commands.warps.Warp
import kimu.klossom.ryoko.commands.warps.WarpDel
import kimu.klossom.ryoko.commands.warps.WarpSet
import org.bukkit.plugin.Plugin

interface Module {
    fun load()
    fun unload()
}

object ModuleTypes {
    const val CoreModule = "core-module";
    const val HomeModule = "home-module";
    const val SocialModule = "social-module";
    const val SpawnModule = "spawn-module";
    const val TpaModule = "tpa-module";
    const val WarpModule = "warp-module";
}

class CoreModule(private val plugin: Plugin) : Module {
    override fun load() {
        YamlProvider(plugin).loadSourceFile("messages");
        getMessageModule(plugin, ModuleTypes.CoreModule);
        plugin.logger.info(">>= Core module");
    }

    override fun unload() {
        TODO("Not yet implemented")
    }
}

class HomeModule(private val plugin: Plugin) : Module {
    override fun load() {
        getMessageModule(plugin, ModuleTypes.HomeModule);
        plugin.server.commandMap.register("ryoko", Home(plugin));
        plugin.server.commandMap.register("ryoko", HomeSet(plugin));
        plugin.server.commandMap.register("ryoko", HomeDel(plugin));
        plugin.logger.info(">>= Home module");
    }

    override fun unload() {
        TODO("Not yet implemented")
    }
}

class SocialModule(private val plugin: Plugin) : Module {
    override fun load() {
        getMessageModule(plugin, ModuleTypes.SocialModule);
        plugin.server.commandMap.register("ryoko", DirectMessage(plugin));
        plugin.logger.info(">>= Social module");
    }

    override fun unload() {
        TODO("Not yet implemented")
    }
}

class SpawnModule(private val plugin: Plugin) : Module {
    override fun load() {
        getMessageModule(plugin, ModuleTypes.SpawnModule);
        plugin.server.commandMap.register("ryoko", SpawnSet(plugin));
        YamlProvider(plugin).loadSourceFile("spawn-config");
        plugin.logger.info(">>= Spawn module");
    }

    override fun unload() {
        TODO("Not yet implemented")
    }
}

class TpaModule(private val plugin: Plugin) : Module {
    override fun load() {
        getMessageModule(plugin, ModuleTypes.TpaModule);
        plugin.server.commandMap.register("ryoko", TpaRequest(plugin));
        plugin.server.commandMap.register("ryoko", TpaAccept(plugin));
        plugin.server.commandMap.register("ryoko", TpaReject(plugin));
        plugin.logger.info(">>= TPA module");
    }

    override fun unload() {
        TODO("Not yet implemented")
    }
}

class WarpModule(private val plugin: Plugin) : Module {
    override fun load() {
        getMessageModule(plugin, ModuleTypes.WarpModule);
        plugin.server.commandMap.register("ryoko", Warp(plugin));
        plugin.server.commandMap.register("ryoko", WarpSet(plugin));
        plugin.server.commandMap.register("ryoko", WarpDel(plugin));
        plugin.logger.info(">>= Warp module");
    }

    override fun unload() {
        TODO("Not yet implemented")
    }
}

private fun getMessageModule(plugin: Plugin, key: String) {
    val messagesFile = YamlProvider(plugin).getFile("messages");

    val moduleSection = requireNotNull(messagesFile.getConfigurationSection(key)) {
        plugin.logger.warning("Module `$key` wasn't found!");
        return;
    };

    val moduleKeys = moduleSection.getKeys(false);

    moduleKeys.forEach { moduleMessageKey ->
        val messageContent = requireNotNull(moduleSection.getString(moduleMessageKey)) {
            return@forEach;
        };

        MessageProvider.addMessage(moduleMessageKey, messageContent);
    }

}