package com.example.castalknote.module

import android.content.Context
import androidx.room.Room
import com.example.castalknote.data_source.NoteDAO
import com.example.castalknote.data_source.NoteDatabase
import com.example.castalknote.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NoteDatabase::class.java, "note database")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideNoteDao(db: NoteDatabase) = db.noteDAO()

    @Singleton
    @Provides
    fun provideNoteRepository(noteDao: NoteDAO) = NoteRepository(noteDao)
}
