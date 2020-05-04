/***
 * @Title
 * Console for displaying all messages on Server
 *
 * @author
 * Anwar Ulhaq          1706206
 */
class ChatConsole: ChatHistoryObserver {
    //Registers as an observer to ChatHistory
    init {
        ChatHistory.registerObserver(this)
    }

    /**
     * Prints out to System.out all chat messages in the conversation
     */
    override fun newMessage(message: ChatMessage) {
        println("Chat Console")
        println(message.chatMessageWithoutTimeStamp)
    }
}
