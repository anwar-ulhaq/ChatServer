/***
 * @Title
 * TopChatter object keeps record of top 4 chat user with respect to No of messages and print statistics on Server console
 *
 * @author
 * Anwar Ulhaq          1706206
 */

import java.util.concurrent.ConcurrentHashMap

object TopChatter: ChatHistoryObserver {
    private var userName: String = String()
    //Map of (userName,No of Messages)
    private var usersMap = ConcurrentHashMap<String, Int>()

    init {
        ChatHistory.registerObserver(this)
    }

    //Writes to console list of active users including the number of messages sent every time the list changes
    @Synchronized override fun newMessage(message: ChatMessage) {
        println("TopChatter: ")
        //Get list of Active users from Users object
        val activeUser = Users.userList
        //Break chat message in to a List of String
        val messageAsListOfString = message.chatMessageWithoutTimeStamp.split(" ")
        //Last string in message is a username
        userName = messageAsListOfString.last()

        //If message is form System, Exclude it form Top chatter
        if (userName == "System")
        {
        }else {
            // if usermap contains a name that is in Active user list
            if (usersMap.containsKey(userName)) {
                // get current no of messages value, add One to it and put value back
                usersMap[userName] = ((usersMap[userName]!! + 1))
            } else {
                //If not, add user to map with message count 1
                usersMap[userName] = 1
            }

            //Remove inactive users from map
            //Iterate through userMap. If pair has name that is not in userHashSet, remove that user from userMap
            for (pair in usersMap) {
                if (!activeUser.contains(pair.key)) {
                    usersMap.remove(pair.key)
                } else {
                    //Do nothing
                }
            }

            //Convert the map into a List so that it can be sorted out. Map object can not be sorted
            val listOfSortedPairs = usersMap.entries.map { it.toPair() }.sortedByDescending { it.second }.take(4)
            println("User Name \t\t\t\t No. of Messages")
            listOfSortedPairs.forEach { println("${it.first} \t\t\t\t ${it.second}") }
        }
    }
}