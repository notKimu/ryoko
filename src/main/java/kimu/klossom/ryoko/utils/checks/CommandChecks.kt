package kimu.klossom.ryoko.utils.checks

import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandChecks {
    fun checkArgsSize(size: Int, args: Array<out String>?): Boolean {
        return !(args == null || args.size != 1);
    }

    fun isSenderPlayer(sender: CommandSender): Player? {
        if (sender !is Player) {
            return null;
        }

        return sender as Player;
    }

    fun isPlayerOnline(server: Server, playerName: String): Player? {
        val onlinePlayer = requireNotNull(server.getPlayer(playerName)) {
            return null;
        }

        return onlinePlayer;
    }
}
