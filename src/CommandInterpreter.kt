/***
 * @Title
 * CommandInterpreter class creates a command line interface for user where commands and message are entered.
 * It also display messages from other user in chat group
 *
 * @author
 * Anwar Ulhaq          1706206
 */

import java.io.InputStream
import java.io.PrintStream
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern.matches

class CommandInterpreter(private val inputStream: InputStream?, private val out: PrintStream?): Runnable, ChatHistoryObserver {

    /***
     * Print message to the console of the user send by other chat users
     */
    override fun newMessage(message: ChatMessage) {
        //out?.println()
        out?.println(message.chatMessageWithoutTimeStamp)
    }

    private var chatUser: String = String()

    override fun run() {
        try {
            //Display banner to user
            //banner()

            val userInput = Scanner(inputStream)
            var stayInLoopForCommand = true
            var stayInLoopForMessages = true

            /*
            User Creation and Valid Command format check
            Loop runs until user is created.
            */
            do {
                //User prompt
                //out?.print("=> ")
                val command = userInput.nextLine()
                if(command.startsWith(":")) {
                    if (command.startsWith(":user")) {
                        val commandAsListOfStrings = command.split(" ")
                        println(commandAsListOfStrings)
                        if (commandAsListOfStrings.size == 2 && commandAsListOfStrings[1].isNotBlank()) {
                            chatUser = commandAsListOfStrings[1]

                            /**
                             * User.addUser adds a user if it is not already existed and returns a True.
                             * If user exists, this function return False.
                             */
                            if ( Users.addUser(chatUser) ) {
                                out?.println("Username set to  $chatUser" )
                                //Register this instance of CommandInterpreter in ChatHistoryObservable
                                ChatHistory.insert(ChatMessage("$chatUser join chat", "System", LocalDateTime.now()))
                                ChatHistory.registerObserver(this)
                                // Exit User creation loop
                                stayInLoopForCommand = false
                            } else {
                                out?.println("Username already in use. Try Something else again")
                            }
                        } else {
                            out?.println("User name not set: no user name specified")
                        }
                    } else if (command.startsWith(":quit"))
                    {
                        stayInLoopForCommand = false
                        stayInLoopForMessages = false
                    }
                    else
                    {
                        out?.println("User name not set. Use command :user to set it")
                    }

                } else {
                    out?.println("User name not set. Use command :user to set it")
                }
            } while(stayInLoopForCommand)

            //Once user is created, a loop that keeps user in chat group
            //User either enter messages or commands.
            //Loops runs until user enters :quit command

            while (stayInLoopForMessages) {
                //out?.print("=> ")
                val userMessage = userInput.nextLine()
                val currentTime = LocalDateTime.now()

                if(userMessage.startsWith(":")) {
                    //It is a command
                    when (userMessage) {
                        ":messages" -> messageHistory()
                        ":users" -> listUsers()
                        ":quit" -> {
                            //deregister this CommandInterpreter
                            ChatHistory.deregisterObserver(this)
                            //Remove User
                            Users.removeUser(chatUser)
                            //Notify all users
                            ChatHistory.insert(ChatMessage("$chatUser left chat", "System", currentTime))
                            //quit Loop
                            stayInLoopForMessages = false
                        }
                        else -> out?.println("Did not get it $userMessage")
                    }
                } else {
                    //It is a message
                    val thisChatMessage = ChatMessage(userMessage, chatUser, currentTime)
                    ChatHistory.insert(thisChatMessage)
                }
            }

            //Close Input and Output Stream
            inputStream?.close()
            out?.close()

        } catch (e: Exception) {
            println("Got exception: ${e.message}")
        } finally {
        }
    }

    /**
     * Display all messages to user
     */
    private fun messageHistory() {
        out?.println(ChatHistory)
    }

    /**
     * Displays all users in Chat Group
     */
    private fun listUsers() {
        out?.println(Users)
    }

    /**
     * Display Banner for user
     */
    private fun banner() {
        out?.println("*********************************************************")
        out?.println("*             Welcome To 2018 Chat Server              \t*")
        out?.println("*             Please, enter your command               \t*")
        out?.println("*******************************************************\t*")
        out?.println("* Accepted commands and there syntax are as follow    \t*")
        out?.println("* \":user xyz\" Create a user with xyz name.            \t*")
        out?.println("* \":users\" Display list of active users               \t*")
        out?.println("* \":messages\" will display chat history               \t*")
        out?.println("* \":quit\" to exit from chat group                     \t*")
        out?.println("*                                                      \t*")
        out?.println("* NOTE:                                               \t*")
        out?.println("* Command are entered without quotation marks \"\"        *")
        out?.println("* :users and :message only works inside Chatroom        *")
        out?.println("*********************************************************")
        out?.println()
    }
}
