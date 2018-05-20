package me.ialistannen.livingparchment.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.ialistannen.livingparchment.feature.mainscreen.MainActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}