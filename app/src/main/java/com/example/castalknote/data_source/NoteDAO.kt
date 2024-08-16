package com.example.castalknote.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.castalknote.model.NoteLocalModel

@Dao
interface NoteDAO {
    @Query("SELECT * FROM note")
    suspend fun getAllNote(): List<NoteLocalModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg newNote: NoteLocalModel)

    @Query("UPDATE note SET noteName = :name, noteContent = :content WHERE noteId = :id")
    suspend fun updateNote(id: Int, name: String, content: String)

    @Query("DELETE FROM note WHERE noteId = :id")
    suspend fun delete(id: Int)
}
