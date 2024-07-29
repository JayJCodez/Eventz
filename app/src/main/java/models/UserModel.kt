package models

class UserModel {
    private var username: String = ""
    private var firstName: String = ""
    private var lastName: String = ""




    // Setter methods
    fun setUsername(username: String) {
        this.username = username
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }



    // Getter methods
    fun getUsername(): String {
        return username
    }

    fun getFirstName(): String {
        return firstName
    }

    fun getLastName(): String {
        return lastName
    }



}