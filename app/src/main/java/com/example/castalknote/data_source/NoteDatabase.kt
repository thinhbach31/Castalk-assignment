package com.example.castalknote.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.castalknote.model.NoteLocalModel

@Database(entities = [NoteLocalModel::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDAO(): NoteDAO
}