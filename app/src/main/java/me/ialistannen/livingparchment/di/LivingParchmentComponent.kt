package me.ialistannen.livingparchment.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.ialistannen.livingparchment.LivingParchmentApplication

@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ActivityBuilder::class
])
@ApplicationScope
interface LivingParchmentComponent : AndroidInjector<LivingParchmentApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<LivingParchmentApplication>()
}