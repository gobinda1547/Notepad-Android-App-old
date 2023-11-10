package com.gobinda.notepad.main.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gobinda.notepad.main.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}