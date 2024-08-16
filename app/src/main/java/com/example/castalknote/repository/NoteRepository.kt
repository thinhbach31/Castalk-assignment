package com.example.castalknote.repository

import com.example.castalknote.data_source.NoteDAO
import com.example.castalknote.model.NoteLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDAO: NoteDAO) {

    suspend fun getAllNotes(): List<NoteLocalModel> {
        return withContext(Dispatchers.IO) {
            noteDAO.getAllNote()
        }
    }

    suspend fun addNewNote(note: NoteLocalModel) {
        withContext(Dispatchers.IO) {
            noteDAO.insert(note)
        }
    }

    suspend fun updateNote(note: NoteLocalModel) {
        withContext(Dispatchers.IO) {
            note.apply {
                noteDAO.updateNote(noteId, noteName, noteContent)
            }
        }
    }

    suspend fun deleteNote(id: Int) {
        withContext(Dispatchers.IO) {
            noteDAO.delete(id)
        }
    }
}
