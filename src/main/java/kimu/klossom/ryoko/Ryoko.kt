package kimu.klossom.ryoko

import kimu.klossom.ryoko.commands.messages.SendPrivateMessage
import kimu.klossom.ryoko.commands.tpa.TpaAccept
import kimu.klossom.ryoko.commands.tpa.TpaRequest
import kimu.klossom.ryoko.commands.warps.DelWarp
import kimu.klossom.ryoko.commands.warps.SetWarp
import kimu.klossom.ryoko.commands.warps.Warp
import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.YamlProvider
import kimu.klossom.ryoko.utils.checks.MessagesCheck
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Ryoko : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        logger.info("_______   ___  ___  ______    __   ___   ______");
        logger.info("/\"      \\ |\"  \\/\"  |/    \" \\  |/\"| /  \") /    \" \\");
        logger.info("|:        | \\   \\  /// ____  \\ (: |/   / // ____  \\  ");
        logger.info("|_____/   )  \\\\  \\//  /    ) :)|    __/ /  /    ) :)");
        logger.info("//      /   /   /(: (____/ // (// _  \\(: (____/ //");
        logger.info("|:  __   \\  /   /  \\        /  |: | \\  \\\\        /");
        logger.info("|__|  \\___)|___/    \\\"_____/   (__|  \\__)\\\"_____/");
        logger.info(">> Running on V0.1!");

        logger.info("Checking config...");
        saveDefaultConfig();

        logger.info("Caching messages...");
        MessagesCheck(this).startupCheck();

        logger.info("Registering commands...");
        server.commandMap.register("", SendPrivateMessage(this));

        server.commandMap.register("", DelWarp(this));
        server.commandMap.register("", SetWarp(this));
        server.commandMap.register("", Warp(this));

        server.commandMap.register("", TpaAccept(this));
        server.commandMap.register("", TpaRequest(this));

        logger.info("Loaded succexfully!");
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("Going to sleep now... bye!");
    }
}
