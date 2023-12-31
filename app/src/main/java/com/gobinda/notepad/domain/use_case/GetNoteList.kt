package com.gobinda.notepad.domain.use_case

import com.gobinda.notepad.common.NoteRepository
import com.gobinda.notepad.common.toNoteAsListItem
import com.gobinda.notepad.domain.model.NoteAsListItem
import com.gobinda.notepad.domain.util.NoteSortingOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNoteList(private val repository: NoteRepository) {

    /**
     * By taking the [sortingOrder] as an input, we are keeping this function
     * open for writing new sorting-order. Cause in future new requirements
     * could came like ascending by note name or descending by note name. One
     * more thing to mention and that is the default value of the input param
     * is [NoteSortingOrder.Ascending].
     *
     * @param sortingOrder in which order you want to get the saved note list.
     */
    operator fun invoke(
        sortingOrder: NoteSortingOrder = NoteSortingOrder.Ascending
    ): Flow<List<NoteAsListItem>> {
        return repository.getAllNotes().map { notes ->
            when (sortingOrder) {
                NoteSortingOrder.Ascending -> {
                    notes.sortedBy { it.lastEditTime }
                }

                NoteSortingOrder.Descending -> {
                    notes.sortedByDescending { it.lastEditTime }
                }
            }
        }.map { noteModelList -> noteModelList.map { it.toNoteAsListItem() } }
    }
}