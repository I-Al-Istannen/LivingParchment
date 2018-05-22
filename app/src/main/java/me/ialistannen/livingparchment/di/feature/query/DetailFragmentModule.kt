package me.ialistannen.livingparchment.di.feature.query

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.ialistannen.livingparchment.di.FragmentScope
import me.ialistannen.livingparchment.feature.query.detail.BookDetailFragment
import me.ialistannen.livingparchment.feature.query.detail.BookDetailFragmentContract
import me.ialistannen.livingparchment.feature.query.detail.BookDetailPresenter


@Module
abstract class DetailFragmentProvider {

    @FragmentScope
    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    abstract fun bindDetailFragment(): BookDetailFragment
}

@Module
abstract class DetailFragmentModule {

    @Binds
    abstract fun bindDetailPresenter(detailPresenter: BookDetailPresenter): BookDetailFragmentContract.Presenter

    @Binds
    abstract fun bindDetailView(bookDetailFragment: BookDetailFragment): BookDetailFragmentContract.View
}