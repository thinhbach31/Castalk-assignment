package com.example.castalknote.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.castalknote.model.NoteLocalModel

@Dao
interface NoteDAO {
    @Query("SELECT * FROM note ORDER BY noteId DESC")
    suspend fun getAllNote(): List<NoteLocalModel>

    @Query("SELECT * FROM note WHERE noteId =:id")
    suspend fun getNoteByID(id: Int): NoteLocalModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newNote: NoteLocalModel): Long

    @Transaction
    suspend fun insertAndGetNote(newNote: NoteLocalModel): NoteLocalModel? {
        val noteId = insert(newNote)
        return if (noteId != -1L) getNoteByID(noteId.toInt()) else null
    }

    @Query("UPDATE note SET noteName = :name, noteContent = :content WHERE noteId = :id")
    suspend fun updateNote(id: Int, name: String, content: String): Int

    @Query("DELETE FROM note WHERE noteId = :id")
    suspend fun delete(id: Int)

    @Delete
    suspend fun deleteNote(note: NoteLocalModel): Int
}
