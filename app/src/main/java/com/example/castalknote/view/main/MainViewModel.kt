package com.example.castalknote.view.main

import androidx.lifecycle.ViewModel
import com.example.castalknote.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: NoteRepository) : ViewModel() {
}
