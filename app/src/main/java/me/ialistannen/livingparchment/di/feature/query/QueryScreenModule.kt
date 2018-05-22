package me.ialistannen.livingparchment.di.feature.query

import dagger.Binds
import dagger.Module
import me.ialistannen.livingparchment.feature.query.BookQueryActivity
import me.ialistannen.livingparchment.feature.query.QueryScreenContract
import me.ialistannen.livingparchment.feature.query.QueryScreenPresenter

@Module
abstract class QueryScreenModule {

    @Binds
    abstract fun bindPresenter(queryScreenPresenter: QueryScreenPresenter): QueryScreenContract.Presenter

    @Binds
    abstract fun bindView(queryActivity: BookQueryActivity): QueryScreenContract.View
}