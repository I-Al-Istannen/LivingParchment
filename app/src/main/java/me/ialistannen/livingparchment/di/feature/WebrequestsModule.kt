package me.ialistannen.livingparchment.di.feature

import dagger.Module
import dagger.Provides
import me.ialistannen.livingparchment.request.ServerConfig

@Module
class WebrequestsModule {

    @Provides
    fun provideServerConfig(): ServerConfig {
        return ServerConfig("http://192.168.188.72:8080")
    }
}