package me.ialistannen.livingparchment.di.feature.add

import dagger.Binds
import dagger.Module
import me.ialistannen.livingparchment.feature.add.AddScreenContract
import me.ialistannen.livingparchment.feature.add.AddScreenPresenter
import me.ialistannen.livingparchment.feature.add.BookAddActivity

@Module
abstract class AddScreenModule {

    @Binds
    abstract fun bindPresenter(addScreenPresenter: AddScreenPresenter): AddScreenContract.Presenter

    @Binds
    abstract fun bindView(bookAddActivity: BookAddActivity): AddScreenContract.View
}