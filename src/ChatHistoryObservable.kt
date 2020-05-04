/**
 * @Title
 * Interface Observable for ChatHistory object
 *
 * @author
 * Anwar Ulhaq          1706206
 */
interface ChatHistoryObservable {
    fun registerObserver(observer:ChatHistoryObserver)
    fun deregisterObserver(observer:ChatHistoryObserver)
    fun notifyObservers (message:ChatMessage)
}