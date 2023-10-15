package kimu.klossom.ryoko.providers

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

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

    fun saveFile(file: String, yamlData: YamlConfiguration) {
        val yamlFile = File(plugin.dataFolder, "${file}.yml");
        yamlData.save(yamlFile);
    }
}