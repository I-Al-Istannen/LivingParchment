package me.ialistannen.livingparchment.feature.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.common.serialization.fromJson
import me.ialistannen.livingparchment.feature.BaseFragment
import javax.inject.Inject

class EditScreenFragment : BaseFragment(), EditScreenContract.View {

    @Inject
    override lateinit var presenter: EditScreenContract.Presenter

    private var book: Book? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_book_edit, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        if (arguments.containsKey("book")) {
            book = arguments.getString("book").fromJson()
        }
    }
}