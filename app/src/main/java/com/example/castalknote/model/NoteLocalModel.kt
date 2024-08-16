package com.example.castalknote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteLocalModel(
    @PrimaryKey(true)
    val noteId: Int,
    @ColumnInfo(name = "noteName")
    val noteName: String,
    @ColumnInfo(name = "noteContent")
    val noteContent: String,
)
