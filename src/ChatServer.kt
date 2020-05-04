/***
 * @Title
 * ChatServer class creates a socket and accepts incoming connection from user.
 * A separate Thread is created for every user.
 *
 * @author
 * Anwar Ulhaq          1706206
 */

import java.io.PrintStream
import java.net.ServerSocket

class ChatServer {
    fun serve() {
        try {
            val serverSocket = ServerSocket(55551,3)
            println("Listening to port No:  ${serverSocket.localPort} " )
            while(true) {
                println("Waiting for Incoming connection.......")
                val acceptedServerSocket = serverSocket.accept()
                println("Connection established, Starting new session for chat user...")
                //Input Stream
                val inputStream = acceptedServerSocket.getInputStream()
                println("Session Details: " + acceptedServerSocket.inetAddress.hostAddress + " : " + acceptedServerSocket.port)
                //Output Stream
                val outputStream = PrintStream(acceptedServerSocket.getOutputStream(), true)
                //Starts a new Thread for CommandInterpreter
                val processThread = Thread(CommandInterpreter(inputStream, outputStream))
                processThread.start()
            }
        } catch (e: Exception) {
            println("Got exception: ${e.message}")
        } finally {
            println("Connection established successfully")
        }
    }
}