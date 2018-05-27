package me.ialistannen.livingparchment.feature.location.add

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.ialistannen.livingparchment.R
import me.ialistannen.livingparchment.common.model.BookLocation

class LocationRecyclerView : RecyclerView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private var adapter: LocationAdapter
        set(value) = setAdapter(value)
        get() = getAdapter() as LocationAdapter

    init {
        adapter = LocationAdapter()
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    fun setLocations(locations: List<BookLocation>) {
        adapter.locations = locations
    }

    /**
     * Sets the long press listener. Must be set before the items are set via [setLocations].
     *
     * @param listener the listener
     */
    fun setContextMenuListener(listener: (item: BookLocation, menu: ContextMenu, v: View,
                                          menuInfo: ContextMenu.ContextMenuInfo?) -> Unit) {
        adapter.contextMenuListener = listener
    }

    private class LocationAdapter : RecyclerView.Adapter<LocationViewHolder>() {

        var locations: List<BookLocation> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        var contextMenuListener: (BookLocation, ContextMenu, View,
                                  ContextMenu.ContextMenuInfo?) -> Unit = { _, _, _, _ -> }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            val view = inflater.inflate(R.layout.book_detail_viewholder_view, parent, false)

            return LocationViewHolder(view)
        }

        override fun getItemCount(): Int = locations.size

        override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
            holder.bind(locations[position])
            holder.setContextMenuListener(contextMenuListener)
        }
    }

    private class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.book_detail_key)
        private val value: TextView = view.findViewById(R.id.book_detail_value)
        private var bookLocation: BookLocation? = null

        fun bind(bookLocation: BookLocation) {
            this.bookLocation = bookLocation
            title.text = bookLocation.name
            value.text = bookLocation.description
        }

        fun setContextMenuListener(listener: (BookLocation, ContextMenu, View,
                                              ContextMenu.ContextMenuInfo?) -> Unit) {
            itemView.setOnCreateContextMenuListener({ menu, v, menuInfo ->
                listener.invoke(bookLocation!!, menu, v, menuInfo)
            })
        }
    }

}