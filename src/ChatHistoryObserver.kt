/**
 * @Title
 * Interface Observer for CommandInterpreter class
 *
 * @author
 * Anwar Ulhaq          1706206
 */
interface ChatHistoryObserver {
    fun newMessage(message:ChatMessage)
}