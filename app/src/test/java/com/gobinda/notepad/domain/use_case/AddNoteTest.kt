package com.gobinda.notepad.domain.use_case

import com.gobinda.notepad.R
import com.gobinda.notepad.common.NoteRepository
import com.gobinda.notepad.domain.model.Note
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class AddNoteTest {

    private lateinit var repository: NoteRepository

    @Before
    fun setUp() {
        repository = mockk()
    }

    @Test
    fun `trying to add note with empty title`() = runBlocking {
        val addNoteUseCase = AddNote(repository)
        val newNoteToAdd = Note(
            title = "",
            content = "demo content",
            lastEditTime = System.currentTimeMillis()
        )
        val desiredExceptionFound = try {
            addNoteUseCase(newNoteToAdd)
            false
        } catch (e: AddNoteException) {
            TestCase.assertEquals(R.string.text_empty_title, e.reason)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
        assert(desiredExceptionFound)
    }

    @Test
    fun `trying to add note with empty content`() = runBlocking {
        val addNoteUseCase = AddNote(repository)
        val newNoteToAdd = Note(
            title = "demo title",
            content = "",
            lastEditTime = System.currentTimeMillis()
        )
        val desiredExceptionFound = try {
            addNoteUseCase(newNoteToAdd)
            false
        } catch (e: AddNoteException) {
            TestCase.assertEquals(R.string.text_empty_content, e.reason)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
        assert(desiredExceptionFound)
    }

    @Test
    fun `trying to add a note but failed in room database`() = runBlocking {
        coEvery { repository.addOrUpdateNote(any()) } returns 0 // means 0 row affected
        val addNoteUseCase = AddNote(repository)
        val newNoteToAdd = Note(
            title = "demo title",
            content = "demo content",
            lastEditTime = System.currentTimeMillis()
        )
        val desiredExceptionFound = try {
            addNoteUseCase(newNoteToAdd)
            false
        } catch (e: AddNoteException) {
            TestCase.assertEquals(R.string.text_unknown_exception, e.reason)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
        assert(desiredExceptionFound)
    }

    @Test
    fun `trying to add a note - successful case`() = runBlocking {
        coEvery { repository.addOrUpdateNote(any()) } returns 1 // means 1 row affected
        val addNoteUseCase = AddNote(repository)
        val newNoteToAdd = Note(
            title = "demo title",
            content = "demo content",
            lastEditTime = System.currentTimeMillis()
        )
        val isAnyException = try {
            addNoteUseCase(newNoteToAdd)
            false
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
        assert(isAnyException.not())
    }
}