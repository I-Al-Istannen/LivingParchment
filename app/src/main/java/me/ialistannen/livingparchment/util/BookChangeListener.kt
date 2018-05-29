package me.ialistannen.livingparchment.util

import me.ialistannen.livingparchment.common.model.Book
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.ConcurrentHashMap

interface BookChangeListener {

    /**
     * Called when a book changed.
     *
     * @param books the changed books
     */
    fun onBookChanged(books: List<Book>)
}

object BookChangeListeners {

    private val listeners: MutableSet<WeakReference<BookChangeListener>> = Collections.newSetFromMap(
            ConcurrentHashMap()
    )

    /**
     * Adds a listener to this object.
     *
     * @param bookChangeListener the listener to add
     */
    fun addListener(bookChangeListener: BookChangeListener) {
        listeners.add(WeakReference(bookChangeListener))
    }

    /**
     * Notifies all listeners that some books changed.
     *
     * @param books the changed books
     */
    fun booksChanged(vararg books: Book) {
        booksChanged(books.toList())
    }

    /**
     * Notifies all listeners that some books changed.
     *
     * @param books the changed books
     */
    fun booksChanged(books: Collection<Book>) {
        val changedBooks = books.toList()

        val iterator = listeners.iterator()
        while (iterator.hasNext()) {
            val reference = iterator.next()
            if (reference.get() == null) {
                iterator.remove()
                continue
            }

            reference.get()?.onBookChanged(changedBooks)
        }
    }
}