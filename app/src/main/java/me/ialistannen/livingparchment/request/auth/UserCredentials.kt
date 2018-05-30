package me.ialistannen.livingparchment.request.auth

class UserCredentials(
        private val nameProvider: () -> String,
        private val passwordProvider: () -> String
) {

    val name
        get() = nameProvider.invoke()

    val password
        get() = passwordProvider.invoke()
}