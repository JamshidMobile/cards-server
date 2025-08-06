package jtoir.uz.lib.utils

class Constants {
    object Role{
        const val ADMIN = "ADMIN"
        const val CLIENT = "CLIENT"
        const val MODERATOR = "MODERATOR"
    }

    object Error{
        const val GENERAL = "Oh, something went wrong!"
        const val WRONG_EMAIL = "Wrong email address!"
        const val INCORRECT_PASSWORD = "Incorrect password!"
        const val MISSING_FIELDS = "Missing required fields!"
        const val USER_NOT_FOUND = "User not found!"
    }

    object Success{
        const val CARD_ADDED_SUCCESSFULLY = "Card added successfully!"
        const val CARD_CHANGED_SUCCESSFULLY = "Card changed successfully!"
        const val CARD_DELETED_SUCCESSFULLY = "Card deleted successfully!"
    }

    object Value{
        const val ID= "id"
    }
}