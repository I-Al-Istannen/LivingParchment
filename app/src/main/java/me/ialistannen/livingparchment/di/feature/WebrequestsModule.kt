package me.ialistannen.livingparchment.di.feature

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import me.ialistannen.livingparchment.di.ApplicationScope
import me.ialistannen.livingparchment.request.ServerConfig
import me.ialistannen.livingparchment.request.auth.UserCredentials

@Module
class WebrequestsModule {

    @ApplicationScope
    @Provides
    fun provideServerConfig(context: Context): ServerConfig {
        return ServerConfig() {
            PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
                    .getString("server_url", "http://192.168.188.72:8080")
        }
    }

    @ApplicationScope
    @Provides
    fun provideUserCredentials(context: Context): UserCredentials {
        return UserCredentials(
                { getStringFromPreferences(context, "settings_server_username", "") },
                { getStringFromPreferences(context, "settings_server_password", "") }
        )
    }

    private fun getStringFromPreferences(context: Context, key: String, default: String): String {
        return PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
                .getString(key, default)
    }
}