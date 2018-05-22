package me.ialistannen.livingparchment.feature.query.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_book_query.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.api.query.QueryType
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseFragment
import me.ialistannen.livingparchment.feature.query.QueryNavigator
import javax.inject.Inject

class QueryFragment : BaseFragment(), QueryFragmentContract.View {

    @Inject
    override lateinit var presenter: QueryFragmentContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = inflater.inflate(R.layout.fragment_book_query, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        query_type_spinner.adapter = ArrayAdapter<QueryType>(activity,
                android.R.layout.simple_spinner_item,
                QueryType.values()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        button_execute_query.setOnClickListener {
            presenter.runQuery(
                    query_type_spinner.selectedItem as QueryType,
                    attribute_name_spinner.selectedItem as String,
                    query_input_field.text.toString()
            )
        }
    }

    override fun displayResults(books: List<Book>) {
        val navigator = (activity as? QueryNavigator)

        if (navigator == null) {
            displayGenericError("In wrong activity! Not attached to navigator.")
            return
        }

        navigator.displayResults(books)
    }

    override fun displayGenericError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}