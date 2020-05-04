/***
 * @Title
 * Starting point of application
 *
 * @author
 * Anwar Ulhaq          1706206
 */
fun main(args: Array<String>) {

    //Make a Chat console
    ChatConsole()

    //User Statistics
    TopChatter

    //Start Chat server
    ChatServer().serve()
}