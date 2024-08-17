package com.example.castalknote.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note")
data class NoteLocalModel(
    @PrimaryKey(true)
    var noteId: Int,
    @ColumnInfo(name = "noteName")
    var noteName: String,
    @ColumnInfo(name = "noteContent")
    var noteContent: String,
) : Parcelable

fun NoteLocalModel.isBlankNote(): Boolean {
    return noteId == 0 && noteName == "" && noteContent == ""
}

