package kimu.klossom.ryoko.providers

object MessageProvider {
    private var messageList = HashMap<String, String>();

    fun setMessages(messages: HashMap<String, String>) {
        messageList = messages;
    }

    fun getMessage(identifier: String): String {
        return messageList[identifier] ?: "Message not found, contact an admin (they messed up something)";
    }
}