package kimu.klossom.ryoko.providers

object MessageProvider {
    private var messageList = HashMap<String, String>();

    fun addMessage(key: String, value: String) {
        messageList[key] = value;
    }

    fun getMessage(identifier: MessageType): String {
        return messageList[identifier.key] ?: "Message not found, contact an admin (they messed up something)";
    }

    fun removeMessages(messages: HashMap<String, String>) {
        messages.forEach { (key, _) ->
            messageList.remove(key);
        }
    }
}

enum class MessageType(val key: String) {
    PlayerOnlyCommand("player-only-command"),

    PlayerNotOnline("player-not-online"),
    PlayerRequestedSelf("player-requested-self"),

    DirectMessagePrefix("direct-message-prefix"),

    UnsecureTeleportLocation("teleport-unsecure-location"),

    Warping("warping"),
    WarpSet("warp-set"),
    WarpList("warp-list"),
    WarpListEmpty("warp-list-empty"),
    WarpNotFound("warp-not-found"),

    HomeTeleporting("home-teleporting"),
    HomeSet("home-set"),
    HomeDelete("home-delete"),
    HomeList("home-list"),
    HomeListEmpty("home-list-empty"),
    HomeNotFound("home-not-found"),

    TpaAccepted("tpa-accepted"),
    TpaIncoming("tpa-incoming"),
    TpaRejected("tpa-rejected"),
    TpaGotRejected("tpa-got-rejected"),
    TpaRequested("tpa-requested"),
    TpaAcceptNonExistent("tpa-accept-non-existent"),
    TpaRejectNonExistent("tpa-reject-non-existent"),
    TpaAcceptedSelf("tpa-accepted-self"),
    TpaRejectedSelf("tpa-rejected-self"),

    SpawnPointSet("spawn-point-set");
}