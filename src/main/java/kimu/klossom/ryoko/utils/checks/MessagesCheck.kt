package kimu.klossom.ryoko.utils.checks

import kimu.klossom.ryoko.providers.MessageProvider
import kimu.klossom.ryoko.providers.YamlProvider
import org.bukkit.plugin.Plugin
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Files

class MessagesCheck(private val plugin: Plugin) {
    fun startupCheck() {
        val yamlProvider = YamlProvider(plugin);
        val messagesFile = File(plugin.dataFolder, "messages.yml");

        if (messagesFile.exists()) {
            val messagesInputStream = FileInputStream(messagesFile);
            val messagesHashMap = Yaml().load<HashMap<String, String>>(messagesInputStream);
            MessageProvider.setMessages(messagesHashMap);

        } else {
            val defaultMessagesInputStream = requireNotNull(plugin.getResource("messages.yml")) {
                plugin.logger.severe("THE MESSAGES.YML FILE IS MISSINGGGGGG WTF HAPPENED TO THE JAR FILE!?!?!!1?")
                return;
            };

            try {
                Files.copy(defaultMessagesInputStream, messagesFile.toPath());
            } catch (e: IOException) {
                plugin.logger.severe("Error creating the messages file:\n" + e.message);
                e.printStackTrace();
            }

            val messagesInputStream = FileInputStream(File(plugin.dataFolder, "messages.yml"));
            val messagesHashMap = Yaml().load<HashMap<String, String>>(messagesInputStream);
            MessageProvider.setMessages(messagesHashMap);
        }
    }
}