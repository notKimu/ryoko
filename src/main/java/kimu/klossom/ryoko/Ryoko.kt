package kimu.klossom.ryoko

import kimu.klossom.ryoko.checks.MainChecks
import org.bukkit.plugin.java.JavaPlugin
import kotlin.system.measureTimeMillis

class Ryoko : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        logger.info("    _____");
        logger.info(" __|__   |___");
        logger.info("|     |      |   Ryoko v0.1");
        logger.info("|     \\      |   Running ${server.version}");
        logger.info("|      \\     |");
        logger.info("|__|\\___\\ ___|");
        logger.info("   |_____|");
        logger.info("");

        val measureLoadingTime = measureTimeMillis {
            logger.info("Checking config...");
            saveDefaultConfig();

            logger.info("Loading modules...");
            MainChecks(this).moduleLoader();
        }

        logger.info("Ryoko started succexfully! >>= ${measureLoadingTime}ms");
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("Going to sleep now... bye!");
    }
}
