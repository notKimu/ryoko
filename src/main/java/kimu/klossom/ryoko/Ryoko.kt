package kimu.klossom.ryoko

import kimu.klossom.ryoko.commands.homes.HomeSet
import kimu.klossom.ryoko.commands.messages.DirectMessage
import kimu.klossom.ryoko.commands.tpa.TpaAccept
import kimu.klossom.ryoko.commands.tpa.TpaReject
import kimu.klossom.ryoko.commands.tpa.TpaRequest
import kimu.klossom.ryoko.commands.warps.Warp
import kimu.klossom.ryoko.commands.warps.WarpDel
import kimu.klossom.ryoko.commands.warps.WarpSet
import kimu.klossom.ryoko.utils.checks.MessagesCheck
import org.bukkit.plugin.java.JavaPlugin

class Ryoko : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        logger.info("    _____");
        logger.info(" __|__   |___");
        logger.info("|     |      |    Ryoko v0.1");
        logger.info("|     \\      |    Running ${server.version}");
        logger.info("|     \\      |");
        logger.info("|__|\\__\\  ___|");
        logger.info("   |_____|");
        logger.info("");

        logger.info("Checking config...");
        saveDefaultConfig();

        logger.info("Caching messages...");
        MessagesCheck(this).startupCheck();

        logger.info("Registering commands...");
        server.commandMap.register("ryoko", DirectMessage(this));

        server.commandMap.register("ryoko", Warp(this));
        server.commandMap.register("ryoko", WarpSet(this));
        server.commandMap.register("ryoko", WarpDel(this));

        server.commandMap.register("ryoko", TpaRequest(this));
        server.commandMap.register("ryoko", TpaAccept(this));
        server.commandMap.register("ryoko", TpaReject(this));

        server.commandMap.register("ryoko", HomeSet(this));

        logger.info("Loaded succexfully!");
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("Going to sleep now... bye!");
    }
}
