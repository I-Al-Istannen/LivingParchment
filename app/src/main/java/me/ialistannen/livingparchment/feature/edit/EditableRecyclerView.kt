package me.ialistannen.livingparchment.feature.edit

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import me.ialistannen.livingparchment.R

class EditableRecyclerView : RecyclerView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private var adapter: EditableAdapter
        get() = getAdapter() as EditableAdapter
        set(value) = setAdapter(value)

    init {
        adapter = EditableAdapter()
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    /**
     * Sets whether the individual cells are editable. Changes are written out once [commit]
     * is called.
     *
     * @param editable true if the cell should be editable
     */
    fun setEditable(editable: Boolean) {
        viewholders().forEach {
            it.setEdit(editable)
        }
    }

    /**
     * Writes all changes out to the underlying properties and returns them.
     *
     * @return the underlying properties set via [setItems]
     */
    fun commit(): List<EditableProperty> {
        return viewholders().map { it.commit() }
    }

    private fun viewholders(): List<EditableViewHolder> {
        val result: MutableList<EditableViewHolder> = mutableListOf()
        for (i in 0 until adapter.itemCount) {
            result.add(findViewHolderForAdapterPosition(i) as EditableViewHolder)
        }

        return result
    }

    /**
     * Sets the items this list displays.
     *
     * @param items the items to display
     */
    fun setItems(items: List<EditableProperty>) {
        adapter.items = items
    }

    class EditableAdapter : RecyclerView.Adapter<EditableViewHolder>() {

        var items: List<EditableProperty> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditableViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            val view = inflater.inflate(R.layout.editable_viewholder_view, parent, false)

            return EditableViewHolder(view)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: EditableViewHolder, position: Int) {
            holder.bind(items[position])
        }
    }

    class EditableViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val key: TextView = view.findViewById(R.id.editable_list_key)
        private val valueUneditable: TextView = view.findViewById(R.id.editable_list_value_uneditable)
        private val valueEditable: EditText = view.findViewById(R.id.editable_list_value_editable)

        private lateinit var property: EditableProperty

        fun bind(property: EditableProperty) {
            this.property = property

            valueUneditable.text = property.getValue()
            valueEditable.setText(property.getValue())
            key.text = property.name

            setEdit(false)
        }

        /**
         * Makes this cell editable.
         */
        fun setEdit(editable: Boolean) {
            if (editable) {
                valueUneditable.visibility = View.GONE
                valueEditable.visibility = View.VISIBLE
            } else {
                valueUneditable.visibility = View.VISIBLE
                valueEditable.visibility = View.GONE
            }
        }

        /**
         * Commits the changes and returns the underlying property.
         *
         * @return the underlying property
         */
        fun commit(): EditableProperty {
            if (valueEditable.text != valueUneditable) {
                property.setValueFromString(valueEditable.text.toString())
            }
            return property
        }
    }
}

interface EditableProperty {

    /**
     * The name of this property.
     */
    val name: String

    /**
     * The value for the property.
     */
    fun getValue(): String

    /**
     * Sets the value of this property, back from the string the user entered.
     *
     * @param value the user entered new value
     */
    fun setValueFromString(value: String)
}