package me.ialistannen.livingparchment.feature.edit

import me.ialistannen.livingparchment.feature.BasePresenter
import javax.inject.Inject

class EditScreenPresenter @Inject constructor(
        private val view: EditScreenContract.View
) : BasePresenter(), EditScreenContract.Presenter