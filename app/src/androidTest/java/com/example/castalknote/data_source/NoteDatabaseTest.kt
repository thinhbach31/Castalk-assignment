package com.example.castalknote.data_source

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.castalknote.model.NoteLocalModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest : TestCase() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: NoteDatabase
    private lateinit var dao: NoteDAO

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).build()
        dao = db.noteDAO()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun testInsertNote() = runBlocking {
        val note = NoteLocalModel(0, "this is a test note", "content of a test note")
        val id = dao.insert(note)
        val fetchedNote = dao.getNoteByID(id.toInt())
        assertEquals(note.noteName, fetchedNote.noteName)
        assertEquals(note.noteContent, fetchedNote.noteContent)
    }

    @Test
    fun testUpdateNote() = runBlocking {
        val note = NoteLocalModel(0, "this is a test note", "content of a test note")
        val id = dao.insert(note)

        val newTitle = "this is a new note's title"
        val newContent = "new content"
        val updatedNoteID = dao.updateNote(id.toInt(), newTitle, newContent)
        val updatedNote = dao.getNoteByID(updatedNoteID)

        assertEquals(newTitle, updatedNote.noteName)
        assertEquals(newContent, updatedNote.noteContent)
    }

    @Test
    fun testDeleteNote() = runBlocking {
        val note = NoteLocalModel(0, "this is a test note", "content of a test note")
        val id = dao.insert(note)
        val insertedNote = dao.getNoteByID(id.toInt())

        val rowDeleted = dao.deleteNote(insertedNote)
        assertEquals(rowDeleted, 1)

        val fetchedNote = dao.getNoteByID(id.toInt())
        assertNull(fetchedNote)
    }
}
