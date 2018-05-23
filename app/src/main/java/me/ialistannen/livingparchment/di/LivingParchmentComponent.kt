package me.ialistannen.livingparchment.di

import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.ialistannen.livingparchment.LivingParchmentApplication
import me.ialistannen.livingparchment.di.feature.WebrequestsModule

@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityBuilder::class,
    WebrequestsModule::class
])
@ApplicationScope
interface LivingParchmentComponent : AndroidInjector<LivingParchmentApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<LivingParchmentApplication>()
}

@Module
class ApplicationModule {

    @ApplicationScope
    @Provides
    fun provideContext(application: LivingParchmentApplication): Context = application.applicationContext
}