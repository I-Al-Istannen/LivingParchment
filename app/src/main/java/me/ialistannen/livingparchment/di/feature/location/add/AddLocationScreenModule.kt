package me.ialistannen.livingparchment.di.feature.location.add

import dagger.Binds
import dagger.Module
import me.ialistannen.livingparchment.feature.location.add.AddLocationPresenter
import me.ialistannen.livingparchment.feature.location.add.ManageBookLocationActivity
import me.ialistannen.livingparchment.feature.location.add.ManageBookLocationContract

@Module
abstract class AddLocationScreenModule {

    @Binds
    abstract fun bindView(manageBookLocationActivity: ManageBookLocationActivity): ManageBookLocationContract.View

    @Binds
    abstract fun bindPresenter(addLocationPresenter: AddLocationPresenter): ManageBookLocationContract.Presenter
}