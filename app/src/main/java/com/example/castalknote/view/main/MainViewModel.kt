package com.example.castalknote.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.castalknote.model.NoteLocalModel
import com.example.castalknote.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    private val _noteLiveData = MutableLiveData<ArrayList<NoteLocalModel>>()
    var noteLiveData: LiveData<ArrayList<NoteLocalModel>> = _noteLiveData

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    fun getAllNote() {
        viewModelScope.launch {
            repository.getAllNotes().apply {
                _noteLiveData.postValue(this.toArrayList())
            }
        }
    }

    fun deleteNote(note: NoteLocalModel) {
        viewModelScope.launch {
            repository.deleteNote(note).let {
                _isDeleted.postValue(it)
            }
        }
    }

    private fun List<NoteLocalModel>.toArrayList(): ArrayList<NoteLocalModel> {
        return arrayListOf<NoteLocalModel>().apply {
            this.addAll(this@toArrayList)
        }
    }
}
