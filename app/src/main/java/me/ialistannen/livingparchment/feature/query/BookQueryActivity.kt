package me.ialistannen.livingparchment.feature.query

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_book_query.*
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.api.query.QueryType
import me.ialistannen.livingparchment.common.model.Book
import me.ialistannen.livingparchment.feature.BaseActivity
import javax.inject.Inject

class BookQueryActivity : BaseActivity(), QueryScreenContract.View {

    @Inject
    override lateinit var presenter: QueryScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_query)

        setSupportActionBar(actionbar as Toolbar)

        query_type_spinner.adapter = ArrayAdapter<QueryType>(this,
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

    override fun displayDetailPage(book: Book) {
        Toast.makeText(this, "Detail page for ${book.title}", Toast.LENGTH_LONG).show()
    }

    override fun displayResults(books: List<Book>) {
        Toast.makeText(this, "Results: ${books.size}", Toast.LENGTH_LONG).show()
    }

    override fun displayGenericError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
