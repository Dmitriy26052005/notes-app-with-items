package models

import utils.formatSetString

data class Note(var noteId: Int = 0,
                var noteTitle: String,
                var notePriority: Int,
                var noteCategory: String,
                var isNoteArchived: Boolean = false,
                var items: MutableSet<Item> = mutableSetOf()){

    private var lastItemId = 0
    private fun getNextItemId() = lastItemId++

    fun addItem(item: Item): Boolean {
        item.itemId = getNextItemId()
        return items.add(item)
    }

    fun numberOfItems() = items.size

    fun findOne(id: Int) : Item? {
        return items.find {item -> item.itemId == id}
    }

    fun delete(id: Int) : Boolean {
        return items.removeIf{item -> item.itemId == id}
    }

    fun update(id: Int, newItem: Item) : Boolean {
        val foundItem = findOne(id)

        //if the object exists, use the details passed in the findOne method
        if (foundItem != null) {
            foundItem.itemContents = newItem.itemContents
            foundItem.isItemComplete = newItem.isItemComplete
            return true
        }
        return false
        }

    fun listItems() = if (items.isEmpty()) "No items added" else formatSetString(items)

    override fun toString(): String {
        val archived = if (isNoteArchived) 'Y' else 'N'
        return "$noteId: $noteTitle, Priority($notePriority), Category($noteCategory) Archived($archived) \n${listItems()}"
    }

    fun checkNoteCompletionStatus(): Boolean{
        if (items.isNotEmpty()) {
            for (item in items) {
                if (!item.isItemComplete) {
                    return false
                }
            }
        }
        return true
    }
// functions to manage the items set will gon inside the items attribute.
}
