package me.ialistannen.livingparchment.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.ialistannen.livingparchment.di.feature.add.AddScreenModule
import me.ialistannen.livingparchment.di.feature.delete.DeleteScreenModule
import me.ialistannen.livingparchment.di.feature.query.QueryScreenModule
import me.ialistannen.livingparchment.feature.add.BookAddActivity
import me.ialistannen.livingparchment.feature.delete.BookDeleteActivity
import me.ialistannen.livingparchment.feature.mainscreen.MainActivity
import me.ialistannen.livingparchment.feature.query.BookQueryActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [AddScreenModule::class])
    abstract fun bindBookAddActivity(): BookAddActivity

    @ContributesAndroidInjector(modules = [DeleteScreenModule::class])
    abstract fun bindBookDeleteActivity(): BookDeleteActivity

    @ContributesAndroidInjector(modules = [QueryScreenModule::class])
    abstract fun bindBookQueryActivity(): BookQueryActivity
}