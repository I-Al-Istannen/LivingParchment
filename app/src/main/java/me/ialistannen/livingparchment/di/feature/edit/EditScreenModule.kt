package me.ialistannen.livingparchment.di.feature.edit

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.ialistannen.livingparchment.di.FragmentScope
import me.ialistannen.livingparchment.feature.edit.EditScreenContract
import me.ialistannen.livingparchment.feature.edit.EditScreenFragment
import me.ialistannen.livingparchment.feature.edit.EditScreenPresenter


@Module
abstract class EditFragmentProvider {

    @FragmentScope
    @ContributesAndroidInjector(modules = [EditFragmentModule::class])
    abstract fun bindEditFragment(): EditScreenFragment
}

@Module
abstract class EditFragmentModule {

    @Binds
    abstract fun bindEditPresenter(editScreenPresenter: EditScreenPresenter): EditScreenContract.Presenter

    @Binds
    abstract fun bindEditView(editScreenFragment: EditScreenFragment): EditScreenContract.View
}