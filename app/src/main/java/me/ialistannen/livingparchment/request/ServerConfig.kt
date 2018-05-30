package me.ialistannen.livingparchment.request

class ServerConfig(private val urlProvider: () -> String) {

    val url
        get() = urlProvider.invoke()
}