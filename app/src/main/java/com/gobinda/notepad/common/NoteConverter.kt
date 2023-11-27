package com.gobinda.notepad.common

import com.gobinda.notepad.data.model.NoteModel
import com.gobinda.notepad.domain.model.Note
import com.gobinda.notepad.domain.model.NoteAsListItem
import com.gobinda.notepad.domain.util.NoteUtil

fun NoteModel.toNote(): Note {
    return Note(title, content, lastEditTime, id)
}

fun NoteModel.toNoteAsListItem(): NoteAsListItem {
    return NoteAsListItem(
        title = NoteUtil.getFirstNotEmptyLineWithTrim(title),
        content = NoteUtil.getFirstNotEmptyLineWithTrim(content),
        id = id
    )
}

fun Note.toNoteModel(): NoteModel {
    return NoteModel(title, content, lastEditTime, id)
}