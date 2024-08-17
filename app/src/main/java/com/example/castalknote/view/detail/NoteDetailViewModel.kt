package com.example.castalknote.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.castalknote.model.NoteLocalModel
import com.example.castalknote.model.isBlankNote
import com.example.castalknote.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(private val repository: NoteRepository) :
    ViewModel() {

    private val _noteDetail = MutableLiveData<NoteLocalModel>()
    val noteDetail: LiveData<NoteLocalModel> = _noteDetail

    private val _isSaving = MutableLiveData<Boolean>()
    val isSaving: LiveData<Boolean> = _isSaving

    private val _isAdded = MutableLiveData<Boolean>()
    val isAdded: LiveData<Boolean> = _isAdded

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    var isNewNote = false
    private lateinit var noteDefault: NoteLocalModel

    fun getNoteDetailByID() {
        viewModelScope.launch {
            repository.getNoteByID(noteDefault.noteId).apply {
                _noteDetail.postValue(this)
            }
        }
    }

    fun saveNote() {
        if (noteDefault.isBlankNote().not()) {
            if (isNewNote) {
                createNewNote(noteDefault)
                isNewNote = false
            } else {
                updateNote(noteDefault)
            }
        }
    }

    fun setNote(id: Int? = null, title: String? = null, content: String? = null) {
        noteDefault = NoteLocalModel(
            id ?: noteDefault.noteId,
            title ?: noteDefault.noteName,
            content ?: noteDefault.noteContent
        )
    }

    fun setNote(note: NoteLocalModel) {
        noteDefault = note
    }

    private fun createNewNote(note: NoteLocalModel) {
        viewModelScope.launch {
            repository.addNewNote(note)?.let { note ->
                _isAdded.postValue(true)
                setNote(note)
            }
        }
    }

    private fun updateNote(note: NoteLocalModel) {
        viewModelScope.launch {
            _isSaving.postValue(true)
            repository.updateNote(note).let { isUpdateSuccess ->
                _isSaving.postValue(!isUpdateSuccess)
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            repository.deleteNote(noteDefault).let {
                _isDeleted.postValue(it)
            }
        }
    }
}
