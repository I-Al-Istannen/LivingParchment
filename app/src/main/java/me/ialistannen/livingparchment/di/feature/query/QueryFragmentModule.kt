package me.ialistannen.livingparchment.di.feature.query

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.ialistannen.livingparchment.di.FragmentScope
import me.ialistannen.livingparchment.feature.query.query.QueryFragment
import me.ialistannen.livingparchment.feature.query.query.QueryFragmentContract
import me.ialistannen.livingparchment.feature.query.query.QueryFragmentPresenter

@Module
abstract class QueryFragmentProvider {

    @FragmentScope
    @ContributesAndroidInjector(modules = [QueryFragmentModule::class])
    abstract fun bindQueryFragment(): QueryFragment
}

@Module
abstract class QueryFragmentModule {

    @Binds
    abstract fun bindPresenter(queryFragmentPresenter: QueryFragmentPresenter): QueryFragmentContract.Presenter

    @Binds
    abstract fun bindView(queryFragment: QueryFragment): QueryFragmentContract.View
}