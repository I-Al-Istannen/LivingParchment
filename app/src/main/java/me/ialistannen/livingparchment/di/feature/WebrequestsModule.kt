package me.ialistannen.livingparchment.di.feature

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import me.ialistannen.livingparchment.request.ServerConfig

@Module
class WebrequestsModule {

    @Provides
    fun provideServerConfig(context: Context): ServerConfig {
        return ServerConfig(
                PreferenceManager.getDefaultSharedPreferences(context)
                        .getString("server_url", "http://192.168.188.72:8080")
        )
    }
}