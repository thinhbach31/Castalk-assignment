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

    suspend fun getNoteByID(id: Int): NoteLocalModel {
        return withContext(Dispatchers.IO) {
            noteDAO.getNoteByID(id)
        }
    }

    suspend fun addNewNote(note: NoteLocalModel): NoteLocalModel? {
        return withContext(Dispatchers.IO) {
            noteDAO.insertAndGetNote(note)
        }
    }

    suspend fun updateNote(note: NoteLocalModel): Boolean {
        return withContext(Dispatchers.IO) {
            // > 0 means update successfully
            noteDAO.updateNote(note.noteId, note.noteName, note.noteContent) > 0
        }
    }

    suspend fun deleteNote(note: NoteLocalModel): Boolean {
        return withContext(Dispatchers.IO) {
            noteDAO.deleteNote(note) > 0
        }
    }
}
