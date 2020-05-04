/***
 * @Title
 * Users object maintains a list of users
 *
 * @author
 * Anwar Ulhaq          1706206
 */

object Users {
    val userList = HashSet<String>()

    /**
     * Adds a user if it is not already existed and returns a True.
     * If user exists, this function return False.
     */
    @Synchronized fun addUser(newUser: String): Boolean {
        return if (userList.contains(newUser)) {
            false
        } else {
            //User name not in userList, create a user and return True
            userList.add(newUser)
            true
        }
    }

    /***
     * Removes a user
     */
    @Synchronized fun removeUser(remUser: String) {
        userList.remove(remUser)
    }

    /**
     * Prints all user in String format
     */
    @Synchronized override fun toString(): String {
        //Fold implementation
        return userList.fold("Users: ,") {allName, nextName  -> allName + nextName + ","}
    }
}