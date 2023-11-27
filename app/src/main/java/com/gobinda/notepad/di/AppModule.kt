package com.gobinda.notepad.di

import android.content.Context
import androidx.room.Room
import com.gobinda.notepad.common.NoteRepository
import com.gobinda.notepad.data.repository.NoteRepositoryImpl
import com.gobinda.notepad.data.source.NoteDao
import com.gobinda.notepad.data.source.NoteDatabase
import com.gobinda.notepad.domain.use_case.AddNote
import com.gobinda.notepad.domain.use_case.DeleteNote
import com.gobinda.notepad.domain.use_case.GetNoteList
import com.gobinda.notepad.domain.use_case.GetSingleNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext appContext: Context): NoteDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = NoteDatabase::class.java,
            name = NoteDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    @Provides
    fun provideAddNoteUseCase(repository: NoteRepository): AddNote {
        return AddNote(repository)
    }

    @Provides
    fun provideDeleteNoteUseCase(repository: NoteRepository): DeleteNote {
        return DeleteNote(repository)
    }

    @Provides
    fun provideGetNoteListUseCase(repository: NoteRepository): GetNoteList {
        return GetNoteList(repository)
    }

    @Provides
    fun provideGetSingleNoteUseCase(repository: NoteRepository): GetSingleNote {
        return GetSingleNote(repository)
    }
}