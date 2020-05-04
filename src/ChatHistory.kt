/***
 * @Title
 * ChatHistory is an object that maintains a log of all messages sent by users.
 * It also notifies all users when new messages is arrive in history
 *
 * @author
 * Anwar Ulhaq          1706206
 */
import java.util.*

object ChatHistory : ChatHistoryObservable{
    //A Set of Observer (CommandInterpreter)
    private val setOfObserver = mutableSetOf<ChatHistoryObserver>()
    //Array of ChatMessage objects
    private val chatHistory = ArrayList<ChatMessage>()

    /**
     * Register or Add observer in order to receive update
     */
    @Synchronized override fun registerObserver(observer: ChatHistoryObserver){
        setOfObserver.add(observer)
    }

    /***
     * Remove observer
     */
    @Synchronized override fun deregisterObserver(observer: ChatHistoryObserver){
        setOfObserver.remove(observer)
    }

    /**
     * Update all observers
     *
     */
    @Synchronized override fun notifyObservers(message: ChatMessage){
        for (observer in setOfObserver)
        {
           observer.newMessage(message)
        }
    }

    /**
     * Insert or save a message in ChatHistory object
     * At the same time notify all Observers about new message
     */
    @Synchronized fun insert(message: ChatMessage){
        chatHistory.add(message)
        notifyObservers(message)
    }

    /**
     * print ChatHistory in String Format
     */
    @Synchronized override fun toString(): String{
        //Fold Implementation
        return chatHistory.fold( "History: ,") { allMessages, nextMessage -> allMessages + nextMessage + "," }
    }
}