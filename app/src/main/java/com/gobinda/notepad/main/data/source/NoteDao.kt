package com.gobinda.notepad.main.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gobinda.notepad.main.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * from note_table")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * from note_table WHERE id = :noteId")
    suspend fun getSingleNote(noteId: Long): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateNote(note: Note)

    @Query("DELETE from note_table WHERE id = :noteId")
    suspend fun deleteNote(noteId: Long)
}