package kimu.klossom.ryoko.providers

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.IOException
import java.nio.file.Files

class YamlProvider(private val plugin: Plugin) {
    fun load(fileNames: List<String>) {
        fileNames.forEach { file ->
            val yamlFile = File(plugin.dataFolder, "${file}.yml");
            val configFile = YamlConfiguration.loadConfiguration(yamlFile);

            configFile.save(yamlFile);
        }
    }

    fun getFile(file: String): YamlConfiguration {
        val yamlFile = File(plugin.dataFolder, "${file}.yml");
        val configFile = YamlConfiguration.loadConfiguration(yamlFile);

        return configFile;
    }

    fun set(file: String, key: String, value: Any?) {
        val yamlFile = File(plugin.dataFolder, "${file}.yml");
        val configFile = YamlConfiguration.loadConfiguration(yamlFile);

        configFile.set(key, value);

        configFile.save(yamlFile);
    }

    fun getKeys(file: String): MutableSet<String> {
        val yamlFile = File(plugin.dataFolder, "${file}.yml");

        val yamlInputStream = yamlFile.inputStream();
        val keyValueList = Yaml().load<HashMap<String, Any>>(yamlInputStream);

        return keyValueList.keys;
    }

    fun loadSourceFile(name: String) {
        val yamlProvider = YamlProvider(plugin);
        val configFile = File(plugin.dataFolder, "$name.yml");

        val defaultMessagesInputStream = requireNotNull(plugin.getResource("messages.yml")) {
            plugin.logger.severe("File $name.yml does not exist on the plugin's source!")
            return;
        };

        try {
            Files.copy(defaultMessagesInputStream, configFile.toPath());
        } catch (e: IOException) {
            plugin.logger.severe("Error creating th $name.yml\n" + e.message);
            e.printStackTrace();
        }

        plugin.logger.info("Created file $name.yml");
    }

    fun save(file: YamlConfiguration) {
        val yamlFile = File(plugin.dataFolder, "${file.name}.yml");
        file.save(yamlFile);
    }
}