package kimu.klossom.ryoko.providers

object MessageProvider {
    private var messageList = HashMap<String, String>();

    fun setMessages(messages: HashMap<String, String>) {
        messageList = messages;
    }

    fun getMessage(identifier: MessageType): String {
        return messageList[identifier.key] ?: "Message not found, contact an admin (they messed up something)";
    }
}

enum class MessageType(val key: String) {
    PlayerOnlyCommand("player-only-command"),

    PlayerNotOnline("player-not-online"),
    PlayerRequestedSelf("player-requested-self"),

    DirectMessagePrefix("direct-message-prefix"),
    DirectMessageToSelf("direct-message-to-self"),

    UnsecureTeleportLocation("teleport-unsecure-location"),

    Warping("warping"),
    WarpSet("warp-set"),
    WarpList("warp-list"),
    WarpListEmpty("warp-list-empty"),
    WarpNotFound("warp-not-found"),

    TpaAccepted("tpa-accepted"),
    TpaIncoming("tpa-incoming"),
    TpaRejected("tpa-rejected"),
    TpaGotRejected("tpa-got-rejected"),
    TpaRequested("tpa-requested"),
    TpaAcceptNonExistent("tpa-accept-non-existent"),
    TpaRejectNonExistent("tpa-reject-non-existent"),
    TpaAcceptedSelf("tpa-accepted-self"),
    TpaRejectedSelf("tpa-rejected-self");
}