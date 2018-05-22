package me.ialistannen.livingparchment.di.feature.query

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.ialistannen.livingparchment.di.FragmentScope
import me.ialistannen.livingparchment.feature.query.list.BookListFragment
import me.ialistannen.livingparchment.feature.query.list.BookListFragmentContract
import me.ialistannen.livingparchment.feature.query.list.BookListPresenter


@Module
abstract class ListFragmentProvider {

    @FragmentScope
    @ContributesAndroidInjector(modules = [ListFragmentModule::class])
    abstract fun bindListFragment(): BookListFragment
}

@Module
abstract class ListFragmentModule {

    @Binds
    abstract fun bindPresenter(bookListPresenter: BookListPresenter): BookListFragmentContract.Presenter

    @Binds
    abstract fun bindView(bookListFragment: BookListFragment): BookListFragmentContract.View
}