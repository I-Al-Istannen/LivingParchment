package me.ialistannen.livingparchment.di.feature.delete

import dagger.Binds
import dagger.Module
import me.ialistannen.livingparchment.feature.delete.BookDeleteActivity
import me.ialistannen.livingparchment.feature.delete.DeleteScreenContract
import me.ialistannen.livingparchment.feature.delete.DeleteScreenPresenter

@Module
abstract class DeleteScreenModule {

    @Binds
    abstract fun bindPresenter(deleteScreenPresenter: DeleteScreenPresenter): DeleteScreenContract.Presenter

    @Binds
    abstract fun bindView(bookDeleteActivity: BookDeleteActivity): DeleteScreenContract.View
}