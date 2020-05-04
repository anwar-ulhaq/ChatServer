/***
 * @Title
 * ChatMessage class creates message object that stores message, username and time when message were entered
 *
 * @author
 * Anwar Ulhaq          1706206
 */
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatMessage(private val userMessage: String, private val user: String, private val time: LocalDateTime) {
    // Message without time stamp
    val chatMessageWithoutTimeStamp get() = "$userMessage from $user"

    /**
     * Print ChatMessage object
     */
    override fun toString(): String {
        return "[ ${time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))} ] $userMessage from $user "
    }
}
